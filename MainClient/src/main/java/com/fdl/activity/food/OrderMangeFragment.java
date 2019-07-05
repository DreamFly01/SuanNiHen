package com.fdl.activity.food;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseFragment;
import com.fdl.adapter.foodadapter.FoodOrderMangeAdapter;
import com.fdl.bean.daoBean.CommonBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Administrator on 2019/5/31 0031 16:23
 * jy
 * 美食管理订单
 */
public class OrderMangeFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder1;

    private FoodOrderMangeAdapter adapter;


    //模拟数据源
    private List<CommonBean> mList;
    private int imgHead[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private String foodStore[] = {"茶的世界", "果果零食铺"};


    /**
     * 设置模拟数据
     */
    private void setDataList() {
        mList = new ArrayList<>();
        for (int i = 0; i < imgHead.length; i++) {
            CommonBean orderBean = new CommonBean();
            orderBean.setImgUrl(imgHead[i]);//头像
            orderBean.setOrderNo(foodStore[i]);//店铺名字
            mList.add(orderBean);
        }

    }

    private void setRecyclerView() {
        setDataList();//设置模拟数据
        adapter = new FoodOrderMangeAdapter(getContext(), R.layout.item_ordermange_layout, mList);
//        adapter.setCancelOnClick(getActivity());
//        adapter.setComfireGet(this);
//        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                isFrist = false;
//                bundle = new Bundle();
//                bundle.putString("orderid", myOrderBeanList.get(position).Id + "");
//                bundle.putString("subOrderNo", myOrderBeanList.get(position).OrderNo + "");//注意:我的订单过去用subOrderNo
//                JumpUtils.dataJump(getActivity(), MyOrderDetailsActivity.class, bundle, false);
//            }

//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //  adapter.setSetIsFrist(this);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_order_management;
    }

    @Override
    public void setUpViews(View view) {
        setRecyclerView();
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
