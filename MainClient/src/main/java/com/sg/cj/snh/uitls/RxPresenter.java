package com.sg.cj.snh.uitls;


import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.model.response.BaseResponse;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : ${CHENJIE}
 * created at  2018/10/25 17:20
 * e_mail : chenjie_goodboy@163.com
 * describle : 基于Rx的Presenter封装,控制订阅的生命周期
 */
public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

  protected T mView;
  protected CompositeDisposable mCompositeDisposable;

  protected void unSubscribe() {
    if (mCompositeDisposable != null) {
      mCompositeDisposable.clear();
    }
  }

  protected void addSubscribe(Disposable subscription) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = new CompositeDisposable();
    }
    mCompositeDisposable.add(subscription);
  }

  protected <U> void addRxBusSubscribe(Class<U> eventType, Consumer<U> act) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = new CompositeDisposable();
    }
    mCompositeDisposable.add(RxBus.getDefault().toDefaultFlowable(eventType, act));
  }

  @Override
  public void attachView(T view) {
    this.mView = view;
  }

  @Override
  public void detachView() {
    this.mView = null;
    unSubscribe();
  }

  @Override
  public void doApiLogin(String uid) {
    addSubscribe(PartyApp.getAppComponent().getDataManager().doApiLogin(uid)
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<BaseResponse> (mView){
          @Override
          public void onNext(BaseResponse baseResponse) {
            mView.startWebview();
          }
        })


    );


  }
}
