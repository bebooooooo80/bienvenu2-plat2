package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.FaisDesPhrasesItem
import com.example.data.FrenchCourseData
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Amber300
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald300
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose300
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose800
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Teal100
import com.example.ui.theme.Teal50
import com.example.ui.theme.Teal600
import com.example.ui.theme.Teal700
import com.example.ui.theme.Violet700

/**
 * قسم تكوين الجمل ص 24 من كتيّب منهج Bienvenu 2
 * Exercice : - Fais des Phrases:-
 */
@Composable
fun Unit1FaisDesPhrasesSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val items = FrenchCourseData.unit1FaisDesPhrasesList
    var selectedIndex by remember { mutableIntStateOf(0) }
    var viewMode by remember { mutableIntStateOf(0) } // 0: تفاعلي (ترتيب الكلمات), 1: مراجعة شاملة

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

        // 1. بطاقة الهيدر الرسمية
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "- Fais des Phrases:-",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            AudioPlayButton(
                                textToSpeak = "Fais des phrases",
                                audioHelper = audioHelper,
                                size = 32
                            )
                        }
                        Text(
                            text = "كتيّب ص 24 • تكوين الجمل الفرنسية السليمة من الكلمات المعطاة مع تصريف الفعل",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal700
                        )
                    }

                    Surface(
                        color = Teal700,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "ص 24",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                softWrap = false,
                                maxLines = 1
                            )
                            Text(
                                text = "p. 24",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Teal100,
                                softWrap = false,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // شريط التبديل بين الأنماط
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val modeTitles = listOf("🎮 تمرين ترتيب الكلمات التفاعلي", "📖 الجمل النموذجية والشرح الكامل")
                    modeTitles.forEachIndexed { idx, title ->
                        val isSelected = viewMode == idx
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Teal700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Teal700 else Slate200),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    audioHelper.playClick()
                                    viewMode = idx
                                }
                        ) {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = if (isSelected) Color.White else Slate700,
                                textAlign = TextAlign.Center,
                                softWrap = false,
                                maxLines = 1,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        if (viewMode == 0) {
            InteractiveSentenceBuilderView(
                items = items,
                selectedIndex = selectedIndex,
                onSelectIndex = { newIdx ->
                    audioHelper.playClick()
                    selectedIndex = newIdx
                },
                audioHelper = audioHelper,
                onScoreEarned = onScoreEarned
            )
        } else {
            FullOverviewSentencesView(
                items = items,
                audioHelper = audioHelper
            )
        }
    }
}

