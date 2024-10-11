package com.igaworks.dfinerysample.view.eventdetails.productdetails;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.igaworks.dfinery.constants.DF;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.EventdetailsItemPropertiesBinding;
import com.igaworks.dfinerysample.view.eventdetails.ItemProperties;
import com.igaworks.dfinerysample.view.eventdetails.ValueType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProductPropertiesAdapter extends RecyclerView.Adapter<ProductPropertiesAdapter.ViewHolder>{
    public static final String TAG = Utils.getTagFromClass(ProductPropertiesAdapter.class);
    private Context context;
    private FragmentManager fragmentManager;
    private List<ItemProperties> dataSet;

    public ProductPropertiesAdapter(Context context, FragmentManager fragmentManager, List<ItemProperties> dataSet) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventdetailsItemPropertiesBinding binding = EventdetailsItemPropertiesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductPropertiesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final ItemProperties itemProperties = dataSet.get(position);
        holder.binding.itemTextViewKey.setText(itemProperties.getKey());
        holder.binding.itemTextViewKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItemPosition = holder.getAdapterPosition();
                if(selectedItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("상세보기");
                builder.setMessage(itemProperties.getKey());
                builder.setPositiveButton("타입 변경", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showChangeTypeDialog(itemProperties, selectedItemPosition);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });
        if(!TextUtils.isEmpty(itemProperties.getValue())){
            holder.binding.itemEditTextValue.setText(itemProperties.getValue());
        }
        if(itemProperties.getValueType()!= null){
            if(itemProperties.getValueType().equals(ValueType.Boolean) ||
                    itemProperties.getValueType().equals(ValueType.Datetime)
            ) {
                holder.binding.itemEditTextValue.setFocusable(false);
            } else{
                holder.binding.itemEditTextValue.setFocusable(true);
            }
        }
        if(itemProperties.getValueType() != null){
            holder.binding.itemTextInputLayoutValue.post(new Runnable() {
                @Override
                public void run() {
                    holder.binding.itemTextInputLayoutValue.setHint(itemProperties.getValueType().name());
                    switch (itemProperties.getValueType()){
                        case Long:{
                            holder.binding.itemEditTextValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                            break;
                        }
                        case Double:{
                            holder.binding.itemEditTextValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            break;
                        }
                        case Product:
                        case Boolean:
                        case Datetime:
                        case String:{
                            holder.binding.itemEditTextValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                            break;
                        }
                    }
                }
            });
        } else{
            holder.binding.itemTextInputLayoutValue.post(new Runnable() {
                @Override
                public void run() {
                    holder.binding.itemTextInputLayoutValue.setHint("");
                    holder.binding.itemEditTextValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
            });
        }
//        Log.d(TAG, "inputType: "+holder.binding.itemEditTextValue.getInputType());
        holder.binding.itemImageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItemPosition = holder.getAdapterPosition();
                if(selectedItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                Log.d(TAG, "selectedItemPosition: "+selectedItemPosition);
                showRemoveAlertDialog(selectedItemPosition);
            }
        });
        holder.binding.itemEditTextValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItemPosition = holder.getAdapterPosition();
                if(selectedItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                if(itemProperties.getValueType().equals(ValueType.Boolean)){
                    showBooleanValueSelectionDialog(holder.binding.itemEditTextValue, selectedItemPosition);
                } else if(itemProperties.getValueType().equals(ValueType.Datetime)){
                    MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText("날짜 설정하기")
                            .build();
                    datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                        @Override
                        public void onPositiveButtonClick(Long selection) {

                            Date date = new Date(selection);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                                    .setTitleText("시간 설정하기")
                                    .setTimeFormat(TimeFormat.CLOCK_12H)
                                    .build();
                            timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                                    calendar.set(Calendar.MINUTE, timePicker.getMinute());
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DF.EventProperty.FORMAT_DATETIME, Locale.getDefault());
                                    holder.binding.itemEditTextValue.setText(simpleDateFormat.format(calendar.getTime()));
                                }
                            });
                            timePicker.show(fragmentManager, "Time");
                        }
                    });
                    datePicker.show(fragmentManager, "Date");
                }
            }
        });
        holder.binding.itemEditTextValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int selectedItemPosition = holder.getAdapterPosition();
                if(selectedItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                ItemProperties watchedProperty = dataSet.get(selectedItemPosition);
                watchedProperty.setValue(editable.toString());
                dataSet.set(selectedItemPosition, watchedProperty);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public boolean isDuplicateItem(String key){
        if(TextUtils.isEmpty(key)){
            return false;
        }
        for(ItemProperties index: dataSet){
            if(key.equals(index.getKey())){
                return true;
            }
        }
        return false;
    }
    public void addItem(ItemProperties itemProperties){
        dataSet.add(itemProperties);
        notifyItemInserted(getItemCount());
    }
    public void updateItem(int position, ItemProperties itemProperties){
        dataSet.set(position, itemProperties);
        notifyItemChanged(position);
    }
    private void showRemoveAlertDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("속성을 제거하시겠습니까?");
        builder.setPositiveButton("제거", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dataSet.remove(position);
//                notifyItemRemoved(position);
                notifyDataSetChanged();
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

    private void showChangeTypeDialog(ItemProperties itemProperties, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("변경할 타입을 선택하세요.");
        ValueType[] valueTypes = ValueType.values();
        String[] valueTypeNames = new String[valueTypes.length-1];
        final Map<Integer, ValueType> map = new HashMap<>();
        int index = 0;
        for(ValueType valueType: valueTypes){
            if(valueType.equals(ValueType.Product)){
                continue;
            }
            map.put(index, valueType);
            valueTypeNames[index] = valueType.name();
            index++;
        }
        builder.setSingleChoiceItems(valueTypeNames, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemProperties.setValueType(map.get(which));
                dataSet.set(position, itemProperties);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void showBooleanValueSelectionDialog(EditText editText, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Boolean 값을 선택하세요");
        builder.setSingleChoiceItems(new String[]{"true","false"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0: editText.setText("true"); break;
                    case 1: editText.setText("false"); break;
                    default: break;
                }
//                notifyItemChanged(position);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.show();
    }
    public void clear(){
        dataSet.clear();
        notifyDataSetChanged();
    }

    public List<ItemProperties> getDataSet() {
        return dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        EventdetailsItemPropertiesBinding binding;

        public ViewHolder(@NonNull EventdetailsItemPropertiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
