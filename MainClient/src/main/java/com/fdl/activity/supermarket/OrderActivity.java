package com.fdl.activity.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fdl.BaseActivity;
import com.fdl.activity.buy.PayActivity;
import com.fdl.adapter.SupermartOrderAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CouponsBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.db.DBManager;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/28<p>
 * <p>changeTime：2019/1/28<p>
 * <p>version：1<p> 到店自取
 */
public class OrderActivity extends BaseActivity {
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
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_gomap)
    TextView tvGomap;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.ll_youhui)
    LinearLayout llYouhui;
    @BindView(R.id.tv_coupons)
    TextView tvCoupons;
    private SupermartOrderAdapter adapter;
    private List<CommTenant> datas = new ArrayList<>();

    private String SupplierIcon, Address, distance, SupplierName, latitude1, longitude1;
    private double latitude, longitude;
    private int SupplierId;
    private Bundle bundle;
    private DialogUtils dialogUtils;
    private List<Map<String, Object>> dataList = new ArrayList<>();
    private Map<String, Object> dataMap;
    private long arriveTime;

    private int storeId;
    private int couponsId = 0;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_supermart_order_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            SupplierIcon = bundle.getString("SupplierIcon");
            SupplierId = bundle.getInt("SupplierId");
            Address = bundle.getString("Address");
            distance = bundle.getString("distance");
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            latitude1 = (bundle.getString("Latitude1"));
            longitude1 = (bundle.getString("Longitude1"));
            SupplierName = bundle.getString("SupplierName");
            storeId = bundle.getInt("storeId");
        }
        dialogUtils = new DialogUtils(this);
    }

    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        long currenTime = System.currentTimeMillis() + 30 * 60 * 1000;
        Calendar c = Calendar.getInstance();//
        c.setTimeInMillis(currenTime);

        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        mHour = c.get(Calendar.HOUR_OF_DAY);//时
        mMinute = c.get(Calendar.MINUTE);//分
        heardTitle.setText("到店自取");
        tvAddress.setText(Address);
        tvDistance.setText("距离：" + distance);
        tvName.setText(SupplierName);
        tvYouhui.setText("已优惠：¥0.00");
        tvTime.setText(mHour + ":" + mMinute);
        etPhone.setText(PartyApp.getAppComponent().getDataManager().getTel());
        ImageUtils.loadUrlImage(this, SupplierIcon, ivLogo);
        querInit();
    }

    @Override
    public void setUpLisener() {

    }


    @OnClick({R.id.heard_back, R.id.tv_gomap, R.id.ll_time, R.id.tv_commit, R.id.ll_youhui})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_gomap:
                goMap();
                break;
            case R.id.ll_time:
                showPicke();
                break;
            case R.id.ll_youhui:
                Intent intent = new Intent(this, StoreCouponsActivity.class);
                intent.putExtra("storeId", storeId);
                intent.putExtra("money", totalPrice);
                intent.putExtra("SupplierId", SupplierId);
                startActivityForResult(intent, 1000);
                break;
            case R.id.tv_commit:
                if (check()) {
                    commit();
                }
                break;
        }
    }

    private boolean check() {
        if (StrUtils.isEmpty(etPhone.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入手机号码");
            return false;
        }
        if (StrUtils.isEmpty(tvTime.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请选择到店时间");
            return false;
        }
        if (!StrUtils.checkMobile(etPhone.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入正确的手机号码");
            return false;
        }

        return true;
    }

    double totalPrice = 0;

    private void querInit() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        datas = commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(SupplierId)).list();

        adapter = new SupermartOrderAdapter(R.layout.item_supermart_order_layout, null);
        adapter.setNewData(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < datas.size(); i++) {
            dataMap = new TreeMap<>();
            dataMap.put("ShopGoodsId", datas.get(i).CommTenantId);
            dataMap.put("Number", datas.get(i).total);
            dataList.add(dataMap);
            totalPrice = totalPrice + (datas.get(i).Price * datas.get(i).total);
        }

        tvTotalPrice.setText("  合计：¥" + StrUtils.moenyToDH(totalPrice + ""));
    }

    List<String> options11 = new ArrayList<>();
    List<String> options22 = new ArrayList<>();
    List<List<String>> optins12 = new ArrayList<>();

    private void showPicke() {

//        options22.add(mMinute+"fen");
        for (int j = 0; j < 60; j++) {
            if (j < 10) {
                options22.add("0" + j + "分");
            } else {
                options22.add(j + "分");
            }
        }
        for (int i = mHour; i < 24; i++) {
            if (i < 10) {
                options11.add("0" + i + "时");
            } else {
                options11.add(i + "时");
            }
            optins12.add(options22);
        }

        OptionsPickerView pickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = options11.get(options1) + optins12.get(options1).get(options2);
                String str1 = str.replace("时", ":");
                String str2 = str1.replace("分", "");
                tvTime.setText(str2);
            }
        }).build();
        pickerView.setPicker(options11, optins12);
        pickerView.show();
    }

    private void commit() {
        String s = tvTime.getText().toString();
        String s1 = s.replace("时", ":");
        String s2 = s1.replace("分", "");
        String datastr = mYear + "-" + mMonth + "-" + mDay + " " + s2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date data = simpleDateFormat.parse(datastr + ":00");
            arriveTime = data.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String price = tvTotalPrice.getText().toString().trim().substring(tvTotalPrice.getText().toString().trim().indexOf("¥") + 1, tvTotalPrice.getText().toString().trim().length());
        addSubscription(RequestClient.CommitOrder(couponsId, (arriveTime / 1000) + "", etPhone.getText().toString().trim(), SupplierId + "", "", price + "", etContent.getText().toString().trim(), dataList, this, new NetSubscriber<BaseResultBean<String>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<String> model) {
                bundle = new Bundle();
                bundle.putString("TotalMoney", price);
                bundle.putString("TotalOrderNo", model.data);
                bundle.putString("type", "商超士多");
                JumpUtils.dataJump(OrderActivity.this, PayActivity.class, bundle, true);
                deleteDataOnDb();
            }
        }));
    }

    private void deleteDataOnDb() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
