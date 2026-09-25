package com.example

import com.example.data.BookletLieuxEtExercicesData
import com.example.data.FrenchCourseData
import com.example.data.MidYearExamData
import com.example.data.RevisionData
import com.example.data.RevisionGrammaireData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Production Content & Data Integrity Tests
 * Validates no duplicate IDs, no missing texts, no broken options,
 * exactly one correct option per single-choice question, and valid scoring.
 */
class ContentIntegrityTest {

    @Test
    fun testMidYearExamIntegrityAndScoring() {
        val allIds = mutableSetOf<String>()

        // Document Part A (MCQ)
        var docPartASum = 0f
        MidYearExamData.docPartAQuestions.forEach { q ->
            assertTrue("ID must be unique: ${q.id}", allIds.add(q.id))
            assertTrue("Question Fr not empty in ${q.id}", q.questionFr.isNotBlank())
            assertTrue("Question Ar not empty in ${q.id}", q.questionAr.isNotBlank())
            assertTrue("Options >= 2 in ${q.id}", q.options.size >= 2)
            assertTrue("CorrectIndex valid in ${q.id}", q.correctIndex in 0 until q.options.size)
            assertTrue("Points > 0 in ${q.id}", q.points > 0f)
            docPartASum += q.points
        }
        assertEquals("Doc Part A total points must be 3.0", 3.0f, docPartASum, 0.01f)

        // Document Part B (True/False)
        var docPartBSum = 0f
        MidYearExamData.docPartBQuestions.forEach { q ->
            assertTrue("ID must be unique: ${q.id}", allIds.add(q.id))
            assertTrue("Statement Fr not empty in ${q.id}", q.statementFr.isNotBlank())
            assertTrue("Statement Ar not empty in ${q.id}", q.statementAr.isNotBlank())
            assertTrue("Points > 0 in ${q.id}", q.points > 0f)
            docPartBSum += q.points
        }
        assertEquals("Doc Part B total points must be 3.0", 3.0f, docPartBSum, 0.01f)

        // Document Part C (Complete)
        var docPartCSum = 0f
        MidYearExamData.docPartCQuestions.forEach { q ->
            assertTrue("ID must be unique: ${q.id}", allIds.add(q.id))
            assertTrue("Template Fr not empty in ${q.id}", q.sentenceTemplateFr.isNotBlank())
            assertTrue("Blanks words not empty in ${q.id}", q.blanksWords.isNotEmpty())
            assertTrue("Word bank not empty in ${q.id}", q.wordBank.isNotEmpty())
            // Check all blank words are in word bank
            q.blanksWords.forEach { blank ->
                assertTrue("Word bank must contain $blank in ${q.id}", q.wordBank.contains(blank))
            }
            assertTrue("Points > 0 in ${q.id}", q.points > 0f)
            docPartCSum += q.points
        }
        assertEquals("Doc Part C total points must be 2.0", 2.0f, docPartCSum, 0.01f)

        // Grammar Part A (Conjugation)
        var gramPartASum = 0f
        MidYearExamData.gramPartAQuestions.forEach { q ->
            assertTrue("ID must be unique: ${q.id}", allIds.add(q.id))
            assertTrue("Prompt Fr not empty in ${q.id}", q.promptFr.isNotBlank())
            assertTrue("Correct answer not empty in ${q.id}", q.correctAnswer.isNotBlank())
            assertTrue("Points > 0 in ${q.id}", q.points > 0f)
            gramPartASum += q.points
        }
        assertEquals("Gram Part A total points must be 1.0", 1.0f, gramPartASum, 0.01f)

        // Grammar Part B (MCQ & Transform)
        var gramPartBSum = 0f
        MidYearExamData.gramPartBQuestions.forEach { item ->
            when (item) {
                is com.example.data.ExamMCQQuestion -> {
                    assertTrue("ID must be unique: ${item.id}", allIds.add(item.id))
                    assertTrue("Question Fr not empty in ${item.id}", item.questionFr.isNotBlank())
                    assertTrue("Options >= 2 in ${item.id}", item.options.size >= 2)
                    assertTrue("CorrectIndex valid in ${item.id}", item.correctIndex in 0 until item.options.size)
                    gramPartBSum += item.points
                }
                is com.example.data.ExamTransformQuestion -> {
                    assertTrue("ID must be unique: ${item.id}", allIds.add(item.id))
                    assertTrue("Original Fr not empty in ${item.id}", item.originalFr.isNotBlank())
                    assertTrue("Expected Fr not empty in ${item.id}", item.expectedAnswerFr.isNotBlank())
                    gramPartBSum += item.points
                }
            }
        }
        assertEquals("Gram Part B total points must be 6.0", 6.0f, gramPartBSum, 0.01f)

        // Grammar Part C (Pronouns Transform)
        var gramPartCSum = 0f
        MidYearExamData.gramPartCQuestions.forEach { q ->
            assertTrue("ID must be unique: ${q.id}", allIds.add(q.id))
            assertTrue("Original Fr not empty in ${q.id}", q.originalFr.isNotBlank())
            assertTrue("Expected Fr not empty in ${q.id}", q.expectedAnswerFr.isNotBlank())
            assertTrue("Points > 0 in ${q.id}", q.points > 0f)
            gramPartCSum += q.points
        }
        assertEquals("Gram Part C total points must be 2.0", 2.0f, gramPartCSum, 0.01f)

        // Production questions
        var prodSum = 0f
        MidYearExamData.productionQuestions.forEach { q ->
            assertTrue("ID must be unique: ${q.id}", allIds.add(q.id))
            assertTrue("Purpose Fr not empty in ${q.id}", q.purposeFr.isNotBlank())
            assertTrue("Options >= 2 in ${q.id}", q.options.size >= 2)
            assertTrue("CorrectIndex valid in ${q.id}", q.correctIndex in 0 until q.options.size)
            assertTrue("Points > 0 in ${q.id}", q.points > 0f)
            prodSum += q.points
        }
        assertEquals("Production total points must be 1.0", 1.0f, prodSum, 0.01f)

        // Situations questions
        var sitSum = 0f
        MidYearExamData.situationsQuestions.forEach { q ->
            assertTrue("ID must be unique: ${q.id}", allIds.add(q.id))
            assertTrue("Situation Fr not empty in ${q.id}", q.situationFr.isNotBlank())
            assertTrue("Options >= 2 in ${q.id}", q.options.size >= 2)
            assertTrue("CorrectIndex valid in ${q.id}", q.correctIndex in 0 until q.options.size)
            assertTrue("Points > 0 in ${q.id}", q.points > 0f)
            sitSum += q.points
        }
        assertEquals("Situations total points must be 2.0", 2.0f, sitSum, 0.01f)

        // Total sum verification
        val totalExamPoints = docPartASum + docPartBSum + docPartCSum +
                gramPartASum + gramPartBSum + gramPartCSum +
                prodSum + sitSum
        assertEquals("DocA=$docPartASum, DocB=$docPartBSum, DocC=$docPartCSum, GramA=$gramPartASum, GramB=$gramPartBSum, GramC=$gramPartCSum, Prod=$prodSum, Sit=$sitSum", 20.0f, totalExamPoints, 0.01f)
    }

