package com.igaworks.dfineryjavasample;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinery.models.DfineryPushPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = BaseFirebaseMessagingService.class.getCanonicalName();

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
            showPushNotificationReceivedMessage(remoteMessage);
        }else{
            //This is not a push notification sent from Dfinery.
        }
    }

    private void showPushNotificationReceivedMessage(RemoteMessage remoteMessage){
        String dfn = remoteMessage.getData().get(DfineryPushPayload.payload_root);
        try {
            JSONObject root = new JSONObject(dfn);
            JSONObject clickAct = root.getJSONObject(DfineryPushPayload.payload_click_action);
            String uri = clickAct.getString(DfineryPushPayload.payload_click_action_deeplink);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setAction(MainActivity.ACTION_SHOW_MESSAGE);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(MainActivity.EXTRA_TITLE, "푸시 알림이 수신되었습니다."+uri);
            intent.putExtra(MainActivity.EXTRA_MESSAGE, uri);
            startActivity(intent);
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
    }
}
