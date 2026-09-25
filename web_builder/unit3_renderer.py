# -*- coding: utf-8 -*-

def get_unit3_renderer_code():
    return """
    // 4. RENDER UNIT 3 SCREEN
    function renderUnit3Screen() {
      const c = DATA.course;
      const container = document.getElementById('screen-unit3');

      container.innerHTML = `
        <!-- Unit 3 Header -->
        <div class="rounded-3xl bg-gradient-to-r from-emerald-600 via-teal-600 to-emerald-700 text-white p-5 sm:p-6 shadow-lg border border-emerald-500/30">
          <div class="flex items-center justify-between gap-3 mb-2">
            <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold font-french">Unité 3 • Pages 59-67</span>
            <button onclick="speakText('Unité trois : La santé, chez le médecin')" class="px-3 py-1 rounded-full bg-white/15 hover:bg-white/25 active:scale-95 text-xs font-bold font-french flex items-center gap-1.5 transition">
              <i class="fa-solid fa-volume-high"></i>
              <span>Écouter</span>
            </button>
          </div>
          <h2 class="text-xl sm:text-2xl font-black mb-1">Unité 3 : La santé (الصحة وزيارة الطبيب)</h2>
          <p class="text-xs sm:text-sm text-emerald-100 font-medium">نصوص ص 59 وص 60، محادثات العيادة (10)، أجزاء الجسم والتعبير عن الألم، والمواقف الطبية.</p>
        </div>

        <!-- Unit 3 Sub-tabs -->
        <div class="bg-white p-1.5 rounded-2xl border border-slate-200 shadow-sm flex overflow-x-auto custom-scrollbar gap-1">
          <button onclick="switchUnit3SubTab('texte')" id="u3-tab-btn-texte" class="u3-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition tab-active">
            <i class="fa-solid fa-stethoscope ml-1"></i> النصوص والمحادثات
          </button>
          <button onclick="switchUnit3SubTab('corps')" id="u3-tab-btn-corps" class="u3-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-child ml-1"></i> أجزاء الجسم والألم (5)
          </button>
          <button onclick="switchUnit3SubTab('situations')" id="u3-tab-btn-situations" class="u3-nav-btn flex-1 py-2 px-3 rounded-xl text-xs font-bold whitespace-nowrap transition text-slate-600 hover:bg-slate-100">
            <i class="fa-solid fa-comments ml-1"></i> المواقف الطبية (3)
          </button>
        </div>

        <!-- 1. TEXTE & DIALOGUES -->
        <div id="u3-pane-texte" class="u3-pane space-y-4">
          <!-- Main Full Text -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <div class="flex items-center justify-between border-b border-slate-100 pb-2">
              <h3 class="text-base font-bold text-slate-900">نص الوحدة الثالثة الرسمي</h3>
              <button onclick="speakText('${c.unit3FullText.replace(/['"\\n]/g, ' ')}')" class="px-3 py-1 rounded-xl bg-emerald-50 text-emerald-700 text-xs font-bold font-french flex items-center gap-1 hover:bg-emerald-100 transition">
                <i class="fa-solid fa-volume-high"></i>
                <span>استمع</span>
              </button>
            </div>
            <div class="bg-slate-50 p-4 rounded-xl text-xs sm:text-sm font-french leading-relaxed text-slate-800" dir="ltr">
              ${c.unit3FullText.replace(/\\n/g, '<br>')}
            </div>
            <div class="p-3 bg-emerald-50/60 rounded-xl text-xs text-emerald-900 border border-emerald-200/60 leading-relaxed">
              <strong>ترجمة النص باللغة العربية :</strong><br>
              ${c.unit3FullTextArabic.replace(/\\n/g, '<br>')}
            </div>
          </div>

          <!-- Page 59 Text -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-2">
            <span class="px-2.5 py-0.5 rounded-full bg-emerald-100 text-emerald-800 text-[11px] font-bold font-french">Page 59</span>
            <div class="bg-slate-50 p-3.5 rounded-xl text-xs font-french text-slate-800 leading-relaxed" dir="ltr">
              ${c.unit3Page59Text.replace(/\\n/g, '<br>')}
            </div>
            <p class="text-xs text-slate-600 leading-relaxed">${c.unit3Page59TextArabic}</p>
          </div>

          <!-- Page 60 Text -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-2">
            <span class="px-2.5 py-0.5 rounded-full bg-emerald-100 text-emerald-800 text-[11px] font-bold font-french">Page 60</span>
            <div class="bg-slate-50 p-3.5 rounded-xl text-xs font-french text-slate-800 leading-relaxed" dir="ltr">
              ${c.unit3Page60Text.replace(/\\n/g, '<br>')}
            </div>
            <p class="text-xs text-slate-600 leading-relaxed">${c.unit3Page60TextArabic}</p>
          </div>

          <!-- Dialogues (10 Dialogues) -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">محادثات العيادة وزيارة الطبيب (${c.unit3Dialogues.length} مقاطع)</h3>
            <div class="space-y-2">
              ${c.unit3Dialogues.map(d => `
                <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 flex items-start justify-between gap-3">
                  <div class="flex-1" dir="ltr">
                    <span class="font-bold text-emerald-700 font-french text-xs block mb-0.5">${d.speaker} :</span>
                    <p class="text-xs font-french text-slate-800">${d.text}</p>
                    ${d.arabicNote ? `<p class="text-[11px] text-slate-500 font-sans mt-1" dir="rtl">${d.arabicNote}</p>` : ''}
                  </div>
                  <button onclick="speakText('${d.speaker} : ${d.text.replace(/['"\\n]/g, ' ')}')" class="w-8 h-8 rounded-lg bg-emerald-50 text-emerald-600 hover:bg-emerald-100 flex items-center justify-center shrink-0">
                    <i class="fa-solid fa-volume-high text-xs"></i>
                  </button>
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Ex1: Vrai ou Faux -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">1. Mets (Vrai) ou (Faux) (صح أم خطأ)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.unit3Ex1VraiOuFaux)}</div>
          </div>

          <!-- Ex2: Questions-Réponses -->
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-sm font-bold text-slate-900">2. Questions et Réponses (أجب عن الأسئلة)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.unit3Ex2QuestionsReponses)}</div>
          </div>
        </div>

        <!-- 2. CORPS HUMAIN & DOULEUR (Pages 64-66) -->
        <div id="u3-pane-corps" class="u3-pane hidden space-y-4">
          <!-- Comprehensive Grammar Explanation Header -->
          <div class="bg-white rounded-3xl p-5 sm:p-6 border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center justify-between border-b border-slate-100 pb-3">
              <div>
                <h3 class="text-base sm:text-lg font-black text-emerald-800 font-french">Le Corps & L'expression de la douleur (ص 64-66)</h3>
                <p class="text-xs text-emerald-700 font-bold">أعضاء جسم الإنسان وقاعدة التعبير عن الألم (Avoir mal à + أداة مدغمة)</p>
              </div>
              <span class="px-2.5 py-1 rounded-lg bg-emerald-100 text-emerald-800 text-xs font-bold font-french">Pages 64-66</span>
            </div>

            <!-- Verb Avoir Conjugation -->
            <div class="p-3.5 bg-emerald-50/70 rounded-2xl border border-emerald-200 space-y-2 text-xs">
              <div class="flex items-center justify-between">
                <span class="font-bold text-emerald-950 font-french">1. تصريف فعل يملك/يشعر (Avoir au présent) :</span>
                <button onclick="speakText('J ai, tu as, il a, nous avons, vous avez, ils ont')" class="text-emerald-700 hover:text-emerald-900"><i class="fa-solid fa-volume-high"></i></button>
              </div>
              <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-6 gap-1.5 font-french text-[11px] font-bold text-center" dir="ltr">
                <div class="p-2 bg-white rounded-xl border border-emerald-100">J'ai <span class="font-sans text-[10px] text-slate-400 font-normal block" dir="rtl">أنا أشعر</span></div>
                <div class="p-2 bg-white rounded-xl border border-emerald-100">Tu as <span class="font-sans text-[10px] text-slate-400 font-normal block" dir="rtl">أنت تشعر</span></div>
                <div class="p-2 bg-white rounded-xl border border-emerald-100">Il / Elle a <span class="font-sans text-[10px] text-slate-400 font-normal block" dir="rtl">هو/هي يشعر</span></div>
                <div class="p-2 bg-white rounded-xl border border-emerald-100">Nous avons <span class="font-sans text-[10px] text-slate-400 font-normal block" dir="rtl">نحن نشعر</span></div>
                <div class="p-2 bg-white rounded-xl border border-emerald-100">Vous avez <span class="font-sans text-[10px] text-slate-400 font-normal block" dir="rtl">أنتم تشعرون</span></div>
                <div class="p-2 bg-white rounded-xl border border-emerald-100">Ils / Elles ont <span class="font-sans text-[10px] text-slate-400 font-normal block" dir="rtl">هم يشعرون</span></div>
              </div>
            </div>

            <!-- Avoir Mal à Rule Grid -->
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-2.5 text-xs">
              <div class="p-3.5 rounded-2xl bg-indigo-50/70 border border-indigo-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-indigo-600 text-white font-bold font-french text-[11px]">au</span>
                <p class="font-bold text-slate-800">مفرد مذكر ساكن (à + le = au)</p>
                <p class="text-[11px] font-french text-indigo-800 font-semibold" dir="ltr">au bras (الذراع), au ventre (البطن), au dos (الظهر), au pied (القدم), au genou (الركبة), au nez (الأنف)</p>
              </div>

              <div class="p-3.5 rounded-2xl bg-purple-50/70 border border-purple-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-purple-600 text-white font-bold font-french text-[11px]">à la</span>
                <p class="font-bold text-slate-800">مفرد مؤنث ساكن</p>
                <p class="text-[11px] font-french text-purple-800 font-semibold" dir="ltr">à la tête (الرأس), à la jambe (الساق), à la main (اليد), à la bouche (الفم), à la gorge (الحلق)</p>
              </div>

              <div class="p-3.5 rounded-2xl bg-emerald-50/70 border border-emerald-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-emerald-600 text-white font-bold font-french text-[11px]">à l'</span>
                <p class="font-bold text-slate-800">مفرد مبدوء بحرف متحرك</p>
                <p class="text-[11px] font-french text-emerald-800 font-semibold" dir="ltr">à l'estomac (المعدة), à l'oreille (الأذن), à l'œil (العين)</p>
              </div>

              <div class="p-3.5 rounded-2xl bg-amber-50/70 border border-amber-200 space-y-1">
                <span class="px-2 py-0.5 rounded bg-amber-600 text-white font-bold font-french text-[11px]">aux</span>
                <p class="font-bold text-slate-800">جمع ينتهي بـ s أو x</p>
                <p class="text-[11px] font-french text-amber-900 font-semibold" dir="ltr">aux yeux (العيون), aux dents (الأسنان), aux oreilles (الآذان), aux pieds (الأقدام)</p>
              </div>
            </div>

            <!-- Model Example with Audio -->
            <div class="p-3.5 bg-slate-50 rounded-2xl border border-slate-200 flex items-center justify-between gap-3 text-xs">
              <div dir="ltr">
                <span class="font-bold text-slate-900 font-french text-sm">J'ai mal à la tête et au bras.</span>
                <p class="text-slate-500 font-sans text-[11px] mt-0.5" dir="rtl">أشعر بألم في الرأس (مؤنث: à la tête) وفي الذراع (مذكر: au bras).</p>
              </div>
              <button onclick="speakText('J ai mal à la tête et au bras')" class="w-8 h-8 rounded-lg bg-emerald-100 text-emerald-700 hover:bg-emerald-200 flex items-center justify-center shrink-0">
                <i class="fa-solid fa-volume-high text-xs"></i>
              </button>
            </div>

            <!-- Quiz Questions Section -->
            <div class="pt-2">
              <h4 class="text-xs font-bold text-slate-800 mb-2">تدريبات التعبير عن الألم المقررة (ص 65-66) :</h4>
              <div class="space-y-3">${renderQuizQuestionsList(c.bodyPainQuestions)}</div>
            </div>
          </div>
        </div>

        <!-- 3. SITUATIONS -->
        <div id="u3-pane-situations" class="u3-pane hidden space-y-4">
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm space-y-3">
            <h3 class="text-base font-bold text-slate-900">المواقف الطبية والنصائح الصحية (${c.medicalSituations.length} مواقف)</h3>
            <div class="space-y-3">${renderQuizQuestionsList(c.medicalSituations)}</div>
          </div>
        </div>
      `;
    }

    function switchUnit3SubTab(tabId) {
      playClickSound();
      document.querySelectorAll('.u3-pane').forEach(el => el.classList.add('hidden'));
      document.querySelectorAll('.u3-nav-btn').forEach(el => el.classList.remove('tab-active'));
      const p = document.getElementById('u3-pane-' + tabId);
      const b = document.getElementById('u3-tab-btn-' + tabId);
      if (p) p.classList.remove('hidden');
      if (b) b.classList.add('tab-active');
    }
"""
