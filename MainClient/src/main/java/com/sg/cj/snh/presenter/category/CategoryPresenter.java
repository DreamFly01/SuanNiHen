package com.sg.cj.snh.presenter.category;


import android.text.TextUtils;

import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.category.CategoryContract;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.response.category.CategoryResponse;
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
public class CategoryPresenter extends RxPresenter<CategoryContract.View> implements CategoryContract.Presenter {


  private DataManager mDataManager;

  @Inject
  public CategoryPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }


  /**
   * 24．商品分类
   */
  @Override
  public void doGetclassify() {
    addSubscribe(mDataManager.doGetclassify()
    .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<CategoryResponse>(mView){
          @Override
          public void onNext(CategoryResponse categoryResponse) {

            if (null == categoryResponse) {
              mView.showErrorMsg(Constants.ERROR_MSG);
            } else {
              if (categoryResponse.getCode().equals(Constants.SUCCESS_CODE)) {
                mView.displayClassifySuccess(categoryResponse);
              } else {
                if (TextUtils.isEmpty(categoryResponse.getMsg())) {
                  mView.showErrorMsg(Constants.ERROR_MSG);
                } else {
                  mView.showErrorMsg(categoryResponse.getMsg());
                }
              }
            }

          }
        })
    );
  }
}
