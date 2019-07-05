package com.sg.cj.common.base.view.indicator.slidebar;


import android.view.View;

/**
 * author : ${CHENJIE}
 * created at  2018/10/23 15:01
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public interface ScrollBar {
  public static enum Gravity {
    TOP,
    TOP_FLOAT,
    BOTTOM,
    BOTTOM_FLOAT,
    CENTENT,
    CENTENT_BACKGROUND
  }

  public int getHeight(int tabHeight);

  public int getWidth(int tabWidth);

  public View getSlideView();

  public Gravity getGravity();

  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
}

