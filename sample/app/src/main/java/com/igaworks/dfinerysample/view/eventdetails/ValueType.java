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
    public static ValueType get(String value, String dateFormat){
        if(TextUtils.isEmpty(value)){
            return String;
        }
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
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
        String converted1 = value.replace(",", "");
        String converted2 = converted1.replace("-", "");
        String converted3 = converted2.substring(1);
        String converted4 = converted3.substring(0, converted3.length()-1);
        if(TextUtils.isDigitsOnly(converted4)){
            return ArrayOfLong;
        }
        String valueRemovedDot = converted4.replace(".", "");
        if(TextUtils.isDigitsOnly(valueRemovedDot)){
            return ArrayOfDouble;
        }
        return ArrayOfString;
    }
}
