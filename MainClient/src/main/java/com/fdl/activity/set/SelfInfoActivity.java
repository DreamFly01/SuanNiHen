package com.fdl.activity.set;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
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
import java.util.ArrayList;
import java.util.List;

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
public class SelfInfoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.iv_heard)
    ImageView ivHeard;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.ll_03)
    LinearLayout ll03;
    @BindView(R.id.tv_commit)
    TextView commit;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;
    private DialogUtils dialogUtils;
    private String path;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_selfinfo_activity);
        takePhoto = getTakePhoto();
        compressConfig = new CompressConfig.Builder().setMaxPixel(800).setMaxSize(2 * 1024*1024).create();
        dialogUtils = new DialogUtils(this, this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("我的信息");
        tvGrade.setText("v" + PartyApp.getAppComponent().getDataManager().getGradeId());
        tvName.setText(PartyApp.getAppComponent().getDataManager().getWxNickName());
        StringBuffer sbf = new StringBuffer(PartyApp.getAppComponent().getDataManager().getWxHeadImg());

        ImageUtils.loadUrlCircleImage(this, sbf.replace(0,1,"h").toString(), ivHeard);
    }

    @Override
    public void setUpLisener() {

    }

    @OnClick({R.id.heard_back, R.id.ll_01, R.id.ll_02, R.id.ll_03,R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_01:
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
                break;
            case R.id.ll_02:
                break;
            case R.id.ll_03:
                break;
            case R.id.tv_commit:
                upLoadImg(pathList);
                break;
        }
    }

    List<String> pathList = new ArrayList<>();

    @Override
    public void takeSuccess(TResult result) {
        path = result.getImage().getCompressPath();
        ImageUtils.loadUrlCircleImage(this, result.getImage().getOriginalPath(), ivHeard);
        dialogUtils.dismissDialog();
        File file = new File(result.getImage().getOriginalPath());
        if (file.length() > 2 * 1024 *1024) {
            pathList.add(result.getImage().getCompressPath());
        } else {
            pathList.add(result.getImage().getOriginalPath());
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);

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

    private void upLoadImg(List<String> datas) {
        addSubscription(RequestClient.UpLoadFile(datas, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                addSubscription(RequestClient.FixHeardImg(model.filepath, SelfInfoActivity.this, new NetSubscriber<BaseResultBean>(SelfInfoActivity.this, false) {
                    @Override
                    public void onResultNext(BaseResultBean baseResultBean) {
                        showShortToast("头像修改成功！");
                        PartyApp.getAppComponent().getDataManager().setWxHeadImg(model.filepath);
                    }

                }));
            }
        }));

    }

}
