package com.fdl.activity.buy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.supermarket.SupermarkDialogFragment;
import com.fdl.adapter.GoodsDetailsAdapter;
import com.fdl.utils.DialogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/3/20<p>
 * <p>changeTime：2019/3/20<p>
 * <p>version：1<p>
 */
public class GoodsDetailsActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.iv_01)
    ImageView iv01;
    @BindView(R.id.iv_02)
    ImageView iv02;
    @BindView(R.id.iv_03)
    ImageView iv03;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.ll_03)
    LinearLayout ll03;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    @BindView(R.id.ll_store)
    LinearLayout llStore;
    @BindView(R.id.ll_buy_car)
    LinearLayout llBuyCar;
    @BindView(R.id.tv_add_buy_car)
    TextView tvAddBuyCar;
    @BindView(R.id.tv_buy_away)
    TextView tvBuyAway;
    @BindView(R.id.ll_buy_or_add)
    LinearLayout llBuyOrAdd;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl_head1)
    LinearLayout rlHead1;

    private LinearLayout heard1;
    private LinearLayout llCoupone;
    private LinearLayout llSku;
    private LinearLayout llParams;
    private LinearLayout heard2;
    private LinearLayout heard3;
    private LayoutInflater inflater = null;

    private GoodsDetailsAdapter adapter;

    private int totalY = 0;
    private int statuBarHeight;
    private ImmersionBar immersionBar;

    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goodsdetails_layout);
    }

    @Override
    public void setUpViews() {
        setImm(false);
        dialogUtils = new DialogUtils(this);
        rlHead.setVisibility(View.INVISIBLE);
        immersionBar = ImmersionBar.with(this);
        statuBarHeight = immersionBar.getStatusBarHeight(this);
        immersionBar.statusBarColor(R.color.transparent).init();
        immersionBar.setTitleBar(this, rlHead1);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        heard1 = (LinearLayout) inflater.inflate(R.layout.goods_heard1_layout, null);
        llCoupone = heard1.findViewById(R.id.ll_coupon);
        llSku = heard1.findViewById(R.id.ll_sku);
        llParams = heard1.findViewById(R.id.ll_parms);
        heard2 = (LinearLayout) inflater.inflate(R.layout.goods_heard2_layout, null);
        heard3 = (LinearLayout) inflater.inflate(R.layout.goods_heard3_layout, null);
        adapter = new GoodsDetailsAdapter(R.layout.item_menu_home_layout, null);

        adapter.addHeaderView(heard1);
        adapter.addHeaderView(heard2);
        adapter.addHeaderView(heard3);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void setUpLisener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalY += dy;

                if (totalY > 800) {
                    rlHead1.setVisibility(View.GONE);
                    rlHead.setVisibility(View.VISIBLE);
                } else {
                    rlHead1.setVisibility(View.VISIBLE);
                    rlHead.setVisibility(View.INVISIBLE);
                }
                SgLog.d("totalY:" + totalY);
                SgLog.d(heard1.getHeight() + "    " + heard2.getHeight() + "    " + heard3.getHeight());
                if (totalY > heard1.getHeight() - statuBarHeight) {
                    setVisible(iv03);
                }
                if (totalY > (heard1.getHeight() + heard2.getHeight()) - statuBarHeight) {
                    setVisible(iv02);
                }
                if (totalY < heard1.getHeight() - statuBarHeight) {
                    setVisible(iv01);
                }
            }
        });
        llCoupone.setOnClickListener(this::onUserClick);
        llParams.setOnClickListener(this::onUserClick);
        llSku.setOnClickListener(this::onUserClick);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void setVisible(ImageView iv) {
        iv01.setVisibility(View.GONE);
        iv02.setVisibility(View.GONE);
        iv03.setVisibility(View.GONE);
        iv.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.heard_back, R.id.ll_01, R.id.ll_02, R.id.ll_03})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                break;
            case R.id.ll_01:
                recyclerView.scrollBy(0, -(heard1.getHeight() + heard2.getHeight() + heard3.getHeight()));
                setVisible(iv01);
                break;
            case R.id.ll_02:
                if (totalY >= (heard1.getHeight() + heard2.getHeight()) - statuBarHeight) {
                } else {
                    recyclerView.scrollBy(0, (heard1.getHeight() + heard2.getHeight()) - rlHead.getHeight());
                }
                setVisible(iv02);
                break;
            case R.id.ll_03:
                if (totalY < heard1.getHeight() - statuBarHeight) {
                    recyclerView.scrollBy(0, heard1.getHeight() - rlHead.getHeight());
                }
                setVisible(iv03);
                break;
        }
    }

    @Override
    public void onUserClick(View v) {
        super.onUserClick(v);
        switch (v.getId()) {
            case R.id.ll_coupon:
                dialogUtils.CouponDialog();
                break;
            case R.id.ll_sku:
                new CalendarDialogFragment().show(getSupportFragmentManager(),"calendar");
                break;
            case R.id.ll_parms:
                dialogUtils.ParamsDialog();
                break;
        }
    }
}
