package com.fdl.activity.supermarket;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.activity.food.FoodShopActivity;
import com.fdl.activity.main.MainActivity;
import com.fdl.adapter.StroreAdapter;
import com.fdl.bean.AgreementBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.SuperMarketBean;
import com.fdl.bean.SupplierBean;
import com.fdl.db.DBManager;
import com.fdl.db.ShopTypeEnum;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.utils.DistanceUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;
import com.snh.greendao.SuperMarketBeanDao;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/24<p>
 * <p>changeTime：2019/1/24<p>
 * <p>version：1<p>
 */
public class StoreListActivity extends BaseActivity {
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

    private List<SuperMarketBean> datas = new ArrayList<>();
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private double latitude;
    private double longitude;
    private int index = 1;
    private StroreAdapter adapter;
    boolean isClick;
    private String province;
    private String city;
    private String district = "";
    private Bundle bundle;

    private String addressLevel = "";
    private String addressId = "";
    private int shopType = 0;
    private String title = "";

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_storelist_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            district = SPUtils.getInstance(this).getString(Contans.LAST_CITY_ID);
            province = SPUtils.getInstance(this).getString(Contans.PROVINCE);
            city = SPUtils.getInstance(this).getString(Contans.LAST_CITY);
            addressId = SPUtils.getInstance(this).getString(Contans.ADDRESS_ID);
            addressLevel = SPUtils.getInstance(this).getString(Contans.LEVEL);
            shopType = bundle.getInt("type");
            title = bundle.getString("title");
            if (!StrUtils.isEmpty(SPUtils.getInstance(this).getString(Contans.LATITUDE))) {
                latitude = Double.parseDouble(SPUtils.getInstance(this).getString(Contans.LATITUDE));
                longitude = Double.parseDouble(SPUtils.getInstance(this).getString(Contans.LONGITUDE));
            } else {
                initLocation();
            }
        }
    }

    @Override
    public void setUpViews() {
        heardTitle.setText(title);
        IsBang.setImmerHeard(this, rlHead);
        setRecyclerView();
    }

    @Override
    public void setUpLisener() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (isLoadMore) {
                    index += 1;
                    getData();
                } else {
                    refreshLayout.finishLoadMore();
                }

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                getData();
            }
        });
    }

    private boolean isLoadMore = true;

    public void getData() {
        addSubscription(RequestClient.GetSuperMarketList(index, addressLevel, addressId, longitude + "", latitude + "", shopType, this, new NetSubscriber<BaseResultBean<List<SuperMarketBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<SuperMarketBean>> model) {
                avi.setVisibility(View.GONE);
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                if (index == 1) {
                    if (model.data.size() > 0) {
                        datas.clear();
                        if (getCache().size() > 0) {
                            SuperMarketBean superMarketBean = new SuperMarketBean(SuperMarketBean.CATCHDATE_TITLE);
                            datas.add(superMarketBean);
                            datas.addAll(getCache());
                        }
                        SuperMarketBean superMarketBean = new SuperMarketBean(SuperMarketBean.NETDATE_TITLE);
                        datas.add(superMarketBean);
                        datas.addAll(model.data);
                        for (int i = 0; i < model.data.size(); i++) {
                            model.data.get(i).itemType = SuperMarketBean.NETDATE;
                        }
                        adapter.setNewData(datas);
                        if (model.data.size() < 10) {
                            isLoadMore = false;
                        }
                    } else {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (model.data.size() > 0) {
                        for (int i = 0; i < model.data.size(); i++) {
                            model.data.get(i).itemType = SuperMarketBean.NETDATE;
                        }
                        datas.addAll(model.data);
                        adapter.setNewData(datas);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }

            }
        }));
    }

    @OnClick({R.id.heard_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
        }
    }

    public void setRecyclerView() {
        adapter = new StroreAdapter(null);
        adapter.setShopType(shopType);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (datas.get(position).itemType == 1 || datas.get(position).itemType == 2) {
            Bundle bundle = new Bundle();
            if (shopType == ShopTypeEnum.TYPE6.getValue()) {
                bundle.putInt("stroeId", datas.get(position).SupplierId);
                bundle.putString("distance", datas.get(position).Distance);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putInt("shopType", shopType);
                JumpUtils.dataJump(StoreListActivity.this, StoreDetailsActivity.class, bundle, false);
            }
            if (shopType == ShopTypeEnum.TYPE5.getValue() | shopType == ShopTypeEnum.TYPE3.getValue() | shopType == ShopTypeEnum.TYPE7.getValue() | shopType == ShopTypeEnum.TYPE7.getValue()) {
                bundle.putInt("stroeId", datas.get(position).SupplierId);
                JumpUtils.dataJump(StoreListActivity.this, ShopDetailsActivity.class, bundle, false);
            }
            if (shopType == ShopTypeEnum.TYPE1.getValue()) {
                bundle.putInt("stroeId", datas.get(position).SupplierId);
                bundle.putString("distance", datas.get(position).Distance);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putInt("shopType", shopType);
                JumpUtils.dataJump(StoreListActivity.this, FoodShopActivity.class, bundle, false);
            }
        }

        });
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (null != bdLocation) {
                    latitude = bdLocation.getLatitude();
                    longitude = bdLocation.getLongitude();
                    SPUtils.getInstance(StoreListActivity.this).saveData(Contans.LATITUDE, bdLocation.getLatitude() + "");
                    SPUtils.getInstance(StoreListActivity.this).saveData(Contans.LONGITUDE, bdLocation.getLongitude() + "");
                }

            }
        });
        //注册监听函数

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }

    private List<SuperMarketBean> getCache() {
        SuperMarketBeanDao marketBeanDao = DBManager.getInstance(this).getDaoSession().getSuperMarketBeanDao();
        List<SuperMarketBean> beanList = marketBeanDao.queryBuilder().where(SuperMarketBeanDao.Properties.ShopType.eq(shopType)).list();
        List<SuperMarketBean> datas = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            datas.add(beanList.get(i));
        }
        return datas;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }
}
