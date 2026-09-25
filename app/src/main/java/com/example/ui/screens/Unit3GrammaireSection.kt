package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.components.TranslateIconButton
import com.example.ui.theme.Amber100
import com.example.ui.theme.Amber200
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber600
import com.example.ui.theme.Amber700
import com.example.ui.theme.Amber800
import com.example.ui.theme.Amber900
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald300
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Emerald900
import com.example.ui.theme.Indigo100
import com.example.ui.theme.Indigo200
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Indigo700
import com.example.ui.theme.Indigo900
import com.example.ui.theme.Orange100
import com.example.ui.theme.Orange50
import com.example.ui.theme.Orange600
import com.example.ui.theme.Orange700
import com.example.ui.theme.Rose100
import com.example.ui.theme.Rose200
import com.example.ui.theme.Rose50
import com.example.ui.theme.Rose600
import com.example.ui.theme.Rose700
import com.example.ui.theme.Rose800
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

data class GrammarExerciseItem(
    val id: Int,
    val exerciseNumber: Int, // 1 أو 2
    val questionFr: String,
    val questionAr: String,
    val bodyPart: String,
    val correctArticle: String, // "au", "à la", "à l'", "aux"
    val explanation: String
)

@Composable
fun Unit3GrammaireSection(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    var activeSubTab by remember { mutableIntStateOf(0) } // 0: أعضاء الجسم (ص64), 1: قاعدة التعبير عن الألم (ص65), 2: تمارين وتدريبات الكتاب (ص66)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header Card
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Teal50),
            border = BorderStroke(1.5.dp, Teal600.copy(alpha = 0.35f))
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
                        color = Teal700
                    ) {
                        Text(
                            text = "Grammaire • Unité 3",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Teal100)
                    ) {
                        Text(
                            text = "📖 كتيّب المعهد - الصفحات 64-66",
                            color = Teal700,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(
                    text = "أعضاء الجسم وقاعدة التعبير عن الألم (Le Corps & Avoir mal)",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = Teal700
                )

                Text(
                    text = "شرح كامل لأعضاء الجسم (Le Corps) ص 64، وقاعدة أدوات المعرفة المدغمة مع الألم (avoir mal + articles contractés) ص 65، والحل التفاعلي الكامل لتمارين صفحة 66 (17 جملة).",
                    fontSize = 11.sp,
                    color = Slate700,
                    lineHeight = 17.sp
                )
            }
        }

        // Sub-tabs Navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val tabs = listOf(
                Pair("🧍 الجسم (ص64)", 0),
                Pair("📖 القاعدة (ص65)", 1),
                Pair("✍️ تمارين (ص66)", 2)
            )

            tabs.forEach { (label, idx) ->
                val isSelected = activeSubTab == idx
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Teal700 else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) Teal700 else Slate200),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            audioHelper.playClick()
                            activeSubTab = idx
                        }
                        .testTag("unit3_grammaire_subtab_$idx")
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.padding(vertical = 9.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        fontSize = 11.sp,
                        color = if (isSelected) Color.White else Slate700
                    )
                }
            }
        }

        // Content switching based on subtab
        when (activeSubTab) {
            0 -> Unit3BodyPartsView(audioHelper = audioHelper)
            1 -> Unit3GrammarRuleView(audioHelper = audioHelper)
            2 -> Unit3GrammarExercisesView(audioHelper = audioHelper, onScoreEarned = onScoreEarned)
        }
    }
}

