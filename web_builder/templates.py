# -*- coding: utf-8 -*-

def get_html_shell_top():
    return """<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>Bienvenu 2 - المنصة التعليمية الكاملة للصف الثاني الإعدادي</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@400;600;700;800;900&family=Plus+Jakarta+Sans:wght@500;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.9.2/dist/confetti.browser.min.js"></script>
  <style>
    body {
      font-family: 'Cairo', -apple-system, BlinkMacSystemFont, sans-serif;
      -webkit-tap-highlight-color: transparent;
      user-select: none;
    }
    .font-french {
      font-family: 'Plus Jakarta Sans', sans-serif;
    }
    .custom-scrollbar::-webkit-scrollbar {
      height: 4px;
      width: 4px;
    }
    .custom-scrollbar::-webkit-scrollbar-thumb {
      background-color: #cbd5e1;
      border-radius: 9999px;
    }
    .tab-active {
      background-color: #4338ca !important;
      color: #ffffff !important;
      box-shadow: 0 4px 12px rgba(67, 56, 202, 0.25);
    }
  </style>
</head>
<body class="bg-slate-50 text-slate-800 min-h-screen pb-24 md:pb-16 flex flex-col antialiased selection:bg-indigo-500 selection:text-white">

  <!-- ACTIVATION MODAL -->
  <div id="activationOverlay" class="fixed inset-0 z-50 bg-slate-900/80 backdrop-blur-md flex items-center justify-center p-4">
    <div class="bg-white max-w-md w-full rounded-3xl p-6 sm:p-8 shadow-2xl border border-slate-100 text-center relative overflow-hidden animate-fade-in">
      <div class="absolute top-0 left-0 right-0 h-3 bg-gradient-to-r from-blue-600 via-white to-red-600"></div>
      
      <div class="w-16 h-16 mx-auto mb-4 rounded-2xl bg-indigo-50 text-indigo-600 flex items-center justify-center text-3xl shadow-inner border border-indigo-100">
        <i class="fa-solid fa-graduation-cap"></i>
      </div>
      
      <h2 class="text-2xl font-black text-slate-800 mb-1">منصة Bienvenu 2 الشاملة</h2>
      <p class="text-xs font-semibold text-indigo-600 mb-4 font-french">2ème Préparatoire - المحتوى الأصلي الكامل 100%</p>
      <p class="text-sm text-slate-600 mb-6 leading-relaxed">أهلاً بك! تم تحويل وتضمين 100% من محتوى تطبيق الأندرويد الدراسي بالكامل بدون أي اختصار أو حذف.</p>

      <div class="space-y-4 mb-6 text-right">
        <div>
          <label class="block text-xs font-bold text-slate-700 mb-1.5"><i class="fa-solid fa-user text-indigo-500 ml-1"></i> اسم الطالب / الطالبة</label>
          <input type="text" id="studentNameInput" placeholder="أدخل اسمك هنا..." class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-sm font-semibold transition" value="طالب متفوق">
        </div>
        <div>
          <label class="block text-xs font-bold text-slate-700 mb-1.5"><i class="fa-solid fa-key text-indigo-500 ml-1"></i> كود التفعيل الدراسي</label>
          <input type="text" id="activationCodeInput" placeholder="اكتب كود التفعيل (مثال: BIENVENU2025)..." class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-sm font-french font-bold uppercase transition tracking-wider" value="BIENVENU2025">
          <p id="activationError" class="text-xs text-rose-500 mt-1.5 hidden font-bold"></p>
        </div>
      </div>

      <!-- Quick Codes -->
      <div class="mb-5 bg-slate-50 p-3 rounded-2xl border border-slate-200/70 text-right">
        <span class="text-xs font-bold text-slate-500 block mb-1.5"><i class="fa-solid fa-wand-magic-sparkles text-amber-500 ml-1"></i> انقر على أي كود تجريبي للملء التلقائي:</span>
        <div class="flex flex-wrap gap-1.5">
          <button onclick="fillCode('BIENVENU2025')" class="px-2.5 py-1 text-xs font-bold font-french rounded-lg bg-indigo-100 text-indigo-700 hover:bg-indigo-200 transition">BIENVENU2025</button>
          <button onclick="fillCode('PREPA2')" class="px-2.5 py-1 text-xs font-bold font-french rounded-lg bg-emerald-100 text-emerald-700 hover:bg-emerald-200 transition">PREPA2</button>
          <button onclick="fillCode('FRANCAIS2')" class="px-2.5 py-1 text-xs font-bold font-french rounded-lg bg-violet-100 text-violet-700 hover:bg-violet-200 transition">FRANCAIS2</button>
          <button onclick="fillCode('EXCELLENCE')" class="px-2.5 py-1 text-xs font-bold font-french rounded-lg bg-amber-100 text-amber-700 hover:bg-amber-200 transition">EXCELLENCE</button>
        </div>
      </div>

      <button onclick="activateAccount()" class="w-full py-3.5 px-4 rounded-xl bg-indigo-600 hover:bg-indigo-700 active:scale-98 text-white font-bold text-sm shadow-lg shadow-indigo-600/30 transition flex items-center justify-center gap-2">
        <span>دخول وتفعيل المنهج الكامل</span>
        <i class="fa-solid fa-arrow-left"></i>
      </button>

      <button onclick="instantDemoLogin()" class="w-full mt-2.5 py-2.5 px-4 rounded-xl bg-emerald-50 hover:bg-emerald-100 text-emerald-700 font-bold text-xs border border-emerald-200 transition flex items-center justify-center gap-2">
        <i class="fa-solid fa-bolt text-emerald-600"></i>
        <span>⚡ دخول فوري بنقرة واحدة</span>
      </button>
    </div>
  </div>

  <!-- TOP APP BAR -->
  <header class="sticky top-0 z-40 bg-indigo-700 text-white shadow-md transition">
    <div class="max-w-5xl mx-auto px-4 py-2.5 flex items-center justify-between">
      <div class="flex items-center gap-2.5">
        <button id="topBackButton" onclick="goBack()" class="w-9 h-9 rounded-xl bg-indigo-600/80 hover:bg-indigo-600 active:scale-95 flex items-center justify-center text-sm transition hidden">
          <i class="fa-solid fa-arrow-right"></i>
        </button>
        <div>
          <div class="flex items-center gap-2">
            <span class="font-french font-black tracking-wide text-lg text-white">Bienvenu 2</span>
            <span class="inline-flex rounded-full overflow-hidden w-4 h-3 border border-white/30">
              <span class="w-1/3 bg-blue-500"></span><span class="w-1/3 bg-white"></span><span class="w-1/3 bg-red-500"></span>
            </span>
          </div>
          <p id="topSubtitle" class="text-xs text-indigo-200 font-semibold leading-tight">الصف الثاني الإعدادي • المحتوى الكامل 100%</p>
        </div>
      </div>

      <div class="flex items-center gap-2">
        <button id="soundToggleBtn" onclick="toggleSound()" title="تشغيل / كتم الصوت" class="w-9 h-9 rounded-xl bg-indigo-600/80 hover:bg-indigo-600 active:scale-95 flex items-center justify-center text-sm text-white transition">
          <i class="fa-solid fa-volume-high"></i>
        </button>
        <div id="userBadge" class="hidden sm:flex items-center gap-1.5 px-3 py-1 rounded-xl bg-indigo-800/80 border border-indigo-500/30 text-xs font-bold text-indigo-100">
          <i class="fa-solid fa-circle-user text-emerald-400"></i>
          <span id="userNameDisplay">طالب متفوق</span>
        </div>
      </div>
    </div>

    <!-- HORIZONTAL MODULE CHIPS -->
    <div class="bg-indigo-800/90 border-t border-indigo-600/50 px-3 py-2 overflow-x-auto custom-scrollbar flex items-center gap-1.5 max-w-5xl mx-auto">
      <button onclick="navigateTo('portal')" id="chip-portal" class="chip-btn px-3 py-1 rounded-lg text-xs font-bold whitespace-nowrap transition bg-indigo-600 text-white shadow-sm flex items-center gap-1.5">
        <i class="fa-solid fa-house"></i>
        <span>الرئيسية</span>
      </button>
      <button onclick="navigateTo('revision')" id="chip-revision" class="chip-btn px-3 py-1 rounded-lg text-xs font-bold whitespace-nowrap transition text-indigo-200 hover:text-white hover:bg-indigo-700/60 flex items-center gap-1.5">
        <i class="fa-solid fa-book-open"></i>
        <span>المراجعة (p.67-77)</span>
      </button>
      <button onclick="navigateTo('grammar')" id="chip-grammar" class="chip-btn px-3 py-1 rounded-lg text-xs font-bold whitespace-nowrap transition text-indigo-200 hover:text-white hover:bg-indigo-700/60 flex items-center gap-1.5">
        <i class="fa-solid fa-ruler-combined"></i>
        <span>القواعد والأسئلة (p.4-6)</span>
      </button>
      <button onclick="navigateTo('unit1')" id="chip-unit1" class="chip-btn px-3 py-1 rounded-lg text-xs font-bold whitespace-nowrap transition text-indigo-200 hover:text-white hover:bg-indigo-700/60 flex items-center gap-1.5">
        <span class="w-4 h-4 rounded-full bg-blue-500 text-white flex items-center justify-center text-[10px]">1</span>
        <span>الوحدة 1 (p.11-34)</span>
      </button>
      <button onclick="navigateTo('unit2')" id="chip-unit2" class="chip-btn px-3 py-1 rounded-lg text-xs font-bold whitespace-nowrap transition text-indigo-200 hover:text-white hover:bg-indigo-700/60 flex items-center gap-1.5">
        <span class="w-4 h-4 rounded-full bg-amber-500 text-white flex items-center justify-center text-[10px]">2</span>
        <span>الوحدة 2 (p.35-58)</span>
      </button>
      <button onclick="navigateTo('unit3')" id="chip-unit3" class="chip-btn px-3 py-1 rounded-lg text-xs font-bold whitespace-nowrap transition text-indigo-200 hover:text-white hover:bg-indigo-700/60 flex items-center gap-1.5">
        <span class="w-4 h-4 rounded-full bg-emerald-500 text-white flex items-center justify-center text-[10px]">3</span>
        <span>الوحدة 3 (p.59-67)</span>
      </button>
      <button onclick="navigateTo('exam')" id="chip-exam" class="chip-btn px-3 py-1 rounded-lg text-xs font-bold whitespace-nowrap transition text-indigo-200 hover:text-white hover:bg-indigo-700/60 flex items-center gap-1.5">
        <i class="fa-solid fa-file-pen text-rose-300"></i>
        <span>امتحان نصف العام (20 درجة)</span>
      </button>
    </div>
  </header>

  <!-- MAIN WRAPPER -->
  <main class="max-w-5xl mx-auto px-4 py-5 flex-1 w-full">
"""

