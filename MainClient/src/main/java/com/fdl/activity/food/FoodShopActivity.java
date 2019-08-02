package com.fdl.activity.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.activity.order.SearchProductActivity;
import com.fdl.activity.set.ProtocolActivity;
import com.fdl.activity.supermarket.MapActivity;
import com.fdl.activity.supermarket.OrderActivity;
import com.fdl.activity.supermarket.SupermarkDialogFragment;
import com.fdl.adapter.CouponsAdapter;
import com.fdl.adapter.ScrollLeftAdapter;
import com.fdl.adapter.ScrollRightAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CouponsBean;
import com.fdl.bean.FoodPredeterminelistBean;
import com.fdl.bean.ScrollBean;
import com.fdl.bean.StoreBannerBean;
import com.fdl.bean.SuperMarketBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.bean.daoBean.SupplierBean;
import com.fdl.db.DBManager;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.UrlUtils;
import com.fdl.wedgit.BaseFragmentActivity;
import com.fdl.wedgit.StarBarView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.sg.cj.snh.uitls.GlideImageLoader;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;
import com.snh.greendao.SuperMarketBeanDao;
import com.wang.avi.AVLoadingIndicatorView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */
@SuppressLint("Registered")
public class FoodShopActivity extends BaseFragmentActivity {


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
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_Logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.coupon_recyclerview)
    RecyclerView couponRecyclerview;
    @BindView(R.id.ll_coupons)
    LinearLayout llCoupons;
    @BindView(R.id.heard_back)
    RelativeLayout heardBack;
    @BindView(R.id.heard_title)
    ImageView heardTitle;
    @BindView(R.id.ll_follow)
    ImageView llFollow;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_sp)
    TextView tvSp;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;
    @BindView(R.id.tv_xx)
    TextView tvXx;
    @BindView(R.id.rec_left)
    RecyclerView recLeft;
    @BindView(R.id.rec_right)
    RecyclerView recRight;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.ll_product)
    LinearLayout llProduct;
    @BindView(R.id.start_view)
    StarBarView startView;
    @BindView(R.id.start_view2)
    StarBarView startView2;
    @BindView(R.id.start_view3)
    StarBarView startView3;
    @BindView(R.id.li_evaluate)
    LinearLayout liEvaluate;
    @BindView(R.id.store_phone)
    TextView storePhone;
    //    @BindView(R.id.rv_store_img)
//    RecyclerView rvStoreImg;
    @BindView(R.id.tv_Business_hours)
    TextView tvBusinessHours;
    @BindView(R.id.tv_ServerContent)
    TextView tvServerContent;
    //    @BindView(R.id.tv_Promotions)
//    TextView tvPromotions;
    @BindView(R.id.ll_storeInfo)
    LinearLayout llStoreInfo;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.iv_shop_logo)
    ImageView ivShopLogo;
    @BindView(R.id.tv_noOne)
    TextView tvNoOne;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_predestine)
    TextView tvPredestine;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.rl_bottom_bar)
    RelativeLayout rlBottomBar;

    private ImmersionBar immersionBar;
    private List<String> left = new ArrayList<>();
    private List<ScrollBean> right = new ArrayList<>();
    private ScrollLeftAdapter leftAdapter;
    private ScrollRightAdapter rightAdapter;
    private List<Integer> tPosition = new ArrayList<>();
    //title的高度
    private int tHeight;
    //记录右侧当前可见的第一个item的position
    private int first = 0;
    private LinearLayoutManager rightManager;
    private int id;
    private Bundle bundle;
    private SupplierBean dataBean;
    private String distance;
    private DialogUtils dialogUtils;
    private SupplierBean bean;
    private double latitude;
    private double longitude;

    private SupermarkDialogFragment fragment;
    private FoodkDialogFragment fragment1;
    private CouponsAdapter couponsAdapter;
    private int goodsId;
    private int shopType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_shop_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getInt("stroeId");
            distance = bundle.getString("distance","");
