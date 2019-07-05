package com.sg.cj.snh.ui.fragment.main;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.UrlUtils;
import com.sg.cj.common.base.adapter.ViewHolder;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseListFragment;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.findgoods.FindGoodsContract;
import com.sg.cj.snh.model.response.findgoods.FindGoodsResponse;
import com.sg.cj.snh.presenter.findgoods.FindGoodsPresenter;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * author : ${CHENJIE}
 * created at  2018/10/23 15:32
 * e_mail : chenjie_goodboy@163.com
 * describle : 发现
 */
public class DiscoverLayerFragment extends BaseListFragment<FindGoodsResponse.DataBean, FindGoodsPresenter> implements FindGoodsContract.View {


  @Override
  protected int getListItemLayout() {
    return R.layout.item_main_discover_list;
  }

  @Override
  protected void initEventAndData() {
    super.initEventAndData();
    listView.setDivider(null);
    setEnableLoadMore(true);
  }

  @Override
  protected void loadMore() {
    super.loadMore();
    mPresenter.doFindGoods(currentPage, Constants.LOAD_PAFE_SIZE);
  }

  @Override
  protected void requestData() {

    mPresenter.doFindGoods(currentPage, Constants.LOAD_PAFE_SIZE);
  }

  @Override
  public void displayFindGods(FindGoodsResponse response) {
    finishLoadMore();
    if (null == response) {
      return;
    }
    if (null == response.data) {
      return;
    }
    if (response.data.size() == 0) {
      setEnableLoadMore(false);
    }

    mData.addAll(response.data);
    notifyDataSetChanged();
  }

  @Override
  protected void convertView(ViewHolder helper, FindGoodsResponse.DataBean item, int position) {

    ImageView imgSupplier = helper.getConvertView().findViewById(R.id.img_Supplier);

    if (!TextUtils.isEmpty(item.SupplierImg)) {
      Glide.with(mContext).load(item.SupplierImg).into(imgSupplier);
    } else {
      imgSupplier.setImageDrawable(null);
    }

//    ImageView imgCover = helper.getConvertView().findViewById(R.id.img_Cover);
//
//    if (!TextUtils.isEmpty(item.CoverImg)) {
//      Glide.with(mContext).load(item.CoverImg).into(imgCover);
//    } else {
//      imgCover.setImageDrawable(null);
//    }

    helper.setText(R.id.txtSupplierName, item.SupplierName);
    helper.setText(R.id.txtfbTime, item.fbTime);
    helper.setText(R.id.txtText, item.Text);

    LinearLayout linearLayoutGoBuy = helper.getConvertView().findViewById(R.id.layout_go_buy);
    TextView tvShop = helper.getConvertView().findViewById(R.id.txtSupplierName);
    tvShop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("stroeId",UrlUtils.getParameters(item.StoreUrl).get("storeid"));
        JumpUtils.dataJump(getActivity(),ShopDetailsActivity.class,bundle,false);
      }
    });
    linearLayoutGoBuy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        startWebviewAct(item.GoodsUrl);
        Bundle bundle = new Bundle();
        bundle.putInt("goodsId",Integer.parseInt(UrlUtils.getParameters(item.GoodsUrl).get("GoodsId")));
        JumpUtils.dataJump(getActivity(),ProductDetailsActivity.class,bundle,false);
        //startActivity(new Intent(mContext,CategoryActivity.class));
      }
    });


    JCVideoPlayerStandard jcVideoPlayer = (JCVideoPlayerStandard)  helper.getConvertView().findViewById(R.id.videoplayer);

    jcVideoPlayer.setUp(
       item.Content, JCVideoPlayer.SCREEN_LAYOUT_NORMAL,
        "");




    if (!TextUtils.isEmpty(item.CoverImg)) {
      Glide.with(mContext).load(item.CoverImg).into(jcVideoPlayer.thumbImageView);
    } else {
      jcVideoPlayer.thumbImageView.setImageDrawable(null);
    }


  }


  @Override
  public void onPause() {
    super.onPause();
    JCVideoPlayer.releaseAllVideos();
  }

  @Override
  protected void onListItemClick(FindGoodsResponse.DataBean item, int position) {

  }

  @Override
  protected void initInject() {
    getFragmentComponent().inject(this);
  }
}

