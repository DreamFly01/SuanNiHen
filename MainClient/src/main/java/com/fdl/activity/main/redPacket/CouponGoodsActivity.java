package com.fdl.activity.main.redPacket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.activity.main.redPacket.bean.CouponsGoodsBean;
import com.fdl.activity.main.redPacket.bean.RedPackedCouponsConstants;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.common.event.BaiduMapEvent;
import com.fdl.jpush.Logger;
import com.fdl.utils.BaiduMapUtils;
import com.fdl.utils.IsBang;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.uitls.OkGoTools;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CouponGoodsActivity extends BaseActivity {
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
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private double latitude;
    private double longitude;
    private int couponId;
    private String gooodName;

    private int pageIndex;
    private int pageSize;
    private CouponGoodsAdapter adapter;

    private List<CouponsGoodsBean.DataBean.ListBean> listBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        listBeans = new ArrayList<>();
        pageIndex = 1;
        pageSize = 20;
        Bundle bundle = getIntent().getExtras();
        couponId = bundle.getInt(RedPackedCouponsConstants.COUPON_ID);
        gooodName = bundle.getString(RedPackedCouponsConstants.GOODS_NAME);
        //开启定位，定位成功后跳转到baiDuLocationEvent方法
        BaiduMapUtils.baiDuLocation(this);

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_storelist_layout);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText(gooodName);
        IsBang.setImmerHeard(this, rlHead);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new CouponGoodsAdapter(R.layout.item_store_layout, null);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("stroeId", listBeans.get(position).SupplierId);
                bundle.putInt("goodsId", listBeans.get(position).GoodsId);
                Intent intent = new Intent(CouponGoodsActivity.this, StoreDetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private boolean isLoadMore = true;


    @Override
    public void setUpLisener() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (isLoadMore) {
                    pageIndex += 1;
                    getData();
                } else {
                    refreshLayout.finishLoadMore();
                }

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getData();
            }
        });
    }

    @OnClick({R.id.heard_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void baiDuLocationEvent(BaiduMapEvent event) {
        if (event.getLatitude() != 0) {
            longitude = event.getLongitude();
            latitude = event.getLatitude();
        }
        getData();
    }


    public void getData() {
//        Map<String, Object> params = new HashMap<>();
        HttpParams params = new HttpParams();
        params.put(RedPackedCouponsConstants.COUPON_ID, couponId);
        params.put(RedPackedCouponsConstants.GOODS_NAME, gooodName);
        params.put("Longitude", longitude);
        params.put("Latitude", latitude);
        params.put("PageIndex", pageIndex);
        params.put("PageSize", pageSize);
        params.put("UserId", PartyApp.getAppComponent().getDataManager().getId());

        OkGoTools.getRequst(Constants.GET_COUPON_LIST, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {
                    Logger.i("RedPacketCoupon OKGO", response.body().toString());
                    avi.setVisibility(View.GONE);
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();

                    Gson gson = new Gson();
                    CouponsGoodsBean bean = gson.fromJson(response.body(), CouponsGoodsBean.class);


                    adapter.setNewData(bean.data.list);
                    adapter.notifyDataSetChanged();
                    if (bean.data.list.size() < 10) {
                        isLoadMore = false;
                    }
                    if (pageIndex == 1) {
                        listBeans.clear();
                    }
                    listBeans.addAll(bean.data.list);
                }
            }
        });
//        addSubscription(RequestClient.getCouponsGoodsList(this, params, new NetSubscriber<BaseResultBean<CouponsGoodsBean>>(this) {
//
//            @Override
//            public void onResultNext(BaseResultBean<CouponsGoodsBean> model) {
//
//            }
//        }));

    }
}
