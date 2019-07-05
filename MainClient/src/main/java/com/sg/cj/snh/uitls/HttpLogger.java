package com.sg.cj.snh.uitls;


import com.sg.cj.common.base.utils.SgLog;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author : ${CHENJIE}
 * created at  2018/10/28 17:43
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
  @Override
  public void log(String message) {
    SgLog.d("HttpLogInfo", message);
  }

}
