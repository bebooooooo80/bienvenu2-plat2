# -*- coding: utf-8 -*-

def get_unit1_renderer_code():
    return """
    // 2. RENDER UNIT 1 SCREEN
    function renderUnit1Screen() {
      const c = DATA.course;
      const b = DATA.bookletLieux;
      const container = document.getElementById('screen-unit1');

      container.innerHTML = `
        <!-- Unit 1 Header -->
        <div class="rounded-3xl bg-gradient-to-r from-blue-700 via-indigo-700 to-blue-800 text-white p-5 sm:p-6 shadow-lg border border-blue-500/30">
          <div class="flex items-center justify-between gap-3 mb-2">
            <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold font-french">Unité 1 • Pages 11-34</span>
            <button onclick="speakText('Unité un : Une fête, l\\'invitation')" class="px-3 py-1 rounded-full bg-white/15 hover:bg-white/25 active:scale-95 text-xs font-bold font-french flex items-center gap-1.5 transition">
              <i class="fa-solid fa-volume-high"></i>
              <span>Écouter</span>
            </button>
          </div>
          <h2 class="text-xl sm:text-2xl font-black mb-1">Unité 1 : Une fête (حفلة عيد الميلاد والدعوة)</h2>
          <p class="text-xs sm:text-sm text-blue-100 font-medium">النص والوثائق، بنك الكلمات الكامل (86 كلمة)، المواقف (6)، القواعد والضمائر (36 تمريناً)، والإنتاج وتدريبات ص 26.</p>
        </div>

        <!-- Unit 1 Sub-tabs Navigation -->
        <div class="bg-white p-1.5 rounded-2xl border border-slate-200 shadow-sm flex overflow-x-auto custom-scrollbar gap-1">
          <button onclick="switchUnit1SubTab('texte')" id="u1-tab-btn-texte" class="u1-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition tab-active">
            <i class="fa-solid fa-file-lines ml-1"></i> النص والتمارين (6)
          </button>
          <button onclick="switchUnit1SubTab('vocab')" id="u1-tab-btn-vocab" class="u1-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-book ml-1"></i> بنك الكلمات (${c.unit1VocabWords.length + c.unit1BanqueDesMots.length})
          </button>
          <button onclick="switchUnit1SubTab('situations')" id="u1-tab-btn-situations" class="u1-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-comments ml-1"></i> المواقف (${c.unit1Situations.length})
          </button>
          <button onclick="switchUnit1SubTab('grammar')" id="u1-tab-btn-grammar" class="u1-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-ruler ml-1"></i> القواعد والضمائر
          </button>
          <button onclick="switchUnit1SubTab('composition')" id="u1-tab-btn-composition" class="u1-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-pen-nib ml-1"></i> التعبير والجمل
          </button>
          <button onclick="switchUnit1SubTab('exercices26')" id="u1-tab-btn-exercices26" class="u1-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-clipboard-check ml-1"></i> تمارين ص 26 والكراسة
          </button>
        </div>

        <!-- 1. TEXTE & EXERCISES -->
        <div id="u1-pane-texte" class="u1-pane space-y-4">
          <!-- Full Text Card -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <div class="flex items-center justify-between border-b border-slate-100 pb-2.5">
              <h3 class="text-sm sm:text-base font-bold text-slate-900 flex items-center gap-2">
                <i class="fa-solid fa-envelope-open-text text-blue-600"></i>
                <span>نص الوحدة الأولى الرسمي (Texte de l'unité 1)</span>
              </h3>
              <button onclick="speakText('${c.unit1FullText.replace(/['"\\n]/g, ' ')}')" class="px-3 py-1 rounded-xl bg-blue-50 text-blue-700 text-xs font-bold font-french flex items-center gap-1.5 hover:bg-blue-100 transition">
                <i class="fa-solid fa-volume-high"></i>
                <span>نطق النص كاملاً</span>
              </button>
            </div>
            
            <div class="bg-slate-50 p-4 rounded-xl text-xs sm:text-sm font-french leading-relaxed text-slate-800 border border-slate-200/80" dir="ltr">
              ${c.unit1FullText.replace(/\\n/g, '<br>')}
            </div>
            
            <div class="p-3 bg-amber-50/70 rounded-xl text-xs text-amber-900 border border-amber-200/70 leading-relaxed">
              <strong>ترجمة النص باللغة العربية :</strong><br>
              ${c.unit1FullTextArabic.replace(/\\n/g, '<br>')}
            </div>
          </div>

          <!-- Dialogues List -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900 flex items-center gap-2">
              <i class="fa-solid fa-comments text-indigo-600"></i>
              <span>محادثات وحوارات الوحدة (${c.unit1Dialogues.length} Dialogues)</span>
            </h3>
            <div class="space-y-2">
              ${c.unit1Dialogues.map(d => `
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200/80 flex items-start justify-between gap-3">
                  <div class="flex-1" dir="ltr">
                    <span class="font-bold text-indigo-700 font-french text-xs block mb-0.5">${d.speaker} :</span>
                    <p class="text-xs font-french text-slate-800">${d.text}</p>
                    ${d.arabicNote ? `<p class="text-[11px] text-slate-500 font-sans mt-1" dir="rtl">${d.arabicNote}</p>` : ''}
                  </div>
                  <button onclick="speakText('${d.speaker} : ${d.text.replace(/['"\\n]/g, ' ')}')" class="w-8 h-8 rounded-lg bg-indigo-50 text-indigo-600 hover:bg-indigo-100 flex items-center justify-center shrink-0">
                    <i class="fa-solid fa-volume-high text-xs"></i>
                  </button>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Ex2: Réponds aux questions -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900 flex items-center gap-2">
              <span class="w-6 h-6 rounded-md bg-blue-100 text-blue-700 text-xs font-bold flex items-center justify-center">2</span>
              <span>Exercice 2 : Réponds aux questions (أجب عن الأسئلة)</span>
            </h3>
            <div class="space-y-3">
              ${renderQuizQuestionsList(c.unit1Ex2RepondsAuxQuestions)}
            </div>
          </div>

          <!-- Ex3: Vrai ou Faux -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900 flex items-center gap-2">
              <span class="w-6 h-6 rounded-md bg-blue-100 text-blue-700 text-xs font-bold flex items-center justify-center">3</span>
              <span>Exercice 3 : Mets (Vrai) ou (Faux) (صح أم خطأ)</span>
            </h3>
            <div class="space-y-3">
              ${renderQuizQuestionsList(c.unit1Ex3VraiOuFaux)}
            </div>
          </div>

          <!-- Ex4: Associe -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900 flex items-center gap-2">
              <span class="w-6 h-6 rounded-md bg-blue-100 text-blue-700 text-xs font-bold flex items-center justify-center">4</span>
              <span>Exercice 4 : Associe les colonnes (صل العمود أ بالعمود ب)</span>
            </h3>
            <div class="space-y-3">
              ${renderQuizQuestionsList(c.unit1Ex4Associe)}
            </div>
          </div>

          <!-- Ex5: Complète -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900 flex items-center gap-2">
              <span class="w-6 h-6 rounded-md bg-blue-100 text-blue-700 text-xs font-bold flex items-center justify-center">5</span>
              <span>Exercice 5 : Complète avec les mots du texte (أكمل من النص)</span>
            </h3>
            <div class="space-y-3">
              ${renderQuizQuestionsList(c.unit1Ex5Complete)}
            </div>
          </div>
        </div>

        <!-- 2. VOCABULAIRE & BANQUE DE MOTS -->
        <div id="u1-pane-vocab" class="u1-pane hidden space-y-4">
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-4">
            <div class="flex flex-wrap items-center justify-between gap-3 border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base font-bold text-slate-900">بنك الكلمات الصوتي الشامل (${c.unit1VocabWords.length + c.unit1BanqueDesMots.length} كلمة)</h3>
                <p class="text-xs text-slate-500 font-french">Vocabulaire & Banque de mots • p. 13-14</p>
              </div>
              <input type="text" id="u1VocabSearch" oninput="filterUnit1Vocab()" placeholder="🔍 ابحث عن كلمة فرنسية أو عربية..." class="px-3.5 py-1.5 text-xs rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full sm:w-64">
            </div>

            <!-- Category Pills -->
            <div class="flex flex-wrap gap-1.5" id="u1VocabCategoryPills">
              <button onclick="setU1VocabFilter('ALL')" class="u1-vcat-btn px-3 py-1 rounded-lg text-xs font-bold bg-blue-600 text-white">الكل</button>
              <button onclick="setU1VocabFilter('MASCULINE')" class="u1-vcat-btn px-3 py-1 rounded-lg text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">مذكر ♂</button>
              <button onclick="setU1VocabFilter('FEMININE')" class="u1-vcat-btn px-3 py-1 rounded-lg text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">مؤنث ♀</button>
              <button onclick="setU1VocabFilter('VERB')" class="u1-vcat-btn px-3 py-1 rounded-lg text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">أفعال ⚡</button>
              <button onclick="setU1VocabFilter('EXPRESSION')" class="u1-vcat-btn px-3 py-1 rounded-lg text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">تعبيرات 💬</button>
              <button onclick="setU1VocabFilter('PERSONNAGE')" class="u1-vcat-btn px-3 py-1 rounded-lg text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">شخصيات 👥</button>
              <button onclick="setU1VocabFilter('LIEU')" class="u1-vcat-btn px-3 py-1 rounded-lg text-xs font-bold bg-slate-100 text-slate-700 hover:bg-slate-200">أماكن 📍</button>
            </div>

            <!-- Words Grid -->
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2.5 pt-2" id="u1VocabGrid">
              ${renderVocabWordsList([...c.unit1VocabWords, ...c.unit1BanqueDesMots])}
            </div>
          </div>
        </div>

        <!-- 3. SITUATIONS -->
        <div id="u1-pane-situations" class="u1-pane hidden space-y-4">
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-4">
            <div class="border-b border-slate-100 pb-3">
              <h3 class="text-base font-bold text-slate-900">مواقف الوحدة الأولى الرسمية مع القاعدة الذهبية (Situations p. 15)</h3>
              <p class="text-xs text-slate-500">6 مواقف تفاعلية مع التمثيل الصوتي والشرح التوضيحي</p>
            </div>

            <div class="space-y-4">
              ${c.unit1Situations.map(s => `
                <div class="quiz-card p-4 rounded-2xl border border-slate-200 bg-slate-50/60 space-y-3">
                  <div class="flex items-center justify-between">
                    <span class="px-2.5 py-0.5 rounded-full bg-blue-100 text-blue-800 text-[11px] font-bold font-french">Situation ${s.number} • ${s.categoryFr}</span>
                    <span class="text-xs text-slate-500 font-bold">${s.categoryAr}</span>
                  </div>

                  <!-- Prompt -->
                  <div class="bg-white p-3 rounded-xl border border-slate-200/80">
                    <div class="flex items-center justify-between">
                      <p class="text-xs sm:text-sm font-bold text-slate-900 font-french" dir="ltr">${s.promptFr}</p>
                      <button onclick="speakText('${s.promptFr.replace(/['"]/g, ' ')}')" class="w-7 h-7 rounded-lg bg-blue-50 text-blue-600 flex items-center justify-center shrink-0">
                        <i class="fa-solid fa-volume-high text-xs"></i>
                      </button>
                    </div>
                    <p class="text-xs text-slate-500 mt-1 font-semibold">${s.promptAr}</p>
                  </div>

                  <!-- Role Play Context -->
                  ${s.rolePlayMessage ? `
                    <div class="p-2.5 rounded-xl bg-indigo-50/80 border border-indigo-100 text-xs flex items-center gap-2">
                      <i class="fa-solid fa-user-tie text-indigo-600"></i>
                      <span class="font-bold text-indigo-900">${s.rolePlaySpeaker} :</span>
                      <span class="font-french text-indigo-800" dir="ltr">« ${s.rolePlayMessage} »</span>
                    </div>
                  ` : ''}

                  <!-- Options -->
                  <div class="space-y-1.5" dir="ltr">
                    ${s.options.map(opt => `
                      <button onclick="checkQuizOption(this, ${opt.isCorrect}, '${(opt.explanation || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn w-full p-2.5 rounded-xl border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:bg-slate-50 transition flex items-center justify-between">
                        <span>${opt.textFr}</span>
                        <span class="text-[11px] text-slate-500 font-sans" dir="rtl">${opt.textAr}</span>
                      </button>
                    `).join('')}
                  </div>

                  <div class="quiz-feedback hidden text-xs font-bold mt-2"></div>

                  <!-- Golden Rule -->
                  <div class="p-2.5 rounded-xl bg-amber-50 border border-amber-200/80 text-[11px] text-amber-900">
                    <i class="fa-solid fa-lightbulb text-amber-600 ml-1"></i>
                    <strong>القاعدة الذهبية : </strong> ${s.goldenRule}
                  </div>
                </div>
              `).join('')}
            </div>
          </div>
        </div>

        <!-- 4. GRAMMAIRE & PRONOMS -->
        <div id="u1-pane-grammar" class="u1-pane hidden space-y-4">
          <!-- Possessive Table -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <div class="flex items-center justify-between border-b border-slate-100 pb-2">
              <h3 class="text-sm sm:text-base font-bold text-slate-900">1. جدول صفات الملكية (Les Adjectifs Possessifs - p. 17)</h3>
              <span class="text-xs text-indigo-600 font-bold font-french">mon, ton, son...</span>
            </div>
            <div class="overflow-x-auto custom-scrollbar">
              <table class="w-full text-xs text-center border-collapse">
                <thead>
                  <tr class="bg-indigo-50 text-indigo-900 font-bold">
                    <th class="p-2 border border-slate-200">الفاعل (Sujet)</th>
                    <th class="p-2 border border-slate-200">مذكر مفرد (Masc.)</th>
                    <th class="p-2 border border-slate-200">مؤنث مفرد (Fém.)</th>
                    <th class="p-2 border border-slate-200">الجمع بنوعيه (Pluriel)</th>
                    <th class="p-2 border border-slate-200">مثال توضيحي</th>
                  </tr>
                </thead>
                <tbody class="font-french">
                  ${(c.unit1PossessiveTable || []).map(row => `
                    <tr class="hover:bg-slate-50">
                      <td class="p-2 border border-slate-200 font-bold text-indigo-700">${row.subject} (${row.arabicSubject})</td>
                      <td class="p-2 border border-slate-200 font-bold text-emerald-700">${row.mascSingular}</td>
                      <td class="p-2 border border-slate-200 font-bold text-purple-700">${row.femSingular}</td>
                      <td class="p-2 border border-slate-200 font-bold text-amber-700">${row.plural}</td>
                      <td class="p-2 border border-slate-200 text-left text-[11px]">${row.exampleFr}</td>
                    </tr>
                  `).join('')}
                </tbody>
              </table>
            </div>

            <!-- Golden Vowel Rule Callout -->
            <div class="p-3.5 bg-amber-50 rounded-xl border-2 border-amber-300 space-y-1.5 text-xs">
              <div class="flex items-center gap-2">
                <span class="px-2 py-0.5 rounded-full bg-amber-400 text-amber-950 text-[10px] font-black font-french">⭐ Note Bien</span>
                <span class="font-bold text-amber-900">القاعدة الذهبية الأهم في صفات الملكية (ص 17) :</span>
              </div>
              <p class="text-slate-800 leading-relaxed">
                إذا كان الاسم <strong>مفرداً مؤنثاً</strong> مبدوءاً بـ <strong>حرف متحرك</strong> (a, e, i, o, u, y) أو h صامتة، نستخدم صفات المذكر <span class="font-french font-bold text-indigo-700">(mon, ton, son)</span> بدلاً من <span class="font-french font-bold text-rose-700">(ma, ta, sa)</span> منعاً لالتقاء حرفين متحركين!
              </p>
              <div class="flex flex-wrap gap-2 pt-1 font-french text-[11px] font-bold text-indigo-900" dir="ltr">
                <span class="px-2.5 py-1 rounded-lg bg-white border border-amber-200">mon amie <span class="text-slate-400 font-sans font-normal" dir="rtl">(صديقتي)</span></span>
                <span class="px-2.5 py-1 rounded-lg bg-white border border-amber-200">ton école <span class="text-slate-400 font-sans font-normal" dir="rtl">(مدرستك)</span></span>
                <span class="px-2.5 py-1 rounded-lg bg-white border border-amber-200">son adresse <span class="text-slate-400 font-sans font-normal" dir="rtl">(عنوانه/عنوانها)</span></span>
                <span class="px-2.5 py-1 rounded-lg bg-white border border-amber-200">son idée <span class="text-slate-400 font-sans font-normal" dir="rtl">(فكرته/فكرتها)</span></span>
              </div>
            </div>

            <!-- Possessive Exercises -->
            <div class="pt-3">
              <h4 class="text-xs font-bold text-slate-800 mb-2">تدريبات صفات الملكية (${c.unit1PossessiveExercises.length} تمارين) :</h4>
              <div class="space-y-2">
                ${c.unit1PossessiveExercises.map(ex => `
                  <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs">
                    <p class="font-bold text-slate-900 font-french" dir="ltr">${ex.number}. ${ex.fullSentenceFr}</p>
                    <p class="text-slate-500 mt-0.5">${ex.translationAr}</p>
                    <p class="text-indigo-600 font-semibold mt-1">💡 الشرح: ${ex.explanationAr}</p>
                  </div>
                `).join('')}
              </div>
            </div>
          </div>

          <!-- Pronom ON -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm sm:text-base font-bold text-slate-900">2. قاعدة الضمير (Le pronom 'On' - p. 19)</h3>
            <div class="p-3 bg-indigo-50 border border-indigo-100 rounded-xl text-xs space-y-1">
              <p><strong>قاعدة الضمير ON :</strong> يعامل معاملة (Nous) في المعنى، ويصرف معه الفعل كضمير (Il / Elle) دائماً.</p>
            </div>
            
            <h4 class="text-xs font-bold text-slate-800 pt-2">تمارين اختر الفعل المناسب مع ON :</h4>
            <div class="space-y-2">
              ${c.unit1PronomOnChoisisExercises.map(ex => `
                <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs">
                  <p class="font-bold text-slate-900 font-french" dir="ltr">${ex.number}. ${ex.fullSentenceFr}</p>
                  <p class="text-slate-500 mt-0.5">${ex.translationAr}</p>
                  <p class="text-emerald-600 font-semibold mt-1">✓ الإجابة: ${ex.correctAnswer} - ${ex.explanationAr}</p>
                </div>
              `).join('')}
            </div>

            <h4 class="text-xs font-bold text-slate-800 pt-2">تمارين استبدال Nous بـ On :</h4>
            <div class="space-y-2">
              ${c.unit1PronomOnReplaceExercises.map(ex => `
                <div class="p-3 rounded-xl border border-slate-200 bg-white text-xs">
                  <p class="font-bold text-slate-700 font-french" dir="ltr">Nous : ${ex.nousSentenceFr}</p>
                  <p class="font-bold text-indigo-700 font-french mt-0.5" dir="ltr">➔ On : ${ex.onSentenceFr}</p>
                  <p class="text-slate-500 text-[11px] mt-0.5">${ex.onSentenceAr}</p>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Booklet Pronouns (Official 15 Exercises & Full Explanations) -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-2">
              <div>
                <h3 class="text-sm sm:text-base font-bold text-slate-900">3. ضمائر المفعول به المباشر وغير المباشر (ص 20-23)</h3>
                <p class="text-xs text-indigo-700 font-semibold">الشرح الشامل لقواعد C.O.D و C.O.I وتمرين الـ 15 جملة المعتمد</p>
              </div>
              <span class="px-2 py-0.5 rounded bg-teal-100 text-teal-800 text-[11px] font-bold font-french">C.O.D & C.O.I</span>
            </div>

            <!-- Detailed Grammar Explanation Cards -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-3 text-xs">
              <!-- COD Explanation -->
              <div class="p-4 rounded-2xl bg-indigo-50/70 border border-indigo-200 space-y-2">
                <div class="flex items-center justify-between">
                  <span class="font-black text-indigo-900 font-french">🎯 1. المفعول المباشر (C.O.D) - ص 21</span>
                  <span class="px-2 py-0.5 rounded bg-indigo-200 text-indigo-800 text-[10px] font-bold">بدون حرف جر</span>
                </div>
                <p class="text-slate-700 leading-relaxed">
                  يحل محل مفعول به <u>غير مسبوق بحرف جر</u> (اسم مسبوق بأداة معرفة، نكرة، إشارة، أو صفة ملكية) :
                </p>
                <div class="grid grid-cols-2 gap-1.5 font-french text-[11px] font-bold" dir="ltr">
                  <div class="p-2 bg-white rounded-lg border border-indigo-100 text-indigo-800">le <span class="font-sans font-normal text-[10px] text-slate-500" dir="rtl">(مذكر مفرد)</span></div>
                  <div class="p-2 bg-white rounded-lg border border-indigo-100 text-purple-800">la <span class="font-sans font-normal text-[10px] text-slate-500" dir="rtl">(مؤنث مفرد)</span></div>
                  <div class="p-2 bg-white rounded-lg border border-indigo-100 text-emerald-800">l' <span class="font-sans font-normal text-[10px] text-slate-500" dir="rtl">(أمام متحرك)</span></div>
                  <div class="p-2 bg-white rounded-lg border border-indigo-100 text-amber-800">les <span class="font-sans font-normal text-[10px] text-slate-500" dir="rtl">(جمع بنوعيه)</span></div>
                </div>
                <div class="p-2.5 bg-white rounded-xl border border-indigo-100 text-[11px] text-slate-700 space-y-1" dir="ltr">
                  <p class="font-bold text-indigo-900 font-sans" dir="rtl">📌 مكان الضمير :</p>
                  <p>• قبل الفعل : Je regarde le match ➔ Je <strong>le</strong> regarde.</p>
                  <p>• في النفي : Je <strong>ne le</strong> regarde <strong>pas</strong>.</p>
                  <p>• مع فعلين (مصرف + مصدر) : يوضع قبل المصدر : Je vais <strong>le</strong> regarder.</p>
                </div>
              </div>

              <!-- COI Explanation -->
              <div class="p-4 rounded-2xl bg-teal-50/70 border border-teal-200 space-y-2">
                <div class="flex items-center justify-between">
                  <span class="font-black text-teal-900 font-french">🤝 2. المفعول غير المباشر (C.O.I) - ص 22</span>
                  <span class="px-2 py-0.5 rounded bg-teal-200 text-teal-800 text-[10px] font-bold">مسبوق بحرف جر à</span>
                </div>
                <p class="text-slate-700 leading-relaxed">
                  يحل محل مفعول به <u>عاقل (إنسان أو حيوان)</u> مسبوق بحرف الجر <span class="font-french font-bold text-teal-800">(à / au / à la / à l' / aux)</span> :
                </p>
                <div class="grid grid-cols-2 gap-1.5 font-french text-[11px] font-bold" dir="ltr">
                  <div class="p-2 bg-white rounded-lg border border-teal-100 text-teal-800">lui <span class="font-sans font-normal text-[10px] text-slate-500" dir="rtl">(مفرد مذكر أو مؤنث)</span></div>
                  <div class="p-2 bg-white rounded-lg border border-teal-100 text-amber-800">leur <span class="font-sans font-normal text-[10px] text-slate-500" dir="rtl">(جمع مذكر أو مؤنث)</span></div>
                </div>
                <div class="p-2.5 bg-white rounded-xl border border-teal-100 text-[11px] text-slate-700 space-y-1" dir="ltr">
                  <p class="font-bold text-teal-900 font-sans" dir="rtl">📌 أمثلة هامة :</p>
                  <p>• Je parle <u>à Ali</u> ➔ Je <strong>lui</strong> parle.</p>
                  <p>• Je téléphone <u>à Mona</u> ➔ Je <strong>lui</strong> téléphone.</p>
                  <p>• J'écris <u>à mes parents</u> ➔ Je <strong>leur</strong> écris.</p>
                </div>
              </div>
            </div>

            <!-- 15 Official Exercises -->
            <div class="space-y-3 pt-2">
              <h4 class="text-xs font-bold text-slate-800">التدريب الرسمي المعتمد (15 جملة ص 22-23) :</h4>
              ${c.bookletPronounsOfficial15Exercises.map(ex => `
                <div class="p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                  <div class="flex items-center justify-between">
                    <span class="px-2 py-0.5 rounded bg-indigo-100 text-indigo-800 text-[10px] font-bold font-french">${ex.category} • ${ex.ruleTag}</span>
                    <span class="text-[11px] font-bold text-emerald-700 font-french">Target: ${ex.targetPronoun}</span>
                  </div>
                  <p class="font-bold text-slate-900 font-french" dir="ltr">${ex.number}. ${ex.fullSentenceFr}</p>
                  <p class="text-slate-500">${ex.sentenceAr}</p>
                  <p class="font-bold text-teal-800 font-french" dir="ltr">➔ ${ex.transformedSentenceFr}</p>
                  <div class="p-2 bg-white rounded-lg border border-slate-200 text-[11px] text-slate-600">
                    💡 ${ex.explanationAr} (${ex.positionRuleAr})
                  </div>
                </div>
              `).join('')}
            </div>
          </div>
        </div>

        <!-- 5. COMPOSITION & FAIS DES PHRASES -->
        <div id="u1-pane-composition" class="u1-pane hidden space-y-4">
          <!-- Composition Topic -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900 flex items-center gap-2">
              <i class="fa-solid fa-pen-nib text-blue-600"></i>
              <span>موضوع التعبير الكتابي (Sujet de composition - p. 16)</span>
            </h3>
            
            <div class="p-3.5 bg-blue-50 border border-blue-200 rounded-xl space-y-2">
              <h4 class="font-bold text-blue-900 text-xs font-french" dir="ltr">${c.unit1CompositionTopic.titleFr} (${c.unit1CompositionTopic.titleAr})</h4>
              <p class="text-xs text-slate-700 leading-relaxed">${c.unit1CompositionTopic.instructionAr}</p>
              
              <!-- Sentences -->
              <div class="bg-white p-3 rounded-lg border border-blue-100 space-y-1 text-xs font-french" dir="ltr">
                ${c.unit1CompositionTopic.sentences.map(s => `
                  <p><span class="font-bold text-indigo-700">${s.order}.</span> ${s.french} <span class="text-slate-400 font-sans" dir="rtl">(${s.arabic})</span></p>
                `).join('')}
              </div>
            </div>
          </div>

          <!-- Fais des phrases list -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">تكوين الجمل النموذجية (Fais des phrases avec - p. 24)</h3>
            <div class="space-y-3">
              ${c.unit1FaisDesPhrasesList.map(item => `
                <div class="p-3.5 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1.5">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-indigo-700 font-french">${item.number}. [ ${item.promptFr} ]</span>
                    <span class="text-slate-500 font-bold">${item.promptAr}</span>
                  </div>
                  <p class="font-bold text-slate-900 font-french" dir="ltr">➔ ${item.modelSentenceFr}</p>
                  <p class="text-slate-500">${item.modelSentenceAr}</p>
                  <p class="text-indigo-600 text-[11px]">💡 ملحوظة: ${item.grammarTipAr}</p>
                </div>
              `).join('')}
            </div>
          </div>
        </div>

        <!-- 6. EXERCICES PAGE 26 & BOOKLET -->
        <div id="u1-pane-exercices26" class="u1-pane hidden space-y-4">
          <!-- Où vas-tu -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">1. أين تذهب لكي...؟ (Où vas-tu pour...? - p. 26)</h3>
            <div class="space-y-2">
              ${b.ouVasTuItems.map(item => `
                <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
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

          <!-- Qui parle -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">2. من المتحدث؟ (Qui parle? - p. 26)</h3>
            <div class="space-y-2">
              ${b.quiParleItems.map(item => `
                <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-indigo-900 font-french" dir="ltr">${item.number}. ${item.quoteFr}</span>
                    <span class="text-slate-500">${item.quoteAr}</span>
                  </div>
                  <p class="font-bold text-blue-700 font-french" dir="ltr">➔ Qui parle : ${item.speakerFr} (${item.speakerAr})</p>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Qui fait ce travail -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">3. من يقوم بهذا العمل؟ (Qui fait ce travail? - p. 26)</h3>
            <div class="space-y-2">
              ${b.quiFaitCeTravailItems.map(item => `
                <div class="p-3 rounded-xl border border-slate-200 bg-slate-50 text-xs space-y-1">
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-slate-900 font-french" dir="ltr">${item.number}. ${item.actionFr}</span>
                    <span class="text-slate-500">${item.actionAr}</span>
                  </div>
                  <p class="font-bold text-purple-700 font-french" dir="ltr">➔ Profession : ${item.professionFr} (${item.professionAr})</p>
                </div>
              `).join('')}
            </div>
          </div>
        </div>
      `;
    }

    // Helper: render quiz question list
    function renderQuizQuestionsList(questions) {
      if (!questions || !questions.length) return '';
      return questions.map(q => `
        <div class="quiz-card p-3.5 rounded-xl border border-slate-200 bg-slate-50/70 space-y-2">
          <p class="text-xs font-bold text-slate-900 font-french" dir="ltr">${q.question || q.questionFr || ''}</p>
          ${q.arabicTranslation || q.questionAr ? `<p class="text-[11px] text-slate-500">${q.arabicTranslation || q.questionAr}</p>` : ''}
          <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-1.5" dir="ltr">
            ${(q.options || []).map((opt, oIdx) => `
              <button onclick="checkQuizOption(this, ${oIdx === q.correctIndex}, '${(q.explanation || q.explanationAr || '').replace(/['"]/g, ' ')}')" class="quiz-opt-btn p-2 rounded-lg border border-slate-200 bg-white text-xs font-french font-semibold text-slate-800 text-left hover:border-indigo-300 transition">
                ${opt}
              </button>
            `).join('')}
          </div>
          <div class="quiz-feedback hidden text-xs font-bold mt-1.5"></div>
        </div>
      `).join('');
    }

    // Helper: render vocab words list
    function renderVocabWordsList(words) {
      if (!words || !words.length) return '';
      return words.map(w => `
        <div class="vocab-item p-3 rounded-xl bg-slate-50 hover:bg-blue-50/50 border border-slate-200/80 hover:border-blue-300 transition flex items-center justify-between gap-2" data-cat="${w.category || 'ALL'}" data-fr="${(w.french || '').toLowerCase()}" data-ar="${w.arabic || ''}">
          <div class="overflow-hidden">
            <span class="block text-xs font-bold text-slate-900 font-french truncate">${w.french}</span>
            <span class="text-[11px] text-slate-500 font-medium block truncate">${w.arabic}</span>
            ${w.phoneticOrNote ? `<span class="text-[10px] text-indigo-500 block truncate font-french">${w.phoneticOrNote}</span>` : ''}
          </div>
          <button onclick="speakText('${(w.french || '').replace(/['"]/g, ' ')}')" class="w-8 h-8 rounded-lg bg-blue-100/60 hover:bg-blue-200 text-blue-700 flex items-center justify-center shrink-0">
            <i class="fa-solid fa-volume-high text-xs"></i>
          </button>
        </div>
      `).join('');
    }

    function switchUnit1SubTab(tabId) {
      playClickSound();
      document.querySelectorAll('.u1-pane').forEach(el => el.classList.add('hidden'));
      document.querySelectorAll('.u1-nav-btn').forEach(el => el.classList.remove('tab-active'));
      const p = document.getElementById('u1-pane-' + tabId);
      const b = document.getElementById('u1-tab-btn-' + tabId);
      if (p) p.classList.remove('hidden');
      if (b) b.classList.add('tab-active');
    }

    let activeU1Cat = 'ALL';
    function setU1VocabFilter(cat) {
      activeU1Cat = cat;
      document.querySelectorAll('.u1-vcat-btn').forEach(btn => {
        btn.classList.remove('bg-blue-600', 'text-white');
        btn.classList.add('bg-slate-100', 'text-slate-700');
      });
      event.target.classList.add('bg-blue-600', 'text-white');
      event.target.classList.remove('bg-slate-100', 'text-slate-700');
      filterUnit1Vocab();
    }

    function filterUnit1Vocab() {
      const q = (document.getElementById('u1VocabSearch').value || '').trim().toLowerCase();
      document.querySelectorAll('.vocab-item').forEach(item => {
        const cat = item.getAttribute('data-cat');
        const fr = item.getAttribute('data-fr');
        const ar = item.getAttribute('data-ar');
        const matchCat = (activeU1Cat === 'ALL' || cat === activeU1Cat);
        const matchSearch = (!q || fr.includes(q) || ar.includes(q));
        item.style.display = (matchCat && matchSearch) ? 'flex' : 'none';
      });
    }
"""
