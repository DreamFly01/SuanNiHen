package com.sg.cj.snh.presenter.self;


import android.text.TextUtils;

import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.self.MainSelfFragmentContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.response.self.GetDataResponse;
import com.sg.cj.snh.model.response.self.PersonInfoResponse;
import com.sg.cj.snh.uitls.CommonSubscriber;
import com.sg.cj.snh.uitls.RxPresenter;
import com.sg.cj.snh.uitls.RxUtil;

import javax.inject.Inject;

/**
 * author : ${CHENJIE}
 * created at  2018/11/8 11:20
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class MainSelfFragmentPresenter extends RxPresenter<MainSelfFragmentContract.View> implements MainSelfFragmentContract.Presenter {


  private DataManager mDataManager;


  @Inject
  public MainSelfFragmentPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }

  @Override
  public void doPersoninfo(String uid) {
    addSubscribe(mDataManager.doPersoninfo(uid)
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<PersonInfoResponse>(mView) {
          @Override
          public void onNext(PersonInfoResponse personInfoResponse) {

            if (null == personInfoResponse) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (personInfoResponse.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayPersoninfo(personInfoResponse);
              } else {
                if (TextUtils.isEmpty(personInfoResponse.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(personInfoResponse.getMsg());
                }
              }
            }

          }
        })

    );
  }

  /**
   * 10．个人中心推荐商品
   */
  @Override
  public void doGetData(int index, int size, String name) {

    addSubscribe(mDataManager.doGetData(index, size, name)
        .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<GetDataResponse>(mView) {
          @Override
          public void onNext(GetDataResponse getDataResponse) {
            if (null == getDataResponse) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (getDataResponse.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayGetData(getDataResponse);
              } else {
                if (TextUtils.isEmpty(getDataResponse.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(getDataResponse.getMsg());
                }
              }
            }

          }
        })

    );

  }
}
