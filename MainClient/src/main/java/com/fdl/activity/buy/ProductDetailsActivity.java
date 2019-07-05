package com.fdl.activity.buy;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fdl.BaseActivity;
import com.fdl.activity.main.MainActivity;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ProductDetailsBean;
import com.fdl.db.DBManager;
import com.fdl.db.ShopTypeEnum;
import com.fdl.requestApi.APIException;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.netease.nim.uikit.api.NimUIKit;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.sg.cj.snh.uitls.GlideImageLoader;
import com.wang.avi.AVLoadingIndicatorView;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/24<p>
 * <p>changeTime：2018/12/24<p>
 * <p>version：1<p>
 */

public class ProductDetailsActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_money)
    TextView tvMoney;//销售价格
    @BindView(R.id.tv_money1)
    TextView tvMoney1;//市场价格
    @BindView(R.id.ll_sz)
    LinearLayout llSz;//收藏按钮
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;//简介名字
    @BindView(R.id.iv_share)
    ImageView ivShare;//分享按钮
    @BindView(R.id.tv_yf)
    TextView tvYf;//运费
    @BindView(R.id.tv_yx)
    TextView tvYx;//月销
    @BindView(R.id.iv_store_logo)
    ImageView ivStoreLogo;//店铺logo
    @BindView(R.id.tv_store)
    TextView tvStore;//店铺名字
    @BindView(R.id.tv_zxl)
    TextView tvZxl;//总销量
    @BindView(R.id.tv_zsc)
    TextView tvZsc;//总收藏
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.ll_buy_car)
    LinearLayout llBuyCar;
    @BindView(R.id.tv_add_buy_car)
    TextView tvAddBuyCar;
    @BindView(R.id.tv_buy_away)
    TextView tvBuyAway;
    @BindView(R.id.ll_dp)
    LinearLayout llDp;
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.ll_buy_or_add)
    LinearLayout llBuyOrAdd;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    private int goodids;
    private Bundle bundle;
    private DialogUtils dialogUtils;

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.avi)
    RelativeLayout avLoadingIndicatorView;
    @BindView(R.id.iv_is_follow)
    ImageView isFollow;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_productdetails_layout);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            goodids = bundle.getInt("goodsId");
        }
        dialogUtils = new DialogUtils(this);
    }

    ImageView heardMenu;

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        TextView heardTitle = (TextView) findViewById(R.id.heard_title);
        heardMenu = (ImageView) findViewById(R.id.heard_menu);
        heardTitle.setText("商品详情");
        ivShare.setVisibility(View.GONE);

    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void getDataOnCreate() {

        addSubscription(RequestClient.GetProductDetails(goodids + "", this, new NetSubscriber<BaseResultBean<ProductDetailsBean>>(this) {
            @Override
            public void onResultNext(BaseResultBean<ProductDetailsBean> model) {
                bean = model.data;
                fillView(model.data);
                avLoadingIndicatorView.setVisibility(View.GONE);
            }

            @Override
            public void onResultErro(APIException erro) {
                super.onResultErro(erro);
                avLoadingIndicatorView.setVisibility(View.VISIBLE);
                avAvi.hide();
                llNoData.setVisibility(View.VISIBLE);
            }
        }));
    }

    private List<String> imgs = new ArrayList<>();
    ProductDetailsBean bean;
    private boolean isClick = false;

    private void fillView(ProductDetailsBean bean) {
        if (!StrUtils.isEmpty(bean.shop.ShopType) && bean.shop.ShopType.equals("6")) {
            llBuyOrAdd.setVisibility(View.GONE);
        } else {
            llBuyOrAdd.setVisibility(View.VISIBLE);
        }
        heardMenu.setVisibility(View.VISIBLE);
        if(bean.SalesPrice>=bean.MarketPrice){
            tvMoney1.setVisibility(View.INVISIBLE);
        }else {
            tvMoney1.setVisibility(View.VISIBLE);
        }
        tvMoney.setText("¥" + bean.SalesPrice + "");
        tvMoney1.setText("¥" + bean.MarketPrice + "");
        tvMoney1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvStoreName.setText(bean.Name);
        if (bean.ExpressMoney > 0) {
            tvYf.setText("运费：" + bean.ExpressMoney + "");
        } else {
            if (!StrUtils.isEmpty(bean.shop.ShopType) && bean.shop.ShopType.equals("6")) {
                tvYf.setText("到店自取");
            } else {
                tvYf.setText("运费：包邮");
            }
        }
        tvYx.setText("月销：" + bean.MonthSaleNumber + "");
        tvStore.setText(bean.shop.ShopName);
        ImageUtils.loadUrlCircleImage(this, bean.shop.ShopLogo, ivStoreLogo);
        imgs = Arrays.asList(bean.RollImages.split(","));
        tvZsc.setText(bean.FollowNum + "");
        tvZxl.setText(bean.MarketNumber + "");

        banner.setImageLoader(new com.fdl.utils.GlideImageLoader());
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
        if (!StrUtils.isEmpty(bean.Describe)) {

            webView.loadDataWithBaseURL(null, getNewData(bean.Describe), "text/html", "UTF-8", null);
        }
        isClick = bean.IsFollow;
        if (bean.IsFollow) {
            isFollow.setBackgroundResource(R.drawable.collection_selected);

        } else {
            isFollow.setBackgroundResource(R.drawable.collection_normal);
        }
    }

    @OnClick({R.id.heard_menu, R.id.iv_share, R.id.ll_sz, R.id.ll_dp, R.id.ll_buy_car, R.id.tv_add_buy_car, R.id.tv_buy_away, R.id.heard_back, R.id.ll_chat})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.heard_menu:
                if (StrUtils.isEmpty(bean.XCXUrl)) {
                    dialogUtils.ShareDialog(bean.Name, bean.DetailUrl, "我在算你狠发现一个不错的商品，赶快来看看吧~", bean.CoverImgId);
                } else {
                    dialogUtils.ShareDialog(bean.Name, bean.XCXUrl, "我在算你狠发现一个不错的商品，赶快来看看吧~", bean.CoverImgId);
                }
                break;
            case R.id.iv_share:
