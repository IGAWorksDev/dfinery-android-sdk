package com.igaworks.dfinerykotlinsample

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.igaworks.dfinery.Dfinery
import com.igaworks.dfinery.DfineryProperties

class BaseFirebaseMessagingService: FirebaseMessagingService() {
    val TAG: String? = BaseFirebaseMessagingService::class.java.canonicalName

    override fun onNewToken(registrationId: String) {
        super.onNewToken(registrationId)
        DfineryProperties.setPushToken(registrationId)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (Dfinery.getInstance().handleRemoteMessage(applicationContext, remoteMessage.data)) {
            Log.d(TAG, remoteMessage.data.toString())
        } else {
            //This is not a push notification sent from Dfinery.
        }
    }
}