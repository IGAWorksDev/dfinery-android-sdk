package com.igaworks.dfinerysample.view;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.models.DfineryPushPayload;
import com.igaworks.dfinerysample.databinding.PushlistActivityBinding;

public class PushListActivity extends BaseActivity {
    private static final String TAG = "PushListActivity";
    private PushlistActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PushlistActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StringBuilder stringBuilder = new StringBuilder();
        DfineryPushPayload dfineryPushPayload = Dfinery.getInstance().getDfineryPushPayload(getIntent());
        if(dfineryPushPayload != null && dfineryPushPayload.isAvailable()){
            Log.d(TAG, "pushData: "+ dfineryPushPayload);
            stringBuilder.append(dfineryPushPayload.toString());
            stringBuilder.append("\n");
        }
        if(getIntent() != null && getIntent().getData()!=null){
            stringBuilder.append(getIntent().getData().toString());
        }
        binding.pushListTextView.setText(stringBuilder.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
