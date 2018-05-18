package com.savion.loadinglayout;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class LoadingLayout extends FrameLayout {
    private static String TAG = LoadingLayout.class.getSimpleName();

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
                setContent(getChildAt(0));
                break;
            default:
                throw new IllegalArgumentException("LoadingLayout only support one content layout");
        }
    }

    public void setContent(View view) {
        _view_content = view.getId();
        _viewMaps.put(_view_content, view);
        showContent();
    }

    /**
     * 实例化一个LoadingLayout，包裹住view，并绑定到view所在activity
     * 相当于自动生成一个包含content的LoadingLayout
     *
     * @param view
     * @return
     */
    public static LoadingLayout setUpToParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);
        LoadingLayout loadingLayout = new LoadingLayout(view.getContext());
        loadingLayout.addView(view);
        loadingLayout.setContent(view);
        parent.addView(loadingLayout, index);
        loadingLayout.setLayoutParams(params);
        return loadingLayout;
    }

    public String getErrorText() {
        return _error_text;
    }

    public LoadingLayout setErrorText(String _error_text) {
        this._error_text = _error_text;
        setText(_view_error, R.id.msg, _error_text, _text_size, _text_color);
        return this;
    }

    public String getEmptyText() {
        return _empty_text;
    }

    public LoadingLayout setEmptyText(String _empty_text) {
        this._empty_text = _empty_text;
        setText(_view_empty, R.id.msg, _empty_text, _text_size, _text_color);
        return this;
    }

    public String getLoadingText() {
        return _loading_text;
    }

    public LoadingLayout setLoadingText(String _loading_text) {
        this._loading_text = _loading_text;
        setText(_view_loading, R.id.msg, _loading_text, _text_size, _text_color);
        return this;
    }

    public int getErrorImg() {
        return _error_image;
    }

    public LoadingLayout setErrorImg(int _error_image) {
        this._error_image = _error_image;
        setImg(_view_error, R.id.icon, _error_image);
        return this;
    }

    public int getEmptyImg() {
        return _empty_image;
    }

    public LoadingLayout setEmptyImg(int _empty_image) {
        this._empty_image = _empty_image;
        setImg(_view_empty, R.id.icon, _empty_image);
        return this;
    }

    public int getLoadingImg() {
        return _loading_drawable;
    }

    public LoadingLayout setLoadingImg(int _loading_drawable) {
        this._loading_drawable = _loading_drawable;
        setImg(_view_loading, R.id.icon, _loading_drawable);
        return this;
    }

    public int getTextColor() {
        return _text_color;
    }

    public LoadingLayout setTextColor(int _text_color) {
        this._text_color = _text_color;
        setText(_view_error, R.id.msg, _error_text, _text_size, _text_color);
        setText(_view_empty, R.id.msg, _empty_text, _text_size, _text_color);
        setText(_view_loading, R.id.msg, _loading_text, _text_size, _text_color);
        return this;
    }

    public float getTextSize() {
        return _text_size;
    }

    public LoadingLayout setTextSize(float _text_size) {
        this._text_size = _text_size;
        setText(_view_error, R.id.msg, _error_text, _text_size, _text_color);
        setText(_view_empty, R.id.msg, _empty_text, _text_size, _text_color);
        setText(_view_loading, R.id.msg, _loading_text, _text_size, _text_color);
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
        View result = null;
        for (int index : _viewMaps.keySet()) {
            if (index == layout) {
                // TODO: 2018/5/18 启动view vector动画
                result = _viewMaps.get(index);
                ImageView i = result.findViewById(R.id.icon);
                Drawable drawable = i != null ? i.getDrawable() : null;
                if (drawable != null && drawable instanceof Animatable) {
                    Animatable animatable = ((Animatable) drawable);
                    if (!animatable.isRunning()) {
                        animatable.start();
                        Log.d(TAG,String.format("%s anim start",result.getTag()));
                    }
                    Log.d(TAG,String.format("%s anim start:%s",_viewMaps.get(index).getTag(),animatable.isRunning()));
                }
            } else {
                // TODO: 2018/5/18 停止各view vector动画
                View v = _viewMaps.get(index);
                ImageView i = v.findViewById(R.id.icon);
                Drawable drawable = i != null ? i.getDrawable() : null;
                if (drawable != null && drawable instanceof Animatable) {
                    Animatable animatable = ((Animatable) drawable);
                    if (animatable.isRunning()) {
                        animatable.stop();
                        Log.d(TAG,String.format("%s anim stop",v.getTag()));
                    }
                    Log.d(TAG,String.format("%s anim stop:%s",_viewMaps.get(index).getTag(),animatable.isRunning()));
                }
            }
        }
        if (result != null)
            return result;
        //inflate(指定需要添加的layoutID,root,attachToRoot)
        //root表示需要添加到哪个的根布局（这里指定root的作用是使layoutID的根元素属性能生效，比如layout的width,height等属性是需要有parentLayout才能生效的，如果此处没有指定inflate的root的话，那么layoutID的根布局属性也就不会生效）
        //attachToRoot表示是否立即添加到root上，false的话就需要手动addView添加，true就会立即添加到root上
        result = _layoutInflater.inflate(layout, this, false);
        result.setVisibility(View.GONE);
        addView(result);
        _viewMaps.put(layout, result);

        if (layout == _view_empty) {
            setText(_view_empty, R.id.msg, _empty_text, _text_size, _text_color);
            setImg(_view_empty, R.id.icon, _empty_image);
            result.setTag("empty");
        } else if (layout == _view_error) {
            TextView retry = result.findViewById(R.id.retry);
            retry.setOnClickListener(_retryListener);
            setImg(_view_error, R.id.icon, _error_image);
            setText(_view_error, R.id.msg, _error_text, _text_size, _text_color);
            result.setTag("error");
        } else if (layout == _view_loading) {
            setText(_view_loading, R.id.msg, _loading_text, _text_size, _text_color);
            setImg(_view_loading, R.id.icon, _loading_drawable);
            result.setTag("loading");
        }
        return result;
    }

    private void setText(int layoutID, int textID, String value, float size, int color) {
        if (_viewMaps.containsKey(layoutID)) {
            View view = _viewMaps.get(layoutID);
            TextView textView = view.findViewById(textID);
            if (textView != null) {
                textView.setText(value);
                textView.setTextSize(size);
                textView.setTextColor(color);
            }
        }
    }

    private void setImg(int layoutID, int imgID, int drawable) {
        if (_viewMaps.containsKey(layoutID)) {
            View view = _viewMaps.get(layoutID);
            ImageView imageView = view.findViewById(imgID);
            if (imageView != null) {
                imageView.setImageResource(drawable);
            }
        }
    }

}
