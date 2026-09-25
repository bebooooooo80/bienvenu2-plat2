package com.example.data

/**
 * Data structures and content for the Official Mid-Year French Exam
 * Examen officiel de mi-année (Pages 78-79) - 2ème Préparatoire
 * Bienvenu 2
 * Note totale : 20 Points
 */

data class ExamDialogueLine(
    val speaker: String,
    val textFr: String,
    val textAr: String
)

data class ExamMCQQuestion(
    val id: String,
    val questionFr: String,
    val questionAr: String,
    val options: List<String>,
    val correctIndex: Int,
    val points: Float,
    val explanationFr: String,
    val explanationAr: String
)

data class ExamTrueFalseQuestion(
    val id: String,
    val statementFr: String,
    val statementAr: String,
    val isTrue: Boolean,
    val points: Float,
    val explanationFr: String,
    val explanationAr: String
)

data class ExamCompleteQuestion(
    val id: String,
    val sentenceTemplateFr: String, // e.g. "Comme plat principal Ahmed demande de la ______ , du ______ et des frites."
    val blanksWords: List<String>,   // words to fill in order
    val wordBank: List<String>,      // choices for chips
    val sentenceAr: String,
    val points: Float,
    val fullSentenceFr: String
)

data class ExamConjugateQuestion(
    val id: String,
    val promptFr: String, // "1- Nous .................... aux élèves. (parler)"
    val verb: String,     // "parler"
    val subject: String,  // "Nous"
    val correctAnswer: String, // "parlons"
    val points: Float,
    val ruleExplanationAr: String
)

data class ExamTransformQuestion(
    val id: String,
    val originalFr: String,
    val instructionFr: String,
    val instructionAr: String,
    val targetWord: String = "", // underlined part if applicable
    val expectedAnswerFr: String,
    val points: Float,
    val explanationAr: String,
    val options: List<String> = emptyList() // options if presented as MCQ or choice chips
)

data class ExamProductionQuestion(
    val id: String,
    val purposeFr: String, // "Regarder les monuments."
    val purposeAr: String, // "مشاهدة الآثار"
    val options: List<String>, // ["au club", "à l'hôpital", "au musée"]
    val correctIndex: Int,
    val modelSentenceFr: String, // "Je vais au musée pour regarder les monuments."
    val points: Float
)

data class ExamSituationQuestion(
    val id: String,
    val situationFr: String,
    val situationAr: String,
    val options: List<String>,
    val correctIndex: Int,
    val points: Float,
    val explanationAr: String
)

object MidYearExamData {
    const val EXAM_TITLE_FR = "Examen de Mi-année"
    const val EXAM_TITLE_AR = "امتحان نصف العام الرسمي"
    const val INSTITUTE = "Bienvenu 2"
    const val INSTITUTE_AR = "منهج Bienvenu 2"
    const val GRADE_LEVEL = "2ème Préparatoire • الصف الثاني الإعدادي"
    const val TOTAL_MARKS = 20f

    // =========================================================================
    // SECTION 1: COMPRÉHENSION DU TEXTE (8 Points) - Page 78
    // =========================================================================
    val documentTitleFr = "Au restaurant"
    val documentTitleAr = "في المطعم"

    val documentLines = listOf(
        ExamDialogueLine(
            speaker = "Le garçon",
            textFr = "Bonjour monsieur, vous voulez prendre le déjeuner au menu ou à la carte ?",
            textAr = "صباح الخير سيدي، هل ترغب في تناول الغداء من قائمة الوجبات المحددة (au menu) أم حسب الطلب (à la carte)؟"
        ),
        ExamDialogueLine(
            speaker = "Ahmed",
            textFr = "À la carte.",
            textAr = "حسب الطلب (أختار أطباقي بنفسي)."
        ),
        ExamDialogueLine(
            speaker = "Le garçon",
            textFr = "Que voulez-vous comme entrée ?",
            textAr = "ماذا تريد كطبق مقبلات / فاتح للشهية؟"
        ),
        ExamDialogueLine(
            speaker = "Ahmed",
            textFr = "Des tomates et des concombres en salade.",
            textAr = "طماطم وخيار في طبق سلطة."
        ),
        ExamDialogueLine(
            speaker = "Le garçon",
            textFr = "et le plat principal ?",
            textAr = "وماذا عن الطبق الرئيسي؟"
        ),
        ExamDialogueLine(
            speaker = "Ahmed",
            textFr = "De la viande, du riz et des frites.",
            textAr = "لحم، وأرز، وبطاطس مقلية."
        ),
        ExamDialogueLine(
            speaker = "Le garçon",
            textFr = "Voulez-vous du dessert ?",
            textAr = "هل تريد حلوى؟"
        ),
        ExamDialogueLine(
            speaker = "Ahmed",
            textFr = "Oui, j'aime le gâteau.",
            textAr = "نعم، أنا أحب الجاتوه / الكعك."
        )
    )

