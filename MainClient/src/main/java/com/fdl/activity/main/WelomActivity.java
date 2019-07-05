package com.fdl.activity.main;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fdl.BaseActivity;
import com.fdl.db.DBManager;
import com.fdl.db.MyOpenHelper;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/21<p>
 * <p>changeTime：2019/1/21<p>
 * <p>version：1<p>
 */
public class WelomActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    private static int[] imagIds = {R.drawable.welcome_page_1, R.drawable.welcome_page_2,
            R.drawable.welcome_page_3};
    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;
    @BindView(R.id.activity_welcome_pager)
    ViewPager activityWelcomePager;
    public static WelomActivity instans;
    private DialogUtils dialogUtils;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setImm(false);
        instans = this;
        dialogUtils = new DialogUtils(this);
        setContentView(R.layout.activity_welcom_layout);
    }

    private Timer timer = new Timer();
    private int num = 2;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    num--;
//                    tvJump.setText("跳过("+num+")");
                    if(num<1){
                        timer.cancel();
                       checkPerm();
                    }
                }
            });
        }
    };
    @Override
    public void setUpViews() {
        dialogUtils.initJson();
        if (PartyApp.getAppComponent().getDataManager().getLaunchFirst()) {
            ivWelcomeBg.setVisibility(View.GONE);
            activityWelcomePager.setVisibility(View.VISIBLE);
            initWelcom();
            SPUtils.getInstance(this).saveData(Contans.SP_HOSt,Contans.HOST);
            SPUtils.getInstance(this).saveData(Contans.LAST_CITY, "全国");
            SPUtils.getInstance(this).saveData(Contans.LAST_CITY_ID, "全国");
        } else {
            ivWelcomeBg.setVisibility(View.VISIBLE);
            activityWelcomePager.setVisibility(View.GONE);
            tvJump.setVisibility(View.GONE);
            timer.schedule(task,0,1000);
        }
    }

    @Override
    public void setUpLisener() {

    }

    private void initWelcom() {
        List<ImageView> datas = new ArrayList<>();
        ivWelcomeBg.setVisibility(View.GONE);
        for (int i = 0; i < imagIds.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            iv.setImageResource(imagIds[i]);
            Glide.with(this).load(imagIds[i]).into(iv);
            datas.add(iv);

        }
        MyViewPagerAdapor myViewPagerAdapor = new MyViewPagerAdapor(datas);
        activityWelcomePager.setAdapter(myViewPagerAdapor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
//        DBManager.getInstance(this,"buy_cart_tab").deleSQL();
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        MyOpenHelper helper = new MyOpenHelper(this,"test_db",null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        commTenantDao.deleteAll();

    }

    @OnClick({R.id.tv_jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jump:
                timer.cancel();
                checkPerm();
                break;
        }
    }




    private class MyViewPagerAdapor extends PagerAdapter {

        private List<ImageView> mList = new ArrayList<>();

        public MyViewPagerAdapor(List<ImageView> data) {
            mList = data;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 2) {
                mList.get(2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPerm();
                    }
                });
            }
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }

    private void checkPerm() {
        if(PartyApp.getAppComponent().getDataManager().getLaunchFirst()){
            PartyApp.getAppComponent().getDataManager().setLaunchFirst(false);
        }
        Bundle bundle = new Bundle();
        JumpUtils.dataJump(WelomActivity.this,MainActivity.class,bundle,true);
//        String[] params={Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        if(EasyPermissions.hasPermissions(this,params)){
//
//        }else{
////          EasyPermissions.requestPermissions(this,"需要读写本地权限",1,params);
//            EasyPermissions.requestPermissions(
//                    new PermissionRequest.Builder(this, 1, params)
//                            .setRationale("需要读写本地权限")
//                            .setPositiveButtonText("确定")
//                            .setNegativeButtonText("取消")
//                            .setTheme(R.style.permision)
//                            .build());
//        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        dialogUtils.noBtnDialog("请前往开启权限");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