    @Test
    fun testFrenchCourseDataSituations() {
        val situations = FrenchCourseData.unit1Situations
        assertTrue("Unit 1 situations must not be empty", situations.isNotEmpty())
        val ids = mutableSetOf<String>()

        situations.forEach { s ->
            assertTrue("Unique situation id: ${s.id}", ids.add(s.id))
            assertTrue("Situation promptFr not empty", s.promptFr.isNotBlank())
            assertTrue("Situation promptAr not empty", s.promptAr.isNotBlank())
            assertTrue("Situation goldenRule not empty", s.goldenRule.isNotBlank())
            assertTrue("Options >= 2 in situation ${s.id}", s.options.size >= 2)

            val correctCount = s.options.count { it.isCorrect }
            assertEquals("Situation ${s.id} must have exactly one correct option", 1, correctCount)

            s.options.forEach { opt ->
                assertTrue("Option textFr not empty in ${s.id}", opt.textFr.isNotBlank())
                assertTrue("Option textAr not empty in ${s.id}", opt.textAr.isNotBlank())
            }
        }
    }

    @Test
    fun testFrenchCourseDataContent() {
        val vocab = FrenchCourseData.unit2VocabWords
        assertTrue("Unit 2 vocab not empty", vocab.isNotEmpty())
        val ids = mutableSetOf<String>()
        vocab.forEach { v ->
            assertTrue("Vocab ID unique: ${v.id}", ids.add(v.id))
            assertTrue("Vocab french not empty in ${v.id}", v.french.isNotBlank())
            assertTrue("Vocab arabic not empty in ${v.id}", v.arabic.isNotBlank())
        }

        val grammarQuestions = FrenchCourseData.grammarQuizQuestions
        assertTrue("Grammar quiz questions not empty", grammarQuestions.isNotEmpty())
        grammarQuestions.forEach { q ->
            assertTrue("Options >= 2 in ${q.id}", q.options.size >= 2)
            assertTrue("CorrectIndex in range in ${q.id}", q.correctIndex in 0 until q.options.size)
            assertTrue("Question not blank in ${q.id}", q.question.isNotBlank())
        }

        val dialogues = FrenchCourseData.unit1Dialogues + FrenchCourseData.unit2Dialogues + FrenchCourseData.unit3Dialogues
        assertTrue("Dialogues not empty", dialogues.isNotEmpty())
        dialogues.forEach { d ->
            assertTrue("Speaker not blank", d.speaker.isNotBlank())
            assertTrue("Text not blank", d.text.isNotBlank())
            assertTrue("Arabic note not blank", d.arabicNote.isNotBlank())
        }
    }

