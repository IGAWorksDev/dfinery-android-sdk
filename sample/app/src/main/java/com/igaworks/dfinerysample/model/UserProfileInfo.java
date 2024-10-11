package com.igaworks.dfinerysample.model;

import androidx.annotation.NonNull;

import com.igaworks.dfinery.internal.support.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;

public class UserProfileInfo {
    private String key;
    private Object value;

    public UserProfileInfo(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
    public String getValueString(){
        if(value instanceof Date){
            return DateUtils.getSimpleDateFormat(DateUtils.EVENT_DATE_TIME_FORMAT).format((Date)value);
        }
        if(value instanceof Long[]){
            return Arrays.toString(((Long[])value));
        }
        if(value instanceof Double[]){
            return Arrays.toString(((Double[])value));
        }
        if(value instanceof String[]){
            return Arrays.toString(((String[])value));
        }
        return ""+value;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(key);
//        stringBuilder.append(", ");
//        stringBuilder.append(value);
        return stringBuilder.toString();
    }
}
