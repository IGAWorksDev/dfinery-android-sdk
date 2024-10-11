package com.igaworks.dfinerysample.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.igaworks.dfinerysample.BuildConfig;

import java.util.Map;

public class PreferenceHelper {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = BuildConfig.APPLICATION_ID;
    public PreferenceHelper(Context context) {
        this.context = context;
        this.sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void putString(String key, String var2) {
        putString(key, var2, false);
    }
    public void putString(String key, String var2, boolean isSynchronized) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, var2);
        if(isSynchronized){
            editor.commit();
        } else{
            editor.apply();
        }
    }

    public void putInt(String key, int var2) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key, var2);
        editor.apply();
    }

    public void putLong(String key, long var2) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putLong(key, var2);
        editor.apply();
    }

    public void putFloat(String key, float var2) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putFloat(key, var2);
        editor.apply();
    }

    public void putBoolean(String key, boolean var2) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key, var2);
        editor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public void clear() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }
}
