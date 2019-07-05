package com.sg.cj.snh.ui.fragment.main;


import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fdl.activity.account.AddressActivity;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.account.AccountMoneyActivity;
import com.fdl.activity.account.BrowsHistoryListActivity;
import com.fdl.activity.account.ProductCollectListActivity;
import com.fdl.activity.account.ServerCallActivity;
import com.fdl.activity.account.ShopFollowListActivity;
import com.fdl.activity.order.MyOrderActivity;
import com.fdl.activity.order.MyRefundOrderActivity;
import com.fdl.activity.set.SetActivity;
import com.fdl.activity.wool.WoolActivity;
import com.fdl.utils.JumpUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.sg.cj.common.base.adapter.SgQuickAdapter;
import com.sg.cj.common.base.adapter.ViewHolder;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.view.SgHeaderGridView;
import com.sg.cj.common.base.view.SgNoScrollGridView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.adaper.MyRecommendAdapter;
import com.sg.cj.snh.base.BaseFragment;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.self.MainSelfFragmentContract;
import com.sg.cj.snh.model.response.self.GetDataResponse;
import com.sg.cj.snh.model.response.self.PersonInfoResponse;
import com.sg.cj.snh.presenter.self.MainSelfFragmentPresenter;
import com.sg.cj.snh.uitls.ImmersionOwner;
import com.sg.cj.snh.uitls.ImmersionProxy;
import com.sg.cj.snh.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : ${CHENJIE}
 * created at  2018/10/23 15:32
 * e_mail : chenjie_goodboy@163.com
 * describle : 我的
 */
public class SelfLayerFragment extends BaseFragment<MainSelfFragmentPresenter> implements MainSelfFragmentContract.View, ImmersionOwner {

    @BindView(R.id.grid_recommend)
    SgHeaderGridView gridRecommend;
    @BindView(R.id.pull_refresh)
    RefreshLayout refreshLayout;

    @BindView(R.id.txtTopName)
    TextView txtTopName;
    @BindView(R.id.imgBtnTopSet)
    ImageView imgBtnTopSet;
    @BindView(R.id.ll_self_top1)
    LinearLayout ll_self_top;


    private SgQuickAdapter<SelfToolBean> adapterTool;

    private List<SelfToolBean> listTool = new ArrayList<>();

    private MyRecommendAdapter myRecommendAdapter;

    private List<GetDataResponse.DataBean> listRecommend = new ArrayList<>();

    private HeaderViewHolder headerViewHolder;

    private String[] txtTools = new String[]{
            "薅羊毛", "收货地址", "我的卡包", "优惠券",
            "带你去旅行", "客服中心", "商家登录"
    };

    private String[] jumpUrl = new String[]{
            Constants.HYM, Constants.SHDZ, Constants.WDKB, Constants.YHQ,
            Constants.DNQLX, Constants.KF, Constants.SJDL
    };

    private int currentPage = 1;
    private String searchName = "";

    private int[] imgTools = new int[]{
            R.drawable.img_hym,
            R.drawable.img_shdz,
            R.drawable.img_wdkb,
            R.drawable.img_yhq,
            R.drawable.img_dnqlx,
            R.drawable.img_kfzx,
            R.drawable.img_sjdl

    };

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_main_self_scroll;
    }

    @Override
    protected void initEventAndData() {
        initRecomend();
        initToolGrid();

//    initToolBar();

        initInfo();
        initRefreshLayout();
        String imgHeadPath = PartyApp.getAppComponent().getDataManager().getWxHeadImg();
        StringBuffer str1 = new StringBuffer(imgHeadPath);
        str1.replace(0,1,"h");
        SgLog.d("imgHeadPath == " + imgHeadPath);
        System.out.println(imgHeadPath);
        if (!TextUtils.isEmpty(imgHeadPath)) {
//            ImageUtils.loadUrlCircleImage(mContext,imgHeadPath.trim(),headerViewHolder.imgHead);
            Glide.with(mContext).load(str1.toString()).into(headerViewHolder.imgHead);
        }

        mPresenter.doPersoninfo("" + PartyApp.getAppComponent().getDataManager().getId());

        mPresenter.doGetData(currentPage, Constants.LOAD_PAFE_SIZE, searchName);
        txtTopName.setAlpha(0);
    }


