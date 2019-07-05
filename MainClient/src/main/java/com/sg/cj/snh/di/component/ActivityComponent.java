package com.sg.cj.snh.di.component;


import android.app.Activity;

import com.sg.cj.snh.di.module.ActivityModule;
import com.sg.cj.snh.di.scope.ActivityScope;
import com.sg.cj.snh.ui.activity.MainActivity;
import com.sg.cj.snh.ui.activity.category.CategoryActivity;
import com.sg.cj.snh.ui.activity.login.FindPsdActivity;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.sg.cj.snh.ui.activity.login.RegisterActivity;
import com.sg.cj.snh.ui.activity.login.SmsLoginActivity;
import com.sg.cj.snh.ui.activity.login.WechatBindActivity;
import com.sg.cj.snh.ui.activity.shopcar.ShopCarActivity;
import com.wta.YdbDev.jiuwei212903.wxapi.WXEntryActivity;


import dagger.Component;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 09:40
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  Activity getActivity();

  void inject(MainActivity activity);
  void inject(LoginActivity activity);
  void inject(SmsLoginActivity activity);
  void inject(FindPsdActivity activity);
  void inject(WechatBindActivity activity);
  void inject(RegisterActivity activity);
  void inject(CategoryActivity activity);
  void inject(WXEntryActivity activity);

  void inject(ShopCarActivity activity);






}
