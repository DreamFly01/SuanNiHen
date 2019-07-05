package com.fdl.activity.account.money;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fdl.BaseActivity;
import com.fdl.bean.BanksBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/3<p>
 * <p>changeTime：2019/4/3<p>
 * <p>version：1<p>
 */
public class AddBankActivity extends BaseActivity {
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
    @BindView(R.id.tv_chose)
    TextView tvChose;
    @BindView(R.id.et_01)
    EditText et01;
    @BindView(R.id.et_02)
    EditText et02;
    @BindView(R.id.et_03)
    EditText et03;
    @BindView(R.id.et_04)
    EditText et04;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.iv_chose)
    ImageView ivChose;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;

    private DialogUtils dialogUtils;
    private List<BanksBean> banks = new ArrayList<>();

    private boolean isFrist = true;
    private Map<String, Object> map = new TreeMap<>();
    private int type;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_addbank_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("添加银行卡");
        IsBang.setImmerHeard(this,rlHead);
        getBankType();
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

    @OnClick({R.id.heard_back, R.id.tv_chose, R.id.btn_commit, R.id.iv_chose, R.id.tv_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_chose:
                if (banks.size() > 0) {
                    showDialog(banks);
                } else {
                    isFrist = false;
                    getBankType();
                }
                break;
            case R.id.btn_commit:
                if (check()) {
                    addBank();
                }
                break;
            case R.id.iv_chose:
                break;
            case R.id.tv_protocol:
                break;
        }
    }

    private void getBankType() {
        addSubscription(RequestClient.GetBanks("", this, new NetSubscriber<BaseResultBean<List<BanksBean>>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<BanksBean>> model) {
                if (!isFrist) {
                    showDialog(model.data);
                } else {
                    banks = model.data;
                }
            }
        }));
    }

    private void showDialog(final List<BanksBean> banks) {
        dialogUtils.bankDiaolog(new DialogUtils.ConfirmBankLisener() {
            @Override
            public void onConfirmClick(BanksBean bean,int position) {
                for (int i = 0; i < banks.size(); i++) {
                    if(i==position){
                        banks.get(i).isChose = true;
                    }else {
                        banks.get(i).isChose = false;
                    }
                }
                tvChose.setText(bean.BankName);
                   type = bean.BankId;
                   dialogUtils.dismissDialog();
            }
        }, banks,true);
    }

    private boolean check() {
        if ("请选择银行".equals(tvChose.getText().toString())) {
            dialogUtils.noBtnDialog("请选择银行");
            return false;

        }
        if (StrUtils.isEmpty(et01.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入开户行");
            return false;
        }
        if (StrUtils.isEmpty(et02.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入银行账号");
            return false;
        }
        if (StrUtils.isEmpty(et03.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入开户名");
            return false;
        }
        if (StrUtils.isEmpty(et04.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入手机号码");
            return false;
        }
        if(!StrUtils.isEmpty(et04.getText().toString().trim())&&!StrUtils.checkMobile(et04.getText().toString().trim())){
            dialogUtils.noBtnDialog("请输入正确的手机号码");
            return false;
        }
        return true;
    }

    private void setData() {
        map.put("BankType", type);
        map.put("BankName", et01.getText().toString().trim());
        map.put("BankCardNo", et02.getText().toString().trim());
        map.put("BankCardUserName", et03.getText().toString().trim());
        map.put("BankTel", et04.getText().toString().trim());
    }

    private void addBank() {
        setData();
        addSubscription(RequestClient.AddSupplierBankCard(map, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                showShortToast("添加成功");
                finish();
            }
        }));
    }
}
