package com.sg.cj.snh.uitls;


/**
 * author : ${CHENJIE}
 * created at  2018/10/25 08:55
 * e_mail : chenjie_goodboy@163.com
 * describle : 展示圆形图片
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideCircleTransform extends BitmapTransformation {

  public GlideCircleTransform(Context context) {
    super();
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

  }

  @Override
  protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
    return circleCrop(pool, toTransform);
  }

  private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
    if (source == null) return null;

    int size = Math.min(source.getWidth(), source.getHeight());
    int x = (source.getWidth() - size) / 2;
    int y = (source.getHeight() - size) / 2;

    Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

    Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(result);
    Paint paint = new Paint();
    paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    paint.setAntiAlias(true);
    float r = size / 2f;
    canvas.drawCircle(r, r, r, paint);
    return result;
  }


  public String getId() {
    return getClass().getName();
  }

}




