package com.fdl.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/2<p>
 * <p>changeTime：2019/1/2<p>
 * <p>version：1<p>
 */
public class AccountSafeActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.ll_03)
    LinearLayout ll03;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_accoutsafe_layout);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("账号安全");
        if (!StrUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getTel())) {
            StringBuffer sbf = new StringBuffer(PartyApp.getAppComponent().getDataManager().getTel());
            tvPhone.setText(sbf.replace(3, 7, "****").toString());
        }else {
            tvPhone.setText("");
        }
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.heard_back, R.id.ll_01, R.id.ll_02, R.id.ll_03})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_01:
                Intent intent = new Intent(this, ChangePhoneActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.ll_02:
                jumpActivity(ChangePswActivity.class);
                break;
            case R.id.ll_03:
                jumpActivity(SetPayPswActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            StringBuffer sbf = new StringBuffer(PartyApp.getAppComponent().getDataManager().getTel());

            tvPhone.setText(sbf.replace(3, 7, "****").toString());
        }
    }
}
