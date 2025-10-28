package com.igaworks.dfineryhybridappsample;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinery.constants.DFIdentity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DfineryJavascriptInterface {
    public static final String TAG = "DfineryJavascriptInterface";
    @JavascriptInterface
    public void logEvent(String eventName, String properties){
        if(TextUtils.isEmpty(properties)){
           Dfinery.getInstance().logEvent(eventName);
        }else{
            try {
                JSONObject jsonObject = new JSONObject(properties);
                Dfinery.getInstance().logEvent(eventName, jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @JavascriptInterface
    public void enableSDK(){
        Dfinery.getInstance().enableSDK();
    }
    @JavascriptInterface
    public void disableSDK(){
        Dfinery.getInstance().disableSDK();
    }
    @JavascriptInterface
    public void setUserProfile(String keyAndValue){
        try {
            JSONObject jsonObject = new JSONObject(keyAndValue);
            String key = jsonObject.getString("key");
            Object value = jsonObject.get("value");
            if(value instanceof Boolean){
                DfineryProperties.setUserProfile(key, (Boolean)value);
            }
            if(value instanceof Long){
                DfineryProperties.setUserProfile(key, (Long)value);
            }
            if(value instanceof Double){
                DfineryProperties.setUserProfile(key, (Double)value);
            }
            if(value instanceof String){
                DfineryProperties.setUserProfile(key, (String)value);
            }
            if(value instanceof JSONArray){
                DfineryProperties.setUserProfile(key, (JSONArray) value);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @JavascriptInterface
    public void setUserProfiles(String values){
        try {
            JSONObject jsonObject = new JSONObject(values);
            Iterator<String> keys = jsonObject.keys();
            Map<String, Object> map = new HashMap<>();
            while(keys.hasNext()){
                String key = keys.next();
                map.put(key, jsonObject.get(key));
            }
            DfineryProperties.setUserProfiles(map);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @JavascriptInterface
    public void setIdentity(String key, String value){
        DFIdentity identity = DFIdentity.get(key);
        DfineryProperties.setIdentity(identity, value);
    }
    @JavascriptInterface
    public void setIdentities(String values){
        try {
            JSONObject jsonObject = new JSONObject(values);
            Iterator<String> keys = jsonObject.keys();
            Map<DFIdentity, String> map = new HashMap<>();
            while(keys.hasNext()){
                String key = keys.next();
                map.put(DFIdentity.get(key), jsonObject.getString(key));
            }
            DfineryProperties.setIdentities(map);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @JavascriptInterface
    public void resetIdentity(){
        DfineryProperties.resetIdentity();
    }
}