// =========================================================================
// SUBTAB 1: أعضاء الجسم (صفحة 64 - Le Corps & La Tête)
// =========================================================================
@Composable
private fun Unit3BodyPartsView(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Body Overview Intro Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
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
                        text = "🧍 Le Corps (أعضاء الجسم كاملة - ص 64)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Teal700
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Teal50
                    ) {
                        Text(
                            text = "انقر للاستماع 🔊",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal700,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "جميع أعضاء الجسم المذكورة في الرسم التوضيحي بكتيّب المعهد صفحة 64 مصنفة حسب النوع وأداة التعريف مع النطق والترجمة:",
                    fontSize = 11.sp,
                    color = Slate600
                )

                // Category 1: Le Corps en général
                Text(
                    text = "1. أعضاء الجسم الرئيسية (Le Corps) :",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )

                val mainBodyParts = listOf(
                    Triple("la tête", "الرأس", "Féminin (مؤنث)"),
                    Triple("l'épaule", "الكتف", "Voyelle (متحرك)"),
                    Triple("le bras", "الذراع", "Masculin (مذكر)"),
                    Triple("le cou", "الرقبة / العنق", "Masculin (مذكر)"),
                    Triple("le coude", "الكوع", "Masculin (مذكر)"),
                    Triple("la main", "اليد", "Féminin (مؤنث)"),
                    Triple("les doigts", "الأصابع", "Pluriel (جمع)"),
                    Triple("le ventre", "البطن", "Masculin (مذكر)"),
                    Triple("le genou", "الركبة", "Masculin (مذكر)"),
                    Triple("la jambe", "الساق", "Féminin (مؤنث)"),
                    Triple("le pied", "القدم", "Masculin (مذكر)")
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    mainBodyParts.chunked(2).forEach { pair ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            pair.forEach { (fr, ar, category) ->
                                BodyPartInteractiveCard(
                                    french = fr,
                                    arabic = ar,
                                    category = category,
                                    audioHelper = audioHelper,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (pair.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }

        // Category 2: La Tête en détail (قسم الرأس والوجه)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.dp, Amber400)
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
                        text = "👦 La tête (أجزاء الرأس والوجه - ص 64)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Amber900
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White
                    ) {
                        Text(
                            text = "المربع السفلي ص 64",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Amber900,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                val headParts = listOf(
                    Triple("les cheveux", "الشعر", "Pluriel (جمع)"),
                    Triple("l'œil (les yeux)", "العين (العيون)", "Voyelle / Pluriel"),
                    Triple("le nez", "الأنف", "Masculin (مذكر)"),
                    Triple("les dents", "الأسنان", "Pluriel (جمع)"),
                    Triple("l'oreille", "الأذن", "Voyelle (متحرك)"),
                    Triple("la joue", "الخد", "Féminin (مؤنث)"),
                    Triple("la bouche", "الفم", "Féminin (مؤنث)"),
                    Triple("la langue", "اللسان", "Féminin (مؤنث)")
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    headParts.chunked(2).forEach { pair ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            pair.forEach { (fr, ar, category) ->
                                BodyPartInteractiveCard(
                                    french = fr,
                                    arabic = ar,
                                    category = category,
                                    audioHelper = audioHelper,
                                    isAmber = true,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BodyPartInteractiveCard(
    french: String,
    arabic: String,
    category: String,
    audioHelper: AudioHelper,
    isAmber: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, if (isAmber) Amber200 else Slate200),
        modifier = modifier.clickable { audioHelper.speak(french) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = french,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isAmber) Amber900 else Teal700
                )
                Text(
                    text = arabic,
                    fontSize = 11.sp,
                    color = Slate700
                )
                Text(
                    text = category,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate500
                )
            }

            IconButton(
                onClick = { audioHelper.speak(french) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "استماع",
                    tint = if (isAmber) Amber700 else Teal700,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// =========================================================================
// SUBTAB 2: شرح قاعدة التعبير عن الألم (صفحة 65 - avoir mal + articles contractés)
// =========================================================================
@Composable
private fun Unit3GrammarRuleView(audioHelper: AudioHelper) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Main Rule Header Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Indigo50),
            border = BorderStroke(1.5.dp, Indigo600.copy(alpha = 0.35f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🌟 قاعدة التعبير عن الألم (صفحة 65) :",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Indigo900
                )

                Text(
                    text = "للتعبير عن الألم في عضو من أعضاء الجسم، نستخدم الفعل Avoir مصرفاً مع الفاعل + كلمة mal + حرف الجر à مدغماً مع أداة التعريف المناسبة للعضو:",
                    fontSize = 11.sp,
                    color = Slate700,
                    lineHeight = 17.sp
                )

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Indigo200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sujet + avoir + mal + [ au / à la / à l' / aux ] + عضو الجسم",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Indigo700,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)
                    )
                }
            }
        }

        // Branching Diagram matching Page 65 exactly
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🌳 مخطط شجرة أدوات الألم (مطابق لصفحة 65 بالكتيّب) :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate900
                )

                // 1. J'ai mal à la (Féminin)
                RuleBranchSection(
                    title = "- J'ai mal à la",
                    categoryTitle = "المفرد المؤنث المبدوء بحرف ساكن (Féminin)",
                    color = Rose600,
                    bgColor = Rose50,
                    items = listOf(
                        Pair("tête", "الرأس ➔ J'ai mal à la tête."),
                        Pair("gorge", "الحلق ➔ J'ai mal à la gorge."),
                        Pair("bouche", "الفم ➔ J'ai mal à la bouche."),
                        Pair("main", "اليد ➔ J'ai mal à la main."),
                        Pair("jambe", "الساق ➔ J'ai mal à la jambe.")
                    ),
                    audioHelper = audioHelper
                )

                // 2. J'ai mal au (Masculin: à + le = au)
                RuleBranchSection(
                    title = "- J'ai mal au",
                    categoryTitle = "المفرد المذكر المبدوء بحرف ساكن (à + le = au)",
                    color = Indigo700,
                    bgColor = Indigo50,
                    items = listOf(
                        Pair("ventre", "البطن ➔ J'ai mal au ventre."),
                        Pair("bras", "الذراع ➔ J'ai mal au bras."),
                        Pair("dos", "الظهر ➔ J'ai mal au dos."),
                        Pair("pied", "القدم ➔ J'ai mal au pied.")
                    ),
                    audioHelper = audioHelper
                )

                // 3. J'ai mal à l' (Voyelle)
                RuleBranchSection(
                    title = "- J'ai mal à l'",
                    categoryTitle = "المفرد (مذكر أو مؤنث) المبدوء بمتحرك أو h صامتة",
                    color = Teal700,
                    bgColor = Teal50,
                    items = listOf(
                        Pair("oreille", "الأذن ➔ J'ai mal à l'oreille."),
                        Pair("œil", "العين ➔ J'ai mal à l'œil."),
                        Pair("épaule", "الكتف ➔ J'ai mal à l'épaule."),
                        Pair("estomac", "المعدة ➔ J'ai mal à l'estomac.")
                    ),
                    audioHelper = audioHelper
                )

                // 4. J'ai mal aux (Pluriel: à + les = aux)
                RuleBranchSection(
                    title = "- J'ai mal aux",
                    categoryTitle = "الجمع بنوعيه (ينتهي بـ s أو x) (à + les = aux)",
                    color = Emerald700,
                    bgColor = Emerald50,
                    items = listOf(
                        Pair("mains", "الأيدي ➔ J'ai mal aux mains."),
                        Pair("bras", "الأذرع ➔ J'ai mal aux bras."),
                        Pair("pieds", "الأقدام ➔ J'ai mal aux pieds."),
                        Pair("yeux", "العيون ➔ J'ai mal aux yeux."),
                        Pair("oreilles", "الآذان ➔ J'ai mal aux oreilles."),
                        Pair("jambes", "السيقان ➔ J'ai mal aux jambes."),
                        Pair("dents", "الأسنان ➔ J'ai mal aux dents."),
                        Pair("cheveux", "الشعر ➔ J'ai mal aux cheveux.")
                    ),
                    audioHelper = audioHelper
                )
            }
        }

        // Conjugation of AVOIR
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Amber50),
            border = BorderStroke(1.dp, Amber400)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "⚙️ تذكير بتصريف فعل (Avoir) في المضارع :",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Amber900
                )

                val conj = listOf(
                    Pair("J'ai mal", "أنا أشعر بألم"),
                    Pair("Tu as mal", "أنت تشعر بألم"),
                    Pair("Il / Elle a mal", "هو / هي يشعر بألم"),
                    Pair("Nous avons mal", "نحن نشعر بألم"),
                    Pair("Vous avez mal", "أنتم تشعرون بألم"),
                    Pair("Ils / Elles ont mal", "هم / هنّ يشعرون بألم")
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    conj.chunked(2).forEach { rowPair ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowPair.forEach { (fr, ar) ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { audioHelper.speak(fr) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(fr, fontSize = 11.sp, fontWeight = FontWeight.Black, color = Amber900)
                                            Text(ar, fontSize = 10.sp, color = Slate700)
                                        }
                                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Amber700, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RuleBranchSection(
    title: String,
    categoryTitle: String,
    color: Color,
    bgColor: Color,
    items: List<Pair<String, String>>,
    audioHelper: AudioHelper
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = bgColor,
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = color
                )
                Text(
                    text = categoryTitle,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate700
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items.forEach { (part, example) ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { audioHelper.speak(example) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "➔ $part",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Text(
                                text = example,
                                fontSize = 11.sp,
                                color = Slate600
                            )
                        }
                    }
                }
            }
        }
    }
}

// =========================================================================
// SUBTAB 3: تمارين وتدريبات الكتاب المدرسي التفاعلية (صفحة 66)
// =========================================================================
@Composable
private fun Unit3GrammarExercisesView(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit
) {
    val context = LocalContext.current
    var selectedExerciseTab by remember { mutableIntStateOf(1) } // 1: تمرين 1 (7 جمل), 2: تمرين 2 (10 جمل)
    val userAnswers = remember { mutableStateMapOf<Int, String>() }

    val allExercises = remember { getPage66GrammarExercises() }
    val currentExercises = allExercises.filter { it.exerciseNumber == selectedExerciseTab }

    val correctCount = currentExercises.count { ex ->
        userAnswers[ex.id] == ex.correctArticle
    }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Exercise Selector Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val exTabs = listOf(
                Pair("1) Remplace les points (7 جمل)", 1),
                Pair("2) Complète avec (10 جمل)", 2)
            )

            exTabs.forEach { (label, exNum) ->
                val isSelected = selectedExerciseTab == exNum
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Teal700 else Slate100,
                    border = BorderStroke(1.dp, if (isSelected) Teal700 else Slate200),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            audioHelper.playClick()
                            selectedExerciseTab = exNum
                        }
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

        // Header Instructions & Score
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedExerciseTab == 1)
                            "1) Remplace les points avec (au - à la - à l' - aux) :"
                        else
                            "2) Complète avec (au - à la - à l' - aux) :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Teal700
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Teal50
                    ) {
                        Text(
                            text = "ص 66 بالكتيّب",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal700,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Amber600, modifier = Modifier.size(18.dp))
                        Text(
                            text = "النتيجة: $correctCount من ${currentExercises.size}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                    }

                    if (userAnswers.isNotEmpty()) {
                        Row(
                            modifier = Modifier.clickable {
                                audioHelper.playClick()
                                currentExercises.forEach { userAnswers.remove(it.id) }
                                Toast.makeText(context, "تمت إعادة ضبط إجابات هذا التمرين", Toast.LENGTH_SHORT).show()
                            },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, tint = Slate600, modifier = Modifier.size(16.dp))
                            Text("إعادة التمرين", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate600)
                        }
                    }
                }
            }
        }

        // Exercises Items List
        currentExercises.forEach { item ->
            val chosenArticle = userAnswers[item.id]
            val isAnswered = chosenArticle != null
            val isCorrect = chosenArticle == item.correctArticle

            var showTranslation by remember { mutableStateOf(false) }

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
                    width = if (isAnswered) 1.5.dp else 1.dp,
                    color = when {
                        !isAnswered -> Slate200
                        isCorrect -> Emerald600
                        else -> Rose600
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Question text & audio
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val fullSentence = if (isAnswered) {
                            item.questionFr.replace("...............", chosenArticle ?: "")
                        } else {
                            item.questionFr
                        }

                        Text(
                            text = fullSentence,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900,
                            modifier = Modifier.weight(1f)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            TranslateIconButton(
                                isTranslated = showTranslation,
                                onClick = { showTranslation = !showTranslation },
                                contentDescription = "ترجمة الجملة"
                            )

                            IconButton(
                                onClick = {
                                    val textToRead = item.questionFr.replace("...............", item.correctArticle)
                                    audioHelper.speak(textToRead)
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Teal50, CircleShape)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "استماع", tint = Teal700, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    // Arabic Translation banner
                    AnimatedVisibility(visible = showTranslation) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Teal50,
                            border = BorderStroke(1.dp, Teal100),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "💬 الترجمة: ${item.questionAr}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Teal700,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    // Article Options Selector (au, à la, à l', aux)
                    val options = listOf("au", "à la", "à l'", "aux")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        options.forEach { opt ->
                            val isThisSelected = chosenArticle == opt
                            val isThisCorrect = opt == item.correctArticle

                            val btnBg = when {
                                !isAnswered -> Slate50
                                isThisSelected && isThisCorrect -> Emerald600
                                isThisSelected && !isThisCorrect -> Rose600
                                !isThisSelected && isThisCorrect -> Emerald100
                                else -> Slate50
                            }

                            val textColor = when {
                                !isAnswered -> Slate800
                                isThisSelected -> Color.White
                                !isThisSelected && isThisCorrect -> Emerald900
                                else -> Slate500
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = btnBg,
                                border = BorderStroke(
                                    1.dp,
                                    if (isAnswered && isThisCorrect) Emerald600 else Slate300
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (!isAnswered) {
                                            audioHelper.playClick()
                                            val isFirst = userAnswers[item.id] == null
                                            userAnswers[item.id] = opt
                                            if (isFirst && opt == item.correctArticle) {
                                                onScoreEarned(5)
                                            }
                                        }
                                    }
                            ) {
                                Text(
                                    text = opt,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = textColor,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    // Explanation Feedback
                    if (isAnswered) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isCorrect) Emerald50 else Rose50,
                            border = BorderStroke(1.dp, if (isCorrect) Emerald300 else Rose200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        if (isCorrect) Icons.Default.Check else Icons.Default.Info,
                                        contentDescription = null,
                                        tint = if (isCorrect) Emerald700 else Rose700,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = if (isCorrect) "إجابة صحيحة! (+5 نقاط) 🎉" else "إجابة غير صحيحة، انتبه للسبب: 💡",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp,
                                        color = if (isCorrect) Emerald900 else Rose800
                                    )
                                }
                                Text(
                                    text = item.explanation,
                                    fontSize = 11.sp,
                                    color = Slate800,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// =========================================================================
// قائمة جمل تمارين صفحة 66 نصاً من كتيّب منهج Bienvenu 2
// =========================================================================
private fun getPage66GrammarExercises(): List<GrammarExerciseItem> = listOf(
    // -------------------------------------------------------------
    // التمرين الأول (ص 66): 1) Remplace les points avec (au - à la - à l' - aux)
    // -------------------------------------------------------------
    GrammarExerciseItem(
        id = 101,
        exerciseNumber = 1,
        questionFr = "1. Vous avez mal ............... tête?",
        questionAr = "هل تشعرون بألم في الرأس؟",
        bodyPart = "tête",
        correctArticle = "à la",
        explanation = "كلمة tête مفرد مؤنث مبدوء بساكن (la tête) ➔ نختار (à la)."
    ),
    GrammarExerciseItem(
        id = 102,
        exerciseNumber = 1,
        questionFr = "2. Tu as mal ............... oreille?",
        questionAr = "هل تشعر بألم في الأذن؟",
        bodyPart = "oreille",
        correctArticle = "à l'",
        explanation = "كلمة oreille مفرد يبدأ بحرف متحرك (o) ➔ نختار (à l')."
    ),
    GrammarExerciseItem(
        id = 103,
        exerciseNumber = 1,
        questionFr = "3. Nous avons mal ............... pieds.",
        questionAr = "نحن نشعر بألم في الأقدام.",
        bodyPart = "pieds",
        correctArticle = "aux",
        explanation = "كلمة pieds جمع ينتهي بـ s (les pieds) ➔ نختار (aux)."
    ),
    GrammarExerciseItem(
        id = 104,
        exerciseNumber = 1,
        questionFr = "4. Ma fille a mal ............... ventre.",
        questionAr = "ابنتي تشعر بألم في البطن.",
        bodyPart = "ventre",
        correctArticle = "au",
        explanation = "كلمة ventre مفرد مذكر مبدوء بساكن (le ventre) ➔ نختار (au)."
    ),
    GrammarExerciseItem(
        id = 105,
        exerciseNumber = 1,
        questionFr = "5. J'ai mal ............... cheveux.",
        questionAr = "أشعر بألم في شعري.",
        bodyPart = "cheveux",
        correctArticle = "aux",
        explanation = "كلمة cheveux جمع ينتهي بـ x (les cheveux) ➔ نختار (aux)."
    ),
    GrammarExerciseItem(
        id = 106,
        exerciseNumber = 1,
        questionFr = "6. Elle a mal ............... main gauche.",
        questionAr = "هي تشعر بألم في يدها اليسرى.",
        bodyPart = "main gauche",
        correctArticle = "à la",
        explanation = "كلمة main مفرد مؤنث (la main) ➔ نختار (à la)."
    ),
    GrammarExerciseItem(
        id = 107,
        exerciseNumber = 1,
        questionFr = "7. Sami et Fadi ont mal ............... yeux.",
        questionAr = "سامي وفادي يشعران بألم في العينين.",
        bodyPart = "yeux",
        correctArticle = "aux",
        explanation = "كلمة yeux جمع شاذ لكلمة œil وتنتهي بـ x ➔ نختار (aux)."
    ),

    // -------------------------------------------------------------
    // التمرين الثاني (ص 66): 2) Complète avec (au - à la - à l' - aux)
    // -------------------------------------------------------------
    GrammarExerciseItem(
        id = 201,
        exerciseNumber = 2,
        questionFr = "1. J'ai mal ............... tête.",
        questionAr = "أشعر بألم في الرأس.",
        bodyPart = "tête",
        correctArticle = "à la",
        explanation = "كلمة tête مفرد مؤنث (la tête) ➔ نختار (à la)."
    ),
    GrammarExerciseItem(
        id = 202,
        exerciseNumber = 2,
        questionFr = "2. Sami a mal ............... pieds.",
        questionAr = "سامي يشعر بألم في القدمين.",
        bodyPart = "pieds",
        correctArticle = "aux",
        explanation = "كلمة pieds جمع ينتهي بـ s (les pieds) ➔ نختار (aux)."
    ),
    GrammarExerciseItem(
        id = 203,
        exerciseNumber = 2,
        questionFr = "3. Ma mère a mal ............... ventre.",
        questionAr = "أمي تشعر بألم في البطن.",
        bodyPart = "ventre",
        correctArticle = "au",
        explanation = "كلمة ventre مفرد مذكر (le ventre) ➔ نختار (au)."
    ),
    GrammarExerciseItem(
        id = 204,
        exerciseNumber = 2,
        questionFr = "4. Sara et Lina ont mal ............... dents.",
        questionAr = "سارة ولينا تشعران بألم في الأسنان.",
        bodyPart = "dents",
        correctArticle = "aux",
        explanation = "كلمة dents جمع ينتهي بـ s (les dents) ➔ نختار (aux)."
    ),
    GrammarExerciseItem(
        id = 205,
        exerciseNumber = 2,
        questionFr = "5. Nous avons mal ............... estomac.",
        questionAr = "نحن نشعر بألم في المعدة.",
        bodyPart = "estomac",
        correctArticle = "à l'",
        explanation = "كلمة estomac مفرد يبدأ بحرف متحرك (e) ➔ نختار (à l')."
    ),
    GrammarExerciseItem(
        id = 206,
        exerciseNumber = 2,
        questionFr = "6. Vous avez mal ............... oreilles.",
        questionAr = "أنتم تشعرون بألم في الآذان.",
        bodyPart = "oreilles",
        correctArticle = "aux",
        explanation = "كلمة oreilles جمع ينتهي بـ s (les oreilles) ➔ نختار (aux)."
    ),
    GrammarExerciseItem(
        id = 207,
        exerciseNumber = 2,
        questionFr = "7. Nada a mal ............... jambe.",
        questionAr = "ندى تشعر بألم في الساق.",
        bodyPart = "jambe",
        correctArticle = "à la",
        explanation = "كلمة jambe مفرد مؤنث (la jambe) ➔ نختار (à la)."
    ),
    GrammarExerciseItem(
        id = 208,
        exerciseNumber = 2,
        questionFr = "8. Mon père a mal ............... yeux.",
        questionAr = "والدي يشعر بألم في العينين.",
        bodyPart = "yeux",
        correctArticle = "aux",
        explanation = "كلمة yeux جمع ينتهي بـ x (les yeux) ➔ نختار (aux)."
    ),
    GrammarExerciseItem(
        id = 209,
        exerciseNumber = 2,
        questionFr = "9. Ali a mal ............... main.",
        questionAr = "علي يشعر بألم في اليد.",
        bodyPart = "main",
        correctArticle = "à la",
        explanation = "كلمة main مفرد مؤنث (la main) ➔ نختار (à la)."
    ),
    GrammarExerciseItem(
        id = 210,
        exerciseNumber = 2,
        questionFr = "10. Farida a mal ............... œil.",
        questionAr = "فريدة تشعر بألم في العين.",
        bodyPart = "œil",
        correctArticle = "à l'",
        explanation = "كلمة œil مفرد يبدأ بحرف متحرك (œ) ➔ نختار (à l')."
    )
)
