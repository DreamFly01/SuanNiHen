package com.sg.cj.snh.uitls;


import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.model.DataManager;

import java.io.IOException;
import java.util.HashSet;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : ${CHENJIE}
 * created at  2018/10/28 21:08
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class AddCookiesInterceptor  implements Interceptor {



  public AddCookiesInterceptor() {
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request.Builder builder = chain.request().newBuilder();
    HashSet<String> preferences = (HashSet) PartyApp.getAppComponent().getDataManager().getCookie();
    if (preferences != null) {
      for (String cookie : preferences) {
        builder.addHeader("Cookie", cookie);
        SgLog.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
      }
    }
    return chain.proceed(builder.build());
  }
}

