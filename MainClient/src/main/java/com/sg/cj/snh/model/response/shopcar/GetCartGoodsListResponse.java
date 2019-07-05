package com.sg.cj.snh.model.response.shopcar;


import android.os.Parcel;
import android.os.Parcelable;

import com.sg.cj.snh.model.response.BaseResponse;

import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/12/6 11:36
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class GetCartGoodsListResponse extends BaseResponse implements Parcelable {

  /**
   * data : [{"goodsList":[{"Id":582,"Name":"2018婴童女童樱桃荷叶边宝宝长袖加绒连衣裙","SupplierId":47,"GoodsId":5649,"UserId":104,"Number":1,"GoodsImg":"http://rs.chankor.com/api/GetResources/453370","NormsInfo":"米白色,衣标73","NormsId":0,"SalesPrice":46,"DetailUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=582"}],"Id":47,"SupplierName":"Arbinin童装","SupplierLogo":null,"SupplierUrl":"http://shop.hnyunshang.com/Wx_User/Supplier/Index?storeid=47"}]
   */

  public List<DataBean> data;

  protected GetCartGoodsListResponse(Parcel in) {
    data = in.createTypedArrayList(DataBean.CREATOR);
  }

  public static final Creator<GetCartGoodsListResponse> CREATOR = new Creator<GetCartGoodsListResponse>() {
    @Override
    public GetCartGoodsListResponse createFromParcel(Parcel in) {
      return new GetCartGoodsListResponse(in);
    }

    @Override
    public GetCartGoodsListResponse[] newArray(int size) {
      return new GetCartGoodsListResponse[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(data);
  }

  public static class DataBean implements Parcelable {
    /**
     * goodsList : [{"Id":582,"Name":"2018婴童女童樱桃荷叶边宝宝长袖加绒连衣裙","SupplierId":47,"GoodsId":5649,"UserId":104,"Number":1,"GoodsImg":"http://rs.chankor.com/api/GetResources/453370","NormsInfo":"米白色,衣标73","NormsId":0,"SalesPrice":46,"DetailUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=582"}]
     * Id : 47
     * SupplierName : Arbinin童装
     * SupplierLogo : null
     * SupplierUrl : http://shop.hnyunshang.com/Wx_User/Supplier/Index?storeid=47
     */

    public int Id;
    public String SupplierName;
    public String SupplierLogo;
    public String SupplierUrl;
    public List<GoodsListBean> goodsList;
    public boolean select=false;

    protected DataBean(Parcel in) {
      Id = in.readInt();
      SupplierName = in.readString();
      SupplierLogo = in.readString();
      SupplierUrl = in.readString();
      goodsList = in.createTypedArrayList(GoodsListBean.CREATOR);
      select = in.readByte() != 0;
    }

    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
      @Override
      public DataBean createFromParcel(Parcel in) {
        return new DataBean(in);
      }

      @Override
      public DataBean[] newArray(int size) {
        return new DataBean[size];
      }
    };

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(Id);
      dest.writeString(SupplierName);
      dest.writeString(SupplierLogo);
      dest.writeString(SupplierUrl);
      dest.writeTypedList(goodsList);
      dest.writeByte((byte) (select ? 1 : 0));
    }

    public static class GoodsListBean implements Parcelable  {
      /**
       * Id : 582
       * Name : 2018婴童女童樱桃荷叶边宝宝长袖加绒连衣裙
       * SupplierId : 47
       * GoodsId : 5649
       * UserId : 104
       * Number : 1
       * GoodsImg : http://rs.chankor.com/api/GetResources/453370
       * NormsInfo : 米白色,衣标73
       * NormsId : 0
       * SalesPrice : 46.0
       * DetailUrl : http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=582
       */

      public int Id;
      public String Name;
      public int SupplierId;
      public int GoodsId;
      public int UserId;
      public int Number;
      public String GoodsImg;
      public String NormsInfo;
      public int NormsId;
      public double SalesPrice;
      public String DetailUrl;
      public boolean select=false;

      protected GoodsListBean(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        SupplierId = in.readInt();
        GoodsId = in.readInt();
        UserId = in.readInt();
        Number = in.readInt();
        GoodsImg = in.readString();
        NormsInfo = in.readString();
        NormsId = in.readInt();
        SalesPrice = in.readDouble();
        DetailUrl = in.readString();
        select = in.readByte() != 0;
      }

      public static final Creator<GoodsListBean> CREATOR = new Creator<GoodsListBean>() {
        @Override
        public GoodsListBean createFromParcel(Parcel in) {
          return new GoodsListBean(in);
        }

        @Override
        public GoodsListBean[] newArray(int size) {
          return new GoodsListBean[size];
        }
      };

      @Override
      public int describeContents() {
        return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeInt(SupplierId);
        dest.writeInt(GoodsId);
        dest.writeInt(UserId);
        dest.writeInt(Number);
        dest.writeString(GoodsImg);
        dest.writeString(NormsInfo);
        dest.writeInt(NormsId);
        dest.writeDouble(SalesPrice);
        dest.writeString(DetailUrl);
        dest.writeByte((byte) (select ? 1 : 0));
      }
    }
  }
}
