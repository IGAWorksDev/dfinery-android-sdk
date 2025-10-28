var dfineryBridge = {
  logEvent: function(eventName, properties){
      if(isAndroidBridgeAvailable()){
        if(properties === null || properties === undefined){
          window.dfineryWebBridge.logEvent(eventName, null);
        } else{
          window.dfineryWebBridge.logEvent(eventName, JSON.stringify(properties));
        }
      }
    },
    enableSDK: function(){
        if(isAndroidBridgeAvailable()){
            window.dfineryWebBridge.enableSDK();
        }
    },
    disableSDK: function(){
        if(isAndroidBridgeAvailable()){
            window.dfineryWebBridge.disableSDK();
        }
    },
    setUserProfile: function(key, value){
      if(isAndroidBridgeAvailable()){
        const param = {
          'key': key,
          'value':value
        };
        window.dfineryWebBridge.setUserProfile(JSON.stringify(param));
      }
    },
    setUserProfiles: function(values){
      if(isAndroidBridgeAvailable()){
        window.dfineryWebBridge.setUserProfiles(JSON.stringify(values));
      }
    },
    setIdentity: function(key, value){
      if(isAndroidBridgeAvailable()){
        window.dfineryWebBridge.setIdentity(key, value);
      }
    },
    setIdentities: function(values){
      if(isAndroidBridgeAvailable()){
        window.dfineryWebBridge.setIdentities(JSON.stringify(values));
      }
    },
    resetIdentity: function(){
      if(isAndroidBridgeAvailable()){
        window.dfineryWebBridge.resetIdentity();
      }
    }
};
function isAndroidBridgeAvailable() {
  var result = false;
  try{
    if (window.dfineryWebBridge) {
      result = true;
    }
    if (!result) {
      console.log("Android dfineryWebBridge not found.");
    }
  } catch(err){
    console.log(err);
  }
  return result;
}

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
  static get ITEMS() { return "df_items"; }
  static get ITEM_ID() { return "df_item_id"; }
  static get ITEM_NAME() { return "df_item_name"; }
  static get ITEM_PRICE() { return "df_price"; }
  static get ITEM_QUANTITY() { return "df_quantity"; }
  static get ITEM_DISCOUNT() { return "df_discount"; }
  static get ITEM_CATEGORY1() { return "df_category1"; }
  static get ITEM_CATEGORY2() { return "df_category2"; }
  static get ITEM_CATEGORY3() { return "df_category3"; }
  static get ITEM_CATEGORY4() { return "df_category4"; }
  static get ITEM_CATEGORY5() { return "df_category5"; }
  static get DISCOUNT() { return "df_discount"; }
  static get TOTAL_REFUND_AMOUNT() { return "df_total_refund_amount"; }
  static get ORDER_ID() { return "df_order_id"; }
  static get DELIVERY_CHARGE() { return "df_delivery_charge"; }
  static get PAYMENT_METHOD() { return "df_payment_method"; }
  static get TOTAL_PURCHASE_AMOUNT() { return "df_total_purchase_amount"; }
  static get SHARING_CHANNEL() { return "df_sharing_channel"; }
  static get SIGN_CHANNEL() { return "df_sign_channel"; }
  static get KEYWORD() { return "df_keyword"; }
}
class DFUserProfile {
  static get NAME() { return "df_name"; }
  static get GENDER() { return "df_gender"; }
  static get BIRTH() { return "df_birth"; }
  static get MEMBERSHIP() { return "df_membership"; }
  static get PUSH_ADS_OPTIN() { return "df_push_ads_optin"; }
  static get SMS_ADS_OPTIN() { return "df_sms_ads_optin"; }
  static get KAKAO_ADS_OPTIN() { return "df_kakao_ads_optin"; }
  static get PUSH_NIGHT_ADS_OPTIN() { return "df_push_night_ads_optin"; }
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