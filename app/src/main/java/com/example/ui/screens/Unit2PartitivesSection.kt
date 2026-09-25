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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber200
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
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
import com.example.ui.theme.Rose200
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose700
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
import com.example.ui.theme.Violet900

/**
 * Data representation for partitive vocabulary from page 44
 */
data class PartitiveWordItem(
    val id: String,
    val article: String,       // "Du", "De la", "De l'", "Des"
    val frenchNoun: String,    // "Pain", "tartine", "eau", "fèves"
    val fullFrench: String,    // "Du pain"
    val arabicMeaning: String, // "خبز"
    val category: PartitiveCategory,
    val emoji: String,
    val note: String? = null
)

enum class PartitiveCategory(val labelFr: String, val labelAr: String, val color: Color, val bg: Color) {
    MASCULIN("Masculin (du)", "مفرد مذكر", Orange600, Orange50),
    FEMININ("Féminin (de la)", "مفرد مؤنث", Rose600, Rose50),
    VOYELLE("Voyelles (de l')", "يبدأ بمتحرك", Teal700, Teal50),
    PLURIEL("Pluriel (des)", "جمع بنوعيه", Violet700, Violet50)
}

/**
 * Exercise 1 Item from page 45: Complète par un article partitif
 */
data class PartitiveEx1Item(
    val id: Int,
    val nounFr: String,
    val arabicMeaning: String,
    val correctArticle: String,
    val options: List<String> = listOf("du", "de la", "de l'", "des"),
    val explanationAr: String
)

/**
 * Exercise 2 Item from page 45: Complète par (du - de la - de l' - d' - des)
 */
