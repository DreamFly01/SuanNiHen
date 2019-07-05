package com.fdl.activity.goTravel;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.TravelBean;
import com.fdl.bean.TravelLuckyBean;
import com.fdl.bean.WinningLogBean;
import com.fdl.requestApi.APIException;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/25<p>
 * <p>changeTime：2019/4/25<p>
 * <p>version：1<p>
 */
public class TravelActivity extends BaseActivity {
    @BindView(R.id.vf)
    ViewFlipper vf;
    @BindView(R.id.iv_box)
    LinearLayout ivBox;
    @BindView(R.id.tv_explain)
    RelativeLayout tvExplain;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.btn_action)
    ImageView btnAction;

    public static RelativeLayout tvRecode;
    public static TextView tvCount;
    @BindView(R.id.ll_action)
    RelativeLayout llAction;
    private LuckyMonkeyPanelView lucky_panel;
    private String orderNo;
    private Bundle bundle;
    public static ImageView btn_action;
    private int type;
    private static int count;
    private static int isOpen;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_travel_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            orderNo = bundle.getString("orderNo");
            type = bundle.getInt("type");
        }
    }

    @Override
    public void setUpViews() {

        lucky_panel = (LuckyMonkeyPanelView) findViewById(R.id.lucky_panel);
        btn_action = (ImageView) findViewById(R.id.btn_action);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvRecode = (RelativeLayout)findViewById(R.id.tv_recode);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.travel_big_small);
        llAction.setAnimation(animation);
        animation.start();


    }

    private void fillView(List<TravelBean.PrizePlate> datas) {
        lucky_panel.setDatas(this, datas);
        setBtnAction();
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen == 0) {
                    ToastUtil.show("该区域抽奖暂未开放");
                } else {
                    if (count > 0) {
                        tvCount.setText("您还有" + count + "次");
                        lucky_panel.startGame();
                        btn_action.setEnabled(false);
                        tvRecode.setEnabled(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                    getLucky();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        showShortToast("您没有抽奖次数");
                    }
                }
            }
        });
    }

    public static void setBtnAction() {
        if (isOpen == 0) {
            tvCount.setText("该区域抽奖暂未开放");
        } else {
            if (count > 0) {
                tvCount.setText("您还有" + count + "次");
            } else {
                tvCount.setText("您的抽奖机会已用完");
            }
        }

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

    private List<TravelBean.PrizePlate> datas = new ArrayList<>();
    private String content;

    private void getTravelData() {
        addSubscription(RequestClient.GetPrizePool(this, new NetSubscriber<BaseResultBean<TravelBean>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<TravelBean> model) {
                datas = model.data.result;
                content = model.data.description;
                count = model.data.count;
                isOpen = model.data.isOpen;
                fillView(datas);
            }
        }));
    }

    private void getLucky() {
        addSubscription(RequestClient.LuckyDraw(this, new NetSubscriber<BaseResultBean<TravelLuckyBean>>(this) {
            @Override
            public void onResultNext(BaseResultBean<TravelLuckyBean> model) {
                count = model.data.count;
                for (int i = 0; i < datas.size(); i++) {
                    if (model.data.PrizeLevelCode.equals(datas.get(i).LevelCode)) {

                        lucky_panel.tryToStop(i, getFragmentManager(), model.data);
                    }
                }
            }

            @Override
            public void onResultErro(APIException erro) {
                super.onResultErro(erro);
                int stopIndex = 0;
                TravelLuckyBean travelLuckyBean = new TravelLuckyBean();
                travelLuckyBean.PrizeLevelCode = "V00040";
                travelLuckyBean.PrizeLevelName = "谢谢参与";
                travelLuckyBean.PrizeName = "网络错误";
                travelLuckyBean.PrizeImg = "https://shop.snihen.com:8448/api/Source/474128";
                for (int i = 0; i < datas.size(); i++) {
                    if ("V00080".equals(datas.get(i).LevelCode)) {
                        stopIndex = i;
                    }
                }
                lucky_panel.tryToStop(stopIndex, getFragmentManager(), travelLuckyBean);
            }

        }));
    }

    private void getWinningLog() {
        addSubscription(RequestClient.GetWinningLog(this, new NetSubscriber<BaseResultBean<List<WinningLogBean>>>(this) {
            @Override
            public void onResultNext(BaseResultBean<List<WinningLogBean>> model) {
                getTravelData();

                vf.setInAnimation(TravelActivity.this, R.anim.notice_in);
                vf.setOutAnimation(TravelActivity.this, R.anim.notice_out);
                for (int i = 0; i < model.data.size(); i++) {
                    View v = (TravelActivity.this).getLayoutInflater().inflate(R.layout.item_tip_layout, null);
                    TextView titleTv = (TextView) v.findViewById(R.id.tv_tip);
                    titleTv.setTextColor(Color.parseColor("#F2F2F2"));
                    StringBuffer stringBuffer = new StringBuffer(model.data.get(i).UserName);
                    stringBuffer.replace(1, model.data.get(i).UserName.length(), "****");
                    titleTv.setText("用户《" + stringBuffer.toString() + "》 刚刚抽中了 “" + model.data.get(i).PrizeLevelName + "”,奖品为" + model.data.get(i).PrizeName);
                    vf.addView(v);
                }
                vf.startFlipping();
            }
        }));
    }

    @OnClick({R.id.tv_explain, R.id.tv_recode, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_explain:
                ExplainFragment explainFragment = new ExplainFragment();
                bundle = new Bundle();
                bundle.putString("data", content);
                explainFragment.setArguments(bundle);
                explainFragment.show(getFragmentManager(), "explain");
                break;
            case R.id.tv_recode:
                jumpActivity(MyLuckyActivity.class);
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWinningLog();
    }
}
