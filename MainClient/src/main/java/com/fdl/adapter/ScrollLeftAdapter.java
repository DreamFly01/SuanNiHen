package com.fdl.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */
public class ScrollLeftAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private List<TextView> tv = new ArrayList<>();
    public ScrollLeftAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.left_text,item)
                    .addOnClickListener(R.id.item);
        tv.add((TextView) helper.getView(R.id.left_text));
        if (tv != null && getData() != null && tv.size() == getData().size()) {
            selectItem(0);
        }
        helper.getView(R.id.item).setSelected(true);
    }
    public void cleanTv(){
        tv.clear();
    }
    //传入position,设置左侧列表相应item高亮
    public void selectItem(int position) {
        for (int i = 0; i < getData().size(); i++) {
            if (position == i) {
                tv.get(i).setBackgroundColor(Color.WHITE);
                //以下是指定某一个TextView滚动的效果
//                tv.get(i).setEllipsize(TextUtils.TruncateAt.MARQUEE);
//                tv.get(i).setFocusable(true);
//                tv.get(i).setFocusableInTouchMode(true);
//                tv.get(i).setMarqueeRepeatLimit(-1);
            } else {
                tv.get(i).setBackgroundColor(Color.parseColor("#f3f3f7"));

                //失去焦点则停止滚动
//                tv.get(i).setEllipsize(TextUtils.TruncateAt.END);
//                tv.get(i).setFocusable(false);
//                tv.get(i).setFocusableInTouchMode(false);
//                tv.get(i).setMarqueeRepeatLimit(0);
            }
        }
    }
}
