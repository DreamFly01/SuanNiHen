package com.sg.cj.common.base.utils;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * author : ${CHENJIE}
 * created at  2018/10/29 08:49
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgUIUtils {
  /**
   * 读取图片
   * @param context 上下文
   * @param path 图片路径
   * @return
   */
  public static Bitmap readBitmapWithLocalPath(Context context, String path){
    try {
      BitmapFactory.Options opt = new BitmapFactory.Options();
      opt.inPreferredConfig = Bitmap.Config.RGB_565;
      opt.inPurgeable = true;
      opt.inInputShareable = true;
      // 获取资源图片
      InputStream bitmapStream = new FileInputStream(new File(path));
      return BitmapFactory.decodeStream(bitmapStream, null, opt);
    } catch (FileNotFoundException e) {
      return null;
    }
  }


  /**
   * 自动弹出键盘
   * @param et 需要弹出键盘的输入框
   */
  public static void autoShowKeyboard(final EditText et){
    et.post(new Runnable() {
      @Override
      public void run() {
        et.dispatchTouchEvent(getSimulationDownEvent(et));
        et.dispatchTouchEvent(getSimulationUpEvent(et));
      }
    });
  }

  /**
   * EditText获取焦点并显示软键盘
   */
  public static void showSoftKeyboard(Activity activity, EditText editText) {
    editText.setFocusable(true);
    editText.setFocusableInTouchMode(true);
    editText.requestFocus();
    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
  }

  /**
   * 隐藏软键盘
   * */
  public static void hideSoftKeyboard(Activity activity) {
    InputMethodManager imm  = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    View view = activity.getCurrentFocus();
    if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
      if (view != null)
        imm .hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  /**
   * 隐藏输入输入法
   */
  public static void hideSoftKeyboard(View v) {
    Context context = v.getContext();
    InputMethodManager imm  = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm .hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  /**
   * 生成按下触摸时间
   * @return {@link MotionEvent#ACTION_DOWN}
   */
  private static MotionEvent getSimulationDownEvent(EditText et) {
    long downTime = SystemClock.uptimeMillis();
    long eventTime = downTime + 100;

    float x = et.getWidth();
    float y = et.getHeight();
    return MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
  }

  /**
   * 生成放开触摸事件
   * @return {@link MotionEvent#ACTION_UP}
   */
  private static MotionEvent getSimulationUpEvent(EditText et) {
    long downTime = SystemClock.uptimeMillis();
    long eventTime = downTime + 100;

    float x = et.getWidth();
    float y = et.getHeight();
    return MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
  }






  /***
   * 读取图片
   *
   * @param context
   * @param resId
   * @return
   */
  public static Bitmap readBitMap(Context context, int resId) {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inPreferredConfig = Bitmap.Config.RGB_565;
    opt.inPurgeable = true;
    opt.inInputShareable = true;
    // 获取资源图片
    InputStream is = context.getResources().openRawResource(resId);
    return BitmapFactory.decodeStream(is, null, opt);
  }

  /***
   * 读取图片
   *
   * @param context
   * @param resId
   * @param inSampleSize
   *            采样比率
   * @return
   */
  public static Bitmap readBitMap(Context context, int resId, int inSampleSize) {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inPreferredConfig = Bitmap.Config.RGB_565;
    opt.inPurgeable = true;
    opt.inInputShareable = true;
    opt.inSampleSize = inSampleSize;
    // 获取资源图片
    InputStream is = context.getResources().openRawResource(resId);
    return BitmapFactory.decodeStream(is, null, opt);
  }


  /**
   * 将InputStream 转换为String
   *
   * @param is
   * @param encoding
   *            编码格式,可以为null,null表示适用utf-8
   */
  public static String stream_2String(InputStream is, String encoding) throws IOException {
    if (is == null)
      return null;

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int i = -1;
    while ((i = is.read()) != -1) {
      baos.write(i);
    }
    baos.close();
    String result = null;
    if (encoding == null) {
      encoding = "utf-8";
    }
    result = baos.toString(encoding);
    return result;
  }

  /**
   * String => inputStream
   *
   * @param src
   * @param charsetName
   *            编码格式 可以为null,null表示适用utf-8
   * @return
   */
  public static InputStream string_2stream(String src, String charsetName) {
    try {
      if (null == charsetName) {
        charsetName = "utf-8";
      }
      byte[] bArray = src.getBytes(charsetName);
      InputStream is = new ByteArrayInputStream(bArray);
      return is;
    } catch (UnsupportedEncodingException e) {
      SgLog.e(e.toString());
    }
    return null;
  }

  /**
   * 从Context中获取Activity
   *
   * @param mContext
   * @return
   */
  public static Activity getActFromContext(Context mContext) {
    if (mContext == null)
      return null;
    else if (mContext instanceof Activity)
      return (Activity) mContext;
    else if (mContext instanceof ContextWrapper)
      return getActFromContext(((ContextWrapper) mContext).getBaseContext());
    return null;
  }

  public static void printCallStack() {
    Map<Thread, StackTraceElement[]> stes = Thread.getAllStackTraces();
    if (null == stes || stes.isEmpty())
      return;
    StackTraceElement[] ste = stes.get(Thread.currentThread());
    if (null == ste || 0 == ste.length)
      return;
    for (StackTraceElement s : ste) {
//            CcbLogUtil.d("STACK PRINT", "======call stack======" + s.toString());
    }
  }
}
