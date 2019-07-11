package com.fdl.activity.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.fdl.adapter.CouponsAdapter;
import com.fdl.adapter.MyCouponsAdapter;
import com.fdl.adapter.StoreCouponsAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CouponsBean;
import com.fdl.bean.MyCouponsBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.db.DBManager;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.ToastUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/13<p>
 * <p>changeTime：2019/5/13<p>
 * <p>version：1<p>
 */
public class StoreCouponsActivity extends BaseActivity {
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

    private int storeId, SupplierId;
    private Bundle bundle;
    private StoreCouponsAdapter couponsAdapter;
    private double money;
    private int from;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_storecoupons_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            storeId = bundle.getInt("storeId");
            SupplierId = bundle.getInt("SupplierId");
            money = bundle.getDouble("money");
            from = bundle.getInt("from");
        }
    }

    @Override
    public void setUpViews() {
        if (ImmersionBar.hasNotchScreen(this)) {
            IsBang.setImmerHeard(this, rlHead);
        }
        heardTitle.setText("可使用优惠券");
//        heardTvMenu.setText("确定");
//        heardTvMenu.setTextColor(this.getResources().getColor(R.color.white));
        couponsAdapter = new StoreCouponsAdapter(R.layout.item_coupon_layout, null);
        couponsAdapter.setFrom(1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL, R.drawable.line_gray));
        recyclerView.setAdapter(couponsAdapter);
        queryPruduct();
    }

    private List<CouponsBean> choseDatas = new ArrayList<>();

    @Override
    public void setUpLisener() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        couponsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_is_select:
                        if (check(position)) {
//                            if (datas.get(position).isSelect) {
//                                datas.get(position).isSelect = false;
//                            } else {
//                                datas.get(position).isSelect = true;
//                            }
//                            couponsAdapter.notifyItemChanged(position, datas.get(position));
//                            couponsAdapter.notifyDataSetChanged();

                        }
                        break;
                    case R.id.ll_item:
//                        if (check(position)) {
//                            Intent intent = new Intent();
//                            intent.putExtra("couponsId",datas.get(position).CouponId);
//                            intent.putExtra("couponsInfo",datas.get(position).CouponName);
//                            intent.putExtra("CouponValue",datas.get(position).CouponValue);
//                            setResult(1001,intent);
//                            StoreCouponsActivity.this.finish();
//                        }
                        if (datas.get(position).UseState != 2) {
                            Intent intent = new Intent();
                            intent.putExtra("couponsId", datas.get(position).CouponId);
                            intent.putExtra("couponsInfo", datas.get(position).CouponName);
                            intent.putExtra("CouponValue", datas.get(position).CouponValue);
                            setResult(1001, intent);
                            StoreCouponsActivity.this.finish();
                        } else {
                            ToastUtils.toast("此订单没有满足使用该优惠劵的条件");
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.heard_back, R.id.heard_tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.heard_tv_menu:

                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).isSelect) {
                        choseDatas.add(datas.get(i));
                    }
                }
                if (choseDatas.size() > 0) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) choseDatas);
                    setResult(1001, intent);
                    StoreCouponsActivity.this.finish();
                } else {
                    ToastUtils.toast("您还没选择任何优惠券");
                }
                break;
        }
    }

    private List<CouponsBean> datas = new ArrayList<>();

    private void getData() {
        addSubscription(RequestClient.GetUseCoupon(storeId, this, new NetSubscriber<BaseResultBean<List<CouponsBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<CouponsBean>> model) {
                avi.setVisibility(View.GONE);
                if (model.data.size() > 0) {
                    datas = model.data;
                    for (int i = 0; i < datas.size(); i++) {
                        choseProduct.clear();
                        if (datas.get(i).CouponWay == 2) {
                            for (int j = 0; j < productData.size(); j++) {
                                productNames = Arrays.asList(datas.get(i).GoodsNames2.split("\\|&\\|"));
                                for (String name : productNames) {
                                    if (productData.get(j).CommTenantName.equals(name)) {
                                        choseProduct.add(productData.get(j));
                                    }
                                }
                            }
                            double totalPrice = 0;
                            if (choseProduct.size() > 0) {
                                for (int k = 0; k < choseProduct.size(); k++) {
                                    totalPrice += choseProduct.get(k).Price * choseProduct.get(k).total;
                                }
                                if (totalPrice >= datas.get(i).ConditionValue) {
//                                       datas.get(i).UseState = 2;
                                } else {
                                    datas.get(i).UseState = 2;
                                }
                            } else {
                                datas.get(i).UseState = 2;
                            }
                        } else {
                            if (money >= datas.get(i).ConditionValue) {
//                                return true;
                            } else {
                                datas.get(i).UseState = 2;
                            }
                        }
                    }
                    couponsAdapter.setNewData(model.data);
                } else {
                    couponsAdapter.setNewData(null);
                    couponsAdapter.setEmptyView(R.layout.empty_layout, recyclerView);
                }
            }
        }));
    }

    private List<String> productNames = new ArrayList<>();
    private List<CommTenant> choseProduct = new ArrayList<>();

    private boolean check(int postion) {
        choseProduct.clear();
        if (datas.get(postion).CouponWay == 2) {
            for (int i = 0; i < productData.size(); i++) {
                productNames = Arrays.asList(datas.get(postion).GoodsNames2.split("\\|&\\|"));
                for (String name : productNames) {
                    if (productData.get(i).CommTenantName.equals(name)) {
                        choseProduct.add(productData.get(i));
                    }
                }
                double totalPrice = 0;
                if (choseProduct.size() > 0) {
                    for (int j = 0; j < choseProduct.size(); j++) {
                        totalPrice += choseProduct.get(j).Price * choseProduct.get(j).total;
                    }
                    if (totalPrice >= datas.get(postion).ConditionValue) {
                        return true;
                    } else {
                        ToastUtils.toast("此订单没有满足使用该优惠劵的条件");
                        return false;
                    }
                } else {
                    ToastUtils.toast("此订单没有满足使用该优惠劵的条件");
                    return false;
                }
            }
        } else {
            if (money >= datas.get(postion).ConditionValue) {
                return true;
            } else {
                ToastUtils.toast("此订单没有满足使用该优惠劵的条件");
                return false;
            }
        }
        return false;
    }

    private List<CommTenant> productData = new ArrayList<>();

    private void queryPruduct() {
        if (from == 1) {
            productData = bundle.getParcelableArrayList("data");
        } else {
            CommTenantDao commTenantDao = DBManager.getDaoMaster().newSession().getCommTenantDao();
            productData = commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(SupplierId)).list();
        }
        getData();

    }
}
