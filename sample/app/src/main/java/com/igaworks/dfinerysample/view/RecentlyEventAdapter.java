package com.igaworks.dfinerysample.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.databinding.RecentlyEventItemBinding;
import com.igaworks.dfinerysample.view.eventdetails.EventDetailsActivity;

import org.json.JSONException;

import java.util.List;

public class RecentlyEventAdapter extends RecyclerView.Adapter<RecentlyEventAdapter.ViewHolder>{
    private static final String TAG = "RecentlyEventAdapter";
    private final Context context;
    private final List<RecentlyEvent> dataSet;
    private final OnItemClickListener itemClickListener;
    private int selectedPosition;
    enum ClickType{
        SELECT,
        DELETE
    }

    public RecentlyEventAdapter(Context context, List<RecentlyEvent> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
        this.itemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, ClickType clickType) {
                if(clickType.equals(ClickType.SELECT)){
                    selectedPosition = position;
                    notifyDataSetChanged();
                } else if(clickType.equals(ClickType.DELETE)){
                    showRemoveAlertDialog(position);
                }
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecentlyEventItemBinding binding = RecentlyEventItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecentlyEventAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RecentlyEvent item = dataSet.get(position);
        holder.binding.itemTextViewKey.setText(item.getName());
        holder.binding.itemEditTextValue.setText(item.getProperties().toString());
        holder.binding.itemImageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItemPosition = holder.getAdapterPosition();
                if(selectedItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                itemClickListener.onClick(selectedItemPosition, ClickType.DELETE);
            }
        });
        holder.binding.itemEditTextValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "itemTextInputLayoutValue is clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("상세보기");
                String message = "";
                try {
                    message = item.getProperties().toString(2);
                } catch (JSONException e) {
                }
                builder.setMessage(message);
                builder.setPositiveButton("복사하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, EventDetailsActivity.class);
                        intent.putExtra(EventDetailsActivity.BUNDLE_KEY_EVENT_NAME, item.getName());
                        intent.putExtra(EventDetailsActivity.BUNDLE_KEY_EVENT_PROPERTIES, item.getProperties().toString());
                        context.startActivity(intent);
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
        holder.binding.itemTextViewKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "itemTextViewKey is clicked");
                int selectedItemPosition = holder.getAdapterPosition();
                if(selectedItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                itemClickListener.onClick(selectedItemPosition, ClickType.SELECT);
            }
        });
        if(position == selectedPosition){
            holder.binding.itemImageViewRemove.setImageResource(R.drawable.baseline_check_box_24);
        } else{
            holder.binding.itemImageViewRemove.setImageResource(R.drawable.ic_baseline_indeterminate_check_box_24);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    private void showRemoveAlertDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("해당 기록을 제거하시겠습니까?");
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
    public RecentlyEvent getSelectedItem(){
        if(dataSet.isEmpty()){
            return null;
        }
        return dataSet.get(selectedPosition);
    }
    public void clearDataSet(){
        dataSet.clear();
    }

    public List<RecentlyEvent> getDataSet() {
        return dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecentlyEventItemBinding binding;

        public ViewHolder(@NonNull RecentlyEventItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface OnItemClickListener{
        void onClick(int position, ClickType clickType);
    }
}
