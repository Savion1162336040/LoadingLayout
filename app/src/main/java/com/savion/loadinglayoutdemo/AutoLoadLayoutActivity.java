package com.savion.loadinglayoutdemo;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.savion.loadinglayout.LoadingLayout;

public class AutoLoadLayoutActivity extends AppCompatActivity {
    LoadingLayout loadinglayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_load_layout);
        TextView content = findViewById(R.id.contet);
        loadinglayout = LoadingLayout.setUpToParent(content)
                .setLoadingText("加载中哦》》》")
                .setErrorText("遇到错误哦！！！")
                .setEmptyText("没有数据哦！！！")
                .setTextSize(10)
                .setTextColor(Color.BLUE)
                .setEmptyImg(R.mipmap.ic_launcher)
                .setErrorImg(R.drawable.ic_launcher_background)
                .setLoadingImg(R.drawable.svg_anim_logo);
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
}
