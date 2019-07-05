package com.fdl.activity.wool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.adapter.HymAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.WoolBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.ShareUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.TimeUtils;
import com.fdl.wedgit.MyWebView;
import com.sg.cj.snh.R;
import com.sg.cj.snh.uitls.GlideImageLoader;
import com.wang.avi.AVLoadingIndicatorView;
import com.youth.banner.Banner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static com.mob.tools.utils.Strings.getString;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/9<p>
 * <p>changeTime：2019/1/9<p>
 * <p>version：1<p>
 */
public class WoolDetailsActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_clock)
    ImageView ivClock;
    @BindView(R.id.tv_clock)
    TextView tvClock;
    @BindView(R.id.iv_product_logo1)
    ImageView ivProductLogo1;
    @BindView(R.id.tv_GoodsName)
    TextView tvGoodsName;
    @BindView(R.id.tv_Number1)
    TextView tvNumber1;
    @BindView(R.id.tv_SalesPrice)
    TextView tvSalesPrice;
    @BindView(R.id.tv_BargainPrice)
    TextView tvBargainPrice;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.iv_details)
    ImageView ivDetails;
    @BindView(R.id.ll_details)
    LinearLayout llDetails;
    @BindView(R.id.tv_friend)
    TextView tvFriend;
    @BindView(R.id.iv_friend)
    ImageView ivFriend;
    @BindView(R.id.ll_friend)
    LinearLayout llFriend;
    @BindView(R.id.web_view)
    MyWebView webView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_hMoney)
    TextView tvHmoney;
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    private Bundle bundle;
    private int id;
    private List<String> imgs = new ArrayList<>();
    private HymAdapter adapter;
    private DialogUtils dialogUtils;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wooldetails_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getInt("id");
        }
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("薅羊毛详情");
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetHymDetails(id, this, new NetSubscriber<BaseResultBean<WoolBean>>(this, false) {
            @Override
            public void onResultNext(BaseResultBean<WoolBean> model) {
                bean = model.data;
//                bitmap = GetLocalOrNetBitmap(bean.CoverImg);
                bitmap = returnBitMap(bean.CoverImg);
                avi.setVisibility(View.GONE);
                fillView(model.data);
            }
        }));
    }

    @OnClick({R.id.heard_back, R.id.ll_details, R.id.ll_friend, R.id.tv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_details:
                ivDetails.setVisibility(View.VISIBLE);
                tvDetails.setTextColor(Color.parseColor("#fc1a4e"));
                ivFriend.setVisibility(View.GONE);
                tvFriend.setTextColor(Color.parseColor("#999999"));
                webView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                break;
            case R.id.ll_friend:
                ivFriend.setVisibility(View.VISIBLE);
                tvFriend.setTextColor(Color.parseColor("#fc1a4e"));
                ivDetails.setVisibility(View.GONE);
                tvDetails.setTextColor(Color.parseColor("#999999"));
                webView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_share:
                if (StrUtils.isEmpty(bean.XCXUrl)) {
                    dialogUtils.ShareDialog(bean.GoodsName, bean.detailUrl, "快来一起薅羊毛吧~", bean.CoverImg);
                } else {
                    dialogUtils.ShareDialog(bean.GoodsName, bean.XCXUrl, "快来一起薅羊毛吧~", bean.CoverImg);
                }
                break;
        }
    }

    private long time1;
    public CountDownTimer countDownTimer;
    private WoolBean bean;
    private void fillView(WoolBean bean) {
        tvGoodsName.setText(bean.GoodsName);
        if(StrUtils.isEmpty(bean.NormsInfo)){
            bean.NormsInfo = "";
        }
        tvNumber1.setText(bean.NormsInfo + " " + bean.Unit + " x" + bean.Number);
        tvSalesPrice.setText("价格：" + bean.Cheap);
        tvBargainPrice.setText("已薅羊毛：" + bean.BargainPrice);
        tvHmoney.setText("最高可薅羊毛：" + StrUtils.moenyToDH((bean.SalesPrice - bean.Cheap)*0.7+""));
        ImageUtils.loadUrlImage(this,bean.CoverImg,ivProductLogo1);
        time1 = bean.CutDownTime * 60 * 60 * 1000;
        time1 -= 1000;
        countDownTimer = new CountDownTimer(time1, 1000) {
            public void onTick(long millisUntilFinished) {
                tvClock.setText("剩余时间：" + TimeUtils.formatTime(millisUntilFinished));
            }

            public void onFinish() {
                tvClock.setText("已结束");
            }
        }.start();
        imgs = Arrays.asList(bean.RollImg.split(","));
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgs);
        banner.start();
        WebSettings settings = webView.getSettings();

        //支持javascript
        settings.setJavaScriptEnabled(true);
// 设置可以支持缩放
        settings.setSupportZoom(true);
// 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
//扩大比例的缩放
        settings.setUseWideViewPort(true);
//自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);

//       String content = bean.Describe.replace("<img", "<img style=\"max-width:100%;height:auto\"");
//        webView.loadDataWithBaseURL(null,content, "text/html", "UTF-8",null);
        webView.loadData(getNewData(bean.Describe), "text/html", "UTF-8");
        adapter = new HymAdapter(this,R.layout.item_hym_layout,bean.userlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * webview图片适配
     * 设置img标签下的width为手机屏幕宽度，height自适应
     *
     * @param data html字符串
     * @return 更新宽高属性后的html字符串
     */
    private String getNewData(String data) {
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int w = outMetrics.widthPixels;
        Document document = Jsoup.parse(data);

        Elements pElements = document.select("p:has(img)");
        for (Element pElement : pElements) {
            pElement.attr("style", "text-align:center");
            pElement.attr("max-width", String.valueOf(w + "px"))
                    .attr("height", "auto");
        }
        Elements imgElements = document.select("img");
        for (Element imgElement : imgElements) {
            //重新设置宽高
            imgElement.attr("max-width", "100%")
                    .attr("height", "auto");
            imgElement.attr("style", "max-width:100%;height:auto");
        }
        return document.toString();
    }



    private Bitmap bitmap;
    public Bitmap returnBitMap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }

}
