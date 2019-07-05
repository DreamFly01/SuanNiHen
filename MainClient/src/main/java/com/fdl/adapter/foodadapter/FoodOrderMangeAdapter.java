package com.fdl.adapter.foodadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.activity.buy.PayActivity;
import com.fdl.activity.order.DiscussActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.bean.daoBean.CommonBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.JumpUtils;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 美食订单管理
 */
public class FoodOrderMangeAdapter extends CommonAdapter<CommonBean> {


    private FoodOrderManageItemAdapter adapter;

    private DialogUtils dialogUtils;

    private CancelOnClick cancelOnClick;
    private ComfireGet comfireGet;
    private setIsFrist setIsFrist;

    private int foodImg[] = {R.mipmap.cp_icon_empty, R.mipmap.cp_icon_empty};
    private String titelname[] = {"冬天的毛衣", "秋天的落叶"};
    private String foodColor[] = {"颜色黑色", "颜色黄色"};
    private String foodNumbwr[] = {"x2", "x1"};
    private String money[] = {"实付100.00", "实付10:00元"};


    public FoodOrderMangeAdapter(Context context, int layoutId, List<CommonBean> datas) {
        super(context, layoutId, datas);
        dialogUtils = new DialogUtils(context);

    }


    @Override
    protected void convert(ViewHolder holder, CommonBean CommonBean, int position) {

        ImageView imgFoodHead = holder.getView(R.id.img_food_head);
        TextView tvStore = holder.getView(R.id.tv_store);
        TextView tv_state1 = holder.getView(R.id.tv_state1);//待支付
        TextView tvMoney = holder.getView(R.id.tv_money);//金额

        TextView tvChose1 = holder.getView(R.id.tv_chose1);
        TextView tvChose2 = holder.getView(R.id.tv_chose2);
        TextView tvChose3 = holder.getView(R.id.tv_chose3);
        RecyclerView recyclerView = holder.getView(R.id.item_recyclerview);
        LinearLayout ll_item = holder.getView(R.id.ll_item);


        adapter = new FoodOrderManageItemAdapter(mContext, R.layout.item_order_item_layout, CommonBean.goodslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        tvChose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (CommonBean.OrderState) {
                    case 0:
                        break;
                    //好友代付
                    case 1:
                        ShareWechat("订单号：" + CommonBean.OrderNo, CommonBean.FriendPayUrl, CommonBean.goodslist.get(0).GoodsImg);
                        break;
                    //提醒发货
                    case 2:
                        postMsg(CommonBean.ShopId + "", CommonBean.Id + "");
                        break;
                    //确认收货
                    case 3:
                        comfireGet(CommonBean.Id + "");
                        tv_state1.setText("实付款：");
                        tvChose1.setVisibility(View.GONE);
                        tvChose2.setVisibility(View.GONE);
                        tvChose3.setVisibility(View.GONE);
                        break;
                    //去评价
                    case 4:
                        Bundle bundle = new Bundle();
                        bundle.putString("url", CommonBean.goodslist.get(0).GoodsImg);
                        bundle.putString("name", CommonBean.goodslist.get(0).Name);
                        bundle.putString("orderNo", CommonBean.OrderNo);
                        bundle.putString("goodsId", CommonBean.goodslist.get(0).Id + "");
                        JumpUtils.dataJump((Activity) mContext, DiscussActivity.class, bundle, false);

                        break;
                    case 5:
                        break;
                }
            }
        });
        tvChose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUtils.simpleDialog("确定取消订单么？", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        cancelOrder(CommonBean.Id + "");
                    }
                }, true);
                tv_state1.setText("待付款：");
                tvChose1.setVisibility(View.GONE);
                tvChose2.setVisibility(View.GONE);
                tvChose3.setVisibility(View.VISIBLE);
                tvChose3.setText("取消");
                tvChose3.setTextColor(Color.BLACK);
                tvChose3.setBackgroundColor(Color.WHITE);
            }
        });
        tvChose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("TotalOrderNo", CommonBean.ToTalOrderNo);
                bundle.putString("TotalMoney", CommonBean.TotalPrice + "");
                bundle.putString("Integral", CommonBean.Integral + "");
                bundle.putString("Balance", CommonBean.BalanceOne + "");
                bundle.putString("subOrderNo", CommonBean.OrderNo + "");
                setIsFrist.isFrist(false);
                JumpUtils.dataJump((Activity) mContext, PayActivity.class, bundle, false);
            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view1, RecyclerView.ViewHolder holder1, int position1) {
                mOnItemClickListener.onItemClick(holder.getConvertView(), holder1, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    //发货提醒
    private void postMsg(String touid, String orderid) {
        RequestClient.PostMsg(touid, orderid, "", "", mContext, new NetSubscriber<BaseResultBean<List<OrderDataBean>>>(mContext, true) {
            @Override
            public void onResultNext(BaseResultBean<List<OrderDataBean>> model) {
                ToastUtil.shortShow("店家已收到您的信息，请耐心等候~");
            }
        });
    }

    //确认收货
    private void comfireGet(String orderid) {
        RequestClient.ComfireGet(orderid, mContext, new NetSubscriber<BaseResultBean<List<OrderDataBean>>>(mContext, true) {
            @Override
            public void onResultNext(BaseResultBean<List<OrderDataBean>> model) {
                ToastUtil.shortShow("确认收货成功~");
                comfireGet.comfire();
            }
        });
    }

    //好友代付
    private void ShareWechat(String title, String url, String imgUrl) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText("我在算你狠发现一个不错的商品，赶快来帮我支付一下");
        sp.setUrl(url);
        sp.setTitle(title);
        sp.setImageUrl(imgUrl);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.share(sp);
    }

    //取消订单
    private void cancelOrder(String orderid) {
        RequestClient.CancelOrder(orderid, mContext, new NetSubscriber<BaseResultBean<List<OrderDataBean>>>(mContext, true) {
            @Override
            public void onResultNext(BaseResultBean<List<OrderDataBean>> model) {
                ToastUtil.shortShow("取消成功~");
                dialogUtils.dismissDialog();
                cancelOnClick.cancle();

            }
        });
    }

    public void addData(List<CommonBean> list) {
        mDatas.addAll(list);
        this.notifyDataSetChanged();
    }

    public void setCancelOnClick(CancelOnClick onClick) {
        this.cancelOnClick = onClick;
        notifyDataSetChanged();
    }

    public void setComfireGet(ComfireGet onclick) {
        this.comfireGet = onclick;
        notifyDataSetChanged();
    }

    public void setSetIsFrist(setIsFrist onclick) {
        this.setIsFrist = onclick;
    }

    public interface CancelOnClick {
        void cancle();
    }

    public interface ComfireGet {
        void comfire();
    }

    public interface setIsFrist {
        void isFrist(boolean isFrist);
    }
}
