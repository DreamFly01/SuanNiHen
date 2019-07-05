package com.sg.cj.snh.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fdl.activity.main.MainActivity;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * author : ${CHENJIE}
 * created at  2018/12/17 11:11
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WelcomeActivity extends BaseActivity {

  private List<View> mAllianceViews;

  @BindView(R.id.activity_welcome_pager)
  ViewPager activityWelcomePager;
  private int pageScrolledNum = 0;
  @Override
  protected void initInject() {

  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {


    requestWindowFeature(Window.FEATURE_NO_TITLE);
    //全屏
    getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
        WindowManager.LayoutParams. FLAG_FULLSCREEN);

    super.onCreate(savedInstanceState);
    System.out.println("fdl:进入迎页面");

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private class MyPagerAdapter extends PagerAdapter {
    private List<View> mListView;

    public MyPagerAdapter(List<View> mListView) {
      this.mListView = mListView;
    }

    @Override
    public Object instantiateItem(View view, int position) {
      ((ViewPager) view).addView(mListView.get(position));
      return mListView.get(position);
    }

    @Override
    public int getCount() {
      return mListView.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
      return arg0 == arg1;
    }

    @Override
    public void destroyItem(View view, int position, Object object) {
      ((ViewPager) view).removeView((View) object);
    }

  }

  private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
      if (pageScrolledNum == 3 && arg0 == 3 && arg1 == 0 && arg2 == 0) {

        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        finish();
      }

      pageScrolledNum = arg0;
    }

    @Override
    public void onPageSelected(int arg0) {



    }

  }



  @Override
  protected int getLayout() {
    return R.layout.activity_welcome_new;
  }

  @Override
  protected void initEventAndData() {

    mAllianceViews = new ArrayList<View>();

    mAllianceViews.clear();

    ImageView iv1 = new ImageView(this);
    Glide.with(this).load(R.drawable.welcome_page_1).into(iv1);
//    iv1.setBackgroundDrawable(getResources().getDrawable(R.drawable.welcome_page_1));
    mAllianceViews.add(iv1);
    ImageView iv2 = new ImageView(this);
    Glide.with(this).load(R.drawable.welcome_page_2).into(iv2);
//    iv2.setBackgroundDrawable(getResources().getDrawable(R.drawable.welcome_page_2));
    mAllianceViews.add(iv2);
    ImageView iv3 = new ImageView(this);

//    iv3.setBackgroundDrawable(getResources().getDrawable(R.drawable.welcome_page_3));
    Glide.with(this).load(R.drawable.welcome_page_3).into(iv3);
    mAllianceViews.add(iv3);


    iv3.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        PartyApp.getAppComponent().getDataManager().setLaunchFirst(false);
//        startAct(MainActivity.class);
        startAct(MainActivity.class);
        finish();

      }
    });


    activityWelcomePager.setAdapter(new MyPagerAdapter(mAllianceViews));
    activityWelcomePager.setOnPageChangeListener(new MyOnPageChangeListener());
    activityWelcomePager.invalidate();

  }


}