    @Test
    fun testBookletLieuxEtExercicesData() {
        val personnages = BookletLieuxEtExercicesData.personnagesList
        assertTrue("Personnages not empty", personnages.isNotEmpty())
        personnages.forEach { p ->
            assertTrue("French name not empty in ${p.id}", p.french.isNotBlank())
            assertTrue("Arabic name not empty in ${p.id}", p.arabic.isNotBlank())
            assertTrue("Gender valid in ${p.id}", p.gender in listOf("m", "f", "m/f"))
            assertTrue("Typical places not empty in ${p.id}", p.typicalPlaces.isNotEmpty())
        }

        val lieux = BookletLieuxEtExercicesData.lieuxList
        assertTrue("Lieux not empty", lieux.isNotEmpty())
        lieux.forEach { l ->
            assertTrue("French with prep not empty in ${l.id}", l.frenchWithPreposition.isNotBlank())
            assertTrue("Place name not empty in ${l.id}", l.placeNameOnly.isNotBlank())
            assertTrue("Preposition not empty in ${l.id}", l.preposition.isNotBlank())
            assertTrue("Arabic name not empty in ${l.id}", l.arabic.isNotBlank())
            assertTrue("Typical characters not empty in ${l.id}", l.typicalCharacters.isNotEmpty())
        }

        val ouVasTu = BookletLieuxEtExercicesData.ouVasTuItems
        assertTrue("ouVasTu not empty", ouVasTu.isNotEmpty())
        ouVasTu.forEach { item ->
            assertTrue("Expected answer in options in ${item.id}", item.options.contains(item.expectedAnswerFr))
        }

        val quiParle = BookletLieuxEtExercicesData.quiParleItems
        assertTrue("quiParle not empty", quiParle.isNotEmpty())
        quiParle.forEach { item ->
            assertTrue("Speaker in options in ${item.id}", item.options.contains(item.speakerFr))
        }

        val quiFait = BookletLieuxEtExercicesData.quiFaitCeTravailItems
        assertTrue("quiFait not empty", quiFait.isNotEmpty())
        quiFait.forEach { item ->
            assertTrue("Profession in options in ${item.id}", item.options.contains(item.professionFr))
        }
    }

    @Test
    fun testRevisionData() {
        val docs = RevisionData.documents
        assertTrue("Revision documents not empty", docs.isNotEmpty())
        docs.forEach { doc ->
            assertTrue("Doc titleFr not blank", doc.titleFr.isNotBlank())
            assertTrue("Doc titleAr not blank", doc.titleAr.isNotBlank())
            assertTrue("Doc textFr not blank", doc.textFr.isNotBlank())
            doc.mcqQuestions.forEach { q ->
                assertTrue("Options >= 2 in ${q.id}", q.options.size >= 2)
                assertTrue("CorrectIndex in range in ${q.id}", q.correctIndex in 0 until q.options.size)
                assertTrue("Question not blank in ${q.id}", q.questionFr.isNotBlank())
            }
            doc.trueFalseQuestions.forEach { q ->
                assertTrue("Statement Fr not blank in ${q.id}", q.statementFr.isNotBlank())
                assertTrue("Statement Ar not blank in ${q.id}", q.statementAr.isNotBlank())
            }
        }
    }

    @Test
    fun testRevisionGrammaireData() {
        val exercises = RevisionGrammaireData.allExercises
        assertTrue("Revision grammar exercises not empty", exercises.isNotEmpty())
        exercises.forEach { ex ->
            assertTrue("Title not blank in ex ${ex.id}", ex.titleFr.isNotBlank())
            assertTrue("Items not empty in ex ${ex.id}", ex.items.isNotEmpty())
            ex.items.forEach { item ->
                assertTrue("Sentence Fr not empty in ${item.id}", item.sentenceFr.isNotBlank())
                assertTrue("Answer Fr not empty in ${item.id}", item.answerFr.isNotBlank())
                if (item.options.isNotEmpty()) {
                    assertTrue("CorrectIndex in range in ${item.id}", item.correctIndex in 0 until item.options.size)
                }
            }
        }
    }
}
