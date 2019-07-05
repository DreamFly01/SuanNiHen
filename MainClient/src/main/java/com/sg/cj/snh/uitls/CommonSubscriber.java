package com.sg.cj.snh.uitls;


import android.text.TextUtils;

import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.model.http.exception.ApiException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 15:55
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onNext(T t) {
        SgLog.d("数据：" + t.toString());
//        mView.closeLoading();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SgLog.d("开始加载");
        mView.showLoading();
    }

    @Override
    public void onComplete() {
        SgLog.d("加载完成");
        mView.closeLoading();
    }


    @Override
    public void onError(Throwable e) {

        if (mView == null) {
            return;
        }
        mView.closeLoading();
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ApiException) {
//            if (e.getMessage().equals("获取用户信息为空") | e.getMessage().equals("用户存在微信账户,需要绑定或注册现有账号")) {
//                mView.showErrorMsg("请绑定微信");
//            } else {
                mView.showErrorMsg(e.getMessage());
//            }
            if (((ApiException) e).getCode().equals("E10001")) {
                mView.startLogin();
            }

        } else if (e instanceof HttpException) {
            mView.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.showErrorMsg("未知错误ヽ(≧Д≦)ノ");
            SgLog.d(e.toString());
        }
        if (isShowErrorState) {
            mView.stateError();
        }
    }
}
