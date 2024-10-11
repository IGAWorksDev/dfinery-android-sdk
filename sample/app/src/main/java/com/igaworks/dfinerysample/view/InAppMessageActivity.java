package com.igaworks.dfinerysample.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinerysample.databinding.InappmessageActivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class InAppMessageActivity extends BaseActionBarActivity {
    private static final String TAG = "InAppMessageActivity";
    public static final String BUNDLE_EVENT_NAME = "BUNDLE_EVENT_NAME";
    public static final String BUNDLE_JSON_OBJECT_EVENT_PROPERTIES = "BUNDLE_JSON_OBJECT_EVENT_PROPERTIES";
    private InappmessageActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InappmessageActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String eventName = getIntent().getStringExtra(BUNDLE_EVENT_NAME);
        String eventProperties = getIntent().getStringExtra(BUNDLE_JSON_OBJECT_EVENT_PROPERTIES);
        binding.inAppMessageTextViewEventName.setText(eventName);
        binding.inAppMessageTextViewEventProperties.setText(eventProperties);
        try {
            JSONObject properties = new JSONObject(eventProperties);
            Dfinery.getInstance().logEvent(eventName, properties);
            Snackbar.make(binding.inAppMessageConstraintLayoutContainer, "이벤트를 등록하였습니다", BaseTransientBottomBar.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }

        binding.inAppMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