data class PartitiveEx2Sentence(
    val id: Int,
    val sentenceTemplate: String, // using ___ for blanks
    val arabicTranslation: String,
    val correctAnswers: List<String>,
    val optionsPerBlank: List<List<String>>,
    val ruleExplanationAr: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Unit2PartitivesSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    var activeSubTab by remember { mutableIntStateOf(0) }
    // 0: 📜 Règles & N.B (p.43)
    // 1: 🗂️ Tableau Complet p.44 (45 mots)
    // 2: ✍️ Exercice 1 (p.45)
    // 3: 🎯 Exercice 2 (p.45)
    // 4: ⚡ Simulateur Négation

    val subTabs = listOf(
        "📜 القواعد والتنبيهات (ص 43)",
        "🗂️ جدول الكلمات (ص 44)",
        "✍️ تمرين 1 (ص 45)",
        "🎯 تمرين 2 نفي وتجزئة (ص 45)",
        "⚡ محاكي النفي"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Header Banner
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Orange50),
            border = BorderStroke(1.5.dp, Orange500.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Orange600
                    ) {
                        Text(
                            text = "Grammaire • Unité 2",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Amber100
                    ) {
                        Text(
                            text = "📖 صفحات 43 - 44 - 45",
                            color = Amber800,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(
                    text = "Les Articles Partitifs (أدوات التجزئة)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Orange700
                )

                Text(
                    text = "« On utilise les articles partitifs avec les boissons et les aliments. »\nتُستخدم أدوات التجزئة مع المأكولات والمشروبات للتعبير عن أخذ جزء من الشيء أو كمية غير محددة.",
                    fontSize = 12.sp,
                    color = Slate700,
                    lineHeight = 18.sp
                )

                Button(
                    onClick = {
                        audioHelper.speak("Les articles partitifs. On utilise les articles partitifs avec les boissons et les aliments.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("استمع لقاعدة أدوات التجزئة بالفرنسية 🔊", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        // Horizontal navigation tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            subTabs.forEachIndexed { index, title ->
                val isSelected = activeSubTab == index
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isSelected) Orange600 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Orange600 else Slate200),
                    modifier = Modifier
                        .clickable { activeSubTab = index }
                        .testTag("partitive_tab_$index")
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.White else Slate700
                    )
                }
            }
        }

        // SubTab Content
        when (activeSubTab) {
            0 -> PartitivesRulesSubSection(audioHelper = audioHelper)
            1 -> PartitivesTableSubSection(audioHelper = audioHelper)
            2 -> PartitivesExercise1SubSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            3 -> PartitivesExercise2SubSection(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            4 -> PartitivesNegationSimulatorSubSection(audioHelper = audioHelper)
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 0: RÈGLES ET N.B (PAGE 43)
// -------------------------------------------------------------
@Composable
private fun PartitivesRulesSubSection(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // The 4 Articles Cards
        Text(
            text = "📌 الأشكال الأربعة لأداة التجزئة (ص 43) :",
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = Slate800
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PartitiveCardCompact(
                article = "Du",
                ruleFr = "masculin singulier",
                ruleAr = "مفرد مذكر يبدأ بحرف ساكن",
                examples = "pain, riz, miel, café, yaourt",
                color = Orange600,
                bg = Orange50,
                modifier = Modifier.weight(1f),
                audioHelper = audioHelper
            )
            PartitiveCardCompact(
                article = "De la",
                ruleFr = "féminin singulier",
                ruleAr = "مفرد مؤنث يبدأ بحرف ساكن",
                examples = "tartine, viande, salade, glace",
                color = Rose600,
                bg = Rose50,
                modifier = Modifier.weight(1f),
                audioHelper = audioHelper
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PartitiveCardCompact(
                article = "De l'",
                ruleFr = "masc. ou fém. avec voyelle",
                ruleAr = "مفرد بنوعيه يبدأ بمتحرك أو H",
                examples = "eau, huile, olive, ail, œuf",
                color = Teal700,
                bg = Teal50,
                modifier = Modifier.weight(1f),
                audioHelper = audioHelper
            )
            PartitiveCardCompact(
                article = "Des",
                ruleFr = "Pluriel",
                ruleAr = "جمع بنوعيه ينتهي بـ s أو x",
                examples = "fèves, frites, œufs, pâtes",
                color = Violet700,
                bg = Violet50,
                modifier = Modifier.weight(1f),
                audioHelper = audioHelper
            )
        }

        // Note Banner (Boissons et Aliments)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Amber50,
            border = BorderStroke(1.5.dp, Amber400)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📌", fontSize = 24.sp, modifier = Modifier.padding(end = 10.dp))
                Column {
                    Text(
                        "On utilise les articles partitifs avec les boissons et les aliments.",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        color = Amber900
                    )
                    Text(
                        "تُستخدم أدوات التجزئة مع المأكولات والمشروبات عند تناول كمية أو جزء غير محدد (مع أفعال مثل: manger, boire, prendre, vouloir, mettre, acheter).",
                        fontSize = 11.sp,
                        color = Amber800
                    )
                }
            }
        }

        // N.B 1: LA NÉGATION
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Rose50),
            border = BorderStroke(1.5.dp, Rose500)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Rose600
                    ) {
                        Text(
                            "N.B. 1 • تنبيه هام جداً بالامتحان",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("🚨 La Négation (النفي)", fontWeight = FontWeight.Black, fontSize = 13.sp, color = Rose700)
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Rose200)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Du / De la / De l' / Des  +  (ne ..... pas)  ➔  de / d'",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = Rose700,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "في الجملة المنفية (ne ... pas)، تتحول جميع أدوات التجزئة (du, de la, de l', des) إلى (de) أو (d') إذا كانت الكلمة التالية تبدأ بحرف متحرك أو H صامتة!",
                            fontSize = 11.sp,
                            color = Slate800,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Examples of negation from page 45
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    NegationExampleRow(
                        affirmatif = "Je bois du café.",
                        negatif = "Je ne bois pas de café.",
                        note = "du ➔ de (مفرد ساكن)",
                        audioHelper = audioHelper
                    )
                    NegationExampleRow(
                        affirmatif = "Hala met de l'huile.",
                        negatif = "Hala ne met pas d'huile.",
                        note = "de l' ➔ d' (أمام حرف متحرك h)",
                        audioHelper = audioHelper
                    )
                    NegationExampleRow(
                        affirmatif = "Je mange des croissants.",
                        negatif = "Je ne mange pas de croissant.",
                        note = "des ➔ de (في النفي لا نضع des)",
                        audioHelper = audioHelper
                    )
                }
            }
        }

        // N.B 2: LES VERBES DES GOÛTS
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Emerald50),
            border = BorderStroke(1.5.dp, Emerald500)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Emerald600
                    ) {
                        Text(
                            "N.B. 2 • قاعدة أفعال الميول",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("❤️ Les verbes des goûts", fontWeight = FontWeight.Black, fontSize = 13.sp, color = Emerald900)
                }

                Text(
                    text = "مع أفعال الميول والتفضيل الأربعة نستخدم دائماً أدوات المعرفة [le, la, l', les] ولا نستخدم أدوات التجزئة إطلاقاً (حتى في النفي تبقى أدوات معرفة) :",
                    fontSize = 11.sp,
                    color = Slate800,
                    lineHeight = 16.sp
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Emerald300)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("aimer (يحب)", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Emerald700)
                            Text("préférer (يفضل)", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Emerald700)
                            Text("adorer (يعشق)", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Emerald700)
                            Text("détester (يكره)", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Emerald700)
                        }

                        Text("➔", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Emerald600)

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("+ articles définis", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Emerald900)
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Emerald100
                            ) {
                                Text(
                                    "[ le, la, l', les ]",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    color = Emerald900,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Comparison examples
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("تجزئة (كمية)", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Orange600)
                            Text("Je prends du chocolat.", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate800)
                            Text("آخذ بعض الشوكولاتة", fontSize = 9.sp, color = Slate600)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("فعل ميول (معرفة)", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Emerald600)
                            Text("J'aime le chocolat.", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate800)
                            Text("أحب الشوكولاتة عموماً", fontSize = 9.sp, color = Slate600)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PartitiveCardCompact(
    article: String,
    ruleFr: String,
    ruleAr: String,
    examples: String,
    color: Color,
    bg: Color,
    modifier: Modifier = Modifier,
    audioHelper: AudioHelper
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = color
                )
                IconButton(
                    onClick = { audioHelper.speak(article) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                }
            }

            Text(text = ruleFr, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = ruleAr, fontSize = 9.sp, color = Slate700)

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color.White.copy(alpha = 0.8f)
            ) {
                Text(
                    text = "Ex: $examples",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate800,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun NegationExampleRow(
    affirmatif: String,
    negatif: String,
    note: String,
    audioHelper: AudioHelper
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Rose100),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("☀️ $affirmatif", fontSize = 10.sp, color = Slate700)
                    Text("  ➔  ", fontSize = 10.sp, color = Rose500)
                    Text("🌧️ $negatif", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Rose700)
                }
                Text("• $note", fontSize = 9.sp, color = Slate600)
            }

            IconButton(
                onClick = { audioHelper.speak(negatif) },
                modifier = Modifier.size(26.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Rose600, modifier = Modifier.size(18.dp))
            }
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 1: TABLEAU COMPLET (PAGE 44) - 45 MOTS
// -------------------------------------------------------------
@Composable
private fun PartitivesTableSubSection(audioHelper: AudioHelper) {
    var selectedCategoryFilter by remember { mutableStateOf<PartitiveCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val allWords = remember { getPartitivesFullTableData() }

    val filteredWords = remember(selectedCategoryFilter, searchQuery) {
        allWords.filter { item ->
            val matchesCategory = selectedCategoryFilter == null || item.category == selectedCategoryFilter
            val matchesSearch = searchQuery.isBlank() ||
                    item.fullFrench.contains(searchQuery, ignoreCase = true) ||
                    item.arabicMeaning.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Search and Stats
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("ابحث عن أي كلمة بالفرنسية أو العربية...", fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Slate500) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Slate500)
                    }
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Orange500,
                unfocusedBorderColor = Slate300
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (selectedCategoryFilter == null) Orange600 else Slate100,
                modifier = Modifier.clickable { selectedCategoryFilter = null }
            ) {
                Text(
                    text = "الكل (${allWords.size})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedCategoryFilter == null) Color.White else Slate700,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            PartitiveCategory.values().forEach { cat ->
                val isSelected = selectedCategoryFilter == cat
                val count = allWords.count { it.category == cat }
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) cat.color else cat.bg,
                    border = BorderStroke(1.dp, if (isSelected) cat.color else cat.color.copy(alpha = 0.3f)),
                    modifier = Modifier.clickable { selectedCategoryFilter = cat }
                ) {
                    Text(
                        text = "${cat.labelFr} ($count)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else cat.color,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Yaourt Alert
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Amber50,
            border = BorderStroke(1.dp, Amber400)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("⚠️", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = "ملاحظة هامة (ص 44): كلمة « Du yaourt » مفرد مذكر وتأخذ أداة التجزئة (du) ولا تعامل كحرف متحرك!",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Amber900
                )
            }
        }

        // List of items
        Text(
            text = "قائمة الكلمات الرسمية المقررة (${filteredWords.size} كلمة) :",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = Slate800
        )

        filteredWords.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    PartitiveItemCard(
                        item = item,
                        audioHelper = audioHelper,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun PartitiveItemCard(
    item: PartitiveWordItem,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = item.category.bg,
        border = BorderStroke(1.dp, item.category.color.copy(alpha = 0.4f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(item.emoji, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                Column {
                    Text(
                        text = item.fullFrench,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = item.category.color
                    )
                    Text(
                        text = item.arabicMeaning,
                        fontSize = 11.sp,
                        color = Slate700
                    )
                    if (item.note != null) {
                        Text(
                            text = item.note,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange600
                        )
                    }
                }
            }

            IconButton(
                onClick = { audioHelper.speak(item.fullFrench) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "استمع",
                    tint = item.category.color,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 2: EXERCICE 1 (PAGE 45) - COMPLÈTE PAR UN ARTICLE PARTITIF
// -------------------------------------------------------------
@Composable
private fun PartitivesExercise1SubSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    val questions = remember { getPartitiveEx1Questions() }
    val userAnswers = remember { mutableStateMapOf<Int, String>() }
    var score by remember { mutableIntStateOf(0) }
    var submitted by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.5.dp, Indigo600.copy(alpha = 0.4f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "تمرين كتاب المعهد ص 45",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = Indigo900
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Indigo600
                    ) {
                        Text(
                            "14 نقطة اختبارية",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    text = "Complète par un article partitif : ( du - de la - de l' - des )",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    color = Indigo700
                )
                Text(
                    text = "اختر أداة التجزئة الصحيحة لكل كلمة كما وردت تماماً في صفحة 45 من كتيّب المعهد.",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        // Questions List
        questions.forEach { item ->
            val selected = userAnswers[item.id]
            val isAnswered = selected != null
            val isCorrect = selected == item.correctArticle

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        !isAnswered -> Color.White
                        isCorrect -> Emerald50
                        else -> Rose50
                    }
                ),
                border = BorderStroke(
                    1.dp,
                    when {
                        !isAnswered -> Slate200
                        isCorrect -> Emerald500
                        else -> Rose500
                    }
                )
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = if (isAnswered && isCorrect) Emerald600 else if (isAnswered) Rose600 else Indigo600,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${item.id}",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "..... ${item.nounFr}",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = Slate900
                            )
                        }

                        Text(
                            text = item.arabicMeaning,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )
                    }

                    // Options
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item.options.forEach { opt ->
                            val isThisSelected = selected == opt
                            val isThisCorrect = opt == item.correctArticle

                            val btnBg = when {
                                !isAnswered -> if (isThisSelected) Indigo600 else Slate100
                                isThisSelected && isCorrect -> Emerald600
                                isThisSelected && !isCorrect -> Rose600
                                isThisCorrect && !isCorrect -> Emerald100
                                else -> Slate100
                            }

                            val textColor = when {
                                !isAnswered -> if (isThisSelected) Color.White else Slate800
                                isThisSelected -> Color.White
                                isThisCorrect && !isCorrect -> Emerald700
                                else -> Slate700
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = btnBg,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (!isAnswered) {
                                            userAnswers[item.id] = opt
                                            if (opt == item.correctArticle) {
                                                onScoreEarned(5)
                                                audioHelper.speak("${item.correctArticle} ${item.nounFr}")
                                            } else {
                                                audioHelper.speak(item.nounFr)
                                            }
                                        }
                                    }
                            ) {
                                Text(
                                    text = opt,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = textColor
                                )
                            }
                        }
                    }

                    // Feedback explanation
                    if (isAnswered) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCorrect) Emerald100.copy(alpha = 0.5f) else Rose100.copy(alpha = 0.5f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (isCorrect) " أحسنت! ${item.explanationAr}" else "❌ الإجابة الصحيحة: ${item.correctArticle} (${item.explanationAr})",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCorrect) Emerald700 else Rose700,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(
                                    onClick = { audioHelper.speak("${item.correctArticle} ${item.nounFr}") },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = if (isCorrect) Emerald600 else Rose600,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Reset Button
        OutlinedButton(
            onClick = {
                userAnswers.clear()
                Toast.makeText(context, "تمت إعادة تعيين التمرين!", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("إعادة حل التمرين بالكامل", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 3: EXERCICE 2 (PAGE 45) - COMPLÈTE PAR (DU, DE LA, DE L', D', DES)
// -------------------------------------------------------------
@Composable
private fun PartitivesExercise2SubSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val sentences = remember { getPartitiveEx2Sentences() }
    val userSelections = remember { mutableStateMapOf<String, String>() }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Rose50),
            border = BorderStroke(1.5.dp, Rose500.copy(alpha = 0.4f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "تمرين النفي والتجزئة ص 45",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = Rose700
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Rose600
                    ) {
                        Text(
                            "Complète (du - de la - de l' - d' - des)",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    text = "🚨 انتبه جيداً: الجمل المنفية (ne ... pas) تتحول فيها أداة التجزئة إلى (de) أو (d') !",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Rose700
                )
            }
        }

        // Sentences
        sentences.forEach { sentence ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, Slate200)
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
                            text = "${sentence.id}- ${sentence.sentenceTemplate}",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = Slate900,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                // Speak full sentence with correct answers filled in
                                var filledSentence = sentence.sentenceTemplate
                                sentence.correctAnswers.forEach { ans ->
                                    filledSentence = filledSentence.replaceFirst("..............", ans).replaceFirst("............", ans)
                                }
                                audioHelper.speak(filledSentence)
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Indigo600)
                        }
                    }

                    Text(
                        text = "الترجمة: ${sentence.arabicTranslation}",
                        fontSize = 11.sp,
                        color = Slate600
                    )

                    // Options for each blank
                    sentence.optionsPerBlank.forEachIndexed { blankIndex, optionsList ->
                        val key = "${sentence.id}_$blankIndex"
                        val currentAnswer = userSelections[key]
                        val correctAnswer = sentence.correctAnswers[blankIndex]
                        val isAnswered = currentAnswer != null
                        val isCorrect = currentAnswer == correctAnswer

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "الفراغ رقم (${blankIndex + 1}) :",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo700
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                optionsList.forEach { opt ->
                                    val isThis = currentAnswer == opt
                                    val isThisRight = opt == correctAnswer

                                    val bg = when {
                                        !isAnswered -> if (isThis) Indigo600 else Slate100
                                        isThis && isCorrect -> Emerald600
                                        isThis && !isCorrect -> Rose600
                                        isThisRight && !isCorrect -> Emerald100
                                        else -> Slate100
                                    }

                                    val textCol = when {
                                        !isAnswered -> if (isThis) Color.White else Slate800
                                        isThis -> Color.White
                                        isThisRight && !isCorrect -> Emerald700
                                        else -> Slate700
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = bg,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {
                                                if (!isAnswered) {
                                                    userSelections[key] = opt
                                                    if (opt == correctAnswer) {
                                                        onScoreEarned(5)
                                                    }
                                                }
                                            }
                                    ) {
                                        Text(
                                            text = opt,
                                            modifier = Modifier.padding(vertical = 7.dp),
                                            textAlign = TextAlign.Center,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Black,
                                            color = textCol
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Check if all blanks of this sentence are answered
                    val allAnswered = sentence.correctAnswers.indices.all { idx ->
                        userSelections["${sentence.id}_$idx"] != null
                    }

                    if (allAnswered) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Emerald50,
                            border = BorderStroke(1.dp, Emerald300)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "💡 الشرح القاعدي :",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Emerald900
                                )
                                Text(
                                    text = sentence.ruleExplanationAr,
                                    fontSize = 10.sp,
                                    color = Emerald900,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SUBSECTION 4: SIMULATEUR NÉGATION
// -------------------------------------------------------------
@Composable
private fun PartitivesNegationSimulatorSubSection(audioHelper: AudioHelper) {
    var isNegativeMode by remember { mutableStateOf(false) }

    val simulationExamples = listOf(
        Pair("Je bois du thé.", "Je ne bois pas de thé."),
        Pair("Je mange de la confiture.", "Je ne mange pas de confiture."),
        Pair("Hala met de l'huile.", "Hala ne met pas d'huile."),
        Pair("Elle prend des frites.", "Elle ne prend pas de frites."),
        Pair("Il achète du pain.", "Il n'achète pas de pain."),
        Pair("J'aime le chocolat.", "Je n'aime pas le chocolat. (فعل ميول: المعرفة تبقى كما هي!)")
    )

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = if (isNegativeMode) Rose50 else Amber50),
            border = BorderStroke(2.dp, if (isNegativeMode) Rose500 else Amber500)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isNegativeMode) "🌧️ الوضع: الجملة المنفية (Négation)" else "☀️ الوضع: الجملة المثبتة (Affirmation)",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = if (isNegativeMode) Rose700 else Amber900
                        )
                        Text(
                            text = if (isNegativeMode) "جميع أدوات التجزئة تحولت إلى [ de / d' ]" else "تُستخدم أدوات التجزئة [ du, de la, de l', des ]",
                            fontSize = 11.sp,
                            color = Slate700
                        )
                    }

                    Switch(
                        checked = isNegativeMode,
                        onCheckedChange = { isNegativeMode = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Rose600,
                            checkedTrackColor = Rose200,
                            uncheckedThumbColor = Amber600,
                            uncheckedTrackColor = Amber200
                        )
                    )
                }

                Text(
                    text = "حرّك المفتاح أعلاه لتشاهد كيف تتغير الجملة وقواعد التجزئة فوراً بين الإثبات والنفي!",
                    fontSize = 11.sp,
                    color = Slate600
                )
            }
        }

        // Examples
        Text(
            text = "⚡ نماذج حية مع النطق الصوتي :",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = Slate800
        )

        simulationExamples.forEachIndexed { index, (aff, neg) ->
            val displayText = if (isNegativeMode) neg else aff
            val activeColor = if (isNegativeMode) Rose700 else Orange700
            val activeBg = if (isNegativeMode) Rose50 else Orange50

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = activeBg,
                border = BorderStroke(1.dp, activeColor.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = displayText,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = activeColor
                        )
                        Text(
                            text = if (isNegativeMode) "صيغة النفي (تحولت أداة التجزئة)" else "صيغة الإثبات (أداة تجزئة أصلية)",
                            fontSize = 10.sp,
                            color = Slate600
                        )
                    }

                    IconButton(
                        onClick = { audioHelper.speak(displayText) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "استمع",
                            tint = activeColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// DATA PROVIDERS (PAGE 44 & 45 EXACT MATCH)
// -------------------------------------------------------------
private fun getPartitivesFullTableData(): List<PartitiveWordItem> {
    return listOf(
        // Masculin (du) - 24 items from page 44
        PartitiveWordItem("m1", "Du", "pain", "Du pain", "خبز", PartitiveCategory.MASCULIN, "🥖"),
        PartitiveWordItem("m2", "Du", "miel", "Du miel", "عسل", PartitiveCategory.MASCULIN, "🍯"),
        PartitiveWordItem("m3", "Du", "fromage", "Du fromage", "جبن", PartitiveCategory.MASCULIN, "🧀"),
        PartitiveWordItem("m4", "Du", "beurre", "Du beurre", "زبدة", PartitiveCategory.MASCULIN, "🧈"),
        PartitiveWordItem("m5", "Du", "croissant", "Du croissant", "كرواسون", PartitiveCategory.MASCULIN, "🥐"),
        PartitiveWordItem("m6", "Du", "gâteau", "Du gâteau", "جاتوه / كيك", PartitiveCategory.MASCULIN, "🍰"),
        PartitiveWordItem("m7", "Du", "riz", "Du riz", "أرز", PartitiveCategory.MASCULIN, "🍚"),
        PartitiveWordItem("m8", "Du", "poulet", "Du poulet", "دجاج", PartitiveCategory.MASCULIN, "🍗"),
        PartitiveWordItem("m9", "Du", "macaroni", "Du macaroni", "مكرونة", PartitiveCategory.MASCULIN, "🍝"),
        PartitiveWordItem("m10", "Du", "poisson", "Du poisson", "سمك", PartitiveCategory.MASCULIN, "🐟"),
        PartitiveWordItem("m11", "Du", "lait", "Du lait", "حليب / لبن", PartitiveCategory.MASCULIN, "🥛"),
        PartitiveWordItem("m12", "Du", "café", "Du café", "قهوة", PartitiveCategory.MASCULIN, "☕"),
        PartitiveWordItem("m13", "Du", "thé", "Du thé", "شاي", PartitiveCategory.MASCULIN, "🍵"),
        PartitiveWordItem("m14", "Du", "jus", "Du jus", "عصير", PartitiveCategory.MASCULIN, "🧃"),
        PartitiveWordItem("m15", "Du", "chocolat", "Du chocolat", "شوكولاتة", PartitiveCategory.MASCULIN, "🍫"),
        PartitiveWordItem("m16", "Du", "coca", "Du coca", "كوكاكولا", PartitiveCategory.MASCULIN, "🥤"),
        PartitiveWordItem("m17", "Du", "poivre", "Du poivre", "فلفل أسود", PartitiveCategory.MASCULIN, "🧂"),
        PartitiveWordItem("m18", "Du", "sel", "Du sel", "ملح", PartitiveCategory.MASCULIN, "🧂"),
        PartitiveWordItem("m19", "Du", "thym", "Du thym", "زعتر", PartitiveCategory.MASCULIN, "🌿"),
        PartitiveWordItem("m20", "Du", "sucre", "Du sucre", "سكر", PartitiveCategory.MASCULIN, "🍬"),
        PartitiveWordItem("m21", "Du", "veau", "Du veau", "لحم بتلو / عجل", PartitiveCategory.MASCULIN, "🥩"),
        PartitiveWordItem("m22", "Du", "bœuf", "Du bœuf", "لحم بقري", PartitiveCategory.MASCULIN, "🥩"),
        PartitiveWordItem("m23", "Du", "rôti", "Du rôti", "لحم مشوي / روستو", PartitiveCategory.MASCULIN, "🍖"),
        PartitiveWordItem("m24", "Du", "yaourt", "Du yaourt", "زبادي", PartitiveCategory.MASCULIN, "🥣", "مذكر بأداة du"),

        // Féminin (de la) - 12 items from page 44
        PartitiveWordItem("f1", "De la", "tartine", "De la tartine", "شريحة خبز مدهونة", PartitiveCategory.FEMININ, "🍞"),
        PartitiveWordItem("f2", "De la", "confiture", "De la confiture", "مربى", PartitiveCategory.FEMININ, "🍓"),
        PartitiveWordItem("f3", "De la", "salade", "De la salade", "سلطة", PartitiveCategory.FEMININ, "🥗"),
        PartitiveWordItem("f4", "De la", "dinde", "De la dinde", "ديك رومي", PartitiveCategory.FEMININ, "🦃"),
        PartitiveWordItem("f5", "De la", "viande", "De la viande", "لحم", PartitiveCategory.FEMININ, "🥩"),
        PartitiveWordItem("f6", "De la", "glace", "De la glace", "آيس كريم / مثلجات", PartitiveCategory.FEMININ, "🍨"),
        PartitiveWordItem("f7", "De la", "tarte", "De la tarte", "تارت / فطيرة حلوة", PartitiveCategory.FEMININ, "🥧"),
        PartitiveWordItem("f8", "De la", "soupe", "De la soupe", "شوربة / حساء", PartitiveCategory.FEMININ, "🍲"),
        PartitiveWordItem("f9", "De la", "crème", "De la crème", "كريمة / قشطة", PartitiveCategory.FEMININ, "🥛"),
        PartitiveWordItem("f10", "De la", "limonade", "De la limonade", "ليمونادة / عصير ليمون", PartitiveCategory.FEMININ, "🍋"),
        PartitiveWordItem("f11", "De la", "purée", "De la purée", "بطاطس مهروسة / بوريه", PartitiveCategory.FEMININ, "🥔"),
        PartitiveWordItem("f12", "De la", "pizza", "De la pizza", "بيتزا", PartitiveCategory.FEMININ, "🍕"),

        // Voyelles (de l') - 5 items from page 44
        PartitiveWordItem("v1", "De l'", "eau", "De l'eau", "ماء", PartitiveCategory.VOYELLE, "💧"),
        PartitiveWordItem("v2", "De l'", "huile", "De l'huile", "زيت", PartitiveCategory.VOYELLE, "🫒"),
        PartitiveWordItem("v3", "De l'", "olive", "De l'olive", "زيتون", PartitiveCategory.VOYELLE, "🫒"),
        PartitiveWordItem("v4", "De l'", "ail", "De l'ail", "ثوم", PartitiveCategory.VOYELLE, "🧄"),
        PartitiveWordItem("v5", "De l'", "œuf", "De l'œuf", "بيضة واحدة (مفرد)", PartitiveCategory.VOYELLE, "🥚", "مفرد يبدأ بحرف متحرك"),

        // Pluriel (des) - 4 items from page 44
        PartitiveWordItem("p1", "Des", "fèves", "Des fèves", "فول مدمس", PartitiveCategory.PLURIEL, "🫘"),
        PartitiveWordItem("p2", "Des", "frites", "Des frites", "بطاطس مقلية", PartitiveCategory.PLURIEL, "🍟"),
        PartitiveWordItem("p3", "Des", "œufs", "Des œufs", "بيض (جمع)", PartitiveCategory.PLURIEL, "🥚", "جمع بأداة des"),
        PartitiveWordItem("p4", "Des", "pâtes", "Des pâtes", "مكرونة / باستا", PartitiveCategory.PLURIEL, "🍝")
    )
}

private fun getPartitiveEx1Questions(): List<PartitiveEx1Item> {
    return listOf(
        PartitiveEx1Item(1, "tartine.", "شريحة خبز", "de la", explanationAr = "كلمة tartine مفرد مؤنث ➔ de la"),
        PartitiveEx1Item(2, "sel.", "ملح", "du", explanationAr = "كلمة sel مفرد مذكر ➔ du"),
        PartitiveEx1Item(3, "thym.", "زعتر", "du", explanationAr = "كلمة thym مفرد مذكر ➔ du"),
        PartitiveEx1Item(4, "viande.", "لحم", "de la", explanationAr = "كلمة viande مفرد مؤنث ➔ de la"),
        PartitiveEx1Item(5, "sucre.", "سكر", "du", explanationAr = "كلمة sucre مفرد مذكر ➔ du"),
        PartitiveEx1Item(6, "œufs.", "بيض (جمع)", "des", explanationAr = "تنتهي بـ s فهي جمع ➔ des"),
        PartitiveEx1Item(7, "veau.", "لحم عجل / بتلو", "du", explanationAr = "كلمة veau مفرد مذكر ➔ du"),
        PartitiveEx1Item(8, "miel.", "عسل", "du", explanationAr = "كلمة miel مفرد مذكر ➔ du"),
        PartitiveEx1Item(9, "glace.", "آيس كريم / مثلجات", "de la", explanationAr = "كلمة glace مفرد مؤنث ➔ de la"),
        PartitiveEx1Item(10, "fromage.", "جبن", "du", explanationAr = "كلمة fromage مفرد مذكر ➔ du"),
        PartitiveEx1Item(11, "confiture.", "مربى", "de la", explanationAr = "كلمة confiture مفرد مؤنث ➔ de la"),
        PartitiveEx1Item(12, "beurre.", "زبدة", "du", explanationAr = "كلمة beurre مفرد مذكر ➔ du"),
        PartitiveEx1Item(13, "pain.", "خبز", "du", explanationAr = "كلمة pain مفرد مذكر ➔ du"),
        PartitiveEx1Item(14, "croissant.", "كرواسون", "du", explanationAr = "كلمة croissant مفرد مذكر ➔ du")
    )
}

private fun getPartitiveEx2Sentences(): List<PartitiveEx2Sentence> {
    return listOf(
        PartitiveEx2Sentence(
            id = 1,
            sentenceTemplate = "Je ne bois pas .............. café au lait.",
            arabicTranslation = "أنا لا أشرب قهوة بالحليب.",
            correctAnswers = listOf("de"),
            optionsPerBlank = listOf(listOf("du", "de", "d'", "des")),
            ruleExplanationAr = "جملة منفية بـ (ne ... pas) وفعل boire: تتحول أداة التجزئة (du) إلى (de)."
        ),
        PartitiveEx2Sentence(
            id = 2,
            sentenceTemplate = "Comme entrée, je prends ............ salade ou ............ soupe.",
            arabicTranslation = "كمقبلات، أتناول سلطة أو شوربة.",
            correctAnswers = listOf("de la", "de la"),
            optionsPerBlank = listOf(
                listOf("du", "de la", "des"),
                listOf("du", "de la", "des")
            ),
            ruleExplanationAr = "كلمة salade و soupe كلاهما مفرد مؤنث ➔ تأخذان (de la)."
        ),
        PartitiveEx2Sentence(
            id = 3,
            sentenceTemplate = "Comme plat principal, je mange .............. poulet, .............. riz et .............. pain.",
            arabicTranslation = "كطبق رئيسي، آكل دجاج، أرز وخبز.",
            correctAnswers = listOf("du", "du", "du"),
            optionsPerBlank = listOf(
                listOf("du", "de la", "des"),
                listOf("du", "de la", "des"),
                listOf("du", "de la", "des")
            ),
            ruleExplanationAr = "كلمات poulet و riz و pain كلها مفرد مذكر يبدأ بحرف ساكن ➔ تأخذ (du)."
        ),
        PartitiveEx2Sentence(
            id = 4,
            sentenceTemplate = "Au petit déjeuner, je prends .............. fèves, .............. fromage, .............. confiture et .............. baguette.",
            arabicTranslation = "في الإفطار، أتناول فول، جبن، مربى وخبز باجيت فرنسي.",
            correctAnswers = listOf("des", "du", "de la", "de la"),
            optionsPerBlank = listOf(
                listOf("du", "des", "de la"),
                listOf("du", "de la", "des"),
                listOf("du", "de la", "des"),
                listOf("du", "de la", "des")
            ),
            ruleExplanationAr = "fèves جمع ➔ des | fromage مذكر ➔ du | confiture مؤنث ➔ de la | baguette مؤنث ➔ de la."
        ),
        PartitiveEx2Sentence(
            id = 5,
            sentenceTemplate = "Je ne mange pas .............. croissant, .............. pâtés.",
            arabicTranslation = "أنا لا آكل كرواسون، ولا باتيه.",
            correctAnswers = listOf("de", "de"),
            optionsPerBlank = listOf(
                listOf("du", "de", "des"),
                listOf("des", "de", "d'")
            ),
            ruleExplanationAr = "جملة منفية بـ (ne ... pas): في النفي تتحول أدوات التجزئة (du / des) إلى (de) مع الأسماء التي تبدأ بحرف ساكن."
        ),
        PartitiveEx2Sentence(
            id = 6,
            sentenceTemplate = "Hala ne met pas .............. huile.",
            arabicTranslation = "هالة لا تضع زيتاً.",
            correctAnswers = listOf("d'"),
            optionsPerBlank = listOf(listOf("de", "d'", "de l'", "du")),
            ruleExplanationAr = "جملة منفية بـ (ne ... pas) وكلمة huile تبدأ بحرف H غير ملفوظ (صامت / حرف متحرك)، فتتحول أداة التجزئة إلى (d')."
        )
    )
}
