package com.igaworks.dfinerysample.view.eventdetails;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum ValueType {
    String,
    Boolean,
    Long,
    Double,
    Datetime,
    Product,
    ArrayOfString,
    ArrayOfLong,
    ArrayOfDouble;
    public static ValueType get(String value){
        if(TextUtils.isEmpty(value)){
            return String;
        }
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = simpleDateFormat.parse(value);
        } catch (ParseException e) {}
        if(date != null){
            return Datetime;
        }
        String valueRemovedMinus = value.replace("-", "");
        if(TextUtils.isDigitsOnly(valueRemovedMinus)){
            return Long;
        }
        String valueRemovedDot = valueRemovedMinus.replace(".", "");
        if(TextUtils.isDigitsOnly(valueRemovedDot)){
            return Double;
        }
        if("true".equals(value)){
            return Boolean;
        }
        if("false".equals(value)){
            return Boolean;
        }
        return getArrayValueType(value);
    }
    private static ValueType getArrayValueType(String value){
        if(!value.contains(",")){
            return String;
        }
        String valueRemovedComma = value.replace(",", "");
        String valueRemovedMinus = valueRemovedComma.replace("-", "");
        if(TextUtils.isDigitsOnly(valueRemovedMinus)){
            return ArrayOfLong;
        }
        String valueRemovedDot = valueRemovedMinus.replace(".", "");
        if(TextUtils.isDigitsOnly(valueRemovedDot)){
            return ArrayOfDouble;
        }
        return ArrayOfString;
    }
    public static Object getCastedValue(String value){
        switch (ValueType.get(value)){
            case Long: return java.lang.Long.valueOf(value);
            case Double: return java.lang.Double.valueOf(value);
            case Boolean: return java.lang.Boolean.valueOf(value);
            default: return value;
        }
    }
}
