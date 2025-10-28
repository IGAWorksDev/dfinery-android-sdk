package com.igaworks.dfineryhybridappsample;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryConfig;
import com.igaworks.dfinery.DfineryProperties;

import androidx.annotation.NonNull;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //create notification channel
        final String DEFAULT_NOTIFICATION_CHANNEL_ID = this.getPackageName() + ".notificationChannel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(DEFAULT_NOTIFICATION_CHANNEL_ID, "Default Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            notificationChannel.setSound(soundUri, audioAttributes);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        //refresh push token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    DfineryProperties.setPushToken(task.getResult());
                }
            }
        });

        //init dfinery
        DfineryConfig config = new DfineryConfig.Builder()
                .setLogEnable(BuildConfig.DEBUG)
                .setLogLevel(Log.VERBOSE)
                .setDefaultNotificationChannelId(DEFAULT_NOTIFICATION_CHANNEL_ID)
                .setNotificationIcon(R.drawable.ic_dfinery)
                .setNotificationAccentColor(Color.parseColor("#FF3700B3"))
                .build();
        Dfinery.getInstance().init(this, BuildConfig.DFINERY_SERVICE_ID, config);
    }
}