// -------------------------------------------------------------------------------------------------
// المكون التفاعلي لبناء الجملة وترتيب كلماتها
// -------------------------------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InteractiveSentenceBuilderView(
    items: List<FaisDesPhrasesItem>,
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val currentItem = items[selectedIndex]

    var assembledWords by remember(selectedIndex) { mutableStateOf<List<String>>(emptyList()) }
    var availableWords by remember(selectedIndex) { mutableStateOf(currentItem.scrambledWords) }
    var isSubmitted by remember(selectedIndex) { mutableStateOf(false) }
    var isCorrect by remember(selectedIndex) { mutableStateOf(false) }
    var showTranslation by remember(selectedIndex) { mutableStateOf(false) }
    val answeredCorrectly = remember { mutableStateMapOf<Int, Boolean>() }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // شريط الأزرار المرقمة للجمل الأربعة
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            itemsIndexed(items) { idx, item ->
                val isSelected = selectedIndex == idx
                val isSolved = answeredCorrectly[idx] == true
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = when {
                        isSelected -> Teal700
                        isSolved -> Emerald100
                        else -> Color.White
                    },
                    border = BorderStroke(
                        1.5.dp,
                        when {
                            isSelected -> Teal700
                            isSolved -> Emerald500
                            else -> Slate200
                        }
                    ),
                    modifier = Modifier.clickable { onSelectIndex(idx) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (isSolved) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Emerald700,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Text(
                            text = "جملة ${item.number}",
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = when {
                                isSelected -> Color.White
                                isSolved -> Emerald800
                                else -> Slate700
                            },
                            softWrap = false,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // بطاقة السؤال الحالية
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, if (isSubmitted && isCorrect) Emerald500 else Teal600.copy(alpha = 0.3f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {

                // الترويسة مع الكلمات المعطاة في الكتيّب
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = Teal700,
                            shape = CircleShape,
                            modifier = Modifier.size(26.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${currentItem.number}",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                        Text(
                            text = "المعطيات في الكتيّب:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate500
                        )
                    }

                    Surface(
                        color = Amber50,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Amber300)
                    ) {
                        Text(
                            text = currentItem.pageReference,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Amber800,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // الكلمات المعطاة في كتيّب ص 24
                Surface(
                    color = Teal50,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, Teal600.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${currentItem.number}- ${currentItem.promptFr}",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900,
                                fontFamily = FontFamily.SansSerif
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentItem.promptAr,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Teal700
                            )
                        }

                        AudioPlayButton(
                            textToSpeak = currentItem.promptFr.replace("–", " "),
                            audioHelper = audioHelper,
                            size = 36
                        )
                    }
                }

                // منطقة تجميع الجملة
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "اضغط على الكلمات بالترتيب لتكوين الجملة الصحيحة:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )

                    Surface(
                        color = if (isSubmitted) {
                            if (isCorrect) Emerald50 else Rose50
                        } else Slate100.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            1.5.dp,
                            if (isSubmitted) {
                                if (isCorrect) Emerald500 else Rose300
                            } else Slate300
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (assembledWords.isEmpty()) {
                                Text(
                                    text = "« اضغط على الكلمات بالأسفل لإضافتها هنا »",
                                    fontSize = 13.sp,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    color = Slate500,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 14.dp)
                                )
                            } else {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    assembledWords.forEachIndexed { wordIdx, word ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = Teal700,
                                            modifier = Modifier.clickable {
                                                if (!isSubmitted) {
                                                    audioHelper.playClick()
                                                    assembledWords = assembledWords.filterIndexed { i, _ -> i != wordIdx }
                                                    availableWords = availableWords + word
                                                }
                                            }
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Text(
                                                    text = word,
                                                    color = Color.White,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                if (!isSubmitted) {
                                                    Icon(
                                                        Icons.Default.Close,
                                                        contentDescription = "إزالة",
                                                        tint = Color.White.copy(alpha = 0.7f),
                                                        modifier = Modifier.size(12.dp)
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

                // بنك الكلمات المتاحة
                if (availableWords.isNotEmpty() && !isSubmitted) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "بنك الكلمات المتاحة:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            availableWords.forEachIndexed { index, word ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, Teal600),
                                    modifier = Modifier.clickable {
                                        audioHelper.playClick()
                                        assembledWords = assembledWords + word
                                        availableWords = availableWords.filterIndexed { i, _ -> i != index }
                                    }
                                ) {
                                    Text(
                                        text = word,
                                        color = Indigo900,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // أزرار التحكم
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isSubmitted) {
                        Button(
                            onClick = {
                                if (assembledWords.isNotEmpty()) {
                                    isSubmitted = true
                                    val userSentence = assembledWords.joinToString(" ").trim()
                                    val targetSentence = currentItem.correctOrder.joinToString(" ").trim()
                                    val isMatch = userSentence.equals(targetSentence, ignoreCase = true)
                                    isCorrect = isMatch
                                    if (isMatch) {
                                        audioHelper.playSuccessChime()
                                        if (answeredCorrectly[selectedIndex] != true) {
                                            answeredCorrectly[selectedIndex] = true
                                            onScoreEarned(1)
                                        }
                                    } else {
                                        audioHelper.playErrorBuzz()
                                    }
                                }
                            },
                            enabled = assembledWords.isNotEmpty(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Teal700),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "تحقق من صحة الجملة",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                audioHelper.playClick()
                                assembledWords = emptyList()
                                availableWords = currentItem.scrambledWords
                                isSubmitted = false
                                isCorrect = false
                            },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Slate300)
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "إعادة",
                                tint = Slate600,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                audioHelper.playClick()
                                assembledWords = emptyList()
                                availableWords = currentItem.scrambledWords
                                isSubmitted = false
                                isCorrect = false
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCorrect) Emerald700 else Teal700
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (isCorrect) "أحسنت! إعادة المحاولة" else "حاول مرة أخرى",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // بطاقة النتيجة والشرح
                AnimatedVisibility(visible = isSubmitted) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            color = if (isCorrect) Emerald50 else Rose50,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isCorrect) Emerald300 else Rose300)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isCorrect) Emerald600 else Rose600,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = if (isCorrect) {
                                        "ممتاز إجابة صحيحة! تم ترتيب الجملة وتصريف الفعل بنجاح."
                                    } else {
                                        "تحقق من الترتيب والتصريف الصحيح للجملة النموذجية بالأسفل."
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCorrect) Emerald800 else Rose800
                                )
                            }
                        }

                        ModelSentenceDetailedCard(
                            item = currentItem,
                            audioHelper = audioHelper,
                            showTranslation = showTranslation,
                            onToggleTranslation = { showTranslation = !showTranslation }
                        )

                        VerbConjugationHelperCard(item = currentItem, audioHelper = audioHelper)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// بطاقة عرض الجملة النموذجية والبدائل
