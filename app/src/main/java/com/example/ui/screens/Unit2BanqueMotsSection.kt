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
import androidx.compose.material.icons.filled.Clear
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
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber200
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber800
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

enum class WordCategory(val labelFr: String, val labelAr: String, val emoji: String, val color: Color) {
    ALL("Tous", "الكل (79)", "📚", Indigo700),
    MASCULINE("Noms masculins", "أسماء مذكرة (18)", "👔", Indigo700),
    FEMININE("Noms féminins", "أسماء مؤنثة (15)", "👗", Rose600),
    FOOD("Aliments & Repas", "الأطعمة والوجبات (30)", "🥩", Orange600),
    VERBS("Verbes", "أفعال (16)", "⚡", Emerald700)
}

data class BanqueWord(
    val id: String,
    val french: String,
    val arabic: String,
    val category: WordCategory,
    val page: Int,
    val note: String? = null
)

@Composable
fun Unit2BanqueMotsSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val allWords = remember { getAllBanqueWords() }
    var selectedCategory by remember { mutableStateOf(WordCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var activeMode by remember { mutableIntStateOf(0) } // 0: القاموس, 1: بطاقات الحفظ (Flashcards), 2: اختبار التميز

    val filteredWords = remember(selectedCategory, searchQuery) {
        allWords.filter { word ->
            val matchesCategory = (selectedCategory == WordCategory.ALL) || (word.category == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                    word.french.contains(searchQuery, ignoreCase = true) ||
                    word.arabic.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.5.dp, Amber600.copy(alpha = 0.4f))
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
                        color = Amber600
                    ) {
                        Text(
                            text = "Banque des mots Repas",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Amber200)
                    ) {
                        Text(
                            text = "📖 صفحات 51 - 52 - 53",
                            color = Amber900,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(
                    text = "بنك كلمات وجبات ومطعم الوحدة الثانية",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Amber950
                )

                Text(
                    text = "القاموس الكامل والشامل لجميع مفردات الوحدة (79 مفردة وتعبير) من كتيّب منهج Bienvenu 2: أسماء مذكرة ومؤنثة، قائمة الأطعمة والمأكولات، وأفعال المطعم والوجبات مع النطق الصوتي التفاعلي.",
                    fontSize = 12.sp,
                    color = Slate700,
                    lineHeight = 18.sp
                )

                // Stats Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Indigo100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("18", fontWeight = FontWeight.Black, color = Indigo700, fontSize = 13.sp)
                            Text("مذكر ص 51", fontSize = 9.sp, color = Slate600)
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Rose100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("15", fontWeight = FontWeight.Black, color = Rose600, fontSize = 13.sp)
                            Text("مؤنث ص 51", fontSize = 9.sp, color = Slate600)
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Orange100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("30", fontWeight = FontWeight.Black, color = Orange600, fontSize = 13.sp)
                            Text("أطعمة 52-53", fontSize = 9.sp, color = Slate600)
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Emerald100),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("16", fontWeight = FontWeight.Black, color = Emerald700, fontSize = 13.sp)
                            Text("أفعال ص 53", fontSize = 9.sp, color = Slate600)
                        }
                    }
                }
            }
        }

        // Mode switch: 0 = قائمة الكلمات, 1 = بطاقات الحفظ, 2 = اختبار الكلمات
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val modes = listOf(
                Pair("📑 القاموس المنسق", 0),
                Pair("🗂️ بطاقات الحفظ", 1),
                Pair("🎯 اختبار الكلمات", 2)
            )

            modes.forEach { (label, modeIdx) ->
                val isSelected = activeMode == modeIdx
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Amber600 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Amber600 else Slate200),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { activeMode = modeIdx }
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        fontSize = 11.sp,
                        color = if (isSelected) Color.White else Slate700
                    )
                }
            }
        }

        when (activeMode) {
            0 -> BanqueListView(
                filteredWords = filteredWords,
                selectedCategory = selectedCategory,
                onCategorySelect = { selectedCategory = it },
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it },
                audioHelper = audioHelper
            )
            1 -> BanqueFlashcardsView(
                words = allWords,
                audioHelper = audioHelper
            )
            2 -> BanqueQuizView(
                words = allWords,
                audioHelper = audioHelper,
                onScoreEarned = onScoreEarned
            )
        }
    }
}

