package com.fdl.activity.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CityBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;
//import com.zaaach.citypicker.CityPicker;
//import com.zaaach.citypicker.adapter.OnPickListener;
//import com.zaaach.citypicker.model.City;
//import com.zaaach.citypicker.model.LocateState;
//import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/18<p>
 * <p>changeTime：2019/1/18<p>
 * <p>version：1<p>
 */
public class CityActivity extends BaseActivity {
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
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;

//    private List<City> allCities = new ArrayList<>();
//test svn
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        IsBang.setImmerHeard(this,rlHead);
        setContentView(R.layout.activity_city_layout);

    }

    @Override
    public void setUpViews() {

    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.Getareas("", this, new NetSubscriber<BaseResultBean<List<CityBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<CityBean>> model) {
                avi.setVisibility(View.GONE);
//                if (model.data.size() > 0) {
//                    for (int i = 0; i < model.data.size(); i++) {
//                        City city = new City(model.data.get(i).name,"",Pinyin.toPinyin(model.data.get(i).name, ""),model.data.get(i).Id+"");
//                        allCities.add(city);
//                    }
////                        Collections.sort(allCities);
//                }
//                cityPick();
            }
        }));
    }

//    private void cityPick() {
//        CityPicker.from(CityActivity.this)
//                .enableAnimation(false)
//                .setAnimationStyle(0)
//                .setLocatedCity(null)
//                .setHotCities(null)
//                .setOnPickListener(new OnPickListener() {
//                    @Override
//                    public void onPick(int position, City data) {
////                        currentTV.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
//                        Toast.makeText(
//                                getApplicationContext(),
//                                String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
//                                Toast.LENGTH_SHORT)
//                                .show();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onLocate() {
//                        //开始定位，这里模拟一下定位
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                CityPicker.from(CityActivity.this).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
//                            }
//                        }, 3000);
//                    }
//                })
//                .show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.heard_back)
    public void onClick() {
    }
}
