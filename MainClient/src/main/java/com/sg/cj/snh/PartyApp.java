package com.sg.cj.snh;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bulong.rudeness.RudenessScreenHelper;
import com.fdl.activity.main.MainActivity;
import com.fdl.utils.ActivityManagerUtils;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.LanguageUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.mob.MobSDK;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sg.cj.common.base.App;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.di.component.AppComponent;
import com.sg.cj.snh.di.component.DaggerAppComponent;
import com.sg.cj.snh.di.module.AppModule;
import com.sg.cj.snh.di.module.HttpModule;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.snh.greendao.DaoSession;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * author : ${CHENJIE}
 * created at  2018/10/23 08:23
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class PartyApp extends App {


    public static AppComponent appComponent;
    public static DaoSession mDaoSession;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//        layout.setPrimaryColorsId(R.color.white, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    String content = "";

    @Override
    public void onCreate() {
        super.onCreate();
        registerToWX();
        MobSDK.init(this);
        int designWidth = 750;
        new RudenessScreenHelper(this, designWidth).activate();
        ToastMgr.builder.init(getApplicationContext());
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManagerUtils.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        NIMClient.init(this, null, options());
        if (inMainProcess(this)) {
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            initUiKit();
            if (!Contans.debug) {
                CrashReport.initCrashReport(getApplicationContext(), Contans.BUGLY_ID, true);
            }
        }
        LanguageUtils.setdefaultLanguage(this,"zh");
    }

    public enum ToastMgr {
        builder;
        private View view;
        private TextView tv;
        private Toast toast;

        /**
         * 初始化Toast
         *
         * @param context
         */
        public void init(Context context) {
            view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            tv = (TextView) view.findViewById(R.id.tv_toast_msg);
            toast = new Toast(context);
            toast.setView(view);
        }

        /**
         * 显示Toast
         *
         * @param content
         * @param duration Toast持续时间
         */
        public void display(CharSequence content, int duration) {
            if (content.length() != 0) {
                tv.setText(content);
                toast.setDuration(duration);
                //动态设置toast显示的位置
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    public static IWXAPI mWxApi;

    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constants.WEIXIN_APP_ID);
    }


    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(getInstance()))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

    private void initUiKit() {

        // 初始化
        NimUIKit.init(this);

        NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                return false;
            }
        });
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(new Observer<StatusCode>() {
            public void onEvent(StatusCode status) {
                if (status == StatusCode.KICKOUT | status == StatusCode.KICK_BY_OTHER_CLIENT) {
                    // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
//                            DBManager.getInstance(getApplicationContext()).cleanUser();
                    content = "您的账号其他端口登录，若非本人操作，请及时修改密码";
                    String tel = PartyApp.getAppComponent().getDataManager().getTel();
                    PartyApp.getAppComponent().getDataManager().setTel(tel);
                    DialogUtils dialogUtils = new DialogUtils(ActivityManagerUtils.getInstance().getCurrentActivity());
                    dialogUtils.simpleDialog(content, new DialogUtils.ConfirmClickLisener() {
                        @Override
                        public void onConfirmClick(View v) {
                            SPUtils.getInstance(ActivityManagerUtils.getInstance().getCurrentActivity()).cleanUserData();
                            NIMClient.getService(AuthService.class).logout();
                            JumpUtils.simpJump(ActivityManagerUtils.getInstance().getCurrentActivity(), LoginActivity.class, true);
                        }
                    }, false);
//                            JumpUtils.simpJump(ActivityManagerUtils.getInstance().getCurrentActivity(),LogingActivity.class,true);
                }
            }
        }, true);
//        监听登陆状态
//
//        Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
//            @Override
//            public void onEvent(List<OnlineClient> onlineClients) {
//                if (onlineClients == null || onlineClients.size() == 0) {
//                    return;
//                }
//                OnlineClient client = onlineClients.get(0);
//                switch (client.getClientType()) {
//                    case ClientType.Windows:
//                        // PC端
////            DBManager.getInstance(getApplicationContext()).cleanUser();
//                        content = "您的账号在pc端登录，若非本人，请及时修改密码";
//                        break;
//                    case ClientType.MAC:
//                        // MAC端
//                        content = "您的账号在MAC端登录，若非本人，请及时修改密码";
//                        break;
//                    case ClientType.Web:
//                        // Web端
//                        content = "您的账号在WEB端登录，若非本人，请及时修改密码";
//                        break;
//                    case ClientType.iOS:
//                        // IOS端
//                        content = "您的账号在IOS端登录，若非本人，请及时修改密码";
//                        break;
//                    case ClientType.Android:
//                        // Android端
//                        content = "您的账号在android端登录，若非本人，请及时修改密码";
//                        break;
//                    default:
//                        break;
//
//                }
//
//                DialogUtils dialogUtils = new DialogUtils(ActivityManagerUtils.getInstance().getCurrentActivity());
//                dialogUtils.simpleDialog(content, new DialogUtils.ConfirmClickLisener() {
//                    @Override
//                    public void onConfirmClick(View v) {
//                        PartyApp.getAppComponent().getDataManager().cleanData();
//
//                        JumpUtils.simpJump(ActivityManagerUtils.getInstance().getCurrentActivity(), LoginActivity.class, true);
//                    }
//                }, false);
//            }
//
//        };
//
//        NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, true);
    }

    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context);
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!StrUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.logo;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        DisplayMetrics dm = new DisplayMetrics();
        int width = dm.widthPixels;
        options.thumbnailSize = width / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {


            @Override
            public UserInfo getUserInfo(String s) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String s, String s1, SessionTypeEnum sessionTypeEnum) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
                return null;
            }


        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        String account = SPUtils.getInstance(this).getString(Contans.IM_ACCOUNT);
        String token = SPUtils.getInstance(this).getString(Contans.IM_TOKEN);
//        String account = "";
//        String token = "";
        if (!StrUtils.isEmpty(account) && !StrUtils.isEmpty(token)) {
//            DemoCache.setAccount(account.toLowerCase());
            NimUIKitImpl.setAccount(account);
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }
}
