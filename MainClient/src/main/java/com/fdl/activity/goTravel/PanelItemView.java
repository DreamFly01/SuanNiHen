package com.fdl.activity.goTravel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/6<p>
 * <p>changeTime：2019/5/6<p>
 * <p>version：1<p>
 */
public class PanelItemView  extends FrameLayout implements ItemView{

    private View overlay;
    private TextView tvName;
    private ImageView ivItem;

    public PanelItemView(Context context) {
        this(context, null);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);
        overlay = findViewById(R.id.overlay);
        tvName = findViewById(R.id.tv_item_name);
        ivItem = findViewById(R.id.iv_item_bg);
        overlay.setVisibility(INVISIBLE);
    }

    @Override
    public void setFocus(boolean isFocused) {
        if (overlay != null) {
            overlay.setVisibility(isFocused ? VISIBLE : INVISIBLE);
        }
    }
    public void setView(Context context,String name,String imgUrl){
        tvName.setText(name);
        ImageUtils.loadUrlCorners(context,imgUrl,ivItem);
    }
}