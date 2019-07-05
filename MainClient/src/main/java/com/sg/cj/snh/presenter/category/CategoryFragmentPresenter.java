package com.sg.cj.snh.presenter.category;


import com.sg.cj.snh.contract.category.CategoryContract;
import com.sg.cj.snh.contract.category.CategoryFragmentContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.response.category.CategoryResponse;
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
public class CategoryFragmentPresenter extends RxPresenter<CategoryFragmentContract.View> implements CategoryFragmentContract.Presenter {


  private DataManager mDataManager;

  @Inject
  public CategoryFragmentPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }



}
