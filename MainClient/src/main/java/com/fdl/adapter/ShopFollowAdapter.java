package com.fdl.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ShopBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/12<p>
 * <p>changeTime：2019/1/12<p>
 * <p>version：1<p>
 */
public class ShopFollowAdapter extends CommonAdapter<ShopBean> {
    List<String> startDatas = new ArrayList<>();
    private StartAdapter startAdapter;
    private DeleteFollowLisener deleteFollow;
    public ShopFollowAdapter(Context context, int layoutId, List<ShopBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ShopBean shopBean, int position) {
        ImageView logo = holder.getView(R.id.iv_Logo);
        TextView name = holder.getView(R.id.tv_name);
        LinearLayout llFollow = (LinearLayout) holder.getView(R.id.ll_follow);
        RecyclerView recyclerViewStart = holder.getView(R.id.recyclerView_start);
        ImageUtils.loadUrlImage(mContext,shopBean.Logo,logo);
        for (int i = 0; i < shopBean.Startlevel; i++) {
            startDatas.add(i + "");
        }
        name.setText(shopBean.ShopName);
        startAdapter = new StartAdapter(R.layout.item_start_layout, startDatas);
        recyclerViewStart.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        recyclerViewStart.setNestedScrollingEnabled(false);
        recyclerViewStart.setAdapter(startAdapter);

        llFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFollow.delete(v,shopBean.Id);
            }
        });
    }

    public void addDatas(List<ShopBean> datas){
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
    public void setDeleteFollow(DeleteFollowLisener deleteFollow) {
        this.deleteFollow = deleteFollow;
    }

    public interface DeleteFollowLisener{
        void delete(View view,int id);
    }
}
