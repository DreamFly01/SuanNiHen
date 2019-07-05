package com.sg.cj.snh.ui.activity;



import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.fdl.activity.main.MainActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.R;

/**
 * author : ${CHENJIE}
 * created at  2018/12/12 09:19
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class LaunchActivity extends BaseActivity {

  private static Handler mHandler = new Handler(Looper.getMainLooper());


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {

    ImmersionBar.with(this).init();
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    //全屏
    getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
        WindowManager.LayoutParams. FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void initInject() {

  }

  @Override
  protected int getLayout() {
    return R.layout.activity_launch;
  }

  @Override
  protected void initEventAndData() {
    mHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        //startAct(MainActivity.class);

        if(PartyApp.getAppComponent().getDataManager().getLaunchFirst()){
          System.out.println("fdl:进入启动页跳转至欢迎页面");
          startAct(WelcomeActivity.class);
        }else {
          startAct(MainActivity.class);
        }
        finish();
      }
    }, 1500);
  }
}
