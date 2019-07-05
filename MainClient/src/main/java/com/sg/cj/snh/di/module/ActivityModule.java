package com.sg.cj.snh.di.module;


import android.app.Activity;

import com.sg.cj.snh.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 09:48
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@Module
public class ActivityModule {
  private Activity mActivity;

  public ActivityModule(Activity activity) {
    this.mActivity = activity;
  }

  @Provides
  @ActivityScope
  public Activity provideActivity() {
    return mActivity;
  }
}
