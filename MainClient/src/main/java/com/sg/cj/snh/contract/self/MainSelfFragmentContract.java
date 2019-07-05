package com.sg.cj.snh.contract.self;


import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.snh.model.response.self.GetDataResponse;
import com.sg.cj.snh.model.response.self.PersonInfoResponse;

/**
 * author : ${CHENJIE}
 * created at  2018/10/29 10:20
 * e_mail : chenjie_goodboy@163.com
 * describle :首页我的
 */
public interface MainSelfFragmentContract {
  interface View extends BaseView {

    void displayPersoninfo(PersonInfoResponse response);
    void displayGetData(GetDataResponse response);

  }

  interface Presenter extends BasePresenter<View> {
    void doPersoninfo(String uid);

    /**
     * 10．个人中心推荐商品
     */
    void doGetData(int index, int size, String name);
  }
}
