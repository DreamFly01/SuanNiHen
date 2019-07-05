package com.sg.cj.snh.uitls;


import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.model.DataManager;

import java.io.IOException;
import java.util.HashSet;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * author : ${CHENJIE}
 * created at  2018/10/28 21:04
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class ReceivedCookiesInterceptor implements Interceptor {



 @Inject
  public ReceivedCookiesInterceptor() {
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Response originalResponse = chain.proceed(chain.request());

    if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//      HashSet<String> cookies = new HashSet<>();
//
//      for (String header : originalResponse.headers("Set-Cookie")) {
//        cookies.add(header);
//      }
//
//      PartyApp.getAppComponent().getDataManager().setCookie(cookies);



      final StringBuffer cookieBuffer = new StringBuffer();

      for (String header : originalResponse.headers("Set-Cookie")) {
        cookieBuffer.append(header).append(";");
      }
      PartyApp.getAppComponent().getDataManager().setCookieString(cookieBuffer.toString());



    }

    return originalResponse;
  }
}

