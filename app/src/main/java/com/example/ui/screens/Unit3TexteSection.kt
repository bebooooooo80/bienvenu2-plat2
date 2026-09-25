package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.DialogueLine
import com.example.data.FrenchCourseData
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.InteractiveQuizCard
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
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
import com.example.ui.theme.Orange700
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose700
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Teal100
import com.example.ui.theme.Teal50
import com.example.ui.theme.Teal600
import com.example.ui.theme.Teal700

@Composable
fun Unit3TexteSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var activeSubTab by remember { mutableIntStateOf(0) }
    var showAllTranslations by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Navigation Sub Tabs (نص / حوار / تدريبات ص 61)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val subTabs = listOf(
                    Triple("📖 نص ص 59-60", "Lecture", 0),
                    Triple("💬 المحادثة", "Dialogue", 1),
                    Triple("📝 تدريبات ص 61", "Exercices", 2)
                )

                subTabs.forEach { (labelAr, labelFr, idx) ->
                    val isSelected = activeSubTab == idx
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Teal700 else Color.Transparent,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                audioHelper.playClick()
                                activeSubTab = idx
                            }
                            .testTag("unit3_subtab_$idx")
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = labelAr,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = if (isSelected) Color.White else Slate700
                            )
                            Text(
                                text = labelFr,
                                fontSize = 10.sp,
                                color = if (isSelected) Teal100 else Slate600
                            )
                        }
                    }
                }
            }
        }

        when (activeSubTab) {
            0 -> Unit3TextReadingView(
                audioHelper = audioHelper,
                showAllTranslations = showAllTranslations,
                onToggleTranslations = { showAllTranslations = !showAllTranslations }
            )
            1 -> Unit3DialogueView(audioHelper = audioHelper)
            2 -> Unit3ExercisesView(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// -------------------------------------------------------------
// 1. قراءة نص الوحدة ص 59 و 60
// -------------------------------------------------------------
@Composable
private fun Unit3TextReadingView(
    audioHelper: AudioHelper,
    showAllTranslations: Boolean,
    onToggleTranslations: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Quick Action Bar
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Teal50),
            border = BorderStroke(1.dp, Teal100)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🎧 استمع للنص كاملاً:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Teal700)
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = { audioHelper.speak(FrenchCourseData.unit3FullText) },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Teal700, CircleShape)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "تشغيل الكل", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (showAllTranslations) Teal700 else Color.White,
                    border = BorderStroke(1.dp, if (showAllTranslations) Teal700 else Slate200),
                    modifier = Modifier.clickable { onToggleTranslations() }
                ) {
                    Text(
                        text = if (showAllTranslations) "إخفاء الترجمة 👁️" else "إظهار الترجمة 🌐",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (showAllTranslations) Color.White else Slate700,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // --- PAGE 59: الحادث والوصول للمستشفى ---
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Page Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = RoundedCornerShape(8.dp), color = Teal100) {
                        Text(
                            text = "📖 Page 59 : L'accident",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal700,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = FrenchCourseData.unit3Page59Text,
                        audioHelper = audioHelper
                    )
                }

                Text(
                    text = "Santé / Hôpital (Partie 1)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Teal700
                )

                // French Narrative & Dialogue
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Slate50,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Samir, l'ami de Gamal a eu un accident dans la rue. L'ambulance l'a vite transporté à l'hôpital. Gamal et Ali sont allés le voir. Dans sa chambre à l'hôpital, Samir était couché. Il avait l'air fatigué et il parlait difficilement.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate900,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Mini Dialogue
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("— Gamal : Ça va bien maintenant?", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Indigo700)
                            Text("— Samir : Oui, merci.", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Emerald700)
                            Text("— Gamal : Qu'est-ce qui est arrivé?", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Indigo700)
                            Text("— Samir : Une voiture m'a renversé et je suis tombé.", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Rose700)
                        }
                    }
                }

                // Translation banner
                ArabicTranslationBanner(
                    translation = FrenchCourseData.unit3Page59TextArabic,
                    visible = showAllTranslations
                )

                // Vocabulary highlights
                Text("💡 كلمات مفتاحية هامة من صفحة 59:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate700)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    WordBadge("un accident", "حادث", Teal100, Teal700, Modifier.weight(1f))
                    WordBadge("l'ambulance", "الإسعاف", Orange100, Orange700, Modifier.weight(1f))
                    WordBadge("l'hôpital", "المستشفى", Indigo100, Indigo700, Modifier.weight(1f))
                }
            }
        }

        // --- PAGE 60: دخول الطبيب والفحص والنصائح ---
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Page Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = RoundedCornerShape(8.dp), color = Emerald100) {
                        Text(
                            text = "📖 Page 60 : La visite du médecin",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald700,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = FrenchCourseData.unit3Page60Text,
                        audioHelper = audioHelper
                    )
                }

                Text(
                    text = "L'examen & Les conseils (Partie 2)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Emerald700
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Slate50,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "À ce moment, le médecin et l'infirmière sont entrés.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )

                        // Doctor & Samir dialogue
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("— Le médecin : Ça va aujourd'hui?", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Teal700)
                            Text("— Samir : Oui docteur, mais j'ai encore mal au bras et à la jambe.", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Rose700)
                        }

                        Text(
                            text = "Le médecin l'a examiné, il a lu la radiographie et le rapport et lui a dit :",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate800
                        )

                        // Doctor's exact advice
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Emerald50,
                            border = BorderStroke(1.dp, Emerald600.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "« Ne t'inquiète pas, repose-toi bien, et prends tes médicaments. Je te conseille de prendre encore ce calmant. Tu peux marcher dans quelques jours. »",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Emerald700,
                                modifier = Modifier.padding(10.dp),
                                lineHeight = 19.sp
                            )
                        }

                        Text(
                            text = "Quand le médecin est sorti, Gamal et Ali ont salué leur ami et sont rentrés chez eux.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate700
                        )
                    }
                }

                // Translation banner
                ArabicTranslationBanner(
                    translation = FrenchCourseData.unit3Page60TextArabic,
                    visible = showAllTranslations
                )

                // Vocabulary highlights
                Text("💡 كلمات وتعبيرات هامة من صفحة 60:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate700)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    WordBadge("le médecin", "الطبيب", Emerald100, Emerald700, Modifier.weight(1f))
                    WordBadge("la radiographie", "الأشعة", Rose100, Rose700, Modifier.weight(1f))
                    WordBadge("le calmant", "المسكن", Indigo100, Indigo700, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun WordBadge(french: String, arabic: String, bg: Color, textCol: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bg,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(french, fontSize = 11.sp, fontWeight = FontWeight.Black, color = textCol)
            Text(arabic, fontSize = 10.sp, color = textCol.copy(alpha = 0.85f))
        }
    }
}

