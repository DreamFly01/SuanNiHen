package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.SuperMarkGoodsBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.TimeUtils;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/4<p>
 * <p>changeTime：2019/4/4<p>
 * <p>version：1<p>
 */
public class SuperMarkAdapter extends BaseQuickAdapter<SuperMarkGoodsBean.GoodsCommentResult, BaseViewHolder> {
    public SuperMarkAdapter(int layoutResId, @Nullable List<SuperMarkGoodsBean.GoodsCommentResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SuperMarkGoodsBean.GoodsCommentResult item) {
        helper.setText(R.id.tv_name, item.UserName);
        helper.setText(R.id.tv_time, TimeUtils.timeStamp2Date(item.CommentDate + "", "yyyy-MM-dd"));
        helper.setText(R.id.tv_introduce, item.Comment);
        ImageUtils.loadUrlCircleImage(mContext,item.HeadPortrait,helper.getView(R.id.iv_product_logo));
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        RecyclerView start = helper.getView(R.id.recyclerView_start);
        List<String> startDatas = new ArrayList<>();
        for (int i = 0; i < item.Starlevel; i++) {
            startDatas.add(i + "");
        }
        StartAdapter startAdapter = new StartAdapter(R.layout.item_start_layout, startDatas);
        start.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        start.setNestedScrollingEnabled(false);
        start.setAdapter(startAdapter);
        List<String> imgDatas = new ArrayList<>();
        if (item.CommentImgs != null) {
            for (int j = 0; j < item.CommentImgs.length; j++) {
                imgDatas.add(item.CommentImgs[j]);
            }
        }
        ImageAdapter imageAdapter = new ImageAdapter(R.layout.item_image_layout, imgDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(imageAdapter);
    }
}
