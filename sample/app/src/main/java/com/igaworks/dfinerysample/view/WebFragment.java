package com.igaworks.dfinerysample.view;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.igaworks.dfinery.DfineryBridge;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.WebFragmentBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebFragment extends Fragment {
    public static final String TAG = Utils.getTagFromClass(WebFragment.class);
    private WebFragmentBinding binding;
    public WebFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WebFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        initWebView();
        binding.webWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
        if(getActivity() == null){
            return;
        }
        if(getActivity().getAssets() == null){
            return;
        }
        BufferedReader reader = null;
        StringBuilder javascriptString = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("dfinery-html-bridge.js"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                javascriptString.append(line);
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        view.evaluateJavascript(javascriptString.toString(), null);
    }
}
