package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.FrenchCourseData
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.InteractiveQuizCard
import com.example.ui.components.TranslateIconButton
import com.example.ui.components.UnitHeroBanner
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
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
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
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
fun GrammarScreen(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember(initialTab) { mutableIntStateOf(initialTab) }
    val tabs = listOf(
        "🧱 La Phrase (p. 84)",
        "⏱️ Le Présent (p. 85)",
        "🗺️ Lieux (p. 86)",
        "🚫 Négation (p. 87)",
        "🎯 التمهيدي (ص 4-6)"
    )

    LazyColumn(
        modifier = modifier.padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            UnitHeroBanner(
                title = "Atelier Révision & Grammaire",
                subtitle = "Livret Bienvenu 2 (Pages 84 à 87) • La Phrase, Le Présent, Lieux & Négation",
                pageRange = "Pages 84-88",
                instituteTag = "Bienvenu 2",
                gradientColors = listOf(Indigo900, Indigo700, Indigo600),
                speechText = "Atelier de révision et de grammaire. Pages 84 à 87. De quoi se compose la phrase ? Le présent de l'indicatif, les articles contractés de lieu, et la négation.",
                arabicSubtitle = "كتيّب منهج Bienvenu 2: مراجعة شاملة لقواعد اللغة الفرنسية للصف الثاني الإعدادي.",
                audioHelper = audioHelper
            )
        }

        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate200)
            ) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.White,
                    contentColor = Indigo700,
                    edgePadding = 12.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Indigo700,
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
                                    color = if (selectedTab == index) Indigo700 else Slate600
                                )
                            },
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("grammar_tab_$index")
                        )
                    }
                }
            }
        }

        item {
            when (selectedTab) {
                0 -> Page84LaPhraseSection(audioHelper = audioHelper)
                1 -> Page85LePresentSection(audioHelper = audioHelper)
                2 -> Page86LieuxSection(audioHelper = audioHelper)
                3 -> Page87NegationSection(audioHelper = audioHelper)
                4 -> IntroductoryQuestionsSection(
                    audioHelper = audioHelper,
                    onScoreEarned = onScoreEarned,
                    onNavigateToTab = { selectedTab = it }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

// ----------------------------------------------------------------------------
// PAGE 84: De quoi se compose la phrase ?
// ----------------------------------------------------------------------------
@Composable
private fun Page84LaPhraseSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
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
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "De quoi se compose la phrase ?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "مما تتكون الجملة في اللغة الفرنسية؟ (ص 84)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo700
                        )
                    }
                    Surface(shape = RoundedCornerShape(8.dp), color = Indigo100) {
                        Text(
                            text = "p. 84",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo700,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Diagram layout: Sujet + Verbe + Complément
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // 1. Sujet
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Indigo50,
                        border = BorderStroke(1.dp, Indigo100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("1. Sujet (الفاعل)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo900)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• Nom (اسم):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Indigo700)
                            Text("Ahmed, Sara, Mona, Le stylo, Le lion", fontSize = 9.sp, color = Slate800)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• Pronom (ضمير):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Indigo700)
                            Text("je, tu, il, elle, nous, vous, ils, elles", fontSize = 9.sp, color = Slate800)
                        }
                    }

                    // 2. Verbe
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Amber50,
                        border = BorderStroke(1.dp, Amber400.copy(alpha = 0.5f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("2. Verbe (الفعل)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Amber950)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• 1er groupe:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Amber950)
                            Text("terminé par (-er)", fontSize = 9.sp, color = Slate800)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• 2ème groupe:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Amber950)
                            Text("terminé par (-ir)", fontSize = 9.sp, color = Slate800)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• 3ème groupe:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Amber950)
                            Text("(-re, -oir, -ir)", fontSize = 9.sp, color = Slate800)
                        }
                    }

                    // 3. Complément
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Emerald100.copy(alpha = 0.4f),
                        border = BorderStroke(1.dp, Emerald500.copy(alpha = 0.4f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("3. Complément", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Emerald600)
                            Text("(المفعول به)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• C.O.D.:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                            Text("مباشر بدون حرف جر", fontSize = 9.sp, color = Slate800)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• C.O.I.:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                            Text("غير مباشر مسبوق بحرف جر", fontSize = 9.sp, color = Slate800)
                        }
                    }
                }
            }
        }

        // Examples with breakdown
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "📖 Exemples du livret avec analyse (ص 84)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Ex (1)
                SentenceAnalysisCard(
                    exampleNum = "Ex (1)",
                    sentence = "Je vais à l'école",
                    sujet = "Je",
                    verbe = "vais",
                    complement = "à l'école (C.O.I)",
                    translation = "أنا أذهب إلى المدرسة (أنا = فاعل، أذهب = فعل aller، إلى المدرسة = مفعول به غير مباشر C.O.I)",
                    audioHelper = audioHelper
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Ex (2)
                SentenceAnalysisCard(
                    exampleNum = "Ex (2)",
                    sentence = "Sara mange une pomme",
                    sujet = "Sara",
                    verbe = "mange",
                    complement = "une pomme (C.O.D)",
                    translation = "سارة تأكل تفاحة (سارة = فاعل، تأكل = فعل manger، تفاحة = مفعول به مباشر C.O.D)",
                    audioHelper = audioHelper
                )
            }
        }
    }
}

