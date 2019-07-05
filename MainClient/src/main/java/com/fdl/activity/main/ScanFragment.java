package com.fdl.activity.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseFragment;
import com.fdl.utils.IsBang;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.activity.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/19<p>
 * <p>changeTime：2019/1/19<p>
 * <p>version：1<p>
 */
public class ScanFragment extends BaseFragment  implements QRCodeView.Delegate,EasyPermissions.PermissionCallbacks {
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
    @BindView(R.id.zxingview)
    ZXingView mZXingView;
    Unbinder unbinder;

    @Override
    public int initContentView() {
        return R.layout.activity_scan_layout;
    }

    @Override
    public void setUpViews(View view) {
        IsBang.setImmerHeard(getContext(),rlHead);
        heardTitle.setText("扫一扫");
        heardTvMenu.setVisibility(View.GONE);
        heardTvMenu.setTextColor(Color.WHITE);
        heardTvMenu.setText("相册");
        mZXingView.setDelegate(this);
        mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        mZXingView.setType(BarcodeType.ALL, null); // 识别所有类型的码
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.heard_back, R.id.heard_tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                MainActivity.instans.setTabSelection(0);
                break;
            case R.id.heard_tv_menu:
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mZXingView.startCamera();
        // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }


    @Override
    public void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mZXingView.onDestroy();
        super.onDestroy();
    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    //    @Override
//    protected void onStop() {
//        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
//        super.onStop();
//    }
    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
//        mZXingView.startSpot(); // 开始识别
        Intent intent = new Intent(getActivity(),WebViewActivity.class);
        intent.putExtra("url",result);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPerm();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @AfterPermissionGranted(110)

    private void checkPerm() {
        String[] params={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA};
        if(EasyPermissions.hasPermissions(getContext(),params)){
        }else{
            EasyPermissions.requestPermissions(this,"需要定位权限,和相机权限",110,params);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

}
