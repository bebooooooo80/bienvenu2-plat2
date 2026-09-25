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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange600
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Teal100
import com.example.ui.theme.Teal50
import com.example.ui.theme.Teal600
import com.example.ui.theme.Violet100
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Unit1VocabSection(
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

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Header Banner for Page 14 Booklet
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
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
                            text = "مفردات وأفعال كتيّب المعهد (ص 14)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700
                        )
                    }
                    Surface(
                        color = Violet700,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "p. 14 • 25 كلمة",
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

                // Mode Selector Bar
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
                            color = if (isSelected) Violet700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Violet700 else Slate200),
                            modifier = Modifier.clickable { selectedVocabMode = index }
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

        // Selected Mode Content
        when (selectedVocabMode) {
            0 -> VocabStudyCardsMode(audioHelper = audioHelper)
            1 -> VocabFlipCardsMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            2 -> VocabQuizTranslationMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            3 -> VocabMissingLettersGameMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// -----------------------------------------------------------------------------
// 1. 🗂️ بطاقات الكلمات الكاملة (Cartes d'étude)
// -----------------------------------------------------------------------------
@Composable
private fun VocabStudyCardsMode(
    audioHelper: AudioHelper
) {
    var selectedCategory by remember { mutableStateOf(VocabCategory.ALL) }
    val categories = listOf(
        VocabCategory.ALL to "الكل (25)",
        VocabCategory.MASCULINE to "مذكر ♂ (6)",
        VocabCategory.FEMININE to "مؤنث ♀ (6)",
        VocabCategory.VERB to "أفعال ⚡ (10)",
        VocabCategory.EXPRESSION to "تعبيرات 💬 (3)"
    )

    val coroutineScope = rememberCoroutineScope()
    var isContinuousPlaying by remember { mutableStateOf(false) }
    var currentPlayingIndex by remember { mutableIntStateOf(-1) }
    var continuousJob by remember { mutableStateOf<Job?>(null) }

    val filteredWords = remember(selectedCategory) {
        if (selectedCategory == VocabCategory.ALL) {
            FrenchCourseData.unit1VocabWords
        } else {
            FrenchCourseData.unit1VocabWords.filter { it.category == selectedCategory }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Continuous playback & Category Chips
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Violet50),
            border = BorderStroke(1.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🎧 استماع تلقائي متتابع :",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Violet700
                    )

                    Button(
                        onClick = {
                            if (isContinuousPlaying) {
                                continuousJob?.cancel()
                                isContinuousPlaying = false
                                currentPlayingIndex = -1
                                audioHelper.stopSpeech()
                            } else {
                                isContinuousPlaying = true
                                continuousJob = coroutineScope.launch {
                                    for (i in filteredWords.indices) {
                                        currentPlayingIndex = i
                                        audioHelper.speak(filteredWords[i].french)
                                        delay(2400)
                                    }
                                    isContinuousPlaying = false
                                    currentPlayingIndex = -1
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isContinuousPlaying) Rose600 else Indigo600
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = if (isContinuousPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isContinuousPlaying) "إيقاف التشغيل" else "استمع للكل بالتتابع",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Category Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { (cat, label) ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) Indigo700 else Color.White,
                            border = BorderStroke(1.dp, if (isSelected) Indigo700 else Slate200),
                            modifier = Modifier.clickable { selectedCategory = cat }
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate700,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }

        // List of Vocabulary Cards
        filteredWords.forEachIndexed { index, word ->
            val isCurrentlyPlaying = isContinuousPlaying && currentPlayingIndex == index
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrentlyPlaying) Amber50 else Color.White
                ),
                border = BorderStroke(
                    if (isCurrentlyPlaying) 2.dp else 1.dp,
                    if (isCurrentlyPlaying) Amber400 else Slate200
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = word.french,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isCurrentlyPlaying) Amber950 else Slate900
                            )

                            // Category badge
                            val (catColor, catBg) = when (word.category) {
                                VocabCategory.MASCULINE -> Indigo700 to Indigo50
                                VocabCategory.FEMININE -> Rose600 to Rose50
                                VocabCategory.VERB -> Emerald600 to Emerald100
                                VocabCategory.EXPRESSION -> Orange600 to Orange50
                                else -> Violet700 to Violet50
                            }
                            Surface(
                                color = catBg,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = word.category.labelAr,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = catColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = word.arabic,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700
                        )

                        if (word.exampleFr.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "💡 ${word.exampleFr}",
                                fontSize = 11.sp,
                                color = Slate600
                            )
                        }
                    }

                    AudioPlayButton(
                        textToSpeak = word.french,
                        audioHelper = audioHelper,
                        backgroundColor = if (isCurrentlyPlaying) Amber400 else Violet50,
                        iconTint = if (isCurrentlyPlaying) Amber950 else Violet700,
                        size = 36
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 2. 🔄 البطاقات المقلوبة (Flip Cards) ثلاثية الأبعاد
// -----------------------------------------------------------------------------
@Composable
private fun VocabFlipCardsMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val words = FrenchCourseData.unit1VocabWords
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    // Mastery tracking
    val masteredWords = remember { mutableStateListOf<String>() }
    val toReviewWords = remember { mutableStateListOf<String>() }

    val currentWord = words[currentIndex]

    // Smooth 3D rotation animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
        label = "flipAnimation"
    )

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // 3D Flip Card Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { isFlipped = !isFlipped }
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                },
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                // FRONT SIDE (French)
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, Violet600),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Violet100,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "${currentIndex + 1} / ${words.size}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Violet700,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            Surface(
                                color = Slate100,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = currentWord.category.labelAr,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate700,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = currentWord.french,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            AudioPlayButton(
                                textToSpeak = currentWord.french,
                                audioHelper = audioHelper,
                                backgroundColor = Violet50,
                                iconTint = Violet700,
                                size = 42
                            )
                        }

                        Text(
                            text = "👆 اضغط هنا لقلب البطاقة ومعرفة المعنى",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )
                    }
                }
            } else {
                // BACK SIDE (Arabic & Example) - rotated 180 so it appears upright
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Indigo50),
                    border = BorderStroke(2.dp, Indigo600),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🇸🇦 الترجمة والمعنى :",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo700
                            )

                            AudioPlayButton(
                                textToSpeak = currentWord.french,
                                audioHelper = audioHelper,
                                backgroundColor = Indigo100,
                                iconTint = Indigo700,
                                size = 32
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = currentWord.arabic,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Violet700,
                                textAlign = TextAlign.Center
                            )

                            if (currentWord.exampleFr.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, Indigo100)
                                ) {
                                    Text(
                                        text = "💬 ${currentWord.exampleFr}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Slate800,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = "👆 اضغط للرجوع للوجه الأمامي",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )
                    }
                }
            }
        }

        // Self-evaluation buttons (👍 حفظتها / 🔁 سأراجعها)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    if (!masteredWords.contains(currentWord.id)) {
                        masteredWords.add(currentWord.id)
                        toReviewWords.remove(currentWord.id)
                        onScoreEarned(1)
                    }
                    if (currentIndex < words.size - 1) {
                        isFlipped = false
                        currentIndex++
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("👍 حفظتها", fontSize = 12.sp, fontWeight = FontWeight.Black)
            }

            OutlinedButton(
                onClick = {
                    if (!toReviewWords.contains(currentWord.id)) {
                        toReviewWords.add(currentWord.id)
                        masteredWords.remove(currentWord.id)
                    }
                    if (currentIndex < words.size - 1) {
                        isFlipped = false
                        currentIndex++
                    }
                },
                border = BorderStroke(1.5.dp, Orange600),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, tint = Orange600, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("🔁 سأراجعها", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Orange600)
            }
        }

        // Navigation (Previous, Random, Next)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (currentIndex > 0) {
                        isFlipped = false
                        currentIndex--
                    }
                },
                enabled = currentIndex > 0,
                colors = ButtonDefaults.buttonColors(containerColor = Slate200, disabledContainerColor = Slate100),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Slate800, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("السابق", fontSize = 11.sp, color = Slate800, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    isFlipped = false
                    currentIndex = words.indices.random()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Violet50),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Violet100)
            ) {
                Text("🎲 عشوائي", fontSize = 11.sp, color = Violet700, fontWeight = FontWeight.Black)
            }

            Button(
                onClick = {
                    if (currentIndex < words.size - 1) {
                        isFlipped = false
                        currentIndex++
                    }
                },
                enabled = currentIndex < words.size - 1,
                colors = ButtonDefaults.buttonColors(containerColor = Slate200, disabledContainerColor = Slate100),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("التالي", fontSize = 11.sp, color = Slate800, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Slate800, modifier = Modifier.size(16.dp))
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. 🎯 اختيار من متعدد وترجمة بالاتجاهين (QCM & Traduction)
// -----------------------------------------------------------------------------
@Composable
private fun VocabQuizTranslationMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val words = FrenchCourseData.unit1VocabWords
    var isFrenchToArabic by remember { mutableStateOf(true) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }

    val currentWord = words[currentIndex]

    // Generate 4 options (1 correct + 3 smart distractors)
    val options = remember(currentIndex, isFrenchToArabic) {
        val distractors = words.filter { it.id != currentWord.id }.shuffled().take(3)
        val allFour = (distractors + currentWord).shuffled()
        allFour.map { if (isFrenchToArabic) it.arabic else it.french }
    }

    val correctOptionText = if (isFrenchToArabic) currentWord.arabic else currentWord.french
    val correctIndex = options.indexOf(correctOptionText)

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Mode Switch (FR -> AR vs AR -> FR)
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Slate200)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isFrenchToArabic) Violet700 else Color.Transparent,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            isFrenchToArabic = true
                            selectedOptionIndex = null
                            isSubmitted = false
                        }
                ) {
                    Text(
                        text = "🇫🇷 ➔ 🇪🇬 فرنسي إلى عربي",
                        fontSize = 11.sp,
                        fontWeight = if (isFrenchToArabic) FontWeight.Black else FontWeight.Bold,
                        color = if (isFrenchToArabic) Color.White else Slate700,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (!isFrenchToArabic) Violet700 else Color.Transparent,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            isFrenchToArabic = false
                            selectedOptionIndex = null
                            isSubmitted = false
                        }
                ) {
                    Text(
                        text = "🇪🇬 ➔ 🇫🇷 عربي إلى فرنسي",
                        fontSize = 11.sp,
                        fontWeight = if (!isFrenchToArabic) FontWeight.Black else FontWeight.Bold,
                        color = if (!isFrenchToArabic) Color.White else Slate700,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        // Quiz Question Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "السؤال ${currentIndex + 1} من ${words.size}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )

                    AudioPlayButton(
                        textToSpeak = currentWord.french,
                        audioHelper = audioHelper,
                        backgroundColor = Violet50,
                        size = 34
                    )
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Violet50,
                    border = BorderStroke(1.dp, Violet100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isFrenchToArabic) "ما معنى الكلمة الآتية بالعربية ؟" else "ما المرادف الفرنسي لهذه الكلمة ؟",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (isFrenchToArabic) currentWord.french else currentWord.arabic,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                    }
                }

                // Options (4 Choices)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    options.forEachIndexed { index, optionText ->
                        val isSelected = selectedOptionIndex == index
                        val isThisCorrect = index == correctIndex

                        val (bgColor, borderColor, textColor) = when {
                            isSubmitted && isThisCorrect -> Triple(Emerald100, Emerald500, Emerald600)
                            isSubmitted && isSelected && !isThisCorrect -> Triple(Rose100, Rose500, Rose600)
                            isSelected -> Triple(Violet100, Violet600, Violet700)
                            else -> Triple(Slate50, Slate200, Slate800)
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = bgColor,
                            border = BorderStroke(if (isSelected || (isSubmitted && isThisCorrect)) 2.dp else 1.dp, borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isSubmitted) {
                                    selectedOptionIndex = index
                                    isSubmitted = true
                                    if (index == correctIndex) {
                                        onScoreEarned(1)
                                        audioHelper.speak(currentWord.french)
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = optionText,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected || (isSubmitted && isThisCorrect)) FontWeight.Black else FontWeight.Medium,
                                    color = textColor
                                )

                                if (isSubmitted) {
                                    if (isThisCorrect) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Emerald600, modifier = Modifier.size(20.dp))
                                    } else if (isSelected) {
                                        Icon(Icons.Default.Close, contentDescription = null, tint = Rose600, modifier = Modifier.size(20.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Next Button
                if (isSubmitted) {
                    Button(
                        onClick = {
                            selectedOptionIndex = null
                            isSubmitted = false
                            currentIndex = (currentIndex + 1) % words.size
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Violet700),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("الكلمة التالية ➔", fontSize = 13.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. 🧩 لعبة الحروف الناقصة الذكية (Lettres Manquantes)
// -----------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VocabMissingLettersGameMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    // Choose words suitable for missing letter game (no spaces or hyphens)
    val candidateWords = remember {
        FrenchCourseData.unit1VocabWords.filter {
            !it.french.contains(" ") && !it.french.contains("≠") && it.french.length >= 4
        }
    }

    var wordIndex by remember { mutableIntStateOf(0) }
    val currentWord = candidateWords[wordIndex]
    val targetLetters = remember(currentWord) {
        currentWord.french.uppercase().toCharArray().toList()
    }

    // Determine missing letter indices (2 for short, 3 for long words)
    val missingCount = if (targetLetters.size <= 6) 2 else 3
    val missingIndices = remember(currentWord) {
        val indices = targetLetters.indices.filter { targetLetters[it].isLetter() }
        indices.shuffled().take(missingCount).sorted()
    }

    // User's filled answers per missing index: map of missingIndex to filled char
    val filledAnswers = remember(currentWord) { mutableStateMapOf<Int, Char?>() }

    // Letter bank: missing chars + 3 decoy letters
    val letterBank = remember(currentWord) {
        val decoys = listOf('A', 'E', 'I', 'O', 'U', 'R', 'S', 'T', 'L', 'N', 'P')
            .filter { decoy -> !targetLetters.contains(decoy) }
            .shuffled()
            .take(3)
        val bank = missingIndices.map { targetLetters[it] } + decoys
        bank.shuffled()
    }

    // Check if word is completed and correct
    val isCompleted = missingIndices.all { filledAnswers[it] != null }
    val isCorrect = isCompleted && missingIndices.all { filledAnswers[it] == targetLetters[it] }

    LaunchedEffect(isCorrect) {
        if (isCorrect) {
            audioHelper.speak(currentWord.french)
            onScoreEarned(2)
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Game Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "🧩 لعبة الحروف الناقصة",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "المعنى: ${currentWord.arabic}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = currentWord.french,
                        audioHelper = audioHelper,
                        backgroundColor = Violet50,
                        size = 36
                    )
                }

                // Interactive Letter Slots
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    targetLetters.forEachIndexed { index, actualChar ->
                        val isMissing = missingIndices.contains(index)
                        val filledChar = filledAnswers[index]

                        if (isMissing) {
                            // Missing slot
                            val isCorrectSlot = isCorrect || (filledChar != null && filledChar == actualChar)
                            val isWrongSlot = isCompleted && !isCorrect && filledChar != actualChar

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = when {
                                    isCorrectSlot -> Emerald100
                                    isWrongSlot -> Rose100
                                    filledChar != null -> Violet100
                                    else -> Slate100
                                },
                                border = BorderStroke(
                                    2.dp,
                                    when {
                                        isCorrectSlot -> Emerald500
                                        isWrongSlot -> Rose500
                                        filledChar != null -> Violet600
                                        else -> Violet600
                                    }
                                ),
                                modifier = Modifier
                                    .padding(3.dp)
                                    .size(42.dp)
                                    .clickable {
                                        if (filledChar != null) {
                                            filledAnswers.remove(index)
                                        }
                                    }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = filledChar?.toString() ?: "_",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = when {
                                            isCorrectSlot -> Emerald600
                                            isWrongSlot -> Rose600
                                            filledChar != null -> Violet700
                                            else -> Slate500
                                        }
                                    )
                                }
                            }
                        } else {
                            // Existing visible letter
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Slate100,
                                border = BorderStroke(1.dp, Slate200),
                                modifier = Modifier
                                    .padding(3.dp)
                                    .size(42.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = actualChar.toString(),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800
                                    )
                                }
                            }
                        }
                    }
                }

                // Letter Bank
                Text(
                    text = "👇 اختر الحروف لإكمال الكلمة :",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate600
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    letterBank.forEach { letter ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Violet50,
                            border = BorderStroke(1.5.dp, Violet100),
                            modifier = Modifier
                                .size(44.dp)
                                .clickable {
                                    // Find first empty missing slot
                                    val emptySlot = missingIndices.firstOrNull { filledAnswers[it] == null }
                                    if (emptySlot != null) {
                                        filledAnswers[emptySlot] = letter
                                    }
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = letter.toString(),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Violet700
                                )
                            }
                        }
                    }
                }

                // Controls: Erase, Hint, Reset
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            // Remove last filled
                            val lastFilled = missingIndices.lastOrNull { filledAnswers[it] != null }
                            if (lastFilled != null) {
                                filledAnswers.remove(lastFilled)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("⌫ مسح", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            // Fill one empty slot with correct letter
                            val emptySlot = missingIndices.firstOrNull { filledAnswers[it] == null }
                            if (emptySlot != null) {
                                filledAnswers[emptySlot] = targetLetters[emptySlot]
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("💡 تلميح", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            filledAnswers.clear()
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("🔄 إعادة", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Success Celebration Banner
                AnimatedVisibility(visible = isCorrect) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Emerald100,
                        border = BorderStroke(1.5.dp, Emerald500),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "🎉 Bravo ! ممتاز ورائع جداً !",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = Emerald600
                            )
                            Text(
                                text = "${currentWord.french} = ${currentWord.arabic} (+2 نقاط)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate800
                            )
                            Button(
                                onClick = {
                                    filledAnswers.clear()
                                    wordIndex = (wordIndex + 1) % candidateWords.size
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("الكلمة التالية ➔", fontSize = 12.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}
