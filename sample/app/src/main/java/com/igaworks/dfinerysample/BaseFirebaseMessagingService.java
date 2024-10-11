package com.igaworks.dfinerysample;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryProperties;

public class BaseFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "BaseFirebaseMessagingService";
    @Override
    public void onNewToken(@NonNull String registrationId) {
        super.onNewToken(registrationId);
        DfineryProperties.setPushToken(registrationId);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(Dfinery.getInstance().handleRemoteMessage(getApplicationContext(), remoteMessage.getData())){
            Log.d(TAG, remoteMessage.getData().toString());
        }else{
            //This is not a push notification sent from Dfinery.
        }
    }
}
