package com.fdl.activity.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.activity.supermarket.SupermarkDialogFragment;
import com.fdl.adapter.ScrollLeftAdapter;
import com.fdl.adapter.ScrollRightAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.FoodGoodsCommitBean;
import com.fdl.bean.OrdersData;
import com.fdl.bean.ScrollBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.bean.daoBean.CommTenantType;
import com.fdl.bean.daoBean.SupplierBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.StarBarView;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
public class FoodAddGoodsActivity extends FragmentActivity {


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
    //    @BindView(R.id.tv_Promotions)
//    TextView tvPromotions;
    @BindView(R.id.ll_storeInfo)
    LinearLayout llStoreInfo;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    private ImmersionBar immersionBar;
    private List<String> left;
    private List<ScrollBean> right;
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
    private DialogUtils dialogUtils;
    private SupplierBean bean;


    private SupermarkDialogFragment fragment;

    private int goodsId;
    private int shopType;
    private List<OrdersData> ordersData = new ArrayList<>();
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add_goods_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getInt("storeId");
            goodsId = bundle.getInt("goodsId");
            shopType = bundle.getInt("shopType");
            orderNo = bundle.getString("orderNo");
            ordersData = bundle.getParcelableArrayList("data");
        }

        ButterKnife.bind(this);
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarDarkFont(true);
        immersionBar.statusBarColor(R.color.white);
        immersionBar.init();
        setUpViews();
        dialogUtils = new DialogUtils(this);
    }


    public void setUpViews() {
        heardMenu.setVisibility(View.VISIBLE);
        heardTitle.setText("添加菜品");
        getData();
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

    private void getData() {
        avi.setVisibility(View.VISIBLE);
        RequestClient.GetSuperMarketCommTenant1(id, "", this, new NetSubscriber<BaseResultBean<SupplierBean>>(this) {
            @Override
            public void onResultNext(BaseResultBean<SupplierBean> model) {
                bean = model.data;
                avi.setVisibility(View.GONE);
                initData(model.data);
                initLeft(FoodAddGoodsActivity.this);
                initRight(FoodAddGoodsActivity.this);
            }
        });
    }

    //初始化右边数据
    private void initRight(Context mContext) {
        rightManager = new LinearLayoutManager(this);
        if (rightAdapter == null) {
            rightAdapter = new ScrollRightAdapter(R.layout.item_right_layout, R.layout.layout_right_title, null);
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
                            int num = right.get(position - 1).itemBean.total += 1;
                            updata(right.get(position-1).itemBean.CommTenantId.longValue(),num);

                        }
                        break;
                    case R.id.iv_delete:
                        int num =right.get(position - 1).itemBean.total -= 1;
                        updata(right.get(position-1).itemBean.CommTenantId.longValue(),num);

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

    private void updata(long id, int num) {
        for (int i = 0; i < right.size(); i++) {
            if (null!=right.get(i).itemBean&&right.get(i).itemBean.CommTenantId.longValue() == id) {
                right.get(i).itemBean.total = num;
            }
        }
        rightAdapter.setNewData(right);
        setTvPrice();
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
        left = new ArrayList<>();
        right = new ArrayList<>();
        List<ScrollBean> list = new ArrayList<>();
        ScrollBean scrollBean = null;
        ScrollBean.ScrollItemBean scrollItemBean;
        for (CommTenantType commTenantType : bean.CommTenantResultList) {
            for (CommTenant commTenant : commTenantType.CommTenantList) {
                for (OrdersData ordersDatum : ordersData) {
                    if (commTenant.CommTenantId.longValue() == ordersDatum.GoodsId) {
                        commTenant.total = ordersDatum.GoodsNum2;
                    }
                }
            }
        }
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


    @OnClick({R.id.heard_back, R.id.tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_pay:
                setReturnData();
                break;
        }
    }

    List<OrdersData> returnData = new ArrayList<>();

    private void setReturnData() {
        for (int i = 0; i < right.size(); i++) {
            if (null != right.get(i).itemBean && right.get(i).itemBean.total > 0) {
                OrdersData bean = new OrdersData();
                bean.GoodsId = (right.get(i).itemBean.CommTenantId).intValue();
                bean.Image = right.get(i).itemBean.CommTenantIcon;
                bean.SalesPrice = right.get(i).itemBean.Price;
                bean.GoodsNum = 0;
                bean.GoodsNum2 = right.get(i).itemBean.total;
                bean.GoodsName = right.get(i).itemBean.CommTenantName;
                returnData.add(bean);
            }
        }
        if (returnData.size() > 0) {
            setCommitData();
        } else {
            dialogUtils.noBtnDialog("没有新增任何菜品");
        }
    }

    HashMap<Integer, FoodGoodsCommitBean> listMap = new HashMap<>();
    List<FoodGoodsCommitBean> goodsList1 = new ArrayList<>();

    private void setCommitData() {
        if (ordersData.size() > 0) {
            for (OrdersData returnDatum : returnData) {
                for (OrdersData ordersDatum : ordersData) {
                    FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
                    bean.GoodsId = returnDatum.GoodsId;
                    bean.GoodsNum = (ordersDatum.GoodsNum + returnDatum.GoodsNum2);
                    bean.GoodsNum2 = 0;
                    bean.GoodsNormId = 0;
                    if (returnDatum.GoodsId == ordersDatum.GoodsId) {
                        bean.OrderId = ordersDatum.Id;
                    } else {
                        bean.OrderId = 0;
                    }
                    listMap.put(returnDatum.GoodsId, bean);
                }
            }
        } else {
            for (OrdersData returnDatum : returnData) {
                FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
                bean.GoodsId = returnDatum.GoodsId;
                bean.GoodsNum = returnDatum.GoodsNum2;
                bean.GoodsNormId = 0;
                bean.OrderId = 0;
                bean.GoodsNum2 = 0;
                listMap.put(returnDatum.GoodsId, bean);
            }
        }

        for (Integer integer : listMap.keySet()) {
            goodsList1.add(listMap.get(integer));
        }
        commitData();
    }

    private void commitData() {
        if (StrUtils.isEmpty(orderNo)) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) returnData);
            setResult(1002, intent);
            FoodAddGoodsActivity.this.finish();
        } else {
            RequestClient.ChangePredeterminerOrder(orderNo, 1, goodsList1, this, new NetSubscriber<BaseResultBean>(this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) returnData);
                    setResult(1002, intent);
                    FoodAddGoodsActivity.this.finish();
                }
            });
        }

    }

    private double totalPrice;

    private Map<Long,ScrollBean.ScrollItemBean> priceMap = new TreeMap<>();
    private List<ScrollBean.ScrollItemBean> priceList = new ArrayList<>();

    private void setTvPrice() {
        totalPrice = 0;
        priceList.clear();
        for (int i = 0; i < right.size(); i++) {
            if (null != right.get(i).itemBean && right.get(i).itemBean.total > 0) {
                priceMap.put(right.get(i).itemBean.CommTenantId,right.get(i).itemBean);
            }
        }
        for (Object o : priceMap.keySet()) {
            priceList.add( priceMap.get(o));
        }
        for (int i = 0; i < priceList.size(); i++) {
                totalPrice += (priceList.get(i).Price * priceList.get(i).total);
        }
        tvPrice.setText("共¥" + StrUtils.moenyToDH(totalPrice + ""));
    }
}
