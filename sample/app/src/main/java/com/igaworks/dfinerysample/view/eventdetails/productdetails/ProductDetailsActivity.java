package com.igaworks.dfinerysample.view.eventdetails.productdetails;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.constants.DF;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.ProductdetailsActivityBinding;
import com.igaworks.dfinerysample.view.BaseActionBarActivity;
import com.igaworks.dfinerysample.view.eventdetails.ItemProperties;
import com.igaworks.dfinerysample.view.eventdetails.ValueType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductDetailsActivity extends BaseActionBarActivity implements View.OnClickListener {
    public static final String TAG = Utils.getTagFromClass(ProductDetailsActivity.class);
    public static final String INTENT_ACTION_CONFIRM = "INTENT_ACTION_CONFIRM";
    public static final String BUNDLE_KEY_POSITION = "BUNDLE_KEY_POSITION";
    public static final String BUNDLE_KEY_VALUE = "BUNDLE_KEY_VALUE";
    private ProductdetailsActivityBinding binding;
    private ProductPropertiesAdapter productPropertiesAdapter;
    private List<ItemProperties> productProperties;
    private List<String> productPropertiesSelectorItems;
    private int position;
    private String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProductdetailsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Product");
        initPredefinedProductPropertiesSelectorItems();
        value = getIntent().getStringExtra(BUNDLE_KEY_VALUE);
        productProperties = new ArrayList<>();
        JSONObject previousValue = new JSONObject();
        try {
            previousValue = new JSONObject(value);
        } catch (JSONException e) {}
        if(previousValue.length() == 0){
            initProductProperties();
        } else{
            putPreviouslyEnteredItem(value);
        }
        position = getIntent().getIntExtra(BUNDLE_KEY_POSITION, -1);
        setClickListener();
        setAdapter();
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                setResult(RESULT_CANCELED);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        productProperties.clear();
        productProperties = null;
        productPropertiesAdapter = null;
        productPropertiesSelectorItems.clear();
        productPropertiesSelectorItems = null;
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
        if(id == R.id.productDetail_button_confirm){
            confirm();
            hideKeypad();
        } else if (id == R.id.productDetail_imageView_addProductProperties){
            showPredefinedProductPropertiesSelector();
        }
    }
    private void showDeleteAllDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("속성을 모두 제거하시겠습니까?");
        builder.setPositiveButton("제거하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                productPropertiesAdapter.clear();
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

    private void putPreviouslyEnteredItem(String value){
        if(TextUtils.isEmpty(value)){
            return;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(value);
        } catch (JSONException e) {}
        if(jsonObject == null){
            return;
        }
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next();
            String itemValue = jsonObject.optString(key);
            ItemProperties itemProperties = new ItemProperties(key, ValueType.get(itemValue, DF.EventProperty.FORMAT_DATETIME));
            itemProperties.setValue(itemValue);
            productProperties.add(itemProperties);
        }
    }
    private void setAdapter(){
        productPropertiesAdapter = new ProductPropertiesAdapter(this, getSupportFragmentManager(), productProperties);
        binding.productDetailRecyclerViewProductProperties.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productDetailRecyclerViewProductProperties.setAdapter(productPropertiesAdapter);
        binding.productDetailRecyclerViewProductProperties.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    private void initPredefinedProductPropertiesSelectorItems(){
        productPropertiesSelectorItems = new ArrayList<>();
        for(PredefinedProductProperties item : PredefinedProductProperties.values()){
            productPropertiesSelectorItems.add(item.name());
        }
    }
    private void setClickListener(){
        binding.productDetailButtonConfirm.setOnClickListener(this);
        binding.productDetailImageViewAddProductProperties.setOnClickListener(this);
    }
    private void showPredefinedProductPropertiesSelector(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추가할 속성 값을 선택하세요");
        builder.setSingleChoiceItems(productPropertiesSelectorItems.toArray(new String[0]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectorItem = productPropertiesSelectorItems.get(which);
                if(selectorItem.equals(PredefinedProductProperties.MANUAL.name())){
                    dialog.dismiss();
                    showInputFormForProductProperty();
                    return;
                }
                PredefinedProductProperties predefinedProductProperties = PredefinedProductProperties.get(selectorItem);
                ItemProperties itemProperties = new ItemProperties(predefinedProductProperties.getKey(), predefinedProductProperties.getValueType());
                if(productPropertiesAdapter.isDuplicateItem(itemProperties.getKey())){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "이미 같은 속성이 추가되어 있습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else{
                    productPropertiesAdapter.addItem(itemProperties);
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "속성이 추가되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void showInputFormForProductProperty(){
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
                if(productPropertiesAdapter.isDuplicateItem(itemProperties.getKey())){
                    Snackbar.make(binding.eventDetailConstraintLayoutContainer, "이미 같은 속성이 추가되어 있습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else{
                    productPropertiesAdapter.addItem(itemProperties);
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
    private void confirm(){
        Intent intent = new Intent();
        intent.setAction(INTENT_ACTION_CONFIRM);
        intent.putExtra(BUNDLE_KEY_POSITION, position);
        List<ItemProperties> selectedData = productPropertiesAdapter.getDataSet();
        JSONObject jsonObject = new JSONObject();
        for(ItemProperties index: selectedData){
            try {
                jsonObject.put(index.getKey(), index.getCastedValueByValueType());
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }
        }
        intent.putExtra(BUNDLE_KEY_VALUE, jsonObject.toString());
        setResult(RESULT_OK, intent);
        finish();
    }
    private void initProductProperties(){
        for(PredefinedProductProperties item : PredefinedProductProperties.values()){
            if(item.isRequired()){
                productProperties.add(new ItemProperties(item.getKey(), item.getValueType()));
            }
        }
    }
}
