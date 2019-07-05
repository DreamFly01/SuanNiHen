package com.sg.cj.snh.di.module;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.sg.cj.snh.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * author : ${CHENJIE}
 * created at  2018/10/29 10:10
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@Module
public class FragmentModule {

  private Fragment fragment;

  public FragmentModule(Fragment fragment) {
    this.fragment = fragment;
  }

  @Provides
  @FragmentScope
  public Activity provideActivity() {
    return fragment.getActivity();
  }
}