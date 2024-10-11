package com.igaworks.dfinerysample.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.SettingFragmentBinding;
import com.igaworks.dfinerysample.enums.PreferenceKey;
import com.igaworks.dfinerysample.helper.PreferenceHelper;

public class SettingFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = Utils.getTagFromClass(SettingFragment.class);
    private SettingFragmentBinding binding;
    private PreferenceHelper preferenceHelper;

    public SettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SettingFragmentBinding.inflate(inflater, container, false);
        preferenceHelper = new PreferenceHelper(getContext());
        setClickListener();
        refreshData();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        this.preferenceHelper = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.setting_button_resetUser){
            DfineryProperties.resetIdentity();
            Snackbar.make(binding.settingConstraintLayoutContainer, "Identity is reset", BaseTransientBottomBar.LENGTH_SHORT).show();
        } else if (id == R.id.setting_button_disconnectDevice){
            Dfinery.getInstance().suspendUserTargeting();
            Snackbar.make(binding.settingConstraintLayoutContainer, "device is disconnected", BaseTransientBottomBar.LENGTH_SHORT).show();
        }  else if (id == R.id.setting_button_changeServiceId) {
            String input = binding.settingEditTextServiceId.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                Snackbar.make(binding.settingConstraintLayoutContainer, "Service ID에 값을 넣어주세요", BaseTransientBottomBar.LENGTH_SHORT).show();
                return;
            }
            if (DfineryProperties.getServiceId().equals(input)) {
                Snackbar.make(binding.settingConstraintLayoutContainer, "기존 Service ID와 동일합니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                return;
            }
            preferenceHelper.putString(PreferenceKey.STRING_SDK_SERVICE_ID.name(), binding.settingEditTextServiceId.getText().toString(), true);
            showExitApplicationAlert("서비스 ID를 \"" + input + "\"로 변경합니다.", "서비스 ID 변경의 적용은 앱을 재시작 해야 적용 됩니다.\n앱을 종료 하시겠습니까?");
        } else if (id == R.id.setting_button_startWebActivity){
            Intent intent = new Intent(getActivity(), WebActivity.class);
            startActivity(intent);
        }
    }
    private void showExitApplicationAlert(String title, String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setPositiveButton("앱 종료하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                exit();
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
        binding.settingButtonDisconnectDevice.setOnClickListener(this);
        binding.settingButtonResetUser.setOnClickListener(this);
        binding.settingButtonChangeServiceId.setOnClickListener(this);
        binding.settingButtonStartWebActivity.setOnClickListener(this);
    }
    private void refreshData(){
        binding.settingEditTextServiceId.setText(DfineryProperties.getServiceId());
        String pushToken = preferenceHelper.getString(PreferenceKey.STRING_FIREBASE_REGISTRATION_ID.name(), null);
        binding.settingEditTextRegistrationId.setText(pushToken);
    }
    private void exit() {
        getActivity().moveTaskToBack(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().finishAndRemoveTask();
        } else {
            getActivity().finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }
}
