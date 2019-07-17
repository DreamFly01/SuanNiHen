package com.fdl.activity.supermarket;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.adapter.SuperMarkAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.SuperMarkGoodsBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.MyWebView;
import com.fdl.wedgit.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sg.cj.snh.R;
import com.youth.banner.Banner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/1<p>
 * <p>changeTime：2019/4/1<p>
 * <p>version：1<p>
 */
public class SupermarkDialogFragment extends DialogFragment {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private LinearLayout view;
    private LinearLayout heard;
    private Banner banner;
    private MyWebView webView;
    private TextView goodsName, num;

    private int index = 1;
    private Long goodsId;
    private SuperMarkAdapter adapter;
    private SuperMarkGoodsBean data;

    private boolean isShow = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = (LinearLayout) inflater.inflate(R.layout.dialog_goodsdetails_layout, container, false);
        heard = (LinearLayout) inflater.inflate(R.layout.heard_supmark_layout, null);
        banner = heard.findViewById(R.id.banner);
        webView = heard.findViewById(R.id.web_view);
        goodsName = heard.findViewById(R.id.tv_goodsName);
        num = heard.findViewById(R.id.tv_num);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.height = (int) (display.getHeight() * 0.8);
        getDialog().getWindow().setAttributes(layoutParams);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setContentView(view);
        getDialog().getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goodsId = getArguments().getLong("id");
        setView();
        getData();

    }

    private void setView() {
        adapter = new SuperMarkAdapter(R.layout.item_supermart_commont_layout, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(),LinearLayout.VERTICAL,R.drawable.line));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(heard);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isShow = false;
                        index++;
                        getData();
                    }
                }, 1000);
            }
        }, recyclerView);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isShow = true;
                index = 1;
                getData();
            }
        });
    }

    private List<String> imgs = new ArrayList<>();

    private void fillView(SuperMarkGoodsBean bean) {
        banner.setImageLoader(new com.fdl.utils.GlideImageLoader());
        imgs.clear();
        if (bean.RollImages != null && bean.RollImages.length > 0) {
            for (int i = 0; i < bean.RollImages.length; i++) {
                imgs.add(bean.RollImages[i]);
            }
        }else {
            imgs.add(bean.CoverImgId);
        }
        banner.setImages(imgs);
        banner.start();
        goodsName.setText(bean.GoodsName);
        num.setText("("+bean.CommentCount+")");
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        getDialog().dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        this.onDestroyView();
    }

    private void getData() {
        RequestClient.GetGoodsDetail(goodsId, index, getContext(), new NetSubscriber<BaseResultBean<SuperMarkGoodsBean>>(getContext(), isShow) {
            @Override
            public void onResultNext(BaseResultBean<SuperMarkGoodsBean> model) {
                data = model.data;
                fillView(data);
                if (index == 1) {
                    if (model.data.Comment.size() > 0) {
                        adapter.setNewData(model.data.Comment);
                        adapter.loadMoreComplete();
                    } else {
                        adapter.loadMoreEnd();
                    }
                } else {
                    if (model.data.Comment.size() > 0) {
                        adapter.addData(model.data.Comment);
                        adapter.loadMoreComplete();
                    } else {
                        adapter.loadMoreEnd();
                    }
                }
            }
        });
    }

    private String getNewData(String data) {
        WindowManager windowManager = getActivity().getWindowManager();
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
}
