package com.fdl.activity.main;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseFragment;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.activity.main.event.AddressLocationEvent;
import com.fdl.activity.main.event.GPSEvent;
import com.fdl.activity.set.ProtocolActivity;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.activity.supermarket.StoreListActivity;
import com.fdl.adapter.MainProductAdapter;
import com.fdl.adapter.MenuAdapter;
import com.fdl.bean.AreasBean;
import com.fdl.bean.BannerBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.MainProcuctBean;
import com.fdl.bean.MenuBean;
import com.fdl.bean.daoBean.AearBean;
import com.fdl.bean.daoBean.BannerData;
import com.fdl.bean.daoBean.BannerData1;
import com.fdl.bean.daoBean.MainProductBean;
import com.fdl.bean.daoBean.Tipsbean;
import com.fdl.db.DBManager;
import com.fdl.db.ShopTypeEnum;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.updata.UpdateAppHttpUtil;
import com.fdl.utils.BaiduMapUtils;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;
import com.sg.cj.snh.uitls.GlideImageLoader;
import com.snh.greendao.AearBeanDao;
import com.snh.greendao.BannerData1Dao;
import com.snh.greendao.BannerDataDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;
import com.snh.greendao.MainProductBeanDao;
import com.snh.greendao.TipsbeanDao;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.utils.AppUpdateUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.weyye.hipermission.PermissionItem;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/12<p>
 * <p>changeTime：2019/2/12<p>
 * <p>version：1<p>
 */
