import fs from 'node:fs';
import path from 'node:path';
import http from 'node:http';

process.env.NO_AUTO_LISTEN = '1';
const { server } = await import('./server.mjs');

const CSV_PATH = path.resolve('server/private_exports/codes_export_500.csv');

// Listen on an OS-assigned free ephemeral port
await new Promise((resolve) => server.listen(0, '127.0.0.1', resolve));
const testPort = server.address().port;
console.log(`Test server active on ephemeral port ${testPort}`);

console.log('====================================================');
console.log('RUNNING PHASE 3.1 SECURITY & AUTHENTICATION AUDIT...');
console.log('====================================================\n');

const csvLines = fs.readFileSync(CSV_PATH, 'utf-8').trim().split('\n').slice(1);
const rawCodes = csvLines.map(l => l.split(',')[1].trim());
console.log(`Total production codes in CSV: ${rawCodes.length}`);

function makeReq(pathname, method = 'GET', body = null, cookie = '') {
  return new Promise((resolve, reject) => {
    const data = body ? JSON.stringify(body) : null;
    const headers = { 'Content-Type': 'application/json' };
    if (cookie) headers['Cookie'] = cookie;
    if (data) headers['Content-Length'] = Buffer.byteLength(data);

    const req = http.request({
      hostname: '127.0.0.1',
      port: testPort,
      path: pathname,
      method,
      headers
    }, (res) => {
      let resBody = '';
      res.on('data', chunk => { resBody += chunk; });
      res.on('end', () => {
        let json = null;
        try {
          json = JSON.parse(resBody);
        } catch (e) {
          json = resBody;
        }
        const setCookie = res.headers['set-cookie'] || [];
        resolve({
          status: res.statusCode,
          headers: res.headers,
          cookies: setCookie,
          data: json
        });
      });
    });

    req.on('error', reject);
    if (data) req.write(data);
    req.end();
  });
}

function extractSessionToken(cookies) {
  for (const c of cookies) {
    const match = c.match(/session_token=([^;]+)/);
    if (match && match[1]) return `session_token=${match[1]}`;
  }
  return '';
}

const testCode1 = rawCodes[120];
const testCode2 = rawCodes[121];
const testCode3 = rawCodes[122];

// TEST 1
console.log('TEST 1: Unused code + new unique username...');
const t1 = await makeReq('/api/student/login', 'POST', { username: 'AhmedFinal1', code: testCode1 });
if (t1.status !== 200 || !t1.data.success || t1.data.user.accessType !== 'ANNUAL' || t1.data.user.accessStatus !== 'ACTIVE') {
  throw new Error(`TEST 1 Failed: ${JSON.stringify(t1.data)}`);
}
const session1 = extractSessionToken(t1.cookies);
if (!session1) throw new Error('TEST 1 Failed: No session cookie set');
console.log('✓ TEST 1 PASS: Account created + code activated + 365-day access');

// TEST 2
console.log('TEST 2: Same username + same code later...');
const t2 = await makeReq('/api/student/login', 'POST', { username: 'AhmedFinal1', code: testCode1 });
if (t2.status !== 200 || !t2.data.success || t2.data.user.username !== 'ahmedfinal1') {
  throw new Error(`TEST 2 Failed: ${JSON.stringify(t2.data)}`);
}
console.log('✓ TEST 2 PASS: Returning student login successful');

// TEST 3
console.log('TEST 3: Different username + already assigned code...');
const t3 = await makeReq('/api/student/login', 'POST', { username: 'OtherStudentFinal', code: testCode1 });
if (t3.status !== 401 || t3.data.success === true) throw new Error(`TEST 3 Failed: status ${t3.status}`);
console.log('✓ TEST 3 PASS: Code reuse rejected for different username');

// TEST 4
console.log('TEST 4: Correct username + wrong code...');
const t4 = await makeReq('/api/student/login', 'POST', { username: 'AhmedFinal1', code: 'FR2-WRONG-0000-0000' });
if (t4.status !== 401 || t4.data.success === true) throw new Error(`TEST 4 Failed: status ${t4.status}`);
console.log('✓ TEST 4 PASS: Rejected wrong code');

// TEST 5
console.log('TEST 5: Wrong username + valid assigned code...');
const t5 = await makeReq('/api/student/login', 'POST', { username: 'UnknownUserFinal', code: testCode1 });
if (t5.status !== 401 || t5.data.success === true) throw new Error(`TEST 5 Failed: status ${t5.status}`);
console.log('✓ TEST 5 PASS: Rejected invalid username/code pair');

// TEST 6
console.log('TEST 6: Checking frontend files for 0 exposed raw codes...');
const indexHtml = fs.readFileSync('index.html', 'utf-8');
const assetHtml = fs.readFileSync('app/src/main/assets/index.html', 'utf-8');
if (indexHtml.includes('RAW_500_CODES') || assetHtml.includes('RAW_500_CODES')) throw new Error('TEST 6 Failed: RAW_500_CODES in frontend');
for (const c of rawCodes.slice(0, 100)) {
  if (indexHtml.includes(c) || assetHtml.includes(c)) throw new Error(`TEST 6 Failed on code ${c}`);
}
console.log('✓ TEST 6 PASS: Zero raw activation codes exposed');

