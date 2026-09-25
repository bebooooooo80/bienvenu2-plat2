# -*- coding: utf-8 -*-

def get_exam_renderer_code():
    return """
    // 7. RENDER OFFICIAL MID-YEAR EXAM SCREEN (20 Marks)
    function renderExamScreen() {
      const ex = DATA.midExam;
      const container = document.getElementById('screen-exam');

      container.innerHTML = `
        <!-- Exam Banner & Timer -->
        <div class="rounded-3xl bg-gradient-to-r from-rose-700 via-pink-700 to-rose-800 text-white p-5 sm:p-6 shadow-lg border border-rose-500/30">
          <div class="flex flex-wrap items-center justify-between gap-3 mb-2">
            <div class="flex items-center gap-2">
              <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold font-french">Examen Officiel Mi-Année • p. 78-79</span>
              <span class="px-2.5 py-0.5 rounded-full bg-amber-400 text-slate-900 text-xs font-black">20 درجة</span>
            </div>
            <div class="flex items-center gap-2 bg-black/20 px-3 py-1.5 rounded-xl border border-white/20 text-xs font-mono font-bold">
              <i class="fa-solid fa-stopwatch text-amber-300"></i>
              <span id="examTimerDisplay">60:00</span>
            </div>
          </div>
          <h2 class="text-xl sm:text-2xl font-black mb-1">امتحان نصف العام الدراسي الرسمي (20 درجة)</h2>
          <p class="text-xs sm:text-sm text-rose-100 font-medium">الورقة الامتحانية الرسمية الشاملة المطابقة لمواصفات وزارة التربية والتعليم لكتاب Bienvenu 2.</p>
        </div>

        <!-- Exam Paper Form -->
        <div class="bg-white rounded-3xl p-5 sm:p-7 border border-slate-200 shadow-sm space-y-6">
          
          <!-- SECTION 1: COMPRÉHENSION (6 Marks) -->
          <div class="border-b border-slate-100 pb-5 space-y-3">
            <div class="flex items-center justify-between">
              <span class="text-xs font-black px-2.5 py-1 rounded bg-blue-100 text-blue-800">I. Compréhension de l'écrit (6 Marks)</span>
              <span class="text-xs text-slate-500 font-french font-semibold">وثيقة الفهم الرسمية</span>
            </div>

            <div class="p-4 rounded-2xl bg-slate-50 border border-slate-200 text-xs sm:text-sm font-french leading-relaxed text-slate-800 space-y-2" dir="ltr">
              <p class="font-bold text-indigo-900">${ex.documentTitleFr} (${ex.documentTitleAr})</p>
              <p>${ex.documentFullTextFr}</p>
              <div class="space-y-1 pt-2 border-t border-slate-200 text-xs">
                ${ex.documentLines.map(l => `
                  <p><strong class="text-indigo-700">${l.speaker} :</strong> ${l.textFr} <span class="text-slate-400 font-sans" dir="rtl">(${l.textAr})</span></p>
                `).join('')}
              </div>
            </div>

            <!-- Part A: MCQ -->
            <div class="space-y-2.5 pt-2">
              <h4 class="text-xs font-bold text-slate-800">A) Choisis la bonne réponse (3 أسئلة) :</h4>
              <div class="space-y-2.5">
                ${ex.docPartAQuestions.map(q => `
                  <div class="quiz-card p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${q.questionFr}</p>
                    <p class="text-slate-500">${q.questionAr}</p>
                    <div class="grid grid-cols-1 sm:grid-cols-3 gap-1.5" dir="ltr">
                      ${q.options.map((opt, oIdx) => `
                        <button onclick="checkQuizOption(this, ${oIdx === q.correctIndex}, '${(q.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:border-blue-300 transition">
                          ${opt}
                        </button>
                      `).join('')}
                    </div>
                    <div class="quiz-feedback hidden text-xs font-bold mt-1"></div>
                  </div>
                `).join('')}
              </div>
            </div>

            <!-- Part B: True/False -->
            <div class="space-y-2.5 pt-2">
              <h4 class="text-xs font-bold text-slate-800">B) Mets (Vrai) ou (Faux) (3 أسئلة) :</h4>
              <div class="space-y-2.5">
                ${ex.docPartBQuestions.map(q => `
                  <div class="quiz-card p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${q.statementFr}</p>
                    <p class="text-slate-500">${q.statementAr}</p>
                    <div class="grid grid-cols-2 gap-2" dir="ltr">
                      <button onclick="checkQuizOption(this, ${q.isTrue === true}, '${(q.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-bold text-slate-800 text-center hover:border-emerald-300 transition">
                        Vrai (صح)
                      </button>
                      <button onclick="checkQuizOption(this, ${q.isTrue === false}, '${(q.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-bold text-slate-800 text-center hover:border-rose-300 transition">
                        Faux (خطأ)
                      </button>
                    </div>
                    <div class="quiz-feedback hidden text-xs font-bold mt-1"></div>
                  </div>
                `).join('')}
              </div>
            </div>

            <!-- Part C: Complète -->
            <div class="space-y-2 pt-2">
              <h4 class="text-xs font-bold text-slate-800">C) Complète les phrases (سؤالان) :</h4>
              <div class="space-y-2">
                ${ex.docPartCQuestions.map(q => `
                  <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${q.sentenceTemplateFr}</p>
                    <p class="text-slate-500">${q.sentenceAr}</p>
                    <p class="font-bold text-emerald-700 font-french" dir="ltr">➔ ${q.fullSentenceFr}</p>
                  </div>
                `).join('')}
              </div>
            </div>
          </div>

          <!-- SECTION 2: SITUATIONS (4 Marks) -->
          <div class="border-b border-slate-100 pb-5 space-y-3">
            <div class="flex items-center justify-between">
              <span class="text-xs font-black px-2.5 py-1 rounded bg-amber-100 text-amber-800">II. Situations de la vie quotidienne (4 Marks)</span>
              <span class="text-xs text-slate-500 font-french font-semibold">مواقف الحياة اليومية</span>
            </div>

            <div class="space-y-3">
              ${ex.situationsQuestions.map(s => `
                <div class="quiz-card p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                  <p class="font-bold text-slate-900 font-french" dir="ltr">${s.situationFr}</p>
                  <p class="text-slate-500">${s.situationAr}</p>
                  <div class="space-y-1.5" dir="ltr">
                    ${s.options.map((opt, oIdx) => `
                      <button onclick="checkQuizOption(this, ${oIdx === s.correctIndex}, '${(s.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn w-full p-2.5 rounded-lg border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:border-amber-300 transition">
                        ${opt}
                      </button>
                    `).join('')}
                  </div>
                  <div class="quiz-feedback hidden text-xs font-bold mt-1"></div>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- SECTION 3: GRAMMAIRE (6 Marks) -->
          <div class="border-b border-slate-100 pb-5 space-y-3">
            <div class="flex items-center justify-between">
              <span class="text-xs font-black px-2.5 py-1 rounded bg-violet-100 text-violet-800">III. Grammaire (6 Marks)</span>
              <span class="text-xs text-slate-500 font-french font-semibold">القواعد النحوية</span>
            </div>

            <!-- Part A: Conjugate -->
            <div class="space-y-2">
              <h4 class="text-xs font-bold text-slate-800">A) Conjugue les verbes entre parenthèses (تصريف الأفعال) :</h4>
              <div class="space-y-2">
                ${ex.gramPartAQuestions.map(q => `
                  <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${q.promptFr}</p>
                    <p class="font-bold text-indigo-700 font-french" dir="ltr">➔ Réponse : ${q.correctAnswer}</p>
                    <p class="text-slate-500 text-[11px]">${q.ruleExplanationAr}</p>
                  </div>
                `).join('')}
              </div>
            </div>

            <!-- Part B: Transform & MCQ -->
            <div class="space-y-2 pt-2">
              <h4 class="text-xs font-bold text-slate-800">B) Fais comme indiqué entre parenthèses (5 أسئلة) :</h4>
              <div class="space-y-2.5">
                ${ex.gramPartBQuestions.map(item => `
                  <div class="quiz-card p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${item.questionFr || item.originalFr}</p>
                    ${item.instructionAr ? `<p class="text-indigo-600 font-semibold">[ ${item.instructionAr} ]</p>` : ''}
                    
                    ${item.options && item.options.length ? `
                      <div class="grid grid-cols-1 sm:grid-cols-3 gap-1.5" dir="ltr">
                        ${item.options.map((opt, oIdx) => `
                          <button onclick="checkQuizOption(this, ${item.type === 'mcq' ? oIdx === item.correctIndex : opt === item.expectedAnswerFr}, '${(item.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:border-violet-300 transition">
                            ${opt}
                          </button>
                        `).join('')}
                      </div>
                      <div class="quiz-feedback hidden text-xs font-bold mt-1"></div>
                    ` : ''}

                    ${item.expectedAnswerFr ? `
                      <p class="font-bold text-teal-800 font-french pt-1" dir="ltr">➔ ${item.expectedAnswerFr}</p>
                    ` : ''}
                  </div>
                `).join('')}
              </div>
            </div>

            <!-- Part C: Pronouns -->
            <div class="space-y-2 pt-2">
              <h4 class="text-xs font-bold text-slate-800">C) Remplace par un pronom personnel (سؤالان) :</h4>
              <div class="space-y-2">
                ${ex.gramPartCQuestions.map(q => `
                  <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${q.originalFr}</p>
                    <p class="text-indigo-600 font-semibold">[ ${q.instructionAr} ]</p>
                    <p class="font-bold text-teal-800 font-french" dir="ltr">➔ ${q.expectedAnswerFr}</p>
                    <p class="text-slate-500 text-[11px]">${q.explanationAr}</p>
                  </div>
                `).join('')}
              </div>
            </div>
          </div>

          <!-- SECTION 4: PRODUCTION (4 Marks) -->
          <div class="space-y-3">
            <div class="flex items-center justify-between">
              <span class="text-xs font-black px-2.5 py-1 rounded bg-emerald-100 text-emerald-800">IV. Production (4 Marks)</span>
              <span class="text-xs text-slate-500 font-french font-semibold">تكوين الجمل</span>
            </div>

            <div class="space-y-2">
              ${ex.productionQuestions.map(q => `
                <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-slate-900 font-french" dir="ltr">Pour ${q.purposeFr} :</span>
                    <span class="text-slate-500">${q.purposeAr}</span>
                  </div>
                  <p class="font-bold text-emerald-700 font-french" dir="ltr">➔ ${q.modelSentenceFr}</p>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Final Grade Button -->
          <div class="pt-4 text-center border-t border-slate-100">
            <button onclick="finishOfficialExam()" class="py-3.5 px-8 rounded-2xl bg-rose-600 hover:bg-rose-700 active:scale-95 text-white font-black text-sm shadow-xl shadow-rose-600/30 transition flex items-center justify-center gap-2 mx-auto">
              <i class="fa-solid fa-award"></i>
              <span>اعتماد النتيجة والشهادة التقديرية</span>
            </button>
          </div>

          <!-- Result Modal -->
          <div id="officialExamResultModal" class="hidden p-6 rounded-3xl bg-gradient-to-br from-indigo-50 to-rose-50 border-2 border-indigo-200 text-center space-y-3">
            <div class="w-16 h-16 rounded-full bg-white shadow-md mx-auto flex items-center justify-center text-3xl text-amber-500">
              <i class="fa-solid fa-trophy"></i>
            </div>
            <h3 class="text-xl font-black text-slate-900">شهادة نتيجة امتحان نصف العام الرسمي</h3>
            <div class="text-3xl font-black font-french text-indigo-700">
              20 / 20
            </div>
            <p class="text-sm font-bold text-slate-700">🌟 ممتاز جداً (Très Bien) - تهانينا لك يا بطل على إتمام ومراجعة كافة أسئلة الامتحان بنجاح باهر!</p>
          </div>

        </div>
      `;

      startExamTimer();
    }

    function startExamTimer() {
      if (examTimerInterval) clearInterval(examTimerInterval);
      examTimeRemaining = 3600;
      examTimerInterval = setInterval(() => {
        if (examTimeRemaining <= 0) {
          clearInterval(examTimerInterval);
          return;
        }
        examTimeRemaining--;
        const mins = Math.floor(examTimeRemaining / 60);
        const secs = examTimeRemaining % 60;
        const display = document.getElementById('examTimerDisplay');
        if (display) {
          display.innerText = (mins < 10 ? '0' : '') + mins + ':' + (secs < 10 ? '0' : '') + secs;
        }
      }, 1000);
    }

    function finishOfficialExam() {
      playSuccessSound();
      if (typeof confetti === 'function') confetti({ particleCount: 150, spread: 80 });
      const modal = document.getElementById('officialExamResultModal');
      if (modal) {
        modal.classList.remove('hidden');
        modal.scrollIntoView({ behavior: 'smooth' });
      }
    }
"""
