package com.igaworks.dfineryhybridappsample;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinery.models.DfineryPushPayload;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final String ACTION_SHOW_MESSAGE = "com.igaworks.dfinery.MainActivity.ACTION_SHOW_MESSAGE";
    public static final String EXTRA_TITLE = "com.igaworks.dfinery.MainActivity.EXTRA_TITLE";
    public static final String EXTRA_MESSAGE = "com.igaworks.dfinery.MainActivity.EXTRA_MESSAGE";
    public static final int REQUEST_CODE_POST_NOTIFICATIONS = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_frameLayout_rootContainer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
        }
        getAdvertisingId();
        showPushNotificationIsClickedMessage(getIntent());

        WebView webView = findViewById(R.id.main_webView);
        initWebView(webView);
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if(ACTION_SHOW_MESSAGE.equals(intent.getAction())){
            String title = intent.getStringExtra(EXTRA_TITLE);
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            if(!TextUtils.isEmpty(title)){
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .show();
            }
        }
        showPushNotificationIsClickedMessage(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WebView webView = findViewById(R.id.main_webView);
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebView webView = findViewById(R.id.main_webView);
        webView.onResume();
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

    private void showPushNotificationIsClickedMessage(Intent intent){
        if(intent!=null && intent.hasExtra("com.igaworks.dfinery.IS_DFINERY_PUSH")){
            String dfn = intent.getStringExtra(DfineryPushPayload.payload_root);
            if(TextUtils.isEmpty(dfn)){
                return;
            }
            try {
                JSONObject root = new JSONObject(dfn);
                JSONObject clickAct = root.getJSONObject(DfineryPushPayload.payload_click_action);
                String uri = clickAct.getString(DfineryPushPayload.payload_click_action_deeplink);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("사용자가 푸시 알림을 클릭했습니다.")
                        .setMessage(uri)
                        .show();
            } catch (JSONException e) {
                Log.e(TAG, "parsing error", e);
            }
        }
    }

    private void initWebView(WebView webView){
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(TAG, "["+consoleMessage.messageLevel().name()+"] "+consoleMessage.sourceId()+" ("+consoleMessage.lineNumber()+") "+consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished. url:"+url);
                if(url.equals("file:///android_asset/index.html")){
                    initJavascript(view);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted. url:"+url);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setEnableSmoothTransition(true);
        }
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        webView.addJavascriptInterface(new DfineryJavascriptInterface(), "dfineryWebBridge");
        webView.loadUrl("file:///android_asset/index.html");
    }

    /**
     * java 코드를 통해 html에 script를 추가하는 방법
     * @param view
     */
    private void initJavascript(WebView view){
        BufferedReader reader = null;
        StringBuilder javascriptString = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("dfinery-bridge.js"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                javascriptString.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "error", e);
                }
            }
        }
        view.evaluateJavascript(javascriptString.toString(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d(TAG, "onReceiveValue: "+value);
            }
        });
    }
}