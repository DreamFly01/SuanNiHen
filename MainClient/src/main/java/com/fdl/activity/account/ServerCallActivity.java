package com.fdl.activity.account;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.supermarket.StoreListActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/18<p>
 * <p>changeTime：2019/1/18<p>
 * <p>version：1<p>
 */
public class ServerCallActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
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
    @BindView(R.id.tv_tell)
    TextView tvTell;
    @BindView(R.id.tv_email)
    TextView tvEmail;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_servercall_layout);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("联系客服");
    }

    @Override
    public void setUpLisener() {

    }

    private String tell;
    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetServerTel(this, new NetSubscriber<BaseResultBean>(this) {
            @Override
            public void onResultNext(BaseResultBean model) {
                tell = model.tel;
                tvEmail.setText(model.email);
                tvTell.setText(model.tel);
            }
        }));
    }

    @OnClick({R.id.heard_back, R.id.tv_tell})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_tell:
                if(StrUtils.isEmpty(tell)){
//                    dialogUtils.noBtnDialog("商家未留联系号码");
                }else {
                   checkPerm();
                }
                break;
        }
    }

    private void checkPerm() {
        String[] params = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, params)) {
            Intent myIntentDial=new Intent("android.intent.action.CALL",Uri.parse("tel:"+tell));
            startActivity(myIntentDial);
        } else {
            EasyPermissions.requestPermissions(this, "需要拨打电话权限", 100, params);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
