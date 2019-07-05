package com.fdl.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.adapter.AddressAdapter;
import com.fdl.bean.AddressBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/29<p>
 * <p>changeTime：2018/12/29<p>
 * <p>version：1<p>
 */
public class AddressActivity extends BaseActivity implements AddressAdapter.isZero {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.recyclerView_address)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_noData)
    TextView tvNoData;

    private String order;
    private List<AddressBean> myData = new ArrayList<>();
    private AddressAdapter addressAdapter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_layout);
        order = this.getIntent().getStringExtra("order");
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("收货地址管理");

    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetAddress(this, new NetSubscriber<BaseResultBean<List<AddressBean>>>(this, false) {
            @Override
            public void onResultNext(BaseResultBean<List<AddressBean>> model) {
                myData = model.data;
                initReclcyview(model.data);
                avi.setVisibility(View.GONE);
                if (model.data.size() > 0) {
                    tvNoData.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }
        }));
    }

    @OnClick({R.id.heard_back, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_commit:
                jumpActivity(EditAddressActivity.class);
                break;
        }
    }

    private void initReclcyview(List<AddressBean> data) {
        addressAdapter = new AddressAdapter(this, R.layout.item_address_layout, data, this);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAddress.setAdapter(addressAdapter);

        addressAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (!StrUtils.isEmpty(order)) {

                    //数据是使用Intent返回
                    Intent intent = new Intent();
                    //把返回数据存入Intent
                    intent.putExtra("address", myData.get(position));
                    //设置返回数据
                    AddressActivity.this.setResult(0, intent);
                    //关闭Activity
                    AddressActivity.this.finish();
                }
            }


            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataOnCreate();
    }

    @Override
    public void isZero(boolean flag) {
        if (flag) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }


}
