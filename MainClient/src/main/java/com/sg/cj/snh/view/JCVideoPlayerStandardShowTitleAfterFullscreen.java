package com.sg.cj.snh.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * author : ${CHENJIE}
 * created at  2018/12/9 21:02
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class JCVideoPlayerStandardShowTitleAfterFullscreen extends JCVideoPlayerStandard {
  public JCVideoPlayerStandardShowTitleAfterFullscreen(Context context) {
    super(context);
  }

  public JCVideoPlayerStandardShowTitleAfterFullscreen(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void setUp(String url, int screen, Object... objects) {
    super.setUp(url, screen, objects);
//    if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
//      titleTextView.setVisibility(View.VISIBLE);
//    } else {
//      titleTextView.setVisibility(View.INVISIBLE);
//    }
  }
}