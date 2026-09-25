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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.data.RevisionData
import com.example.data.RevisionDocument
import com.example.data.RevisionMcqQuestion
import com.example.data.RevisionOpenQuestion
import com.example.data.RevisionTrueFalseQuestion
import com.example.ui.components.TranslateIconButton
import com.example.ui.components.UnitHeroBanner
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber200
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald300
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Emerald900
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo200
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange600
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose200
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose700
import com.example.ui.theme.Rose800
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
import com.example.ui.theme.Violet800
import com.example.ui.theme.Violet900

@Composable
fun RevisionScreen(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    onNavigateToIntro: () -> Unit = {},
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var mainTab by remember(initialTab) { mutableIntStateOf(initialTab) } // 0: Textes (p.67-71), 1: Grammaire (p.72-77), 2: Examen (p.78-79)

    val mainTabs = listOf(
        "📖 نصوص الفهم (p.67-71)",
        "📐 القواعد والتمارين (p.72-77)",
        "📝 امتحان نصف العام (p.78-79)"
    )

    LazyColumn(
        modifier = modifier.padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            UnitHeroBanner(
                title = "Révision de mi-année • مراجعة نصف العام",
                subtitle = when (mainTab) {
                    0 -> "نصوص الفهم والاستيعاب (Compréhension p.67-71) • منهج Bienvenu 2"
                    1 -> "قواعد النحو والتمارين الشاملة (Grammaire p.72-77) • منهج Bienvenu 2"
                    else -> "امتحان نصف العام الرسمي المعتمد (Examen p.78-79) • منهج Bienvenu 2"
                },
                pageRange = when (mainTab) {
                    0 -> "Pages 67-71"
                    1 -> "Pages 72-77"
                    else -> "Pages 78-79"
                },
                instituteTag = "Bienvenu 2",
                gradientColors = listOf(Indigo700, Violet700, Orange600),
                speechText = when (mainTab) {
                    0 -> "Révision générale des textes de compréhension."
                    1 -> "Révision de grammaire et exercices de la deuxième année préparatoire."
                    else -> "Examen officiel de mi-année pour la deuxième année préparatoire."
                },
                arabicSubtitle = when (mainTab) {
                    0 -> "مراجعة عامة وتدريبات شاملة على نصوص الفصل الدراسي الأول كاملة وفق كتيّب منهج Bienvenu 2."
                    1 -> "مراجعة وتدريبات قواعد النحو الكاملة (النفي، حروف الجر، الضمائر، المضارع، صفات الملكية)."
                    else -> "امتحان نصف العام الفعلي المطابق لصفحتي 78 و 79 بالكتيّب بدرجة رسمية 20/20."
                },
                audioHelper = audioHelper
            )
        }

        item {
            // Main Tabs Row (نصوص / قواعد / امتحان)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate200)
            ) {
                ScrollableTabRow(
                    selectedTabIndex = mainTab,
                    containerColor = Color.White,
                    contentColor = Indigo700,
                    edgePadding = 12.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[mainTab]),
                            color = Indigo700,
                            height = 3.dp
                        )
                    }
                ) {
                    mainTabs.forEachIndexed { index, title ->
                        Tab(
                            selected = mainTab == index,
                            onClick = {
                                audioHelper.playClick()
                                mainTab = index
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (mainTab == index) FontWeight.Black else FontWeight.Bold,
                                    color = if (mainTab == index) Indigo700 else Slate600
                                )
                            },
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("revision_main_tab_$index")
                        )
                    }
                }
            }
        }

        when (mainTab) {
            0 -> item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Indigo50),
                        border = BorderStroke(1.5.dp, Indigo100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f).padding(end = 10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text("🎯", fontSize = 16.sp)
                                    Text(
                                        text = "الأسئلة التمهيدية قبل الوحدات (ص 4-6)",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Indigo900
                                    )
                                }
                                Text(
                                    text = "37 سؤالاً في تصريف المضارع، أدوات الاستفهام، الأماكن، والنفي مع القواعد المرتبطة",
                                    fontSize = 11.sp,
                                    color = Slate700
                                )
                            }

                            Button(
                                onClick = onNavigateToIntro,
                                colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 44.dp)
                                    .testTag("btn_go_to_intro_exercises")
                            ) {
                                Text(
                                    text = "ابدأ الحل ➔",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    RevisionTextesSection(
                        audioHelper = audioHelper,
                        onScoreEarned = onScoreEarned
                    )
                }
            }
            1 -> item {
                RevisionGrammaireSection(
                    audioHelper = audioHelper,
                    onScoreEarned = onScoreEarned
                )
            }
            2 -> item {
                OfficialMidYearExamContent(
                    audioHelper = audioHelper,
                    onScoreEarned = onScoreEarned
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

// =========================================================================
// SECTION 1: نصوص الفهم والاستيعاب (الوثائق الأربع ص 67 - 71)
// =========================================================================
@Composable
private fun RevisionTextesSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val documents = remember { RevisionData.documents }
    var selectedDocIndex by remember { mutableIntStateOf(0) }
    val currentDoc = documents[selectedDocIndex]

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Document Selector Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            documents.forEachIndexed { idx, doc ->
                val isSelected = selectedDocIndex == idx
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Indigo700 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Indigo700 else Slate200),
                    modifier = Modifier
                        .clickable {
                            audioHelper.playClick()
                            selectedDocIndex = idx
                        }
                        .testTag("revision_doc_selector_$idx")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = when (doc.id) {
                                1 -> "💌 Doc (1)"
                                2 -> "🏥 Doc (2)"
                                3 -> "🍽️ Doc (3)"
                                else -> "🚶 Doc (4)"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Indigo700
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isSelected) Indigo900 else Slate100
                        ) {
                            Text(
                                text = doc.pageNumber,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Amber400 else Slate600,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Active Document View
        RevisionDocumentCard(
            document = currentDoc,
            audioHelper = audioHelper,
            onScoreEarned = onScoreEarned
        )
    }
}

@Composable
private fun RevisionDocumentCard(
    document: RevisionDocument,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    var showArabicText by remember { mutableStateOf(false) }

    // User answers state
    val mcqAnswers = remember(document.id) { mutableStateMapOf<String, Int>() }
    val tfAnswers = remember(document.id) { mutableStateMapOf<String, Boolean>() }
    val revealedAnswers = remember(document.id) { mutableStateMapOf<String, Boolean>() }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Document Text Box
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header of document
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Indigo50
                            ) {
                                Text(
                                    text = document.pageNumber,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Indigo700,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = document.documentType,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate600
                            )
                        }
                        Text(
                            text = document.titleFr,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = document.titleAr,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        TranslateIconButton(
                            isTranslated = showArabicText,
                            onClick = { showArabicText = !showArabicText },
                            contentDescription = "ترجمة نص الوثيقة"
                        )

                        IconButton(
                            onClick = { audioHelper.speak(document.textFr) },
                            modifier = Modifier
                                .size(36.dp)
                                .background(Indigo50, CircleShape)
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "استماع للنص كاملاً",
                                tint = Indigo700,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // French Document Body
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Slate50,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = document.textFr,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate900,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(14.dp)
                    )
                }

                // Arabic Translation Banner
                AnimatedVisibility(visible = showArabicText) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Teal50,
                        border = BorderStroke(1.dp, Teal100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "💬 الترجمة العربية للوثيقة:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Teal700
                            )
                            Text(
                                text = document.textAr,
                                fontSize = 12.sp,
                                color = Slate800,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }

                // Vocabulary Pills if available
                if (document.vocabulary.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Amber50,
                        border = BorderStroke(1.dp, Amber200),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "💡 مفردات هامة بالوثيقة (Vocabulaire) :",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Amber900
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                document.vocabulary.forEach { (fr, ar) ->
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color.White,
                                        border = BorderStroke(1.dp, Amber400),
                                        modifier = Modifier.clickable { audioHelper.speak(fr) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(fr, fontSize = 11.sp, fontWeight = FontWeight.Black, color = Amber900)
                                            Text("=", fontSize = 10.sp, color = Slate500)
                                            Text(ar, fontSize = 10.sp, color = Slate700)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // ==========================================================
        // SECTION A: Choisis la bonne réponse
        // ==========================================================
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "A) Choisis la bonne réponse :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Indigo50
                    ) {
                        Text(
                            text = "${document.mcqQuestions.size} أسئلة",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo700,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                document.mcqQuestions.forEach { mcq ->
                    RevisionMcqCard(
                        question = mcq,
                        selectedOption = mcqAnswers[mcq.id],
                        onOptionSelected = { chosenIdx ->
                            val isFirst = mcqAnswers[mcq.id] == null
                            mcqAnswers[mcq.id] = chosenIdx
                            if (isFirst && chosenIdx == mcq.correctIndex) {
                                onScoreEarned(5)
                            }
                        },
                        audioHelper = audioHelper
                    )
                }
            }
        }

        // ==========================================================
        // SECTION B: Mets (Vrai) ou (Faux)
        // ==========================================================
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "B) Mets (Vrai) ou (Faux) :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Violet900
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Violet50
                    ) {
                        Text(
                            text = "${document.trueFalseQuestions.size} عبارات",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                document.trueFalseQuestions.forEach { tf ->
                    RevisionTrueFalseCard(
                        question = tf,
                        userSelection = tfAnswers[tf.id],
                        onAnswerSelected = { ans ->
                            val isFirst = tfAnswers[tf.id] == null
                            tfAnswers[tf.id] = ans
                            if (isFirst && ans == tf.isTrue) {
                                onScoreEarned(5)
                            }
                        },
                        audioHelper = audioHelper
                    )
                }
            }
        }

        // ==========================================================
        // SECTION C: Réponds aux questions / Complète
        // ==========================================================
        if (document.completionQuestions.isNotEmpty()) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = when (document.id) {
                            1 -> "C) Réponds aux questions :"
                            2 -> "C) Complète selon le document :"
                            3 -> "C) Complète ce texte d'après le document :"
                            else -> "C) Qui peut dire ces phrases d'après le document? :"
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Teal700
                    )

                    document.completionQuestions.forEach { openQ ->
                        RevisionOpenAnswerCard(
                            question = openQ,
                            isRevealed = revealedAnswers[openQ.id] == true,
                            onToggleReveal = {
                                val current = revealedAnswers[openQ.id] == true
                                revealedAnswers[openQ.id] = !current
                                if (!current) {
                                    audioHelper.playClick()
                                }
                            },
                            audioHelper = audioHelper
                        )
                    }
                }
            }
        }

        // Reset Button for Current Document
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = {
                    audioHelper.playClick()
                    mcqAnswers.clear()
                    tfAnswers.clear()
                    revealedAnswers.clear()
                    Toast.makeText(context, "تمت إعادة ضبط تدريبات ${document.titleFr}", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("إعادة حل أسئلة الوثيقة", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// =========================================================================
// MCQ Card Component
// =========================================================================
@Composable
private fun RevisionMcqCard(
    question: RevisionMcqQuestion,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    audioHelper: AudioHelper
) {
    var showTrans by remember { mutableStateOf(false) }
    val isAnswered = selectedOption != null
    val isCorrect = selectedOption == question.correctIndex

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = when {
            !isAnswered -> Slate50
            isCorrect -> Emerald50
            else -> Rose50
        },
        border = BorderStroke(
            1.dp,
            when {
                !isAnswered -> Slate200
                isCorrect -> Emerald600
                else -> Rose600
            }
        ),
        modifier = Modifier.fillMaxWidth()
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
                Text(
                    text = question.questionFr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TranslateIconButton(
                        isTranslated = showTrans,
                        onClick = { showTrans = !showTrans },
                        contentDescription = "ترجمة السؤال"
                    )

                    IconButton(
                        onClick = { audioHelper.speak(question.questionFr) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "استماع",
                            tint = Indigo700,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = showTrans) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💬 ${question.questionAr}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                question.options.forEachIndexed { idx, opt ->
                    val isThisSelected = selectedOption == idx
                    val isThisCorrect = idx == question.correctIndex

                    val optBg = when {
                        !isAnswered -> Color.White
                        isThisSelected && isThisCorrect -> Emerald600
                        isThisSelected && !isThisCorrect -> Rose600
                        !isThisSelected && isThisCorrect -> Emerald100
                        else -> Color.White
                    }

                    val optText = when {
                        !isAnswered -> Slate800
                        isThisSelected -> Color.White
                        !isThisSelected && isThisCorrect -> Emerald900
                        else -> Slate600
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = optBg,
                        border = BorderStroke(
                            1.dp,
                            if (isAnswered && isThisCorrect) Emerald600 else Slate200
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (!isAnswered) {
                                    audioHelper.playClick()
                                    onOptionSelected(idx)
                                }
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = opt,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = optText
                            )

                            if (isAnswered) {
                                if (isThisCorrect) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = if (isThisSelected) Color.White else Emerald600, modifier = Modifier.size(16.dp))
                                } else if (isThisSelected) {
                                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            if (isAnswered) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isCorrect) Emerald100 else Rose100,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = if (isCorrect) "إجابة صحيحة! (+5 نقاط) 🎉" else "إجابة غير صحيحة 💡",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isCorrect) Emerald800 else Rose800
                        )
                        Text(
                            text = question.explanationAr,
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

// =========================================================================
// True / False Card Component
// =========================================================================
@Composable
private fun RevisionTrueFalseCard(
    question: RevisionTrueFalseQuestion,
    userSelection: Boolean?,
    onAnswerSelected: (Boolean) -> Unit,
    audioHelper: AudioHelper
) {
    var showTrans by remember { mutableStateOf(false) }
    val isAnswered = userSelection != null
    val isCorrect = userSelection == question.isTrue

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = when {
            !isAnswered -> Slate50
            isCorrect -> Emerald50
            else -> Rose50
        },
        border = BorderStroke(
            1.dp,
            when {
                !isAnswered -> Slate200
                isCorrect -> Emerald600
                else -> Rose600
            }
        ),
        modifier = Modifier.fillMaxWidth()
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
                Text(
                    text = question.statementFr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TranslateIconButton(
                        isTranslated = showTrans,
                        onClick = { showTrans = !showTrans },
                        contentDescription = "ترجمة العبارة"
                    )

                    IconButton(
                        onClick = { audioHelper.speak(question.statementFr) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "استماع",
                            tint = Violet700,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = showTrans) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💬 ${question.statementAr}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            // Vrai / Faux Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // VRAI button
                val isVraiChosen = userSelection == true
                val isVraiCorrect = question.isTrue
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = when {
                        !isAnswered -> Color.White
                        isVraiChosen && isVraiCorrect -> Emerald600
                        isVraiChosen && !isVraiCorrect -> Rose600
                        !isVraiChosen && isVraiCorrect -> Emerald100
                        else -> Color.White
                    },
                    border = BorderStroke(
                        1.dp,
                        if (isAnswered && isVraiCorrect) Emerald600 else Slate300
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (!isAnswered) {
                                audioHelper.playClick()
                                onAnswerSelected(true)
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✔️ Vrai (صحيح)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = when {
                                !isAnswered -> Slate800
                                isVraiChosen -> Color.White
                                isVraiCorrect -> Emerald900
                                else -> Slate500
                            }
                        )
                    }
                }

                // FAUX button
                val isFauxChosen = userSelection == false
                val isFauxCorrect = !question.isTrue
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = when {
                        !isAnswered -> Color.White
                        isFauxChosen && isFauxCorrect -> Emerald600
                        isFauxChosen && !isFauxCorrect -> Rose600
                        !isFauxChosen && isFauxCorrect -> Emerald100
                        else -> Color.White
                    },
                    border = BorderStroke(
                        1.dp,
                        if (isAnswered && isFauxCorrect) Emerald600 else Slate300
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (!isAnswered) {
                                audioHelper.playClick()
                                onAnswerSelected(false)
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "❌ Faux (خطأ)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = when {
                                !isAnswered -> Slate800
                                isFauxChosen -> Color.White
                                isFauxCorrect -> Emerald900
                                else -> Slate500
                            }
                        )
                    }
                }
            }

            if (isAnswered) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isCorrect) Emerald100 else Rose100,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = if (isCorrect) "إجابة صحيحة! (+5 نقاط) 🎉" else "إجابة غير صحيحة 💡",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isCorrect) Emerald800 else Rose800
                        )
                        Text(
                            text = question.explanationAr,
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

// =========================================================================
// Open Answer Card (Réponds aux questions / Complète / Qui peut dire)
// =========================================================================
@Composable
private fun RevisionOpenAnswerCard(
    question: RevisionOpenQuestion,
    isRevealed: Boolean,
    onToggleReveal: () -> Unit,
    audioHelper: AudioHelper
) {
    var showTrans by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
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
                Text(
                    text = question.promptFr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TranslateIconButton(
                        isTranslated = showTrans,
                        onClick = { showTrans = !showTrans },
                        contentDescription = "ترجمة المطلوب"
                    )

                    IconButton(
                        onClick = { audioHelper.speak(question.promptFr) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "استماع",
                            tint = Teal700,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = showTrans) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💬 ${question.promptAr}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            // Reveal Button
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isRevealed) Teal50 else Color.White,
                border = BorderStroke(1.dp, if (isRevealed) Teal600 else Slate300),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleReveal() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            if (isRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = if (isRevealed) Teal700 else Slate600,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isRevealed) "إخفاء الإجابة النموذجية" else "إظهار الإجابة النموذجية",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isRevealed) Teal700 else Slate700
                        )
                    }

                    if (question.hint.isNotEmpty() && !isRevealed) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Amber100
                        ) {
                            Text(
                                text = "💡 تلميح: ${question.hint}",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Amber900,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Model Answer Revealed
            AnimatedVisibility(visible = isRevealed) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Emerald50,
                    border = BorderStroke(1.dp, Emerald300),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "✅ الإجابة النموذجية (Réponse type) :",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Emerald900
                            )

                            IconButton(
                                onClick = { audioHelper.speak(question.modelAnswerFr) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "استماع للإجابة",
                                    tint = Emerald700,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Text(
                            text = "➔ ${question.modelAnswerFr}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900
                        )

                        Text(
                            text = "💬 ${question.modelAnswerAr}",
                            fontSize = 11.sp,
                            color = Slate700
                        )
                    }
                }
            }
        }
    }
}