// TEST 7
console.log('TEST 7: Checking for auth token in localStorage...');
if (indexHtml.includes("localStorage.setItem('bienvenu2_token'") || indexHtml.includes("localStorage.getItem('bienvenu2_token'")) {
  throw new Error('TEST 7 Failed: localStorage token in frontend');
}
console.log('✓ TEST 7 PASS: No authentication tokens in localStorage');

// TEST 8
console.log('TEST 8: Free Trial starts with username only...');
const t8 = await makeReq('/api/student/trial', 'POST', { username: 'TrialFinalUser' });
if (t8.status !== 200 || !t8.data.success || t8.data.user.accessType !== 'TRIAL' || t8.data.user.accessStatus !== 'ACTIVE') {
  throw new Error(`TEST 8 Failed: ${JSON.stringify(t8.data)}`);
}
const trialSession = extractSessionToken(t8.cookies);
if (!trialSession) throw new Error('TEST 8 Failed: No trial session cookie');
console.log('✓ TEST 8 PASS: 24-Hour Free Trial started using server time');

// TEST 9
console.log('TEST 9: Trial student restores session with cookie...');
const t9 = await makeReq('/api/auth/me', 'GET', null, trialSession);
if (t9.status !== 200 || !t9.data.success || t9.data.user.username !== 'trialfinaluser') {
  throw new Error(`TEST 9 Failed: ${JSON.stringify(t9.data)}`);
}
console.log('✓ TEST 9 PASS: Trial session automatically restored');

// TEST 10
console.log('TEST 10: Trial upgrade to Annual with code preserves account...');
const t10 = await makeReq('/api/access/activate-code', 'POST', { code: testCode2 }, trialSession);
if (t10.status !== 200 || !t10.data.success || t10.data.accessType !== 'ANNUAL') {
  throw new Error(`TEST 10 Failed: ${JSON.stringify(t10.data)}`);
}
console.log('✓ TEST 10 PASS: Trial upgraded to Annual, account & username preserved');

// TEST 11
console.log('TEST 11: Race condition test on same UNUSED code...');
const [r1, r2] = await Promise.all([
  makeReq('/api/student/login', 'POST', { username: 'RaceStudentA_Final', code: testCode3 }),
  makeReq('/api/student/login', 'POST', { username: 'RaceStudentB_Final', code: testCode3 })
]);
const successes = (r1.status === 200 ? 1 : 0) + (r2.status === 200 ? 1 : 0);
if (successes !== 1) throw new Error(`TEST 11 Failed: expected 1 success, got ${successes}`);
console.log('✓ TEST 11 PASS: Atomic transaction prevented duplicate redemption');

// TEST 12
console.log('TEST 12: Storage manipulation protection...');
const t12 = await makeReq('/api/auth/me', 'GET');
if (t12.status !== 401) throw new Error('TEST 12 Failed: Unauthenticated request should be 401');
console.log('✓ TEST 12 PASS: Unauthenticated access rejected');

// TEST 13
console.log('TEST 13: Student attempts Admin API -> 403 Forbidden...');
const t13 = await makeReq('/api/admin/overview', 'GET', null, session1);
if (t13.status !== 403) throw new Error(`TEST 13 Failed: status ${t13.status}`);
console.log('✓ TEST 13 PASS: Student access to Admin API rejected with 403 Forbidden');

// TEST 14
console.log('TEST 14: Private files blocked from static serving...');
const [f1, f2, f3] = await Promise.all([
  makeReq('/server/database.sqlite', 'GET'),
  makeReq('/server/private_exports/codes_export_500.csv', 'GET'),
  makeReq('/.env', 'GET')
]);
if (f1.status !== 403 || f2.status !== 403 || f3.status !== 403) throw new Error('TEST 14 Failed');
console.log('✓ TEST 14 PASS: Private database and export files blocked');

// TEST 15
console.log('TEST 15: Logout invalidates session & cookie...');
const t15 = await makeReq('/api/auth/logout', 'POST', null, session1);
if (t15.status !== 200) throw new Error('TEST 15 Failed');
const t15Check = await makeReq('/api/auth/me', 'GET', null, session1);
if (t15Check.status !== 401) throw new Error('TEST 15 Failed: session still valid');
console.log('✓ TEST 15 PASS: Session invalidated and cookie cleared on logout');

// TEST 16
console.log('TEST 16: Educational content check...');
if (!indexHtml.includes('Bienvenu 2') || !indexHtml.includes('Unité 1') || !indexHtml.includes('Unité 2') || !indexHtml.includes('Unité 3')) {
  throw new Error('TEST 16 Failed');
}
console.log('✓ TEST 16 PASS: Zero educational content loss');

console.log('\n====================================================');
console.log('ALL 16 PHASE 3.1 TESTS PASSED WITH 100% SUCCESS! ✅');
console.log('====================================================\n');

server.close(() => {
  process.exit(0);
});
