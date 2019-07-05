package com.fdl.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.fdl.bean.ImgDelagateBean;
import com.fdl.utils.DialogUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/3<p>
 * <p>changeTime：2019/1/3<p>
 * <p>version：1<p>
 */
public class FootItemDelagateAdapter implements ItemViewDelegate<ImgDelagateBean> {
    private DialogUtils dialogUtils;
    private Context context;
    private Activity activity;
    private OnMyAdapterClick onMyPhotoClick;

    public FootItemDelagateAdapter(Context context, Activity activity,OnMyAdapterClick onPhotoClick) {
        this.context = context;
        this.activity = activity;
        this.onMyPhotoClick = onPhotoClick;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_adapte5_layout;
    }

    @Override
    public boolean isForViewType(ImgDelagateBean item, int position) {
        return !item.isItem;
    }

    @Override
    public void convert(ViewHolder holder, ImgDelagateBean imgDelagateBean, int position) {
        LinearLayout add = holder.getView(R.id.ll_item_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogUtils dialogUtils = new DialogUtils(context,activity);
                dialogUtils.headImgDialog(new DialogUtils.HeadImgChoseLisener() {
                    @Override
                    public void onCancelClick(View v) {
                        dialogUtils.dismissDialog();
                    }

                    @Override
                    public void onPhotoClick(View v) {
                        onMyPhotoClick.onPhotoClick(v);
                        dialogUtils.dismissDialog();
                    }

                    @Override
                    public void onAlumClick(View v) {
                        onMyPhotoClick.onAlumbClick(v);
                        dialogUtils.dismissDialog();
                    }
                }, true);
            }
        });
    }

    public interface OnMyAdapterClick {
        public void onPhotoClick(View v);
        public void onAlumbClick(View v);
    }

}