    val documentFullTextFr = documentLines.joinToString(" ") { "${it.speaker} : ${it.textFr}" }
    val documentFullTextAr = documentLines.joinToString("\n") { "${it.speaker} : ${it.textAr}" }

    // A) Choisis le bon groupe (3 Points - 1 pt each)
    val docPartAQuestions = listOf(
        ExamMCQQuestion(
            id = "doc_q1",
            questionFr = "1- Ahmed prend son repas ....................",
            questionAr = "١- يتناول أحمد وجبته ....................",
            options = listOf("à la carte", "au menu", "au dîner"),
            correctIndex = 0,
            points = 1f,
            explanationFr = "Ahmed a répondu au garçon : « À la carte. »",
            explanationAr = "أحمد أجاب النادل صراحة: « À la carte » أي حسب اختياره."
        ),
        ExamMCQQuestion(
            id = "doc_q2",
            questionFr = "2- Ahmed demande .................... comme plat principal.",
            questionAr = "٢- يطلب أحمد .................... كطبق رئيسي.",
            options = listOf("du poisson", "de la glace", "de la viande et du riz"),
            correctIndex = 2,
            points = 1f,
            explanationFr = "Pour le plat principal, Ahmed demande : « De la viande, du riz et des frites. »",
            explanationAr = "طلب أحمد للطبق الرئيسي لحماً وأرزاً وبطاطس مقلية (viande et riz)."
        ),
        ExamMCQQuestion(
            id = "doc_q3",
            questionFr = "3- Ahmed va prendre ....................",
            questionAr = "٣- سيتناول أحمد وجبة ....................",
            options = listOf("le petit-déjeuner", "le déjeuner", "le dîner"),
            correctIndex = 1,
            points = 1f,
            explanationFr = "Le garçon a demandé : « vous voulez prendre le déjeuner au menu ou à la carte ? »",
            explanationAr = "سؤال النادل في البداية كان عن وجبة الغداء « le déjeuner »."
        )
    )

    // B) Mets (√) ou (×) (3 Points - 1 pt each)
    val docPartBQuestions = listOf(
        ExamTrueFalseQuestion(
            id = "doc_tf1",
            statementFr = "1. Ce dialogue est au restaurant.",
            statementAr = "١. هذا الحوار يدور في المطعم.",
            isTrue = true,
            points = 1f,
            explanationFr = "Vrai : Le dialogue se passe entre un garçon et un client dans un restaurant.",
            explanationAr = "صح (Vrai): عنوان الوثيقة وشخصياتها (نادل وزبون) في المطعم."
        ),
        ExamTrueFalseQuestion(
            id = "doc_tf2",
            statementFr = "2. Ahmed aime le gâteau.",
            statementAr = "٢. أحمد يحب الجاتوه / الكعك.",
            isTrue = true,
            points = 1f,
            explanationFr = "Vrai : Ahmed dit : « Oui, j'aime le gâteau. »",
            explanationAr = "صح (Vrai): أحمد قال صراحة: « Oui, j'aime le gâteau »."
        ),
        ExamTrueFalseQuestion(
            id = "doc_tf3",
            statementFr = "3. Comme entrée, Ahmed demande de la crème.",
            statementAr = "٣. كطبق مقبلات، طلب أحمد كريمة.",
            isTrue = false,
            points = 1f,
            explanationFr = "Faux : Comme entrée, Ahmed demande des tomates et des concombres en salade.",
            explanationAr = "خطأ (Faux): طلب أحمد طماطم وخيار في سلطة وليس كريمة."
        )
    )

