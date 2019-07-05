package com.fdl.activity.set;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.bean.AgreementBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.CoolIndicatorLayout;
import com.just.agentweb.AgentWeb;
import com.sg.cj.snh.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/3<p>
 * <p>changeTime：2019/1/3<p>
 * <p>version：1<p>
 */
public class ProtocolActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.layout_web)
    LinearLayout layoutWeb;

    private Bundle bundle;

    private int flag;

    private AgentWeb agentWeb;
    private String url;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_protocol_layout);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            flag = bundle.getInt("flag");
            url = bundle.getString("url");
        }
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        if (StrUtils.isEmpty(url)) {
            getData();
        }else {
            heardTitle.setText("算你狠");
            fillWebView();
        }
    }

    @Override
    public void setUpLisener() {

    }

    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }


    public void getData() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetAgreementList(this, new NetSubscriber<BaseResultBean<List<AgreementBean>>>(this, false) {
            @Override
            public void onResultNext(BaseResultBean<List<AgreementBean>> model) {

                for (int i = 0; i < model.data.size(); i++) {
                    if (model.data.get(i).AgreementType == flag) {
                        url = model.data.get(i).AgreementContent;
                    }
                }
                switch (flag) {
                    case 1:
                        heardTitle.setText("软件使用许可协议");
                        break;
                    case 2:
                        heardTitle.setText("算你狠平台服务协议");
                        break;
                    case 3:
                        heardTitle.setText("法律申明和隐私权政策");
                        break;
                    case 4:
                        heardTitle.setText("企业入驻协议");
                        break;

                }
                fillWebView();
            }
        }));
    }


    private void fillWebView() {
        CoolIndicatorLayout coolIndicatorLayout = new CoolIndicatorLayout(this);
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) layoutWeb, new LinearLayout.LayoutParams(-1, -1))
                .setCustomIndicator(coolIndicatorLayout)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
