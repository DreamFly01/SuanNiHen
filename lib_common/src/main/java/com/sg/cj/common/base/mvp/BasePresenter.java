package com.sg.cj.common.base.mvp;

/**
 * author : ${CHENJIE}
 * created at  16/10/24 10:48
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();

    void doApiLogin(String uid);

}
