package com.fdl.activity.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseFragment;
import com.fdl.adapter.MyOrderAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.MyOrderBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.JumpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/5<p>
 * <p>changeTime：2019/1/5<p>
 * <p>version：1<p>
 */

public class OrderFragment extends BaseFragment implements MyOrderAdapter.CancelOnClick, MyOrderAdapter.ComfireGet, MyOrderAdapter.setIsFrist {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private Bundle bundle;
    private int type;
    private MyOrderAdapter adapter;
    private List<MyOrderBean> myOrderBeanList = new ArrayList<>();
    private int index = 1;
    private boolean isFresh = false;
    private boolean isLoad = false;
    private boolean isFrist = true;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        getData();
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_order_layout;
    }

    @Override
    public void setUpViews(View view) {
        tvTip.setText("您还没有相关的订单");
        refreshLayout.setEnableScrollContentWhenLoaded(true);
    }

    @Override
    public void setUpLisener() {
        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index = index + 1;
                isFresh = false;
                isLoad = true;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                isFresh = true;
                isLoad = false;
                getData();
            }
        });


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
    }


    private void getData() {
        RequestClient.GetOrderList(index, 10, type, getContext(), new NetSubscriber<BaseResultBean<List<MyOrderBean>>>(getActivity(), false) {
            @Override
            public void onResultNext(BaseResultBean model) {
                avi.setVisibility(View.GONE);
                if (index == 1) {
                    myOrderBeanList = (List<MyOrderBean>) model.data;
                    if (((List<MyOrderBean>) model.data).size() <= 0) {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                    setRecyclerView();
                } else {
                    adapter.addData((List<MyOrderBean>) model.data);
                }

                if (isFresh) {
                    refreshLayout.finishRefresh();
                }
                if (isLoad) {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    private void setRecyclerView() {
        adapter = new MyOrderAdapter(getContext(), R.layout.item_myorder_layout, myOrderBeanList);
        adapter.setCancelOnClick(this);
        adapter.setComfireGet(this);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                isFrist = false;
                bundle = new Bundle();
                bundle.putString("orderid", myOrderBeanList.get(position).Id + "");
                bundle.putString("subOrderNo", myOrderBeanList.get(position).OrderNo + "");//注意:我的订单过去用subOrderNo
                JumpUtils.dataJump(getActivity(), MyOrderDetailsActivity.class, bundle, false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setSetIsFrist(this);
    }

    public boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFrist && isVisible) {
            avi.setVisibility(View.VISIBLE);
            index = 1;
            getData();
        }
    }

    @Override
    public void cancle() {
        if (isVisible) {
            index = 1;
            getData();
        }
    }

    @Override
    public void comfire() {
        if (isVisible) {
            index = 1;
            getData();
        }
    }

    @Override
    public void isFrist(boolean isFrist) {
        this.isFrist = isFrist;
    }
}
