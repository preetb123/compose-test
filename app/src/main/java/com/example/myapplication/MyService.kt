package com.example.myapplication

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.myapplication.R


const val INTENT_COMMAND = "Command"
const val INTENT_COMMAND_EXIT = "Exit"
const val INTENT_COMMAND_REPLY = "Reply"
const val INTENT_COMMAND_ACHIEVE = "Achieve"

private const val NOTIFICATION_CHANNEL_GENERAL = "Checking"
private const val CODE_FOREGROUND_SERVICE = 1
private const val CODE_REPLY_INTENT = 2
private const val CODE_ACHIEVE_INTENT = 3

class MyService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val command = intent.getStringExtra(INTENT_COMMAND)
        if (command == INTENT_COMMAND_EXIT) {
            stopService()
            return START_NOT_STICKY
        }

        showNotification()

        if (command == INTENT_COMMAND_REPLY) {
            Toast.makeText(this, "Clicked in Notification", Toast.LENGTH_SHORT).show()
        }

        return START_STICKY
    }

    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    @SuppressLint("LaunchActivityFromNotification")
    private fun showNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val replyIntent = Intent(this, MyService::class.java).apply {
            putExtra(INTENT_COMMAND, INTENT_COMMAND_REPLY)
        }
        val achieveIntent = Intent(this, MyService::class.java).apply {
            putExtra(INTENT_COMMAND, INTENT_COMMAND_ACHIEVE)
        }
        val replyPendingIntent = PendingIntent.getService(
            this, CODE_REPLY_INTENT, replyIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val achievePendingIntent = PendingIntent.getService(
            this, CODE_ACHIEVE_INTENT, achieveIntent, PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                with(
                    NotificationChannel(
                        NOTIFICATION_CHANNEL_GENERAL,
                        "Hello World",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                ) {
                    enableLights(false)
                    setShowBadge(false)
                    enableVibration(false)
                    setSound(null, null)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    manager.createNotificationChannel(this)
                }
            } catch (e: Exception) {
                Log.d("Error", "showNotification: ${e.localizedMessage}")
            }
        }

        with(
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_GENERAL)
        ) {
            setTicker(null)
            setContentTitle("Hello World")
            setContentText("Hello World Description")
            setAutoCancel(false)
            setOngoing(true)
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            priority = Notification.PRIORITY_MAX
            setContentIntent(replyPendingIntent)
            addAction(
                0, "REPLY", replyPendingIntent
            )
            addAction(
                0, "ACHIEVE", replyPendingIntent
            )
            startForeground(CODE_FOREGROUND_SERVICE, build())
        }
    }
}
