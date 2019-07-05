package com.fdl.activity.account;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fdl.BaseActivity;
import com.fdl.bean.AddressBean;
import com.fdl.bean.AearBean;
import com.fdl.bean.AreasBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.db.DBManager;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.GetJsonDataUtil;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.google.gson.Gson;
import com.sg.cj.snh.R;
import com.snh.greendao.AearBeanDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>desc：地址编辑<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/29<p>
 * <p>changeTime：2018/12/29<p>
 * <p>version：1<p>
 */
public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_aer_address)
    TextView tvAerAddress;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    @BindView(R.id.iv_is_oppen)
    ImageView ivIsOppen;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private DialogUtils dialogUtils;
    private boolean isOpen = true;

    private String name, phone, aerAddress, address,isDefult;
    private AddressBean addressBean;
    private  Bundle bundle;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_address_layout);
        dialogUtils = new DialogUtils(this);
        bundle = this.getIntent().getExtras();
        if(null!=bundle){
            addressBean = bundle.getParcelable("address");
            addreddId = addressBean.AreaId;
        }
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        if(null!=addressBean){
            tvName.setText(addressBean.RealName);
            tvAddress.setText(addressBean.Address);
            tvAerAddress.setText(addressBean.AreaAddress);
            tvPhone.setText(addressBean.TelPhone);
            if(addressBean.IsDefault == 1){
                ivIsOppen.setBackgroundResource(R.drawable.btn_on);
                isOpen = true;
            }else {
                ivIsOppen.setBackgroundResource(R.drawable.btn_off);
                isOpen = false;
            }
        }
        heardTitle.setText("添加地址");
    }

    @Override
    public void setUpLisener() {

    }
    DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
    DaoSession daoSession = daoMaster.newSession();
    AearBeanDao aearBeanDao = daoSession.getAearBeanDao();
    private String addreddId;
    @OnClick({R.id.tv_aer_address,R.id.heard_back, R.id.iv_is_oppen, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_aer_address:
                if (isLoaded) {
//                    showPickerView();
                    dialogUtils.Address1Dialog(new DialogUtils.Address1Chose() {
                        @Override
                        public void onAddressChose(AreasBean bean) {
                            com.fdl.bean.daoBean.AearBean aearBean = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.Id.eq(bean.ParentID)).unique();
                            String city = aearBean.AddressName;
                            com.fdl.bean.daoBean.AearBean aearBean1 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.Id.eq(aearBean.ParentID)).unique();
                            String province = aearBean1.AddressName;
                            tvAerAddress.setText(province+" "+city+" "+bean.AddressName);
                            addreddId = bean.id;
                        }
                    });
                } else {

//                    Toast.makeText(JsonDataActivity.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.iv_is_oppen:
                if (isOpen) {
                    ivIsOppen.setBackgroundResource(R.drawable.btn_off);
                    isOpen = false;
                } else {
                    ivIsOppen.setBackgroundResource(R.drawable.btn_on);
                    isOpen = true;
                }
                break;
            case R.id.tv_commit:
                if(check()){
                    saveAddress();
                }
                break;
        }
    }

    private boolean check() {
        name = tvName.getText().toString().trim();
        phone = tvPhone.getText().toString().trim();
        aerAddress = tvAerAddress.getText().toString().trim();
        address = tvAddress.getText().toString().trim();
        if (StrUtils.isEmpty(name)) {
            dialogUtils.noBtnDialog("请填写姓名");
            return false;
        }
        if (StrUtils.isEmpty(phone)) {
            dialogUtils.noBtnDialog("请填写手机号");
            return false;
        }
        if (StrUtils.isEmpty(aerAddress)) {
            dialogUtils.noBtnDialog("请选择地区");
            return false;
        }
        if (StrUtils.isEmpty(address)) {
            dialogUtils.noBtnDialog("请填详细地址");
            return false;
        }
        return true;
    }

    //提交保存地址
    private void saveAddress() {
        if(isOpen){
            isDefult = 1+"";
        }else {
            isDefult = 0+"";
        }
        if(null!=addressBean){

            addSubscription(RequestClient.FixAddress(addressBean.Id+"",name, phone, addreddId, address.trim(), isDefult, this, new NetSubscriber<BaseResultBean>(this,true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                        dialogUtils.noBtnDialog("修改地址成功");
                        EditAddressActivity.this.finish();
                }
            }));
        }else {

        addSubscription(RequestClient.PostAddress(name, phone, addreddId, address.trim(), isDefult, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                EditAddressActivity.this.finish();
                showShortToast("添加地址成功");
            }
        }));
    }
    }

    private OptionsPickerView pvOptions;
    private void showPickerView() {//条件选择器初始化

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +" "+
                        options2Items.get(options1).get(options2) +" "+
                        options3Items.get(options1).get(options2).get(options3);
                // TODO: 2019/2/13 地区id
                int id = jsonBean.get(options1).Cities.get(options2).Areas.get(options3).Id;
                tvAerAddress.setText(tx);
//                Toast.makeText(JsonDataActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
    ArrayList<AearBean> jsonBean;
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province1.json");//获取assets目录下的json文件数据

        jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).Cities.size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).Cities.get(c).Name;
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).Cities.get(c).Areas == null
                        || jsonBean.get(i).Cities.get(c).Areas.size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int j = 0; j < jsonBean.get(i).Cities.get(c).Areas.size(); j++) {

                    City_AreaList.add(jsonBean.get(i).Cities.get(c).Areas.get(j).Name);
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<AearBean> parseData(String result) {//Gson 解析
        ArrayList<AearBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AearBean entity = gson.fromJson(data.optJSONObject(i).toString(), AearBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

//                        Toast.makeText(JsonDataActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(JsonDataActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
//                    Toast.makeText(JsonDataActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private ArrayList<AearBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;
}
