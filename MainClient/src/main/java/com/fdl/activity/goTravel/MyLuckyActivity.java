package com.fdl.activity.goTravel;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.adapter.MyLuckyAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.WinningLogUserBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
 * <p>creatTime：2019/5/10<p>
 * <p>changeTime：2019/5/10<p>
 * <p>version：1<p>
 */
public class MyLuckyActivity extends Activity {
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
    private int type = 1;
    private MyLuckyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylucky_layout);
        ButterKnife.bind(this);
        setView();
    }

    ImmersionBar immersionBar;

    private void setView() {
        heardTitle.setText("我的记录");
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarColor(R.color.white);
        if (!ImmersionBar.hasNotchScreen(this)) {
            immersionBar.titleBar(R.id.rl_head);
        } else {
            IsBang.setImmerHeard(this, rlHead, "#ffffff");
        }
        immersionBar.statusBarColor(R.color.white);
        immersionBar.statusBarDarkFont(true);
        immersionBar.init();
        adapter = new MyLuckyAdapter(R.layout.item_mylucky_layout, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL, R.drawable.line));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setListener();
        getData();
    }

    private void setListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index += 1;
                getData();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                getData();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", datas.get(position).LogId);
                JumpUtils.dataJump(MyLuckyActivity.this, MyLuckyDetailsActivity.class, bundle, false);
            }
        });
    }

    private List<WinningLogUserBean> datas = new ArrayList<>();

    private void getData() {
        RequestClient.GetWinningLogForUser(index, type, this, new NetSubscriber<BaseResultBean<List<WinningLogUserBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<WinningLogUserBean>> model) {
                avi.setVisibility(View.GONE);
                if (index == 1) {
                    if (model.data.size() > 0) {
                        datas = model.data;
                        adapter.setNewData(datas);
                    } else {
                        adapter.setNewData(null);
                        adapter.setEmptyView(R.layout.empty_layout, recyclerView);
                    }
                } else {
                    if (model.data.size() > 0) {
                        datas.addAll(model.data);
                        adapter.setNewData(datas);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
            }
        });
    }

    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }
}
