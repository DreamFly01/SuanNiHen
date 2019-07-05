package com.fdl.wedgit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sg.cj.snh.R;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/9<p>
 * <p>changeTime：2019/5/9<p>
 * <p>version：1<p>
 */
public class StoreToolBar extends Toolbar {
    private LayoutInflater inflater;
    private View view;
    private RelativeLayout back,search,fllow,share;

    public StoreToolBar(Context context) {
        super(context);
    }

    public StoreToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if(view==null){
            inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.store_toolbar_layout,null);
            back = (RelativeLayout)view.findViewById(R.id.heard_back);
            search = view.findViewById(R.id.heard_search);
            fllow = view.findViewById(R.id.heard_follow);
            share = view.findViewById(R.id.heard_share);

            addView(view);
        }
    }

    public void setBackListener(OnClickListener listener){
        back.setOnClickListener(listener);
    }
    public void setSearchListener(OnClickListener listener){
        search.setOnClickListener(listener);
    }
    public void setFllowListener(OnClickListener listener){
        fllow.setOnClickListener(listener);
    }
    public void setShareListener(OnClickListener listener){
        share.setOnClickListener(listener);
    }
}
