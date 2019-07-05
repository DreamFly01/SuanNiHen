package com.fdl.activity.main;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.utils.IsBang;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.common.activity.UI;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/3/26<p>
 * <p>changeTime：2019/3/26<p>
 * <p>version：1<p>
 */
public class MsgActivity extends BaseActivity {
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

    private RecentContactsFragment fragment;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_msg_layout);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("消息");

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
        this.finish();
    }


}
