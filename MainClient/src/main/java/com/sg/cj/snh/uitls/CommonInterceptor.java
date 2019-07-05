package com.sg.cj.snh.uitls;


import android.text.TextUtils;

import com.sg.cj.snh.PartyApp;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * author : ${CHENJIE}
 * created at  2018/11/5 10:33
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class CommonInterceptor implements Interceptor {


  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {

    Request oldRequest = chain.request();

    // 添加新的参数
    HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
        .newBuilder()
        .scheme(oldRequest.url().scheme())
        .host(oldRequest.url().host())
        .addQueryParameter("lnclient", "android");

//    if (!TextUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getUserToken())) {
//      authorizedUrlBuilder.addQueryParameter("lnst", PartyApp.getAppComponent().getDataManager().getUserToken());
//    }

    // 新的请求
    Request newRequest = oldRequest.newBuilder()
        .method(oldRequest.method(), oldRequest.body())
        .url(authorizedUrlBuilder.build())
        .build();

    return chain.proceed(newRequest);
  }

}
