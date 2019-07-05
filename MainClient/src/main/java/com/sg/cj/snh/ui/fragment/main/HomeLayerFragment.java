package com.sg.cj.snh.ui.fragment.main;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.activity.main.MainActivity;
import com.fdl.activity.merchantEntry.LogingActivity;
import com.fdl.activity.supermarket.StoreListActivity;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.UrlUtils;
import com.github.promeg.pinyinhelper.Pinyin;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.sg.cj.common.base.adapter.SgQuickAdapter;
import com.sg.cj.common.base.adapter.ViewHolder;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.SgUtils;
import com.sg.cj.common.base.view.SgHeaderGridView;
import com.sg.cj.snh.R;
import com.sg.cj.snh.adaper.MySgQuickAdapter;
import com.sg.cj.snh.base.BaseFragment;
import com.sg.cj.snh.bean.CityBean;
import com.sg.cj.snh.bean.MainGridBean;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.main.MainHomeContract;
import com.sg.cj.snh.model.response.main.LocalAdResponse;
import com.sg.cj.snh.model.response.main.LocalgoodsdataResponse;
import com.sg.cj.snh.presenter.main.MainHomePresenter;
import com.sg.cj.snh.uitls.GlideImageLoader;
import com.sg.cj.snh.uitls.ImmersionOwner;
import com.sg.cj.snh.uitls.ImmersionProxy;
import com.sg.cj.snh.uitls.MenuTools;
import com.sg.cj.snh.view.ScrollViewGroup;
import com.sg.cj.snh.view.VerticalScrollTextView;
import com.sg.cj.snh.view.citypicker.CityPicker;
import com.sg.cj.snh.view.citypicker.adapter.OnPickListener;
import com.sg.cj.snh.view.citypicker.model.City;
import com.sg.cj.snh.view.citypicker.model.HotCity;
import com.sg.cj.snh.view.citypicker.model.LocateState;
import com.sg.cj.snh.view.citypicker.model.LocatedCity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * author : ${CHENJIE}
 * created at  2018/10/29 10:15
 * e_mail : chenjie_goodboy@163.com
 * describle : 首页
 */
@RuntimePermissions
public class HomeLayerFragment extends BaseFragment<MainHomePresenter> implements MainHomeContract.View, ImmersionOwner ,EasyPermissions.PermissionCallbacks{

    //    @BindView(R.id.grid_recommend)
//    SgHeaderGridView gridRecommend;
    @BindView(R.id.recyclerView_recommend)
    RecyclerView recyclerView;
    @BindView(R.id.pull_refresh)
    RefreshLayout refreshLayout;
    @BindView(R.id.view_main)
    LinearLayout view_main;
    @BindView(R.id.ll_top)
    LinearLayout view_top;


    @BindView(R.id.txt_location)
    TextView txtLocation;

    @BindView(R.id.layout_location)
    LinearLayout layoutLocation;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    @BindView(R.id.avi)
    RelativeLayout avi;
    Map<String, String> params;
    private MySgQuickAdapter myAdapter;
    private Bundle bundle;
    private int currentPage = 1;
    private int currentQuanPage = 1;
    private String local = "";
    private String localInfo = "";
    private String name = "";
    private int type = 0;
    private HeaderViewHolder headerViewHolder;

    private List<LocalgoodsdataResponse.DataBean> list = new ArrayList<>();

    private SgQuickAdapter<LocalgoodsdataResponse.DataBean> adapter;


    public LocationClient mLocationClient = null;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home_scroll;
    }

    @Override
    protected void initEventAndData() {

//    initToolBar();
        initLocation();
        initRefreshLayout();
        checkPerm();
        initGrid();
        initSgDragViewPage();
        view_top.getBackground().mutate().setAlpha(0);
        if (null != (SPUtils.getInstance(getContext()).getDataList())) {
            headerViewHolder.banner.update(SPUtils.getInstance(getContext()).getDataList());
        }
    }

