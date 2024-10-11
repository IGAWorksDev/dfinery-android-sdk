package com.igaworks.dfinerysample.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.InappmessageFragmentBinding;
import com.igaworks.dfinerysample.enums.PreferenceKey;
import com.igaworks.dfinerysample.helper.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InAppMessageFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = Utils.getTagFromClass(InAppMessageFragment.class);
    private InappmessageFragmentBinding binding;
    private RecentlyEventAdapter recentlyEventAdapter;
    private List<RecentlyEvent> recentlyEvents;
    private PreferenceHelper preferenceHelper;

    public InAppMessageFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = InappmessageFragmentBinding.inflate(inflater, container, false);
        setClickListener();
        preferenceHelper = new PreferenceHelper(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onStop() {
        super.onStop();
        updateRecentlyEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
        recentlyEventAdapter.clearDataSet();
        recentlyEventAdapter = null;
        recentlyEvents = null;
        preferenceHelper = null;
    }
    @Override
    public void onClick(View view) {
        final int viewId = view.getId();
        if(viewId == R.id.inAppMessage_button_notify){
            RecentlyEvent selectedItem = recentlyEventAdapter.getSelectedItem();
            if(selectedItem == null){
                return;
            }
            if(binding.inAppMessageCheckBoxMoveActivity.isChecked()){
                Intent intent = new Intent(getActivity(), InAppMessageActivity.class);
                intent.putExtra(InAppMessageActivity.BUNDLE_EVENT_NAME, selectedItem.getName());
                intent.putExtra(InAppMessageActivity.BUNDLE_JSON_OBJECT_EVENT_PROPERTIES, selectedItem.getProperties().toString());
                startActivity(intent);
            } else{
                Dfinery.getInstance().logEvent(selectedItem.getName(), selectedItem.getProperties());
                Snackbar.make(binding.inAppMessageConstraintLayoutContainer, "이벤트를 등록하였습니다", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
            ((BaseActivity)getActivity()).hideKeypad();
        } else if(viewId == R.id.inAppMessage_imageView_deleteRecentlyEvents){
            showRemoveAlertDialog();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    private void setClickListener(){
        binding.inAppMessageButtonNotify.setOnClickListener(this);
        binding.inAppMessageImageViewDeleteRecentlyEvents.setOnClickListener(this);
    }
    private void setAdapter(){
        binding.inAppMessageRecyclerViewRecentlyCalledEvent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.inAppMessageRecyclerViewRecentlyCalledEvent.setAdapter(recentlyEventAdapter);
        binding.inAppMessageRecyclerViewRecentlyCalledEvent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
    private void initDataSet(){
        List<RecentlyEvent> events = new ArrayList<>();
        recentlyEvents = new ArrayList<>();
        String eventNames = preferenceHelper.getString(PreferenceKey.JSON_ARRAY_EVENT_NAMES.name(), null);
        if(TextUtils.isEmpty(eventNames)){
            return;
        }
        try {
            JSONArray eventNamesArray = new JSONArray(eventNames);
            String eventProperties = preferenceHelper.getString(PreferenceKey.JSON_ARRAY_EVENT_PROPERTIES.name(), null);
            JSONArray properties = new JSONArray(eventProperties);
            for(int i=0; i<eventNamesArray.length(); i++){
                events.add(new RecentlyEvent(eventNamesArray.getString(i), properties.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        HashSet<Integer> hashSet = new HashSet<>();
        for(RecentlyEvent event: events){
            if(!hashSet.contains(event.getHashCode())){
                recentlyEvents.add(event);
            }
            hashSet.add(event.getHashCode());
        }
    }
    private void updateRecentlyEvents(){
        List<RecentlyEvent> dataSet = recentlyEventAdapter.getDataSet();
        preferenceHelper.remove(PreferenceKey.JSON_ARRAY_EVENT_NAMES.name());
        preferenceHelper.remove(PreferenceKey.JSON_ARRAY_EVENT_PROPERTIES.name());
        JSONArray names = new JSONArray();
        JSONArray properties = new JSONArray();
        for(RecentlyEvent event: dataSet){
            names.put(event.getName());
            properties.put(event.getProperties());
        }
        preferenceHelper.putString(PreferenceKey.JSON_ARRAY_EVENT_NAMES.name(), names.toString());
        preferenceHelper.putString(PreferenceKey.JSON_ARRAY_EVENT_PROPERTIES.name(), properties.toString());
    }

    private void showRemoveAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("이벤트 기록을 모두 삭제 하시겠습니까?");
        builder.setPositiveButton("제거", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recentlyEventAdapter.clearDataSet();
                recentlyEventAdapter.notifyDataSetChanged();
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

    private void updateList(){
        initDataSet();
        recentlyEventAdapter = new RecentlyEventAdapter(getActivity(), recentlyEvents);
        setAdapter();
        if(recentlyEvents.isEmpty()){
            binding.inAppMessageTextViewNoData.setVisibility(View.VISIBLE);
        } else{
            binding.inAppMessageTextViewNoData.setVisibility(View.GONE);
        }
    }
}
