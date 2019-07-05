package com.sg.cj.snh.contract.main;


import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.snh.bean.CityBean;
import com.sg.cj.snh.model.response.main.LocalAdResponse;
import com.sg.cj.snh.model.response.main.LocalgoodsdataResponse;


/**
 * @author : chenjie
 * creat at 2018/11/26 09:12
 * @Description:
 */
public interface MainActivityContract {
  interface View extends BaseView {

  }

  interface Presenter extends BasePresenter<View> {


  }
}
