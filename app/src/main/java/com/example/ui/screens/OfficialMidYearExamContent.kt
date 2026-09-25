package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.MidYearExamData
import com.example.ui.components.ArabicTranslationBanner
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber950
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Emerald900
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo200
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo800
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange600
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose500
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose700
import com.example.ui.theme.Rose800
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Violet600
import com.example.ui.theme.Violet700

/**
 * Complete Official Mid-Year French Exam Composable
 * Pages 78-79 - 2ème Préparatoire - Bienvenu 2
 * Total: 20/20 Marks
 */
@Composable
fun OfficialMidYearExamContent(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Exam Mode: false = Mode Entraînement (Practice with instant feedback), true = Mode Examen Réel (Simulate real exam)
    var isExamMode by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }
    var showAllReveals by remember { mutableStateOf(false) }
    var showDocTranslation by remember { mutableStateOf(false) }

    // User selections state
    // Section 1:
    val docPartAAnswers = remember { mutableStateMapOf<String, Int>() } // doc_q1 -> optionIndex
    val docPartBAnswers = remember { mutableStateMapOf<String, Boolean>() } // doc_tf1 -> isTrue
    val docPartCAnswers = remember { mutableStateMapOf<String, String>() } // doc_c1 -> selected full choice or revealed
    val docPartCRevealed = remember { mutableStateMapOf<String, Boolean>() }

    // Section 2:
    val gramPartAAnswers = remember { mutableStateMapOf<String, String>() } // gram_a1 -> "parlons"
    val gramPartBAnswers = remember { mutableStateMapOf<String, Int>() } // gram_b1..b5 -> optionIndex
    val gramPartCAnswers = remember { mutableStateMapOf<String, Int>() } // gram_c1, c2 -> optionIndex

    // Section 3:
    val prodAnswers = remember { mutableStateMapOf<String, Int>() } // prod_1, prod_2 -> optionIndex

    // Section 4:
    val sitAnswers = remember { mutableStateMapOf<String, Int>() } // sit_1, sit_2 -> optionIndex

    // Score calculations
    var calculatedScore by remember { mutableFloatStateOf(0f) }

    fun calculateTotal(): Float {
        var total = 0f
        // Sec 1 A (3 pts)
        MidYearExamData.docPartAQuestions.forEach { q ->
            if (docPartAAnswers[q.id] == q.correctIndex) total += q.points
        }
        // Sec 1 B (3 pts)
        MidYearExamData.docPartBQuestions.forEach { q ->
            if (docPartBAnswers[q.id] == q.isTrue) total += q.points
        }
        // Sec 1 C (2 pts)
        MidYearExamData.docPartCQuestions.forEach { q ->
            if (docPartCAnswers[q.id] == q.blanksWords.joinToString(" - ")) total += q.points
        }
        // Sec 2 A (1 pt)
        MidYearExamData.gramPartAQuestions.forEach { q ->
            if (gramPartAAnswers[q.id] == q.correctAnswer) total += q.points
        }
        // Sec 2 B (6 pts)
        MidYearExamData.gramPartBQuestions.forEach { q ->
            when (q) {
                is com.example.data.ExamMCQQuestion -> {
                    if (gramPartBAnswers[q.id] == q.correctIndex) total += q.points
                }
                is com.example.data.ExamTransformQuestion -> {
                    if (gramPartBAnswers[q.id] == 0) total += q.points
                }
            }
        }
        // Sec 2 C (2 pts)
        MidYearExamData.gramPartCQuestions.forEach { q ->
            if (gramPartCAnswers[q.id] == 0) total += q.points
        }
        // Sec 3 (1 pt)
        MidYearExamData.productionQuestions.forEach { q ->
            if (prodAnswers[q.id] == q.correctIndex) total += q.points
        }
        // Sec 4 (2 pts)
        MidYearExamData.situationsQuestions.forEach { q ->
            if (sitAnswers[q.id] == q.correctIndex) total += q.points
        }
        return total
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // =========================================================================
            // OFFICIAL EXAM HEADER CARD
            // =========================================================================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(Indigo800, Violet700, Orange600)
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text("📚", fontSize = 14.sp)
                                    Text(
                                        text = "منهج Bienvenu 2",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Official Grade Box
                            Surface(
                                color = Amber400,
                                shape = RoundedCornerShape(12.dp),
                                shadowElevation = 2.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Note : ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900)
                                    Text(
                                        text = if (isSubmitted) "${"%.1f".format(calculatedScore)} / 20" else "-- / 20",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Slate900
                                    )
                                }
                            }
                        }

                        Column {
                            Text(
                                text = "Examen de Mi-année • امتحان نصف العام",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "2ème Préparatoire • Français (Pages 78-79)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Amber400
                            )
                            Text(
                                text = "الامتحان الرسمي المعتمد شاملاً الوثيقة، القواعد، الإنتاج اللغوي والمواقف مع التصحيح المعتمد.",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 16.sp
                            )
                        }

                        // Mode Selector Switch
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color.Black.copy(alpha = 0.25f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(4.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (!isExamMode) Color.White else Color.Transparent,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            audioHelper.playClick()
                                            isExamMode = false
                                        }
                                ) {
                                    Text(
                                        text = "💡 وضع التدريب والمساعدة",
                                        fontSize = 11.sp,
                                        fontWeight = if (!isExamMode) FontWeight.Black else FontWeight.Normal,
                                        color = if (!isExamMode) Indigo900 else Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isExamMode) Color.White else Color.Transparent,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            audioHelper.playClick()
                                            isExamMode = true
                                        }
                                ) {
                                    Text(
                                        text = "🎯 وضع الاختبار الرسمي",
                                        fontSize = 11.sp,
                                        fontWeight = if (isExamMode) FontWeight.Black else FontWeight.Normal,
                                        color = if (isExamMode) Indigo900 else Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // SECTION 1: COMPRÉHENSION DU TEXTE (8 Points) - Page 78
            // =========================================================================
            SectionHeaderBadge(
                number = "(1)",
                titleFr = "Lis le document puis réponds aux questions",
                titleAr = "اقرأ الوثيقة التالية ثم أجب عن الأسئلة",
                pointsTag = "8 Points",
                color = Indigo700
            )

            // Document Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, Indigo100)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("🍽️", fontSize = 20.sp)
                            Text(
                                text = MidYearExamData.documentTitleFr,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Indigo900
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            TranslateIconButton(
                                isTranslated = showDocTranslation,
                                onClick = {
                                    audioHelper.playClick()
                                    showDocTranslation = !showDocTranslation
                                },
                                contentDescription = "ترجمة نص الوثيقة للعربية"
                            )
                            AudioPlayButton(
                                textToSpeak = MidYearExamData.documentFullTextFr,
                                audioHelper = audioHelper,
                                backgroundColor = Indigo50,
                                iconTint = Indigo700
                            )
                        }
                    }

                    ArabicTranslationBanner(
                        translation = MidYearExamData.documentFullTextAr,
                        visible = showDocTranslation
                    )

                    // Dialogue Lines
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Indigo50.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, Indigo100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MidYearExamData.documentLines.forEach { line ->
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { audioHelper.speak(line.textFr) }
                                ) {
                                    val isWaiter = line.speaker == "Le garçon"
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isWaiter) Indigo600 else Amber500,
                                        modifier = Modifier.padding(top = 2.dp)
                                    ) {
                                        Text(
                                            text = if (isWaiter) "🤵 ${line.speaker}" else "👤 ${line.speaker}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = line.textFr,
                                            fontSize = 12.sp,
                                            fontWeight = if (isWaiter) FontWeight.Normal else FontWeight.Bold,
                                            color = Slate800,
                                            lineHeight = 17.sp
                                        )
                                        if (showDocTranslation) {
                                            Text(
                                                text = line.textAr,
                                                fontSize = 10.sp,
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

            // Part A) Choisis le bon groupe (3 Points)
            SubSectionTitle(title = "A) Choisis le bon groupe :", points = "3 Points")
            MidYearExamData.docPartAQuestions.forEachIndexed { qIdx, question ->
                MCQQuestionCard(
                    index = qIdx + 1,
                    questionFr = question.questionFr,
                    questionAr = question.questionAr,
                    options = question.options,
                    correctIndex = question.correctIndex,
                    selectedIndex = docPartAAnswers[question.id],
                    isSubmitted = isSubmitted,
                    isExamMode = isExamMode,
                    showReveal = showAllReveals,
                    explanationFr = question.explanationFr,
                    explanationAr = question.explanationAr,
                    points = question.points,
                    audioHelper = audioHelper,
                    onSelect = { optIdx ->
                        audioHelper.playClick()
                        docPartAAnswers[question.id] = optIdx
                    }
                )
            }

            // Part B) Mets (√) ou (×) (3 Points)
            SubSectionTitle(title = "B) Mets (√) ou (×) :", points = "3 Points")
            MidYearExamData.docPartBQuestions.forEachIndexed { qIdx, question ->
                TrueFalseQuestionCard(
                    index = qIdx + 1,
                    statementFr = question.statementFr,
                    statementAr = question.statementAr,
                    isTrue = question.isTrue,
                    selected = docPartBAnswers[question.id],
                    isSubmitted = isSubmitted,
                    isExamMode = isExamMode,
                    showReveal = showAllReveals,
                    explanationFr = question.explanationFr,
                    explanationAr = question.explanationAr,
                    points = question.points,
                    audioHelper = audioHelper,
                    onSelect = { chosen ->
                        audioHelper.playClick()
                        docPartBAnswers[question.id] = chosen
                    }
                )
            }

            // Part C) Complète (2 Points)
            SubSectionTitle(title = "C) Complète :", points = "2 Points")
            MidYearExamData.docPartCQuestions.forEachIndexed { qIdx, question ->
                CompleteQuestionCard(
                    index = qIdx + 1,
                    templateFr = question.sentenceTemplateFr,
                    sentenceAr = question.sentenceAr,
                    blanksWords = question.blanksWords,
                    fullSentenceFr = question.fullSentenceFr,
                    wordBank = question.wordBank,
                    selectedWords = docPartCAnswers[question.id],
                    isRevealed = docPartCRevealed[question.id] == true || showAllReveals || isSubmitted,
                    isExamMode = isExamMode,
                    audioHelper = audioHelper,
                    onAnswerSelected = { ans ->
                        audioHelper.playClick()
                        docPartCAnswers[question.id] = ans
                    },
                    onToggleReveal = {
                        audioHelper.playClick()
                        docPartCRevealed[question.id] = !(docPartCRevealed[question.id] ?: false)
                    }
                )
            }

            // =========================================================================
            // SECTION 2: GRAMMAIRE (9 Points) - Page 79
            // =========================================================================
            SectionHeaderBadge(
                number = "(2)",
                titleFr = "Grammaire",
                titleAr = "قواعد النحو وتراكيب الجمل",
                pointsTag = "9 Points",
                color = Violet700
            )

            // Part A) Mets les verbes au présent (1 Point)
            SubSectionTitle(title = "A) Mets les verbes au présent :", points = "1 Point")
            MidYearExamData.gramPartAQuestions.forEachIndexed { qIdx, question ->
                ConjugateQuestionCard(
                    index = qIdx + 1,
                    promptFr = question.promptFr,
                    correctAnswer = question.correctAnswer,
                    selected = gramPartAAnswers[question.id],
                    ruleExplanationAr = question.ruleExplanationAr,
                    isSubmitted = isSubmitted,
                    isExamMode = isExamMode,
                    showReveal = showAllReveals,
                    audioHelper = audioHelper,
                    onSelect = { ans ->
                        audioHelper.playClick()
                        gramPartAAnswers[question.id] = ans
                    }
                )
            }

            // Part B) Fais comme indiqués entre parenthèses (6 Points)
            SubSectionTitle(title = "B) Fais comme indiqués entre parenthèses :", points = "6 Points")
            MidYearExamData.gramPartBQuestions.forEachIndexed { qIdx, question ->
                when (question) {
                    is com.example.data.ExamMCQQuestion -> {
                        MCQQuestionCard(
                            index = qIdx + 1,
                            questionFr = question.questionFr,
                            questionAr = question.questionAr,
                            options = question.options,
                            correctIndex = question.correctIndex,
                            selectedIndex = gramPartBAnswers[question.id],
                            isSubmitted = isSubmitted,
                            isExamMode = isExamMode,
                            showReveal = showAllReveals,
                            explanationFr = question.explanationFr,
                            explanationAr = question.explanationAr,
                            points = question.points,
                            audioHelper = audioHelper,
                            onSelect = { optIdx ->
                                audioHelper.playClick()
                                gramPartBAnswers[question.id] = optIdx
                            }
                        )
                    }
                    is com.example.data.ExamTransformQuestion -> {
                        TransformQuestionCard(
                            index = qIdx + 1,
                            originalFr = question.originalFr,
                            instructionFr = question.instructionFr,
                            instructionAr = question.instructionAr,
                            options = question.options,
                            expectedAnswerFr = question.expectedAnswerFr,
                            selectedIndex = gramPartBAnswers[question.id],
                            isSubmitted = isSubmitted,
                            isExamMode = isExamMode,
                            showReveal = showAllReveals,
                            explanationAr = question.explanationAr,
                            audioHelper = audioHelper,
                            onSelect = { optIdx ->
                                audioHelper.playClick()
                                gramPartBAnswers[question.id] = optIdx
                            }
                        )
                    }
                }
            }

            // Part C) Remplace les mots soulignés par un pronom personnel (2 Points)
            SubSectionTitle(title = "C) Remplace les mots soulignés par un pronom personnel :", points = "2 Points")
            MidYearExamData.gramPartCQuestions.forEachIndexed { qIdx, question ->
                TransformQuestionCard(
                    index = qIdx + 1,
                    originalFr = question.originalFr,
                    instructionFr = question.instructionFr,
                    instructionAr = question.instructionAr,
                    targetWord = question.targetWord,
                    options = question.options,
                    expectedAnswerFr = question.expectedAnswerFr,
                    selectedIndex = gramPartCAnswers[question.id],
                    isSubmitted = isSubmitted,
                    isExamMode = isExamMode,
                    showReveal = showAllReveals,
                    explanationAr = question.explanationAr,
                    audioHelper = audioHelper,
                    onSelect = { optIdx ->
                        audioHelper.playClick()
                        gramPartCAnswers[question.id] = optIdx
                    }
                )
            }

            // =========================================================================
            // SECTION 3: PRODUCTION ÉCRITE (1 Point) - Page 79
            // =========================================================================
            SectionHeaderBadge(
                number = "(3)",
                titleFr = "Production : Où vas-tu pour.... ? (au club - à l'hôpital - au musée)",
                titleAr = "الإنتاج اللغوي: إلى أين تذهب لكي....؟",
                pointsTag = "1 Point",
                color = Amber800
            )

            MidYearExamData.productionQuestions.forEachIndexed { qIdx, question ->
                MCQQuestionCard(
                    index = qIdx + 1,
                    questionFr = question.purposeFr,
                    questionAr = question.purposeAr,
                    options = question.options,
                    correctIndex = question.correctIndex,
                    selectedIndex = prodAnswers[question.id],
                    isSubmitted = isSubmitted,
                    isExamMode = isExamMode,
                    showReveal = showAllReveals,
                    explanationFr = question.modelSentenceFr,
                    explanationAr = "الإجابة النموذجية للجملة: ${question.modelSentenceFr}",
                    points = question.points,
                    audioHelper = audioHelper,
                    onSelect = { optIdx ->
                        audioHelper.playClick()
                        prodAnswers[question.id] = optIdx
                    }
                )
            }

            // =========================================================================
            // SECTION 4: SITUATIONS (2 Points) - Page 79
            // =========================================================================
            SectionHeaderBadge(
                number = "(4)",
                titleFr = "Situations",
                titleAr = "المواقف اليومية في المطعم",
                pointsTag = "2 Points",
                color = Emerald700
            )

            MidYearExamData.situationsQuestions.forEachIndexed { qIdx, question ->
                MCQQuestionCard(
                    index = qIdx + 1,
                    questionFr = question.situationFr,
                    questionAr = question.situationAr,
                    options = question.options,
                    correctIndex = question.correctIndex,
                    selectedIndex = sitAnswers[question.id],
                    isSubmitted = isSubmitted,
                    isExamMode = isExamMode,
                    showReveal = showAllReveals,
                    explanationFr = question.options[question.correctIndex],
                    explanationAr = question.explanationAr,
                    points = question.points,
                    audioHelper = audioHelper,
                    onSelect = { optIdx ->
                        audioHelper.playClick()
                        sitAnswers[question.id] = optIdx
                    }
                )
            }

            // =========================================================================
            // VALIDATION, GRADING & SUBMISSION CONTROLS
            // =========================================================================
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, Indigo200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                audioHelper.playClick()
                                docPartAAnswers.clear()
                                docPartBAnswers.clear()
                                docPartCAnswers.clear()
                                docPartCRevealed.clear()
                                gramPartAAnswers.clear()
                                gramPartBAnswers.clear()
                                gramPartCAnswers.clear()
                                prodAnswers.clear()
                                sitAnswers.clear()
                                isSubmitted = false
                                showConfetti = false
                                showAllReveals = false
                                calculatedScore = 0f
                            },
                            modifier = Modifier
                                .weight(1f)
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("reset_official_exam_button"),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("إعادة الاختبار", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                audioHelper.playClick()
                                val score = calculateTotal()
                                calculatedScore = score
                                isSubmitted = true

                                if (score >= 16f) {
                                    audioHelper.speak("Félicitations ! Tu as obtenu ${"%.1f".format(score)} sur 20. Excellent travail !")
                                    showConfetti = true
                                    onScoreEarned(20)
                                    Toast.makeText(context, "درجة ممتازة! ما شاء الله 🏆 (${"%.1f".format(score)}/20)", Toast.LENGTH_LONG).show()
                                } else if (score >= 10f) {
                                    audioHelper.speak("Très bien ! Tu as obtenu ${"%.1f".format(score)} sur 20. Bon travail !")
                                    onScoreEarned(10)
                                    Toast.makeText(context, "أحسنت! (${"%.1f".format(score)}/20)", Toast.LENGTH_SHORT).show()
                                } else {
                                    audioHelper.speak("Révise bien tes leçons et réessaie pour avoir la note complète.")
                                    Toast.makeText(context, "راجع دروسك وحاول ثانية للحصول على 20/20!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Indigo700),
                            modifier = Modifier
                                .weight(1.3f)
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("submit_official_exam_button"),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("تصحيح الامتحان ✨", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }

                    // Reveal Answer Toggle Button
                    OutlinedButton(
                        onClick = {
                            audioHelper.playClick()
                            showAllReveals = !showAllReveals
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            if (showAllReveals) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (showAllReveals) "إخفاء نموذج الإجابة الرسمي" else "كشف نموذج الإجابة الرسمي الكامل (Corrigé)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo800
                        )
                    }

                    // Exam Result Banner
                    AnimatedVisibility(visible = isSubmitted) {
                        val score = calculatedScore
                        val isPassing = score >= 10f
                        val isHigh = score >= 16f

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isHigh) Emerald100 else if (isPassing) Amber50 else Rose100,
                            border = BorderStroke(1.5.dp, if (isHigh) Emerald500 else if (isPassing) Amber500 else Rose500),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.EmojiEvents,
                                        contentDescription = null,
                                        tint = if (isHigh) Emerald700 else if (isPassing) Amber800 else Rose700,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Text(
                                        text = "الدرجة الرسمية : ${"%.1f".format(score)} / 20",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isHigh) Emerald900 else if (isPassing) Amber950 else Rose800
                                    )
                                }

                                Text(
                                    text = when {
                                        score >= 18f -> "ممتاز متفوق (Major de promo Bienvenu 2 🌟)"
                                        score >= 15f -> "جيد جداً ومتميز (Très Bien 👏)"
                                        score >= 10f -> "ناجح ومستوى جيد (Bien 👍)"
                                        else -> "تحتاج لمراجعة القواعد والنصوص (À réviser 💪)"
                                    },
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isHigh) Emerald800 else if (isPassing) Amber950 else Rose800
                                )

                                Text(
                                    text = "Bonne Chance aux examens officiels ! • منهج Bienvenu 2",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Slate600
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        ConfettiOverlay(active = showConfetti)
    }
}

// =============================================================================
// HELPER COMPOSABLES
// =============================================================================

@Composable
private fun SectionHeaderBadge(
    number: String,
    titleFr: String,
    titleAr: String,
    pointsTag: String,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = color.copy(alpha = 0.08f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$number $titleFr",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = color
                )
                Text(
                    text = titleAr,
                    fontSize = 11.sp,
                    color = Slate700
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = color
            ) {
                Text(
                    text = pointsTag,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun SubSectionTitle(title: String, points: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Slate900
        )
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = Slate100
        ) {
            Text(
                text = points,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Slate700,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
private fun MCQQuestionCard(
    index: Int,
    questionFr: String,
    questionAr: String,
    options: List<String>,
    correctIndex: Int,
    selectedIndex: Int?,
    isSubmitted: Boolean,
    isExamMode: Boolean,
    showReveal: Boolean,
    explanationFr: String,
    explanationAr: String,
    points: Float,
    audioHelper: AudioHelper,
    onSelect: (Int) -> Unit
) {
    var showTranslation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = questionFr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = {
                            audioHelper.playClick()
                            showTranslation = !showTranslation
                        },
                        contentDescription = "ترجمة السؤال $index",
                        size = 30
                    )
                    AudioPlayButton(
                        textToSpeak = questionFr,
                        audioHelper = audioHelper,
                        size = 30
                    )
                }
            }

            ArabicTranslationBanner(
                translation = questionAr,
                visible = showTranslation
            )

            // Options
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                options.forEachIndexed { optIdx, optText ->
                    val isChosen = selectedIndex == optIdx
                    val isCorrect = optIdx == correctIndex
                    val shouldHighlightCorrect = (isSubmitted || (!isExamMode && isChosen) || showReveal) && isCorrect
                    val shouldHighlightWrong = (isSubmitted || !isExamMode) && isChosen && !isCorrect

                    val bgColor = when {
                        shouldHighlightCorrect -> Emerald100
                        shouldHighlightWrong -> Rose100
                        isChosen -> Indigo50
                        else -> Slate50
                    }

                    val borderColor = when {
                        shouldHighlightCorrect -> Emerald500
                        shouldHighlightWrong -> Rose500
                        isChosen -> Indigo600
                        else -> Slate200
                    }

                    val textColor = when {
                        shouldHighlightCorrect -> Emerald900
                        shouldHighlightWrong -> Rose800
                        isChosen -> Indigo900
                        else -> Slate800
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = bgColor,
                        border = BorderStroke(1.dp, borderColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(optIdx) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = optText,
                                fontSize = 12.sp,
                                fontWeight = if (isChosen || shouldHighlightCorrect) FontWeight.Bold else FontWeight.Normal,
                                color = textColor,
                                modifier = Modifier.weight(1f)
                            )

                            if (shouldHighlightCorrect) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "صحيح", tint = Emerald600, modifier = Modifier.size(16.dp))
                            } else if (shouldHighlightWrong) {
                                Icon(Icons.Default.Close, contentDescription = "خطأ", tint = Rose600, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            // Explanation card in practice mode or after reveal
            if ((showReveal || (!isExamMode && selectedIndex != null) || isSubmitted) && explanationAr.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Amber50,
                    border = BorderStroke(1.dp, Amber400.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Amber800, modifier = Modifier.size(15.dp))
                        Text(
                            text = explanationAr,
                            fontSize = 10.5.sp,
                            color = Amber950,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrueFalseQuestionCard(
    index: Int,
    statementFr: String,
    statementAr: String,
    isTrue: Boolean,
    selected: Boolean?,
    isSubmitted: Boolean,
    isExamMode: Boolean,
    showReveal: Boolean,
    explanationFr: String,
    explanationAr: String,
    points: Float,
    audioHelper: AudioHelper,
    onSelect: (Boolean) -> Unit
) {
    var showTranslation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = statementFr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = {
                            audioHelper.playClick()
                            showTranslation = !showTranslation
                        },
                        contentDescription = "ترجمة العبارة $index",
                        size = 30
                    )
                    AudioPlayButton(
                        textToSpeak = statementFr,
                        audioHelper = audioHelper,
                        size = 30
                    )
                }
            }

            ArabicTranslationBanner(
                translation = statementAr,
                visible = showTranslation
            )

            // True / False Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Vrai Button (√)
                val isTrueChosen = selected == true
                val isTrueCorrect = isTrue
                val showTrueSuccess = (isSubmitted || (!isExamMode && isTrueChosen) || showReveal) && isTrueCorrect
                val showTrueError = (isSubmitted || !isExamMode) && isTrueChosen && !isTrueCorrect

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = when {
                        showTrueSuccess && isTrueChosen -> Emerald100
                        showTrueError -> Rose100
                        showTrueSuccess -> Emerald50
                        isTrueChosen -> Indigo50
                        else -> Slate50
                    },
                    border = BorderStroke(
                        1.dp,
                        when {
                            showTrueSuccess -> Emerald600
                            showTrueError -> Rose600
                            isTrueChosen -> Indigo600
                            else -> Slate200
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(true) }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Vrai (√)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (showTrueSuccess) Emerald800 else Slate800)
                    }
                }

                // Faux Button (×)
                val isFalseChosen = selected == false
                val isFalseCorrect = !isTrue
                val showFalseSuccess = (isSubmitted || (!isExamMode && isFalseChosen) || showReveal) && isFalseCorrect
                val showFalseError = (isSubmitted || !isExamMode) && isFalseChosen && !isFalseCorrect

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = when {
                        showFalseSuccess && isFalseChosen -> Emerald100
                        showFalseError -> Rose100
                        showFalseSuccess -> Emerald50
                        isFalseChosen -> Indigo50
                        else -> Slate50
                    },
                    border = BorderStroke(
                        1.dp,
                        when {
                            showFalseSuccess -> Emerald600
                            showFalseError -> Rose600
                            isFalseChosen -> Indigo600
                            else -> Slate200
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(false) }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Faux (×)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (showFalseSuccess) Emerald800 else Slate800)
                    }
                }
            }

            if ((showReveal || (!isExamMode && selected != null) || isSubmitted) && explanationAr.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Amber50,
                    border = BorderStroke(1.dp, Amber400.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Amber800, modifier = Modifier.size(15.dp))
                        Text(
                            text = explanationAr,
                            fontSize = 10.5.sp,
                            color = Amber950,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CompleteQuestionCard(
    index: Int,
    templateFr: String,
    sentenceAr: String,
    blanksWords: List<String>,
    fullSentenceFr: String,
    wordBank: List<String>,
    selectedWords: String?,
    isRevealed: Boolean,
    isExamMode: Boolean,
    audioHelper: AudioHelper,
    onAnswerSelected: (String) -> Unit,
    onToggleReveal: () -> Unit
) {
    var showTranslation by remember { mutableStateOf(false) }
    val correctKey = blanksWords.joinToString(" - ")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isRevealed) fullSentenceFr else templateFr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isRevealed) Emerald800 else Slate900,
                    modifier = Modifier.weight(1f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    TranslateIconButton(
                        isTranslated = showTranslation,
                        onClick = {
                            audioHelper.playClick()
                            showTranslation = !showTranslation
                        },
                        contentDescription = "ترجمة السؤال $index",
                        size = 30
                    )
                    AudioPlayButton(
                        textToSpeak = fullSentenceFr,
                        audioHelper = audioHelper,
                        size = 30
                    )
                }
            }

            ArabicTranslationBanner(
                translation = sentenceAr,
                visible = showTranslation
            )

            // Chips to select the missing pair
            Text(
                text = "اختر الكلمات المناسبة لملء الفراغ :",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Slate600
            )

            // Multiple choice pairs for easy selection
            val pairsOptions = listOf(
                blanksWords.joinToString(" - "),
                wordBank.take(2).reversed().joinToString(" - "),
                listOf(wordBank.first(), wordBank.last()).joinToString(" - ")
            ).distinct()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                pairsOptions.forEach { pairStr ->
                    val isChosen = selectedWords == pairStr
                    val isCorrect = pairStr == correctKey

                    FilterChip(
                        selected = isChosen,
                        onClick = { onAnswerSelected(pairStr) },
                        label = {
                            Text(
                                text = pairStr,
                                fontSize = 11.sp,
                                fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if (isCorrect && isRevealed) Emerald100 else Indigo100,
                            selectedLabelColor = if (isCorrect && isRevealed) Emerald900 else Indigo900
                        )
                    )
                }
            }

            // Reveal button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (isRevealed) "الحل: $fullSentenceFr" else "اضغط هنا لكشف الحل الكامل 💡",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isRevealed) Emerald700 else Indigo600,
                    modifier = Modifier.clickable { onToggleReveal() }
                )
            }
        }
    }
}

