package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.FrenchCourseData
import com.example.data.QuizQuestion
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.InteractiveQuizCard
import com.example.ui.components.UnitHeroBanner
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
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
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

/**
 * قسم الأسئلة التمهيدية ومراجعة ما قبل الوحدات (Pages 4, 5, 6 du livret officiel)
 * مع ربط كل نوع أسئلة بالقواعد المقررة رسمياً وشرحها التفاعلي الشامل.
 */
@Composable
fun IntroductoryQuestionsSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    onNavigateToTab: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedExerciseFilter by rememberSaveable { mutableIntStateOf(0) }
    var isRuleExpanded by rememberSaveable { mutableStateOf(true) }

    val exerciseTabs = listOf(
        "🌟 الكل (37 Qs)",
        "⏱️ (1) المضارع (ص 4)",
        "🎯 (2) اختر الفعل (ص 4)",
        "❓ (3) الاستفهام (ص 5)",
        "🗺️ (4) الأماكن (ص 5)",
        "🚫 (5) النفي (ص 6)"
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

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header for Introductory Section
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Indigo100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            color = Indigo700,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🎯", fontSize = 22.sp)
                            }
                        }

                        Column {
                            Text(
                                text = "الأسئلة التمهيدية قبل الوحدات",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            Text(
                                text = "Révision Préparatoire • Pages 4, 5, 6",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo700
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = Indigo50,
                        border = BorderStroke(1.dp, Indigo100)
                    ) {
                        Text(
                            text = "37 سؤالاً",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo700,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "حل جميع تدريبات كتيّب المعهد التمهيدية الـ 5 مع ربط كل تمرين بالقاعدة النحوية المعتمدة وشرحها التفاعلي.",
                    fontSize = 12.sp,
                    color = Slate700,
                    lineHeight = 18.sp
                )
            }
        }

        // Horizontal Exercise Selector Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            exerciseTabs.forEachIndexed { index, title ->
                val isSelected = selectedExerciseFilter == index
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Indigo700 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Indigo700 else Slate200),
                    modifier = Modifier
                        .clickable {
                            audioHelper.playClick()
                            selectedExerciseFilter = index
                            isRuleExpanded = true
                        }
                        .defaultMinSize(minHeight = 44.dp)
                        .testTag("intro_exercise_tab_$index")
                ) {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color.White else Slate800,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }
            }
        }

        // Linked Grammar Rule Card (بطاقة ربط الأسئلة بالقواعد الموجودة بالفعل)
        LinkedRuleBridgeCard(
            filterIndex = selectedExerciseFilter,
            isExpanded = isRuleExpanded,
            onToggleExpand = { isRuleExpanded = !isRuleExpanded },
            audioHelper = audioHelper,
            onNavigateToTab = onNavigateToTab
        )

        // Count Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "الأسئلة والتمارين التطبيقية (${currentQuestions.size} سؤال):",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Slate900
            )

            Text(
                text = "المس للإجابة والاستماع",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo700
            )
        }

        // Questions List
        currentQuestions.forEachIndexed { qIdx, question ->
            InteractiveQuizCard(
                question = question,
                audioHelper = audioHelper,
                onCorrectAnswer = { onScoreEarned(1) }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * بطاقة ربط كل تمرين بالقاعدة النحوية المعتمدة
 */
@Composable
private fun LinkedRuleBridgeCard(
    filterIndex: Int,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    audioHelper: AudioHelper,
    onNavigateToTab: ((Int) -> Unit)?
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo50.copy(alpha = 0.7f)),
        border = BorderStroke(1.5.dp, Indigo100),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row with Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "القاعدة المرتبطة",
                        tint = Indigo700,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = when (filterIndex) {
                            1 -> "📖 القاعدة المرتبطة: زمن المضارع (Le Présent p.85)"
                            2 -> "⏱️ القاعدة المرتبطة: كلمات المضارع والضمائر (p.85)"
                            3 -> "❓ القاعدة المرتبطة: أدوات الاستفهام (Interrogatifs p.46-50)"
                            4 -> "🗺️ القاعدة المرتبطة: إدغام الأماكن (Lieux p.86)"
                            5 -> "🚫 القاعدة المرتبطة: النفي واستثناء Être (Négation p.87)"
                            else -> "📚 القواعد النحوية المرتبطة بتمارين التمهيدي"
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    border = BorderStroke(1.dp, Indigo100),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "تبديل عرض القاعدة",
                            tint = Indigo700,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    when (filterIndex) {
                        1 -> RuleCardPresentGroup1AndIrregular(audioHelper, onNavigateToTab)
                        2 -> RuleCardTimeKeywordsAndVerbs(audioHelper, onNavigateToTab)
                        3 -> RuleCardInterrogatives(audioHelper)
                        4 -> RuleCardPlaceArticles(audioHelper, onNavigateToTab)
                        5 -> RuleCardNegationRules(audioHelper, onNavigateToTab)
                        else -> RuleCardOverviewAll(audioHelper, onNavigateToTab)
                    }
                }
            }
        }
    }
}

