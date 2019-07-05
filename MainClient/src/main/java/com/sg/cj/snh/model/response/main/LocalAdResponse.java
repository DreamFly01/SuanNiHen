package com.sg.cj.snh.model.response.main;


import com.sg.cj.snh.model.response.BaseResponse;

import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/12/1 16:38
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class LocalAdResponse extends BaseResponse {

  /**
   * data : {"banner":[{"Id":52,"AdminId":0,"Title":"劲酒","Href":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5057&PUid=440&from=singlemessage","ImageUrl":"http://rs.chankor.com/api/GetResources/436306","OrderBy":0,"Default":true,"Audit":false},{"Id":47,"AdminId":0,"Title":"女","Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=92","ImageUrl":"http://rs.chankor.com/api/GetResources/422204","OrderBy":3,"Default":true,"Audit":false},{"Id":46,"AdminId":0,"Title":"鞋","Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=53&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/422166","OrderBy":4,"Default":true,"Audit":false},{"Id":49,"AdminId":0,"Title":"床","Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=56&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/422206","OrderBy":5,"Default":true,"Audit":false}],"fours":[{"Id":12,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=4944&PUid=129&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/441859","OrderBy":0,"Default":true,"Audit":false},{"Id":13,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=72&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/423739","OrderBy":0,"Default":true,"Audit":false},{"Id":14,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=10","ImageUrl":"http://rs.chankor.com/api/GetResources/442256","OrderBy":0,"Default":true,"Audit":false},{"Id":15,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5233&PUid=129&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/442028","OrderBy":0,"Default":true,"Audit":false}],"localsub":[{"Id":35,"AdminId":23,"Front":"332447","Title":"西红柿鸡蛋汤哪种做法更营养","Defaults":5,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=35","CreateTime":"2018-06-29 11:29:04"},{"Id":36,"AdminId":23,"Front":"332448","Title":"运动饮料可以调节人体电解质","Defaults":5,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=36","CreateTime":"2018-06-29 11:38:04"},{"Id":37,"AdminId":23,"Front":"332461","Title":"用什么洗脸洁面的效果才最好","Defaults":4,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=37","CreateTime":"2018-07-13 10:48:06"},{"Id":38,"AdminId":23,"Front":"332464","Title":"掌握6个关键轻松祛痘印","Defaults":4,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=38","CreateTime":"2018-07-13 10:50:21"},{"Id":39,"AdminId":0,"Front":"367488","Title":"低碳达人的家电使用妙招","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=39","CreateTime":"2018-08-17 17:30:28"},{"Id":40,"AdminId":0,"Front":"367523","Title":"衣食住行低碳生活小技巧","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=40","CreateTime":"2018-08-17 17:36:38"},{"Id":41,"AdminId":0,"Front":"367538","Title":"开学学生和家长的低碳生活","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=41","CreateTime":"2018-08-17 17:42:30"},{"Id":42,"AdminId":0,"Front":"367587","Title":"购物也能低碳生活的技巧","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=42","CreateTime":"2018-08-17 17:47:41"},{"Id":43,"AdminId":0,"Front":"367588","Title":"厨房装修中节约的技巧","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=43","CreateTime":"2018-08-17 17:49:37"},{"Id":44,"AdminId":0,"Front":"367589","Title":"洗衣机这么用最节能！","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=44","CreateTime":"2018-08-17 17:53:05"},{"Id":45,"AdminId":0,"Front":"367595","Title":"夏天吹空调一定要注意五个问题","Defaults":3,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=45","CreateTime":"2018-08-17 17:58:45"}]}
   */

  public DataBean data;

  public static class DataBean {
    /**
     * banner : [{"Id":52,"AdminId":0,"Title":"劲酒","Href":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5057&PUid=440&from=singlemessage","ImageUrl":"http://rs.chankor.com/api/GetResources/436306","OrderBy":0,"Default":true,"Audit":false},{"Id":47,"AdminId":0,"Title":"女","Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=92","ImageUrl":"http://rs.chankor.com/api/GetResources/422204","OrderBy":3,"Default":true,"Audit":false},{"Id":46,"AdminId":0,"Title":"鞋","Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=53&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/422166","OrderBy":4,"Default":true,"Audit":false},{"Id":49,"AdminId":0,"Title":"床","Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=56&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/422206","OrderBy":5,"Default":true,"Audit":false}]
     * fours : [{"Id":12,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=4944&PUid=129&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/441859","OrderBy":0,"Default":true,"Audit":false},{"Id":13,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=72&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/423739","OrderBy":0,"Default":true,"Audit":false},{"Id":14,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/Supplier?storeid=10","ImageUrl":"http://rs.chankor.com/api/GetResources/442256","OrderBy":0,"Default":true,"Audit":false},{"Id":15,"AdminId":0,"Href":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5233&PUid=129&from=singlemessage&isappinstalled=0","ImageUrl":"http://rs.chankor.com/api/GetResources/442028","OrderBy":0,"Default":true,"Audit":false}]
     * localsub : [{"Id":35,"AdminId":23,"Front":"332447","Title":"西红柿鸡蛋汤哪种做法更营养","Defaults":5,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=35","CreateTime":"2018-06-29 11:29:04"},{"Id":36,"AdminId":23,"Front":"332448","Title":"运动饮料可以调节人体电解质","Defaults":5,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=36","CreateTime":"2018-06-29 11:38:04"},{"Id":37,"AdminId":23,"Front":"332461","Title":"用什么洗脸洁面的效果才最好","Defaults":4,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=37","CreateTime":"2018-07-13 10:48:06"},{"Id":38,"AdminId":23,"Front":"332464","Title":"掌握6个关键轻松祛痘印","Defaults":4,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=38","CreateTime":"2018-07-13 10:50:21"},{"Id":39,"AdminId":0,"Front":"367488","Title":"低碳达人的家电使用妙招","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=39","CreateTime":"2018-08-17 17:30:28"},{"Id":40,"AdminId":0,"Front":"367523","Title":"衣食住行低碳生活小技巧","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=40","CreateTime":"2018-08-17 17:36:38"},{"Id":41,"AdminId":0,"Front":"367538","Title":"开学学生和家长的低碳生活","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=41","CreateTime":"2018-08-17 17:42:30"},{"Id":42,"AdminId":0,"Front":"367587","Title":"购物也能低碳生活的技巧","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=42","CreateTime":"2018-08-17 17:47:41"},{"Id":43,"AdminId":0,"Front":"367588","Title":"厨房装修中节约的技巧","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=43","CreateTime":"2018-08-17 17:49:37"},{"Id":44,"AdminId":0,"Front":"367589","Title":"洗衣机这么用最节能！","Defaults":2,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=44","CreateTime":"2018-08-17 17:53:05"},{"Id":45,"AdminId":0,"Front":"367595","Title":"夏天吹空调一定要注意五个问题","Defaults":3,"Audit":false,"localUrl":"http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=45","CreateTime":"2018-08-17 17:58:45"}]
     */

    public List<BannerBean> banner;
    public List<FoursBean> fours;
    public List<LocalsubBean> localsub;

    public static class BannerBean {
      /**
       * Id : 52
       * AdminId : 0
       * Title : 劲酒
       * Href : http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5057&PUid=440&from=singlemessage
       * ImageUrl : http://rs.chankor.com/api/GetResources/436306
       * OrderBy : 0
       * Default : true
       * Audit : false
       */

      public int Id;
      public int AdminId;
      public String Title;
      public String Href;
      public String ImageUrl;
      public int OrderBy;
      public boolean Default;
      public boolean Audit;
    }

    public static class FoursBean {
      /**
       * Id : 12
       * AdminId : 0
       * Href : http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=4944&PUid=129&from=singlemessage&isappinstalled=0
       * ImageUrl : http://rs.chankor.com/api/GetResources/441859
       * OrderBy : 0
       * Default : true
       * Audit : false
       */

      public int Id;
      public int AdminId;
      public String Href;
      public String ImageUrl;
      public int OrderBy;
      public boolean Default;
      public boolean Audit;
    }

    public static class LocalsubBean {
      /**
       * Id : 35
       * AdminId : 23
       * Front : 332447
       * Title : 西红柿鸡蛋汤哪种做法更营养
       * Defaults : 5
       * Audit : false
       * localUrl : http://shop.hnyunshang.com/Wx_User/LocalPage/lcoalsubmission?Id=35
       * CreateTime : 2018-06-29 11:29:04
       */

      public int Id;
      public int AdminId;
      public String Front;
      public String Title;
      public int Defaults;
      public boolean Audit;
      public String localUrl;
      public String CreateTime;
    }
  }
}
