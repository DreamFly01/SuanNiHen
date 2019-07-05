package com.fdl.activity.set;

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
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.fdl.BaseActivity;
import com.fdl.adapter.AddImgAdapter;
import com.fdl.adapter.FootItemDelagateAdapter;
import com.fdl.adapter.MyMultiItemAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ImgDelagateBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
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
import com.sg.cj.snh.R;

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
public class FeedActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {


    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.tv_type)
    TextView type;
    @BindView(R.id.tv_img_count)
    TextView count;
    @BindView(R.id.et_comment)
    EditText comment;

    @BindView(R.id.ll_01)
    LinearLayout chose;
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
        setContentView(R.layout.activity_feed_layout);

        takePhoto = getTakePhoto();
        compressConfig = new CompressConfig.Builder().setMaxPixel(800).setMaxSize(200 * 1024).create();

        dialogUtils = new DialogUtils(this, this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("意见反馈");
        initReclyView();
    }

    @Override
    public void setUpLisener() {

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
            count.setText(paths.size() + "/3");
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
                count.setText(paths.size() + "/3");
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

    private String level;

    private void setWheelView() {
        List<String> options1Items = new ArrayList<>();
        options1Items.add("产品功能");
        options1Items.add("服务质量");
        options1Items.add("服务速度");
        options1Items.add("其他意见");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1);
                level = options1 + 1 + "";
                type.setText(tx);
            }
        })
                .setCancelText("取消")
                .setSubmitText("确定")
                .setTitleText("请选择")
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();

    }

    @OnClick({R.id.ll_01, R.id.heard_back, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_01:
                setWheelView();
                break;
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_exit:
                if (check()) {

                    upLoadImg();
                }
                break;
        }
    }

    private boolean check() {
        if (StrUtils.isEmpty(level)) {
            dialogUtils.noBtnDialog("请选择类型");
            return false;
        }
        if (StrUtils.isEmpty(comment.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入反馈意见");
            return false;
        }
        return true;
    }

    private void upLoadImg() {
        if(paths.size()>0){
            addSubscription(RequestClient.UpLoadFile(paths, this, new NetSubscriber<BaseResultBean>(this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    RequestClient.FeedSuggestion(level, model.filepath, comment.getText().toString().trim(), FeedActivity.this, new NetSubscriber<BaseResultBean>(FeedActivity.this,true) {
                        @Override
                        public void onResultNext(BaseResultBean model) {
                            dialogUtils.simpleDialog("提交成功", new DialogUtils.ConfirmClickLisener() {
                                @Override
                                public void onConfirmClick(View v) {
                                    FeedActivity.this.finish();
                                }
                            },false);
                        }
                    });
                }
            }));
        }else {
            RequestClient.FeedSuggestion(level, null, comment.getText().toString().trim(), FeedActivity.this, new NetSubscriber<BaseResultBean>(FeedActivity.this,true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    dialogUtils.simpleDialog("提交成功", new DialogUtils.ConfirmClickLisener() {
                        @Override
                        public void onConfirmClick(View v) {
                            FeedActivity.this.finish();
                        }
                    },false);
                }
            });
        }

    }
}
