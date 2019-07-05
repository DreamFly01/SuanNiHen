package com.sg.cj.common.base.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;


public class SgUtils {


    /**
     * 启动 app
     *
     * @param mContext
     * @param pkgName
     * @param bundle
     */
    public static void start(Context mContext, String pkgName, Bundle bundle) {
        PackageInfo pi = null;
        try {
            pi = mContext.getPackageManager().getPackageInfo(pkgName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pm = mContext.getPackageManager();
            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                mContext.startActivity(intent);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印List,list的元素是String,String型的Map
     *
     * @param list
     * @return
     */
    public static String list2String(List<Map<String, String>> list) {
        final String indent = "    ";
        if (list == null) {
            return indent + "null list";
        }
        if (list.size() == 0) {
            return indent + "list size = 0";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = list.size(); i < size; i++) {
            if (i != 0) {
                sb.append("\n");
            }
            sb.append("++map[" + (i + 1) + "]++");
            Map<String, String> m = list.get(i);
            sb.append(map2String(m));
        }

        return sb.toString();
    }

    /**
     * 打印String,String型的map
     *
     * @param map
     * @return
     */
    public static String map2String(Map<String, String> map) {
        final String indent = "    ";

        if (map == null) {
            return indent + "null map";
        }
        if (map.size() == 0) {
            return indent + "map size = 0";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : map.keySet()) {
            sb.append(indent + s + " = " + map.get(s) + "\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 打印 string array
     *
     * @param array
     * @return
     */
    public static String array2String(String[] array) {
        final String indent = "    ";

        if (array == null) {
            return indent + "null array";
        }
        if (array.length == 0) {
            return indent + "array size = 0";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(indent + s + "\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /***** 获取客户端技术版本号,如3.4.3.001 *****/
    private static CcbVersionInterface ccbVersionInterface = null;

    public static void setCcbVersionInterface(CcbVersionInterface ccbVersionInterface) {
        SgUtils.ccbVersionInterface = ccbVersionInterface;
    }

    public static String getCcbVersion() {
        if (null == SgUtils.ccbVersionInterface) {
            return null;
        }
        return SgUtils.ccbVersionInterface.getCcbVersion();
    }

    public interface CcbVersionInterface {
        String getCcbVersion();
    }



    /**
     * 将InputStream 转换为String
     *
     * @param is
     * @param encoding 编码格式,可以为null,null表示适用utf-8
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
     * @param charsetName 编码格式 可以为null,null表示适用utf-8
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


    public static String defType = "drawable";
    public static String defTypeMipmap = "mipmap";

    /**
     * 读取drawable下的图片
     *
     * @param context
     * @param imageName 不包括图片后缀名
     * @return
     */
    public static Bitmap readBitMap(Context context, String imageName) {
        int resId = getDrawableId(context, imageName);
        if (0 == resId)
            return null;
        return readBitMap(context, resId);
    }

    /**
     * 通过名字获取drawable id
     *
     * @param context   上下文
     * @param imageName 图片名称 不含后缀
     */
    public static int getDrawableId(Context context, String imageName) {
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (0 == resId) {
            resId = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
        }
        return resId;
    }

    /**
     * 根据图片名称获取资源ID
     *
     * @param context
     * @param drawableName
     * @return
     */
    public static int getResIdByName(Context context, String drawableName) {
        if (TextUtils.isEmpty(drawableName))
            return 0;
        int resId = context.getResources().getIdentifier(drawableName, defType, context.getPackageName());
        if (0 == resId) {
            resId = context.getResources().getIdentifier(drawableName, defTypeMipmap, context.getPackageName());
        }
        return resId;
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
     * 读取图片
     *
     * @param context 上下文
     * @param path    图片路径
     * @return
     */
    public static Bitmap readBitmapWithLocalPath(Context context, String path) {
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



    private static void setScreenLight(Context context, int value) {
        ContentResolver contentResolver = context.getContentResolver();
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        float light = value / 255f;
        layoutParams.screenBrightness = light;
        window.setAttributes(layoutParams);
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, value);
    }







    public static void printCallStack() {
        Map<Thread, StackTraceElement[]> stes = Thread.getAllStackTraces();
        if (null == stes || stes.isEmpty())
            return;
        StackTraceElement[] ste = stes.get(Thread.currentThread());
        if (null == ste || 0 == ste.length)
            return;
        for (StackTraceElement s : ste) {
//			CcbLogUtil.d("STACK PRINT"+ "======call stack======" + s.toString());
        }
    }



    /**
     * 判断集合为空
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断Map为空
     *
     * @param map
     * @return
     */
    public static boolean isMapEmpty(Map map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 如果value为空，默认--填充
     *
     * @param value
     * @return
     */
    public static String getString(String value) {
        if (TextUtils.isEmpty(value) || "null".equalsIgnoreCase(value)) {
            return "- -";
        }
        return value;
    }

    /**
     * 获取百分比
     *
     * @param totalValue
     * @param value
     * @return
     */
    public static String getPercent(double totalValue, double value) {
        String percent = "";
        if (totalValue == 0) {
            return percent;
        }
        NumberFormat numberFormat = new DecimalFormat("#.00");
        percent = numberFormat.format(value / totalValue * 100) + "%";
        return percent;
    }

    /**
     * 保留2位小数点
     *
     * @param s
     * @return
     */
    public static String get2DicimalString(String s) {
        if (s == null || s.length() < 1) {
            return "0.00";
        }
        if(s.equalsIgnoreCase(".")) {
            return "0.00";
        }
        NumberFormat formater = new DecimalFormat("#.00");
        double num = Double.parseDouble(s);

        String returnstr = formater.format(num);
        if (returnstr.startsWith(".")) {
            return "0" + returnstr;
        } else if (returnstr.startsWith("-.")) {
            return "-0" + returnstr.substring(1, returnstr.length());
        } else {
            return returnstr;
        }
     }

    /**
     * 如果空，返回0
     *
     * @param str
     * @return
     */
    public static String ifEmptyStrReturn0(String str) {
        return TextUtils.isEmpty(str) ? "0" : str;
    }


}
