package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald300
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald900
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange500
import com.example.ui.theme.Orange600
import com.example.ui.theme.Orange700
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose200
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose700
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
import com.example.ui.theme.Teal700
import com.example.ui.theme.Violet100
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700
import com.example.ui.theme.Violet900

// Data structures for Interrogatives (Pages 46-50)

data class InterrogativeWordRule(
    val id: String,
    val wordFr: String,
    val wordAr: String,
    val purposeFr: String,
    val purposeAr: String,
    val examples: List<InterrogativeExample>,
    val emoji: String,
    val color: Color,
    val bg: Color
)

data class InterrogativeExample(
    val question: String,
    val response: String,
    val note: String? = null
)

data class InterrogativeEx1Question(
    val id: Int,
    val questionTemplate: String, // e.g. "............. est absent?"
    val response: String,         // e.g. "- Ali est absent."
    val correctAnswer: String,
    val options: List<String>,
    val arabicTranslation: String,
    val ruleExplanationAr: String
)

data class InterrogativeEx2Question(
    val id: Int,
    val questionTemplate: String, // e.g. "1- .............. vas-tu au club?"
    val response: String,         // e.g. "- Le matin, je vais au club."
    val options: List<String>,
    val correctAnswer: String,
    val arabicTranslation: String,
    val ruleExplanationAr: String
)

