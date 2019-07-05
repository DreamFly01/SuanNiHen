package com.sg.cj.snh.view;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : ${CHENJIE}
 * created at  2018/11/7 14:58
 * e_mail : chenjie_goodboy@163.com
 * describle : 我的gridview 头部
 */
public class SelfGridHeadView extends LinearLayout {


  @BindView(R.id.txt_all)
  TextView txtAll;
  @BindView(R.id.layout_delete)
  LinearLayout layoutDelete;
  private Context mContext;
  private View rootView;


  public SelfGridHeadView(Context context) {
    this(context, null);
  }

  public SelfGridHeadView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SelfGridHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }

  private void initView(Context context) {
    mContext = context;

    rootView = LayoutInflater.from(context).inflate(R.layout.view_shop_car_head, this);


  }


  @OnClick({R.id.txt_all, R.id.layout_delete})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.txt_all:
        break;
      case R.id.layout_delete:
        break;
    }
  }
}
