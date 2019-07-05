package com.sg.cj.snh.uitls;


import android.text.TextUtils;

import com.sg.cj.snh.model.http.exception.ApiException;
import com.sg.cj.snh.model.response.BaseResponse;
import com.sg.cj.snh.model.response.HttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 16:31
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class RxUtil {

  /**
   * 统一线程处理
   * @param <T>
   * @return
   */
  public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
    return new FlowableTransformer<T, T>() {
      @Override
      public Flowable<T> apply(Flowable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }



  /**
   * 统一返回结果处理
   * @param <T>
   * @return
   */
//  public static <T> FlowableTransformer<HttpResponse<T>, T> handleResult() {   //compose判断结果
//    return new FlowableTransformer<HttpResponse<T>, T>() {
//      @Override
//      public Flowable<T> apply(Flowable<HttpResponse<T>> httpResponseFlowable) {
//        return httpResponseFlowable.flatMap(new Function<HttpResponse<T>, Flowable<T>>() {
//          @Override
//          public Flowable<T> apply(HttpResponse<T> httpResponse) {
//            if(null!=httpResponse.getResults()) {
//              return createData(httpResponse.getResults());
//            } else {
//              return Flowable.error(new ApiException(httpResponse.getMessage(),httpResponse.getCode()));
//            }
//          }
//        });
//      }
//    };
//  }



  public static <T> FlowableTransformer<T, T> handleWeChatResult() {    //compose简化线程

    return new FlowableTransformer<T, T>() {
      @Override
      public Flowable<T> apply(Flowable<T> upstream) {
        return upstream.flatMap(new Function<T, Flowable<T>>() {
          @Override
          public Flowable<T> apply(T response) throws Exception {

            if(null==response){
              return Flowable.error(new ApiException("请求失败",""));

            }else {
              return createData(response);
            }
          }
        });
      }
    };


  }


  public static <T> FlowableTransformer<T, T> handleResult() {    //compose简化线程

    return new FlowableTransformer<T, T>() {
      @Override
      public Flowable<T> apply(Flowable<T> upstream) {
        return upstream.flatMap(new Function<T, Flowable<T>>() {
          @Override
          public Flowable<T> apply(T response) throws Exception {
            if (response instanceof BaseResponse){
              BaseResponse baseResponse=(BaseResponse)response;

              if(TextUtils.isEmpty(baseResponse.getCode())){
                return Flowable.error(new ApiException("后端返回数据有误",""));
              }else {
                return createData(response);
              }

            }else {
              return Flowable.error(new ApiException("响应体有误",""));
//
            }
          }
        });
      }
    };


  }

  public static <T> FlowableTransformer<HttpResponse<T>, T> handleHttpResponse() {   //compose判断结果
    return new FlowableTransformer<HttpResponse<T>, T>() {
      @Override
      public Flowable<T> apply(Flowable<HttpResponse<T>> httpResponseFlowable) {
        return httpResponseFlowable.flatMap(new Function<HttpResponse<T>, Flowable<T>>() {
          @Override
          public Flowable<T> apply(HttpResponse<T> httpResponse) {
            if(null!=httpResponse.getResults()) {
              return createData(httpResponse.getResults());
            } else {
              return Flowable.error(new ApiException(httpResponse.getMessage(),httpResponse.getCode()));
            }
          }
        });
      }
    };
  }

  //public static <T>FlowableSubscriber<T> ha

//  public static <T> FlowableTransformer<T, T> handleResult() {   //compose判断结果
//    return new FlowableTransformer<T, T>() {
//      @Override
//      public Flowable<T> apply(Flowable<T> httpResponseFlowable) {
//        return httpResponseFlowable.flatMap(new Function<T, Flowable<T>>() {
//          @Override
//          public Flowable<T> apply(T response) {
//            if (response instanceof BaseResponse){
//              BaseResponse baseResponse=(BaseResponse)response;
//
//              if(TextUtils.isEmpty(baseResponse.getCode())){
//                return Flowable.error(new ApiException("后端fan'h",""));
//              }else {
//                return createData(response);
//              }
//
//            }else {
//              return Flowable.error(new ApiException("响应体有误",""));
////
//            }
//
////            if(null!=httpResponse.getResults()) {
////              return createData(httpResponse.getResults());
////            } else {
////              return Flowable.error(new ApiException(httpResponse.getMessage(),httpResponse.getCode()));
////            }
//          }
//        });
//      }
//    };
//  }

  /**
   * 生成Flowable
   * @param <T>
   * @return
   */
  public static <T> Flowable<T> createData(final T t) {
    return Flowable.create(new FlowableOnSubscribe<T>() {
      @Override
      public void subscribe(FlowableEmitter<T> emitter) throws Exception {
        try {
          emitter.onNext(t);
          emitter.onComplete();
        } catch (Exception e) {
          emitter.onError(e);
        }
      }
    }, BackpressureStrategy.BUFFER);
  }



}
