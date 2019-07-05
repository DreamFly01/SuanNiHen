package com.sg.cj.snh.model.response.login;


/**
 * author : ${CHENJIE}
 * created at  2018/12/4 21:13
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WeChatGetAccessTokenResponse  {

  /**
   * access_token : 16_3VW7iMvw9Eq060JM1tGt-EUgHIzbdgAL5C4fya2lMoVD5Zu2UZfoqdTbg4ge7BLwXTALPrvqjUflGuSw20U5zuxxRmT8PPGKMzT4PSEBouM
   * expires_in : 7200
   * refresh_token : 16_tEirm1upjLAONazuJberZU-rK3gnkXXQbba8DWdccotq1bUK52tqYE_ZL2LUGqOB_c47wyWs_VdTOWvLsmjjj-ldyboj5hAx19Rd_gmjt-I
   * openid : o0mVi0c1LJoN4xTQyjVSKfqtdpa0
   * scope : snsapi_userinfo
   * unionid : oY0pv1qm5OwClukRgcbAg_iZQf3Q
   */

  public String access_token;
  public int expires_in;
  public String refresh_token;
  public String openid;
  public String scope;
  public String unionid;
}
