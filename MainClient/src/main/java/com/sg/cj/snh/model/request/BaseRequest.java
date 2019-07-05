package com.sg.cj.snh.model.request;


import com.google.gson.Gson;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author : ${CHENJIE}
 * created at  2018/10/28 21:53
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class BaseRequest {


  /**
   * 将实体类转换成请求参数,json字符串形式返回
   *
   * @return
   */
  public String getJsonParams() {
    String jsonStr = new Gson().toJson(this);
    if (TextUtils.isEmpty(jsonStr)) {
      jsonStr = "";
    }

    return jsonStr;
  }


  public RequestBody getRequestBody(HashMap<String, String> hashMap) {
    StringBuffer data = new StringBuffer();
    if (hashMap != null && hashMap.size() > 0) {
      Iterator iter = hashMap.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry entry = (Map.Entry) iter.next();
        Object key = entry.getKey();
        Object val = entry.getValue();
        data.append(key).append("=").append(val).append("&");
      }
    }
    String jso = data.substring(0, data.length() - 1);
    RequestBody requestBody =
        RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),jso);

    return requestBody;
  }


  public RequestBody getRequestBody() {
    StringBuffer data = new StringBuffer();




    Class<? extends BaseRequest> clazz = this.getClass();
    Class<? extends Object> superclass = clazz.getSuperclass();

    Field[] fields = clazz.getDeclaredFields();
    Field[] superFields = superclass.getDeclaredFields();




    try {
      for (Field field : fields) {
        field.setAccessible(true);


        data.append(field.getName()).append("=").append(String.valueOf(field.get(this))).append("&");

      }

      for (Field superField : superFields) {
        superField.setAccessible(true);
        data.append(superField.getName()).append("=").append(String.valueOf(superField.get(this))).append("&");

      }

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }




    String jso = data.substring(0, data.length() - 1);
    RequestBody requestBody =
        RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),jso);

    return requestBody;
  }


  /**
   * 将实体类转换成请求参数,以map<k,v>形式返回
   *
   * @return
   */
  public Map<String, String> getMapParams() {
    Class<? extends BaseRequest> clazz = this.getClass();
    Class<? extends Object> superclass = clazz.getSuperclass();

    Field[] fields = clazz.getDeclaredFields();
    Field[] superFields = superclass.getDeclaredFields();

    if (fields == null || fields.length == 0) {
      return Collections.emptyMap();
    }

    Map<String, String> params = new HashMap<String, String>();
    try {
      for (Field field : fields) {
        field.setAccessible(true);
        params.put(field.getName(), String.valueOf(field.get(this)));
      }

      for (Field superField : superFields) {
        superField.setAccessible(true);
        params.put(superField.getName(), String.valueOf(superField.get(this)));
      }

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    return params;
  }

}




