package com.fdl.activity.main;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseFragment;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.adapter.DiscoverAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.DiscoverBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.UrlUtils;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/13<p>
 * <p>changeTime：2019/2/13<p>
 * <p>version：1<p>
 */
public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private DiscoverAdapter adapter;
    private JCVideoPlayerStandard currPlayer;

    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数
    private Bundle bundle;
    private int index = 1;
    private List<DiscoverBean> beans = new ArrayList<>();

    @Override
    public int initContentView() {
        return R.layout.fragment_discover_layout;
    }

    @Override
    public void setUpViews(View view) {
        heardBack.setVisibility(View.GONE);
        heardTitle.setText("发现");
        getDiscoverData();
        IsBang.setImmerHeard(getContext(), rlHead);
        setRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void setRecyclerView() {
        adapter = new DiscoverAdapter(R.layout.item_discover_layout, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_store:
                        bundle = new Bundle();
                        bundle.putInt("stroeId", Integer.parseInt(UrlUtils.getParameters(beans.get(position).StoreUrl).get("storeid")));
                        JumpUtils.dataJump(getActivity(), ShopDetailsActivity.class, bundle, false);
                        break;
                    case R.id.layout_go_buy:
                        bundle = new Bundle();
                        bundle.putInt("goodsId", Integer.parseInt(UrlUtils.getParameters(beans.get(position).GoodsUrl).get("GoodsId")));
                        JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
                        break;
                }
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index += 1;
                        getDiscoverData();
                    }
                }, 1000);

            }
        }, recyclerView);
    }

    private void getDiscoverData() {
        addSubscription(RequestClient.GetDiscover(index, getContext(), new NetSubscriber<BaseResultBean<List<DiscoverBean>>>(getContext()) {
            @Override
            public void onResultNext(BaseResultBean<List<DiscoverBean>> model) {
                avi.setVisibility(View.GONE);
                if (index == 1) {
                    if (model.data.size() > 0) {
                        beans = model.data;
                        adapter.setNewData(model.data);
                        adapter.loadMoreComplete();
                    }else {
                        adapter.setEmptyView(R.layout.empty_layout);
                    }
                } else {
                    if (model.data.size() > 0) {
                        adapter.addData(model.data);
                        adapter.loadMoreComplete();
                    } else {
                        adapter.loadMoreEnd();
                    }
                }
            }
        }));
    }


    @Override
    public void setUpLisener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case 1:
                        //滑动停止自动播放视频
                        autoPlayVideo(recyclerView);
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void autoPlayVideo(RecyclerView view) {

        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.videoplayer) != null) {
                currPlayer = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.videoplayer);
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
