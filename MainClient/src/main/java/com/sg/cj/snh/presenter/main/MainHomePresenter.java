package com.sg.cj.snh.presenter.main;


import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.sg.cj.snh.bean.CityBean;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.main.MainHomeContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.response.main.LocalAdResponse;
import com.sg.cj.snh.model.response.main.LocalgoodsdataResponse;
import com.sg.cj.snh.uitls.CommonSubscriber;
import com.sg.cj.snh.uitls.RxPresenter;
import com.sg.cj.snh.uitls.RxUtil;

import javax.inject.Inject;

/**
 * author : ${CHENJIE}
 * created at  2018/10/29 10:19
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public  class MainHomePresenter extends RxPresenter<MainHomeContract.View> implements MainHomeContract.Presenter {


  private DataManager mDataManager;

  @Inject
  public MainHomePresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }

  /**
   * 25．当地页广告
   */
  @Override
  public void doGetLocalAd(String area) {
    addSubscribe(mDataManager.doGetLocalAd(area)
    .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<LocalAdResponse>(mView){
          @Override
          public void onNext(LocalAdResponse response) {
            if (null == response) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (response.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayLocalAd(response);
              } else {
                if (TextUtils.isEmpty(response.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(response.getMsg());
                }
              }
            }
          }
        })
    );
  }

  /**
   * 26．本地推荐商品
   */
  @Override
  public void doLocalgoodsdata(int index, int size, String area, String name, RelativeLayout view) {
    addSubscribe(mDataManager.doLocalgoodsdata(index, size, area, name)
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<LocalgoodsdataResponse>(mView){
          @Override
          public void onNext(LocalgoodsdataResponse response) {
            view.setVisibility(View.GONE);
            if (null == response) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (response.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayLocalgoodsdata(response);
              } else {
                if (TextUtils.isEmpty(response.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(response.getMsg());
                }
              }
            }
          }
        })
    );
  }


  @Override
  public void doGoodsdata(int index, int size, String name) {
    addSubscribe(mDataManager.doGoodsdata(index, size, name)
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<LocalgoodsdataResponse>(mView){
          @Override
          public void onNext(LocalgoodsdataResponse response) {
            if (null == response) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (response.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayGoodsdata(response);
              } else {
                if (TextUtils.isEmpty(response.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(response.getMsg());
                }
              }
            }
          }
        })
    );
  }

  @Override
  public void doGetareas() {
    addSubscribe(mDataManager.doGetareas()
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<CityBean>(mView,false){
          @Override
          public void onNext(CityBean response) {
            if (null == response) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (response.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayGetareas(response);
              } else {
                if (TextUtils.isEmpty(response.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(response.getMsg());
                }
              }
            }
          }
        })
    );
  }
}
