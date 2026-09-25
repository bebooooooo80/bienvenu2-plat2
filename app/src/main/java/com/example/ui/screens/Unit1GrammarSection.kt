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
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.FrenchCourseData
import com.example.data.PossessiveExerciseItem
import com.example.data.PossessiveTableRow
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

@Composable
fun Unit1GrammarSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedMode by remember { mutableIntStateOf(0) }
    val modes = listOf(
        "🎒 1. صفات الملكية (ص 17)",
        "✍️ 2. تمرين الملكية (ص 18)",
        "🌟 3. ضمير On وتمارينه (ص 19)",
        "🌳 4. مخطط الضمائر والفاعل (ص 20)",
        "🎯 5. المفعول المباشر C.O.D (ص 21)",
        "🤝 6. غير المباشر C.O.I (ص 22)",
        "📝 7. تمرين الضمائر 15 جملة (ص 22-23)"
    )

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Main Grammar Header Banner
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
                            text = "📐 Grammaire de l'Unité (1)",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "قواعد الوحدة الأولى • كتيّب ص 17 إلى 23 • صفات الملكية وضمير On والضمائر الشخصية بالتتابع",
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
                            text = "p. 17-23 • قواعد",
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

                // Mode Selector Bar - Sequential from Page 17 to 23
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    modes.forEachIndexed { index, modeTitle ->
                        val isSelected = selectedMode == index
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Violet700 else Slate100,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) Violet700 else Slate200
                            ),
                            modifier = Modifier.clickable {
                                audioHelper.playClick()
                                selectedMode = index
                            }
                        ) {
                            Text(
                                text = modeTitle,
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

        when (selectedMode) {
            0 -> PossessiveTableMode(audioHelper = audioHelper)
            1 -> PossessiveOfficialExerciseMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            2 -> PronomOnPage19CombinedMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            3 -> PronounsPage20CombinedMode(audioHelper = audioHelper)
            4 -> PronounsCodView(audioHelper = audioHelper)
            5 -> PronounsCoiView(audioHelper = audioHelper)
            6 -> PronounsOfficial15ExercisesView(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Mode 2: ضمير On وتمارينه بالكامل ص 19
// -------------------------------------------------------------------------------------------------
@Composable
private fun PronomOnPage19CombinedMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var subTab by remember { mutableIntStateOf(0) }
    val subTabs = listOf(
        "🌟 الشرح والمخطط (ص 19)",
        "📝 تمرين Choisis (7 جمل)",
        "🔄 تمرين Remplace (5 جمل)"
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            subTabs.forEachIndexed { index, title ->
                val isSelected = subTab == index
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) Violet700 else Slate100,
                    border = BorderStroke(1.dp, if (isSelected) Violet700 else Slate200),
                    modifier = Modifier.clickable {
                        audioHelper.playClick()
                        subTab = index
                    }
                ) {
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color.White else Slate700,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        when (subTab) {
            0 -> PronomOnTheoryCard(audioHelper = audioHelper)
            1 -> PronomOnChoisisCard(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            2 -> PronomOnReplaceCard(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Mode 3: مخطط الضمائر الشجري وضمائر الفاعل ص 20 - 21
// -------------------------------------------------------------------------------------------------
@Composable
private fun PronounsPage20CombinedMode(audioHelper: AudioHelper) {
    var subTab by remember { mutableIntStateOf(0) }
    val subTabs = listOf(
        "🌳 المخطط الشجري العام (ص 20)",
        "👤 ضمائر الفاعل الستة وأمثلتها (ص 20-21)"
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            subTabs.forEachIndexed { index, title ->
                val isSelected = subTab == index
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) Indigo700 else Slate100,
                    border = BorderStroke(1.dp, if (isSelected) Indigo700 else Slate200),
                    modifier = Modifier.clickable {
                        audioHelper.playClick()
                        subTab = index
                    }
                ) {
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color.White else Slate700,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        when (subTab) {
            0 -> PronounsTreeDiagramView(audioHelper = audioHelper)
            1 -> PronounsSujetView(audioHelper = audioHelper)
        }
    }
}


// -------------------------------------------------------------------------------------------------
// Mode 0: Official Table from Page 17 & Example & N.B Rule
// -------------------------------------------------------------------------------------------------
@Composable
private fun PossessiveTableMode(audioHelper: AudioHelper) {
    var selectedRowSubject by remember { mutableStateOf("Je") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Official Table Card
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
                            text = "🎒 Tableau des adjectifs possessifs (Page 17)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "اضغط على أي فاعل لرؤية مثال حي وسماع النطق الفرنسي",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = "Les adjectifs possessifs. Mon, ma, mes. Ton, ta, tes. Son, sa, ses. Notre, notre, nos. Votre, votre, vos. Leur, leur, leurs.",
                        audioHelper = audioHelper,
                        backgroundColor = Indigo100,
                        iconTint = Indigo700,
                        size = 32
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Table structure matching page 17 exactly: Sujet | Masculin | Féminin | pluriel
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, Indigo600)
                ) {
                    Column {
                        // Header Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Indigo700)
                                .padding(horizontal = 8.dp, vertical = 9.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sujet",
                                modifier = Modifier.weight(1.1f),
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Masculin",
                                modifier = Modifier.weight(1f),
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Féminin",
                                modifier = Modifier.weight(1f),
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "pluriel",
                                modifier = Modifier.weight(1f),
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Data rows
                        FrenchCourseData.unit1PossessiveTable.forEachIndexed { index, row ->
                            val isSelected = selectedRowSubject == row.subject
                            val rowBg = when {
                                isSelected -> Violet100.copy(alpha = 0.7f)
                                index % 2 == 0 -> Slate50
                                else -> Color.White
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(rowBg)
                                    .clickable {
                                        selectedRowSubject = row.subject
                                        audioHelper.speak("${row.subject}. ${row.mascSingular}, ${row.femSingular}, ${row.plural}")
                                    }
                                    .padding(horizontal = 8.dp, vertical = 9.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = row.subject,
                                    modifier = Modifier.weight(1.1f),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    color = if (isSelected) Violet700 else Indigo900,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = row.mascSingular,
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Slate800,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = row.femSingular,
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Slate800,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = row.plural,
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Slate800,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Active Row Card with Example
                val activeRow = FrenchCourseData.unit1PossessiveTable.firstOrNull { it.subject == selectedRowSubject }
                    ?: FrenchCourseData.unit1PossessiveTable.first()

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Violet50,
                    border = BorderStroke(1.dp, Violet100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    color = Violet700,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = activeRow.subject,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = activeRow.arabicSubject,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Violet700
                                )
                            }

                            AudioPlayButton(
                                textToSpeak = activeRow.exampleFr,
                                audioHelper = audioHelper,
                                backgroundColor = Violet100,
                                iconTint = Violet700,
                                size = 28
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "💡 مثال: « ${activeRow.exampleFr} »",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo900
                        )
                        Text(
                            text = activeRow.exampleAr,
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }
                }
            }
        }

        // Example Breakdown from Page 17 (Ex. Je prends ............ crayon)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Emerald100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "💥 مثال الشرح التوضيحي بالكتيّب (Ex. Page 17)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Emerald600
                        )
                        Text(
                            text = "كيف تفكر وتحل بطريقة كتيّب منهج Bienvenu 2 خطوة بخطوة",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = "Je prends mon crayon. Le sujet est Je, le complément est crayon, masculin, donc on met mon.",
                        audioHelper = audioHelper,
                        backgroundColor = Emerald100,
                        iconTint = Emerald600,
                        size = 30
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Big Visual Example sentence
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Emerald100.copy(alpha = 0.4f),
                    border = BorderStroke(1.dp, Emerald500),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Je prends ............... crayon",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // The two branches as in booklet page 17
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Left Branch: Le Sujet
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Indigo50,
                        border = BorderStroke(1.dp, Indigo100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "↓ Le sujet (الفاعل)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            Text(
                                text = "• الفاعل هو « Je »\n• خياراته المتاحة :\n(mon – ma – mes)",
                                fontSize = 11.sp,
                                color = Slate800,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // Right Branch: Le Complément
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Amber50,
                        border = BorderStroke(1.dp, Amber400),
                        modifier = Modifier.weight(1.1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "↓ Le complément (المفعول)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Amber950
                            )
                            Text(
                                text = "• Masculin ➔ mon ✨\n• Féminin ➔ ma\n• Pluriel ➔ mes\n➔ crayon مفرد مذكر ➔ mon",
                                fontSize = 11.sp,
                                color = Slate800,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Emerald500,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "✅ الحل النموذجي : « Je prends mon crayon. » (أنا آخذ قلمي)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // N.B Golden Rule from Page 17
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.5.dp, Amber400)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    ) {
                        Text(text = "💥", fontSize = 16.sp)
                        Text(
                            text = "N.B (ملاحظة هامة جداً - كتيّب ص 17)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = Amber950
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = "Si le nom féminin commence par voyelle, on remplace: ma par mon, ta par ton, sa par son. Ton amie, son adresse, mon école.",
                        audioHelper = audioHelper,
                        backgroundColor = Color.White,
                        iconTint = Amber950,
                        size = 30
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Si le nom féminin commence par voyelle on remplace :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Text(
                    text = "«إذا كان الاسم المفرد المؤنث يبدأ بحرف متحرك (a, e, i, o, u, y, h)، نستبدل صفة الملكية المؤنثة بصفة مذكرة لمنع التقاء حرفين متحركين ولتسهيل النطق» :",
                    fontSize = 11.sp,
                    color = Slate800,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // The 3 replacements
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Surface(shape = RoundedCornerShape(8.dp), color = Color.White, border = BorderStroke(1.dp, Amber400)) {
                        Text(text = "ma ➔ mon", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Amber950, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                    }
                    Surface(shape = RoundedCornerShape(8.dp), color = Color.White, border = BorderStroke(1.dp, Amber400)) {
                        Text(text = "ta ➔ ton", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Amber950, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                    }
                    Surface(shape = RoundedCornerShape(8.dp), color = Color.White, border = BorderStroke(1.dp, Amber400)) {
                        Text(text = "sa ➔ son", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Amber950, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // The 3 examples from booklet: (ton amie – son adresse – mon école)
                Text(
                    text = "الأمثلة الرسمية بالكتيّب ص 17 :",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Amber950
                )
                Spacer(modifier = Modifier.height(4.dp))

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    FrenchCourseData.unit1VowelRuleExamples.forEach { (word, phrase, note) ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = phrase,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Indigo900
                                    )
                                    Text(
                                        text = "• $note",
                                        fontSize = 10.sp,
                                        color = Slate600
                                    )
                                }

                                AudioPlayButton(
                                    textToSpeak = phrase,
                                    audioHelper = audioHelper,
                                    backgroundColor = Indigo50,
                                    iconTint = Indigo700,
                                    size = 24
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
// Mode 1: Official Exercise from Page 18 (Complète par un adjectif possessif) - 9 sentences
// -------------------------------------------------------------------------------------------------
@Composable
private fun PossessiveOfficialExerciseMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    val exercises = FrenchCourseData.unit1PossessiveExercises

    // State to hold user answers: key = "exIndex_blankIndex", value = selected choice
    val userAnswers = remember { mutableStateMapOf<String, String>() }
    // State to show details/explanations
    val revealedExplanations = remember { mutableStateMapOf<Int, Boolean>() }
    var showAllTranslations by remember { mutableStateOf(false) }

    val correctCount = exercises.count { item ->
        item.targetBlanks.indices.all { blankIdx ->
            userAnswers["${item.number}_$blankIdx"]?.equals(item.targetBlanks[blankIdx], ignoreCase = true) == true
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Exercise Title Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "✍️ Complète par un adjectif possessif :",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(
                            text = "تمرين كتيّب المعهد ص 18 الرسمي كاملاً (9 جمل) • اختر صفة الملكية المناسبة",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }

                    Surface(
                        color = if (correctCount == exercises.size) Emerald600 else Indigo600,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$correctCount / ${exercises.size} مكتملة",
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { showAllTranslations = !showAllTranslations },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = if (showAllTranslations) "إخفاء الترجمة العربية" else "عرض الترجمة العربية للجميع",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            userAnswers.clear()
                            revealedExplanations.clear()
                            Toast.makeText(context, "تمت إعادة تعيين الإجابات للمحاولة مجدداً", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Slate100),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Slate700, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إعادة التمرين", fontSize = 11.sp, color = Slate700, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Each of the 9 Questions from Page 18
        exercises.forEach { item ->
            SinglePossessiveExerciseCard(
                item = item,
                userAnswers = userAnswers,
                isExplanationRevealed = revealedExplanations[item.number] == true,
                onToggleExplanation = {
                    revealedExplanations[item.number] = !(revealedExplanations[item.number] ?: false)
                },
                showGlobalTranslation = showAllTranslations,
                audioHelper = audioHelper,
                onScoreEarned = onScoreEarned
            )
        }

        // Final Celebration Banner when completed
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
                        text = "🎉 ممتاز جداً! أتممت جميع جمل كتيّب ص 18 بنجاح كامل!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Emerald600,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "أنت الآن تتقن صفات الملكية وقاعدة المتحرك N.B باحترافية تامة للأزهر الشريف 🌟",
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
private fun SinglePossessiveExerciseCard(
    item: PossessiveExerciseItem,
    userAnswers: MutableMap<String, String>,
    isExplanationRevealed: Boolean,
    onToggleExplanation: () -> Unit,
    showGlobalTranslation: Boolean,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var isLocalTranslated by remember { mutableStateOf(false) }
    val showTranslation = showGlobalTranslation || isLocalTranslated

    // Determine if question is fully correct
    val isFullyCorrect = item.targetBlanks.indices.all { idx ->
        userAnswers["${item.number}_$idx"]?.equals(item.targetBlanks[idx], ignoreCase = true) == true
    }

    val isAnyAnswered = item.targetBlanks.indices.any { idx ->
        userAnswers.containsKey("${item.number}_$idx")
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isFullyCorrect -> Emerald100.copy(alpha = 0.2f)
                isAnyAnswered -> Rose100.copy(alpha = 0.15f)
                else -> Color.White
            }
        ),
        border = BorderStroke(
            1.5.dp,
            when {
                isFullyCorrect -> Emerald500
                isAnyAnswered -> Rose500.copy(alpha = 0.4f)
                item.isVowelRule -> Amber400
                else -> Slate200
            }
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Top Row: Number + Vowel Rule Tag + Audio & Translation Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (isFullyCorrect) Emerald500 else Indigo700,
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

                    if (item.isVowelRule) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Amber400
                        ) {
                            Text(
                                text = "⚠️ فخ متحرك N.B",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Amber950,
                                softWrap = false,
                                maxLines = 1,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    if (isFullyCorrect) {
                        Text(
                            text = "✨ إجابة صحيحة",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald600
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = { isLocalTranslated = !isLocalTranslated },
                        contentDescription = "ترجمة الجملة رقم ${item.number}",
                        size = 30
                    )

                    AudioPlayButton(
                        textToSpeak = item.fullSentenceFr,
                        audioHelper = audioHelper,
                        backgroundColor = Indigo50,
                        iconTint = Indigo700,
                        size = 30
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // The French Sentence with interactive blank pills
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Slate50,
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    // Sentence text rendering
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = item.sentenceParts[0],
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )

                        // Blank 1
                        val val1 = userAnswers["${item.number}_0"]
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = when {
                                val1 == null -> Indigo100
                                val1 == item.targetBlanks[0] -> Emerald100
                                else -> Rose100
                            },
                            border = BorderStroke(
                                1.dp,
                                when {
                                    val1 == null -> Indigo600
                                    val1 == item.targetBlanks[0] -> Emerald500
                                    else -> Rose500
                                }
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = val1 ?: "........",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = when {
                                    val1 == null -> Indigo700
                                    val1 == item.targetBlanks[0] -> Emerald600
                                    else -> Rose600
                                },
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        if (item.sentenceParts.size > 1) {
                            Text(
                                text = item.sentenceParts[1],
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                        }

                        // Blank 2 if exists (like sentence 1)
                        if (item.targetBlanks.size > 1) {
                            val val2 = userAnswers["${item.number}_1"]
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = when {
                                    val2 == null -> Indigo100
                                    val2 == item.targetBlanks[1] -> Emerald100
                                    else -> Rose100
                                },
                                border = BorderStroke(
                                    1.dp,
                                    when {
                                        val2 == null -> Indigo600
                                        val2 == item.targetBlanks[1] -> Emerald500
                                        else -> Rose500
                                    }
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = val2 ?: "........",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = when {
                                        val2 == null -> Indigo700
                                        val2 == item.targetBlanks[1] -> Emerald600
                                        else -> Rose600
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (item.sentenceParts.size > 2) {
                            Text(
                                text = item.sentenceParts[2],
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                        }
                    }

                    ArabicTranslationBanner(
                        translation = item.translationAr,
                        visible = showTranslation
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Option Pickers for each blank
            item.targetBlanks.indices.forEach { blankIdx ->
                val currentChoice = userAnswers["${item.number}_$blankIdx"]
                val options = item.optionsPerBlank.getOrNull(blankIdx) ?: listOf("mon", "ma", "mes")

                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    if (item.targetBlanks.size > 1) {
                        Text(
                            text = "الفراغ رقم (${blankIdx + 1}) بالنسبة لـ ${item.complements[blankIdx]} :",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo700
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        options.forEach { opt ->
                            val isSelected = currentChoice == opt
                            val isCorrectOpt = opt == item.targetBlanks[blankIdx]

                            Button(
                                onClick = {
                                    val wasCorrectBefore = userAnswers["${item.number}_$blankIdx"] == item.targetBlanks[blankIdx]
                                    userAnswers["${item.number}_$blankIdx"] = opt

                                    if (opt == item.targetBlanks[blankIdx]) {
                                        if (!wasCorrectBefore) {
                                            audioHelper.playSuccessChime()
                                            onScoreEarned(1)
                                        }
                                    } else {
                                        audioHelper.playErrorBuzz()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = when {
                                        currentChoice == null -> Violet50
                                        isSelected && isCorrectOpt -> Emerald500
                                        isSelected && !isCorrectOpt -> Rose500
                                        else -> Slate100
                                    }
                                ),
                                border = BorderStroke(
                                    1.dp,
                                    when {
                                        isSelected && isCorrectOpt -> Emerald600
                                        isSelected && !isCorrectOpt -> Rose600
                                        else -> Violet100
                                    }
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = opt,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = when {
                                        isSelected -> Color.White
                                        else -> Indigo900
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Explanation / Analysis Accordion Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Slate100,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { onToggleExplanation() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Violet700, modifier = Modifier.size(14.dp))
                        Text(
                            text = if (isExplanationRevealed) "إخفاء الشرح والتحليل" else "💡 كيف ولماذا اخترنا الحل؟",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700
                        )
                    }
                }

                if (isFullyCorrect) {
                    Text(
                        text = "✅ الحل: ${item.fullSentenceFr}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald600
                    )
                }
            }

            AnimatedVisibility(visible = isExplanationRevealed) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Indigo50,
                    border = BorderStroke(1.dp, Indigo100),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "🎯 الفاعل : « ${item.subject} »", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo900)
                            Text(text = "• المفعول : ${item.complements.joinToString(", ")}", fontSize = 11.sp, color = Slate700)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
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
// Mode 2: Interactive Mechanism & Diagram (Ex. Page 17 solver)
// -------------------------------------------------------------------------------------------------
@Composable
private fun PossessiveMechanismMode(audioHelper: AudioHelper) {
    var chosenSubject by remember { mutableStateOf("Je") }
    var chosenGenderType by remember { mutableStateOf("masc") } // masc, fem_consonant, fem_vowel, plural

    val subjects = listOf("Je", "Tu", "Il / Elle", "Nous", "Vous", "Ils / Elles")

    // Calculate result
    val calculatedResult = when (chosenSubject) {
        "Je" -> when (chosenGenderType) {
            "masc" -> Pair("mon", "mon livre (كتابي)")
            "fem_consonant" -> Pair("ma", "ma trousse (مقلمتي)")
            "fem_vowel" -> Pair("mon", "mon école (مدرستي - قاعدة N.B)")
            else -> Pair("mes", "mes stylos (أقلامي)")
        }
        "Tu" -> when (chosenGenderType) {
            "masc" -> Pair("ton", "ton père (والدك)")
            "fem_consonant" -> Pair("ta", "ta mère (والدتك)")
            "fem_vowel" -> Pair("ton", "ton amie (صديقتك - قاعدة N.B)")
            else -> Pair("tes", "tes amis (أصدقاؤك)")
        }
        "Il / Elle" -> when (chosenGenderType) {
            "masc" -> Pair("son", "son devoir (واجبه)")
            "fem_consonant" -> Pair("sa", "sa voiture (سيارته)")
            "fem_vowel" -> Pair("son", "son adresse (عنوانه - قاعدة N.B)")
            else -> Pair("ses", "ses examens (امتحاناته)")
        }
        "Nous" -> when (chosenGenderType) {
            "masc", "fem_consonant", "fem_vowel" -> Pair("notre", "notre classe / notre école (فصلنا / مدرستنا)")
            else -> Pair("nos", "nos professeurs (معلمونا)")
        }
        "Vous" -> when (chosenGenderType) {
            "masc", "fem_consonant", "fem_vowel" -> Pair("votre", "votre sac / votre amie (حقيبتكم / صديقتكم)")
            else -> Pair("vos", "vos grand-parents (أجدادكم)")
        }
        else -> when (chosenGenderType) {
            "masc", "fem_consonant", "fem_vowel" -> Pair("leur", "leur école / leur maison (مدرستهم / منزلهم)")
            else -> Pair("leurs", "leurs parents (والداهم)")
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🔍 المخطط الشجري التفاعلي (Ex. Page 17)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Text(
                    text = "حدد الفاعل وحالة الاسم المملوك لترى كيف تختار الصفة الصحيحة فوراً وبدون تردد",
                    fontSize = 11.sp,
                    color = Slate600
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Step 1: Select Subject
                Text(
                    text = "1️⃣ الخطوة الأولى : حدد فاعل الجملة (Sujet) :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Violet700
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    subjects.forEach { subj ->
                        val isSelected = chosenSubject == subj
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Violet700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Violet700 else Slate200),
                            modifier = Modifier.clickable {
                                chosenSubject = subj
                                audioHelper.speak(subj)
                            }
                        ) {
                            Text(
                                text = subj,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = if (isSelected) Color.White else Slate800,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Step 2: Select Complement Type
                Text(
                    text = "2️⃣ الخطوة الثانية : ما هي حالة الاسم المملوك (Complément) ؟",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo700
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    val types = listOf(
                        "masc" to "مفرد مذكر (Masculin singulier) ➔ مثل: livre, stylo, crayon",
                        "fem_consonant" to "مفرد مؤنث يبدأ بساكن (Féminin) ➔ مثل: voiture, trousse, règle",
                        "fem_vowel" to "⚠️ مفرد مؤنث يبدأ بمتحرك (N.B) ➔ مثل: école, amie, adresse",
                        "plural" to "جمع بنوعيه (Pluriel -s / -x) ➔ مثل: amis, livres, parents"
                    )

                    types.forEach { (key, label) ->
                        val isSelected = chosenGenderType == key
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Indigo50 else Color.White,
                            border = BorderStroke(1.5.dp, if (isSelected) Indigo600 else Slate200),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { chosenGenderType = key }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .background(if (isSelected) Indigo600 else Color.Transparent, CircleShape)
                                        .border(1.dp, if (isSelected) Indigo600 else Slate500, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                    color = if (isSelected) Indigo900 else Slate800
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Output Result Card
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
                            text = "✨ الصفة المناسبة تلقائياً :",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald600
                        )

                        Text(
                            text = calculatedResult.first,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = Emerald600
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "مثال تطبيقي: « ${calculatedResult.second} »",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )

                            AudioPlayButton(
                                textToSpeak = calculatedResult.second,
                                audioHelper = audioHelper,
                                backgroundColor = Color.White,
                                iconTint = Emerald600,
                                size = 26
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Mode 3: N.B Golden Vowel Rule & Traps
// -------------------------------------------------------------------------------------------------
@Composable
private fun PossessiveVowelRuleNBMode(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Main Warning Card
        Card(
            shape = RoundedCornerShape(18.dp),
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
                        text = "⚠️ فخاخ وملاحظات N.B الرسمية (ص 17)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber950,
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )

                    AudioPlayButton(
                        textToSpeak = "Attention à la règle N.B. Devant un nom féminin commençant par une voyelle, on utilise mon, ton, son au lieu de ma, ta, sa.",
                        audioHelper = audioHelper,
                        backgroundColor = Color.White,
                        iconTint = Amber950,
                        size = 30
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "قاعدة ص 17 الذهبية بنص الكتيّب :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Text(
                    text = "« Si le nom féminin commence par voyelle on remplace : ma ➔ mon , ta ➔ ton , sa ➔ son »\n\nالحروف المتحركة في الفرنسية هي : (a - e - i - o - u - y) بالإضافة إلى حرف (h) الصامت.",
                    fontSize = 11.sp,
                    color = Slate800,
                    lineHeight = 17.sp
                )
            }
        }

        // Common Word Traps
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🎯 أشهر الكلمات المؤنثة البادئة بمتحرك في اختبارات الأزهر :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Spacer(modifier = Modifier.height(8.dp))

                val traps = listOf(
                    Triple("école (مدرسة)", "C'est mon école.", "مؤنث لكن يبدأ بـ é ➔ mon école وليس ma école ❌"),
                    Triple("amie (صديقة)", "Il aime son amie.", "مؤنث يبدأ بـ a ➔ son amie وليس sa amie ❌"),
                    Triple("adresse (عنوان)", "Quelle est ton adresse ?", "مؤنث يبدأ بـ a ➔ ton adresse وليس ta adresse ❌"),
                    Triple("idée (فكرة)", "C'est une bonne idée ➔ mon idée", "مؤنث يبدأ بـ i ➔ mon idée"),
                    Triple("équipe (فريق)", "J'encourage son équipe.", "مؤنث يبدأ بـ é ➔ son équipe"),
                    Triple("histoire (قصة)", "Je lis ton histoire.", "مؤنث يبدأ بـ h muet ➔ ton histoire")
                )

                traps.forEach { (word, example, note) ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Slate50,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                                Text(text = word, fontSize = 12.sp, fontWeight = FontWeight.Black, color = Indigo900)
                                Text(text = "« $example »", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Violet700)
                                Text(text = note, fontSize = 10.sp, color = Slate600)
                            }

                            AudioPlayButton(
                                textToSpeak = example,
                                audioHelper = audioHelper,
                                backgroundColor = Indigo100,
                                iconTint = Indigo700,
                                size = 26
                            )
                        }
                    }
                }
            }
        }

        // Trap 2: leur vs leurs vs leur (pronom)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Violet50),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "💡 الفرق الجوهري بين leur و leurs :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Violet700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "1. صفة الملكية للمفرد (leur) : Ils aiment leur professeur.\n2. صفة الملكية للجمع (leurs) : Elles aiment leurs professeurs. (كما في جملة 4 ص 18)\n3. ضمير المفعول غير المباشر (leur) : يوضع أمام الفعل ولا يأخذ (s) أبداً : Je leur parle.",
                    fontSize = 11.sp,
                    color = Slate800,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Mode 4: Complement Pronouns (COD / COI) from Unit 1
// -------------------------------------------------------------------------------------------------
@Composable
private fun Unit1PronounComplementMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var challengeIndex by remember { mutableIntStateOf(0) }
    var userSelectedAnswer by remember { mutableStateOf<String?>(null) }
    val currentExercise = FrenchCourseData.pronounExercises[challengeIndex]

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "🎯 Les pronoms compléments (COD / COI)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "ضمائر المفعول المباشر (le, la, l', les) وغير المباشر (lui, leur)",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = currentExercise.phrase,
                        audioHelper = audioHelper,
                        backgroundColor = Indigo100,
                        iconTint = Indigo700,
                        size = 32
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                var showPronounTranslation by remember(challengeIndex) { mutableStateOf(false) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentExercise.phrase,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900,
                        modifier = Modifier.weight(1f)
                    )

                    TranslateIconButton(
                        isTranslated = showPronounTranslation,
                        onClick = { showPronounTranslation = !showPronounTranslation },
                        contentDescription = "ترجمة عبارة تمرين الضمائر",
                        size = 30
                    )
                }

                ArabicTranslationBanner(
                    translation = currentExercise.arabicTranslation,
                    visible = showPronounTranslation
                )

                Text(
                    text = "Remplace par le bon pronom personnel :",
                    fontSize = 11.sp,
                    color = Slate600,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    currentExercise.choices.forEach { choice ->
                        val isChosen = userSelectedAnswer == choice
                        val isCorrect = choice == currentExercise.correct

                        Button(
                            onClick = {
                                userSelectedAnswer = choice
                                if (isCorrect) {
                                    audioHelper.playSuccessChime()
                                    onScoreEarned(1)
                                } else {
                                    audioHelper.playErrorBuzz()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when {
                                    userSelectedAnswer == null -> Violet600
                                    isChosen && isCorrect -> Emerald500
                                    isChosen && !isCorrect -> Rose500
                                    else -> Slate200
                                }
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = choice,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = if (userSelectedAnswer != null && !isChosen) Slate700 else Color.White
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = userSelectedAnswer != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        val isCorrect = userSelectedAnswer == currentExercise.correct
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isCorrect) Emerald100 else Rose100,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isCorrect) "🎉 Exact ! ${currentExercise.explanation}" else "❌ Attention : ${currentExercise.explanation}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Emerald600 else Rose600,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                userSelectedAnswer = null
                                challengeIndex = (challengeIndex + 1) % FrenchCourseData.pronounExercises.size
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Slate900),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("السؤال التالي", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }

        // Pronom ON Card (Page 19)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Violet50),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🌟 Le pronom (On) - Page 19 :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Violet700,
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )

                    AudioPlayButton(
                        textToSpeak = "Le pronom On. Nous allons au club = On va au club. Nous mangeons des gâteaux = On mange des gâteaux.",
                        audioHelper = audioHelper,
                        backgroundColor = Color.White,
                        iconTint = Violet700,
                        size = 28
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ضمير فاعل بمعنى «نحن» لكن يصرف الفعل معه دائماً مثل (il / elle) في المفرد !\n• Nous allons au club = On va au club.\n• Nous mangeons des gâteaux = On mange des gâteaux.",
                    fontSize = 11.sp,
                    color = Slate800,
                    lineHeight = 17.sp
                )
            }
        }
    }
}
