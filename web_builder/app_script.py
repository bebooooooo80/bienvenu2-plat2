# -*- coding: utf-8 -*-

def get_app_script():
    return """
    // Global State
    let currentScreen = 'portal';
    let navHistory = [];
    let isSoundEnabled = true;
    let audioCtx = null;
    let examTimerInterval = null;
    let examTimeRemaining = 3600; // 60 minutes

    const DATA = window.COURSE_DATA;

    // --- Audio Engine ---
    function initAudio() {
      if (!audioCtx) {
        audioCtx = new (window.AudioContext || window.webkitAudioContext)();
      }
    }

    function playSuccessSound() {
      if (!isSoundEnabled) return;
      initAudio();
      try {
        const now = audioCtx.currentTime;
        const osc = audioCtx.createOscillator();
        const gain = audioCtx.createGain();
        osc.connect(gain);
        gain.connect(audioCtx.destination);
        osc.type = 'triangle';
        osc.frequency.setValueAtTime(523.25, now);
        osc.frequency.setValueAtTime(659.25, now + 0.1);
        osc.frequency.setValueAtTime(783.99, now + 0.2);
        osc.frequency.setValueAtTime(1046.50, now + 0.3);
        gain.gain.setValueAtTime(0.15, now);
        gain.gain.exponentialRampToValueAtTime(0.001, now + 0.5);
        osc.start(now);
        osc.stop(now + 0.5);
      } catch (e) {}
    }

    function playErrorSound() {
      if (!isSoundEnabled) return;
      initAudio();
      try {
        const now = audioCtx.currentTime;
        const osc = audioCtx.createOscillator();
        const gain = audioCtx.createGain();
        osc.connect(gain);
        gain.connect(audioCtx.destination);
        osc.type = 'sawtooth';
        osc.frequency.setValueAtTime(180, now);
        osc.frequency.linearRampToValueAtTime(120, now + 0.25);
        gain.gain.setValueAtTime(0.15, now);
        gain.gain.exponentialRampToValueAtTime(0.001, now + 0.3);
        osc.start(now);
        osc.stop(now + 0.3);
      } catch (e) {}
    }

    function playClickSound() {
      if (!isSoundEnabled) return;
      initAudio();
      try {
        const now = audioCtx.currentTime;
        const osc = audioCtx.createOscillator();
        const gain = audioCtx.createGain();
        osc.connect(gain);
        gain.connect(audioCtx.destination);
        osc.type = 'sine';
        osc.frequency.setValueAtTime(800, now);
        gain.gain.setValueAtTime(0.08, now);
        gain.gain.exponentialRampToValueAtTime(0.001, now + 0.05);
        osc.start(now);
        osc.stop(now + 0.05);
      } catch (e) {}
    }

    function toggleSound() {
      isSoundEnabled = !isSoundEnabled;
      const btn = document.getElementById('soundToggleBtn');
      if (isSoundEnabled) {
        btn.innerHTML = '<i class="fa-solid fa-volume-high"></i>';
        btn.classList.replace('text-indigo-300', 'text-white');
      } else {
        btn.innerHTML = '<i class="fa-solid fa-volume-xmark"></i>';
        btn.classList.replace('text-white', 'text-indigo-300');
        if ('speechSynthesis' in window) window.speechSynthesis.cancel();
      }
    }

    function speakText(frenchText) {
      if (!isSoundEnabled) return;
      if (!('speechSynthesis' in window)) return;
      window.speechSynthesis.cancel();
      const utterance = new SpeechSynthesisUtterance(frenchText);
      utterance.lang = 'fr-FR';
      utterance.rate = 0.9;
      const voices = window.speechSynthesis.getVoices();
      const frVoice = voices.find(v => v.lang.startsWith('fr'));
      if (frVoice) utterance.voice = frVoice;
      window.speechSynthesis.speak(utterance);
    }

    // --- Activation ---
    const VALID_CODES = ['BIENVENU2025', 'PREPA2', 'FRANCAIS2', 'EXCELLENCE'];

    function checkActivation() {
      const isActivated = localStorage.getItem('bienvenu2_activated');
      const studentName = localStorage.getItem('bienvenu2_student') || 'طالب متفوق';
      if (isActivated === 'true') {
        document.getElementById('activationOverlay').classList.add('hidden');
        document.getElementById('userNameDisplay').innerText = studentName;
      }
    }

    function fillCode(code) {
      document.getElementById('activationCodeInput').value = code;
      playClickSound();
    }

    function activateAccount() {
      const code = document.getElementById('activationCodeInput').value.trim().toUpperCase();
      const name = document.getElementById('studentNameInput').value.trim() || 'طالب متفوق';
      const err = document.getElementById('activationError');
      if (VALID_CODES.includes(code)) {
        localStorage.setItem('bienvenu2_activated', 'true');
        localStorage.setItem('bienvenu2_student', name);
        document.getElementById('userNameDisplay').innerText = name;
        document.getElementById('activationOverlay').classList.add('hidden');
        playSuccessSound();
        if (typeof confetti === 'function') confetti({ particleCount: 100, spread: 70 });
      } else {
        playErrorSound();
        err.innerText = '❌ كود غير صحيح! يرجى اختيار أحد الأكواد المتاحة في القائمة أعلاه.';
        err.classList.remove('hidden');
      }
    }

    function instantDemoLogin() {
      localStorage.setItem('bienvenu2_activated', 'true');
      localStorage.setItem('bienvenu2_student', 'طالب متفوق');
      document.getElementById('userNameDisplay').innerText = 'طالب متفوق';
      document.getElementById('activationOverlay').classList.add('hidden');
      playSuccessSound();
      if (typeof confetti === 'function') confetti({ particleCount: 120, spread: 70 });
    }

    // --- Navigation ---
    const SCREENS = ['portal', 'unit1', 'unit2', 'unit3', 'revision', 'grammar', 'exam'];
    const SUBTITLES = {
      'portal': 'الصف الثاني الإعدادي • المحتوى الكامل 100%',
      'unit1': 'Unité 1 : Une fête • الدعوة وحفلة عيد الميلاد (p.11-34)',
      'unit2': 'Unité 2 : Les repas • الوجبات وفي المطعم (p.35-58)',
      'unit3': 'Unité 3 : La santé • الصحة وزيارة الطبيب (p.59-67)',
      'revision': 'Révision générale • نصوص وتمارين الكراسة (p.67-77)',
      'grammar': 'Atelier Grammaire • ورشة القواعد والأسئلة (p.4-6)',
      'exam': 'Examen Officiel • امتحان نصف العام 20 درجة'
    };

    function navigateTo(screenId, saveHistory = true) {
      if (!SCREENS.includes(screenId)) return;
      playClickSound();
      if (saveHistory && currentScreen !== screenId) {
        navHistory.push(currentScreen);
      }
      currentScreen = screenId;

      SCREENS.forEach(id => {
        const el = document.getElementById('screen-' + id);
        if (el) el.classList.toggle('hidden', id !== screenId);
      });

      const backBtn = document.getElementById('topBackButton');
      if (currentScreen === 'portal') {
        backBtn.classList.add('hidden');
      } else {
        backBtn.classList.remove('hidden');
      }

      document.getElementById('topSubtitle').innerText = SUBTITLES[screenId] || 'الصف الثاني الإعدادي';

      document.querySelectorAll('.chip-btn').forEach(btn => {
        btn.classList.remove('bg-indigo-600', 'text-white', 'shadow-sm');
        btn.classList.add('text-indigo-200');
      });
      const activeChip = document.getElementById('chip-' + screenId);
      if (activeChip) {
        activeChip.classList.add('bg-indigo-600', 'text-white', 'shadow-sm');
        activeChip.classList.remove('text-indigo-200');
        activeChip.scrollIntoView({ behavior: 'smooth', inline: 'center' });
      }

      document.querySelectorAll('.bot-nav-btn').forEach(btn => {
        btn.classList.remove('text-indigo-600', 'font-bold');
        btn.classList.add('text-slate-500', 'font-semibold');
      });
      const activeBot = document.getElementById('bot-btn-' + screenId);
      if (activeBot) {
        activeBot.classList.add('text-indigo-600', 'font-bold');
        activeBot.classList.remove('text-slate-500', 'font-semibold');
      }

      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    function goBack() {
      if (navHistory.length > 0) {
        const prev = navHistory.pop();
        navigateTo(prev, false);
      } else if (currentScreen !== 'portal') {
        navigateTo('portal', false);
      }
    }

    // --- Quiz Answer Checker ---
    function checkQuizOption(btn, isCorrect, explanation) {
      const parent = btn.closest('.quiz-card');
      const allButtons = parent.querySelectorAll('.quiz-opt-btn');
      const feedback = parent.querySelector('.quiz-feedback');
      allButtons.forEach(b => {
        b.disabled = true;
        b.classList.remove('hover:bg-slate-50', 'hover:border-indigo-300');
      });
      if (isCorrect) {
        btn.classList.add('bg-emerald-500', 'text-white', 'border-emerald-600');
        playSuccessSound();
        if (feedback) {
          feedback.innerHTML = '<span class="text-emerald-600">✓ أحسنت! ' + (explanation || 'إجابة صحيحة') + '</span>';
          feedback.classList.remove('hidden');
        }
      } else {
        btn.classList.add('bg-rose-500', 'text-white', 'border-rose-600');
        playErrorSound();
        if (feedback) {
          feedback.innerHTML = '<span class="text-rose-500">✗ إجابة غير صحيحة. ' + (explanation || '') + '</span>';
          feedback.classList.remove('hidden');
        }
      }
    }

    // ==========================================
    // RENDERERS FOR ALL SCREENS
    // ==========================================

    // 1. RENDER PORTAL SCREEN
    function renderPortalScreen() {
      const c = DATA.course;
      const html = `
        <div id="portal-hub-view" class="space-y-6">
          <!-- Hero Banner -->
          <div class="relative overflow-hidden rounded-3xl bg-gradient-to-br from-indigo-900 via-indigo-800 to-indigo-950 text-white p-6 sm:p-8 shadow-xl border border-indigo-700/50">
            <div class="relative z-10">
              <div class="flex flex-wrap items-center justify-between gap-4 mb-4">
                <div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-white/10 backdrop-blur-md border border-white/15 text-xs font-bold text-indigo-200">
                  <span class="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
                  <span>المنهج الدراسي المعتمد 2025/2026 - 100% كامل</span>
                </div>
                <button onclick="speakText('Bienvenu 2, deuxième préparatoire')" class="px-3.5 py-1.5 rounded-full bg-white/15 hover:bg-white/25 active:scale-95 text-xs font-bold font-french flex items-center gap-2 transition text-white border border-white/20">
                  <i class="fa-solid fa-volume-high text-indigo-300"></i>
                  <span>Écouter (استمع)</span>
                </button>
              </div>

              <h1 class="text-2xl sm:text-3xl font-black mb-2">منصة Bienvenu 2 الشاملة</h1>
              <p class="text-sm sm:text-base text-indigo-200 font-medium leading-relaxed max-w-2xl mb-6">
                بوابتك المتكاملة لتفوق اللغة الفرنسية في الصف الثاني الإعدادي. تم استيراد ونقل كامل بنك المفردات، نصوص القراءة، التمارين، والمواقف وقواعد الكتيّب المدرسي بدقة 100%.
              </p>

              <!-- Statistics -->
              <div class="grid grid-cols-2 sm:grid-cols-4 gap-3 text-center">
                <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-3 border border-white/10">
                  <span class="block text-2xl font-black text-amber-300 font-french">3</span>
                  <span class="text-xs text-indigo-100 font-bold">وحدات دراسية كاملة</span>
                </div>
                <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-3 border border-white/10">
                  <span class="block text-2xl font-black text-emerald-300 font-french">${c.unit1VocabWords.length + c.unit1BanqueDesMots.length + c.unit2VocabWords.length}+</span>
                  <span class="text-xs text-indigo-100 font-bold">مفردة وكلمة صوتية</span>
                </div>
                <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-3 border border-white/10">
                  <span class="block text-2xl font-black text-cyan-300 font-french">${c.grammarQuizQuestions.length + c.bookletPronounsOfficial15Exercises.length + c.unit1Situations.length}+</span>
                  <span class="text-xs text-indigo-100 font-bold">تمريناً وموقفاً تفاعلياً</span>
                </div>
                <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-3 border border-white/10">
                  <span class="block text-2xl font-black text-rose-300 font-french">20/20</span>
                  <span class="text-xs text-indigo-100 font-bold">امتحان نصف العام الرسمي</span>
                </div>
              </div>
            </div>
          </div>

          <!-- The Two Big Doors -->
          <div>
            <div class="flex items-center gap-2 mb-4">
              <div class="w-2.5 h-6 rounded-full bg-indigo-600"></div>
              <h2 class="text-lg font-black text-slate-800">بوابتا المنهج الرئيسية (Les Deux Grandes Portes)</h2>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <!-- Door 1 -->
              <div onclick="openRevisionIndex()" class="group cursor-pointer rounded-3xl p-6 bg-gradient-to-br from-violet-600 via-indigo-600 to-indigo-700 text-white shadow-lg hover:shadow-xl transition-all duration-300 hover:-translate-y-1 relative overflow-hidden border border-indigo-400/30">
                <div class="flex items-start justify-between mb-4">
                  <div class="w-14 h-14 rounded-2xl bg-white/20 backdrop-blur-md flex items-center justify-center text-2xl shadow-inner border border-white/20">
                    <i class="fa-solid fa-book-bookmark text-amber-300"></i>
                  </div>
                  <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold text-indigo-100 font-french border border-white/20">
                    Pages 4 - 79
                  </span>
                </div>
                <h3 class="text-xl font-black mb-1">البوابة الأولى: قسم المراجعة والامتحانات</h3>
                <p class="text-xs font-semibold text-indigo-200 font-french mb-2">Révision générale & Examens (فهرس كراسة المراجعة)</p>
                <p class="text-xs text-indigo-100 leading-relaxed mb-4">
                  تصفح الفهرس المرتب بأرقام الصفحات: الأسئلة التمهيدية (ص 4-6)، نصوص الفهم الأربعة (ص 67-71)، قواعد الكراسة الشاملة (ص 72-77)، وامتحان نصف العام الرسمي (ص 78-79).
                </p>
                <div class="flex items-center justify-between pt-3 border-t border-white/15 text-xs font-bold text-amber-300">
                  <span>فتح فهرس كراسة المراجعة</span>
                  <i class="fa-solid fa-arrow-left group-hover:-translate-x-1 transition-transform"></i>
                </div>
              </div>

              <!-- Door 2 -->
              <div onclick="document.getElementById('unitsSection').scrollIntoView({ behavior: 'smooth' })" class="group cursor-pointer rounded-3xl p-6 bg-gradient-to-br from-blue-600 via-indigo-600 to-emerald-700 text-white shadow-lg hover:shadow-xl transition-all duration-300 hover:-translate-y-1 relative overflow-hidden border border-emerald-400/30">
                <div class="flex items-start justify-between mb-4">
                  <div class="w-14 h-14 rounded-2xl bg-white/20 backdrop-blur-md flex items-center justify-center text-2xl shadow-inner border border-white/20">
                    <i class="fa-solid fa-graduation-cap text-emerald-300"></i>
                  </div>
                  <span class="px-3 py-1 rounded-full bg-white/20 text-xs font-bold text-emerald-100 font-french border border-white/20">
                    3 Unités complètes
                  </span>
                </div>
                <h3 class="text-xl font-black mb-1">البوابة الثانية: وحدات المنهج الدراسي</h3>
                <p class="text-xs font-semibold text-emerald-200 font-french mb-2">Les 3 Unités du Programme</p>
                <p class="text-xs text-indigo-100 leading-relaxed mb-4">
                  الوحدات الثلاث المقررة: نصوص الاستماع والقراءة، جداول المفردات وبنك الكلمات الصوتي، مواقف الحياة اليومية، القواعد والضمائر، وموضوعات الإنتاج وتكوين الجمل.
                </p>
                <div class="flex items-center justify-between pt-3 border-t border-white/15 text-xs font-bold text-emerald-300">
                  <span>استكشف وحدات المنهج</span>
                  <i class="fa-solid fa-arrow-left group-hover:-translate-x-1 transition-transform"></i>
                </div>
              </div>
            </div>
          </div>

          <!-- Units Cards -->
          <div id="unitsSection" class="pt-2">
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center gap-2">
                <div class="w-2.5 h-6 rounded-full bg-blue-600"></div>
                <h2 class="text-lg font-black text-slate-800">وحدات المنهج الدراسي المقررة</h2>
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
              <!-- Unit 1 -->
              <div onclick="navigateTo('unit1')" class="cursor-pointer bg-white rounded-2xl p-5 border border-slate-200 hover:border-blue-400 hover:shadow-md transition">
                <div class="flex items-center justify-between mb-2">
                  <span class="w-8 h-8 rounded-lg bg-blue-50 text-blue-600 font-black font-french flex items-center justify-center text-sm">1</span>
                  <span class="text-[11px] font-bold text-blue-600 font-french bg-blue-50 px-2 py-0.5 rounded">p. 11-34</span>
                </div>
                <h3 class="text-base font-bold text-slate-900 mb-1">Unité 1 : Une fête</h3>
                <p class="text-xs text-slate-500 mb-3 leading-relaxed">حفلة عيد الميلاد، بنك الكلمات (61 مفردة)، المواقف (6)، الضمير On، ضمائر المفعول (15 تمريناً)، صفات الملكية، وتدريبات ص 26.</p>
                <div class="text-xs font-bold text-blue-600 flex items-center justify-between border-t border-slate-100 pt-2.5">
                  <span>فتح الوحدة 1</span>
                  <i class="fa-solid fa-arrow-left"></i>
                </div>
              </div>

              <!-- Unit 2 -->
              <div onclick="navigateTo('unit2')" class="cursor-pointer bg-white rounded-2xl p-5 border border-slate-200 hover:border-amber-400 hover:shadow-md transition">
                <div class="flex items-center justify-between mb-2">
                  <span class="w-8 h-8 rounded-lg bg-amber-50 text-amber-600 font-black font-french flex items-center justify-center text-sm">2</span>
                  <span class="text-[11px] font-bold text-amber-600 font-french bg-amber-50 px-2 py-0.5 rounded">p. 35-58</span>
                </div>
                <h3 class="text-base font-bold text-slate-900 mb-1">Unité 2 : Les repas</h3>
                <p class="text-xs text-slate-500 mb-3 leading-relaxed">الوجبات وفي المطعم، أصناف الطعام والشراب (28 عنصراً)، كويز الوجبات (10)، أدوات التجزئة، أدوات الاستفهام، ومواقف Qui parle.</p>
                <div class="text-xs font-bold text-amber-600 flex items-center justify-between border-t border-slate-100 pt-2.5">
                  <span>فتح الوحدة 2</span>
                  <i class="fa-solid fa-arrow-left"></i>
                </div>
              </div>

              <!-- Unit 3 -->
              <div onclick="navigateTo('unit3')" class="cursor-pointer bg-white rounded-2xl p-5 border border-slate-200 hover:border-emerald-400 hover:shadow-md transition">
                <div class="flex items-center justify-between mb-2">
                  <span class="w-8 h-8 rounded-lg bg-emerald-50 text-emerald-600 font-black font-french flex items-center justify-center text-sm">3</span>
                  <span class="text-[11px] font-bold text-emerald-600 font-french bg-emerald-50 px-2 py-0.5 rounded">p. 59-67</span>
                </div>
                <h3 class="text-base font-bold text-slate-900 mb-1">Unité 3 : La santé</h3>
                <p class="text-xs text-slate-500 mb-3 leading-relaxed">الصحة والمستشفى، نصوص ص 59 و 60 والحوارات (10)، أجزاء الجسم والتعبير عن الألم (5 أسئلة)، والمواقف الطبية.</p>
                <div class="text-xs font-bold text-emerald-600 flex items-center justify-between border-t border-slate-100 pt-2.5">
                  <span>فتح الوحدة 3</span>
                  <i class="fa-solid fa-arrow-left"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- SUB-VIEW 1: REVISION CHRONOLOGICAL PAGES INDEX -->
        <div id="portal-revision-index" class="hidden space-y-4">
          <div class="flex items-center justify-between bg-white p-4 rounded-2xl border border-slate-200 shadow-sm">
            <div class="flex items-center gap-3">
              <button onclick="closeRevisionIndex()" class="px-3.5 py-2 rounded-xl bg-slate-100 hover:bg-slate-200 active:scale-95 text-xs font-bold text-slate-700 flex items-center gap-2 transition">
                <i class="fa-solid fa-arrow-right"></i>
                <span>العودة للبوابة الرئيسية</span>
              </button>
              <div>
                <h2 class="text-base font-black text-slate-900">فهرس كراسة المراجعة الرسمية</h2>
                <p class="text-xs text-indigo-600 font-french">Révision Générale • Pages 4 à 79</p>
              </div>
            </div>
          </div>

          <div class="space-y-3">
            <div onclick="navigateTo('grammar')" class="cursor-pointer bg-white p-4 rounded-2xl border border-slate-200 hover:border-indigo-400 hover:shadow-sm transition flex items-center justify-between">
              <div class="flex items-center gap-3">
                <span class="w-12 h-12 rounded-xl bg-indigo-50 text-indigo-600 flex items-center justify-center font-bold text-xs font-french shrink-0">p. 4-6</span>
                <div>
                  <h4 class="text-sm font-bold text-slate-900">الأسئلة التمهيدية وقواعد ما قبل المنهج (37 سؤالاً)</h4>
                  <p class="text-xs text-slate-500">المضارع، أدوات الاستفهام الخمسة، حروف الجر للأماكن، والنفي التام</p>
                </div>
              </div>
              <i class="fa-solid fa-chevron-left text-slate-400"></i>
            </div>

            <div onclick="navigateTo('revision')" class="cursor-pointer bg-white p-4 rounded-2xl border border-slate-200 hover:border-blue-400 hover:shadow-sm transition flex items-center justify-between">
              <div class="flex items-center gap-3">
                <span class="w-12 h-12 rounded-xl bg-blue-50 text-blue-600 flex items-center justify-center font-bold text-xs font-french shrink-0">p. 67-71</span>
                <div>
                  <h4 class="text-sm font-bold text-slate-900">نصوص كراسة الفهم الأربعة الرسمية (4 Textes de compréhension)</h4>
                  <p class="text-xs text-slate-500">خطاب الدعوة، محادثة الهاتف، النص السردي، والحوار في الحفل مع أسئلة MCQ وصح/خطأ وتكملة</p>
                </div>
              </div>
              <i class="fa-solid fa-chevron-left text-slate-400"></i>
            </div>

            <div onclick="navigateTo('revision')" class="cursor-pointer bg-white p-4 rounded-2xl border border-slate-200 hover:border-violet-400 hover:shadow-sm transition flex items-center justify-between">
              <div class="flex items-center gap-3">
                <span class="w-12 h-12 rounded-xl bg-violet-50 text-violet-600 flex items-center justify-center font-bold text-xs font-french shrink-0">p. 72-77</span>
                <div>
                  <h4 class="text-sm font-bold text-slate-900">تمارين القواعد الشاملة بكراسة المراجعة (7 تمارين كبرى)</h4>
                  <p class="text-xs text-slate-500">النفي، أدوات التجزئة، الضمائر الشخصية، تصريف الأفعال، صفات الملكية، وتصويب الأخطاء</p>
                </div>
              </div>
              <i class="fa-solid fa-chevron-left text-slate-400"></i>
            </div>

            <div onclick="navigateTo('exam')" class="cursor-pointer bg-gradient-to-r from-rose-50 to-indigo-50 p-4 rounded-2xl border-2 border-rose-200 hover:border-rose-400 hover:shadow-sm transition flex items-center justify-between">
              <div class="flex items-center gap-3">
                <span class="w-12 h-12 rounded-xl bg-rose-600 text-white flex items-center justify-center font-black text-xs font-french shrink-0">p. 78-79</span>
                <div>
                  <h4 class="text-sm font-black text-slate-900">امتحان نصف العام الدراسي الرسمي (20/20)</h4>
                  <p class="text-xs text-slate-600">الورقة الرسمية الكاملة: فهم، مواقف، قواعد، وإنتاج مع حساب الدرجة وتوقيت 60 دقيقة</p>
                </div>
              </div>
              <i class="fa-solid fa-chevron-left text-rose-500"></i>
            </div>
          </div>
        </div>
      `;
      document.getElementById('screen-portal').innerHTML = html;
    }

    function openRevisionIndex() {
      playClickSound();
      document.getElementById('portal-hub-view').classList.add('hidden');
      document.getElementById('portal-revision-index').classList.remove('hidden');
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    function closeRevisionIndex() {
      playClickSound();
      document.getElementById('portal-revision-index').classList.add('hidden');
      document.getElementById('portal-hub-view').classList.remove('hidden');
    }

    // --- INIT APP ---
    window.addEventListener('DOMContentLoaded', () => {
      checkActivation();
      renderPortalScreen();
      renderUnit1Screen();
      renderUnit2Screen();
      renderUnit3Screen();
      renderRevisionScreen();
      renderGrammarScreen();
      renderExamScreen();
    });
"""
