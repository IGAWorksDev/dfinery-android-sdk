package com.igaworks.dfinerykotlinsample

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.igaworks.dfinery.Dfinery
import com.igaworks.dfinery.DfineryProperties
import com.igaworks.dfinery.models.DfineryPushPayload
import org.json.JSONException
import org.json.JSONObject

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
            showPushNotificationReceivedMessage(remoteMessage)
        } else {
            //This is not a push notification sent from Dfinery.
        }
    }

    private fun showPushNotificationReceivedMessage(remoteMessage: RemoteMessage) {
        val dfn = remoteMessage.data[DfineryPushPayload.payload_root]
        try {
            val root = JSONObject(dfn)
            val clickAct = root.getJSONObject(DfineryPushPayload.payload_click_action)
            val uri = clickAct.getString(DfineryPushPayload.payload_click_action_deeplink)
            val intent = Intent(
                applicationContext,
                MainActivity::class.java
            )
            intent.setAction(MainActivity.ACTION_SHOW_MESSAGE)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(MainActivity.EXTRA_TITLE, "푸시 알림이 수신되었습니다.")
            intent.putExtra(MainActivity.EXTRA_MESSAGE, uri)
            startActivity(intent)
        } catch (e: JSONException) {
            Log.d(TAG, e.toString())
        }
    }
}