package com.recordandplayaudioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.recordandplayaudioapp.playback.AndroidAudioPlayer
import com.recordandplayaudioapp.record.AndroidAudioRecorder
import com.recordandplayaudioapp.ui.theme.RecordAndPlayAudioAppTheme
import java.io.File

class MainActivity : ComponentActivity() {

    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    private var audioFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.RECORD_AUDIO),
            0
        )
        setContent {
            RecordAndPlayAudioAppTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        File(cacheDir, "audio.mp3").also {
                            recorder.start(it)
                            audioFile = it
                        }
                    }) {
                        Text(text = "Start Recording")
                        
                    }
                    Button(onClick = {
                        File(cacheDir, "audio.mp3").also {
                            recorder.stop()
                            audioFile = it
                        }
                    }) {
                        Text(text = "Stop Recording")
                    }
                    Button(onClick = {
                        File(cacheDir, "audio.mp3").also {
                            player.playFile(audioFile ?: return@Button)
                            audioFile = it
                        }
                    }) {
                        Text(text = "Play")
                    }
                    Button(onClick = {
                        player.stop()
                    }) {
                        Text(text = "Stop Playing")
                    }
                }

            }
        }
    }
}