//            latitude = bundle.getDouble("latitude");
//            longitude = bundle.getDouble("longitude");
            goodsId = bundle.getInt("goodsId");
            shopType = bundle.getInt("shopType");
        }

        try {

            latitude = Double.parseDouble(SPUtils.getInstance(this).getString(Contans.LATITUDE));
            longitude = Double.parseDouble(SPUtils.getInstance(this).getString(Contans.LONGITUDE));
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
        }
        ButterKnife.bind(this);
        immersionBar = ImmersionBar.with(this);
//            IsBang.setImmerHeard(this, rlHead);
//        if (ImmersionBar.hasNotchScreen(this)) {
//        } else {
        immersionBar.titleBar(rlHead);
//        }
        immersionBar.statusBarDarkFont(true);
        immersionBar.init();
        setUpViews();
        dialogUtils = new DialogUtils(this);
//        id = 330;
        getData();
    }


    public void setUpViews() {

//        setBoomMenu();
        heardMenu.setVisibility(View.VISIBLE);

        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    heardBack.setBackgroundResource(R.drawable.shape_solid_gray_50_bg);
                    llMenu.setBackgroundResource(R.drawable.shape_solid_left_gray_50_bg);
                    immersionBar.statusBarDarkFont(true).init();
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    heardBack.setBackgroundColor(Color.parseColor("#00000000"));
                    llMenu.setBackgroundColor(Color.parseColor("#00000000"));
                    immersionBar.statusBarDarkFont(false).init();
                } else {

                    //中间状态

                }

            }
        });

        couponsAdapter = new CouponsAdapter(null);
        LinearLayoutManager couponManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        couponRecyclerview.setLayoutManager(couponManager);
        couponRecyclerview.setAdapter(couponsAdapter);
        couponsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_item1:
                        String linkUrl = couponsData.get(position).LinkUrl;
                        Map<String, String> map = null;
                        if (linkUrl.contains("SHOP")) {
                            map = UrlUtils.getShopParameters(linkUrl);
                        }
                        //跳转指定商品
                        if (linkUrl.contains("SHOPP://")) {
                            if (id != Integer.parseInt(map.get("shopid"))) {
                                if (couponsData.get(position).IsBack == 1) {
                                    bannerClick(couponsData.get(position).ID, map, 1);
                                } else {
                                    jump(map, 1);
                                }
                            } else {
                                goodsId = Integer.parseInt(map.get("goodsid"));
                                showFragment();
                            }
                        }
                        //跳转指定店铺
                        if (linkUrl.contains("SHOP://")) {
                            if (id != Integer.parseInt(map.get("shopid"))) {
                                if (couponsData.get(position).IsBack == 1) {
                                    bannerClick(couponsData.get(position).ID, map, 2);
                                } else {
                                    jump(map, 2);
                                }
                            }
                        }

                        if (linkUrl.contains("http")) {
                            bundle = new Bundle();
                            bundle.putString("url", couponsData.get(position).LinkUrl);
                            JumpUtils.dataJump(FoodShopActivity.this, ProtocolActivity.class, bundle, false);
                        }
                        break;
                    case R.id.ll_item2:
                        recevierCoupons(couponsData.get(position).CouponId);
                        break;
                    case R.id.ll_item3:
                        if (isScorllow) {
                            smoothMoveToPosition(couponRecyclerview, 0);
                            isScorllow = false;
                        } else {
                            smoothMoveToPosition(couponRecyclerview, producBannertListBeans.size() - 1);
                            isScorllow = true;
                        }
                        break;
                    case R.id.ll_item4:

                        break;
                }
            }
        });
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String linkUrl = bannerListBeans.get(position).LinkUrl;
                Map<String, String> map = null;
                if (linkUrl.contains("SHOP")) {
                    map = UrlUtils.getShopParameters(linkUrl);
                }
                //跳转指定商品
                if (linkUrl.contains("SHOPP://")) {
                    if (id != Integer.parseInt(map.get("shopid"))) {
                        if (bannerListBeans.get(position).IsBack == 1) {
                            bannerClick(bannerListBeans.get(position).ID, map, 1);
                        } else {
                            jump(map, 1);
                        }
                    } else {
                        goodsId = Integer.parseInt(map.get("goodsid"));
                        showFragment();
                    }
                }
                //跳转指定店铺
                if (linkUrl.contains("SHOP://")) {
                    if (id != Integer.parseInt(map.get("shopid"))) {
                        if (bannerListBeans.get(position).IsBack == 1) {
                            bannerClick(bannerListBeans.get(position).ID, map, 2);
                        } else {
                            jump(map, 2);
                        }
                    }
                }

                if (linkUrl.contains("http")) {
                    bundle = new Bundle();
                    bundle.putString("url", bannerListBeans.get(position).LinkUrl);
                    JumpUtils.dataJump(FoodShopActivity.this, ProtocolActivity.class, bundle, false);
                }
            }
        });
    }

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    private boolean isScorllow = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != immersionBar) {
            immersionBar.destroy();
        }
    }

    boolean isClick;

    private void getData() {
        avi.setVisibility(View.VISIBLE);
        RequestClient.GetSuperMarketCommTenant1(id, "", this, new NetSubscriber<BaseResultBean<SupplierBean>>(this) {
            @Override
            public void onResultNext(BaseResultBean<SupplierBean> model) {
                bean = model.data;
                if (bean.IsFavorite) {
                    llFollow.setBackgroundResource(R.drawable.follow_bg);
                } else {
                    llFollow.setBackgroundResource(R.drawable.collotion_bg);
                }
                isClick = bean.IsFavorite;
                ImageUtils.loadUrlCorners(FoodShopActivity.this, model.data.SupplierIcon, ivLogo);
                tvName.setText(model.data.SupplierName);
                storePhone.setText(model.data.ServiceTel);
                tvBusinessHours.setText(model.data.BusinessHour);
                tvServerContent.setText(model.data.ServerContent);
                if (bean.CommTenantResultList.size() > 0) {
                    querInit();
                } else {
                    avi.setVisibility(View.GONE);
                }
                couponsData.clear();
                bannerListBeans.clear();
                producBannertListBeans.clear();
                bannerUrls.clear();
                getBanner();
                saveShopInfo();
            }
        });
    }

    //初始化右边数据
    private void initRight(Context mContext) {
        rightManager = new LinearLayoutManager(this);
        if (rightAdapter == null) {
            rightAdapter = new ScrollRightAdapter(R.layout.item_right_layout, R.layout.layout_right_title, null);
            rightAdapter.setType(1);
            recRight.setLayoutManager(rightManager);
            recRight.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
                            , 0
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3)));
                }
            });
            recRight.setAdapter(rightAdapter);
        } else {
            rightAdapter.notifyDataSetChanged();
        }

        rightAdapter.setNewData(right);

        //设置右侧初始title
        if (right.get(first).isHeader) {
            rightTitle.setText(right.get(first).header);
        }

        recRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取右侧title的高度
                tHeight = rightTitle.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //判断如果是header
                if (right.get(first).isHeader) {
                    //获取此组名item的view
                    View view = rightManager.findViewByPosition(first);
                    if (view != null) {
                        //如果此组名item顶部和父容器顶部距离大于等于title的高度,则设置偏移量
                        if (view.getTop() >= tHeight) {
                            rightTitle.setY(view.getTop() - tHeight);
                        } else {
                            //否则不设置
                            rightTitle.setY(0);
                        }
                    }
                }

                //因为每次滑动之后,右侧列表中可见的第一个item的position肯定会改变,并且右侧列表中可见的第一个item的position变换了之后,
                //才有可能改变右侧title的值,所以这个方法内的逻辑在右侧可见的第一个item的position改变之后一定会执行
                int firstPosition = rightManager.findFirstVisibleItemPosition();
                if (first != firstPosition && firstPosition >= 0) {
                    //给first赋值
                    first = firstPosition;
                    //不设置Y轴的偏移量
                    rightTitle.setY(0);

                    //判断如果右侧可见的第一个item是否是header,设置相应的值
                    if (right.get(first).isHeader) {
                        rightTitle.setText(right.get(first).header);
                    } else {
                        rightTitle.setText(right.get(first).t.type);
                    }
                }

                //遍历左边列表,列表对应的内容等于右边的title,则设置左侧对应item高亮
                for (int i = 0; i < left.size(); i++) {
                    if (left.get(i).equals(rightTitle.getText().toString())) {
                        leftAdapter.selectItem(i);
                    }
                }

                //如果右边最后一个完全显示的item的position,等于bean中最后一条数据的position(也就是右侧列表拉到底了),
                //则设置左侧列表最后一条item高亮
