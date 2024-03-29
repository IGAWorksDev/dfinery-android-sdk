package com.igaworks.dfinerysample.view.eventdetails;

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
        return valueType;
    }
}
