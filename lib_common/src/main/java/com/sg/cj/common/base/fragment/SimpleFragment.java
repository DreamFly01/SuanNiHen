package com.sg.cj.common.base.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author : ${CHENJIE}
 * created at  2018/10/29 09:56
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public abstract class SimpleFragment extends SupportFragment {

  protected View mView;
  protected Activity mActivity;
  protected Context mContext;
  private Unbinder mUnBinder;
  protected boolean isInited = false;

  @Override
  public void onAttach(Context context) {
    mActivity = (Activity) context;
    mContext = context;
    super.onAttach(context);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mView = inflater.inflate(getLayoutId(), null);
    return mView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUnBinder = ButterKnife.bind(this, view);
  }

  @Override
  public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    isInited = true;
    initEventAndData();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mUnBinder.unbind();
  }

  protected abstract int getLayoutId();
  protected abstract void initEventAndData();
}
