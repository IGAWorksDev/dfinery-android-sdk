package com.igaworks.dfinerysample;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryProperties;

public class BaseFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String registrationId) {
        super.onNewToken(registrationId);
        DfineryProperties.setPushToken(registrationId);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(Dfinery.getInstance().handleRemoteMessage(getApplicationContext(), remoteMessage.getData())){
            //The SDK will use this push data to display push notifications.
        }else{
            //This is not a push notification sent from Dfinery.
        }
    }
}
