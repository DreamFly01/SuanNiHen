package com.fdl.activity.account.money;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.adapter.WithdrawListAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ChoseBean;
import com.fdl.bean.WithdrawBean;
import com.fdl.bean.WithdrawListBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.TimeUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sg.cj.snh.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/16<p>
 * <p>changeTime：2019/4/16<p>
 * <p>version：1<p>
 */
public class WithdrawListActivity extends Activity {
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
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_commein)
    TextView tvCommein;
    @BindView(R.id.tv_payout)
    TextView tvPayout;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private DialogUtils dialogUtils;
    private int type = 0;
    private int index = 1;
    private List<ChoseBean> choseBeanList = new ArrayList<>();
    private List<ChoseBean> choseBean1List = new ArrayList<>();
    private int mTop;

    int mYear;
    int mMonth;
    int mDay;
    private WithdrawListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_withdrawlist_layout);

        ButterKnife.bind(this);
        setUpViews();
        setUpLisener();
        getData();
    }

    private String dataStr;
    private ImmersionBar immersionBar;
    public void setUpViews() {
        immersionBar = ImmersionBar.with(this);
        if (ImmersionBar.hasNotchScreen(this)) {
            IsBang.setImmerHeard(this, rlHead, "#ffffff");
        } else {
            immersionBar.statusBarColor(R.color.white).titleBar(R.id.rl_head).init();
        }
        immersionBar.statusBarDarkFont(true).init();
        heardTitle.setText("明细");
        dialogUtils = new DialogUtils(this);
        for (int i = 0; i < 4; i++) {
            ChoseBean bean = new ChoseBean();
            switch (i) {
                case 0:
                    bean.itemContent = "全部";
                    bean.type = 0;
                    bean.imgRes = R.drawable.select_1_bg;
                    break;
                case 1:
                    bean.itemContent = "收入";
                    bean.type = 1;
                    bean.imgRes = R.drawable.select_2_bg;
                    break;
                case 2:
                    bean.type = 2;
                    bean.itemContent = "支出";
                    bean.imgRes = R.drawable.select_3_bg;
                    break;
                case 3:
                    bean.type = 3;
                    bean.itemContent = "提现";
                    bean.imgRes = R.drawable.select_4_bg;
                    break;
            }
            choseBeanList.add(bean);
        }
        for (int i = 0; i < 2; i++) {
            ChoseBean bean = new ChoseBean();
            switch (i) {
                case 0:
                    bean.itemContent = "按月查询";
//                    bean.imgRes = R.drawable.pay_selete;
                    break;
                case 1:
                    bean.itemContent = "按日查询";
//                    bean.imgRes = R.drawable.pay_selete;
                    break;
            }
            choseBean1List.add(bean);
        }
        long currenTime = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();//
        c.setTimeInMillis(currenTime);
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH);// 获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        dataStr = TimeUtils.getDateTimeString(System.currentTimeMillis(), "yyyy-MM");
        tvTime.setText(dataStr);

        adapter = new WithdrawListAdapter(R.layout.item_withdrawlist_layout, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL, R.drawable.line_2_gray));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        refreshLayout.setEnableLoadMore(false);
    }

    public boolean isShow = true;

    public void setUpLisener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                isShow = false;
                getData();
            }

        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index += 1;
                        isShow = false;
                        getData();
                    }
                }, 1000);
            }
        }, recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", datas.get(position).Id);
                bundle.putInt("type", datas.get(position).Type);
                JumpUtils.dataJump(WithdrawListActivity.this, WithdrawDetailsActivity.class, bundle, false);
            }
        });
    }


    private List<WithdrawListBean> datas = new ArrayList<>();

    private void getData() {
        RequestClient.GetSupplierMoneyLog(dataStr, type, index, this, new NetSubscriber<BaseResultBean<WithdrawBean>>(this, isShow) {
            @Override
            public void onResultNext(BaseResultBean<WithdrawBean> model) {
                refreshLayout.finishRefresh();
                tvCommein.setText("收入：" + StrUtils.moenyToDH("" + model.data.Income));
                tvPayout.setText("支出：" +

                        StrUtils.moenyToDH("" + model.data.Spending));
                if (index == 1) {
                    if (model.data.LogDetail.size() > 0) {
                        datas = model.data.LogDetail;
                        adapter.setNewData(datas);
                        adapter.loadMoreComplete();
                    } else {
                        adapter.setNewData(null);
                        adapter.setEmptyView(R.layout.empty_layout, recyclerView);
                        adapter.loadMoreEnd();
                    }
                } else {
                    if (model.data.LogDetail.size() > 0) {
                        datas.addAll(model.data.LogDetail);
                        adapter.setNewData(datas);
                        adapter.loadMoreComplete();
                    } else {
                        adapter.loadMoreEnd();
                    }
                }
            }
        });
    }

    private int showType;

    private void show1Dialog() {
//        mTop = llTitle.getMeasuredHeight()+ImmersionBar.getStatusBarHeight(this);
//        llSelect.setBackgroundResource(R.drawable.shape_soild_red_20_bg);
//        tvSelect.setTextColor(Color.parseColor("#ffffff"));
        int[] titlearrar = new int[2];
        llTitle.getLocationOnScreen(titlearrar);
        mTop = titlearrar[1] + (llTitle.getMeasuredHeight() / 2);
        List<ChoseBean> data = new ArrayList<>();
        if (showType == 1) {
            data = choseBeanList;
        } else {
            data = choseBean1List;
        }
        dialogUtils.choseDialog(new DialogUtils.OnItemClick() {
            @Override
            public void onItemClick(View v, int position) {
                if (showType == 1) {
                    for (int i = 0; i < choseBeanList.size(); i++) {
                        if (i == position) {
                            choseBeanList.get(i).isChose = true;
                        } else {
                            choseBeanList.get(i).isChose = false;
                        }
                    }
                    dialogUtils.setChoseDatas(choseBeanList);
                    tvSelect.setText(choseBeanList.get(position).itemContent);
//                llSelect.setBackgroundResource(R.drawable.shape_white_20);
//                tvSelect.setTextColor(Color.parseColor("#1e1e1e"));
                    dialogUtils.dismissDialog();
                    dataStr = tvTime.getText().toString().trim();
                    index = 1;
                    isShow = true;
                    type = choseBeanList.get(position).type;
                    getData();
                } else {
                    for (int i = 0; i < choseBean1List.size(); i++) {
                        if (i == position) {
                            choseBean1List.get(i).isChose = true;
                            switch (i) {
                                case 0:
                                    selectType = 1;
                                    break;
                                case 1:
                                    selectType = 2;
                                    break;
                            }
                        } else {
                            choseBean1List.get(i).isChose = false;
                        }

                    }
                    dialogUtils.setChoseDatas(choseBean1List);
                    dialogUtils.dismissDialog();
                    showPickView();
                }
            }
        }, mTop, data).outSideIsDismiss(true);
    }

    private int selectType = 0;
    TimePickerView pvTime;

    private void showPickView() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2018, 3, 1);
        endDate.set(mYear, mMonth, mDay);
        if (selectType == 1) {
            pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    tvTime.setText(sdf.format(date));
                    dataStr = tvTime.getText().toString().trim();
                    index = 1;
                    isShow = true;
                    getData();
                }
            })
                    .setDate(endDate)
                    .setType(new boolean[]{true, true, false, false, false, false})
                    .setRangDate(startDate, endDate)
                    .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                    .build();
        } else {
            pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    tvTime.setText(sdf.format(date));
                    dataStr = tvTime.getText().toString().trim();
                    index = 1;
                    isShow = true;
                    getData();
                }
            })
                    .setDate(endDate)
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .setRangDate(startDate, endDate)
                    .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                    .build();
        }
        pvTime.show();
    }

    @OnClick({R.id.heard_back, R.id.ll_select, R.id.ll_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_select:
                showType = 1;
                show1Dialog();
                break;
            case R.id.ll_time:
                showType = 2;
                show1Dialog();

                break;
        }
    }


}
