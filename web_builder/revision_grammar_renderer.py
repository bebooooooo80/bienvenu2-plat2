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

    // 6. RENDER GRAMMAR SCREEN (Atelier de Grammaire p. 84-87 + Questions Introductives p. 4-6)
    function renderGrammarScreen() {
      const c = DATA.course;
      const container = document.getElementById('screen-grammar');

      container.innerHTML = `
        <!-- Grammar Header Banner -->
        <div class="rounded-3xl bg-gradient-to-r from-indigo-700 via-violet-700 to-indigo-900 text-white p-5 sm:p-6 shadow-lg border border-indigo-500/30">
          <div class="flex items-center justify-between gap-3 mb-2">
            <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold font-french">Atelier Grammaire • Pages 84 à 87 & 4-6</span>
            <button onclick="speakText('Atelier de révision et de grammaire. Pages 84 à 87. De quoi se compose la phrase ? Le présent de l indicatif, les articles contractés de lieu, et la négation.')" class="px-3 py-1 rounded-full bg-white/15 hover:bg-white/25 active:scale-95 text-xs font-bold font-french flex items-center gap-1.5 transition">
              <i class="fa-solid fa-volume-high"></i>
              <span>Écouter</span>
            </button>
          </div>
          <h2 class="text-xl sm:text-2xl font-black mb-1">Atelier Révision & Grammaire (ورشة القواعد الشاملة)</h2>
          <p class="text-xs sm:text-sm text-indigo-100 font-medium">كتيّب منهج Bienvenu 2: مراجعة شاملة لقواعد اللغة الفرنسية للصف الثاني الإعدادي (تكوين الجملة، المضارع، الأماكن، النفي، والأسئلة التمهيدية).</p>
        </div>

        <!-- Grammar Subtabs (Matching GrammarScreen.kt) -->
        <div class="bg-white p-1.5 rounded-2xl border border-slate-200 shadow-sm flex overflow-x-auto custom-scrollbar gap-1.5">
          <button onclick="switchGrammarTab('phrase')" id="grm-tab-btn-phrase" class="grm-nav-btn tab-active px-3.5 py-2 rounded-xl text-xs font-bold whitespace-nowrap transition flex items-center gap-1.5">
            <span>🧱 La Phrase (p. 84)</span>
          </button>
          <button onclick="switchGrammarTab('present')" id="grm-tab-btn-present" class="grm-nav-btn px-3.5 py-2 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:text-indigo-600 flex items-center gap-1.5">
            <span>⏱️ Le Présent (p. 85)</span>
          </button>
          <button onclick="switchGrammarTab('lieux')" id="grm-tab-btn-lieux" class="grm-nav-btn px-3.5 py-2 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:text-indigo-600 flex items-center gap-1.5">
            <span>🗺️ Lieux (p. 86)</span>
          </button>
          <button onclick="switchGrammarTab('negation')" id="grm-tab-btn-negation" class="grm-nav-btn px-3.5 py-2 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:text-indigo-600 flex items-center gap-1.5">
            <span>🚫 Négation (p. 87)</span>
          </button>
          <button onclick="switchGrammarTab('intro')" id="grm-tab-btn-intro" class="grm-nav-btn px-3.5 py-2 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:text-indigo-600 flex items-center gap-1.5">
            <span>🎯 التمهيدي (ص 4-6)</span>
          </button>
        </div>

        <!-- TAB 0: PAGE 84 - LA PHRASE -->
        <div id="grm-pane-phrase" class="grm-pane space-y-4">
          <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base sm:text-lg font-black text-indigo-900 font-french">De quoi se compose la phrase ?</h3>
                <p class="text-xs text-indigo-700 font-bold">مما تتكون الجملة في اللغة الفرنسية؟ (كراسة المراجعة ص 84)</p>
              </div>
              <span class="px-2.5 py-1 rounded-lg bg-indigo-100 text-indigo-800 text-xs font-bold font-french">Page 84</span>
            </div>

            <!-- Structure Diagram -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-3">
              <!-- 1. Sujet -->
              <div class="p-4 rounded-2xl bg-indigo-50/70 border border-indigo-200 space-y-2">
                <div class="flex items-center justify-between">
                  <span class="text-xs font-black text-indigo-900 font-french">1. Sujet (الفاعل)</span>
                  <span class="text-[10px] px-2 py-0.5 rounded bg-indigo-200 text-indigo-800 font-bold">بداية الجملة</span>
                </div>
                <div class="text-xs space-y-1 text-slate-800">
                  <p><strong>• Nom (اسم):</strong></p>
                  <p class="text-slate-600 text-[11px] font-french">Ahmed, Sara, Mona, Le stylo, Le lion, Les élèves...</p>
                  <p class="pt-1"><strong>• Pronom (ضمير فاعل):</strong></p>
                  <div class="flex flex-wrap gap-1 font-french text-[11px] font-bold text-indigo-700">
                    <span class="px-1.5 py-0.5 rounded bg-white border border-indigo-200">je</span>
                    <span class="px-1.5 py-0.5 rounded bg-white border border-indigo-200">tu</span>
                    <span class="px-1.5 py-0.5 rounded bg-white border border-indigo-200">il / elle</span>
                    <span class="px-1.5 py-0.5 rounded bg-white border border-indigo-200">on</span>
                    <span class="px-1.5 py-0.5 rounded bg-white border border-indigo-200">nous</span>
                    <span class="px-1.5 py-0.5 rounded bg-white border border-indigo-200">vous</span>
                    <span class="px-1.5 py-0.5 rounded bg-white border border-indigo-200">ils / elles</span>
                  </div>
                </div>
              </div>

              <!-- 2. Verbe -->
              <div class="p-4 rounded-2xl bg-amber-50/70 border border-amber-200 space-y-2">
                <div class="flex items-center justify-between">
                  <span class="text-xs font-black text-amber-950 font-french">2. Verbe (الفعل)</span>
                  <span class="text-[10px] px-2 py-0.5 rounded bg-amber-200 text-amber-900 font-bold">المحرك الأساسي</span>
                </div>
                <div class="text-xs space-y-1.5 text-slate-800">
                  <p><strong>• 1er groupe :</strong> ينتهي بـ <span class="font-french font-bold text-amber-700">(-er)</span> مثل parler, visiter, regarder</p>
                  <p><strong>• 2ème groupe :</strong> ينتهي بـ <span class="font-french font-bold text-amber-700">(-ir)</span> مثل finir, choisir, réussir</p>
                  <p><strong>• 3ème groupe :</strong> أفعال شاذة <span class="font-french font-bold text-amber-700">(-re, -oir, -ir)</span> مثل être, avoir, aller, faire</p>
                </div>
              </div>

              <!-- 3. Complément -->
              <div class="p-4 rounded-2xl bg-emerald-50/70 border border-emerald-200 space-y-2">
                <div class="flex items-center justify-between">
                  <span class="text-xs font-black text-emerald-900 font-french">3. Complément (المفعول)</span>
                  <span class="text-[10px] px-2 py-0.5 rounded bg-emerald-200 text-emerald-800 font-bold">مكمل المعنى</span>
                </div>
                <div class="text-xs space-y-1.5 text-slate-800">
                  <p><strong>• C.O.D (مباشر):</strong> يأتي بعد الفعل مباشرة <u>بدون حرف جر</u> (manger <strong>une pomme</strong>).</p>
                  <p><strong>• C.O.I (غير مباشر):</strong> يسبق بـ <u>حرف جر</u> مثل à, de, pour (parler <strong>à Ali</strong>, aller <strong>à l'école</strong>).</p>
                </div>
              </div>
            </div>

            <!-- Analysed Examples -->
            <div class="pt-2 space-y-2.5">
              <h4 class="text-xs font-bold text-slate-800 flex items-center gap-1.5">
                <i class="fa-solid fa-magnifying-glass text-indigo-600"></i>
                <span>أمثلة توضيحية من كراسة المراجعة ص 84 مع التحليل الإعرابي :</span>
              </h4>
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
                <div class="p-3.5 rounded-2xl bg-slate-50 border border-slate-200 space-y-1.5">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-indigo-700">Exemple 1</span>
                    <button onclick="speakText('Je vais à l école')" class="text-indigo-600 hover:text-indigo-800"><i class="fa-solid fa-volume-high"></i></button>
                  </div>
                  <p class="font-bold text-slate-900 font-french text-sm" dir="ltr">Je vais à l'école.</p>
                  <p class="text-slate-600 text-[11px]">أنا أذهب إلى المدرسة.</p>
                  <div class="pt-1 border-t border-slate-200/60 text-[11px] text-slate-700 space-y-0.5">
                    <p><span class="font-bold text-indigo-600">Sujet (فاعل):</span> Je (ضمير فاعل)</p>
                    <p><span class="font-bold text-amber-600">Verbe (فعل):</span> vais (فعل aller في المضارع)</p>
                    <p><span class="font-bold text-emerald-600">Complément:</span> à l'école (C.O.I مفعول به غير مباشر مسبوق بحرف جر)</p>
                  </div>
                </div>

                <div class="p-3.5 rounded-2xl bg-slate-50 border border-slate-200 space-y-1.5">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-indigo-700">Exemple 2</span>
                    <button onclick="speakText('Sara mange une pomme')" class="text-indigo-600 hover:text-indigo-800"><i class="fa-solid fa-volume-high"></i></button>
                  </div>
                  <p class="font-bold text-slate-900 font-french text-sm" dir="ltr">Sara mange une pomme.</p>
                  <p class="text-slate-600 text-[11px]">سارة تأكل تفاحة.</p>
                  <div class="pt-1 border-t border-slate-200/60 text-[11px] text-slate-700 space-y-0.5">
                    <p><span class="font-bold text-indigo-600">Sujet (فاعل):</span> Sara (اسم علم مفرد مؤنث = Elle)</p>
                    <p><span class="font-bold text-amber-600">Verbe (فعل):</span> mange (فعل manger مجموعة أولى)</p>
                    <p><span class="font-bold text-emerald-600">Complément:</span> une pomme (C.O.D مفعول به مباشر غير مسبوق بحرف جر)</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- TAB 1: PAGE 85 - LE PRÉSENT DE L'INDICATIF -->
        <div id="grm-pane-present" class="grm-pane hidden space-y-4">
          <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base sm:text-lg font-black text-indigo-900 font-french">Le présent de l'indicatif (Page 85)</h3>
                <p class="text-xs text-indigo-700 font-bold">زمن المضارع: نهايات المجموعات والأفعال الشواذ والكلمات الدالة</p>
              </div>
              <span class="px-2.5 py-1 rounded-lg bg-indigo-100 text-indigo-800 text-xs font-bold font-french">Page 85</span>
            </div>

            <!-- Group 1 and 2 Table -->
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
              <!-- 1er groupe -->
              <div class="p-4 rounded-2xl bg-indigo-50/60 border border-indigo-200 space-y-2">
                <div class="flex items-center justify-between">
                  <span class="font-black text-indigo-900 font-french">1er groupe (-er) : Marcher (يمشي)</span>
                  <button onclick="speakText('Je marche, tu marches, il marche, nous marchons, vous marchez, ils marchent')" class="text-indigo-600 hover:text-indigo-800"><i class="fa-solid fa-volume-high"></i></button>
                </div>
                <p class="text-[11px] text-slate-600">نحذف (-er) ونضيف النهايات التالية :</p>
                <div class="space-y-1 font-french font-semibold text-slate-800 text-[11px]" dir="ltr">
                  <p>Je <span class="font-bold text-indigo-700">march-e</span></p>
                  <p>Tu <span class="font-bold text-indigo-700">march-es</span></p>
                  <p>Il / Elle <span class="font-bold text-indigo-700">march-e</span></p>
                  <p>Nous <span class="font-bold text-indigo-700">march-ons</span></p>
                  <p>Vous <span class="font-bold text-indigo-700">march-ez</span></p>
                  <p>Ils / Elles <span class="font-bold text-indigo-700">march-ent</span></p>
                </div>
              </div>

              <!-- 2eme groupe -->
              <div class="p-4 rounded-2xl bg-emerald-50/60 border border-emerald-200 space-y-2">
                <div class="flex items-center justify-between">
                  <span class="font-black text-emerald-900 font-french">2ème groupe (-ir) : Finir (ينهي)</span>
                  <button onclick="speakText('Je finis, tu finis, il finit, nous finissons, vous finissez, ils finissent')" class="text-emerald-600 hover:text-emerald-800"><i class="fa-solid fa-volume-high"></i></button>
                </div>
                <p class="text-[11px] text-slate-600">نحذف (-ir) ونضيف النهايات التالية :</p>
                <div class="space-y-1 font-french font-semibold text-slate-800 text-[11px]" dir="ltr">
                  <p>Je <span class="font-bold text-emerald-700">fin-is</span></p>
                  <p>Tu <span class="font-bold text-emerald-700">fin-is</span></p>
                  <p>Il / Elle <span class="font-bold text-emerald-700">fin-it</span></p>
                  <p>Nous <span class="font-bold text-emerald-700">fin-issons</span></p>
                  <p>Vous <span class="font-bold text-emerald-700">fin-issez</span></p>
                  <p>Ils / Elles <span class="font-bold text-emerald-700">fin-issent</span></p>
                </div>
              </div>
            </div>

            <!-- 3eme groupe: Irregular Verbs Table -->
            <div class="space-y-2 pt-2">
              <h4 class="text-xs font-bold text-slate-800 flex items-center justify-between">
                <span>⭐ الأفعال الشواذ الستة الأساسية المقررة (3ème groupe - ص 85) :</span>
                <span class="text-[10px] text-indigo-600 font-semibold">احفظها جيداً للامتحان</span>
              </h4>
              <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2.5 text-xs font-french" dir="ltr">
                <!-- Aller -->
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1">
                  <div class="flex items-center justify-between font-bold text-indigo-800">
                    <span>aller (يذهب)</span>
                    <button onclick="speakText('Je vais, tu vas, il va, nous allons, vous allez, ils vont')" class="text-slate-400 hover:text-indigo-600"><i class="fa-solid fa-volume-high text-[11px]"></i></button>
                  </div>
                  <p class="text-[11px] text-slate-700">vais, vas, va, allons, allez, vont</p>
                </div>
                <!-- Avoir -->
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1">
                  <div class="flex items-center justify-between font-bold text-indigo-800">
                    <span>avoir (يملك/عنده)</span>
                    <button onclick="speakText('J ai, tu as, il a, nous avons, vous avez, ils ont')" class="text-slate-400 hover:text-indigo-600"><i class="fa-solid fa-volume-high text-[11px]"></i></button>
                  </div>
                  <p class="text-[11px] text-slate-700">ai, as, a, avons, avez, ont</p>
                </div>
                <!-- Faire -->
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1">
                  <div class="flex items-center justify-between font-bold text-indigo-800">
                    <span>faire (يعمل/يمارس)</span>
                    <button onclick="speakText('Je fais, tu fais, il fait, nous faisons, vous faites, ils font')" class="text-slate-400 hover:text-indigo-600"><i class="fa-solid fa-volume-high text-[11px]"></i></button>
                  </div>
                  <p class="text-[11px] text-slate-700">fais, fais, fait, faisons, <strong>faites</strong>, <strong>font</strong></p>
                </div>
                <!-- Etre -->
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1">
                  <div class="flex items-center justify-between font-bold text-indigo-800">
                    <span>être (يكون)</span>
                    <button onclick="speakText('Je suis, tu es, il est, nous sommes, vous êtes, ils sont')" class="text-slate-400 hover:text-indigo-600"><i class="fa-solid fa-volume-high text-[11px]"></i></button>
                  </div>
                  <p class="text-[11px] text-slate-700">suis, es, est, sommes, <strong>êtes</strong>, sont</p>
                </div>
                <!-- Vouloir -->
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1">
                  <div class="flex items-center justify-between font-bold text-indigo-800">
                    <span>vouloir (يريد)</span>
                    <button onclick="speakText('Je veux, tu veux, il veut, nous voulons, vous voulez, ils veulent')" class="text-slate-400 hover:text-indigo-600"><i class="fa-solid fa-volume-high text-[11px]"></i></button>
                  </div>
                  <p class="text-[11px] text-slate-700">veux, veux, veut, voulons, voulez, veulent</p>
                </div>
                <!-- Pouvoir -->
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1">
                  <div class="flex items-center justify-between font-bold text-indigo-800">
                    <span>pouvoir (يستطيع)</span>
                    <button onclick="speakText('Je peux, tu peux, il peut, nous pouvons, vous pouvez, ils peuvent')" class="text-slate-400 hover:text-indigo-600"><i class="fa-solid fa-volume-high text-[11px]"></i></button>
                  </div>
                  <p class="text-[11px] text-slate-700">peux, peux, peut, pouvons, pouvez, peuvent</p>
                </div>
              </div>
            </div>

            <!-- Key Words of Present -->
            <div class="p-3.5 rounded-2xl bg-teal-50 border border-teal-200 space-y-2">
              <span class="text-xs font-bold text-teal-900 block">🔑 Les mots clés du présent (الكلمات الدالة على زمن المضارع) :</span>
              <div class="flex flex-wrap gap-2 text-xs font-french">
                <span class="px-2.5 py-1 rounded-lg bg-white border border-teal-200 text-teal-800 font-bold">Maintenant (الآن)</span>
                <span class="px-2.5 py-1 rounded-lg bg-white border border-teal-200 text-teal-800 font-bold">Aujourd'hui (اليوم)</span>
                <span class="px-2.5 py-1 rounded-lg bg-white border border-teal-200 text-teal-800 font-bold">Chaque jour / matin (كل يوم/صباح)</span>
                <span class="px-2.5 py-1 rounded-lg bg-white border border-teal-200 text-teal-800 font-bold">Souvent (غالباً)</span>
                <span class="px-2.5 py-1 rounded-lg bg-white border border-teal-200 text-teal-800 font-bold">Toujours (دائماً)</span>
              </div>
            </div>
          </div>
        </div>

        <!-- TAB 2: PAGE 86 - LES ARTICLES CONTRACTÉS DE LIEUX -->
        <div id="grm-pane-lieux" class="grm-pane hidden space-y-4">
          <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base sm:text-lg font-black text-indigo-900 font-french">Les Articles Contractés (Lieux) (Page 86)</h3>
                <p class="text-xs text-indigo-700 font-bold">أدوات وحروف جر المكان مع المدن والمواقع والمؤسسات</p>
              </div>
              <span class="px-2.5 py-1 rounded-lg bg-indigo-100 text-indigo-800 text-xs font-bold font-french">Page 86</span>
            </div>

            <!-- Rules Summary -->
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2.5 text-xs">
              <div class="p-3 rounded-xl bg-indigo-50/70 border border-indigo-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">au</span>
                <p class="font-bold text-slate-800">مفرد مذكر مبدوء بساكن (à + le = au)</p>
                <p class="text-[11px] font-french text-indigo-700 font-semibold" dir="ltr">au club, au cinéma, au stade, au restaurant, au musée, au zoo</p>
              </div>
              <div class="p-3 rounded-xl bg-purple-50/70 border border-purple-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-purple-600 text-white font-bold font-french text-[11px]">à la</span>
                <p class="font-bold text-slate-800">مفرد مؤنث مبدوء بساكن</p>
                <p class="text-[11px] font-french text-purple-700 font-semibold" dir="ltr">à la pharmacie, à la gare, à la poste, à la plage, à la piscine</p>
              </div>
              <div class="p-3 rounded-xl bg-emerald-50/70 border border-emerald-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-emerald-600 text-white font-bold font-french text-[11px]">à l'</span>
                <p class="font-bold text-slate-800">مفرد يبدأ بحرف متحرك أو h صامتة</p>
                <p class="text-[11px] font-french text-emerald-700 font-semibold" dir="ltr">à l'école, à l'hôpital, à l'hôtel, à l'aéroport</p>
              </div>
              <div class="p-3 rounded-xl bg-amber-50/70 border border-amber-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-amber-600 text-white font-bold font-french text-[11px]">aux</span>
                <p class="font-bold text-slate-800">جمع بنوعيه ينتهي بـ s أو x (à + les = aux)</p>
                <p class="text-[11px] font-french text-amber-800 font-semibold" dir="ltr">aux pyramides, aux magasins</p>
              </div>
              <div class="p-3 rounded-xl bg-rose-50/70 border border-rose-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-rose-600 text-white font-bold font-french text-[11px]">à</span>
                <p class="font-bold text-slate-800">أمام أسماء المدن العادية</p>
                <p class="text-[11px] font-french text-rose-700 font-semibold" dir="ltr">à Paris, à Alexandrie, à Tanta, à Louxor</p>
              </div>
              <div class="p-3 rounded-xl bg-teal-50/70 border border-teal-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-teal-600 text-white font-bold font-french text-[11px]">en</span>
                <p class="font-bold text-slate-800">أمام أسماء الدول المؤنثة والمبدوءة بمتحرك</p>
                <p class="text-[11px] font-french text-teal-700 font-semibold" dir="ltr">en Égypte, en France, en Italie</p>
              </div>
            </div>

            <!-- Crucial Exception: Sauf au Caire -->
            <div class="p-4 rounded-2xl bg-amber-400/15 border-2 border-amber-400 space-y-1.5">
              <span class="px-2.5 py-0.5 rounded-full bg-amber-400 text-amber-950 font-black text-xs font-french">⚡ Sauf ! استثناءات ذهبية هامة جداً في الامتحان</span>
              <p class="text-xs font-bold text-slate-900 leading-relaxed">
                كل المدن تأخذ حرف الجر <span class="font-french font-bold text-indigo-700">(à)</span> ما عدا 3 مدن ومحافظات مصرية مذكر تأخذ <span class="font-french font-bold text-rose-700">(au)</span> :
              </p>
              <div class="flex flex-wrap gap-2 text-xs font-french font-bold text-indigo-900 pt-1">
                <span class="px-3 py-1 rounded-xl bg-white border border-amber-300">➔ au Caire (في القاهرة)</span>
                <span class="px-3 py-1 rounded-xl bg-white border border-amber-300">➔ au Fayoum (في الفيوم)</span>
                <span class="px-3 py-1 rounded-xl bg-white border border-amber-300">➔ au Sinaï (في سيناء)</span>
              </div>
            </div>
          </div>
        </div>

        <!-- TAB 3: PAGE 87 - LA NÉGATION -->
        <div id="grm-pane-negation" class="grm-pane hidden space-y-4">
          <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base sm:text-lg font-black text-rose-600 font-french">La Négation (Page 87)</h3>
                <p class="text-xs text-slate-700 font-bold">صيغة النفي وقواعد تحويل أدوات النكرة والتجزئة</p>
              </div>
              <span class="px-2.5 py-1 rounded-lg bg-rose-100 text-rose-800 text-xs font-bold font-french">Page 87</span>
            </div>

            <!-- Base Rule -->
            <div class="p-4 rounded-2xl bg-rose-50 border border-rose-200 space-y-2 text-xs">
              <span class="font-bold text-rose-900 block">الصيغة الأساسية للنفي في الفرنسية :</span>
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-2 font-french font-bold text-rose-800 text-center" dir="ltr">
                <div class="p-2.5 rounded-xl bg-white border border-rose-200">ne + Verbe + pas</div>
                <div class="p-2.5 rounded-xl bg-white border border-rose-200">n' + Verbe (voyelle) + pas</div>
              </div>
              <div class="space-y-1 pt-1 font-french text-slate-800" dir="ltr">
                <p>• Je <strong>ne vais pas</strong> à l'école. <span class="text-slate-400 font-sans" dir="rtl">(أنا لا أذهب إلى المدرسة)</span></p>
                <p>• Je <strong>n'aime pas</strong> mon amie. <span class="text-slate-400 font-sans" dir="rtl">(أنا لا أحب صديقتي - n' لأن aimer يبدأ بمتحرك)</span></p>
              </div>
            </div>

            <!-- Golden Rule: Transformation to de/d' -->
            <div class="p-4 rounded-2xl bg-amber-50 border-2 border-amber-300 space-y-3">
              <span class="px-2.5 py-0.5 rounded-full bg-amber-400 text-amber-950 font-black text-xs font-french">⭐ N.B. أهم قاعدة نفي في الامتحان (تحويل الأدوات)</span>
              <p class="text-xs text-slate-800 leading-relaxed font-semibold">
                عند النفي، تتحول أدوات النكرة والتجزئة <span class="font-french font-bold text-rose-700">(un, une, des, du, de la, de l')</span> إلى <span class="font-french font-bold text-indigo-700">« de »</span> أو <span class="font-french font-bold text-indigo-700">« d' »</span> (أمام حرف متحرك).
              </p>

              <div class="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
                <!-- Case 1: Normal Verb -->
                <div class="p-3.5 rounded-xl bg-white border border-rose-200 space-y-1.5" dir="ltr">
                  <span class="font-bold text-rose-700 font-sans" dir="rtl">مع الأفعال العادية (مثل avoir, manger, acheter) :</span>
                  <p class="text-slate-700">J'ai <strong>un</strong> cahier.</p>
                  <p class="font-bold text-rose-600">➔ Je n'ai pas <strong>de</strong> cahier.</p>
                  <p class="text-slate-500 text-[10px] font-sans" dir="rtl">(تحولت un إلى de في النفي)</p>
                </div>

                <!-- Case 2: Verb Etre Exception -->
                <div class="p-3.5 rounded-xl bg-white border border-emerald-300 space-y-1.5" dir="ltr">
                  <span class="font-bold text-emerald-700 font-sans" dir="rtl">⚠️ ما عدا مع فعل الكينونة (Être) :</span>
                  <p class="text-slate-700">C'est <strong>un</strong> professeur. / Je suis <strong>un</strong> élève.</p>
                  <p class="font-bold text-emerald-700">➔ Ce n'est pas <strong>un</strong> professeur.</p>
                  <p class="text-slate-500 text-[10px] font-sans" dir="rtl">(مع être تظل الأداة كما هي دون تغيير!)</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- TAB 4: INTRODUCTORY QUESTIONS (ص 4-6) -->
        <div id="grm-pane-intro" class="grm-pane hidden space-y-4">
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
        </div>
      `;
    }

    function switchGrammarTab(tabId) {
      playClickSound();
      document.querySelectorAll('.grm-pane').forEach(el => el.classList.add('hidden'));
      document.querySelectorAll('.grm-nav-btn').forEach(el => el.classList.remove('tab-active'));
      const p = document.getElementById('grm-pane-' + tabId);
      const b = document.getElementById('grm-tab-btn-' + tabId);
      if (p) p.classList.remove('hidden');
      if (b) b.classList.add('tab-active');
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
