package com.fdl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.fdl.wedgit.GlideRoundTransform;
import com.sg.cj.snh.R;

import java.io.File;


/**
 * <p>desc：图片加载工具<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/9/27<p>
 * <p>changeTime：2018/9/27<p>
 * <p>version：1<p>
 */
public class ImageUtils {
    /**
     * 一般图片加载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadUrlImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_load1_error)
                .placeholder(R.drawable.img_load1_error);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void loadUrlImage1(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_load_rl_bg)
                .placeholder(R.drawable.img_load_rl_bg)
                .override(187, (200));
        Glide.with(context).load(url).apply(options).into(imageView);
    }
    public static void loadUrlImage2(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.drawable.img_load_ud_bg)
//                .placeholder(R.drawable.img_load_ud_bg)
                .override(362,250);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
    /**
     * 根据图片宽高加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void WHloadUrlImage(Context context, String url, ImageView imageView) {
        int[] wh = getImageWidthHeight(url);
        if (wh[0] < wh[1]) {

            RequestOptions options = new RequestOptions()
                    .transform(new RotateTransformation(90f));
            Glide.with(context).load(url)
                    .apply(options)
                    .into(imageView);
        }else {
            Glide.with(context).load(url)
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadUrlCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_load1_error)
                .placeholder(R.drawable.img_load1_error);
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(options).into(imageView);
    }

    /**
     * 加载失败时候显示预览图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadUrlHolderImage(Context context, String url, ImageView imageView) {
//        Glide.with(context).load(url).apply(RequestOptions.placeholderOf())

    }

    /**
     * 加载圆角图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadUrlCorners(Context context, String url, ImageView imageView) {
        //设置图片圆角角度
//        RoundedCorners roundedCorners = new RoundedCorners(20);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(new GlideRoundTransform(context,10)).diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_load1_error)
                .placeholder(R.drawable.img_load1_error);
        Glide.with(context).load(url).apply(options).into(imageView);


    }

    /**
     * 获取图片的宽高
     */

    public static int[] getImageWidthHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth, options.outHeight};
    }

}