// -------------------------------------------------------------------------------------------------
@Composable
private fun ModelSentenceDetailedCard(
    item: FaisDesPhrasesItem,
    audioHelper: AudioHelper,
    showTranslation: Boolean,
    onToggleTranslation: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo50),
        border = BorderStroke(1.dp, Indigo100),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🌟 الجملة النموذجية المعتمدة:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AudioPlayButton(
                        textToSpeak = item.modelSentenceFr,
                        audioHelper = audioHelper,
                        size = 32
                    )
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = onToggleTranslation,
                        size = 32
                    )
                }
            }

            Text(
                text = item.modelSentenceFr,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900,
                fontFamily = FontFamily.SansSerif
            )

            ArabicTranslationBanner(
                translation = item.modelSentenceAr,
                visible = showTranslation
            )

            if (item.alternativeSentences.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "💡 صياغات نموذجية أخرى مقبولة أيضاً في الامتحان:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Violet700
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    item.alternativeSentences.forEach { (altFr, altAr) ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "• $altFr",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800
                                    )
                                    Text(
                                        text = altAr,
                                        fontSize = 11.sp,
                                        color = Slate600
                                    )
                                }
                                AudioPlayButton(
                                    textToSpeak = altFr,
                                    audioHelper = audioHelper,
                                    size = 28
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// بطاقة تصريف الفعل والقاعدة النحوية
// -------------------------------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VerbConjugationHelperCard(
    item: FaisDesPhrasesItem,
    audioHelper: AudioHelper
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Amber50),
        border = BorderStroke(1.dp, Amber300),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Amber800,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "قاعدة تكوين الجملة وتصريف فعل (${item.verbInfinitive}) في المضارع:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber900
                    )
                }

                AudioPlayButton(
                    textToSpeak = item.verbInfinitive,
                    audioHelper = audioHelper,
                    size = 28
                )
            }

            Text(
                text = item.grammarTipAr,
                fontSize = 12.sp,
                color = Slate800,
                lineHeight = 18.sp
            )

            Text(
                text = "تصريف الفعل في زمن المضارع (Le présent) مع جميع الضمائر:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Amber900
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item.conjugationSamples.forEach { (conjFr, conjAr) ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Amber300),
                        modifier = Modifier.clickable {
                            audioHelper.speak(conjFr)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = conjFr,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            Text(
                                text = "($conjAr)",
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

// -------------------------------------------------------------------------------------------------
// العرض الشامل للجمل الأربعة وقواعدها (Full Overview)
// -------------------------------------------------------------------------------------------------
@Composable
private fun FullOverviewSentencesView(
    items: List<FaisDesPhrasesItem>,
    audioHelper: AudioHelper
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Teal600),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "📌 خطوات كتابة وتكوين جملة فرنسية نموذجية (Fais des phrases):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Teal700
                )
                Text(
                    text = "1. نبدأ بفاعل مناسب (اسم مثل Maman / Ali أو ضمير مثل Je / Nous).\n2. نصرف الفعل المصدر المعطى في زمن المضارع مع الفاعل الذي اخترناه.\n3. نضع المفعول أو التكملة المعطاة مع مراعاة أدوات المعرفة (le, la, l', les) وحروف الجر المناسبة (à, pour).",
                    fontSize = 12.sp,
                    color = Slate700,
                    lineHeight = 18.sp
                )
            }
        }

        items.forEach { item ->
            var isTranslated by remember { mutableStateOf(false) }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                color = Teal700,
                                shape = CircleShape,
                                modifier = Modifier.size(26.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${item.number}",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                            Text(
                                text = item.promptFr,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            AudioPlayButton(
                                textToSpeak = item.modelSentenceFr,
                                audioHelper = audioHelper,
                                size = 30
                            )
                            TranslateIconButton(
                                isTranslated = isTranslated,
                                onClick = { isTranslated = !isTranslated },
                                size = 30
                            )
                        }
                    }

                    Surface(
                        color = Teal50,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Teal100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "➔ ${item.modelSentenceFr}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = Teal700,
                                fontFamily = FontFamily.SansSerif
                            )
                            ArabicTranslationBanner(
                                translation = item.modelSentenceAr,
                                visible = isTranslated
                            )
                        }
                    }

                    Text(
                        text = "💡 ${item.grammarTipAr}",
                        fontSize = 11.5.sp,
                        color = Slate600,
                        lineHeight = 16.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item.conjugationSamples.take(4).forEach { (fr, ar) ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Slate100,
                                modifier = Modifier.clickable { audioHelper.speak(fr) }
                            ) {
                                Text(
                                    text = "$fr ($ar)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate700,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