public class MainFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, DialogUtils.AddressChose, DialogUtils.AddressQg {

    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.layout_location)
    LinearLayout layoutLocation;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    @BindView(R.id.rl_head)
    LinearLayout llTop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_msg)
    LinearLayout llMsg;

    private MainProductAdapter adapter;
    private LayoutInflater inflater = null;

    //banner广告图
    private LinearLayout heardBanner;
    private Banner banner;

    //菜单栏
    private LinearLayout heardMenu;
    private RecyclerView menuRecycleview;
    private MenuAdapter menuAdapter;

    //滚动消息栏
    private LinearLayout heardTip;
    private ViewFlipper vfView;
    //广告栏
    private LinearLayout heardBanner1;
    private ImageView ivRight, ivTop, ivButtom, ivLeft;

    private LinearLayout heardTitle;

    private MenuBean menuBean;
    private List<MenuBean> menuBeanList = new ArrayList<>();
    private List<String> menuStrList = new ArrayList<>();
    private List<Integer> imgList = new ArrayList<>();
    private List<String> bannerUrls = new ArrayList<>();
    private List<String> banner1Urls = new ArrayList<>();
    private List<String> tipStrs = new ArrayList<>();
    private List<MainProcuctBean> listData = new ArrayList<>();

    private BannerBean bannerBean;
    private Map<String, String> params;
    private Bundle bundle;
    private int index = 1;

    private String district, local;
    private DialogUtils dialogUtils;
    DaoMaster daoMaster;
    DaoSession daoSession;
    private boolean isShow = true;

    @Override
    public int initContentView() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_main_layout;
    }

    @Override
    public void setUpViews(View view) {
        llMsg.getBackground().mutate().setAlpha(155);
        layoutLocation.getBackground().mutate().setAlpha(155);
        daoMaster = new DaoMaster(DBManager.getInstance(getContext()).getWritableDatabase());
        daoSession = daoMaster.newSession();
//        if (PartyApp.getAppComponent().getDataManager().getId() != 0) {
//            imLoging(SPUtils.getInstance(getContext()).getString(Contans.IM_ACCOUNT), SPUtils.getInstance(getContext()).getString(Contans.IM_TOKEN), getContext());
//        }
        updataVersion();
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogUtils = new DialogUtils(getContext());
        if (ImmersionBar.hasNotchScreen(getActivity())) {
            IsBang.setImmerHeard(getContext(), llTop);
        } else {
            ImmersionBar.with(this).titleBar(llTop).init();
        }

        setBanner();
        setMenu();
        setTip();
        setBanner1();
        setRecyclerView();
        district = SPUtils.getInstance(getContext()).getString(Contans.LAST_CITY_ID);
        loadingCache();
        if (!BaiduMapUtils.isOPenGPS(getContext())) {
            dialogUtils.twoBtnDialog("为了提高定位的准确度，更好的为您服务，请打开GPS", new DialogUtils.ChoseClickLisener() {
                @Override
                public void onConfirmClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    //设置完成后返回原来的界面
                    getActivity().startActivityForResult(intent,101);
                    dialogUtils.dismissDialog();
                }

                @Override
                public void onCancelClick(View v) {
                    dialogUtils.dismissDialog();
                }
            }, true);
        }
        if (isNetworkConnected(getContext())) {
            checkPerm();
        } else {
            dialogUtils.noBtnDialog("请打开网络连接");
            SPUtils.getInstance(getContext()).saveData(Contans.CITY, "");
        }
    }
    @Subscribe
    public void gPSEvent(GPSEvent event) {
        if (event.isOpen()) {
            checkPerm();
        }
    }

    private void setBanner() {
        heardBanner = (LinearLayout) inflater.inflate(R.layout.heard_banner_layout, null);
        banner = (Banner) heardBanner.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                jump(bannerBean.banner.get(position));
            }
        });
    }

    private int shopType;
    private String title;

    private void setMenu() {
        menuStrList.clear();
        imgList.clear();
        menuBeanList.clear();
//        menuStrList.add("分类");
//        menuStrList.add("FORANY");
        menuStrList.add("商家入驻");
        menuStrList.add("美食");
        menuStrList.add("娱乐休闲");
        menuStrList.add("逛街吧");
        menuStrList.add("酒店公寓");
        menuStrList.add("地方名产");
        menuStrList.add("商超士多");
        menuStrList.add("家装建材");
//        imgList.add(R.drawable.img_fl);
//        imgList.add(R.drawable.img_forany);
        imgList.add(R.drawable.img_sjrz);
        imgList.add(R.drawable.img_msfx);
        imgList.add(R.drawable.img_xxyl);
        imgList.add(R.drawable.img_gjb);
        imgList.add(R.drawable.img_jdgy);
        imgList.add(R.drawable.img_dfmc);
        imgList.add(R.drawable.img_scsd);
        imgList.add(R.drawable.img_jzjc);

        for (int i = 0; i < menuStrList.size(); i++) {
            menuBean = new MenuBean();
            menuBean.name = menuStrList.get(i);
            menuBean.imgRes = imgList.get(i);
            menuBeanList.add(menuBean);
        }
        heardMenu = (LinearLayout) inflater.inflate(R.layout.heard_menu_layout, null);
        menuRecycleview = (RecyclerView) heardMenu.findViewById(R.id.heard_menu_recycleview);
        menuAdapter = new MenuAdapter(R.layout.item_menu_layout, menuBeanList);
        menuRecycleview.setLayoutManager(new GridLayoutManager(getContext(), 4));
        menuRecycleview.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                switch (position) {
                    case 0:
//                        JumpUtils.simpJump(getActivity(), LogingActivity.class, false);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        /**知道要跳转应用的包命与目标Activity*/
                        try {
                            ComponentName componentName = new ComponentName("com.snh.snhseller", "com.snh.snhseller.ui.merchantEntry.MerchantLogingActivity");
                            intent.setComponent(componentName);
                            intent.putExtra("", "");//这里Intent传值
                            startActivity(intent);
                        } catch (Exception e) {
                            dialogUtils.twoBtnDialog("未检测到商家管理APP,是否前往下载？", new DialogUtils.ChoseClickLisener() {
                                @Override
                                public void onConfirmClick(View v) {
                                    goToMarket(getContext(), "com.snh.snhseller");
                                    dialogUtils.dismissDialog();
                                }

                                @Override
                                public void onCancelClick(View v) {
                                    dialogUtils.dismissDialog();
                                }
                            }, true);
                        }

                        break;
                    case 1:
                        shopType = ShopTypeEnum.TYPE1.getValue();
                        type = 1;
                        title = "美食分享";
//                        JumpUtils.simpJump(getActivity(),OrderDetailsActivity.class,true);
                        checkPerm();
                        break;
                    case 2:
                        shopType = ShopTypeEnum.TYPE2.getValue();
                        type = 1;
                        title = "娱乐休闲";
                        checkPerm();
                        break;
                    case 3:
                        shopType = ShopTypeEnum.TYPE3.getValue();
                        type = 1;
                        title = "逛街吧";
                        checkPerm();
                        break;
                    case 4:
                        shopType = ShopTypeEnum.TYPE4.getValue();
                        type = 1;
                        title = "酒店公寓";
                        checkPerm();
                        break;
                    case 5:
                        shopType = ShopTypeEnum.TYPE5.getValue();
                        type = 1;
                        title = "地方名产";
                        checkPerm();
                        break;
                    case 6:
                        shopType = ShopTypeEnum.TYPE6.getValue();
                        type = 1;
                        title = "商超士多";
                        checkPerm();
                        break;
                    case 7:
                        shopType = ShopTypeEnum.TYPE7.getValue();
                        type = 1;
                        title = "家装建材";
                        checkPerm();
                        break;
                }
            }
        });
    }

    private void setTip() {
        heardTip = (LinearLayout) inflater.inflate(R.layout.heard_tip1_layout, null);
        vfView = (ViewFlipper) heardTip.findViewById(R.id.vf);
    }

    private void setBanner1() {
        heardBanner1 = (LinearLayout) inflater.inflate(R.layout.heard_banner1_layout, null);
        ivLeft = (ImageView) heardBanner1.findViewById(R.id.img_main_left);
        ivTop = (ImageView) heardBanner1.findViewById(R.id.img_main_top);
        ivButtom = (ImageView) heardBanner1.findViewById(R.id.img_main_buttom);
        ivRight = (ImageView) heardBanner1.findViewById(R.id.img_main_right);
        ivLeft.setEnabled(false);
        ivTop.setEnabled(false);
        ivButtom.setEnabled(false);
        ivRight.setEnabled(false);
        ivRight.setOnClickListener(this::onUserClick);
        ivLeft.setOnClickListener(this::onUserClick);
        ivTop.setOnClickListener(this::onUserClick);
        ivButtom.setOnClickListener(this::onUserClick);
    }

    private int scrollY = 0;

    private void setRecyclerView() {
        heardTitle = (LinearLayout) inflater.inflate(R.layout.heard_title_layout, null);
        adapter = new MainProductAdapter(R.layout.item_main_layout, null);
        adapter.addHeaderView(heardBanner);
        adapter.addHeaderView(heardMenu);
        adapter.addHeaderView(heardTip);
        adapter.addHeaderView(heardBanner1);
        adapter.addHeaderView(heardTitle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                if (scrollY > (banner.getHeight() - ImmersionBar.getStatusBarHeight(getActivity()))) {
                    llTop.getBackground().mutate().setAlpha(255);
                    llMsg.getBackground().mutate().setAlpha(0);
                    layoutLocation.getBackground().mutate().setAlpha(0);
                } else {
                    llTop.getBackground().mutate().setAlpha(0);
                    llMsg.getBackground().mutate().setAlpha(155);
                    layoutLocation.getBackground().mutate().setAlpha(155);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bundle = new Bundle();
                if (("6").equals(listData.get(position).ShopType)) {
                    bundle.putInt("stroeId", listData.get(position).SupplierId);
                    bundle.putInt("goodsId", listData.get(position).Id);
                    JumpUtils.dataJump(getActivity(), StoreDetailsActivity.class, bundle, false);
                } else {
                    bundle.putInt("goodsId", listData.get(position).Id);
                    JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
                }
            }
        });

    }

    private void getBannerData() {
        banner.stopAutoPlay();
        addSubscription(RequestClient.GetBannerData(district, getContext(), new NetSubscriber<BaseResultBean<BannerBean>>(getContext()) {
            @Override
            public void onResultNext(BaseResultBean<BannerBean> model) {
                bannerBean = model.data;
                banner1Urls.clear();
                bannerUrls.clear();
                tipStrs.clear();
                if (model.data.banner != null && model.data.banner.size() > 0) {
                    for (int i = 0; i < model.data.banner.size(); i++) {
                        bannerUrls.add(model.data.banner.get(i).ImageUrl);
                    }
                }
                if (model.data.fours != null && model.data.fours.size() > 0) {
                    for (int i = 0; i < model.data.fours.size(); i++) {
                        banner1Urls.add(model.data.fours.get(i).ImageUrl);
                    }
                }
                if (model.data.localsub != null && model.data.localsub.size() > 0) {
                    for (int i = 0; i < model.data.localsub.size(); i++) {
                        tipStrs.add(model.data.localsub.get(i).Title);
                    }
                }
                setViewData();
                getProductData();
                SPUtils.getInstance(getContext()).saveData(Contans.DISTRICT, district);
            }
        }));
    }

    private void getProductData() {
        if ("全国".equals(district)) {
            addSubscription(RequestClient.GetData(index, "", getContext(), new NetSubscriber<BaseResultBean<List<MainProcuctBean>>>(getContext(), isShow) {
                @Override
                public void onResultNext(BaseResultBean<List<MainProcuctBean>> model) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    if (index == 1) {
                        if (model.data.size() > 0) {
                            listData.clear();
                            isShow = false;
                            listData = model.data;
                            adapter.setNewData(listData);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    saveCache(bannerBean);
                                }
                            }).start();
                            banner.start();
                        }
                    } else {
                        if (model.data.size() > 0) {
                            listData.addAll(model.data);
                            adapter.setNewData(listData);
//                            adapter.setNewData(listData);
                        } else {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        }

                    }
                }
            }));
        } else {
            addSubscription(RequestClient.Localgoodsdata(index, district, "", getContext(), new NetSubscriber<BaseResultBean<List<MainProcuctBean>>>(getContext(), isShow) {
                @Override
                public void onResultNext(BaseResultBean<List<MainProcuctBean>> model) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    if (index == 1) {
                        if (model.data.size() > 0) {
                            isShow = false;
                            listData.clear();
                            listData = model.data;
                            adapter.setNewData(model.data);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    saveCache(bannerBean);
                                }
                            }).start();
                        }
                        banner.start();
                    } else {
                        if (model.data.size() > 0) {
                            listData.addAll(model.data);
                            adapter.setNewData(listData);
//                            adapter.addData(model.data);
                        } else {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        }
                    }

                }
            }));
        }

    }

    private void requestPermision() {

        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
        if (!EasyPermissions.hasPermissions(getContext(), permissions)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 10010);
            }
        } else {
            initLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10010) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        district = "全国";
                        address = "全国";
                        txtLocation.setText(address);
                        SPUtils.getInstance(getContext()).saveData(Contans.CITY, local);
                        SPUtils.getInstance(getContext()).saveData(Contans.CITY_ID, district);
                        getBannerData();
                        Toast.makeText(getContext(), "请前往系统设置开启位置权限", Toast.LENGTH_SHORT).show();
                    } else {
//                        initLocation();
                    }
                } else {
                    initLocation();
                }
            }
        }
    }

    @Override
    public void setUpLisener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                getBannerData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index += 1;
                getProductData();
            }
        });
    }

    @Override
    public void onUserClick(View v) {
        super.onUserClick(v);
        if (null == bannerBean.fours) {
            return;
        }
        switch (v.getId()) {
            case R.id.img_main_left:
                jump(bannerBean.fours.get(0));
                break;
            case R.id.img_main_top:
                jump(bannerBean.fours.get(1));
                break;
            case R.id.img_main_buttom:
                jump(bannerBean.fours.get(2));
                break;
            case R.id.img_main_right:
                jump(bannerBean.fours.get(3));
                break;
        }
    }

    private void jump(BannerBean.BannerData1 bean) {
        bundle = new Bundle();
        switch (bean.Type) {
            //店铺
            case 1:
                if (bean.BusinessActivities == 1) {
                    bundle.putInt("stroeId", bean.SupplierId);
                    JumpUtils.dataJump(getActivity(), StoreDetailsActivity.class, bundle, false);
                } else {
                    bundle.putInt("stroeId", bean.SupplierId);
                    JumpUtils.dataJump(getActivity(), ShopDetailsActivity.class, bundle, false);
                }
//            JumpUtils.dataJump(getActivity(), GoodsDetailsActivity.class, bundle, false);
                break;
            //商品
            case 2:
                if (bean.BusinessActivities == 1) {
                    bundle.putInt("stroeId", bean.SupplierId);
                    bundle.putInt("goodsId", bean.GoodsId);
                    JumpUtils.dataJump(getActivity(), StoreDetailsActivity.class, bundle, false);
                } else {
                    bundle.putInt("stroeId", bean.SupplierId);
                    JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
                }
                break;
            //h5
            case 3:
                bundle.putString("url", bean.Href);
                JumpUtils.dataJump(getActivity(), ProtocolActivity.class, bundle, false);
                break;
        }
    }

    private void jump(BannerBean.BannerData bean) {
//        params = UrlUtils.getParameters(str);
        bundle = new Bundle();
        switch (bean.Type) {
            //店铺
            case 1:
                if (bean.BusinessActivities == 1) {
                    bundle.putInt("stroeId", bean.SupplierId);
                    JumpUtils.dataJump(getActivity(), StoreDetailsActivity.class, bundle, false);
                } else {
                    bundle.putInt("stroeId", bean.SupplierId);
                    JumpUtils.dataJump(getActivity(), ShopDetailsActivity.class, bundle, false);
                }
//            JumpUtils.dataJump(getActivity(), GoodsDetailsActivity.class, bundle, false);
                break;
            //商品
            case 2:
                if (bean.BusinessActivities == 1) {
                    bundle.putInt("stroeId", bean.SupplierId);
                    bundle.putInt("goodsId", bean.GoodsId);
                    JumpUtils.dataJump(getActivity(), StoreDetailsActivity.class, bundle, false);
                } else {
                    bundle.putInt("stroeId", bean.SupplierId);
                    JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
                }
                break;
            //h5
            case 3:
                bundle.putString("url", bean.Href);
                JumpUtils.dataJump(getActivity(), ProtocolActivity.class, bundle, false);
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        district = "全国";
        setCacheData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        if (null != mLocationClient) {
            mLocationClient.stop();
        }
    }


    private int type = 0;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private void checkPerm() {

        if (EasyPermissions.hasPermissions(getContext(), permissions)) {
            if (type == 1) {
                bundle = new Bundle();
                bundle.putString("addressLevel", level);
                bundle.putString("addressId", addressId);
                bundle.putInt("type", shopType);
                bundle.putString("title", title);
                if ("全国".equals(district) | StrUtils.isEmpty(district)) {
                    dialogUtils.noBtnDialog("请选择地区");
                } else {
                    JumpUtils.dataJump(getActivity(), StoreListActivity.class, bundle, false);
                }
            } else if (type == 2) {
                dialogUtils.AddressDialog(this, this);
            } else {
                initLocation();
            }
        } else {
            requestPermision();
        }
    }

    private boolean cityPickerLocation = false;
    public LocationClient mLocationClient = null;
    private double latitude;
    private double longitude;
    private String province;
    private String city;
    AearBean aearBean1;
    AearBeanDao aearBeanDao;
    private String address;

    private void initLocation() {
        aearBeanDao = daoSession.getAearBeanDao();
        mLocationClient = new LocationClient(getContext().getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (null != bdLocation) {
                    bdLocation.setLocationWhere(BDLocation.LOCATION_WHERE_IN_CN);
                    latitude = bdLocation.getLatitude();
                    longitude = bdLocation.getLongitude();
                    city = bdLocation.getAddress().city;
                    province = bdLocation.getAddress().province;
                    String district1 = getMyDistrict(latitude, longitude);
                    SgLog.d(district1 + "   " + city + "   " + province);
                    local = district1; //最后一次选择的位置
                    String locaStr = SPUtils.getInstance(getContext()).getString(Contans.LAST_CITY);
                    SgLog.d("locaStr:" + locaStr);
                    mLocationClient.stop();
                    if (!StrUtils.isEmpty(SPUtils.getInstance(getContext()).getString(Contans.LAST_CITY)) && !StrUtils.isEmpty(district1)) {
                        if (txtLocation.getText().toString().trim().equals(district1)) {
                            setAddress();
                            SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY, local);
                            SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY_ID, district);
                        } else {
                            dialogUtils.twoBtnDialog("是否切换到当前地理位置（" + district1 + "）？", new DialogUtils.ChoseClickLisener() {
                                @Override
                                public void onConfirmClick(View v) {
                                    setAddress();
                                    SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY, local);
                                    SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY_ID, district);
                                }

                                @Override
                                public void onCancelClick(View v) {
                                    dialogUtils.dismissDialog();
//                                    district = SPUtils.getInstance(getContext()).getString(Contans.LAST_CITY_ID);
                                    level = SPUtils.getInstance(getContext()).getString(Contans.LEVEL);
                                    addressId = SPUtils.getInstance(getContext()).getString(Contans.ADDRESS_ID);
                                    district = SPUtils.getInstance(getContext()).getString(Contans.LAST_CITY_ID);
                                    getBannerData();
                                }
                            }, false);
                        }
                    }
                    SPUtils.getInstance(getContext()).saveData(Contans.CITY, local);
                    SPUtils.getInstance(getContext()).saveData(Contans.LATITUDE, latitude + "");
                    SPUtils.getInstance(getContext()).saveData(Contans.LONGITUDE, longitude + "");
                    SPUtils.getInstance(getContext()).saveData(Contans.PROVINCE, province);
                    try {
                        if (StrUtils.isEmpty(district)) {
                            if (!SPUtils.getInstance(getContext()).getBoolean(Contans.ADDRESS)) {
                                dialogUtils.initJson();
                            }
                        }
                        AearBean aearProvince = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.AddressName.eq(province)).unique();
                        AearBean aearCity = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aearProvince.id), AearBeanDao.Properties.AddressName.eq(city)).unique();
                        aearBean1 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aearCity.id), AearBeanDao.Properties.AddressName.eq(local)).unique();
                        district = aearBean1.id;
                        SPUtils.getInstance(getContext()).saveData(Contans.CITY_LEVEL, aearBean1.Level);
                        SPUtils.getInstance(getContext()).saveData(Contans.CITY_ID, aearBean1.id);

                    } catch (Exception e) {

                    }
                }

            }
        });
        //注册监听函数

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        cityPickerLocation = false;
        mLocationClient.start();
    }

    //百度地图 获取的区县位置没有更新，所以采用Android 系统自带
    private String getMyDistrict(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> mAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!mAddresses.isEmpty()) {
                Address address = mAddresses.get(0);
                SgLog.d("手机定位：" + address.getAdminArea() + "  " + address.getLocality() + "  " + address.getCountryName() + "  " + address.getSubLocality());
                return address.getSubLocality();
            }
        } catch (IOException e) {
            Toast.makeText(getContext(), "定位失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    private void setAddress() {
        try {
            if (StrUtils.isEmpty(district)) {
                if (!SPUtils.getInstance(getContext()).getBoolean(Contans.ADDRESS)) {
                    dialogUtils.initJson();
                }
            }
            AearBean aearProvince = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.AddressName.eq(province)).unique();
            AearBean aearCity = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aearProvince.id), AearBeanDao.Properties.AddressName.eq(city)).unique();
            aearBean1 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aearCity.id), AearBeanDao.Properties.AddressName.eq(local)).unique();
            district = aearBean1.id;
            SPUtils.getInstance(getContext()).saveData(Contans.LEVEL, aearBean1.Level);
            SPUtils.getInstance(getContext()).saveData(Contans.ADDRESS_ID, aearBean1.id);
            level = aearBean1.Level;
            addressId = aearBean1.id;

            address = aearBean1.AddressName;
            txtLocation.setText(address);
            AddressLocationEvent event = new AddressLocationEvent();
            event.setLocation(true);
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            district = "全国";
            address = "全国";
            txtLocation.setText(address);
        }
        SPUtils.getInstance(getContext()).saveData(Contans.CITY, local);
        SPUtils.getInstance(getContext()).saveData(Contans.CITY_ID, district);
        dialogUtils.dismissDialog();
        getBannerData();
    }

    @OnClick({R.id.layout_location, R.id.layout_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_location:
                dialogUtils.AddressDialog(this, this);
                break;
            case R.id.layout_search:
                bundle = new Bundle();
                bundle.putInt("type", 1);
                if (district.equals("全国")) {
                    bundle.putInt("AreaId", 0);
                } else {
                    bundle.putInt("AreaId", Integer.parseInt(district));
                }
                JumpUtils.dataJump(getActivity(), SearchActivity.class, bundle, false);
                break;
        }

    }


    private void saveCache(BannerBean bannerBean) {
        TipsbeanDao tipsbeanDao = daoSession.getTipsbeanDao();
        Tipsbean tips = new Tipsbean();
        tipsbeanDao.deleteAll();
        for (int i = 0; i < tipStrs.size(); i++) {
            tips.Title = tipStrs.get(i);
            tipsbeanDao.insert(tips);
        }
        BannerDataDao bannerDataDao = daoSession.getBannerDataDao();
        BannerData bannerData = new BannerData();
        bannerDataDao.deleteAll();
        for (int i = 0; i < bannerBean.banner.size(); i++) {
            bannerData.Id = bannerBean.banner.get(i).Id;
            bannerData.AdminId = bannerBean.banner.get(i).AdminId;
            bannerData.Audit = bannerBean.banner.get(i).Audit;
            bannerData.Default = bannerBean.banner.get(i).Default;
            bannerData.Href = bannerBean.banner.get(i).Href;
            bannerData.ImageUrl = bannerBean.banner.get(i).ImageUrl;
            bannerData.OrderBy = bannerBean.banner.get(i).OrderBy;
            bannerDataDao.insert(bannerData);
        }
        BannerData1Dao bannerData1Dao = daoSession.getBannerData1Dao();
        BannerData1 bannerData1 = new BannerData1();
        bannerData1Dao.deleteAll();
        for (int i = 0; i < bannerBean.fours.size(); i++) {
            bannerData1.Id = bannerBean.fours.get(i).Id;
            bannerData1.AdminId = bannerBean.fours.get(i).AdminId;
            bannerData1.Audit = bannerBean.fours.get(i).Audit;
            bannerData1.Default = bannerBean.fours.get(i).Default;
            bannerData1.Href = bannerBean.fours.get(i).Href;
            bannerData1.ImageUrl = bannerBean.fours.get(i).ImageUrl;
            bannerData1.OrderBy = bannerBean.fours.get(i).OrderBy;
            bannerData1Dao.insert(bannerData1);
        }
        MainProductBeanDao productBeanDao = daoSession.getMainProductBeanDao();
        MainProductBean productBean = new MainProductBean();
        productBeanDao.deleteAll();
        for (int i = 0; i < listData.size(); i++) {
            productBean.Id = listData.get(i).Id;
            productBean.DetailUrl = listData.get(i).DetailUrl;
            productBean.GoodsImg = listData.get(i).GoodsImg;
            productBean.MarketNumber = listData.get(i).MarketNumber;
            productBean.MarketPrice = listData.get(i).MarketPrice;
            productBean.Name = listData.get(i).Name;
            productBean.SalesPrice = listData.get(i).SalesPrice;
            productBeanDao.insert(productBean);
        }
    }

    private void loadingCache() {
        try {
            if (!StrUtils.isEmpty(SPUtils.getInstance(getContext()).getString(Contans.LAST_CITY))) {
                txtLocation.setText(SPUtils.getInstance(getContext()).getString(Contans.LAST_CITY));
            }
            TipsbeanDao tipsbeanDao = daoSession.getTipsbeanDao();
            List<Tipsbean> tipList = tipsbeanDao.queryBuilder().list();

            //轮播banner
            BannerDataDao bannerDataDao = daoSession.getBannerDataDao();
            List<BannerData> bannerDataList = bannerDataDao.queryBuilder().list();

            //固定四张banner
            BannerData1Dao fours = daoSession.getBannerData1Dao();
            List<BannerData1> foursBanner = fours.queryBuilder().list();

            //产品数据
            MainProductBeanDao productBeanDao = daoSession.getMainProductBeanDao();
            List<MainProductBean> productBean = productBeanDao.queryBuilder().list();
            if (tipList.size() > 0) {
                for (int i = 0; i < tipList.size(); i++) {
                    tipStrs.add(tipList.get(i).Title);
                }
                bannerBean = new BannerBean();
                BannerBean.BannerData bannerDataBean;
                List<BannerBean.BannerData> bannerList = new ArrayList<>();
                bannerUrls.clear();
                for (int i = 0; i < bannerDataList.size(); i++) {
                    bannerDataBean = bannerBean.new BannerData();
                    bannerDataBean.Id = bannerDataList.get(i).Id;
                    bannerDataBean.Href = bannerDataList.get(i).Href;
                    bannerDataBean.AdminId = bannerDataList.get(i).AdminId;
                    bannerDataBean.Audit = bannerDataList.get(i).Audit;
                    bannerDataBean.Default = bannerDataList.get(i).Default;
                    bannerDataBean.ImageUrl = bannerDataList.get(i).ImageUrl;
                    bannerDataBean.OrderBy = bannerDataList.get(i).OrderBy;
                    bannerList.add(bannerDataBean);
                    bannerUrls.add(bannerDataList.get(i).ImageUrl);
                }
                bannerBean.banner = bannerList;

                BannerBean.BannerData1 bannerData1;
                List<BannerBean.BannerData1> bannerList1 = new ArrayList<>();
                banner1Urls.clear();
                for (int i = 0; i < foursBanner.size(); i++) {
                    bannerData1 = bannerBean.new BannerData1();
                    bannerData1.Id = foursBanner.get(i).Id;
                    bannerData1.Href = foursBanner.get(i).Href;
                    bannerData1.AdminId = foursBanner.get(i).AdminId;
                    bannerData1.Audit = foursBanner.get(i).Audit;
                    bannerData1.Default = foursBanner.get(i).Default;
                    bannerData1.ImageUrl = foursBanner.get(i).ImageUrl;
                    bannerData1.OrderBy = foursBanner.get(i).OrderBy;
                    bannerList1.add(bannerData1);
                    banner1Urls.add(foursBanner.get(i).ImageUrl);
                }
                bannerBean.fours = bannerList1;


                MainProcuctBean mainBean;
                listData.clear();
                for (int i = 0; i < productBean.size(); i++) {
                    mainBean = new MainProcuctBean();
                    mainBean.Id = productBean.get(i).Id;
                    mainBean.DetailUrl = productBean.get(i).DetailUrl;
                    mainBean.GoodsImg = productBean.get(i).GoodsImg;
                    mainBean.MarketNumber = productBean.get(i).MarketNumber;
                    mainBean.MarketPrice = productBean.get(i).MarketPrice;
                    mainBean.Name = productBean.get(i).Name;
                    mainBean.SalesPrice = productBean.get(i).SalesPrice;
                    listData.add(mainBean);
                }

                setViewData();
                adapter.setNewData(listData);
                banner.start();
                banner.stopAutoPlay();
            }
            getBannerData();
        } catch (Exception e) {
            getBannerData();
        }

    }

    private void setCacheData() {
        if (!StrUtils.isEmpty(SPUtils.getInstance(getContext()).getString(Contans.DISTRICT))) {
            loadingCache();
        } else {
            txtLocation.setText(address);
        }
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    private void setViewData() {
        banner.setImages(bannerUrls);
        vfView.setInAnimation(getContext(), R.anim.notice_in);
        vfView.setOutAnimation(getContext(), R.anim.notice_out);
        for (int i = 0; i < tipStrs.size(); i++) {
            View v = (getActivity()).getLayoutInflater().inflate(R.layout.item_tip_layout, null);
            TextView titleTv = (TextView) v.findViewById(R.id.tv_tip);
            titleTv.setText(tipStrs.get(i));
            vfView.addView(v);
        }
        vfView.startFlipping();

        if (banner1Urls.size() == 1) {
            ImageUtils.loadUrlImage1(getContext(), banner1Urls.get(0), ivLeft);
            ivLeft.setEnabled(true);
            ivTop.setEnabled(false);
            ivButtom.setEnabled(false);
            ivRight.setEnabled(false);
        }
        if (banner1Urls.size() == 2) {
            ImageUtils.loadUrlImage1(getContext(), banner1Urls.get(0), ivLeft);
            ImageUtils.loadUrlImage(getContext(), banner1Urls.get(1), ivTop);
            ivLeft.setEnabled(true);
            ivTop.setEnabled(true);
            ivButtom.setEnabled(false);
            ivRight.setEnabled(false);
        }

        if (banner1Urls.size() == 3) {
            ImageUtils.loadUrlImage1(getContext(), banner1Urls.get(0), ivLeft);
            ImageUtils.loadUrlImage(getContext(), banner1Urls.get(1), ivTop);
            ImageUtils.loadUrlImage(getContext(), banner1Urls.get(2), ivButtom);
            ivLeft.setEnabled(true);
            ivTop.setEnabled(true);
            ivButtom.setEnabled(true);
            ivRight.setEnabled(false);
        }
        if (banner1Urls.size() == 4) {
            ImageUtils.loadUrlImage1(getContext(), banner1Urls.get(0), ivLeft);
            ImageUtils.loadUrlImage(getContext(), banner1Urls.get(1), ivTop);
            ImageUtils.loadUrlImage(getContext(), banner1Urls.get(2), ivButtom);
            ImageUtils.loadUrlImage1(getContext(), banner1Urls.get(3), ivRight);
            ivLeft.setEnabled(true);
            ivTop.setEnabled(true);
            ivButtom.setEnabled(true);
            ivRight.setEnabled(true);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void updataVersion() {
        new UpdateAppManager.Builder()
                .setActivity(getActivity())
                .setHttpManager(new UpdateAppHttpUtil())
                .setUpdateUrl(Contans.HOST + "webapi/Version/GetVersion?Channel=1&SourceSystem=1")
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                .hideDialogOnDownloading()
                .build()
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        String isUpdata = "No";
                        try {
                            JSONObject jsonObject = JSONObject.parseObject(json);
                            JSONObject data = jsonObject.getJSONObject("data");

                            if (AppUpdateUtils.getVersionCode(getContext()) < Integer.parseInt(data.getString("VersionCode"))) {
                                isUpdata = "Yes";
                            }
                            updateAppBean
                                    //是否更新Yes,No
                                    .setUpdate(isUpdata)
                                    //新版本号
                                    .setNewVersion(data.getString("VersionName"))
                                    //下载地址
                                    .setApkFileUrl(data.getString("DownloadPath"))
                                    //更新内容
                                    .setUpdateLog(jsonObject.getString("Describe"))
                                    //是否强制更新
                                    .setConstraint(data.getBoolean("IsCompel"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    /**
                     * 有新版本
                     *
                     * @param updateApp        新版本信息
                     * @param updateAppManager app更新管理器
                     */
                    @Override
                    public void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                    }

                    @Override
                    protected void noNewApp(String error) {
                        super.noNewApp(error);
                    }
                });
    }

    @Override
    public void onAddressChose(AreasBean bean) {

        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY, bean.AddressName);
        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY_ID, bean.id);
        SPUtils.getInstance(getContext()).saveData(Contans.LATELY_CITY, bean.AddressName);
        SPUtils.getInstance(getContext()).saveData(Contans.LATELY_CITY_ID, district);
        SPUtils.getInstance(getContext()).saveData(Contans.LATELY_LEVEL, bean.Level);
        SPUtils.getInstance(getContext()).saveData(Contans.LATELY_ADDRESS_ID, bean.id);
        SPUtils.getInstance(getContext()).saveData(Contans.LEVEL, bean.Level);
        SPUtils.getInstance(getContext()).saveData(Contans.ADDRESS_ID, bean.id);
        addressId = bean.id;
        level = bean.Level;
        llTop.setBackgroundColor(Color.TRANSPARENT);
        llTop.getBackground().mutate().setAlpha(0);
        district = bean.id;
        txtLocation.setText(bean.AddressName);
        index = 1;
        isShow = true;
        getBannerData();
    }

    private String addressId;
    private String level;

    @Override
    public void onAddressChose(String id, String AddressName, String level) {
        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY, AddressName);
        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY_ID, id);
        addressId = SPUtils.getInstance(getContext()).getString(Contans.ADDRESS_ID);
        SPUtils.getInstance(getContext()).saveData(Contans.LEVEL, level);
        SPUtils.getInstance(getContext()).saveData(Contans.ADDRESS_ID, id);
        llTop.setBackgroundColor(Color.TRANSPARENT);
        llTop.getBackground().mutate().setAlpha(0);
        district = id;
        addressId = id;
        this.level = level;
        txtLocation.setText(AddressName);
        index = 1;
        isShow = true;
        getBannerData();
    }

    @Override
    public void onLatylyChose(String id, String AddressName, String level) {
        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY_ID, id);
        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY, AddressName);
        addressId = SPUtils.getInstance(getContext()).getString(Contans.LATELY_ADDRESS_ID);
        SPUtils.getInstance(getContext()).saveData(Contans.LEVEL, level);
        SPUtils.getInstance(getContext()).saveData(Contans.ADDRESS_ID, id);
        llTop.setBackgroundColor(Color.TRANSPARENT);
        llTop.getBackground().mutate().setAlpha(0);
        district = id;
        addressId = id;
        this.level = level;
        txtLocation.setText(AddressName);
        index = 1;
        isShow = true;
        getBannerData();
    }

    @OnClick(R.id.ll_msg)
    public void onClick() {
        if (isLogin()) {
            JumpUtils.simpJump(getActivity(), MsgActivity.class, false);
        } else {
            Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
        }
    }

    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddressQg(String address) {
        district = "全国";
        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY, "全国");
        SPUtils.getInstance(getContext()).saveData(Contans.LAST_CITY_ID, "全国");
        SPUtils.getInstance(getContext()).saveData(Contans.LATELY_CITY, "全国");
        SPUtils.getInstance(getContext()).saveData(Contans.LATELY_CITY_ID, "全国");
        txtLocation.setText("全国");
        index = 1;
        isShow = true;
        getBannerData();
    }


}
