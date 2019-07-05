package com.sg.cj.snh.contract.findgoods;


import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.snh.model.response.findgoods.FindGoodsResponse;

/**
 * @author : chenjie
 * creat at 2018/11/26 08:55
 * @Description:
 */
public interface FindGoodsContract {
  interface View extends BaseView {

    void displayFindGods(FindGoodsResponse response);

  }

  interface Presenter extends BasePresenter<View> {

    void doFindGoods(int index, int size);
  }
}
