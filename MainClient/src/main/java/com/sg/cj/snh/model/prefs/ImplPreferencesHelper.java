package com.sg.cj.snh.model.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.fdl.utils.Contans;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.constants.Constants;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 14:43
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class ImplPreferencesHelper implements PreferencesHelper {


  private static final String SHAREDPREFERENCES_NAME = "my_sp";


  private final SharedPreferences mSPrefs;

  @Inject
  public ImplPreferencesHelper() {
    mSPrefs = PartyApp.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
  }


  @Override
  public int getId() {
    return mSPrefs.getInt(Constants.SP_ID,0);

  }

  @Override
  public void setId(int Id) {
    mSPrefs.edit().putInt(Constants.SP_ID,Id).apply();

  }

  @Override
  public int getGradeId() {
    return mSPrefs.getInt(Constants.SP_GradeId,0);
  }

  @Override
  public void setGradeId(int GradeId) {

    mSPrefs.edit().putInt(Constants.SP_GradeId,GradeId).apply();
  }

  @Override
  public String getWxNickName() {
    return mSPrefs.getString(Constants.SP_WxNickName, "");
  }

  @Override
  public void setWxNickName(String WxNickName) {
    mSPrefs.edit().putString(Constants.SP_WxNickName, WxNickName).apply();

  }

  @Override
  public String getWxHeadImg() {
    return mSPrefs.getString(Constants.SP_WxHeadImg, "");
  }

  @Override
  public void setWxHeadImg(String WxHeadImg) {
    mSPrefs.edit().putString(Constants.SP_WxHeadImg, WxHeadImg).apply();

  }

  @Override
  public String getUnionID() {
    return mSPrefs.getString(Constants.SP_UnionID, "");
  }

  @Override
  public void setUnionID(String UnionID) {
    mSPrefs.edit().putString(Constants.SP_UnionID, UnionID).apply();

  }

  @Override
  public String getPayment() {
    return mSPrefs.getString(Constants.SP_Payment, "");
  }

  @Override
  public void setPayment(String Payment) {
    mSPrefs.edit().putString(Constants.SP_Payment, Payment).apply();

  }

  @Override
  public String getTel() {
    return mSPrefs.getString(Constants.SP_Tel, "");
  }

  @Override
  public void setTel(String Tel) {
    mSPrefs.edit().putString(Constants.SP_Tel, Tel).apply();

  }

  @Override
  public float getBalanceOne() {
    return mSPrefs.getFloat(Constants.SP_BalanceOne, 0);
  }

  @Override
  public void setBalanceOne(float BalanceOne) {
    mSPrefs.edit().putFloat(Constants.SP_BalanceOne, BalanceOne).apply();

  }

  @Override
  public float getIntegral() {
    return mSPrefs.getFloat(Constants.SP_Integral, 0);
  }

  @Override
  public void setIntegral(float Integral) {
    mSPrefs.edit().putFloat(Constants.SP_Integral, Integral).apply();

  }

  @Override
  public String getWxOpenId() {
    return mSPrefs.getString(Constants.SP_WxOpenId, "");
  }

  @Override
  public void setWxOpenId(String WxOpenId) {
    mSPrefs.edit().putString(Constants.SP_WxOpenId, WxOpenId).apply();

  }

  @Override
  public boolean getLaunchFirst() {
    return mSPrefs.getBoolean(Constants.SP_LAUNCH_FIRST, true);

  }

  @Override
  public void setLaunchFirst(boolean isFirst) {
    mSPrefs.edit().putBoolean(Constants.SP_LAUNCH_FIRST, isFirst).apply();

  }

  @Override
  public Set<String> getCookie() {
    return mSPrefs.getStringSet(Constants.SP_COOKIES, null);
  }

  @Override
  public void setCookie(Set<String> cookies) {
    mSPrefs.edit().putStringSet(Constants.SP_COOKIES, cookies).apply();
  }

  @Override
  public String getCookieString() {
    return mSPrefs.getString(Constants.SP_COOKIES_STRING, null);
  }

  @Override
  public void setCookieString(String cookies) {
    mSPrefs.edit().putString(Constants.SP_COOKIES_STRING, cookies).apply();

  }

  @Override
  public String getOpenid() {
    return mSPrefs.getString(Constants.OPENID,"");
  }

  @Override
  public void setOpenid(String openid) {
    mSPrefs.edit().putString(Constants.OPENID, openid).apply();

  }

  @Override
  public void cleanData() {
    mSPrefs.edit().clear().commit();
    mSPrefs.getBoolean(Constants.SP_LAUNCH_FIRST, true);  }
}
