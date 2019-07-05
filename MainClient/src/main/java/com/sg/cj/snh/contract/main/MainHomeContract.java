package com.sg.cj.snh.contract.main;


import android.widget.RelativeLayout;

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
public interface MainHomeContract {
  interface View extends BaseView {

    void displayLocalAd(LocalAdResponse response);

    void displayLocalgoodsdata(LocalgoodsdataResponse response);
    void displayGoodsdata(LocalgoodsdataResponse response);

    void displayGetareas(CityBean bean);
  }

  interface Presenter extends BasePresenter<View> {

    /**
     * 25．当地页广告
     */

    void doGetLocalAd(String area);

    /**
     * 26．本地推荐商品
     */
    void doLocalgoodsdata(int index, int size, String area, String name, RelativeLayout view);

    /**
     * 26．全国商品
     */
    void doGoodsdata(int index, int size, String name);

    void doGetareas();
  }
}
