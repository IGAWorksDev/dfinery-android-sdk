package com.igaworks.dfinerysample.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.igaworks.dfinery.DfineryProperties;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    public static final String TAG = Utils.getTagFromClass(MainActivity.class);
    private MainActivityBinding binding;
    private Bundle savedInstanceState;
    private String selectedFragmentTag;
    public static final String FRAGMENT_TAG = "MainFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mainBottomNavigationView.setOnItemSelectedListener(this);
        binding.mainTextViewSdkVersion.setText(DfineryProperties.getSdkVersion());
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.navigation_userProfile) {
            selectedFragment = getUserProfileFragment();
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
}