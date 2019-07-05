package com.sg.cj.snh.model.response.login;


import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/12/4 21:13
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WeChatGetUserinfoResponse {


  /**
   * openid : OPENID
   * nickname : NICKNAME
   * sex : 1
   * province : PROVINCE
   * city : CITY
   * country : COUNTRY
   * headimgurl : http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0
   * privilege : ["PRIVILEGE1","PRIVILEGE2"]
   * unionid :  o6_bmasdasdsad6_2sgVt7hMZOPfL
   */

  public String openid;
  public String nickname;
  public int sex;
  public String province;
  public String city;
  public String country;
  public String headimgurl;
  public String unionid;
  public List<String> privilege;
}
