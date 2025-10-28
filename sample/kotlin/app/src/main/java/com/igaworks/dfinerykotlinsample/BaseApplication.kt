package com.igaworks.dfinerykotlinsample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.igaworks.dfinery.Dfinery
import com.igaworks.dfinery.DfineryConfig
import com.igaworks.dfinery.DfineryProperties

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        //create notification channel
        val DEFAULT_NOTIFICATION_CHANNEL_ID = this.packageName + ".notificationChannel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        var notificationChannel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                DEFAULT_NOTIFICATION_CHANNEL_ID,
                "Default Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            notificationChannel.setSound(soundUri, audioAttributes)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        //refresh push token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                DfineryProperties.setPushToken(task.result)
            }
        }

        //init dfinery
        val config = DfineryConfig.Builder()
            .setLogEnable(BuildConfig.DEBUG)
            .setLogLevel(Log.VERBOSE)
            .setDefaultNotificationChannelId(DEFAULT_NOTIFICATION_CHANNEL_ID)
            .setNotificationIcon(R.drawable.ic_dfinery)
            .setNotificationAccentColor(Color.parseColor("#FF3700B3"))
            .build()
        Dfinery.getInstance().init(this, BuildConfig.DFINERY_SERVICE_ID, config)
    }
}