package com.sg.cj.snh.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : ${CHENJIE}
 * created at  2018/11/30 21:45
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class RightBean implements Parcelable {
  private  int Id;
  private  int ParentId;
  private  int Depth;
  private  String Title;
  private  String ImageUrl;
  private String tag;
  private  int OrderBy;
  private  String linkUrl;

  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public int getParentId() {
    return ParentId;
  }

  public void setParentId(int parentId) {
    ParentId = parentId;
  }

  public int getDepth() {
    return Depth;
  }

  public void setDepth(int depth) {
    Depth = depth;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String getImageUrl() {
    return ImageUrl;
  }

  public void setImageUrl(String imageUrl) {
    ImageUrl = imageUrl;
  }

  public int getOrderBy() {
    return OrderBy;
  }

  public void setOrderBy(int orderBy) {
    OrderBy = orderBy;
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }
  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.Id);
    dest.writeInt(this.ParentId);
    dest.writeInt(this.Depth);
    dest.writeString(this.Title);
    dest.writeString(this.ImageUrl);
    dest.writeInt(this.OrderBy);
    dest.writeString(this.linkUrl);
    dest.writeString(tag);
  }

  public RightBean() {
  }

  public RightBean(Parcel in) {
    this.Id = in.readInt();
    this.ParentId = in.readInt();
    this.Depth = in.readInt();
    this.Title = in.readString();
    this.ImageUrl = in.readString();
    this.OrderBy = in.readInt();
    this.linkUrl = in.readString();
    this.tag = in.readString();
  }

  public static final Parcelable.Creator<RightBean> CREATOR = new Parcelable.Creator<RightBean>() {
    public RightBean createFromParcel(Parcel source) {
      return new RightBean(source);
    }

    public RightBean[] newArray(int size) {
      return new RightBean[size];
    }
  };
}
