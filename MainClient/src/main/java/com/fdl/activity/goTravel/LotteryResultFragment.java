package com.fdl.activity.goTravel;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.adapter.MyLuckyDetailsAdapter;
import com.fdl.bean.TravelLuckyBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/7<p>
 * <p>changeTime：2019/5/7<p>
 * <p>version：1<p>
 */
public class LotteryResultFragment extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_lucky)
    TextView tvLucky;
    @BindView(R.id.iv_lucky_bg)
    ImageView ivLuckyBg;
    @BindView(R.id.tv_lucky_name)
    TextView tvLuckyName;
    @BindView(R.id.tv_lucky_num)
    TextView tvLuckyNum;
    @BindView(R.id.tv_lucky_address)
    TextView tvLuckyAddress;
    @BindView(R.id.confir)
    TextView confir;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.confir1)
    TextView confir1;
    @BindView(R.id.rl_01)
    RelativeLayout rl01;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private LinearLayout view;

    private Bundle bundle;
    private TravelLuckyBean bean;
    private MyLuckyDetailsAdapter adapter;
    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = (LinearLayout) inflater.inflate(R.layout.dialog_lotteryresult_layout, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();
        if (null != bundle) {
            bean = bundle.getParcelable("data");
            setRecyclerView();
            fillView();
        }
        TravelActivity.btn_action.setEnabled(true);
        TravelActivity.tvRecode.setEnabled(true);
        TravelActivity.setBtnAction();
        return view;
    }

    private void fillView() {
        if ("V00040".equals(bean.PrizeLevelCode) || "V00080".equals(bean.PrizeLevelCode)) {
            ll01.setVisibility(View.GONE);
            rl01.setVisibility(View.VISIBLE);
        } else {
            ll01.setVisibility(View.VISIBLE);
            rl01.setVisibility(View.GONE);
            tvLucky.setText("刚刚中了" + bean.PrizeLevelName);
            ImageUtils.loadUrlImage(PartyApp.getAppComponent().getContext(), bean.PrizeImg, ivLuckyBg);
            tvLuckyName.setText(bean.PrizeName);
            tvLuckyNum.setText("兑奖号：" + bean.ExchangeCode);
        }
        adapter.setNewData(bean.ExchangeAddress);


    }
private void setRecyclerView(){
    adapter = new MyLuckyDetailsAdapter(R.layout.item_myluckydetails_layout,null);
    adapter.setFrom(1);
    LinearLayoutManager linearLayoutManager =new LinearLayoutManager(PartyApp.getAppComponent().getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(adapter);
    recyclerView.setNestedScrollingEnabled(false);
}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.confir, R.id.confir1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confir:
                getDialog().dismiss();
                break;
            case R.id.confir1:
                getDialog().dismiss();
                break;
        }
    }
}
