package com.sg.cj.snh.di.module;


import com.sg.cj.common.base.App;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.http.HttpHelper;
import com.sg.cj.snh.model.http.RetrofitHelper;
import com.sg.cj.snh.model.prefs.ImplPreferencesHelper;
import com.sg.cj.snh.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 09:58
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@Module
public class AppModule {
  private final App application;

  public AppModule(App application) {
    this.application = application;
  }

  @Provides
  @Singleton
  App provideApplicationContext() {
    return application;
  }

  @Provides
  @Singleton
  HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
    return retrofitHelper;
  }

  @Provides
  @Singleton
  PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
    return implPreferencesHelper;
  }

  @Provides
  @Singleton
  DataManager provideDataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper) {
    return new DataManager(httpHelper, preferencesHelper);
  }
}
