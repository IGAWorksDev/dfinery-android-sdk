const Dfinery = 'Dfinery';
const DfineryProperties = 'DfineryProperties';
const DfineryConfig = 'DfineryConfig';

const LOGIN = "df_login";
const LOGOUT = "df_logout";
const SIGN_UP = "df_sign_up";
const PURCHASE = "df_purchase";
const REFUND = "df_refund";
const VIEW_HOME = "df_view_home";
const VIEW_PRODUCT_DETAILS = "df_view_product_details";
const ADD_TO_CART = "df_add_to_cart";
const ADD_TO_WISHLIST = "df_add_to_wishlist";
const VIEW_SEARCH_RESULT = "df_view_search_result";
const SHARE_PRODUCT = "df_share_product";
const VIEW_LIST = "df_view_list";
const VIEW_CART = "df_view_cart";
const REMOVE_CART = "df_remove_cart";
const ADD_PAYMENT_INFO = "df_add_payment_info";

const KEY_ARRAY_ITEMS = "df_items";
const KEY_STRING_ITEM_ID = "df_item_id";
const KEY_STRING_ITEM_NAME = "df_item_name";
const KEY_DOUBLE_PRICE = "df_price";
const KEY_LONG_QUANTITY = "df_quantity";
const KEY_DOUBLE_DISCOUNT = "df_discount";
const KEY_STRING_CATEGORY1 = "df_category1";
const KEY_STRING_CATEGORY2 = "df_category2";
const KEY_STRING_CATEGORY3 = "df_category3";
const KEY_STRING_CATEGORY4 = "df_category4";
const KEY_STRING_CATEGORY5 = "df_category5";
const KEY_DOUBLE_TOTAL_REFUND_AMOUNT = "df_total_refund_amount";
const KEY_STRING_ORDER_ID = "df_order_id";
const KEY_DOUBLE_DELIVERY_CHARGE = "df_delivery_charge";
const KEY_STRING_PAYMENT_METHOD = "df_payment_method";
const KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT = "df_total_purchase_amount";
const KEY_STRING_SHARING_CHANNEL = "df_sharing_channel";
const KEY_STRING_SIGN_CHANNEL = "df_sign_channel";
const KEY_STRING_KEYWORD = "df_keyword";

const NAME = "df_name";
const GENDER = "df_gender";
const BIRTH = "df_birth";
const MEMBERSHIP = "df_membership";
const INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL = "df_push_optin";
const ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL = "df_push_ads_optin";
const ADVERTISING_NOTIFICATION_FOR_SMS_CHANNEL = "df_sms_ads_optin";
const ADVERTISING_NOTIFICATION_FOR_KAKAO_CHANNEL = "df_kakao_ads_optin";
const ADVERTISING_NOTIFICATION_AT_NIGHT_FOR_PUSH_CHANNEL = "df_push_night_ads_optin";

const MALE = "Male";
const FEMALE = "Female";
const NON_BINARY = "NonBinary";
const OTHER = "Other";

const EXTERNAL_ID = "external_id";
const EMAIL = "email";
const PHONE_NO = "phone_no";
const KAKAO_USER_ID = "kakao_user_id";
const LINE_USER_ID = "line_user_id";

function isAndroidBridgeAvailable() {
  var result = false;
  if (window.dfineryInternalBridge) {
    result = true;
  }
  if (!result) {
    console.log("Android dfineryInternalBridge not found.");
  }
  return result;
}
function makeMessage(){
  const message = new Object();
  message['className'] = arguments[0];
  message['methodName'] = arguments[1];
  if(arguments.length > 2){
    const methodParam = new Object();
    for (let i = 2; i <= arguments.length; i++) {
      const key = 'args'+(i-1);
      methodParam[key] = arguments[i];
    }
    message['methodParam'] = methodParam;
  }
  return message;
}
function encodeMessage(message){
    const json = JSON.stringify(message);
    const encoded = encodeURIComponent(json);
    return window.btoa(encoded);
}

var dfineryBridge = {
  logEvent: function(eventName, properties){
    const message = makeMessage(Dfinery, 'logEvent', eventName, properties);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  suspendUserTargeting: function(){
    const message = makeMessage(Dfinery, 'suspendUserTargeting');
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setGoogleAdvertisingId: function(googleAdvertisingId, isLimitAdTrackingEnabled){
    const message = makeMessage(DfineryProperties, 'setGoogleAdvertisingId', googleAdvertisingId, isLimitAdTrackingEnabled);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setUserProfile: function(key, value){
    const message = makeMessage(DfineryProperties, 'setUserProfile', key, value);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setUserProfiles: function(values){
    const message = makeMessage(DfineryProperties, 'setUserProfiles', values);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setIdentity: function(key, value){
    const message = makeMessage(DfineryProperties, 'setIdentity', key, value);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setIdentities: function(values){
    const message = makeMessage(DfineryProperties, 'setIdentities', values);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  resetIdentity: function(){
    const message = makeMessage(DfineryProperties, 'resetIdentity');
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setPushToken: function(pushToken){
    const message = makeMessage(DfineryProperties, 'setPushToken', pushToken);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setLogEnable: function(enable){
    const message = makeMessage(DfineryConfig, 'setLogEnable', enable);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setLogLevel: function(logLevel){
    const message = makeMessage(DfineryConfig, 'setLogLevel', logLevel);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setNotificationIcon: function(resourceId){
    const message = makeMessage(DfineryConfig, 'setNotificationIcon', resourceId);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  },
  setDefaultNotificationChannelId: function(id){
    const message = makeMessage(DfineryConfig, 'setDefaultNotificationChannelId', id);
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
  }
};
