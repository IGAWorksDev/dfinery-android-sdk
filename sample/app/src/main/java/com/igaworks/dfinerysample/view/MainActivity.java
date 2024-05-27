package com.igaworks.dfinerysample.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinery.constants.DF;
import com.igaworks.dfinery.models.PushNotification;
import com.igaworks.dfinerysample.Preference;
import com.igaworks.dfinerysample.PreferenceHelper;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.MainActivityBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    public static final String TAG = Utils.getTagFromClass(MainActivity.class);
    private MainActivityBinding binding;
    private Bundle savedInstanceState;
    private String selectedFragmentTag;
    public static final String FRAGMENT_TAG = "MainFragmentTag";
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mainBottomNavigationView.setOnItemSelectedListener(this);
        binding.mainTextViewSdkVersion.setText(DfineryProperties.getSdkVersion());
        preferenceHelper = new PreferenceHelper(this);
        Log.i(TAG, "onCreate");
        this.savedInstanceState = savedInstanceState;
        if(Utils.isNull(savedInstanceState)){
            //initial state
            EventFragment fragment = new EventFragment();
            fragment.setArguments(getIntent().getExtras());
            this.selectedFragmentTag = fragment.getTag();
            this.savedInstanceState = new Bundle();
            this.savedInstanceState.putString(MainActivity.FRAGMENT_TAG, this.selectedFragmentTag);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_frameLayout_container, fragment, this.selectedFragmentTag)
                    .commit();
        }
        showSuggestionsToAllowPush();
        handlePushNotification();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.navigation_userProfile) {
            selectedFragment = getUserProfileFragment();
        } else if(itemId == R.id.navigation_webView){
            selectedFragment = getWebFragment();
        } else {
            selectedFragment = getEventFragment();
        }
        this.selectedFragmentTag = selectedFragment.getTag();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frameLayout_container, selectedFragment, this.selectedFragmentTag)
                .addToBackStack(null)
                .commit();
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment fragment = null;
        try {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
        }catch (Exception e){}
        if(Utils.notNull(fragment)){
            outState.putString(MainActivity.FRAGMENT_TAG, fragment.getTag());
            getSupportFragmentManager().putFragment(outState, fragment.getTag(), fragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        this.savedInstanceState = null;
        this.preferenceHelper = null;
    }

    public EventFragment getEventFragment() {
        EventFragment fragment = null;
        try {
            fragment = (EventFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
        }catch (Exception e){}
        if(Utils.isNull(fragment)){
            fragment = new EventFragment();
        }
        return fragment;
    }
    public UserProfileFragment getUserProfileFragment() {
        UserProfileFragment fragment = null;
        try {
            fragment = (UserProfileFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
        }catch (Exception e){}
        if(Utils.isNull(fragment)){
            fragment = new UserProfileFragment();
        }
        return fragment;
    }
    public WebFragment getWebFragment() {
        WebFragment fragment = null;
        try {
            fragment = (WebFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
        }catch (Exception e){}
        if(Utils.isNull(fragment)){
            fragment = new WebFragment();
        }
        return fragment;
    }
    private void requestPostPushNotificationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            return;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            } else {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);
            }
        }
    }
    private void handlePushNotification(){
        PushNotification pushNotification = Dfinery.getInstance().getDfineryPushNotification(getIntent());
        if(pushNotification==null){
            return;
        }
        Log.d(TAG, "pushNotification: "+pushNotification);
    }
    private void showSuggestionsToAllowPush(){
        boolean isAllowed = preferenceHelper.getBoolean(Preference.BOOLEAN_IS_NOTIFICATION_ALLOWED, false);
        if(isAllowed){
            requestPostPushNotificationPermission();
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("This App Would Like to Send Push Notifications")
                .setMessage("Notifications may include alerts, sounds, and icon badges. These can be configured in Settings.")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> consents = new HashMap<>();
                        consents.put(DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL, true);
                        consents.put(DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL, true);
                        DfineryProperties.setUserProfiles(consents);
                        preferenceHelper.putBoolean(Preference.BOOLEAN_IS_NOTIFICATION_ALLOWED, true);
                        requestPostPushNotificationPermission();
                    }
                })
                .setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> consents = new HashMap<>();
                        consents.put(DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL, false);
                        preferenceHelper.putBoolean(Preference.BOOLEAN_IS_NOTIFICATION_ALLOWED, false);
                        DfineryProperties.setUserProfiles(consents);
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .create();
        dialog.show();
    }
}