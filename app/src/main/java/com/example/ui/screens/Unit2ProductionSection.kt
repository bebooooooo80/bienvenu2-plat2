package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.BookletOuVasTuItem
import com.example.data.BookletQuiParleItem
import com.example.data.FaisDesPhrasesItem
import com.example.data.FrenchCourseData
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Amber300
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald300
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
import com.example.ui.theme.Teal100
import com.example.ui.theme.Teal50
import com.example.ui.theme.Teal600
import com.example.ui.theme.Violet100
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

/**
 * شاشة الإنتاج اللغوي وتكوين الجمل والتعبير للوحدة الثانية (ص 39 و 40)
 * Production & Composition (p. 39-40)
 */
@Composable
fun Unit2ProductionSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val quiParleList = FrenchCourseData.unit2QuiParleItems
    val ouVasTuList = FrenchCourseData.unit2OuVasTuItems
    val faisPhrasesList = FrenchCourseData.unit2FaisDesPhrasesItems
    val compositionTopic = FrenchCourseData.unit2CompositionTopic

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "👥 1- Qui parle ? (ص 39)",
        "📍 2- Où vas-tu pour...? (ص 39)",
        "🔤 3- Fais des phrases (ص 39-40)",
        "✍️ 4- Composition (ص 40)"
    )

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

        // هيدر كتيّب المعهد ص 39-40
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100)
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
                            text = "✍️ Production & Composition (ص 39-40)",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "كتيّب منهج Bienvenu 2 • من يتحدث؟ أين تذهب؟ كوّن جملاً وموضوع تعبير المطعم",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )
                    }
                    Surface(
                        color = Indigo700,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "p. 39-40",
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

                // شريط التبويبات الأربعة
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Indigo700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Indigo700 else Slate200),
                            modifier = Modifier.clickable { selectedTab = index }
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

        // محتوى التبويب المختار
        when (selectedTab) {
            0 -> Unit2QuiParleMode(quiParleList, audioHelper, onScoreEarned)
            1 -> Unit2OuVasTuMode(ouVasTuList, audioHelper, onScoreEarned)
            2 -> Unit2FaisDesPhrasesMode(faisPhrasesList, audioHelper, onScoreEarned)
            3 -> Unit2CompositionMode(compositionTopic, audioHelper, onScoreEarned)
        }
    }
}

