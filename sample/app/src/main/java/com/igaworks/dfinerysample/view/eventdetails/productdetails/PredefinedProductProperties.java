package com.igaworks.dfinerysample.view.eventdetails.productdetails;

import com.igaworks.dfinerysample.view.eventdetails.ValueType;

import java.util.HashMap;
import java.util.Map;

public enum PredefinedProductProperties {
    ITEM_ID("df_item_id", ValueType.String),
    ITEM_NAME("df_item_name", ValueType.String),
    PRICE("df_price", ValueType.Double),
    QUANTITY("df_quantity", ValueType.Long),
    DISCOUNT("df_discount", ValueType.Double),
    CATEGORY1("df_category1", ValueType.String),
    CATEGORY2("df_category2", ValueType.String),
    CATEGORY3("df_category3", ValueType.String),
    CATEGORY4("df_category4", ValueType.String),
    CATEGORY5("df_category5", ValueType.String),
    MANUAL("", null);
    private final String key;
    private final ValueType valueType;
    private static Map<String, PredefinedProductProperties> map;
    static {
        map = new HashMap<>();
        for(PredefinedProductProperties item: PredefinedProductProperties.values()){
            map.put(item.name(), item);
        }
    }

    PredefinedProductProperties(String key, ValueType valueType) {
        this.key = key;
        this.valueType = valueType;
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
}
