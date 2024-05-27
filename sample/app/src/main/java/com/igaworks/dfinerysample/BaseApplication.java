package com.igaworks.dfinerysample;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryConfig;
import com.igaworks.dfinery.DfineryProperties;

import java.io.IOException;

public class BaseApplication extends Application {
    private static final String TAG = Utils.getTagFromClass(BaseApplication.class);
    private static final String NOTIFICATION_CHANNEL_ID = "Dfinery";
    @Override
    public void onCreate() {
        super.onCreate();
        DfineryConfig config = new DfineryConfig.Builder()
                .setLogEnable(true)
                .setLogLevel(Log.VERBOSE)
                .setDefaultNotificationChannelId(NOTIFICATION_CHANNEL_ID)
                .setNotificationIcon(R.drawable.ic_dfinery)
                .build();
        Dfinery.getInstance().init(this, "{service_id}", config);
        refreshRegistrationId();
        refreshGoogleAdvertisementId();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannel();
        }
    }
    private void refreshRegistrationId(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "pushToken: "+task.getResult());
                    DfineryProperties.setPushToken(task.getResult());
                }
            }
        });
    }
    private void refreshGoogleAdvertisementId(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                    DfineryProperties.setGoogleAdvertisingId(idInfo.getId(), idInfo.isLimitAdTrackingEnabled());
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, e.toString());
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
        thread.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initNotificationChannel(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Default", NotificationManager.IMPORTANCE_HIGH);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        notificationChannel.setSound(soundUri, audioAttributes);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
