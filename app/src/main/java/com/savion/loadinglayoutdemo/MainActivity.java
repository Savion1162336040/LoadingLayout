package com.savion.loadinglayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.savion.loadinglayout.LoadingLayout;

public class MainActivity extends AppCompatActivity {
    LoadingLayout loadinglayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadinglayout = findViewById(R.id.loadinglayout);
        loadinglayout.setLoadingImg(R.drawable.loading_rotate);
    }

    public void loading(View view) {
        loadinglayout.showLoading();
    }

    public void error(View view) {
        loadinglayout.showError();
    }

    public void empty(View view) {
        loadinglayout.showEmpty();
    }

    public void content(View view) {
        loadinglayout.showContent();
    }

    public void toAuto(View view) {
        startActivity(new Intent(this, AutoLoadLayoutActivity.class));
    }

}
