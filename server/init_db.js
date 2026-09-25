import { DatabaseSync } from 'node:sqlite';
import crypto from 'node:crypto';
import fs from 'node:fs';
import path from 'node:path';

const DB_PATH = path.resolve('server/database.sqlite');
const CSV_PATH = path.resolve('server/admin_exports/codes_export_500.csv');

export function initDatabase() {
  const db = new DatabaseSync(DB_PATH);

  // 1. Enable WAL mode for better concurrency
  db.exec('PRAGMA journal_mode = WAL;');

  // 2. Create tables
  db.exec(`
    CREATE TABLE IF NOT EXISTS users (
      uid TEXT PRIMARY KEY,
      name TEXT NOT NULL,
      login_identifier TEXT UNIQUE NOT NULL,
      password_hash TEXT NOT NULL,
      password_salt TEXT NOT NULL,
      role TEXT DEFAULT 'student',
      created_at TEXT NOT NULL,
      trial_used INTEGER DEFAULT 0,
      trial_started_at TEXT,
      trial_expires_at TEXT,
      access_type TEXT DEFAULT 'NONE',
      access_status TEXT DEFAULT 'INACTIVE',
      annual_activated_at TEXT,
      annual_expires_at TEXT,
      activation_code_id INTEGER
    );

    CREATE TABLE IF NOT EXISTS activation_codes (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      code_hash TEXT UNIQUE NOT NULL,
      type TEXT DEFAULT 'ANNUAL_365',
      status TEXT DEFAULT 'UNUSED',
      created_at TEXT NOT NULL,
      activated_at TEXT,
      expires_at TEXT,
      assigned_user_id TEXT
    );

    CREATE TABLE IF NOT EXISTS access_audit_logs (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id TEXT NOT NULL,
      event_type TEXT NOT NULL,
      timestamp TEXT NOT NULL,
      metadata TEXT
    );

    CREATE TABLE IF NOT EXISTS sessions (
      token TEXT PRIMARY KEY,
      user_id TEXT NOT NULL,
      created_at TEXT NOT NULL,
      expires_at TEXT NOT NULL
    );
  `);

  // 3. Populate 500 activation codes if empty
  const countRow = db.prepare('SELECT COUNT(*) as count FROM activation_codes').get();
  if (countRow.count === 0) {
    console.log('Seeding 500 activation codes into database...');
    if (!fs.existsSync(CSV_PATH)) {
      throw new Error(`CSV file not found at ${CSV_PATH}`);
    }
    const lines = fs.readFileSync(CSV_PATH, 'utf-8').trim().split('\n').slice(1);
    const insertCode = db.prepare(`
      INSERT INTO activation_codes (code_hash, type, status, created_at)
      VALUES (?, 'ANNUAL_365', 'UNUSED', ?)
    `);

    const nowIso = new Date().toISOString();
    db.exec('BEGIN IMMEDIATE');
    for (const line of lines) {
      const parts = line.split(',');
      if (parts.length >= 2) {
        const rawCode = parts[1].trim().toUpperCase();
        const codeHash = crypto.createHash('sha256').update(rawCode).digest('hex');
        insertCode.run(codeHash, nowIso);
      }
    }
    db.exec('COMMIT');
    console.log(`Successfully seeded 500 codes into database.`);
  }

  // 4. Create default administrator account if not present
  const adminRow = db.prepare("SELECT * FROM users WHERE role = 'admin'").get();
  if (!adminRow) {
    const adminUid = 'admin_' + crypto.randomBytes(8).toString('hex');
    const salt = crypto.randomBytes(16).toString('hex');
    const hash = crypto.pbkdf2Sync('Admin@Bienvenu2026!', salt, 10000, 32, 'sha256').toString('hex');
    db.prepare(`
      INSERT INTO users (uid, name, login_identifier, password_hash, password_salt, role, created_at, access_type, access_status)
      VALUES (?, 'مدير المنصة', 'admin@bienvenu2.fr', ?, ?, 'admin', ?, 'ANNUAL', 'ACTIVE')
    `).run(adminUid, hash, salt, new Date().toISOString());
    console.log('Initialized default admin account: admin@bienvenu2.fr');
  }

  return db;
}

if (process.argv[1] && process.argv[1].endsWith('init_db.js')) {
  initDatabase();
  console.log('Database initialized successfully.');
}