@Composable
private fun ConjugateQuestionCard(
    index: Int,
    promptFr: String,
    correctAnswer: String,
    selected: String?,
    ruleExplanationAr: String,
    isSubmitted: Boolean,
    isExamMode: Boolean,
    showReveal: Boolean,
    audioHelper: AudioHelper,
    onSelect: (String) -> Unit
) {
    // Generate 3 believable conjugation choices
    val options = when (correctAnswer) {
        "parlons" -> listOf("parlons", "parlez", "parle")
        "choisissez" -> listOf("choisissez", "choisissons", "choisit")
        else -> listOf(correctAnswer)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = promptFr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )

                AudioPlayButton(
                    textToSpeak = promptFr.replace("....................", correctAnswer),
                    audioHelper = audioHelper,
                    size = 30
                )
            }

            // Options Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEach { opt ->
                    val isChosen = selected == opt
                    val isCorrect = opt == correctAnswer
                    val shouldHighlightCorrect = (isSubmitted || (!isExamMode && isChosen) || showReveal) && isCorrect
                    val shouldHighlightWrong = (isSubmitted || !isExamMode) && isChosen && !isCorrect

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = when {
                            shouldHighlightCorrect -> Emerald100
                            shouldHighlightWrong -> Rose100
                            isChosen -> Indigo50
                            else -> Slate50
                        },
                        border = BorderStroke(
                            1.dp,
                            when {
                                shouldHighlightCorrect -> Emerald600
                                shouldHighlightWrong -> Rose600
                                isChosen -> Indigo600
                                else -> Slate200
                            }
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelect(opt) }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = opt,
                                fontSize = 12.sp,
                                fontWeight = if (isChosen || shouldHighlightCorrect) FontWeight.Bold else FontWeight.Normal,
                                color = if (shouldHighlightCorrect) Emerald800 else Slate800
                            )
                        }
                    }
                }
            }

            if ((showReveal || (!isExamMode && selected != null) || isSubmitted) && ruleExplanationAr.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Amber50,
                    border = BorderStroke(1.dp, Amber400.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Amber800, modifier = Modifier.size(15.dp))
                        Text(
                            text = ruleExplanationAr,
                            fontSize = 10.5.sp,
                            color = Amber950,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransformQuestionCard(
    index: Int,
    originalFr: String,
    instructionFr: String,
    instructionAr: String,
    targetWord: String = "",
    options: List<String>,
    expectedAnswerFr: String,
    selectedIndex: Int?,
    isSubmitted: Boolean,
    isExamMode: Boolean,
    showReveal: Boolean,
    explanationAr: String,
    audioHelper: AudioHelper,
    onSelect: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "$index- $originalFr",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Indigo50,
                            border = BorderStroke(1.dp, Indigo200)
                        ) {
                            Text(
                                text = "[$instructionFr]",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo800,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = instructionAr,
                            fontSize = 10.sp,
                            color = Slate600
                        )
                    }
                }

                AudioPlayButton(
                    textToSpeak = expectedAnswerFr,
                    audioHelper = audioHelper,
                    size = 30
                )
            }

            // Options
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                options.forEachIndexed { optIdx, optText ->
                    val isChosen = selectedIndex == optIdx
                    val isCorrect = optIdx == 0 // first item is the expected answer in data
                    val shouldHighlightCorrect = (isSubmitted || (!isExamMode && isChosen) || showReveal) && isCorrect
                    val shouldHighlightWrong = (isSubmitted || !isExamMode) && isChosen && !isCorrect

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = when {
                            shouldHighlightCorrect -> Emerald100
                            shouldHighlightWrong -> Rose100
                            isChosen -> Indigo50
                            else -> Slate50
                        },
                        border = BorderStroke(
                            1.dp,
                            when {
                                shouldHighlightCorrect -> Emerald600
                                shouldHighlightWrong -> Rose600
                                isChosen -> Indigo600
                                else -> Slate200
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(optIdx) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = optText,
                                fontSize = 12.sp,
                                fontWeight = if (isChosen || shouldHighlightCorrect) FontWeight.Bold else FontWeight.Normal,
                                color = if (shouldHighlightCorrect) Emerald800 else Slate800,
                                modifier = Modifier.weight(1f)
                            )

                            if (shouldHighlightCorrect) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "صحيح", tint = Emerald600, modifier = Modifier.size(16.dp))
                            } else if (shouldHighlightWrong) {
                                Icon(Icons.Default.Close, contentDescription = "خطأ", tint = Rose600, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            if ((showReveal || (!isExamMode && selectedIndex != null) || isSubmitted) && explanationAr.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Amber50,
                    border = BorderStroke(1.dp, Amber400.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Amber800, modifier = Modifier.size(15.dp))
                        Text(
                            text = explanationAr,
                            fontSize = 10.5.sp,
                            color = Amber950,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}
