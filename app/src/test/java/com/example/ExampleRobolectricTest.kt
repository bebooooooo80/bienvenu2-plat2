package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("2eme Prep", appName)
  }

  @Test
  fun `verify unit 1 situations content and answers`() {
    val situations = com.example.data.FrenchCourseData.unit1Situations
    assertEquals(6, situations.size)

    // Verify all 6 situations have exactly 1 correct answer and non-empty golden rules
    situations.forEach { situation ->
      val correctCount = situation.options.count { it.isCorrect }
      assertEquals("Situation ${situation.id} must have exactly one correct option", 1, correctCount)
      assert(situation.goldenRule.isNotEmpty())
      assert(situation.promptFr.isNotEmpty())
      assert(situation.promptAr.isNotEmpty())
    }

    // Situation 1: accept invitation -> avec plaisir
    assertEquals("avec plaisir !", situations[0].options.first { it.isCorrect }.textFr)
    // Situation 4: bon anniversaire
    assertEquals("Bon anniversaire !", situations[3].options.first { it.isCorrect }.textFr)
  }

  @Test
  fun `verify unit 1 composition content and sentences`() {
    val composition = com.example.data.FrenchCourseData.unit1CompositionTopic
    assertEquals("Présente ta famille", composition.titleFr)
    assertEquals("p. 16", composition.pageReference)
    assertEquals(7, composition.sentences.size)
    assertEquals(6, composition.fillBlankSolutions.size)
    assert(composition.sentences.any { it.french.contains("Je m'appelle") })
    assert(composition.sentences.any { it.french.contains("famille") })
  }
}

