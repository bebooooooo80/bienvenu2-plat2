package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange500
import com.example.ui.theme.Orange600
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

@Composable
fun Unit2Screen(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "📖 Texte (p.35-37)",
        "🗂️ Vocabulaire (p.38)",
        "✍️ Production (p.39-40)",
        "🍽️ Les Repas (p.41-42)",
        "🥗 Articles Partitifs (p.43-45)",
        "❓ Mots Interrogatifs (p.46-50)",
        "📚 Banque des Mots (p.51-53)",
        "🎭 Situations (p.54)"
    )

    LazyColumn(
        modifier = modifier.padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            UnitHeroBanner(
                title = "Unité (2) : Repas & Restaurant",
                subtitle = "Le mariage de Jean • Vocabulaire • Production • Les Repas • Grammaire • Situations",
                pageRange = "Pages 35-54",
                instituteTag = "Bienvenu 2",
                gradientColors = listOf(Orange600, Orange500, Amber500),
                speechText = FrenchCourseData.unit2FullText,
                arabicSubtitle = FrenchCourseData.unit2FullTextArabic,
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
                    contentColor = Orange600,
                    edgePadding = 12.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Orange600,
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
                                    color = if (selectedTab == index) Orange600 else Slate600
                                )
                            },
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("unit2_tab_$index")
                        )
                    }
                }
            }
        }

        when (selectedTab) {
            0 -> item { Unit2TexteSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            1 -> item { Unit2VocabSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            2 -> item { Unit2ProductionSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            3 -> item { Unit2LesRepasSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            4 -> item { Unit2PartitivesSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            5 -> item { Unit2InterrogativesSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            6 -> item { Unit2BanqueMotsSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            7 -> item { Unit2SituationsSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun Unit2MenuSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Orange100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "📋 Le Menu du Restaurant (Pages 36-37, 41-42)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Orange600
                )
                Text(
                    text = "Il y a trois repas par jour : Le petit déjeuner, le déjeuner, le dîner",
                    fontSize = 11.sp,
                    color = Slate600,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                val courses = listOf(
                    Triple("1. Comme entrée 🥗", "Des crudités, des tomates, des concombres en salade, de la soupe.", "Comme entrée : des crudités, des tomates et des concombres en salade."),
                    Triple("2. Plat principal 🍗", "Du veau, du poulet, du poisson ou de la viande avec du riz et des frites.", "Comme plat principal : du veau, du poulet, de la viande avec du riz et des frites."),
                    Triple("3. Comme dessert 🍰", "Des fruits de toutes sortes, un gâteau au chocolat en forme de pyramide, de la glace.", "Comme dessert : des fruits et un gâteau au chocolat.")
                )

                courses.forEach { (title, desc, speech) ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Orange50,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Orange100),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(title, fontWeight = FontWeight.Black, fontSize = 12.sp, color = Orange600)
                                Text(desc, fontSize = 11.sp, color = Slate800, modifier = Modifier.padding(top = 2.dp))
                            }
                            AudioPlayButton(
                                textToSpeak = speech,
                                audioHelper = audioHelper,
                                backgroundColor = Orange100,
                                iconTint = Orange600
                            )
                        }
                    }
                }
            }
        }

        // Restaurant Dialogue Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🤵 Dialogue au Restaurant (Page 70)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Spacer(modifier = Modifier.height(8.dp))

                val dialogueLines = listOf(
                    Triple("🤵 Le garçon :", "Bonjour monsieur, vous déjeunez au menu ou à la carte ?", "النادل: مرحباً سيدي، هل تتناول الغداء بقائمة اليوم أم حسب الطلب؟"),
                    Triple("👤 Le client :", "À la carte. Je voudrais des crudités, de la viande, du riz et du gâteau.", "الزبون: حسب الطلب. أود خضاراً مقطعة، لحماً، أرزاً، وكعكاً."),
                    Triple("👤 Le client au garçon :", "L'addition, s'il vous plaît !", "الزبون للنادل: الحساب من فضلك!")
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    dialogueLines.forEach { (speaker, text, translation) ->
                        RestaurantDialogueBubble(
                            speaker = speaker,
                            text = text,
                            translation = translation,
                            audioHelper = audioHelper
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RestaurantDialogueBubble(
    speaker: String,
    text: String,
    translation: String,
    audioHelper: AudioHelper
) {
    var isTranslated by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Slate50,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(speaker, fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo700)
                    Text(text, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Slate800)
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TranslateIconButton(
                        isTranslated = isTranslated,
                        onClick = { isTranslated = !isTranslated },
                        contentDescription = "ترجمة $speaker",
                        size = 30
                    )
                    AudioPlayButton(
                        textToSpeak = text,
                        audioHelper = audioHelper,
                        size = 30
                    )
                }
            }

            ArabicTranslationBanner(
                translation = translation,
                visible = isTranslated
            )
        }
    }
}
