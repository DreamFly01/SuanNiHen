package com.fdl.requestApi;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fdl.bean.BaseResultBean;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.CustomProgress;
import com.google.gson.Gson;

import rx.Subscriber;

public abstract class NetSubscriber<T> extends Subscriber<T> {
    private DialogUtils dialogUtils;
    private Context context;
    /***
     * 菊花转,默认情况下是关闭状态
     */
    private CustomProgress mCustomProgress;

    /**
     * 是否显示菊花转
     */
    private boolean mIsShowLoading = false;

    public NetSubscriber(Context context) {
        this.context = context;
    }

    public NetSubscriber(Context context, boolean isShowLoading) {
        this.context = context;
        this.mIsShowLoading = isShowLoading;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (context != null && mIsShowLoading) {
            mCustomProgress = CustomProgress.show((Activity) context, null);
        }
    }


    @Override
    public void onError(Throwable e) {
        onResultErro(new APIException(e.getMessage()));
    }

    @Override
    public void onNext(T t) {
        dialogUtils = new DialogUtils(context, (Activity) context);
        if (t instanceof BaseResultBean) {
            BaseResultBean bean = (BaseResultBean) t;
            if(null!=bean.code){
            if (bean.code.equals("01")|bean.code.equals("1")) {
                onResultNext(t);
            } else {
                onResultErro(new APIException(bean.msg));
            }
            }else {
                onResultNext(t);
            }

        } else {
            onError(new Throwable());
        }

    }

    public abstract void onResultNext(T model);

    public void onResultErro(final APIException erro) {
        dialogUtils = new DialogUtils(context, (Activity) context);
        if (StrUtils.isEmpty(erro.msg)) {
            erro.msg = "网络连接失败";
        }
        if(!StrUtils.isEmpty(erro.msg)&&erro.msg.contains("未将对象引用设置到对象的实例")){
            erro.msg = "操作失败，请稍后再试";
        }
        Log.d(HttpLogger.LOGKYE, "erro: " + erro.msg);
        dialogUtils.simpleDialog(erro.msg, new DialogUtils.ConfirmClickLisener() {
            @Override
            public void onConfirmClick(View v) {
                dialogUtils.dismissDialog();
                dismissLoadingView();
            }
        }, true);
    }

    public void dismissLoadingView() {
        if (mIsShowLoading && null != mCustomProgress) {
            mCustomProgress.dismiss();
        }
    }

    @Override
    public void onCompleted() {
        dismissLoadingView();
    }

}
