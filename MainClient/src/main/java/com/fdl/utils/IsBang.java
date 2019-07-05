package com.fdl.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/21<p>
 * <p>changeTime：2019/1/21<p>
 * <p>version：1<p>
 */
public class IsBang {

    //是否是小米刘海
    public static boolean getInt(String key, Activity activity) {
        int result = 0;
        if (isXiaomi()) {
            try {
                ClassLoader classLoader = activity.getClassLoader();
                @SuppressWarnings("rawtypes")
                Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
                //参数类型
                @SuppressWarnings("rawtypes")
                Class[] paramTypes = new Class[2];
                paramTypes[0] = String.class;
                paramTypes[1] = int.class;
                Method getInt = SystemProperties.getMethod("getInt", paramTypes);
                //参数
                Object[] params = new Object[2];
                params[0] = new String(key);
                params[1] = new Integer(0);
                result = (Integer) getInt.invoke(SystemProperties, params);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 1 == result ? true : false;
    }

    public static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

    //是否是华为刘海
    public static boolean hasNotchAtHuawei(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
        } finally {
            return ret;
        }
    }

    //是否是vivo刘海
    public static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    public static final int VIVO_FILLET = 0x00000008;//是否有圆角

    public static boolean hasNotchAtVivo(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
        } finally {
            return ret;
        }
    }

    //是否是oppo刘海
    public static boolean hasNotchAtOPPO(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    //获取刘海高度
    public static int[] getNotchSize(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
        } finally {
            return ret;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setImmerHeard(Context context, LinearLayout heard) {
        if (IsBang.getInt("isBang", (Activity) context) | IsBang.hasNotchAtHuawei(context) | IsBang.hasNotchAtOPPO(context) | IsBang.hasNotchAtVivo(context)) {
            if (ImmersionBar.hasNotchScreen((Activity) context)) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) heard.getLayoutParams();
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                heard.setLayoutParams(params);
                heard.setPadding(0, ImmersionBar.getActionBarHeight((Activity) context) / 2 + 10, 0, 20);
            }
        }
    }
    public static void setImmerHeard(Context context,LinearLayout heard,String color){
        if(IsBang.getInt("isBang",(Activity) context)|IsBang.hasNotchAtHuawei(context)|IsBang.hasNotchAtOPPO(context)|IsBang.hasNotchAtVivo(context)){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) heard.getLayoutParams();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            heard.setLayoutParams(params);
            heard.setPadding(0, ImmersionBar.getActionBarHeight((Activity) context) / 2 + 10, 0, 20);
            if(StrUtils.isEmpty(color)){
                heard.setBackgroundColor(Color.parseColor("#2E8AFF"));
            }else {
                heard.setBackgroundColor(Color.parseColor(color));
            }
        }
    }
}