// -------------------------------------------------------------
// 2. محادثة الشخصيات مجزأة جملة بجملة
// -------------------------------------------------------------
@Composable
private fun Unit3DialogueView(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "💬 المحادثة مقسمة لكل شخصية مع الاستماع والترجمة:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = Slate800
        )

        FrenchCourseData.unit3Dialogues.forEach { line ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(line.borderColorHex).copy(alpha = 0.35f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Avatar icon based on speaker
                    Surface(
                        shape = CircleShape,
                        color = Color(line.borderColorHex).copy(alpha = 0.15f),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = when {
                                    line.speaker.contains("Gamal", ignoreCase = true) -> "👦"
                                    line.speaker.contains("Samir", ignoreCase = true) -> "🛌"
                                    line.speaker.contains("médecin", ignoreCase = true) -> "👨‍⚕️"
                                    else -> "📖"
                                },
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = line.speaker,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(line.borderColorHex)
                            )

                            IconButton(
                                onClick = { audioHelper.speak(line.text) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "استماع",
                                    tint = Color(line.borderColorHex),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Text(
                            text = line.text,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate900,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = line.arabicNote,
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. تدريبات الفهم والاستيعاب ص 61 (Vrai/Faux & Questions)
// -------------------------------------------------------------
@Composable
private fun Unit3ExercisesView(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Exercise A: Mets Vrai ou Faux
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Teal50),
            border = BorderStroke(1.5.dp, Teal600.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "A) Mets (Vrai) ou (Faux) devant chaque phrases :",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Teal700
                    )
                    Surface(shape = RoundedCornerShape(6.dp), color = Color.White) {
                        Text("ص 61", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate600, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
                Text(
                    text = "ضع علامة (صح) أو (خطأ) أمام كل جملة وفقاً لأحداث النص المقروء في المستشفى:",
                    fontSize = 11.sp,
                    color = Slate600
                )
            }
        }

        FrenchCourseData.unit3Ex1VraiOuFaux.forEach { question ->
            InteractiveQuizCard(
                question = question,
                audioHelper = audioHelper,
                onCorrectAnswer = { onScoreEarned(5) }
            )
        }

        // Exercise B: Réponds aux questions
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.5.dp, Indigo600.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "B) Réponds aux questions :",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                    Surface(shape = RoundedCornerShape(6.dp), color = Color.White) {
                        Text("ص 61", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate600, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
                Text(
                    text = "أجب عن الأسئلة الأربعة باختيار الإجابة الصحيحة النموذجية مع الشرح والترجمة:",
                    fontSize = 11.sp,
                    color = Slate600
                )
            }
        }

        FrenchCourseData.unit3Ex2QuestionsReponses.forEach { question ->
            InteractiveQuizCard(
                question = question,
                audioHelper = audioHelper,
                onCorrectAnswer = { onScoreEarned(5) }
            )
        }
    }
}
