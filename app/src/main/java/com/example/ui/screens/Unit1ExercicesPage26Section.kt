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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.data.BookletLieuxEtExercicesData
import com.example.data.BookletOuVasTuItem
import com.example.data.BookletQuiFaitCeTravailItem
import com.example.data.BookletQuiParleItem
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Amber300
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber800
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald300
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose800
import com.example.ui.theme.Slate50
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
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

/**
 * قسم التمارين الرسمية ص 26 من كتيّب منهج Bienvenu 2
 * 1. Où vas- tu pour ..........?
 * 2. Qui parle ?
 * 3. Qui peut faire ce travail:
 */
@Composable
fun Unit1ExercicesPage26Section(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val ouVasTuList = BookletLieuxEtExercicesData.ouVasTuItems
    val quiParleList = BookletLieuxEtExercicesData.quiParleItems
    val quiTravailList = BookletLieuxEtExercicesData.quiFaitCeTravailItems

    var selectedExerciseTab by remember { mutableIntStateOf(0) } // 0: الكل, 1: Où vas-tu, 2: Qui parle, 3: Qui fait ce travail

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

        // 1. بطاقة الهيدر الرسمية لصفحة 26
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
                                text = "Exercices officiels (p. 26)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            AudioPlayButton(
                                textToSpeak = "Exercices de la page vingt-six",
                                audioHelper = audioHelper,
                                size = 32
                            )
                        }
                        Text(
                            text = "كتيّب ص 26 • التمارين الثلاثة المقررة في منهج Bienvenu 2",
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
                                text = "ص 26",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                softWrap = false,
                                maxLines = 1
                            )
                            Text(
                                text = "p. 26",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Teal100,
                                softWrap = false,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // تبديل تمارين الصفحة
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val exTitles = listOf(
                        "🌟 الكل (15 سؤال)",
                        "1️⃣ Où vas-tu pour...?",
                        "2️⃣ Qui parle ?",
                        "3️⃣ Qui peut faire ce travail"
                    )
                    exTitles.forEachIndexed { idx, title ->
                        val isSelected = selectedExerciseTab == idx
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Teal700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Teal700 else Slate200),
                            modifier = Modifier.clickable {
                                audioHelper.playClick()
                                selectedExerciseTab = idx
                            }
                        ) {
                            Text(
                                text = title,
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = if (isSelected) Color.White else Slate700,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // --- التمرين الأول: 1. Où vas- tu pour ..........? ---
        if (selectedExerciseTab == 0 || selectedExerciseTab == 1) {
            OfficialHeaderBanner(
                number = "1",
                titleFr = "1. Où vas- tu pour ..........?",
                titleAr = "1. أين تذهب لكي .......... ؟",
                audioText = "Où vas-tu pour",
                audioHelper = audioHelper,
                badgeColor = Teal700
            )

            ouVasTuList.forEach { item ->
                OuVasTuInteractiveCard(
                    item = item,
                    audioHelper = audioHelper,
                    onScoreEarned = onScoreEarned
                )
            }
        }

        // --- التمرين الثاني: 2. Qui parle ? ---
        if (selectedExerciseTab == 0 || selectedExerciseTab == 2) {
            OfficialHeaderBanner(
                number = "2",
                titleFr = "2. Qui parle ?",
                titleAr = "2. من المتحدث في هذه المواقف ؟",
                audioText = "Qui parle",
                audioHelper = audioHelper,
                badgeColor = Indigo700
            )

            quiParleList.forEach { item ->
                QuiParleInteractiveCard(
                    item = item,
                    audioHelper = audioHelper,
                    onScoreEarned = onScoreEarned
                )
            }
        }

        // --- التمرين الثالث: 3. Qui peut faire ce travail: ---
        if (selectedExerciseTab == 0 || selectedExerciseTab == 3) {
            OfficialHeaderBanner(
                number = "3",
                titleFr = "3. Qui peut faire ce travail:",
                titleAr = "3. من يستطيع القيام بهذا العمل (المهن والوظائف) :",
                audioText = "Qui peut faire ce travail",
                audioHelper = audioHelper,
                badgeColor = Violet700
            )

            quiTravailList.forEach { item ->
                QuiTravailInteractiveCard(
                    item = item,
                    audioHelper = audioHelper,
                    onScoreEarned = onScoreEarned
                )
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// بنر عنوان التمرين الرسمي
// -------------------------------------------------------------------------------------------------
@Composable
private fun OfficialHeaderBanner(
    number: String,
    titleFr: String,
    titleAr: String,
    audioText: String,
    audioHelper: AudioHelper,
    badgeColor: Color
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = badgeColor.copy(alpha = 0.08f),
        border = BorderStroke(1.5.dp, badgeColor.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = badgeColor,
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = number,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
                Column {
                    Text(
                        text = titleFr,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = titleAr,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                }
            }

            AudioPlayButton(
                textToSpeak = audioText,
                audioHelper = audioHelper,
                size = 30
            )
        }
    }
}

// -------------------------------------------------------------------------------------------------
// بطاقة السؤال الأول: 1. Où vas- tu pour ..........?
// -------------------------------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OuVasTuInteractiveCard(
    item: BookletOuVasTuItem,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var showTranslation by remember { mutableStateOf(false) }
    var hasScored by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, if (isSubmitted && isCorrect) Emerald300 else Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

            // رأس السؤال
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Teal700,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${item.number}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "${item.number}- ${item.activityFr}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        if (showTranslation) {
                            Text(
                                text = item.activityAr,
                                fontSize = 11.5.sp,
                                color = Teal700,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AudioPlayButton(
                        textToSpeak = item.activityFr,
                        audioHelper = audioHelper,
                        size = 30
                    )
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = { showTranslation = !showTranslation },
                        size = 30
                    )
                }
            }

            // الخيارات التفاعلية
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                item.options.forEach { option ->
                    val isThisSelected = selectedOption == option
                    val isTarget = option.equals(item.expectedAnswerFr, ignoreCase = true)

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = when {
                            isSubmitted && isTarget -> Emerald50
                            isSubmitted && isThisSelected && !isCorrect -> Rose50
                            isThisSelected -> Teal50
                            else -> Slate50
                        },
                        border = BorderStroke(
                            1.dp,
                            when {
                                isSubmitted && isTarget -> Emerald500
                                isSubmitted && isThisSelected && !isCorrect -> Rose600
                                isThisSelected -> Teal700
                                else -> Slate200
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (!isSubmitted) {
                                    audioHelper.playClick()
                                    selectedOption = option
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
                                text = "➔ $option",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    isSubmitted && isTarget -> Emerald800
                                    isSubmitted && isThisSelected && !isCorrect -> Rose800
                                    isThisSelected -> Teal700
                                    else -> Slate800
                                }
                            )

                            if (isSubmitted && isTarget) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Emerald600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // زر التحقق
            if (!isSubmitted) {
                Button(
                    onClick = {
                        if (selectedOption != null) {
                            isSubmitted = true
                            isCorrect = selectedOption.equals(item.expectedAnswerFr, ignoreCase = true)
                            if (isCorrect) {
                                audioHelper.playSuccessChime()
                                if (!hasScored) {
                                    hasScored = true
                                    onScoreEarned(1)
                                }
                            } else {
                                audioHelper.playErrorBuzz()
                            }
                        }
                    },
                    enabled = selectedOption != null,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal700),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "تحقق من الإجابة",
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                // صندوق الشرح والحل النموذجي
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isCorrect) Emerald50 else Teal50,
                        border = BorderStroke(1.dp, if (isCorrect) Emerald300 else Teal100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🎯 إجابة كتيّب المعهد: ${item.expectedAnswerFr}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isCorrect) Emerald800 else Teal700
                                )
                                AudioPlayButton(
                                    textToSpeak = item.expectedAnswerFr,
                                    audioHelper = audioHelper,
                                    size = 26
                                )
                            }
                            Text(
                                text = item.answerAr,
                                fontSize = 11.5.sp,
                                color = Slate700
                            )
                            Text(
                                text = "💡 ${item.explanationAr}",
                                fontSize = 11.sp,
                                color = Slate600
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "إعادة المحاولة",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal700,
                            modifier = Modifier
                                .clickable {
                                    audioHelper.playClick()
                                    selectedOption = null
                                    isSubmitted = false
                                    isCorrect = false
                                }
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// بطاقة السؤال الثاني: 2. Qui parle ?
// -------------------------------------------------------------------------------------------------
@Composable
private fun QuiParleInteractiveCard(
    item: BookletQuiParleItem,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var showTranslation by remember { mutableStateOf(false) }
    var hasScored by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, if (isSubmitted && isCorrect) Emerald300 else Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

            // الاقتباس ورأس السؤال
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Indigo700,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${item.number}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "${item.number}- ${item.quoteFr}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        if (showTranslation) {
                            Text(
                                text = item.quoteAr,
                                fontSize = 11.5.sp,
                                color = Indigo700,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AudioPlayButton(
                        textToSpeak = item.quoteFr.replace("«", "").replace("»", ""),
                        audioHelper = audioHelper,
                        size = 30
                    )
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = { showTranslation = !showTranslation },
                        size = 30
                    )
                }
            }

            // خيارات المتحدث
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                item.options.forEach { option ->
                    val isThisSelected = selectedOption == option
                    val isTarget = option.equals(item.speakerFr, ignoreCase = true)

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = when {
                            isSubmitted && isTarget -> Emerald50
                            isSubmitted && isThisSelected && !isCorrect -> Rose50
                            isThisSelected -> Indigo50
                            else -> Slate50
                        },
                        border = BorderStroke(
                            1.dp,
                            when {
                                isSubmitted && isTarget -> Emerald500
                                isSubmitted && isThisSelected && !isCorrect -> Rose600
                                isThisSelected -> Indigo700
                                else -> Slate200
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (!isSubmitted) {
                                    audioHelper.playClick()
                                    selectedOption = option
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
                                text = "( $option )",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    isSubmitted && isTarget -> Emerald800
                                    isSubmitted && isThisSelected && !isCorrect -> Rose800
                                    isThisSelected -> Indigo700
                                    else -> Slate800
                                }
                            )

                            if (isSubmitted && isTarget) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Emerald600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // زر التحقق
            if (!isSubmitted) {
                Button(
                    onClick = {
                        if (selectedOption != null) {
                            isSubmitted = true
                            isCorrect = selectedOption.equals(item.speakerFr, ignoreCase = true)
                            if (isCorrect) {
                                audioHelper.playSuccessChime()
                                if (!hasScored) {
                                    hasScored = true
                                    onScoreEarned(1)
                                }
                            } else {
                                audioHelper.playErrorBuzz()
                            }
                        }
                    },
                    enabled = selectedOption != null,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "تحقق من المتحدث",
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isCorrect) Emerald50 else Indigo50,
                        border = BorderStroke(1.dp, if (isCorrect) Emerald300 else Indigo100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🗣️ المتحدث: ${item.speakerFr}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isCorrect) Emerald800 else Indigo900
                                )
                                AudioPlayButton(
                                    textToSpeak = item.speakerFr,
                                    audioHelper = audioHelper,
                                    size = 26
                                )
                            }
                            Text(
                                text = "${item.speakerAr} • سياق الموقف: ${item.situationContextAr}",
                                fontSize = 11.5.sp,
                                color = Slate700
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "إعادة المحاولة",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo700,
                            modifier = Modifier
                                .clickable {
                                    audioHelper.playClick()
                                    selectedOption = null
                                    isSubmitted = false
                                    isCorrect = false
                                }
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// بطاقة السؤال الثالث: 3. Qui peut faire ce travail:
// -------------------------------------------------------------------------------------------------
@Composable
private fun QuiTravailInteractiveCard(
    item: BookletQuiFaitCeTravailItem,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var showTranslation by remember { mutableStateOf(false) }
    var hasScored by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, if (isSubmitted && isCorrect) Emerald300 else Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

            // رأس السؤال
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Violet700,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${item.number}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "${item.number}- ${item.actionFr}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        if (showTranslation) {
                            Text(
                                text = item.actionAr,
                                fontSize = 11.5.sp,
                                color = Violet700,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AudioPlayButton(
                        textToSpeak = item.actionFr,
                        audioHelper = audioHelper,
                        size = 30
                    )
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = { showTranslation = !showTranslation },
                        size = 30
                    )
                }
            }

            // خيارات المهنة
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                item.options.forEach { option ->
                    val isThisSelected = selectedOption == option
                    val isTarget = option.equals(item.professionFr, ignoreCase = true)

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = when {
                            isSubmitted && isTarget -> Emerald50
                            isSubmitted && isThisSelected && !isCorrect -> Rose50
                            isThisSelected -> Indigo50
                            else -> Slate50
                        },
                        border = BorderStroke(
                            1.dp,
                            when {
                                isSubmitted && isTarget -> Emerald500
                                isSubmitted && isThisSelected && !isCorrect -> Rose600
                                isThisSelected -> Violet700
                                else -> Slate200
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (!isSubmitted) {
                                    audioHelper.playClick()
                                    selectedOption = option
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
                                text = "➔ $option",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    isSubmitted && isTarget -> Emerald800
                                    isSubmitted && isThisSelected && !isCorrect -> Rose800
                                    isThisSelected -> Violet700
                                    else -> Slate800
                                }
                            )

                            if (isSubmitted && isTarget) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Emerald600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // زر التحقق
            if (!isSubmitted) {
                Button(
                    onClick = {
                        if (selectedOption != null) {
                            isSubmitted = true
                            isCorrect = selectedOption.equals(item.professionFr, ignoreCase = true)
                            if (isCorrect) {
                                audioHelper.playSuccessChime()
                                if (!hasScored) {
                                    hasScored = true
                                    onScoreEarned(1)
                                }
                            } else {
                                audioHelper.playErrorBuzz()
                            }
                        }
                    },
                    enabled = selectedOption != null,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Violet700),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "تحقق من صاحب المهنة",
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isCorrect) Emerald50 else Violet700.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, if (isCorrect) Emerald300 else Violet700.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "👨‍🔧 المهنة: ${item.professionFr}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isCorrect) Emerald800 else Violet700
                                )
                                AudioPlayButton(
                                    textToSpeak = item.professionFr,
                                    audioHelper = audioHelper,
                                    size = 26
                                )
                            }
                            Text(
                                text = "${item.professionAr} • ${item.descriptionAr}",
                                fontSize = 11.5.sp,
                                color = Slate700
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "إعادة المحاولة",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700,
                            modifier = Modifier
                                .clickable {
                                    audioHelper.playClick()
                                    selectedOption = null
                                    isSubmitted = false
                                    isCorrect = false
                                }
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}
