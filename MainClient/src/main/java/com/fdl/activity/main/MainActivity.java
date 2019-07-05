package com.fdl.activity.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fdl.BaseActivity;
import com.fdl.activity.goTravel.TravelActivity;
import com.fdl.activity.merchantEntry.PerfectCompanyThreeActivity;
import com.fdl.activity.supermarket.StoreDetails2Activity;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.sg.cj.snh.ui.fragment.main.DiscoverLayerFragment;
import com.sg.cj.snh.ui.fragment.main.HomeLayerFragment;
import com.sg.cj.snh.ui.fragment.main.SelfLayerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.yokeyword.fragmentation.SupportActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/14<p>
 * <p>changeTime：2019/1/14<p>
 * <p>version：1<p>
 */
public class MainActivity extends SupportActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.framecontent)
    FrameLayout framecontent;
    @BindView(R.id.iv_01)
    ImageView iv01;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.iv_02)
    ImageView iv02;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.iv_03)
    ImageView iv03;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.ll_03)
    LinearLayout ll03;
    @BindView(R.id.iv_04)
    ImageView iv04;
    @BindView(R.id.tv_04)
    TextView tv04;
    @BindView(R.id.ll_04)
    LinearLayout ll04;
    @BindView(R.id.iv_05)
    ImageView iv05;
    @BindView(R.id.tv_05)
    TextView tv05;
    @BindView(R.id.ll_05)
    LinearLayout ll05;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    private BuyCarFragment buyCarFragment;
    private ScanFragment scanFragment;
    private MainFragment mainFragment;
    private DiscoverFragment discoverFragment;
    private HomeFragment homeFragment;
    private Bundle bundle;
    private int index = 0;
    public static final String SHOW_FRAGMENT_INDEX = "show_fragment_index";
    public static boolean isForeground = false;
    /**
     * 退出间隔时间 单位毫秒
     */
    public static final int EXIT_TIME = 2000;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    public static MainActivity instans;

    private DialogUtils dialogUtils;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        initContentView(savedInstanceState);
        ButterKnife.bind(this);
        setUpViews();
//        checkPerm();
        setUpLisener();
        ImmersionBar.with(this).init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade().setDuration(2000));
            getWindow().setExitTransition(new Fade().setDuration(2000));
        }
    }

    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_layout);
        instans = this;
    }

    public void setUpViews() {
        dialogUtils = new DialogUtils(this);

        InitailFragments();
        llTab.getBackground().setAlpha(255);
//        checkPerm();
    }

    public void setUpLisener() {

    }

    private void InitailFragments() {

        fragmentManager = this.getSupportFragmentManager();
        bundle = getIntent().getExtras();
        if (null != bundle) {
            index = bundle.getInt(SHOW_FRAGMENT_INDEX, 0);
            setTabSelection(index);
        } else {
            setTabSelection(0);
        }
    }

    public void setTabSelection(int index) {
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments();
        switch (index) {
            case 0:
                resetBtn();
                iv01.setBackgroundResource(R.drawable.tab_1_selected);
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    transaction.add(R.id.framecontent, mainFragment);
                } else {
                    transaction.show(mainFragment);
                }
                transaction.commit();
                break;
            case 1:
//                if(Contans.debug){
//                    JumpUtils.simpJump(this,StoreDetails2Activity.class,false);
//                }else {

                    resetBtn();
                    iv02.setBackgroundResource(R.drawable.tab_3_selected);

                    if (discoverFragment == null) {
                        discoverFragment = new DiscoverFragment();
                        transaction.add(R.id.framecontent, discoverFragment);
                    } else {
                        transaction.show(discoverFragment);
                    }
                    transaction.commit();
//                }
                break;
            case 2:
                if (!isLogin()) {
                    bundle = new Bundle();
                    bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX, 2);
                    JumpUtils.dataJump(this, LoginActivity.class, bundle, false);
                } else {
                    resetBtn();
                    iv04.setBackgroundResource(R.drawable.tab_4_selected);
                    if (buyCarFragment == null) {
                        // 如果MessageFragment为空，则创建一个并添加到界面上
                        buyCarFragment = new BuyCarFragment();
                        transaction.add(R.id.framecontent, buyCarFragment);
                    } else {
                        // 如果MessageFragment不为空，则直接将它显示出来
                        transaction.show(buyCarFragment);
                    }
                    transaction.commit();

                }

                break;
            case 3:
                if (!isLogin()) {
                    bundle = new Bundle();
                    bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX, 3);
                    JumpUtils.dataJump(this, LoginActivity.class, bundle, false);
                } else {
                    resetBtn();
                    iv05.setBackgroundResource(R.drawable.tab_5_selected);
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                        transaction.add(R.id.framecontent, homeFragment);
                    } else {
                        transaction.show(homeFragment);
                    }
                    transaction.commit();

                }
                break;
            case 4:
