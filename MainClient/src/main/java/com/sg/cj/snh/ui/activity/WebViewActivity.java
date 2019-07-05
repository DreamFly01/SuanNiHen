package com.sg.cj.snh.ui.activity;


import android.widget.LinearLayout;

import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.constants.Constants;

import butterknife.BindView;

/**
 * author : ${CHENJIE}
 * created at  2018/11/29 21:41
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WebViewActivity extends BaseActivity {

  private static final String TAG = "WebViewActivity";
  private String url;

  @BindView(R.id.layout_web)
  LinearLayout layoutWeb;

  private String webViewUrl;

  private AgentWeb mAgentWeb;

  @Override
  protected void initInject() {

  }

  @Override
  protected int getLayout() {
    return R.layout.activity_webview;
  }

  @Override
  protected void initEventAndData() {
    url = getIntent().getStringExtra("url");

    webViewUrl = getIntent().getStringExtra("webViewUrl");

    if(StrUtils.isEmpty(url)) {
      SgLog.d(TAG + "webViewUrl == " + webViewUrl);

      String cookies = PartyApp.getAppComponent().getDataManager().getCookieString();
      SgLog.d("cookies == " + cookies);
      AgentWebConfig.syncCookie(Constants.BASE_HOST, cookies);
      ImmersionBar.with(this).statusBarView(R.id.view_status).init();
      mAgentWeb = AgentWeb.with(this)
              .setAgentWebParent(layoutWeb, new LinearLayout.LayoutParams(-1, -1))
              .useDefaultIndicator()
              .createAgentWeb()
              .ready()
              .go(webViewUrl);
    }else {
      mAgentWeb = AgentWeb.with(this)
              .setAgentWebParent(layoutWeb, new LinearLayout.LayoutParams(-1, -1))
              .useDefaultIndicator()
              .createAgentWeb()
              .ready()
              .go(url);
    }
  }


  @Override
  protected void onPause() {
    if (null != mAgentWeb) {
      mAgentWeb.getWebLifeCycle().onPause();
    }
    super.onPause();

  }

  @Override
  protected void onResume() {
    if (null != mAgentWeb) {
      mAgentWeb.getWebLifeCycle().onResume();
    }
    super.onResume();
  }


  @Override
  public void onDestroy() {

    if (null != mAgentWeb) {
      mAgentWeb.getWebLifeCycle().onDestroy();
    }

    SgLog.d("TAG:" + TAG, "----onDestroy()--WebViewActivity");

    super.onDestroy();
  }


}
