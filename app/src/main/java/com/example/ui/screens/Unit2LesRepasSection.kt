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
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.FoodDrinkItem
import com.example.data.FrenchCourseData
import com.example.data.MealType
import com.example.data.RepasQuizItem
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber900
import com.example.ui.theme.Amber950
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
import com.example.ui.theme.Violet100
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

/**
 * قسم الوجبات اليومية (Les Repas) - كتيّب الصف الثاني الإعدادي ص 41 و 42
 * منهج Bienvenu 2
 */
@Composable
fun Unit2LesRepasSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableIntStateOf(0) }
    val subTabs = listOf(
        Pair("🥐 الإفطار (p.41)", "Le Petit déjeuner"),
        Pair("🍗 الغداء (p.41)", "Le Déjeuner"),
        Pair("🥣 العشاء (p.42)", "Le Dîner"),
        Pair("🍰 الحلويات (p.42)", "Les Desserts"),
        Pair("🍳 صانع وجبتي", "Compose ton repas"),
        Pair("📝 بنك الأسئلة (10)", "Quiz Repas")
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ---------------------------------------------------------------------
        // البانر الرئيسي: سؤال الوجبات الرسمي ومقدمة ص 41
        // ---------------------------------------------------------------------
        RepasMainHeaderCard(audioHelper = audioHelper)

        // ---------------------------------------------------------------------
        // شريط التبويبات الداخلية الستة
        // ---------------------------------------------------------------------
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                subTabs.forEachIndexed { index, (labelAr, labelFr) ->
                    val isSelected = selectedSubTab == index
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) Orange600 else Slate50,
                        border = BorderStroke(1.dp, if (isSelected) Orange600 else Slate200),
                        modifier = Modifier
                            .clickable {
                                audioHelper.playClick()
                                selectedSubTab = index
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = labelAr,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Slate800
                            )
                            Text(
                                text = labelFr,
                                fontSize = 10.sp,
                                color = if (isSelected) Orange100 else Slate500
                            )
                        }
                    }
                }
            }
        }

        // ---------------------------------------------------------------------
        // محتوى التبويب المختار
        // ---------------------------------------------------------------------
        when (selectedSubTab) {
            0 -> RepasPetitDejeunerSubMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            1 -> RepasDejeunerSubMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            2 -> RepasDinerSubMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            3 -> RepasDessertsSubMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            4 -> RepasMealBuilderSubMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            5 -> RepasQuizSubMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// -----------------------------------------------------------------------------
// البانر الرئيسي: سؤال الوجبات الرسمي ومقدمة ص 41
// -----------------------------------------------------------------------------
@Composable
private fun RepasMainHeaderCard(audioHelper: AudioHelper) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Orange100),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🍽️", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Les Repas (ص 41 و 42)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Orange700
                        )
                        Text(
                            text = "كتيّب اللغة الفرنسية - منهج Bienvenu 2",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }
                }

                AudioPlayButton(
                    textToSpeak = "Combien de repas y a-t-il par jour ? Il y a trois repas par jour : le petit déjeuner, le déjeuner, le dîner.",
                    audioHelper = audioHelper
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // مربع السؤال الرسمي
            Surface(
                color = Orange50,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Orange100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "❓ Question d'examen :",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Orange700
                    )
                    Text(
                        text = "« Combien de repas y a-t-il par jour ? »",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900
                    )
                    Text(
                        text = "كم عدد الوجبات المقررة في اليوم الواحد؟",
                        fontSize = 12.sp,
                        color = Slate700,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // مربع الإجابة المعتمدة
            Surface(
                color = Emerald50,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Emerald300),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "💡 Réponse officielle (ص 41) :",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald700
                        )
                        AudioPlayButton(
                            textToSpeak = "Il y a trois repas par jour : le petit déjeuner, le déjeuner, le dîner.",
                            audioHelper = audioHelper
                        )
                    }
                    Text(
                        text = "« Il y a trois repas par jour :- »",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Emerald900
                    )
                    Text(
                        text = "يوجد ثلاث وجبات يومياً :",
                        fontSize = 12.sp,
                        color = Emerald700,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FrenchCourseData.unit2RepasList.forEach { (repasFr, repasAr) ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, Emerald300),
                                modifier = Modifier.weight(1f).padding(horizontal = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = repasFr,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Emerald900,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = repasAr,
                                        fontSize = 9.sp,
                                        color = Slate600,
                                        textAlign = TextAlign.Center
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

// -----------------------------------------------------------------------------
// 1. تبويب الإفطار (Le Petit Déjeuner - ص 41)
// -----------------------------------------------------------------------------
@Composable
private fun RepasPetitDejeunerSubMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val allItems = FrenchCourseData.unit2RepasItems.filter { it.mealType == MealType.PETIT_DEJEUNER }
    val mangeItems = allItems.filter { it.itemType == "mange" }
    val boisItems = allItems.filter { it.itemType == "bois" }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة عنوان الإفطار
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.5.dp, Amber100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🥐", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "1- le Petit déjeuner :- (ص 41)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Amber950
                        )
                        Text(
                            text = "وجبة الإفطار في الصباح (Le matin)",
                            fontSize = 12.sp,
                            color = Amber900
                        )
                    }
                }

                AudioPlayButton(
                    textToSpeak = "Au petit déjeuner, je mange et je bois.",
                    audioHelper = audioHelper
                )
            }
        }

        // --- القسم الأول: Je mange :- (أنا آكل) ---
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Amber100,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "🥖", fontSize = 16.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Je mange :- (أنا آكل)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Amber950
                            )
                            Text(
                                text = "9 أطعمة مقررة في كتيّب المعهد ص 41",
                                fontSize = 11.sp,
                                color = Slate600
                            )
                        }
                    }

                    AudioPlayButton(
                        textToSpeak = "Au petit déjeuner, je mange du pain, de la tartine, du miel, des œufs, du beurre, des fèves, du croissant, du fromage, de la confiture.",
                        audioHelper = audioHelper
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                mangeItems.forEach { item ->
                    RepasItemCard(item = item, audioHelper = audioHelper)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }

        // --- القسم الثاني: Je bois :- (أنا أشرب) ---
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Indigo100,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "🥛", fontSize = 16.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Je bois :- (أنا أشرب)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            Text(
                                text = "6 مشروبات مقررة في كتيّب المعهد ص 41",
                                fontSize = 11.sp,
                                color = Slate600
                            )
                        }
                    }

                    AudioPlayButton(
                        textToSpeak = "Pour boire, je prends du lait, de la limonade, du jus, de l'eau, du thé au lait, du café.",
                        audioHelper = audioHelper
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                boisItems.forEach { item ->
                    RepasItemCard(item = item, audioHelper = audioHelper)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 2. تبويب الغداء (Le Déjeuner - ص 41)
// -----------------------------------------------------------------------------
@Composable
private fun RepasDejeunerSubMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val items = FrenchCourseData.unit2RepasItems.filter { it.mealType == MealType.DEJEUNER }
    val entreeItems = items.filter { it.courseCategory == "Comme entrée" }
    val platItems = items.filter { it.courseCategory == "Comme plat principal" }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة عنوان الغداء
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Orange50),
            border = BorderStroke(1.5.dp, Orange100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🍗", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "2- le déjeuner :- (ص 41)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Orange700
                        )
                        Text(
                            text = "وجبة الغداء في منتصف اليوم (L'après-midi)",
                            fontSize = 12.sp,
                            color = Slate700
                        )
                    }
                }

                AudioPlayButton(
                    textToSpeak = "Le déjeuner : comme entrée, comme plat principal, comme dessert.",
                    audioHelper = audioHelper
                )
            }
        }

        // المخطط الشجري للغداء كما ورد في الكتيّب نصاً ص 41
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "📋 هيكل وجبة الغداء كما ورد في الكتيّب (ص 41) :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Spacer(modifier = Modifier.height(8.dp))

                val courses = listOf(
                    Triple("🥗 Comme entrée", "كمقبلات خفيفة", "de la salade (سلطة خضراء)"),
                    Triple("🍗 Comme plat principal", "كطبق رئيسي متكامل", "du riz, de la viande, du poulet, du poisson, des frites"),
                    Triple("🍰 Comme dessert", "كتحلية وحلوى الختام", "du gâteau, de la tarte, de la glace, des fruits")
                )

                courses.forEach { (frenchTitle, arabicTitle, foods) ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Indigo50,
                        border = BorderStroke(1.dp, Indigo100),
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
                                Text(
                                    text = frenchTitle,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Indigo900
                                )
                                Text(
                                    text = arabicTitle,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Indigo700
                                )
                                Text(
                                    text = foods,
                                    fontSize = 12.sp,
                                    color = Slate800,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                            AudioPlayButton(
                                textToSpeak = "$frenchTitle : $foods",
                                audioHelper = audioHelper
                            )
                        }
                    }
                }
            }
        }

        // أطعمة الغداء المقررة ص 41
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "🍽️ أطعمة الغداء وتصنيفاتها المقررة (ص 41) :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900
                )
                Spacer(modifier = Modifier.height(8.dp))

                items.forEach { item ->
                    RepasItemCard(item = item, audioHelper = audioHelper)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. تبويب العشاء (Le Dîner - ص 42)
// -----------------------------------------------------------------------------
@Composable
private fun RepasDinerSubMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val items = FrenchCourseData.unit2RepasItems.filter { it.mealType == MealType.DINER }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة عنوان العشاء
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Violet50),
            border = BorderStroke(1.5.dp, Violet100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🥣", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "3- le dîner :- (ص 42)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Violet700
                        )
                        Text(
                            text = "وجبة العشاء في المساء (Le soir)",
                            fontSize = 12.sp,
                            color = Slate700
                        )
                    }
                }

                AudioPlayButton(
                    textToSpeak = "Le dîner : Je prends du yaourt, des fruits, du gâteau. Ou comme le petit déjeuner.",
                    audioHelper = audioHelper
                )
            }
        }

        // النص المقرر كما ورد في الكتيّب ص 42 بالضبط
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "📖 نص الكتيّب المدرسي ص 42 بالحرف :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Violet700
                )
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Violet50,
                    border = BorderStroke(1.dp, Violet100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "« Je prends du yaourt, des fruits, du gâteau.\nOu comme le petit déjeuner. »",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Violet700,
                                lineHeight = 20.sp
                            )
                            AudioPlayButton(
                                textToSpeak = "Je prends du yaourt, des fruits, du gâteau. Ou comme le petit déjeuner.",
                                audioHelper = audioHelper
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "« أتناول زبادي، فواكه، كيك / جاتوه.\nأو مثل وجبة الإفطار تماماً. »",
                            fontSize = 12.sp,
                            color = Slate800,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // بطاقة التنبيه اللغوي لكلمة yaourt
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Amber50,
                    border = BorderStroke(1.dp, Amber100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "⚠️", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "ملحوظة امتحانية هامة جداً على كلمة yaourt :",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Amber950
                            )
                            Text(
                                text = "كلمة yaourt في الفرنسية مفرد مذكر ولا تعامل كحرف متحرك مع التجزئة، فنقول دائماً : (du yaourt) وليس (de l'yaourt).",
                                fontSize = 11.sp,
                                color = Slate800
                            )
                        }
                    }
                }
            }
        }

        // عناصر العشاء
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "🥣 عناصر العشاء ص 42 :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900
                )
                Spacer(modifier = Modifier.height(8.dp))

                items.forEach { item ->
                    RepasItemCard(item = item, audioHelper = audioHelper)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. تبويب الحلويات (Les Desserts - ص 42)
// -----------------------------------------------------------------------------
@Composable
private fun RepasDessertsSubMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val items = FrenchCourseData.unit2RepasItems.filter { it.mealType == MealType.DESSERTS }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // بطاقة عنوان الحلويات
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Rose50),
            border = BorderStroke(1.5.dp, Rose100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🍰", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "4- les desserts :- (ص 42)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Rose600
                        )
                        Text(
                            text = "الحلويات والمثلجات والفاكهة (À la fin du repas)",
                            fontSize = 12.sp,
                            color = Slate700
                        )
                    }
                }

                AudioPlayButton(
                    textToSpeak = "Les desserts : je prends du gâteau, de la tarte, de la glace, des fruits.",
                    audioHelper = audioHelper
                )
            }
        }

        // نص ص 42
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Rose100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "📖 نص كتيّب المعهد ص 42 :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Rose600
                )
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Rose50,
                    border = BorderStroke(1.dp, Rose100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "je prends :",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Rose600
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "• du gâteau (جاتوه / كيك 🍰)\n• de la tarte (تارت / فطيرة 🥧)\n• de la glace (آيس كريم / مثلجات 🍨)\n• des fruits (فواكه 🍓)",
                            fontSize = 13.sp,
                            color = Slate800,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }

        // بطاقات الحلويات الأربعة
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "🍰 الحلويات المقررة بالأدوات والأمثلة :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900
                )
                Spacer(modifier = Modifier.height(8.dp))

                items.forEach { item ->
                    RepasItemCard(item = item, audioHelper = audioHelper)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// بطاقة عرض عنصر طعام أو شراب موحدة وأنيقة
// -----------------------------------------------------------------------------
@Composable
private fun RepasItemCard(
    item: FoodDrinkItem,
    audioHelper: AudioHelper
) {
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = item.emoji, fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = item.frenchWithArticle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            // Badge نوع الأداة
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = when (item.articlePartitive) {
                                    "du" -> Orange100
                                    "de la" -> Rose100
                                    "de l'" -> Indigo100
                                    else -> Emerald100
                                }
                            ) {
                                Text(
                                    text = item.articlePartitive,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (item.articlePartitive) {
                                        "du" -> Orange700
                                        "de la" -> Rose600
                                        "de l'" -> Indigo700
                                        else -> Emerald700
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "${item.arabic} • ${item.genderAr}",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AudioPlayButton(
                        textToSpeak = item.frenchWithArticle,
                        audioHelper = audioHelper
                    )
                }
            }

            // الجملة التوضيحية عند النقر
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.sampleSentenceFr,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                AudioPlayButton(
                                    textToSpeak = item.sampleSentenceFr,
                                    audioHelper = audioHelper
                                )
                            }
                            Text(
                                text = item.sampleSentenceAr,
                                fontSize = 11.sp,
                                color = Slate600,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 5. صانع وجبتي التفاعلي (Compose ton repas)
// -----------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RepasMealBuilderSubMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var selectedMeal by remember { mutableStateOf(MealType.PETIT_DEJEUNER) }
    val selectedFoods = remember { mutableStateListOf<FoodDrinkItem>() }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var hasEarnedScore by remember { mutableStateOf(false) }

    // العناصر المتاحة حسب الوجبة المختارة
    val availableItems = FrenchCourseData.unit2RepasItems.filter {
        when (selectedMeal) {
            MealType.PETIT_DEJEUNER -> it.mealType == MealType.PETIT_DEJEUNER
            MealType.DEJEUNER -> it.mealType == MealType.DEJEUNER || it.mealType == MealType.DESSERTS
            MealType.DINER -> it.mealType == MealType.DINER || it.mealType == MealType.PETIT_DEJEUNER
            MealType.DESSERTS -> it.mealType == MealType.DESSERTS
        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Orange100),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🍳 صانع وجبتي المفضلة (Compose ton repas)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Orange700
                    )
                    Text(
                        text = "اختر الوجبة ثم انقر على الأطعمة لبناء جملة فرنسية سليمة",
                        fontSize = 11.sp,
                        color = Slate600
                    )
                }

                if (selectedFoods.isNotEmpty()) {
                    OutlinedButton(
                        onClick = {
                            selectedFoods.clear()
                            hasEarnedScore = false
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "إفراغ", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // اختيار نوع الوجبة
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    Pair(MealType.PETIT_DEJEUNER, "🥐 الإفطار"),
                    Pair(MealType.DEJEUNER, "🍗 الغداء"),
                    Pair(MealType.DINER, "🥣 العشاء")
                ).forEach { (meal, label) ->
                    val isSelected = selectedMeal == meal
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) Orange600 else Slate100,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                audioHelper.playClick()
                                selectedMeal = meal
                                selectedFoods.clear()
                                hasEarnedScore = false
                            }
                    ) {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Slate800,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // طبق الوجبة (عناصر مضافة)
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Amber50,
                border = BorderStroke(1.5.dp, Amber100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "🍽️ طبق وجبتك الحالي (${selectedFoods.size} أصناف) :",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Amber950
                    )

                    if (selectedFoods.isEmpty()) {
                        Text(
                            text = "انقر على الأطعمة والمشروبات بالأسفل لإضافتها إلى طبقك...",
                            fontSize = 11.sp,
                            color = Slate600,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    } else {
                        FlowRow(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            selectedFoods.forEach { food ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, Amber600),
                                    modifier = Modifier.clickable {
                                        selectedFoods.remove(food)
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "${food.emoji} ${food.frenchWithArticle}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(imageVector = Icons.Default.Close, contentDescription = "Remove", tint = Rose500, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }

                        // الجملة الفرنسية المكتملة
                        val mealNameFr = when (selectedMeal) {
                            MealType.PETIT_DEJEUNER -> "le petit déjeuner"
                            MealType.DEJEUNER -> "le déjeuner"
                            MealType.DINER -> "le dîner"
                            MealType.DESSERTS -> "le dessert"
                        }
                        val foodListFr = selectedFoods.joinToString(" et ") { it.frenchWithArticle }
                        val generatedSentence = "Pour $mealNameFr, je prends $foodListFr."

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = generatedSentence,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Indigo900,
                                        modifier = Modifier.weight(1f)
                                    )
                                    AudioPlayButton(textToSpeak = generatedSentence, audioHelper = audioHelper)
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString(generatedSentence))
                                            Toast.makeText(context, "تم نسخ الجملة بنجاح!", Toast.LENGTH_SHORT).show()
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = "نسخ الجملة", fontSize = 11.sp)
                                    }
                                }
                            }
                        }

                        if (!hasEarnedScore && selectedFoods.size >= 2) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    hasEarnedScore = true
                                    audioHelper.playSuccessChime()
                                    onScoreEarned(10)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "🎉 اعتماد وجبتي وحفظ النقاط (+10)")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // قائمة الأطعمة المتاحة للاختيار
            Text(
                text = "قائمة الأطعمة والمشروبات المتاحة للإضافة :",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Slate800
            )
            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                availableItems.forEach { item ->
                    val isAlreadySelected = selectedFoods.contains(item)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isAlreadySelected) Slate200 else Slate50,
                        border = BorderStroke(1.dp, if (isAlreadySelected) Slate300 else Slate200),
                        modifier = Modifier.clickable {
                            if (!isAlreadySelected) {
                                audioHelper.playClick()
                                selectedFoods.add(item)
                            } else {
                                selectedFoods.remove(item)
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item.emoji, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.frenchWithArticle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAlreadySelected) Slate500 else Slate800
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 6. تبويب بنك الأسئلة والتدريبات (Quiz Repas ص 41-42)
// -----------------------------------------------------------------------------
@Composable
private fun RepasQuizSubMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val quizItems = FrenchCourseData.unit2RepasQuizList
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<String, Int>() }
    val answeredCorrectly = remember { mutableStateMapOf<String, Boolean>() }
    val currentQ = quizItems[currentQuestionIndex]

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Orange100),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // رقم السؤال
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "السؤال ${currentQuestionIndex + 1} من ${quizItems.size}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Orange700
                )

                Text(
                    text = "ص 41 - 42",
                    fontSize = 11.sp,
                    color = Slate600
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // نص السؤال
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Orange50,
                border = BorderStroke(1.dp, Orange100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentQ.questionFr,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900,
                            modifier = Modifier.weight(1f)
                        )
                        AudioPlayButton(
                            textToSpeak = currentQ.questionFr,
                            audioHelper = audioHelper
                        )
                    }

                    Text(
                        text = currentQ.questionAr,
                        fontSize = 12.sp,
                        color = Slate700,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // الاختيارات
            currentQ.options.forEachIndexed { optIndex, optionText ->
                val selectedOpt = userAnswers[currentQ.id]
                val isAnswered = selectedOpt != null
                val isThisSelected = selectedOpt == optIndex
                val isCorrect = optIndex == currentQ.correctIndex

                val cardBg = when {
                    !isAnswered -> Slate50
                    isCorrect -> Emerald100
                    isThisSelected -> Rose100
                    else -> Slate50
                }

                val cardBorder = when {
                    !isAnswered -> Slate200
                    isCorrect -> Emerald500
                    isThisSelected -> Rose500
                    else -> Slate200
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = cardBg,
                    border = BorderStroke(1.dp, cardBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable(enabled = !isAnswered) {
                            userAnswers[currentQ.id] = optIndex
                            if (optIndex == currentQ.correctIndex) {
                                answeredCorrectly[currentQ.id] = true
                                audioHelper.playSuccessChime()
                                onScoreEarned(10)
                            } else {
                                answeredCorrectly[currentQ.id] = false
                                audioHelper.playErrorBuzz()
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = optionText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )

                        if (isAnswered) {
                            if (isCorrect) {
                                Text(text = "✓ صحيح", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Emerald700)
                            } else if (isThisSelected) {
                                Text(text = "✗ خطأ", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Rose600)
                            }
                        }
                    }
                }
            }

            // التفسير والنصيحة الذهبية
            if (userAnswers.containsKey(currentQ.id)) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Indigo50,
                    border = BorderStroke(1.dp, Indigo100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "💡 الشرح والتوضيح :",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo900
                        )
                        Text(
                            text = currentQ.explanationAr,
                            fontSize = 11.sp,
                            color = Slate800,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        if (currentQ.tip.isNotEmpty()) {
                            Text(
                                text = "⭐ نصيحة الامتحان: ${currentQ.tip}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo700,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // أزرار التنقل بين الأسئلة
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if (currentQuestionIndex > 0) currentQuestionIndex--
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Slate100),
                    shape = RoundedCornerShape(8.dp),
                    enabled = currentQuestionIndex > 0
                ) {
                    Text(text = "السابق", color = Slate800, fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        if (currentQuestionIndex < quizItems.size - 1) {
                            currentQuestionIndex++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                    shape = RoundedCornerShape(8.dp),
                    enabled = currentQuestionIndex < quizItems.size - 1
                ) {
                    Text(text = "التالي ➔", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}
