# -*- coding: utf-8 -*-

def get_revision_grammar_renderer_code():
    return """
    // 5. RENDER REVISION SCREEN
    function renderRevisionScreen() {
      const docs = DATA.revisionDocs;
      const gramExs = DATA.revisionGram;
      const container = document.getElementById('screen-revision');

      container.innerHTML = `
        <!-- Revision Header -->
        <div class="rounded-3xl bg-gradient-to-r from-violet-700 via-indigo-700 to-purple-800 text-white p-5 sm:p-6 shadow-lg border border-purple-500/30">
          <div class="flex items-center justify-between gap-3 mb-2">
            <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold font-french">Révision Générale • Pages 67-77</span>
            <button onclick="speakText('Révision générale, textes et exercices')" class="px-3 py-1 rounded-full bg-white/15 hover:bg-white/25 active:scale-95 text-xs font-bold font-french flex items-center gap-1.5 transition">
              <i class="fa-solid fa-volume-high"></i>
              <span>Écouter</span>
            </button>
          </div>
          <h2 class="text-xl sm:text-2xl font-black mb-1">قسم المراجعة العامة بكراسة الأنشطة الرسمية</h2>
          <p class="text-xs sm:text-sm text-purple-100 font-medium">نصوص الفهم الأربعة الرسمية (ص 67-71) وتمارين القواعد الشاملة السبعة (ص 72-77).</p>
        </div>

        <!-- Sub-tabs -->
        <div class="bg-white p-1.5 rounded-2xl border border-slate-200 shadow-sm flex overflow-x-auto custom-scrollbar gap-1">
          <button onclick="switchRevisionSubTab('textes')" id="rev-tab-btn-textes" class="rev-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition tab-active">
            <i class="fa-solid fa-book-open ml-1"></i> نصوص الفهم الأربعة (ص 67-71)
          </button>
          <button onclick="switchRevisionSubTab('grammaire')" id="rev-tab-btn-grammaire" class="rev-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-ruler-combined ml-1"></i> تمارين القواعد السبعة (ص 72-77)
          </button>
        </div>

        <!-- 1. TEXTES DE COMPRÉHENSION -->
        <div id="rev-pane-textes" class="rev-pane space-y-5">
          ${docs.map(doc => `
            <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
              <div class="flex items-center justify-between border-b border-slate-100 pb-3">
                <div>
                  <span class="px-2.5 py-0.5 rounded-full bg-violet-100 text-violet-800 text-[11px] font-bold font-french">${doc.pageNumber} • ${doc.documentType}</span>
                  <h3 class="text-base font-bold text-slate-900 mt-1 font-french">${doc.titleFr}</h3>
                  <p class="text-xs text-slate-500 font-sans">${doc.titleAr}</p>
                </div>
                <button onclick="speakText('${doc.textFr.replace(/['"\\n]/g, ' ')}')" class="px-3 py-1.5 rounded-xl bg-violet-50 text-violet-700 hover:bg-violet-100 text-xs font-bold font-french flex items-center gap-1.5 shrink-0 transition">
                  <i class="fa-solid fa-volume-high"></i>
                  <span>استمع للوثيقة</span>
                </button>
              </div>

              <!-- Text Content -->
              <div class="bg-slate-50 p-4 rounded-2xl text-xs sm:text-sm font-french leading-relaxed text-slate-800 border border-slate-200/80" dir="ltr">
                ${doc.textFr.replace(/\\n/g, '<br>')}
              </div>
              
              <div class="p-3 bg-violet-50/50 rounded-xl text-xs text-violet-900 border border-violet-100 leading-relaxed">
                <strong>الترجمة العربية للوثيقة :</strong><br>
                ${doc.textAr.replace(/\\n/g, '<br>')}
              </div>

              <!-- Vocabulary from Document -->
              ${doc.vocabulary && doc.vocabulary.length ? `
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200/70">
                  <span class="text-xs font-bold text-slate-700 block mb-1.5"><i class="fa-solid fa-list text-indigo-500 ml-1"></i> مفردات هامة من الوثيقة :</span>
                  <div class="grid grid-cols-2 sm:grid-cols-3 gap-2 text-xs">
                    ${doc.vocabulary.map(v => `
                      <div class="p-1.5 bg-white rounded-lg border border-slate-200/80 flex items-center justify-between">
                        <span class="font-bold text-slate-900 font-french text-[11px]">${v.first}</span>
                        <span class="text-slate-500 text-[11px]">${v.second}</span>
                      </div>
                    `).join('')}
                  </div>
                </div>
              ` : ''}

              <!-- A) Choisis le bon groupe (MCQ) -->
              <div class="space-y-2 pt-1">
                <h4 class="text-xs font-bold text-slate-800">A) Choisis le bon groupe (اختر الإجابة الصحيحة) :</h4>
                <div class="space-y-2.5">
                  ${(doc.mcqQuestions || []).map(q => `
                    <div class="quiz-card p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                      <p class="font-bold text-slate-900 font-french" dir="ltr">${q.questionFr}</p>
                      <p class="text-slate-500">${q.questionAr}</p>
                      <div class="grid grid-cols-1 sm:grid-cols-3 gap-1.5" dir="ltr">
                        ${q.options.map((opt, oIdx) => `
                          <button onclick="checkQuizOption(this, ${oIdx === q.correctIndex}, '${(q.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:border-violet-300 transition">
                            ${opt}
                          </button>
                        `).join('')}
                      </div>
                      <div class="quiz-feedback hidden text-xs font-bold mt-1"></div>
                    </div>
                  `).join('')}
                </div>
              </div>

              <!-- B) Vrai ou Faux -->
              <div class="space-y-2 pt-1">
                <h4 class="text-xs font-bold text-slate-800">B) Mets (Vrai) ou (Faux) (ضع صح أو خطأ) :</h4>
                <div class="space-y-2.5">
                  ${(doc.trueFalseQuestions || []).map(q => `
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

              <!-- C) Complète -->
              ${doc.completionQuestions && doc.completionQuestions.length ? `
                <div class="space-y-2 pt-1">
                  <h4 class="text-xs font-bold text-slate-800">C) Complète d'après le document (أكمل من الوثيقة) :</h4>
                  <div class="space-y-2">
                    ${doc.completionQuestions.map(cQ => `
                      <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                        <p class="font-bold text-slate-900 font-french" dir="ltr">${cQ.promptFr}</p>
                        <p class="text-slate-500">${cQ.promptAr}</p>
                        <p class="font-bold text-emerald-700 font-french" dir="ltr">➔ Réponse : ${cQ.modelAnswerFr}</p>
                        <p class="text-slate-500 text-[11px]">${cQ.modelAnswerAr}</p>
                      </div>
                    `).join('')}
                  </div>
                </div>
              ` : ''}

            </div>
          `).join('')}
        </div>

        <!-- 2. EXERCICES DE GRAMMAIRE (All 7 Exercises) -->
        <div id="rev-pane-grammaire" class="rev-pane hidden space-y-5">
          ${gramExs.map(ex => `
            <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-3">
              <div class="flex items-center justify-between border-b border-slate-100 pb-2.5">
                <div>
                  <span class="px-2.5 py-0.5 rounded-full bg-indigo-100 text-indigo-800 text-[11px] font-bold font-french">${ex.pageNumber} • Exercice ${ex.id}</span>
                  <h3 class="text-sm sm:text-base font-bold text-slate-900 mt-1 font-french">${ex.titleFr} (${ex.titleAr})</h3>
                </div>
              </div>

              <div class="p-3 rounded-xl bg-indigo-50/60 border border-indigo-100 text-xs">
                <p class="font-bold text-indigo-900 font-french" dir="ltr">${ex.instructionFr}</p>
                <p class="text-indigo-800 mt-0.5">${ex.instructionAr}</p>
                ${ex.ruleSummaryAr ? `<p class="text-[11px] text-slate-600 mt-1 font-semibold">💡 ملخص القاعدة: ${ex.ruleSummaryAr}</p>` : ''}
              </div>

              <!-- Exercise Items -->
              <div class="space-y-3 pt-1">
                ${ex.items.map((item, itmIdx) => `
                  <div class="quiz-card p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${itmIdx + 1}. ${item.sentenceFr}</p>
                    <p class="text-slate-500">${item.sentenceAr}</p>

                    ${item.options && item.options.length ? `
                      <div class="grid grid-cols-1 sm:grid-cols-3 gap-1.5 pt-1" dir="ltr">
                        ${item.options.map((opt, oIdx) => `
                          <button onclick="checkQuizOption(this, ${oIdx === item.correctIndex}, '${(item.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:border-indigo-300 transition">
                            ${opt}
                          </button>
                        `).join('')}
                      </div>
                      <div class="quiz-feedback hidden text-xs font-bold mt-1"></div>
                    ` : ''}

                    <div class="pt-1 border-t border-slate-200/60 text-[11px] space-y-0.5">
                      <p class="font-bold text-teal-800 font-french" dir="ltr">➔ ${item.fullTransformedSentenceFr || item.answerFr}</p>
                      ${item.fullTransformedSentenceAr ? `<p class="text-slate-500">${item.fullTransformedSentenceAr}</p>` : ''}
                      <p class="text-indigo-600 font-semibold">💡 ${item.explanationAr}</p>
                    </div>
                  </div>
                `).join('')}
              </div>
            </div>
          `).join('')}
        </div>
      `;
    }

    function switchRevisionSubTab(tabId) {
      playClickSound();
      document.querySelectorAll('.rev-pane').forEach(el => el.classList.add('hidden'));
      document.querySelectorAll('.rev-nav-btn').forEach(el => el.classList.remove('tab-active'));
      const p = document.getElementById('rev-pane-' + tabId);
      const b = document.getElementById('rev-tab-btn-' + tabId);
      if (p) p.classList.remove('hidden');
      if (b) b.classList.add('tab-active');
    }

    // 6. RENDER GRAMMAR SCREEN (Introductory Questions p. 4-6)
    function renderGrammarScreen() {
      const c = DATA.course;
      const container = document.getElementById('screen-grammar');

      container.innerHTML = `
        <!-- Grammar Header -->
        <div class="rounded-3xl bg-gradient-to-r from-indigo-700 via-violet-700 to-indigo-900 text-white p-5 sm:p-6 shadow-lg border border-indigo-500/30">
          <div class="flex items-center justify-between gap-3 mb-2">
            <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold font-french">Atelier Grammaire • Pages 4-6</span>
            <button onclick="speakText('Atelier de grammaire et questions introductives')" class="px-3 py-1 rounded-full bg-white/15 hover:bg-white/25 active:scale-95 text-xs font-bold font-french flex items-center gap-1.5 transition">
              <i class="fa-solid fa-volume-high"></i>
              <span>Écouter</span>
            </button>
          </div>
          <h2 class="text-xl sm:text-2xl font-black mb-1">الأسئلة التمهيدية وقواعد ما قبل الوحدات (37 سؤالاً)</h2>
          <p class="text-xs sm:text-sm text-indigo-100 font-medium">الأسئلة التمهيدية المعتمدة في كراسة المراجعة الرسمية ص 4 وص 5 وص 6 مصنفة بالكامل مع الشرح.</p>
        </div>

        <!-- Filter Chips -->
        <div class="bg-white p-2 rounded-2xl border border-slate-200 shadow-sm flex flex-wrap gap-1.5">
          <button onclick="filterGrammarQuestions(0)" class="grm-filter-btn px-3 py-1.5 rounded-xl text-xs font-bold bg-indigo-600 text-white">الكل (${c.grammarQuizQuestions.length})</button>
          <button onclick="filterGrammarQuestions(1)" class="grm-filter-btn px-3 py-1.5 rounded-xl text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">1. المضارع ص 4 (${c.revisionEx1MetsAuPresent.length})</button>
          <button onclick="filterGrammarQuestions(2)" class="grm-filter-btn px-3 py-1.5 rounded-xl text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">2. اختر الفعل ص 4 (${c.revisionEx2ChoisisBonneReponse.length})</button>
          <button onclick="filterGrammarQuestions(3)" class="grm-filter-btn px-3 py-1.5 rounded-xl text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">3. الاستفهام ص 5 (${c.revisionEx3Interrogatifs.length})</button>
          <button onclick="filterGrammarQuestions(4)" class="grm-filter-btn px-3 py-1.5 rounded-xl text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">4. الأماكن ص 5 (${c.revisionEx4ArticlesLieux.length})</button>
          <button onclick="filterGrammarQuestions(5)" class="grm-filter-btn px-3 py-1.5 rounded-xl text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">5. النفي ص 6 (${c.revisionEx5Negation.length})</button>
        </div>

        <!-- Questions Container -->
        <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-3" id="grammarQuestionsContainer">
          ${renderQuizQuestionsList(c.grammarQuizQuestions)}
        </div>
      `;
    }

    function filterGrammarQuestions(filterIndex) {
      playClickSound();
      const c = DATA.course;
      document.querySelectorAll('.grm-filter-btn').forEach((btn, idx) => {
        if (idx === filterIndex) {
          btn.classList.add('bg-indigo-600', 'text-white');
          btn.classList.remove('bg-slate-100', 'text-slate-700');
        } else {
          btn.classList.remove('bg-indigo-600', 'text-white');
          btn.classList.add('bg-slate-100', 'text-slate-700');
        }
      });

      let questions = c.grammarQuizQuestions;
      if (filterIndex === 1) questions = c.revisionEx1MetsAuPresent;
      else if (filterIndex === 2) questions = c.revisionEx2ChoisisBonneReponse;
      else if (filterIndex === 3) questions = c.revisionEx3Interrogatifs;
      else if (filterIndex === 4) questions = c.revisionEx4ArticlesLieux;
      else if (filterIndex === 5) questions = c.revisionEx5Negation;

      document.getElementById('grammarQuestionsContainer').innerHTML = renderQuizQuestionsList(questions);
    }
"""
