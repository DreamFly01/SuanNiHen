package com.fdl.activity.merchantEntry;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.adapter.TypePriceAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CityBean;
import com.fdl.bean.MapDataBean;
import com.fdl.bean.StoreClassficationBean;
import com.fdl.bean.TypePriceBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.JumpUtils;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class PerfectPersonThreeActivity extends BaseActivity {
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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private TypePriceBean bean;
    private List<TypePriceBean> datas = new ArrayList<>();
    private TypePriceAdapter adapter;
    private Bundle bundle;
    private Map<String, Object> dataMap;
    private int flag;
    private String psw;
    private String phone;
    private String name;
    private String shopType;
    private String money;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_perfectperson3_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            dataMap = (Map<String, Object>) bundle.getSerializable("data");
            flag = bundle.getInt("flag");
            psw = bundle.getString("psw");
            phone = bundle.getString("phone");
            name = bundle.getString("name");
            shopType = bundle.getString("shopType");
            money = bundle.getString("money");
        }
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("缴纳保证金");
        tvName.setText(name);
        tvPrice.setText(money);
        getShopType();
        setDatas();
        setAdapter();
    }

    @Override
    public void setUpLisener() {

    }

    @OnClick({R.id.heard_back, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_commit:
                commitData();
                break;
        }
    }

    private void setDatas() {


    }


    private void setAdapter() {
        adapter = new TypePriceAdapter( R.layout.item_type_price_layout, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void commitData() {

        switch (flag) {
            case 1:
                addSubscription(RequestClient.MerchantPersonEnter(dataMap, this, new NetSubscriber<BaseResultBean>(this, true) {
                    @Override
                    public void onResultNext(BaseResultBean model) {
                        bundle = new Bundle();
                        bundle.putString("psw", psw);
                        bundle.putString("phone", (String) dataMap.get("PhoneNumber"));
                        JumpUtils.dataJump(PerfectPersonThreeActivity.this, CompleteActivity.class, bundle, false);
                    }
                }));
                break;
            case 2:
                addSubscription(RequestClient.MerchantCompanyEntry(dataMap, this, new NetSubscriber<BaseResultBean>(this, true) {
                    @Override
                    public void onResultNext(BaseResultBean model) {
                        bundle = new Bundle();
                        bundle.putString("psw", psw);
                        bundle.putString("phone", (String) dataMap.get("PhoneNumber"));
                        JumpUtils.dataJump(PerfectPersonThreeActivity.this, CompleteActivity.class, bundle, false);
                    }
                }));
                break;
            case 3:
                break;
        }

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
                bean = new TypePriceBean();
                bean.name = "类  目";
                bean.price1 = "非海淘";
                bean.price2 = "海淘";
                datas.add(bean);
                for (StoreClassficationBean bean1 : typeData) {
                    bean = new TypePriceBean();
                    bean.name = bean1.Name;
                    if (shopType.equals("3")) {
                        bean.price1 = bean1.PNoHaiTao;
                        bean.price2 = bean1.PHaiTao;
                    }
                    if (shopType.equals("2")) {
                        bean.price1 = bean1.ENoHaiTao;
                        bean.price2 = bean1.ENoHaiTao;
                    }
                    datas.add(bean);
                }
                adapter.setNewData(datas);
            }
        }));
    }
}
