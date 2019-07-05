package com.sg.cj.snh.presenter.findgoods;


import android.text.TextUtils;

import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.findgoods.FindGoodsContract;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.response.findgoods.FindGoodsResponse;
import com.sg.cj.snh.model.response.login.LoginResponse;
import com.sg.cj.snh.uitls.CommonSubscriber;
import com.sg.cj.snh.uitls.RxPresenter;
import com.sg.cj.snh.uitls.RxUtil;

import javax.inject.Inject;

/**
 * author : ${CHENJIE}
 * created at  2018/10/25 17:16
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class FindGoodsPresenter extends RxPresenter<FindGoodsContract.View> implements FindGoodsContract.Presenter {


  private DataManager mDataManager;

  @Inject
  public FindGoodsPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }


  @Override
  public void doFindGoods(int index, int size) {

    addSubscribe(mDataManager.doFindGoods(index, size)
    .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<FindGoodsResponse>(mView){
          @Override
          public void onNext(FindGoodsResponse findGoodsResponse) {
            if (null == findGoodsResponse) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (findGoodsResponse.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayFindGods(findGoodsResponse);
              } else {
                if (TextUtils.isEmpty(findGoodsResponse.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(findGoodsResponse.getMsg());
                }
              }
            }
          }
        })
    );
  }
}
