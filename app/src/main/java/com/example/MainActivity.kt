package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.example.audio.AudioHelper
import com.example.ui.FrenchApp
import com.example.ui.theme.FrenchPrepTheme

class MainActivity : ComponentActivity() {

    private lateinit var audioHelper: AudioHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioHelper = AudioHelper(this)

        setContent {
            FrenchPrepTheme {
                FrenchApp(audioHelper = audioHelper)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioHelper.release()
    }
}
