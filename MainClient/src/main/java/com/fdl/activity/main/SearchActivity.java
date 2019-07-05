package com.fdl.activity.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.adapter.MainProductAdapter;
import com.fdl.adapter.ShopSearchAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.MainProcuctBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
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
 * <p>creatTime：2019/2/15<p>
 * <p>changeTime：2019/2/15<p>
 * <p>version：1<p>
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    @BindView(R.id.rl_head)
    LinearLayout llTop;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_search1)
    EditText etSearch1;

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
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private MainProductAdapter adapter;
    private ShopSearchAdapter shopAdapter;
    private int type = 1;
    private int index = 1;
    private Bundle bundle;
    private List<MainProcuctBean> beans = new ArrayList<>();
    private boolean isShow = true;
    private int AreaId;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            type = bundle.getInt("type");
            AreaId = bundle.getInt("AreaId");
        }
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, llTop);
        avi.setVisibility(View.GONE);
        adapter = new MainProductAdapter(R.layout.item_searchproduct_layout, null);
        shopAdapter = new ShopSearchAdapter(R.layout.item_searchshop_layout, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayout.VERTICAL,R.drawable.line));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bundle = new Bundle();
                if ("2".equals(beans.get(position).BusinessActivities)) {
                    bundle.putInt("goodsId", beans.get(position).Id);
                    JumpUtils.dataJump(SearchActivity.this, ProductDetailsActivity.class, bundle, false);
                } else {
                    if ("6".equals(beans.get(position).ShopType)) {
                        bundle.putInt("goodsId", beans.get(position).Id);
                        bundle.putInt("stroeId", beans.get(position).SupplierId);
                        JumpUtils.dataJump(SearchActivity.this, StoreDetailsActivity.class, bundle, false);
                    }
                }
            }
        });
        shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bundle = new Bundle();
                if ("2".equals(beans.get(position).BusinessActivities)) {
                    bundle.putInt("stroeId", shopBeans.get(position).Id);
                    JumpUtils.dataJump(SearchActivity.this, ShopDetailsActivity.class, bundle, false);
                } else {
                    if ("6".equals(beans.get(position).ShopType)) {
                        bundle.putInt("stroeId", shopBeans.get(position).Id);
                        JumpUtils.dataJump(SearchActivity.this, StoreDetailsActivity.class, bundle, false);
                    }
                }
            }
        });
    }

    @Override
    public void setUpLisener() {
        etSearch1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hintKeyBoard();
                    isShow = true;
                    if (!StrUtils.isEmpty(etSearch1.getText().toString())) {
                        getSearch();
                    }
//                    showLongToast("开始搜索……");
                    return true;
                }
                return false;
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isShow = false;
                index += 1;
                getSearch();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_search, R.id.tv_01, R.id.tv_02, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                if (!StrUtils.isEmpty(etSearch1.getText().toString())) {
                    getSearch();
                }
                break;
            case R.id.tv_01:
                type = 1;
                index = 1;
                tv01.setTextColor(Color.parseColor("#F6003E"));
                tv02.setTextColor(Color.BLACK);

                adapter.setNewData(null);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.tv_02:
                type = 2;
                index = 1;
                tv02.setTextColor(Color.parseColor("#F6003E"));
                tv01.setTextColor(Color.BLACK);

                shopAdapter.setNewData(null);
                recyclerView.setAdapter(shopAdapter);
                break;
            case R.id.ll_back:
                this.finish();
                break;
        }
    }

    private List<MainProcuctBean> shopBeans = new ArrayList<>();

    public void getSearch() {
        addSubscription(RequestClient.Search(index, etSearch1.getText().toString().trim(), type, AreaId, this, new NetSubscriber<BaseResultBean<List<MainProcuctBean>>>(this, isShow) {
            @Override
            public void onResultNext(BaseResultBean<List<MainProcuctBean>> model) {
                avi.setVisibility(View.GONE);

                if (index == 1) {
                    if (model.data.size() > 0) {
                        if (type == 1) {
                            beans = model.data;
                            adapter.setNewData(model.data);
                        } else {
                            shopBeans = model.data;
                            shopAdapter.setNewData(model.data);
                        }
                    } else {
                        avi.setVisibility(View.VISIBLE);
                        avi1.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (model.data.size() > 0) {
                        if (type == 1) {
                            beans.addAll(model.data);
                            adapter.addData(model.data);
                            adapter.loadMoreComplete();
                        } else {
                            shopBeans.addAll(model.data);
                            shopAdapter.setNewData(beans);
                        }
                        refreshLayout.finishLoadMore();
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        }));
    }


}
