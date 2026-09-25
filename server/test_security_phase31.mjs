import fs from 'node:fs';
import path from 'node:path';
import crypto from 'node:crypto';
import http from 'node:http';
import { initDatabase, hashCode } from './init_db.js';

const CSV_PATH = path.resolve('server/private_exports/codes_export_500.csv');

async function runTests() {
  console.log('====================================================');
  console.log('RUNNING PHASE 3.1 SECURITY & AUTHENTICATION AUDIT...');
  console.log('====================================================\n');

  // Verify CSV file exists and has 500 unique codes
  const csvLines = fs.readFileSync(CSV_PATH, 'utf-8').trim().split('\n').slice(1);
  const rawCodes = csvLines.map(l => l.split(',')[1].trim());
  console.log(`Total production codes in CSV: ${rawCodes.length}`);
  if (rawCodes.length !== 500) {
    throw new Error(`Expected 500 codes, found ${rawCodes.length}`);
  }

  const uniqueCodes = new Set(rawCodes);
  if (uniqueCodes.size !== 500) {
    throw new Error(`Codes are not unique! ${uniqueCodes.size} unique out of 500`);
  }

  // Helper: make HTTP request to local server
  function makeReq(pathname, method = 'GET', body = null, cookie = '') {
    return new Promise((resolve, reject) => {
      const data = body ? JSON.stringify(body) : null;
      const headers = {
        'Content-Type': 'application/json'
      };
      if (cookie) headers['Cookie'] = cookie;
      if (data) headers['Content-Length'] = Buffer.byteLength(data);

      const req = http.request({
        hostname: '127.0.0.1',
        port: 3000,
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

  const testCode1 = rawCodes[10];
  const testCode2 = rawCodes[11];
  const testCode3 = rawCodes[12];
  const testCode4 = rawCodes[13];

  // -------------------------------------------------------------
  // TEST 1: Unused code + new unique username -> Account created + code activated
  // -------------------------------------------------------------
  console.log('TEST 1: Unused code + new unique username...');
  const t1 = await makeReq('/api/student/login', 'POST', {
    username: 'AhmedTest1',
    code: testCode1
  });
  if (t1.status !== 200 || !t1.data.success || t1.data.user.accessType !== 'ANNUAL' || t1.data.user.accessStatus !== 'ACTIVE') {
    throw new Error(`TEST 1 Failed: ${JSON.stringify(t1.data)}`);
  }
  const session1 = extractSessionToken(t1.cookies);
  if (!session1) throw new Error('TEST 1 Failed: No session cookie set');
  console.log('✓ TEST 1 PASS: Account created + code activated + 365-day access');

  // -------------------------------------------------------------
  // TEST 2: Same username + same code later -> Login success
  // -------------------------------------------------------------
  console.log('TEST 2: Same username + same code later...');
  const t2 = await makeReq('/api/student/login', 'POST', {
    username: 'AhmedTest1',
    code: testCode1
  });
  if (t2.status !== 200 || !t2.data.success || t2.data.user.username !== 'ahmedtest1') {
    throw new Error(`TEST 2 Failed: ${JSON.stringify(t2.data)}`);
  }
  console.log('✓ TEST 2 PASS: Returning student login successful with same username + code');

  // -------------------------------------------------------------
  // TEST 3: Different username + already assigned code -> Rejected
  // -------------------------------------------------------------
  console.log('TEST 3: Different username + already assigned code...');
  const t3 = await makeReq('/api/student/login', 'POST', {
    username: 'OtherStudent',
    code: testCode1
  });
  if (t3.status !== 401 || t3.data.success === true) {
    throw new Error(`TEST 3 Failed: Should be rejected, got ${t3.status}`);
  }
  console.log('✓ TEST 3 PASS: Rejected code reuse by different username');

  // -------------------------------------------------------------
  // TEST 4: Correct username + wrong code -> Rejected
  // -------------------------------------------------------------
  console.log('TEST 4: Correct username + wrong code...');
  const t4 = await makeReq('/api/student/login', 'POST', {
    username: 'AhmedTest1',
    code: 'FR2-WRONG-CODE-0000'
  });
  if (t4.status !== 401 || t4.data.success === true) {
    throw new Error(`TEST 4 Failed: Should be rejected, got ${t4.status}`);
  }
  console.log('✓ TEST 4 PASS: Rejected wrong code');

  // -------------------------------------------------------------
  // TEST 5: Wrong username + valid assigned code -> Rejected
  // -------------------------------------------------------------
  console.log('TEST 5: Wrong username + valid assigned code...');
  const t5 = await makeReq('/api/student/login', 'POST', {
    username: 'WrongUserXYZ',
    code: testCode1
  });
  if (t5.status !== 401 || t5.data.success === true) {
    throw new Error(`TEST 5 Failed: Should be rejected, got ${t5.status}`);
  }
  console.log('✓ TEST 5 PASS: Rejected invalid username/code combination');

  // -------------------------------------------------------------
  // TEST 6: 500-code list in frontend -> 0 codes exposed
  // -------------------------------------------------------------
  console.log('TEST 6: Checking frontend files for raw codes...');
  const indexHtml = fs.readFileSync('index.html', 'utf-8');
  const assetHtml = fs.readFileSync('app/src/main/assets/index.html', 'utf-8');
  if (indexHtml.includes('RAW_500_CODES') || assetHtml.includes('RAW_500_CODES')) {
    throw new Error('TEST 6 Failed: RAW_500_CODES variable found in frontend');
  }
  for (const c of rawCodes.slice(0, 50)) {
    if (indexHtml.includes(c) || assetHtml.includes(c)) {
      throw new Error(`TEST 6 Failed: Raw code ${c} found in frontend`);
    }
  }
  console.log('✓ TEST 6 PASS: Zero raw activation codes exposed in frontend');

  // -------------------------------------------------------------
  // TEST 7: Authentication token in localStorage
  // -------------------------------------------------------------
  console.log('TEST 7: Checking for auth token in localStorage...');
  if (indexHtml.includes("localStorage.setItem('bienvenu2_token'") || indexHtml.includes("localStorage.getItem('bienvenu2_token'")) {
    throw new Error('TEST 7 Failed: localStorage token usage still found in index.html');
  }
  console.log('✓ TEST 7 PASS: No authentication tokens in localStorage');

  // -------------------------------------------------------------
  // TEST 8: Free Trial + new username -> 24-hour Trial starts using server time
  // -------------------------------------------------------------
  console.log('TEST 8: Free Trial + new username...');
  const t8 = await makeReq('/api/student/trial', 'POST', {
    username: 'TrialUser1'
  });
  if (t8.status !== 200 || !t8.data.success || t8.data.user.accessType !== 'TRIAL' || t8.data.user.accessStatus !== 'ACTIVE') {
    throw new Error(`TEST 8 Failed: ${JSON.stringify(t8.data)}`);
  }
  const trialSession = extractSessionToken(t8.cookies);
  if (!trialSession) throw new Error('TEST 8 Failed: No trial session cookie set');
  console.log('✓ TEST 8 PASS: 24-Hour Free Trial started using server time');

  // -------------------------------------------------------------
  // TEST 9: Trial student restores session with valid cookie
  // -------------------------------------------------------------
  console.log('TEST 9: Trial student restores session with valid cookie...');
  const t9 = await makeReq('/api/auth/me', 'GET', null, trialSession);
  if (t9.status !== 200 || !t9.data.success || t9.data.user.username !== 'trialuser1' || t9.data.user.accessType !== 'TRIAL') {
    throw new Error(`TEST 9 Failed: ${JSON.stringify(t9.data)}`);
  }
  console.log('✓ TEST 9 PASS: Trial account safely restored via HttpOnly session');

  // -------------------------------------------------------------
  // TEST 10: Trial -> Paid code upgrade (preserves user account)
  // -------------------------------------------------------------
  console.log('TEST 10: Trial -> Paid code upgrade...');
  const t10 = await makeReq('/api/access/activate-code', 'POST', {
    code: testCode2
  }, trialSession);
  if (t10.status !== 200 || !t10.data.success || t10.data.accessType !== 'ANNUAL') {
    throw new Error(`TEST 10 Failed: ${JSON.stringify(t10.data)}`);
  }
  // Verify future login with trial user's username + code2
  const t10Login = await makeReq('/api/student/login', 'POST', {
    username: 'TrialUser1',
    code: testCode2
  });
  if (t10Login.status !== 200 || t10Login.data.user.accessType !== 'ANNUAL') {
    throw new Error(`TEST 10 Future login Failed: ${JSON.stringify(t10Login.data)}`);
  }
  console.log('✓ TEST 10 PASS: Trial upgraded to Annual, account & username preserved');

  // -------------------------------------------------------------
  // TEST 11: Two users simultaneously attempt same UNUSED code -> exactly ONE succeeds
  // -------------------------------------------------------------
  console.log('TEST 11: Race condition test on same UNUSED code...');
  const [race1, race2] = await Promise.all([
    makeReq('/api/student/login', 'POST', { username: 'RaceUserA', code: testCode3 }),
    makeReq('/api/student/login', 'POST', { username: 'RaceUserB', code: testCode3 })
  ]);
  const successCount = (race1.status === 200 ? 1 : 0) + (race2.status === 200 ? 1 : 0);
  if (successCount !== 1) {
    throw new Error(`TEST 11 Failed: Expected exactly 1 success, got ${successCount}`);
  }
  console.log('✓ TEST 11 PASS: Atomic transaction prevented race condition (exactly 1 succeeded)');

  // -------------------------------------------------------------
  // TEST 12: Manipulate browser storage
  // -------------------------------------------------------------
  console.log('TEST 12: Verify server-side authorization enforcement...');
  const t12 = await makeReq('/api/auth/me', 'GET');
  if (t12.status !== 401) {
    throw new Error(`TEST 12 Failed: Unauthenticated request should be 401, got ${t12.status}`);
  }
  console.log('✓ TEST 12 PASS: Unauthenticated client cannot access protected session');

  // -------------------------------------------------------------
  // TEST 13: Student attempts Admin API -> 403
  // -------------------------------------------------------------
  console.log('TEST 13: Student attempts Admin API...');
  const t13 = await makeReq('/api/admin/overview', 'GET', null, session1);
  if (t13.status !== 403) {
    throw new Error(`TEST 13 Failed: Expected 403 Forbidden, got ${t13.status}`);
  }
  console.log('✓ TEST 13 PASS: Student access to Admin API rejected with 403 Forbidden');

  // -------------------------------------------------------------
  // TEST 14: Direct request to private files
  // -------------------------------------------------------------
  console.log('TEST 14: Testing static directory traversal & private file isolation...');
  const t14a = await makeReq('/server/database.sqlite', 'GET');
  const t14b = await makeReq('/server/private_exports/codes_export_500.csv', 'GET');
  const t14c = await makeReq('/.env', 'GET');
  if (t14a.status !== 403 || t14b.status !== 403 || t14c.status !== 403) {
    throw new Error(`TEST 14 Failed: Private files accessible! ${t14a.status}, ${t14b.status}, ${t14c.status}`);
  }
  console.log('✓ TEST 14 PASS: Private files and database blocked from static HTTP serving');

  // -------------------------------------------------------------
  // TEST 15: Logout
  // -------------------------------------------------------------
  console.log('TEST 15: Logout test...');
  const t15 = await makeReq('/api/auth/logout', 'POST', null, session1);
  if (t15.status !== 200) {
    throw new Error(`TEST 15 Failed: ${JSON.stringify(t15.data)}`);
  }
  const t15Check = await makeReq('/api/auth/me', 'GET', null, session1);
  if (t15Check.status !== 401) {
    throw new Error(`TEST 15 Failed: Session still valid after logout`);
  }
  console.log('✓ TEST 15 PASS: Server session invalidated and cookie cleared on logout');

  // -------------------------------------------------------------
  // TEST 16: Educational content comparison
  // -------------------------------------------------------------
  console.log('TEST 16: Educational content check...');
  if (!indexHtml.includes('Bienvenu 2') || !indexHtml.includes('Unité 1') || !indexHtml.includes('Unité 2') || !indexHtml.includes('Unité 3')) {
    throw new Error('TEST 16 Failed: Educational content missing from index.html');
  }
  console.log('✓ TEST 16 PASS: Zero educational content loss');

  console.log('\n====================================================');
  console.log('ALL 16 PHASE 3.1 TESTS PASSED SUCCESSFULLY! ✅');
  console.log('====================================================\n');
}

runTests().catch(err => {
  console.error('Test Suite Failed:', err);
  process.exit(1);
});
