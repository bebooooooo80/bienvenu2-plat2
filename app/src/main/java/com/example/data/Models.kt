package com.example.data

enum class AppNavModule(val title: String, val icon: String, val shortLabel: String) {
    PORTAL("الرئيسية", "🏠", "الرئيسية"),
    WEB_SITE("الموقع التفاعلي", "🌐", "الموقع"),
    REVISION("Révision Mi-Année", "📑", "Révision"),
    UNIT_1("Unité 1: Invitation", "✉️", "Unité 1"),
    UNIT_2("Unité 2: Repas & Resto", "🍽️", "Unité 2"),
    UNIT_3("Unité 3: Santé & Hôpital", "🏥", "Unité 3"),
    GRAMMAR("Grammaire Générale", "📐", "Grammaire"),
    EXAM("Examen Mi-Année", "📝", "Examen")
}

data class DialogueLine(
    val speaker: String,
    val text: String,
    val arabicNote: String = "",
    val borderColorHex: Long = 0xFF4F46E5
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val pageReference: String = "",
    val explanation: String = "",
    val arabicTranslation: String = ""
)

data class PronounExercise(
    val phrase: String,
    val choices: List<String>,
    val correct: String,
    val explanation: String,
    val arabicTranslation: String = ""
)

data class CharacterPlacePair(
    val character: String,
    val place: String,
    val emoji: String,
    val arabicTranslation: String = ""
)

data class ExamQuestion(
    val section: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val arabicTranslation: String = ""
)

enum class VocabCategory(val labelFr: String, val labelAr: String) {
    ALL("Tous", "الكل"),
    MASCULINE("Masculin", "مذكر ♂"),
    FEMININE("Féminin", "مؤنث ♀"),
    VERB("Verbes", "أفعال ⚡"),
    EXPRESSION("Expressions & Mots", "كلمات وتعبيرات 💬"),
    PERSONNAGE("Personnages", "شخصيات 👥"),
    LIEU("Lieux", "أماكن 📍")
}

data class VocabWord(
    val id: String,
    val french: String,
    val arabic: String,
    val category: VocabCategory,
    val exampleFr: String = "",
    val exampleAr: String = "",
    val phoneticOrNote: String = "",
    val pageReference: String = "p. 14"
)

data class SituationOption(
    val textFr: String,
    val textAr: String,
    val isCorrect: Boolean,
    val explanation: String = ""
)

data class SituationItem(
    val id: String,
    val number: Int,
    val promptFr: String,
    val promptAr: String,
    val categoryAr: String,
    val categoryFr: String,
    val options: List<SituationOption>,
    val goldenRule: String,
    val rolePlaySpeaker: String = "Ton ami",
    val rolePlayMessage: String = "",
    val pageReference: String = "p. 15"
)

data class CompositionSentence(
    val order: Int,
    val french: String,
    val arabic: String,
    val hint: String = ""
)

data class CompositionTopic(
    val id: String,
    val titleFr: String,
    val titleAr: String,
    val instructionFr: String,
    val instructionAr: String,
    val pageReference: String = "p. 16",
    val requiredElements: List<Pair<String, String>>, // (element name, example)
    val sentences: List<CompositionSentence>,
    val scrambleWords: List<String> = emptyList(),
    val fillBlankText: String = "",
    val fillBlankSolutions: List<String> = emptyList()
)

data class PossessiveTableRow(
    val subject: String,
    val mascSingular: String,
    val femSingular: String,
    val plural: String,
    val arabicSubject: String,
    val exampleFr: String,
    val exampleAr: String
)

data class PossessiveExerciseItem(
    val id: String,
    val number: Int,
    val sentenceParts: List<String>, // text before, between, and after blanks
    val targetBlanks: List<String>,  // correct answers in order
    val optionsPerBlank: List<List<String>>, // options for each blank
    val subject: String,
    val complements: List<String>,
    val fullSentenceFr: String,
    val translationAr: String,
    val explanationAr: String,
    val isVowelRule: Boolean = false,
    val pageReference: String = "p. 18"
)

data class PronomOnExerciseItem(
    val id: String,
    val number: Int,
    val predicateFr: String,
    val correctAnswer: String,
    val fullSentenceFr: String,
    val translationAr: String,
    val explanationAr: String,
    val verbAnalyzed: String,
    val pageReference: String = "p. 19"
)

data class PronomOnReplaceItem(
    val id: String,
    val number: Int,
    val onSentenceFr: String,
    val nousSentenceFr: String,
    val onSentenceAr: String,
    val nousSentenceAr: String,
    val verbTransformation: String,
    val pageReference: String = "p. 19"
)

