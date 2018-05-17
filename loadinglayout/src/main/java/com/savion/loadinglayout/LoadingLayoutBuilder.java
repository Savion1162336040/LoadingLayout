package com.savion.loadinglayout;

import android.content.Context;

public class LoadingLayoutBuilder {

    private String error_msg;
    private String empty_msg;
    private String loading_msg;
    private int error_drawable;
    private int empty_drawable;
    private int loading_drawable;
    private Context context;

    public LoadingLayoutBuilder(Context context) {
        this.context = context;
    }

    public LoadingLayoutBuilder setError_msg(String error_msg) {
        this.error_msg = error_msg;
        return this;
    }

    public LoadingLayoutBuilder setEmpty_msg(String empty_msg) {
        this.empty_msg = empty_msg;
        return this;
    }

    public LoadingLayoutBuilder setLoading_msg(String loading_msg) {
        this.loading_msg = loading_msg;
        return this;
    }

    public LoadingLayoutBuilder setError_drawable(int error_drawable) {
        this.error_drawable = error_drawable;
        return this;
    }

    public LoadingLayoutBuilder setEmpty_drawable(int empty_drawable) {
        this.empty_drawable = empty_drawable;
        return this;
    }

    public LoadingLayoutBuilder setLoading_drawable(int loading_drawable) {
        this.loading_drawable = loading_drawable;
        return this;
    }

    public LoadingLayout build() {
        LoadingLayout loadingLayout = new LoadingLayout(context);
        loadingLayout.setBuilder(this);
        return loadingLayout;
    }

    public void show() {
        build().setBuilder(this).show();
    }


}
