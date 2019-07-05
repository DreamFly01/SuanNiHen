package com.sg.cj.snh.adaper;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sg.cj.common.base.view.indicator.IndicatorViewPager;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.fragment.main.DiscoverLayerFragment;
import com.sg.cj.snh.ui.fragment.main.HomeLayerFragment;
import com.sg.cj.snh.ui.fragment.main.NewsLayerFragment;
import com.sg.cj.snh.ui.fragment.main.SelfLayerFragment;
import com.sg.cj.snh.ui.fragment.main.ShopCarLayerFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * tabNames
 * @Description:
 * @author : chenjie
 * creat at 2018/11/26 08:39
 */
public class MainFragmentPageAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
  private String[] tabNames = {"首页", "附近", "发现", "购物车","我的"};
  private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
      R.drawable.maintab_4_selector,R.drawable.maintab_5_selector};
  private LayoutInflater inflater;


  private List<Fragment> fragments=new ArrayList<>();


  public MainFragmentPageAdapter(FragmentManager fragmentManager, Context context) {
    super(fragmentManager);
    inflater = LayoutInflater.from(context);
    fragments.add(new HomeLayerFragment());
    fragments.add(new NewsLayerFragment());
    fragments.add(new DiscoverLayerFragment());
    fragments.add(new ShopCarLayerFragment());
    fragments.add(new SelfLayerFragment());
  }

  @Override
  public int getCount() {
    return tabNames.length;
  }

  @Override
  public View getViewForTab(int position, View convertView, ViewGroup container) {
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.tab_main, container, false);
    }
    TextView textView =  convertView.findViewById(R.id.tab_txt);
    ImageView iView =  convertView.findViewById(R.id.tab_img);
    textView.setText(tabNames[position]);
    iView.setImageResource(tabIcons[position]);
    return convertView;
  }

  @Override
  public Fragment getFragmentForPage(int position) {

    return fragments.get(position);
  }
}