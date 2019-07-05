package com.fdl.updata;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.fdl.utils.AppUtils;
import com.fdl.utils.ArithUtil;
import com.fdl.utils.Contans;
import com.sg.cj.snh.R;

import java.io.File;
import java.util.UUID;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/8<p>
 * <p>changeTime：2018/12/8<p>
 * <p>version：1<p>
 */
public class UpdateAppService extends Service {

    /***
     * 下载的apk存放路径
     * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
     * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
     */
    private String APK_dir =
            Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/com.cfcar.android/download/";// 保存到SD卡路径下;
    /***
     * 下载的apk名称
     */
    private String APK_name = "";
    /***
     * 通知栏ID
     */
    private final int NotificationID = 0x10000;
    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder builder;

    /***
     * 是否显示通知栏
     */
    private boolean isShowNotification = false;
    /***
     * 进度回调给Bind的Activity的接口
     */
    private IProgressLisenr iProgressLisenr;

    private YCDownRequest ycDownRequest;

    public UpdateAppService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ycDownRequest = new YCDownRequest();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBuinder();
    }

    //在服务被系统杀死时会重新被创建,onStartCommand方法会被调用，但是需要注意的是，在调用onStartCommand时传入的intent值可能为null
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private int downloadCount = 0;
    private boolean showFlag = false;

    private RequestResponseListener progressResponseListener = new RequestResponseListener() {

        @Override
        public void inProgress(long bytesRead, long contentLength, boolean done) {
            if (!done) {
                // YCRequestKernel.getInstance().sendProgressResultCallback(bytesRead,contentLength, this.ycResultCallBack);
                if (bytesRead == contentLength) {
                    installAPK();
                } else {
                    int x = (int) bytesRead;
                    int totalS = (int) contentLength;
                    if (isShowNotification) {
                        //通知栏显示
                        builder.setProgress(totalS, x, false);
                        if (showFlag) {
                            builder.setContentInfo((int) (ArithUtil.div(bytesRead, contentLength, 2) * 100) + "%");
                            mNotificationManager.notify(NotificationID,
                                    builder.build());
                            showFlag = false;
                            startForeground(NotificationID, builder.build());
                        } else {
                            //更新5%提示一次，因为频繁更新会导致应用吃紧。
                            if ((downloadCount == 0) || (int) (bytesRead * 100 / contentLength) - 5 > downloadCount) {
                                downloadCount += 5;
                                builder.setContentInfo((int) (ArithUtil.div(bytesRead, contentLength, 2) * 100) + "%");
                                mNotificationManager.notify(NotificationID,
                                        builder.build());
                                //iProgressLisenr.inProgress(bytesRead,contentLength);
                            }
                        }
                    } else {
                        //前台显示
                        iProgressLisenr.inProgress(bytesRead, contentLength);
                    }
                }
            }
        }

        @Override
        public void onStart() {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setSmallIcon(R.drawable.logo);
            builder.setTicker("正在下载新版本");
            builder.setContentTitle(AppUtils.getAppInfo(getApplicationContext()).getName());
            builder.setContentText("正在下载,请稍后...");
            builder.setNumber(0);
            builder.build().flags |= Notification.FLAG_ONGOING_EVENT;
            builder.setOngoing(true);
        }

        @Override
        public void onRequestFailed(Exception e) {
            //通知前台下载失败
            iProgressLisenr.inProgress(-1, -1);
            stopForeground(true);
            //下载失败
            builder.setContentText("下载失败!");
            builder.setTicker("下载失败!");
            builder.setOngoing(false);
            // builder.setDeleteIntent(PendingIntent.getBroadcast(UpdateAppService.this, NotificationID, new Intent("com.cfcar.android.NotificationClearReceive"), 0));
            mNotificationManager.notify(NotificationID,
                    builder.build());
            stopSelf();

        }
    };

    private void DownFile(String downUrl) {

        APK_name = UUID.randomUUID() + Contans.APK_SUFFIXS_STR;
        ycDownRequest.setUrl(downUrl)
                .setRequestResponseListener(progressResponseListener)
                .setSaveFileName(APK_name)
                .setSaveFileDir(APK_dir)
                .invokeAsyn();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showFlag = false;
    }

    private void installAPK() {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(APK_dir + APK_name));
        installIntent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // 震动提示
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000L);// 参数是震动时间(long类型)

        //如果开启了通知栏提示，就更新通知提示栏
        if (isShowNotification) {
            PendingIntent mPendingIntent = PendingIntent
                    .getActivity(UpdateAppService.this, 0,
                            installIntent, 0);
            builder.setContentText("下载完成,请点击安装");
            builder.setContentIntent(mPendingIntent);
            mNotificationManager.notify(NotificationID,
                    builder.build());
            mNotificationManager.cancel(NotificationID);
        }
        stopSelf();
        startActivity(installIntent);// 下载完成之后自动弹出安装界面

    }

    public class MyBuinder extends Binder {

        //设置监听回调接口
        public void setProgressLisener(IProgressLisenr progressLisener) {
            iProgressLisenr = progressLisener;
        }

        //开启通知栏显示，不会继续向外发送 进度信息
        public void startNotifacation() {
            isShowNotification = true;
            showFlag = true;
        }

        //关闭服务
        public void stopService() {
            stopForeground(true);
            stopSelf();
        }


        //开始下载服务
        public void startDown(String downUrl) {
            DownFile(downUrl);
        }
    }

    /***
     * 获得前台应用包名，在5.0及以上，获取进程名，在其它app没有手动指定则进程名则为包名 ，默认为包名
     *
     * @return 进程名
     */
    public String getProcessName() {
        String proccessName = "";
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 20) {
            proccessName = activityManager.getRunningAppProcesses().get(0).processName;
        } else {
            proccessName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        return proccessName;
    }


}
