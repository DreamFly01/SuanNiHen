package com.fdl.activity.merchantEntry;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.main.MainActivity;
import com.fdl.utils.IsBang;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/22<p>
 * <p>changeTime：2019/1/22<p>
 * <p>version：1<p>
 */
public class CompleteActivity extends BaseActivity {

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
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_psw)
    TextView tvPsw;

    private String phone;
    private String psw;
    private Bundle bundle;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_complete_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            phone = bundle.getString("phone");
            psw = bundle.getString("psw");
        }
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        heardTitle.setText("");
        tvPhone.setText("账户："+phone);
        tvPsw.setText("密码："+psw);
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

    @OnClick(R.id.heard_back)
    public void onClick() {
        jumpActivity(MainActivity.class);
    }

    @Override
    public void onBackPressed() {
        jumpActivity(MainActivity.class);
    }
}
