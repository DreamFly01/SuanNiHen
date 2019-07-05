package com.fdl.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fdl.bean.ImgDelagateBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.JumpUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/3<p>
 * <p>changeTime：2019/1/3<p>
 * <p>version：1<p>
 */
public class AddImgAdapter implements ItemViewDelegate<ImgDelagateBean> {
    private Context context;
    private List<ImgDelagateBean> data = new ArrayList<>();
    private List<ImgDelagateBean> data1 = new ArrayList<>();
    private DetelOnClick detelOnClick;
    private Activity activity;

    public AddImgAdapter(Context context, Activity activity, DetelOnClick detelOnClick) {
        this.context = context;
        this.detelOnClick = detelOnClick;
        this.activity = activity;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_adapte4_layout;
    }

    @Override
    public boolean isForViewType(ImgDelagateBean item, int position) {
        return item.isItem;
    }

    @Override
    public void convert(ViewHolder holder, final ImgDelagateBean imgDelagateBean, final int position) {
        ImageView imageView = holder.getView(R.id.img_item_cialiao);
        ImageView detel = holder.getView(R.id.img_detel);
        ImageUtils.loadUrlCorners(context, data1.get(position).url, imageView);
        detel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detelOnClick.detelClick(view, position);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl", data1.get(position).url);
//                JumpUtils.dataJump(activity, ImgaActivity.class, bundle, false);
            }
        });
    }

    public void setData(List<ImgDelagateBean> data, List<ImgDelagateBean> data1) {
        this.data = data;
        this.data1 = data1;
    }

    //    public void updata(List<ImgDelagateBean> data,ni ){
//        this.data = data;
//    }
    public interface DetelOnClick {
        public void detelClick(View v, int position);
    }
}
