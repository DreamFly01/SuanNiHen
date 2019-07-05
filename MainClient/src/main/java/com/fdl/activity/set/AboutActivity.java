package com.fdl.activity.set;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
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
public class AboutActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.ll_03)
    LinearLayout ll03;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_layout);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);

        heardTitle.setText("关于我们");
        tvVersion.setText("算你狠 Android "+getLocalVersion(this));
    }

    @Override
    public void setUpLisener() {

    }


    private Bundle bundle;
    @OnClick({R.id.ll_01, R.id.ll_02, R.id.ll_03,R.id.heard_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_01:
                bundle = new Bundle();
                bundle.putInt("flag",1);
                JumpUtils.dataJump(this,ProtocolActivity.class,bundle,false);
                break;
            case R.id.ll_02:
                bundle = new Bundle();
                bundle.putInt("flag",2);
                JumpUtils.dataJump(this,ProtocolActivity.class,bundle,false);
                break;
            case R.id.ll_03:
                bundle = new Bundle();
                bundle.putInt("flag",3);
                JumpUtils.dataJump(this,ProtocolActivity.class,bundle,false);
                break;
        }
    }

    public static String getLocalVersion(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
