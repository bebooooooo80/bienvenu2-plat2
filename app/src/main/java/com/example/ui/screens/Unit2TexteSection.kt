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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange500
import com.example.ui.theme.Orange600
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

/**
 * قسم النص والتمارين للوحدة الثانية: Repas & Restaurant (الصفحات 35 - 37)
 * مطابق تماماً لمواصفات وتصميم كتيّب منهج Bienvenu 2
 */
@Composable
fun Unit2TexteSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedExerciseFilter by remember { mutableIntStateOf(0) }
    val exerciseFilters = listOf(
        "Tous (20)",
        "Ex 1: Choisis (4)",
        "Ex 2: Vrai / Faux (5)",
        "Ex 3: Réponds (5)",
        "Ex 4: Complète (6)"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.testTag("unit2_texte_section")
    ) {
        // --- البطاقة التقديمية الأولى: المشهد والنص (ص 35) ---
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Orange100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Surface(
                            color = Orange50,
                            shape = CircleShape
                        ) {
                            Text(
                                text = "🍽️",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Unité (2) : Repas & Restaurant",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            Text(
                                text = "Observe le dessin, lis le document, puis réponds aux questions",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Orange600
                            )
                        }
                    }

                    Surface(
                        color = Orange100,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Booklet p. 35-37",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange600,
                            softWrap = false,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // بطاقة المشهد المسرحي والرسم التوضيحي (ص 35)
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Orange50,
                    border = BorderStroke(1.dp, Orange100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🎨 ملخص مشهد الحفلة والمطعم (ص 35) :",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            AudioPlayButton(
                                textToSpeak = "Le 15 juin à l'occasion de leur fête de mariage, Jean Morelle et sa femme ont invité des amis à dîner à 22 heures. Ils ont réservé une table pour 20 personnes dans un grand restaurant.",
                                audioHelper = audioHelper,
                                backgroundColor = Color.White,
                                size = 32
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Unit2SceneBadge(icon = "📅", label = "Le 15 juin (22h)", modifier = Modifier.weight(1f))
                            Unit2SceneBadge(icon = "💍", label = "Fête de mariage", modifier = Modifier.weight(1f))
                            Unit2SceneBadge(icon = "👥", label = "20 personnes", modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // بطاقة استعراض قائمة طعام الحفلة (Menu du dîner)
                Unit2MenuShowcaseCard(audioHelper = audioHelper)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "🔊 استمع لكل جملة مع النص وترجمتها التفاعلية :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate700,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                // خطوط الحوار كاملة من صفحة 35 و 36
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    FrenchCourseData.unit2Dialogues.forEach { item ->
                        Unit2DialogueBubble(item = item, audioHelper = audioHelper)
                    }
                }
            }
        }

        // --- شريط عنوان أسئلة فهم النص الرسمية (ص 36 - 37) ---
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Orange50,
            border = BorderStroke(1.dp, Orange100)
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
                        text = "📝 أسئلة فهم النص الرسمية (كتيّب ص 36 - 37)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Orange600
                    )
                    Text(
                        text = "تمارين (1 و 2 و 3 و 4) بمطابقة تامة لكتيّب المعهد ونظام التقييم الفوري",
                        fontSize = 11.sp,
                        color = Slate600
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Orange600
                ) {
                    Text(
                        text = "p. 36-37",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        softWrap = false,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // أزرار تصفية التمارين (Filter Chips)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            exerciseFilters.forEachIndexed { index, label ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedExerciseFilter == index) Orange600 else Slate100,
                    border = BorderStroke(
                        1.dp,
                        if (selectedExerciseFilter == index) Orange600 else Slate200
                    ),
                    modifier = Modifier.clickable {
                        audioHelper.playClick()
                        selectedExerciseFilter = index
                    }
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

        // --- EXERCISE 1: Complète avec le bon groupe (ص 36) ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 1) {
            Unit2OfficialExerciseHeader(
                number = "1",
                titleFrench = "1) Complète avec le bon groupe:-",
                titleArabic = "اختر الإجابة الصحيحة وفقاً لنص الوثيقة (كتيّب ص 36) :",
                audioHelper = audioHelper,
                badgeColor = Orange600
            )
            FrenchCourseData.unit2Ex1BonGroupe.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }

        // --- EXERCISE 2: Mets vrai (√) ou faux (X) devant chaque phrase (ص 37) ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 2) {
            Unit2OfficialExerciseHeader(
                number = "2",
                titleFrench = "2) Mets vrai (√) ou faux (X) devant chaque phrase :-",
                titleArabic = "ضع علامة (صح Vrai) أو (خطأ Faux) أمام كل جملة (كتيّب ص 37) :",
                audioHelper = audioHelper,
                badgeColor = Indigo700
            )
            FrenchCourseData.unit2Ex2VraiOuFaux.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }

        // --- EXERCISE 3: Réponds avec des mots pris du texte (ص 37) ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 3) {
            Unit2OfficialExerciseHeader(
                number = "3",
                titleFrench = "3) Réponds avec des mots pris du texte :-",
                titleArabic = "أجب بكلمات مأخوذة من نص الوثيقة (كتيّب ص 37) :",
                audioHelper = audioHelper,
                badgeColor = Emerald600
            )
            FrenchCourseData.unit2Ex3RepondsPrisDuTexte.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }

        // --- EXERCISE 4: Complete (ص 37) ---
        if (selectedExerciseFilter == 0 || selectedExerciseFilter == 4) {
            Unit2OfficialExerciseHeader(
                number = "4",
                titleFrench = "4) Complete:-",
                titleArabic = "أكمل الفراغات من بنك الكلمات التالي (كتيّب ص 37) :",
                audioHelper = audioHelper,
                badgeColor = Amber500
            )

            // بطاقة بنك الكلمات الرسمي للتمرين الرابع
            Unit2WordBankCard(audioHelper = audioHelper)

            FrenchCourseData.unit2Ex4Complete.forEach { question ->
                InteractiveQuizCard(
                    question = question,
                    audioHelper = audioHelper,
                    onCorrectAnswer = { onScoreEarned(1) }
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------
// المكونات المساعدة الخاصة بالنص
// -----------------------------------------------------------------------------

@Composable
private fun Unit2SceneBadge(
    icon: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = icon, fontSize = 11.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Slate800,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun Unit2MenuShowcaseCard(audioHelper: AudioHelper) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Amber50),
        border = BorderStroke(1.dp, Amber400.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "📜", fontSize = 16.sp)
                    Text(
                        text = "Menu de la fête (قائمة طعام الحفلة)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber950
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AudioPlayButton(
                        textToSpeak = "Comme entrée, on a servi des crudités : des tomates, des concombres en salade et des laitues. Comme plat principal : du veau ou du poulet avec des frites et des légumes. Et comme dessert : des fruits de toutes sortes et un gâteau au chocolat en forme de pyramide avec 5 bougies.",
                        audioHelper = audioHelper,
                        backgroundColor = Color.White,
                        size = 30
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (expanded) "▲" else "▼",
                        fontSize = 11.sp,
                        color = Amber950
                    )
                }
            }

            AnimatedVisibility(visible = expanded || true) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Unit2MenuItemRow(
                        course = "🥗 Entrée (المقبلات):",
                        french = "Des crudités (tomates, concombres en salade, laitues)",
                        arabic = "خضروات طازجة وسلطات (طماطم، خيار، خس)"
                    )
                    Unit2MenuItemRow(
                        course = "🥩 Plat principal (الطبق الرئيسي):",
                        french = "Du veau ou du poulet avec des frites et des légumes",
                        arabic = "لحم بتلو أو دجاج مع بطاطس محمرة وخضروات"
                    )
                    Unit2MenuItemRow(
                        course = "🎂 Dessert (التحلية):",
                        french = "Des fruits de toutes sortes et un gâteau au chocolat (5 bougies)",
                        arabic = "فواكه متنوعة وتورتة شوكولاتة هرمية مع 5 شمعات"
                    )
                }
            }
        }
    }
}