//                if (rightManager.findLastCompletelyVisibleItemPosition() == right.size() - 1) {
//                    leftAdapter.selectItem(left.size() - 1);
//                }
            }
        });

        rightAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        if (right.get(position - 1).itemBean.total >= right.get(position - 1).itemBean.Inventory) {
                        } else {
                            right.get(position - 1).itemBean.total += 1;
//                            rightAdapter.setNewData(right);
                            updataDataToDb(right.get(position - 1));
                            querInit();

                        }
                        break;
                    case R.id.iv_delete:
                        right.get(position - 1).itemBean.total -= 1;
//                        rightAdapter.setNewData(right);
                        deleteDataInDb(right.get(position - 1));
                        querInit();

                        break;
                    case R.id.iv_product_logo1:
                        bundle = new Bundle();
                        bundle.putLong("id", right.get(position - 1).itemBean.CommTenantId);
                        fragment = new SupermarkDialogFragment();
                        fragment.setArguments(bundle);
                        fragment.show(getSupportFragmentManager(), "details");
                        break;
                }
            }
        });
    }

    private void initLeft(Context mContext) {
        if (leftAdapter == null) {
            leftAdapter = new ScrollLeftAdapter(R.layout.scroll_left, null);
            recLeft.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//            recLeft.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            recLeft.setAdapter(leftAdapter);
        } else {
            leftAdapter.notifyDataSetChanged();
        }

        leftAdapter.setNewData(left);
        leftAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点击左侧列表的相应item,右侧列表相应的title置顶显示
                    //(最后一组内容若不能填充右侧整个可见页面,则显示到右侧列表的最底端)
                    case R.id.item:
                        leftAdapter.selectItem(position);
                        rightManager.scrollToPositionWithOffset(tPosition.get(position), 0);
                        break;

                }
            }
        });
    }

    //获取数据(若请求服务端数据,请求到的列表需有序排列)
    private void initData(SupplierBean bean) {

        List<ScrollBean> list = new ArrayList<>();
        ScrollBean scrollBean = null;
        ScrollBean.ScrollItemBean scrollItemBean;
        for (int i = 0; i < bean.CommTenantResultList.size(); i++) {
            left.add(bean.CommTenantResultList.get(i).TypeName);
            scrollBean = new ScrollBean(true, bean.CommTenantResultList.get(i).TypeName);
            scrollBean.SupplierId = bean.SupplierId;
            right.add(scrollBean);
            for (int j = 0; j < bean.CommTenantResultList.get(i).CommTenantList.size(); j++) {
                scrollItemBean = new ScrollBean.ScrollItemBean();
                scrollItemBean.Inventory = bean.CommTenantResultList.get(i).CommTenantList.get(j).Inventory;
                scrollItemBean.type = bean.CommTenantResultList.get(i).TypeName;
                scrollItemBean.CommTenantIcon = bean.CommTenantResultList.get(i).CommTenantList.get(j).CommTenantIcon;
                scrollItemBean.CommTenantId = bean.CommTenantResultList.get(i).CommTenantList.get(j).CommTenantId;
                scrollItemBean.CommTenantName = bean.CommTenantResultList.get(i).CommTenantList.get(j).CommTenantName;
                scrollItemBean.OnThePin = bean.CommTenantResultList.get(i).CommTenantList.get(j).OnThePin;
                scrollItemBean.Price = bean.CommTenantResultList.get(i).CommTenantList.get(j).Price;
                scrollItemBean.UnitsTitle = bean.CommTenantResultList.get(i).CommTenantList.get(j).UnitsTitle;
                scrollItemBean.total = bean.CommTenantResultList.get(i).CommTenantList.get(j).total;
                scrollBean.itemBean = scrollItemBean;
                scrollBean = new ScrollBean(scrollItemBean);
                right.add(scrollBean);
            }
        }

        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).isHeader) {
                //遍历右侧列表,判断如果是header,则将此header在右侧列表中所在的position添加到集合中
                tPosition.add(i);
            }
            if (right.get(i).SupplierId != 0) {
                list.add(right.get(i));
            }
        }
    }

    /**
     * 获得资源 dimens (dp)
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public float getDimens(Context context, int id) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = context.getResources().getDimension(id);
        return px / dm.density;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.heard_back, R.id.tv_pay, R.id.iv_shop_logo, R.id.ll_follow, R.id.heard_title, R.id.heard_menu, R.id.tv_sp, R.id.tv_xx, R.id.tv_evaluate, R.id.tv_predestine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_pay:
                if (PartyApp.getAppComponent().getDataManager().getId() != 0) {
                    toPay();
                } else {
                    bundle.putString("type", "store");
                    JumpUtils.dataJump(this, LoginActivity.class, bundle, false);
                }
                break;
            case R.id.iv_shop_logo:
                if (query().size() > 0) {
                    dialogUtils.shopCartDialog(new DialogUtils.ChoseClickLisener() {
                        @Override
                        public void onConfirmClick(View v) {
                            querInit();
                        }

                        @Override
                        public void onCancelClick(View v) {
                            querInit();
                        }
                    }, query(), id, rlBottomBar.getHeight(), true);
                }
                break;
            case R.id.ll_follow:
                if (!(PartyApp.getAppComponent().getDataManager().getId() != 0)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    followShop();
                }
                break;
            case R.id.heard_title:
                bundle = new Bundle();
                bundle.putInt("storeId", id);
                JumpUtils.dataJump(FoodShopActivity.this, SearchProductActivity.class, bundle, false);
                break;
            case R.id.heard_menu:
                if (StrUtils.isEmpty(bean.XCXUrl)) {
                    dialogUtils.ShareDialog(bean.SupplierName, bean.FxUrl, "我在算你狠发现一个不错的店铺，赶快来看看吧~", bean.SupplierIcon);
                } else {
                    dialogUtils.ShareDialog(bean.SupplierName, bean.XCXUrl, "我在算你狠发现一个不错的店铺，赶快来看看吧~", bean.SupplierIcon);
                }
                break;
            case R.id.tv_sp://商品列表
                tvSp.setTextColor(Color.parseColor("#323231"));
                tvXx.setTextColor(Color.parseColor("#929292"));
                tvEvaluate.setTextColor(Color.parseColor("#929292"));
                llProduct.setVisibility(View.VISIBLE);
                llStoreInfo.setVisibility(View.GONE);
                rlBottomBar.setVisibility(View.VISIBLE);
                liEvaluate.setVisibility(View.GONE);//评价
                break;
            case R.id.tv_evaluate://评价
                tvSp.setTextColor(Color.parseColor("#929292"));
                tvXx.setTextColor(Color.parseColor("#929292"));
                tvEvaluate.setTextColor(Color.parseColor("#323231"));
                liEvaluate.setVisibility(View.VISIBLE);//评价
                llStoreInfo.setVisibility(View.GONE);//商家信息
                rlBottomBar.setVisibility(View.GONE);//底部
                llProduct.setVisibility(View.GONE);
                break;
            case R.id.tv_xx://商家
                tvEvaluate.setTextColor(Color.parseColor("#929292"));
                tvSp.setTextColor(Color.parseColor("#929292"));
                tvXx.setTextColor(Color.parseColor("#323231"));
                llProduct.setVisibility(View.GONE);
                llStoreInfo.setVisibility(View.VISIBLE);
                rlBottomBar.setVisibility(View.GONE);
                liEvaluate.setVisibility(View.GONE);//评价
                break;
            case R.id.tv_predestine:
                if (!(PartyApp.getAppComponent().getDataManager().getId() != 0)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    getPredetermineListData();
                }

                break;

        }
    }

    private void updataDataToDb(ScrollBean beans) {

        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();

        CommTenant commTenant = commTenantDao.load(beans.itemBean.CommTenantId);
        if (commTenant != null) {
            commTenant.setSupplierId(id);
            commTenant.setCommTenantIcon(beans.itemBean.CommTenantIcon);
            commTenant.setCommTenantId(beans.itemBean.CommTenantId);
            commTenant.setCommTenantName(beans.itemBean.CommTenantName);
            commTenant.setTotal(beans.itemBean.total);
            commTenant.setPrice(beans.itemBean.Price);
            commTenant.setInventory(beans.itemBean.Inventory);
            try {
                commTenantDao.update(commTenant);
                SgLog.d("更新成功");
            } catch (Exception e) {
                SgLog.d("更新失败");
            }
        } else {
            commTenant = new CommTenant();
            commTenant.setSupplierId(id);
            commTenant.setPrice(beans.itemBean.Price);
            commTenant.setCommTenantIcon(beans.itemBean.CommTenantIcon);
            commTenant.setCommTenantId((long) beans.itemBean.CommTenantId);
            commTenant.setCommTenantName(beans.itemBean.CommTenantName);
            commTenant.setTotal(beans.itemBean.total);
            commTenant.setInventory(beans.itemBean.Inventory);
            commTenantDao.insert(commTenant);
        }
        SgLog.d("数据：" + new Gson().toJson(commTenantDao.loadAll()));
    }

    private void deleteDataInDb(ScrollBean beans) {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        CommTenant commTenant = commTenantDao.load(beans.itemBean.CommTenantId);
        if (commTenant != null) {
            if (commTenant.total > 1) {
                commTenant.setSupplierId(id);
                commTenant.setPrice(beans.itemBean.Price);
                commTenant.setCommTenantIcon(beans.itemBean.CommTenantIcon);
                commTenant.setCommTenantId((long) beans.itemBean.CommTenantId);
                commTenant.setCommTenantName(beans.itemBean.CommTenantName);
                commTenant.setTotal(beans.itemBean.total);
                try {
                    commTenantDao.update(commTenant);
                    SgLog.d("删除成功");
                } catch (Exception e) {
                    SgLog.d("删除失败");
                }
            } else {
                try {
                    SgLog.d("删除成功");
                    commTenantDao.delete(commTenant);
                } catch (Exception e) {
                    SgLog.d("删除失败");
                }
            }
        }


    }

    private void setBoomMenu() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        List<CommTenant> list = commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(id)).list();
        if (list.size() > 0) {
            tvNum.setText(list.size() + "");
            tvNum.setVisibility(View.VISIBLE);
//            tvPay.setBackgroundResource(R.drawable.shape_soild_red_5_bg);
            tvPay.setBackgroundColor(getResources().getColor(R.color.app_red));
            tvPay.setEnabled(true);
            double price = 0;
            for (int i = 0; i < list.size(); i++) {
                price += (list.get(i).Price * list.get(i).total);
            }
            tvNoOne.setVisibility(View.GONE);
            tvPrice.setVisibility(View.VISIBLE);
            tvPrice.setText("¥" + StrUtils.moenyToDH(price + ""));
        } else {
            tvNoOne.setVisibility(View.VISIBLE);
            tvPrice.setVisibility(View.GONE);
            tvNum.setVisibility(View.GONE);
//            tvPay.setBackgroundResource(R.drawable.shape_soild_gray_5_bg);
            tvPay.setBackgroundColor(getResources().getColor(R.color.gray));
            tvPay.setEnabled(false);
        }
    }

    private List<CommTenant> query() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
//        commTenantDao.load(Long.parseLong(bean.SupplierId+""));

        return commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(id)).list();
    }

    private void querInit() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        List<CommTenant> datas = commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(id)).list();
        if (datas.size() > 0) {
            for (int i = 0; i < bean.CommTenantResultList.size(); i++) {
                System.out.println(bean.CommTenantResultList.get(i).CommTenantList.size() + "");
                for (int j = 0; j < datas.size(); j++) {
                    for (int k = 0; k < bean.CommTenantResultList.get(i).CommTenantList.size(); k++) {
                        if (datas.get(j).CommTenantId.longValue() == bean.CommTenantResultList.get(i).CommTenantList.get(k).CommTenantId.longValue()) {
                            bean.CommTenantResultList.get(i).CommTenantList.get(k).total = datas.get(j).total;
                            bean.CommTenantResultList.get(i).CommTenantList.get(k).setSupplierId(datas.get(j).getSupplierId());
                        } else {
//                        bean.CommTenantResultList.get(i).CommTenantList.get(k).total = 0;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < bean.CommTenantResultList.size(); i++) {
                for (int k = 0; k < bean.CommTenantResultList.get(i).CommTenantList.size(); k++) {
                    bean.CommTenantResultList.get(i).CommTenantList.get(k).total = 0;
                }
            }
        }
        left.clear();
        right.clear();
        initData(bean);
        if (null != leftAdapter) {
            leftAdapter.cleanTv();
        }
        initLeft(FoodShopActivity.this);
        initRight(FoodShopActivity.this);
        setBoomMenu();
        avi.setVisibility(View.GONE);
    }

    private void toPay() {
        bundle = new Bundle();
        bundle.putString("SupplierIcon", bean.SupplierIcon);
        bundle.putInt("SupplierId", id);
        bundle.putString("Address", bean.Address);
        bundle.putString("distance", distance);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("Latitude1", bean.Latitude);
        bundle.putString("Longitude1", bean.Longitude);
        bundle.putString("SupplierName", bean.SupplierName);
        bundle.putInt("storeId", id);
        bundle.putString("ServiceTel", bean.ServiceTel);
//        JumpUtils.dataJump(this, OrderActivity.class, bundle, false);
        JumpUtils.dataJump(this, FoodCommitOrderActivity.class, bundle, false);
    }

    private void goMap() {
        bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("Latitude1", bean.Latitude);
        bundle.putString("Longitude1", bean.Longitude);
        bundle.putString("address", bean.Address);
        if (!StrUtils.isEmpty(bean.Latitude)) {
            JumpUtils.dataJump(this, MapActivity.class, bundle, false);
        } else {
            dialogUtils.noBtnDialog("店铺地址不详，无法查看地图");
        }
    }

    private void followShop() {
        RequestClient.FavoriteShop(bean.SupplierId + "", this, new NetSubscriber<BaseResultBean<String>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<String> model) {
                if (model.data.equals("0")) {
                    llFollow.setBackgroundResource(R.drawable.collotion_bg);
//                    tvIsFollow.setText("未关注");
                } else {
                    llFollow.setBackgroundResource(R.drawable.follow_bg);
//                    tvIsFollow.setText("已关注");
                }
            }
        });
    }

    private List<CouponsBean> couponsData = new ArrayList<>();
    private boolean isFrist = true;

    private void getCoupons() {
        RequestClient.GetSupplierCoupons(id, this, new NetSubscriber<BaseResultBean<List<CouponsBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<CouponsBean>> model) {
                if (model.data.size() > 0) {
                    for (int i = 0; i < model.data.size(); i++) {
                        model.data.get(i).itemType = 2;
                    }
                    CouponsBean couponsTitle = new CouponsBean(CouponsBean.COUPONS_TITLE);
                    couponsData.add(couponsTitle);
                    couponsData.addAll(model.data);
                    couponsAdapter.setNewData(couponsData);
                }
                if (couponsData.size() <= 0) {
                    llCoupons.setVisibility(View.GONE);
                }
                banner.setImages(bannerUrls);
                banner.start();
                if (goodsId != 0 && isFrist) {
                    showFragment();
                    goodsId = 0;
                    isFrist = false;
                }
            }
        });
    }

    private void showFragment() {
        bundle = new Bundle();
        bundle.putLong("id", goodsId);
        fragment = new SupermarkDialogFragment();
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "details");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public abstract static class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

        public enum State {
            EXPANDED,
            COLLAPSED,
            IDLE
        }

        private State mCurrentState = State.IDLE;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                mCurrentState = State.COLLAPSED;
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE);
                }
                mCurrentState = State.IDLE;
            }
        }

        public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
    }

    private List<CouponsBean> bannerListBeans = new ArrayList<>();
    private List<CouponsBean> producBannertListBeans = new ArrayList<>();
    private List<String> bannerUrls = new ArrayList<>();

    private void getBanner() {
        RequestClient.BannerGetList(id, this, new NetSubscriber<BaseResultBean<StoreBannerBean>>(this) {
            @Override
            public void onResultNext(BaseResultBean<StoreBannerBean> model) {

                bannerListBeans = model.data.B;
                producBannertListBeans = model.data.S;
                if (producBannertListBeans.size() > 0) {
                    CouponsBean procuctTitle = new CouponsBean(CouponsBean.PRODUCT_TITLE);
                    couponsData.add(procuctTitle);
                    for (int i = 0; i < producBannertListBeans.size(); i++) {
                        producBannertListBeans.get(i).itemType = CouponsBean.PRODUCT;
                    }
                    couponsData.addAll(producBannertListBeans);
                }
                for (int i = 0; i < bannerListBeans.size(); i++) {
                    bannerUrls.add(bannerListBeans.get(i).ShowImg);
                }
                getCoupons();
            }
        });
    }

    private void recevierCoupons(int couponid) {
        RequestClient.ReceiveCoupons(couponid, id, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                Toast.makeText(FoodShopActivity.this, "领取成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bannerClick(int id, Map<String, String> map, int type) {
        RequestClient.BannerClick(id, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                jump(map, type);
            }
        });
    }

    private void jump(Map<String, String> map, int type) {
        if (type == 1) {
            bundle = new Bundle();
            bundle.putInt("stroeId", Integer.parseInt(map.get("shopid")));
            bundle.putInt("goodsId", Integer.parseInt(map.get("goodsid")));
            if (map.get("storeType").equals("6")) {
                JumpUtils.dataJump(FoodShopActivity.this, FoodShopActivity.class, bundle, false);
            } else {
                JumpUtils.dataJump(FoodShopActivity.this, ProductDetailsActivity.class, bundle, false);
            }

        } else {
            bundle = new Bundle();
            bundle.putInt("stroeId", Integer.parseInt(map.get("shopid")));
            if (map.get("storeType").equals("6")) {
                JumpUtils.dataJump(FoodShopActivity.this, FoodShopActivity.class, bundle, false);
            } else {
                JumpUtils.dataJump(FoodShopActivity.this, ShopDetailsActivity.class, bundle, false);

            }
        }

    }

    private void saveShopInfo() {
        SuperMarketBeanDao marketBeanDao = DBManager.getDaoSession().getSuperMarketBeanDao();
//        List<SuperMarketBean> beanList = marketBeanDao.loadAll();
        //根据type查询商超市多店铺
        List<SuperMarketBean> beanList = marketBeanDao.queryBuilder().where(SuperMarketBeanDao.Properties.ShopType.eq(shopType)).list();
        SuperMarketBean market = marketBeanDao.queryBuilder().where(SuperMarketBeanDao.Properties.SupplierId.eq(bean.SupplierId)).unique();
        SuperMarketBean marketBean = new SuperMarketBean();
        marketBean.superMarketId = (long) bean.SupplierId;
        marketBean.SupplierId = bean.SupplierId;
        marketBean.Address = bean.Address;
        marketBean.Icon = bean.SupplierIcon;
        marketBean.SupplierName = bean.SupplierName;
        marketBean.shopType = shopType;
        marketBean.itemType = SuperMarketBean.CATCHDATE;
        marketBean.insertTime = String.valueOf(System.currentTimeMillis());
        if (beanList.size() < 3) {
            if (null != market) {
                marketBeanDao.update(marketBean);
            } else {
                marketBeanDao.insert(marketBean);
            }
        } else {
            if (null != market) {
                marketBeanDao.update(marketBean);
            } else {
                marketBeanDao.delete(beanList.get(2));
                marketBeanDao.insert(marketBean);
            }
        }
    }

    private List<FoodPredeterminelistBean> data = new ArrayList<>();

    private void getPredetermineListData() {
        RequestClient.GetPredeterminerList(id, this, new NetSubscriber<BaseResultBean<List<FoodPredeterminelistBean>>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<FoodPredeterminelistBean>> model) {
                data = model.data;
                if (null != data && data.size() > 0) {
                    bundle = new Bundle();
                    bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) data);
                    bundle.putInt("storeId", id);
                    fragment1 = new FoodkDialogFragment();
                    fragment1.setArguments(bundle);
                    fragment1.show(getSupportFragmentManager(), "food");
                } else {
                    bundle = new Bundle();
                    bundle.putInt("storeId", id);
                    JumpUtils.dataJump(FoodShopActivity.this, FoodPredetermineStepOneActivity.class, bundle, false);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != fragment1) {
            fragment1.dismiss();
        }
    }
}
