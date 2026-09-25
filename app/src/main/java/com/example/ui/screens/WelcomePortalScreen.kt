package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.AppNavModule
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber200
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald300
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald900
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo200
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
import com.example.ui.theme.Rose600
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
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
import com.example.ui.theme.Violet900

/**
 * Main portal screen featuring exactly two primary doors:
 * 1. قسم المراجعة والامتحانات (مرتب بأرقام الصفحات بدقة)
 * 2. قسم الوحدات (الوحدة 1: الدعوة، الوحدة 2: الوجبات والمطعم، الوحدة 3: الصحة والمستشفى)
 */
@Composable
fun WelcomePortalScreen(
    audioHelper: AudioHelper,
    onNavigateToRevisionPage: (targetModule: AppNavModule, tabIndex: Int) -> Unit,
    onNavigateToUnit: (AppNavModule) -> Unit,
    modifier: Modifier = Modifier
) {
    // 0: Main Hub (2 big doors), 1: Inside Revision Sub-Hub (arranged by page numbers)
    var activeView by rememberSaveable { mutableStateOf(0) }

    Crossfade(targetState = activeView, label = "PortalViewTransition") { viewState ->
        when (viewState) {
            0 -> PortalHomeView(
                audioHelper = audioHelper,
                onOpenRevisionHub = {
                    audioHelper.playClick()
                    activeView = 1
                },
                onOpenUnit = { unitModule ->
                    audioHelper.playClick()
                    onNavigateToUnit(unitModule)
                },
                modifier = modifier
            )
            1 -> RevisionPagesIndexView(
                audioHelper = audioHelper,
                onBack = {
                    audioHelper.playClick()
                    activeView = 0
                },
                onSelectRevisionItem = { targetModule, tabIndex ->
                    audioHelper.playClick()
                    onNavigateToRevisionPage(targetModule, tabIndex)
                },
                modifier = modifier
            )
        }
    }
}

/**
 * Primary Hub: Contains exactly the two requested main sections:
 * - قسم المراجعة والامتحانات
 * - قسم الوحدات (الوحدة الأولى، الوحدة الثانية، الوحدة الثالثة)
 */