//                showShare();
//                dialogUtils.ShareDialog(bean.Name, bean.DetailUrl, "我在算你狠发现一个不错的商品，赶快来看看吧~", bean.CoverImgId);
                break;
            case R.id.ll_sz:
                if (!isLogin()) {
                    jumpActivity(LoginActivity.class);
                } else {
                    isFollow();
                }
                break;
            case R.id.ll_dp:
                bundle = new Bundle();
                bundle.putInt("stroeId", bean.shop.Id);
                if((ShopTypeEnum.TYPE3.getValue()+"").equals(bean.shop.ShopType)|(ShopTypeEnum.TYPE5.getValue()+"").equals(bean.shop.ShopType)|(ShopTypeEnum.TYPE7.getValue()+"").equals(bean.shop.ShopType)|(ShopTypeEnum.TYPE9.getValue()+"").equals(bean.shop.ShopType)){
                    JumpUtils.dataJump(this, ShopDetailsActivity.class, bundle, false);
                }else if((ShopTypeEnum.TYPE6.getValue()+"").equals(bean.shop.ShopType)){
                    JumpUtils.dataJump(this, StoreDetailsActivity.class, bundle, false);
                }else {
                    JumpUtils.dataJump(this, ShopDetailsActivity.class, bundle, false);
                }

                break;
            case R.id.ll_buy_car:
                if (!isLogin()) {
                    jumpActivity(LoginActivity.class);
                } else {
                    bundle = new Bundle();
                    bundle.putInt("show_fragment_index", 2);
                    JumpUtils.dataJump(ProductDetailsActivity.this, MainActivity.class, bundle, true);
                }
                break;
            case R.id.tv_add_buy_car:
                if (!isLogin()) {
                    jumpActivity(LoginActivity.class);
                } else {
                    buyOrAddDialog(1);
                }
                break;
            case R.id.tv_buy_away:
                if (!isLogin()) {
                    jumpActivity(LoginActivity.class);
                } else {
                    buyOrAddDialog(2);
                }
                break;
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_chat:
                if (isLogin()) {
                    if (StrUtils.isEmpty(SPUtils.getInstance(this).getString(Contans.IM_ACCOUNT))) {
                        Toast.makeText(ProductDetailsActivity.this, "通讯异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        NimUIKit.startP2PSession(this, "supp_" + bean.SupplierId);
                    }
                } else {
                    jumpActivity(LoginActivity.class);
                }
                break;
        }
    }

    //点击关注
    private void isFollow() {
        addSubscription(RequestClient.IsFullow(goodids, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                if (isClick) {
                    isFollow.setBackgroundResource(R.drawable.collection_normal);
                    isClick = false;
                    showShortToast("取消成功");
                } else {
                    isClick = true;
                    isFollow.setBackgroundResource(R.drawable.collection_selected);
                    showShortToast("收藏成功");
                }
                EventBus.getDefault().post("collect");
            }
        }));
    }

    //加入购物车或者立即购买
    private void buyOrAddDialog(int flag) {

        dialogUtils.SukDilogButtom(flag, goodids, new DialogUtils.ConfirmClickLisener() {
            @Override
            public void onConfirmClick(View v) {
                dialogUtils.dismissDialog();

            }
        }, bean).outSideIsDismiss(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