    // C) Complète (2 Points - 1 pt each)
    val docPartCQuestions = listOf(
        ExamCompleteQuestion(
            id = "doc_c1",
            sentenceTemplateFr = "1- Comme plat principal Ahmed demande de la ______ , du ______ et des frites.",
            blanksWords = listOf("viande", "riz"),
            wordBank = listOf("viande", "riz", "poisson", "crème", "salade"),
            sentenceAr = "١- كطبق رئيسي يطلب أحمد من الـ (لحم)، والـ (أرز) والبطاطس المقلية.",
            points = 1f,
            fullSentenceFr = "Comme plat principal Ahmed demande de la viande, du riz et des frites."
        ),
        ExamCompleteQuestion(
            id = "doc_c2",
            sentenceTemplateFr = "2- Comme entrée, Ahmed demande des ______ et des ______ en salade.",
            blanksWords = listOf("tomates", "concombres"),
            wordBank = listOf("tomates", "concombres", "pommes", "fruits", "gâteaux"),
            sentenceAr = "٢- كطبق مقبلات، يطلب أحمد (طماطم) و(خيار) في سلطة.",
            points = 1f,
            fullSentenceFr = "Comme entrée, Ahmed demande des tomates et des concombres en salade."
        )
    )

    // =========================================================================
    // SECTION 2: GRAMMAIRE (9 Points) - Page 79
    // =========================================================================

    // A) Mets les verbes au présent (1 Point - 0.5 pt each)
    val gramPartAQuestions = listOf(
        ExamConjugateQuestion(
            id = "gram_a1",
            promptFr = "1- Nous .................... aux élèves. (parler)",
            verb = "parler",
            subject = "Nous",
            correctAnswer = "parlons",
            points = 0.5f,
            ruleExplanationAr = "فعل parler مجموعة أولى، مع الضمير Nous نحذف er ونضع ons ➔ parlons."
        ),
        ExamConjugateQuestion(
            id = "gram_a2",
            promptFr = "2- Vous .................... une robe. (choisir)",
            verb = "choisir",
            subject = "Vous",
            correctAnswer = "choisissez",
            points = 0.5f,
            ruleExplanationAr = "فعل choisir مجموعة ثانية، مع الضمير Vous نحذف ir ونضع issez ➔ choisissez."
        )
    )

    // B) Fais comme indiqué entre parenthèses (6 Points)
    val gramPartBQuestions = listOf(
        ExamMCQQuestion(
            id = "gram_b1",
            questionFr = "1. On peut prendre .................... boissons fraîches.",
            questionAr = "١. يمكننا تناول .................... مشروبات باردة.",
            options = listOf("du", "de l'", "des"),
            correctIndex = 2,
            points = 1.2f,
            explanationFr = "« Boissons fraîches » est un nom au pluriel (-s), on utilise donc l'article indéfini ou partitif pluriel « des ».",
            explanationAr = "كلمة boissons اسم جمع مؤنث ينتهي بـ s ➔ نختار أداة الجمع des."
        ),
        ExamMCQQuestion(
            id = "gram_b2",
            questionFr = "2- Tu aimes .................... parents.",
            questionAr = "٢- أنت تحب .................... والديك.",
            options = listOf("ton", "ta", "tes"),
            correctIndex = 2,
            points = 1.2f,
            explanationFr = "Le sujet est « Tu » et le mot « parents » est au pluriel (-s) ➔ adjectif possessif pluriel « tes ».",
            explanationAr = "الفاعل Tu والاسم المملوك parents جمع ➔ صفة الملكية للجمع هي tes."
        ),
        ExamMCQQuestion(
            id = "gram_b3",
            questionFr = "3- .................... vas-tu ? - Je vais au cinéma.",
            questionAr = "٣- .................... تذهب؟ - أنا أذهب إلى السينما.",
            options = listOf("Comment", "Qui", "Où"),
            correctIndex = 2,
            points = 1.2f,
            explanationFr = "La réponse indique un lieu « au cinéma », le mot interrogatif approprié est « Où » (أين).",
            explanationAr = "الإجابة تحدد مكاناً (au cinéma) ➔ أداة الاستفهام عن المكان هي Où (أين)."
        ),
        ExamTransformQuestion(
            id = "gram_b4",
            originalFr = "Elle mange des légumes.",
            instructionFr = "Mets à la forme négative",
            instructionAr = "ضع في صيغة النفي",
            expectedAnswerFr = "Elle ne mange pas de légumes.",
            points = 1.2f,
            explanationAr = "عند النفي نضع الفعل بين ne ... pas، وتتحول أداة التجزئة des إلى de (لأن الفعل ليس être) ➔ Elle ne mange pas de légumes.",
            options = listOf(
                "Elle ne mange pas de légumes.",
                "Elle ne mange pas des légumes.",
                "Elle ne mange plus de légumes."
            )
        ),
        ExamTransformQuestion(
            id = "gram_b5",
            originalFr = "Jouer - au ballon",
            instructionFr = "Fais une phrase",
            instructionAr = "كوّن جملة مفيدة وصحيحة",
            expectedAnswerFr = "Les garçons jouent au ballon. (ou : Je joue au ballon.)",
            points = 1.2f,
            explanationAr = "نصرّف فعل jouer مع فاعل مناسب ونكمل بـ au ballon، مثل: « Je joue au ballon » أو « Les garçons jouent au ballon ».",
            options = listOf(
                "Je joue au ballon.",
                "Les garçons jouent au ballon.",
                "Nous jouons au ballon."
            )
        )
    )

