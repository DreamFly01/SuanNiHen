package com.fdl.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.activity.buy.PayActivity;
import com.fdl.activity.order.DiscussActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.MyOrderBean;
import com.fdl.bean.OrderDataBean;
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

import static com.mob.tools.utils.Strings.getString;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/5<p>
 * <p>changeTime：2019/1/5<p>
 * <p>version：1<p>
 */
public class MyOrderAdapter extends CommonAdapter<MyOrderBean> {
    private MyOrderItemAdapter adapter;

    private DialogUtils dialogUtils;

    private CancelOnClick cancelOnClick;
    private ComfireGet comfireGet;
    private setIsFrist setIsFrist;

    public MyOrderAdapter(Context context, int layoutId, List<MyOrderBean> datas) {
        super(context, layoutId, datas);
        dialogUtils = new DialogUtils(context);

    }

    @Override
    protected void convert(ViewHolder holder, MyOrderBean myOrderBean, int position) {
        TextView tvOrder = holder.getView(R.id.tv_order_no);
        TextView tvState = holder.getView(R.id.tv_state);
        TextView tv_state1 = holder.getView(R.id.tv_state1);
        TextView tvMoney = holder.getView(R.id.tv_money);
        TextView tvExtra = holder.getView(R.id.tv_extra);
        TextView tvChose1 = holder.getView(R.id.tv_chose1);
        TextView tvChose2 = holder.getView(R.id.tv_chose2);
        TextView tvChose3 = holder.getView(R.id.tv_chose3);
        RecyclerView recyclerView = holder.getView(R.id.item_recyclerview);
        LinearLayout ll_item = holder.getView(R.id.ll_item);


        tvOrder.setText("订单号：" + myOrderBean.OrderNo);
        switch (myOrderBean.OrderState) {
            case 0:
                tvState.setText("已取消");
                break;
            case 1:
                tvState.setText("待付款");
                tv_state1.setText("待付款：");
                tvChose1.setVisibility(View.VISIBLE);
                tvChose2.setVisibility(View.VISIBLE);
                tvChose3.setVisibility(View.VISIBLE);
                tvChose1.setText("取消订单");
                tvChose2.setText("去付款");
                if (myOrderBean.ShopType.equals("6")) {
                    tvChose3.setVisibility(View.GONE);
                }
                tvChose3.setVisibility(View.GONE);
                tvChose3.setText("好友代付");
                tvChose3.setTextColor(Color.parseColor("#fc1a4e"));
                tvChose3.setBackgroundResource(R.drawable.shape_red_range);
                break;
            case 2:
                tvState.setText("待发货");
                tv_state1.setText("实付款：");
                tvChose1.setVisibility(View.GONE);
                tvChose2.setVisibility(View.GONE);
                tvChose3.setVisibility(View.VISIBLE);
                tvChose3.setText("提醒发货");
                break;
            case 3:
                tvState.setText("待收货");
                tv_state1.setText("实付款：");
                tvChose1.setVisibility(View.GONE);
                tvChose2.setVisibility(View.GONE);
                tvChose3.setVisibility(View.VISIBLE);
                tvChose3.setText("确认收货");

                break;
            case 4:
                tvState.setText("待评价");
                tv_state1.setText("实付款：");
                tvChose1.setVisibility(View.GONE);
                tvChose2.setVisibility(View.GONE);
                tvChose3.setVisibility(View.VISIBLE);
                tvChose3.setText("去评论");
                break;
            case 5:
                tvState.setText("已完成");
                tv_state1.setText("实付款：");
                tvChose1.setVisibility(View.GONE);
                tvChose2.setVisibility(View.GONE);
                tvChose3.setVisibility(View.GONE);
                break;
            case 6:
                tvState.setText("交易关闭");
                tv_state1.setText("待付款：");
                tvChose1.setVisibility(View.GONE);
                tvChose2.setVisibility(View.GONE);
                tvChose3.setVisibility(View.GONE);
                tvChose3.setText("取消");
                tvChose3.setTextColor(Color.BLACK);
                tvChose3.setBackgroundColor(Color.WHITE);
                break;
        }
        tvMoney.setText("¥" + myOrderBean.TotalPrice + "");

        if ("6".equals(myOrderBean.ShopType)) {
            tvExtra.setText("(到店自取)");
        } else {
            if (myOrderBean.ExpressPrice <= 0) {
                tvExtra.setText("(包邮)");
            } else {
                tvExtra.setText("(" + myOrderBean.ExpressPrice + ")");
            }
        }

        adapter = new MyOrderItemAdapter(mContext, R.layout.item_order_item_layout, myOrderBean.goodslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        tvChose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (myOrderBean.OrderState) {
                    case 0:
                        break;
                    //好友代付
                    case 1:
                        ShareWechat("订单号：" + myOrderBean.OrderNo, myOrderBean.FriendPayUrl, myOrderBean.goodslist.get(0).GoodsImg);
                        break;
                    //提醒发货
                    case 2:
                        postMsg(myOrderBean.ShopId + "", myOrderBean.Id + "");
                        break;
                    //确认收货
                    case 3:
                        comfireGet(myOrderBean.Id + "");
                        tvState.setText("完成");
                        tv_state1.setText("实付款：");
                        tvChose1.setVisibility(View.GONE);
                        tvChose2.setVisibility(View.GONE);
                        tvChose3.setVisibility(View.GONE);
                        break;
                    //去评价
                    case 4:
                        Bundle bundle = new Bundle();
                        bundle.putString("url", myOrderBean.goodslist.get(0).GoodsImg);
                        bundle.putString("name", myOrderBean.goodslist.get(0).Name);
                        bundle.putString("orderNo", myOrderBean.OrderNo);
                        bundle.putString("goodsId", myOrderBean.goodslist.get(0).Id + "");
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
                        cancelOrder(myOrderBean.Id + "");
                    }
                }, true);
                tvState.setText("");
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
                bundle.putString("TotalOrderNo", myOrderBean.ToTalOrderNo);
                bundle.putString("TotalMoney", myOrderBean.TotalPrice + "");
                bundle.putString("Integral", myOrderBean.Integral + "");
                bundle.putString("Balance", myOrderBean.BalanceOne + "");
                bundle.putString("subOrderNo", myOrderBean.OrderNo + "");
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

    public void addData(List<MyOrderBean> list) {
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
