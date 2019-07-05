package com.fdl.activity.merchantEntry;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.fdl.BaseActivity;
import com.fdl.adapter.AddImgAdapter;
import com.fdl.adapter.FootItemDelagateAdapter;
import com.fdl.adapter.MyMultiItemAdapter;
import com.fdl.bean.AreasBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ImgDelagateBean;
import com.fdl.bean.JsonBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.db.DBManager;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.GetJsonDataUtil;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.google.gson.Gson;
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
import com.snh.greendao.AearBeanDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;

import org.json.JSONArray;

import java.io.File;
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
 * <p>creatTime：2019/1/23<p>
 * <p>changeTime：2019/1/23<p>
 * <p>version：1<p>
 */
public class PerfectCompanyThreeActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {


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
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.tv_jd)
    TextView tvJd;
    @BindView(R.id.tv_wd)
    TextView tvWd;
    @BindView(R.id.et_18code)
    EditText et18code;
    @BindView(R.id.iv_chose1)
    ImageView ivChose1;
    @BindView(R.id.iv_chose2)
    ImageView ivChose2;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LocationClient mLocationListener;
    private double Latitude;
    private double Longitude;
    private BaiduMap baiduMap;

    private LocationClient mLocationClient;

    private Bundle bundle;
    private Map<String, Object> dataMap;

    private DialogUtils dialogUtils;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;
    private int type = 0;
    private String phone;
    private List<Map<Object, Object>> mapList = new ArrayList<>();
    private List<String> pathList = new ArrayList<>();
    private Map<Object, Object> pathMap1 = new TreeMap<>();
    private Map<Object, Object> pathMap2 = new TreeMap<>();
    private Map<Object, Object> pathMap3 = new TreeMap<>();
    private Map<Object, Object> allMap = new TreeMap<>();
    private String ShopCategoryType;
    private FootItemDelagateAdapter.OnMyAdapterClick onMyClick;
    private AddImgAdapter.DetelOnClick detelOnClick;

    private FootItemDelagateAdapter footAdapter;
    private AddImgAdapter addAdapter;
    private int flag;
    private String psw;
    private String shopType;
    private String money;
    private String name;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_perfectcompany3_layout);
        bundle = getIntent().getExtras();
        dialogUtils = new DialogUtils(this);
        takePhoto = getTakePhoto();
        compressConfig = new CompressConfig.Builder().setMaxPixel(800).setMaxSize(200 * 1024).create();
        if (null != bundle) {
            ShopCategoryType = bundle.getString("ShopCategoryType");
            flag = bundle.getInt("flag");
            dataMap = (Map<String, Object>) bundle.getSerializable("data");
            psw = bundle.getString("psw");
            phone = bundle.getString("phone");
            shopType = bundle.getString("shopType");
            money = bundle.getString("money");
            name = bundle.getString("name");
        }
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("入驻企业信息完善");
        checkPermission();
        setMap();
        initReclyView();
        mapList1 = (List<Map<Object, Object>>) dataMap.get("ImgUrlList");
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

    private void setMap() {

        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            showLongToast("必须同意才能使用");
                            finish();
                            return;
                        }
                    }
                    getLoction();
                } else {
                    showLongToast("发生未知错误");
                    finish();
                }
                break;
        }
    }


    @OnClick({R.id.heard_back, R.id.tv_address, R.id.iv_chose1, R.id.iv_chose2, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_address:
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
            case R.id.tv_commit:
                if (dataCopy.size() > 0) {
                    for (int i = 0; i < dataCopy.size(); i++) {
                        paths.add(dataCopy.get(i).url);
                    }
                    type = 3;
                    upLoadImg(paths);
                } else {
                    setData();
                    if (check()) {
                        bundle = new Bundle();
                        bundle.putSerializable("data", (Serializable) dataMap);
                        bundle.putInt("flag", 2);
                        bundle.putString("psw", psw);
                        bundle.putString("shopType",shopType);
                        bundle.putString("name",name);
                        bundle.putString("money",money);
                        if (flag == 3) {
                            commitData();
                        } else {
                            JumpUtils.dataJump(this, PerfectPersonThreeActivity.class, bundle, false);
                        }
                    }
                }

                break;
        }
    }
    private List<Map<Object, Object>> mapList1 = new ArrayList<>();
    private void setData() {
        mapList.clear();
        paths.clear();
        dataMap.put("ShopCategoryType", ShopCategoryType);
        dataMap.put("CompanyName", etName.getText().toString().trim());
        dataMap.put("CompanyAddress", etAddress.getText().toString().trim());
        dataMap.put("CreditCode", et18code.getText().toString().trim());
        dataMap.put("Longitude", Longitude);
        dataMap.put("Latitude", Latitude);
        dataMap.put("Province", Province);
        dataMap.put("City", City);
        dataMap.put("Area", Area);
        for (int i = 0; i < allMap.size(); i++) {
            mapList.add((Map<Object, Object>) allMap.get(i+1));
        }
        mapList1.addAll(mapList);
        dataMap.put("ImgUrlList", mapList1);
    }

    private boolean check() {

        if (StrUtils.isEmpty(et18code.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入社会信用代码");
            return false;
        }
        if (StrUtils.isEmpty(etAddress.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入详细地址");
            return false;
        }
        if (StrUtils.isEmpty(etName.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入公司名称");
            return false;
        }
        if (et18code.getText().toString().trim().length() != 18) {
            dialogUtils.noBtnDialog("请输入正确的社会信用代码");
            return false;
        }
        if (!(allMap.size() > 0)) {
            dialogUtils.noBtnDialog("请完善相关资质信息");
            return false;
        }
        return true;
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
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
                type = 3;
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
                type = 3;
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

    @Override
    public void takeSuccess(TResult result) {
        if (type == 3) {
            if (paths.size() < 5) {
                ImgDelagateBean imgDelagateBean = new ImgDelagateBean();
                imgDelagateBean.isItem = true;
                imgDelagateBean.isDelet = false;

                File file = new File(result.getImage().getCompressPath());
                if (file.length() > 2 * 1024 * 1024) {
                    imgDelagateBean.url = result.getImage().getOriginalPath();
                } else {
                    imgDelagateBean.url = result.getImage().getOriginalPath();
                }

                data.add(0, imgDelagateBean);
                dataCopy.add(imgDelagateBean);
                addAdapter.setData(data, dataCopy);
                adapter.notifyDataSetChanged();
            } else {
                dialogUtils.noBtnDialog("最多添加五张");
            }
        } else {

            pathList.clear();
            File file = new File(result.getImage().getCompressPath());
            if (file.length() > 200* 1024) {
                pathList.add(result.getImage().getCompressPath());
            } else {
                pathList.add(result.getImage().getOriginalPath());
            }
            switch (type) {
                case 1:
                    ImageUtils.loadUrlImage(PerfectCompanyThreeActivity.this, pathList.get(0), ivChose1);
//                    allMap.put(1, pathMap1);
                    break;
                case 2:
                    ImageUtils.loadUrlImage(PerfectCompanyThreeActivity.this, pathList.get(0), ivChose2);
//                    allMap.put(2, pathMap2);
                    break;
            }
            upLoadImg(pathList);
        }

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


    private void upLoadImg(List<String> datas) {
        dialogUtils.dismissDialog();

        addSubscription(RequestClient.UpLoadFile(datas, this, new NetSubscriber<BaseResultBean>(this,true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                StringBuffer str = new StringBuffer(model.filepath);
                String url = str.replace(0, 1, "h").toString().trim();
                switch (type) {
                    case 1:
                        pathMap1.put("ImgType", 3);
                        pathMap1.put("ImgSaveUrl", model.filepath);
                        allMap.put(1, pathMap1);
                        break;
                    case 2:
                        pathMap2.put("ImgType", 4);
                        pathMap2.put("ImgSaveUrl", model.filepath);
                        allMap.put(2, pathMap2);
                        break;
                    case 3:
                        pathMap3.put("ImgType", 5);
                        pathMap3.put("ImgSaveUrl", model.filepath);
                        allMap.put(3, pathMap3);
                        setData();
                        if (check()) {
                            bundle = new Bundle();
                            bundle.putSerializable("data", (Serializable) dataMap);
                            bundle.putInt("flag", 2);
                            bundle.putString("psw",psw);
                            if (flag == 3) {
                                commitData();
                            } else {
                                JumpUtils.dataJump(PerfectCompanyThreeActivity.this, PerfectPersonThreeActivity.class, bundle, false);
                            }
                        }
                        break;
                }
            }
        }));

    }

    private String Province;
    private String City;
    private String Area;

    private void showPickerView() {//条件选择器初始化

//        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText() + " " +
//                        options2Items.get(options1).get(options2) + " " +
//                        options3Items.get(options1).get(options2).get(options3);
//                Province = options1Items.get(options1).getPickerViewText();
//                City = options2Items.get(options1).get(options2);
//                Area = options3Items.get(options1).get(options2).get(options3);
//                tvAddress.setText(tx);
//            }
//        })
//
//                .setTitleText("城市选择")
//                .setCancelText("取消")
//                .setSubmitText("确定")
//                .setDividerColor(Color.BLACK)
//                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
//                .setContentTextSize(20)
//                .build();
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
//        pvOptions.show();
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(this).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AearBeanDao aearBeanDao = daoSession.getAearBeanDao();
        dialogUtils.Address1Dialog(new DialogUtils.Address1Chose() {
            @Override
            public void onAddressChose(AreasBean bean) {
                com.fdl.bean.daoBean.AearBean aearBean = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.Id.eq(bean.ParentID)).unique();
                 City = aearBean.id;
                com.fdl.bean.daoBean.AearBean aearBean1 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.Id.eq(aearBean.ParentID)).unique();
                 Province = aearBean1.id;
                Area = bean.id;
                tvAddress.setText(aearBean1.AddressName+" "+aearBean.AddressName+" "+bean.AddressName);
            }
        });
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

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

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
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


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
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

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;


    private void checkPermission() {
        List<String> permissionlist = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(PerfectCompanyThreeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(PerfectCompanyThreeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(PerfectCompanyThreeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionlist.isEmpty()) {
            String[] permissions = permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(PerfectCompanyThreeActivity.this, permissions, 1);
        } else {
            getLoction();
        }

    }

    private void getLoction() {
        //设置位置客户端选项
        LocationClientOption option = new LocationClientOption();
        //设置位置取得模式  （这里是指定为设备传感器也就是 GPS 定位）
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        //设置 间隔扫描的时间  也就是 位置时隔多长时间更新
//        option.setScanSpan(5000);
        //设置 是否需要地址 （需要联网取得 百度提供的位置信息）
        option.setIsNeedAddress(true);

        // 实例化 LocationClient  传入的context 应该是全局的
        mLocationListener = new LocationClient(getApplicationContext());
        //将选项设置进去
        mLocationListener.setLocOption(option);
        //设置监听器 （这个方法有两个其中一个过时了，要使用 new BDAbstractLocationListener 的这个监听器）
        mLocationListener.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                StringBuilder currentPosition = new StringBuilder();
                //获取经纬度
                currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
                currentPosition.append("经线：").append(bdLocation.getLongitude()).append("\n");
                Latitude = bdLocation.getLatitude();
                Longitude = bdLocation.getLongitude();
                tvJd.setText("纬度：" + Latitude);
                tvWd.setText("经度：" + Longitude);

                //获取定位方式

                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                    currentPosition.append("GPS 定位");
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                    currentPosition.append("网络 定位");
                }
            }
        });
        //显示到Activity
        mLocationListener.start();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);

        mapView.onDestroy();
        mapView = null;
        super.onDestroy();

    }

    private void commitData() {
        addSubscription(RequestClient.MerchantLocalEnter(dataMap, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                bundle = new Bundle();
                bundle.putString("phone", (String) dataMap.get("PhoneNumber"));
                bundle.putString("psw", psw);
                JumpUtils.dataJump(PerfectCompanyThreeActivity.this, CompleteActivity.class, bundle, false);
            }
        }));
    }
}
