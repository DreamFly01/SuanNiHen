package com.sg.cj.common.base.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.SgUIUtils;
import com.sg.cj.lib_common.R;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * author : ${CHENJIE}
 * created at  2018/10/29 08:45
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgLoadingDialog {
  private static final String TAG = SgLoadingDialog.class.getSimpleName();
  private static SgLoadingDialog mCcbLoadingDialog = null;
  private Dialog dlg = null;
  private Handler handler = new Handler(Looper.getMainLooper());

  private static Queue<Context> mQueue;

  private Context mContext;

  private SgLoadingDialog() {
    super();
  }

  public synchronized static SgLoadingDialog getInstance() {
    if (null == mCcbLoadingDialog) {
      mCcbLoadingDialog = new SgLoadingDialog();
      mQueue = new LinkedBlockingQueue<Context>();
    }
    return mCcbLoadingDialog;
  }

  private void createLoadingDialog(Context context) {
    mQueue.offer(context);
    SgLog.d(TAG, String.format("=======================%s request add=====================", context.toString()));
    printQueue();
    SgUIUtils.printCallStack();
    dlg = new AlertDialog.Builder(context, R.style.Sg_Theme_Dialog).create();
    dlg.show();
    View view = LayoutInflater.from(context).inflate(R.layout.sg_dialog_loading, null);
    dlg.setCancelable(false);
    dlg.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    ViewGroup.LayoutParams VG_LP_FW = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(VG_LP_FW);
    LP_FW.gravity = Gravity.CENTER_HORIZONTAL;
    dlg.getWindow().setContentView(view, LP_FW);
    SgLog.d(TAG, "======================= show loading dialog=====================");
  }

  /**
   * 弹出loading框
   */
  public synchronized void showLoading(Context context) {
    if (context == null)
      return;

    //非当前界面 清除所有队列
    if (mContext != context) {
      reset();
    }

    mContext = context;
    if (dlg != null && dlg.isShowing()) {
      SgLog.d(TAG, String.format("=======================%s has loading no handle=====================", context.toString()));
      mQueue.offer(context);
      SgLog.d(TAG, String.format("=======================%s request add=====================", context.toString()));
      printQueue();
    } else {
      createLoadingDialog(context);
    }
    SgUIUtils.printCallStack();
  }

  private void reset() {
    handler.removeCallbacksAndMessages(null);
    dismiss();
    mQueue.clear();
  }

  /**
   * 关闭loading框
   */
  public synchronized boolean dismissLoading() {
    boolean hasRemoved = false;
    if (mQueue.size() > 0) {
      Context currentContext = mQueue.poll();
      SgLog.d(TAG, String.format("=======================%s request remove=====================", currentContext.toString()));
      hasRemoved = true;
    }
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        SgLog.d(TAG, "=======================current queue size=====================" + mQueue.size());
        printQueue();
        if (mQueue.size() > 0)
          return;
        dismiss();
      }
    }, 100);

    SgUIUtils.printCallStack();

    return hasRemoved;
  }

  public synchronized void dismissLoadingDialogImmediatly(Context context) {
    if (mQueue.size() == 1) {
      Context currentContext = mQueue.peek();
      if (currentContext == context) {
        mQueue.poll();
        SgLog.i(TAG, "dismissLoadingDialogImmediatly, context : " + context);
        dismiss();
        return;
      }
    }
    dismissLoading();
  }

  private void dismiss() {
    if (null == dlg)
      return;
    dlg.dismiss();
    dlg.cancel();
    dlg = null;
    SgLog.d(TAG, "=======================dismiss loading dialog=====================");
  }

  private void printQueue() {
    for (Context context : mQueue) {
      SgLog.d(TAG, "======mQueue======" + context.toString());
    }
  }

  /**
   * 显示loading框，同时支持按键事件
   */
  public synchronized void show(Context context, DialogInterface.OnKeyListener keyListener) {
    showLoading(context);
    dlg.setOnKeyListener(keyListener);
  }


  public boolean isShow() {
    if (null != dlg) {
      return dlg.isShowing();
    }
    return false;
  }

  public Context getContext() {
    return mContext;
  }

  public void clearContext() {
    if (null != mContext) {
      reset();
    }
  }
}