    // C) Remplace les mots soulignés par un pronom personnel (2 Points - 1 pt each)
    val gramPartCQuestions = listOf(
        ExamTransformQuestion(
            id = "gram_c1",
            originalFr = "Elle aime les fruits.",
            instructionFr = "Remplace les mots soulignés par un pronom personnel",
            instructionAr = "استبدل ما تحته خط (les fruits) بضمير مفعول مناسب",
            targetWord = "les fruits",
            expectedAnswerFr = "Elle les aime.",
            points = 1f,
            explanationAr = "« les fruits » مفعول به مباشر جمع (C.O.D) ➔ نستبدله بالضمير les ونضعه قبل الفعل ➔ Elle les aime.",
            options = listOf("Elle les aime.", "Elle lui aime.", "Elle l'aime.")
        ),
        ExamTransformQuestion(
            id = "gram_c2",
            originalFr = "Je parle à mon ami.",
            instructionFr = "Remplace les mots soulignés par un pronom personnel",
            instructionAr = "استبدل ما تحته خط (à mon ami) بضمير مفعول مناسب",
            targetWord = "à mon ami",
            expectedAnswerFr = "Je lui parle.",
            points = 1f,
            explanationAr = "« à mon ami » مفعول به غير مباشر عاقل مفرد مسبوق بـ à (C.O.I) ➔ نستبدله بالضمير lui ونضعه قبل الفعل ➔ Je lui parle.",
            options = listOf("Je lui parle.", "Je le parle.", "Je leur parle.")
        )
    )

    // =========================================================================
    // SECTION 3: PRODUCTION ÉCRITE (1 Point - 0.5 pt each) - Page 79
    // "Où vas-tu pour....? (au club - à l'hôpital - au musée)"
    // =========================================================================
    val prodOptions = listOf("au club", "à l'hôpital", "au musée")

    val productionQuestions = listOf(
        ExamProductionQuestion(
            id = "prod_1",
            purposeFr = "1- Regarder les monuments.",
            purposeAr = "١- لمشاهدة الآثار والمعالم التاريخية.",
            options = prodOptions,
            correctIndex = 2, // au musée
            modelSentenceFr = "Je vais au musée pour regarder les monuments.",
            points = 0.5f
        ),
        ExamProductionQuestion(
            id = "prod_2",
            purposeFr = "2- Faire du sport.",
            purposeAr = "٢- لممارسة الرياضة.",
            options = prodOptions,
            correctIndex = 0, // au club
            modelSentenceFr = "Je vais au club pour faire du sport.",
            points = 0.5f
        )
    )

    // =========================================================================
    // SECTION 4: SITUATIONS (2 Points - 1 pt each) - Page 79
    // =========================================================================
    val situationsQuestions = listOf(
        ExamSituationQuestion(
            id = "sit_1",
            situationFr = "1- Tu demandes au garçon l'addition, tu dis :",
            situationAr = "١- تطلب من النادل الحساب (الفاتورة)، ماذا تقول؟",
            options = listOf(
                "a) l'addition S.V.P!",
                "b) Que voulez-vous?",
                "c) Vous voulez autre chose?"
            ),
            correctIndex = 0,
            points = 1f,
            explanationAr = "لطلب الفاتورة والحساب من النادل في المطعم تقول بأدب: « l'addition S.V.P! » (الحساب لو سمحت)."
        ),
        ExamSituationQuestion(
            id = "sit_2",
            situationFr = "2. Au restaurant, tu demandes le menu au garçon, tu dis :",
            situationAr = "٢- في المطعم، تطلب قائمة الطعام من النادل، ماذا تقول؟",
            options = listOf(
                "a) Je prends de la glace.",
                "b) Apportez moi le menu S.V.P!",
                "c) Bon appétit."
            ),
            correctIndex = 1,
            points = 1f,
            explanationAr = "لطلب قائمة الطعام من النادل تقول: « Apportez-moi le menu S.V.P! » (أحضر لي قائمة الطعام لو سمحت)."
        )
    )
}
