package com.fdl.activity.main;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseFragment;
import com.fdl.activity.account.AccountMoneyActivity;
import com.fdl.activity.account.AddressActivity;
import com.fdl.activity.account.BrowsHistoryListActivity;
import com.fdl.activity.account.ProductCollectListActivity;
import com.fdl.activity.account.ServerCallActivity;
import com.fdl.activity.account.ShopFollowListActivity;
import com.fdl.activity.coupons.MyCouponsActivity;
import com.fdl.activity.goTravel.TravelActivity;
import com.fdl.activity.merchantEntry.LogingActivity;
import com.fdl.activity.order.MyOrderActivity;
import com.fdl.activity.order.MyRefundOrderActivity;
import com.fdl.activity.set.SetActivity;
import com.fdl.activity.wool.WoolActivity;
import com.fdl.adapter.MainProductAdapter;
import com.fdl.adapter.MenuAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.MenuBean;
import com.fdl.bean.MessageEvent;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.wedgit.DividerItemDecoration;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.uitls.ImmersionOwner;
import com.sg.cj.snh.uitls.ImmersionProxy;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/14<p>
 * <p>changeTime：2019/2/14<p>
 * <p>version：1<p>
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

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
    Unbinder unbinder;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_set)
    ImageView ivSet;
    @BindView(R.id.ll_top1)
    LinearLayout llTop1;

    private LinearLayout llTop;
    private MainProductAdapter adapter;
    private LayoutInflater inflater = null;
    private LinearLayout heard;
    private RecyclerView menuRecycleview;
    private int scrollY = 0;


    private MenuBean menuBean;
    private List<MenuBean> menuBeanList = new ArrayList<>();
    private List<String> menuStrList = new ArrayList<>();
    private List<Integer> imgList = new ArrayList<>();
    private MenuAdapter menuAdapter;

    private LinearLayout ll_order, ll01, ll02, ll03, ll11, ll12, ll13, ll14, ll15;
    private TextView tv01, tv02, tv03, tvN1, tvN2, tvN3, tvN4, tvN5, tvName1, tvGrade;
    private ImageView imgHeard, set;
    private Bundle bundle;
    private DialogUtils dialogUtils;

    @Override
    public int initContentView() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public void setUpViews(View view) {
        EventBus.getDefault().register(this);//注册EventBus
        dialogUtils = new DialogUtils(getContext());
        refreshLayout.setEnableLoadMore(false);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        heard = (LinearLayout) inflater.inflate(R.layout.heard_home_layout, null);
        tvName1 = (TextView) heard.findViewById(R.id.tv_name);
        tvGrade = heard.findViewById(R.id.tv_grade);
        imgHeard = (ImageView) heard.findViewById(R.id.iv_heard_img);
        llTop = heard.findViewById(R.id.ll_top);
        tv01 = heard.findViewById(R.id.tv_01);
        tv02 = heard.findViewById(R.id.tv_02);
        tv03 = heard.findViewById(R.id.tv_03);
        tvN1 = heard.findViewById(R.id.tv_num1);
        tvN2 = heard.findViewById(R.id.tv_num2);
        tvN3 = heard.findViewById(R.id.tv_num3);
        tvN4 = heard.findViewById(R.id.tv_num4);
        tvN5 = heard.findViewById(R.id.tv_num5);
        ll_order = heard.findViewById(R.id.ll_order);
        ll01 = heard.findViewById(R.id.ll_01);
        ll02 = heard.findViewById(R.id.ll_02);
        ll03 = heard.findViewById(R.id.ll_03);
        ll11 = heard.findViewById(R.id.ll_11);
        ll12 = heard.findViewById(R.id.ll_12);
        ll13 = heard.findViewById(R.id.ll_13);
        ll14 = heard.findViewById(R.id.ll_14);
        ll15 = heard.findViewById(R.id.ll_15);
        set = heard.findViewById(R.id.iv_set);
        ImmersionBar.with(getActivity()).titleBar(llTop).init();
        ImmersionBar.with(getActivity()).titleBar(llTop1).init();
        IsBang.setImmerHeard(getContext(), llTop);
        IsBang.setImmerHeard(getContext(), llTop1);
        menuRecycleview = heard.findViewById(R.id.recyclerView_menu);
        setMenu();
        adapter = new MainProductAdapter(R.layout.item_main_layout, null);
        adapter.addHeaderView(heard);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                if (scrollY > llTop.getHeight()) {
                    llTop1.setVisibility(View.VISIBLE);
                } else {
                    llTop1.setVisibility(View.GONE);
                }
            }
        });

        getData();
    }


    @Override
    public void setUpLisener() {
        ll01.setOnClickListener(this::onUserClick);
        ll02.setOnClickListener(this::onUserClick);
        ll03.setOnClickListener(this::onUserClick);
        ll11.setOnClickListener(this::onUserClick);
        ll12.setOnClickListener(this::onUserClick);
        ll13.setOnClickListener(this::onUserClick);
        ll14.setOnClickListener(this::onUserClick);
        ll15.setOnClickListener(this::onUserClick);
        ll_order.setOnClickListener(this::onUserClick);
        set.setOnClickListener(this::onUserClick);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    @Override
    public void onUserClick(View v) {
        super.onUserClick(v);
        switch (v.getId()) {
            case R.id.ll_01:
                JumpUtils.simpJump(getActivity(), ProductCollectListActivity.class, false);
                break;
            case R.id.ll_02:
                JumpUtils.simpJump(getActivity(), ShopFollowListActivity.class, false);
                break;
            case R.id.ll_03:
                JumpUtils.simpJump(getActivity(), BrowsHistoryListActivity.class, false);
                break;
            case R.id.ll_11:
                bundle = new Bundle();
                bundle.putInt("tabType", 1);
                JumpUtils.dataJump(getActivity(), MyOrderActivity.class, bundle, false);
                break;
            case R.id.ll_12:
                bundle = new Bundle();
                bundle.putInt("tabType", 2);
                JumpUtils.dataJump(getActivity(), MyOrderActivity.class, bundle, false);
                break;
            case R.id.ll_13:
                bundle = new Bundle();
                bundle.putInt("tabType", 3);
                JumpUtils.dataJump(getActivity(), MyOrderActivity.class, bundle, false);
                break;
            case R.id.ll_14:
                bundle = new Bundle();
                bundle.putInt("tabType", 4);
                JumpUtils.dataJump(getActivity(), MyOrderActivity.class, bundle, false);
                break;
            case R.id.ll_15:
                JumpUtils.simpJump(getActivity(), MyRefundOrderActivity.class, false);
                break;
            case R.id.ll_order:
                bundle = new Bundle();
                bundle.putInt("tabType", 0);
                JumpUtils.dataJump(getActivity(), MyOrderActivity.class, bundle, false);
                break;
            case R.id.iv_set:
                JumpUtils.simpJump(getActivity(), SetActivity.class, false);
                break;
        }
    }

    private void getData() {
        addSubscription(RequestClient.GetPersonInfo(getContext(), new NetSubscriber<BaseResultBean>(getContext()) {
            @Override
            public void onResultNext(BaseResultBean model) {
                avi.setVisibility(View.GONE);
                refreshLayout.finishRefresh();
                fillView(model);
            }
        }));
    }

    private void setMenu() {
        menuStrList.clear();
        imgList.clear();
        menuBeanList.clear();
        menuStrList.add("薅羊毛");
        menuStrList.add("收货地址");
        menuStrList.add("我的卡包");
        menuStrList.add("优惠券");
        menuStrList.add("带你去旅行");
        menuStrList.add("客服中心");
        menuStrList.add("商家管理");
        imgList.add(R.drawable.img_hym);
        imgList.add(R.drawable.img_shdz);
        imgList.add(R.drawable.img_wdkb);
        imgList.add(R.drawable.img_yhq);
        imgList.add(R.drawable.img_dnqlx);
        imgList.add(R.drawable.img_kfzx);
        imgList.add(R.drawable.img_sjdl);

        for (int i = 0; i < menuStrList.size(); i++) {
            menuBean = new MenuBean();
            menuBean.name = menuStrList.get(i);
            menuBean.imgRes = imgList.get(i);
            menuBeanList.add(menuBean);
        }
        menuAdapter = new MenuAdapter(R.layout.item_menu_home_layout, menuBeanList);
        menuRecycleview.setLayoutManager(new GridLayoutManager(getContext(), 4));
        menuRecycleview.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        JumpUtils.simpJump(getActivity(), WoolActivity.class, false);
                        break;
                    case 1:
                        JumpUtils.simpJump(getActivity(), AddressActivity.class, false);
                        break;
                    case 2:
                        JumpUtils.simpJump(getActivity(), AccountMoneyActivity.class, false);
                        break;
                    case 3:
                        JumpUtils.simpJump(getActivity(), MyCouponsActivity.class, false);
                        break;
                    case 4:
                        bundle = new Bundle();
                        bundle.putInt("type", 2);
                        JumpUtils.dataJump(getActivity(), TravelActivity.class, bundle, false);
                        break;
                    case 5:
                        JumpUtils.simpJump(getActivity(), ServerCallActivity.class, false);
                        break;
                    case 6:
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        /**知道要跳转应用的包命与目标Activity*/
                        try {
                            ComponentName componentName = new ComponentName("com.snh.snhseller", "com.snh.snhseller.WelcomActivity");
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
                }
            }
        });
    }

    private void fillView(BaseResultBean bean) {
        StringBuffer sbf = new StringBuffer(PartyApp.getAppComponent().getDataManager().getWxHeadImg());
        ImageUtils.loadUrlCircleImage(getContext(), sbf.replace(0, 1, "h").toString(), imgHeard);
        tvName.setText(PartyApp.getAppComponent().getDataManager().getWxNickName());
        tvName1.setText(PartyApp.getAppComponent().getDataManager().getWxNickName());
        tvGrade.setText("等级：v" + PartyApp.getAppComponent().getDataManager().getGradeId());
        tv01.setText("商品收藏(" + bean.caregoods + ")");
        tv02.setText("店铺关注(" + bean.careshop + ")");
        if (bean.topay > 0) {
            tvN1.setVisibility(View.VISIBLE);
            tvN1.setText(bean.topay + "");
        } else {
            tvN1.setVisibility(View.GONE);
        }
        if (bean.tosendproduct > 0) {
            tvN2.setVisibility(View.VISIBLE);
            tvN2.setText(bean.tosendproduct + "");
        } else {
            tvN2.setVisibility(View.GONE);
        }
        if (bean.toreceiveproduct > 0) {
            tvN3.setVisibility(View.VISIBLE);
            tvN3.setText(bean.toreceiveproduct + "");
        } else {
            tvN3.setVisibility(View.GONE);
        }
        if (bean.toreview > 0) {
            tvN4.setVisibility(View.VISIBLE);
            tvN4.setText(bean.toreview + "");
        } else {
            tvN4.setVisibility(View.GONE);
        }
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!this.isHidden()) {
            avi.setVisibility(View.VISIBLE);
            getData();
        } else {
            StringBuffer sbf = new StringBuffer(PartyApp.getAppComponent().getDataManager().getWxHeadImg());
            ImageUtils.loadUrlCircleImage(getContext(), sbf.replace(0, 1, "h").toString(), imgHeard);
            tvName.setText(PartyApp.getAppComponent().getDataManager().getWxNickName());
            tvName1.setText(PartyApp.getAppComponent().getDataManager().getWxNickName());
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


    //注意这里sticky=true
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        // UI updates must run on MainThread
        if (event.getMessage().equals("collect") || event.getMessage().equals("storeFollow")) {
            getData();
        }

    }

}
