package com.fdl.activity.merchantEntry;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.MapDataBean;
import com.fdl.bean.StoreClassficationBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.Md5Utils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import java.io.Serializable;
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
 * <p>creatTime：2019/1/22<p>
 * <p>changeTime：2019/1/22<p>
 * <p>version：1<p>
 */
public class PerfectPersonTwoActivity extends BaseActivity {
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
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_psw1)
    EditText etPsw1;
    @BindView(R.id.et_psw2)
    EditText etPsw2;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private int flag;
    private int flag1;
    private Bundle bundle;
    private Map<String, Object> personMap;
    private MapDataBean bean;
    private DialogUtils dialogUtils;
    private String ShopCategoryType;
    private String phone;
    private String shopType;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_perfectperson2_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            flag = bundle.getInt("flag", -1);
            flag1 = bundle.getInt("flag1", -1);
            ShopCategoryType = bundle.getString("ShopCategoryType");
            personMap = (Map<String, Object>) bundle.getSerializable("data");
            phone = bundle.getString("phone");
            shopType = bundle.getString("shopType");
        }
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("填写店铺信息");
        IsBang.setImmerHeard(this, rlHead);
        tvPhone.setText((String) personMap.get("PhoneNumber"));
        getShopType();
        if (Contans.debug) {
            etName.setText("ssss");
            etPsw1.setText("111111q");
            etPsw2.setText("111111q");
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

    @OnClick({R.id.heard_back, R.id.tv_type, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_type:
                showPickView();
                break;
            case R.id.tv_commit:
                if (check()) {
                    bundle = new Bundle();
                    if (!shopType.equals("1")) {

                        dialogUtils.twoBtnDialog("是否是海淘店", new DialogUtils.ChoseClickLisener() {
                            @Override
                            public void onConfirmClick(View v) {
                                IsOverseas = "1";
                                if (shopType.equals("3")) {
                                    bundle.putString("money", typeData.get(position).PHaiTao);
                                } else {
                                    bundle.putString("money", typeData.get(position).EHaiTao);
                                }
                                setData();
                                checkJump();
                                dialogUtils.dismissDialog();
                            }

                            @Override
                            public void onCancelClick(View v) {
                                IsOverseas = "0";
                                if (shopType.equals("3")) {
                                    bundle.putString("money", typeData.get(position).PNoHaiTao);
                                } else {
                                    bundle.putString("money", typeData.get(position).ENoHaiTao);
                                }
                                setData();
                                checkJump();
                                dialogUtils.dismissDialog();
                            }
                        }, false);
                    } else {
                        setData();
                        checkJump();
                    }

                }

                break;
        }
    }

    private void checkJump() {
        if (flag == 1) {
            bundle.putSerializable("data", (Serializable) personMap);
            bundle.putInt("flag", 1);
            bundle.putString("psw", etPsw1.getText().toString().trim());
            bundle.putString("name", tvType.getText().toString().trim());
            bundle.putString("shopType", shopType);
            JumpUtils.dataJump(this, PerfectPersonThreeActivity.class, bundle, false);
        }
        if (flag == 2) {
            bundle.putString("ShopCategoryType", ShopCategoryType);
            bundle.putSerializable("data", (Serializable) personMap);
            bundle.putInt("flag", flag1);
            bundle.putString("psw", etPsw1.getText().toString().trim());
            bundle.putString("name", tvType.getText().toString().trim());
            bundle.putString("shopType", shopType);
            JumpUtils.dataJump(this, PerfectCompanyThreeActivity.class, bundle, false);
        }
    }

    private void setData() {

        personMap.put("ShopName", etName.getText().toString().trim());

        personMap.put("ShopTypeEnum", ShopTypeId);
        personMap.put("Pwd", Md5Utils.md5(etPsw1.getText().toString().trim()));
        if (!shopType.equals("1")) {
            personMap.put("IsOverseas", IsOverseas);
        }
//        bean.setMap(personMap);
    }

    private List<String> options1Items = new ArrayList<>();
    private String ShopTypeId;
    private String IsOverseas;
    private int position;

    private void showPickView() {
        options1Items.clear();
        for (StoreClassficationBean bean : typeData) {
            options1Items.add(bean.Name);
        }

        OptionsPickerView pvOptions = new OptionsPickerBuilder(PerfectPersonTwoActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1);
                ShopTypeId = typeData.get(options1).Id + "";
                position = options1;
                tvType.setText(tx);
            }
        }).build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }

    private boolean check() {
        if (StrUtils.isEmpty(etName.getText().toString().trim())) {
            dialogUtils.noBtnDialog("店铺名不能为空");
            return false;
        }
        if (tvType.getText().toString().trim().equals("请选择类目")) {
            dialogUtils.noBtnDialog("请选择店铺类型");
            return false;
        }
        if (StrUtils.isEmpty(etPsw1.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入密码");
            return false;
        }
        if (!etPsw1.getText().toString().trim().equals(etPsw2.getText().toString().trim())) {
            dialogUtils.noBtnDialog("两次密码输入不一致");
            return false;
        }
        if (!StrUtils.isPsw(etPsw1.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请设置6-20位数字字母组合密码");
            return false;
        }
        return true;
    }

    List<StoreClassficationBean> typeData;

    private String type;

    private void getShopType() {
        if (shopType.equals("3")) {
            type = "2";
        } else {
            type = shopType;
        }
        addSubscription(RequestClient.GetShopType(type, this, new NetSubscriber<BaseResultBean<List<StoreClassficationBean>>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<StoreClassficationBean>> model) {
                typeData = model.data;
            }
        }));
    }
}
