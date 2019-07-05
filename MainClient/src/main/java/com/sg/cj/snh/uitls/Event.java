package com.sg.cj.snh.uitls;


/**
 * author : ${CHENJIE}
 * created at  2018/12/11 21:33
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class Event<T> {


  public static final int EVENT_CLOSE_ALL_ACTIVITY = 10001;

  /**
   * reserved data
   */
  private T data;

  /**
   * this code distinguish between different events
   */
  private int eventCode = -1;

  public Event(int eventCode) {
    this(eventCode, null);
  }

  public Event(int eventCode, T data) {
    this.eventCode = eventCode;
    this.data = data;
  }

  /**
   * get event code
   *
   * @return
   */
  public int getCode() {
    return this.eventCode;
  }

  /**
   * get event reserved data
   *
   * @return
   */
  public T getData() {
    return this.data;
  }


}

