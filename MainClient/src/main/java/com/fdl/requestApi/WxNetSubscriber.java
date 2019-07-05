package com.fdl.requestApi;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.fdl.bean.BaseResultBean;
import com.fdl.bean.WxBean;
import com.fdl.utils.DialogUtils;
import com.fdl.wedgit.CustomProgress;

import rx.Subscriber;

public abstract class WxNetSubscriber<T> extends Subscriber<T> {
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

    public WxNetSubscriber(Context context) {
        this.context = context;
    }

    public WxNetSubscriber(Context context, boolean isShowLoading) {
        this.context = context;
        this.mIsShowLoading = isShowLoading;
        dialogUtils = new DialogUtils(context);
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
            dialogUtils.simpleDialog(e.getMessage(), new DialogUtils.ConfirmClickLisener() {
                @Override
                public void onConfirmClick(View v) {
                    dialogUtils.dismissDialog();
                }
            },true);
            dismissLoadingView();
    }

    @Override
    public void onNext(T t) {
//        dialogUtils = new DialogUtils(context, (Activity) context);
//        if (t instanceof WxBean) {
//            onResultNext(t);
//            WxBean wxBean = (WxBean) t;
//            Log.d(HttpLogger.LOGKYE, "openid:" + wxBean.openid);
//        }else if(t instanceof BaseResultBean){
//            BaseResultBean o = (BaseResultBean) t;
////            Log.d(HttpLogger.LOGKYE, "onNext: t:"+((BaseResultBean) t).Code+((BaseResultBean) t).Data+((BaseResultBean) t).Message);
//            if (o.Code == 200) {
//                onResultNext(t);
//                Log.d(HttpLogger.LOGKYE,o.Message);
//            } else if(o.Code == 402){
//                dialogUtils.simpleDialog("登录过期，请重新登录", new DialogUtils.ConfirmClickLisener() {
//                    @Override
//                    public void onConfirmClick(View v) {
//                        dialogUtils.dismissDialog();
//                        JumpUtils.simpJump((Activity) context, LogingActivity.class, false);
//                    }
//                }, false);
//            }else {
//                onError(new Throwable(o.Message));
//            }
//        }else {
//            onError(new Throwable());
//        }
    }

    public abstract void onResultNext(T t);

    public void dismissLoadingView() {
        if (mIsShowLoading && null != mCustomProgress) {
            mCustomProgress.dismiss();
        }
    }

    @Override
    public void onCompleted() {
        dialogUtils.dismissDialog();
        dismissLoadingView();
    }

}
