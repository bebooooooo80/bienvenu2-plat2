package com.example.data

data class RevisionDocument(
    val id: Int,
    val pageNumber: String,
    val titleFr: String,
    val titleAr: String,
    val documentType: String,
    val textFr: String,
    val textAr: String,
    val vocabulary: List<Pair<String, String>> = emptyList(),
    val mcqQuestions: List<RevisionMcqQuestion>,
    val trueFalseQuestions: List<RevisionTrueFalseQuestion>,
    val completionQuestions: List<RevisionOpenQuestion>
)

data class RevisionMcqQuestion(
    val id: String,
    val questionFr: String,
    val questionAr: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationAr: String
)

data class RevisionTrueFalseQuestion(
    val id: String,
    val statementFr: String,
    val statementAr: String,
    val isTrue: Boolean,
    val explanationAr: String
)

data class RevisionOpenQuestion(
    val id: String,
    val promptFr: String,
    val promptAr: String,
    val modelAnswerFr: String,
    val modelAnswerAr: String,
    val hint: String = ""
)

object RevisionData {
    val documents: List<RevisionDocument> = listOf(
        // ==============================================================
        // Document (1) - Page 67 : Invitation d'anniversaire
        // ==============================================================
        RevisionDocument(
            id = 1,
            pageNumber = "p. 67",
            titleFr = "Document (1) : Lettre d'invitation",
            titleAr = "الوثيقة 1 : خطاب دعوة لحفل عيد ميلاد",
            documentType = "Une lettre (رسالة)",
            textFr = """
Alexandrie, le 15 Novembre
Chère Noha,
    Je t'invite à une petite fête à l'occasion de mon anniversaire, le jeudi 30 Novembre, à 20 heures à la maison. Je vais inviter nos amies Racha et Rana. Ma cousine Salma va apporter ses CD. On va chanter, danser et manger des gâteaux. Nous allons être contentes de te voir.
À bientôt
Ton amie
Hoda
            """.trimIndent(),
            textAr = """
الإسكندرية، 15 نوفمبر
عزيزتي نهى،
أدعوكِ إلى حفلة صغيرة بمناسبة عيد ميلادي، يوم الخميس 30 نوفمبر في تمام الساعة الثامنة مساءً في المنزل. سوف أدعو صديقتينا رشا ورنا. وابنة عمي سلمى ستحضر أسطواناتها الموسيقية (CDs). سوف نغني ونرقص ونأكل الكعك والجاتوه. سنكون سعيدات جداً برؤيتكِ.
إلى اللقاء قريباً
صديقتكِ
هدى
            """.trimIndent(),
            vocabulary = listOf(
                Pair("une fête", "حفلة"),
                Pair("à l'occasion de", "بمناسبة"),
                Pair("apporter des CD", "يُحضر أسطوانات"),
                Pair("chanter et danser", "يغني ويرقص"),
                Pair("contentes de te voir", "مسرورات برؤيتك")
            ),
            mcqQuestions = listOf(
                RevisionMcqQuestion(
                    id = "d1_q1",
                    questionFr = "1. Ce document est ............... .",
                    questionAr = "هذه الوثيقة عبارة عن :",
                    options = listOf("une lettre", "un dialogue", "un texte"),
                    correctIndex = 0,
                    explanationAr = "الوثيقة تبدأ بالمدينة والتاريخ (Alexandrie, le 15 Novembre) وتنتهي بتوقيع (Ton amie Hoda) لذا فهي خطاب (une lettre)."
                ),
                RevisionMcqQuestion(
                    id = "d1_q2",
                    questionFr = "2. C'est l'anniversaire ............... .",
                    questionAr = "هذا عيد ميلاد :",
                    options = listOf("de Noha", "de Hoda", "de Salma"),
                    correctIndex = 1,
                    explanationAr = "هدى هي كاتبة الرسالة وتقول: «à l'occasion de mon anniversaire» أي بمناسبة عيد ميلادي أنا."
                ),
                RevisionMcqQuestion(
                    id = "d1_q3",
                    questionFr = "3. L'anniversaire va être ............... Novembre.",
                    questionAr = "حفل عيد الميلاد سيكون يوم :",
                    options = listOf("le quinze", "le vingt", "le trente"),
                    correctIndex = 2,
                    explanationAr = "تاريخ الحفل المذكور في النص هو: «le jeudi 30 Novembre» (30 نوفمبر = le trente)."
                ),
                RevisionMcqQuestion(
                    id = "d1_q4",
                    questionFr = "4. La fête va être ............... .",
                    questionAr = "الحفلة ستقام :",
                    options = listOf("le soir", "l'après-midi", "le matin"),
                    correctIndex = 0,
                    explanationAr = "الساعة 20:00 (à 20 heures) في التوقيت الفرنسي تعني الثامنة مساءً (le soir)."
                )
            ),
            trueFalseQuestions = listOf(
                RevisionTrueFalseQuestion(
                    id = "d1_tf1",
                    statementFr = "1. Hoda écrit à sa cousine.",
                    statementAr = "هدى تكتب لابنة عمها.",
                    isTrue = false,
                    explanationAr = "خطأ (Faux): هدى تكتب لصديقتها نهى (Chère Noha / Ton amie Hoda)، بينما ابنة عمها هي سلمى (Ma cousine Salma)."
                ),
                RevisionTrueFalseQuestion(
                    id = "d1_tf2",
                    statementFr = "2. Racha est l'amie de Noha.",
                    statementAr = "رشا هي صديقة لنهى.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): هدى تقول في النص «nos amies Racha et Rana» أي صديقاتنا المشتركات."
                ),
                RevisionTrueFalseQuestion(
                    id = "d1_tf3",
                    statementFr = "3. Hoda habite à Alexandrie.",
                    statementAr = "هدى تسكن في الإسكندرية.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): كُتب الخطاب من الإسكندرية «Alexandrie, le 15 Novembre»."
                )
            ),
            completionQuestions = listOf(
                RevisionOpenQuestion(
                    id = "d1_op1",
                    promptFr = "1. Qui va apporter les CD?",
                    promptAr = "من الذي سيحضر أسطوانات الأغاني (CD)?",
                    modelAnswerFr = "Salma (la cousine de Hoda) va apporter ses CD.",
                    modelAnswerAr = "سلمى (ابنة عم هدى) هي التي ستحضر الأسطوانات.",
                    hint = "Salma va apporter ses CD."
                ),
                RevisionOpenQuestion(
                    id = "d1_op2",
                    promptFr = "2. Où est-ce que la fête va être?",
                    promptAr = "أين ستقام الحفلة؟",
                    modelAnswerFr = "La fête va être à la maison (de Hoda).",
                    modelAnswerAr = "ستقام الحفلة في المنزل (منزل هدى).",
                    hint = "à la maison"
                ),
                RevisionOpenQuestion(
                    id = "d1_op3",
                    promptFr = "3. Qu'est-ce qu' on va faire à la fête?",
                    promptAr = "ماذا سيفعلون في الحفل؟",
                    modelAnswerFr = "On va chanter, danser et manger des gâteaux.",
                    modelAnswerAr = "سوف يغنون، يرقصون، ويتناولون الجاتوه والكعك.",
                    hint = "chanter, danser et manger des gâteaux"
                )
            )
        ),

        // ==============================================================
        // Document (2) - Page 68 : À l'hôpital
        // ==============================================================
        RevisionDocument(
            id = 2,
            pageNumber = "p. 68",
            titleFr = "Document (2) : À l'hôpital",
            titleAr = "الوثيقة 2 : في المستشفى",
            documentType = "Un texte descriptif (نص وصفي)",
            textFr = """
À l'hôpital
Le médecin examine les malades, il écrit l'ordonnance. L'infirmière donne les médicaments aux malades à des heures fixes. Sur les lits, il y a des couvertures blanches. Le bureau de l'infirmière est près des malades. Des parents et des amis visitent les malades et leur apportent des fleurs.
            """.trimIndent(),
            textAr = """
في المستشفى
يفحص الطبيب المرضى، ويكتب الروشتة الطبية. وتعطي الممرضة الأدوية للمرضى في مواعيد محددة. وعلى الأسِرة، توجد أغطية وبطاطين بيضاء. مكتب الممرضة قريب من المرضى. ويزور الأقارب والأصدقاء المرضى ويحضرون لهم الزهور.
            """.trimIndent(),
            vocabulary = listOf(
                Pair("examine les malades", "يفحص المرضى"),
                Pair("l'ordonnance", "الروشتة الطبية"),
                Pair("des heures fixes", "مواعيد ثابتة / محددة"),
                Pair("des couvertures", "أغطية / بطاطين"),
                Pair("apportent des fleurs", "يحضرون زهوراً")
            ),
            mcqQuestions = listOf(
                RevisionMcqQuestion(
                    id = "d2_q1",
                    questionFr = "1. L'infirmière donne les médicaments ...............",
                    questionAr = "تعطي الممرضة الأدوية لـ :",
                    options = listOf("aux médecins", "aux malades", "aux parents"),
                    correctIndex = 1,
                    explanationAr = "في النص: «L'infirmière donne les médicaments aux malades» للمرضى."
                ),
                RevisionMcqQuestion(
                    id = "d2_q2",
                    questionFr = "2. Sur le lit, il y a ...............",
                    questionAr = "يوجد على السرير :",
                    options = listOf("des fleurs", "des médicaments", "des couvertures"),
                    correctIndex = 2,
                    explanationAr = "في النص: «Sur les lits, il y a des couvertures blanches» أغطية بيضاء."
                ),
                RevisionMcqQuestion(
                    id = "d2_q3",
                    questionFr = "3. ............... écrit les ordonnances.",
                    questionAr = "الذي يكتب الروشتات هو :",
                    options = listOf("Le médecin", "L'infirmière", "Le malade"),
                    correctIndex = 0,
                    explanationAr = "في النص: «Le médecin examine les malades, il écrit l'ordonnance»."
                )
            ),
            trueFalseQuestions = listOf(
                RevisionTrueFalseQuestion(
                    id = "d2_tf1",
                    statementFr = "1. L'infirmière travaille dans un hôpital.",
                    statementAr = "الممرضة تعمل في مستشفى.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): العنوان والنص يتحدثان عن المستشفى (À l'hôpital)."
                ),
                RevisionTrueFalseQuestion(
                    id = "d2_tf2",
                    statementFr = "2. Le médecin examine les malades.",
                    statementAr = "الطبيب يفحص المرضى.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): جملة صريحة في بداية النص."
                ),
                RevisionTrueFalseQuestion(
                    id = "d2_tf3",
                    statementFr = "3. Le médecin donne les médicaments à des heures fixes.",
                    statementAr = "الطبيب يعطي الأدوية في مواعيد محددة.",
                    isTrue = false,
                    explanationAr = "خطأ (Faux): الممرضة هي من تعطي الدواء (L'infirmière donne les médicaments)."
                ),
                RevisionTrueFalseQuestion(
                    id = "d2_tf4",
                    statementFr = "4. L'infirmière a un bureau près des malades.",
                    statementAr = "للممرضة مكتب بجوار المرضى.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): «Le bureau de l'infirmière est près des malades»."
                )
            ),
            completionQuestions = listOf(
                RevisionOpenQuestion(
                    id = "d2_op1",
                    promptFr = "1. Le médecin ............... les malades, il écrit une ............... .",
                    promptAr = "أكمل: الطبيب ..... المرضى، ويكتب ..... .",
                    modelAnswerFr = "examine / ordonnance",
                    modelAnswerAr = "يفحص (examine) / روشتة (ordonnance).",
                    hint = "examine - ordonnance"
                ),
                RevisionOpenQuestion(
                    id = "d2_op2",
                    promptFr = "2. Les visiteurs apportent des ............... pour les malades.",
                    promptAr = "أكمل: يُحضر الزائرون ..... للمرضى.",
                    modelAnswerFr = "fleurs",
                    modelAnswerAr = "زهوراً (fleurs).",
                    hint = "fleurs"
                )
            )
        ),

        // ==============================================================
        // Document (3) - Page 69 : Au restaurant
        // ==============================================================
        RevisionDocument(
            id = 3,
            pageNumber = "p. 69",
            titleFr = "Document (3) : Au restaurant",
            titleAr = "الوثيقة 3 : في المطعم",
            documentType = "Un dialogue (حوار)",
            textFr = """
Au restaurant
Le garçon : Bonjour monsieur, vous déjeunez au menu ou à la carte?
Le client : À la carte.
Le garçon : Que voulez-vous comme entrée?
Le client : Des tomates et des concombres en salade.
Le garçon : Et le plat principal?
Le client : De la viande, du riz et des frites.
Le garçon : Voulez-vous du dessert?
Le client : Oui, du gâteau.
            """.trimIndent(),
            textAr = """
في المطعم
النادل : مرحباً سيدي، هل تتناول الغداء وفقاً لقائمة الوجبات الثابتة (au menu) أم تختار أصنافاً محددة (à la carte)؟
الزبون : أصناف محددة من القائمة (À la carte).
النادل : ماذا تود كمقبلات؟
الزبون : طماطم وخيار في طبق سلطة.
النادل : والطبق الرئيسي؟
الزبون : لحم، وأرز، وبطاطس محمرة.
النادل : هل ترغب في تحلية؟
الزبون : نعم، قطعة جاتوه.
            """.trimIndent(),
            vocabulary = listOf(
                Pair("au menu", "وجبة محددة مسبقاً"),
                Pair("à la carte", "طلب أصناف مفردة"),
                Pair("comme entrée", "كمقبلات"),
                Pair("le plat principal", "الطبق الرئيسي"),
                Pair("le dessert", "الحلوى / التحلية")
            ),
            mcqQuestions = listOf(
                RevisionMcqQuestion(
                    id = "d3_q1",
                    questionFr = "1. Le client prend son repas ............... .",
                    questionAr = "يتناول الزبون وجبته :",
                    options = listOf("à la carte", "au menu", "au dîner"),
                    correctIndex = 0,
                    explanationAr = "سأله النادل فأجاب: «Le client : À la carte»."
                ),
                RevisionMcqQuestion(
                    id = "d3_q2",
                    questionFr = "2. Le client demande ............... comme plat principal.",
                    questionAr = "يطلب الزبون ..... كطبق رئيسي :",
                    options = listOf("du gâteau", "de la salade", "de la viande et du riz"),
                    correctIndex = 2,
                    explanationAr = "قال الزبون عن الطبق الرئيسي: «De la viande, du riz et des frites»."
                ),
                RevisionMcqQuestion(
                    id = "d3_q3",
                    questionFr = "3. Le client prend ............... .",
                    questionAr = "يتناول الزبون وجبة :",
                    options = listOf("le petit-déjeuner", "le déjeuner", "le dîner"),
                    correctIndex = 1,
                    explanationAr = "سأله النادل: «vous déjeunez...» ومشتقة من فعل déjeuner أي يتناول الغداء."
                )
            ),
            trueFalseQuestions = listOf(
                RevisionTrueFalseQuestion(
                    id = "d3_tf1",
                    statementFr = "1. Ce dialogue est dans un restaurant.",
                    statementAr = "يدور هذا الحوار في مطعم.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): العنوان والحوار بين النادل والزبون في المطعم (Au restaurant)."
                ),
                RevisionTrueFalseQuestion(
                    id = "d3_tf2",
                    statementFr = "2. Le client n'aime pas le gâteau.",
                    statementAr = "الزبون لا يحب الجاتوه.",
                    isTrue = false,
                    explanationAr = "خطأ (Faux): عندما سأله النادل عن التحلية قال الزبون بحماس: «Oui, du gâteau»."
                ),
                RevisionTrueFalseQuestion(
                    id = "d3_tf3",
                    statementFr = "3. Le client ne préfère pas les frites avec le repas.",
                    statementAr = "الزبون لا يفضل البطاطس المحمرة مع الوجبة.",
                    isTrue = false,
                    explanationAr = "خطأ (Faux): الزبون طلب البطاطس المحمرة صراحة: «De la viande, du riz et des frites»."
                ),
                RevisionTrueFalseQuestion(
                    id = "d3_tf4",
                    statementFr = "4. Comme entrée, le client demande une salade.",
                    statementAr = "كمقبلات، طلب الزبون سلطة.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): «Des tomates et des concombres en salade»."
                )
            ),
            completionQuestions = listOf(
                RevisionOpenQuestion(
                    id = "d3_op1",
                    promptFr = "Complète ce texte d'après le document:\nUn ....... va au ....... pour déjeuner. Il demande du ......., de la ....... et des ....... comme plat ....... .",
                    promptAr = "أكمل الملخص وفقاً للوثيقة:\nUn ....... va au ....... pour déjeuner...",
                    modelAnswerFr = "Un client va au restaurant pour déjeuner. Il demande du riz, de la viande et des frites comme plat principal.",
                    modelAnswerAr = "ذهب زبون (client) إلى المطعم (restaurant) ليتناول الغداء، وطلب أرزاً (riz)، ولحماً (viande)، وبطاطس (frites) كطبق رئيسي (principal).",
                    hint = "client, restaurant, riz, viande, frites, principal"
                )
            )
        ),

        // ==============================================================
        // Document (4) - Pages 70-71 : Dans la rue
        // ==============================================================
        RevisionDocument(
            id = 4,
            pageNumber = "p. 70-71",
            titleFr = "Document (4) : Dans la rue",
            titleAr = "الوثيقة 4 : في الشارع (حادث وانزلاق)",
            documentType = "Un dialogue (حوار)",
            textFr = """
Dans la rue
La femme : Ça va, monsieur? Qu'est-ce qui vous est arrivé?
L'homme : J'ai glissé et je suis tombé par terre.
La femme : Vous avez mal?
L'homme : Oui, j'ai mal à la jambe.
La femme : Vous pouvez marcher?
L'homme : Oui, ça va, mais ça fait un peu mal.
La femme : Vous voulez que j'appelle l'ambulance? J'ai mon téléphone portable.
L'homme : Non, merci madame. J'habite près d'ici, et il y a un médecin dans mon immeuble.
            """.trimIndent(),
            textAr = """
في الشارع
السيدة : هل أنت بخير يا سيدي؟ ماذا حدث لك؟
الرجل : تزلقت وسقطت على الأرض.
السيدة : هل تشعر بألم؟
الرجل : نعم، أشعر بألم في الساق.
السيدة : هل تستطيع المشي؟
الرجل : نعم، لا بأس، لكنها تؤلمني قليلاً.
السيدة : هل تريد أن أتصل بالإسعاف؟ معي هاتفي المحمول.
الرجل : لا، شكراً يا سيدتي. أنا أسكن بالقرب من هنا، وهناك طبيب في عماراتي.
            """.trimIndent(),
            vocabulary = listOf(
                Pair("glissé", "تزحلقت"),
                Pair("tombé par terre", "سقطت أرضاً"),
                Pair("l'ambulance", "عربة الإسعاف"),
                Pair("téléphone portable", "هاتف محمول"),
                Pair("immeuble", "عمارة سكنية")
            ),
            mcqQuestions = listOf(
                RevisionMcqQuestion(
                    id = "d4_q1",
                    questionFr = "1. Cette scène se passe ............... .",
                    questionAr = "يدور هذا المشهد في :",
                    options = listOf("dans une rue", "dans une maison", "dans un hôpital"),
                    correctIndex = 0,
                    explanationAr = "عنوان الوثيقة هو: «Dans la rue» (في الشارع)."
                ),
                RevisionMcqQuestion(
                    id = "d4_q2",
                    questionFr = "2. Cette scène se passe entre ............... .",
                    questionAr = "يدور المشهد بين :",
                    options = listOf("un homme et une femme", "deux hommes", "deux femmes"),
                    correctIndex = 0,
                    explanationAr = "أطراف الحوار هما: «La femme» و «L'homme»."
                )
            ),
            trueFalseQuestions = listOf(
                RevisionTrueFalseQuestion(
                    id = "d4_tf1",
                    statementFr = "1. La femme est tombée dans la rue.",
                    statementAr = "سقطت السيدة في الشارع.",
                    isTrue = false,
                    explanationAr = "خطأ (Faux): الرجل هو الذي انزلق وسقط (L'homme : J'ai glissé et je suis tombé par terre)."
                ),
                RevisionTrueFalseQuestion(
                    id = "d4_tf2",
                    statementFr = "2. L'homme a mal à la jambe.",
                    statementAr = "الرجل يشعر بألم في ساقه.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): «L'homme : Oui, j'ai mal à la jambe»."
                ),
                RevisionTrueFalseQuestion(
                    id = "d4_tf3",
                    statementFr = "3. La femme a appelé l'ambulance.",
                    statementAr = "اتصلت السيدة بالإسعاف.",
                    isTrue = false,
                    explanationAr = "خطأ (Faux): هي عرضت الاتصال لكن الرجل رفض شاكراً: «Non, merci madame»."
                ),
                RevisionTrueFalseQuestion(
                    id = "d4_tf4",
                    statementFr = "4. Le voisin de cet homme est médecin.",
                    statementAr = "جار هذا الرجل طبيب.",
                    isTrue = true,
                    explanationAr = "صحيح (Vrai): «il y a un médecin dans mon immeuble» (في عمارته أي جاره)."
                ),
                RevisionTrueFalseQuestion(
                    id = "d4_tf5",
                    statementFr = "5. L'homme ne peut pas marcher.",
                    statementAr = "الرجل لا يستطيع المشي إطلاقاً.",
                    isTrue = false,
                    explanationAr = "خطأ (Faux): عندما سألته هل تستطيع المشي قال: «Oui, ça va, mais ça fait un peu mal»."
                )
            ),
            completionQuestions = listOf(
                RevisionOpenQuestion(
                    id = "d4_op1",
                    promptFr = "1. Qui peut dire: « Ça vous fait mal? »",
                    promptAr = "من القائل: « Ça vous fait mal? » (هل يؤلمك؟)؟",
                    modelAnswerFr = "La femme",
                    modelAnswerAr = "السيدة (La femme)",
                    hint = "La femme"
                ),
                RevisionOpenQuestion(
                    id = "d4_op2",
                    promptFr = "2. Qui peut dire: « J'habite près d'ici. »",
                    promptAr = "من القائل: « J'habite près d'ici. » (أنا أسكن بالقرب من هنا)؟",
                    modelAnswerFr = "L'homme",
                    modelAnswerAr = "الرجل (L'homme)",
                    hint = "L'homme"
                )
            )
        )
    )
}
