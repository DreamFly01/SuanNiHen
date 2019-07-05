package com.sg.cj.common.base.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.View;
import android.widget.LinearLayout;

import com.sg.cj.common.base.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

import com.sg.cj.common.base.view.SgTopBar;
import com.sg.cj.lib_common.R;
/**
 * author : ${CHENJIE}
 * created at  2018/10/25 16:29
 * e_mail : chenjie_goodboy@163.com
 * describle :无MVP无topbar的activity基类
 */
public abstract class SimpleActivity extends SupportActivity {
  protected Activity mContext;
  private Unbinder mUnBinder;

  private LinearLayout layoutContent;

  protected SgTopBar sgTopBar;

  private View rootView;
  private View content;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContext = this;
    rootView=View.inflate(mContext,R.layout.base_title_activity,null);

    layoutContent=rootView.findViewById(R.id.layout_content);
    sgTopBar=rootView.findViewById(R.id.sgTopbar);
    if(getLayout()!=0) {
      content=View.inflate(mContext,getLayout(),layoutContent);
      setContentView(getLayout());
    }
    setContentView(rootView);
    mUnBinder = ButterKnife.bind(this);
    onViewCreated();
    App.getInstance().addActivity(this);
    initEventAndData();
  }

  /**
   * 跳转activity
   * @param cls
   */
  protected void startAct(Class<?> cls) {
    Intent intent = new Intent(mContext, cls);
    startActivity(intent);
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
