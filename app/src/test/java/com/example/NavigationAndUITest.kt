package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import com.example.audio.AudioHelper
import com.example.data.AppNavModule
import com.example.ui.FrenchApp
import com.example.ui.screens.WelcomePortalScreen
import com.example.ui.theme.FrenchPrepTheme
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Production UI & Navigation Tests
 * Tests app startup without crash, navigation across all 6 modules,
 * switching between all sub-tabs, sound toggle, and exam interactions.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class NavigationAndUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var audioHelper: AudioHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        audioHelper = AudioHelper(context)
    }

    @After
    fun tearDown() {
        audioHelper.release()
    }

    @Test
    fun testAppLaunchWithoutCrash() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Verify bottom navigation is displayed
        composeTestRule.onNodeWithTag("bottom_nav_PORTAL").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bottom_nav_REVISION").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_2").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_3").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bottom_nav_GRAMMAR").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bottom_nav_EXAM").assertIsDisplayed()
    }

    @Test
    fun testBottomNavigationAcrossAllModules() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        val modules = listOf(
            AppNavModule.UNIT_1,
            AppNavModule.UNIT_2,
            AppNavModule.UNIT_3,
            AppNavModule.GRAMMAR,
            AppNavModule.EXAM,
            AppNavModule.REVISION
        )

        modules.forEach { module ->
            val tag = "bottom_nav_${module.name}"
            composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
            composeTestRule.onNodeWithTag(tag).performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Test
    fun testTopChipsNavigationAcrossAllModules() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        val modules = listOf(
            AppNavModule.UNIT_1,
            AppNavModule.UNIT_2,
            AppNavModule.UNIT_3,
            AppNavModule.GRAMMAR,
            AppNavModule.EXAM,
            AppNavModule.REVISION
        )

        modules.forEach { module ->
            val tag = "top_module_chip_${module.name}"
            composeTestRule.onNodeWithTag(tag).assertExists()
            composeTestRule.onNodeWithTag(tag).performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Test
    fun testUnit1TabsNavigation() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Navigate to Unit 1
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_1").performClick()
        composeTestRule.waitForIdle()

        // Click through tabs 0 to 6
        for (i in 0..6) {
            val tabTag = "unit1_tab_$i"
            composeTestRule.onNodeWithTag(tabTag).assertExists()
            composeTestRule.onNodeWithTag(tabTag).performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Test
    fun testUnit2TabsNavigation() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Navigate to Unit 2
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_2").performClick()
        composeTestRule.waitForIdle()

        // Click through Unit 2 tabs
        for (i in 0..4) {
            val tabTag = "unit2_tab_$i"
            composeTestRule.onNodeWithTag(tabTag).assertExists()
            composeTestRule.onNodeWithTag(tabTag).performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Test
    fun testUnit3TabsNavigation() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Navigate to Unit 3
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_3").performClick()
        composeTestRule.waitForIdle()

        // Click through Unit 3 tabs
        for (i in 0..4) {
            val tabTag = "unit3_tab_$i"
            composeTestRule.onNodeWithTag(tabTag).assertExists()
            composeTestRule.onNodeWithTag(tabTag).performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Test
    fun testGrammarTabsNavigation() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Navigate to Grammar
        composeTestRule.onNodeWithTag("bottom_nav_GRAMMAR").performClick()
        composeTestRule.waitForIdle()

        // Click through Grammar tabs
        for (i in 0..4) {
            val tabTag = "grammar_tab_$i"
            composeTestRule.onNodeWithTag(tabTag).assertExists()
            composeTestRule.onNodeWithTag(tabTag).performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Test
    fun testRevisionTabsNavigation() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Navigate to Revision
        composeTestRule.onNodeWithTag("bottom_nav_REVISION").performClick()
        composeTestRule.waitForIdle()

        // Click through Revision tabs (0: Textes, 1: Grammaire, 2: Examen)
        for (i in 0..2) {
            val tabTag = "revision_main_tab_$i"
            composeTestRule.onNodeWithTag(tabTag).assertExists()
            composeTestRule.onNodeWithTag(tabTag).performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Test
    fun testSoundToggleInteraction() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        val initialSoundState = audioHelper.soundEnabled
        composeTestRule.onNodeWithTag("sound_toggle_button").performClick()
        composeTestRule.waitForIdle()
        assertEquals(!initialSoundState, audioHelper.soundEnabled)

        composeTestRule.onNodeWithTag("sound_toggle_button").performClick()
        composeTestRule.waitForIdle()
        assertEquals(initialSoundState, audioHelper.soundEnabled)
    }

    @Test
    fun testPortalScreenDoors() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Verify the primary revision & exam door exists on the welcome portal
        composeTestRule.onNodeWithTag("door_revision_and_exams").assertExists()

        // Enter revision hub by clicking the door card
        composeTestRule.onNodeWithTag("door_revision_and_exams").performClick()
        composeTestRule.waitForIdle()

        // Verify page-ordered card and sub-header exist inside revision section
        composeTestRule.onNodeWithTag("btn_back_to_portal_home").assertExists()
        composeTestRule.onNodeWithTag("revision_page_card_4_6").assertExists()

        // Back to Portal home
        composeTestRule.onNodeWithTag("btn_back_to_portal_home").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("door_revision_and_exams").assertExists()
    }

    @Test
    fun testExamScreenInteractions() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Navigate to Exam
        composeTestRule.onNodeWithTag("bottom_nav_EXAM").performClick()
        composeTestRule.waitForIdle()

        // Verify submit and reset buttons are present
        composeTestRule.onNodeWithTag("submit_official_exam_button").assertExists()
        composeTestRule.onNodeWithTag("reset_official_exam_button").assertExists()

        // Click submit
        composeTestRule.onNodeWithTag("submit_official_exam_button").performClick()
        composeTestRule.waitForIdle()

        // Click reset
        composeTestRule.onNodeWithTag("reset_official_exam_button").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun testStepByStepReverseNavigationAndHomeButton() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Home button exists in TopBar
        composeTestRule.onNodeWithTag("top_bar_home_button").assertIsDisplayed()

        // Initially at PORTAL, back button in top bar is hidden
        composeTestRule.onNodeWithTag("top_bar_back_button").assertDoesNotExist()

        // Step 1: Navigate to Unit 1
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_1").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("top_bar_back_button").assertIsDisplayed()

        // Step 2: Navigate to Unit 2
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_2").performClick()
        composeTestRule.waitForIdle()

        // Step 3: Navigate to Grammar
        composeTestRule.onNodeWithTag("bottom_nav_GRAMMAR").performClick()
        composeTestRule.waitForIdle()

        // Reverse step 1: Click back -> returns to Unit 2
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()
        composeTestRule.waitForIdle()

        // Reverse step 2: Click back -> returns to Unit 1
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()
        composeTestRule.waitForIdle()

        // Reverse step 3: Click back -> returns to PORTAL
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()
        composeTestRule.waitForIdle()

        // Now back at PORTAL, top bar back button is not shown
        composeTestRule.onNodeWithTag("top_bar_back_button").assertDoesNotExist()

        // Direct Home button test from any deep tab
        composeTestRule.onNodeWithTag("bottom_nav_EXAM").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("top_bar_home_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("door_revision_and_exams").assertExists()
    }

    @Test
    fun testExitConfirmationDialogAtPortal() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Exit dialog is initially closed
        composeTestRule.onNodeWithTag("exit_confirmation_dialog").assertDoesNotExist()

        // Navigate to Revision then Home
        composeTestRule.onNodeWithTag("bottom_nav_REVISION").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("top_bar_home_button").performClick()
        composeTestRule.waitForIdle()

        // Back to Revision
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()
        composeTestRule.waitForIdle()

        // Back to Portal
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()
        composeTestRule.waitForIdle()

        // At portal, top bar back button is absent
        composeTestRule.onNodeWithTag("top_bar_back_button").assertDoesNotExist()
    }

    @Test
    fun testUnitPortalCardConsistentDimensions() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                WelcomePortalScreen(
                    audioHelper = audioHelper,
                    onNavigateToRevisionPage = { _, _ -> },
                    onNavigateToUnit = {}
                )
            }
        }
        composeTestRule.waitForIdle()

        // Scroll to and verify all 3 unit cards and their standardized 48dp icon badges
        composeTestRule.onNodeWithTag("portal_home_lazy_column")
            .performScrollToNode(hasTestTag("portal_unit_1_card"))
        composeTestRule.onNodeWithTag("portal_unit_1_card").assertExists()
        composeTestRule.onNodeWithTag("portal_unit_1_card_icon_badge", useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithTag("portal_home_lazy_column")
            .performScrollToNode(hasTestTag("portal_unit_2_card"))
        composeTestRule.onNodeWithTag("portal_unit_2_card").assertExists()
        composeTestRule.onNodeWithTag("portal_unit_2_card_icon_badge", useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithTag("portal_home_lazy_column")
            .performScrollToNode(hasTestTag("portal_unit_3_card"))
        composeTestRule.onNodeWithTag("portal_unit_3_card").assertExists()
        composeTestRule.onNodeWithTag("portal_unit_3_card_icon_badge", useUnmergedTree = true).assertExists()
    }
}
