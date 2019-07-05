package com.fdl.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.adapter.RefundDetailsAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.RefundOrderBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class RefundOrderDetailsActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_customer_no)
    TextView tvCustomerNo;
    @BindView(R.id.tv_creat_time)
    TextView tvCreatTime;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_address1)
    TextView tvAddress1;
    @BindView(R.id.tv_address2)
    TextView tvAddress2;
    @BindView(R.id.tv_address3)
    TextView tvAddress3;
    @BindView(R.id.tv_address4)
    TextView tvAddress4;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_creat_time1)
    TextView tvCreatTime1;
    @BindView(R.id.tv_order1)
    TextView tvOrder1;
    @BindView(R.id.tv_order2)
    TextView tvOrder2;
    @BindView(R.id.tv_order3)
    TextView tvOrder3;
    @BindView(R.id.tv_order4)
    TextView tvOrder4;
    @BindView(R.id.avi)
    RelativeLayout avi;
    private String orderNo;
    private Bundle bundle;
    private RefundDetailsAdapter adapter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_refunddetails_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            orderNo = bundle.getString("orderNo");
        }
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("退款/售后详情");
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetRefundOrderDetail(orderNo, this, new NetSubscriber<BaseResultBean<RefundOrderBean>>(RefundOrderDetailsActivity.this, false) {
            @Override
            public void onResultNext(BaseResultBean<RefundOrderBean> model) {
                avi.setVisibility(View.GONE);
                fillView(model.data);
            }
        }));
    }


    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }

    private void fillView(RefundOrderBean bean) {
        tvCustomerNo.setText("服务单号：" + bean.CustomerNo);
        tvCreatTime.setText("申请时间：" + bean.CreateTime);
        tvResult.setText(bean.seriallist.get(0).Commit);
        tvMoney.setText("¥"+bean.returnMoney);
        if (StrUtils.isEmpty(bean.Express)) {
            tvAddress1.setText("物流：无");
        } else {
            tvAddress1.setText("物流：" + bean.Express);
        }
        if (StrUtils.isEmpty(bean.ApplyTel)) {
            tvAddress2.setText("联系电话：无");
        } else {
            tvAddress2.setText("联系电话：" + bean.ApplyTel);
        }
        if (StrUtils.isEmpty(bean.ApplyPsn)) {
            tvAddress3.setText("收货人：无");
        } else {
            tvAddress3.setText("收货人：" + bean.ApplyPsn);
        }
        if (StrUtils.isEmpty(bean.returnReason)) {
            tvAddress4.setText("退款说明：无");
        } else {
            tvAddress4.setText("退款说明：" + bean.returnReason);
        }
        if (StrUtils.isEmpty(bean.returnRemark)) {
            tvDesc.setText("无");
        } else {
            tvDesc.setText(bean.returnRemark);
        }
        tvCreatTime1.setText(bean.CreateTime);
        if (bean.Flag.equals("1")) {

            tvOrder1.setText("服务类型：退款退货");
        } else {
            tvOrder1.setText("服务类型：仅退款");
        }
        if (StrUtils.isEmpty(bean.returnReason)) {
            tvOrder2.setText("申请原因：无");
        } else {
            tvOrder2.setText("申请原因：" + bean.returnReason);
        }
        tvOrder3.setText("退款方式：原返");
        tvOrder4.setText("快递至第三方卖家");
        adapter = new RefundDetailsAdapter(this, R.layout.item_refund_desc_layout, bean.seriallist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }
}