//  private void initToolBar(){
//
//
//
//    appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//      @Override
//      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        //垂直方向偏移量
//        int offset = Math.abs(verticalOffset);
//        //最大偏移距离
//        int scrollRange = appBarLayout.getTotalScrollRange();
//        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
////          toolbarOpen.setVisibility(View.VISIBLE);
////          toolbarClose.setVisibility(View.GONE);
//
//          bgToolbarOpen.setVisibility(View.VISIBLE);
//          bgToolbarClose.setVisibility(View.GONE);
//
//          //根据偏移百分比 计算透明值
//          float scale2 = (float) offset / (scrollRange / 2);
//          int alpha2 = (int) (255 * scale2);
//          bgToolbarOpen.setBackgroundColor(Color.argb(alpha2, 253, 131, 8));
//
//          //bgToolbarOpen.setBackgroundColor(getResources().getColor(R.color.topbar_bg));
//        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
////          toolbarClose.setVisibility(View.VISIBLE);
////          toolbarOpen.setVisibility(View.GONE);
//          bgToolbarOpen.setVisibility(View.GONE);
//          bgToolbarClose.setVisibility(View.VISIBLE);
//          float scale3 = (float) (scrollRange  - offset) / (scrollRange / 2);
//          int alpha3 = (int) (255 * scale3);
//          bgToolbarOpen.setBackgroundColor(Color.argb(alpha3, 253, 131, 8));
//        }
//        //根据偏移百分比计算扫一扫布局的透明度值
//        float scale = (float) offset / scrollRange;
//        int alpha = (int) (255 * scale);
//
//
//        bgContent.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
//
//
//
//      }
//    });
//  }

    private boolean cityPickerLocation = false;

    private void initLocation() {
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String district = bdLocation.getDistrict();    //获取区县

                local = district;

                localInfo = "&Qx=" + local + "&longitude=" + bdLocation.getLatitude() + "&latitude=" + bdLocation.getLatitude();
                SgLog.d("district == " + district);


//        loadLocalgoodsdata();
//        mPresenter.doGetLocalAd(local);
                if (cityPickerLocation) {
                    CityPicker.from(getActivity()).locateComplete(new LocatedCity(local, "", ""), LocateState.SUCCESS);
                    cityPickerLocation = false;
                } else {
                    refreshLocal(district);
                }

            }
        });
        //注册监听函数

        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        cityPickerLocation = false;
        mLocationClient.start();
    }

    private void loadLocalgoodsdata() {
        mPresenter.doLocalgoodsdata(currentPage, Constants.LOAD_PAFE_SIZE, local, name, avi);
    }

    private void loadQuangoodsdata() {
        mPresenter.doGoodsdata(currentQuanPage, Constants.LOAD_PAFE_SIZE, name);
    }

    private boolean loadLocalgoodsdataAble = true;

    private void initRefreshLayout() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (loadLocalgoodsdataAble) {
                    currentPage++;
                    loadLocalgoodsdata();
                } else {
                    currentQuanPage++;
                    loadQuangoodsdata();
                }
            }
        });
        setEnableLoadMore(true);
        setEnableRefresh(false);
    }

    private void initSgDragViewPage() {
        List<MainGridBean> dragList = MenuTools.getInstance(mContext).getAllList();
        SgQuickAdapter<MainGridBean> dragAdapter = new SgQuickAdapter<MainGridBean>(mContext, dragList, R.layout.item_drag) {
            @Override
            public void convert(ViewHolder helper, MainGridBean item, int position) {
                if (!TextUtils.isEmpty(item.title)) {
                    helper.setText(R.id.txtTitle, item.title);
                }
                String iconName = item.drawableName;
                if (TextUtils.isEmpty(iconName)) {
                    iconName = "main_functions_add";
                }
                helper.setImageResource(R.id.img, SgUtils.getResIdByName(mContext, iconName));
            }
        };
        headerViewHolder.sgDragView.setAdapter(dragAdapter);
        dragAdapter.notifyDataSetChanged();
        headerViewHolder.sgDragView.setOnItemClickListener(new ScrollViewGroup.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MainGridBean bean = dragList.get(position);
                if (null == bean) {
                    return;
                }
                if(position == 8){
                    JumpUtils.simpJump(getActivity(), LogingActivity.class, false);
                    return;
                }
                if(position == 10){
                    type = 1;
                    checkPerm();
                }
                if (bean.type.equals("1")) {
                    if (!isLogin()) {
                        startLogin();
                        return;
                    }
                    String jumpUrl = bean.jumpUrl;
                    if (TextUtils.isEmpty(jumpUrl)) {
                        return;
                    }
                    try {
                        Class jumpActivity = Class.forName(jumpUrl);
                        ((Activity) mContext).startActivity(new Intent(mContext, jumpActivity));
                    } catch (Exception e) {

                    }
                } else if (bean.type.equals("2")) {
                    String webUrl = Constants.BASE_IP + bean.jumpUrl;
                    startWebviewAct(webUrl);
                } else if (bean.type.equals("3")) {
//                    if (!isLogin()) {
//                        startLogin();
//                        return;
//                    }
//                    String webUrl = Constants.BASE_IP + bean.jumpUrl + localInfo;
//                    startWebviewAct(webUrl);

                }
            }
        });


    }

    HeaderAndFooterWrapper mHeaderWrapper;
    private int scrollY = 0;

    @SuppressLint("ClickableViewAccessibility")
    private void initGrid() {
//        adapter = new SgQuickAdapter<LocalgoodsdataResponse.DataBean>(mContext, list, R.layout.item_main_recommend_grid) {
//            @Override
//            public void convert(ViewHolder helper, LocalgoodsdataResponse.DataBean item, int position) {
//
//
//                ImageView img = helper.getConvertView().findViewById(R.id.img_product);
//                if (!TextUtils.isEmpty(item.GoodsImg)) {
//                    Glide.with(mContext).load(item.GoodsImg).into(img);
//                } else {
//                    img.setImageDrawable(null);
//                }
//
//                helper.setText(R.id.txt_product, item.Name);
//                helper.setText(R.id.txt_new_price, "" + item.SalesPrice);
//                TextView txtOldPrice = helper.getConvertView().findViewById(R.id.txt_old_price);
//
//                helper.setText(R.id.txt_product, item.Name);
//
//                txtOldPrice.setText("" + item.MarketPrice);
//                txtOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//
//            }
//        };

//        ViewCompat.setNestedScrollingEnabled(gridRecommend, true);
        myAdapter = new MySgQuickAdapter(mContext, R.layout.item_main_recommend_grid, list);

        View headView = LayoutInflater.from(mContext).inflate(R.layout.view_main_grid_head, null);
        headerViewHolder = new HeaderViewHolder(headView);
        mHeaderWrapper = new HeaderAndFooterWrapper(myAdapter);
        mHeaderWrapper.addHeaderView(headView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(mHeaderWrapper);
//        gridRecommend.addHeaderView(headView);
//        gridRecommend.setAdapter(adapter);
        initHeaderViewHolder();


        myAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                LocalgoodsdataResponse.DataBean dataBean = list.get(position - 1);
                if (null != dataBean) {
                    if (!TextUtils.isEmpty(dataBean.DetailUrl)) {
//                        startWebviewAct(dataBean.DetailUrl);
                        Bundle bundle = new Bundle();
                        bundle.putInt("goodsId", dataBean.Id);
                        JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
//        gridRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                LocalgoodsdataResponse.DataBean dataBean = list.get(position - 2);
//                if (null != dataBean) {
//                    if (!TextUtils.isEmpty(dataBean.DetailUrl)) {
////                        startWebviewAct(dataBean.DetailUrl);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("goodsId", dataBean.Id);
//                        JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
//                    }
//                }
//
//            }
//        });

//        gridRecommend.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (isInView(headerViewHolder.sgDragView, event)) {
//                    SgLog.d("isInView");
//                    headerViewHolder.sgDragView.onTouchEvent(event);
//                    //return false;
//                } else {
//                    //SgLog.d("is not  InView");
//                }
//
//                return false;
//            }
//        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                System.out.println("scrolly:" + scrollY + "   getAlphaFloat(scrollY):" + getAlphaFloat(scrollY));
//                SgLog.d(scrollY + "   " + llBg.getHeight());
//                if (scrollY >= headerViewHolder.banner.getHeight()) {
//                view_top.setVisibility(View.VISIBLE);
                if (scrollY > headerViewHolder.banner.getHeight()) {
                    view_top.getBackground().mutate().setAlpha(255);
                } else {
                    view_top.getBackground().mutate().setAlpha(0);
                }
//                } else {
//                    view_top.setVisibility(View.GONE);
//                }
            }
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    headerViewHolder.banner.getLocationOnScreen(loc);
//                    System.out.println("scrolly:"+getAlphaFloat(loc[1]));
//                    view_top.getBackground().setAlpha((int) getAlphaFloat(loc[1]));
//                    view_top.setVisibility(View.VISIBLE);
//                }
//            });
//        }
    }


    /**
     * 判断触摸的点是否在View范围内
     */
    private boolean isInView(View v, MotionEvent event) {
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
        float eventX = event.getRawX();
        float eventY = event.getRawY();
        Rect rect = new Rect(left, top, right, bottom);
        return rect.contains((int) eventX, (int) eventY);
    }

    private void initHeaderViewHolder() {
        initBanner();
        initVerticalScrollTextView();
    }

    private void initBanner() {
        //设置banner样式
        headerViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        headerViewHolder.banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        // banner.setImages(images);
        //设置banner动画效果
        headerViewHolder.banner.setBannerAnimation(Transformer.DepthPage);

        //设置自动轮播，默认为true
        headerViewHolder.banner.isAutoPlay(true);
        //设置轮播时间
        headerViewHolder.banner.setDelayTime(4500);
        //设置指示器位置（当banner模式中有指示器时）
        headerViewHolder.banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        headerViewHolder.banner.start();
        headerViewHolder.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

//                if (!isLogin()) {
//                    startLogin();
//                    return;
//                }

                if (null == localAdResponse) {
                    return;
                }


                if (null == localAdResponse.data) {
                    return;
                }
                if (null == localAdResponse.data.banner) {
                    return;
                }
                if (localAdResponse.data.banner.size() < position) {
                    return;
                }

//                startWebviewAct(localAdResponse.data.banner.get(position).Href);
                String str = localAdResponse.data.banner.get(position).Href;
                params = UrlUtils.getParameters(str);
                bundle = new Bundle();
                bundle.putInt("stroeId", Integer.parseInt(params.get("storeid")));
                JumpUtils.dataJump(getActivity(), ShopDetailsActivity.class, bundle, false);
            }
        });
    }


    private void initVerticalScrollTextView() {

    }

    LocalAdResponse localAdResponse;

    @Override
    public void displayLocalAd(LocalAdResponse response) {

        if (null == response) {
            return;
        }

        if (null == response.data) {
            return;
        }

        localAdResponse = response;
        if (response.data.banner.size() > 0) {
            List<String> images = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (LocalAdResponse.DataBean.BannerBean ba : response.data.banner) {

                images.add(ba.ImageUrl);
                SPUtils.getInstance(getContext()).setDataList(images);
                titles.add(ba.Title);
            }
            if (images.size() > 0) {
                headerViewHolder.banner.update(images, titles);
                headerViewHolder.banner.setVisibility(View.VISIBLE);
            } else {
                headerViewHolder.banner.setVisibility(View.GONE);
            }
        }

        if (null != response.data.fours) {
            if (response.data.fours.size() > 0) {
                // List<String> list = new ArrayList<>();
                for (int i = 0; i < response.data.fours.size(); i++) {
                    LocalAdResponse.DataBean.FoursBean foursBean = response.data.fours.get(i);

                    if (i == 0) {
                        Glide.with(mContext).load(foursBean.ImageUrl).into(headerViewHolder.imgMainLeft);
                    } else if (i == 1) {
                        Glide.with(mContext).load(foursBean.ImageUrl).into(headerViewHolder.imgMainTop);

                    } else if (i == 2) {
                        Glide.with(mContext).load(foursBean.ImageUrl).into(headerViewHolder.imgMainButtom);

                    } else if (i == 3) {
                        Glide.with(mContext).load(foursBean.ImageUrl).into(headerViewHolder.imgMainRight);

                    }
                }

                headerViewHolder.layoutFour.setVisibility(View.VISIBLE);

            }
        }


        if (null != response.data.fours) {
            if (response.data.fours.size() > 0) {
                List<String> list = new ArrayList<>();
                for (LocalAdResponse.DataBean.LocalsubBean localsubBean : response.data.localsub) {
                    list.add(localsubBean.Title);
                }
                if (list.size() > 0) {

                    headerViewHolder.txtShbkScroll.setTipList(list);
                }
            }
        }

    }


    protected void finishLoadMore() {
        refreshLayout.finishLoadMore();
    }

    /**
     * 设置允许加载更多
     */
    protected void setEnableLoadMore(boolean loadMore) {
        refreshLayout.setEnableLoadMore(loadMore);
    }

    /**
     * 设置允许下拉刷新
     */
    protected void setEnableRefresh(boolean refresh) {
        refreshLayout.setEnableRefresh(refresh);
    }


    @Override
    public void displayGoodsdata(LocalgoodsdataResponse response) {
        finishLoadMore();


        if (null != response) {
            if (null != response.data) {
                if (response.data.size() < Constants.LOAD_PAFE_SIZE) {
                    setEnableLoadMore(false);
                }
                list.addAll(response.data);
                adapter.notifyDataSetChanged();
                if (list.size() > 0) {
                    headerViewHolder.layoutRecomm.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void displayLocalgoodsdata(LocalgoodsdataResponse response) {

        finishLoadMore();
        if (response.currentpage == 1) {
            list.clear();
        } else {

        }


        if (null != response) {
            if (null != response.data) {
                if (response.data.size() < Constants.LOAD_PAFE_SIZE) {
                    loadLocalgoodsdataAble = false;
                }
                if (response.data.size() == 0) {
                    loadQuangoodsdata();
                }
                list.addAll(response.data);
                if (response.currentpage > 1) {
                    myAdapter.addDatas(list);
                }
                myAdapter.notifyDataSetChanged();
                if (list.size() > 0) {
                    headerViewHolder.layoutRecomm.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void hasPermission() {
        mLocationClient.start();
        SgLog.d("Permission has");
    }


    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRationale(final PermissionRequest request) {

        SgLog.d("Permission Request");
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showDenied() {
        SgLog.d("Permission Denied");
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showNeverAsk() {
        SgLog.d("Permission AskAgain");
    }


    @OnClick({R.id.layout_location, R.id.layout_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_location:
                if (allCities.size() == 0) {
                    mPresenter.doGetareas();
                } else {
                    showCityPicker();
                }
                break;
//      case R.id.layout_location_open:
//        if (allCities.size() == 0) {
//          mPresenter.doGetareas();
//        } else {
//          showCityPicker();
//        }
//        break;
            case R.id.layout_search:
                startWebviewAct(Constants.MAIN_SEARCH);
                break;
//      case R.id.layout_search_open:
//        startWebviewAct(Constants.MAIN_SEARCH);
//        break;

        }
    }


    @Override
    public void displayGetareas(CityBean bean) {
        if (null != bean) {
            if (null != bean.data) {
                for (CityBean.DataBean dataBean : bean.data) {
                    City city = new City(dataBean.Name, Pinyin.toPinyin(dataBean.Name, ""));
                    allCities.add(city);
                }
            }
        }
        Collections.sort(allCities);
        showCityPicker();
    }


    private void refreshLocal(String lo) {
        local = lo;
        loadLocalgoodsdata();
        mPresenter.doGetLocalAd(local);
        txtLocation.setText(local);
//    txtLocationOpen.setText(local);
    }

    private List<HotCity> hotCities = new ArrayList<>();
    private List<City> allCities = new ArrayList<>();
    private int anim;
    private int theme;
    private boolean enable;

    private void showCityPicker() {

        CityPicker.from(getActivity())
                .enableAnimation(enable)
                .setAnimationStyle(anim)
                .setLocatedCity(new LocatedCity(local, "", ""))
                .setHotCities(hotCities)
                .setAllCities(allCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        refreshLocal(data.getName());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位

                        cityPickerLocation = true;
                        mLocationClient.start();


                    }
                })
                .show();
    }

    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(getActivity()).titleBar(R.id.ll_top).init();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_top.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        view_top.setLayoutParams(params);

        view_top.setPadding(0, ImmersionBar.getActionBarHeight(getActivity()) / 2 + 10, 0, 20);
        SgLog.d("immersionbar:" + ImmersionBar.getActionBarHeight(getActivity()));
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }


    class HeaderViewHolder {

        @BindView(R.id.banner)
        Banner banner;
        @BindView(R.id.txt_shbk)
        TextView txtShbk;
        @BindView(R.id.txt_shbk_scroll)
        VerticalScrollTextView txtShbkScroll;
        @BindView(R.id.layout_shbk_more)
        LinearLayout layoutShbkMore;
        @BindView(R.id.layout_four)
        LinearLayout layoutFour;
        @BindView(R.id.layout_recomm)
        LinearLayout layoutRecomm;
        @BindView(R.id.img_main_left)
        ImageView imgMainLeft;
        @BindView(R.id.img_main_top)
        ImageView imgMainTop;
        @BindView(R.id.img_main_buttom)
        ImageView imgMainButtom;
        @BindView(R.id.img_main_right)
        ImageView imgMainRight;
        @BindView(R.id.sgDragView)
        ScrollViewGroup sgDragView;


        public HeaderViewHolder(View headerRootView) {
            ButterKnife.bind(this, headerRootView);
        }


        @OnClick({R.id.txt_shbk, R.id.txt_shbk_scroll, R.id.layout_shbk_more, R.id.img_main_left, R.id.img_main_top, R.id.img_main_buttom, R.id.img_main_right})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.txt_shbk:
                    break;
                case R.id.txt_shbk_scroll:
                    if (null == localAdResponse) {
                        return;
                    }
                    int index = txtShbkScroll.getCurTipIndex();
                    if (index < 0) {
                        return;
                    }
                    if (null == localAdResponse.data) {
                        return;
                    }
                    if (null == localAdResponse.data.localsub) {
                        return;
                    }
                    if (index >= localAdResponse.data.localsub.size()) {
                        return;
                    }
                    startWebviewAct(localAdResponse.data.localsub.get(index).localUrl);

                    break;
                case R.id.layout_shbk_more:
                    startWebviewAct(Constants.MAIN_GD);
                    break;
                case R.id.img_main_left:
                    jumpFour(0);
                    break;
                case R.id.img_main_top:
                    jumpFour(1);
                    break;
                case R.id.img_main_buttom:
                    jumpFour(2);
                    break;
                case R.id.img_main_right:
                    jumpFour(3);
                    break;
            }
        }


    }


    private void jumpFour(int position) {

//        if (!isLogin()) {
//            startLogin();
//            return;
//        }

        if (null == localAdResponse) {
            return;
        }


        if (null == localAdResponse.data) {
            return;
        }
        if (null == localAdResponse.data.fours) {
            return;
        }
        if (localAdResponse.data.localsub.size() < 4) {
            return;
        }

//        startWebviewAct(localAdResponse.data.fours.get(position).Href);
        String str = localAdResponse.data.fours.get(position).Href;
        params = UrlUtils.getParameters(str);
        bundle = new Bundle();
        if (!StrUtils.isEmpty(params.get("GoodsId"))) {
            bundle.putInt("goodsId", Integer.parseInt(params.get("GoodsId")));
            JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
        }
        if (!StrUtils.isEmpty(params.get("storeid"))) {
            bundle.putString("stroeId", params.get("storeid"));
            JumpUtils.dataJump(getActivity(), ShopDetailsActivity.class, bundle, false);
        }

    }

    int[] loc = new int[2];

    /**
     * 获取渐变透明值
     *
     * @param dis
     * @return
     */
    public float getAlphaFloat(int dis) {

        int step = 248;
        if (dis == 0) {
            return 0.0f;
        }

        if (dis > step) {
            return (float) (dis * (1.0 / step));
        } else {
            return 1.0f;
        }

    }

    @AfterPermissionGranted(100)
    private void checkPerm() {
        String[] params={Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(getContext(),params)){
            if(type == 1){
                JumpUtils.simpJump(getActivity(),StoreListActivity.class,false);
            }
        }else{
            EasyPermissions.requestPermissions(this,"需要定位权限,和相机权限",100,params);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        initLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        refreshLocal("全国");
    }
    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}
