package com.example.audio

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

class AudioHelper(context: Context) {

    var soundEnabled by mutableStateOf(true)
        private set

    var isSpeaking by mutableStateOf(false)
        private set

    var currentSpeakingText by mutableStateOf<String?>(null)
        private set

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private val soundEffectEngine = SoundEffectEngine(context)
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        try {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = tts?.setLanguage(Locale.FRENCH)
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.w("AudioHelper", "French language not supported, fallback to default")
                        tts?.setLanguage(Locale.getDefault())
                    }
                    tts?.setSpeechRate(0.9f)
                    tts?.setPitch(1.0f)
                    tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {
                            mainHandler.post { isSpeaking = true }
                        }

                        override fun onDone(utteranceId: String?) {
                            mainHandler.post {
                                isSpeaking = false
                                currentSpeakingText = null
                            }
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onError(utteranceId: String?) {
                            mainHandler.post {
                                isSpeaking = false
                                currentSpeakingText = null
                            }
                        }
                    })
                    isTtsReady = true
                } else {
                    Log.w("AudioHelper", "TextToSpeech init failed")
                }
            }
        } catch (e: Exception) {
            Log.w("AudioHelper", "Failed to start TTS", e)
        }
    }

    fun toggleSound() {
        if (soundEnabled) {
            // User is muting: play subtle mute feedback first, then mute
            try {
                soundEffectEngine.playMute()
            } catch (_: Exception) {}
            stopSpeech()
            soundEnabled = false
        } else {
            // User is unmuting: enable sound, then play cheerful unmute chime
            soundEnabled = true
            try {
                soundEffectEngine.playUnmute()
            } catch (_: Exception) {}
        }
    }

    fun speak(text: String) {
        if (!soundEnabled) return
        if (isTtsReady && tts != null) {
            try {
                tts?.stop()
                currentSpeakingText = text
                isSpeaking = true
                val utteranceId = "FrenchSpeech_${System.currentTimeMillis()}"
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            } catch (e: Exception) {
                Log.w("AudioHelper", "Error playing speech", e)
                isSpeaking = false
                currentSpeakingText = null
            }
        }
    }

    fun stopSpeech() {
        try {
            tts?.stop()
            isSpeaking = false
            currentSpeakingText = null
        } catch (_: Exception) {}
    }

    fun playSuccessChime() {
        if (!soundEnabled) return
        try {
            soundEffectEngine.playSuccess()
        } catch (e: Exception) {
            Log.w("AudioHelper", "Success chime error", e)
        }
    }

    fun playErrorBuzz() {
        if (!soundEnabled) return
        try {
            soundEffectEngine.playError()
        } catch (e: Exception) {
            Log.w("AudioHelper", "Error buzz error", e)
        }
    }

    fun playMuteFeedback() {
        try {
            soundEffectEngine.playMute()
        } catch (_: Exception) {}
    }

    fun playUnmuteFeedback() {
        try {
            soundEffectEngine.playUnmute()
        } catch (_: Exception) {}
    }

    fun playClick() {
        if (!soundEnabled) return
        try {
            soundEffectEngine.playClick()
        } catch (e: Exception) {
            Log.w("AudioHelper", "Click sound error", e)
        }
    }

    fun release() {
        try {
            tts?.stop()
            tts?.shutdown()
            soundEffectEngine.release()
        } catch (_: Exception) {}
    }
}