enum class BookletPronounCategory(val code: String, val titleFr: String, val titleAr: String, val badgeColorHex: Long) {
    SUJET("Sujet", "1- Pronoms Sujets", "ضمائر الفاعل", 0xFF6366F1),
    COD("C.O.D", "2- Complément d'objet direct (C.O.D)", "المفعول المباشر (بدون حرف جر)", 0xFF0D9488),
    COI("C.O.I", "3- Complément d'objet indirect (C.O.I)", "المفعول غير المباشر (حرف جر + شخص)", 0xFFD97706)
}

data class BookletPronounRuleItem(
    val id: String,
    val category: BookletPronounCategory,
    val pronoun: String,
    val replacesFr: String,
    val replacesAr: String,
    val exampleFr: String,
    val transformedFr: String,
    val noteAr: String,
    val pageReference: String
)

data class BookletPronounOfficialExerciseItem(
    val id: String,
    val number: Int,
    val fullSentenceFr: String,
    val underlinedPart: String,
    val category: BookletPronounCategory,
    val targetPronoun: String,
    val options: List<String>,
    val transformedSentenceFr: String,
    val sentenceAr: String,
    val transformedSentenceAr: String,
    val explanationAr: String,
    val ruleTag: String,
    val positionRuleAr: String,
    val pageReference: String
)

data class FaisDesPhrasesItem(
    val id: String,
    val number: Int,
    val promptFr: String,
    val promptAr: String,
    val verbInfinitive: String,
    val complement: String,
    val modelSentenceFr: String,
    val modelSentenceAr: String,
    val alternativeSentences: List<Pair<String, String>>,
    val scrambledWords: List<String>,
    val correctOrder: List<String>,
    val grammarTipAr: String,
    val conjugationSamples: List<Pair<String, String>>,
    val pageReference: String = "p. 24"
)

// --- كتيّب ص 25: الشخصيات والأماكن (Les lieux et Les personnages) ---
data class BookletPersonnageItem(
    val id: String,
    val french: String,
    val arabic: String,
    val gender: String, // "m", "f", "m/f"
    val typicalPlaces: List<String>,
    val emoji: String,
    val exampleSentenceFr: String = "",
    val exampleSentenceAr: String = ""
)

data class BookletLieuItem(
    val id: String,
    val frenchWithPreposition: String, // "au restaurant", "à l'école", etc.
    val placeNameOnly: String,
    val preposition: String, // "au", "à l'", "à la", "dans la", "en"
    val arabic: String,
    val emoji: String,
    val typicalCharacters: List<String>,
    val explanationAr: String = ""
)

// --- كتيّب ص 26: التمارين الثلاثة (1. Où vas-tu pour...? 2. Qui parle? 3. Qui peut faire ce travail:) ---
data class BookletOuVasTuItem(
    val id: String,
    val number: Int,
    val activityFr: String,
    val activityAr: String,
    val expectedAnswerFr: String,
    val alternativeAnswersFr: List<String>,
    val answerAr: String,
    val options: List<String>,
    val explanationAr: String
)

data class BookletQuiParleItem(
    val id: String,
    val number: Int,
    val quoteFr: String,
    val quoteAr: String,
    val speakerFr: String,
    val alternativeSpeakersFr: List<String>,
    val speakerAr: String,
    val options: List<String>,
    val situationContextAr: String
)

data class BookletQuiFaitCeTravailItem(
    val id: String,
    val number: Int,
    val actionFr: String,
    val actionAr: String,
    val professionFr: String,
    val alternativeProfessionsFr: List<String>,
    val professionAr: String,
    val options: List<String>,
    val descriptionAr: String
)

// --- كتيّب ص 41 و 42: الوجبات (Les Repas) ---
enum class MealType(val titleFr: String, val titleAr: String, val emoji: String, val timeSlot: String) {
    PETIT_DEJEUNER("Le Petit déjeuner", "وجبة الإفطار", "🥐", "Le matin (الصباح)"),
    DEJEUNER("Le Déjeuner", "وجبة الغداء", "🍗", "L'après-midi (الظهيرة)"),
    DINER("Le Dîner", "وجبة العشاء", "🥣", "Le soir (المساء)"),
    DESSERTS("Les Desserts", "الحلويات والتحلية", "🍰", "À la fin du repas (في نهاية الوجبة)")
}

data class FoodDrinkItem(
    val id: String,
    val frenchWithArticle: String,
    val frenchBase: String,
    val articlePartitive: String,
    val genderAr: String,
    val arabic: String,
    val itemType: String, // "mange" (طعام) or "bois" (مشروب) or "dessert" (تحلية)
    val mealType: MealType,
    val courseCategory: String = "", // "Comme entrée", "Comme plat principal", "Comme dessert"
    val emoji: String,
    val sampleSentenceFr: String,
    val sampleSentenceAr: String,
    val pageReference: String
)

data class RepasQuizItem(
    val id: String,
    val questionFr: String,
    val questionAr: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationAr: String,
    val tip: String = ""
)


