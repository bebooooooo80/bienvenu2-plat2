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
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Indigo100
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
import com.example.ui.theme.Teal100
import com.example.ui.theme.Teal50
import com.example.ui.theme.Teal600
import com.example.ui.theme.Teal700
import com.example.ui.theme.Violet100

@Composable
fun Unit3Screen(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "📖 Texte (p.59-61)",
        "📚 Banque des Mots (p.62)",
        "🎭 Situations (p.63)",
        "📐 Grammaire (p.64-66)",
        "🏥 À l'Hôpital"
    )

    LazyColumn(
        modifier = modifier.padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            UnitHeroBanner(
                title = "Unité (3) : Santé / Hôpital",
                subtitle = "L'accident (p.59-61) • Banque des Mots (p.62) • Situations (p.63) • Le Corps & Grammaire (p.64-66)",
                pageRange = "Pages 59-67",
                instituteTag = "Bienvenu 2",
                gradientColors = listOf(Teal700, Teal600, Emerald600),
                speechText = FrenchCourseData.unit3FullText,
                arabicSubtitle = FrenchCourseData.unit3FullTextArabic,
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
                    contentColor = Teal700,
                    edgePadding = 12.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Teal700,
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
                                    color = if (selectedTab == index) Teal700 else Slate600
                                )
                            },
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("unit3_tab_$index")
                        )
                    }
                }
            }
        }

        when (selectedTab) {
            0 -> item { Unit3TexteSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            1 -> item { Unit3BanqueMotsSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            2 -> item { Unit3SituationsSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            3 -> item { Unit3GrammaireSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
            4 -> item { Unit3HospitalSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned) }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun Unit3HospitalSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Teal100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🚑 Vocabulaire Médical Clé (Pages 61-64)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Teal700
                )
                Spacer(modifier = Modifier.height(8.dp))

                val vocab = listOf(
                    Pair("💊 Un médicament", "دواء"),
                    Pair("📑 Une ordonnance", "روشتة"),
                    Pair("🩻 Une radiographie", "أشعة"),
                    Pair("🚑 Une ambulance", "سيارة إسعاف")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    vocab.take(2).forEach { (fr, ar) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Slate50,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Slate200),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(fr, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                                Text(ar, fontSize = 10.sp, color = Slate600)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    vocab.drop(2).forEach { (fr, ar) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Slate50,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Slate200),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(fr, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                                Text(ar, fontSize = 10.sp, color = Slate600)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Doctor's Advice Box
                val adviceText = "« Ne t'inquiète pas, repose-toi bien, et prends tes médicaments à l'heure ! Tu pourras marcher dans quelques jours. »"
                val adviceArabic = "نصيحة الطبيب للمريض: «لا تقلق، استرح جيداً، وتناول أدويتك في موعدها! ستتمكن من المشي خلال أيام قليلة.»"
                var showAdviceTranslation by remember { mutableStateOf(false) }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Teal50,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Teal100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("👨‍⚕️ Conseil du médecin au malade :", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Teal700)
                                Text(adviceText, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Slate800, modifier = Modifier.padding(top = 2.dp))
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TranslateIconButton(
                                    isTranslated = showAdviceTranslation,
                                    onClick = { showAdviceTranslation = !showAdviceTranslation },
                                    contentDescription = "ترجمة نصيحة الطبيب للعربية",
                                    size = 32
                                )
                                AudioPlayButton(
                                    textToSpeak = adviceText,
                                    audioHelper = audioHelper,
                                    backgroundColor = Teal100,
                                    iconTint = Teal700,
                                    size = 32
                                )
                            }
                        }

                        ArabicTranslationBanner(
                            translation = adviceArabic,
                            visible = showAdviceTranslation
                        )
                    }
                }
            }
        }

        Text(
            text = "🎯 Situations types (Choisis la bonne réponse) :",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = Teal700
        )

        FrenchCourseData.medicalSituations.forEach { question ->
            InteractiveQuizCard(
                question = question,
                audioHelper = audioHelper,
                onCorrectAnswer = { onScoreEarned(1) }
            )
        }
    }
}
