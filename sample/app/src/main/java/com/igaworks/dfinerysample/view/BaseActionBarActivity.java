package com.igaworks.dfinerysample.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActionBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                getOnBackPressedDispatcher().onBackPressed();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }
    private void initActionBar(){
        getSupportActionBar().show();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
