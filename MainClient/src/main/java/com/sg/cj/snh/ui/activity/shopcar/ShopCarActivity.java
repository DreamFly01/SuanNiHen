package com.sg.cj.snh.ui.activity.shopcar;


import com.google.gson.Gson;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sg.cj.common.base.adapter.SgQuickAdapter;
import com.sg.cj.common.base.adapter.ViewHolder;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.view.SgNoScrollListView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseListActivity;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.main.ShopCarContract;
import com.sg.cj.snh.model.request.shopcar.DeleteCartGoodsRequest;
import com.sg.cj.snh.model.response.shopcar.GetCartGoodsListResponse;
import com.sg.cj.snh.presenter.main.ShopCarPresenter;
import com.sg.cj.snh.view.NumberAddSubView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : ${CHENJIE}
 * created at  2018/12/6 20:54
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class ShopCarActivity extends BaseListActivity<GetCartGoodsListResponse.DataBean, ShopCarPresenter> implements ShopCarContract.View {


  private HeaderViewHolder headerViewHolder;
  private ButtomViewHolder buttomViewHolder;

  private List<Integer> cartids = new ArrayList<>();
  private Set<Integer> SupplierId=new HashSet<>();

  private boolean selectAll = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initTitle("购物车");
  }

  @Override
  protected int getListItemLayout() {
    return R.layout.item_shop_car;
  }

  @Override
  protected void initEventAndData() {
    super.initEventAndData();
    View buttomView = LayoutInflater.from(mContext).inflate(R.layout.view_shop_car_buttom, null);
    buttomViewHolder = new ButtomViewHolder(buttomView);
    layoutButtom.addView(buttomView);


    View headView = LayoutInflater.from(mContext).inflate(R.layout.view_shop_car_head, null);
    headerViewHolder = new HeaderViewHolder(headView);
    listView.addHeaderView(headView);

    listView.setDivider(new ColorDrawable(getResources().getColor(R.color.main_bg)));
    listView.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.y30));

  }


  class HeaderViewHolder {
    @BindView(R.id.txt_all)
    TextView txtAll;
    @BindView(R.id.layout_delete)
    LinearLayout layoutDelete;

    public HeaderViewHolder(View headerRootView) {
      ButterKnife.bind(this, headerRootView);
    }

    @OnClick({R.id.txt_all, R.id.layout_delete})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.txt_all:
          selectAll();
          break;
        case R.id.layout_delete:
          showDeleteDialog();
          break;
      }
    }

  }


  private void selectAll() {

    selectAll = !selectAll;
    for (GetCartGoodsListResponse.DataBean dataBean : mData) {
      dataBean.select=selectAll;
      for (GetCartGoodsListResponse.DataBean.GoodsListBean goodsListBean : dataBean.goodsList) {

        goodsListBean.select=selectAll;

      }
    }
    notifyDataSetChanged();
  }


  private String getSupplierId(){
    String supplierId = new Gson().toJson(SupplierId);
    if (TextUtils.isEmpty(supplierId)) {
      return "";
    }
    supplierId = supplierId.substring(1, supplierId.length() - 1);
    return supplierId;
  }

  private String getCarid(){
    String carid = new Gson().toJson(cartids);
    if (TextUtils.isEmpty(carid)) {
      return "";
    }
    carid = carid.substring(1, carid.length() - 1);
    return carid;
  }

  private void deleteGoods() {

    String carid=getCarid();
    SgLog.d("carid == " + carid);
    mPresenter.doDeleteCartGoods(new DeleteCartGoodsRequest(carid));
  }

  private void showDeleteDialog() {
    deleteGoods();

//    SgDialogUtils.showWarning(mContext, "您确定删除选中商品？", new SweetAlertDialog.OnSweetClickListener() {
//      @Override
//      public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//      }
//    });
  }

  class ButtomViewHolder {
    @BindView(R.id.txt_amt)
    TextView txtAmt;
    @BindView(R.id.txt_tran)
    TextView txtTran;
    @BindView(R.id.txt_go)
    TextView txtGo;

    public ButtomViewHolder(View buttomRootView) {
      ButterKnife.bind(this, buttomRootView);
    }

    @OnClick(R.id.txt_go)
    public void onViewClicked() {
      if(cartids.size()==0){
        return;
      }
      String url=Constants.QZF.replaceAll("@@",getCarid()).replaceAll("##",getSupplierId());
      startWebviewAct(url);
//      Bundle bundle = new Bundle();
////      GetCartGoodsListResponse.DataBean
////      bundle.putParcelable("bean");
//      bundle.putString("carId",getCarid());
//      bundle.putDouble("money",amtSum.doubleValue());
//      JumpUtils.dataJump(ShopCarActivity.this,OrderActivity.class,bundle,true);
    }
  }


  @Override
  protected void requestData() {

    mPresenter.doGetCartGoodsList(PartyApp.getAppComponent().getDataManager().getId());
  }


  @Override
  public void displayDeleteCartGoods() {
    requestData();
  }

  @Override
  public void displayUpdateGoodsNum() {
    requestData();
  }

  @Override
  public void displayGetCartGoodsList(GetCartGoodsListResponse response) {
    if (null == response) {
      return;
    }
    if (null == response.data) {

      return;
    }
    if(response.data.size()==0){
      if(currentPage==1)
      layoutEmpty.setVisibility(View.VISIBLE);
    }
    mData.clear();
    mData.addAll(response.data);
    notifyDataSetChanged();
  }

  private BigDecimal amtSum;

  private void refreshSelectAmt() {
    amtSum = new BigDecimal(0.0);
    cartids.clear();
    SupplierId.clear();
    selectAll = true;
    for (GetCartGoodsListResponse.DataBean dataBean : mData) {
      for (GetCartGoodsListResponse.DataBean.GoodsListBean goodsListBean : dataBean.goodsList) {
        if (goodsListBean.select) {
          cartids.add(goodsListBean.Id);
          SupplierId.add(goodsListBean.SupplierId);
          BigDecimal b2 = new BigDecimal(goodsListBean.SalesPrice);
          amtSum = amtSum.add(b2);
        } else {
          selectAll = false;
        }
      }
    }
    buttomViewHolder.txtAmt.setText(amtSum.doubleValue() + "");
  }

  @Override
  protected void convertView(ViewHolder helper, GetCartGoodsListResponse.DataBean item, int position) {
    helper.setText(R.id.txt_shop_title, item.SupplierName);

    ImageView shop_select = helper.getConvertView().findViewById(R.id.shop_select);

    helper.setOnClickListener(R.id.layout_shop, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startWebviewAct(item.SupplierUrl);
      }
    });

    if (item.select) {
      shop_select.setSelected(true);
    } else {
      shop_select.setSelected(false);
    }

    shop_select.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        item.select = !item.select;
        for (GetCartGoodsListResponse.DataBean.GoodsListBean goodsListBean : item.goodsList) {
          goodsListBean.select = item.select;
        }
        notifyDataSetChanged();
        refreshSelectAmt();
      }
    });

    if (item.goodsList != null && item.goodsList.size() > 0) {
      SgNoScrollListView noScrollListView = helper.getConvertView().findViewById(R.id.noScrollListView);
      noScrollListView.setAdapter(new SgQuickAdapter<GetCartGoodsListResponse.DataBean.GoodsListBean>(mContext, item.goodsList, R.layout.item_shop_car_product) {
        @Override
        public void convert(ViewHolder helper, GetCartGoodsListResponse.DataBean.GoodsListBean goodItem, int position) {

          ImageView product_select = helper.getConvertView().findViewById(R.id.product_select);


          product_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              goodItem.select = !goodItem.select;
              item.select = true;
              for (GetCartGoodsListResponse.DataBean.GoodsListBean goodsListBean : item.goodsList) {
                if (!goodsListBean.select) {
                  item.select = false;
                }
              }

              if (item.select) {
                shop_select.setSelected(true);
              } else {
                shop_select.setSelected(false);
              }
              notifyDataSetChanged();
              refreshSelectAmt();
            }
          });

          if (goodItem.select) {
            product_select.setSelected(true);
          } else {
            product_select.setSelected(false);
          }

          ImageView imgCover = helper.getConvertView().findViewById(R.id.img_product);

          if (!TextUtils.isEmpty(goodItem.GoodsImg)) {
            Glide.with(mContext).load(goodItem.GoodsImg).into(imgCover);
          } else {
            imgCover.setImageDrawable(null);
          }
          helper.setText(R.id.txt_product_title, goodItem.Name);
          helper.setText(R.id.txt_product_info, goodItem.NormsInfo);
          helper.setText(R.id.txt_price, "" + goodItem.SalesPrice);

          NumberAddSubView numberAddSubView = helper.getConvertView().findViewById(R.id.numberAddSubView);
          numberAddSubView.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
            @Override
            public void addNumber(View view, int value) {

              mPresenter.doUpdateGoodsNum(goodItem.Id, value);
            }

            @Override
            public void subNumner(View view, int value) {
              mPresenter.doUpdateGoodsNum(goodItem.Id, value);
            }
          });

          numberAddSubView.setValue(goodItem.Number);

        }
      });
    }
  }

  @Override
  protected void onListItemClick(GetCartGoodsListResponse.DataBean item, int position) {

  }

  @Override
  protected void initInject() {
    getActivityComponent().inject(this);
  }
}
