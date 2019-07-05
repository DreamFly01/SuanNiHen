package com.fdl.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/24<p>
 * <p>changeTime：2018/10/24<p>
 * <p>version：1<p>
 */
public final class SPUtils {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static Context context;
    public static SPUtils instance;

    public SPUtils(Context context, String fileName) {
        this.context = context;
        sp = this.context.getSharedPreferences(fileName, 0);
        editor = sp.edit();
    }

    public static SPUtils getInstance(Context context, String fileName) {
        if (instance == null) {
            instance = new SPUtils(context, fileName);
        }
        return instance;
    }

    public static SPUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SPUtils(context, Contans.USER_INFO);
        }
        return instance;
    }




    public void saveData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public SPUtils saveData1(String key, String value) {
        editor.putString(key, value);
        return this;
    }
    public SPUtils saveFloat(String key,float value){
        editor.putFloat(key,value);
        return this;
    }
    public void commit() {
        editor.commit();
    }
    public SPUtils savaBoolean(String key,boolean flag){
        editor.putBoolean(key,flag);
        return this;
    }

    public String getString(String fileName) {
        return sp.getString(fileName, "");
    }

    public boolean getBoolean(String name) {
        return sp.getBoolean(name, false);
    }

    public void removeData(String name){
        editor = sp.edit();
        editor.remove(name);
        editor.commit();
    }
    /**
     * 保存List
     * @param datalist
     */
    public <T> void setDataList(List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.putString(Contans.LIST_BANNER_DATA, strJson);
        editor.commit();
    }

    /**
     * 获取List
     * @return
     */
    public <T> List<T> getDataList() {
        List<T> datalist=new ArrayList<T>();
        String strJson = sp.getString(Contans.LIST_BANNER_DATA, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;

    }
    public void cleanUserData(){
        editor = sp.edit();
        editor.remove(Contans.SP_COOKIES);
        editor.remove(Contans.SP_COOKIES_STRING);
        editor.remove(Contans.OPENID);
        editor.remove(Contans.SP_ID);
        editor.remove(Contans.SP_GradeId);
        editor.remove(Contans.SP_WxNickName);
        editor.remove(Contans.SP_WxHeadImg);
        editor.remove(Contans.SP_UnionID);
        editor.remove(Contans.SP_Payment);
        editor.remove(Contans.SP_Tel);
        editor.remove(Contans.SP_BalanceOne);
        editor.remove(Contans.SP_Integral);
        editor.remove(Contans.SP_WxOpenId);
        editor.commit();
    }
}
