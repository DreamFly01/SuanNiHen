package com.sg.cj.snh.presenter.main;


import android.text.TextUtils;

import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.main.ShopCarContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.request.shopcar.DeleteCartGoodsRequest;
import com.sg.cj.snh.model.response.BaseResponse;
import com.sg.cj.snh.model.response.shopcar.GetCartGoodsListResponse;
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
public  class ShopCarPresenter extends RxPresenter<ShopCarContract.View> implements ShopCarContract.Presenter {


  private DataManager mDataManager;

  @Inject
  public ShopCarPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }

  @Override
  public void doGetCartGoodsList(int uid) {
    addSubscribe(mDataManager.doGetCartGoodsList(uid)
    .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<GetCartGoodsListResponse>(mView){
          @Override
          public void onNext(GetCartGoodsListResponse response) {
            if (null == response) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (response.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayGetCartGoodsList(response);
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
  public void doUpdateGoodsNum(int Id, int num) {

    addSubscribe(mDataManager.doUpdateGoodsNum(Id,num)
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<BaseResponse>(mView){
          @Override
          public void onNext(BaseResponse response) {
            if (null == response) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (response.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayUpdateGoodsNum();
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
  public void doDeleteCartGoods(DeleteCartGoodsRequest request) {
    addSubscribe(mDataManager.doDeleteCartGoods(request)
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<BaseResponse>(mView){
          @Override
          public void onNext(BaseResponse response) {
            if (null == response) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (response.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayDeleteCartGoods();
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
