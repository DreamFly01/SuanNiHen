package com.fdl.activity.account.money;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.adapter.MyBanksAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.daoBean.MyBankBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.sg.cj.snh.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/2<p>
 * <p>changeTime：2019/4/2<p>
 * <p>version：1<p>
 */
public class MyBanksActivity extends BaseActivity {
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_commit)
    LinearLayout llCommit;

    private MyBanksAdapter adapter;
    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mybank_layout);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("我的银行卡");
        dialogUtils = new DialogUtils(this);
        IsBang.setImmerHeard(this,rlHead);
        adapter = new MyBanksAdapter(R.layout.item_mybanks_layout, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void setUpLisener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btnDelete:
                        dele(datas.get(position).Id);
                        datas.remove(position);
                        break;
                }
            }
        });
    }

    private List<MyBankBean> datas;

    private void getData() {
        addSubscription(RequestClient.GetSupplierBankCards(this, new NetSubscriber<BaseResultBean<List<MyBankBean>>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<MyBankBean>> model) {
                if (model.data.size() > 0) {
                    datas = model.data;
                    adapter.setNewData(model.data);
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }
        }));
    }

    private void dele(int id) {
        addSubscription(RequestClient.DeleteSupplierBankCard(id, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {

                showShortToast("删除成功");
                adapter.setNewData(datas);
                if(datas.size()<=0){
                    recyclerView.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.heard_back, R.id.ll_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_commit:
                jumpActivity(AddBankActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
