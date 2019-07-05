package com.sg.cj.snh.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.common.base.App;
import com.sg.cj.common.base.activity.SimpleActivity;
import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;

import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.common.base.view.SgLoadingDialog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.di.component.ActivityComponent;
import com.sg.cj.snh.di.component.DaggerActivityComponent;
import com.sg.cj.snh.di.module.ActivityModule;
import com.sg.cj.snh.ui.activity.WebViewActivity;
import com.sg.cj.snh.ui.activity.login.LoginActivity;

import javax.inject.Inject;

/**
 * @author : chenjie
 * creat at 2018/11/26 08:39
 * @Description:
 */
public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {


  @Inject
  protected T mPresenter;
  private String title;


  protected ActivityComponent getActivityComponent() {
    return DaggerActivityComponent.builder()
        .appComponent(PartyApp.getAppComponent())
        .activityModule(getActivityModule())
        .build();

  }

  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    ImmersionBar.with(this).init();
  }

  @Override
  protected void onViewCreated() {
    super.onViewCreated();
    initInject();
    if (mPresenter != null) {
      mPresenter.attachView(this);
    }
  }

  @Override
  protected void onDestroy() {
    if (mPresenter != null) {
      mPresenter.detachView();
    }
    super.onDestroy();
  }

  @Override
  public void showErrorMsg(String msg) {
    ToastUtil.shortShow(msg);
  }



  protected boolean isLogin(){
    return PartyApp.getAppComponent().getDataManager().getId()!=0;
  }
  @Override
  public void startLogin() {
    //PartyApp.getAppComponent().getDataManager().setUserToken("");
    startAct(LoginActivity.class);
  }

  @Override
  public void stateError() {

  }

  @Override
  public void stateEmpty() {

  }

  @Override
  public void showLoading() {
    SgLoadingDialog.getInstance().showLoading(mContext);
  }

  @Override
  public void closeLoading() {
    SgLoadingDialog.getInstance().dismissLoading();
  }

  @Override
  public void stateLoading() {

  }

  @Override
  public void startWebview() {
    jumpWebview();
  }

  private void jumpWebview() {
    Intent intent = new Intent(mContext, WebViewActivity.class);
    intent.putExtra("webViewUrl", webViewUrl);

    if (TextUtils.isEmpty(webViewUrl)) {
      showErrorMsg("跳转路径有误");
      return;
    }


    startActivity(intent);
  }

  @Override
  public void stateMain() {

  }

  private String webViewUrl;

  protected void startWebviewAct(String webViewUrl) {

    this.webViewUrl = webViewUrl;
    mPresenter.doApiLogin("" + PartyApp.getAppComponent().getDataManager().getId());
  }

  protected abstract void initInject();

  /**
   * //   * 顶部标题栏
   * //   *
   * //   * @param title         标题
   * //   *
   * //
   */
  public void initTitle(String title) {
    sgTopBar.initTopBar(title);
    setTopbarVisiable(View.VISIBLE);

  }

  protected void setTopbarVisiable(int visiable){
    sgTopBar.setVisibility(visiable);
  }

}
