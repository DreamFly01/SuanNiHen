package com.fdl.wedgit;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fdl.utils.DialogUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

/**
 * <p>desc：短信验证码控件<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/18<p>
 * <p>changeTime：2018/10/18<p>
 * <p>version：1<p>
 */
public class IdentifyCodeView extends LinearLayout implements View.OnClickListener {


    private Button btn_yzm;
    private CountDownTimer countDownTimer;
    private EditText et_tel;
    private boolean isGetVertifyCode = false;
    private DialogUtils dialogUtils;

    public void setIdentifyCodeViewLisener(IdentifyCodeViewLisener IdentifyCodeViewLisener) {
        this.IdentifyCodeViewLisener = IdentifyCodeViewLisener;
    }


    public void StartCountDown(int millisecond) {
        isGetVertifyCode = true;
        btn_yzm.setEnabled(false);
        btn_yzm.setTextColor(getResources().getColor(R.color.txt_white));
        countDownTimer = new CountDownTimer(millisecond, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                btn_yzm.setText(millisUntilFinished / 1000 + "s");

            }

            @Override
            public void onFinish() {
                btn_yzm.setEnabled(true);
                btn_yzm.setText("获取验证码");
                btn_yzm.setTextColor(getResources().getColor(R.color.txt_white));
            }

        };
        countDownTimer.start();
    }

    public void setEt_tel(EditText et_tel) {
        this.et_tel = et_tel;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        dialogUtils = new DialogUtils(activity);
    }

    public boolean isGetVertifyCode() {
        return isGetVertifyCode;
    }


    public interface IdentifyCodeViewLisener {
        void onYCIdentifyCodeViewClickLisener();
    }

    private IdentifyCodeViewLisener IdentifyCodeViewLisener;

    private Activity activity;

    public IdentifyCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initialView(Context context) {
        View view =View.inflate(context, R.layout.vertifycode_layout, this);
        btn_yzm = (Button) view.findViewById(R.id.btn_common);
        btn_yzm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (null != IdentifyCodeViewLisener) {
            if (et_tel != null) {
                String tel = et_tel.getText().toString().trim();
//                String tel = "12345678907";
                if (StrUtils.isEmpty(tel)) {
                    dialogUtils.simpleDialog( "手机号码不能为空", new DialogUtils.ConfirmClickLisener() {
                        @Override
                        public void onConfirmClick(View v) {
                            dialogUtils.dismissDialog();
                        }

                    }, true);
                } else if (!StrUtils.checkMobile(tel)) {
                    dialogUtils.simpleDialog( "请输入正确的手机号码", new DialogUtils.ConfirmClickLisener() {
                        @Override
                        public void onConfirmClick(View v) {
                            dialogUtils.dismissDialog();
                        }

                    }, true);
                } else {
                    IdentifyCodeViewLisener.onYCIdentifyCodeViewClickLisener();
                }
            } else {
                IdentifyCodeViewLisener.onYCIdentifyCodeViewClickLisener();
            }
        }

    }
}
