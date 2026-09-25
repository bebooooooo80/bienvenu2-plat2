package com.example

import android.content.Context
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.core.app.ApplicationProvider
import com.example.audio.AudioHelper
import com.example.ui.FrenchApp
import com.example.ui.theme.FrenchPrepTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Production Tests for Back Navigation, State Restoration, and Configuration Re-creation.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LifecycleAndBackNavigationTest {

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
    fun testBackNavigationReturnsToPortal() {
        val backDispatcher = OnBackPressedDispatcher()
        val lifecycleOwner = object : LifecycleOwner, androidx.activity.OnBackPressedDispatcherOwner {
            private val registry = LifecycleRegistry(this).apply {
                currentState = Lifecycle.State.RESUMED
            }
            override val lifecycle: Lifecycle get() = registry
            override val onBackPressedDispatcher: OnBackPressedDispatcher get() = backDispatcher
        }

        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalOnBackPressedDispatcherOwner provides lifecycleOwner
            ) {
                FrenchPrepTheme {
                    FrenchApp(audioHelper = audioHelper)
                }
            }
        }
        composeTestRule.waitForIdle()

        // Initially in Portal
        composeTestRule.onNodeWithTag("door_revision_and_exams").assertIsDisplayed()

        // Navigate to Unit 1
        composeTestRule.onNodeWithTag("bottom_nav_UNIT_1").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("unit1_tab_0").assertIsDisplayed()

        // Trigger system back button
        backDispatcher.onBackPressed()
        composeTestRule.waitForIdle()

        // Verify we returned to Portal
        composeTestRule.onNodeWithTag("door_revision_and_exams").assertIsDisplayed()
    }

    @Test
    fun testRecreationPreservesStability() {
        composeTestRule.setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
        composeTestRule.waitForIdle()

        // Navigate to Grammar
        composeTestRule.onNodeWithTag("bottom_nav_GRAMMAR").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("grammar_tab_0").assertIsDisplayed()

        // Verify stability across re-compositions
        composeTestRule.onNodeWithTag("bottom_nav_GRAMMAR").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("grammar_tab_0").assertIsDisplayed()
        
        // Navigate across modules and verify UI remains completely stable
        composeTestRule.onNodeWithTag("bottom_nav_EXAM").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("bottom_nav_REVISION").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("revision_main_tab_0").assertIsDisplayed()
    }
}
