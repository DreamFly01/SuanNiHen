package com.fdl.activity.food;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.fdl.activity.supermarket.MapActivity;
import com.fdl.activity.supermarket.StoreCouponsActivity;
import com.fdl.adapter.FoodCommitOrderAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.db.DBManager;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
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
import pub.devrel.easypermissions.EasyPermissions;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/6/25<p>
 * <p>changeTime：2019/6/25<p>
 * <p>version：1<p>
 */
public class FoodCommitOrderActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.ll_heard)
    LinearLayout llHeard;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_more_data)
    LinearLayout llMoreData;
    @BindView(R.id.iv_upOrdown)
    ImageView upOrDown;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.goMap)
    TextView tvGoMap;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_money1)
    TextView tvMoney1;
    @BindView(R.id.tv_money2)
    TextView tvMoney2;
    @BindView(R.id.tv_redution)
    TextView tvRedution;
    @BindView(R.id.tv_TotalMoney)
    TextView tvTotalMoney;
    @BindView(R.id.ll_coupons)
    LinearLayout llCoupons;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.ll_payType)
    LinearLayout llPayType;
    @BindView(R.id.et_levelWord)
    EditText etLevelWord;
    @BindView(R.id.ll_remarks)
    LinearLayout llRemarks;
    @BindView(R.id.et_personNum)
    EditText etPersonNum;
    @BindView(R.id.ll_personNum)
    LinearLayout llPersonNum;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_conpons)
    TextView tvConpons;
    @BindView(R.id.tv_SupPhone)
    TextView tvSupPhone;
    private LinearLayout hear1;
    private LinearLayout hear2;
    private FoodCommitOrderAdapter adapter;
    private int storeId;
    private Bundle bundle;
    private boolean isLess = true;
    private String SupplierIcon, Address, distance, SupplierName, latitude1, longitude1,ServiceTel;
    private double latitude, longitude;
    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;

    private TextView tvName;
    private ImageView ivLogo;
    private long arriveTime;
    private int couponsId = 0;
    private int SupplierId;
    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_food_commitorder_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            SupplierId = bundle.getInt("SupplierId");
            SupplierIcon = bundle.getString("SupplierIcon");
            Address = bundle.getString("Address");
            distance = bundle.getString("distance");
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            latitude1 = (bundle.getString("Latitude1"));
            longitude1 = (bundle.getString("Longitude1"));
            SupplierName = bundle.getString("SupplierName");
            ServiceTel = bundle.getString("ServiceTel");
            storeId = bundle.getInt("storeId");
        }
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        setImm(false);
        ImmersionBar.with(this).setTitleBar(this, llHeard);
