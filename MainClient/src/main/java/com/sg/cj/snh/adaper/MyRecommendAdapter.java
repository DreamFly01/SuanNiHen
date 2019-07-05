package com.sg.cj.snh.adaper;


import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sg.cj.snh.R;
import com.sg.cj.snh.model.response.self.GetDataResponse;

import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/11/28 21:19
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class MyRecommendAdapter extends BaseAdapter {
  private List<GetDataResponse.DataBean> Datas;
  private Context mContext;

  public MyRecommendAdapter(List<GetDataResponse.DataBean> datas, Context mContext) {
    Datas = datas;
    this.mContext = mContext;
  }

  /**
   * 返回item的个数
   * @return
   */
  @Override
  public int getCount() {
    return Datas.size();
  }

  /**
   * 返回每一个item对象
   * @param i
   * @return
   */
  @Override
  public Object getItem(int i) {
    return Datas.get(i);
  }

  /**
   * 返回每一个item的id
   * @param i
   * @return
   */
  @Override
  public long getItemId(int i) {
    return i;
  }

  /**
   * 暂时不做优化处理，后面会专门整理BaseAdapter的优化
   * @param i
   * @param view
   * @param viewGroup
   * @return
   */
  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder holder;
    if(view==null)
    {

      holder=new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.item_self_recommend_grid,viewGroup,false);

      holder.imageProduct=view.findViewById(R.id.img_product);
      holder.txtProduct=view.findViewById(R.id.txt_product);
      holder.txtOldPrice=view.findViewById(R.id.txt_old_price);
      holder.txtNewPrice=view.findViewById(R.id.txt_new_price);

      view.setTag(holder);
    }else {
      holder=(ViewHolder)view.getTag();
    }

    GetDataResponse.DataBean item=Datas.get(i);
    if (!TextUtils.isEmpty(item.CoverImgId)) {
      Glide.with(mContext).load(item.CoverImgId).into(holder.imageProduct);
    } else {
      holder.imageProduct.setImageDrawable(null);
    }

    holder.txtProduct.setText(item.Name);
    holder.txtNewPrice.setText("" + item.SalesPrice);
    holder.txtOldPrice.setText("" + item.MarketPrice);
    holder.txtOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    return view;
  }


  class ViewHolder{

    private ImageView imageProduct;
    private TextView txtProduct;
    private TextView txtNewPrice;
    private TextView txtOldPrice;
  }
}