//  private void initToolBar(){
//
//
//
//    appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//      @Override
//      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        //垂直方向偏移量
//        int offset = Math.abs(verticalOffset);
//        //最大偏移距离
//        int scrollRange = appBarLayout.getTotalScrollRange();
//        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
////          toolbarOpen.setVisibility(View.VISIBLE);
////          toolbarClose.setVisibility(View.GONE);
//
//          bgToolbarOpen.setVisibility(View.VISIBLE);
//          bgToolbarClose.setVisibility(View.GONE);
//            ImmersionBar.with(getActivity()).titleBar(bgToolbarOpen).init();
//          //根据偏移百分比 计算透明值
//          float scale2 = (float) offset / (scrollRange / 2);
//          int alpha2 = (int) (255 * scale2);
//         // bgToolbarOpen.setBackgroundColor(Color.argb(alpha2, 253, 131, 8));
//
//          //bgToolbarOpen.setBackgroundColor(getResources().getColor(R.color.topbar_bg));
//        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
////          toolbarClose.setVisibility(View.VISIBLE);
////          toolbarOpen.setVisibility(View.GONE);
//          bgToolbarOpen.setVisibility(View.GONE);
//          bgToolbarClose.setVisibility(View.VISIBLE);
//          ImmersionBar.with(getActivity()).titleBar(bgToolbarClose).init();
//          float scale3 = (float) (scrollRange  - offset) / (scrollRange / 2);
//          int alpha3 = (int) (255 * scale3);
//         // bgToolbarOpen.setBackgroundColor(Color.argb(alpha3, 253, 131, 8));
//        }
//        //根据偏移百分比计算扫一扫布局的透明度值
//        float scale = (float) offset / scrollRange;
//        int alpha = (int) (255 * scale);
//
//
//       // bgContent.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
//
//
//
//      }
//    });
//  }

    private void initInfo() {

        headerViewHolder.txtNickName.setText(PartyApp.getAppComponent().getDataManager().getWxNickName());
        txtTopName.setText(PartyApp.getAppComponent().getDataManager().getWxNickName());

        imgBtnTopSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.simpJump(getActivity(), SetActivity.class, false);
            }
        });
    }

    private void initToolGrid() {
        int leng = Math.min(txtTools.length, imgTools.length);
        for (int i = 0; i < leng; i++) {
            SelfToolBean bean = new SelfToolBean(txtTools[i], imgTools[i], jumpUrl[i]);
            listTool.add(bean);
        }

        adapterTool = new SgQuickAdapter<SelfToolBean>(mContext, listTool, R.layout.item_self_tool_grid) {
            @Override
            public void convert(ViewHolder helper, SelfToolBean item, int position) {

                helper.setText(R.id.txt_tool, item.txt);
                helper.setImageResource(R.id.img_tool, item.image);
            }
        };
        headerViewHolder.gridTool.setAdapter(adapterTool);
        headerViewHolder.gridTool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {
                    JumpUtils.simpJump(getActivity(), AddressActivity.class, false);
                }else if(position == 0){
                    JumpUtils.simpJump(getActivity(),WoolActivity.class,false);
                } else if(position ==2){
                    JumpUtils.simpJump(getActivity(),AccountMoneyActivity.class,false);
                }else if(position == 5){
                    JumpUtils.simpJump(getActivity(),ServerCallActivity.class,false);
                }else {
                    startWebviewAct(jumpUrl[position]);
                }
            }
        });
    }

    private void initRecomend() {

        ViewCompat.setNestedScrollingEnabled(gridRecommend, true);


        View headView = LayoutInflater.from(mContext).inflate(R.layout.view_self_grid_head, null);
        headerViewHolder = new HeaderViewHolder(headView);
        gridRecommend.addHeaderView(headView);


        myRecommendAdapter = new MyRecommendAdapter(listRecommend, mContext);
        gridRecommend.setAdapter(myRecommendAdapter);
        gridRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String jumpUrl = listRecommend.get(position - 2).DetailUrl;
                if (TextUtils.isEmpty(jumpUrl)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("goodsId", listRecommend.get(position - 2).Id);
                JumpUtils.dataJump(getActivity(), ProductDetailsActivity.class, bundle, false);
            }
        });
        int[] loc = new int[2];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gridRecommend.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    headerViewHolder.ll_menu.getLocationOnScreen(loc);
                    SgLog.d(Math.abs(loc[1]) + "");
