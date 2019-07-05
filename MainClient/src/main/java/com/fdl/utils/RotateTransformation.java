package com.fdl.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/19<p>
 * <p>changeTime：2018/10/19<p>
 * <p>version：1<p>
 */
public class RotateTransformation extends BitmapTransformation {
    private float rotateRotationAngle = 0f;

    public RotateTransformation( float rotateRotationAngle) {

        this.rotateRotationAngle = rotateRotationAngle;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();

        matrix.postRotate(rotateRotationAngle);

        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
    }




    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}



