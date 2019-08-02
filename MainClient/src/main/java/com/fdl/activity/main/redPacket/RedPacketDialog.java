package com.fdl.activity.main.redPacket;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fdl.activity.coupons.MyCouponsActivity;
import com.sg.cj.snh.R;

/**
 * @author 陈自强
 * @date 2019/7/29
 */
public class RedPacketDialog extends Dialog implements View.OnClickListener {
    private TextView mRedPacketShow;
    private TextView mRedPacketMyCoupon;
    private ImageButton mRedPacketGetBtn;
    private ImageButton mRedPacketCloseBtn;

    private Context context;

    public RedPacketDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public RedPacketDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_red_enelope);
        getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mRedPacketShow = (TextView) findViewById(R.id.red_packet_show);
        mRedPacketMyCoupon = (TextView) findViewById(R.id.red_packet_my_coupon);
        mRedPacketGetBtn = (ImageButton) findViewById(R.id.red_packet_get_btn);
        mRedPacketCloseBtn = (ImageButton) findViewById(R.id.red_packet_close_btn);

        mRedPacketMyCoupon.setOnClickListener(this::onClick);
        mRedPacketGetBtn.setOnClickListener(this::onClick);
        mRedPacketCloseBtn.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.red_packet_get_btn:
                context.startActivity(new Intent(context, RedPacketActivity.class));
                dismiss();
                break;
            case R.id.red_packet_my_coupon:
                context.startActivity(new Intent(context, MyCouponsActivity.class));
                break;
            case R.id.red_packet_close_btn:
                dismiss();
                break;
            default:
                break;

        }
    }
}
