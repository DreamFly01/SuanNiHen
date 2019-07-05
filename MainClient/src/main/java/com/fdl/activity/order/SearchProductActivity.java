package com.fdl.activity.order;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.activity.supermarket.SupermarkDialogFragment;
import com.fdl.adapter.ScrollRightAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ScrollBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.bean.daoBean.SupplierBean;
import com.fdl.db.DBManager;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/13<p>
 * <p>changeTime：2019/5/13<p>
 * <p>version：1<p>
 */
public class SearchProductActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.et_search1)
    EditText etSearch1;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private Bundle bundle;
    private int id;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_searchproduct_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getInt("storeId");
        }
    }

    @Override
    public void setUpViews() {
        if (ImmersionBar.hasNotchScreen(this)) {
            IsBang.setImmerHeard(this, rlHead);
        }
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);

    }

    @Override
    public void setUpLisener() {
        etSearch1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hintKeyBoard();
                    if (!StrUtils.isEmpty(etSearch1.getText().toString())) {
                        getData();
                    }
//                    showLongToast("开始搜索……");
                    return true;
                }
                return false;
            }
        });
    }

    private SupplierBean bean;

    private void getData() {
        RequestClient.GetSuperMarketCommTenant(id, etSearch1.getText().toString(), this, new NetSubscriber<BaseResultBean<SupplierBean>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<SupplierBean> model) {
                bean = model.data;


                querInit();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void querInit() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        List<CommTenant> datas = commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(id)).list();
        if (datas.size() > 0) {
            for (int i = 0; i < bean.CommTenantResultList.size(); i++) {
                System.out.println(bean.CommTenantResultList.get(i).CommTenantList.size() + "");
                for (int j = 0; j < datas.size(); j++) {
                    for (int k = 0; k < bean.CommTenantResultList.get(i).CommTenantList.size(); k++) {
                        if (datas.get(j).CommTenantId.longValue() == bean.CommTenantResultList.get(i).CommTenantList.get(k).CommTenantId.longValue()) {
                            bean.CommTenantResultList.get(i).CommTenantList.get(k).total = datas.get(j).total;
                            bean.CommTenantResultList.get(i).CommTenantList.get(k).setSupplierId(datas.get(j).getSupplierId());
                        } else {
//                        bean.CommTenantResultList.get(i).CommTenantList.get(k).total = 0;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < bean.CommTenantResultList.size(); i++) {
                for (int k = 0; k < bean.CommTenantResultList.get(i).CommTenantList.size(); k++) {
                    bean.CommTenantResultList.get(i).CommTenantList.get(k).total = 0;
                }
            }
        }
        initData(bean);
        initRight();

    }

    private List<ScrollBean> right;

    private void initData(SupplierBean bean) {
        right = new ArrayList<>();
        List<ScrollBean> list = new ArrayList<>();
        ScrollBean scrollBean = null;
        ScrollBean.ScrollItemBean scrollItemBean;
        for (int i = 0; i < bean.CommTenantResultList.size(); i++) {
            scrollBean = new ScrollBean(true, bean.CommTenantResultList.get(i).TypeName);
            scrollBean.SupplierId = bean.SupplierId;
            right.add(scrollBean);
            for (int j = 0; j < bean.CommTenantResultList.get(i).CommTenantList.size(); j++) {
                scrollItemBean = new ScrollBean.ScrollItemBean();
                scrollItemBean.Inventory = bean.CommTenantResultList.get(i).CommTenantList.get(j).Inventory;
                scrollItemBean.type = bean.CommTenantResultList.get(i).TypeName;
                scrollItemBean.CommTenantIcon = bean.CommTenantResultList.get(i).CommTenantList.get(j).CommTenantIcon;
                scrollItemBean.CommTenantId = bean.CommTenantResultList.get(i).CommTenantList.get(j).CommTenantId;
                scrollItemBean.CommTenantName = bean.CommTenantResultList.get(i).CommTenantList.get(j).CommTenantName;
                scrollItemBean.OnThePin = bean.CommTenantResultList.get(i).CommTenantList.get(j).OnThePin;
                scrollItemBean.Price = bean.CommTenantResultList.get(i).CommTenantList.get(j).Price;
                scrollItemBean.UnitsTitle = bean.CommTenantResultList.get(i).CommTenantList.get(j).UnitsTitle;
                scrollItemBean.total = bean.CommTenantResultList.get(i).CommTenantList.get(j).total;
                scrollBean.itemBean = scrollItemBean;
                scrollBean = new ScrollBean(scrollItemBean);
                right.add(scrollBean);
            }
        }

        for (int i = 0; i < right.size(); i++) {
//            if (right.get(i).isHeader) {
//                //遍历右侧列表,判断如果是header,则将此header在右侧列表中所在的position添加到集合中
//                tPosition.add(i);
//            }
            if (right.get(i).SupplierId != 0) {
                list.add(right.get(i));
            }
        }

    }

    private LinearLayoutManager rightManager;
    private ScrollRightAdapter rightAdapter;
    private SupermarkDialogFragment fragment;

    private void initRight() {

        rightManager = new LinearLayoutManager(this);

        if (rightAdapter == null) {
            rightAdapter = new ScrollRightAdapter(R.layout.item_right_layout, R.layout.layout_right_title, null);
            recyclerView.setLayoutManager(rightManager);
//            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//                @Override
//                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                    super.getItemOffsets(outRect, view, parent, state);
//                    outRect.set(dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
//                            , 0
//                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
//                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3)));
//                }
//            });
            recyclerView.setAdapter(rightAdapter);
        } else {
            rightAdapter.notifyDataSetChanged();
        }

        if (right.size() > 0) {

            rightAdapter.setNewData(right);
        } else {
            rightAdapter.setEmptyView(R.layout.empty_layout, recyclerView);
        }

        //设置右侧初始title
//        if (right.get(first).isHeader) {
//            rightTitle.setText(right.get(first).header);
//        }

        rightAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        if (right.get(position - 1).itemBean.total >= right.get(position - 1).itemBean.Inventory) {
                        } else {
                            right.get(position - 1).itemBean.total += 1;
                            rightAdapter.setNewData(right);
                            updataDataToDb(right.get(position - 1));
                        }
                        break;
                    case R.id.iv_delete:
                        right.get(position - 1).itemBean.total -= 1;
                        rightAdapter.setNewData(right);
                        deleteDataInDb(right.get(position - 1));
                        break;
                    case R.id.iv_product_logo1:

                        bundle = new Bundle();
                        bundle.putLong("id", right.get(position - 1).itemBean.CommTenantId);
                        fragment = new SupermarkDialogFragment();
                        fragment.setArguments(bundle);
                        fragment.show(getSupportFragmentManager(), "details");
                        break;
                }

            }
        });
    }

    private void updataDataToDb(ScrollBean beans) {

        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();

        CommTenant commTenant = commTenantDao.load(beans.itemBean.CommTenantId);
        if (commTenant != null) {
            commTenant.setSupplierId(id);
            commTenant.setCommTenantIcon(beans.itemBean.CommTenantIcon);
            commTenant.setCommTenantId(beans.itemBean.CommTenantId);
            commTenant.setCommTenantName(beans.itemBean.CommTenantName);
            commTenant.setTotal(beans.itemBean.total);
            commTenant.setPrice(beans.itemBean.Price);
            commTenant.setInventory(beans.itemBean.Inventory);
            try {
                commTenantDao.update(commTenant);
                SgLog.d("更新成功");
            } catch (Exception e) {
                SgLog.d("更新失败");
            }
        } else {
            commTenant = new CommTenant();
            commTenant.setSupplierId(id);
            commTenant.setPrice(beans.itemBean.Price);
            commTenant.setCommTenantIcon(beans.itemBean.CommTenantIcon);
            commTenant.setCommTenantId((long) beans.itemBean.CommTenantId);
            commTenant.setCommTenantName(beans.itemBean.CommTenantName);
            commTenant.setTotal(beans.itemBean.total);
            commTenant.setInventory(beans.itemBean.Inventory);
            commTenantDao.insert(commTenant);
        }
        SgLog.d("数据：" + new Gson().toJson(commTenantDao.loadAll()));
    }

    private void deleteDataInDb(ScrollBean beans) {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        CommTenant commTenant = commTenantDao.load(beans.itemBean.CommTenantId);
        if (commTenant != null) {
            if (commTenant.total > 1) {
                commTenant.setSupplierId(id);
                commTenant.setPrice(beans.itemBean.Price);
                commTenant.setCommTenantIcon(beans.itemBean.CommTenantIcon);
                commTenant.setCommTenantId((long) beans.itemBean.CommTenantId);
                commTenant.setCommTenantName(beans.itemBean.CommTenantName);
                commTenant.setTotal(beans.itemBean.total);
                try {
                    commTenantDao.update(commTenant);
                    SgLog.d("删除成功");
                } catch (Exception e) {
                    SgLog.d("删除失败");
                }
            } else {
                try {
                    SgLog.d("删除成功");
                    commTenantDao.delete(commTenant);
                } catch (Exception e) {
                    SgLog.d("删除失败");
                }
            }
        }


    }
    @OnClick({R.id.ll_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.tv_search:
                getData();
                break;
        }
    }
}
