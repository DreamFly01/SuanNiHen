package com.fdl.activity.merchantEntry;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bm.library.Info;
//import com.bm.library.PhotoView;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/22<p>
 * <p>changeTime：2019/1/22<p>
 * <p>version：1<p>
 */
public class ImageViewActivity extends Activity {

    @BindView(R.id.iv_introduce)
    PhotoView ivIntroduce;
    private ImmersionBar immersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview_layout);
        ButterKnife.bind(this);
        immersionBar = ImmersionBar.with(this);
        immersionBar.init();

    }


    public void setUpViews() {
//        ivIntroduce.enable();
// 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)
//        ivIntroduce.disenable();
// 获取图片信息
//        Info info = ivIntroduce.getInfo();
// 从普通的ImageView中获取Info
//        Info info = PhotoView.getImageViewInfo(ivIntroduce);
// 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照demo的使用
//        ivIntroduce.animaFrom(info);
// 从现在的图片变化到所给定的图片信息，用于图片放大后点击缩小到原来的位置，具体使用可以参照demo的使用
//        ivIntroduce.animaTo(info,new Runnable() {
//            @Override
//            public void run() {
//                //动画完成监听
//            }
//        });
// 获取/设置 动画持续时间
//        ivIntroduce.setAnimaDuring(int during);
//        int d = ivIntroduce.getAnimaDuring();
// 获取/设置 最大缩放倍数
//        ivIntroduce.setMaxScale(float maxScale);
//        float maxScale = ivIntroduce.getMaxScale();
// 设置动画的插入器
//        ivIntroduce.setInterpolator(Interpolator interpolator);
    }


    public void setUpLisener() {
        ivIntroduce.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                ImageViewActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != immersionBar) {
            immersionBar.destroy();
        }
    }

}
