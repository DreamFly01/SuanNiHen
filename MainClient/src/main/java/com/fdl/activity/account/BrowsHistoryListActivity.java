package com.fdl.activity.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.adapter.BrowsHistoryAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.GoodsBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/12<p>
 * <p>changeTime：2019/1/12<p>
 * <p>version：1<p>
 */
public class BrowsHistoryListActivity extends BaseActivity {
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
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;

    private BrowsHistoryAdapter adapter;

    private DialogUtils dialogUtils;
    private int index = 1;
    private String ids;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_browhistory_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        heardTitle.setText("浏览历史");
        heardTvMenu.setText("管理");
        heardTvMenu.setTextColor(Color.WHITE);
        heardTvMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUpLisener() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index += 1;
                getDataOnCreate();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    boolean isClick = false;

    @OnClick({R.id.heard_back, R.id.heard_tv_menu, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.heard_tv_menu:
                if (isClick) {
                    adapter.setChoseIsVisible(!isClick);
                    isClick = false;
                    heardTvMenu.setText("管理");
                    llDelete.setVisibility(View.GONE);
                } else {
                    adapter.setChoseIsVisible(!isClick);
                    isClick = true;
                    heardTvMenu.setText("完成");
                    llDelete.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_delete:
                if (adapter.getIds().size() > 0) {
                    List<Integer> idList = new ArrayList<Integer>(adapter.getIds().values());
                    ids = StringUtils.join(idList, ",", 0, idList.size());
                    delete();
                }
                break;
        }
    }


    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetBrowsHistory(index, this, new NetSubscriber<BaseResultBean<List<GoodsBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<GoodsBean>> model) {
                avi.setVisibility(View.GONE);
                if (index == 1) {
                    if (model.data.size() <= 0) {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    } else {
                        setRecyclerView(model.data);
                    }
                } else {
                    adapter.addDatas(model.data);
                }
                refreshLayout.finishLoadMore();
            }
        }));
    }

    private void setRecyclerView(List<GoodsBean> datas) {
        adapter = new BrowsHistoryAdapter(this, R.layout.item_browshistory_layout, datas);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Bundle bundle = new Bundle();
                if ("6".equals(datas.get(position).ShopType)) {
                    bundle.putInt("stroeId", datas.get(position).SupplierId);
                    JumpUtils.dataJump(BrowsHistoryListActivity.this, StoreDetailsActivity.class, bundle, false);
                } else {
                    bundle.putInt("goodsId", datas.get(position).GoodId);
                    JumpUtils.dataJump(BrowsHistoryListActivity.this, ProductDetailsActivity.class, bundle, false);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void delete() {
        addSubscription(RequestClient.DeleteBrowsHistory(ids, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                dialogUtils.simpleDialog("删除成功", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        dialogUtils.dismissDialog();
                        index = 1;
                        getDataOnCreate();
                    }
                }, false);
            }
        }));
    }
}
