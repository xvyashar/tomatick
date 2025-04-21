package com.xvyashar.tomatick

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class TimerViewModel : ViewModel() {

    var stateIndex by mutableIntStateOf(1)
        private set
    var timerText by mutableStateOf("00:00")
        private set
    var timerProgress by mutableFloatStateOf(1f)
        private set
    var isPaused by mutableStateOf(true)
        private set

    private var isReceiverRegistered = false

    private val timerTickReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val state = intent?.getStringExtra("state") ?: "Pomodoro"
            val rawSeconds = intent?.getLongExtra("remainingSeconds", 0L) ?: 0L
            val totalSeconds = intent?.getLongExtra("totalSeconds", 0L) ?: 0L

            stateIndex = if (state == "Short Break") 0 else if (state == "Pomodoro") 1 else 2

            timerText = formatTime(rawSeconds)
            timerProgress = rawSeconds.toFloat() / totalSeconds.toFloat()
            isPaused = intent?.getBooleanExtra("isPaused", true) != false
        }
    }

    fun registerReceiver(context: Context) {
        if (!isReceiverRegistered) {
            val filter = IntentFilter("com.xvyashar.tomatick.TIMER_TICK")

            ContextCompat.registerReceiver(context, timerTickReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)

            isReceiverRegistered = true
        }
    }

    fun unregisterReceiver(context: Context) {
        if (isReceiverRegistered) {
            context.unregisterReceiver(timerTickReceiver)
            isReceiverRegistered = false
        }
    }

    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format(Locale.ROOT, "%02d:%02d", minutes, secs)
    }
}
