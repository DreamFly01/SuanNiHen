package com.sg.cj.snh.contract.main;


import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.snh.model.request.shopcar.DeleteCartGoodsRequest;
import com.sg.cj.snh.model.response.shopcar.GetCartGoodsListResponse;


/**
 * 
 * @Description: 
 * @author : chenjie
 * creat at 2018/11/26 09:12
 */
public interface ShopCarContract {
  interface View extends BaseView {


    void displayGetCartGoodsList(GetCartGoodsListResponse response);

    void displayUpdateGoodsNum();
    void displayDeleteCartGoods();
  }

  interface  Presenter extends BasePresenter<View> {

   void doGetCartGoodsList(int uid);
   void doUpdateGoodsNum(int Id, int num);
   void doDeleteCartGoods(DeleteCartGoodsRequest request);

  }
}
