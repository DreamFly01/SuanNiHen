package com.sg.cj.common.base.view;


import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.cj.lib_common.R;

/**
 * author : ${CHENJIE}
 * created at  2018/10/23 17:23
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgTopBar extends LinearLayout {

  private View topBarView;
  private TextView tvTopBarTitle;

  private String title;

  public SgTopBar(Context context) {
    this(context, null);
  }

  public SgTopBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SgTopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }

  private void initView(Context context){
    topBarView = LayoutInflater.from(context).inflate(R.layout.layout_top_bar, this);
    tvTopBarTitle = (TextView) topBarView.findViewById(R.id.top_bar_titl_tv);

  }


  public void initTopBar(String title) {
    this.title = title;
    showTopBar();
  }

  public void showTopBar() {
    tvTopBarTitle.setText(title);
  }
}
