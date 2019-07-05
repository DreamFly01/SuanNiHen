package com.fdl.db;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/3<p>
 * <p>changeTime：2019/4/3<p>
 * <p>version：1<p>
 */
public enum ShopTypeEnum {
//    美食分享(1),娱乐休闲(2),逛街吧(3),酒店公寓(4),地方名产(5),商超士多(6),家装建材(7),其它(8);
    TYPE1("美食分享",1),TYPE2("娱乐休闲",2),TYPE3("逛街吧",3),TYPE4("酒店公寓",4),TYPE5("地方名产",5),TYPE6("商超士多",6),TYPE7("家装建材",7),TYPE9("全国",9);

    private int value;
    private String name;

    ShopTypeEnum(String name, int value) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;

    }

    public String getName() {
        return name;
    }
}