/**
 * شرح قاعدة التمرين الأول: تصريف المضارع (Mets au présent - ص 4 و 85)
 */
@Composable
private fun RuleCardPresentGroup1AndIrregular(
    audioHelper: AudioHelper,
    onNavigateToTab: ((Int) -> Unit)?
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "قاعدة التمرين الأول: تصريف الأفعال في المضارع (ص 4 و 85)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                IconButton(
                    onClick = {
                        audioHelper.speak("Le présent de l'indicatif. Premier groupe : e, es, e, ons, ez, ent. Deuxième groupe : is, is, it, issons, issez, issent. Verbes avoir et être.")
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "استمع لقاعدة المضارع",
                        tint = Indigo700,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // 1er Groupe
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Indigo50.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "1️⃣ أفعال المجموعة الأولى (المنتهية بـ -er) مثل: parler, regarder, jouer, écouter",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                    Text(
                        text = "• نحذف (-er) ونضيف النهايات حسب الفاعل:",
                        fontSize = 11.sp,
                        color = Slate700
                    )
                    Text(
                        text = "Je ➔ -e | Tu ➔ -es | Il/Elle ➔ -e | Nous ➔ -ons | Vous ➔ -ez | Ils/Elles ➔ -ent",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Indigo700
                    )
                    Text(
                        text = "⚠️ ملحوظة خاصة: فعل manger مع nous نضيف حرف e قبل ons للحفاظ على النطق ➔ Nous mangeons des gâteaux.",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Rose600
                    )
                }
            }

            // 2eme Groupe & Irregulars
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Slate50,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "2️⃣ المجموعة الثانية (finir) وأهم الأفعال الشاذة:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900
                    )
                    Text(
                        text = "• فعل finir (ينهي): Je finis, Tu finis, Il finit, Nous finissons, Vous finissez, Ils finissent.",
                        fontSize = 11.sp,
                        color = Slate800
                    )
                    Text(
                        text = "• فعل avoir (يملك/عنده): J'ai un livre, Tu as, Il a, Nous avons, Vous avez, Ils ont 16 ans.",
                        fontSize = 11.sp,
                        color = Slate800
                    )
                    Text(
                        text = "• فعل être (يكون): Je suis, Tu es content, Il est, Nous sommes des filles, Vous êtes, Ils sont.",
                        fontSize = 11.sp,
                        color = Slate800
                    )
                }
            }

            // Jump Button
            if (onNavigateToTab != null) {
                OutlinedButton(
                    onClick = { onNavigateToTab(1) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Indigo700)
                ) {
                    Text("انتقل لصفحة شرح المضارع الكاملة في الكتيّب (ص 85) ➔", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * شرح قاعدة التمرين الثاني: الكلمات الدالة وتوافق الفعل مع الفاعل (ص 4 و 85)
 */
@Composable
private fun RuleCardTimeKeywordsAndVerbs(
    audioHelper: AudioHelper,
    onNavigateToTab: ((Int) -> Unit)?
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "قاعدة التمرين الثاني: الكلمات الدالة وتوافق الفعل مع الفاعل (ص 4)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900
            )

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Emerald50,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "⏱️ الكلمات الدالة على زمن المضارع في التمرين:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Emerald600
                    )
                    Text("• Maintenant = الآن (Le professeur = il ➔ explique)", fontSize = 11.sp, color = Slate800)
                    Text("• Aujourd'hui = اليوم (nous ➔ allons au stade)", fontSize = 11.sp, color = Slate800)
                    Text("• Chaque jour = كل يوم (ils ➔ finissent leurs devoirs)", fontSize = 11.sp, color = Slate800)
                    Text("• Chaque matin = كل صباح (je ➔ préfère)", fontSize = 11.sp, color = Slate800)
                }
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Indigo50.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "💡 قاعدة توافق الفاعل مع النهاية:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                    Text("1. الفاعل المفرد الغائب (le professeur = il) يأخذ نهاية المفرد (explique).", fontSize = 11.sp, color = Slate700)
                    Text("2. الفاعل (nous) يأخذ دائماً نهاية -ons في أفعال المجموعة الأولى وفعل Aller ➔ nous allons, nous aimons.", fontSize = 11.sp, color = Slate700)
                    Text("3. الفاعل (ils) للجمع يأخذ نهاية -ent أو -issent ➔ ils finissent.", fontSize = 11.sp, color = Slate700)
                }
            }

            if (onNavigateToTab != null) {
                OutlinedButton(
                    onClick = { onNavigateToTab(1) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Indigo700)
                ) {
                    Text("انتقل لدرس تصريف الأفعال (ص 85) ➔", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * شرح قاعدة التمرين الثالث: أدوات الاستفهام (ص 5 و 46-50)
 */
@Composable
private fun RuleCardInterrogatives(audioHelper: AudioHelper) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "قاعدة التمرين الثالث: أدوات الاستفهام الخمسة واستخداماتها (ص 5)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900
            )

            val interrogatives = listOf(
                Triple("Comment", "السؤال عن الحال والصحة والوسيلة", "Comment allez-vous ? - ça va bien merci."),
                Triple("Qu'est ce que", "السؤال عن مفعول به غير عاقل", "Qu'est ce que tu as ? - J'ai un examen."),
                Triple("Qui", "السؤال عن شخص عاقل (من هذا؟)", "Qui c'est ? - C'est la maitresse de français."),
                Triple("Où", "السؤال عن المكان المسبوق بحرف جر", "Où sont les élèves ? - dans la cour."),
                Triple("Que", "السؤال عن الفعل مع تقديم الفعل على الفاعل", "Que fais-tu ? - J'écoute de la musique.")
            )

            interrogatives.forEach { (word, purpose, example) ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Slate50,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "❓ $word",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo700
                            )
                            Text(
                                text = purpose,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate700
                            )
                        }
                        Text(
                            text = "مثال: $example",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate900
                        )
                    }
                }
            }
        }
    }
}

