package com.fdl.activity.set;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.Md5Utils;
import com.fdl.utils.StrUtils;
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
public class ChangePswActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_old)
    EditText etOld;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.et_new1)
    EditText etNew1;

    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_changepsw_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("修改密码");
    }

    @Override
    public void setUpLisener() {
        IsBang.setImmerHeard(this,rlHead);
    }


    @OnClick({R.id.heard_back, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_commit:
                if(check()){
                    changePsw();
                }
                break;
        }
    }

    private void changePsw() {
        String oldPsw = Md5Utils.md5(etOld.getText().toString().trim());
        String newPsw = Md5Utils.md5(etNew.getText().toString().trim());
        addSubscription(RequestClient.FixPsw(oldPsw, newPsw, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                dialogUtils.simpleDialog("密码修改成功", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        dialogUtils.dismissDialog();
                        ChangePswActivity.this.finish();
                    }
                }, false);
            }
        }));
    }

    private boolean check() {
        if (StrUtils.isEmpty(etNew.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入原密码");
            return false;
        }
        if(StrUtils.isEmpty(etNew.getText().toString().trim())){
            dialogUtils.noBtnDialog("请输入新密码");
            return false;
        }
        if(StrUtils.isEmpty(etNew1.getText().toString().trim())){
            dialogUtils.noBtnDialog("请再次输入新密码");
            return false;
        }
        if (!etNew.getText().toString().trim().equals(etNew1.getText().toString().trim())) {
            dialogUtils.noBtnDialog("两次输入新密码不一致");
            return  false;
        }
        return true;
    }

}
