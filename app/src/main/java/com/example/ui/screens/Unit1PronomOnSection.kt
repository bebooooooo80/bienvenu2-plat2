package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.FrenchCourseData
import com.example.data.PronomOnExerciseItem
import com.example.data.PronomOnReplaceItem
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.TranslateIconButton
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
import com.example.ui.theme.Rose100
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
import com.example.ui.theme.Violet100
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

/**
 * قسم قاعدة ضمير (On) المدمج بالوحدة الأولى - كتيّب المعهد ص 19
 * متطابق 100% مع المحتوى والرسومات وتدريبي (Choisis) و (Remplace).
 */
@Composable
fun Unit1PronomOnSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableIntStateOf(0) }
    val subTabs = listOf(
        "🌟 الشرح والأمثلة (ص 19)",
        "📝 تمرين 1: Choisis (7 جمل)",
        "🔄 تمرين 2: Remplace (5 جمل)"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Main Header Banner for Page 19
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
                            text = "🌟 Le pronom (On)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "قواعد الوحدة الأولى • كتيّب ص 19 • ضمير الفاعل On والفرق بينه وبين Nous",
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
                            text = "Page 19",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sub-tabs Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    subTabs.forEachIndexed { index, title ->
                        val isSelected = selectedSubTab == index
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Violet700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Violet700 else Slate200),
                            modifier = Modifier.clickable { selectedSubTab = index }
                        ) {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate700,
                                softWrap = false,
                                maxLines = 1,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                            )
                        }
                    }
                }
            }
        }

        when (selectedSubTab) {
            0 -> PronomOnTheoryCard(audioHelper = audioHelper)
            1 -> PronomOnChoisisCard(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            2 -> PronomOnReplaceCard(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// -------------------------------------------------------------------------------------------------
// SubTab 0: الشرح التفاعلي والمخطط بالأسهم والأمثلة الرسمية من ص 19
// -------------------------------------------------------------------------------------------------
@Composable
fun PronomOnTheoryCard(audioHelper: AudioHelper) {
    var isDiagramTranslated by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Decorative Ribbon Header matching the Booklet Page 19 style
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Banner graphic representation
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Slate50,
                    border = BorderStroke(2.dp, Slate800),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⮜  Le pronom (On)  ⮞",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Serif,
                            color = Slate900
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // The Official Arrow Diagram from Booklet Page 19
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Indigo50,
                    border = BorderStroke(1.5.dp, Indigo600),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📌 مخطط القاعدة الرسمي (ص 19) :",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )

                            AudioPlayButton(
                                textToSpeak = "Le pronom On. On est un pronom qui a le sens de nous, mais on conjugue le verbe avec lui comme il et elle au singulier.",
                                audioHelper = audioHelper,
                                backgroundColor = Color.White,
                                iconTint = Indigo700,
                                size = 28
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Diagram Layout: Box [On] with two branches
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // The Box "On"
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White,
                                border = BorderStroke(2.dp, Indigo900),
                                modifier = Modifier.padding(end = 10.dp)
                            ) {
                                Text(
                                    text = "On",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Indigo900,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
                                )
                            }

                            // The Two Branches
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Branch 1: المعنى
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, Indigo100)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "➔",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Indigo600,
                                            modifier = Modifier.padding(end = 6.dp)
                                        )
                                        Column {
                                            Text(
                                                text = "- ضمير بمعنى نحن (Sens = nous)",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Indigo900
                                            )
                                            Text(
                                                text = "في المعنى يشير إلى جماعة المتكلمين «نحن»",
                                                fontSize = 10.sp,
                                                color = Slate600
                                            )
                                        }
                                    }
                                }

                                // Branch 2: التصريف
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, Violet100)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "➔",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Violet700,
                                            modifier = Modifier.padding(end = 6.dp)
                                        )
                                        Column {
                                            Text(
                                                text = "- نصرف الفعل معه مثل (il – elle)",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Violet700
                                            )
                                            Text(
                                                text = "في التصريف والقواعد يُعامل دائماً معاملة المفرد الغائب!",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Slate600
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

        // Official Examples Card (Ex: Page 19)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "📖 Exemples officiels du livret (ص 19)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "أمثلة الكتيّب الرسمية مع بيان التحويل بين Nous و On",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }

                    Surface(
                        color = Amber50,
                        border = BorderStroke(1.dp, Amber400),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Ex ص 19",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Amber950,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Example 1
                OfficialExampleRow(
                    nousText = "Nous allons au club",
                    onText = "On va au club",
                    arabicMeaning = "نحن نذهب إلى النادي",
                    verbChangeNote = "allons (-ons) ➔ va (comme il/elle)",
                    audioHelper = audioHelper
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Example 2
                OfficialExampleRow(
                    nousText = "Nous visitons les pyramides",
                    onText = "on visite les pyramides",
                    arabicMeaning = "نحن نزور الأهرامات",
                    verbChangeNote = "visitons (-ons) ➔ visite (comme il/elle)",
                    audioHelper = audioHelper
                )
            }
        }

        // Exam Golden Tip Box
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.5.dp, Amber400)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💡 مفتاح التفوق في امتحان الأزهر (سر الحل السريع) :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber950,
                        modifier = Modifier.weight(1f).padding(end = 6.dp)
                    )

                    Surface(
                        color = Amber400,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "هام جداً",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Amber950,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "1️⃣ انظر لنهاية الفعل مباشرة :\n" +
                            "• إذا انتهى الفعل بـ (-ons) أو كان (sommes / faisons / allons) ➔ اختر فوراً « Nous ».\n" +
                            "• إذا انتهى الفعل بـ (-e أو -t) أو كان (va / est / a / choisit) ➔ اختر فوراً « On ».\n\n" +
                            "2️⃣ عند التحويل من (on) إلى (nous) :\n" +
                            "• بدّل on بـ nous، ثم صرّف الفعل مع nous بإضافة (-ons) أو صياغته الشاذة (va ➔ allons, est ➔ sommes, a ➔ avons).",
                    fontSize = 11.sp,
                    color = Amber950,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun OfficialExampleRow(
    nousText: String,
    onText: String,
    arabicMeaning: String,
    verbChangeNote: String,
    audioHelper: AudioHelper
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 6.dp)) {
                    Text(
                        text = "$nousText = $onText",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900
                    )
                    Text(
                        text = "المعنى: $arabicMeaning",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Violet700
                    )
                }

                AudioPlayButton(
                    textToSpeak = "$nousText. $onText.",
                    audioHelper = audioHelper,
                    backgroundColor = Color.White,
                    iconTint = Indigo700,
                    size = 28
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Violet50,
                border = BorderStroke(1.dp, Violet100)
            ) {
                Text(
                    text = "تغيير الفعل: $verbChangeNote",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Violet700,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// SubTab 1: تمرين 1 من ص 19: Choisis (On – Nous) :- (7 جمل كاملة)
// -------------------------------------------------------------------------------------------------
@Composable
fun PronomOnChoisisCard(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    val exercises = FrenchCourseData.unit1PronomOnChoisisExercises
    val userAnswers = remember { mutableStateMapOf<String, String>() }
    val revealedExplanations = remember { mutableStateMapOf<Int, Boolean>() }
    var showAllTranslations by remember { mutableStateOf(false) }

    val answeredCount = userAnswers.size
    val correctCount = exercises.count { item ->
        userAnswers[item.id] == item.correctAnswer
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Exercise Title & Control Header
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "📝 Choisis (On – Nous) :-",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "كتيّب المعهد ص 19 • 7 جمل رسمية لاختيار الفاعل الصحيح",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }

                    Surface(
                        color = if (correctCount == exercises.size) Emerald100 else Indigo50,
                        border = BorderStroke(1.dp, if (correctCount == exercises.size) Emerald500 else Indigo600),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$correctCount / ${exercises.size} صحيح",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = if (correctCount == exercises.size) Emerald600 else Indigo700,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Control Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showAllTranslations = !showAllTranslations },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Violet600)
                    ) {
                        Text(
                            text = if (showAllTranslations) "إخفاء الترجمة" else "إظهار الترجمة",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700,
                            softWrap = false,
                            maxLines = 1
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            userAnswers.clear()
                            revealedExplanations.clear()
                            Toast.makeText(context, "تمت إعادة تعيين التمرين", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Slate300())
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            tint = Slate600,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "إعادة",
                            fontSize = 11.sp,
                            color = Slate700,
                            softWrap = false,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // Each of the 7 Questions from Page 19
        exercises.forEach { item ->
            SinglePronomOnQuestionCard(
                item = item,
                selectedAnswer = userAnswers[item.id],
                isExplanationRevealed = revealedExplanations[item.number] == true,
                onSelectAnswer = { choice ->
                    if (userAnswers[item.id] == null) {
                        userAnswers[item.id] = choice
                        if (choice == item.correctAnswer) {
                            onScoreEarned(5)
                            audioHelper.playSuccessChime()
                        } else {
                            audioHelper.playErrorBuzz()
                        }
                    } else {
                        userAnswers[item.id] = choice
                    }
                },
                onToggleExplanation = {
                    revealedExplanations[item.number] = !(revealedExplanations[item.number] ?: false)
                },
                showGlobalTranslation = showAllTranslations,
                audioHelper = audioHelper
            )
        }

        // Completion Banner
        if (correctCount == exercises.size) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Emerald100,
                border = BorderStroke(1.5.dp, Emerald500),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎉 أحسنت صنعاً! أتقنت جميع جمل تمرين Choisis ص 19!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Emerald600,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "أصبحت الآن تفرّق بين On و Nous بحسب تصريف الفعل بكل ثقة 🌟",
                        fontSize = 11.sp,
                        color = Slate800,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun SinglePronomOnQuestionCard(
    item: PronomOnExerciseItem,
    selectedAnswer: String?,
    isExplanationRevealed: Boolean,
    onSelectAnswer: (String) -> Unit,
    onToggleExplanation: () -> Unit,
    showGlobalTranslation: Boolean,
    audioHelper: AudioHelper
) {
    var isLocalTranslated by remember { mutableStateOf(false) }
    val showTranslation = showGlobalTranslation || isLocalTranslated
    val isCorrect = selectedAnswer == item.correctAnswer
    val isAnswered = selectedAnswer != null

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                selectedAnswer == null -> Color.White
                isCorrect -> Emerald100.copy(alpha = 0.25f)
                else -> Rose100.copy(alpha = 0.25f)
            }
        ),
        border = BorderStroke(
            1.5.dp,
            when {
                selectedAnswer == null -> Slate200
                isCorrect -> Emerald500
                else -> Rose500
            }
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Number + Audio + Translate
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Indigo600,
                        shape = CircleShape,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${item.number}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "الجملة (${item.number}) ص 19",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Indigo900
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = { isLocalTranslated = !isLocalTranslated },
                        size = 32
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    AudioPlayButton(
                        textToSpeak = item.fullSentenceFr,
                        audioHelper = audioHelper,
                        backgroundColor = Indigo50,
                        iconTint = Indigo700,
                        size = 32
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // The Question Sentence
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Slate50,
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.number}- ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )

                    // The Blank Box
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = when {
                            selectedAnswer == null -> Color.White
                            isCorrect -> Emerald100
                            else -> Rose100
                        },
                        border = BorderStroke(
                            1.dp,
                            when {
                                selectedAnswer == null -> Indigo600
                                isCorrect -> Emerald500
                                else -> Rose500
                            }
                        )
                    ) {
                        Text(
                            text = selectedAnswer ?: ".....................",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = when {
                                selectedAnswer == null -> Slate500
                                isCorrect -> Emerald600
                                else -> Rose600
                            },
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = item.predicateFr,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900
                    )
                }
            }

            // Arabic Translation
            ArabicTranslationBanner(
                translation = item.translationAr,
                visible = showTranslation,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Choice Buttons: [On] vs [Nous]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("On", "Nous").forEach { choice ->
                    val isChosen = selectedAnswer == choice
                    val isThisChoiceCorrect = choice == item.correctAnswer

                    Button(
                        onClick = { onSelectAnswer(choice) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when {
                                !isAnswered -> Color.White
                                isChosen && isCorrect -> Emerald600
                                isChosen && !isCorrect -> Rose600
                                isThisChoiceCorrect -> Emerald100
                                else -> Color.White
                            },
                            contentColor = when {
                                !isAnswered -> Indigo900
                                isChosen && (isCorrect || !isCorrect) -> Color.White
                                isThisChoiceCorrect -> Emerald600
                                else -> Slate500
                            }
                        ),
                        border = BorderStroke(
                            1.5.dp,
                            when {
                                !isAnswered -> Indigo600
                                isChosen && isCorrect -> Emerald600
                                isChosen && !isCorrect -> Rose600
                                isThisChoiceCorrect -> Emerald500
                                else -> Slate200
                            }
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isAnswered && isChosen) {
                                Icon(
                                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            Text(
                                text = choice,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }

            // Grammatical Reason & Hint Button
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clickable { onToggleExplanation() }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Analysis",
                        tint = Violet700,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isExplanationRevealed) "إخفاء تحليل القاعدة" else "لماذا هذه الإجابة؟ (تحليل الفعل)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Violet700
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Slate100
                ) {
                    Text(
                        text = item.verbAnalyzed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            AnimatedVisibility(visible = isExplanationRevealed) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Violet50,
                    border = BorderStroke(1.dp, Violet100),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "💡 التبرير النحوي :",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Violet700
                        )
                        Text(
                            text = item.explanationAr,
                            fontSize = 11.sp,
                            color = Slate800,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// SubTab 2: تمرين 2 من ص 19: Remplace (on) par (nous): (5 جمل كاملة)
// -------------------------------------------------------------------------------------------------
@Composable
fun PronomOnReplaceCard(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val exercises = FrenchCourseData.unit1PronomOnReplaceExercises
    val revealedAnswers = remember { mutableStateMapOf<String, Boolean>() }
    val userQuizAnswers = remember { mutableStateMapOf<String, String>() }
    var showAllMeanings by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Title Banner
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "🔄 Remplace (on) par (nous):",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "كتيّب ص 19 • 5 جمل رسمية لتحويل الجمل وتصريف الأفعال مع Nous",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }

                    Surface(
                        color = Violet700,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "تمرين ص 19",
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showAllMeanings = !showAllMeanings },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Violet600)
                    ) {
                        Text(
                            text = if (showAllMeanings) "إخفاء المعاني" else "إظهار المعاني بالعربية",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700,
                            softWrap = false,
                            maxLines = 1
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            val allRevealed = revealedAnswers.size == exercises.size
                            if (allRevealed) {
                                revealedAnswers.clear()
                            } else {
                                exercises.forEach { revealedAnswers[it.id] = true }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Indigo600)
                    ) {
                        Text(
                            text = if (revealedAnswers.size == exercises.size) "إخفاء الحلول" else "كشف جميع الحلول",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo700,
                            softWrap = false,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // Each of the 5 Sentences from Page 19
        exercises.forEach { item ->
            SinglePronomReplaceCard(
                item = item,
                isAnswerRevealed = revealedAnswers[item.id] == true,
                onToggleReveal = {
                    revealedAnswers[item.id] = !(revealedAnswers[item.id] ?: false)
                    if (revealedAnswers[item.id] == true) {
                        onScoreEarned(3)
                        audioHelper.playSuccessChime()
                    }
                },
                selectedQuizAnswer = userQuizAnswers[item.id],
                onSelectQuizAnswer = { choice ->
                    userQuizAnswers[item.id] = choice
                    revealedAnswers[item.id] = true
                    if (choice == item.nousSentenceFr) {
                        onScoreEarned(5)
                        audioHelper.playSuccessChime()
                    } else {
                        audioHelper.playErrorBuzz()
                    }
                },
                showGlobalMeaning = showAllMeanings,
                audioHelper = audioHelper
            )
        }
    }
}

@Composable
private fun SinglePronomReplaceCard(
    item: PronomOnReplaceItem,
    isAnswerRevealed: Boolean,
    onToggleReveal: () -> Unit,
    selectedQuizAnswer: String?,
    onSelectQuizAnswer: (String) -> Unit,
    showGlobalMeaning: Boolean,
    audioHelper: AudioHelper
) {
    var isLocalMeaningVisible by remember { mutableStateOf(false) }
    val showMeaning = showGlobalMeaning || isLocalMeaningVisible

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, if (isAnswerRevealed) Emerald500 else Indigo100)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header with number and audio buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Indigo600,
                        shape = CircleShape,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${item.number}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "الجملة (${item.number}) ص 19",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Indigo900
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TranslateIconButton(
                        isTranslated = showMeaning,
                        onClick = { isLocalMeaningVisible = !isLocalMeaningVisible },
                        size = 32
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    AudioPlayButton(
                        textToSpeak = if (isAnswerRevealed) "${item.onSentenceFr}. ${item.nousSentenceFr}" else item.onSentenceFr,
                        audioHelper = audioHelper,
                        backgroundColor = Indigo50,
                        iconTint = Indigo700,
                        size = 32
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Original Sentence with On (As written in booklet)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Slate50,
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 6.dp)) {
                        Text(
                            text = "${item.number}- ${item.onSentenceFr}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900
                        )
                        if (showMeaning) {
                            Text(
                                text = item.onSentenceAr,
                                fontSize = 11.sp,
                                color = Slate600
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Slate200
                    ) {
                        Text(
                            text = "الأصل بـ On",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dotted space or Answer with Nous
            if (!isAnswerRevealed) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Indigo50.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, Indigo100),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleReveal() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "........................................................",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate400Compat()
                        )

                        Text(
                            text = "انقر لكشف التحويل مع Nous ➔",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo700,
                            softWrap = false,
                            maxLines = 1
                        )
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Emerald100.copy(alpha = 0.35f),
                    border = BorderStroke(1.5.dp, Emerald500),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f).padding(end = 6.dp)) {
                                Text(
                                    text = "➔ ${item.nousSentenceFr}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Emerald600
                                )
                                if (showMeaning) {
                                    Text(
                                        text = item.nousSentenceAr,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Indigo900
                                    )
                                }
                            }

                            AudioPlayButton(
                                textToSpeak = item.nousSentenceFr,
                                audioHelper = audioHelper,
                                backgroundColor = Color.White,
                                iconTint = Emerald600,
                                size = 28
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Emerald500)
                        ) {
                            Text(
                                text = "تغيير وتصريف الفعل: ${item.verbTransformation}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Emerald600,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            // Quick Interactive Check
            Spacer(modifier = Modifier.height(8.dp))

            // Generator for two distractor choices based on common mistakes
            val options = remember(item.number) {
                when (item.number) {
                    1 -> listOf("Nous allons au club.", "Nous va au club.", "Nous allez au club.")
                    2 -> listOf("Nous jouons dans le jardin.", "Nous joue dans le jardin.", "Nous jouez dans le jardin.")
                    3 -> listOf("Nous sommes contents.", "Nous est content.", "Nous sont contents.")
                    4 -> listOf("Nous avons congé.", "Nous a congé.", "Nous avez congé.")
                    5 -> listOf("Nous choisissons le livre.", "Nous choisit le livre.", "Nous choisissez le livre.")
                    else -> listOf(item.nousSentenceFr)
                }
            }

            Text(
                text = "⚡ اختر التحويل الصحيح لاختبار فهمك :",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Slate700
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                options.forEach { opt ->
                    val isChosen = selectedQuizAnswer == opt
                    val isThisCorrect = opt == item.nousSentenceFr

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = when {
                            selectedQuizAnswer == null -> Slate50
                            isChosen && isThisCorrect -> Emerald100
                            isChosen && !isThisCorrect -> Rose100
                            isThisCorrect -> Emerald100.copy(alpha = 0.5f)
                            else -> Slate50
                        },
                        border = BorderStroke(
                            1.dp,
                            when {
                                selectedQuizAnswer == null -> Slate300()
                                isChosen && isThisCorrect -> Emerald500
                                isChosen && !isThisCorrect -> Rose500
                                isThisCorrect -> Emerald500
                                else -> Slate200
                            }
                        ),
                        modifier = Modifier.clickable { onSelectQuizAnswer(opt) }
                    ) {
                        Text(
                            text = opt,
                            fontSize = 11.sp,
                            fontWeight = if (isChosen) FontWeight.Black else FontWeight.Medium,
                            color = when {
                                selectedQuizAnswer == null -> Slate800
                                isChosen && isThisCorrect -> Emerald600
                                isChosen && !isThisCorrect -> Rose600
                                else -> Slate700
                            },
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

// Utility colors
@Composable
private fun Slate300(): Color = Color(0xFFCBD5E1)

@Composable
private fun Slate400Compat(): Color = Color(0xFF94A3B8)
