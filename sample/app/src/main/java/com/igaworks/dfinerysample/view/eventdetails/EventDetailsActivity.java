package com.igaworks.dfinerysample.view.eventdetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.constants.DF;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.EventdetailsActivityBinding;
import com.igaworks.dfinerysample.enums.PreferenceKey;
import com.igaworks.dfinerysample.helper.PreferenceHelper;
import com.igaworks.dfinerysample.view.BaseActionBarActivity;
import com.igaworks.dfinerysample.view.eventdetails.productdetails.ProductDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventDetailsActivity extends BaseActionBarActivity implements View.OnClickListener{
    public static final String TAG = Utils.getTagFromClass(EventDetailsActivity.class);
    public static final String BUNDLE_KEY_EVENT_NAME = "BUNDLE_KEY_EVENT_NAME";
    public static final String BUNDLE_KEY_EVENT_PROPERTIES = "BUNDLE_KEY_EVENT_PROPERTIES";
    private EventdetailsActivityBinding binding;
    private String eventName;
    private EventPropertiesAdapter eventPropertiesAdapter;
    private List<ItemProperties> eventProperties;
    private List<String> eventPropertiesSelectorItems;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EventdetailsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.eventName = getIntent().getStringExtra(BUNDLE_KEY_EVENT_NAME);
        super.setTitle(eventName);
        String eventProperties = getIntent().getStringExtra(BUNDLE_KEY_EVENT_PROPERTIES);
        initEventProperties(eventProperties);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_CANCELED){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "속성 값 설정을 취소했습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                } else if(result.getResultCode() != RESULT_OK){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "속성 값 설정에 실패했습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = result.getData();
                int position = intent.getIntExtra(ProductDetailsActivity.BUNDLE_KEY_POSITION, -1);
                if(position == -1){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "속성 값 Position 오류.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                String value = intent.getStringExtra(ProductDetailsActivity.BUNDLE_KEY_VALUE);
                if(TextUtils.isEmpty(value)){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "속성 값 Value 오류.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                ItemProperties itemProperties = new ItemProperties(PredefinedEventProperties.PRODUCT.getKey(), ValueType.Product);
                Log.d(TAG, "itemValue: "+value);
                itemProperties.setValue(value);
                eventPropertiesAdapter.updateItem(position, itemProperties);
                Snackbar.make(binding.eventDetailConstraintLayoutContainer, "상품 속성 값이 적용되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
        initPredefinedEventPropertiesSelectorItems();
        setClickListener();
        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                setResult(RESULT_CANCELED);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventPropertiesAdapter = new EventPropertiesAdapter(this, getSupportFragmentManager(), activityResultLauncher, eventProperties);
        setAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        activityResultLauncher = null;
        eventProperties.clear();
        eventProperties = null;
        eventPropertiesSelectorItems.clear();
        eventPropertiesSelectorItems = null;
        eventPropertiesAdapter = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent == null){
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_delete_all){
            showDeleteAllDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.eventDetail_button_request){
            request();
        } else if (id == R.id.eventDetail_imageView_addProperties){
            showPredefinedEventPropertiesSelector();
        }
    }

    private void showDeleteAllDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("속성을 모두 제거하시겠습니까?");
        builder.setPositiveButton("제거하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eventPropertiesAdapter.clear();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void setClickListener(){
        binding.eventDetailImageViewAddProperties.setOnClickListener(this);
        binding.eventDetailButtonRequest.setOnClickListener(this);
    }
    private void setAdapter(){
        binding.eventDetailRecyclerViewProperties.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.eventDetailRecyclerViewProperties.setAdapter(eventPropertiesAdapter);
        binding.eventDetailRecyclerViewProperties.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void showPredefinedEventPropertiesSelector(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추가할 속성 값을 선택하세요");
        builder.setSingleChoiceItems(eventPropertiesSelectorItems.toArray(new String[0]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String eventPropertiesSelectorItem = eventPropertiesSelectorItems.get(which);
                if(eventPropertiesSelectorItem.equals(PredefinedEventProperties.MANUAL.name())){
                    dialog.dismiss();
                    showInputFormForEventProperty();
                    return;
                }
                PredefinedEventProperties predefinedEventProperty = PredefinedEventProperties.get(eventPropertiesSelectorItem);
                ItemProperties itemProperties = new ItemProperties(predefinedEventProperty.getKey(), predefinedEventProperty.getValueType());
                if(eventPropertiesAdapter.isDuplicateItem(itemProperties.getKey())){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "이미 같은 속성이 추가되어 있습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else{
                    eventPropertiesAdapter.addItem(itemProperties);
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "속성이 추가되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void showInputFormForEventProperty(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추가할 속성 값의 이름을 입력하세요");
        // Set up the input
        final EditText editText = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);
        builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString().trim();
                if(TextUtils.isEmpty(input)){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "값이 입력되지 않았습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                ItemProperties itemProperties = new ItemProperties(input, null);
                if(eventPropertiesAdapter.isDuplicateItem(itemProperties.getKey())){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "이미 같은 속성이 추가되어 있습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else{
                    eventPropertiesAdapter.addItem(itemProperties);
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "속성이 추가되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
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
    private void request(){
        List<ItemProperties> selectedData = eventPropertiesAdapter.getDataSet();
        JSONObject root = new JSONObject();
        JSONArray items = new JSONArray();
        for(ItemProperties index: selectedData){
            String value = index.getValue();
            if(index.getKey().equals(PredefinedEventProperties.PRODUCT.getKey())){
                if(value == null || value.isEmpty()){
                    continue;
                }
                JSONObject item = null;
                try {
                    item = new JSONObject(value);
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                if(item != null){
                    items.put(item);
                }
            } else{
                try {
                    root.put(index.getKey(), index.getCastedValueByValueType());
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
        if(items.length() > 0){
            try {
                root.put(DF.EventProperty.KEY_ARRAY_ITEMS, items);
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }
        }
        Dfinery.getInstance().logEvent(eventName, root);
        saveEvent(eventName, root);
        setResult(RESULT_OK);
        finish();
    }
    private void initPredefinedEventPropertiesSelectorItems(){
        eventPropertiesSelectorItems = new ArrayList<>();
        for(PredefinedEventProperties item : PredefinedEventProperties.values()){
            if(item.getMatchedEventName() == null || eventName.equals(item.getMatchedEventName())){
                eventPropertiesSelectorItems.add(item.name());
            }
        }
    }
    private void initEventProperties(String properties){
        eventProperties = new ArrayList<>();
        if(TextUtils.isEmpty(properties)){
            for(PredefinedEventProperties item : PredefinedEventProperties.values()){
                if(item.getMatchedEventName() == null || eventName.equals(item.getMatchedEventName())){
                    if(item.isRequired()){
                        eventProperties.add(new ItemProperties(item.getKey(), item.getValueType()));
                    }
                }
            }
            return;
        }
        try {
            JSONObject root = new JSONObject(properties);
            JSONArray eventItems = root.optJSONArray(DF.EventProperty.KEY_ARRAY_ITEMS);
            if(eventItems != null){
                root.remove(DF.EventProperty.KEY_ARRAY_ITEMS);
                for(int i=0; i<eventItems.length(); i++){
                    ItemProperties item = new ItemProperties("{product}", ValueType.Product);
                    JSONObject jsonObject = eventItems.optJSONObject(i);
                    if(jsonObject!=null){
                        item.setValue(jsonObject.toString());
                        eventProperties.add(item);
                    }
                }
            }
            Iterator<String> iterator = root.keys();
            while(iterator.hasNext()){
                String key = iterator.next();
                String value = root.optString(key);
                ItemProperties itemProperties = new ItemProperties(key, ValueType.get(value, DF.EventProperty.FORMAT_DATETIME));
                itemProperties.setValue(value);
                eventProperties.add(itemProperties);
            }
        } catch (JSONException e) {
            return;
        }
    }
    private void saveEvent(String eventName, JSONObject properties){
        PreferenceHelper preferenceHelper = new PreferenceHelper(getApplicationContext());
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