def get_html_shell_bottom():
    return """
  </main>

  <!-- BOTTOM NAVIGATION BAR -->
  <nav class="fixed bottom-0 left-0 right-0 z-40 bg-white/95 backdrop-blur-md border-t border-slate-200/90 shadow-lg px-2 py-1.5 flex items-center justify-around max-w-5xl mx-auto">
    <button onclick="navigateTo('portal')" id="bot-btn-portal" class="bot-nav-btn flex flex-col items-center gap-0.5 py-1 px-2.5 rounded-xl transition text-indigo-600 font-bold">
      <i class="fa-solid fa-house text-base"></i>
      <span class="text-[10px]">الرئيسية</span>
    </button>
    <button onclick="navigateTo('revision')" id="bot-btn-revision" class="bot-nav-btn flex flex-col items-center gap-0.5 py-1 px-2.5 rounded-xl transition text-slate-500 hover:text-indigo-600 font-semibold">
      <i class="fa-solid fa-book-open text-base"></i>
      <span class="text-[10px]">المراجعة</span>
    </button>
    <button onclick="navigateTo('grammar')" id="bot-btn-grammar" class="bot-nav-btn flex flex-col items-center gap-0.5 py-1 px-2.5 rounded-xl transition text-slate-500 hover:text-indigo-600 font-semibold">
      <i class="fa-solid fa-ruler-combined text-base"></i>
      <span class="text-[10px]">القواعد</span>
    </button>
    <button onclick="navigateTo('unit1')" id="bot-btn-unit1" class="bot-nav-btn flex flex-col items-center gap-0.5 py-1 px-2.5 rounded-xl transition text-slate-500 hover:text-indigo-600 font-semibold">
      <span class="w-4 h-4 rounded-full bg-blue-500 text-white flex items-center justify-center text-[9px] font-bold">1</span>
      <span class="text-[10px]">الوحدة 1</span>
    </button>
    <button onclick="navigateTo('unit2')" id="bot-btn-unit2" class="bot-nav-btn flex flex-col items-center gap-0.5 py-1 px-2.5 rounded-xl transition text-slate-500 hover:text-indigo-600 font-semibold">
      <span class="w-4 h-4 rounded-full bg-amber-500 text-white flex items-center justify-center text-[9px] font-bold">2</span>
      <span class="text-[10px]">الوحدة 2</span>
    </button>
    <button onclick="navigateTo('unit3')" id="bot-btn-unit3" class="bot-nav-btn flex flex-col items-center gap-0.5 py-1 px-2.5 rounded-xl transition text-slate-500 hover:text-indigo-600 font-semibold">
      <span class="w-4 h-4 rounded-full bg-emerald-500 text-white flex items-center justify-center text-[9px] font-bold">3</span>
      <span class="text-[10px]">الوحدة 3</span>
    </button>
    <button onclick="navigateTo('exam')" id="bot-btn-exam" class="bot-nav-btn flex flex-col items-center gap-0.5 py-1 px-2.5 rounded-xl transition text-slate-500 hover:text-indigo-600 font-semibold">
      <i class="fa-solid fa-file-pen text-rose-500 text-base"></i>
      <span class="text-[10px]">الامتحان</span>
    </button>
  </nav>
"""
