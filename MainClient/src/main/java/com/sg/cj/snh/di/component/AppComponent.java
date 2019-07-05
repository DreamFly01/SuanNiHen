package com.sg.cj.snh.di.component;


import com.sg.cj.common.base.App;
import com.sg.cj.snh.di.module.AppModule;
import com.sg.cj.snh.di.module.HttpModule;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.http.RetrofitHelper;
import com.sg.cj.snh.model.prefs.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 09:50
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

  App getContext();  // 提供App的Context

  DataManager getDataManager(); //数据中心

  RetrofitHelper retrofitHelper();  //提供http的帮助类


  ImplPreferencesHelper preferencesHelper(); //提供sp帮助类
}