// -------------------------------------------------------------
// VIEW 1: قائمة الكلمات مع البحث والتصنيف
// -------------------------------------------------------------
@Composable
private fun BanqueListView(
    filteredWords: List<BanqueWord>,
    selectedCategory: WordCategory,
    onCategorySelect: (WordCategory) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    audioHelper: AudioHelper
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("ابحث عن أي كلمة بالفرنسية أو بالعربية...", fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Amber700) },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "مسح", tint = Slate500)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amber600,
                unfocusedBorderColor = Slate200,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            WordCategory.values().forEach { cat ->
                val isSelected = selectedCategory == cat
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) cat.color else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) cat.color else Slate200),
                    modifier = Modifier.clickable { onCategorySelect(cat) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(cat.emoji, fontSize = 12.sp, modifier = Modifier.padding(end = 4.dp))
                        Text(
                            text = cat.labelAr,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color.White else Slate700
                        )
                    }
                }
            }
        }

        // Count summary
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "عدد النتائج: ${filteredWords.size} كلمة",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Slate600
            )

            Button(
                onClick = {
                    val previewText = filteredWords.take(5).joinToString(", ") { it.french }
                    audioHelper.speak(previewText)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Amber600),
                shape = RoundedCornerShape(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier.height(28.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("استمع للعينات 🔊", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Word Items Grid/List
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            filteredWords.forEach { item ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = item.category.color.copy(alpha = 0.12f),
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(item.category.emoji, fontSize = 14.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = item.french,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Slate900
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = item.arabic,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = item.category.color
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "• ص ${item.page}",
                                        fontSize = 10.sp,
                                        color = Slate500
                                    )
                                    if (item.note != null) {
                                        Text(
                                            text = " (${item.note})",
                                            fontSize = 9.sp,
                                            color = Slate600
                                        )
                                    }
                                }
                            }
                        }

                        IconButton(
                            onClick = { audioHelper.speak(item.french) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "استمع",
                                tint = item.category.color,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// VIEW 2: بطاقات الحفظ التفاعلية (Flashcards)
// -------------------------------------------------------------
@Composable
private fun BanqueFlashcardsView(
    words: List<BanqueWord>,
    audioHelper: AudioHelper
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var isRevealed by remember { mutableStateOf(false) }

    val currentWord = words[currentIndex]

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "البطاقة رقم ${currentIndex + 1} من أصل ${words.size}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Slate600
        )

        // Flashcard container
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = if (isRevealed) Amber50 else Color.White),
            border = BorderStroke(2.dp, if (isRevealed) Amber500 else Slate200),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable {
                    isRevealed = !isRevealed
                    if (isRevealed) audioHelper.speak(currentWord.french)
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = currentWord.category.color.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${currentWord.category.emoji} ${currentWord.category.labelFr} (p.${currentWord.page})",
                            color = currentWord.category.color,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentWord.french,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate900,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (isRevealed) {
                        Text(
                            text = currentWord.arabic,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Amber800,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "👉 اضغط لكشف المعنى بالعربية والنطق 🔊",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }
                }
            }
        }

        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (currentIndex > 0) {
                        currentIndex--
                        isRevealed = false
                    }
                },
                enabled = currentIndex > 0,
                colors = ButtonDefaults.buttonColors(containerColor = Slate700),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("السابقة ⬅️", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            IconButton(
                onClick = { audioHelper.speak(currentWord.french) },
                modifier = Modifier.size(44.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "استمع", tint = Amber600, modifier = Modifier.size(30.dp))
            }

            Button(
                onClick = {
                    if (currentIndex < words.size - 1) {
                        currentIndex++
                        isRevealed = false
                    }
                },
                enabled = currentIndex < words.size - 1,
                colors = ButtonDefaults.buttonColors(containerColor = Amber600),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("التالية ➡️", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -------------------------------------------------------------
// VIEW 3: اختبار التميز والذاكرة لبنك الكلمات
// -------------------------------------------------------------
@Composable
private fun BanqueQuizView(
    words: List<BanqueWord>,
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    var quizSeed by remember { mutableIntStateOf(0) }
    val quizItems = remember(quizSeed) {
        words.shuffled().take(8).map { correctWord ->
            val wrongOptions = words
                .filter { it.id != correctWord.id && it.category == correctWord.category }
                .shuffled()
                .take(3)
                .map { it.arabic }
                .toMutableList()
            if (wrongOptions.size < 3) {
                wrongOptions.addAll(words.filter { it.id != correctWord.id }.shuffled().take(3 - wrongOptions.size).map { it.arabic })
            }
            val allOptions = (wrongOptions + correctWord.arabic).shuffled()
            Triple(correctWord, allOptions, correctWord.arabic)
        }
    }

    val userSelections = remember(quizSeed) { mutableStateMapOf<String, String>() }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.dp, Indigo200)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "تحدي مفردات بنك الكلمات",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = Indigo900
                    )
                    Surface(shape = RoundedCornerShape(8.dp), color = Indigo600) {
                        Text(
                            "+5 درجات لكل كلمة",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = "اختر المعنى العربي الصحيح لكل كلمة فرنسية مقررة:",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        quizItems.forEachIndexed { index, (word, options, correctArabic) ->
            val selected = userSelections[word.id]
            val isAnswered = selected != null
            val isCorrect = selected == correctArabic

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = when {
                    !isAnswered -> Color.White
                    isCorrect -> Emerald50
                    else -> Rose50
                },
                border = BorderStroke(
                    1.dp,
                    when {
                        !isAnswered -> Slate200
                        isCorrect -> Emerald500
                        else -> Rose500
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = if (isAnswered && isCorrect) Emerald600 else if (isAnswered) Rose600 else Amber600,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("${index + 1}", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = word.french,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = Slate900
                            )
                        }

                        IconButton(
                            onClick = { audioHelper.speak(word.french) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Amber600)
                        }
                    }

                    // Options
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        options.forEach { opt ->
                            val isThisSelected = selected == opt
                            val isThisCorrect = opt == correctArabic

                            val bgCol = when {
                                !isAnswered -> if (isThisSelected) Amber600 else Slate100
                                isThisSelected && isCorrect -> Emerald600
                                isThisSelected && !isCorrect -> Rose600
                                isThisCorrect && !isCorrect -> Emerald100
                                else -> Slate100
                            }

                            val txtCol = when {
                                !isAnswered -> if (isThisSelected) Color.White else Slate800
                                isThisSelected -> Color.White
                                isThisCorrect && !isCorrect -> Emerald700
                                else -> Slate700
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = bgCol,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (!isAnswered) {
                                            userSelections[word.id] = opt
                                            if (opt == correctArabic) {
                                                onScoreEarned(5)
                                                audioHelper.speak(word.french)
                                            }
                                        }
                                    }
                            ) {
                                Text(
                                    text = opt,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = txtCol,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 7.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = {
                quizSeed++
                Toast.makeText(context, "تم تجديد الاختبار بكلمات جديدة!", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("توليد اختبار كلمات جديد 🔄", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

// -------------------------------------------------------------
// DATA FACTORY: جميع كلمات صفحات 51 و 52 و 53 بالتمام والكمال
// -------------------------------------------------------------
private fun getAllBanqueWords(): List<BanqueWord> {
    return listOf(
        // ==========================================
        // PAGE 51: Noms masculins (18 كلمة)
        // ==========================================
        BanqueWord("m1", "un tableau", "سبورة", WordCategory.MASCULINE, 51),
        BanqueWord("m2", "le matin", "في الصباح", WordCategory.MASCULINE, 51),
        BanqueWord("m3", "le soir", "في المساء", WordCategory.MASCULINE, 51),
        BanqueWord("m4", "un téléphone", "تليفون", WordCategory.MASCULINE, 51),
        BanqueWord("m5", "un hôtel", "فندق", WordCategory.MASCULINE, 51),
        BanqueWord("m6", "un café", "قهوة (كافيه)", WordCategory.MASCULINE, 51),
        BanqueWord("m7", "un an", "عام", WordCategory.MASCULINE, 51),
        BanqueWord("m8", "un uniforme", "زي موحد", WordCategory.MASCULINE, 51),
        BanqueWord("m9", "un devoir", "واجب", WordCategory.MASCULINE, 51),
        BanqueWord("m10", "un réfrigérateur", "ثلاجة", WordCategory.MASCULINE, 51),
        BanqueWord("m11", "un micro", "ميكرفون (مكبر صوت)", WordCategory.MASCULINE, 51),
        BanqueWord("m12", "un lavabo", "حوض", WordCategory.MASCULINE, 51),
        BanqueWord("m13", "l'après-midi", "بعد الظهر", WordCategory.MASCULINE, 51),
        BanqueWord("m14", "un oreiller", "وسادة / مخدة", WordCategory.MASCULINE, 51),
        BanqueWord("m15", "un timbre", "طابع بريد", WordCategory.MASCULINE, 51),
        BanqueWord("m16", "un rendez-vous", "ميعاد", WordCategory.MASCULINE, 51),
        BanqueWord("m17", "un âge", "عمر", WordCategory.MASCULINE, 51),
        BanqueWord("m18", "un drapeau", "علم", WordCategory.MASCULINE, 51),

        // ==========================================
        // PAGE 51: Noms féminins (15 كلمة)
        // ==========================================
        BanqueWord("f1", "une table", "منضدة", WordCategory.FEMININE, 51),
        BanqueWord("f2", "une nappe", "مفرش", WordCategory.FEMININE, 51),
        BanqueWord("f3", "une tasse", "فنجان", WordCategory.FEMININE, 51),
        BanqueWord("f4", "une assiette", "طبق", WordCategory.FEMININE, 51),
        BanqueWord("f5", "une carafe d'eau", "دورق مياه", WordCategory.FEMININE, 51),
        BanqueWord("f6", "une cuillère", "ملعقة", WordCategory.FEMININE, 51),
        BanqueWord("f7", "une fourchette", "شوكة", WordCategory.FEMININE, 51),
        BanqueWord("f8", "une boisson", "مشروب", WordCategory.FEMININE, 51),
        BanqueWord("f9", "une fenêtre", "شباك", WordCategory.FEMININE, 51),
        BanqueWord("f10", "l'addition", "الحساب", WordCategory.FEMININE, 51),
        BanqueWord("f11", "une commande", "طلب", WordCategory.FEMININE, 51),
        BanqueWord("f12", "la journée", "فترة نهارية", WordCategory.FEMININE, 51),
        BanqueWord("f13", "une monnaie", "عملة", WordCategory.FEMININE, 51),
        BanqueWord("f14", "une sorte", "نوع", WordCategory.FEMININE, 51),
        BanqueWord("f15", "une serviette", "فوطة", WordCategory.FEMININE, 51),

        // ==========================================
        // PAGE 52: Les aliments (24 كلمة)
        // ==========================================
        BanqueWord("a1", "viande d'agneau", "لحم ضأن", WordCategory.FOOD, 52),
        BanqueWord("a2", "viande de veau", "لحمة بتلو", WordCategory.FOOD, 52),
        BanqueWord("a3", "viande de boeuf", "لحمة بقرى", WordCategory.FOOD, 52),
        BanqueWord("a4", "un poulet", "فرخة", WordCategory.FOOD, 52),
        BanqueWord("a5", "un canard", "بطة", WordCategory.FOOD, 52),
        BanqueWord("a6", "une dinde", "فرخة رومى", WordCategory.FOOD, 52),
        BanqueWord("a7", "du poisson", "سمك", WordCategory.FOOD, 52),
        BanqueWord("a8", "des frites", "بطاطس مقلية", WordCategory.FOOD, 52),
        BanqueWord("a9", "des poires", "كمثرى", WordCategory.FOOD, 52),
        BanqueWord("a10", "des fraises", "فراولة", WordCategory.FOOD, 52),
        BanqueWord("a11", "un steak", "بفتيك", WordCategory.FOOD, 52),
        BanqueWord("a12", "un dindon", "ديك رومى", WordCategory.FOOD, 52),
        BanqueWord("a13", "des oeufs", "بيض", WordCategory.FOOD, 52),
        BanqueWord("a14", "des pâtes", "معجنات (مكرونة)", WordCategory.FOOD, 52),
        BanqueWord("a15", "du riz", "الأرز", WordCategory.FOOD, 52),
        BanqueWord("a16", "du fromage", "الجبنة", WordCategory.FOOD, 52),
        BanqueWord("a17", "des haricots verts", "فاصوليا خضراء", WordCategory.FOOD, 52),
        BanqueWord("a18", "des petits pois", "بسلة", WordCategory.FOOD, 52),
        BanqueWord("a19", "la salade-du laitue", "خسة", WordCategory.FOOD, 52),
        BanqueWord("a20", "la purée", "بطاطس مهروسة", WordCategory.FOOD, 52),
        BanqueWord("a21", "un crème caramel", "كريم كراميل", WordCategory.FOOD, 52),
        BanqueWord("a22", "des pommes", "تفاح", WordCategory.FOOD, 52),
        BanqueWord("a23", "des bananes", "موز", WordCategory.FOOD, 52),
        BanqueWord("a24", "un pâté", "فطيرة محشوة (باتيه)", WordCategory.FOOD, 52),

        // ==========================================
        // PAGE 53 (Top): Repas & Aliments (6 كلمات)
        // ==========================================
        BanqueWord("a25", "des légumes", "خضروات", WordCategory.FOOD, 53),
        BanqueWord("a26", "de la volaille", "لحم طيور", WordCategory.FOOD, 53),
        BanqueWord("a27", "de l'huile", "زيت", WordCategory.FOOD, 53),
        BanqueWord("a28", "des fruits", "فواكه", WordCategory.FOOD, 53),
        BanqueWord("a29", "de la viande", "لحمة", WordCategory.FOOD, 53, "وردت في الكتاب de la vinade"),
        BanqueWord("a30", "des crudités", "خضروات غير مطهية", WordCategory.FOOD, 53),

        // ==========================================
        // PAGE 53: Verbes (16 فعل)
        // ==========================================
        BanqueWord("v1", "préparer", "يعد / يجهز", WordCategory.VERBS, 53),
        BanqueWord("v2", "mettre", "يضع / يرتدى", WordCategory.VERBS, 53),
        BanqueWord("v3", "noter", "يدون / يسجل", WordCategory.VERBS, 53),
        BanqueWord("v4", "chercher", "يبحث عن", WordCategory.VERBS, 53),
        BanqueWord("v5", "aider", "يساعد", WordCategory.VERBS, 53),
        BanqueWord("v6", "laisser", "يترك", WordCategory.VERBS, 53),
        BanqueWord("v7", "commander", "يطلب", WordCategory.VERBS, 53),
        BanqueWord("v8", "manger", "يأكل", WordCategory.VERBS, 53),
        BanqueWord("v9", "prendre", "يتناول / يأخذ", WordCategory.VERBS, 53),
        BanqueWord("v10", "commencer", "يبدأ", WordCategory.VERBS, 53),
        BanqueWord("v11", "conseiller", "ينصح", WordCategory.VERBS, 53),
        BanqueWord("v12", "manquer", "ينقص", WordCategory.VERBS, 53),
        BanqueWord("v13", "proposer", "يقترح", WordCategory.VERBS, 53),
        BanqueWord("v14", "réserver", "يحجز", WordCategory.VERBS, 53),
        BanqueWord("v15", "payer", "يدفع", WordCategory.VERBS, 53),
        BanqueWord("v16", "servir", "يخدم", WordCategory.VERBS, 53)
    )
}
