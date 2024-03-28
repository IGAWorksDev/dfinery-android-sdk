package com.igaworks.dfinerysample.view.eventdetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.igaworks.dfinerysample.view.BaseActionBarActivity;
import com.igaworks.dfinerysample.view.eventdetails.productdetails.ProductDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsActivity extends BaseActionBarActivity implements View.OnClickListener{
    public static final String TAG = Utils.getTagFromClass(EventDetailsActivity.class);
    public static final String BUNDLE_KEY_EVENT_NAME = "BUNDLE_KEY_EVENT_NAME";
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
        eventProperties = new ArrayList<>();
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
        eventPropertiesAdapter = new EventPropertiesAdapter(this, activityResultLauncher, eventProperties);
        setAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        activityResultLauncher = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent == null){
            return;
        }
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
                String input = editText.getText().toString();
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
        if(!eventPropertiesAdapter.validate()){
            Snackbar.make(binding.eventDetailConstraintLayoutContainer, "미작성된 속성이 있습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
            return;
        }
        List<ItemProperties> selectedData = eventPropertiesAdapter.getDataSet();
        JSONObject root = new JSONObject();
        JSONArray items = new JSONArray();
        for(ItemProperties index: selectedData){
            if(index.getKey().equals(PredefinedEventProperties.PRODUCT.getKey())){
                JSONObject item = null;
                try {
                    item = new JSONObject(index.getValue());
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                if(item != null){
                    items.put(item);
                }
            } else{
                try {
                    root.put(index.getKey(), ValueType.getCastedValue(index.getValue()));
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
}
