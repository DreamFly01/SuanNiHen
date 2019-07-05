package com.fdl.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ProductDetailsBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.IdentifyCodeView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/3<p>
 * <p>changeTime：2019/1/3<p>
 * <p>version：1<p>
 */
public class ChangePhoneActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_code)
    EditText tvCode;
    @BindView(R.id.identify_code_view)
    IdentifyCodeView identifyCodeView;
    @BindView(R.id.tv_commit)
    TextView tvCommit;


    private DialogUtils dialogUtils;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_changephone_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("修改手机号码");
        identifyCodeView.setActivity(this);
        identifyCodeView.setEt_tel(etPhone);
        identifyCodeView.setIdentifyCodeViewLisener(new IdentifyCodeView.IdentifyCodeViewLisener() {
            @Override
            public void onYCIdentifyCodeViewClickLisener() {
                addSubscription(RequestClient.GetPhoneCode(etPhone.getText().toString().trim(), "4", ChangePhoneActivity.this, new NetSubscriber<BaseResultBean>(ChangePhoneActivity.this,true) {
                    @Override
                    public void onResultNext(BaseResultBean model) {
                        identifyCodeView.StartCountDown(60000);
                        showShortToast("短信已发送,请注意查收!");
                    }
                }));
            }
        });
    }

    @Override
    public void setUpLisener() {

    }

    @OnClick({R.id.heard_back, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_commit:
                if(check()){
                    changePhone();
                }
                break;
        }
    }
    private void changePhone(){
        addSubscription(RequestClient.updateusertel(etPhone.getText().toString().trim(), tvCode.getText().toString().trim(), this, new NetSubscriber<BaseResultBean>(this,true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                PartyApp.getAppComponent().getDataManager().setTel(etPhone.getText().toString().trim());
                dialogUtils.simpleDialog("修改成功", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        dialogUtils.dismissDialog();
                        Intent intent = new Intent(ChangePhoneActivity.this,AccountSafeActivity.class);
                        setResult(2,intent);
                        ChangePhoneActivity.this.finish();
                    }
                },false);
            }
        }));
    }

    private boolean check(){
        if(StrUtils.isEmpty(etPhone.getText().toString().trim())){
            dialogUtils.noBtnDialog("请输入手机号码");
            return false;
        }
        if(!identifyCodeView.isGetVertifyCode()){
            dialogUtils.noBtnDialog("请获取验证码");
            return false;
        }
        if(StrUtils.isEmpty(tvCode.getText().toString().trim())){
            dialogUtils.noBtnDialog("请输入验证码");
            return false;
        }
        return true;
    }
}
