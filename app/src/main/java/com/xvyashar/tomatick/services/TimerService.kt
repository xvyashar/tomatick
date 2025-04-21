package com.xvyashar.tomatick.services

import android.app.*
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import com.xvyashar.tomatick.MainActivity
import com.xvyashar.tomatick.R
import java.util.Locale

class TimerService : Service() {

    companion object {
        const val CHANNEL_ID = "TimerServiceChannel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_RESET = "ACTION_RESET"
    }

    private var timer: CountDownTimer? = null

    private var pomodoroTime = 0L
    private var shortBreakTime = 0L
    private var longBreakTime = 0L

    private var isPaused = true
    private var state = "Pomodoro"
    private var remainingSeconds = 0L
    private var shCycle = 1

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val pomodoroPref = getSharedPreferences("Pomodoro", MODE_PRIVATE)
        pomodoroTime = pomodoroPref.getLong("pomodoro_time", 25 * 60)
        shortBreakTime = pomodoroPref.getLong("short_break_time", 5 * 60)
        longBreakTime = pomodoroPref.getLong("long_break_time", 15 * 60)

        remainingSeconds = pomodoroTime
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> {
                if (isPaused) startTimer() else timer?.cancel()
                isPaused = !isPaused
                updateNotification()
                sendTickBroadcast()
            }

            ACTION_RESET -> {
                timer?.cancel()
                state = "Pomodoro"
                remainingSeconds = pomodoroTime
                shCycle = 1
                isPaused = true
                updateNotification()
                sendTickBroadcast()
            }

            else -> {
                sendTickBroadcast()
            }
        }

        startForeground(NOTIFICATION_ID, buildNotification(formatTime(remainingSeconds)))
        return START_STICKY
    }

    private fun startTimer() {
        timer?.cancel()

        timer = object : CountDownTimer(remainingSeconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingSeconds = millisUntilFinished / 1000
                sendTickBroadcast()
                updateNotification()
            }

            override fun onFinish() {
                if (shCycle == 4) {
                    if (state == "Pomodoro") {
                        state = "Long Break"
                        remainingSeconds = longBreakTime
                    } else {
                        state = "Pomodoro"
                        remainingSeconds = pomodoroTime
                        shCycle = 1
                    }
                } else {
                    if (state == "Pomodoro") {
                        state = "Short Break"
                        remainingSeconds = shortBreakTime
                    } else {
                        state = "Pomodoro"
                        remainingSeconds = pomodoroTime
                        shCycle++
                    }
                }
                updateNotification()
                startTimer()
                sendTickBroadcast()
            }
        }.start()
    }

    private fun sendTickBroadcast() {
        val intent = Intent("com.xvyashar.tomatick.TIMER_TICK").apply {
            putExtra("state", state)
            putExtra("remainingSeconds", remainingSeconds)
            putExtra("totalSeconds", if (state == "Pomodoro") pomodoroTime else if (state == "Short Break") shortBreakTime else longBreakTime)
            putExtra("isPaused", isPaused)
            setPackage("com.xvyashar.tomatick")
        }
        sendBroadcast(intent)
    }

    private fun updateNotification() {
        val timeText = formatTime(remainingSeconds)
        val notification = buildNotification(timeText)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(time: String): Notification {
        val playPauseIntent = Intent(this, TimerService::class.java).apply {
            action = ACTION_PLAY_PAUSE
        }
        val playPausePendingIntent = PendingIntent.getService(this, 0, playPauseIntent, PendingIntent.FLAG_IMMUTABLE)

        val resetIntent = Intent(this, TimerService::class.java).apply {
            action = ACTION_RESET
        }
        val resetPendingIntent = PendingIntent.getService(this, 1, resetIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 2, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(state)
            .setContentText("Remaining: $time")
            .setSmallIcon(R.drawable.timer_vector)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .setOngoing(true)
            .addAction(
                if (isPaused) R.drawable.play_vector else R.drawable.pause_vector,
                if (isPaused) "Play" else "Pause",
                playPausePendingIntent
            )
            .addAction(R.drawable.reset_vector, "Reset", resetPendingIntent)
            .build()
    }

    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format(Locale.ROOT, "%02d:%02d", minutes, secs)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Timer Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        timer = null
    }

    override fun onBind(intent: Intent?): IBinder? = null
}