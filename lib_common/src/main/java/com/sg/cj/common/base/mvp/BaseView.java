package com.sg.cj.common.base.mvp;

/**
 * author : ${CHENJIE}
 * created at  16/10/24 10:47
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public interface BaseView {
  void showErrorMsg(String msg);


  void showLoading();
  void closeLoading();
  void startLogin();

  //=======  State  =======
  void stateError();

  void stateEmpty();

  void stateLoading();

  void stateMain();

  void startWebview();


}

