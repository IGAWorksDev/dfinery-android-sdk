class DFEvent {
  static get LOGIN() { return "df_login"; }
  static get LOGOUT() { return "df_logout"; }
  static get SIGN_UP() { return "df_sign_up"; }
  static get PURCHASE() { return "df_purchase"; }
  static get REFUND() { return "df_refund"; }
  static get VIEW_HOME() { return "df_view_home"; }
  static get VIEW_PRODUCT_DETAILS() { return "df_view_product_details"; }
  static get ADD_TO_CART() { return "df_add_to_cart"; }
  static get ADD_TO_WISHLIST() { return "df_add_to_wishlist"; }
  static get VIEW_SEARCH_RESULT() { return "df_view_search_result"; }
  static get SHARE_PRODUCT() { return "df_share_product"; }
  static get VIEW_LIST() { return "df_view_list"; }
  static get VIEW_CART() { return "df_view_cart"; }
  static get REMOVE_CART() { return "df_remove_cart"; }
  static get ADD_PAYMENT_INFO() { return "df_add_payment_info"; }
}
class DFEventProperty {
  static get KEY_ARRAY_ITEMS() { return "df_items"; }
  static get KEY_STRING_ITEM_ID() { return "df_item_id"; }
  static get KEY_STRING_ITEM_NAME() { return "df_item_name"; }
  static get KEY_DOUBLE_PRICE() { return "df_price"; }
  static get KEY_LONG_QUANTITY() { return "df_quantity"; }
  static get KEY_DOUBLE_DISCOUNT() { return "df_discount"; }
  static get KEY_STRING_CATEGORY1() { return "df_category1"; }
  static get KEY_STRING_CATEGORY2() { return "df_category2"; }
  static get KEY_STRING_CATEGORY3() { return "df_category3"; }
  static get KEY_STRING_CATEGORY4() { return "df_category4"; }
  static get KEY_STRING_CATEGORY5() { return "df_category5"; }
  static get KEY_DOUBLE_TOTAL_REFUND_AMOUNT() { return "df_total_refund_amount"; }
  static get KEY_STRING_ORDER_ID() { return "df_order_id"; }
  static get KEY_DOUBLE_DELIVERY_CHARGE() { return "df_delivery_charge"; }
  static get KEY_STRING_PAYMENT_METHOD() { return "df_payment_method"; }
  static get KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT() { return "df_total_purchase_amount"; }
  static get KEY_STRING_SHARING_CHANNEL() { return "df_sharing_channel"; }
  static get KEY_STRING_SIGN_CHANNEL() { return "df_sign_channel"; }
  static get KEY_STRING_KEYWORD() { return "df_keyword"; }
}
class DFUserProfile {
  static get NAME() { return "df_name"; }
  static get GENDER() { return "df_gender"; }
  static get BIRTH() { return "df_birth"; }
  static get MEMBERSHIP() { return "df_membership"; }
  static get INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL() { return "df_push_optin"; }
  static get ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL() { return "df_push_ads_optin"; }
  static get ADVERTISING_NOTIFICATION_FOR_SMS_CHANNEL() { return "df_sms_ads_optin"; }
  static get ADVERTISING_NOTIFICATION_FOR_KAKAO_CHANNEL() { return "df_kakao_ads_optin"; }
  static get ADVERTISING_NOTIFICATION_AT_NIGHT_FOR_PUSH_CHANNEL() { return "df_push_night_ads_optin"; }
}
class DFGender {
  static get MALE() { return "Male"; }
  static get FEMALE() { return "Female"; }
  static get NON_BINARY() { return "NonBinary"; }
  static get OTHER() { return "Other"; }
}
class DFIdentity {
  static get EXTERNAL_ID() { return "external_id";}
  static get EMAIL() { return "email";}
  static get PHONE_NO() { return "phone_no";}
  static get KAKAO_USER_ID() { return "kakao_user_id";}
  static get LINE_USER_ID() { return "line_user_id";}
}
function isAndroidBridgeAvailable() {
  var result = false;
  try{
    if (window.dfineryInternalBridge) {
      result = true;
    }
    if (!result) {
      console.log("Android dfineryInternalBridge not found.");
    }
  } catch(err){
    console.log(err);
  }
  return result;
}
function isIosBridgeAvailable() {
    var result = false;
    try {
        if (window.webkit
            && window.webkit.messageHandlers
            && window.webkit.messageHandlers.dfineryInternalBridge) {
            result = true;
        }
        if (!result) {
            console.log("iOS dfineryInternalBridge not found.");
        }
    } catch (err) {
        console.log(err);
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
function sendMessage(message){
    if(isAndroidBridgeAvailable()){
      window.dfineryInternalBridge.invoke(encodeMessage(message));
    }
    if(isIosBridgeAvailable()){
      window.webkit.messageHandlers.dfineryInternalBridge.postMessage(message);
    }
}
const Dfinery = 'Dfinery';
const DfineryProperties = 'DfineryProperties';
const DfineryConfig = 'DfineryConfig';
var dfineryBridge = {
  logEvent: function(eventName, properties){
    const message = makeMessage(Dfinery, 'logEvent', eventName, properties);
    sendMessage(message);
  },
  suspendUserTargeting: function(){
    const message = makeMessage(Dfinery, 'suspendUserTargeting');
    sendMessage(message);
  },
  setGoogleAdvertisingId: function(googleAdvertisingId, isLimitAdTrackingEnabled){
    const message = makeMessage(DfineryProperties, 'setGoogleAdvertisingId', googleAdvertisingId, isLimitAdTrackingEnabled);
    sendMessage(message);
  },
  setUserProfile: function(key, value){
    const message = makeMessage(DfineryProperties, 'setUserProfile', key, value);
    sendMessage(message);
  },
  setUserProfiles: function(values){
    const message = makeMessage(DfineryProperties, 'setUserProfiles', values);
    sendMessage(message);
  },
  setIdentity: function(key, value){
    const message = makeMessage(DfineryProperties, 'setIdentity', key, value);
    sendMessage(message);
  },
  setIdentities: function(values){
    const message = makeMessage(DfineryProperties, 'setIdentities', values);
    sendMessage(message);
  },
  resetIdentity: function(){
    const message = makeMessage(DfineryProperties, 'resetIdentity');
    sendMessage(message);
  },
  setPushToken: function(pushToken){
    const message = makeMessage(DfineryProperties, 'setPushToken', pushToken);
    sendMessage(message);
  }
};