//        commTenantDao.deleteByKey(Long.parseLong(SupplierId+""));
        commTenantDao.deleteInTx(datas);
    }

    private void goMap() {
        bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("Latitude1", latitude1);
        bundle.putString("Longitude1", longitude1);
        if (!StrUtils.isEmpty(latitude1)) {
            JumpUtils.dataJump(this, MapActivity.class, bundle, false);
        } else {
            dialogUtils.noBtnDialog("店铺地址不详，无法查看地图");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private List<CouponsBean> choseDatas = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        double couponValue = 0;
        if (resultCode == 1001 && requestCode == 1000) {
            if (null != data) {
//                choseDatas = data.getExtras().getParcelableArrayList("data");
//                for (int i = 0; i < choseDatas.size(); i++) {
//                    couponValue = couponValue + choseDatas.get(i).CouponValue;
//                }
//                couponsId = choseDatas.get(0).CouponId;
//                tvCoupons.setText(choseDatas.get(0).CouponName);
//                tvYouhui.setText("已优惠：¥" + StrUtils.moenyToDH(couponValue + ""));
//                double price = totalPrice - couponValue;
//                tvTotalPrice.setText("  合计：¥" + StrUtils.moenyToDH(price + ""));
                couponValue = data.getDoubleExtra("CouponValue",0);
                couponsId = data.getIntExtra("couponsId",0);
                tvCoupons.setText(data.getStringExtra("couponsInfo"));
                tvYouhui.setText("已优惠：¥"+StrUtils.moenyToDH(couponValue+""));
                double price = totalPrice-couponValue;
                tvTotalPrice.setText("  合计：¥" + StrUtils.moenyToDH(price + ""));
            }
        }
    }
}
