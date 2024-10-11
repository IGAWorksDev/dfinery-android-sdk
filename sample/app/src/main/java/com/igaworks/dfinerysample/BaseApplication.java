package com.igaworks.dfinerysample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDexApplication;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryConfig;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinerysample.enums.PreferenceKey;
import com.igaworks.dfinerysample.helper.PreferenceHelper;


public class BaseApplication extends MultiDexApplication {
    private static final String TAG = Utils.getTagFromClass(BaseApplication.class);
    private static final String NOTIFICATION_CHANNEL_ID = "dfinery";
    private PreferenceHelper preferenceHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        preferenceHelper = new PreferenceHelper(getApplicationContext());
        String serviceId = preferenceHelper.getString(PreferenceKey.STRING_SDK_SERVICE_ID.name(), getServiceId());
        Dfinery.getInstance().init(this, serviceId, getConfig(preferenceHelper));
        refreshRegistrationId();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannel();
        }
    }
    private String getServiceId(){
        return "your_service_id";
    }
    private void refreshRegistrationId(){
        boolean isFirebaseInitialize = false;
        try {
            FirebaseApp.getInstance();
            isFirebaseInitialize = true;
        }catch (IllegalStateException e){
            Log.e(TAG, "Firebase is not initialized.", e);
        }
        if(!isFirebaseInitialize){
            return;
        }
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    preferenceHelper.putString(PreferenceKey.STRING_FIREBASE_REGISTRATION_ID.name(), task.getResult());
                    DfineryProperties.setPushToken(task.getResult());
                }
            }
        });
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
    private DfineryConfig getConfig(PreferenceHelper preferenceHelper){
        DfineryConfig config = new DfineryConfig.Builder()
                .setDefaultNotificationChannelId(NOTIFICATION_CHANNEL_ID)
                .setLogEnable(true)
                .setLogLevel(Log.VERBOSE)
                .build();
        return config;
    }
}
