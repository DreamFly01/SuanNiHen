package com.fdl.activity.main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseFragment;
import com.fdl.activity.buy.OrderActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.adapter.BuycarAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.IdBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/12<p>
 * <p>changeTime：2019/1/12<p>
 * <p>version：1<p>
 */
public class BuyCarFragment extends BaseFragment {
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
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.iv_chose)
    ImageView ivChose;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.tv_extra)
    TextView tvExtra;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_chose)
    LinearLayout llChose;

    private BuycarAdapter adapter;
    private List<OrderDataBean> dataBeans = new ArrayList<>();
    private String totalMoney;
    private DialogUtils dialogUtils;

    @Override
    public int initContentView() {
        return R.layout.fragment_buycar_layout;
    }

    @Override
    public void setUpViews(View view) {

        IsBang.setImmerHeard(getContext(), rlHead);
        dialogUtils = new DialogUtils(getContext());
        heardBack.setVisibility(View.GONE);
        heardTitle.setText("购物车");
        heardTvMenu.setVisibility(View.VISIBLE);
        heardTvMenu.setText("管理");
        heardTvMenu.setTextColor(Color.WHITE);
        refreshLayout.setEnableLoadMore(false);
        getDataOnActivityCreated();

    }

    @Override
    public void setUpLisener() {

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

    /**
     * 查询购物车是否有商品
     */
    public void getDataOnActivityCreated() {
        tvAllPrice.setText("0.00");
        ivChose.setBackgroundResource(R.drawable.pay_normall);
        addSubscription(RequestClient.GetBuyCar(getContext(), new NetSubscriber<BaseResultBean<List<OrderDataBean>>>(getContext(), false) {
            @Override
            public void onResultNext(BaseResultBean<List<OrderDataBean>> model) {
                if (model.data.size() > 0) {
                    dataBeans = model.data;
                    avi.setVisibility(View.GONE);
                    setRecyclerView(model.data);
                } else {
                    avi.setVisibility(View.VISIBLE);
                    avi1.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    tvTip.setTextColor(Color.RED);
                    tvTip.setText("购物车竟然是空的，去买点东西吧~");
                }
            }
        }));
    }

    private boolean isFirstSet = true;

    /**
     * 设置数据列表
     *
     * @param datas
     */
    private void setRecyclerView(List<OrderDataBean> datas) {
        adapter = new BuycarAdapter(getContext(), R.layout.item_shopcar_layout, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (isFirstSet) {
            RecycleViewDivider recycleViewDivider = new RecycleViewDivider(getContext(), LinearLayout.VERTICAL, 20, Color.parseColor("#f3f3f7"));
            recyclerView.addItemDecoration(recycleViewDivider);
            isFirstSet = false;

        }
        recyclerView.setAdapter(adapter);
        recyclerView.setFocusableInTouchMode(false);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        adapter.setItemOnClikListener(new BuycarAdapter.TextItemOnClikListener() {
            @Override
            public void onItemOnClikListener(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("stroeId", datas.get(position).Id);
                JumpUtils.dataJump(getActivity(), ShopDetailsActivity.class, bundle, false);
            }
        });
        adapter.setRefreshFragment(new BuycarAdapter.RefreshFragment() {
            @Override
            public void refresh(List<OrderDataBean> datas) {
                calculate();
            }
        });
    }

    boolean isClick = false;
    boolean isChose = false;
    boolean deleteOrCommit = false;

    @OnClick({R.id.heard_tv_menu, R.id.iv_chose, R.id.tv_delete, R.id.ll_chose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_tv_menu:
                if (isClick) {
                    isClick = false;
                    heardTvMenu.setText("完成");
                    tvDelete.setText("删除");
                    tvExtra.setText("");
                    llPrice.setVisibility(View.GONE);
                    deleteOrCommit = true;
                } else {
                    isClick = true;
                    heardTvMenu.setText("管理");
                    tvDelete.setText("结算");
                    tvExtra.setText("不包含运费");
                    llPrice.setVisibility(View.VISIBLE);
                    deleteOrCommit = false;
                }
                break;
            case R.id.iv_chose:

                break;
            case R.id.tv_delete:
                if (idList.size() > 0) {
                    if (deleteOrCommit) {
                        //删除数据
                        delete(StringUtils.join(idList, ",", 0, idList.size()));
                    } else {
                        Bundle bundle = new Bundle();
                        //添加list对象
                        bundle.putString("carId", StringUtils.join(idList, ",", 0, idList.size()));
//                        bundle.putString("idDatas",StringUtils.join(idsList,",",0,idsList.size()));
                        bundle.putString("totalPrice", totalMoney);
                        bundle.putParcelableArrayList("idBeans", (ArrayList<? extends Parcelable>) idBeanList);
                        //订单购买
                        JumpUtils.dataJump(getActivity(), OrderActivity.class, bundle, false);

                    }
                } else {
                    dialogUtils.noBtnDialog("请选择商品");
                }
                break;
            case R.id.ll_chose:
                if (isChose) {
                    ivChose.setBackgroundResource(R.drawable.pay_normall);
                    isChose = false;
                } else {
                    ivChose.setBackgroundResource(R.drawable.pay_selete);
                    tvExtra.setText("不包含运费");
                    llPrice.setVisibility(View.VISIBLE);
                    tvDelete.setBackgroundResource(R.color.app_red);
                    isChose = true;
                }
                for (int i = 0; i < dataBeans.size(); i++) {
                    for (int j = 0; j < dataBeans.get(i).goodsList.size(); j++) {
                        dataBeans.get(i).goodsList.get(j).isChose = isChose;
                    }
                }
                calculate();
                adapter.updataDatas(dataBeans);
                break;
        }
    }


    private double totalPrice;//勾选的商品总价格
    List<Integer> idList = new ArrayList<Integer>();
    List<OrderDataBean> bundDatas = new ArrayList<>();
    Map<Integer, List<Integer>> map = new TreeMap<>();
    List<Integer> idDataList = new ArrayList<>();
    List<IdBean> idBeanList = new ArrayList<>();
    IdBean idBean;


    private void calculate() {
        totalPrice = 0.00;
        idList.clear();
//        bundDatas.clear();
        idBeanList.clear();
        boolean storeChoose = true;
        for (int i = 0; i < dataBeans.size(); i++) {
            idDataList.clear();
            idBean = new IdBean();
            for (int j = 0; j < dataBeans.get(i).goodsList.size(); j++) {
                storeChoose = storeChoose && dataBeans.get(i).goodsList.get(j).isChose;
                if (dataBeans.get(i).goodsList.get(j).isChose) {
                    totalPrice += dataBeans.get(i).goodsList.get(j).SalesPrice * dataBeans.get(i).goodsList.get(j).Number;
                    idList.add(dataBeans.get(i).goodsList.get(j).Id);
                    idDataList.add(dataBeans.get(i).goodsList.get(j).Id);
                    llPrice.setVisibility(View.VISIBLE);
                    tvExtra.setText("不包含运费");
                }
            }
            idBean.id = dataBeans.get(i).Id;
            idBean.carId = StringUtils.join(idDataList, ",", 0, idDataList.size());
            idBeanList.add(idBean);
        }
        if (storeChoose) {
            ivChose.setBackgroundResource(R.drawable.pay_selete);
        } else {
            ivChose.setBackgroundResource(R.drawable.pay_normall);
            tvExtra.setText("");

        }
        totalMoney = StrUtils.moenyToDH(totalPrice + "");
        tvAllPrice.setText(StrUtils.moenyToDH(totalPrice + ""));
        if (idList.size() > 0) {
            tvDelete.setBackgroundResource(R.color.app_red);
        } else {
            tvDelete.setBackgroundResource(R.color.txt_9);
        }
    }

    private boolean isFirst = true;
    private boolean isHidden = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            avi.setVisibility(View.VISIBLE);
            getDataOnActivityCreated();
        }
        isHidden = hidden;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst && !isHidden) {
            avi.setVisibility(View.VISIBLE);
            getDataOnActivityCreated();
        }
        isFirst = false;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    //    测试更新
    private void delete(String ids) {
        addSubscription(RequestClient.DeleteCarGoods(ids, getContext(), new NetSubscriber<BaseResultBean>(getContext(), true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                dialogUtils.simpleDialog("删除成功", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        isClick = true;
                        heardTvMenu.setText("管理");
                        tvDelete.setText("结算");
                        tvExtra.setText("不包含运费");
                        llPrice.setVisibility(View.VISIBLE);
                        deleteOrCommit = false;
                        dialogUtils.dismissDialog();
                        getDataOnActivityCreated();
                    }
                }, true);
            }
        }));
    }
}
