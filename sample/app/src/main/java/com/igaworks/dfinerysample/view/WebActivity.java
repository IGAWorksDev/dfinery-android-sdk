package com.igaworks.dfinerysample.view;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.igaworks.dfinery.DfineryBridge;
import com.igaworks.dfinerysample.databinding.WebActivityBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebActivity extends BaseActionBarActivity{
    public static final String TAG = "WebActivity";
    private WebActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WebActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Dfinery WebView Demo");
        initWebView();
        binding.webWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.webWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webWebView.removeJavascriptInterface(DfineryBridge.JAVASCRIPT_INTERFACE_NAME);
        binding.webWebView.destroy();
        binding = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.webWebView.onPause();
    }

    private void initWebView(){
        binding.webWebView.setHorizontalScrollBarEnabled(false);
        binding.webWebView.setVerticalScrollBarEnabled(true);
        binding.webWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        binding.webWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(TAG, "["+consoleMessage.messageLevel().name()+"] "+consoleMessage.sourceId()+" ("+consoleMessage.lineNumber()+") "+consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        binding.webWebView.setWebViewClient(new WebViewClient(){
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
            binding.webWebView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true);
        }
        WebSettings webSettings = binding.webWebView.getSettings();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        binding.webWebView.addJavascriptInterface(new DfineryBridge(), DfineryBridge.JAVASCRIPT_INTERFACE_NAME);
    }
    private void initJavascript(WebView view){
        BufferedReader reader = null;
        StringBuilder javascriptString = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("dfinery-html-bridge.js"), "UTF-8"));
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