// =============================================================================
// 1. تبويب : 1- Qui parle ? (ص 39)
// =============================================================================
@Composable
private fun Unit2QuiParleMode(
    items: List<BookletQuiParleItem>,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val userAnswers = remember { mutableStateMapOf<String, String>() }
    val answeredCorrectly = remember { mutableStateMapOf<String, Boolean>() }
    var solvedCount by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة توجيهية
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.dp, Indigo100)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Indigo600,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🗣️", fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "1- Qui parle ? (من المتحدث في الموقف؟)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Indigo900
                    )
                    Text(
                        text = "اقرأ العبارة التالية وحدد الشخصية التي تقول هذا الكلام في كتيّب ص 39 ($solvedCount/${items.size} صحيح)",
                        fontSize = 12.sp,
                        color = Indigo700
                    )
                }
            }
        }

        items.forEach { item ->
            var showArabic by remember { mutableStateOf(false) }
            val selectedOption = userAnswers[item.id]
            val isDone = answeredCorrectly[item.id] == true

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(
                    1.5.dp,
                    if (isDone) Emerald300 else Slate200
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // شريط رقم السؤال + الصوت + الترجمة
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = if (isDone) Emerald600 else Indigo700,
                            shape = CircleShape,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${item.number}",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TranslateIconButton(
                                isTranslated = showArabic,
                                onClick = { showArabic = !showArabic }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            AudioPlayButton(
                                textToSpeak = item.quoteFr,
                                audioHelper = audioHelper,
                                size = 36
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // نص العبارة الفرنسية
                    Surface(
                        color = Slate50,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item.quoteFr,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    // ترجمة العبارة بالعربي
                    ArabicTranslationBanner(
                        translation = item.quoteAr,
                        visible = showArabic
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // الخيارات التفاعلية
                    Text(
                        text = "اختر الشخصية الصحيحة:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    item.options.forEach { opt ->
                        val isSelected = selectedOption == opt
                        val isCorrectOption = opt.equals(item.speakerFr, ignoreCase = true) ||
                                item.alternativeSpeakersFr.any { it.equals(opt, ignoreCase = true) }

                        val optColor = when {
                            isSelected && isCorrectOption -> Emerald50
                            isSelected && !isCorrectOption -> Rose50
                            else -> Slate50
                        }
                        val borderColor = when {
                            isSelected && isCorrectOption -> Emerald600
                            isSelected && !isCorrectOption -> Rose500
                            else -> Slate200
                        }
                        val textColor = when {
                            isSelected && isCorrectOption -> Emerald700
                            isSelected && !isCorrectOption -> Rose600
                            else -> Slate800
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = optColor,
                            border = BorderStroke(1.dp, borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable(enabled = !isDone) {
                                    userAnswers[item.id] = opt
                                    if (isCorrectOption) {
                                        answeredCorrectly[item.id] = true
                                        solvedCount++
                                        audioHelper.playSuccessChime()
                                        onScoreEarned(10)
                                    } else {
                                        audioHelper.playErrorBuzz()
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = opt,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                    color = textColor
                                )

                                if (isSelected && isCorrectOption) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Correct",
                                        tint = Emerald600,
                                        modifier = Modifier.size(18.dp)
                                    )
                                } else if (isSelected && !isCorrectOption) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Incorrect",
                                        tint = Rose500,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    // التوضيح والشرح عند الإجابة الصحيحة
                    if (isDone) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = Emerald50,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Emerald300),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Emerald600,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "إجابة نموذجية: ${item.speakerFr} (${item.speakerAr})",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Emerald700
                                    )
                                }
                                Text(
                                    text = item.situationContextAr,
                                    fontSize = 12.sp,
                                    color = Slate700,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// 2. تبويب : 2- Où vas – tu Pour ......? (ص 39)
// =============================================================================
@Composable
private fun Unit2OuVasTuMode(
    items: List<BookletOuVasTuItem>,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val userAnswers = remember { mutableStateMapOf<String, String>() }
    val answeredCorrectly = remember { mutableStateMapOf<String, Boolean>() }
    var solvedCount by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة توجيهية
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Teal50),
            border = BorderStroke(1.dp, Teal100)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Teal600,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "📍", fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "2- Où vas – tu Pour ......? (أين تذهب لكي...؟)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Indigo900
                    )
                    Text(
                        text = "اختر المكان المناسب وحرف الجر لكل نشاط في ص 39 ($solvedCount/${items.size} صحيح)",
                        fontSize = 12.sp,
                        color = Slate700
                    )
                }
            }
        }

        items.forEach { item ->
            var showArabic by remember { mutableStateOf(false) }
            val selectedOption = userAnswers[item.id]
            val isDone = answeredCorrectly[item.id] == true

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(
                    1.5.dp,
                    if (isDone) Emerald300 else Slate200
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // شريط رقم السؤال + الصوت + الترجمة
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = if (isDone) Emerald600 else Teal600,
                            shape = CircleShape,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${item.number}",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TranslateIconButton(
                                isTranslated = showArabic,
                                onClick = { showArabic = !showArabic }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            AudioPlayButton(
                                textToSpeak = "Pour ${item.activityFr} Je vais ${item.expectedAnswerFr}",
                                audioHelper = audioHelper,
                                size = 36
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // النشاط بالفرنسية
                    Surface(
                        color = Slate50,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Pour ${item.activityFr}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo900
                            )
                            Text(
                                text = "➔ .....................................................",
                                fontSize = 13.sp,
                                color = Slate500,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    // الترجمة العربية
                    ArabicTranslationBanner(
                        translation = item.activityAr,
                        visible = showArabic
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "اختر المكان المناسب:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    item.options.forEach { opt ->
                        val isSelected = selectedOption == opt
                        val isCorrectOption = opt.equals(item.expectedAnswerFr, ignoreCase = true) ||
                                item.alternativeAnswersFr.any { it.equals(opt, ignoreCase = true) }

                        val optColor = when {
                            isSelected && isCorrectOption -> Emerald50
                            isSelected && !isCorrectOption -> Rose50
                            else -> Slate50
                        }
                        val borderColor = when {
                            isSelected && isCorrectOption -> Emerald600
                            isSelected && !isCorrectOption -> Rose500
                            else -> Slate200
                        }
                        val textColor = when {
                            isSelected && isCorrectOption -> Emerald700
                            isSelected && !isCorrectOption -> Rose600
                            else -> Slate800
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = optColor,
                            border = BorderStroke(1.dp, borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable(enabled = !isDone) {
                                    userAnswers[item.id] = opt
                                    if (isCorrectOption) {
                                        answeredCorrectly[item.id] = true
                                        solvedCount++
                                        audioHelper.playSuccessChime()
                                        onScoreEarned(10)
                                    } else {
                                        audioHelper.playErrorBuzz()
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = opt,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                    color = textColor
                                )

                                if (isSelected && isCorrectOption) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Correct",
                                        tint = Emerald600,
                                        modifier = Modifier.size(18.dp)
                                    )
                                } else if (isSelected && !isCorrectOption) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Incorrect",
                                        tint = Rose500,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    // التوضيح النحوي والمكاني عند الحل الصحيح
                    if (isDone) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = Emerald50,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Emerald300),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Emerald600,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "الحل النموذجي: ${item.expectedAnswerFr} (${item.answerAr})",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Emerald700
                                    )
                                }
                                Text(
                                    text = item.explanationAr,
                                    fontSize = 12.sp,
                                    color = Slate700,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// 3. تبويب : 3- Fais des Phrases? (ص 39-40)
// =============================================================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Unit2FaisDesPhrasesMode(
    items: List<FaisDesPhrasesItem>,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentItem = items[currentIndex]

    val assembledWords = remember(currentItem.id) { mutableStateListOf<String>() }
    val availableWords = remember(currentItem.id) {
        mutableStateListOf<String>().apply { addAll(currentItem.scrambledWords) }
    }
    var isSolved by remember(currentItem.id) { mutableStateOf(false) }
    var isError by remember(currentItem.id) { mutableStateOf(false) }
    var showGrammarTip by remember(currentItem.id) { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة توجيهية وشريط التنقل بين الجمل الخمس
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.dp, Amber300)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "3- Fais des Phrases ? (كوّن جملاً مفيدة)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Amber950
                        )
                        Text(
                            text = "الكلمات المعطاة بكتيّب ص 39-40 • رتّب الكلمات لتكوين جملة صحيحة قواعدياً",
                            fontSize = 11.sp,
                            color = Amber800
                        )
                    }
                    Surface(
                        color = Amber800,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "الجملة ${currentIndex + 1} من ${items.size}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // أزرار أرقام الجمل
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items.forEachIndexed { idx, itm ->
                        val isSelected = currentIndex == idx
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) Amber800 else Color.White,
                            border = BorderStroke(1.dp, if (isSelected) Amber800 else Slate300),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { currentIndex = idx }
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${idx + 1}",
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                    color = if (isSelected) Color.White else Slate700
                                )
                            }
                        }
                    }
                }
            }
        }

        // بطاقة الجملة الحالية
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, if (isSolved) Emerald300 else Slate200),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                // هيدر السؤال المعطى في الكتيّب
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Amber800,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = currentItem.pageReference,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = currentItem.modelSentenceFr,
                        audioHelper = audioHelper,
                        size = 36
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // الكلمات المعطاة في رأس السؤال
                Surface(
                    color = Slate50,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "${currentItem.number}- ${currentItem.promptFr}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "المعنى: ${currentItem.promptAr}",
                            fontSize = 12.sp,
                            color = Slate600,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // منطقة تجميع الجملة (الصندوق الذي تنزل فيه الكلمات)
                Text(
                    text = "الجملة المركّبة (انقر على الكلمات لإضافتها أو إرجاعها):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate700
                )
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = when {
                        isSolved -> Emerald50
                        isError -> Rose50
                        else -> Slate100
                    },
                    border = BorderStroke(
                        1.5.dp,
                        when {
                            isSolved -> Emerald600
                            isError -> Rose500
                            else -> Slate300
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        if (assembledWords.isEmpty()) {
                            Text(
                                text = "اضغط على الكلمات بالأسفل لتركيب الجملة هنا...",
                                fontSize = 13.sp,
                                color = Slate500,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                assembledWords.forEachIndexed { index, word ->
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSolved) Emerald600 else Indigo700,
                                        modifier = Modifier.clickable(enabled = !isSolved) {
                                            assembledWords.removeAt(index)
                                            availableWords.add(word)
                                            isError = false
                                        }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = word,
                                                color = Color.White,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            if (!isSolved) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Remove",
                                                    tint = Color.White.copy(alpha = 0.8f),
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // بنك الكلمات المتاحة للاختيار
                if (availableWords.isNotEmpty() && !isSolved) {
                    Text(
                        text = "بنك الكلمات المتاحة:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate500
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        availableWords.forEachIndexed { index, word ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Slate100,
                                border = BorderStroke(1.dp, Slate300),
                                modifier = Modifier.clickable {
                                    assembledWords.add(word)
                                    availableWords.removeAt(index)
                                    isError = false
                                }
                            ) {
                                Text(
                                    text = word,
                                    color = Slate800,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // أزرار التحقق وإعادة المحاولة
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!isSolved) {
                        Button(
                            onClick = {
                                val userSentence = assembledWords.joinToString(" ").trim().replace(Regex("\\s+"), " ")
                                val targetSentence = currentItem.correctOrder.joinToString(" ").trim().replace(Regex("\\s+"), " ")
                                val isMatch = userSentence.equals(targetSentence, ignoreCase = true) || assembledWords.toList() == currentItem.correctOrder
                                if (isMatch) {
                                    isSolved = true
                                    isError = false
                                    audioHelper.playSuccessChime()
                                    onScoreEarned(15)
                                } else {
                                    isError = true
                                    audioHelper.playErrorBuzz()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f),
                            enabled = assembledWords.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "تحقق من الترتيب")
                        }

                        OutlinedButton(
                            onClick = {
                                assembledWords.clear()
                                availableWords.clear()
                                availableWords.addAll(currentItem.scrambledWords)
                                isError = false
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else {
                        // عند الحل بنجاح: زر الانتقال للجملة التالية
                        Button(
                            onClick = {
                                if (currentIndex < items.size - 1) {
                                    currentIndex++
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            enabled = currentIndex < items.size - 1
                        ) {
                            Text(text = if (currentIndex < items.size - 1) "الانتقال للجملة التالية ➔" else "أحسنت! أكملت كل الجمل 🎉")
                        }
                    }
                }

                // رسالة الخطأ
                if (isError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Rose50,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Rose500),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Rose500,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "الترتيب غير صحيح، انتبه لتصريف الفعل وحرف الجر ثم حاول ثانية!",
                                fontSize = 12.sp,
                                color = Rose600,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // عند الحل: عرض الجملة النموذجية والجمل البديلة وتصريف الفعل
                if (isSolved) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = Emerald50,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Emerald300),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Emerald600,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "الجملة النموذجية المعتمدة:",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Emerald700
                                )
                            }
                            Text(
                                text = currentItem.modelSentenceFr,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo900,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                text = currentItem.modelSentenceAr,
                                fontSize = 13.sp,
                                color = Slate700,
                                modifier = Modifier.padding(top = 2.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // جمل بديلة للامتحان
                            Text(
                                text = "💡 جمل بديلة صحيحة للامتحان:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Emerald700
                            )
                            currentItem.alternativeSentences.forEach { alt ->
                                Text(
                                    text = "• ${alt.first}  (${alt.second})",
                                    fontSize = 12.sp,
                                    color = Slate800,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // زر عرض القاعدة النحوية وتصريف الفعل
                    Surface(
                        color = Slate50,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showGrammarTip = !showGrammarTip }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (showGrammarTip) "إخفاء قاعدة وتصريف الفعل ▲" else "عرض قاعدة وتصريف فعل (${currentItem.verbInfinitive}) ▼",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo700
                            )
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Indigo600,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    AnimatedVisibility(visible = showGrammarTip) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            Surface(
                                color = Indigo50,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Indigo100),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = currentItem.grammarTipAr,
                                        fontSize = 12.sp,
                                        color = Slate800
                                    )

                                    if (currentItem.conjugationSamples.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "تصريف الفعل (${currentItem.verbInfinitive}):",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Indigo900
                                        )
                                        FlowRow(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp),
                                            modifier = Modifier.padding(top = 4.dp)
                                        ) {
                                            currentItem.conjugationSamples.forEach { conj ->
                                                Surface(
                                                    color = Color.White,
                                                    shape = RoundedCornerShape(6.dp),
                                                    border = BorderStroke(1.dp, Indigo100)
                                                ) {
                                                    Text(
                                                        text = "${conj.first} (${conj.second})",
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = Indigo900,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// 4. تبويب : 4- Composition (ص 40)
// =============================================================================
@Composable
private fun Unit2CompositionMode(
    topic: com.example.data.CompositionTopic,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    var subTab by remember { mutableIntStateOf(0) }
    val subTabs = listOf(
        "📖 الموضوع النموذجي",
        "🧩 رتّب الجمل",
        "✍️ أكمل الفراغات",
        "💡 إرشادات الامتحان"
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة موضوع التعبير الرئيسي ص 40
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Violet50),
            border = BorderStroke(1.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "🍽️ ${topic.titleFr}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "كتيّب ص 40: ${topic.instructionFr}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700
                        )
                        Text(
                            text = topic.instructionAr,
                            fontSize = 12.sp,
                            color = Slate700,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Surface(
                        color = Violet700,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "ص 40",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // التبويبات الداخلية للموضوع
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    subTabs.forEachIndexed { index, title ->
                        val isSelected = subTab == index
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Violet700 else Color.White,
                            border = BorderStroke(1.dp, if (isSelected) Violet700 else Slate200),
                            modifier = Modifier.clickable { subTab = index }
                        ) {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate800,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // محتوى التبويب الداخلي
        when (subTab) {
            0 -> CompositionNotebookSubMode(topic, audioHelper, clipboardManager, context)
            1 -> CompositionOrderingSubMode(topic, audioHelper, onScoreEarned)
            2 -> CompositionFillBlanksSubMode(topic, audioHelper, onScoreEarned)
            3 -> CompositionTipsSubMode()
        }
    }
}

// -----------------------------------------------------------------------------
// 4.1 الموضوع النموذجي المكتوب
// -----------------------------------------------------------------------------
@Composable
private fun CompositionNotebookSubMode(
    topic: com.example.data.CompositionTopic,
    audioHelper: AudioHelper,
    clipboardManager: androidx.compose.ui.platform.ClipboardManager,
    context: android.content.Context
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // شريط العنوان وأزرار المشاركة والنسخ
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📝 نص الموضوع النموذجي الكامل:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo900
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(
                        onClick = {
                            val fullText = topic.sentences.joinToString("\n") { it.french }
                            clipboardManager.setText(AnnotatedString(fullText))
                            Toast.makeText(context, "تم نسخ موضوع التعبير بنجاح!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Copy",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "نسخ الموضوع", fontSize = 11.sp)
                    }

                    AudioPlayButton(
                        textToSpeak = topic.sentences.joinToString(" ") { it.french },
                        audioHelper = audioHelper,
                        size = 36
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // عناصر الموضوع الأساسية (Checklist)
            Surface(
                color = Amber50,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Amber300),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "📌 العناصر والمحتويات المطلوبة في كتيّب ص 40:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Amber900
                    )
                    topic.requiredElements.forEach { el ->
                        Text(
                            text = "✓ ${el.first} ➔ ${el.second}",
                            fontSize = 11.sp,
                            color = Slate800,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // الجمل سطراً بسطر مع النطق الصوتي والترجمة
            topic.sentences.forEach { sentence ->
                var showAr by remember { mutableStateOf(false) }

                Surface(
                    color = Slate50,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Violet700,
                                shape = CircleShape,
                                modifier = Modifier.size(22.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${sentence.order}",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TranslateIconButton(
                                    isTranslated = showAr,
                                    onClick = { showAr = !showAr }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                AudioPlayButton(
                                    textToSpeak = sentence.french,
                                    audioHelper = audioHelper,
                                    size = 30
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = sentence.french,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )

                        AnimatedVisibility(visible = showAr) {
                            Text(
                                text = sentence.arabic,
                                fontSize = 12.sp,
                                color = Emerald700,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        if (sentence.hint.isNotEmpty()) {
                            Text(
                                text = "💡 ${sentence.hint}",
                                fontSize = 11.sp,
                                color = Slate500,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4.2 رتّب جمل الموضوع
// -----------------------------------------------------------------------------
@Composable
private fun CompositionOrderingSubMode(
    topic: com.example.data.CompositionTopic,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val sentences = remember { topic.sentences.shuffled() }
    val orderedSentences = remember { mutableStateListOf<com.example.data.CompositionSentence>() }
    val availableSentences = remember {
        mutableStateListOf<com.example.data.CompositionSentence>().apply { addAll(sentences) }
    }
    var isDone by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, if (isDone) Emerald300 else Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "🧩 رتّب أحداث الغداء في المطعم بالتسلسل الصحيح:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo900
            )
            Text(
                text = "اضغط على الجملة لإضافتها للتسلسل من بداية الخروج حتى دفع الحساب",
                fontSize = 12.sp,
                color = Slate600
            )

            Spacer(modifier = Modifier.height(10.dp))

            // منطقة الترتيب المختار
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isDone) Emerald50 else Slate100,
                border = BorderStroke(1.dp, if (isDone) Emerald600 else Slate300),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    if (orderedSentences.isEmpty()) {
                        Text(
                            text = "اضغط على الجمل من القائمة بالأسفل لإضافتها هنا...",
                            fontSize = 12.sp,
                            color = Slate500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp)
                        )
                    } else {
                        orderedSentences.forEachIndexed { idx, item ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, Slate300),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .clickable(enabled = !isDone) {
                                        orderedSentences.removeAt(idx)
                                        availableSentences.add(item)
                                        isError = false
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = if (isDone) Emerald600 else Indigo700,
                                        shape = CircleShape,
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${idx + 1}",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item.french,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Slate900,
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (!isDone) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove",
                                            tint = Slate400,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // الجمل المتاحة
            if (availableSentences.isNotEmpty() && !isDone) {
                Text(
                    text = "الجمل المتبقية للتسلسل:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate600
                )
                Spacer(modifier = Modifier.height(4.dp))
                availableSentences.forEachIndexed { index, item ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Slate50,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable {
                                orderedSentences.add(item)
                                availableSentences.removeAt(index)
                                isError = false
                            }
                    ) {
                        Text(
                            text = item.french,
                            fontSize = 13.sp,
                            color = Slate800,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // أزرار التحقق
            if (!isDone) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val isCorrect = orderedSentences.map { it.order } == topic.sentences.map { it.order }
                            if (isCorrect) {
                                isDone = true
                                isError = false
                                audioHelper.playSuccessChime()
                                onScoreEarned(25)
                            } else {
                                isError = true
                                audioHelper.playErrorBuzz()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        enabled = orderedSentences.size == topic.sentences.size
                    ) {
                        Text(text = "تحقق من الترتيب")
                    }

                    OutlinedButton(
                        onClick = {
                            orderedSentences.clear()
                            availableSentences.clear()
                            availableSentences.addAll(sentences)
                            isError = false
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                if (isError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "الترتيب غير دقيق! تذكّر تسلسل الزيارة: الدعوة ➔ الذهاب ➔ المنيو ➔ المقبلات ➔ الطبق الرئيسي ➔ التحلية ➔ الفاتورة.",
                        fontSize = 12.sp,
                        color = Rose600,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Surface(
                    color = Emerald50,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Emerald300),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Emerald600,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ممتاز جداً! رتبت أحداث الزيارة بالكامل بتسلسل منطقي سليم! (+25 نقطة)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald700
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4.3 أكمل الفراغات في الموضوع
// -----------------------------------------------------------------------------
@Composable
private fun CompositionFillBlanksSubMode(
    topic: com.example.data.CompositionTopic,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val solutions = topic.fillBlankSolutions
    val userInputs = remember { mutableStateMapOf<Int, String>() }
    var checked by remember { mutableStateOf(false) }
    var allCorrect by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, if (allCorrect) Emerald300 else Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "✍️ تدريب إكمال الكلمات الناقصة في الموضوع:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo900
            )
            Text(
                text = "استحضر الكلمات المفتاحية لموضوع المطعم من كتيّب ص 40:",
                fontSize = 12.sp,
                color = Slate600
            )

            Spacer(modifier = Modifier.height(10.dp))

            // قائمة الكلمات للمساعدة
            Surface(
                color = Indigo50,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Indigo100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "بنك الكلمات المفتاحية:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Indigo900
                    )
                    Text(
                        text = solutions.shuffled().joinToString("  •  "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Indigo700,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            solutions.forEachIndexed { index, solution ->
                val userVal = userInputs[index] ?: ""
                val isCorrect = userVal.trim().equals(solution, ignoreCase = true)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Slate100,
                        shape = CircleShape,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "${index + 1}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = userVal,
                        onValueChange = {
                            userInputs[index] = it
                            checked = false
                        },
                        placeholder = { Text("الكلمة رقم ${index + 1}", fontSize = 12.sp) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (checked && isCorrect) Emerald600 else Indigo600,
                            unfocusedBorderColor = if (checked && !isCorrect) Rose500 else Slate300
                        )
                    )

                    if (checked) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null,
                            tint = if (isCorrect) Emerald600 else Rose500,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    checked = true
                    allCorrect = solutions.indices.all { idx ->
                        (userInputs[idx] ?: "").trim().equals(solutions[idx], ignoreCase = true)
                    }
                    if (allCorrect) {
                        audioHelper.playSuccessChime()
                        onScoreEarned(20)
                    } else {
                        audioHelper.playErrorBuzz()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "تصحيح الإجابات")
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4.4 إرشادات ونصائح الامتحان
// -----------------------------------------------------------------------------
@Composable
private fun CompositionTipsSubMode() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "💡 نصائح وإرشادات الحصول على الدرجة النهائية في سؤال التعبير:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo900
            )

            Spacer(modifier = Modifier.height(10.dp))

            val tips = listOf(
                Pair("1. التمهيد والزمان والمكان", "ابدأ بعبارة زمنية واضحة مثل (Vendredi dernier) وحدد مكان اللقاء (au restaurant) ووسيلة المواصلات (en voiture)."),
                Pair("2. استخدام أدوات التجزئة (Articles Partitifs)", "ركز على أدوات التجزئة مع الأطعمة والمشروبات: du poulet, de la salade, des crudités, de l'eau."),
                Pair("3. التعبيرات الاصطلاحية للوجبة", "استخدم بدقة التعبيرات الثلاثة المقررة: (Comme entrée كمقبلات), (Comme plat principal كطبق رئيسي), (Comme dessert كحلوى تحلية)."),
                Pair("4. شخصية النادل (Le garçon)", "تحدث عن النادل ودوره: أحضر المنيو (a apporté le menu) وطلب الحساب منه (demander l'addition)."),
                Pair("5. الخاتمة والانطباع العام", "اختم دائماً بعبارة إيجابية تعبر عن الإعجاب: (C'était une journée magnifique / Nous avons passé de très bons moments).")
            )

            tips.forEach { tip ->
                Surface(
                    color = Slate50,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = tip.first,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700
                        )
                        Text(
                            text = tip.second,
                            fontSize = 12.sp,
                            color = Slate800,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
val Slate400 = Color(0xFF94A3B8)
