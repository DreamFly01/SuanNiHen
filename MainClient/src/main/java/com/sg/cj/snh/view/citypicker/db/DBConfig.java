package com.sg.cj.snh.view.citypicker.db;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/18<p>
 * <p>changeTime：2019/1/18<p>
 * <p>version：1<p>
 */
public class DBConfig {
    public static final String DB_NAME_V1 = "china_cities.db";
    public static final String DB_NAME_V2 = "china_cities_v2.db";
    public static final String LATEST_DB_NAME = DB_NAME_V2;

    public static final String TABLE_NAME = "cities";

    public static final String COLUMN_C_NAME = "c_name";
    public static final String COLUMN_C_PROVINCE = "c_province";
    public static final String COLUMN_C_PINYIN = "c_pinyin";
    public static final String COLUMN_C_CODE = "c_code";
}