@Composable
private fun Unit2MenuItemRow(
    course: String,
    french: String,
    arabic: String
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            Text(
                text = course,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Orange600
            )
            Text(
                text = french,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate900
            )
            Text(
                text = arabic,
                fontSize = 10.sp,
                color = Slate600
            )
        }
    }
}

@Composable
private fun Unit2DialogueBubble(
    item: DialogueLine,
    audioHelper: AudioHelper
) {
    var isTranslated by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Slate50),
        border = BorderStroke(1.dp, Slate200),
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
                        backgroundColor = Orange100,
                        iconTint = Orange600,
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

@Composable
private fun Unit2OfficialExerciseHeader(
    number: String,
    titleFrench: String,
    titleArabic: String,
    audioHelper: AudioHelper,
    badgeColor: Color = Orange600,
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
private fun Unit2WordBankCard(audioHelper: AudioHelper) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Amber50,
        border = BorderStroke(1.dp, Amber400.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📦 بنك الكلمات الرسمي للتمرين (ص 37) :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Amber950
                )
                AudioPlayButton(
                    textToSpeak = "Le 15 juin, ses amis, à l'Occasion, une table, ses parents, des crudités",
                    audioHelper = audioHelper,
                    backgroundColor = Color.White,
                    size = 28
                )
            }

            Text(
                text = "( Le 15 juin – ses amis – à l'Occasion – une table – ses parents – des crudités )",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )

            Text(
                text = "💡 الترجمة: 15 يونيو – أصدقاؤه – بمناسبة – طاولة – والداه – خضروات طازجة",
                fontSize = 11.sp,
                color = Slate700
            )
        }
    }
}
