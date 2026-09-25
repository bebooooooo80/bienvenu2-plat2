package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.BookletLieuItem
import com.example.data.BookletLieuxEtExercicesData
import com.example.data.BookletPersonnageItem
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Amber300
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber800
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Teal100
import com.example.ui.theme.Teal50
import com.example.ui.theme.Teal600
import com.example.ui.theme.Teal700
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

/**
 * قسم الشخصيات والأماكن ص 25 من كتيّب منهج Bienvenu 2
 * Les lieux et Les personnages (p. 25)
 */
@Composable
fun Unit1LieuxPersonnagesSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val personnages = BookletLieuxEtExercicesData.personnagesList
    val lieux = BookletLieuxEtExercicesData.lieuxList

    var mainViewMode by remember { mutableIntStateOf(0) } // 0: جدول متكامل (شخصيات وأماكن), 1: الشخصيات (23), 2: الأماكن وحروف الجر (22)
    var searchQuery by remember { mutableStateOf("") }
    var selectedPrepositionFilter by remember { mutableStateOf("Tous") }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

        // 1. بطاقة الهيدر الرسمية لصفحة 25
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Les lieux et Les personnages",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                            AudioPlayButton(
                                textToSpeak = "Les lieux et les personnages",
                                audioHelper = audioHelper,
                                size = 32
                            )
                        }
                        Text(
                            text = "كتيّب ص 25 • جدول الشخصيات والمهن والأماكن مع حروف الجر المقترنة بها",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo700
                        )
                    }

                    Surface(
                        color = Indigo700,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "ص 25",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                softWrap = false,
                                maxLines = 1
                            )
                            Text(
                                text = "p. 25",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo100,
                                softWrap = false,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // تبديل نمط العرض
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val modes = listOf(
                        "📋 جدول كتيّب ص 25",
                        "👥 الشخصيات (23)",
                        "🏢 الأماكن وحروف الجر"
                    )
                    modes.forEachIndexed { idx, title ->
                        val isSelected = mainViewMode == idx
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Indigo700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Indigo700 else Slate200),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    audioHelper.playClick()
                                    mainViewMode = idx
                                }
                        ) {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = if (isSelected) Color.White else Slate700,
                                textAlign = TextAlign.Center,
                                softWrap = false,
                                maxLines = 1,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        when (mainViewMode) {
            0 -> {
                // العرض المقارن المطابق للكتيّب ص 25 (عمود الشخصيات وعمود الأماكن)
                BookletTwoColumnView(
                    personnages = personnages,
                    lieux = lieux,
                    audioHelper = audioHelper
                )
            }
            1 -> {
                // عرض الشخصيات بالتفصيل (23 شخصية مع الجمل والأماكن المرتبطة)
                PersonnagesDetailView(
                    personnages = personnages,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    audioHelper = audioHelper
                )
            }
            2 -> {
                // عرض الأماكن وقواعد حروف الجر (au / à l' / à la / dans / en)
                LieuxPrepositionsDetailView(
                    lieux = lieux,
                    selectedPrepositionFilter = selectedPrepositionFilter,
                    onSelectPreposition = { selectedPrepositionFilter = it },
                    audioHelper = audioHelper
                )
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// 1. العرض المقارن لكتيّب ص 25 (عمود الشخصيات وعمود الأماكن)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BookletTwoColumnView(
    personnages: List<BookletPersonnageItem>,
    lieux: List<BookletLieuItem>,
    audioHelper: AudioHelper
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // بطاقة القاعدة النحوية لحروف الجر مع الأماكن
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.dp, Indigo100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Indigo700,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "💡 قاعدة حروف الجر مع الأماكن في كتيّب ص 25:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                }
                Text(
                    text = "• (au) : للمفرد المذكر الساكن مثل (au restaurant, au café, au cinéma, au stade, au club).\n• (à l') : للمفرد المبدوء بمتحرك أو h صامتة مثل (à l'école, à l'hôtel, à l'hôpital, à l'aéroport).\n• (à la) : للمفرد المؤنث الساكن مثل (à la gare, à la pharmacie, à la poste, à la maison).\n• (dans la) : مع الشارع (dans la rue) • (en) : مع الفصل (en classe).",
                    fontSize = 11.5.sp,
                    color = Slate800,
                    lineHeight = 17.sp
                )
            }
        }

        // جدول مطابق للكتيّب (رأس الجدول)
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Indigo900,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Les personnages (الشخصيات)",
                    color = Color.White,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Le Lieux (الأماكن)",
                    color = Color.White,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // صفوف الجدول المتقابلة
        val maxRows = maxOf(personnages.size, lieux.size)
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            for (i in 0 until maxRows) {
                val perso = personnages.getOrNull(i)
                val lieu = lieux.getOrNull(i)

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (i % 2 == 0) Color.White else Slate50,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // الشخصية
                        if (perso != null) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { audioHelper.speak(perso.french) },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(perso.emoji, fontSize = 15.sp)
                                Column {
                                    Text(
                                        text = perso.french,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Indigo900
                                    )
                                    Text(
                                        text = perso.arabic,
                                        fontSize = 10.5.sp,
                                        color = Slate600
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        // فاصل عمودي
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(32.dp)
                                .padding(horizontal = 4.dp)
                        )

                        // المكان المقابل
                        if (lieu != null) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { audioHelper.speak(lieu.frenchWithPreposition) },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = lieu.frenchWithPreposition,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Black,
                                        color = when (lieu.preposition) {
                                            "au" -> Indigo700
                                            "à l'" -> Teal700
                                            "à la" -> Violet700
                                            else -> Amber800
                                        }
                                    )
                                    Text(
                                        text = lieu.arabic,
                                        fontSize = 10.5.sp,
                                        color = Slate600
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(lieu.emoji, fontSize = 15.sp)
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// 2. عرض الشخصيات المفصل (23 شخصية)
// -------------------------------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PersonnagesDetailView(
    personnages: List<BookletPersonnageItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    audioHelper: AudioHelper
) {
    val filteredList = remember(searchQuery, personnages) {
        if (searchQuery.isBlank()) personnages
        else {
            personnages.filter {
                it.french.contains(searchQuery, ignoreCase = true) ||
                        it.arabic.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        // شريط البحث
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("ابحث عن شخصية بالفرنسية أو العربية...", fontSize = 12.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Indigo600)
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Indigo600,
                unfocusedBorderColor = Slate300
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "عدد الشخصيات: ${filteredList.size} من أصل ${personnages.size}",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = Slate600
        )

        // بطاقات الشخصيات
        filteredList.forEach { perso ->
            var showSentence by remember { mutableStateOf(false) }

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(perso.emoji, fontSize = 24.sp)
                            Column {
                                Text(
                                    text = perso.french,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Indigo900
                                )
                                Text(
                                    text = perso.arabic,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Teal700
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            AudioPlayButton(
                                textToSpeak = perso.french,
                                audioHelper = audioHelper,
                                size = 30
                            )
                            TranslateIconButton(
                                isTranslated = showSentence,
                                onClick = { showSentence = !showSentence },
                                size = 30
                            )
                        }
                    }

                    // الأماكن النموذجية التي يتواجد بها
                    if (perso.typicalPlaces.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "📍 المكان المعتاد:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate500
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                perso.typicalPlaces.forEach { place ->
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Indigo50,
                                        border = BorderStroke(1.dp, Indigo100),
                                        modifier = Modifier.clickable { audioHelper.speak(place) }
                                    ) {
                                        Text(
                                            text = place,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Indigo700,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // مثال الجملة النموذجي
                    AnimatedVisibility(visible = showSentence) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Slate50,
                            border = BorderStroke(1.dp, Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "💬 ${perso.exampleSentenceFr}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate800,
                                    fontFamily = FontFamily.SansSerif
                                )
                                Text(
                                    text = perso.exampleSentenceAr,
                                    fontSize = 11.5.sp,
                                    color = Slate600
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// 3. عرض الأماكن وقواعد حروف الجر
// -------------------------------------------------------------------------------------------------
@Composable
private fun LieuxPrepositionsDetailView(
    lieux: List<BookletLieuItem>,
    selectedPrepositionFilter: String,
    onSelectPreposition: (String) -> Unit,
    audioHelper: AudioHelper
) {
    val filterOptions = listOf("Tous", "au", "à l'", "à la", "dans la", "en")

    val filteredLieux = remember(selectedPrepositionFilter, lieux) {
        if (selectedPrepositionFilter == "Tous") lieux
        else lieux.filter { it.preposition == selectedPrepositionFilter }
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        // شريط تصفية حروف الجر
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filterOptions.forEach { prep ->
                val isSelected = selectedPrepositionFilter == prep
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) Indigo700 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Indigo700 else Slate200),
                    modifier = Modifier.clickable {
                        audioHelper.playClick()
                        onSelectPreposition(prep)
                    }
                ) {
                    Text(
                        text = when (prep) {
                            "Tous" -> "كل الأماكن (${lieux.size})"
                            "au" -> "au (مفرد مذكر)"
                            "à l'" -> "à l' (متحرك/h)"
                            "à la" -> "à la (مفرد مؤنث)"
                            "dans la" -> "dans la"
                            "en" -> "en (classe)"
                            else -> prep
                        },
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color.White else Slate700,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // بطاقات الأماكن
        filteredLieux.forEach { lieu ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = when (lieu.preposition) {
                                "au" -> Indigo50
                                "à l'" -> Teal50
                                "à la" -> Amber50
                                else -> Slate100
                            },
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(lieu.emoji, fontSize = 20.sp)
                            }
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = lieu.frenchWithPreposition,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Indigo900
                                )
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = when (lieu.preposition) {
                                        "au" -> Indigo700
                                        "à l'" -> Teal700
                                        "à la" -> Violet700
                                        else -> Amber800
                                    }
                                ) {
                                    Text(
                                        text = lieu.preposition,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = "${lieu.arabic} • ${lieu.explanationAr}",
                                fontSize = 11.5.sp,
                                color = Slate600
                            )
                        }
                    }

                    AudioPlayButton(
                        textToSpeak = lieu.frenchWithPreposition,
                        audioHelper = audioHelper,
                        size = 32
                    )
                }
            }
        }
    }
}
