package com.igaworks.dfinerysample.view.eventdetails;

import com.igaworks.dfinery.constants.DF;

import java.util.HashMap;
import java.util.Map;

public enum PredefinedEventProperties {
    TOTAL_REFUND_AMOUNT( "df_total_refund_amount", ValueType.Double, DF.Event.REFUND),
    ORDER_ID( "df_order_id", ValueType.String, DF.Event.PURCHASE),
    DELIVERY_CHARGE("df_delivery_charge", ValueType.Double, DF.Event.PURCHASE),
    PAYMENT_METHOD("df_payment_method", ValueType.String, DF.Event.PURCHASE),
    TOTAL_PURCHASE_AMOUNT("df_total_purchase_amount", ValueType.Double, DF.Event.PURCHASE),
    SHARING_CHANNEL("df_sharing_channel", ValueType.String, DF.Event.SHARE_PRODUCT),
    SIGN_CHANNEL("df_sign_channel", ValueType.String, DF.Event.SIGN_UP),
    KEYWORD("df_keyword", ValueType.String, DF.Event.VIEW_SEARCH_RESULT),
    PRODUCT("{product}", ValueType.Product, null),
    MANUAL("", null, null);
    private final String key;
    private final ValueType valueType;
    private final String matchedEventName;
    private static Map<String, PredefinedEventProperties> map;
    static{
        map = new HashMap<>();
        for(PredefinedEventProperties item : PredefinedEventProperties.values()){
            map.put(item.name(), item);
        }
    }

    PredefinedEventProperties(String key, ValueType valueType, String matchedEventName) {
        this.key = key;
        this.valueType = valueType;
        this.matchedEventName = matchedEventName;
    }
    public static PredefinedEventProperties get(String name){
        return map.get(name);
    }

    public String getKey() {
        return key;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public String getMatchedEventName() {
        return matchedEventName;
    }
}
