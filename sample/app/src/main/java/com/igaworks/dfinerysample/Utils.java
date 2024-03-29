package com.igaworks.dfinerysample;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class Utils {
    public static boolean not(boolean condition){
        return !condition;
    }
    public static boolean notNull(Object object){
        if(object != null){
            return true;
        } else{
            return false;
        }
    }
    public static boolean isNull(Object object){
        if(object == null){
            return true;
        } else{
            return false;
        }
    }
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
    public static boolean notNullAndHasItems(String value){
        return !isNullOrEmpty(value);
    }
    public static boolean isNullOrEmpty(Bundle bundle){
        boolean result = false;
        if(bundle == null){
            result = true;
            return result;
        }
        if(bundle.size() == 0){
            result = true;
            return result;
        }
        return result;
    }
    public static boolean notNullAndHasItems(Bundle value){
        return !isNullOrEmpty(value);
    }
    public static boolean isNullOrEmpty(List list){
        boolean result = false;
        if(list == null){
            result = true;
            return result;
        }
        if(list.size() == 0){
            result = true;
            return result;
        }
        return result;
    }
    public static boolean notNullAndHasItems(List value){
        return !isNullOrEmpty(value);
    }
    public static boolean isNullOrEmpty(Queue queue){
        boolean result = false;
        if(queue == null){
            result = true;
            return result;
        }
        if(queue.size() == 0){
            result = true;
            return result;
        }
        return result;
    }
    public static boolean notNullAndHasItems(Queue value){
        return !isNullOrEmpty(value);
    }
    public static boolean isNullOrEmpty(Map map){
        boolean result = false;
        if(map == null){
            result = true;
            return result;
        }
        if(map.isEmpty()){
            result = true;
            return result;
        }
        return result;
    }
    public static boolean notNullAndHasItems(Map value){
        return !isNullOrEmpty(value);
    }
    public static boolean isNullOrEmpty(Object[] value){
        boolean result = false;
        if(value == null){
            result = true;
            return result;
        }
        if(value.length == 0){
            result = true;
            return result;
        }
        return result;
    }
    public static boolean notNullAndHasItems(Object[] value){
        return !isNullOrEmpty(value);
    }
    public static boolean isNullOrEmpty(JSONObject value){
        boolean result = false;
        if(value == null){
            result = true;
            return result;
        }
        if(value.length() == 0){
            result = true;
            return result;
        }
        return result;
    }
    public static boolean notNullAndHasItems(JSONObject value){
        return !isNullOrEmpty(value);
    }
    public static boolean isNullOrEmpty(JSONArray value){
        boolean result = false;
        if(value == null){
            result = true;
            return result;
        }
        if(value.length() == 0){
            result = true;
            return result;
        }
        return result;
    }
    public static boolean notNullAndHasItems(JSONArray value){
        return !isNullOrEmpty(value);
    }
    public static String getTagFromClass(Class clazz){
        return clazz.getSimpleName();
    }
}
