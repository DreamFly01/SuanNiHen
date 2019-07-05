package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.DiscoverBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/13<p>
 * <p>changeTime：2019/2/13<p>
 * <p>version：1<p>
 */
public class DiscoverAdapter extends BaseQuickAdapter<DiscoverBean,BaseViewHolder> {
    public DiscoverAdapter(int layoutResId, @Nullable List<DiscoverBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscoverBean item) {
        helper.setText(R.id.txtSupplierName,item.SupplierName);
        helper.setText(R.id.txtfbTime,item.fbTime);
        ImageView logo = helper.getView(R.id.img_Supplier);
        ImageUtils.loadUrlImage(mContext,item.SupplierImg,logo);
        helper.setText(R.id.txtText,item.Text);
        helper.addOnClickListener(R.id.ll_store);
        helper.addOnClickListener(R.id.layout_go_buy);
        JCVideoPlayerStandard jc= helper.getView(R.id.videoplayer);

        jc.setUp(item.Content,JCVideoPlayer.SCREEN_LAYOUT_NORMAL,"");
        if(!StrUtils.isEmpty(item.CoverImg)){
            ImageUtils.loadUrlImage(mContext,item.CoverImg,jc.thumbImageView);
//            Glide.with(mContext).load(item.CoverImg).into(jc.thumbImageView);
        }else {
            jc.thumbImageView.setImageDrawable(null);
        }

    }

}
