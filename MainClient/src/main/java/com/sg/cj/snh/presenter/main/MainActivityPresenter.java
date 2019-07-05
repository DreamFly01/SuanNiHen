package com.sg.cj.snh.presenter.main;


import android.text.TextUtils;

import com.sg.cj.snh.bean.CityBean;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.main.MainActivityContract;
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
public  class MainActivityPresenter extends RxPresenter<MainActivityContract.View> implements MainActivityContract.Presenter {


  private DataManager mDataManager;

  @Inject
  public MainActivityPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }


}
