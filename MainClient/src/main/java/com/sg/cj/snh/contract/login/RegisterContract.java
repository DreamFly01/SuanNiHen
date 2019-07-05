package com.sg.cj.snh.contract.login;


import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;

/**
 * 
 * @Description:  注册
 * @author : chenjie
 * creat at 2018/11/26 08:55
 */
public interface RegisterContract {
  interface View extends BaseView {


    void doSendPhoneCodeSuccess();
    void doRegisterSuccess();

  }

  interface Presenter extends BasePresenter<View> {

    /**
     * 2．获取短信验证码
     *
     * 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
     */

    void doSendPhoneCode(String phone,int type);


    /**
     * 3．验证短信验证码
     * @param phone
     * @param code
     * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
     * @return
     */
    void doValidateCode(String phone, String code,  int type);



    /**
     * 4．短信验证登录
     * @param phone 手机号码
     * @param code  短信验证码
     * @return
     */
    void doPhoneLogin( String phone,  String code);





    /**
     * 8．注册
     * @param account 手机号码
     * @param pwd     登录密码
     * @param nickname 会员名
     * @param code    短信验证码
     * @return
     */


    void doRegister(String account,String pwd,String nickname, String code);


  }
}
