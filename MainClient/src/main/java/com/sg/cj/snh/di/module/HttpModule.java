package com.sg.cj.snh.di.module;


import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.di.qualifier.HttpUrl;
import com.sg.cj.snh.model.http.api.HttpApi;
import com.sg.cj.snh.uitls.AddCookiesInterceptor;
import com.sg.cj.snh.uitls.HttpLogger;
import com.sg.cj.snh.uitls.ReceivedCookiesInterceptor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 09:59
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@Module
public class HttpModule {

  @Singleton
  @Provides
  Retrofit.Builder provideRetrofitBuilder() {
    return new Retrofit.Builder();
  }


  @Singleton
  @Provides
  OkHttpClient.Builder provideOkHttpBuilder() {
    return RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder());
  }


  @Singleton
  @Provides
  OkHttpClient provideClient(OkHttpClient.Builder builder) {
    if(SgLog.D){
      HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());

      logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
     // builder.addInterceptor(logInterceptor);

      builder.addNetworkInterceptor(logInterceptor);
      //builder.addInterceptor(logInterceptor);

      builder.addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
          //获得请求信息，此处如有需要可以添加headers信息
          Request request = chain.request();
          //添加Cookie信息
          request.newBuilder().addHeader("Cookie","aaaa");
          //打印请求信息
          SgLog.d("url:" + request.url());
          SgLog.d("method:" + request.method());
          SgLog.d("请求-body:" + request.toString());

          //记录请求耗时
          long startNs = System.nanoTime();
          okhttp3.Response response;
          try {
            //发送请求，获得相应，
            response = chain.proceed(request);
          } catch (Exception e) {
            throw e;
          }
          long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
          //打印请求耗时
          SgLog.d("耗时:"+tookMs+"ms");
          //使用response获得headers(),可以更新本地Cookie。
          SgLog.d("headers==========");
          Headers headers = response.headers();
          SgLog.d(headers.toString());

          //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
          ResponseBody responseBody = response.body();

          //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
          BufferedSource source = responseBody.source();
          source.request(Long.MAX_VALUE); // Buffer the entire body.
          //获得返回的数据
          Buffer buffer = source.buffer();
          //使用前clone()下，避免直接消耗
          SgLog.d("response:" + buffer.clone().readString(Charset.forName("UTF-8")));
          return response;
        }
      });



    }
    File cacheFile = new File(Constants.PATH_CACHE);
    Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
    //设置缓存

    //builder.addInterceptor(new AddCookiesInterceptor());
    builder.addInterceptor(new ReceivedCookiesInterceptor());
    //builder.addInterceptor(new CommonInterceptor());

    builder.cache(cache);
    //设置超时
    builder.connectTimeout(10, TimeUnit.SECONDS);
    builder.readTimeout(20, TimeUnit.SECONDS);
    builder.writeTimeout(20, TimeUnit.SECONDS);
    //错误重连
    //builder.retryOnConnectionFailure(true);
    return builder.build();
  }


  @Singleton
  @Provides
  @HttpUrl
  Retrofit provideHttpRetrofit(Retrofit.Builder builder,OkHttpClient client){
    return createRetrofit(builder,client,Constants.BASE_HOST);
  }


//  @Singleton
//  @Provides
//  @MainUrl
//  Retrofit provideMainRetrofit(Retrofit.Builder builder,OkHttpClient client){
//    return createRetrofit(builder,client,Constants.BASE_HOST);
//  }

  @Singleton
  @Provides
  HttpApi provideLoginService(@HttpUrl Retrofit retrofit){
    return retrofit.create(HttpApi .class);
  }


//  @Singleton
//  @Provides
//  MainApi provideMainService(@MainUrl Retrofit retrofit){
//    return retrofit.create(MainApi.class);
//  }

  private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
    return builder
        .baseUrl(url)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}
