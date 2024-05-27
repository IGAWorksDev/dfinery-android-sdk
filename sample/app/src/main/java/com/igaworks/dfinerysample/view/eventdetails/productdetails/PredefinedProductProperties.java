package com.igaworks.dfinerysample.view.eventdetails.productdetails;

import com.igaworks.dfinerysample.view.eventdetails.ValueType;

import java.util.HashMap;
import java.util.Map;

public enum PredefinedProductProperties {
    ITEM_ID("df_item_id", ValueType.String, true),
    ITEM_NAME("df_item_name", ValueType.String, true),
    PRICE("df_price", ValueType.Double, true),
    QUANTITY("df_quantity", ValueType.Long, true),
    DISCOUNT("df_discount", ValueType.Double, true),
    CATEGORY1("df_category1", ValueType.String, false),
    CATEGORY2("df_category2", ValueType.String, false),
    CATEGORY3("df_category3", ValueType.String, false),
    CATEGORY4("df_category4", ValueType.String, false),
    CATEGORY5("df_category5", ValueType.String, false),
    MANUAL("", null, false);
    private final String key;
    private final ValueType valueType;
    private final boolean isRequired;
    private static Map<String, PredefinedProductProperties> map;
    static {
        map = new HashMap<>();
        for(PredefinedProductProperties item: PredefinedProductProperties.values()){
            map.put(item.name(), item);
        }
    }

    PredefinedProductProperties(String key, ValueType valueType, boolean isRequired) {
        this.key = key;
        this.valueType = valueType;
        this.isRequired = isRequired;
    }
    public static PredefinedProductProperties get(String name){
        return map.get(name);
    }

    public String getKey() {
        return key;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public boolean isRequired() {
        return isRequired;
    }
}
