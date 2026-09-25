package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import java.util.concurrent.Executors

/**
 * High-fidelity, modern procedural sound synthesizer and player for FrenchPrep app.
 * Replaces primitive ToneGenerator DTMF telecom beeps with warm, rich, educational-app
 * sound effects (Duolingo/Apple style chimes, gentle marimba error tones, and tactile
 * mute/unmute feedback).
 */
class SoundEffectEngine(context: Context) {

    private val executor = Executors.newSingleThreadExecutor()

    // Precomputed raw PCM buffers for immediate fallback / zero-latency play
    private val successSamples: ShortArray by lazy { generateSuccessSamples() }
    private val errorSamples: ShortArray by lazy { generateErrorSamples() }
    private val muteSamples: ShortArray by lazy { generateMuteSamples() }
    private val unmuteSamples: ShortArray by lazy { generateUnmuteSamples() }
    private val clickSamples: ShortArray by lazy { generateClickSamples() }

    private var successTrack: AudioTrack? = null
    private var errorTrack: AudioTrack? = null
    private var muteTrack: AudioTrack? = null
    private var unmuteTrack: AudioTrack? = null
    private var clickTrack: AudioTrack? = null

    init {
        // Pre-allocate and initialize low-latency static tracks on background thread
        executor.execute {
            try {
                successTrack = createStaticTrack(successSamples)
                errorTrack = createStaticTrack(errorSamples)
                muteTrack = createStaticTrack(muteSamples)
                unmuteTrack = createStaticTrack(unmuteSamples)
                clickTrack = createStaticTrack(clickSamples)
            } catch (e: Exception) {
                Log.w("SoundEffectEngine", "Failed to pre-allocate static AudioTracks: ${e.message}")
            }
        }
    }

    private fun createStaticTrack(samples: ShortArray): AudioTrack? {
        return try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val audioFormat = AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(44100)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()

            val track = AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(samples.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(samples, 0, samples.size)
            track
        } catch (e: Exception) {
            Log.w("SoundEffectEngine", "Error creating static AudioTrack: ${e.message}")
            null
        }
    }

    /**
     * Plays the celebratory, ascending celesta/harp success chime.
     */
    fun playSuccess() {
        playSound(successTrack, successSamples, 1.0f)
    }

    /**
     * Plays the gentle, warm descending acoustic marimba error sound.
     */
    fun playError() {
        playSound(errorTrack, errorSamples, 0.85f)
    }

    /**
     * Plays the sleek, soft descending power-down droplet for sound muting.
     */
    fun playMute() {
        playSound(muteTrack, muteSamples, 0.80f)
    }

    /**
     * Plays the bright, cheerful ascending pop/chime for unmuting.
     */
    fun playUnmute() {
        playSound(unmuteTrack, unmuteSamples, 0.90f)
    }

    /**
     * Plays the crisp, tactile UI wooden tick.
     */
    fun playClick() {
        playSound(clickTrack, clickSamples, 0.50f)
    }

    private fun playSound(track: AudioTrack?, samples: ShortArray, volume: Float) {
        executor.execute {
            try {
                if (track != null && track.state == AudioTrack.STATE_INITIALIZED) {
                    try {
                        track.pause()
                        track.setVolume(volume)
                        track.reloadStaticData()
                        track.play()
                        return@execute
                    } catch (_: Exception) {
                        // If reloadStaticData fails, fallback to one-shot
                    }
                }
                playViaOneShot(samples, volume)
            } catch (e: Exception) {
                Log.w("SoundEffectEngine", "Playback failed: ${e.message}")
            }
        }
    }

    private fun playViaOneShot(samples: ShortArray, volume: Float) {
        try {
            val minBuf = AudioTrack.getMinBufferSize(
                44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            val bufSize = maxOf(minBuf, samples.size * 2)
            val track = AudioTrack(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build(),
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(44100)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build(),
                bufSize,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE
            )
            track.setVolume(volume)
            track.write(samples, 0, samples.size)
            track.setNotificationMarkerPosition(samples.size)
            track.setPlaybackPositionUpdateListener(object : AudioTrack.OnPlaybackPositionUpdateListener {
                override fun onMarkerReached(t: AudioTrack?) {
                    try {
                        t?.stop()
                        t?.release()
                    } catch (_: Exception) {}
                }
                override fun onPeriodicNotification(t: AudioTrack?) {}
            })
            track.play()
        } catch (e: Exception) {
            Log.w("SoundEffectEngine", "AudioTrack play error: ${e.message}")
        }
    }

    fun release() {
        try {
            executor.shutdown()
            safeRelease(successTrack)
            safeRelease(errorTrack)
            safeRelease(muteTrack)
            safeRelease(unmuteTrack)
            safeRelease(clickTrack)
            successTrack = null
            errorTrack = null
            muteTrack = null
            unmuteTrack = null
            clickTrack = null
        } catch (_: Exception) {}
    }

    private fun safeRelease(track: AudioTrack?) {
        try {
            track?.stop()
            track?.release()
        } catch (_: Exception) {}
    }

    // =========================================================================
    // PROCEDURAL AUDIO SYNTHESIS ALGORITHMS (Warm, Rich, Natural Harmonics)
    // =========================================================================

    private data class HarmonicNote(
        val freq: Double,
        val start: Double,
        val dur: Double,
        val amp: Double
    )

    /**
     * Ascending 4-note celesta arpeggio (C5 -> E5 -> G5 -> C6) with warm overtones.
     */
    private fun generateSuccessSamples(): ShortArray {
        val sampleRate = 44100
        val totalSec = 0.68
        val totalSamples = (sampleRate * totalSec).toInt()
        val result = ShortArray(totalSamples)

        val notes = listOf(
            HarmonicNote(523.25, 0.00, 0.26, 0.55),  // C5
            HarmonicNote(659.25, 0.07, 0.28, 0.65),  // E5
            HarmonicNote(783.99, 0.14, 0.32, 0.75),  // G5
            HarmonicNote(1046.50, 0.21, 0.46, 0.90)  // C6 (Sparkling climax)
        )

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            var sampleVal = 0.0

            for (note in notes) {
                if (t >= note.start && t < note.start + note.dur) {
                    val dt = t - note.start
                    // Attack (6ms)
                    val attack = (dt / 0.006).coerceAtMost(1.0)
                    // Bell-like exponential decay
                    val decay = Math.exp(-dt * 6.5)
                    val env = attack * decay * note.amp

                    val f = note.freq
                    val h1 = Math.sin(2.0 * Math.PI * f * dt)
                    val h2 = 0.32 * Math.sin(2.0 * Math.PI * (f * 2) * dt)
                    val h3 = 0.14 * Math.sin(2.0 * Math.PI * (f * 3) * dt)
                    val h4 = 0.07 * Math.sin(2.0 * Math.PI * (f * 4) * dt)
                    val tone = (h1 + h2 + h3 + h4) / 1.53

                    sampleVal += tone * env
                }
            }
            result[i] = clampSample(sampleVal.coerceIn(-0.85, 0.85))
        }
        return result
    }

