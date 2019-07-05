package com.fdl.activity.merchantEntry;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.fdl.BaseActivity;
import com.fdl.activity.set.SelfInfoActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ImgDelagateBean;
import com.fdl.bean.MapDataBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
public class PerfectPersonActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
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
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_sfz_num)
    EditText etSfzNum;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.iv_chose1)
    ImageView ivChose1;
    @BindView(R.id.iv_chose2)
    ImageView ivChose2;
    @BindView(R.id.iv_chose3)
    ImageView ivChose3;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private DialogUtils dialogUtils;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;
    private int type = 0;
    private Map<String, Object> dataMap = new HashMap<>();
    private String phone;
    private Bundle bundle;
    private List<Map<Object, Object>> mapList = new ArrayList<>();
    private Map<Object, Object> allMap = new HashMap<>();
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_perfectperson_layout);
        dialogUtils = new DialogUtils(this);
        takePhoto = getTakePhoto();
        compressConfig = new CompressConfig.Builder().setMaxPixel(800).setMaxSize(200 * 1024).create();

        dialogUtils = new DialogUtils(this, this);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            phone = bundle.getString("phone");
        }
    }


    @Override
    public void setUpViews() {
        heardTitle.setText("填写个人资料");
        IsBang.setImmerHeard(this, rlHead);
        tvPhone.setText(phone);
        if(Contans.debug){
            etName.setText("ssss");
            etEmail.setText("290206959@qq.com");
            etSfzNum.setText("430602199108196030");
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

    @OnClick({R.id.heard_back, R.id.tv_limit, R.id.iv_chose1, R.id.iv_chose2, R.id.iv_chose3, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_limit:
                showPickerView();
                break;
            case R.id.iv_chose1:
                type = 1;
                showPhotoPick();
                break;
            case R.id.iv_chose2:
                type = 2;
                showPhotoPick();
                break;
            case R.id.iv_chose3:
                type = 3;
                showPhotoPick();
                break;
            case R.id.tv_commit:
                setDatas();
                if (check()) {
//                    pathList1 = new ArrayList<pathMap>();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", (Serializable) dataMap);
                    bundle.putInt("flag", 1);
                    bundle.putString("phone",phone);
                    bundle.putString("shopType","3");
                    JumpUtils.dataJump(this, PerfectPersonTwoActivity.class, bundle, false);
                }
                break;
        }
    }

    private void setDatas() {
        mapList.clear();
        dataMap.put("RealName", etName.getText().toString().trim());
        dataMap.put("PhoneNumber", phone);
        dataMap.put("CardNo", etSfzNum.getText().toString().trim());
        dataMap.put("CardEndTime", tvLimit.getText().toString().trim());
        dataMap.put("Email", etEmail.getText().toString().trim());
        for (int i = 0; i < allMap.size(); i++) {
            mapList.add((Map<Object, Object>) allMap.get(i+1));
        }
        dataMap.put("ImgUrlList", mapList);
    }

    private boolean check() {
        if (StrUtils.isEmpty(etName.getText().toString())) {
            dialogUtils.noBtnDialog("请填写真实姓名");
            return false;
        }
        if (StrUtils.isEmpty(etEmail.getText().toString())) {
            dialogUtils.noBtnDialog("请填写邮箱");
            return false;
        }
        if (StrUtils.isEmpty(etSfzNum.getText().toString())) {
            dialogUtils.noBtnDialog("请输入身份证号");
            return false;
        }
        if (tvLimit.getText().toString().equals("请选择身份证国徽面的期限")) {
            dialogUtils.noBtnDialog("请选择有效期");
            return false;
        }
        if (!StrUtils.isSfz(etSfzNum.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入正确的身份证号码");
            return false;
        }
        if (!StrUtils.isEmail(etEmail.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入正确的邮箱");
            return false;
        }
        if (mapList.size() != 3) {
            dialogUtils.noBtnDialog("请完善身份证照片信息");
            return false;
        }
        return true;
    }


    private void showPickerView() {//条件选择器初始化

        TimePickerView pvTime = new TimePickerBuilder(PerfectPersonActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                tvLimit.setText(sdf.format(date));
            }
        }).build();
        pvTime.show();
    }


    List<String> pathList = new ArrayList<>();

    @Override
    public void takeSuccess(TResult result) {
        pathList.clear();
        ImgDelagateBean imgDelagateBean = new ImgDelagateBean();
        imgDelagateBean.isItem = true;
        imgDelagateBean.isDelet = false;
        imgDelagateBean.url = result.getImage().getOriginalPath();

        File file = new File(result.getImage().getCompressPath());
        if (file.length() > 200 * 1024 ) {
            pathList.add(result.getImage().getCompressPath());
        } else {
            pathList.add(result.getImage().getOriginalPath());
        }
        switch (type) {
            case 1:
                ImageUtils.loadUrlImage(PerfectPersonActivity.this, pathList.get(0), ivChose1);
                break;
            case 2:
                ImageUtils.loadUrlImage(PerfectPersonActivity.this, pathList.get(0), ivChose2);
                break;
            case 3:
                ImageUtils.loadUrlImage(PerfectPersonActivity.this, pathList.get(0), ivChose3);
                break;
        }
        upLoadImg(pathList);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showShortToast("选取失败");
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    private void showPhotoPick() {
        dialogUtils.headImgDialog(new DialogUtils.HeadImgChoseLisener() {
            @Override
            public void onCancelClick(View v) {
                dialogUtils.dismissDialog();
            }

            @Override
            public void onPhotoClick(View v) {
                String name = "takePhoto" + System.currentTimeMillis();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.TITLE, name);
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpeg");
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                takePhoto.onEnableCompress(compressConfig, true);
                takePhoto.onPickFromCapture(uri);
            }

            @Override
            public void onAlumClick(View v) {
                takePhoto.onEnableCompress(compressConfig, true);
                takePhoto.onPickFromGallery();
            }
        }, true);
    }

    private Map<Object, Object> pathMap1 = new TreeMap<>();
    private Map<Object, Object> pathMap2 = new TreeMap<>();
    private Map<Object, Object> pathMap3 = new TreeMap<>();

    private void upLoadImg(List<String> datas) {
        dialogUtils.dismissDialog();

        addSubscription(RequestClient.UpLoadFile(datas, this, new NetSubscriber<BaseResultBean>(this,true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                StringBuffer str = new StringBuffer(model.filepath);

                String url = str.replace(0, 1, "h").toString().trim();
                switch (type) {
                    case 1:
                        pathMap1.put("ImgType", 1);
                        pathMap1.put("ImgSaveUrl", model.filepath);
                        allMap.put(1,pathMap1);
                        break;
                    case 2:
                        pathMap2.put("ImgType", 1);
                        pathMap2.put("ImgSaveUrl", model.filepath);
                        allMap.put(2,pathMap2);
                        break;
                    case 3:
                        pathMap3.put("ImgType", 1);
                        pathMap3.put("ImgSaveUrl", model.filepath);
                        allMap.put(3,pathMap3);
                        break;
                }
            }
        }));

    }


}