@Composable
fun Unit2InterrogativesSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var activeSubTab by remember { mutableIntStateOf(0) }
    // 0: 📜 طريقة تكوين السؤال (ص 46)
    // 1: ❓ أدوات الاستفهام (ص 46-48)
    // 2: ✍️ تمرين 1 أكمل بأداة الاستفهام (ص 49)
    // 3: 🎯 تمرين 2 اختر (ص 50)
    // 4: 🔍 ملاحظات هامة Note Bien (ص 48)

    val subTabs = listOf(
        "📜 طريقة تكوين السؤال (ص 46)",
        "❓ أدوات الاستفهام (ص 46-48)",
        "✍️ تمرين 1 (ص 49)",
        "🎯 تمرين 2 (ص 50)",
        "🌟 Note Bien (ص 48)"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.5.dp, Indigo600.copy(alpha = 0.4f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Indigo600
                    ) {
                        Text(
                            text = "Grammaire • Unité 2",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Amber100
                    ) {
                        Text(
                            text = "📖 صفحات 46 إلى 50",
                            color = Amber800,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(
                    text = "Les Mots Interrogatifs (أدوات الاستفهام)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )

                Text(
                    text = "تعلم كيفية صياغة السؤال باللغة الفرنسية خطوة بخطوة، مع جميع أدوات الاستفهام المقررة في المعهد وشواهدها وتمارين ص 49 وص 50.",
                    fontSize = 12.sp,
                    color = Slate700,
                    lineHeight = 18.sp
                )

                Button(
                    onClick = {
                        audioHelper.speak("Les mots interrogatifs. Pour poser une question: on supprime la partie soulignée, et on change les pronoms.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo600),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("استمع لقاعدة الاستفهام بالفرنسية 🔊", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        // Horizontal navigation subtabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            subTabs.forEachIndexed { index, title ->
                val isSelected = activeSubTab == index
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isSelected) Indigo600 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Indigo600 else Slate200),
                    modifier = Modifier
                        .clickable { activeSubTab = index }
                        .testTag("interrogative_subtab_$index")
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.White else Slate700
                    )
                }
            }
        }

        // Render selected subtab
        when (activeSubTab) {
            0 -> InterrogativesHowToSubSection(audioHelper = audioHelper)
            1 -> InterrogativesRulesSubSection(audioHelper = audioHelper)
            2 -> InterrogativesEx1SubSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            3 -> InterrogativesEx2SubSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            4 -> InterrogativesNoteBienSubSection(audioHelper = audioHelper)
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 0: طـريقة تكوين السؤال (ص 46)
// -------------------------------------------------------------
@Composable
private fun InterrogativesHowToSubSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Pour poser une question : (خطوات تكوين السؤال - ص 46)",
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp,
                    color = Indigo900
                )

                // Step 1
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Rose50,
                    border = BorderStroke(1.dp, Rose200)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Rose600,
                            modifier = Modifier.size(26.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("1", color = Color.White, fontWeight = FontWeight.Black, fontSize = 13.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "1- On supprime la partie soulignée.",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = Rose700
                            )
                            Text(
                                "نحذف الجزء الذي تحته خط (المسؤول عنه) من الإجابة ونضع أداة الاستفهام المناسبة له.",
                                fontSize = 11.sp,
                                color = Slate700
                            )
                        }
                    }
                }

                // Step 2
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Indigo50,
                    border = BorderStroke(1.dp, Indigo100)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Indigo600,
                            modifier = Modifier.size(26.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("2", color = Color.White, fontWeight = FontWeight.Black, fontSize = 13.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "2- On change les pronoms.",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = Indigo900
                            )
                            Text(
                                "نحول ضمائر الفاعل وصفات الملكية من المتكلم إلى المخاطب.",
                                fontSize = 11.sp,
                                color = Slate700
                            )
                        }
                    }
                }

                // Transformation Table
                Text(
                    text = "جدول التحويلات المعتمد في الكتيّب :",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Slate800
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Pronouns
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate50),
                        border = BorderStroke(1.dp, Slate200)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("ضمائر الفاعل :", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Indigo700)
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text("je", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Slate800)
                                Text("➔", color = Indigo600, fontWeight = FontWeight.Bold)
                                Text("tu", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Indigo700)
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text("nous", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Slate800)
                                Text("➔", color = Indigo600, fontWeight = FontWeight.Bold)
                                Text("vous", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Indigo700)
                            }
                        }
                    }

                    // Possessives
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate50),
                        border = BorderStroke(1.dp, Slate200)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("صفات الملكية :", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Orange600)
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text("mon / ma", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Slate800)
                                Text("➔", color = Orange600, fontWeight = FontWeight.Bold)
                                Text("ton / ta", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Orange700)
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text("mes", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Slate800)
                                Text("➔", color = Orange600, fontWeight = FontWeight.Bold)
                                Text("tes", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Orange700)
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text("notre / nos", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Slate800)
                                Text("➔", color = Orange600, fontWeight = FontWeight.Bold)
                                Text("votre / vos", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Orange700)
                            }
                        }
                    }
                }

                // N.B Alert
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Amber50,
                    border = BorderStroke(1.5.dp, Amber400)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("⚠️", fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                        Column {
                            Text(
                                "N.B : ( il – elle – ils – elles – on ) pas de changement.",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = Amber900
                            )
                            Text(
                                "ضمائر الغائب تبقى كما هي تماماً في السؤال بدون أي تغيير.",
                                fontSize = 11.sp,
                                color = Amber800
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 1: أدوات الاستفهام والشواهد (ص 46-48)
// -------------------------------------------------------------
@Composable
private fun InterrogativesRulesSubSection(audioHelper: AudioHelper) {
    val words = remember { getInterrogativeRulesList() }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            text = "📌 أدوات الاستفهام المقررة والشواهد التطبيقية (ص 46 - 48) :",
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = Slate800
        )

        words.forEach { item ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = item.bg),
                border = BorderStroke(1.5.dp, item.color.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(item.emoji, fontSize = 22.sp, modifier = Modifier.padding(end = 6.dp))
                            Column {
                                Text(
                                    text = item.wordFr,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = item.color
                                )
                                Text(
                                    text = item.wordAr,
                                    fontSize = 11.sp,
                                    color = Slate700
                                )
                            }
                        }

                        IconButton(
                            onClick = { audioHelper.speak(item.wordFr) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = item.color)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.9f)
                    ) {
                        Text(
                            text = "الاستخدام: ${item.purposeAr} (${item.purposeFr})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate800,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Examples from booklet
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        item.examples.forEach { ex ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, item.color.copy(alpha = 0.2f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "❓ ${ex.question}",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp,
                                            color = item.color
                                        )
                                        Text(
                                            text = "💬 ${ex.response}",
                                            fontSize = 12.sp,
                                            color = Slate800
                                        )
                                        if (ex.note != null) {
                                            Text(
                                                text = "• ${ex.note}",
                                                fontSize = 10.sp,
                                                color = Slate600
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = { audioHelper.speak("${ex.question} ${ex.response}") },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = item.color, modifier = Modifier.size(18.dp))
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

// -------------------------------------------------------------
// SUBSECTION 2: تمرين 1 أكمل بأداة الاستفهام (ص 49)
// -------------------------------------------------------------
@Composable
private fun InterrogativesEx1SubSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    val questions = remember { getInterrogativeEx1Questions() }
    val userAnswers = remember { mutableStateMapOf<Int, String>() }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.5.dp, Indigo600.copy(alpha = 0.4f))
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
                        "تمرين كتاب المعهد ص 49",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = Indigo900
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Indigo600
                    ) {
                        Text(
                            "11 نقطة امتحانية كاملة",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    text = "1) Complète par un mot d'interrogatif :",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    color = Indigo700
                )
                Text(
                    text = "اختر أداة الاستفهام المناسبة لكل سؤال بناءً على الإجابة المعطاة والكلمة التي تحتها خط.",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        // Questions
        questions.forEach { item ->
            val selected = userAnswers[item.id]
            val isAnswered = selected != null
            val isCorrect = selected == item.correctAnswer

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        !isAnswered -> Color.White
                        isCorrect -> Emerald50
                        else -> Rose50
                    }
                ),
                border = BorderStroke(
                    1.dp,
                    when {
                        !isAnswered -> Slate200
                        isCorrect -> Emerald500
                        else -> Rose500
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = CircleShape,
                                color = if (isAnswered && isCorrect) Emerald600 else if (isAnswered) Rose600 else Indigo600,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${item.id}",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = item.questionTemplate,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp,
                                    color = Slate900
                                )
                                Text(
                                    text = item.response,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Indigo700
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                val filled = item.questionTemplate.replace(".............", item.correctAnswer)
                                audioHelper.speak("$filled ${item.response}")
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Indigo600)
                        }
                    }

                    Text(
                        text = "الترجمة: ${item.arabicTranslation}",
                        fontSize = 10.sp,
                        color = Slate600
                    )

                    // Option buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item.options.forEach { opt ->
                            val isThisSelected = selected == opt
                            val isThisCorrect = opt == item.correctAnswer

                            val btnBg = when {
                                !isAnswered -> if (isThisSelected) Indigo600 else Slate100
                                isThisSelected && isCorrect -> Emerald600
                                isThisSelected && !isCorrect -> Rose600
                                isThisCorrect && !isCorrect -> Emerald100
                                else -> Slate100
                            }

                            val textCol = when {
                                !isAnswered -> if (isThisSelected) Color.White else Slate800
                                isThisSelected -> Color.White
                                isThisCorrect && !isCorrect -> Emerald700
                                else -> Slate700
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = btnBg,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (!isAnswered) {
                                            userAnswers[item.id] = opt
                                            if (opt == item.correctAnswer) {
                                                onScoreEarned(5)
                                                audioHelper.speak(item.correctAnswer)
                                            }
                                        }
                                    }
                            ) {
                                Text(
                                    text = opt,
                                    modifier = Modifier.padding(vertical = 7.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = textCol
                                )
                            }
                        }
                    }

                    // Explanation feedback
                    if (isAnswered) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCorrect) Emerald100.copy(alpha = 0.5f) else Rose100.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = if (isCorrect) " أحسنت! ${item.ruleExplanationAr}" else "❌ الصحيح هو: ${item.correctAnswer} (${item.ruleExplanationAr})",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Emerald700 else Rose700,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = {
                userAnswers.clear()
                Toast.makeText(context, "تمت إعادة تعيين التمرين!", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("إعادة حل تمرين 1 بالكامل", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 3: تمرين 2 اختر (ص 50)
// -------------------------------------------------------------
@Composable
private fun InterrogativesEx2SubSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    val questions = remember { getInterrogativeEx2Questions() }
    val userAnswers = remember { mutableStateMapOf<Int, String>() }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.5.dp, Amber500.copy(alpha = 0.4f))
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
                        "تمرين كتاب المعهد ص 50",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = Amber900
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Amber600
                    ) {
                        Text(
                            "2) Choisis :",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    text = "اختر أداة الاستفهام الصحيحة من بين الأقواس كما وردت تماماً في صفحة 50 من الكتيّب.",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        questions.forEach { item ->
            val selected = userAnswers[item.id]
            val isAnswered = selected != null
            val isCorrect = selected == item.correctAnswer

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        !isAnswered -> Color.White
                        isCorrect -> Emerald50
                        else -> Rose50
                    }
                ),
                border = BorderStroke(
                    1.dp,
                    when {
                        !isAnswered -> Slate200
                        isCorrect -> Emerald500
                        else -> Rose500
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${item.id}- ${item.questionTemplate}",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Slate900
                            )
                            Text(
                                text = item.response,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Orange700
                            )
                        }

                        IconButton(
                            onClick = {
                                val filled = item.questionTemplate.replace("..............", item.correctAnswer)
                                audioHelper.speak("$filled ${item.response}")
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Orange600)
                        }
                    }

                    Text(
                        text = "الترجمة: ${item.arabicTranslation}",
                        fontSize = 10.sp,
                        color = Slate600
                    )

                    // Options from booklet
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item.options.forEach { opt ->
                            val isThisSelected = selected == opt
                            val isThisCorrect = opt == item.correctAnswer

                            val btnBg = when {
                                !isAnswered -> if (isThisSelected) Amber600 else Slate100
                                isThisSelected && isCorrect -> Emerald600
                                isThisSelected && !isCorrect -> Rose600
                                isThisCorrect && !isCorrect -> Emerald100
                                else -> Slate100
                            }

                            val textCol = when {
                                !isAnswered -> if (isThisSelected) Color.White else Slate800
                                isThisSelected -> Color.White
                                isThisCorrect && !isCorrect -> Emerald700
                                else -> Slate700
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = btnBg,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (!isAnswered) {
                                            userAnswers[item.id] = opt
                                            if (opt == item.correctAnswer) {
                                                onScoreEarned(5)
                                                audioHelper.speak(item.correctAnswer)
                                            }
                                        }
                                    }
                            ) {
                                Text(
                                    text = opt,
                                    modifier = Modifier.padding(vertical = 7.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = textCol
                                )
                            }
                        }
                    }

                    if (isAnswered) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCorrect) Emerald100.copy(alpha = 0.5f) else Rose100.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = if (isCorrect) " أحسنت! ${item.ruleExplanationAr}" else "❌ الصحيح هو: ${item.correctAnswer} (${item.ruleExplanationAr})",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Emerald700 else Rose700,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = {
                userAnswers.clear()
                Toast.makeText(context, "تمت إعادة تعيين تمرين 2!", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("إعادة حل تمرين 2 بالكامل", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 4: NOTE BIEN (ص 48)
// -------------------------------------------------------------
@Composable
private fun InterrogativesNoteBienSubSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(2.dp, Amber500)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("☁️", fontSize = 26.sp, modifier = Modifier.padding(end = 8.dp))
                    Text(
                        text = "Note bien (ملاحظات ذهبية للامتحان ص 48)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber900
                    )
                }

                Text(
                    text = "أربعة تراكيب استفهامية أساسية لا يخلو منها أي امتحان فرنسي للصف الثاني الإعدادي :",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        // The 4 Note Bien Cards from page 48
        val noteBienCards = listOf(
            Triple(
                "1- Qui est-ce ? (مَن هذا للعاقل؟)",
                listOf("- C'est Ahmed.", "- C'est le professeur.", "- C'est ma sœur."),
                "تُستخدم للسؤال عن شخص عاقل (مفرد أو جمع)"
            ),
            Triple(
                "2- Qu'est-ce que c'est ? (ما هذا لغير العاقل؟)",
                listOf("- C'est un chat.", "- C'est ma gomme.", "- C'est un stylo."),
                "تُستخدم للسؤال عن شيء أو حيوان غير عاقل"
            ),
            Triple(
                "3- Quel âge as-tu ? (كم عمرك؟)",
                listOf("- J'ai douze ans.", "- J'ai 9 ans."),
                "تُستخدم (Quel) المذكر لأن كلمة âge مفرد مذكر"
            ),
            Triple(
                "4- Quelle heure est-il ? (كم الساعة؟)",
                listOf("- Il est deux heures.", "- Il est 7 heures.", "- Il est cinq heures."),
                "تُستخدم (Quelle) المؤنث لأن كلمة heure مفرد مؤنث"
            )
        )

        noteBienCards.forEachIndexed { idx, (title, examples, explanation) ->
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
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
                            text = title,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = Indigo700
                        )

                        IconButton(
                            onClick = { audioHelper.speak(title.substringBefore(" (")) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Indigo600)
                        }
                    }

                    Text(
                        text = "💡 $explanation",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Amber800
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        examples.forEach { ex ->
                            Text(
                                text = ex,
                                fontSize = 12.sp,
                                color = Slate800,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// DATA FACTORIES FOR INTERROGATIVES
// -------------------------------------------------------------
private fun getInterrogativeRulesList(): List<InterrogativeWordRule> {
    return listOf(
        // 1) Est-ce que (p.46)
        InterrogativeWordRule(
            id = "est_ce_que",
            wordFr = "1) Est-ce que",
            wordAr = "هل (للسؤال عن مضمون الجملة ويُجاب بـ Oui / Non)",
            purposeFr = "Interrogation totale",
            purposeAr = "السؤال الكامل عن الجملة برمتها",
            examples = listOf(
                InterrogativeExample(
                    question = "Est ce que tu aimes les fruits?",
                    response = "- Oui, j'aime les fruits. / - Non, je n'aime pas les fruits.",
                    note = "عند وجود Oui أو Non في الإجابة نحذفها ونبدأ السؤال بـ Est-ce que"
                )
            ),
            emoji = "🙋",
            color = Indigo700,
            bg = Indigo50
        ),

        // 2) Qui (p.46)
        InterrogativeWordRule(
            id = "qui",
            wordFr = "2) Qui (sujet / personne)",
            wordAr = "مَن (للسؤال عن فاعل أو مفعول عاقل)",
            purposeFr = "Pour une personne",
            purposeAr = "السؤال عن شخص عاقل",
            examples = listOf(
                InterrogativeExample(
                    question = "Qui est ce?",
                    response = "- C'est Ahmed. / - C'est le professeur. / - C'est ma soeur.",
                    note = "Qui est-ce ? = من هذا؟ للسؤال عن هوية الشخص العاقل"
                ),
                InterrogativeExample(
                    question = "Qui est absent?",
                    response = "Ali est absent.",
                    note = "حذفنا الفاعل العاقل (Ali) ووضعنا مكانه Qui بدون تغيير باقي الجملة!"
                )
            ),
            emoji = "👤",
            color = Teal700,
            bg = Teal50
        ),

        // 3) Que / Qu'est-ce que (p.47)
        InterrogativeWordRule(
            id = "que",
            wordFr = "3) Que / Qu'est-ce que",
            wordAr = "ماذا (للسؤال عن مفعول به غير عاقل)",
            purposeFr = "Pour une chose (Que + v. + sujet / Qu'est-ce que + sujet + v.)",
            purposeAr = "السؤال عن مفعول غير عاقل أو نشاط",
            examples = listOf(
                InterrogativeExample(
                    question = "Que fais-tu?",
                    response = "- Je fais le devoir.",
                    note = "صيغة تقديم الفعل على الفاعل: Que + fais-tu ?"
                ),
                InterrogativeExample(
                    question = "Qu'est ce qu'il fait?",
                    response = "- Il est pilote.",
                    note = "صيغة الفاعل أولاً: Qu'est-ce qu'il fait ?"
                ),
                InterrogativeExample(
                    question = "Qu'est ce que c'est?",
                    response = "C'est un chat. / C'est ma gomme.",
                    note = "ما هذا لغير العاقل؟ (حيوان أو جماد)"
                )
            ),
            emoji = "📝",
            color = Orange600,
            bg = Orange50
        ),

        // 4) Où (p.47)
        InterrogativeWordRule(
            id = "ou",
            wordFr = "4) Où",
            wordAr = "أين (للسؤال عن المكان المسبوق بحرف جر)",
            purposeFr = "Pour le lieu",
            purposeAr = "السؤال عن المكان",
            examples = listOf(
                InterrogativeExample(
                    question = "Où vas-tu?",
                    response = "- Je vais à l'école."
                ),
                InterrogativeExample(
                    question = "Où est ce qu'il va?",
                    response = "- Il va au cinema."
                )
            ),
            emoji = "📍",
            color = Emerald700,
            bg = Emerald50
        ),

        // 5) Pourquoi (p.47)
        InterrogativeWordRule(
            id = "pourquoi",
            wordFr = "5) Pourquoi",
            wordAr = "لماذا (للسؤال عن السبب أو الهدف والغاية)",
            purposeFr = "Pour la cause ou le but",
            purposeAr = "السبب: parce que / car + جملة | الغاية: pour + مصدر",
            examples = listOf(
                InterrogativeExample(
                    question = "Pourquoi vas-tu chez le médecin?",
                    response = "Je vais chez le médecin parce que / car je suis malade.\nJe vais chez le médecin pour me soigner.",
                    note = "parce que/car يليهما فاعل وفعل، بينما pour يليها فعل في المصدر (infinitif)"
                ),
                InterrogativeExample(
                    question = "Pourquoi est ce qu'il va au club?",
                    response = "- Il va au club pour faire du sport."
                )
            ),
            emoji = "❓",
            color = Rose600,
            bg = Rose50
        ),

        // 6) Quand (p.47)
        InterrogativeWordRule(
            id = "quand",
            wordFr = "6) Quand",
            wordAr = "متى (للسؤال عن الزمان والتاريخ والوقت العام)",
            purposeFr = "Pour le temps / la date",
            purposeAr = "السؤال عن الزمن أو الفصل أو الوقت",
            examples = listOf(
                InterrogativeExample(
                    question = "Quand vas-tu à l'école?",
                    response = "- Je vais à l'école le matin."
                ),
                InterrogativeExample(
                    question = "Quand est ce qu'il va à Alexandrie?",
                    response = "- Il va à Alexandrie en été."
                )
            ),
            emoji = "⏰",
            color = Amber700,
            bg = Amber50
        ),

        // 7) Comment (p.48)
        InterrogativeWordRule(
            id = "comment",
            wordFr = "7) Comment",
            wordAr = "كيف (للاسم، الحال، الوصف، ووسيلة المواصلات)",
            purposeFr = "l'état – les adjectifs – les moyens de transports",
            purposeAr = "السؤال عن الاسم، الحالة، الصفة، والمواصلات",
            examples = listOf(
                InterrogativeExample(
                    question = "Comment s'appelle-t-elle? / Comment tu t'appelles?",
                    response = "- Elle s'appelle Hoda. / Je m'appelle Ahmed.",
                    note = "السؤال عن الاسم"
                ),
                InterrogativeExample(
                    question = "Comment ça va?",
                    response = "- Ça va bien merci.",
                    note = "السؤال عن الحال والصحة"
                ),
                InterrogativeExample(
                    question = "Comment est ton frère?",
                    response = "- Mon frère est petit.",
                    note = "السؤال عن الصفة والوصف (adjectif)"
                ),
                InterrogativeExample(
                    question = "Comment vas-tu à la maison?",
                    response = "- Je vais à la maison en voiture.",
                    note = "السؤال عن وسيلة المواصلات (en voiture, en bus...)"
                )
            ),
            emoji = "🚗",
            color = Violet700,
            bg = Violet50
        )
    )
}

private fun getInterrogativeEx1Questions(): List<InterrogativeEx1Question> {
    return listOf(
        InterrogativeEx1Question(
            id = 1,
            questionTemplate = "............. est absent?",
            response = "- Ali est absent.",
            correctAnswer = "Qui",
            options = listOf("Qui", "Que", "Où", "Quand"),
            arabicTranslation = "من غائب؟ - علي غائب.",
            ruleExplanationAr = "Ali فاعل عاقل (personne / sujet) ➔ نسأل عنه بـ (Qui)."
        ),
        InterrogativeEx1Question(
            id = 2,
            questionTemplate = "............. fais-tu?",
            response = "- Je fais le devoir.",
            correctAnswer = "Que",
            options = listOf("Que", "Qui", "Où", "Comment"),
            arabicTranslation = "ماذا تفعل؟ - أنا أعمل الواجب.",
            ruleExplanationAr = "le devoir مفعول به مباشر غير عاقل ➔ نسأل عنه بـ (Que) قبل الفعل والفاعل."
        ),
        InterrogativeEx1Question(
            id = 3,
            questionTemplate = "............. tu vas à l'école.",
            response = "- Oui, je vais à l'école.",
            correctAnswer = "Est-ce que",
            options = listOf("Est-ce que", "Qui", "Quand", "Pourquoi"),
            arabicTranslation = "هل تذهب إلى المدرسة؟ - نعم، أذهب إلى المدرسة.",
            ruleExplanationAr = "الإجابة تبدأ بـ (Oui) ➔ السؤال بهل (Est-ce que)."
        ),
        InterrogativeEx1Question(
            id = 4,
            questionTemplate = "............. est-ce?",
            response = "- C'est mon oncle.",
            correctAnswer = "Qui",
            options = listOf("Qui", "Que", "Où", "Quand"),
            arabicTranslation = "من هذا؟ - هذا عمي.",
            ruleExplanationAr = "mon oncle شخص عاقل ➔ السؤال (Qui est-ce ?)."
        ),
        InterrogativeEx1Question(
            id = 5,
            questionTemplate = "............. fais-tu le devoir?",
            response = "- Je fais le devoir le soir.",
            correctAnswer = "Quand",
            options = listOf("Quand", "Où", "Qui", "Comment"),
            arabicTranslation = "متى تعمل الواجب؟ - أعمل الواجب في المساء.",
            ruleExplanationAr = "le soir ظرف زمان (temps) ➔ نسأل عنه بـ (Quand)."
        ),
        InterrogativeEx1Question(
            id = 6,
            questionTemplate = "............. est-elle absente?",
            response = "- Parce qu'elle est malade.",
            correctAnswer = "Pourquoi",
            options = listOf("Pourquoi", "Comment", "Quand", "Où"),
            arabicTranslation = "لماذا هي غائبة؟ - لأنها مريضة.",
            ruleExplanationAr = "الإجابة تبدأ بـ (Parce que) التي تدل على السبب ➔ نسأل بـ (Pourquoi)."
        ),
        InterrogativeEx1Question(
            id = 7,
            questionTemplate = "............. vas-tu à l'ecole?",
            response = "- Je vais à l'école en voiture.",
            correctAnswer = "Comment",
            options = listOf("Comment", "Où", "Quand", "Qui"),
            arabicTranslation = "كيف تذهب إلى المدرسة؟ - أذهب إلى المدرسة بالسيارة.",
            ruleExplanationAr = "en voiture وسيلة مواصلات (moyen de transport) ➔ نسأل عنها بـ (Comment)."
        ),
        InterrogativeEx1Question(
            id = 8,
            questionTemplate = "............. vas-tu le vendredi?",
            response = "- Je vais au club.",
            correctAnswer = "Où",
            options = listOf("Où", "Quand", "Comment", "Qui"),
            arabicTranslation = "أين تذهب يوم الجمعة؟ - أذهب إلى النادي.",
            ruleExplanationAr = "au club مكان (lieu) ➔ نسأل عنه بـ (Où)."
        ),
        InterrogativeEx1Question(
            id = 9,
            questionTemplate = "............. c'est?",
            response = "- C'est un stylo.",
            correctAnswer = "Qu'est-ce que",
            options = listOf("Qu'est-ce que", "Qui", "Où", "Quand"),
            arabicTranslation = "ما هذا؟ - هذا قلم جاف.",
            ruleExplanationAr = "un stylo جماد غير عاقل ➔ السؤال (Qu'est-ce que c'est ?)."
        ),
        InterrogativeEx1Question(
            id = 10,
            questionTemplate = "Avec ............. vas-tu à l'école?",
            response = "- Je vais à l'école avec mon père.",
            correctAnswer = "qui",
            options = listOf("qui", "quoi", "que", "où"),
            arabicTranslation = "مع من تذهب إلى المدرسة؟ - مع والدي.",
            ruleExplanationAr = "avec mon père مع شخص عاقل ➔ (Avec qui)."
        ),
        InterrogativeEx1Question(
            id = 11,
            questionTemplate = "............. heure est-il?",
            response = "- Il est cinq heure.",
            correctAnswer = "Quelle",
            options = listOf("Quelle", "Quel", "Quels", "Quelles"),
            arabicTranslation = "كم الساعة؟ - إنها الساعة الخامسة.",
            ruleExplanationAr = "كلمة heure مفرد مؤنث ➔ تأخذ أداة الاستفهام (Quelle)."
        )
    )
}

private fun getInterrogativeEx2Questions(): List<InterrogativeEx2Question> {
    return listOf(
        InterrogativeEx2Question(
            id = 1,
            questionTemplate = ".............. vas-tu au club?",
            response = "- Le matin, je vais au club.",
            options = listOf("Qui", "Quand", "Est-ce que"),
            correctAnswer = "Quand",
            arabicTranslation = "متى تذهب إلى النادي؟ - في الصباح، أذهب إلى النادي.",
            ruleExplanationAr = "Le matin زمن ➔ الأداة المناسبة هي Quand."
        ),
        InterrogativeEx2Question(
            id = 2,
            questionTemplate = ".............. va Jean?",
            response = "- Jean va au cinéma.",
            options = listOf("Qui", "Quand", "Où"),
            correctAnswer = "Où",
            arabicTranslation = "أين يذهب جان؟ - جان يذهب إلى السينما.",
            ruleExplanationAr = "au cinéma مكان ➔ الأداة المناسبة هي Où."
        ),
        InterrogativeEx2Question(
            id = 3,
            questionTemplate = ".............. heure est-il?",
            response = "- Il est 7 heures.",
            options = listOf("Que", "Quelle", "À quelle"),
            correctAnswer = "Quelle",
            arabicTranslation = "كم الساعة؟ - إنها السابعة.",
            ruleExplanationAr = "السؤال الثابت عن الساعة الحالية هو: Quelle heure est-il ?"
        ),
        InterrogativeEx2Question(
            id = 5,
            questionTemplate = ".............. âge as-tu?",
            response = "- J'ai 9 ans.",
            options = listOf("Que", "Quel", "À quelle"),
            correctAnswer = "Quel",
            arabicTranslation = "كم عمرك؟ - عمري 9 سنوات.",
            ruleExplanationAr = "كلمة âge مفرد مذكر ➔ السؤال (Quel âge as-tu ?)."
        ),
        InterrogativeEx2Question(
            id = 6,
            questionTemplate = ".............. tu joues au football?",
            response = "- Oui, je joue au football.",
            options = listOf("Comment", "Est-ce que", "Quand"),
            correctAnswer = "Est-ce que",
            arabicTranslation = "هل تلعب كرة القدم؟ - نعم، ألعب كرة القدم.",
            ruleExplanationAr = "الإجابة تبدأ بـ Oui ➔ السؤال بـ Est-ce que."
        ),
        InterrogativeEx2Question(
            id = 7,
            questionTemplate = ".............. écris des articles?",
            response = "- Le journaliste écrit des artciles.",
            options = listOf("Que", "Qui", "Qui est ce"),
            correctAnswer = "Qui",
            arabicTranslation = "من يكتب المقالات؟ - الصحفي يكتب المقالات.",
            ruleExplanationAr = "Le journaliste فاعل عاقل ➔ السؤال بـ Qui."
        )
    )
}
