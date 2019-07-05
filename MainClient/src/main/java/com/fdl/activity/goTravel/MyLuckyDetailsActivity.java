package com.fdl.activity.goTravel;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fdl.adapter.MyLuckyDetailsAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.WinningDetailsBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/10<p>
 * <p>changeTime：2019/5/10<p>
 * <p>version：1<p>
 */
public class MyLuckyDetailsActivity extends Activity {
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
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_supplier_name)
    TextView tvSupplierName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_state)
    ImageView ivState;

    private Bundle bundle;
    private int logid;
    private MyLuckyDetailsAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myluckydetails_layout);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            logid = bundle.getInt("id");
        }
        setView();
    }
    ImmersionBar immersionBar;
    private void setView() {
        heardTitle.setText("记录详情");
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarColor(R.color.white);
        if (!ImmersionBar.hasNotchScreen(this)) {
            immersionBar.titleBar(R.id.rl_head);
        } else {
            IsBang.setImmerHeard(this, rlHead,"#ffffff");
        }
        immersionBar.statusBarColor(R.color.white);
        immersionBar.statusBarDarkFont(true);
        immersionBar.init();
        adapter = new MyLuckyDetailsAdapter(R.layout.item_myluckydetails_layout,null);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void fillView(WinningDetailsBean bean) {
        tvLevel.setText(bean.PrizeLevelName);
        ImageUtils.loadUrlImage(this, bean.PrizeImg, ivLogo);
        tvName.setText(bean.PrizeName);
        tvNum.setText(bean.ExchangeCode);
        tvTime.setText(bean.Date + "-" + bean.EndDate);
        tvSupplierName.setText("联系商户：" + bean.SupplierName);
        tvTel.setText("联系电话：" + bean.Tel);
        switch (bean.IsExchange) {
            case 0:
                ivState.setVisibility(View.GONE);
                break;
            case 1:
                Glide.with(this).load(R.drawable.lucky_state1_bg).into(ivState);
                break;
            case 2:
                Glide.with(this).load(R.drawable.lucky_state2_bg).into(ivState);
                break;
        }
        adapter.setNewData(bean.ExchangeAddress);

    }

    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }

    private void getData() {
        RequestClient.GetPrizeLogDetail(logid, this, new NetSubscriber<BaseResultBean<WinningDetailsBean>>(this) {
            @Override
            public void onResultNext(BaseResultBean<WinningDetailsBean> model) {
                fillView(model.data);
            }
        });
    }
}
