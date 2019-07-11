package com.fdl.activity.food;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.adapter.FoodPredetermine1Adapter;
import com.fdl.adapter.FoodPredetermine2Adapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.FoodNumSelectBean;
import com.fdl.bean.SubscribeInfoBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.TimeUtils;
import com.fdl.utils.ToastUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/6/26<p>
 * <p>changeTime：2019/6/26<p>
 * <p>version：1<p>
 */
public class FoodPredetermineStepOneActivity extends BaseActivity {
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
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.recyclerView4)
    RecyclerView recyclerView4;
    @BindView(R.id.recyclerView5)
    RecyclerView recyclerView5;
    @BindView(R.id.tv_content_desc)
    TextView desc;
    @BindView(R.id.heard_fl_menu)
    FrameLayout hearMenu;

    private int id;
    private Bundle bundle;
    private FoodPredetermine1Adapter adapter1;
    private FoodPredetermine1Adapter adapter2;
    private FoodPredetermine2Adapter adapter3;
    private FoodPredetermine1Adapter adapter4;
    private FoodPredetermine2Adapter adapter5;
    private List<FoodNumSelectBean> data1 = new ArrayList<>();
    private List<FoodNumSelectBean> data2 = new ArrayList<>();
    private List<FoodNumSelectBean> data3 = new ArrayList<>();
    private List<FoodNumSelectBean> data4 = new ArrayList<>();
    private List<FoodNumSelectBean> data5 = new ArrayList<>();
    private String str1 = "", str2 = "", str3 = "", str4 = "", str5 = "";
    private String time;
    private int seatType = 0;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_food_perdetermine_one_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getInt("storeId");
        }
        setImm(false);

    }

    @Override
    public void setUpViews() {
        getData();
        setRecyclerView();
        heardTitle.setText("订单详情");
        heardTvMenu.setText("提交");
        heardMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUpLisener() {
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < data1.size(); i++) {
                    if (i == position) {
                        data1.get(i).isSelect = true;
                    } else {
                        data1.get(i).isSelect = false;
                    }
                }
                str1 = "成人" + data1.get(position).content1;
                adapter1.setNewData(data1);
                setDesc();
            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < data2.size(); i++) {
                    if (i == position) {
                        data2.get(i).isSelect = data2.get(i).isSelect ? false : true;
                    } else {
                        data2.get(i).isSelect = false;
                    }
                }
                if (data2.get(position).isSelect) {
                    str2 = "儿童" + data2.get(position).content1;
                } else {
                    str2 = "";
                }
                adapter2.setNewData(data2);
                setDesc();
            }
        });
        adapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < data3.size(); i++) {
                    if (i == position) {
                        data3.get(i).isSelect = true;
                    } else {
                        data3.get(i).isSelect = false;
                    }
                }
                str3 = "," + data3.get(position).content1;
                time = data3.get(position).time;
                adapter3.setNewData(data3);
                setDesc();
            }
        });
        adapter4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < data4.size(); i++) {
                    if (i == position) {
                        data4.get(i).isSelect = true;
                    } else {
                        data4.get(i).isSelect = false;
                    }
                }
                str4 = "," + data4.get(position).content1;
                adapter4.setNewData(data4);
                setDesc();
            }
        });
        adapter5.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < data5.size(); i++) {
                    if (i == position) {
                        data5.get(i).isSelect = true;
                    } else {
                        data5.get(i).isSelect = false;
                    }
                }
                seatType = position;
                str5 = "," + data5.get(position).content1 + data5.get(position).content2;
                adapter5.setNewData(data5);
                setDesc();
            }
        });
    }

    private void setRecyclerView() {
        adapter1 = new FoodPredetermine1Adapter(R.layout.item_predetermine_1_layout, null);
        adapter2 = new FoodPredetermine1Adapter(R.layout.item_predetermine_1_layout, null);
        adapter3 = new FoodPredetermine2Adapter(R.layout.item_predetermine_3_layout, null);
        adapter4 = new FoodPredetermine1Adapter(R.layout.item_predetermine_4_layout, null);
        adapter5 = new FoodPredetermine2Adapter(R.layout.item_predetermine_5_layout, null);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView5.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);
        recyclerView4.setAdapter(adapter4);
        recyclerView5.setAdapter(adapter5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private SubscribeInfoBean bean;

    private void getData() {
        addSubscription(RequestClient.GetSubscribeInfo(id, this, new NetSubscriber<BaseResultBean<SubscribeInfoBean>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<SubscribeInfoBean> model) {
                bean = model.data;
                setData();
            }
        }));
    }

    @OnClick({R.id.heard_back, R.id.heard_fl_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.heard_fl_menu:
                if (check()) {
                    postData();
                }
                break;
        }
    }

    private void setData() {
        if (bean.SubscribeNumber > 0) {
            for (int i = 0; i < bean.SubscribeNumber; i++) {
                FoodNumSelectBean bean = new FoodNumSelectBean();
                bean.content1 = (i + 1) + "";
                data1.add(bean);
                data2.add(bean);
            }
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        for (int i = 0; i <= bean.SubscribeEarlyDay; i++) {
            FoodNumSelectBean bean = new FoodNumSelectBean();
            c.add(Calendar.DAY_OF_MONTH, 1);
            switch (i) {
                case 0:
                    bean.content1 = "今天";
                    bean.content2 = TimeUtils.getWeek(sf.format(c.getTime()));
                    break;
                case 1:
                    bean.content1 = "明天";
                    bean.content2 = TimeUtils.getWeek(sf.format(c.getTime()));
                    break;
                case 2:
                    bean.content1 = sf.format(c.getTime()).substring(5, sf.format(c.getTime()).length());
                    bean.content2 = TimeUtils.getWeek(sf.format(c.getTime()));
                    break;
                case 3:
                    bean.content1 = sf.format(c.getTime()).substring(5, sf.format(c.getTime()).length());
                    bean.content2 = TimeUtils.getWeek(sf.format(c.getTime()));
                    break;
                case 4:
                    bean.content1 = sf.format(c.getTime()).substring(5, sf.format(c.getTime()).length());
                    bean.content2 = TimeUtils.getWeek(sf.format(c.getTime()));
                    break;
            }
            bean.time = sf.format(c.getTime());
            data3.add(bean);
        }

        for (int i = 0; i <= (bean.BusinessHoursE - bean.BusinessHoursF)*2; i++) {
            FoodNumSelectBean bean = new FoodNumSelectBean();
            if (i % 2 == 0) {
                bean.content1 = (this.bean.BusinessHoursF + i) + ":30";
                data4.add(bean);
            } else {
                bean.content1 = (this.bean.BusinessHoursF + i) + ":00";
                data4.add(bean);
            }
        }
        for (int i = 0; i < 2; i++) {
            FoodNumSelectBean bean = new FoodNumSelectBean();
            switch (i) {
                case 0:
                    bean.content1 = "大厅";
                    bean.content2 = "";
                    break;
                case 1:
                    bean.content1 = "包厢";
                    bean.content2 = "6人起订";
                    break;
            }
            data5.add(bean);

        }
        adapter1.setNewData(data1);
        adapter2.setNewData(data2);
        adapter3.setNewData(data3);
        adapter4.setNewData(data4);
        adapter5.setNewData(data5);
    }

    private boolean check() {
        if (StrUtils.isEmpty(str1)) {
            ToastUtils.toast("请选择成人数量");
            return false;
        }
        if (StrUtils.isEmpty(str3)) {
            ToastUtils.toast("请选择用餐时间");
            return false;
        }
        if (StrUtils.isEmpty(str4)) {
            ToastUtils.toast("请选择用餐时间");
            return false;
        }
        if (StrUtils.isEmpty(str5)) {
            ToastUtils.toast("请选择用餐位置");
            return false;
        }
        if (seatType == 1 && (Integer.parseInt(str1.substring(2))< 6)) {
            ToastUtils.toast("包厢起订6人");
            return false;
        }
        return true;
    }

    private void setDesc() {
        desc.setText(str1 + str2 + str3 + str4 + str5);
    }

    private void postData() {
        if(StrUtils.isEmpty(str2)){
            str2 = "儿童0";
        }
        addSubscription(RequestClient.PostSubscribeInfo(id, Integer.parseInt(str1.substring(2)), Integer.parseInt(str2.substring(2)), seatType, time + " " + str4, this, new NetSubscriber<BaseResultBean<Integer>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                bundle = new Bundle();
                bundle.putInt("applyId", (Integer) model.data);
                bundle.putInt("storeId", id);
                JumpUtils.dataJump(FoodPredetermineStepOneActivity.this, FoodPredetermineStepTwoActivity.class, bundle, true);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersionBar.with(this).statusBarColor(R.color.white).statusBarDarkFont(true).init();
    }
}
