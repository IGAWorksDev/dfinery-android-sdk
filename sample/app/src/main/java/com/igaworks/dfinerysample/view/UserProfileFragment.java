package com.igaworks.dfinerysample.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinery.constants.DF;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.UserprofileFragmentBinding;
import com.igaworks.dfinerysample.model.UserProfileInfo;
import com.igaworks.dfinerysample.view.eventdetails.ValueType;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserProfileFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = Utils.getTagFromClass(UserProfileFragment.class);
    private UserprofileFragmentBinding binding;
    private enum KeyType{
        USER_PROFILE, IDENTITY
    }
    private static String BIRTH_DATE_FORMAT = "yyyy-MM-dd";
    private Map<Integer, String> userProfileSet;
    private Map<Integer, String> identityKeySet;
    private int selectedUserProfileKeyPosition;
    private int selectedIdentityKeyPosition;
    public UserProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIdentityKeySet();
        setUserProfileKeySet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        binding = UserprofileFragmentBinding.inflate(inflater, container, false);
        setClickListener();
        setNotificationTypeSpinner();
        setNotificationChannelSpinner(true);
        binding.userProfileEditTextIdentityKey.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showPredefinedIdentityKey();
                }
            }
        });
        binding.userProfileEditTextUserProfileValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                final String input = s.toString();
                if(TextUtils.isEmpty(input)){
                    binding.userProfileTextInputLayoutUserProfileValue.setHint("Value");
                    return;
                }
                binding.userProfileTextInputLayoutUserProfileValue.setHint(ValueType.get(input,"yyyy-MM-dd").name());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userProfileSet.clear();
        userProfileSet = null;
        identityKeySet.clear();
        identityKeySet = null;
    }

    private void setUserProfileKeySet(){
        userProfileSet = new HashMap<>();
        userProfileSet.put(0, DF.UserProfile.NAME);
        userProfileSet.put(1, DF.UserProfile.MEMBERSHIP);
        userProfileSet.put(2, DF.UserProfile.GENDER);
        userProfileSet.put(3, DF.UserProfile.BIRTH);
//        userProfileSet.put(4, "custom_user_string");
//        userProfileSet.put(5, "custom_user_long");
//        userProfileSet.put(6, "custom_user_boolean");
//        userProfileSet.put(7, "custom_user_double");
//        userProfileSet.put(8, "custom_user_dateTime");
//        userProfileSet.put(9, "custom_user_string_list");
//        userProfileSet.put(10, "custom_user_long_list");
//        userProfileSet.put(11, "custom_user_double_list");
    }
    private void setIdentityKeySet(){
        identityKeySet = new HashMap<>();
        identityKeySet.put(0, "external_id");
        identityKeySet.put(1, "email");
        identityKeySet.put(2, "phone_no");
        identityKeySet.put(3, "kakao_user_id");
        identityKeySet.put(4, "line_user_id");
//        identityKeySet.put(5, "zalo_user_id");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.userProfile_button_selectPredefinedUserProfileKey) {
            showPredefinedUserProfileKey();
        } else if (id == R.id.userProfile_button_userProfileSet) {
            String key = binding.userProfileEditTextUserProfileKey.getText().toString();
            String value = binding.userProfileEditTextUserProfileValue.getText().toString();
            if (!validate(key, value, KeyType.USER_PROFILE)) {
                return;
            }
            setUserProfile(key, value);
            ((BaseActivity)getActivity()).hideKeypad();
        } else if (id == R.id.userProfile_button_selectPredefinedIdentityKey) {
            showPredefinedIdentityKey();
        } else if (id == R.id.userProfile_button_identitySet) {
            String key = binding.userProfileEditTextIdentityKey.getText().toString();
            String value = binding.userProfileEditTextIdentityValue.getText().toString();
            if (!validate(key, value, KeyType.IDENTITY)) {
                return;
            }
            setIdentity(key, value);
            ((BaseActivity)getActivity()).hideKeypad();
        } else if (id == R.id.userProfile_button_notificationSet) {
            int notificationTypeSelected = binding.userProfileSpinnerNotificationType.getSelectedItemPosition();
            int notificationChannelSelected = binding.userProfileSpinnerNotificationChannel.getSelectedItemPosition();
            boolean subscriptionEnabled = binding.userProfileCheckBoxSubscriptionStatus.isChecked();
            Log.d(TAG, notificationTypeSelected + "/" + notificationChannelSelected + "/" + subscriptionEnabled);
            setNotificationSubscriptionStatus(notificationTypeSelected, notificationChannelSelected, subscriptionEnabled);
        } else if (id == R.id.userProfile_button_example){
            try {
                showExampleSelector();
            } catch (JSONException e) {

            }
        }
    }
    private void showExampleSelector() throws JSONException {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("유저 프로필을 선택해주세요");
        List<UserProfileInfo> list = new ArrayList<>();
        list.add(new UserProfileInfo("custom_user_string", "test"));
        list.add(new UserProfileInfo("custom_user_long", 12345));
        list.add(new UserProfileInfo("custom_user_boolean", false));
        list.add(new UserProfileInfo("custom_user_double", 123.45));
        list.add(new UserProfileInfo("custom_user_dateTime", new Date()));
        String[] stringList = new String[]{"Hello", "World","!!!"};
        list.add(new UserProfileInfo("custom_user_string_list", stringList));
        Long[] longList = new Long[]{10L,20L,30L,40L};
        list.add(new UserProfileInfo("custom_user_long_list", longList));
        Double[] doubleList = new Double[]{10.1,10.2,10.3,11.4};
        list.add(new UserProfileInfo("custom_user_double_list", doubleList));
        String[] items = new String[list.size()];
        int i = 0;
        for(UserProfileInfo index: list){
            items[i] = index.toString();
            i++;
        }
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserProfileInfo userProfileInfo = list.get(which);
                showExampleInfo(userProfileInfo);
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
    private void showExampleInfo(UserProfileInfo userProfileInfo){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(userProfileInfo.getKey()+"를 등록하시겠습니까?");
        builder.setMessage(userProfileInfo.getValueString());
        builder.setPositiveButton("등록하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> value = new HashMap<>();
                value.put(userProfileInfo.getKey(), userProfileInfo.getValue());
                DfineryProperties.setUserProfiles(value);
                Snackbar.make(binding.userProfileConstraintLayoutContainer, "등록되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
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
    private void setClickListener(){
        binding.userProfileButtonSelectPredefinedUserProfileKey.setOnClickListener(this);
        binding.userProfileButtonUserProfileSet.setOnClickListener(this);
        binding.userProfileButtonSelectPredefinedIdentityKey.setOnClickListener(this);
        binding.userProfileButtonIdentitySet.setOnClickListener(this);
        binding.userProfileButtonNotificationSet.setOnClickListener(this);
        binding.userProfileButtonExample.setOnClickListener(this);
    }
    private void setNotificationTypeSpinner(){
        binding.userProfileSpinnerNotificationType.setAdapter(getNotificationTypeAdapter());
        binding.userProfileSpinnerNotificationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0 || position == 2){
                    setNotificationChannelSpinner(true);
                } else{
                    setNotificationChannelSpinner(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setNotificationChannelSpinner(boolean showOnlyPushChannel){
        binding.userProfileSpinnerNotificationChannel.setAdapter(getNotificationChannelAdapter(showOnlyPushChannel));
    }
    private ArrayAdapter<CharSequence> getNotificationTypeAdapter(){
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.notification_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return spinnerAdapter;
    }
    private ArrayAdapter<CharSequence> getNotificationChannelAdapter(boolean showOnlyPushChannel){
        ArrayAdapter<CharSequence> spinnerAdapter = null;
        if(showOnlyPushChannel){
            spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.notification_channel_at_night, android.R.layout.simple_spinner_item);
        } else{
            spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.notification_channel, android.R.layout.simple_spinner_item);
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return spinnerAdapter;
    }
    private void showPredefinedUserProfileKey(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose predefined user profile key");
        final String[] items = userProfileSet.values().toArray(new String[0]);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedUserProfileKeyPosition = which;
                dialog.dismiss();
                binding.userProfileEditTextUserProfileKey.setText(userProfileSet.get(which));
                if(which == 2) {
                    //GENDER
                    showGenderSelector();
                }
                if(which == 3){
                    //BIRTH
                    showBirthPicker();
                }
            }
        });
        builder.show();
    }
    private void showGenderSelector(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select your gender");
        final String[] items = new String[]{"Male", "Female","NonBinary", "Other"};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0: binding.userProfileEditTextUserProfileValue.setText("Male"); break;
                    case 1: binding.userProfileEditTextUserProfileValue.setText("Female"); break;
                    case 2: binding.userProfileEditTextUserProfileValue.setText("NonBinary"); break;
                    case 3: binding.userProfileEditTextUserProfileValue.setText("Other"); break;
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void showBirthPicker(){
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BIRTH_DATE_FORMAT, Locale.getDefault());
                Date date = new Date(selection);
                binding.userProfileEditTextUserProfileValue.setText(simpleDateFormat.format(date));
            }
        });
        datePicker.show(getChildFragmentManager(), "Date");
    }
    private void showPredefinedIdentityKey(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose predefined identity key");
        final String[] items = identityKeySet.values().toArray(new String[0]);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedIdentityKeyPosition = which;
                dialog.dismiss();
                final String value = identityKeySet.get(which);
                binding.userProfileEditTextIdentityKey.setText(value);
                switch (value){
                    case "email":{
                        binding.userProfileEditTextIdentityValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                    }
                    case "phone_no":{
                        binding.userProfileEditTextIdentityValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        break;
                    }
                    default:{
                        binding.userProfileEditTextIdentityValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    }
                }
            }
        });
        builder.show();
    }
    private void setNotificationSubscriptionStatus(int type, int channel, boolean isEnabled){
        switch (type){
            case 0:{//informative
                switch (channel){
                    case 0:{//PUSH
                        DfineryProperties.setUserProfile(DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL, isEnabled);
                        break;
                    }
                }
                break;
            }
            case 1:{//marketing
                switch (channel){
                    case 0:{//PUSH
                        DfineryProperties.setUserProfile(DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL, isEnabled);
                        break;
                    }
                    case 1:{//SMS
                        DfineryProperties.setUserProfile(DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_SMS_CHANNEL, isEnabled);
                        break;
                    }
                    case 2:{//KAKAO
                        DfineryProperties.setUserProfile(DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_KAKAO_CHANNEL, isEnabled);
                        break;
                    }
                }
                break;
            }
            case 2:{//marketingAtNight
                DfineryProperties.setUserProfile(DF.UserProfile.ADVERTISING_NOTIFICATION_AT_NIGHT_FOR_PUSH_CHANNEL, isEnabled);
                break;
            }
        }
    }
    private boolean validate(String key, String value, KeyType keyType){
        if(TextUtils.isEmpty(key)){
            if(keyType.equals(KeyType.USER_PROFILE)){
                Snackbar.make(binding.userProfileConstraintLayoutContainer, "User Profile Key is empty", BaseTransientBottomBar.LENGTH_SHORT).show();
            } else{
                Snackbar.make(binding.userProfileConstraintLayoutContainer, "Identity Key is empty", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }
    private void setIdentity(String key, String value){
//        identityKeySet.put(0, "user_id");
//        identityKeySet.put(1, "customer_no");
//        identityKeySet.put(2, "email");
//        identityKeySet.put(3, "phone_no");
//        identityKeySet.put(4, "kakao_user_id");
//        identityKeySet.put(5, "line_user_id");
//        identityKeySet.put(6, "zalo_user_id");
        if(TextUtils.isEmpty(value)){
            value = null;
        }
        switch (selectedIdentityKeyPosition){
            case 0: DfineryProperties.setIdentity(DF.Identity.EXTERNAL_ID, value); break;
            case 1: DfineryProperties.setIdentity(DF.Identity.EMAIL, value); break;
            case 2: DfineryProperties.setIdentity(DF.Identity.PHONE_NO, value); break;
            case 3: DfineryProperties.setIdentity(DF.Identity.KAKAO_USER_ID, value); break;
            case 4: DfineryProperties.setIdentity(DF.Identity.LINE_USER_ID, value); break;
//            case 5: DfineryProperties.setIdentity(DF.Identity.ZALO_USER_ID, value); break;
        }
    }
    private void setUserProfile(String key, String value){
//        userProfileSet.put(0, UserProfile.NAME.getValue());
//        userProfileSet.put(1, UserProfile.MEMBERSHIP.getValue());
//        userProfileSet.put(2, UserProfile.GENDER.getValue());
//        userProfileSet.put(3, UserProfile.BIRTH.getValue());
        if(selectedUserProfileKeyPosition == 3){
            Date birthday = getBirthdayDate(value);
            if(birthday != null){
                DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday);
            } else{
                Snackbar.make(binding.userProfileConstraintLayoutContainer, "date format error.", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        } else{
            String valueType = binding.userProfileTextInputLayoutUserProfileValue.getHint().toString();
            switch (valueType){
                case "Long": DfineryProperties.setUserProfile(key, Long.valueOf(value)); break;
                case "Double": DfineryProperties.setUserProfile(key, Double.valueOf(value)); break;
                case "Boolean": DfineryProperties.setUserProfile(key, Boolean.valueOf(value)); break;
                case "Datetime": {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(value);
                    } catch (ParseException e) {}
                    DfineryProperties.setUserProfile(key, date);
                    break;
                }
                case "ArrayOfString": {
                    String[] split = value.split(",");
                    DfineryProperties.setUserProfile(key, split);
                    break;
                }
                case "ArrayOfLong": {
                    String[] split = value.split(",");
                    Long[] arrays = new Long[split.length];
                    for(int i=0; i<split.length; i++){
                       arrays[i] = Long.valueOf(split[i]);
                    }
                    DfineryProperties.setUserProfile(key, arrays);
                    break;
                }
                case "ArrayOfDouble": {
                    String[] split = value.split(",");
                    Double[] arrays = new Double[split.length];
                    for(int i=0; i<split.length; i++){
                        arrays[i] = Double.valueOf(split[i]);
                    }
                    DfineryProperties.setUserProfile(key, arrays);
                    break;
                }
                default: {
                    if(TextUtils.isEmpty(value)){
                        DfineryProperties.setUserProfile(key, (String)null);
                    } else{
                        DfineryProperties.setUserProfile(key, value);
                    }
                }
            }
        }
    }
    private Date getBirthdayDate(String dateFormattedString){
        if(TextUtils.isEmpty(dateFormattedString)){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date validateDate;
        try {
            validateDate = simpleDateFormat.parse(dateFormattedString);
        } catch (ParseException e) {
            return null;
        }
        return validateDate;
    }
}
