package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.CompositionSentence
import com.example.data.FrenchCourseData
import com.example.ui.components.AudioPlayButton
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange600
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Teal100
import com.example.ui.theme.Teal50
import com.example.ui.theme.Teal600
import com.example.ui.theme.Violet100
import com.example.ui.theme.Violet50
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

@Composable
fun Unit1CompositionSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val topic = FrenchCourseData.unit1CompositionTopic
    var selectedMode by remember { mutableIntStateOf(0) }
    val modes = listOf(
        "📝 النموذج المكتوب (ص 16)",
        "🛠️ صانع التعبير التفاعلي",
        "🧩 رتّب جمل الموضوع",
        "✍️ أكمل الفراغات",
        "💡 نصائح الدرجة النهائية"
    )

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Main Booklet Header Banner
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "✍️ Composition : Présente ta famille",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Indigo900
                        )
                        Text(
                            text = "موضوع التعبير بكتيّب منهج Bienvenu 2 (ص 16) • شروحات وتدريبات كتابية",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Violet700
                        )
                    }
                    Surface(
                        color = Violet700,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "p. 16 • كتابة",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            softWrap = false,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Mode Tabs Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    modes.forEachIndexed { index, title ->
                        val isSelected = selectedMode == index
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Violet700 else Slate100,
                            border = BorderStroke(1.dp, if (isSelected) Violet700 else Slate200),
                            modifier = Modifier.clickable { selectedMode = index }
                        ) {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate800,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Mode Content
        when (selectedMode) {
            0 -> CompositionNotebookMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            1 -> CompositionInteractiveWizardMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            2 -> CompositionSentenceOrderingMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            3 -> CompositionFillBlanksMode(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
            4 -> CompositionExamTipsMode(audioHelper = audioHelper)
        }
    }
}

// -----------------------------------------------------------------------------
// 1. 📝 النموذج المكتوب بكتيّب منهج Bienvenu 2 (ص 16) مع ورقة مسطرة ونطق تفاعلي
// -----------------------------------------------------------------------------
@Composable
private fun CompositionNotebookMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val topic = FrenchCourseData.unit1CompositionTopic
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var selectedModelIndex by remember { mutableIntStateOf(0) }
    var activeSentenceIndex by remember { mutableStateOf<Int?>(null) }

    val fullFrenchText = topic.sentences.joinToString(" ") { it.french }

    // Official Prompt Banner from page 16
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Composition",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900,
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Amber400
                ) {
                    Text(
                        text = "نص الكتيّب ص 16",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber950,
                        softWrap = false,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = "- Présente ta famille.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = Slate900
            )

            Text(
                text = "📌 المطلوب: قدّم عائلتك في موضوع تعبير متماسك مستخدماً عناصر (الاسم، السن، الأفراد، الوظائف، والمدينة).",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Violet700
            )
        }
    }

    // Two Models Selector
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val models = listOf("⭐ النموذج الشامل النموذجي (7 جمل)", "⚡ النموذج السريع السلس (5 جمل)")
        models.forEachIndexed { idx, title ->
            val isSelected = selectedModelIndex == idx
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) Violet700 else Color.White,
                border = BorderStroke(1.dp, if (isSelected) Violet700 else Slate200),
                modifier = Modifier
                    .weight(1f)
                    .clickable { selectedModelIndex = idx }
            ) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isSelected) Color.White else Slate700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                )
            }
        }
    }

    // Ruled Notebook Paper Design (Simulating Page 16 dotted lines!)
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFCF7)), // warm paper color
        border = BorderStroke(1.5.dp, Color(0xFFE2D9C8)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Paper Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "📑", fontSize = 16.sp)
                    Text(
                        text = "الموضوع مكتوباً بالفرنسية (انقر للاستماع):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate800
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Copy button
                    Surface(
                        shape = CircleShape,
                        color = Slate100,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                clipboardManager.setText(AnnotatedString(fullFrenchText))
                                Toast.makeText(context, "تم نسخ موضوع التعبير بنجاح! 📋", Toast.LENGTH_SHORT).show()
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "📋", fontSize = 14.sp)
                        }
                    }

                    // Full audio playback
                    AudioPlayButton(
                        textToSpeak = fullFrenchText,
                        audioHelper = audioHelper,
                        backgroundColor = Violet50,
                        iconTint = Violet700,
                        size = 32
                    )
                }
            }

            // Notebook Lines with Sentences
            val sentencesToShow = if (selectedModelIndex == 0) {
                topic.sentences
            } else {
                topic.sentences.filter { it.order in listOf(1, 2, 3, 4, 7) }
            }

            sentencesToShow.forEachIndexed { index, sentence ->
                val isActive = activeSentenceIndex == index

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isActive) Violet50 else Color.White,
                    border = BorderStroke(if (isActive) 1.5.dp else 1.dp, if (isActive) Violet600 else Color(0xFFE9E2D5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            activeSentenceIndex = index
                            audioHelper.speak(sentence.french)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (isActive) Violet700 else Slate200,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${index + 1}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isActive) Color.White else Slate700
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = sentence.french,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isActive) Violet700 else Slate900
                                )
                                Text(
                                    text = sentence.arabic,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate600
                                )
                            }
                        }

                        AudioPlayButton(
                            textToSpeak = sentence.french,
                            audioHelper = audioHelper,
                            backgroundColor = if (isActive) Violet100 else Slate100,
                            size = 28
                        )
                    }
                }
            }
        }
    }

    // Elements Checklist in this composition
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "🎯 العناصر التي غطاها الموضوع (مهمة في تصحيح الامتحان):",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Indigo900
            )

            topic.requiredElements.forEach { (elemName, example) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Slate50, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Emerald600, modifier = Modifier.size(16.dp))
                        Text(text = elemName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate800)
                    }
                    Text(text = example, fontSize = 11.sp, color = Indigo700, fontStyle = FontStyle.Italic)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 2. 🛠️ صانع التعبير التفاعلي (Interactive Composition Wizard)
