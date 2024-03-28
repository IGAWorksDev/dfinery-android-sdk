package com.igaworks.dfinerysample.view.eventdetails.productdetails;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.EventdetailsItemPropertiesBinding;
import com.igaworks.dfinerysample.view.eventdetails.ItemProperties;

import java.util.List;

public class ProductPropertiesAdapter extends RecyclerView.Adapter<ProductPropertiesAdapter.ViewHolder>{
    public static final String TAG = Utils.getTagFromClass(ProductPropertiesAdapter.class);
    private Context context;
    private List<ItemProperties> dataSet;

    public ProductPropertiesAdapter(Context context, List<ItemProperties> dataSet) {
        this.context = context;
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
        if(!TextUtils.isEmpty(itemProperties.getValue())){
            holder.binding.itemEditTextValue.setText(itemProperties.getValue());
        }
        if(itemProperties.getValueType() != null){
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
        } else{
            holder.binding.itemTextInputLayoutValue.setHint("");
            holder.binding.itemEditTextValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }
        holder.binding.itemImageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItemPosition = holder.getAdapterPosition();
                if(selectedItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                showRemoveAlertDialog(selectedItemPosition);
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
                dataSet.remove(position);
                notifyItemRemoved(position);
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
    public boolean validate(){
        int position = 0;
        for(ItemProperties index: dataSet){
            if(TextUtils.isEmpty(index.getValue())){
                return false;
            }
        }
        return true;
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
