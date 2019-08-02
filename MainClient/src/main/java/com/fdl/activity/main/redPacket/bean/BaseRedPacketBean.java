package com.fdl.activity.main.redPacket.bean;

/**
 * @author 陈自强
 * @date 2019/7/30
 */
public class BaseRedPacketBean {

    /**
     * code : 01
     * data : {}
     * msg : 获取成功
     */

    private String code;
    private RedPacketBean data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RedPacketBean getData() {
        return data;
    }

    public void setData(RedPacketBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
