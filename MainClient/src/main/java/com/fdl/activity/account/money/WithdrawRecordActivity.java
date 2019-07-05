//package com.fdl.activity.account.money;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.snh.snhseller.BaseActivity;
//import com.snh.snhseller.R;
//import com.snh.snhseller.adapter.CaptailsAdapter;
//import com.snh.snhseller.bean.BaseResultBean;
//import com.snh.snhseller.bean.CaptailsBean;
//import com.snh.snhseller.bean.MoneyBean;
//import com.snh.snhseller.requestApi.NetSubscriber;
//import com.snh.snhseller.requestApi.RequestClient;
//import com.snh.snhseller.utils.IsBang;
//import com.snh.snhseller.utils.JumpUtils;
//import com.snh.snhseller.utils.StrUtils;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * <p>desc：<p>
// * <p>author：DreamFly<p>
// * <p>creatTime：2019/4/2<p>
// * <p>changeTime：2019/4/2<p>
// * <p>version：1<p>
// */
//public class WithdrawRecordActivity extends BaseActivity {
//    @BindView(R.id.heard_back)
//    LinearLayout heardBack;
//    @BindView(R.id.heard_title)
//    TextView heardTitle;
//    @BindView(R.id.heard_menu)
//    ImageView heardMenu;
//    @BindView(R.id.heard_tv_menu)
//    TextView heardTvMenu;
//    @BindView(R.id.rl_menu)
//    RelativeLayout rlMenu;
//    @BindView(R.id.rl_head)
//    LinearLayout rlHead;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
//    @BindView(R.id.btn_commit)
//    Button btnCommit;
//
//    private CaptailsAdapter adapter;
//    private LinearLayout heard;
//    private TextView tvMoney, tvMoney1, tvMoney2,tvstate;
//    private LayoutInflater inflater = null;
//    private int index = 1;
//    private boolean isShow;
//
//    private double rate,minMoney,maxMoney;
//    private double adMoney;
//
//    @Override
//    protected void initContentView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_withdrawrecord_layout);
//        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public void setUpViews() {
//        heardTitle.setText("账户资金");
//
//        IsBang.setImmerHeard(this, rlHead);
//        heard = (LinearLayout) inflater.inflate(R.layout.heard_captails_layout, null);
//        tvMoney = heard.findViewById(R.id.tv_money);
//        tvMoney1 = heard.findViewById(R.id.tv_money1);
//        tvMoney2 = heard.findViewById(R.id.tv_money2);
//        tvstate = heard.findViewById(R.id.tv_state);
//        adapter = new CaptailsAdapter(R.layout.item_captails_layout, null);
////        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        adapter.addHeaderView(heard);
//    }
//
//    @Override
//    public void setUpLisener() {
//        refreshLayout.setEnableLoadMore(false);
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                isShow = true;
//                index = 1;
//                getData();
//            }
//        });
//        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                isShow = false;
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        index += 1;
//                        getData();
//                    }
//                }, 1000);
//            }
//        }, recyclerView);
//    }
//
//    private List<CaptailsBean> datas;
//    private String money;
//    private void getData() {
//        addSubscription(RequestClient.GetAccountMoney(index, this, new NetSubscriber<BaseResultBean<MoneyBean>>(this, isShow) {
//            @Override
//            public void onResultNext(BaseResultBean<MoneyBean> model) {
//                refreshLayout.finishRefresh();
//                tvMoney.setText(StrUtils.moenyToDH(model.data.HavaMoney + ""));
//                tvMoney1.setText(StrUtils.moenyToDH(model.data.WithdrawDepositMoney + ""));
//                money = model.data.WithdrawDepositMoney+"";
//                rate = model.data.Rate;
//                adMoney = model.data.AdMoney;
//                minMoney = model.data.MinMoney;
//                maxMoney = model.data.MaxMoney;
//                tvMoney2.setText(StrUtils.moenyToDH(model.data.FreezeMoney + ""));
//                if (model.data.MoneyDetail.size() > 0) {
//                    datas = model.data.MoneyDetail;
//                    adapter.setNewData(datas);
//                    adapter.loadMoreComplete();
//                } else {
//                    if (index == 1) {
//                        adapter.setNewData(null);
//                        tvstate.setVisibility(View.GONE);
////                        adapter.setEmptyView(R.layout.empty_layout,recyclerView);
//                    } else {
//                        adapter.loadMoreEnd();
//                    }
//                }
//            }
//        }));
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }
//
//    @OnClick({R.id.heard_back, R.id.btn_commit})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.heard_back:
//                this.finish();
//                break;
//            case R.id.btn_commit:
//                Bundle bundle = new Bundle();
//                bundle.putString("money",money);
//                bundle.putDouble("minMoney",minMoney);
//                bundle.putDouble("maxMoney",maxMoney);
//                bundle.putDouble("rate",rate);
//                bundle.putDouble("adMoney",adMoney);
//                JumpUtils.dataJump(this,WithdrawActivity.class,bundle,false);
//                break;
//
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        index = 1;
//        getData();
//    }
//}
