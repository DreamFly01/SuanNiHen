package com.sg.cj.common.base.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sg.cj.common.base.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : ${CHENJIE}
 * created at  2018/10/23 17:20
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public abstract class BaseNoTitleActivity extends AppCompatActivity {

  protected Activity mContext;
  private Unbinder mUnBinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    mUnBinder = ButterKnife.bind(this);
    mContext = this;
    onViewCreated();
    App.getInstance().addActivity(this);
    initEventAndData();
  }


  /**
   * 省略强转寻找控件
   */
  public <T> T $(int id) {
    return (T) findViewById(id);
  }





  protected void onViewCreated() {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    App.getInstance().removeActivity(this);
    mUnBinder.unbind();
  }

  protected abstract int getLayout();
  protected abstract void initEventAndData();

}
