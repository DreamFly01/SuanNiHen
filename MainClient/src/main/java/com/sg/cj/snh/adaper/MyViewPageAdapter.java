package com.sg.cj.snh.adaper;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 *
 * @Description:
 * @author : chenjie
 * creat at 2018/11/26 08:39
 */
public class MyViewPageAdapter extends FragmentPagerAdapter {
  private ArrayList<String> titleList;
  private ArrayList<Fragment> fragmentList;

  public MyViewPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
    super(fm);
    this.titleList = titleList;
    this.fragmentList = fragmentList;
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return titleList.get(position);
  }
}


