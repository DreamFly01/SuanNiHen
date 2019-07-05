package com.fdl.activity.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.activity.supermarket.StoreListActivity;
import com.fdl.adapter.ShopFollowAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ShopBean;
import com.fdl.db.ShopTypeEnum;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/12<p>
 * <p>changeTime：2019/1/12<p>
 * <p>version：1<p>
 */
public class ShopFollowListActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private ShopFollowAdapter adapter;
    private DialogUtils dialogUtils;
    private int index = 1;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopfollow_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        heardTitle.setText("店铺关注");
    }

    @Override
    public void setUpLisener() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index += 1;
                getDataOnCreate();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

    }


    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }

    List<ShopBean> datas = new ArrayList<>();

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetShopFollow(index, this, new NetSubscriber<BaseResultBean<List<ShopBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<ShopBean>> model) {
                avi.setVisibility(View.GONE);
                refreshLayout.finishLoadMore();
                if (index == 1) {
                    datas.clear();
                    if (model.data.size() > 0) {
                        datas = model.data;
                        setRecyclerView(model.data);
                    } else {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (model.data.size() > 0) {
                        datas.addAll(model.data);
                        adapter.addDatas(model.data);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        }));
    }

    //删除关注记录
    private void deleteFollow(String ids) {
        RequestClient.DeleteShopFollow(ids, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                dialogUtils.simpleDialog("取消成功", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        index = 1;
                        dialogUtils.dismissDialog();
                        getDataOnCreate();
                    }
                }, false);
            }
        });
    }

    public void setRecyclerView(List<ShopBean> datas) {
        adapter = new ShopFollowAdapter(this, R.layout.item_shopfollow_layout, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL, 30, getResources().getColor(R.color.color_app_bg)));
        recyclerView.setAdapter(adapter);
        adapter.setDeleteFollow(new ShopFollowAdapter.DeleteFollowLisener() {
            @Override
            public void delete(View view, int id) {
                deleteFollow(id + "");
            }
        });
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                if (!StrUtils.isEmpty(datas.get(position).ShopType)) {

                    if (Integer.parseInt(datas.get(position).ShopType) == ShopTypeEnum.TYPE6.getValue()) {
                        bundle.putInt("stroeId", datas.get(position).ShopId);
//                    bundle.putString("distance", datas.get(position).distance);
//                    bundle.putDouble("latitude", latitude);
//                    bundle.putDouble("longitude", longitude);
                        JumpUtils.dataJump(ShopFollowListActivity.this, StoreDetailsActivity.class, bundle, false);
                    }
                    if (Integer.parseInt(datas.get(position).ShopType) == ShopTypeEnum.TYPE5.getValue() | Integer.parseInt(datas.get(position).ShopType) == ShopTypeEnum.TYPE3.getValue() | Integer.parseInt(datas.get(position).ShopType) == ShopTypeEnum.TYPE7.getValue() | Integer.parseInt(datas.get(position).ShopType) == ShopTypeEnum.TYPE9.getValue()) {
                        bundle.putInt("stroeId", datas.get(position).ShopId);
                        JumpUtils.dataJump(ShopFollowListActivity.this, ShopDetailsActivity.class, bundle, false);
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