//        hear1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.heard_commit_store_info_layout, null);
        hear2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.heard_food_commit_order_layout, null);
        tvName = hear2.findViewById(R.id.tv_shopName);
        ivLogo = hear2.findViewById(R.id.iv_storeLogo);
        heardTitle.setText("提交订单");
        long currenTime = System.currentTimeMillis() + 30 * 60 * 1000;
        Calendar c = Calendar.getInstance();//
        c.setTimeInMillis(currenTime);

        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        mHour = c.get(Calendar.HOUR_OF_DAY);//时
        mMinute = c.get(Calendar.MINUTE);//分
        tvAddress.setText(Address);
        tvDistance.setText("距离：" + distance);
        tvName.setText(SupplierName);
        tvRedution.setText("已优惠：¥0.00");
        tvTime.setText(mHour + ":" + mMinute);
        etPhone.setText(PartyApp.getAppComponent().getDataManager().getTel());
        tvSupPhone.setText(ServiceTel);
        ImageUtils.loadUrlImage(this, SupplierIcon, ivLogo);
        setRecyclerView();
        querInit();
    }

    private void setRecyclerView() {
        adapter = new FoodCommitOrderAdapter(R.layout.item_food_commit_product_layout, null);
//        adapter.addHeaderView(hear1);
        adapter.addHeaderView(hear2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    List<CommTenant> lessData = new ArrayList<>();
    List<CommTenant> datas = new ArrayList<>();
    double totalPrice = 0;
    private List<Map<String, Object>> dataList = new ArrayList<>();
    private Map<String, Object> dataMap;

    private void querInit() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        datas = commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(storeId)).list();
        if (datas.size() > 3) {
            lessData.add(datas.get(0));
            lessData.add(datas.get(1));
            lessData.add(datas.get(2));
            adapter.setNewData(lessData);
            llMoreData.setVisibility(View.VISIBLE);
        } else {
            adapter.setNewData(datas);
            llMoreData.setVisibility(View.GONE);
        }

        for (int i = 0; i < datas.size(); i++) {
            dataMap = new TreeMap<>();
            dataMap.put("ShopGoodsId", datas.get(i).CommTenantId);
            dataMap.put("Number", datas.get(i).total);
            dataList.add(dataMap);
            totalPrice = totalPrice + (datas.get(i).Price * datas.get(i).total);
        }
        tvMoney1.setText("¥0.0");
        tvMoney2.setText("¥0.0");
        tvTotalMoney.setText("¥" + StrUtils.moenyToDH(totalPrice + ""));
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.heard_back, R.id.goMap, R.id.ll_more_data, R.id.ll_coupons, R.id.ll_call, R.id.tv_commit, R.id.tv_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_more_data:
                if (isLess) {
                    isLess = false;
                    adapter.setNewData(datas);
                    upOrDown.setBackgroundResource(R.drawable.arrow_up);
                } else {
                    isLess = true;
                    adapter.setNewData(lessData);
                    upOrDown.setBackgroundResource(R.drawable.arrow_down);
                }
                break;
            case R.id.goMap:
                goMap();
                break;
            case R.id.tv_time:
                showPicke();
                break;
            case R.id.ll_coupons:
                Intent intent = new Intent(this, StoreCouponsActivity.class);
                intent.putExtra("storeId", storeId);
                intent.putExtra("money", totalPrice);
                intent.putExtra("SupplierId", SupplierId);
                startActivityForResult(intent, 1000);
                break;
            case R.id.ll_call:
                if(StrUtils.isEmpty(tvSupPhone.getText().toString().trim())){
                    dialogUtils.noBtnDialog("商家暂无号码");
                }else {

                checkPerm();
                }
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
        String price = tvTotalMoney.getText().toString().trim().substring(tvTotalMoney.getText().toString().trim().indexOf("¥") + 1, tvTotalMoney.getText().toString().trim().length());
        addSubscription(RequestClient.CommitOrder(couponsId, (arriveTime / 1000) + "", etPhone.getText().toString().trim(), SupplierId + "", "", price + "", etLevelWord.getText().toString().trim(), dataList, this, new NetSubscriber<BaseResultBean<String>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<String> model) {
                bundle = new Bundle();
                bundle.putString("TotalMoney", price);
                bundle.putString("TotalOrderNo", model.data);
                bundle.putString("type", "美食分享");
                JumpUtils.dataJump(FoodCommitOrderActivity.this, PayActivity.class, bundle, true);
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
                couponValue = data.getDoubleExtra("CouponValue", 0);
                couponsId = data.getIntExtra("couponsId", 0);
                tvConpons.setText(data.getStringExtra("couponsInfo"));
                tvRedution.setText("已优惠：¥" + StrUtils.moenyToDH(couponValue + ""));
                double price = totalPrice - couponValue;
                tvTotalMoney.setText("¥" + StrUtils.moenyToDH(price + ""));
            }
        }
    }

    private void checkPerm() {
        String[] params = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, params)) {
            Intent myIntentDial = new Intent("android.intent.action.CALL", Uri.parse("tel:" + tvSupPhone.getText().toString().trim()));
            startActivity(myIntentDial);
        } else {
            EasyPermissions.requestPermissions(this, "需要拨打电话权限", 100, params);
        }
    }
}
