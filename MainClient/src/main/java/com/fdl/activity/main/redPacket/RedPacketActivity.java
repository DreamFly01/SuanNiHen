package com.fdl.activity.main.redPacket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.main.redPacket.bean.BaseRedPacketBean;
import com.fdl.activity.main.redPacket.bean.GoodsListBean;
import com.fdl.activity.main.redPacket.bean.RedPacketBean;
import com.fdl.activity.main.redPacket.bean.RedPacketListBean;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.common.manger.ShopJumpManger;
import com.fdl.jpush.Logger;
import com.fdl.utils.Contans;
import com.fdl.utils.GetJsonDataUtil;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sg.cj.common.base.utils.SystemUtil;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.uitls.AppViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 陈自强
 * @date 2019/7/29
 */
public class RedPacketActivity extends BaseActivity {

    private ListView listView;
    private RedPacketListAdepter adepter;
    private List<RedPacketListBean> list;
    private Context context;
    private Gson gson;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_red_packet);
        context = this;
        gson = new Gson();
        listView = findView(R.id.red_packet_list);
        setFinishBtn(R.id.red_packet_finish_btn);
        list = new ArrayList<>();
        adepter = new RedPacketListAdepter(list, this);
        listView.setAdapter(adepter);
        getData();

    }

    @Override
    public void setUpViews() {

    }

    @Override
    public void setUpLisener() {

    }

    protected void close() {
        finish();
    }


    private void getData() {
        Map<String, Object> map = new TreeMap<>();
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        String addressID = SPUtils.getInstance(context).getString(Contans.CITY_ID);
        if (!TextUtils.isEmpty(addressID)) {
            map.put("AddressID", Integer.parseInt(addressID));
            String json = gson.toJson(map);
            OkGo.<String>post(Constants.GET_RED_PACKET)
                    .upJson(json)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            showData(response.body());
                        }
                    });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ShopJumpManger jumpManger = new ShopJumpManger(RedPacketActivity.this);
                    jumpManger.setBusinessActivities(Integer.parseInt(list.get(position - 1).getBusinessActivities()));
                    jumpManger.setShopType(Integer.parseInt(list.get(position - 1).getShopType()));
                    jumpManger.setStroeId(list.get(position - 1).getID());
                    jumpManger.jumpShopActivity();
                }
            });
        }

    }

    private void showData(String json) {
        Logger.i("okgo", json);
        if (!TextUtils.isEmpty(json)) {
            BaseRedPacketBean bean = gson.fromJson(json, BaseRedPacketBean.class);
            RedPacketBean beans = bean.getData();
            list.addAll(beans.getList());
            for (RedPacketListBean item : list) {
                if (item.getWay() != 2) {
                    item.setShowGoods(false);
                } else {
                    item.setShowGoods(true);
                }

                for (GoodsListBean cItem : item.getGoodsList()) {
                    cItem.setpId(item.getID());
                }
            }

            View view = View.inflate(context, R.layout.red_packet_header_item, null);
            TextView redPacketPrice;
            redPacketPrice = view.findViewById(R.id.red_packet_msg);
            String price = beans.getRedPacketPrice() + "";
            SpannableString spannableString = new SpannableString(StrUtils.fromResources(context, R.string.red_packet_price, price));
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.red_packet_yellow));
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(AppViewUtils.dp2px(16));
            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);

            spannableString.setSpan(colorSpan, 2, 2 + price.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(sizeSpan, 2, 2 + price.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(styleSpan, 2, 2 + price.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            redPacketPrice.setText(spannableString);
            listView.addHeaderView(view);
            adepter.notifyDataSetChanged();
        }
    }


}