@Composable
private fun SentenceAnalysisCard(
    exampleNum: String,
    sentence: String,
    sujet: String,
    verbe: String,
    complement: String,
    translation: String,
    audioHelper: AudioHelper
) {
    var showTranslation by remember { mutableStateOf(false) }

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
                Column(modifier = Modifier.weight(1f)) {
                    Text(exampleNum, fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo700)
                    Text(sentence, fontSize = 15.sp, fontWeight = FontWeight.Black, color = Slate900)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = { showTranslation = !showTranslation },
                        contentDescription = "ترجمة $exampleNum"
                    )
                    AudioPlayButton(
                        textToSpeak = sentence,
                        audioHelper = audioHelper
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Breakdown Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(shape = RoundedCornerShape(6.dp), color = Indigo100) {
                    Text("Sujet : $sujet", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Indigo900, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
                Surface(shape = RoundedCornerShape(6.dp), color = Amber400.copy(alpha = 0.3f)) {
                    Text("Verbe : $verbe", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Amber950, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
                Surface(shape = RoundedCornerShape(6.dp), color = Emerald100) {
                    Text("Compl. : $complement", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Emerald600, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }

            ArabicTranslationBanner(
                translation = translation,
                visible = showTranslation
            )
        }
    }
}

// ----------------------------------------------------------------------------
// PAGE 85: Révision Grammaire - Le présent
// ----------------------------------------------------------------------------
@Composable
private fun Page85LePresentSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // 1er & 2ème groupe
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
                    Column {
                        Text(
                            text = "⏱️ Le présent de l'indicatif (Page 85)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "المجموعة الأولى والثانية (1er & 2ème groupe)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo700
                        )
                    }
                    AudioPlayButton(
                        textToSpeak = "Le présent de l'indicatif. Premier groupe, verbe marcher. Deuxième groupe, verbe finir.",
                        audioHelper = audioHelper
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 1er groupe (Marcher)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Indigo50,
                        border = BorderStroke(1.dp, Indigo100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("1er groupe (-er)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo900)
                            Text("Marcher (يمشى)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Indigo700)
                            Spacer(modifier = Modifier.height(4.dp))
                            val marchLines = listOf(
                                "Je" to "-e" to "marche",
                                "Tu" to "-es" to "marches",
                                "Il / elle" to "-e" to "marche",
                                "Nous" to "-ons" to "marchons",
                                "Vous" to "-ez" to "marchez",
                                "Ils / elles" to "-ent" to "marchent"
                            )
                            marchLines.forEach { (pair, verb) ->
                                val (pronom, term) = pair
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(pronom, fontSize = 9.sp, fontWeight = FontWeight.Medium, color = Slate700)
                                    Text("$term ($verb)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                                }
                            }
                        }
                    }

                    // 2ème groupe (Finir)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Emerald100.copy(alpha = 0.35f),
                        border = BorderStroke(1.dp, Emerald500.copy(alpha = 0.4f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("2ème groupe (-ir)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Emerald600)
                            Text("Finir (ينهى)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                            Spacer(modifier = Modifier.height(4.dp))
                            val finirLines = listOf(
                                "Je" to "-is" to "finis",
                                "Tu" to "-is" to "finis",
                                "Il / elle" to "-it" to "finit",
                                "Nous" to "-issons" to "finissons",
                                "Vous" to "-issez" to "finissez",
                                "Ils / elles" to "-issent" to "finissent"
                            )
                            finirLines.forEach { (pair, verb) ->
                                val (pronom, term) = pair
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(pronom, fontSize = 9.sp, fontWeight = FontWeight.Medium, color = Slate700)
                                    Text("$term ($verb)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3ème groupe: Les verbes irréguliers (aller, avoir, faire, être, vouloir, pouvoir)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Amber400.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "⭐ 3ème groupe : Les verbes irréguliers",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Amber950
                )
                Text(
                    text = "الأفعال الشواذ الستة الأساسية في منهج 2 إعدادي (ص 85)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate700
                )

                Spacer(modifier = Modifier.height(10.dp))

                val irregularVerbs = listOf(
                    Triple("aller (يذهب)", "vais, vas, va, allons, allez, vont", "Je vais, Tu vas, Il va, Nous allons, Vous allez, Ils vont"),
                    Triple("avoir (يملك)", "ai, as, a, avons, avez, ont", "J'ai, Tu as, Il a, Nous avons, Vous avez, Ils ont"),
                    Triple("faire (يعمل)", "fais, fais, fait, faisons, faites, font", "Je fais, Tu fais, Il fait, Nous faisons, Vous faites, Ils font"),
                    Triple("être (يكون)", "suis, es, est, sommes, êtes, sont", "Je suis, Tu es, Il est, Nous sommes, Vous êtes, Ils sont"),
                    Triple("vouloir (يريد)", "veux, veux, veut, voulons, voulez, veulent", "Je veux, Tu veux, Il veut, Nous voulons, Vous voulez, Ils veulent"),
                    Triple("pouvoir (يستطيع)", "peux, peux, peut, pouvons, pouvez, peuvent", "Je peux, Tu peux, Il peut, Nous pouvons, Vous pouvez, Ils peuvent")
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    irregularVerbs.forEach { (header, conjSummary, fullSpeech) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Slate50,
                            border = BorderStroke(1.dp, Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(header, fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo900)
                                    Text(conjSummary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Slate800)
                                }
                                AudioPlayButton(
                                    textToSpeak = fullSpeech,
                                    audioHelper = audioHelper,
                                    size = 30
                                )
                            }
                        }
                    }
                }
            }
        }

        // Les mots clés (الكلمات الدالة)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Teal100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🔑 Les mots clés du présent (الكلمات الدالة)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Teal700
                )
                Spacer(modifier = Modifier.height(8.dp))

                val keyWords = listOf(
                    "Maintenant" to "الآن",
                    "Aujourd'hui" to "النهاردة / اليوم",
                    "Chaque + temps" to "كل (فترة زمنية)",
                    "Souvent" to "غالباً",
                    "Toujours" to "دائماً",
                    "Tout + temps" to "كل (فترة زمنية)"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    keyWords.forEach { (fr, ar) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Teal50,
                            border = BorderStroke(1.dp, Teal100)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(fr, fontSize = 11.sp, fontWeight = FontWeight.Black, color = Teal700)
                                Text(ar, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate800)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// PAGE 86: Les Articles Contractés (Lieux)
// ----------------------------------------------------------------------------
@Composable
private fun Page86LieuxSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Amber50)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🗺️ Les Articles Contractés (Lieux) (ص 86)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Text(
                    text = "أدوات وحروف جر المكان مع المدن والمواقع",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo700
                )

                Spacer(modifier = Modifier.height(10.dp))

                val placeRules = listOf(
                    Triple("à", "les villes (المدن)", "Ex: Mostafa habite à Minia."),
                    Triple("au", "Masculin Singulier (مذكر مفرد)", "Ex: Je vais au théâtre."),
                    Triple("à la", "Féminin Singulier (مؤنث مفرد)", "Ex: Le médecin travaille à la pharmacie."),
                    Triple("à l'", "M. ou F. voyelle (متحرك a,e,i,o,u,h,y)", "Ex: Tu vas à l'école à pied."),
                    Triple("aux", "pluriel (s, x) (الجمع)", "Ex: Ahmed va aux pyramides.")
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    placeRules.forEach { (art, desc, ex) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Slate50,
                            border = BorderStroke(1.dp, Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = Indigo100
                                        ) {
                                            Text(art, fontSize = 12.sp, fontWeight = FontWeight.Black, color = Indigo900, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(desc, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate800)
                                    }
                                    Text(ex, fontSize = 10.sp, color = Indigo700, modifier = Modifier.padding(top = 2.dp))
                                }
                                AudioPlayButton(
                                    textToSpeak = ex,
                                    audioHelper = audioHelper,
                                    size = 30
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sauf callout (au Caire, au Sinaï, au Fayoum)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Amber400.copy(alpha = 0.2f),
                    border = BorderStroke(1.5.dp, Amber500),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "⚡ Sauf ! (استثناءات هامة جداً - مدن ومحافظات تأخذ au)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Amber950
                        )
                        Text(
                            text = "• au Caire (القاهرة)\n• au Sinaï (سيناء)\n• au Fayoum (الفيوم)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Full places table
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "📋 Tableau exhaustif des lieux (Page 86)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Masculin (au)
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Indigo50,
                        border = BorderStroke(1.dp, Indigo100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Masculin (au)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo900)
                            Spacer(modifier = Modifier.height(4.dp))
                            val places = listOf("au stade", "au cirque", "au club", "au cinéma", "au musée", "au restaurant", "au jardin", "au café", "au zoo", "au magasin", "au théâtre", "au lycée")
                            places.forEach { Text(it, fontSize = 9.sp, color = Slate800) }
                        }
                    }

                    // Féminin (à la)
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Rose50,
                        border = BorderStroke(1.dp, Rose100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Féminin (à la)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Rose600)
                            Spacer(modifier = Modifier.height(4.dp))
                            val places = listOf("à la plage", "à la mer", "à la piscine", "à la librairie", "à la pharmacie", "à la gare", "à la poste", "à la tour", "à la montagne", "à la campagne", "à la cuisine", "à la citadelle")
                            places.forEach { Text(it, fontSize = 9.sp, color = Slate800) }
                        }
                    }

                    // Voyelle & Pluriel
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Emerald100.copy(alpha = 0.3f),
                        border = BorderStroke(1.dp, Emerald500.copy(alpha = 0.4f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Voyelle (à l')", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Emerald600)
                            Spacer(modifier = Modifier.height(4.dp))
                            val voyelles = listOf("à l'école", "à l'hôtel", "à l'hôpital")
                            voyelles.forEach { Text(it, fontSize = 9.sp, color = Slate800) }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Pluriel (aux)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Emerald600)
                            Text("aux pyramides", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Slate800)
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// PAGE 87: La Négation
// ----------------------------------------------------------------------------
@Composable
private fun Page87NegationSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Rose100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🚫 La Négation (Page 87)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Rose600
                )
                Text(
                    text = "صيغة النفي وقواعد تحويل الأدوات",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate700
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Base formulas
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Rose50,
                    border = BorderStroke(1.dp, Rose100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("• ne + verbe + pas", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Rose600)
                        Text("• n' + verbe (voyelle) + pas", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Rose600)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Exemples from page 87
                SentenceAnalysisCard(
                    exampleNum = "Ex (1)",
                    sentence = "Je ne vais pas à l'école.",
                    sujet = "Je",
                    verbe = "ne vais pas",
                    complement = "à l'école",
                    translation = "أنا لا أذهب إلى المدرسة. (النفي بإحاطة الفعل بـ ne ... pas)",
                    audioHelper = audioHelper
                )

                Spacer(modifier = Modifier.height(8.dp))

                SentenceAnalysisCard(
                    exampleNum = "Ex (2)",
                    sentence = "Je n'aime pas mon amie.",
                    sujet = "Je",
                    verbe = "n'aime pas",
                    complement = "mon amie",
                    translation = "أنا لا أحب صديقتي. (تحولت ne إلى n' لأن فعل aimer يبدأ بحرف متحرك)",
                    audioHelper = audioHelper
                )
            }
        }

        // N.B. Crucial Rule: (un, une, du, de la, des, de l' -> de / d' sauf avec être)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Amber400.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Amber400
                ) {
                    Text(
                        text = "⭐ N.B. القاعدة الذهبية الأهم في الامتحان",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber950,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "أدوات النكرة والتجزئة (un, une, des, du, de la, de l')\nتتحول جميعاً في النفي إلى « de » أو « d' » (أمام حرف متحرك)\n⚠️ ما عدا مع فعل الكينونة (être) تظل الأدوات كما هي دون تغيير!",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Regular verb (Avoir) -> transforms to de
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Rose50,
                        border = BorderStroke(1.5.dp, Rose500),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Exemple normal :", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate700)
                            Text("J'ai un cahier.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("➔ Je n'ai pas de cahier.", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Rose600)
                            Text("(تحولت un إلى de)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Rose600)
                        }
                    }

                    // Verb Être -> does NOT change
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Emerald100.copy(alpha = 0.4f),
                        border = BorderStroke(1.5.dp, Emerald500),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Sauf verbe \"être\" :", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate700)
                            Text("Je suis un élève.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("➔ Je ne suis pas un élève.", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Emerald600)
                            Text("(ظلت un كما هي مع être)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// PAGE 88 / QUIZ SECTION: Comprehensive Questions for All Rules & Exercises (p. 4-6)
// ----------------------------------------------------------------------------
@Composable
private fun Page88QuizSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedExerciseFilter by remember { mutableIntStateOf(0) }
    val exerciseFilters = listOf(
        "Tous (37)",
        "Ex 1: Présent (10)",
        "Ex 2: Choisis (5)",
        "Ex 3: Interrogatifs (5)",
        "Ex 4: Lieux (12)",
        "Ex 5: Négation (5)"
    )

    val currentQuestions = remember(selectedExerciseFilter) {
        when (selectedExerciseFilter) {
            1 -> FrenchCourseData.revisionEx1MetsAuPresent
            2 -> FrenchCourseData.revisionEx2ChoisisBonneReponse
            3 -> FrenchCourseData.revisionEx3Interrogatifs
            4 -> FrenchCourseData.revisionEx4ArticlesLieux
            5 -> FrenchCourseData.revisionEx5Negation
            else -> FrenchCourseData.grammarQuizQuestions
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Indigo50,
            border = BorderStroke(1.dp, Indigo100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    Text(
                        text = "🎯 تمارين كتيّب المعهد الرسمية (ص 4 و 5 و 6)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                    Text(
                        text = "37 سؤالاً تفاعلياً مطابقاً لتمارين الكتيّب الـ 5 مع الصوت والترجمة",
                        fontSize = 11.sp,
                        color = Indigo700
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Indigo700
                ) {
                    Text(
                        text = "37 Qs",
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

        // Filter chips for the 5 exercises
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            exerciseFilters.forEachIndexed { index, label ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedExerciseFilter == index) Indigo700 else Slate100,
                    border = BorderStroke(
                        1.dp,
                        if (selectedExerciseFilter == index) Indigo700 else Slate200
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

        currentQuestions.forEach { question ->
            InteractiveQuizCard(
                question = question,
                audioHelper = audioHelper,
                onCorrectAnswer = { onScoreEarned(1) }
            )
        }
    }
}
