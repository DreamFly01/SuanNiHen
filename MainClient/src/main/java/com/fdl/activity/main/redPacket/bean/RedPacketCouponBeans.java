package com.fdl.activity.main.redPacket.bean;

import java.util.List;

/**
 * @author 陈自强
 * @date 2019/7/31
 */
public class RedPacketCouponBeans {

    /**
     * code : 01
     * data : {"list":[{"SellerId":166,"ShopName":"龙飞","ShopLogo":"https://shop.snihen.com:8448/api/Source/472626","SupplierId":315,"Address":"湖南省长沙市岳麓区","Longitude":"112.882319","Latitude":"28.233933","Evaluate":0,"Distance":0,"GoodsId":627069},{"SellerId":245,"ShopName":"新生活","ShopLogo":"https://shop.snihen.com:8448/api/Source/472626","SupplierId":394,"Address":"湖南省长沙市岳麓区","Longitude":"112.882247","Latitude":"28.23408","Evaluate":0,"Distance":18,"GoodsId":646038}],"count":2}
     * count : 1
     * msg : 获取成功
     */

    public String code;
    public DataBean data;
    public int count;
    public String msg;

    public static class DataBean {
        /**
         * list : [{"SellerId":166,"ShopName":"龙飞","ShopLogo":"https://shop.snihen.com:8448/api/Source/472626","SupplierId":315,"Address":"湖南省长沙市岳麓区","Longitude":"112.882319","Latitude":"28.233933","Evaluate":0,"Distance":0,"GoodsId":627069},{"SellerId":245,"ShopName":"新生活","ShopLogo":"https://shop.snihen.com:8448/api/Source/472626","SupplierId":394,"Address":"湖南省长沙市岳麓区","Longitude":"112.882247","Latitude":"28.23408","Evaluate":0,"Distance":18,"GoodsId":646038}]
         * count : 2
         */

        public int count;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * SellerId : 166
             * ShopName : 龙飞
             * ShopLogo : https://shop.snihen.com:8448/api/Source/472626
             * SupplierId : 315
             * Address : 湖南省长沙市岳麓区
             * Longitude : 112.882319
             * Latitude : 28.233933
             * Evaluate : 0
             * Distance : 0.0
             * GoodsId : 627069
             */

            public int SellerId;
            public String ShopName;
            public String ShopLogo;
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