// -----------------------------------------------------------------------------
@Composable
private fun CompositionInteractiveWizardMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var myName by remember { mutableStateOf("Ali") }
    var myAge by remember { mutableStateOf("14") }
    var familyMembersCount by remember { mutableStateOf("cinq") }
    var fatherJob by remember { mutableStateOf("médecin à l'hôpital") }
    var motherJob by remember { mutableStateOf("professeure de français") }
    var siblings by remember { mutableStateOf("un frère et une sœur") }
    var city by remember { mutableStateOf("au Caire") }

    val generatedParagraph = "Je m'appelle $myName, j'ai $myAge ans. Ma famille se compose de $familyMembersCount personnes. Mon père est $fatherJob. Ma mère est $motherJob. J'ai $siblings. Nous habitons $city. J'aime beaucoup ma famille !"

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Wizard Header
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Violet50,
            border = BorderStroke(1.dp, Violet100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "🛠️ اصنع موضوع تعبير مخصص لعائلتك الحقيقية :",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Violet700
                )
                Text(
                    text = "اختر بياناتك وأفراد عائلتك لتوليد موضوع تعبير فرنسي سليم لغوياً بنسبة 100% لتكتبه في كتيّب المعهد أو ورقة الإجابة.",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        // Field 1: Name and Age
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "1. اسمك وسنك (Ton nom et ton âge)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Indigo900)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val names = listOf("Ali", "Rami", "Gamal", "Omar", "Sara", "Nour")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        names.forEach { name ->
                            val isSel = myName == name
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) Violet700 else Slate100,
                                modifier = Modifier.clickable { myName = name }
                            ) {
                                Text(
                                    text = name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.White else Slate800,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // Age Chips
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("13", "14", "15").forEach { age ->
                        val isSel = myAge == age
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSel) Violet700 else Slate100,
                            modifier = Modifier.clickable { myAge = age }
                        ) {
                            Text(
                                text = "$age ans",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) Color.White else Slate800,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Field 2: Father's Job
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "2. مهنة الوالد (La profession du père)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Indigo900)

                val fatherJobs = listOf(
                    "médecin à l'hôpital" to "طبيب في المستشفى 🩺",
                    "professeur au lycée" to "معلم في المدرسة 👨‍🏫",
                    "ingénieur" to "مهندس 🏗️",
                    "pharmacien" to "صيدلي 💊",
                    "comptable" to "محاسب 💼"
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    fatherJobs.forEach { (jobFr, labelAr) ->
                        val isSel = fatherJob == jobFr
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSel) Emerald100 else Slate50,
                            border = BorderStroke(1.dp, if (isSel) Emerald500 else Slate200),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { fatherJob = jobFr }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Mon père est $jobFr", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900)
                                Text(text = labelAr, fontSize = 11.sp, color = Slate600)
                            }
                        }
                    }
                }
            }
        }

        // Field 3: Mother's Job
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "3. مهنة الوالدة (La profession de la mère)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Indigo900)

                val motherJobs = listOf(
                    "professeure de français" to "معلمة لغة فرنسية 👩‍🏫",
                    "maîtresse de maison" to "ربة منزل 🏠",
                    "pharmacienne" to "صيدلانية 💊",
                    "médecin" to "طبيبة 🩺",
                    "avocate" to "محامية ⚖️"
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    motherJobs.forEach { (jobFr, labelAr) ->
                        val isSel = motherJob == jobFr
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSel) Rose100 else Slate50,
                            border = BorderStroke(1.dp, if (isSel) Rose500 else Slate200),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { motherJob = jobFr }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Ma mère est $jobFr", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900)
                                Text(text = labelAr, fontSize = 11.sp, color = Slate600)
                            }
                        }
                    }
                }
            }
        }

        // Field 4: Siblings & City
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "4. الإخوة والمدينة (Frères, sœurs et ville)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Indigo900)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val siblingsList = listOf("un frère et une sœur", "deux frères", "deux sœurs", "un frère", "une sœur")
                    siblingsList.forEach { sib ->
                        val isSel = siblings == sib
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSel) Indigo600 else Slate100,
                            modifier = Modifier.clickable { siblings = sib }
                        ) {
                            Text(
                                text = "J'ai $sib",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) Color.White else Slate800,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val cities = listOf("au Caire", "à Alexandrie", "à Gizeh", "à Tanta", "à Assouan")
                    cities.forEach { c ->
                        val isSel = city == c
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSel) Orange600 else Slate100,
                            modifier = Modifier.clickable { city = c }
                        ) {
                            Text(
                                text = "Habite $c",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) Color.White else Slate800,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Live Generated Subject Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F6EE)),
            border = BorderStroke(2.dp, Amber400),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "✨ موضوعك الجاهز للاختبار ولكتيّب المعهد :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber950
                    )

                    AudioPlayButton(
                        textToSpeak = generatedParagraph,
                        audioHelper = audioHelper,
                        backgroundColor = Amber400,
                        iconTint = Amber950,
                        size = 32
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFE2D9C8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = generatedParagraph,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(generatedParagraph))
                            onScoreEarned(2)
                            Toast.makeText(context, "تم نسخ موضوعك المخصص بنجاح! 📋✨", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Violet700),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("📋 نسخ الموضوع (+2)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            audioHelper.speak(generatedParagraph)
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Violet700),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("🔊 استماع للموضوع", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Violet700)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. 🧩 ترتيب جمل موضوع التعبير (Puzzle de recomposition)
// -----------------------------------------------------------------------------
@Composable
private fun CompositionSentenceOrderingMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val topic = FrenchCourseData.unit1CompositionTopic
    val initialSentences = remember { topic.sentences.shuffled() }
    val userOrderedList = remember { mutableStateListOf<CompositionSentence>() }
    var isCompleted by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Slate50,
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "🧩 لعبة ترتيب الجمل لتكوين موضوع متناسق :",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Text(
                    text = "اضغط على الجمل بالترتيب المنطقي السليم (من 1 إلى 7) لتكوين موضوع التعبير.",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        // Ordered sentences box (Target Drop Zone)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, if (isCompleted) Emerald500 else Indigo100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ترتيبك الحالي (${userOrderedList.size}/${topic.sentences.size}) :",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )

                    if (userOrderedList.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Rose50,
                            border = BorderStroke(1.dp, Rose100),
                            modifier = Modifier.clickable {
                                userOrderedList.clear()
                                isCompleted = false
                            }
                        ) {
                            Text(
                                text = "🔄 إعادة البدء",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Rose600,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                if (userOrderedList.isEmpty()) {
                    Text(
                        text = "لم تختر أي جملة بعد.. اضغط على الجمل بالأسفل لبدء الترتيب.",
                        fontSize = 11.sp,
                        color = Slate500,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    )
                } else {
                    userOrderedList.forEachIndexed { idx, sent ->
                        val isCorrectOrder = sent.order == idx + 1
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCorrectOrder) Emerald100 else Rose100,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${idx + 1}. ${sent.french}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCorrectOrder) Emerald600 else Rose600
                                )
                                Text(
                                    text = if (isCorrectOrder) "✓" else "✗ الترتيب الصحيح هو ${sent.order}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isCorrectOrder) Emerald600 else Rose600
                                )
                            }
                        }
                    }
                }

                // Completion message
                if (userOrderedList.size == topic.sentences.size) {
                    val allCorrect = userOrderedList.mapIndexed { idx, s -> s.order == idx + 1 }.all { it }
                    if (allCorrect) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Emerald500,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "🎉 أحسنت رائع جداً! رتبت الموضوع كاملاً بالترتيب النموذجي الصحيح! (+5 نقاط)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        LaunchedEffect(Unit) {
                            onScoreEarned(5)
                        }
                    }
                }
            }
        }

        // Available Unordered Sentences
        Text(text = "👇 الجمل المتاحة للاختيار :", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate700)

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            initialSentences.forEach { sentence ->
                val isAlreadyPicked = userOrderedList.contains(sentence)
                if (!isAlreadyPicked) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                userOrderedList.add(sentence)
                                audioHelper.speak(sentence.french)
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = sentence.french, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900)
                                Text(text = sentence.arabic, fontSize = 11.sp, color = Slate600)
                            }
                            Text(text = "➕", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. ✍️ اختبار أكمل الفراغات في التعبير (Exercice à trous)
// -----------------------------------------------------------------------------
@Composable
private fun CompositionFillBlanksMode(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val topic = FrenchCourseData.unit1CompositionTopic
    val solutions = topic.fillBlankSolutions
    val userFilled = remember { mutableStateListOf<String?>().apply { repeat(solutions.size) { add(null) } } }
    val availableWordBank = remember { (topic.scrambleWords + listOf("école", "professeur")).shuffled() }

    var isChecked by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Slate50,
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "✍️ أكمل الفراغات بالكلمات المناسبة :",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )
                Text(
                    text = "ضع الكلمة المناسبة في كل فراغ لتكوين جمل التعبير السليمة.",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        }

        // The Fill Blank Paragraph with Slots
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Je m'appelle Ali. Ma famille se compose de cinq [1]. Mon père est [2] et ma mère est [3]. J'ai un [4] et une [5]. J'aime beaucoup ma [6].",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900,
                    lineHeight = 22.sp
                )

                // 6 Slots
                val slotLabels = listOf(
                    "1. عدد الأفراد:" to "personnes",
                    "2. مهنة الأب:" to "médecin",
                    "3. مهنة الأم:" to "professeure",
                    "4. أخ:" to "frère",
                    "5. أخت:" to "sœur",
                    "6. العائلة:" to "famille"
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    slotLabels.forEachIndexed { idx, (label, expected) ->
                        val currentVal = userFilled.getOrNull(idx)
                        val isCorrect = currentVal == expected

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = when {
                                isChecked && isCorrect -> Emerald100
                                isChecked && !isCorrect -> Rose100
                                currentVal != null -> Violet50
                                else -> Slate50
                            },
                            border = BorderStroke(
                                1.dp,
                                when {
                                    isChecked && isCorrect -> Emerald500
                                    isChecked && !isCorrect -> Rose500
                                    currentVal != null -> Violet600
                                    else -> Slate200
                                }
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (currentVal != null && !isChecked) {
                                        userFilled[idx] = null
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate700)
                                Text(
                                    text = currentVal ?: "--- اضغط كلمة من البنك بالأسفل ---",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (currentVal != null) Indigo900 else Slate500
                                )
                            }
                        }
                    }
                }

                // Check Button
                if (!isChecked) {
                    Button(
                        onClick = {
                            isChecked = true
                            val correctCount = userFilled.filterIndexed { i, s -> s == solutions[i] }.size
                            if (correctCount > 0) {
                                onScoreEarned(correctCount)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Violet700),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("تحقق من إجاباتي 🎯", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            isChecked = false
                            for (i in 0 until userFilled.size) userFilled[i] = null
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Violet700),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إعادة المحاولة 🔄", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Violet700)
                    }
                }
            }
        }

        // Word Bank
        if (!isChecked) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "📦 بنك الكلمات (اضغط على كلمة لملء أول فراغ شاغر) :", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Slate800)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        availableWordBank.forEach { word ->
                            val isUsed = userFilled.contains(word)
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isUsed) Slate100 else Violet50,
                                border = BorderStroke(1.dp, if (isUsed) Slate200 else Violet100),
                                modifier = Modifier.clickable(enabled = !isUsed) {
                                    val firstEmptyIdx = userFilled.indexOfFirst { it == null }
                                    if (firstEmptyIdx != -1) {
                                        userFilled[firstEmptyIdx] = word
                                        audioHelper.speak(word)
                                    }
                                }
                            ) {
                                Text(
                                    text = word,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isUsed) Slate500 else Violet700,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 5. 💡 نصائح وقواعد الدرجة النهائية في التعبير (Conseils & Règles d'or)
// -----------------------------------------------------------------------------
@Composable
private fun CompositionExamTipsMode(
    audioHelper: AudioHelper
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Essential Checklist Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Violet100)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⭐ العناصر الخمسة الإجبارية لموضوع التعبير الأزهر",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo900
                    )
                    AudioPlayButton(
                        textToSpeak = "Nom, âge, membres, profession, sentiment.",
                        audioHelper = audioHelper,
                        backgroundColor = Violet50,
                        size = 30
                    )
                }

                Text(
                    text = "في امتحانات الصف الثاني الإعدادي (منهج Bienvenu 2)، يقسم المصحح درجات التعبير على النقاط التالية، فتأكد من كتابة جملة لكل عنصر:",
                    fontSize = 12.sp,
                    color = Slate700
                )

                val tips = listOf(
                    "1. الاسم والسن" to "Je m'appelle Ali, j'ai 14 ans. (تأكد من استخدام فعل avoir مع السن وليس être).",
                    "2. أفراد الأسرة" to "Ma famille se compose de ... personnes. (لا تنسَ حرف الجر de).",
                    "3. مهنة الوالد" to "Mon père est médecin / professeur. (تذكير صفة المهنة، وبدون أداة بعد فعل être).",
                    "4. مهنة الوالدة" to "Ma mère est pharmacienne / maîtresse de maison. (مراعاة التأنيث السليم للمهنة).",
                    "5. الإخوة والخاتمة" to "J'ai un frère et une sœur. J'aime beaucoup ma famille."
                )

                tips.forEach { (title, desc) ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Slate50,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Black, color = Violet700)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = desc, fontSize = 11.sp, color = Slate800, lineHeight = 16.sp)
                        }
                    }
                }
            }
        }

        // Common Spelling Mistakes to Avoid
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Rose100)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "⚠️ أخطاء شائعة يقع فيها الطلاب في الامتحان تجنبها فوراً :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Rose600
                )

                val mistakes = listOf(
                    "❌ Je suis 14 ans" to "✅ الصح: J'ai 14 ans (العمر دائماً مع avoir).",
                    "❌ Mon père est un médecin" to "✅ الصح: Mon père est médecin (لا نضع un أمام المهنة بعد être).",
                    "❌ Ma mère est professeur" to "✅ الصح: Ma mère est professeure (تأنيث المهنة بإضافة e).",
                    "❌ J'ai un frère et un sœur" to "✅ الصح: une sœur (أخت مؤنثة تأخذ une)."
                )

                mistakes.forEach { (wrong, right) ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Rose50,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                            Text(text = wrong, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Rose600)
                            Text(text = right, fontSize = 11.sp, fontWeight = FontWeight.Black, color = Emerald600)
                        }
                    }
                }
            }
        }
    }
}
