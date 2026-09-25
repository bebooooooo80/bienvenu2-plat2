package com.example

import com.example.data.*
import org.junit.Test
import java.io.File

class ExportDataTest {

    private fun escape(s: String?): String {
        if (s == null) return "null"
        val sb = StringBuilder()
        for (c in s) {
            when (c) {
                '\\' -> sb.append("\\\\")
                '"' -> sb.append("\\\"")
                '\b' -> sb.append("\\b")
                '\u000C' -> sb.append("\\f")
                '\n' -> sb.append("\\n")
                '\r' -> sb.append("\\r")
                '\t' -> sb.append("\\t")
                else -> if (c < ' ') sb.append(String.format("\\u%04x", c.code)) else sb.append(c)
            }
        }
        return "\"" + sb.toString() + "\""
    }

    private fun strList(list: List<String>): String =
        "[" + list.joinToString(",") { escape(it) } + "]"

    @Test
    fun exportAllData() {
        val outDir = File("exported_data")
        outDir.mkdirs()

        // 1. RevisionData
        val revSb = StringBuilder()
        revSb.append("[\n")
        RevisionData.documents.forEachIndexed { idx, doc ->
            if (idx > 0) revSb.append(",\n")
            revSb.append("  {\n")
            revSb.append("    \"id\": ${doc.id},\n")
            revSb.append("    \"pageNumber\": ${escape(doc.pageNumber)},\n")
            revSb.append("    \"titleFr\": ${escape(doc.titleFr)},\n")
            revSb.append("    \"titleAr\": ${escape(doc.titleAr)},\n")
            revSb.append("    \"documentType\": ${escape(doc.documentType)},\n")
            revSb.append("    \"textFr\": ${escape(doc.textFr)},\n")
            revSb.append("    \"textAr\": ${escape(doc.textAr)},\n")
            revSb.append("    \"vocabulary\": [\n")
            doc.vocabulary.forEachIndexed { vIdx, v ->
                if (vIdx > 0) revSb.append(",\n")
                revSb.append("      {\"first\": ${escape(v.first)}, \"second\": ${escape(v.second)}}")
            }
            revSb.append("\n    ],\n")

            revSb.append("    \"mcqQuestions\": [\n")
            doc.mcqQuestions.forEachIndexed { mIdx, q ->
                if (mIdx > 0) revSb.append(",\n")
                revSb.append("      {\n")
                revSb.append("        \"id\": ${escape(q.id)},\n")
                revSb.append("        \"questionFr\": ${escape(q.questionFr)},\n")
                revSb.append("        \"questionAr\": ${escape(q.questionAr)},\n")
                revSb.append("        \"options\": ${strList(q.options)},\n")
                revSb.append("        \"correctIndex\": ${q.correctIndex},\n")
                revSb.append("        \"explanationAr\": ${escape(q.explanationAr)}\n")
                revSb.append("      }")
            }
            revSb.append("\n    ],\n")

            revSb.append("    \"trueFalseQuestions\": [\n")
            doc.trueFalseQuestions.forEachIndexed { tIdx, q ->
                if (tIdx > 0) revSb.append(",\n")
                revSb.append("      {\n")
                revSb.append("        \"id\": ${escape(q.id)},\n")
                revSb.append("        \"statementFr\": ${escape(q.statementFr)},\n")
                revSb.append("        \"statementAr\": ${escape(q.statementAr)},\n")
                revSb.append("        \"isTrue\": ${q.isTrue},\n")
                revSb.append("        \"explanationAr\": ${escape(q.explanationAr)}\n")
                revSb.append("      }")
            }
            revSb.append("\n    ],\n")

            revSb.append("    \"completionQuestions\": [\n")
            doc.completionQuestions.forEachIndexed { cIdx, q ->
                if (cIdx > 0) revSb.append(",\n")
                revSb.append("      {\n")
                revSb.append("        \"id\": ${escape(q.id)},\n")
                revSb.append("        \"promptFr\": ${escape(q.promptFr)},\n")
                revSb.append("        \"promptAr\": ${escape(q.promptAr)},\n")
                revSb.append("        \"modelAnswerFr\": ${escape(q.modelAnswerFr)},\n")
                revSb.append("        \"modelAnswerAr\": ${escape(q.modelAnswerAr)},\n")
                revSb.append("        \"hint\": ${escape(q.hint)}\n")
                revSb.append("      }")
            }
            revSb.append("\n    ]\n")
            revSb.append("  }")
        }
        revSb.append("\n]")
        File(outDir, "revision_documents.json").writeText(revSb.toString())

        // 2. RevisionGrammaireData
        val rgSb = StringBuilder()
        rgSb.append("[\n")
        RevisionGrammaireData.allExercises.forEachIndexed { eIdx, ex ->
            if (eIdx > 0) rgSb.append(",\n")
            rgSb.append("  {\n")
            rgSb.append("    \"id\": ${ex.id},\n")
            rgSb.append("    \"pageNumber\": ${escape(ex.pageNumber)},\n")
            rgSb.append("    \"titleFr\": ${escape(ex.titleFr)},\n")
            rgSb.append("    \"titleAr\": ${escape(ex.titleAr)},\n")
            rgSb.append("    \"instructionFr\": ${escape(ex.instructionFr)},\n")
            rgSb.append("    \"instructionAr\": ${escape(ex.instructionAr)},\n")
            rgSb.append("    \"ruleSummaryAr\": ${escape(ex.ruleSummaryAr)},\n")
            rgSb.append("    \"items\": [\n")
            ex.items.forEachIndexed { iIdx, itm ->
                if (iIdx > 0) rgSb.append(",\n")
                rgSb.append("      {\n")
                rgSb.append("        \"id\": ${escape(itm.id)},\n")
                rgSb.append("        \"sentenceFr\": ${escape(itm.sentenceFr)},\n")
                rgSb.append("        \"sentenceAr\": ${escape(itm.sentenceAr)},\n")
                rgSb.append("        \"underlinedPart\": ${escape(itm.underlinedPart)},\n")
                rgSb.append("        \"options\": ${strList(itm.options)},\n")
                rgSb.append("        \"correctIndex\": ${itm.correctIndex},\n")
                rgSb.append("        \"answerFr\": ${escape(itm.answerFr)},\n")
                rgSb.append("        \"fullTransformedSentenceFr\": ${escape(itm.fullTransformedSentenceFr)},\n")
                rgSb.append("        \"fullTransformedSentenceAr\": ${escape(itm.fullTransformedSentenceAr)},\n")
                rgSb.append("        \"explanationAr\": ${escape(itm.explanationAr)}\n")
                rgSb.append("      }")
            }
            rgSb.append("\n    ]\n")
            rgSb.append("  }")
        }
        rgSb.append("\n]")
        File(outDir, "revision_grammaire.json").writeText(rgSb.toString())

        // 3. MidYearExamData
        val examSb = StringBuilder()
        examSb.append("{\n")
        examSb.append("  \"documentTitleFr\": ${escape(MidYearExamData.documentTitleFr)},\n")
        examSb.append("  \"documentTitleAr\": ${escape(MidYearExamData.documentTitleAr)},\n")
        examSb.append("  \"documentFullTextFr\": ${escape(MidYearExamData.documentFullTextFr)},\n")
        examSb.append("  \"documentFullTextAr\": ${escape(MidYearExamData.documentFullTextAr)},\n")

        examSb.append("  \"documentLines\": [\n")
        MidYearExamData.documentLines.forEachIndexed { lIdx, line ->
            if (lIdx > 0) examSb.append(",\n")
            examSb.append("    {\"speaker\": ${escape(line.speaker)}, \"textFr\": ${escape(line.textFr)}, \"textAr\": ${escape(line.textAr)}}")
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"docPartAQuestions\": [\n")
        MidYearExamData.docPartAQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) examSb.append(",\n")
            examSb.append("    {\"id\": ${escape(q.id)}, \"questionFr\": ${escape(q.questionFr)}, \"questionAr\": ${escape(q.questionAr)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"points\": ${q.points}, \"explanationFr\": ${escape(q.explanationFr)}, \"explanationAr\": ${escape(q.explanationAr)}}")
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"docPartBQuestions\": [\n")
        MidYearExamData.docPartBQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) examSb.append(",\n")
            examSb.append("    {\"id\": ${escape(q.id)}, \"statementFr\": ${escape(q.statementFr)}, \"statementAr\": ${escape(q.statementAr)}, \"isTrue\": ${q.isTrue}, \"points\": ${q.points}, \"explanationFr\": ${escape(q.explanationFr)}, \"explanationAr\": ${escape(q.explanationAr)}}")
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"docPartCQuestions\": [\n")
        MidYearExamData.docPartCQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) examSb.append(",\n")
            examSb.append("    {\"id\": ${escape(q.id)}, \"sentenceTemplateFr\": ${escape(q.sentenceTemplateFr)}, \"blanksWords\": ${strList(q.blanksWords)}, \"wordBank\": ${strList(q.wordBank)}, \"sentenceAr\": ${escape(q.sentenceAr)}, \"points\": ${q.points}, \"fullSentenceFr\": ${escape(q.fullSentenceFr)}}")
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"gramPartAQuestions\": [\n")
        MidYearExamData.gramPartAQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) examSb.append(",\n")
            examSb.append("    {\"id\": ${escape(q.id)}, \"promptFr\": ${escape(q.promptFr)}, \"verb\": ${escape(q.verb)}, \"subject\": ${escape(q.subject)}, \"correctAnswer\": ${escape(q.correctAnswer)}, \"points\": ${q.points}, \"ruleExplanationAr\": ${escape(q.ruleExplanationAr)}}")
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"gramPartBQuestions\": [\n")
        MidYearExamData.gramPartBQuestions.forEachIndexed { qIdx, item ->
            if (qIdx > 0) examSb.append(",\n")
            when (item) {
                is ExamMCQQuestion -> {
                    examSb.append("    {\"type\": \"mcq\", \"id\": ${escape(item.id)}, \"questionFr\": ${escape(item.questionFr)}, \"questionAr\": ${escape(item.questionAr)}, \"options\": ${strList(item.options)}, \"correctIndex\": ${item.correctIndex}, \"points\": ${item.points}, \"explanationFr\": ${escape(item.explanationFr)}, \"explanationAr\": ${escape(item.explanationAr)}}")
                }
                is ExamTransformQuestion -> {
                    examSb.append("    {\"type\": \"transform\", \"id\": ${escape(item.id)}, \"originalFr\": ${escape(item.originalFr)}, \"instructionFr\": ${escape(item.instructionFr)}, \"instructionAr\": ${escape(item.instructionAr)}, \"targetWord\": ${escape(item.targetWord)}, \"expectedAnswerFr\": ${escape(item.expectedAnswerFr)}, \"points\": ${item.points}, \"explanationAr\": ${escape(item.explanationAr)}, \"options\": ${strList(item.options)}}")
                }
                else -> {}
            }
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"gramPartCQuestions\": [\n")
        MidYearExamData.gramPartCQuestions.forEachIndexed { qIdx, item ->
            if (qIdx > 0) examSb.append(",\n")
            examSb.append("    {\"id\": ${escape(item.id)}, \"originalFr\": ${escape(item.originalFr)}, \"instructionFr\": ${escape(item.instructionFr)}, \"instructionAr\": ${escape(item.instructionAr)}, \"targetWord\": ${escape(item.targetWord)}, \"expectedAnswerFr\": ${escape(item.expectedAnswerFr)}, \"points\": ${item.points}, \"explanationAr\": ${escape(item.explanationAr)}, \"options\": ${strList(item.options)}}")
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"prodOptions\": ${strList(MidYearExamData.prodOptions)},\n")

        examSb.append("  \"productionQuestions\": [\n")
        MidYearExamData.productionQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) examSb.append(",\n")
            examSb.append("    {\"id\": ${escape(q.id)}, \"purposeFr\": ${escape(q.purposeFr)}, \"purposeAr\": ${escape(q.purposeAr)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"modelSentenceFr\": ${escape(q.modelSentenceFr)}, \"points\": ${q.points}}")
        }
        examSb.append("\n  ],\n")

        examSb.append("  \"situationsQuestions\": [\n")
        MidYearExamData.situationsQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) examSb.append(",\n")
            examSb.append("    {\"id\": ${escape(q.id)}, \"situationFr\": ${escape(q.situationFr)}, \"situationAr\": ${escape(q.situationAr)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"points\": ${q.points}, \"explanationAr\": ${escape(q.explanationAr)}}")
        }
        examSb.append("\n  ]\n")
        examSb.append("}")
        File(outDir, "mid_year_exam.json").writeText(examSb.toString())

        // 4. BookletLieuxEtExercicesData
        val bkSb = StringBuilder()
        bkSb.append("{\n")
        bkSb.append("  \"personnagesList\": [\n")
        BookletLieuxEtExercicesData.personnagesList.forEachIndexed { pIdx, p ->
            if (pIdx > 0) bkSb.append(",\n")
            bkSb.append("    {\"id\": ${escape(p.id)}, \"french\": ${escape(p.french)}, \"arabic\": ${escape(p.arabic)}, \"gender\": ${escape(p.gender)}, \"typicalPlaces\": ${strList(p.typicalPlaces)}, \"emoji\": ${escape(p.emoji)}, \"exampleSentenceFr\": ${escape(p.exampleSentenceFr)}, \"exampleSentenceAr\": ${escape(p.exampleSentenceAr)}}")
        }
        bkSb.append("\n  ],\n")

        bkSb.append("  \"lieuxList\": [\n")
        BookletLieuxEtExercicesData.lieuxList.forEachIndexed { lIdx, l ->
            if (lIdx > 0) bkSb.append(",\n")
            bkSb.append("    {\"id\": ${escape(l.id)}, \"frenchWithPreposition\": ${escape(l.frenchWithPreposition)}, \"placeNameOnly\": ${escape(l.placeNameOnly)}, \"preposition\": ${escape(l.preposition)}, \"arabic\": ${escape(l.arabic)}, \"emoji\": ${escape(l.emoji)}, \"typicalCharacters\": ${strList(l.typicalCharacters)}, \"explanationAr\": ${escape(l.explanationAr)}}")
        }
        bkSb.append("\n  ],\n")

        bkSb.append("  \"ouVasTuItems\": [\n")
        BookletLieuxEtExercicesData.ouVasTuItems.forEachIndexed { oIdx, itm ->
            if (oIdx > 0) bkSb.append(",\n")
            bkSb.append("    {\"id\": ${escape(itm.id)}, \"number\": ${itm.number}, \"activityFr\": ${escape(itm.activityFr)}, \"activityAr\": ${escape(itm.activityAr)}, \"expectedAnswerFr\": ${escape(itm.expectedAnswerFr)}, \"alternativeAnswersFr\": ${strList(itm.alternativeAnswersFr)}, \"answerAr\": ${escape(itm.answerAr)}, \"options\": ${strList(itm.options)}, \"explanationAr\": ${escape(itm.explanationAr)}}")
        }
        bkSb.append("\n  ],\n")

        bkSb.append("  \"quiParleItems\": [\n")
        BookletLieuxEtExercicesData.quiParleItems.forEachIndexed { qIdx, itm ->
            if (qIdx > 0) bkSb.append(",\n")
            bkSb.append("    {\"id\": ${escape(itm.id)}, \"number\": ${itm.number}, \"quoteFr\": ${escape(itm.quoteFr)}, \"quoteAr\": ${escape(itm.quoteAr)}, \"speakerFr\": ${escape(itm.speakerFr)}, \"alternativeSpeakersFr\": ${strList(itm.alternativeSpeakersFr)}, \"speakerAr\": ${escape(itm.speakerAr)}, \"options\": ${strList(itm.options)}, \"situationContextAr\": ${escape(itm.situationContextAr)}}")
        }
        bkSb.append("\n  ],\n")

        bkSb.append("  \"quiFaitCeTravailItems\": [\n")
        BookletLieuxEtExercicesData.quiFaitCeTravailItems.forEachIndexed { qIdx, itm ->
            if (qIdx > 0) bkSb.append(",\n")
            bkSb.append("    {\"id\": ${escape(itm.id)}, \"number\": ${itm.number}, \"actionFr\": ${escape(itm.actionFr)}, \"actionAr\": ${escape(itm.actionAr)}, \"professionFr\": ${escape(itm.professionFr)}, \"alternativeProfessionsFr\": ${strList(itm.alternativeProfessionsFr)}, \"professionAr\": ${escape(itm.professionAr)}, \"options\": ${strList(itm.options)}, \"descriptionAr\": ${escape(itm.descriptionAr)}}")
        }
        bkSb.append("\n  ]\n")
        bkSb.append("}")
        File(outDir, "booklet_lieux_exercices.json").writeText(bkSb.toString())

        // 5. FrenchCourseData
        val fcSb = StringBuilder()
        fcSb.append("{\n")
        fcSb.append("  \"unit1FullText\": ${escape(FrenchCourseData.unit1FullText)},\n")
        fcSb.append("  \"unit1FullTextArabic\": ${escape(FrenchCourseData.unit1FullTextArabic)},\n")
        
        fcSb.append("  \"unit1Dialogues\": [\n")
        FrenchCourseData.unit1Dialogues.forEachIndexed { dIdx, d ->
            if (dIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"speaker\": ${escape(d.speaker)}, \"text\": ${escape(d.text)}, \"arabicNote\": ${escape(d.arabicNote)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1Ex2RepondsAuxQuestions\": [\n")
        FrenchCourseData.unit1Ex2RepondsAuxQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1Ex3VraiOuFaux\": [\n")
        FrenchCourseData.unit1Ex3VraiOuFaux.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1Ex4Associe\": [\n")
        FrenchCourseData.unit1Ex4Associe.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1Ex5Complete\": [\n")
        FrenchCourseData.unit1Ex5Complete.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1VocabWords\": [\n")
        FrenchCourseData.unit1VocabWords.forEachIndexed { vIdx, v ->
            if (vIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(v.id)}, \"french\": ${escape(v.french)}, \"arabic\": ${escape(v.arabic)}, \"category\": ${escape(v.category.name)}, \"exampleFr\": ${escape(v.exampleFr)}, \"exampleAr\": ${escape(v.exampleAr)}, \"phoneticOrNote\": ${escape(v.phoneticOrNote)}, \"pageReference\": ${escape(v.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1BanqueDesMots\": [\n")
        FrenchCourseData.unit1BanqueDesMots.forEachIndexed { vIdx, v ->
            if (vIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(v.id)}, \"french\": ${escape(v.french)}, \"arabic\": ${escape(v.arabic)}, \"category\": ${escape(v.category.name)}, \"exampleFr\": ${escape(v.exampleFr)}, \"exampleAr\": ${escape(v.exampleAr)}, \"phoneticOrNote\": ${escape(v.phoneticOrNote)}, \"pageReference\": ${escape(v.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1Situations\": [\n")
        FrenchCourseData.unit1Situations.forEachIndexed { sIdx, s ->
            if (sIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\n")
            fcSb.append("      \"id\": ${escape(s.id)},\n")
            fcSb.append("      \"number\": ${s.number},\n")
            fcSb.append("      \"promptFr\": ${escape(s.promptFr)},\n")
            fcSb.append("      \"promptAr\": ${escape(s.promptAr)},\n")
            fcSb.append("      \"categoryAr\": ${escape(s.categoryAr)},\n")
            fcSb.append("      \"categoryFr\": ${escape(s.categoryFr)},\n")
            fcSb.append("      \"goldenRule\": ${escape(s.goldenRule)},\n")
            fcSb.append("      \"rolePlaySpeaker\": ${escape(s.rolePlaySpeaker)},\n")
            fcSb.append("      \"rolePlayMessage\": ${escape(s.rolePlayMessage)},\n")
            fcSb.append("      \"options\": [\n")
            s.options.forEachIndexed { oIdx, opt ->
                if (oIdx > 0) fcSb.append(",\n")
                fcSb.append("        {\"textFr\": ${escape(opt.textFr)}, \"textAr\": ${escape(opt.textAr)}, \"isCorrect\": ${opt.isCorrect}, \"explanation\": ${escape(opt.explanation)}}")
            }
            fcSb.append("\n      ]\n")
            fcSb.append("    }")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1CompositionTopic\": {\n")
        fcSb.append("    \"id\": ${escape(FrenchCourseData.unit1CompositionTopic.id)},\n")
        fcSb.append("    \"titleFr\": ${escape(FrenchCourseData.unit1CompositionTopic.titleFr)},\n")
        fcSb.append("    \"titleAr\": ${escape(FrenchCourseData.unit1CompositionTopic.titleAr)},\n")
        fcSb.append("    \"instructionFr\": ${escape(FrenchCourseData.unit1CompositionTopic.instructionFr)},\n")
        fcSb.append("    \"instructionAr\": ${escape(FrenchCourseData.unit1CompositionTopic.instructionAr)},\n")
        fcSb.append("    \"pageReference\": ${escape(FrenchCourseData.unit1CompositionTopic.pageReference)},\n")
        fcSb.append("    \"requiredElements\": [\n")
        FrenchCourseData.unit1CompositionTopic.requiredElements.forEachIndexed { rIdx, req ->
            if (rIdx > 0) fcSb.append(",\n")
            fcSb.append("      {\"first\": ${escape(req.first)}, \"second\": ${escape(req.second)}}")
        }
        fcSb.append("\n    ],\n")
        fcSb.append("    \"sentences\": [\n")
        FrenchCourseData.unit1CompositionTopic.sentences.forEachIndexed { sIdx, sent ->
            if (sIdx > 0) fcSb.append(",\n")
            fcSb.append("      {\"order\": ${sent.order}, \"french\": ${escape(sent.french)}, \"arabic\": ${escape(sent.arabic)}, \"hint\": ${escape(sent.hint)}}")
        }
        fcSb.append("\n    ],\n")
        fcSb.append("    \"scrambleWords\": ${strList(FrenchCourseData.unit1CompositionTopic.scrambleWords)},\n")
        fcSb.append("    \"fillBlankText\": ${escape(FrenchCourseData.unit1CompositionTopic.fillBlankText)},\n")
        fcSb.append("    \"fillBlankSolutions\": ${strList(FrenchCourseData.unit1CompositionTopic.fillBlankSolutions)}\n")
        fcSb.append("  },\n")

        fcSb.append("  \"unit1PossessiveExercises\": [\n")
        FrenchCourseData.unit1PossessiveExercises.forEachIndexed { pIdx, p ->
            if (pIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(p.id)}, \"number\": ${p.number}, \"sentenceParts\": ${strList(p.sentenceParts)}, \"targetBlanks\": ${strList(p.targetBlanks)}, \"subject\": ${escape(p.subject)}, \"fullSentenceFr\": ${escape(p.fullSentenceFr)}, \"translationAr\": ${escape(p.translationAr)}, \"explanationAr\": ${escape(p.explanationAr)}, \"isVowelRule\": ${p.isVowelRule}, \"pageReference\": ${escape(p.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1PronomOnChoisisExercises\": [\n")
        FrenchCourseData.unit1PronomOnChoisisExercises.forEachIndexed { pIdx, p ->
            if (pIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(p.id)}, \"number\": ${p.number}, \"predicateFr\": ${escape(p.predicateFr)}, \"correctAnswer\": ${escape(p.correctAnswer)}, \"fullSentenceFr\": ${escape(p.fullSentenceFr)}, \"translationAr\": ${escape(p.translationAr)}, \"explanationAr\": ${escape(p.explanationAr)}, \"verbAnalyzed\": ${escape(p.verbAnalyzed)}, \"pageReference\": ${escape(p.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1PronomOnReplaceExercises\": [\n")
        FrenchCourseData.unit1PronomOnReplaceExercises.forEachIndexed { pIdx, p ->
            if (pIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(p.id)}, \"number\": ${p.number}, \"onSentenceFr\": ${escape(p.onSentenceFr)}, \"nousSentenceFr\": ${escape(p.nousSentenceFr)}, \"onSentenceAr\": ${escape(p.onSentenceAr)}, \"nousSentenceAr\": ${escape(p.nousSentenceAr)}, \"verbTransformation\": ${escape(p.verbTransformation)}, \"pageReference\": ${escape(p.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"bookletPronounsOfficial15Exercises\": [\n")
        FrenchCourseData.bookletPronounsOfficial15Exercises.forEachIndexed { pIdx, p ->
            if (pIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(p.id)}, \"number\": ${p.number}, \"fullSentenceFr\": ${escape(p.fullSentenceFr)}, \"underlinedPart\": ${escape(p.underlinedPart)}, \"category\": ${escape(p.category.name)}, \"targetPronoun\": ${escape(p.targetPronoun)}, \"options\": ${strList(p.options)}, \"transformedSentenceFr\": ${escape(p.transformedSentenceFr)}, \"sentenceAr\": ${escape(p.sentenceAr)}, \"transformedSentenceAr\": ${escape(p.transformedSentenceAr)}, \"explanationAr\": ${escape(p.explanationAr)}, \"ruleTag\": ${escape(p.ruleTag)}, \"positionRuleAr\": ${escape(p.positionRuleAr)}, \"pageReference\": ${escape(p.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit1FaisDesPhrasesList\": [\n")
        FrenchCourseData.unit1FaisDesPhrasesList.forEachIndexed { fIdx, f ->
            if (fIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(f.id)}, \"number\": ${f.number}, \"promptFr\": ${escape(f.promptFr)}, \"promptAr\": ${escape(f.promptAr)}, \"verbInfinitive\": ${escape(f.verbInfinitive)}, \"complement\": ${escape(f.complement)}, \"modelSentenceFr\": ${escape(f.modelSentenceFr)}, \"modelSentenceAr\": ${escape(f.modelSentenceAr)}, \"scrambledWords\": ${strList(f.scrambledWords)}, \"correctOrder\": ${strList(f.correctOrder)}, \"grammarTipAr\": ${escape(f.grammarTipAr)}, \"pageReference\": ${escape(f.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        // Unit 2
        fcSb.append("  \"unit2FullText\": ${escape(FrenchCourseData.unit2FullText)},\n")
        fcSb.append("  \"unit2FullTextArabic\": ${escape(FrenchCourseData.unit2FullTextArabic)},\n")
        fcSb.append("  \"unit2Dialogues\": [\n")
        FrenchCourseData.unit2Dialogues.forEachIndexed { dIdx, d ->
            if (dIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"speaker\": ${escape(d.speaker)}, \"text\": ${escape(d.text)}, \"arabicNote\": ${escape(d.arabicNote)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2Ex1BonGroupe\": [\n")
        FrenchCourseData.unit2Ex1BonGroupe.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2Ex2VraiOuFaux\": [\n")
        FrenchCourseData.unit2Ex2VraiOuFaux.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2Ex3RepondsPrisDuTexte\": [\n")
        FrenchCourseData.unit2Ex3RepondsPrisDuTexte.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2Ex4Complete\": [\n")
        FrenchCourseData.unit2Ex4Complete.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2VocabWords\": [\n")
        FrenchCourseData.unit2VocabWords.forEachIndexed { vIdx, v ->
            if (vIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(v.id)}, \"french\": ${escape(v.french)}, \"arabic\": ${escape(v.arabic)}, \"category\": ${escape(v.category.name)}, \"exampleFr\": ${escape(v.exampleFr)}, \"exampleAr\": ${escape(v.exampleAr)}, \"phoneticOrNote\": ${escape(v.phoneticOrNote)}, \"pageReference\": ${escape(v.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"partitiveQuestions\": [\n")
        FrenchCourseData.partitiveQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"interrogativeQuestions\": [\n")
        FrenchCourseData.interrogativeQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2QuiParleItems\": [\n")
        FrenchCourseData.unit2QuiParleItems.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"number\": ${q.number}, \"quoteFr\": ${escape(q.quoteFr)}, \"quoteAr\": ${escape(q.quoteAr)}, \"speakerFr\": ${escape(q.speakerFr)}, \"alternativeSpeakersFr\": ${strList(q.alternativeSpeakersFr)}, \"speakerAr\": ${escape(q.speakerAr)}, \"options\": ${strList(q.options)}, \"situationContextAr\": ${escape(q.situationContextAr)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2OuVasTuItems\": [\n")
        FrenchCourseData.unit2OuVasTuItems.forEachIndexed { oIdx, o ->
            if (oIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(o.id)}, \"number\": ${o.number}, \"activityFr\": ${escape(o.activityFr)}, \"activityAr\": ${escape(o.activityAr)}, \"expectedAnswerFr\": ${escape(o.expectedAnswerFr)}, \"alternativeAnswersFr\": ${strList(o.alternativeAnswersFr)}, \"answerAr\": ${escape(o.answerAr)}, \"options\": ${strList(o.options)}, \"explanationAr\": ${escape(o.explanationAr)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2FaisDesPhrasesItems\": [\n")
        FrenchCourseData.unit2FaisDesPhrasesItems.forEachIndexed { fIdx, f ->
            if (fIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(f.id)}, \"number\": ${f.number}, \"promptFr\": ${escape(f.promptFr)}, \"promptAr\": ${escape(f.promptAr)}, \"verbInfinitive\": ${escape(f.verbInfinitive)}, \"complement\": ${escape(f.complement)}, \"modelSentenceFr\": ${escape(f.modelSentenceFr)}, \"modelSentenceAr\": ${escape(f.modelSentenceAr)}, \"scrambledWords\": ${strList(f.scrambledWords)}, \"correctOrder\": ${strList(f.correctOrder)}, \"grammarTipAr\": ${escape(f.grammarTipAr)}, \"pageReference\": ${escape(f.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2CompositionTopic\": {\n")
        fcSb.append("    \"id\": ${escape(FrenchCourseData.unit2CompositionTopic.id)},\n")
        fcSb.append("    \"titleFr\": ${escape(FrenchCourseData.unit2CompositionTopic.titleFr)},\n")
        fcSb.append("    \"titleAr\": ${escape(FrenchCourseData.unit2CompositionTopic.titleAr)},\n")
        fcSb.append("    \"instructionFr\": ${escape(FrenchCourseData.unit2CompositionTopic.instructionFr)},\n")
        fcSb.append("    \"instructionAr\": ${escape(FrenchCourseData.unit2CompositionTopic.instructionAr)},\n")
        fcSb.append("    \"pageReference\": ${escape(FrenchCourseData.unit2CompositionTopic.pageReference)},\n")
        fcSb.append("    \"requiredElements\": [\n")
        FrenchCourseData.unit2CompositionTopic.requiredElements.forEachIndexed { rIdx, req ->
            if (rIdx > 0) fcSb.append(",\n")
            fcSb.append("      {\"first\": ${escape(req.first)}, \"second\": ${escape(req.second)}}")
        }
        fcSb.append("\n    ],\n")
        fcSb.append("    \"sentences\": [\n")
        FrenchCourseData.unit2CompositionTopic.sentences.forEachIndexed { sIdx, sent ->
            if (sIdx > 0) fcSb.append(",\n")
            fcSb.append("      {\"order\": ${sent.order}, \"french\": ${escape(sent.french)}, \"arabic\": ${escape(sent.arabic)}, \"hint\": ${escape(sent.hint)}}")
        }
        fcSb.append("\n    ],\n")
        fcSb.append("    \"scrambleWords\": ${strList(FrenchCourseData.unit2CompositionTopic.scrambleWords)},\n")
        fcSb.append("    \"fillBlankText\": ${escape(FrenchCourseData.unit2CompositionTopic.fillBlankText)},\n")
        fcSb.append("    \"fillBlankSolutions\": ${strList(FrenchCourseData.unit2CompositionTopic.fillBlankSolutions)}\n")
        fcSb.append("  },\n")

        fcSb.append("  \"unit2RepasList\": [\n")
        FrenchCourseData.unit2RepasList.forEachIndexed { rIdx, r ->
            if (rIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"first\": ${escape(r.first)}, \"second\": ${escape(r.second)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2RepasItems\": [\n")
        FrenchCourseData.unit2RepasItems.forEachIndexed { rIdx, r ->
            if (rIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(r.id)}, \"frenchWithArticle\": ${escape(r.frenchWithArticle)}, \"frenchBase\": ${escape(r.frenchBase)}, \"articlePartitive\": ${escape(r.articlePartitive)}, \"genderAr\": ${escape(r.genderAr)}, \"arabic\": ${escape(r.arabic)}, \"itemType\": ${escape(r.itemType)}, \"mealType\": ${escape(r.mealType.name)}, \"courseCategory\": ${escape(r.courseCategory)}, \"emoji\": ${escape(r.emoji)}, \"sampleSentenceFr\": ${escape(r.sampleSentenceFr)}, \"sampleSentenceAr\": ${escape(r.sampleSentenceAr)}, \"pageReference\": ${escape(r.pageReference)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit2RepasQuizList\": [\n")
        FrenchCourseData.unit2RepasQuizList.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"questionFr\": ${escape(q.questionFr)}, \"questionAr\": ${escape(q.questionAr)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanationAr\": ${escape(q.explanationAr)}, \"tip\": ${escape(q.tip)}}")
        }
        fcSb.append("\n  ],\n")

        // Unit 3
        fcSb.append("  \"unit3FullText\": ${escape(FrenchCourseData.unit3FullText)},\n")
        fcSb.append("  \"unit3FullTextArabic\": ${escape(FrenchCourseData.unit3FullTextArabic)},\n")
        fcSb.append("  \"unit3Page59Text\": ${escape(FrenchCourseData.unit3Page59Text)},\n")
        fcSb.append("  \"unit3Page59TextArabic\": ${escape(FrenchCourseData.unit3Page59TextArabic)},\n")
        fcSb.append("  \"unit3Page60Text\": ${escape(FrenchCourseData.unit3Page60Text)},\n")
        fcSb.append("  \"unit3Page60TextArabic\": ${escape(FrenchCourseData.unit3Page60TextArabic)},\n")
        fcSb.append("  \"unit3Dialogues\": [\n")
        FrenchCourseData.unit3Dialogues.forEachIndexed { dIdx, d ->
            if (dIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"speaker\": ${escape(d.speaker)}, \"text\": ${escape(d.text)}, \"arabicNote\": ${escape(d.arabicNote)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit3Ex1VraiOuFaux\": [\n")
        FrenchCourseData.unit3Ex1VraiOuFaux.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"unit3Ex2QuestionsReponses\": [\n")
        FrenchCourseData.unit3Ex2QuestionsReponses.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"bodyPainQuestions\": [\n")
        FrenchCourseData.bodyPainQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"medicalSituations\": [\n")
        FrenchCourseData.medicalSituations.forEachIndexed { sIdx, s ->
            if (sIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(s.id)}, \"question\": ${escape(s.question)}, \"options\": ${strList(s.options)}, \"correctIndex\": ${s.correctIndex}, \"explanation\": ${escape(s.explanation)}, \"arabicTranslation\": ${escape(s.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        // Grammar & Revision
        fcSb.append("  \"revisionEx1MetsAuPresent\": [\n")
        FrenchCourseData.revisionEx1MetsAuPresent.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"revisionEx2ChoisisBonneReponse\": [\n")
        FrenchCourseData.revisionEx2ChoisisBonneReponse.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"revisionEx3Interrogatifs\": [\n")
        FrenchCourseData.revisionEx3Interrogatifs.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"revisionEx4ArticlesLieux\": [\n")
        FrenchCourseData.revisionEx4ArticlesLieux.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"revisionEx5Negation\": [\n")
        FrenchCourseData.revisionEx5Negation.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"grammarQuizQuestions\": [\n")
        FrenchCourseData.grammarQuizQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"id\": ${escape(q.id)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"explanation\": ${escape(q.explanation)}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ],\n")

        fcSb.append("  \"examText\": ${escape(FrenchCourseData.examText)},\n")
        fcSb.append("  \"examTextArabic\": ${escape(FrenchCourseData.examTextArabic)},\n")
        fcSb.append("  \"examQuestions\": [\n")
        FrenchCourseData.examQuestions.forEachIndexed { qIdx, q ->
            if (qIdx > 0) fcSb.append(",\n")
            fcSb.append("    {\"section\": ${escape(q.section)}, \"question\": ${escape(q.question)}, \"options\": ${strList(q.options)}, \"correctIndex\": ${q.correctIndex}, \"arabicTranslation\": ${escape(q.arabicTranslation)}}")
        }
        fcSb.append("\n  ]\n")
        fcSb.append("}")
        File(outDir, "french_course_data.json").writeText(fcSb.toString())

        println("SUCCESS: Exported all 5 data sources to ${outDir.absolutePath}")
    }
}
