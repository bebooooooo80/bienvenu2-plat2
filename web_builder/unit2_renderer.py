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

        <!-- 4. ARTICLES PARTITIFS & MOTS INTERROGATIFS -->
        <div id="u2-pane-partitifs" class="u2-pane hidden space-y-4">
          <!-- Partitives Rules & Explanations (Pages 43-45) -->
          <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base sm:text-lg font-black text-amber-900 font-french">Les Articles Partitifs (أدوات التجزئة - ص 43)</h3>
                <p class="text-xs text-amber-700 font-bold">تسبق المأكولات والمشروبات والأشياء التي لا تعد للدلالة على جزء من الكل</p>
              </div>
              <span class="px-2.5 py-1 rounded-lg bg-amber-100 text-amber-800 text-xs font-bold font-french">Pages 43-45</span>
            </div>

            <!-- Articles Grid -->
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-2.5 text-xs">
              <div class="p-3 rounded-xl bg-amber-50/70 border border-amber-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-amber-600 text-white font-bold font-french text-[11px]">du</span>
                <p class="font-bold text-slate-800">مفرد مذكر مبدوء بساكن</p>
                <p class="text-[11px] font-french text-amber-900 font-semibold" dir="ltr">du veau, du poulet, du riz, du fromage, du poisson, du pain</p>
              </div>
              <div class="p-3 rounded-xl bg-orange-50/70 border border-orange-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-orange-600 text-white font-bold font-french text-[11px]">de la</span>
                <p class="font-bold text-slate-800">مفرد مؤنث مبدوء بساكن</p>
                <p class="text-[11px] font-french text-orange-900 font-semibold" dir="ltr">de la viande, de la salade, de la soupe, de la glace</p>
              </div>
              <div class="p-3 rounded-xl bg-emerald-50/70 border border-emerald-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-emerald-600 text-white font-bold font-french text-[11px]">de l'</span>
                <p class="font-bold text-slate-800">مفرد يبدأ بحرف متحرك</p>
                <p class="text-[11px] font-french text-emerald-900 font-semibold" dir="ltr">de l'eau (ماء), de l'huile (زيت)</p>
              </div>
              <div class="p-3 rounded-xl bg-purple-50/70 border border-purple-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-purple-600 text-white font-bold font-french text-[11px]">des</span>
                <p class="font-bold text-slate-800">جمع بنوعيه (s أو x)</p>
                <p class="text-[11px] font-french text-purple-900 font-semibold" dir="ltr">des fruits, des légumes, des frites, des crudités</p>
              </div>
            </div>

            <!-- Golden Rules of Partitives -->
            <div class="space-y-2.5 pt-1 text-xs">
              <div class="p-3.5 bg-amber-50 rounded-xl border border-amber-300 space-y-1">
                <p class="font-bold text-amber-950">⚠️ 1. أفعال الميول والرغبة (Verbes de préférence) :</p>
                <p class="text-slate-700 leading-relaxed">
                  مع أفعال <span class="font-french font-bold text-indigo-700">(aimer, adorer, préférer, détester)</span> نستخدم <strong>أدوات المعرفة</strong> <span class="font-french font-bold text-indigo-700">(le, la, l', les)</span> <u>وليس</u> أدوات التجزئة!
                </p>
                <p class="font-french text-[11px] font-bold text-indigo-800" dir="ltr">Ex: J'aime <strong>la</strong> viande, mais je mange <strong>de la</strong> viande.</p>
              </div>

              <div class="p-3.5 bg-rose-50 rounded-xl border border-rose-300 space-y-1">
                <p class="font-bold text-rose-950">🚫 2. قاعدة النفي مع التجزئة :</p>
                <p class="text-slate-700 leading-relaxed">
                  في النفي تتحول أدوات التجزئة (du, de la, de l', des) إلى <span class="font-french font-bold text-rose-700">« de »</span> أو <span class="font-french font-bold text-rose-700">« d' »</span> (ما عدا مع فعل être).
                </p>
                <p class="font-french text-[11px] font-bold text-rose-800" dir="ltr">Ex: Je mange <strong>du</strong> riz ➔ Je ne mange pas <strong>de</strong> riz.</p>
              </div>

              <div class="p-3.5 bg-indigo-50 rounded-xl border border-indigo-200 space-y-1">
                <p class="font-bold text-indigo-950">⚖️ 3. بعد ظروف الكمية (Adverbes de quantité) :</p>
                <p class="text-slate-700 leading-relaxed">
                  بعد كلمات الكمية مثل <span class="font-french font-bold text-indigo-700">(beaucoup de, un peu de, trop de, assez de, un kilo de)</span> نستخدم دائماً <strong>de أو d'</strong> فقط بدون أداة تجزئة!
                </p>
                <p class="font-french text-[11px] font-bold text-indigo-800" dir="ltr">Ex: Je mange beaucoup <strong>de</strong> fruits. (وليس des fruits)</p>
              </div>
            </div>

            <!-- Quiz Questions -->
            <div class="pt-2">
              <h4 class="text-xs font-bold text-slate-800 mb-2">تدريبات أدوات التجزئة المقررة :</h4>
              <div class="space-y-3">${renderQuizQuestionsList(c.partitiveQuestions)}</div>
            </div>
          </div>

          <!-- Interrogatives Rules & Explanations (Pages 46-50) -->
          <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base sm:text-lg font-black text-indigo-900 font-french">Les Mots Interrogatifs (أدوات الاستفهام - ص 46-50)</h3>
                <p class="text-xs text-indigo-700 font-bold">طرق تكوين السؤال والشرح التفصيلي لجميع أدوات الاستفهام وملاحظات Note Bien</p>
              </div>
              <span class="px-2.5 py-1 rounded-lg bg-indigo-100 text-indigo-800 text-xs font-bold font-french">Pages 46-50</span>
            </div>

            <!-- 3 Ways of Question Formation -->
            <div class="p-3.5 bg-indigo-50/70 rounded-2xl border border-indigo-200 text-xs space-y-2">
              <span class="font-bold text-indigo-950 block">طرق تكوين السؤال في اللغة الفرنسية (ص 46) :</span>
              <div class="grid grid-cols-1 md:grid-cols-3 gap-2 font-french text-[11px]">
                <div class="p-2.5 bg-white rounded-xl border border-indigo-100">
                  <p class="font-bold text-indigo-800">1. Intonation (نبرة الصوت)</p>
                  <p class="text-slate-600">Tu aimes le français ?</p>
                </div>
                <div class="p-2.5 bg-white rounded-xl border border-indigo-100">
                  <p class="font-bold text-indigo-800">2. Est-ce que (هل)</p>
                  <p class="text-slate-600">Est-ce que tu aimes le français ?</p>
                </div>
                <div class="p-2.5 bg-white rounded-xl border border-indigo-100">
                  <p class="font-bold text-indigo-800">3. Inversion (تقديم الفعل)</p>
                  <p class="text-slate-600">Aimes-tu le français ?</p>
                </div>
              </div>
            </div>

            <!-- Comprehensive 11 Interrogative Words Table -->
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2.5 text-xs">
              <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">Où</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">أين (تسأل عن المكان)</p>
                <p class="text-[11px] text-slate-600">Où vas-tu ? ➔ Je vais au club.</p>
              </div>

              <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">Quand</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">متى (تسأل عن الزمن غير المحدد)</p>
                <p class="text-[11px] text-slate-600">Quand vas-tu à Paris ? ➔ En été.</p>
              </div>

              <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">À quelle heure</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">في أي ساعة (لوقت محدد)</p>
                <p class="text-[11px] text-slate-600">À quelle heure commence le film ?</p>
              </div>

              <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">Comment</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">كيف (للحال، الاسم، المواصلات)</p>
                <p class="text-[11px] text-slate-600">Comment vas-tu ? / Comment tu t'appelles ?</p>
              </div>

              <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">Pourquoi</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">لماذا (للسبب: car, parce que, pour)</p>
                <p class="text-[11px] text-slate-600">Pourquoi vas-tu au restaurant ? ➔ Pour manger.</p>
              </div>

              <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">Combien de</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">كم عدد / كمية (يليها اسم جمع)</p>
                <p class="text-[11px] text-slate-600">Combien de repas prends-tu par jour ?</p>
              </div>

              <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-teal-600 text-white font-bold font-french text-[11px]">Qui</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">من (تسأل عن العاقل إنسان)</p>
                <p class="text-[11px] text-slate-600">Qui parle ? / Avec qui vas-tu ?</p>
              </div>

              <div class="p-3 rounded-xl bg-teal-50 border border-teal-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-teal-700 text-white font-bold font-french text-[11px]">Que / Qu'est-ce que</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">ماذا (لغير العاقل مفعول مباشر)</p>
                <p class="text-[11px] text-slate-600">Que voulez-vous comme dessert ?</p>
              </div>

              <div class="p-3 rounded-xl bg-purple-50 border border-purple-200 space-y-1" dir="ltr">
                <span class="px-2 py-0.5 rounded bg-purple-700 text-white font-bold font-french text-[11px]">Quel(le)(s)</span>
                <p class="font-bold text-slate-800 font-sans" dir="rtl">ما / أي (تطابق الاسم نوعاً وعدداً)</p>
                <p class="text-[11px] text-slate-600">Quel âge / Quelle heure / Quels plats</p>
              </div>
            </div>

            <!-- Note Bien Cards (Page 48) -->
            <div class="p-4 rounded-2xl bg-amber-400/15 border-2 border-amber-300 space-y-2 text-xs">
              <span class="px-2 py-0.5 rounded bg-amber-400 text-amber-950 font-black text-[10px] font-french">⭐ Note Bien (ملاحظات الامتحانات ص 48)</span>
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-2.5 font-french text-[11px] pt-1" dir="ltr">
                <div class="p-2.5 bg-white rounded-xl border border-amber-200">
                  <p class="font-bold text-indigo-900">• C'est + شخص عاقل ➔ <span class="text-rose-700">Qui est-ce ?</span></p>
                  <p class="text-slate-500 font-sans text-[10px]" dir="rtl">مثال: C'est Ahmed ➔ Qui est-ce ?</p>
                </div>
                <div class="p-2.5 bg-white rounded-xl border border-amber-200">
                  <p class="font-bold text-indigo-900">• C'est + شيء غير عاقل ➔ <span class="text-rose-700">Qu'est-ce que c'est ?</span></p>
                  <p class="text-slate-500 font-sans text-[10px]" dir="rtl">مثال: C'est un livre ➔ Qu'est-ce que c'est ?</p>
                </div>
              </div>
            </div>

            <!-- Interrogative Quiz Questions -->
            <div class="pt-2">
              <h4 class="text-xs font-bold text-slate-800 mb-2">تدريبات أدوات الاستفهام المقررة :</h4>
              <div class="space-y-3">${renderQuizQuestionsList(c.interrogativeQuestions)}</div>
            </div>
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
