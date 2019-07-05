package com.sg.cj.snh.view;


import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;

/**
 * EMPTY_STATE_SET
 * @Description:
 * @author : chenjie
 * creat at 2018/11/26 20:45
 */
public class SmsBtnView extends LinearLayout {

  private View rootView;

  private Context mContext;

  private TextView txtSms;

  private LinearLayout layoutSms;

  private View.OnClickListener onClickListener;

  private CountDownTimer timer;

  private boolean clickable = true;

  public SmsBtnView(Context context) {
    this(context, null);
  }

  public SmsBtnView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SmsBtnView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }

  private void initView(Context context) {
    rootView = LayoutInflater.from(context).inflate(R.layout.view_btn_sms, this);
    mContext = context;
    layoutSms = (LinearLayout) findViewById(R.id.layoutSms);
    txtSms = (TextView) findViewById(R.id.txtSendSms);
    layoutSms.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (!clickable) {
          ToastUtil.shortShow("请稍后再试");
          return;
        }
        if (null != onClickListener) {
          onClickListener.onClick(v);
        }
      }
    });

    timer = new CountDownTimer(60000, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        txtSms.setText(millisUntilFinished / 1000 + "秒后重新发送");
        SgLog.d("发送验证码  " + millisUntilFinished);
      }

      @Override
      public void onFinish() {
        txtSms.setEnabled(true);
        txtSms.setText("重新发送");
        clickable = true;
      }
    };

  }


  public void setClick(View.OnClickListener listener) {
    //layoutSms.setOnClickListener(listener);
    onClickListener = listener;
  }

  public void setTxtVisiable(int visiable) {
    txtSms.setVisibility(visiable);
  }


  public void startTime() {
    clickable = false;
    timer.start();
  }

  public void onFinish() {
    if(null!=timer) {
      clickable=true;
      timer.onFinish();
    }else {
      clickable = true;
    }

  }

  public void destory() {
    if(null!=timer) {
      clickable=true;
      timer.cancel();
    }else {
      clickable = true;
    }
  }
}
