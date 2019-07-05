package com.sg.cj.snh.uitls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Xml;

import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.SgUtils;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.bean.MainGridBean;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : chenjie
 * creat at 2018/10/29 16:36
 * @Description:
 */

public class MenuTools {

  private static MenuTools instance = null;
  private Context mContext;

  private MenuTools(Context context) {
    mContext = context;
  }

  public static MenuTools getInstance(Context context) {

    if (instance == null) {
      synchronized (MenuTools.class) {
        if (instance == null) {
          instance = new MenuTools(context);
        }
      }
    }
    return instance;
  }


  public List<MainGridBean> getAllList() {
    if (null == allList) {
      allList = getMenuList("main_all_menu.xml",
          MainGridBean.class);
    }
    return allList;
  }

  List<MainGridBean> allList = null;

  Map<String, MainGridBean> allMap = new HashMap<>();

  public Map<String, MainGridBean> getAllMap() {

    if (null == allList) {
      allList = getMenuList("main_all_menu.xml",
          MainGridBean.class);
    }

    if (allMap.size() == 0) {
      if (null != allList) {
        for (MainGridBean bean : allList) {
          allMap.put(bean.menuId, bean);
        }
      }

    }
    return allMap;
  }



  public List<MainGridBean> getTypeMenu(String type) {
    List<MainGridBean> news = new ArrayList<>();

    for(MainGridBean bean:allList){
      if(bean.type.equals(type)){
        news.add(bean);
      }
    }
    return news;
  }
  List<String> selectMenus = null;
//  public List<MainGridBean> getHomeMenu() {
//    //String menu = PartyApp.appComponent.getDataManager().getMainMenu();
//    String menu ="";
//    if (TextUtils.isEmpty(menu)) {
//      Resources resources = PartyApp.getInstance().getResources();
//      String[] arr = resources.getStringArray(R.array.home_default_menu);
//      selectMenus = Arrays.asList(arr);
//    } else {
//      Type type = new TypeToken<List<String>>() {
//      }.getType();
//      selectMenus = new Gson().fromJson(menu, type);
//    }
//    return getHomeMenu(selectMenus);
//  }

  private List<MainGridBean> getHomeMenu(List<String> menus) {
    List<MainGridBean> homemenu = new ArrayList<>();
    for (String id : menus) {
      MainGridBean bean=getAllMap().get(id);
      bean.isSelect=true;
      homemenu.add(bean);

    }

    return homemenu;
  }

  /**
   * 读取侧边栏列表项
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

  /**
   * Json数据泛型转化
   */
  public static <T> List<T> stringTolist(String jsonString, Class<T[]> type) {
    Gson gson = new Gson();
    T[] list = gson.fromJson(jsonString, type);
    List toList = Arrays.asList(list);
    return new ArrayList(toList);
  }


}
