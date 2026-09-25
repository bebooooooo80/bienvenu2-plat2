# -*- coding: utf-8 -*-

def get_unit2_renderer_code():
    return """
    // 3. RENDER UNIT 2 SCREEN
    function renderUnit2Screen() {
      const c = DATA.course;
      const container = document.getElementById('screen-unit2');

      container.innerHTML = `
        <!-- Unit 2 Header -->
        <div class="rounded-3xl bg-gradient-to-r from-amber-600 via-orange-600 to-amber-700 text-white p-5 sm:p-6 shadow-lg border border-amber-500/30">
          <div class="flex items-center justify-between gap-3 mb-2">
            <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold font-french">Unité 2 • Pages 35-58</span>
            <button onclick="speakText('Unité deux : Les repas, au restaurant')" class="px-3 py-1 rounded-full bg-white/15 hover:bg-white/25 active:scale-95 text-xs font-bold font-french flex items-center gap-1.5 transition">
              <i class="fa-solid fa-volume-high"></i>
              <span>Écouter</span>
            </button>
          </div>
          <h2 class="text-xl sm:text-2xl font-black mb-1">Unité 2 : Les repas (الوجبات وفي المطعم)</h2>
          <p class="text-xs sm:text-sm text-amber-100 font-medium">النص والتمارين (20 تمرين)، الوجبات والأطعمة (28 صنفاً)، كويز الوجبات (10)، أدوات التجزئة، والاستفهام، ومواقف المطعم.</p>
        </div>

        <!-- Unit 2 Sub-tabs -->
        <div class="bg-white p-1.5 rounded-2xl border border-slate-200 shadow-sm flex overflow-x-auto custom-scrollbar gap-1">
          <button onclick="switchUnit2SubTab('texte')" id="u2-tab-btn-texte" class="u2-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition tab-active">
            <i class="fa-solid fa-utensils ml-1"></i> النص وحوار المطعم
          </button>
          <button onclick="switchUnit2SubTab('repas')" id="u2-tab-btn-repas" class="u2-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-bowl-food ml-1"></i> الوجبات والأطعمة (${c.unit2RepasItems.length})
          </button>
          <button onclick="switchUnit2SubTab('vocab')" id="u2-tab-btn-vocab" class="u2-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-book ml-1"></i> المفردات (${c.unit2VocabWords.length})
          </button>
          <button onclick="switchUnit2SubTab('partitifs')" id="u2-tab-btn-partitifs" class="u2-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-layer-group ml-1"></i> أدوات التجزئة والاستفهام
          </button>
          <button onclick="switchUnit2SubTab('situations')" id="u2-tab-btn-situations" class="u2-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-comments ml-1"></i> مواقف المطعم (${c.unit2QuiParleItems.length + c.unit2OuVasTuItems.length})
          </button>
          <button onclick="switchUnit2SubTab('production')" id="u2-tab-btn-production" class="u2-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-pen-nib ml-1"></i> التعبير والجمل
          </button>
        </div>

        <!-- 1. TEXTE & EXERCISES -->
        <div id="u2-pane-texte" class="u2-pane space-y-4">
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <div class="flex items-center justify-between border-b border-slate-100 pb-2.5">
              <h3 class="text-base font-bold text-slate-900 flex items-center gap-2">
                <i class="fa-solid fa-receipt text-amber-600"></i>
                <span>نص الوحدة الثانية الرسمي (Au restaurant)</span>
              </h3>
              <button onclick="speakText('${c.unit2FullText.replace(/['"\\n]/g, ' ')}')" class="px-3 py-1 rounded-xl bg-amber-50 text-amber-700 text-xs font-bold font-french flex items-center gap-1.5 hover:bg-amber-100 transition">
                <i class="fa-solid fa-volume-high"></i>
                <span>استمع للنص كاملاً</span>
              </button>
            </div>
            
            <div class="bg-slate-50 p-4 rounded-xl text-xs sm:text-sm font-french leading-relaxed text-slate-800 border border-slate-200/80" dir="ltr">
              ${c.unit2FullText.replace(/\\n/g, '<br>')}
            </div>
            
            <div class="p-3 bg-amber-50/70 rounded-xl text-xs text-amber-900 border border-amber-200/70 leading-relaxed">
              <strong>ترجمة النص باللغة العربية :</strong><br>
              ${c.unit2FullTextArabic.replace(/\\n/g, '<br>')}
            </div>
          </div>

          <!-- Dialogues -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">حوارات المطعم والطلب (${c.unit2Dialogues.length} مقاطع)</h3>
            <div class="space-y-2">
              ${c.unit2Dialogues.map(d => `
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200/80 flex items-start justify-between gap-3">
                  <div class="flex-1" dir="ltr">
                    <span class="font-bold text-amber-700 font-french text-xs block mb-0.5">${d.speaker} :</span>
                    <p class="text-xs font-french text-slate-800">${d.text}</p>
                    ${d.arabicNote ? `<p class="text-[11px] text-slate-500 font-sans mt-1" dir="rtl">${d.arabicNote}</p>` : ''}
                  </div>
                  <button onclick="speakText('${d.speaker} : ${d.text.replace(/['"\\n]/g, ' ')}')" class="w-8 h-8 rounded-lg bg-amber-50 text-amber-600 hover:bg-amber-100 flex items-center justify-center shrink-0">
                    <i class="fa-solid fa-volume-high text-xs"></i>
                  </button>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Ex1: Bon Groupe -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">1. Choisis le bon groupe (اختر الإجابة الصحيحة)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.unit2Ex1BonGroupe)}</div>
          </div>

          <!-- Ex2: Vrai ou Faux -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">2. Mets (Vrai) ou (Faux) (صح أم خطأ)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.unit2Ex2VraiOuFaux)}</div>
          </div>

          <!-- Ex3: Réponds pris du texte -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">3. Réponds aux questions d'après le texte (أجب من النص)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.unit2Ex3RepondsPrisDuTexte)}</div>
          </div>

          <!-- Ex4: Complète -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">4. Complète d'après le texte (أكمل من النص)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.unit2Ex4Complete)}</div>
          </div>
        </div>

        <!-- 2. LES REPAS & FOOD ITEMS -->
        <div id="u2-pane-repas" class="u2-pane hidden space-y-4">
          <!-- Meal Types Overview -->
          <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">
            ${c.unit2RepasList.map(m => `
              <div class="bg-white p-4 rounded-2xl border border-slate-200 shadow-sm space-y-1">
                <span class="block text-xs font-bold text-amber-700 font-french">${m.first}</span>
                <span class="text-xs text-slate-600">${m.second}</span>
              </div>
            `).join('')}
          </div>

          <!-- Food Items Grid (28 items) -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <div class="flex items-center justify-between border-b border-slate-100 pb-2">
              <h3 class="text-base font-bold text-slate-900">قائمة الأطعمة والمشروبات الواردة في ص 41 و 42 (${c.unit2RepasItems.length} صنفاً)</h3>
              <span class="text-xs text-amber-600 font-bold font-french">Articles partitifs inclus</span>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2.5">
              ${c.unit2RepasItems.map(item => `
                <div class="p-3 rounded-xl bg-slate-50 hover:bg-amber-50/50 border border-slate-200 flex items-center justify-between gap-2">
                  <div class="overflow-hidden">
                    <span class="text-xs font-bold text-slate-900 font-french block truncate">${item.emoji} ${item.frenchWithArticle}</span>
                    <span class="text-[11px] text-slate-500 block truncate">${item.arabic} (${item.genderAr})</span>
                    <span class="text-[10px] text-amber-700 block truncate font-french">${item.sampleSentenceFr}</span>
                  </div>
                  <button onclick="speakText('${item.frenchWithArticle.replace(/['"]/g, ' ')}')" class="w-8 h-8 rounded-lg bg-amber-100/60 hover:bg-amber-200 text-amber-700 flex items-center justify-center shrink-0">
                    <i class="fa-solid fa-volume-high text-xs"></i>
                  </button>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Repas Quiz (10 Questions) -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">اختبار كويز الوجبات التفاعلي (${c.unit2RepasQuizList.length} أسئلة)</h3>
            <div class="space-y-3">
              ${c.unit2RepasQuizList.map(q => `
                <div class="quiz-card p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-2">
                  <p class="font-bold text-slate-900 font-french" dir="ltr">${q.questionFr}</p>
                  <p class="text-slate-500">${q.questionAr}</p>
                  <div class="grid grid-cols-1 sm:grid-cols-3 gap-1.5" dir="ltr">
                    ${q.options.map((opt, oIdx) => `
                      <button onclick="checkQuizOption(this, ${oIdx === q.correctIndex}, '${(q.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:border-amber-300 transition">
                        ${opt}
                      </button>
                    `).join('')}
                  </div>
                  <div class="quiz-feedback hidden text-xs font-bold mt-1.5"></div>
                </div>
              `).join('')}
            </div>
          </div>
        </div>

        <!-- 3. VOCABULAIRE -->
        <div id="u2-pane-vocab" class="u2-pane hidden space-y-4">
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">مفردات الوحدة الثانية (${c.unit2VocabWords.length} كلمة)</h3>
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2.5">
              ${renderVocabWordsList(c.unit2VocabWords)}
            </div>
          </div>
        </div>

        <!-- 4. PARTITIFS & INTERROGATIFS -->
        <div id="u2-pane-partitifs" class="u2-pane hidden space-y-4">
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">1. تمارين أدوات التجزئة (Les Articles Partitifs : du, de la, de l', des)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.partitiveQuestions)}</div>
          </div>

          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">2. تمارين أدوات الاستفهام (Les mots interrogatifs : Où, Quand, Comment...)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.interrogativeQuestions)}</div>
          </div>
        </div>

        <!-- 5. SITUATIONS -->
        <div id="u2-pane-situations" class="u2-pane hidden space-y-4">
          <!-- Qui parle -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">1. مواقف المطعم: من المتحدث؟ (Qui parle? - ${c.unit2QuiParleItems.length} مواقف)</h3>
            <div class="space-y-2">
              ${c.unit2QuiParleItems.map(item => `
                <div class="p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-indigo-900 font-french" dir="ltr">${item.number}. ${item.quoteFr}</span>
                    <span class="text-slate-500">${item.quoteAr}</span>
                  </div>
                  <p class="font-bold text-amber-700 font-french" dir="ltr">➔ Qui parle : ${item.speakerFr} (${item.speakerAr})</p>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Où vas-tu -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">2. أين تذهب لكي...؟ (Où vas-tu pour...? - ${c.unit2OuVasTuItems.length} مواقف)</h3>
            <div class="space-y-2">
              ${c.unit2OuVasTuItems.map(item => `
                <div class="p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-slate-900 font-french" dir="ltr">${item.number}. Pour ${item.activityFr} :</span>
                    <span class="text-slate-500">${item.activityAr}</span>
                  </div>
                  <p class="font-bold text-emerald-700 font-french" dir="ltr">➔ ${item.expectedAnswerFr}</p>
                  <p class="text-slate-500 text-[11px]">${item.answerAr}</p>
                </div>
              `).join('')}
            </div>
          </div>
        </div>

        <!-- 6. PRODUCTION -->
        <div id="u2-pane-production" class="u2-pane hidden space-y-4">
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">1. تكوين الجمل (Fais des phrases avec)</h3>
            <div class="space-y-2">
              ${c.unit2FaisDesPhrasesItems.map(item => `
                <div class="p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-amber-700 font-french">${item.number}. [ ${item.promptFr} ]</span>
                    <span class="text-slate-500">${item.promptAr}</span>
                  </div>
                  <p class="font-bold text-slate-900 font-french" dir="ltr">➔ ${item.modelSentenceFr}</p>
                  <p class="text-slate-500">${item.modelSentenceAr}</p>
                </div>
              `).join('')}
            </div>
          </div>

          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">2. موضوع الإنتاج: في المطعم (Au restaurant)</h3>
            <div class="p-3.5 bg-amber-50 border border-amber-200 rounded-xl space-y-2">
              <h4 class="font-bold text-amber-900 text-xs font-french" dir="ltr">${c.unit2CompositionTopic.titleFr} (${c.unit2CompositionTopic.titleAr})</h4>
              <p class="text-xs text-slate-700">${c.unit2CompositionTopic.instructionAr}</p>
              <div class="bg-white p-3 rounded-lg border border-amber-100 space-y-1 text-xs font-french" dir="ltr">
                ${c.unit2CompositionTopic.sentences.map(s => `
                  <p><span class="font-bold text-amber-700">${s.order}.</span> ${s.french} <span class="text-slate-400 font-sans" dir="rtl">(${s.arabic})</span></p>
                `).join('')}
              </div>
            </div>
          </div>
        </div>
      `;
    }

    function switchUnit2SubTab(tabId) {
      playClickSound();
      document.querySelectorAll('.u2-pane').forEach(el => el.classList.add('hidden'));
      document.querySelectorAll('.u2-nav-btn').forEach(el => el.classList.remove('tab-active'));
      const p = document.getElementById('u2-pane-' + tabId);
      const b = document.getElementById('u2-tab-btn-' + tabId);
      if (p) p.classList.remove('hidden');
      if (b) b.classList.add('tab-active');
    }
"""