//            ll_self_top.getBackground().setAlpha((int) getAlphaFloat(Math.abs(loc[1])));
                    txtTopName.setAlpha((int) getAlphaFloat(Math.abs(loc[1])));
                }
            });
        }
    }

    private void initRefreshLayout() {

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                mPresenter.doGetData(currentPage, Constants.LOAD_PAFE_SIZE, searchName);
            }
        });
        setEnableLoadMore(true);
        setEnableRefresh(false);
    }


    protected void finishLoadMore() {
        refreshLayout.finishLoadMore();
    }

    /**
     * 设置允许加载更多
     */
    protected void setEnableLoadMore(boolean loadMore) {
        refreshLayout.setEnableLoadMore(loadMore);
    }

    /**
     * 设置允许下拉刷新
     */
    protected void setEnableRefresh(boolean refresh) {
        refreshLayout.setEnableRefresh(refresh);
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    public void displayGetData(GetDataResponse response) {
        finishLoadMore();
        if (response.CurrentPage == 1) {
            listRecommend.clear();
        }
        if (response.CurrentPage >= response.PageSize) {
            setEnableLoadMore(false);
        }
        if (null != response.data && response.data.size() > 0) {
            listRecommend.addAll(response.data);
            myRecommendAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void displayPersoninfo(PersonInfoResponse response) {

        if (response.caregoods > 0) {
            if (response.caregoods > 99) {
                headerViewHolder.txtProductCollection.setText("商品收藏(99+)");
            } else {
                headerViewHolder.txtProductCollection.setText("商品收藏(" + response.caregoods + ")");
            }
        }

        if (response.careshop > 0) {
            if (response.careshop > 99) {
                headerViewHolder.txtShopFocus.setText("店铺关注(99+)");
            } else {
                headerViewHolder.txtShopFocus.setText("店铺关注(" + response.careshop + ")");
            }
        }

        if (response.topay > 0) {
            if (response.topay > 99) {
                headerViewHolder.txtWaitPayNum.setText("99+");
            } else {
                headerViewHolder.txtWaitPayNum.setText("" + response.topay);
            }
            headerViewHolder.txtWaitPayNum.setVisibility(View.VISIBLE);
        }

        if (response.tosendproduct > 0) {
            if (response.tosendproduct > 99) {
                headerViewHolder.txtWaitSendNum.setText("99+");
            } else {
                headerViewHolder.txtWaitSendNum.setText("" + response.tosendproduct);
            }
            headerViewHolder.txtWaitSendNum.setVisibility(View.VISIBLE);
        }
        if (response.toreceiveproduct > 0) {
            if (response.toreceiveproduct > 99) {
                headerViewHolder.txtWaitShouNum.setText("99+");
            } else {

                headerViewHolder.txtWaitShouNum.setText("" + response.toreceiveproduct);
            }
            headerViewHolder.txtWaitShouNum.setVisibility(View.VISIBLE);
        }
        if (response.toreview > 0) {
            if (response.toreview > 99) {
                headerViewHolder.txtWaitCommentsNum.setText("99+");
            } else {
                headerViewHolder.txtWaitCommentsNum.setText("" + response.toreview);
            }
            headerViewHolder.txtWaitCommentsNum.setVisibility(View.VISIBLE);
        }
        headerViewHolder.txtLevel.setText("等级：v"+PartyApp.getAppComponent().getDataManager().getGradeId());
    }


    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
        initInfo();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(getActivity()).titleBar(R.id.ll_self_top1).init();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_self_top.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        ll_self_top.setLayoutParams(params);

        ll_self_top.setPadding(0, ImmersionBar.getActionBarHeight(getActivity()) / 2 + 10, 0, 20);
        SgLog.d("immersionbar:" + ImmersionBar.getActionBarHeight(getActivity()));
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    class SelfToolBean {

        public SelfToolBean(String txt, int image, String jumpUrl) {
            this.txt = txt;
            this.image = image;
            this.jumpUrl = jumpUrl;
        }

        public String txt;

        public int image;
        public String jumpUrl;
    }

    class HeaderViewHolder {
        @BindView(R.id.imgHead)
        CircleImageView imgHead;
        @BindView(R.id.txtNickName)
        TextView txtNickName;
        @BindView(R.id.txtLevel)
        TextView txtLevel;
        @BindView(R.id.imgBtnSet)
        ImageView imgBtnSet;
        @BindView(R.id.txt_product_collection)
        TextView txtProductCollection;
        @BindView(R.id.layout_product_collection)
        LinearLayout layoutProductCollection;
        @BindView(R.id.txt_shop_focus)
        TextView txtShopFocus;
        @BindView(R.id.layout_shop_focus)
        LinearLayout layoutShopFocus;
        @BindView(R.id.txt_scan_history)
        TextView txtScanHistory;
        @BindView(R.id.layout_scan_history)
        LinearLayout layoutScanHistory;
        @BindView(R.id.layout_my_more)
        LinearLayout layoutMyMore;
        @BindView(R.id.layout_wait_pay)
        LinearLayout layoutWaitPay;
        @BindView(R.id.txt_wait_pay_num)
        TextView txtWaitPayNum;
        @BindView(R.id.layout_wait_send)
        LinearLayout layoutWaitSend;
        @BindView(R.id.txt_wait_send_num)
        TextView txtWaitSendNum;
        @BindView(R.id.layout_wait_shou)
        LinearLayout layoutWaitShou;
        @BindView(R.id.txt_wait_shou_num)
        TextView txtWaitShouNum;
        @BindView(R.id.layout_wait_comments)
        LinearLayout layoutWaitComments;
        @BindView(R.id.txt_wait_comments_num)
        TextView txtWaitCommentsNum;
        @BindView(R.id.layout_refund)
        LinearLayout layoutRefund;
        @BindView(R.id.txt_refund_num)
        TextView txtRefundNum;
        @BindView(R.id.grid_tool)
        SgNoScrollGridView gridTool;

        @BindView(R.id.ll_menu)
        LinearLayout ll_menu;

        public HeaderViewHolder(View headerRootView) {
            ButterKnife.bind(this, headerRootView);
        }


        private Bundle bundle;
        @OnClick({R.id.imgHead, R.id.imgBtnSet, R.id.layout_product_collection, R.id.layout_shop_focus, R.id.layout_scan_history, R.id.layout_my_more, R.id.layout_wait_pay, R.id.layout_wait_send, R.id.layout_wait_shou, R.id.layout_wait_comments, R.id.layout_refund})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.imgHead:
                    break;
                case R.id.imgBtnSet:
//                    startWebviewAct(Constants.SETTING);
                    JumpUtils.simpJump(getActivity(), SetActivity.class, false);
                    break;
                case R.id.layout_product_collection:
//                    startWebviewAct(Constants.SPSC);
                    JumpUtils.simpJump(getActivity(),ProductCollectListActivity.class,false);
                    break;
                case R.id.layout_shop_focus:
//                    startWebviewAct(Constants.DPGZ);
                    JumpUtils.simpJump(getActivity(),ShopFollowListActivity.class,false);
                    break;
                case R.id.layout_scan_history:
//                    startWebviewAct(Constants.LSLL);
                    JumpUtils.simpJump(getActivity(),BrowsHistoryListActivity.class,false);
                    break;
                case R.id.layout_my_more:
                    bundle = new Bundle();
                    bundle.putInt("tabType",0);
                    JumpUtils.dataJump(getActivity(),MyOrderActivity.class,bundle,false);
//                    startWebviewAct(Constants.QBDD);
                    break;
                case R.id.layout_wait_pay:
                    bundle = new Bundle();
                    bundle.putInt("tabType",1);
                    JumpUtils.dataJump(getActivity(),MyOrderActivity.class,bundle,false);
//                    startWebviewAct(Constants.DFKDD);
                    break;
                case R.id.layout_wait_send:
                    bundle = new Bundle();
                    bundle.putInt("tabType",2);
                    JumpUtils.dataJump(getActivity(),MyOrderActivity.class,bundle,false);
//                    startWebviewAct(Constants.DFHDD);
                    break;
                case R.id.layout_wait_shou:
                    bundle = new Bundle();
                    bundle.putInt("tabType",3);
                    JumpUtils.dataJump(getActivity(),MyOrderActivity.class,bundle,false);
//                    startWebviewAct(Constants.DSHDD);
                    break;
                case R.id.layout_wait_comments:
                    bundle = new Bundle();
                    bundle.putInt("tabType",4);
                    JumpUtils.dataJump(getActivity(),MyOrderActivity.class,bundle,false);
//                    startWebviewAct(Constants.DPJDD);
                    break;
                case R.id.layout_refund:
                    JumpUtils.simpJump(getActivity(),MyRefundOrderActivity.class,false);
//                    startWebviewAct(Constants.TKSH);
                    break;
            }
        }

    }

    /**
     * 获取渐变透明值
     *
     * @param dis
     * @return
     */
    public float getAlphaFloat(int dis) {

        int step = 248;
        if (dis == 0) {
            return 0.0f;
        }

        if (dis < step) {
            return (float) (dis * (1.0 / step));
        } else {
            return 1.0f;
        }

    }

}