@Composable
private fun PortalHomeView(
    audioHelper: AudioHelper,
    onOpenRevisionHub: () -> Unit,
    onOpenUnit: (AppNavModule) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("portal_home_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))

            // Welcome Hero Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Indigo900),
                border = BorderStroke(1.5.dp, Indigo600.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Indigo900, Indigo700, Violet900)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Amber400,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text("🇫🇷", fontSize = 14.sp)
                                    Text(
                                        text = "منهج Bienvenu 2",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Indigo900
                                    )
                                }
                            }

                            Surface(
                                color = Color.White.copy(alpha = 0.15f),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "2ème Préparatoire",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Text(
                            text = "Bienvenue dans votre application !",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )

                        Text(
                            text = "تطبيق اللغة الفرنسية التفاعلي الشامل للصف الثاني الإعدادي • الفصل الدراسي الأول. اختر وجهتك للبدء مباشرة:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate100,
                            lineHeight = 19.sp
                        )
                    }
                }
            }
        }

        // =========================================================================
        // DOOR 1: قسم المراجعة والامتحانات (مرتب بأرقام الصفحات)
        // =========================================================================
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Indigo200),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenRevisionHub() }
                    .testTag("door_revision_and_exams")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                color = Indigo50,
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.5.dp, Indigo200),
                                modifier = Modifier.size(54.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("📚", fontSize = 28.sp)
                                }
                            }

                            Column {
                                Surface(
                                    color = Indigo100,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "القسم الأول",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Indigo900,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "قسم المراجعة والامتحانات",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Indigo900
                                )
                                Text(
                                    text = "Révision générale & Examens",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Slate600
                                )
                            }
                        }

                        Surface(
                            color = Amber400,
                            shape = CircleShape,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "فتح",
                                    tint = Indigo900,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "فهرس منظم تسلسلياً بحسب أرقام صفحات الكتيّب المدرسي (من ص 4 حتى ص 88): الأسئلة التمهيدية، نصوص الفهم، تمارين وقواعد النحو الشاملة، وامتحانات نصف العام الرسمية.",
                        fontSize = 12.sp,
                        color = Slate700,
                        lineHeight = 18.sp
                    )

                    // Page range pills overview
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PageMiniPill(label = "التمهيدي", pages = "ص 4-6", color = Indigo700, bgColor = Indigo50)
                        PageMiniPill(label = "نصوص الفهم", pages = "ص 67-71", color = Violet700, bgColor = Violet50)
                        PageMiniPill(label = "القواعد والتمارين", pages = "ص 72-77 / 84-88", color = Teal700, bgColor = Teal50)
                        PageMiniPill(label = "الامتحانات", pages = "ص 78-79", color = Amber700, bgColor = Amber50)
                    }

                    Button(
                        onClick = onOpenRevisionHub,
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 46.dp)
                            .testTag("btn_enter_revision_hub")
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "دخول قسم المراجعة والامتحانات (مرتب بالصفحات)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Amber400,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // =========================================================================
        // DOOR 2: قسم الوحدات الدراسية (Unités 1, 2, 3)
        // =========================================================================
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Orange100),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("door_units")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                color = Orange50,
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.5.dp, Orange100),
                                modifier = Modifier.size(54.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("🎯", fontSize = 28.sp)
                                }
                            }

                            Column {
                                Surface(
                                    color = Orange100,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "القسم الثاني",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Orange700,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "قسم الوحدات الدراسية",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Slate900
                                )
                                Text(
                                    text = "Les 3 Unités du Programme",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Slate600
                                )
                            }
                        }
                    }

                    Text(
                        text = "دروس الوحدات الثلاث المقررة بمنهج Bienvenu 2 كاملة: النصوص، المفردات، المواقف اليومية، القواعد النحوية، التكوين، وبنك الكلمات.",
                        fontSize = 12.sp,
                        color = Slate700,
                        lineHeight = 18.sp
                    )

                    // 3 Unit Cards with accurate, exact syllabus names
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Unité 1
                        UnitPortalCard(
                            unitNumber = "1",
                            unitTitleFr = "Unité 1 : Invitation",
                            unitTitleAr = "الوحدة الأولى : الدعوة • Les amis chez Gamal",
                            pagesRange = "ص 11 – 34",
                            badgeColor = Violet700,
                            bgColor = Violet50,
                            borderColor = Violet100,
                            iconEmoji = "✉️",
                            testTag = "portal_unit_1_card",
                            onClick = { onOpenUnit(AppNavModule.UNIT_1) }
                        )

                        // Unité 2
                        UnitPortalCard(
                            unitNumber = "2",
                            unitTitleFr = "Unité 2 : Repas & Restaurant",
                            unitTitleAr = "الوحدة الثانية : الوجبات والمطعم • Le mariage de Jean",
                            pagesRange = "ص 35 – 54",
                            badgeColor = Orange700,
                            bgColor = Orange50,
                            borderColor = Orange100,
                            iconEmoji = "🍽️",
                            testTag = "portal_unit_2_card",
                            onClick = { onOpenUnit(AppNavModule.UNIT_2) }
                        )

                        // Unité 3
                        UnitPortalCard(
                            unitNumber = "3",
                            unitTitleFr = "Unité 3 : Santé & Hôpital",
                            unitTitleAr = "الوحدة الثالثة : الصحة والمستشفى • L'accident de Samir",
                            pagesRange = "ص 59 – 67",
                            badgeColor = Teal700,
                            bgColor = Teal50,
                            borderColor = Teal100,
                            iconEmoji = "🏥",
                            testTag = "portal_unit_3_card",
                            onClick = { onOpenUnit(AppNavModule.UNIT_3) }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Inside the Revision and Exams section: Arranged strictly by page numbers from the booklet.
 */
@Composable
private fun RevisionPagesIndexView(
    audioHelper: AudioHelper,
    onBack: () -> Unit,
    onSelectRevisionItem: (targetModule: AppNavModule, tabIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))

            // Sub-header with back button
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Indigo900),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onBack() }
                                .testTag("btn_back_to_portal_home")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "العودة للرئيسية",
                                    tint = Color.White
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "قسم المراجعة والامتحانات",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "فهرس منظم بحسب أرقام صفحات الكتيّب المدرسي",
                                fontSize = 11.sp,
                                color = Amber400,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = onBack,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "الرئيسية ↩",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // =========================================================================
        // CHRONOLOGICAL PAGE-ORDERED ITEMS
        // =========================================================================

        // 1. Pages 4 to 6: الأسئلة والتمارين التمهيدية قبل الوحدات
        item {
            RevisionPageCard(
                pageRange = "صفحات 4 – 6",
                categoryTag = "مراجعة تمهيدية ما قبل الوحدات",
                titleFr = "Questions préliminaires & Exercices de base",
                titleAr = "الأسئلة التمهيدية (37 سؤالاً) وربطها بالقواعد",
                description = "تصريف الأفعال في المضارع (ص 4)، اختيار الأفعال المناسبة (ص 4)، أدوات الاستفهام وتكوين السؤال (ص 5)، حروف جر الأماكن (ص 5)، وصيغ النفي وتحويل الأدوات (ص 6).",
                badgeColor = Indigo700,
                bgColor = Indigo50,
                borderColor = Indigo200,
                iconEmoji = "🎯",
                actionLabel = "ابدأ حل الأسئلة التمهيدية (ص 4-6) ➔",
                testTag = "revision_page_card_4_6",
                onClick = { onSelectRevisionItem(AppNavModule.GRAMMAR, 4) }
            )
        }

        // 2. Pages 67 to 71: نصوص الفهم والاستيعاب للمراجعة
        item {
            RevisionPageCard(
                pageRange = "صفحات 67 – 71",
                categoryTag = "نصوص الفهم والاستيعاب (Compréhension)",
                titleFr = "Documents & Textes de révision générale",
                titleAr = "الوثائق الأربع الشاملة ومحادثات الفهم",
                description = "الوثيقة 1 (ص 67: دعوة عائلة موريل)، الوثيقة 2 (ص 68-69: حادث سمير وزيارة المستشفى)، الوثيقة 3 (ص 70: عشاء مطعم بيراميد)، والوثيقة 4 (ص 71: يوم دراسي ومسارات). تشمل أسئلة الاختيار، الصح والخطأ، والأسئلة المقالية.",
                badgeColor = Violet700,
                bgColor = Violet50,
                borderColor = Violet100,
                iconEmoji = "📖",
                actionLabel = "فتح نصوص الفهم (ص 67-71) ➔",
                testTag = "revision_page_card_67_71",
                onClick = { onSelectRevisionItem(AppNavModule.REVISION, 0) }
            )
        }

        // 3. Pages 72 to 77: تمارين القواعد الشاملة لمراجعة نصف العام
        item {
            RevisionPageCard(
                pageRange = "صفحات 72 – 77",
                categoryTag = "تمارين وتطبيقات القواعد الشاملة (Grammaire)",
                titleFr = "Exercices variés de grammaire de mi-année",
                titleAr = "تمارين القواعد الشاملة (7 تمارين متنوعة)",
                description = "تطبيق عملي على قواعد المنهج: تمرين 1 (ص 72: صفات الملكية)، تمرين 2 (ص 73: النفي الكامل)، تمرين 3 (ص 74: حروف الجر والأماكن)، تمرين 4 (ص 75: الضمائر الشخصية le, la, l', les)، تمرين 5 (ص 75: المضارع)، تمرين 6 (ص 76: أدوات التجزئة)، وتمرين 7 (ص 77: الاستفهام).",
                badgeColor = Teal700,
                bgColor = Teal50,
                borderColor = Teal100,
                iconEmoji = "📐",
                actionLabel = "فتح تمارين القواعد (ص 72-77) ➔",
                testTag = "revision_page_card_72_77",
                onClick = { onSelectRevisionItem(AppNavModule.REVISION, 1) }
            )
        }

        // 4. Pages 84 to 88: ملخص ورشة القواعد التأسيسية
        item {
            RevisionPageCard(
                pageRange = "صفحات 84 – 88",
                categoryTag = "شرح القواعد النحوية التأسيسية",
                titleFr = "Atelier Grammaire & Règles fondamentales",
                titleAr = "ورشة وملخص القواعد النحوية المقررة",
                description = "ص 84: تكوين الجملة وأقسام الكلمة (La Phrase) • ص 85: أزمنة المضارع ونهايات المجموعات الثلاث والشواذ (Le Présent) • ص 86: حروف جر الأماكن وأدوات المعرفة المدغمة (Lieux) • ص 87: قواعد وأدوات النفي وتحويل الأدوات (Négation).",
                badgeColor = Slate800,
                bgColor = Slate50,
                borderColor = Slate300,
                iconEmoji = "🧱",
                actionLabel = "فتح ورشة القواعد (ص 84-88) ➔",
                testTag = "revision_page_card_84_88",
                onClick = { onSelectRevisionItem(AppNavModule.GRAMMAR, 0) }
            )
        }

        // 5. Pages 78 to 79: امتحان نصف العام الرسمي المعتمد
        item {
            RevisionPageCard(
                pageRange = "صفحات 78 – 79",
                categoryTag = "الامتحان الرسمي المعتمد (Examen Officiel)",
                titleFr = "Examen officiel de mi-année (20/20)",
                titleAr = "امتحان نصف العام الرسمي المعتمد (منهج Bienvenu 2)",
                description = "مطابق تماماً لصفحتي 78 و 79 بالكتيّب: الوثيقة الأساسية، أسئلة الاختيار والصح والخطأ، سؤال المواقف اليومية، قواعد النحو، وسؤال أكمل الجمل والتعبير مع تصحيح فوري ورصد درجات رسمي من 20 درجة.",
                badgeColor = Amber700,
                bgColor = Amber50,
                borderColor = Amber200,
                iconEmoji = "📝",
                actionLabel = "دخول امتحان نصف العام (ص 78-79) ➔",
                testTag = "revision_page_card_78_79",
                onClick = { onSelectRevisionItem(AppNavModule.EXAM, 0) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Reusable Card component for single unit door with standardized icon dimensions
 */
@Composable
private fun UnitPortalCard(
    unitNumber: String,
    unitTitleFr: String,
    unitTitleAr: String,
    pagesRange: String,
    badgeColor: Color,
    bgColor: Color,
    borderColor: Color,
    iconEmoji: String,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(1.5.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Standardized unit icon container with fixed 48x48dp dimensions to ensure perfect alignment
                Surface(
                    color = badgeColor,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("${testTag}_icon_badge")
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = iconEmoji,
                            fontSize = 22.sp,
                            lineHeight = 22.sp,
                            maxLines = 1
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = unitTitleFr,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.35f))
                        ) {
                            Text(
                                text = pagesRange,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = badgeColor,
                                maxLines = 1,
                                softWrap = false,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = unitTitleAr,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate700,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.85f),
                border = BorderStroke(1.dp, borderColor),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "فتح الوحدة",
                        tint = badgeColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Reusable Card component for page-indexed revision section
 */
@Composable
private fun RevisionPageCard(
    pageRange: String,
    categoryTag: String,
    titleFr: String,
    titleAr: String,
    description: String,
    badgeColor: Color,
    bgColor: Color,
    borderColor: Color,
    iconEmoji: String,
    actionLabel: String,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Page tag & Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = badgeColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(iconEmoji, fontSize = 12.sp)
                        Text(
                            text = pageRange,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }

                Surface(
                    color = bgColor,
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Text(
                        text = categoryTag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Titles
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = titleAr,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900
                )
                Text(
                    text = titleFr,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = badgeColor
                )
            }

            // Description
            Text(
                text = description,
                fontSize = 11.sp,
                color = Slate700,
                lineHeight = 17.sp
            )

            // Action Button
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = badgeColor),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 42.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = actionLabel,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun PageMiniPill(
    label: String,
    pages: String,
    color: Color,
    bgColor: Color
) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pages,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
            Text(
                text = label,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = Slate600
            )
        }
    }
}
