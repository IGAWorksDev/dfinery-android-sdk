package com.igaworks.dfinerykotlinsample

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.igaworks.dfinery.Dfinery
import com.igaworks.dfinery.DfineryProperties
import com.igaworks.dfinery.constants.DFEvent
import com.igaworks.dfinery.constants.DFEventProperty
import com.igaworks.dfinery.constants.DFGender
import com.igaworks.dfinery.constants.DFIdentity
import com.igaworks.dfinery.constants.DFUserProfile
import com.igaworks.dfinery.models.DfineryPushPayload
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.Date

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        val TAG: String? = MainActivity::class.java.canonicalName
        const val ACTION_SHOW_MESSAGE: String = "com.igaworks.dfinery.MainActivity.ACTION_SHOW_MESSAGE"
        const val EXTRA_TITLE: String = "com.igaworks.dfinery.MainActivity.EXTRA_TITLE"
        const val EXTRA_MESSAGE: String = "com.igaworks.dfinery.MainActivity.EXTRA_MESSAGE"
        const val REQUEST_CODE_POST_NOTIFICATIONS: Int = 234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View>(R.id.main_constraintLayout_container)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<View>(R.id.main_button_login).setOnClickListener(this)
        findViewById<View>(R.id.main_button_purchase).setOnClickListener(this)
        findViewById<View>(R.id.main_button_customEvent).setOnClickListener(this)
        findViewById<View>(R.id.main_button_userProfile).setOnClickListener(this)
        findViewById<View>(R.id.main_button_userProfiles).setOnClickListener(this)
        findViewById<View>(R.id.main_button_setIdentity).setOnClickListener(this)
        findViewById<View>(R.id.main_button_setIdentities).setOnClickListener(this)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_POST_NOTIFICATIONS
            )
        }
        getAdvertisingId()
        showPushNotificationIsClickedMessage(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (ACTION_SHOW_MESSAGE == intent.action) {
            val title = intent.getStringExtra(EXTRA_TITLE)
            val message = intent.getStringExtra(EXTRA_MESSAGE)
            if (!TextUtils.isEmpty(title)) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(title)
                    .setMessage(message)
                    .show()
            }
        }
        showPushNotificationIsClickedMessage(intent)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        if (viewId == R.id.main_button_login) {
            Dfinery.getInstance().logEvent(DFEvent.LOGIN)
        } else if (viewId == R.id.main_button_purchase) {
            Dfinery.getInstance().logEvent(
                DFEvent.PURCHASE,
                Utils.JSONObjectBuilder()
                    .addProperty(
                        DFEventProperty.ITEMS, Utils.makeJSONArray(
                            Utils.JSONObjectBuilder()
                                .addProperty(DFEventProperty.ITEM_ID, "상품번호")
                                .addProperty(DFEventProperty.ITEM_NAME, "상품이름")
                                .addProperty(DFEventProperty.ITEM_CATEGORY1, "식품")
                                .addProperty(DFEventProperty.ITEM_CATEGORY2, "과자")
                                .addProperty(DFEventProperty.ITEM_PRICE, 5000.0)
                                .addProperty(DFEventProperty.ITEM_DISCOUNT, 500.0)
                                .addProperty(DFEventProperty.ITEM_QUANTITY, 5L)
                                .build()
                        )
                    )
                    .addProperty(DFEventProperty.ORDER_ID, "주문번호")
                    .addProperty(DFEventProperty.PAYMENT_METHOD, "BankTransfer")
                    .addProperty(DFEventProperty.TOTAL_PURCHASE_AMOUNT, 25500.0)
                    .addProperty(DFEventProperty.DELIVERY_CHARGE, 3000.0)
                    .addProperty(DFEventProperty.DISCOUNT, 0)
                    .build()
            )
        } else if (viewId == R.id.main_button_customEvent) {
            Dfinery.getInstance().logEvent(
                "custom_event",
                Utils.JSONObjectBuilder()
                    .addProperty("custom_property_1", 34000L)
                    .addProperty("custom_property_2", 42.195)
                    .addProperty("custom_property_3", "Seoul")
                    .addProperty("custom_property_4", true)
                    .addProperty(
                        "custom_property_5",
                        Utils.parseDateToDfineryDatetimeStringFormat(Date())
                    )
                    .addProperty("custom_property_6", "1999-01-01")
                    .addProperty("custom_property_7", Utils.makeJSONArray(20L, 30L, 40L))
                    .addProperty("custom_property_8", Utils.makeJSONArray(1.1, 1.2, 1.3))
                    .addProperty("custom_property_9", Utils.makeJSONArray("Hello", "World"))
                    .build()
            )
        } else if (viewId == R.id.main_button_userProfile) {
            DfineryProperties.setUserProfile(DFUserProfile.NAME, "Tester")
        } else if (viewId == R.id.main_button_userProfiles) {
            val map: Map<String, Any> = mapOf(
                DFUserProfile.GENDER to DFGender.FEMALE,
                DFUserProfile.BIRTH to "1999-01-01",
                DFUserProfile.MEMBERSHIP to "VIP",
                DFUserProfile.PUSH_ADS_OPTIN to true,
                DFUserProfile.SMS_ADS_OPTIN to false,
                DFUserProfile.KAKAO_ADS_OPTIN to true,
                DFUserProfile.PUSH_NIGHT_ADS_OPTIN to false,

                // Custom Fields
                "custom_user_profile_1" to 34000L,
                "custom_user_profile_2" to 42.195,
                "custom_user_profile_3" to Utils.parseDateToDfineryDatetimeStringFormat(Date()),
                "custom_user_profile_4" to Utils.makeJSONArray(20L, 30L),
                "custom_user_profile_5" to Utils.makeJSONArray(1.1, 1.2),
                "custom_user_profile_6" to Utils.makeJSONArray("Hello", "World")
            )
            DfineryProperties.setUserProfiles(map)
        } else if (viewId == R.id.main_button_setIdentity) {
            DfineryProperties.setIdentity(DFIdentity.EXTERNAL_ID, "user_a1b2c3d4")
        } else if (viewId == R.id.main_button_setIdentities) {
            val map: Map<DFIdentity, String> = mapOf(
                DFIdentity.EMAIL to "sample.user@example.com",
                DFIdentity.PHONE_NO to "821012345678",
                DFIdentity.KAKAO_USER_ID to "kakao_u98765",
                DFIdentity.LINE_USER_ID to "line_i10293"
            )
            DfineryProperties.setIdentities(map)
        }
    }

    private fun getAdvertisingId() {
        val weakContext = WeakReference(
            applicationContext
        )

        Thread(Runnable {
            val context = weakContext.get() ?: return@Runnable
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                DfineryProperties.setGoogleAdvertisingId(
                    adInfo.id,
                    adInfo.isLimitAdTrackingEnabled
                )
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get Advertising ID", e)
            }
        }).start()
    }

    private fun showPushNotificationIsClickedMessage(intent: Intent?) {
        if (intent != null && intent.hasExtra("com.igaworks.dfinery.IS_DFINERY_PUSH")) {
            val dfn = intent.getStringExtra(DfineryPushPayload.payload_root)
            if (TextUtils.isEmpty(dfn)) {
                return
            }
            try {
                val root = JSONObject(dfn)
                val clickAct = root.getJSONObject(DfineryPushPayload.payload_click_action)
                val uri = clickAct.getString(DfineryPushPayload.payload_click_action_deeplink)
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("사용자가 푸시 알림을 클릭했습니다.")
                    .setMessage(uri)
                    .show()
            } catch (e: JSONException) {
                Log.e(TAG, "parsing error", e)
            }
        }
    }
}
