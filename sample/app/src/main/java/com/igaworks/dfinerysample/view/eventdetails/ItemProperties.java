package com.igaworks.dfinerysample.view.eventdetails;

import org.json.JSONObject;

public class ItemProperties {
    private String key;
    private String value;
    private ValueType valueType;

    public ItemProperties(String key, ValueType valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public ValueType getValueType() {
        if(valueType == null){
            return ValueType.String;
        }
        return valueType;
    }
    public Object getCastedValueByValueType(){
        if(value == null){
            return JSONObject.NULL;
        }
        ValueType wrappedValueType = getValueType();
        if(wrappedValueType.equals(ValueType.Long)){
            return java.lang.Long.valueOf(value);
        }
        if(wrappedValueType.equals(ValueType.Double)){
            return java.lang.Double.valueOf(value);
        }
        if(wrappedValueType.equals(ValueType.Boolean)){
            return java.lang.Boolean.valueOf(value);
        }
        return value;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
