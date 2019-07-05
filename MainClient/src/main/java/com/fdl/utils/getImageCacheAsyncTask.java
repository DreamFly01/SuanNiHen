package com.fdl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/18<p>
 * <p>changeTime：2019/2/18<p>
 * <p>version：1<p>
 */
public class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {
    private final Context context;
    private ImageView img;
    public getImageCacheAsyncTask(Context context, ImageView img) {
        this.context = context;
        this.img = img;
    }

    @Override
    protected File doInBackground(String... params) {
        String imgUrl =  params[0];
        try {
            return Glide.with(context)
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }
        //此path就是对应文件的缓存路径
        String path = result.getPath();
        Bitmap bmp= BitmapFactory.decodeFile(path);
        img.setImageBitmap(bmp);

    }
}
