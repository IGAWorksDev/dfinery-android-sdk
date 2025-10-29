package com.igaworks.dfineryjavasample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinery.constants.DFEvent;
import com.igaworks.dfinery.constants.DFEventProperty;
import com.igaworks.dfinery.constants.DFGender;
import com.igaworks.dfinery.constants.DFIdentity;
import com.igaworks.dfinery.constants.DFUserProfile;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final int REQUEST_CODE_POST_NOTIFICATIONS = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_constraintLayout_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.main_button_login).setOnClickListener(this);
        findViewById(R.id.main_button_purchase).setOnClickListener(this);
        findViewById(R.id.main_button_customEvent).setOnClickListener(this);
        findViewById(R.id.main_button_userProfile).setOnClickListener(this);
        findViewById(R.id.main_button_userProfiles).setOnClickListener(this);
        findViewById(R.id.main_button_setIdentity).setOnClickListener(this);
        findViewById(R.id.main_button_setIdentities).setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
        }
        getAdvertisingId();
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();
        if(viewId == R.id.main_button_login){
            Dfinery.getInstance().logEvent(DFEvent.LOGIN);
        } else if(viewId == R.id.main_button_purchase){
            Dfinery.getInstance().logEvent(DFEvent.PURCHASE,
                    new Utils.JSONObjectBuilder()
                            .addProperty(DFEventProperty.ITEMS, Utils.makeJSONArray(new Utils.JSONObjectBuilder()
                                    .addProperty(DFEventProperty.ITEM_ID, "상품번호")
                                    .addProperty(DFEventProperty.ITEM_NAME, "상품이름")
                                    .addProperty(DFEventProperty.ITEM_CATEGORY1, "식품")
                                    .addProperty(DFEventProperty.ITEM_CATEGORY2, "과자")
                                    .addProperty(DFEventProperty.ITEM_PRICE, 5000.0)
                                    .addProperty(DFEventProperty.ITEM_DISCOUNT, 500.0)
                                    .addProperty(DFEventProperty.ITEM_QUANTITY, 5L)
                                    .build()))
                            .addProperty(DFEventProperty.ORDER_ID, "주문번호")
                            .addProperty(DFEventProperty.PAYMENT_METHOD, "BankTransfer")
                            .addProperty(DFEventProperty.TOTAL_PURCHASE_AMOUNT, 25500.0)
                            .addProperty(DFEventProperty.DELIVERY_CHARGE, 3000.0)
                            .addProperty(DFEventProperty.DISCOUNT, 0)
                            .build());
        } else if(viewId == R.id.main_button_customEvent){
            Dfinery.getInstance().logEvent("custom_event",
                    new Utils.JSONObjectBuilder()
                            .addProperty("custom_property_1", 34000L)
                            .addProperty("custom_property_2", 42.195)
                            .addProperty("custom_property_3", "Seoul")
                            .addProperty("custom_property_4", true)
                            .addProperty("custom_property_5", Utils.parseDateToDfineryDatetimeStringFormat(new Date()))
                            .addProperty("custom_property_6", "1999-01-01")
                            .addProperty("custom_property_7", Utils.makeJSONArray(20L,30L,40L))
                            .addProperty("custom_property_8", Utils.makeJSONArray(1.1, 1.2, 1.3))
                            .addProperty("custom_property_9", Utils.makeJSONArray("Hello", "World"))
                            .build());
        } else if(viewId == R.id.main_button_userProfile){
            DfineryProperties.setUserProfile(DFUserProfile.NAME, "Tester");
        } else if(viewId == R.id.main_button_userProfiles){
            Map<String, Object> map = new HashMap<>();
            map.put(DFUserProfile.GENDER, DFGender.FEMALE);
            map.put(DFUserProfile.BIRTH, "1999-01-01");
            map.put(DFUserProfile.MEMBERSHIP, "VIP");
            map.put(DFUserProfile.PUSH_ADS_OPTIN, true);
            map.put(DFUserProfile.SMS_ADS_OPTIN, false);
            map.put(DFUserProfile.KAKAO_ADS_OPTIN, true);
            map.put(DFUserProfile.PUSH_NIGHT_ADS_OPTIN, false);
            map.put("custom_user_profile_1", 34000L);
            map.put("custom_user_profile_2", 42.195);
            map.put("custom_user_profile_3", Utils.parseDateToDfineryDatetimeStringFormat(new Date()));
            map.put("custom_user_profile_4", Utils.makeJSONArray(20L, 30L));
            map.put("custom_user_profile_5", Utils.makeJSONArray(1.1, 1.2));
            map.put("custom_user_profile_6", Utils.makeJSONArray("Hello", "World"));
            DfineryProperties.setUserProfiles(map);
        } else if(viewId == R.id.main_button_setIdentity){
            DfineryProperties.setIdentity(DFIdentity.EXTERNAL_ID, "user_a1b2c3d4");
        } else if(viewId == R.id.main_button_setIdentities){
            Map<DFIdentity, String> map = new HashMap<>();
            map.put(DFIdentity.EMAIL, "sample.user@example.com");
            map.put(DFIdentity.PHONE_NO, "821012345678");
            map.put(DFIdentity.KAKAO_USER_ID, "kakao_u98765");
            map.put(DFIdentity.LINE_USER_ID, "line_i10293");
            DfineryProperties.setIdentities(map);
        }
    }

    private void getAdvertisingId() {
        WeakReference<Context> weakContext = new WeakReference<>(getApplicationContext());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Context context = weakContext.get();
                if (context == null) {
                    return;
                }
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    if (adInfo != null) {
                        DfineryProperties.setGoogleAdvertisingId(adInfo.getId(), adInfo.isLimitAdTrackingEnabled());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to get Advertising ID", e);
                }
            }
        }).start();
    }
}
