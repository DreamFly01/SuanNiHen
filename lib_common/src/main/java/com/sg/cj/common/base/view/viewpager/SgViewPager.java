package com.sg.cj.common.base.view.viewpager;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author : ${CHENJIE}
 * created at  2018/10/23 14:52
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgViewPager extends ViewPager {

  private boolean canScroll;

  public SgViewPager(Context context) {
    super(context);
    canScroll = false;
  }

  public SgViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    canScroll = false;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (canScroll) {
      try {
        return super.onInterceptTouchEvent(ev);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
        return false;
      }
    }
    return false;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (canScroll) {
      return super.onTouchEvent(event);
    }
    return false;
  }

  public void toggleLock() {
    canScroll = !canScroll;
  }

  public void setCanScroll(boolean canScroll) {
    this.canScroll = canScroll;
  }

  public boolean isCanScroll() {
    return canScroll;
  }

}
