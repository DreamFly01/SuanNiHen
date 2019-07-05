package com.sg.cj.snh.uitls;


import com.google.gson.Gson;

import android.content.Context;

import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.SgUtils;
import com.sg.cj.snh.bean.CityBean;

import java.io.InputStream;
import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/12/5 23:07
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class CityUtil {



  private static CityUtil instance = null;
  private Context mContext;

  private CityUtil(Context context) {
    mContext = context;
  }




  public static CityUtil getInstance(Context context) {

    if (instance == null) {
      synchronized (CityUtil.class) {
        if (instance == null) {
          instance = new CityUtil(context);
        }
      }
    }
    return instance;
  }


  public CityBean getCity(String assetPath){
    try {
      InputStream is = mContext.getAssets().open(assetPath);
      if (is == null) {
        SgLog.d("菜单配置文件不存在。");
        return null;
      }
      String result = SgUtils.stream_2String(is, "UTF-8");
      CityBean beans = new Gson().fromJson(result,CityBean.class);
      return beans;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * 读取侧边栏列表项  "menu/ccb_drawer_menu.xml"
   */
  public <T> List<T> getMenuList(String assetPath, Class<T> beanClazz) {
    try {
      InputStream is = mContext.getAssets().open(assetPath);
      if (is == null) {
        SgLog.d("菜单配置文件不存在。");
        return null;
      }
      String result = SgUtils.stream_2String(is, "UTF-8");
      List<T> beans = XmlUtils.getListBean(result, beanClazz.getSimpleName(), beanClazz);
      return beans;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }


}
