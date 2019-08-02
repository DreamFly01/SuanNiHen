package com.fdl.activity.main.redPacket.bean;

import java.util.List;

/**
 * @author 陈自强
 * @date 2019/8/1
 */
public class CouponsGoodsBean {


    /**
     * code : 01
     * data : {"list":[{"SellerId":245,"ShopName":"新生活","ShopLogo":"https://shop.snihen.com:8448/api/Source/472626","BusinessActivities":"1","ShopType":"6","SupplierId":394,"Address":"湖南省长沙市岳麓区","Longitude":"112.882247","Latitude":"28.23408","Evaluate":0,"Distance":31,"GoodsId":646038}],"count":1}
     * msg : 获取成功
     */

    public String code;
    public DataBean data;
    public String msg;

    public static class DataBean {
        /**
         * list : [{"SellerId":245,"ShopName":"新生活","ShopLogo":"https://shop.snihen.com:8448/api/Source/472626","BusinessActivities":"1","ShopType":"6","SupplierId":394,"Address":"湖南省长沙市岳麓区","Longitude":"112.882247","Latitude":"28.23408","Evaluate":0,"Distance":31,"GoodsId":646038}]
         * count : 1
         */

        public int count;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * SellerId : 245
             * ShopName : 新生活
             * ShopLogo : https://shop.snihen.com:8448/api/Source/472626
             * BusinessActivities : 1
             * ShopType : 6
             * SupplierId : 394
             * Address : 湖南省长沙市岳麓区
             * Longitude : 112.882247
             * Latitude : 28.23408
             * Evaluate : 0
             * Distance : 31.0
             * GoodsId : 646038
             */

            public int SellerId;
            public String ShopName;
            public String ShopLogo;
            public String BusinessActivities;
            public String ShopType;
            public int SupplierId;
            public String Address;
            public String Longitude;
            public String Latitude;
            public int Evaluate;
            public double Distance;
            public int GoodsId;
        }
    }
}
