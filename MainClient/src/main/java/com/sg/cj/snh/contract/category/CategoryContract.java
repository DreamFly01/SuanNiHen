package com.sg.cj.snh.contract.category;


import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.snh.model.response.category.CategoryResponse;

/**
 * 
 * @Description: 
 * @author : chenjie
 * creat at 2018/11/26 08:55
 */
public interface CategoryContract {
  interface View extends BaseView {

    void displayClassifySuccess(CategoryResponse categoryResponse);

  }

  interface Presenter extends BasePresenter<View> {

    /**
     * 24．商品分类
     */
    void doGetclassify();



  }
}
