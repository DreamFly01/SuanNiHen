package com.sg.cj.snh.di.component;


import android.app.Activity;

import com.sg.cj.snh.di.module.FragmentModule;
import com.sg.cj.snh.di.scope.FragmentScope;
import com.sg.cj.snh.ui.fragment.category.CategoryFragment;
import com.sg.cj.snh.ui.fragment.main.DiscoverLayerFragment;
import com.sg.cj.snh.ui.fragment.main.HomeLayerFragment;

import com.sg.cj.snh.ui.fragment.main.SelfLayerFragment;
import com.sg.cj.snh.ui.fragment.main.ShopCarLayerFragment;

import dagger.Component;

/**
 * author : ${CHENJIE}
 * created at  2018/10/29 10:09
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

  Activity getActivity();

  void inject(HomeLayerFragment mFragment);
 // void inject(NewsLayerFragment mFragment);
  void inject(DiscoverLayerFragment mFragment);
  void inject(ShopCarLayerFragment mFragment);
  void inject(SelfLayerFragment mFragment);

  void inject(CategoryFragment mFragment);


}

