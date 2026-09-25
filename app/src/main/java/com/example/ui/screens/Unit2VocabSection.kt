package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.FrenchCourseData
import com.example.data.VocabCategory
import com.example.data.VocabWord
import com.example.ui.components.AudioPlayButton
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange500
import com.example.ui.theme.Orange600
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import kotlinx.coroutines.launch

/**
 * قسم مفردات الوحدة الثانية: Repas & Restaurant (صفحة 38)
 * يضم الجداول الأربعة الرسمية: Vocabulaire, Les Verbes, Les personnages, Les lieux
 * مع 4 أوضاع تعليمية تفاعلية بنفس مواصفات الوحدة الأولى.
 */
@Composable
fun Unit2VocabSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedVocabMode by remember { mutableIntStateOf(0) }
    val modes = listOf(
        "🗂️ بطاقات الكلمات",
        "🔄 البطاقات المقلوبة",
        "🎯 اختبار الترجمة",
        "🧩 الحروف الناقصة"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.testTag("unit2_vocab_section")
    ) {
        // بطاقة الترويسة الرسمية لصفحة 38
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Orange100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "📖 Vocabulaire & Verbes",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "مفردات وأفعال وشخصيات وأماكن كتيّب المعهد (ص 38)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange600
                        )
                    }
                    Surface(
                        color = Orange600,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "p. 38 • 35 كلمة",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // شريط التبديل بين الأوضاع التعليمية
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    modes.forEachIndexed { index, title ->
                        val isSelected = selectedVocabMode == index
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Orange600 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Orange600 else Slate200),
                            modifier = Modifier.clickable {
                                audioHelper.playClick()
                                selectedVocabMode = index
                            }
                        ) {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate800,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // محتوى الوضع المختار
        when (selectedVocabMode) {
            0 -> Unit2VocabStudyCardsMode(audioHelper = audioHelper)
            1 -> Unit2VocabFlipCardsMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            2 -> Unit2VocabQuizTranslationMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            3 -> Unit2VocabMissingLettersGameMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// -----------------------------------------------------------------------------
// 1. 🗂️ بطاقات الكلمات الكاملة (Cartes d'étude)
// -----------------------------------------------------------------------------
@Composable
private fun Unit2VocabStudyCardsMode(
    audioHelper: AudioHelper
) {
    var selectedCategory by remember { mutableStateOf(VocabCategory.ALL) }
    val categories = listOf(
        VocabCategory.ALL to "الكل (35)",
        VocabCategory.EXPRESSION to "كلمات وتعبيرات 💬 (13)",
        VocabCategory.VERB to "أفعال ⚡ (10)",
        VocabCategory.PERSONNAGE to "شخصيات 👥 (6)",
        VocabCategory.LIEU to "أماكن 📍 (6)"
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // شريط تصفية الفئات الأربعة لصفحة 38
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { (cat, label) ->
                val isSelected = selectedCategory == cat
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) Orange600 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Orange600 else Slate200),
                    modifier = Modifier.clickable {
                        audioHelper.playClick()
                        selectedCategory = cat
                    }
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                        color = if (isSelected) Color.White else Slate700,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // قائمة الكلمات
        val filteredWords = remember(selectedCategory) {
            if (selectedCategory == VocabCategory.ALL) {
                FrenchCourseData.unit2VocabWords
            } else if (selectedCategory == VocabCategory.EXPRESSION) {
                FrenchCourseData.unit2VocabWords.filter {
                    it.category == VocabCategory.EXPRESSION ||
                            it.category == VocabCategory.FEMININE ||
                            it.category == VocabCategory.MASCULINE
                }
            } else {
                FrenchCourseData.unit2VocabWords.filter { it.category == selectedCategory }
            }
        }

        filteredWords.forEach { word ->
            Unit2VocabItemCard(word = word, audioHelper = audioHelper)
        }
    }
}

@Composable
private fun Unit2VocabItemCard(
    word: VocabWord,
    audioHelper: AudioHelper
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = word.french,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900
                    )
                    Unit2CategoryBadge(category = word.category)
                }

                AudioPlayButton(
                    textToSpeak = word.french,
                    audioHelper = audioHelper,
                    backgroundColor = Orange50,
                    iconTint = Orange600,
                    size = 32
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // المعنى باللغة العربية
            Text(
                text = "🇸🇦 ${word.arabic}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Emerald700
            )

            // المثال التوضيحي بالفرنسية والعربية
            if (word.exampleFr.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Slate50,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ex: ${word.exampleFr}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Slate800,
                                modifier = Modifier.weight(1f)
                            )
                            AudioPlayButton(
                                textToSpeak = word.exampleFr,
                                audioHelper = audioHelper,
                                backgroundColor = Color.White,
                                size = 26
                            )
                        }
                        if (word.exampleAr.isNotEmpty()) {
                            Text(
                                text = "← ${word.exampleAr}",
                                fontSize = 10.sp,
                                color = Slate600
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Unit2CategoryBadge(category: VocabCategory) {
    val (bgColor, textColor, label) = when (category) {
        VocabCategory.VERB -> Triple(Emerald50, Emerald700, "Verbe ⚡")
        VocabCategory.FEMININE -> Triple(Rose50, Rose600, "Fém. ♀")
        VocabCategory.MASCULINE -> Triple(Indigo50, Indigo700, "Masc. ♂")
        VocabCategory.PERSONNAGE -> Triple(Amber50, Amber950, "Personnage 👥")
        VocabCategory.LIEU -> Triple(Orange50, Orange600, "Lieu 📍")
        else -> Triple(Indigo50, Indigo700, "Vocabulaire 💬")
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.2f))
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

// -----------------------------------------------------------------------------
// 2. 🔄 البطاقات المقلوبة (Cartes mémoire / Flashcards)
// -----------------------------------------------------------------------------
@Composable
private fun Unit2VocabFlipCardsMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val words = remember { FrenchCourseData.unit2VocabWords.shuffled() }
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    val currentWord = words[currentIndex]
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "unit2_card_flip"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "بطاقة ${currentIndex + 1} من ${words.size}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Slate700
            )
        }

        // بطاقة الفلاش كارد القابلة للقلب
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isFlipped) Amber50 else Color.White
            ),
            border = BorderStroke(2.dp, if (isFlipped) Amber400 else Orange100),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .clickable {
                    audioHelper.playClick()
                    isFlipped = !isFlipped
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                contentAlignment = Alignment.Center
            ) {
                if (rotation <= 90f) {
                    // الوجه الأمامي: الكلمة بالفرنسية
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Unit2CategoryBadge(category = currentWord.category)
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = currentWord.french,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AudioPlayButton(
                                textToSpeak = currentWord.french,
                                audioHelper = audioHelper,
                                backgroundColor = Orange50,
                                iconTint = Orange600,
                                size = 36
                            )
                            Text(
                                text = "اضغط لقلب البطاقة ومعرفة المعنى 👆",
                                fontSize = 11.sp,
                                color = Slate500
                            )
                        }
                    }
                } else {
                    // الوجه الخلفي: الترجمة والمثال (معكوس لمحاذاة النص)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.graphicsLayer { rotationY = 180f }
                    ) {
                        Text(
                            text = currentWord.arabic,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Emerald700,
                            textAlign = TextAlign.Center
                        )
                        if (currentWord.exampleFr.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Ex: ${currentWord.exampleFr}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate800,
                                textAlign = TextAlign.Center
                            )
                            if (currentWord.exampleAr.isNotEmpty()) {
                                Text(
                                    text = currentWord.exampleAr,
                                    fontSize = 11.sp,
                                    color = Slate600,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "اضغط للعودة إلى الكلمة بالفرنسية",
                            fontSize = 10.sp,
                            color = Slate500
                        )
                    }
                }
            }
        }

        // أزرار التحكم
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    if (currentIndex > 0) {
                        isFlipped = false
                        currentIndex--
                    }
                },
                enabled = currentIndex > 0,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "السابق")
                Spacer(modifier = Modifier.width(4.dp))
                Text("السابق")
            }

            Button(
                onClick = {
                    if (currentIndex < words.size - 1) {
                        isFlipped = false
                        currentIndex++
                    } else {
                        // إعادة للبداية
                        isFlipped = false
                        currentIndex = 0
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (currentIndex < words.size - 1) "التالي" else "من البداية")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "التالي")
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. 🎯 اختبار الترجمة السريع (Quiz de traduction)
// -----------------------------------------------------------------------------
@Composable
private fun Unit2VocabQuizTranslationMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val allWords = FrenchCourseData.unit2VocabWords
    var questionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }

    // 10 أسئلة متنوعة (فرنسي ➔ عربي و عربي ➔ فرنسي)
    val quizQuestions = remember {
        allWords.shuffled().take(10).mapIndexed { idx, word ->
            val isFrToAr = idx % 2 == 0
            val questionText = if (isFrToAr) {
                "ما معنى « ${word.french} » باللغة العربية ؟"
            } else {
                "ما المقابل بالفرنسية لكلمة « ${word.arabic} » ؟"
            }
            val correctAnswer = if (isFrToAr) word.arabic else word.french

            // خيارات مضللة من نفس المجموعة
            val distractors = allWords
                .filter { it.id != word.id }
                .shuffled()
                .take(3)
                .map { if (isFrToAr) it.arabic else it.french }

            val options = (distractors + correctAnswer).shuffled()

            object {
                val question = questionText
                val speechWord = word.french
                val correct = correctAnswer
                val choices = options
                val explanation = "« ${word.french} » = ${word.arabic}"
            }
        }
    }

    if (questionIndex >= quizQuestions.size) {
        // بطاقة النتيجة النهائية
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, Orange100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(text = "🎉", fontSize = 42.sp)
                Text(
                    text = "أحسنت! أكملت اختبار مفردات ص 38",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "نتيجتك: $score / ${quizQuestions.size}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Orange600
                )
                Button(
                    onClick = {
                        audioHelper.playClick()
                        questionIndex = 0
                        score = 0
                        selectedOption = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "إعادة الاختبار")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("إعادة الاختبار بكلمات جديدة")
                }
            }
        }
    } else {
        val currentQ = quizQuestions[questionIndex]

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Orange50
                    ) {
                        Text(
                            text = "سؤال ${questionIndex + 1} / ${quizQuestions.size}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange600,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = currentQ.speechWord,
                        audioHelper = audioHelper,
                        backgroundColor = Orange50,
                        iconTint = Orange600,
                        size = 32
                    )
                }

                Text(
                    text = currentQ.question,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900
                )

                // الخيارات الأربعة
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    currentQ.choices.forEach { choice ->
                        val isChosen = selectedOption == choice
                        val isCorrect = choice == currentQ.correct
                        val bgColor = when {
                            selectedOption == null -> Slate50
                            isChosen && isCorrect -> Emerald100
                            isChosen && !isCorrect -> Rose100
                            isCorrect -> Emerald50
                            else -> Slate50
                        }
                        val borderColor = when {
                            selectedOption == null -> Slate200
                            isChosen && isCorrect -> Emerald600
                            isChosen && !isCorrect -> Rose500
                            isCorrect -> Emerald600
                            else -> Slate200
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = bgColor,
                            border = BorderStroke(1.5.dp, borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = selectedOption == null) {
                                    selectedOption = choice
                                    if (isCorrect) {
                                        score++
                                        audioHelper.playSuccessChime()
                                        onScoreEarned(1)
                                    } else {
                                        audioHelper.playErrorBuzz()
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = choice,
                                    fontSize = 13.sp,
                                    fontWeight = if (isChosen || isCorrect && selectedOption != null) FontWeight.Black else FontWeight.Bold,
                                    color = Slate900
                                )
                                if (selectedOption != null) {
                                    if (isCorrect) {
                                        Text("✔️", fontSize = 14.sp)
                                    } else if (isChosen) {
                                        Text("❌", fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // زر السؤال التالي بعد الإجابة
                if (selectedOption != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            selectedOption = null
                            questionIndex++
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("السؤال التالي ➔", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. 🧩 لعبة الحروف الناقصة (Jeu des lettres manquantes)
// -----------------------------------------------------------------------------
@Composable
private fun Unit2VocabMissingLettersGameMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    // قائمة كلمات ص 38 المختارة للعبة التهجئة
    val gameWords = remember {
        listOf(
            Triple("RESTAURANT", "مطعم", "Au r _ s _ a u r a n t"),
            Triple("MARIAGE", "زواج", "La fête de m _ r _ a g e"),
            Triple("PYRAMIDE", "هرم", "Une p _ r _ m i d e"),
            Triple("BOUGIE", "شمعة", "Une b _ u _ i e"),
            Triple("CLIENT", "زبون", "Un c _ i _ n t"),
            Triple("GARCON", "نادل / جرسون", "Un g _ r _ o n"),
            Triple("MAGASIN", "محل", "Au m _ g _ s i n"),
            Triple("INVITER", "يدعو", "I n v _ t _ r")
        ).shuffled()
    }

    var gameIndex by remember { mutableIntStateOf(0) }
    val guessedLetters = remember { mutableStateMapOf<Int, String>() }
    var solved by remember { mutableStateOf(false) }

    val current = gameWords[gameIndex]
    val fullWord = current.first
    val arabicHint = current.second

    // تحديد الحروف المحذوفة (مثلاً الحرفين الثاني والخامس)
    val hiddenIndices = remember(gameIndex) {
        listOf(1, 4.coerceAtMost(fullWord.length - 2))
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Orange100),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Orange50,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "كلمة ${gameIndex + 1} / ${gameWords.size}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Orange600,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                AudioPlayButton(
                    textToSpeak = fullWord.lowercase(),
                    audioHelper = audioHelper,
                    backgroundColor = Orange50,
                    iconTint = Orange600,
                    size = 32
                )
            }

            Text(
                text = "💡 المعنى: $arabicHint",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900
            )

            // مربعات الحروف
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                fullWord.forEachIndexed { index, char ->
                    val isHidden = hiddenIndices.contains(index)
                    val letterToShow = if (isHidden) guessedLetters[index] ?: "_" else char.toString()
                    val isFilled = isHidden && guessedLetters.containsKey(index)

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = when {
                            solved -> Emerald100
                            isFilled -> Amber50
                            isHidden -> Slate100
                            else -> Slate200
                        },
                        border = BorderStroke(
                            1.5.dp,
                            when {
                                solved -> Emerald600
                                isFilled -> Amber400
                                isHidden -> Orange500
                                else -> Slate300
                            }
                        ),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = letterToShow,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = if (solved) Emerald700 else Slate900
                            )
                        }
                    }
                }
            }

            // بنك الحروف للاختيار
            if (!solved) {
                Text(
                    text = "اختر الحرف المناسب لإكمال الكلمة :",
                    fontSize = 11.sp,
                    color = Slate600
                )

                // مجموعة الحروف المقترحة (الحروف الصحيحة + حروف عشوائية)
                val candidateLetters = remember(gameIndex) {
                    val correct = hiddenIndices.map { fullWord[it].toString() }
                    val distractors = listOf("A", "E", "I", "O", "U", "S", "R", "T", "N", "M")
                        .filter { !correct.contains(it) }
                        .shuffled()
                        .take(6 - correct.size)
                    (correct + distractors).shuffled()
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    candidateLetters.forEach { letter ->
                        Button(
                            onClick = {
                                // ابحث عن أول خانة فارغة
                                val emptyIndex = hiddenIndices.firstOrNull { !guessedLetters.containsKey(it) }
                                if (emptyIndex != null) {
                                    val isCorrectLetter = fullWord[emptyIndex].toString().equals(letter, ignoreCase = true)
                                    if (isCorrectLetter) {
                                        guessedLetters[emptyIndex] = letter
                                        audioHelper.playClick()
                                        // فحص هل اكتملت الكلمة
                                        if (hiddenIndices.all { guessedLetters.containsKey(it) }) {
                                            solved = true
                                            audioHelper.playSuccessChime()
                                            onScoreEarned(1)
                                        }
                                    } else {
                                        audioHelper.playErrorBuzz()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Slate100),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Slate300),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = letter, fontSize = 13.sp, fontWeight = FontWeight.Black, color = Slate900)
                            }
                        }
                    }
                }
            } else {
                // رسالة التهنئة وزر الانتقال
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "ممتاز! تهجئة صحيحة 🎉 (+1)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Emerald700
                    )
                    Button(
                        onClick = {
                            if (gameIndex < gameWords.size - 1) {
                                gameIndex++
                                guessedLetters.clear()
                                solved = false
                            } else {
                                gameIndex = 0
                                guessedLetters.clear()
                                solved = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (gameIndex < gameWords.size - 1) "الكلمة التالية ➔" else "إعادة اللعبة 🔄")
                    }
                }
            }
        }
    }
}
