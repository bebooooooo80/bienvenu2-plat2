package com.example.ui.screens

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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.DialogueLine
import com.example.data.FrenchCourseData
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.InteractiveQuizCard
import com.example.ui.components.TranslateIconButton
import com.example.ui.components.UnitHeroBanner
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
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Violet100
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

@Composable
fun Unit1Screen(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    onNavigateToIntro: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "📖 Texte",
        "🗂️ Vocabulaire (p.14)",
        "🎭 Situations (p.15)",
        "✍️ Composition (p.16)",
        "📐 Grammaire (p.17-23)",
        "💬 Fais des phrases (p.24)",
        "🏢 Lieux & Personnages (p.25)",
        "🎯 Exercices variés (p.26)",
        "🏦 Banque des mots (p.27-28)",
        "📝 Examen Mi-Terme (p.32-33)"
    )

    LazyColumn(
        modifier = modifier.padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            UnitHeroBanner(
                title = "Unité (1) : Invitation",
                subtitle = "Les amis chez Gamal • Vocabulaire p.14 • Situations p.15 • Composition p.16 • Grammaire p.17-23 • Fais des phrases p.24 • Lieux & Personnages p.25 • Exercices p.26 • Banque des mots p.27-28 • Examen Mi-Terme p.32-33",
                pageRange = "Pages 11-34",
                instituteTag = "Bienvenu 2",
                gradientColors = listOf(Violet700, Violet600, Indigo600),
                speechText = FrenchCourseData.unit1FullText,
                arabicSubtitle = FrenchCourseData.unit1FullTextArabic,
                audioHelper = audioHelper
            )
        }

        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
            ) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.White,
                    contentColor = Violet700,
                    edgePadding = 12.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Violet700,
                            height = 3.dp
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = {
                                audioHelper.playClick()
                                selectedTab = index
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Black else FontWeight.Bold,
                                    color = if (selectedTab == index) Violet700 else Slate600
                                )
                            },
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("unit1_tab_$index")
                        )
                    }
                }
            }
        }

        when (selectedTab) {
            0 -> {
                item {
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
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                                    Text(
                                        text = "💡 قبل البدء في دراسة الوحدة الأولى:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Indigo900
                                    )
                                    Text(
                                        text = "حل الأسئلة التمهيدية (ص 4-6) وراجع قواعد المضارع وحروف الجر والنفي",
                                        fontSize = 11.sp,
                                        color = Slate700
                                    )
                                }

                                Button(
                                    onClick = onNavigateToIntro,
                                    colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                    modifier = Modifier
                                        .defaultMinSize(minHeight = 44.dp)
                                        .testTag("unit1_btn_go_to_intro")
                                ) {
                                    Text(
                                        text = "التمهيدي ➔",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Unit1DialogueSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                    }
                }
            }
            1 -> {
                item {
                    Unit1VocabSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            2 -> {
                item {
                    Unit1SituationsSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            3 -> {
                item {
                    Unit1CompositionSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            4 -> {
                item {
                    Unit1GrammarSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            5 -> {
                item {
                    Unit1FaisDesPhrasesSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            6 -> {
                item {
                    Unit1LieuxPersonnagesSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            7 -> {
                item {
                    Unit1ExercicesPage26Section(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            8 -> {
                item {
                    Unit1BanqueDesMotsSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
            9 -> {
                item {
                    Unit1ExamenSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun OfficialExerciseHeader(
    number: String,
    titleFrench: String,
    titleArabic: String,
    audioHelper: AudioHelper,
    badgeColor: Color = Violet700,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.5.dp, badgeColor.copy(alpha = 0.4f)),
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
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
                    Surface(
                        shape = CircleShape,
                        color = badgeColor
                    ) {
                        Text(
                            text = number,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Text(
                        text = titleFrench,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900,
                        textDecoration = TextDecoration.Underline
                    )
                }

                AudioPlayButton(
                    textToSpeak = titleFrench,
                    audioHelper = audioHelper,
                    backgroundColor = Slate100,
                    size = 32
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "📌 $titleArabic",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = badgeColor
            )
        }
    }
}

@Composable
private fun BookletExercise4AssocieReferenceTable(
    audioHelper: AudioHelper
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Slate50),
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📋 جدول التوصيل كما ورد بالكتيّب (p. 13) :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate700
                )
                AudioPlayButton(
                    textToSpeak = "Moustafa est l'ami de Gamal. Gamal a passé ses vacances à la Mer Rouge. Le livre d'histoire parle des monuments français. Moustafa n'est pas allé au bord de la mer. Les amis ont beaucoup parlé pendant la soirée.",
                    audioHelper = audioHelper,
                    size = 30
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Colonne A
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Colonne (A)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Violet700
                    )
                    Text("a- Moustafa est", fontSize = 11.sp, color = Slate800)
                    Text("b- Gamal a passé ses vacances", fontSize = 11.sp, color = Slate800)
                    Text("c- Le livre d'histoire parle", fontSize = 11.sp, color = Slate800)
                    Text("d- Moustafa n'est pas allé", fontSize = 11.sp, color = Slate800)
                    Text("e- Les amis ont beaucoup parlé", fontSize = 11.sp, color = Slate800)
                }

                // Colonne B
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Colonne (B)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo700
                    )
                    Text("1) au bord de la mer", fontSize = 11.sp, color = Slate800)
                    Text("2) pendant la Soirée", fontSize = 11.sp, color = Slate800)
                    Text("3) à la Mer Rouge.", fontSize = 11.sp, color = Slate800)
                    Text("4) L'ami de Gamal.", fontSize = 11.sp, color = Slate800)
                    Text("5) des monuments français", fontSize = 11.sp, color = Slate800)
                }
            }
        }
    }
}

@Composable
private fun BookletExercise5CompleteReferenceCard(
    audioHelper: AudioHelper
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Slate50),
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📄 نص الإكمال الأصلي كما في الكتيّب (ص 13) :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate700
                )
                AudioPlayButton(
                    textToSpeak = "Gamal a reçu Jean Morelle, sa femme Suzanne et son ami Moustafa chez lui. Il était content parce que Jean Morelle lui a offert un livre d'histoire. Gamal, qui est professeur, a passé ses vacances au bord de la Mer Rouge avec ses élèves.",
                    audioHelper = audioHelper,
                    size = 30
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "• Gamal a reçu jean Morelle, sa .................. et son .................. Moustafa chez lui.",
                        fontSize = 12.sp,
                        color = Slate800,
                        lineHeight = 16.sp
                    )
                    Text(
                        text = "• IL était content parce que jean Morelle lui a offert ..................",
                        fontSize = 12.sp,
                        color = Slate800,
                        lineHeight = 16.sp
                    )
                    Text(
                        text = "• Gamal, qui est professeur, a passé .................. au bord de la mer .................. avec ses ..................",
                        fontSize = 12.sp,
                        color = Slate800,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun Unit1DialogueSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedExerciseFilter by remember { mutableIntStateOf(0) }
    val exerciseFilters = listOf(
        "Tous (15)",
        "Ex 2: Réponds (3)",
        "Ex 3: Vrai / Faux (4)",
        "Ex 4: Associe (5)",
        "Ex 5: Complète (3)"
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                    Text(
                        text = "🎁 Les amis chez Gamal",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900,
                        modifier = Modifier.weight(1f, fill = false).padding(end = 8.dp)
                    )
                    Surface(
                        color = Violet100,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Booklet p. 11-13",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "Écoute chaque réplique et réponds aux questions de compréhension :",
                    fontSize = 12.sp,
                    color = Slate600,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    FrenchCourseData.unit1Dialogues.forEach { item ->
                        Unit1DialogueBubble(item = item, audioHelper = audioHelper)
                    }
                }
            }
        }

        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Violet50,
            border = BorderStroke(1.dp, Violet100)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    Text(
                        text = "📝 أسئلة فهم النص الرسمية (كتيّب ص 13)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Violet700
                    )
                    Text(
                        text = "تمارين (2 و 3 و 4 و 5) برؤوس الأسئلة الرسمية ومطابقة لكتيّب المعهد",
                        fontSize = 11.sp,
                        color = Slate600
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Violet700
                ) {
                    Text(
                        text = "p. 13",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Filter chips for the 4 exercises
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            exerciseFilters.forEachIndexed { index, label ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedExerciseFilter == index) Violet700 else Slate100,
                    border = BorderStroke(
                        1.dp,
                        if (selectedExerciseFilter == index) Violet700 else Slate200
                    ),
                    modifier = Modifier.clickable { selectedExerciseFilter = index }
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (selectedExerciseFilter == index) FontWeight.Black else FontWeight.Medium,
                        color = if (selectedExerciseFilter == index) Color.White else Slate800,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // --- EXERCISE 2: Réponds aux questions :- ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 1) {
            OfficialExerciseHeader(
                number = "2",
                titleFrench = "2) Réponds aux questions :-",
                titleArabic = "أجب عن الأسئلة الآتية بناءً على النص :",
                audioHelper = audioHelper,
                badgeColor = Violet700
            )
            FrenchCourseData.unit1Ex2RepondsAuxQuestions.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }

        // --- EXERCISE 3: Mets (Vrai) Ou (faux) devant Chaque phrase :- ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 2) {
            OfficialExerciseHeader(
                number = "3",
                titleFrench = "3) Mets (Vrai) Ou (faux) devant Chaque phrase :-",
                titleArabic = "ضع علامة (صح Vrai) أو (خطأ Faux) أمام كل جملة :",
                audioHelper = audioHelper,
                badgeColor = Indigo700
            )
            FrenchCourseData.unit1Ex3VraiOuFaux.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }

        // --- EXERCISE 4: Associe les deux parties de la phrase d'après le document:- ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 3) {
            OfficialExerciseHeader(
                number = "4",
                titleFrench = "4) Associe les deux parties de la phrase d'après le document:-",
                titleArabic = "صِل شطري الجملة وفقاً لنص الوثيقة :",
                audioHelper = audioHelper,
                badgeColor = Violet600
            )
            BookletExercise4AssocieReferenceTable(audioHelper = audioHelper)
            FrenchCourseData.unit1Ex4Associe.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }

        // --- EXERCISE 5: Complète par des mots pris du document:- ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 4) {
            OfficialExerciseHeader(
                number = "5",
                titleFrench = "5) Complète par des mots pris du document:-",
                titleArabic = "أكمل الفراغات بكلمات مأخوذة من نص الوثيقة :",
                audioHelper = audioHelper,
                badgeColor = Indigo600
            )
            BookletExercise5CompleteReferenceCard(audioHelper = audioHelper)
            FrenchCourseData.unit1Ex5Complete.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }
    }
}

@Composable
private fun Unit1PronounSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var challengeIndex by remember { mutableIntStateOf(0) }
    var userSelectedAnswer by remember { mutableStateOf<String?>(null) }
    val currentExercise = FrenchCourseData.pronounExercises[challengeIndex]

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // COD Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Indigo50),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Indigo100)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "1. C.O.D (Direct)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                    Text(
                        text = "بدون حرف جر",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Indigo700
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "le (Masc.) • la (Fém.)\nl' (Voyelle) • les (Plur.)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Ex: Il regarde le match ➔ Il le regarde.",
                        fontSize = 10.sp,
                        color = Slate600
                    )
                }
            }

            // COI Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Emerald100.copy(alpha = 0.4f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Emerald100)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "2. C.O.I (Indirect)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Emerald600
                    )
                    Text(
                        text = "à / au / aux + شخص",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald600
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "lui (Singulier M/F)\nleur (Pluriel M/F)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Ex: Je parle à mon ami ➔ Je lui parle.",
                        fontSize = 10.sp,
                        color = Slate600
                    )
                }
            }
        }

        // Interactive Exercise Box
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Violet100)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🎮 Entraînement interactif (Pages 22-23)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Violet700
                    )
                    Text(
                        text = "Question ${challengeIndex + 1} / ${FrenchCourseData.pronounExercises.size}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                var showPronounTranslation by remember(challengeIndex) { mutableStateOf(false) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentExercise.phrase,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900,
                        modifier = Modifier.weight(1f)
                    )

                    TranslateIconButton(
                        isTranslated = showPronounTranslation,
                        onClick = { showPronounTranslation = !showPronounTranslation },
                        contentDescription = "ترجمة عبارة تمرين الضمائر",
                        size = 32
                    )
                }

                ArabicTranslationBanner(
                    translation = currentExercise.arabicTranslation,
                    visible = showPronounTranslation
                )

                Text(
                    text = "Remplace par le bon pronom personnel :",
                    fontSize = 12.sp,
                    color = Slate600,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
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
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("pronoun_choice_$choice")
                        ) {
                            Text(
                                text = choice,
                                fontSize = 14.sp,
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
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Question suivante", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Unit1PossessiveSection(audioHelper: AudioHelper, onScoreEarned: (Int) -> Unit = {}) {
    Unit1GrammarSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
}

@Composable
private fun Unit1PlacesSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🏢 Les Lieux et Les Personnages (Page 25)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    FrenchCourseData.placesCharacters.forEach { pair ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Slate50,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(pair.emoji, fontSize = 16.sp)
                                    Text(
                                        text = pair.character,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Indigo700
                                    )
                                }
                                Text(
                                    text = "➔ " + pair.place,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Slate800
                                )
                            }
                        }
                    }
                }
            }
        }

        Text(
            text = "🎯 Test rapide : Où vas-tu pour ... ?",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = Indigo900
        )

        FrenchCourseData.placesQuestions.forEach { question ->
            InteractiveQuizCard(
                question = question,
                audioHelper = audioHelper,
                onCorrectAnswer = { onScoreEarned(1) }
            )
        }
    }
}

@Composable
private fun Unit1DialogueBubble(
    item: DialogueLine,
    audioHelper: AudioHelper
) {
    var isTranslated by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Slate50,
        border = androidx.compose.foundation.BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 4.dp, height = 44.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(item.borderColorHex))
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = item.speaker,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(item.borderColorHex)
                    )
                    Text(
                        text = item.text,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate900,
                        lineHeight = 17.sp
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.arabicNote.isNotEmpty()) {
                        TranslateIconButton(
                            isTranslated = isTranslated,
                            onClick = { isTranslated = !isTranslated },
                            contentDescription = "ترجمة عبارة ${item.speaker}",
                            size = 32
                        )
                    }

                    AudioPlayButton(
                        textToSpeak = item.text,
                        audioHelper = audioHelper,
                        backgroundColor = Indigo100,
                        iconTint = Indigo700,
                        size = 32
                    )
                }
            }

            ArabicTranslationBanner(
                translation = item.arabicNote,
                visible = isTranslated
            )
        }
    }
}
