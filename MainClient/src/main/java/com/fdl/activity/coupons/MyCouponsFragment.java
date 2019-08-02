package com.fdl.activity.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseFragment;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.adapter.MyCouponsAdapter;
import com.fdl.adapter.MyCouponsListAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CouponsBean;
import com.fdl.bean.MyCouponsBean;
import com.fdl.common.manger.ShopJumpManger;
import com.fdl.jpush.Logger;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.wedgit.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.R;
import com.sg.cj.snh.constants.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/27<p>
 * <p>changeTime：2019/4/27<p>
 * <p>version：1<p>
 */
public class MyCouponsFragment extends BaseFragment {
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
    @BindView(R.id.empty_ll)
    LinearLayout empty;
    //    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
    @BindView(R.id.my_coupons_list)
    ListView listView;
    Unbinder unbinder;

    private int type;
    private int index = 1;
    public boolean isVisible;
    private boolean isFrist = true;
    private MyCouponsAdapter adapter;
    private MyCouponsListAdapter listAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        if (type == 0&&isFrist) {
            getData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public int initContentView() {
        return R.layout.fragment_mycoupons_layout;
    }

    @Override
    public void setUpViews(View view) {
//        refreshLayout.setEnableScrollContentWhenLoaded(true);
//        adapter = new MyCouponsAdapter(R.layout.item_mycoupon_layout,null,type,getContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(),LinearLayout.VERTICAL,R.drawable.line_gray));
//        recyclerView.setAdapter(adapter);
        datas = new ArrayList<>();
        listAdapter = new MyCouponsListAdapter(datas, getContext(), type);
        listView.setAdapter(listAdapter);

        listView.setEmptyView(empty);
        if (type == 0) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(Constants.STROE_ID, datas.get(position).CouponId);
//                    Intent intent = new Intent(getContext(), StoreDetailsActivity.class);
//                    intent.putExtras(bundle);
//                    getContext().startActivity(intent);

                    ShopJumpManger jumpManger = new ShopJumpManger(getContext());
                    jumpManger.setBusinessActivities(Integer.parseInt(datas.get(position).BusinessActivities));
                    jumpManger.setShopType(Integer.parseInt(datas.get(position).ShopType));
                    jumpManger.setStroeId(datas.get(position).SupplierId);
                    jumpManger.jumpShopActivity();
                }
            });
        }

    }

    @Override
    public void setUpLisener() {
//        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
//        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                index = index + 1;
//                getData();
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                index = 1;
//                getData();
//            }
//        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private List<MyCouponsBean.ListBean> datas = new ArrayList<>();

    private void getData() {
        isFrist = false;
        RetrofitUrlManager.getInstance().putDomain("port8089", Contans.PORT_8089_HOST);
        addSubscription(RequestClient.GetUserCouponsList(type, index, getContext(), new NetSubscriber<BaseResultBean<MyCouponsBean>>(getContext()) {
            @Override
            public void onResultNext(BaseResultBean<MyCouponsBean> model) {
                avi.setVisibility(View.GONE);
//                if (index == 1) {
//                    if (model.data.list.size() > 0) {
//                        datas = model.data.list;
//                        adapter.setNewData(model.data.list);
//                    } else {
//                        adapter.setNewData(null);
//                        adapter.setEmptyView(R.layout.empty_layout, recyclerView);
//                    }
//                } else {
//                    if (model.data.list.size() > 0) {
//                        datas.addAll(model.data.list);
//                        adapter.setNewData(datas);
//                    } else {
//                        refreshLayout.finishLoadMoreWithNoMoreData();
//                    }
//                }
//                refreshLayout.finishLoadMore();
//                refreshLayout.finishRefresh();

                datas.addAll(model.data.list);
                listAdapter.notifyDataSetChanged();

            }
        }));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
        }
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//        if (!isFrist && isVisible) {
//            avi.setVisibility(View.VISIBLE);
//            index = 1;
//            getData();
//        }
//    }
    @Subscribe
    public void pageChange(MyCouponsEvent event) {
        if (event.position == type && isFrist) {
            getData();
        }
    }
}
