package com.fdl.wedgit;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sg.cj.snh.R;


/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/11/19<p>
 * <p>changeTime：2018/11/19<p>
 * <p>version：1<p>
 */
public class LoadingBg extends LinearLayout implements View.OnClickListener {

    public LoadingBg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialView(context);

    }

    private ImageView iv_loading;
    private LinearLayout ll_erro_bg;
    private LinearLayout ll_loading_bg;
    private Button btn_cs;
    private LinearLayout ll_view_bg;
    @Override
    public void onClick(View view) {
        iv_loading.setVisibility(View.VISIBLE);
        ll_loading_bg.setVisibility(View.VISIBLE);
        ll_erro_bg.setVisibility(View.GONE);
        if (null != ycErro) {
            ycErro.onTryAgainClick();
        }
    }


    private ErroLisener ycErro;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface ErroLisener {
        void onTryAgainClick();
    }

    private void initialView(Context context) {
        View.inflate(context, R.layout.custom_progress, this);
        setVisibility(View.VISIBLE);
        ll_loading_bg = (LinearLayout) findViewById(R.id.ll_loading_bg);
        ll_view_bg = (LinearLayout) findViewById(R.id.ll_02);
        ll_view_bg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //阻止事件向下传递
            }
        });
        iv_loading = (ImageView) findViewById(R.id.spinnerImageView);
        ll_erro_bg = (LinearLayout) findViewById(R.id.ll_01);
        btn_cs = (Button) findViewById(R.id.btn_try_again);
        spinner = (AnimationDrawable) iv_loading.getBackground();
        assert spinner != null;
//        默认一加载该布局就开始执行 加载动画
        spinner.start();
//        Glide.with(context).load(R.drawable.carloding).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_loading);
    }

    private AnimationDrawable spinner;

    public void showErroBg(ErroLisener ycerro) {
        this.ycErro = ycerro;
        setVisibility(View.VISIBLE);
        iv_loading.setVisibility(View.GONE);
        ll_erro_bg.setVisibility(View.VISIBLE);
        btn_cs.setOnClickListener(this);
        ll_loading_bg.setVisibility(View.GONE);

    }


    public void showLoadingBg() {
        setVisibility(View.VISIBLE);
        iv_loading.setVisibility(View.VISIBLE);
        ll_erro_bg.setVisibility(View.GONE);
        ll_loading_bg.setVisibility(View.VISIBLE);
    }



    public void dissmiss() {
        setVisibility(View.GONE);
    }
}
