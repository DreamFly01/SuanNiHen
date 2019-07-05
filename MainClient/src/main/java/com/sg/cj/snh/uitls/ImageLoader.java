package com.sg.cj.snh.uitls;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sg.cj.snh.PartyApp;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 10:41
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class ImageLoader {



  public static void load(String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图


    RequestOptions options = new RequestOptions()				//加载成功之前占位图
							//指定图片的尺寸
//指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
//.fitCenter()
////指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
//.centerCrop()
//
//.circleCrop()//指定图片的缩放类型为centerCrop （圆形）
.skipMemoryCache(true)							//跳过内存缓存

.diskCacheStrategy(DiskCacheStrategy.NONE)		//跳过磁盘缓存
;


    Glide.with(PartyApp.getInstance())
        .load(url)
        .apply(options)
        .into(iv);

  }


}
