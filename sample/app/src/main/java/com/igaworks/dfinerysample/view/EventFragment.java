package com.igaworks.dfinerysample.view;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.constants.DF;
import com.igaworks.dfinery.internal.support.component.DfineryJSONObject;
import com.igaworks.dfinerysample.enums.PreferenceKey;
import com.igaworks.dfinerysample.helper.PreferenceHelper;
import com.igaworks.dfinerysample.model.EventInfo;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.EventFragmentBinding;
import com.igaworks.dfinerysample.view.eventdetails.EventDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = Utils.getTagFromClass(EventFragment.class);
    private EventFragmentBinding binding;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public EventFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EventFragmentBinding.inflate(inflater, container, false);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                if(activityResult.getResultCode() == RESULT_OK){
                    Snackbar.make(binding.eventConstraintLayoutContainer, "이벤트가 등록되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else if (activityResult.getResultCode() == RESULT_CANCELED){
                    Snackbar.make(binding.eventConstraintLayoutContainer, "이벤트 등록이 취소되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else{
                    Snackbar.make(binding.eventConstraintLayoutContainer, "이벤트가 등록 중 오류가 발생했습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });
        setClickListener();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        activityResultLauncher = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.event_button_login) {
            startEventDetailActivity(DF.Event.LOGIN);
        } else if (id == R.id.event_button_logout) {
            startEventDetailActivity(DF.Event.LOGOUT);
        } else if (id == R.id.event_button_customEvent) {
            showInputFormForCustomEventName();
        } else if (id == R.id.event_button_signUp) {
            startEventDetailActivity(DF.Event.SIGN_UP);
        } else if (id == R.id.event_button_purchase) {
            startEventDetailActivity(DF.Event.PURCHASE);
        } else if (id == R.id.event_button_viewHome) {
            startEventDetailActivity(DF.Event.VIEW_HOME);
        } else if (id == R.id.event_button_productView) {
            startEventDetailActivity(DF.Event.VIEW_PRODUCT_DETAILS);
        } else if (id == R.id.event_button_addToCart) {
            startEventDetailActivity(DF.Event.ADD_TO_CART);
        } else if (id == R.id.event_button_addToWishlist) {
            startEventDetailActivity(DF.Event.ADD_TO_WISHLIST);
        } else if (id == R.id.event_button_refund) {
            startEventDetailActivity(DF.Event.REFUND);
        } else if (id == R.id.event_button_search) {
            startEventDetailActivity(DF.Event.VIEW_SEARCH_RESULT);
        } else if (id == R.id.event_button_share) {
            startEventDetailActivity(DF.Event.SHARE_PRODUCT);
        } else if (id == R.id.event_button_listView) {
            startEventDetailActivity(DF.Event.VIEW_LIST);
        } else if (id == R.id.event_button_cartView) {
            startEventDetailActivity(DF.Event.VIEW_CART);
        } else if (id == R.id.event_button_paymentInfoAdded) {
            startEventDetailActivity(DF.Event.ADD_PAYMENT_INFO);
        } else if (id == R.id.event_button_removeCart){
            startEventDetailActivity(DF.Event.REMOVE_CART);
        } else if(id == R.id.event_button_example){
            try {
                showExampleSelector();
            } catch (JSONException e) {

            }
        }
    }
    private void showInputFormForCustomEventName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("커스텀 이벤트의 이름을 입력하세요");
        // Set up the input
        final EditText editText = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);
        builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString();
                if(TextUtils.isEmpty(input)){
                    Snackbar.make(binding.eventConstraintLayoutContainer, "값이 입력되지 않았습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                startEventDetailActivity(input);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void showExampleSelector() throws JSONException {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("이벤트 이름을 선택하세요");
        final List<EventInfo> list = new ArrayList<>();
        JSONObject item = new JSONObject();
        item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "24062111");
        item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "단호박 에그 샌드위치");
        item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5500);
        item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500);
        item.put(DF.EventProperty.KEY_LONG_QUANTITY, 4);
        item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "푸드");
        item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "샌드위치");
        item.put(DF.EventProperty.KEY_STRING_CATEGORY3, "비건");
        item.put(DF.EventProperty.KEY_STRING_CATEGORY4, "에그");
        item.put(DF.EventProperty.KEY_STRING_CATEGORY5, "단호박");
        item.put("custom_item_string", "스타벅스");
        item.put("custom_item_long", 1);
        item.put("custom_item_boolean", true);
        item.put("custom_item_double", 1.11);
        JSONArray items = new JSONArray();
        items.put(item);
        list.add(new EventInfo(DF.Event.LOGIN));
        list.add(new EventInfo(DF.Event.LOGOUT));
        JSONObject signUpProperty = new JSONObject();
        signUpProperty.put(DF.EventProperty.KEY_STRING_SIGN_CHANNEL, "naver");
        list.add(new EventInfo(DF.Event.SIGN_UP, signUpProperty));
        JSONObject viewListProperty = new JSONObject();
        viewListProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        list.add(new EventInfo(DF.Event.VIEW_LIST, viewListProperty));
        list.add(new EventInfo(DF.Event.VIEW_HOME));
        list.add(new EventInfo(DF.Event.ADD_PAYMENT_INFO));
        JSONObject addToCartProperty = new JSONObject();
        addToCartProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        list.add(new EventInfo(DF.Event.ADD_TO_CART, addToCartProperty));
        JSONObject removeCartProperty = new JSONObject();
        removeCartProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        list.add(new EventInfo(DF.Event.REMOVE_CART, removeCartProperty));
        JSONObject viewCartProperty = new JSONObject();
        viewCartProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        list.add(new EventInfo(DF.Event.VIEW_CART, viewCartProperty));
        JSONObject addToWishlistProperty = new JSONObject();
        addToWishlistProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        list.add(new EventInfo(DF.Event.ADD_TO_WISHLIST, addToWishlistProperty));
        DfineryJSONObject purchaseProperty = new DfineryJSONObject();
        purchaseProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        purchaseProperty.put(DF.EventProperty.KEY_STRING_ORDER_ID, "2406211");
        purchaseProperty.put(DF.EventProperty.KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT, 20000.50);
        purchaseProperty.put(DF.EventProperty.KEY_STRING_PAYMENT_METHOD, "Card");
        purchaseProperty.put(DF.EventProperty.KEY_DOUBLE_DELIVERY_CHARGE, 2500);
        purchaseProperty.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 2000);
        purchaseProperty.put("custom_event_p_string", "푸드");
        purchaseProperty.put("custom_event_p_long", 4);
        purchaseProperty.put("custom_event_p_double", 5500.50);
        purchaseProperty.put("custom_event_p_boolean", true);
        purchaseProperty.put("custom_event_p_boolean2", JSONObject.NULL);
        purchaseProperty.put("custom_event_p_dateTime", new Date());
        JSONArray stringList = new JSONArray();
        stringList.put("event_p1");
        stringList.put("event_p2");
        stringList.put("event_p3");
        purchaseProperty.put("custom_event_p_string_list", stringList);
        JSONArray longList = new JSONArray();
        longList.put(24);
        longList.put(06);
        longList.put(21);
        longList.put(28);
        purchaseProperty.put("custom_event_p_long_list", longList);
        JSONArray doubleList = new JSONArray();
        doubleList.put(24.01);
        doubleList.put(06.02);
        doubleList.put(21.03);
        doubleList.put(28.04);
        purchaseProperty.put("custom_event_p_double_list", doubleList);
        list.add(new EventInfo(DF.Event.PURCHASE, purchaseProperty));
        JSONObject refundProperty = new JSONObject();
        refundProperty.put(DF.EventProperty.KEY_DOUBLE_TOTAL_REFUND_AMOUNT, 20000.50);
        list.add(new EventInfo(DF.Event.REFUND));
        JSONObject shareProductProperty = new JSONObject();
        shareProductProperty.put(DF.EventProperty.KEY_STRING_SHARING_CHANNEL, "naver");
        list.add(new EventInfo(DF.Event.SHARE_PRODUCT, shareProductProperty));
        DfineryJSONObject viewProductDetailsProperty = new DfineryJSONObject();
        viewProductDetailsProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        viewProductDetailsProperty.put("custom_event_p_string", "푸드");
        viewProductDetailsProperty.put("custom_event_p_long", 4);
        viewProductDetailsProperty.put("custom_event_p_double", 5500.50);
        viewProductDetailsProperty.put("custom_event_p_boolean", true);
        viewProductDetailsProperty.put("custom_event_p_boolean2", JSONObject.NULL);
        viewProductDetailsProperty.put("custom_event_p_dateTime", new Date());
        viewProductDetailsProperty.put("custom_event_p_string_list", stringList);
        viewProductDetailsProperty.put("custom_event_p_long_list", longList);
        viewProductDetailsProperty.put("custom_event_p_double_list", doubleList);
        list.add(new EventInfo(DF.Event.VIEW_PRODUCT_DETAILS, viewProductDetailsProperty));
        DfineryJSONObject viewSearchResultProperty = new DfineryJSONObject();
        viewSearchResultProperty.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
        viewSearchResultProperty.put("custom_event_p_string", "푸드");
        viewSearchResultProperty.put("custom_event_p_long", 4);
        viewSearchResultProperty.put("custom_event_p_double", 5500.50);
        viewSearchResultProperty.put("custom_event_p_boolean", true);
        viewSearchResultProperty.put("custom_event_p_boolean2", JSONObject.NULL);
        viewSearchResultProperty.put("custom_event_p_dateTime", new Date());
        viewSearchResultProperty.put("custom_event_p_string_list", stringList);
        viewSearchResultProperty.put("custom_event_p_long_list", longList);
        viewSearchResultProperty.put("custom_event_p_double_list", doubleList);
        list.add(new EventInfo(DF.Event.VIEW_SEARCH_RESULT, viewSearchResultProperty));
        DfineryJSONObject custom1Property = new DfineryJSONObject();
        custom1Property.put("custom_event_p_string", "custom1");
        custom1Property.put("custom_event_p_long", 10);
        custom1Property.put("custom_event_p_boolean", true);
        custom1Property.put("custom_event_p_double", 10.11);
        custom1Property.put("custom_event_p_dateTime", new Date());
        JSONArray stringList2 = new JSONArray();
        stringList2.put("custom1_arr1");
        stringList2.put("custom1_arr2");
        stringList2.put("custom1_arr3");
        custom1Property.put("custom_event_p_string_list", stringList2);
        JSONArray longList2 = new JSONArray();
        longList2.put(10);
        longList2.put(10);
        longList2.put(11);
        longList2.put(12);
        longList2.put(13);
        longList2.put(14);
        custom1Property.put("custom_event_p_long_list", longList2);
        JSONArray doubleList2 = new JSONArray();
        doubleList2.put(10.1);
        doubleList2.put(10.11);
        doubleList2.put(11.1);
        doubleList2.put(11.12);
        custom1Property.put("custom_event_p_double_list", doubleList2);
        list.add(new EventInfo("custom_1", custom1Property));
        DfineryJSONObject custom2Property = new DfineryJSONObject();
        custom2Property.put("custom_event_p_string", "custom2");
        custom2Property.put("custom_event_p_long", 20);
        custom2Property.put("custom_event_p_boolean", false);
        custom2Property.put("custom_event_p_double", 20.22);
        custom2Property.put("custom_event_p_dateTime", new Date());
        JSONArray stringList3 = new JSONArray();
        stringList3.put("custom2_arr1");
        stringList3.put("custom2_arr2");
        stringList3.put("custom2_arr3");
        custom2Property.put("custom_event_p_string_list", stringList3);
        JSONArray longList3 = new JSONArray();
        longList3.put(20);
        longList3.put(21);
        longList3.put(22);
        longList3.put(23);
        longList3.put(24);
        custom2Property.put("custom_event_p_long_list", longList3);
        JSONArray doubleList3 = new JSONArray();
        doubleList3.put(20.1);
        doubleList3.put(20.11);
        doubleList3.put(21.1);
        doubleList3.put(21.12);
        custom2Property.put("custom_event_p_double_list", doubleList3);
        list.add(new EventInfo("custom_2", custom2Property));
        DfineryJSONObject custom3Property = new DfineryJSONObject();
        custom3Property.put("custom_event_p_string", "custom3");
        custom3Property.put("custom_event_p_long", 30);
        custom3Property.put("custom_event_p_boolean", false);
        custom3Property.put("custom_event_p_double", 30.33);
        custom3Property.put("custom_event_p_dateTime", new Date());
        JSONArray stringList4 = new JSONArray();
        stringList3.put("custom3_arr1");
        stringList3.put("custom3_arr2");
        stringList3.put("custom3_arr3");
        custom3Property.put("custom_event_p_string_list", stringList4);
        JSONArray longList4 = new JSONArray();
        longList3.put(33);
        longList3.put(44);
        longList3.put(55);
        longList3.put(66);
        longList3.put(77);
        custom3Property.put("custom_event_p_long_list", longList4);
        JSONArray doubleList4 = new JSONArray();
        doubleList3.put(33.3);
        doubleList3.put(33.33);
        doubleList3.put(44.4);
        doubleList3.put(44.12);
        custom3Property.put("custom_event_p_double_list", doubleList4);
        list.add(new EventInfo("custom_3", custom3Property));
        list.add(new EventInfo("custom_4"));
        String[] choiceItems = new String[list.size()];
        int i = 0;
        for(EventInfo index: list){
            choiceItems[i] = index.toString();
            i++;
        }
        builder.setSingleChoiceItems(choiceItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    showExampleInfo(list.get(which));
                } catch (JSONException e) {

                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void showExampleInfo(EventInfo eventInfo) throws JSONException {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(eventInfo.getEventName()+" 이벤트를 등록하시겠습니까?");
        builder.setMessage(eventInfo.getProperties().toString(1));
        builder.setPositiveButton("등록하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dfinery.getInstance().logEvent(eventInfo.getEventName(), eventInfo.getProperties());
                saveEvent(eventInfo.getEventName(), eventInfo.getProperties());
                Snackbar.make(binding.eventConstraintLayoutContainer, "이벤트가 등록되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void setClickListener(){
        binding.eventButtonLogin.setOnClickListener(this);
        binding.eventButtonLogout.setOnClickListener(this);
        binding.eventButtonCustomEvent.setOnClickListener(this);
        binding.eventButtonSignUp.setOnClickListener(this);
        binding.eventButtonPurchase.setOnClickListener(this);
        binding.eventButtonViewHome.setOnClickListener(this);
        binding.eventButtonProductView.setOnClickListener(this);
        binding.eventButtonAddToCart.setOnClickListener(this);
        binding.eventButtonAddToWishlist.setOnClickListener(this);
        binding.eventButtonRefund.setOnClickListener(this);
        binding.eventButtonSearch.setOnClickListener(this);
        binding.eventButtonListView.setOnClickListener(this);
        binding.eventButtonCartView.setOnClickListener(this);
        binding.eventButtonShare.setOnClickListener(this);
        binding.eventButtonPaymentInfoAdded.setOnClickListener(this);
        binding.eventButtonRemoveCart.setOnClickListener(this);
        binding.eventButtonExample.setOnClickListener(this);
    }
    private JSONArray getProducts(){
        JSONArray jsonArray = new JSONArray();
        for(int i=0; i<1; i++){
            JSONObject product = getProduct(i);
            jsonArray.put(product);
        }
        return jsonArray;
    }
    private JSONObject getParam(){
        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("weather", "sunny");
//            jsonObject.put("friends", 2);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return jsonObject;
    }
    private JSONObject getProduct(int productId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(DF.EventProperty.KEY_STRING_ITEM_ID, "product"+productId);
            jsonObject.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "사또밥");
            jsonObject.put(DF.EventProperty.KEY_DOUBLE_PRICE, 500.2);
            jsonObject.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
            jsonObject.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.1);
            jsonObject.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
            jsonObject.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
            jsonObject.put(DF.EventProperty.KEY_STRING_CATEGORY3, "행사품목");
            jsonObject.put(DF.EventProperty.KEY_STRING_CATEGORY4, "우선처리");
            jsonObject.put(DF.EventProperty.KEY_STRING_CATEGORY5, "묶음할인");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    private void startEventDetailActivity(String eventName){
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra(EventDetailsActivity.BUNDLE_KEY_EVENT_NAME, eventName);
        activityResultLauncher.launch(intent);
    }
    private void saveEvent(String eventName, JSONObject properties){
        PreferenceHelper preferenceHelper = new PreferenceHelper(getContext());
        String eventNames = preferenceHelper.getString(PreferenceKey.JSON_ARRAY_EVENT_NAMES.name(), null);
        if(TextUtils.isEmpty(eventNames)){
            JSONArray names = new JSONArray();
            names.put(eventName);
            preferenceHelper.putString(PreferenceKey.JSON_ARRAY_EVENT_NAMES.name(), names.toString());
        } else{
            try {
                JSONArray names = new JSONArray(eventNames);
                names.put(eventName);
                preferenceHelper.putString(PreferenceKey.JSON_ARRAY_EVENT_NAMES.name(), names.toString());
            } catch (JSONException e) {
                Log.d(TAG, e.toString());
            }
        }
        String eventProperties = preferenceHelper.getString(PreferenceKey.JSON_ARRAY_EVENT_PROPERTIES.name(), null);
        if(TextUtils.isEmpty(eventProperties)){
            JSONArray value = new JSONArray();
            value.put(properties);
            preferenceHelper.putString(PreferenceKey.JSON_ARRAY_EVENT_PROPERTIES.name(), value.toString());
        } else{
            try {
                JSONArray value = new JSONArray(eventProperties);
                value.put(properties);
                preferenceHelper.putString(PreferenceKey.JSON_ARRAY_EVENT_PROPERTIES.name(), value.toString());
            } catch (JSONException e) {
                Log.d(TAG, e.toString());
            }
        }
    }
}
