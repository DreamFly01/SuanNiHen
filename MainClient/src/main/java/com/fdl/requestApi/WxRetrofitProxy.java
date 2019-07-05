package com.fdl.requestApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class WxRetrofitProxy {

    private static RequestApi apiService = null;
    private static Retrofit retrofit = null;
    private static OkHttpClient mOkHttpClient = null;


    /**
     * initialize
     */
    public static void init(final Context context, String url) {

        Executor executor;
        executor = Executors.newCachedThreadPool();
        Gson gson = new GsonBuilder().create();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        LoggingInterceptor httpLog = new LoggingInterceptor.Builder()
//                .loggable(BuildConfig.DEBUG)
//                .setLevel(Level.BASIC)
//                .log(Platform.INFO)
//                .request("Request")
//                .response("Response")
//                .build();

        HeardInterceptor heardInterceptor = new HeardInterceptor(context);


        mOkHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(heardInterceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .removeHeader("Accept-Encoding")
                                .build();
                        return chain.proceed(request);
                    }
                })
//                .addNetworkInterceptor(httpLog)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))// 使用Gson作为数据转换器
                    .baseUrl(url)
                    .callbackExecutor(executor)
                    .client(mOkHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 使用RxJava作为回调适配器
                    .build();


        apiService = retrofit.create(RequestApi.class);

    }


    public static RequestApi getApiService(Context context, String url) {
        if (apiService != null) return apiService;
        init(context, url);
        return getApiService(context, url);
    }


}
