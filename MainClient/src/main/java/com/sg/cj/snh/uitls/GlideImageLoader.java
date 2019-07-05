package com.sg.cj.snh.uitls;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sg.cj.snh.R;
import com.youth.banner.loader.ImageLoader;

/**
 *
 * @Description:
 * @author : chenjie
 * creat at 2018/11/28 17:16
 */
public class GlideImageLoader extends ImageLoader {


  @Override
  public  void displayImage(Context context, Object path, ImageView imageView) {
    /**
     注意：
     1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
     2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
     传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
     切记不要胡乱强转！
     */


    //Glide 加载图片简单用法
    RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.img_load_bg)
            .placeholder(R.drawable.img_load_bg);
    Glide.with(context).load(path).apply(options).into(imageView);

  }


}
