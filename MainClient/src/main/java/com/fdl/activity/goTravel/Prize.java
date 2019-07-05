package com.fdl.activity.goTravel;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/25<p>
 * <p>changeTime：2019/4/25<p>
 * <p>version：1<p>
 */
public class Prize {
    int Id;
    String Name;
    Bitmap Icon;
    int BgColor;
    Rect rect;

    public OnClickListener getListener() {
        return listener;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    OnClickListener listener;

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Bitmap getIcon() {
        return Icon;
    }

    public void setIcon(Bitmap icon) {
        Icon = icon;
    }

    public int getBgColor() {
        return BgColor;
    }

    public void setBgColor(int bgColor) {
        BgColor = bgColor;
    }
    public  interface OnClickListener{
        void click();
    }

    public void click(){
        listener.click();
    }
    public boolean isClick (Point touchPoint, int width){
        if (touchPoint.x < width/3*2&touchPoint.x > width/3&touchPoint.y < width/3*2&touchPoint.y > width/3){
            return true;
        }else{
            return false;
        }
    }

}
