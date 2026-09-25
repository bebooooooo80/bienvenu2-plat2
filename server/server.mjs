import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import crypto from 'node:crypto';
import { initDatabase } from './init_db.js';

const PORT = 3000;
const db = initDatabase();

function hashPassword(password, salt) {
  return crypto.pbkdf2Sync(password, salt, 10000, 32, 'sha256').toString('hex');
}

function hashCode(code) {
  return crypto.createHash('sha256').update(code.trim().toUpperCase()).digest('hex');
}

function parseJsonBody(req) {
  return new Promise((resolve, reject) => {
    let body = '';
    req.on('data', chunk => {
      body += chunk;
      if (body.length > 1e6) {
        req.destroy();
        reject(new Error('Payload too large'));
      }
    });
    req.on('end', () => {
      try {
        resolve(body ? JSON.parse(body) : {});
      } catch (err) {
        reject(err);
      }
    });
    req.on('error', reject);
  });
}

function sendJson(res, statusCode, data) {
  res.writeHead(statusCode, {
    'Content-Type': 'application/json; charset=utf-8',
    'Cache-Control': 'no-store, no-cache, must-revalidate, proxy-revalidate'
  });
  res.end(JSON.stringify(data));
}

function getAuthUser(req) {
  const authHeader = req.headers['authorization'] || '';
  let token = '';
  if (authHeader.startsWith('Bearer ')) {
    token = authHeader.slice(7).trim();
  } else {
    // Check cookie
    const cookieHeader = req.headers['cookie'] || '';
    const match = cookieHeader.match(/session_token=([^;]+)/);
    if (match) token = match[1];
  }

  if (!token) return null;

  const session = db.prepare("SELECT * FROM sessions WHERE token = ?").get(token);
  if (!session) return null;

  const now = new Date();
  if (new Date(session.expires_at) < now) {
    db.prepare("DELETE FROM sessions WHERE token = ?").run(token);
    return null;
  }

  const user = db.prepare("SELECT * FROM users WHERE uid = ?").get(session.user_id);
  if (!user) return null;

  // Evaluate dynamic expirations based on trusted server time
  evaluateUserAccess(user, now);
  return user;
}

function evaluateUserAccess(user, now = new Date()) {
  let changed = false;
  if (user.access_type === 'TRIAL' && user.access_status === 'ACTIVE') {
    if (user.trial_expires_at && new Date(user.trial_expires_at) <= now) {
      user.access_status = 'TRIAL_EXPIRED';
      db.prepare("UPDATE users SET access_status = 'TRIAL_EXPIRED' WHERE uid = ?").run(user.uid);
      db.prepare("INSERT INTO access_audit_logs (user_id, event_type, timestamp, metadata) VALUES (?, 'TRIAL_EXPIRED', ?, ?)")
        .run(user.uid, now.toISOString(), JSON.stringify({ expiredAt: user.trial_expires_at }));
      changed = true;
    }
  } else if (user.access_type === 'ANNUAL' && user.access_status === 'ACTIVE') {
    if (user.annual_expires_at && new Date(user.annual_expires_at) <= now) {
      user.access_status = 'EXPIRED';
      db.prepare("UPDATE users SET access_status = 'EXPIRED' WHERE uid = ?").run(user.uid);
      db.prepare("INSERT INTO access_audit_logs (user_id, event_type, timestamp, metadata) VALUES (?, 'ANNUAL_EXPIRED', ?, ?)")
        .run(user.uid, now.toISOString(), JSON.stringify({ expiredAt: user.annual_expires_at }));
      changed = true;
    }
  }
  return changed;
}

function createSession(userId) {
  const token = crypto.randomBytes(32).toString('hex');
  const now = new Date();
  const expiresAt = new Date(now.getTime() + 30 * 24 * 60 * 60 * 1000); // 30 days
  db.prepare("INSERT INTO sessions (token, user_id, created_at, expires_at) VALUES (?, ?, ?, ?)")
    .run(token, userId, now.toISOString(), expiresAt.toISOString());
  return token;
}

process.on('uncaughtException', (err) => {
  console.error('Unhandled Exception in server process:', err);
});
process.on('unhandledRejection', (reason, promise) => {
  console.error('Unhandled Rejection at:', promise, 'reason:', reason);
});

