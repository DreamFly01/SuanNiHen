package com.fdl.activity.buy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.adapter.ShopAdapter;
import com.fdl.adapter.StartAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.GoodsBean;
import com.fdl.bean.ShopDetailsBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/10<p>
 * <p>changeTime：2019/1/10<p>
 * <p>version：1<p>  y
 */
public class ShopDetailsActivity extends BaseActivity {

    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;

    @BindView(R.id.recyclerView_product)
    RecyclerView recyclerViewProduct;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.item11)
    TextView item11;
    @BindView(R.id.item21)
    TextView item21;
    @BindView(R.id.item31)
    TextView item31;
    @BindView(R.id.item41)
    TextView item41;
    @BindView(R.id.ll_item1)
    LinearLayout llItem1;
    @BindView(R.id.avi)
    RelativeLayout avi;
    LinearLayout llItem;
    ImageView ivLogo;
    TextView tvName;
    ImageView ivIsFollow;
    TextView tvIsFollow;
    LinearLayout llFollow;
    TextView item1;
    TextView item2;
    TextView item3;
    TextView item4;
    RecyclerView recyclerViewStart;
    LinearLayout llBg;
    List<String> startDatas = new ArrayList<>();
    private Bitmap bitmap;

    private ShopDetailsBean bean;
    private Bundle bundle;
    private int storeId;
    private StartAdapter startAdapter;
    private ShopAdapter adapter;
    private boolean isFollow = false;
    private LayoutInflater inflater = null;
    private View headerView;
    private int index = 1;
    private int type = 0;
    private int scrollY = 0;

    private DialogUtils dialogUtils;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopdetails1_layout);
        dialogUtils = new DialogUtils(this);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            storeId = bundle.getInt("stroeId");
        }


    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardMenu.setVisibility(View.GONE);
        heardTitle.setText("店铺详情");
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = inflater.inflate(R.layout.header_shop_layout, null);
        llItem = (LinearLayout) headerView.findViewById(R.id.ll_item);
        ivLogo = (ImageView) headerView.findViewById(R.id.iv_Logo);
        tvName = (TextView) headerView.findViewById(R.id.tv_name);
        ivIsFollow = (ImageView) headerView.findViewById(R.id.iv_isFollow);
        tvIsFollow = (TextView) headerView.findViewById(R.id.tv_isFollow);
        llFollow = (LinearLayout) headerView.findViewById(R.id.ll_follow);
        item1 = (TextView) headerView.findViewById(R.id.item1);
        item2 = (TextView) headerView.findViewById(R.id.item2);
        item3 = (TextView) headerView.findViewById(R.id.item3);
        item4 = (TextView) headerView.findViewById(R.id.item4);
        recyclerViewStart = headerView.findViewById(R.id.recyclerView_start);
        llBg = headerView.findViewById(R.id.ll_shop_bg);

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index += 1;
                refreshData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        fillRecycleView();
    }

    @Override
    public void setUpLisener() {
        item1.setOnClickListener(this::onUserClick);
        item2.setOnClickListener(this::onUserClick);
        item3.setOnClickListener(this::onUserClick);
        item4.setOnClickListener(this::onUserClick);
        llFollow.setOnClickListener(this::onUserClick);
        recyclerViewProduct.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                SgLog.d(scrollY + "   " + llBg.getHeight());
                if (scrollY >= llBg.getHeight()) {
                    llItem1.setVisibility(View.VISIBLE);
                } else {
                    llItem1.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetShopInfo(storeId+"", this, new NetSubscriber<BaseResultBean<ShopDetailsBean>>(ShopDetailsActivity.this) {
            @Override
            public void onResultNext(BaseResultBean<ShopDetailsBean> model) {
                heardMenu.setVisibility(View.VISIBLE);
                bean = model.data;
                fillView(model.data);
                datas.clear();
                datas = model.data.goodsList;
                adapter.setNewData(datas);
                startAdapter.setNewData(startDatas);
                avi.setVisibility(View.GONE);
            }
        }));
    }


    @OnClick({R.id.heard_back, R.id.item11, R.id.item21, R.id.item31, R.id.item41, R.id.heard_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.item11:
                setItem(item11, item1);
                type = 0;
                index = 1;
                refreshData();
                break;
            case R.id.item21:
                setItem(item21, item2);
                type = 1;
                index = 1;
                refreshData();
                break;
            case R.id.item31:
                setItem(item31, item3);
                type = 2;
                index = 1;
                refreshData();
                break;
            case R.id.item41:
                setItem(item41, item4);
                type = 3;
                index = 1;
                refreshData();
                break;

            case R.id.heard_menu:
                if (StrUtils.isEmpty(bean.XCXUrl)) {
                    dialogUtils.ShareDialog(bean.ShopName, bean.ShopUrl, "我在算你狠发现一个不错的店铺，赶快来看看吧~", bean.ShopLogo);
                } else {
                    dialogUtils.ShareDialog(bean.ShopName, bean.XCXUrl, "我在算你狠发现一个不错的店铺，赶快来看看吧~", bean.ShopLogo);
                }
                break;

        }
    }

    @Override
    public void onUserClick(View v) {
        switch (v.getId()) {
            case R.id.item1:
                setItem(item1, item11);
                type = 0;
                index = 1;
                refreshData();
                break;
            case R.id.item2:
                setItem(item2, item21);
                type = 1;
                index = 1;
                refreshData();
                break;
            case R.id.item3:
                setItem(item3, item31);
                type = 2;
                index = 1;
                refreshData();
                break;
            case R.id.item4:
                setItem(item4, item41);
                type = 3;
                index = 1;
                refreshData();
                break;
            case R.id.ll_follow:
                if (!isLogin()) {
                    JumpUtils.simpJump(this,LoginActivity.class,false);
                } else {
                    followShop();
                }
                break;
            case R.id.heard_tv_menu:
                if (StrUtils.isEmpty(bean.XCXUrl)) {
                    dialogUtils.ShareDialog(bean.ShopName, bean.ShopUrl, "我在算你狠发现一个不错的店铺，赶快来看看吧~", bean.ShopLogo);
                } else {
                    dialogUtils.ShareDialog(bean.ShopName, bean.XCXUrl, "我在算你狠发现一个不错的店铺，赶快来看看吧~", bean.ShopLogo);
                }
                break;
        }
    }

    //填充数据
    private void fillView(ShopDetailsBean bean) {
        tvName.setText(bean.ShopName);
        ImageUtils.loadUrlCircleImage(this, bean.ShopLogo, ivLogo);
        isFollow = bean.IsFollow;
        if (isFollow) {
            tvIsFollow.setText("已关注");
            ivIsFollow.setBackgroundResource(R.drawable.follow_bg);
        } else {
            tvIsFollow.setText("未关注");
            ivIsFollow.setBackgroundResource(R.drawable.follow_no_bg);
        }

        for (int i = 0; i < bean.FollowNum; i++) {
            startDatas.add(i + "");
        }
    }

    //初始化已经更新 adapter数据
    private void fillRecycleView() {

        startAdapter = new StartAdapter(R.layout.item_start_layout, null);
        recyclerViewStart.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        recyclerViewStart.setNestedScrollingEnabled(false);
        recyclerViewStart.setAdapter(startAdapter);

        adapter = new ShopAdapter(R.layout.item_shop_layout, null);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewProduct.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bundle = new Bundle();
                bundle.putInt("goodsId", datas.get(position).Id);
                JumpUtils.dataJump(ShopDetailsActivity.this, ProductDetailsActivity.class, bundle, false);
            }
        });
        adapter.addHeaderView(headerView);
        llItem1.setVisibility(View.INVISIBLE);
    }

    private List<GoodsBean> datas = new ArrayList<>();
    //数据刷新
    private void refreshData() {
        addSubscription(RequestClient.GetShopProduct(storeId+"", index, type, 0, "", this, new NetSubscriber<BaseResultBean<List<GoodsBean>>>(ShopDetailsActivity.this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<GoodsBean>> model) {
                if (index == 1) {
//                    fillRecycleView(model.data);
                    datas.clear();
                    datas = model.data;
                    adapter.setNewData(datas);
                } else {
                    datas.addAll(model.data);
                    adapter.setNewData(datas);
                    refreshLayout.finishLoadMore();
                }
            }
        }));
    }

    //设置item样式
    private void setItem(TextView view, TextView view1) {
        item1.setTextColor(Color.parseColor("#999999"));
        item2.setTextColor(Color.parseColor("#999999"));
        item3.setTextColor(Color.parseColor("#999999"));
        item4.setTextColor(Color.parseColor("#999999"));
        item11.setTextColor(Color.parseColor("#999999"));
        item21.setTextColor(Color.parseColor("#999999"));
        item31.setTextColor(Color.parseColor("#999999"));
        item41.setTextColor(Color.parseColor("#999999"));
        view.setTextColor(Color.parseColor("#fc1a4e"));
        view1.setTextColor(Color.parseColor("#fc1a4e"));
    }

    private void followShop() {
        addSubscription(RequestClient.FollowShop(storeId+"", this, new NetSubscriber<BaseResultBean>(ShopDetailsActivity.this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                if (isFollow) {
                    tvIsFollow.setText("未关注");
                    ivIsFollow.setBackgroundResource(R.drawable.follow_no_bg);
                    isFollow = false;
                    showShortToast("取消成功");
                } else {
                    tvIsFollow.setText("已关注");
                    ivIsFollow.setBackgroundResource(R.drawable.follow_bg);
                    isFollow = true;
                    showShortToast("关注成功");
                }
            }
        }));
    }
}