    /**
     * Gentle, soft descending warm acoustic low tones (E4 -> B3) with quick marimba damping.
     */
    private fun generateErrorSamples(): ShortArray {
        val sampleRate = 44100
        val totalSec = 0.36
        val totalSamples = (sampleRate * totalSec).toInt()
        val result = ShortArray(totalSamples)

        val notes = listOf(
            HarmonicNote(329.63, 0.00, 0.14, 0.65),  // E4
            HarmonicNote(246.94, 0.12, 0.22, 0.60)   // B3
        )

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            var sampleVal = 0.0

            for (note in notes) {
                if (t >= note.start && t < note.start + note.dur) {
                    val dt = t - note.start
                    val attack = (dt / 0.008).coerceAtMost(1.0)
                    val decay = Math.exp(-dt * 13.0)
                    val env = attack * decay * note.amp

                    val f = note.freq
                    val h1 = Math.sin(2.0 * Math.PI * f * dt)
                    val h2 = 0.18 * Math.sin(2.0 * Math.PI * (f * 2) * dt)
                    val tone = (h1 + h2) / 1.18

                    sampleVal += tone * env
                }
            }
            result[i] = clampSample(sampleVal.coerceIn(-0.75, 0.75))
        }
        return result
    }

    /**
     * Smooth, tactile descending droplet/swoop (540 Hz down to 220 Hz) for muting.
     */
    private fun generateMuteSamples(): ShortArray {
        val sampleRate = 44100
        val totalSec = 0.14
        val totalSamples = (sampleRate * totalSec).toInt()
        val result = ShortArray(totalSamples)
        var phase = 0.0

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            val freq = 220.0 + 320.0 * Math.exp(-t * 18.0)
            phase += 2.0 * Math.PI * freq / sampleRate
            val attack = (t / 0.005).coerceAtMost(1.0)
            val decay = Math.exp(-t * 22.0)
            val env = attack * decay * 0.70
            val wave = Math.sin(phase) + 0.15 * Math.sin(2.0 * phase)
            result[i] = clampSample((wave * env).coerceIn(-0.80, 0.80))
        }
        return result
    }

    /**
     * Bright, cheerful ascending pop/chime (360 Hz -> 720 Hz rising chirp + 880 Hz bell ping) for unmuting.
     */
    private fun generateUnmuteSamples(): ShortArray {
        val sampleRate = 44100
        val totalSec = 0.22
        val totalSamples = (sampleRate * totalSec).toInt()
        val result = ShortArray(totalSamples)
        var chirpPhase = 0.0

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            var wave = 0.0

            // Rising chirp portion
            if (t < 0.08) {
                val fChirp = 360.0 + 360.0 * (t / 0.08)
                chirpPhase += 2.0 * Math.PI * fChirp / sampleRate
                val env = (t / 0.005).coerceAtMost(1.0) * (1.0 - t / 0.08)
                wave += Math.sin(chirpPhase) * env * 0.60
            }

            // Crystalline bell ping portion
            if (t >= 0.05) {
                val dt = t - 0.05
                val attack = (dt / 0.004).coerceAtMost(1.0)
                val decay = Math.exp(-dt * 15.0)
                val env = attack * decay * 0.75
                val f = 880.0
                val h1 = Math.sin(2.0 * Math.PI * f * dt)
                val h2 = 0.28 * Math.sin(2.0 * Math.PI * (f * 2) * dt)
                wave += ((h1 + h2) / 1.28) * env
            }

            result[i] = clampSample(wave.coerceIn(-0.85, 0.85))
        }
        return result
    }

    /**
     * Subtle, tactile wooden click / tick for UI interactions.
     */
    private fun generateClickSamples(): ShortArray {
        val sampleRate = 44100
        val totalSec = 0.018
        val totalSamples = (sampleRate * totalSec).toInt()
        val result = ShortArray(totalSamples)

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            val freq = 1200.0 * Math.exp(-t * 80.0)
            val decay = Math.exp(-t * 180.0)
            val wave = Math.sin(2.0 * Math.PI * freq * t)
            result[i] = clampSample(wave * decay * 0.40)
        }
        return result
    }

    private fun clampSample(value: Double): Short {
        return (value * 32767.0).coerceIn(-32760.0, 32760.0).toInt().toShort()
    }
}