const server = http.createServer(async (req, res) => {
  try {
    const parsedUrl = new URL(req.url, `http://${req.headers.host || 'localhost'}`);
    const pathname = parsedUrl.pathname;

    // CORS headers for safety
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');

    if (req.method === 'OPTIONS') {
      res.writeHead(204);
      res.end();
      return;
    }

  // -------------------------------------------------------------
  // API ROUTES
  // -------------------------------------------------------------

  // 1. REGISTER
  if (pathname === '/api/auth/register' && req.method === 'POST') {
    try {
      const { name, loginIdentifier, password } = await parseJsonBody(req);
      if (!name || !loginIdentifier || !password) {
        return sendJson(res, 400, { success: false, message: 'يرجى إدخال جميع الحقول المطلوبة.' });
      }
      if (password.length < 6) {
        return sendJson(res, 400, { success: false, message: 'يجب أن لا تقل كلمة المرور عن 6 أحرف.' });
      }

      const cleanIdentifier = loginIdentifier.trim().toLowerCase();
      const existing = db.prepare("SELECT uid FROM users WHERE login_identifier = ?").get(cleanIdentifier);
      if (existing) {
        return sendJson(res, 409, { success: false, message: 'هذا المعرف / البريد مسجل مسبقاً.' });
      }

      const uid = 'usr_' + crypto.randomBytes(10).toString('hex');
      const salt = crypto.randomBytes(16).toString('hex');
      const passHash = hashPassword(password, salt);
      const nowIso = new Date().toISOString();

      db.prepare(`
        INSERT INTO users (uid, name, login_identifier, password_hash, password_salt, role, created_at, access_type, access_status)
        VALUES (?, ?, ?, ?, ?, 'student', ?, 'NONE', 'INACTIVE')
      `).run(uid, name.trim(), cleanIdentifier, passHash, salt, nowIso);

      const token = createSession(uid);
      const user = db.prepare("SELECT uid, name, login_identifier, role, created_at, trial_used, trial_started_at, trial_expires_at, access_type, access_status, annual_activated_at, annual_expires_at FROM users WHERE uid = ?").get(uid);

      return sendJson(res, 201, {
        success: true,
        token,
        user,
        message: 'تم إنشاء الحساب بنجاح.'
      });
    } catch (err) {
      console.error('Register error:', err);
      return sendJson(res, 500, { success: false, message: 'حدث خطأ في الخادم أثناء التسجيل.' });
    }
  }

  // 2. LOGIN
  if (pathname === '/api/auth/login' && req.method === 'POST') {
    try {
      const { loginIdentifier, password } = await parseJsonBody(req);
      if (!loginIdentifier || !password) {
        return sendJson(res, 400, { success: false, message: 'يرجى إدخال اسم المستخدم وكلمة المرور.' });
      }

      const cleanIdentifier = loginIdentifier.trim().toLowerCase();
      const user = db.prepare("SELECT * FROM users WHERE login_identifier = ?").get(cleanIdentifier);
      if (!user) {
        return sendJson(res, 401, { success: false, message: 'بيانات الدخول غير صحيحة.' });
      }

      const testHash = hashPassword(password, user.password_salt);
      if (testHash !== user.password_hash) {
        return sendJson(res, 401, { success: false, message: 'بيانات الدخول غير صحيحة.' });
      }

      evaluateUserAccess(user);
      const token = createSession(user.uid);

      const safeUser = {
        uid: user.uid,
        name: user.name,
        loginIdentifier: user.login_identifier,
        role: user.role,
        createdAt: user.created_at,
        trialUsed: Boolean(user.trial_used),
        trialStartedAt: user.trial_started_at,
        trialExpiresAt: user.trial_expires_at,
        accessType: user.access_type,
        accessStatus: user.access_status,
        annualActivatedAt: user.annual_activated_at,
        annualExpiresAt: user.annual_expires_at
      };

      return sendJson(res, 200, {
        success: true,
        token,
        user: safeUser,
        message: 'تم تسجيل الدخول بنجاح.'
      });
    } catch (err) {
      console.error('Login error:', err);
      return sendJson(res, 500, { success: false, message: 'حدث خطأ في الخادم أثناء الدخول.' });
    }
  }

  // 3. GET CURRENT USER & ACCESS
  if (pathname === '/api/auth/me' && req.method === 'GET') {
    const user = getAuthUser(req);
    if (!user) {
      return sendJson(res, 401, { success: false, message: 'غير مسجل الدخول.' });
    }

    return sendJson(res, 200, {
      success: true,
      user: {
        uid: user.uid,
        name: user.name,
        loginIdentifier: user.login_identifier,
        role: user.role,
        createdAt: user.created_at,
        trialUsed: Boolean(user.trial_used),
        trialStartedAt: user.trial_started_at,
        trialExpiresAt: user.trial_expires_at,
        accessType: user.access_type,
        accessStatus: user.access_status,
        annualActivatedAt: user.annual_activated_at,
        annualExpiresAt: user.annual_expires_at,
        serverTime: new Date().toISOString()
      }
    });
  }

  // 4. START 24-HOUR FREE TRIAL
  if (pathname === '/api/access/start-trial' && req.method === 'POST') {
    const user = getAuthUser(req);
    if (!user) {
      return sendJson(res, 401, { success: false, message: 'يرجى تسجيل الدخول أو إنشاء حساب أولاً.' });
    }

    // STRICT CHECK: Trial only once per account!
    if (user.trial_used === 1) {
      return sendJson(res, 403, {
        success: false,
        code: 'TRIAL_ALREADY_USED',
        message: 'لقد استخدمت الفترة التجريبية المجانية من قبل. يمكنك تفعيل المنصة باستخدام كود التفعيل.'
      });
    }

    if (user.access_type === 'ANNUAL' && user.access_status === 'ACTIVE') {
      return sendJson(res, 400, {
        success: false,
        message: 'لديك اشتراك سنوي مفعل بالفعل.'
      });
    }

    // Atomic update of trial state with trusted server time
    const now = new Date();
    const trialExpiresAt = new Date(now.getTime() + 24 * 60 * 60 * 1000); // 24 hours

    db.exec('BEGIN IMMEDIATE');
    try {
      db.prepare(`
        UPDATE users
        SET trial_used = 1,
            trial_started_at = ?,
            trial_expires_at = ?,
            access_type = 'TRIAL',
            access_status = 'ACTIVE'
        WHERE uid = ?
      `).run(now.toISOString(), trialExpiresAt.toISOString(), user.uid);

      db.prepare(`
        INSERT INTO access_audit_logs (user_id, event_type, timestamp, metadata)
        VALUES (?, 'TRIAL_STARTED', ?, ?)
      `).run(user.uid, now.toISOString(), JSON.stringify({ expiresAt: trialExpiresAt.toISOString() }));

      db.exec('COMMIT');

      return sendJson(res, 200, {
        success: true,
        accessType: 'TRIAL',
        accessStatus: 'ACTIVE',
        trialStartedAt: now.toISOString(),
        trialExpiresAt: trialExpiresAt.toISOString(),
        message: 'تم تفعيل الفترة التجريبية المجانية لمدة 24 ساعة بنجاح!'
      });
    } catch (err) {
      db.exec('ROLLBACK');
      console.error('Trial start error:', err);
      return sendJson(res, 500, { success: false, message: 'حدث خطأ في الخادم أثناء بدء التجربة.' });
    }
  }

  // 5. ACTIVATE PAID ANNUAL CODE (365 DAYS)
  if (pathname === '/api/access/activate-code' && req.method === 'POST') {
    const user = getAuthUser(req);
    if (!user) {
      return sendJson(res, 401, { success: false, message: 'يرجى تسجيل الدخول أولاً لتفعيل الكود.' });
    }

    try {
      const { code } = await parseJsonBody(req);
      if (!code || typeof code !== 'string') {
        return sendJson(res, 400, { success: false, message: 'يرجى كتابة كود التفعيل.' });
      }

      const cleanCode = code.trim().toUpperCase();
      const codeHash = hashCode(cleanCode);

      // ATOMIC TRANSACTION: Code verification & activation
      db.exec('BEGIN IMMEDIATE');
      try {
        const codeRow = db.prepare("SELECT * FROM activation_codes WHERE code_hash = ?").get(codeHash);

        if (!codeRow) {
          db.exec('ROLLBACK');
          return sendJson(res, 400, {
            success: false,
            code: 'INVALID_CODE',
            message: 'كود التفعيل غير صحيح، يرجى التأكد من كتابته بدقة.'
          });
        }

        if (codeRow.status !== 'UNUSED') {
          db.exec('ROLLBACK');
          return sendJson(res, 400, {
            success: false,
            code: 'CODE_ALREADY_USED',
            message: 'هذا الكود تم استخدامه بالفعل.'
          });
        }

        const now = new Date();
        const annualExpiresAt = new Date(now.getTime() + 365 * 24 * 60 * 60 * 1000); // 365 days

        // 1. Update code status to ACTIVE and assign to this user
        db.prepare(`
          UPDATE activation_codes
          SET status = 'ACTIVE',
              activated_at = ?,
              expires_at = ?,
              assigned_user_id = ?
          WHERE id = ?
        `).run(now.toISOString(), annualExpiresAt.toISOString(), user.uid, codeRow.id);

        // 2. Upgrade student account: preserve all existing profile, upgrade to ANNUAL
        db.prepare(`
          UPDATE users
          SET access_type = 'ANNUAL',
              access_status = 'ACTIVE',
              annual_activated_at = ?,
              annual_expires_at = ?,
              activation_code_id = ?
          WHERE uid = ?
        `).run(now.toISOString(), annualExpiresAt.toISOString(), codeRow.id, user.uid);

        // 3. Log access audit event
        db.prepare(`
          INSERT INTO access_audit_logs (user_id, event_type, timestamp, metadata)
          VALUES (?, 'CODE_ACTIVATED', ?, ?)
        `).run(user.uid, now.toISOString(), JSON.stringify({
          codeId: codeRow.id,
          annualExpiresAt: annualExpiresAt.toISOString()
        }));

        db.exec('COMMIT');

        return sendJson(res, 200, {
          success: true,
          accessType: 'ANNUAL',
          accessStatus: 'ACTIVE',
          annualActivatedAt: now.toISOString(),
          annualExpiresAt: annualExpiresAt.toISOString(),
          message: 'تهانينا! تم تفعيل الاشتراك السنوي الكامل لمدة 365 يوماً بنجاح 🎉'
        });
      } catch (txErr) {
        db.exec('ROLLBACK');
        throw txErr;
      }
    } catch (err) {
      console.error('Activate code error:', err);
      return sendJson(res, 500, { success: false, message: 'حدث خطأ في الخادم أثناء تفعيل الكود.' });
    }
  }

  // 6. ADMIN OVERVIEW
  if (pathname === '/api/admin/overview' && req.method === 'GET') {
    const user = getAuthUser(req);
    if (!user || user.role !== 'admin') {
      return sendJson(res, 403, { success: false, message: 'غير مصرح لك بالوصول إلى لوحة الإدارة.' });
    }

    const totalCodes = db.prepare("SELECT COUNT(*) as count FROM activation_codes").get().count;
    const unusedCodes = db.prepare("SELECT COUNT(*) as count FROM activation_codes WHERE status = 'UNUSED'").get().count;
    const activeCodes = db.prepare("SELECT COUNT(*) as count FROM activation_codes WHERE status = 'ACTIVE'").get().count;
    const expiredCodes = db.prepare("SELECT COUNT(*) as count FROM activation_codes WHERE status = 'EXPIRED'").get().count;
    const suspendedCodes = db.prepare("SELECT COUNT(*) as count FROM activation_codes WHERE status = 'SUSPENDED'").get().count;

    const totalStudents = db.prepare("SELECT COUNT(*) as count FROM users WHERE role = 'student'").get().count;
    const activeTrialStudents = db.prepare("SELECT COUNT(*) as count FROM users WHERE role = 'student' AND access_type = 'TRIAL' AND access_status = 'ACTIVE'").get().count;
    const activeAnnualStudents = db.prepare("SELECT COUNT(*) as count FROM users WHERE role = 'student' AND access_type = 'ANNUAL' AND access_status = 'ACTIVE'").get().count;

    return sendJson(res, 200, {
      success: true,
      codes: {
        total: totalCodes,
        unused: unusedCodes,
        active: activeCodes,
        expired: expiredCodes,
        suspended: suspendedCodes
      },
      students: {
        total: totalStudents,
        activeTrial: activeTrialStudents,
        activeAnnual: activeAnnualStudents
      }
    });
  }

  // 7. ADMIN STUDENTS LIST
  if (pathname === '/api/admin/students' && req.method === 'GET') {
    const user = getAuthUser(req);
    if (!user || user.role !== 'admin') {
      return sendJson(res, 403, { success: false, message: 'غير مصرح لك بالوصول.' });
    }

    const search = parsedUrl.searchParams.get('q') || '';
    let students;
    if (search.trim()) {
      const q = `%${search.trim().toLowerCase()}%`;
      students = db.prepare(`
        SELECT uid, name, login_identifier, role, created_at, trial_used, trial_started_at, trial_expires_at, access_type, access_status, annual_activated_at, annual_expires_at
        FROM users
        WHERE role = 'student' AND (LOWER(name) LIKE ? OR LOWER(login_identifier) LIKE ?)
        ORDER BY created_at DESC
        LIMIT 50
      `).all(q, q);
    } else {
      students = db.prepare(`
        SELECT uid, name, login_identifier, role, created_at, trial_used, trial_started_at, trial_expires_at, access_type, access_status, annual_activated_at, annual_expires_at
        FROM users
        WHERE role = 'student'
        ORDER BY created_at DESC
        LIMIT 50
      `).all();
    }

    return sendJson(res, 200, { success: true, students });
  }

  // 8. ADMIN SUSPEND STUDENT
  if (pathname === '/api/admin/student/suspend' && req.method === 'POST') {
    const user = getAuthUser(req);
    if (!user || user.role !== 'admin') {
      return sendJson(res, 403, { success: false, message: 'غير مصرح.' });
    }

    const { studentUid } = await parseJsonBody(req);
    db.prepare("UPDATE users SET access_status = 'SUSPENDED' WHERE uid = ? AND role = 'student'").run(studentUid);
    db.prepare("INSERT INTO access_audit_logs (user_id, event_type, timestamp, metadata) VALUES (?, 'SUSPENDED', ?, ?)")
      .run(studentUid, new Date().toISOString(), JSON.stringify({ suspendedBy: user.uid }));

    return sendJson(res, 200, { success: true, message: 'تم إيقاف حساب الطالب.' });
  }

  // 9. ADMIN REACTIVATE STUDENT
  if (pathname === '/api/admin/student/reactivate' && req.method === 'POST') {
    const user = getAuthUser(req);
    if (!user || user.role !== 'admin') {
      return sendJson(res, 403, { success: false, message: 'غير مصرح.' });
    }

    const { studentUid } = await parseJsonBody(req);
    db.prepare("UPDATE users SET access_status = 'ACTIVE' WHERE uid = ? AND role = 'student'").run(studentUid);
    db.prepare("INSERT INTO access_audit_logs (user_id, event_type, timestamp, metadata) VALUES (?, 'REACTIVATED', ?, ?)")
      .run(studentUid, new Date().toISOString(), JSON.stringify({ reactivatedBy: user.uid }));

    return sendJson(res, 200, { success: true, message: 'تمت إعادة تفعيل حساب الطالب.' });
  }

  // 10. ADMIN EXPORT CODES CSV (Protected)
  if (pathname === '/api/admin/export-codes' && req.method === 'GET') {
    const user = getAuthUser(req);
    if (!user || user.role !== 'admin') {
      return sendJson(res, 403, { success: false, message: 'غير مصرح بالوصول.' });
    }

    const csvPath = path.resolve('server/admin_exports/codes_export_500.csv');
    if (!fs.existsSync(csvPath)) {
      return sendJson(res, 404, { success: false, message: 'ملف التصدير غير موجود.' });
    }

    const csvContent = fs.readFileSync(csvPath, 'utf-8');
    res.writeHead(200, {
      'Content-Type': 'text/csv; charset=utf-8',
      'Content-Disposition': 'attachment; filename="codes_export_500.csv"',
      'Cache-Control': 'no-store'
    });
    res.end(csvContent);
    return;
  }

  // -------------------------------------------------------------
  // STATIC FILE SERVING (index.html & assets)
  // -------------------------------------------------------------
  let filePath = path.join(process.cwd(), pathname === '/' ? 'index.html' : pathname);

  if (!fs.existsSync(filePath) || fs.statSync(filePath).isDirectory()) {
    filePath = path.join(process.cwd(), 'index.html');
  }

  const ext = path.extname(filePath).toLowerCase();
  const mimeTypes = {
    '.html': 'text/html; charset=utf-8',
    '.js': 'application/javascript; charset=utf-8',
    '.json': 'application/json; charset=utf-8',
    '.css': 'text/css; charset=utf-8',
    '.svg': 'image/svg+xml',
    '.png': 'image/png',
    '.webp': 'image/webp'
  };

  const contentType = mimeTypes[ext] || 'application/octet-stream';
  fs.readFile(filePath, (readErr, data) => {
    if (readErr) {
      if (!res.headersSent) {
        res.writeHead(404, { 'Content-Type': 'text/plain' });
        res.end('File Not Found');
      }
      return;
    }
    if (!res.headersSent) {
      res.writeHead(200, { 'Content-Type': contentType });
      res.end(data);
    }
  });
  } catch (err) {
    console.error('Unhandled request error:', err);
    if (!res.headersSent) {
      res.writeHead(500, { 'Content-Type': 'application/json; charset=utf-8' });
      res.end(JSON.stringify({ success: false, message: 'Internal Server Error' }));
    }
  }
});

server.on('error', (err) => {
  console.error('Server encountered error:', err);
});

server.listen(PORT, () => {
  console.log(`Backend server running on http://localhost:${PORT}`);
});
