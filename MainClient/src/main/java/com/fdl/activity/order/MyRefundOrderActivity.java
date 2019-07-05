package com.fdl.activity.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.adapter.MyRefundOrderAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.RefundOrderBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class MyRefundOrderActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MyRefundOrderAdapter adapter;
    private int index = 1;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_refuseorder_layout);
    }

    @Override
    public void setUpViews() {

        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("退款/售后");
    }

    @Override
    public void setUpLisener() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index = index + 1;
                getDataOnCreate();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetRefundOrderList(index, 10, this, new NetSubscriber<BaseResultBean<List<RefundOrderBean>>>(this, false) {
            @Override
            public void onResultNext(BaseResultBean<List<RefundOrderBean>> model) {
                avi.setVisibility(View.GONE);
                if (model.data.size() <= 0) {
                    avi.setVisibility(View.VISIBLE);
                    avi1.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                } else {
                    fillView(model.data);
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

    private void fillView(List<RefundOrderBean> bean) {
        if(index == 1){
        adapter = new MyRefundOrderAdapter(this, R.layout.item_refund_layout, bean);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        }else {
            adapter.addData(bean);
        }
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderNo",bean.get(position).OrderNo);
                JumpUtils.dataJump(MyRefundOrderActivity.this,RefundOrderDetailsActivity.class,bundle,false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