//                JumpUtils.simpJump(this, ScanActivity.class, false);
//                iv02.setBackgroundResource(R.drawable.tab_3_selected);
                if (scanFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    scanFragment = new ScanFragment();
                    transaction.add(R.id.framecontent, scanFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(scanFragment);
                }
                transaction.commit();

                break;
        }
    }

    private void resetBtn() {
        iv01.setBackgroundResource(R.drawable.tab_1);
        iv02.setBackgroundResource(R.drawable.tab_3);
        iv04.setBackgroundResource(R.drawable.tab_4);
        iv05.setBackgroundResource(R.drawable.tab_5);

    }

    FragmentTransaction transaction;

    private void hideFragments() {
        if (discoverFragment != null) {
            if (!discoverFragment.isHidden()) {
                transaction.hide(discoverFragment);
            }
        }

        if (mainFragment != null) {
            if (!mainFragment.isHidden()) {
                transaction.hide(mainFragment);
            }
        }

        if (homeFragment != null) {
            if (!homeFragment.isHidden()) {
                transaction.hide(homeFragment);
            }
        }
        if (buyCarFragment != null) {
            if (!buyCarFragment.isHidden()) {
                transaction.hide(buyCarFragment);
            }
        }
        if (scanFragment != null) {
            if (!scanFragment.isHidden()) {
                transaction.hide(scanFragment);
            }
        }


    }


    @OnClick({R.id.ll_01, R.id.ll_02, R.id.ll_03, R.id.ll_04, R.id.ll_05})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_01:
                setTabSelection(0);
                break;
            case R.id.ll_02:
                setTabSelection(1);
                break;
            case R.id.ll_03:
                if (verfyCamera()) {
//
                    setTabSelection(4);
                }
                break;
            case R.id.ll_04:
                setTabSelection(2);
                break;
            case R.id.ll_05:

                setTabSelection(3);
                break;
        }
    }


    private long mExitTime = 0;
    private Toast toast;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > EXIT_TIME) {
                toast = Toast.makeText(this, "再按一次，退出算你狠", Toast.LENGTH_SHORT);
                toast.show();
                mExitTime = System.currentTimeMillis();
            } else {
                if (null != toast) {
                    toast.cancel();
                }
                finish();
                //System.exit(0);
                // android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            try {
                index = bundle.getInt(SHOW_FRAGMENT_INDEX, 0);
                setTabSelection(index);
            } catch (Exception e) {
                setTabSelection(0);
            }
        } else {
            setTabSelection(0);
        }
    }

    protected boolean isLogin() {
        return PartyApp.getAppComponent().getDataManager().getId() != 0;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    private boolean verfyCamera() {
        String[] params = {Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(this, params)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, params, 10020);
            }
        } else {
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10020) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        Toast.makeText(this, "请前往系统设置开启相机权限", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "该功能需要相机权限", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    setTabSelection(4);
                }
            }
        }
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (JCVideoPlayer.backPress()) {
            return;
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    @Override
    protected void onDestroy() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        // 必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy();
    }

}
