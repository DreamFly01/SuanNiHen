package com.fdl.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.bean.AgreementBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.WechatBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.IdentifyCodeView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.activity.login.LoginActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/26<p>
 * <p>changeTime：2019/1/26<p>
 * <p>version：1<p>
 */
public class WechatBindActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.ic_getCode)
    IdentifyCodeView icGetCode;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    private DialogUtils dialogUtils;
    private String opendId;
    private int typeCode;
    private Bundle bundle;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wechatbind_activity);
        dialogUtils = new DialogUtils(this);
        opendId = PartyApp.getAppComponent().getDataManager().getUnionID();
        bundle = getIntent().getExtras();
        if(null!=bundle){
            typeCode = bundle.getInt("typeCode");
        }
//        opendId = "11111111";
    }

    @Override
    public void setUpViews() {
        icGetCode.setActivity(this);
        icGetCode.setEt_tel(etPhone);
        heardTitle.setText("绑定微信");
    }

    @Override
    public void setUpLisener() {
        icGetCode.setIdentifyCodeViewLisener(new IdentifyCodeView.IdentifyCodeViewLisener() {
            @Override
            public void onYCIdentifyCodeViewClickLisener() {
                getCode();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private boolean check() {
        if (StrUtils.isEmpty(etPhone.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入手机号码");
            return false;
        }
        if (!icGetCode.isGetVertifyCode()) {
            dialogUtils.noBtnDialog("请获取验证码");
            return false;
        }
        return true;
    }

    @OnClick({R.id.heard_back, R.id.btn_commit, R.id.tv_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                jumpActivity(LoginActivity.class);
                break;
            case R.id.btn_commit:
                if (check()) {
                    binding();
                }
                break;
            case R.id.tv_regist:
                jumpActivity(RegisterActivity.class);
                break;
        }
    }

    private void getCode() {
        addSubscription(RequestClient.GetPhoneCode(etPhone.getText().toString().trim(), "3", this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                icGetCode.StartCountDown(60000);
            }
        }));
    }

    private void binding() {
        addSubscription(RequestClient.wxbind(opendId, etPhone.getText().toString().trim(), etCode.getText().toString().trim(), typeCode,this, new NetSubscriber<BaseResultBean<WechatBean>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<WechatBean> model) {
                showShortToast("绑定登陆成功");
                PartyApp.getAppComponent().getDataManager().setId(model.data.Id);
                PartyApp.getAppComponent().getDataManager().setGradeId(model.data.GradeId);
                PartyApp.getAppComponent().getDataManager().setBalanceOne((float) model.data.BalanceOne);
                PartyApp.getAppComponent().getDataManager().setIntegral((float) model.data.Integral);
                PartyApp.getAppComponent().getDataManager().setPayment(model.data.Payment);
                PartyApp.getAppComponent().getDataManager().setTel(model.data.Tel);
                PartyApp.getAppComponent().getDataManager().setUnionID(model.data.WxOpenId);
                PartyApp.getAppComponent().getDataManager().setWxHeadImg(model.data.WxHeadImg);
                PartyApp.getAppComponent().getDataManager().setWxNickName(model.data.WxNickName);
                PartyApp.getAppComponent().getDataManager().setWxOpenId(model.data.WxOpenId);
                jumpActivity(MainActivity.class);
            }
        }));
    }
}
