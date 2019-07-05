package com.fdl.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/11<p>
 * <p>changeTime：2019/1/11<p>
 * <p>version：1<p>
 */
public class ShareUtils {

    private static IWXAPI api;

    public static void ShareWechat(String title, String url, String name, String imgUrl) {
        Platform.ShareParams sp = new Platform.ShareParams();

        if (!StrUtils.isEmpty(imgUrl)) {
            sp.setImageUrl(imgUrl);
        }
        sp.setShareType(Platform.SHARE_WEBPAGE);
        if (!StrUtils.isEmpty(name)) {
            sp.setText(name);
        }
        if (!StrUtils.isEmpty(url)) {
            sp.setUrl(url);
        }
        if (!StrUtils.isEmpty(title)) {
            sp.setTitle(title);
        }
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.share(sp);
    }

    public static void ShareWechatMom(String title, String url, String name, String imgUrl) {
        Platform.ShareParams sp = new Platform.ShareParams();
        if (!StrUtils.isEmpty(imgUrl)) {
            sp.setImageUrl(imgUrl);
        }
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText(name);
        sp.setUrl(url);
        sp.setTitle(title);
        Platform wechat = ShareSDK.getPlatform(WechatMoments.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                SgLog.e("分享----------ok", "ok");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                SgLog.e("分享----------no", "no");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                SgLog.e("分享----------no", "no");
            }
        });
        wechat.share(sp);
    }

    //分享小程序
    public static void ShareWechctApplets(String title, String url, String description, String imgurl) {

        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = 0;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_5f0e9c85b9a5";     // 小程序原始id
        miniProgramObj.path = url;            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        if (!StrUtils.isEmpty(title)) {
            msg.title = title;          // 小程序消息titlele = title;
        }
        msg.description = description;   // 小程序消息desc
        // 小程序消息封面图片，小于128k
        Glide.with(PartyApp.getAppComponent().getContext()).asBitmap().load(imgurl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                Bitmap thumbBmp = Bitmap.createScaledBitmap(resource, 150, 150, true);
                msg.thumbData = BitmapUtils.bmpToByteArray(thumbBmp, true);
                //        msg.thumbData = getThumb();             // 小程序消息封面图片，小于128k
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
                PartyApp.mWxApi.sendReq(req);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                Bitmap resource = BitmapFactory.decodeResource(PartyApp.getAppComponent().getContext().getResources(), R.drawable.logo);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(resource, 150, 150, true);
                msg.thumbData = BitmapUtils.bmpToByteArray(thumbBmp, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
                PartyApp.mWxApi.sendReq(req);
            }
        });
    }


}
