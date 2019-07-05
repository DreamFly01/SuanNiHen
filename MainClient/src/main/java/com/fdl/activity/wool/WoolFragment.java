package com.fdl.activity.wool;

import android.annotation.SuppressLint;
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

import com.fdl.BaseFragment;
import com.fdl.adapter.WoolAdapter;
import com.fdl.adapter.WoolClockAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.WoolBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
@SuppressLint("ValidFragment")
public class WoolFragment extends BaseFragment {

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
    Unbinder unbinder;
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    private Bundle bundle;
    private int type;
    //    private WoolAdapter adapter;
    private WoolClockAdapter adapter;
    private List<WoolBean> myWoolBeanList = new ArrayList<>();
    private int index = 1;
    private boolean isFresh = false;
    private boolean isLoad = false;
    private boolean isFrist = true;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
    }


    @Override
    public int initContentView() {
        return R.layout.fragment_order_layout;
    }

    @Override
    public void setUpViews(View view) {
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

    private void getData() {
        int type1 = 0;
        if (type == 1) {
            type1 = 2;
        }
        if (type == 2) {
            type1 = 1;
        }
        RequestClient.GetHymList(type1, index, getContext(), new NetSubscriber<BaseResultBean<List<WoolBean>>>(getActivity(), false) {
            @Override
            public void onResultNext(BaseResultBean<List<WoolBean>> model) {
                avi.setVisibility(View.GONE);
                avAvi.hide();
                if (index == 1) {
                    myWoolBeanList = model.data;
                    if ((model.data).size() <= 0) {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                    setRecyclerView();
                } else {
                    if (model.data.size() > 0) {
                        myWoolBeanList.addAll(model.data);
//                        adapter.addData(model.data);
                        adapter.setNewData(myWoolBeanList);
                    }else {
//                        refreshLayout.setNoMoreData(true);
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
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

    private void setRecyclerView() {
        adapter = new WoolClockAdapter(getContext(), myWoolBeanList);
        adapter.setOnClick(new WoolClockAdapter.onItemClick() {
            @Override
            public void onClick(int position) {
                bundle = new Bundle();
                bundle.putInt("id", myWoolBeanList.get(position).Id);
                JumpUtils.dataJump(getActivity(), WoolDetailsActivity.class, bundle, false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            getData();
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
    public void onDestroy() {
        super.onDestroy();
        if (null != adapter) {
            adapter.cancelAllTimers();
        }
    }
}
