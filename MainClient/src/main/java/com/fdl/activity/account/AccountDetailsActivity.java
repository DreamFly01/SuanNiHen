package com.fdl.activity.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.adapter.AccountDetailsAdapter;
import com.fdl.bean.AccountDetailsBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.wedgit.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/18<p>
 * <p>changeTime：2019/1/18<p>
 * <p>version：1<p>
 */
public class AccountDetailsActivity extends BaseActivity {
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

    private int index = 1;
    private int type;
    private Bundle bundle;
    private AccountDetailsAdapter adapter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_accountdetails_layout);
        bundle = getIntent().getExtras();

        if (null != bundle) {
            type = bundle.getInt("type");
        }
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        heardTitle.setTextColor(Color.WHITE);
        switch (type) {
            case 0:
                heardTitle.setText("余额明细");
                break;
            case 1:
                heardTitle.setText("待返金额");
                break;
            case 2:
                heardTitle.setText("我的积分");
                break;
        }
        setRecyclerView();
    }

    @Override
    public void setUpLisener() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index += 1;
                switch (type) {
                    case 0:
//                        getBalancelog();
                        break;
                    case 1:
                        getbargainuserlog();

                        break;
                    case 2:
                        getintegrallog();

                        break;
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                switch (type) {
                    case 0:
//                        getBalancelog();
                        break;
                    case 1:
                        getbargainuserlog();
                        break;
                    case 2:
                        getintegrallog();

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

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        switch (type) {
            case 0:
//                getBalancelog();
                break;
            case 1:
                getbargainuserlog();
                break;
            case 2:
                getintegrallog();
                break;
        }
    }

//    //余额明细
//    private void getBalancelog() {
//        addSubscription(RequestClient.GetBalancelog(index, this, new NetSubscriber<BaseResultBean<List<AccountDetailsBean>>>(this) {
//            @Override
//            public void onResultNext(BaseResultBean<List<AccountDetailsBean>> model) {
//                avi.setVisibility(View.GONE);
//
//                if (index == 1) {
//                    if (model.data.size() > 0) {
//                    } else {
//                        avi.setVisibility(View.VISIBLE);
//                        avi1.setVisibility(View.GONE);
//                        llNoData.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    adapter.addDatas(model.data);
//                    refreshLayout.finishLoadMore();
//                }
//            }
//        }));
//    }

    private List<AccountDetailsBean> datas = new ArrayList<>();

    //积分明细
    private void getintegrallog() {
        addSubscription(RequestClient.Getintegrallog(index, this, new NetSubscriber<BaseResultBean<List<AccountDetailsBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<AccountDetailsBean>> model) {
                avi.setVisibility(View.GONE);

                if (index == 1) {
                    if (model.data.size() > 0) {
                        datas = model.data;
                        adapter.setNewData(datas);
                    } else {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                        adapter.setNewData(null);
                    }
                } else {
                    if (model.data.size() > 0) {
                        datas.addAll(model.data);
                        adapter.setNewData(datas);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        }));
    }

    //待返金额明细
    private void getbargainuserlog() {
        addSubscription(RequestClient.Getbargainuserlog(index, this, new NetSubscriber<BaseResultBean<List<AccountDetailsBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<AccountDetailsBean>> model) {
                avi.setVisibility(View.GONE);

                if (index == 1) {
                    if (model.data.size() > 0) {
                        datas = model.data;
                        adapter.setNewData(datas);
                    } else {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                        adapter.setNewData(null);
                    }
                } else {
                    if(model.data.size()>0){
                    datas.addAll(model.data);
                    adapter.setNewData(datas);
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
                    refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
            }
        }));
    }

    private void setRecyclerView() {
        adapter = new AccountDetailsAdapter(R.layout.item_accoutdetails_layout, null);
        adapter.setType(type);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }
}
