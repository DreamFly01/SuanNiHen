package com.fdl.activity.account;

import android.graphics.Color;
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
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.adapter.FollowAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ProductFollowBean;
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
 * <p>creatTime：2019/1/11<p>
 * <p>changeTime：2019/1/11<p>
 * <p>version：1<p>
 */
public class ProductCollectListActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
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
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;

    private int index = 1;
    private FollowAdapter adapter;
    private String ids;
    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_productcollect_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("商品收藏");
        heardTvMenu.setText("管理");
        heardTvMenu.setTextColor(Color.WHITE);
        heardTvMenu.setVisibility(View.VISIBLE);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index += 1;
                getDataOnCreate();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                getDataOnCreate();
            }
        });
    }

    @Override
    public void setUpLisener() {

    }


    private List<ProductFollowBean> beans = new ArrayList<>();

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetProductFollow(index, this, new NetSubscriber<BaseResultBean<List<ProductFollowBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<ProductFollowBean>> model) {
                avi.setVisibility(View.GONE);
                beans = model.data;
                if (index == 1) {
                    if (model.data.size() <= 0) {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    } else {
                        setRecyclerView(model.data);
                        refreshLayout.finishRefresh();
                    }
                } else {
                    adapter.addDatas(model.data);
                    refreshLayout.finishLoadMore();
                }
            }
        }));
    }

    boolean isClick = false;

    @OnClick({R.id.heard_back, R.id.heard_tv_menu, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.heard_tv_menu:
                if (beans.size() > 0) {
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
                }

                break;
            case R.id.tv_delete:
                if (adapter.getIds().size() > 0) {
                    List<Integer> idList = new ArrayList<Integer>(adapter.getIds().values());
                    ids = StringUtils.join(idList, ",", 0, idList.size());
                    deleteFollow();
                }
                break;
        }
    }

    private void setRecyclerView(List<ProductFollowBean> datas) {
        adapter = new FollowAdapter(this, R.layout.item_follow_layout, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodsId", datas.get(position).GoodId);
                JumpUtils.dataJump(ProductCollectListActivity.this, ProductDetailsActivity.class, bundle, false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    //删除收藏记录
    private void deleteFollow() {
        addSubscription(RequestClient.DeleteProductFollow(ids, this, new NetSubscriber<BaseResultBean>(this, true) {
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
