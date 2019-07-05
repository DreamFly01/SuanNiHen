package com.sg.cj.snh.ui.fragment.main;


import com.sg.cj.common.base.adapter.ViewHolder;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseListFragment;
import com.sg.cj.snh.bean.MainGridBean;
import com.sg.cj.snh.contract.main.ShopCarContract;
import com.sg.cj.snh.model.response.findgoods.FindGoodsResponse;
import com.sg.cj.snh.model.response.shopcar.GetCartGoodsListResponse;
import com.sg.cj.snh.presenter.main.ShopCarPresenter;


/**
 * author : ${CHENJIE}
 * created at  2018/10/23 15:32
 * e_mail : chenjie_goodboy@163.com
 * describle : 购物车
 */
public class ShopCarLayerFragment extends BaseListFragment<FindGoodsResponse.DataBean, ShopCarPresenter> implements ShopCarContract.View {


  @Override
  protected int getListItemLayout() {
    return R.layout.item_main_discover_list;
  }

  @Override
  protected void requestData() {

    //mPresenter.doGetCartGoodsList(PartyApp.getAppComponent().getDataManager().getId());
  }

  @Override
  protected void convertView(ViewHolder helper, FindGoodsResponse.DataBean item, int position) {

  }

  @Override
  protected void onListItemClick(FindGoodsResponse.DataBean item, int position) {

  }

  @Override
  public void displayDeleteCartGoods() {

  }

  @Override
  public void displayUpdateGoodsNum() {

  }

  @Override
  public void displayGetCartGoodsList(GetCartGoodsListResponse response) {

  }

  @Override
  protected void initInject() {
    getFragmentComponent().inject(this);
  }
}
