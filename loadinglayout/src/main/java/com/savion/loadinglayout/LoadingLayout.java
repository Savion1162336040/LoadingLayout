package com.savion.loadinglayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class LoadingLayout extends FrameLayout {

    private LoadingLayoutBuilder _builder = null;
    @LayoutRes
    private int _view_error = R.layout.layout_error;
    @LayoutRes
    private int _view_empty = R.layout.layout_empty;
    @LayoutRes
    private int _view_loading = R.layout.layout_loading;
    private int _view_content = NO_ID;

    private String _error_text = null;
    private String _empty_text = null;
    private String _loading_text = null;
    @DrawableRes
    private int _error_image = NO_ID;
    @DrawableRes
    private int _empty_image = NO_ID;
    @DrawableRes
    private int _loading_drawable = NO_ID;
    @ColorInt
    private int _text_color = NO_ID;
    private float _text_size = 15;

    private OnClickListener _retryListener;

    private Map<Integer, View> _viewMaps = new HashMap<>();

    private LayoutInflater _layoutInflater;

    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.styleLoadingLayout);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _layoutInflater = LayoutInflater.from(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, defStyleAttr, R.style.LoadingLayoutStyle);
        _empty_text = typedArray.getString(R.styleable.LoadingLayout_empty_text);
        _loading_text = typedArray.getString(R.styleable.LoadingLayout_loading_text);
        _error_text = typedArray.getString(R.styleable.LoadingLayout_error_text);
        _empty_image = typedArray.getResourceId(R.styleable.LoadingLayout_empty_image, NO_ID);
        _error_image = typedArray.getResourceId(R.styleable.LoadingLayout_error_image, NO_ID);
        _loading_drawable = typedArray.getResourceId(R.styleable.LoadingLayout_loading_drawable, NO_ID);
        _text_size = typedArray.getDimension(R.styleable.LoadingLayout_text_size, 15);
        _text_color = typedArray.getColor(R.styleable.LoadingLayout_text_color, Color.BLACK);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        switch (getChildCount()) {
            case 0:
                throw new NullPointerException("LoadingLayout need a content layout");
            case 1:
                _view_content = getChildAt(0).getId();
                _viewMaps.put(_view_content, getChildAt(0));
                showContent();
                break;
            default:
                throw new IllegalArgumentException("LoadingLayout only support one content layout");
        }
    }

    public String get_error_text() {
        return _error_text;
    }

    public LoadingLayout set_error_text(String _error_text) {
        this._error_text = _error_text;
        return this;
    }

    public String get_empty_text() {
        return _empty_text;
    }

    public LoadingLayout set_empty_text(String _empty_text) {
        this._empty_text = _empty_text;
        return this;
    }

    public String get_loading_text() {
        return _loading_text;
    }

    public LoadingLayout set_loading_text(String _loading_text) {
        this._loading_text = _loading_text;
        return this;
    }

    public int get_error_image() {
        return _error_image;
    }

    public LoadingLayout set_error_image(int _error_image) {
        this._error_image = _error_image;
        return this;
    }

    public int get_empty_image() {
        return _empty_image;
    }

    public LoadingLayout set_empty_image(int _empty_image) {
        this._empty_image = _empty_image;
        return this;
    }

    public int get_loading_drawable() {
        return _loading_drawable;
    }

    public LoadingLayout set_loading_drawable(int _loading_drawable) {
        this._loading_drawable = _loading_drawable;
        return this;
    }

    public int get_text_color() {
        return _text_color;
    }

    public LoadingLayout set_text_color(int _text_color) {
        this._text_color = _text_color;
        return this;
    }

    public float get_text_size() {
        return _text_size;
    }

    public LoadingLayout set_text_size(float _text_size) {
        this._text_size = _text_size;
        return this;
    }

    protected LoadingLayout setBuilder(LoadingLayoutBuilder builder) {
        this._builder = builder;
        return this;
    }

    public void show() {
        show(_builder);
    }

    public void show(LoadingLayoutBuilder builder) {
        if (builder == null) {
            throw new NullPointerException("LoadingLayout need a LoadingLayoutBuilder!!!");
        }
        showContent();
    }

    private void show(int layoutID) {
        for (View i : _viewMaps.values()) {
            i.setVisibility(View.GONE);
        }
        View view = inflatLayout(layoutID);
        view.setVisibility(View.VISIBLE);
    }

    public void showEmpty() {
        show(_view_empty);
    }

    public void showError() {
        show(_view_error);
    }

    public void showLoading() {
        show(_view_loading);
    }

    public void showContent() {
        show(_view_content);
    }

    public LoadingLayout retry(OnClickListener listener) {
        this._retryListener = listener;
        return this;
    }

    private View inflatLayout(int layout) {
        if (_viewMaps.containsKey(layout)) {
            return _viewMaps.get(layout);
        }
        //inflate(指定需要添加的layoutID,root,attachToRoot)
        //root表示需要添加到哪个的根布局（这里指定root的作用是使layoutID的根元素属性能生效，比如layout的width,height等属性是需要有parentLayout才能生效的，如果此处没有指定inflate的root的话，那么layoutID的根布局属性也就不会生效）
        //attachToRoot表示是否立即添加到root上，false的话就需要手动addView添加，true就会立即添加到root上
        View view = _layoutInflater.inflate(layout, this, false);
        view.setVisibility(View.GONE);
        addView(view);
        _viewMaps.put(layout, view);

        ImageView imageView = view.findViewById(R.id.icon);
        TextView textView = view.findViewById(R.id.msg);
        textView.setTextColor(_text_color);
        textView.setTextSize(_text_size);
        if (layout == _view_empty) {
            textView.setText(_empty_text);
            imageView.setImageResource(_empty_image);
        } else if (layout == _view_error) {
            textView.setText(_error_text);
            imageView.setImageResource(_error_image);
            TextView retry = view.findViewById(R.id.retry);
            retry.setOnClickListener(_retryListener);
        } else if (layout == _view_loading) {
            textView.setText(_loading_text);
            imageView.setImageResource(_loading_drawable);
        }

        return view;
    }

}
