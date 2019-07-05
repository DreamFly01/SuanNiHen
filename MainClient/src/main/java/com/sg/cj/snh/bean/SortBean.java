package com.sg.cj.snh.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/11/30 21:21
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SortBean implements Parcelable {
  private ArrayList<CategoryOneArrayBean> categoryOneArray=new ArrayList<>();

  public ArrayList<CategoryOneArrayBean> getCategoryOneArray() {
    return categoryOneArray;
  }

  public void setCategoryOneArray(ArrayList<CategoryOneArrayBean> categoryOneArray) {
    this.categoryOneArray = categoryOneArray;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeList(this.categoryOneArray);
  }

  public SortBean() {
  }

  protected SortBean(Parcel in) {
    this.categoryOneArray = new ArrayList<CategoryOneArrayBean>();
    in.readList(this.categoryOneArray, List.class.getClassLoader());
  }

  public static final Parcelable.Creator<SortBean> CREATOR = new Parcelable.Creator<SortBean>() {
    @Override
    public SortBean createFromParcel(Parcel source) {

      return new SortBean(source);
    }

    @Override
    public SortBean[] newArray(int size) {
      return new SortBean[size];
    }
  };



  public static class CategoryOneArrayBean implements Parcelable {



    private  int Id;
    private  int ParentId;
    private  int Depth;
    private  String Title;
    private  String ImageUrl;
    private  int OrderBy;
    private  String linkUrl;

    private List<CategoryTwoArrayBean> categoryTwoArray=new ArrayList<>();


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

    public List<CategoryTwoArrayBean> getCategoryTwoArray() {
      return categoryTwoArray;
    }

    public void setCategoryTwoArray(List<CategoryTwoArrayBean> categoryTwoArray) {
      this.categoryTwoArray = categoryTwoArray;
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
      dest.writeList(this.categoryTwoArray);
    }

    public CategoryOneArrayBean() {
    }

    protected CategoryOneArrayBean(Parcel in) {
      this.Id = in.readInt();
      this.ParentId = in.readInt();
      this.Depth = in.readInt();
      this.Title = in.readString();
      this.ImageUrl = in.readString();
      this.OrderBy = in.readInt();
      this.linkUrl = in.readString();
      this.categoryTwoArray = new ArrayList<CategoryTwoArrayBean>();
      in.readList(this.categoryTwoArray, List.class.getClassLoader());
    }

    public static final Creator<CategoryOneArrayBean> CREATOR = new Creator<CategoryOneArrayBean>() {
     @Override
      public CategoryOneArrayBean createFromParcel(Parcel source) {
        return new CategoryOneArrayBean(source);
      }
      @Override
      public CategoryOneArrayBean[] newArray(int size) {
        return new CategoryOneArrayBean[size];
      }
    };


    public static class CategoryTwoArrayBean implements Parcelable {

      private  int Id;
      private  int ParentId;
      private  int Depth;
      private  String Title;
      private  String ImageUrl;
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
      }

      public CategoryTwoArrayBean() {
      }

      protected CategoryTwoArrayBean(Parcel in) {
        this.Id = in.readInt();
        this.ParentId = in.readInt();
        this.Depth = in.readInt();
        this.Title = in.readString();
        this.ImageUrl = in.readString();
        this.OrderBy = in.readInt();
        this.linkUrl = in.readString();
      }

      public static final Creator<CategoryTwoArrayBean> CREATOR = new Creator<CategoryTwoArrayBean>() {
        public CategoryTwoArrayBean createFromParcel(Parcel source) {
          return new CategoryTwoArrayBean(source);
        }

        public CategoryTwoArrayBean[] newArray(int size) {
          return new CategoryTwoArrayBean[size];
        }
      };
    }



  }


}