/**
 * شرح قاعدة التمرين الرابع: حروف الجر المدمجة مع الأماكن (ص 5 و 86)
 */
@Composable
private fun RuleCardPlaceArticles(
    audioHelper: AudioHelper,
    onNavigateToTab: ((Int) -> Unit)?
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "قاعدة التمرين الرابع: حروف الجر المدمجة مع الأماكن (ص 5 و 86)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900
            )

            Text(
                text = "يدغم حرف الجر à مع أداة المعرفة قبل اسم المكان كالتالي:",
                fontSize = 11.sp,
                color = Slate700
            )

            // Categories
            val items = listOf(
                Triple("au (à + le)", "مذكر مفرد ساكن", "au restaurant, au zoo, au cinéma, au club, au jardin, au magasin"),
                Triple("à la", "مؤنث مفرد ساكن", "à la pharmacie, à la tour, à la poste"),
                Triple("à l'", "مفرد بادئ بحرف متحرك أو h صامت", "à l'école, à l'hôtel"),
                Triple("aux (à + les)", "جمع بنوعيه", "aux pyramides")
            )

            items.forEach { (article, type, examples) ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = when {
                        article.startsWith("au") -> Indigo50.copy(alpha = 0.6f)
                        article.startsWith("à la") -> Violet50
                        article.startsWith("à l'") -> Teal50
                        else -> Orange50
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = article,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            Text(
                                text = type,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate700
                            )
                        }
                        Text(
                            text = "أمثلة الكتيّب: $examples",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate900
                        )
                    }
                }
            }

            if (onNavigateToTab != null) {
                OutlinedButton(
                    onClick = { onNavigateToTab(2) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Indigo700)
                ) {
                    Text("انتقل لدرس حروف جر الأماكن الكامل في الكتيّب (ص 86) ➔", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * شرح قاعدة التمرين الخامس: النفي وتحويل الأدوات واستثناء فعل être (ص 6 و 87)
 */
@Composable
private fun RuleCardNegationRules(
    audioHelper: AudioHelper,
    onNavigateToTab: ((Int) -> Unit)?
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "قاعدة التمرين الخامس: صيغة النفي واستثناء فعل الكينونة (ص 6 و 87)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900
            )

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Slate50,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("1️⃣ النفي البسيط:", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Indigo700)
                    Text("نضع الفعل بين ne و pas ➔ Je vais au stade ➔ Je ne vais pas au stade.", fontSize = 11.sp, color = Slate800)
                }
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Rose50,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("2️⃣ قاعدة تحويل أدوات النكرة والجمع:", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Rose600)
                    Text("تتحول (un, une, des) في النفي إلى de أو d' أمام حرف متحرك:", fontSize = 11.sp, color = Slate800)
                    Text("• Nous avons des amis ➔ Nous n'avons pas d'amis.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                    Text("• Il a une nouvelle voiture ➔ Il n'a pas de nouvelle voiture.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                }
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Emerald50,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("3️⃣ ⚠️ الاستثناء الذهبي مع فعل الكينونة (ÊTRE):", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Emerald600)
                    Text("مع فعل être، لا تتغير الأدوات إطلاقاً وتظل كما هي:", fontSize = 11.sp, color = Slate800)
                    Text("• Nous sommes des amis ➔ Nous ne sommes pas des amis (ظلت des كما هي).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                    Text("• Elle est une élève ➔ Elle n'est pas une élève (ظلت une كما هي).", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                }
            }

            if (onNavigateToTab != null) {
                OutlinedButton(
                    onClick = { onNavigateToTab(3) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Indigo700)
                ) {
                    Text("انتقل لدرس النفي الكامل في الكتيّب (ص 87) ➔", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * ملخص شامل لجميع القواعد عند اختيار "الكل"
 */
@Composable
private fun RuleCardOverviewAll(
    audioHelper: AudioHelper,
    onNavigateToTab: ((Int) -> Unit)?
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "خريطة القواعد الخمسة لتمارين التمهيدي (ص 4-6):",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900
            )

            val rulesList = listOf(
                "1️⃣ ص 4: تصريف المضارع (نهايات المجموعة الأولى -er والمجموعة الثانية finir وأفعال avoir/être).",
                "2️⃣ ص 4: الكلمات الدالة وتوافق الفعل مع الفاعل (Maintenant, Aujourd'hui, Chaque jour).",
                "3️⃣ ص 5: أدوات الاستفهام الخمسة (Comment, Qu'est ce que, Qui, Où, Que).",
                "4️⃣ ص 5: حروف جر الأماكن (au للمذكر، à la للمؤنث، à l' للمتحرك، aux للجمع).",
                "5️⃣ ص 6: النفي (تحويل un/une/des إلى de/d' ما عدا مع فعل être تظل كما هي)."
            )

            rulesList.forEach { rule ->
                Text(
                    text = rule,
                    fontSize = 11.sp,
                    color = Slate800,
                    lineHeight = 16.sp
                )
            }

            Text(
                text = "💡 يمكنك اختيار أي تمرين من الأزرار العلوية لمطالعة شرح قاعدته بالتفصيل وحل أسئلته.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo700
            )
        }
    }
}
