package com.fdl.requestApi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            Log.d("http", "Receivedchain == null");
        Response originalResponse = chain.proceed(chain.request());
        Log.d("http", "originalResponse" + originalResponse.toString());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(originalResponse.headers("set-cookie"))
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            String[] cookieArray = s.split(";");
                            Log.d("http", s);
                            for (int i = 0; i < cookieArray.length; i++) {
                                if (cookieArray[i].contains("_auth_")) {
//                                    String cookie = cookieArray[i].replace("_auth_=","");
//                                    System.out.println("cookie1:"+cookie);
                                    Log.d("cookie","接受到的cookie："+cookieArray[i]);
                                    return cookieArray[i];
                                }
                            }
                            return null;
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            cookieBuffer.append(cookie).append(";");
                        }
                    });
            if (!"".equals(cookieBuffer.toString())) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cookie", cookieBuffer.toString());
                Log.d("http", "ReceivedCookiesInterceptor:" + cookieBuffer.toString());
                editor.commit();
            }
        }

        return originalResponse;
    }
}
