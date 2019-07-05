package com.fdl.activity.order;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.set.FeedActivity;
import com.fdl.adapter.AddImgAdapter;
import com.fdl.adapter.FootItemDelagateAdapter;
import com.fdl.adapter.MyMultiItemAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ImgDelagateBean;
import com.fdl.bean.WoolBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.StarBarView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/9<p>
 * <p>changeTime：2019/1/9<p>
 * <p>version：1<p>   待评价
 */
public class DiscussActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_img_count)
    TextView tvImgCount;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.start_view)
    StarBarView startView;


    private Bundle bundle;
    private String name, url, orderNo, goodsId;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;

    private FootItemDelagateAdapter.OnMyAdapterClick onMyClick;
    private AddImgAdapter.DetelOnClick detelOnClick;
    private DialogUtils.OnItemClick onItemClick;

    private FootItemDelagateAdapter footAdapter;
    private AddImgAdapter addAdapter;
    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_discuss_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            name = bundle.getString("name");
            url = bundle.getString("url");
            orderNo = bundle.getString("orderNo");
            goodsId = bundle.getString("goodsId");
        }
        takePhoto = getTakePhoto();
        compressConfig = new CompressConfig.Builder().setMaxPixel(200).setMaxSize(200 * 1024).create();

        dialogUtils = new DialogUtils(this, this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("发表评价");
        tvName.setText(name);
        ImageUtils.loadUrlImage(this, url, ivLogo);
        initReclyView();
    }

    @Override
    public void setUpLisener() {

    }


    @OnClick({R.id.heard_back, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_exit:
                if(check()){
                    uploadFile();
                }
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        if (paths.size() < 3) {
            ImgDelagateBean imgDelagateBean = new ImgDelagateBean();
            imgDelagateBean.isItem = true;
            imgDelagateBean.isDelet = false;
            imgDelagateBean.url = result.getImage().getOriginalPath();

            System.out.println("url:" + imgDelagateBean.url + " /ncomprocessUrl:" + result.getImage().getCompressPath());
//        File file = new File(result.getImage().getCompressPath());
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//            long size = fis.available();
//            System.out.println("size:" + size);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

            data.add(0, imgDelagateBean);
            dataCopy.add(imgDelagateBean);
            addAdapter.setData(data, dataCopy);
            adapter.notifyDataSetChanged();

//        Log.d(HttpLogger.LOGKYE,"size:"+dataCopy.size()+"");
//        for (int i = 0; i < data.size(); i++) {
//            Log.d(HttpLogger.LOGKYE,i+data.get(i).url);
//        }
            paths.add(result.getImage().getCompressPath());
            tvImgCount.setText(paths.size() + "/3");
        } else {
            dialogUtils.noBtnDialog("最多添加三张");
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {

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

    private List<ImgDelagateBean> data = new ArrayList<>();
    private List<ImgDelagateBean> dataCopy = new ArrayList<>();
    private List<String> paths = new ArrayList<>();
    MyMultiItemAdapter adapter;

    private void initReclyView() {
        //初始化添加材料+按钮
        ImgDelagateBean imgDelagateBean = new ImgDelagateBean();
        imgDelagateBean.isItem = false;
        imgDelagateBean.isDelet = false;
        data.add(imgDelagateBean);
        //图片材料添加
        adapter = new MyMultiItemAdapter(this, data);
        onMyClick = new FootItemDelagateAdapter.OnMyAdapterClick() {
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
            public void onAlumbClick(View v) {
                takePhoto.onEnableCompress(compressConfig, true);
                takePhoto.onPickFromGallery();
            }
        };
        detelOnClick = new AddImgAdapter.DetelOnClick() {
            @Override
            public void detelClick(View v, int position) {

                data.remove(0);
                dataCopy.remove(position);
                adapter.upData(data);
                paths.remove(position);
                tvImgCount.setText(paths.size() + "/3");
            }
        };

        footAdapter = new FootItemDelagateAdapter(this, this, onMyClick);
        addAdapter = new AddImgAdapter(this, this, detelOnClick);
        addAdapter.setData(data, null);
        adapter.addItemViewDelegate(footAdapter);
        adapter.addItemViewDelegate(addAdapter);
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }

    private void uploadFile() {
        addSubscription(RequestClient.UpLoadFile(paths, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                RequestClient.PostComment(orderNo, goodsId, startView.getStarRating() + "", model.filepath, etComment.getText().toString().trim(), DiscussActivity.this, new NetSubscriber<BaseResultBean>(DiscussActivity.this, false) {
                    @Override
                    public void onResultNext(BaseResultBean model) {
                            dialogUtils.simpleDialog("评论成功", new DialogUtils.ConfirmClickLisener() {
                                @Override
                                public void onConfirmClick(View v) {
                                    dialogUtils.dismissDialog();
                                    DiscussActivity.this.finish();
                                }
                            },false);
                    }
                });
            }
        }));
    }

    private boolean check() {
        if (StrUtils.isEmpty(etComment.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入评价");
            return false;
        }
        if (startView.getStarRating() == 0) {
            dialogUtils.noBtnDialog("请选择星级");
            return false;
        }
//        if(paths.size()<=0){
//            dialogUtils.noBtnDialog("请选择照片");
//            return false;
//        }
        return true;
    }
}
