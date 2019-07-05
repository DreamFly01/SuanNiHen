package com.fdl.requestApi;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>desc：拦截器 头参数<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/22<p>
 * <p>changeTime：2018/10/22<p>
 * <p>version：1<p>
 */
public class HeardInterceptor implements Interceptor {
    private Context context;

    public HeardInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //判断是否有token ，如果有就加参数头，如果没有就不加
        Request original = chain.request();
////        Log.d(HttpLogger.LOGKYE,original.header("api_version"));
//        String token = SPUtils.getInstance(context,UserEnum.USERINFO.getName()).getString(UserEnum.TOKEN.getName());
//        if ("" != token && null != token) {
//            Gson gson = new Gson();
//            TokenBean tokenData = gson.fromJson(token,TokenBean.class);
//            Request request = original.newBuilder()
////                    .addHeader("Content-Type", "application/json")
////                    .addHeader("api_version", "1.0")
//                    .addHeader("staffid", tokenData.StaffId + "")
//                    .addHeader("timestamp", Calendar.getInstance().getTimeInMillis() + "")
//                    .addHeader("nonce", (int) ((Math.random() * 9 + 1) * 100000) + "")
//                    .addHeader("signature", tokenData.SignToken)
//                    .method(original.method(), original.body())
//                    .build();
//            return chain.proceed(request);
//        } else {
            Request request = original.newBuilder()
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
//                    .addHeader("api_version", "1.0")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
//        }
//        return null;
    }
}
