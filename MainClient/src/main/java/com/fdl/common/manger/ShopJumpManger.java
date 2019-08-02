package com.fdl.common.manger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.activity.buy.ShopDetailsActivity;
import com.fdl.activity.food.FoodShopActivity;
import com.fdl.activity.supermarket.StoreDetailsActivity;

/**
 * @author 陈自强
 * @date 2019/8/1
 */
public class ShopJumpManger {
    private int type;
    private int businessActivities;
    private int shopType;
    private int goodsId;
    private int stroeId;
    private String name;
    private String distance;
    private Context mContext;

    public ShopJumpManger(Context mContext) {
        this.mContext = mContext;
    }

    private static final String GOODS_ID = "goodsId";
    private static final String STROE_ID = "stroeId";
    private static final String DISTANCE = "distance";
    private static final String SHOP_TYPE = "shopType";

    public void jumpShopActivity() {
        if (type == 1) {//1本地 2 3全国
            jumpShop();
        } else {
            jumpGoods();
        }
    }


    private void jumpShop() {
        if (businessActivities == 1) {
            jumpLocalShop();
        } else {
            jumpNationalShopActivity();
        }
    }

    /**
     * //8 其它  7 家装建材  6 商超士多  5 地方名产  4 酒店公寓 3 逛街吧  2 娱乐休闲 1美食分享
     * 本地商铺
     */
    private void jumpLocalShop() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (shopType) {
            case 1:
                intent.setClass(mContext, FoodShopActivity.class);
//                bundle.putInt(GOODS_ID, goodsId);
                bundle.putInt(STROE_ID, stroeId);
//                bundle.putString(DISTANCE, distance);
                bundle.putInt(SHOP_TYPE, shopType);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                intent.setClass(mContext, StoreDetailsActivity.class);
                bundle.putInt(STROE_ID, stroeId);
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                break;
        }
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    /**
     * 全国商铺
     */
    private void jumpNationalShopActivity() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (shopType) {
            case 1:
                intent.setClass(mContext, FoodShopActivity.class);
                bundle.putInt(STROE_ID, stroeId);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                intent.setClass(mContext, ShopDetailsActivity.class);
                bundle.putInt(STROE_ID, stroeId);
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                break;
        }
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    private void jumpGoods() {
        if (businessActivities == 1) {
            jumpLocalGoods();
        } else {
            jumpNationalGoods();
        }

    }


    private void jumpLocalGoods() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (shopType) {
            case 1:
                intent.setClass(mContext, ProductDetailsActivity.class);
                bundle.putInt(GOODS_ID, goodsId);
                bundle.putInt(STROE_ID, stroeId);
                bundle.putString(DISTANCE, distance);
                bundle.putInt(SHOP_TYPE, shopType);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                intent.setClass(mContext, StoreDetailsActivity.class);
                bundle.putInt(STROE_ID, stroeId);
                bundle.putInt(GOODS_ID, goodsId);
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                break;
        }
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void jumpNationalGoods() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (shopType) {
            case 1:
                intent.setClass(mContext, ProductDetailsActivity.class);
                bundle.putInt(GOODS_ID, goodsId);
                bundle.putInt(STROE_ID, stroeId);
                bundle.putString(DISTANCE, distance);
                bundle.putInt(SHOP_TYPE, shopType);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                intent.setClass(mContext, StoreDetailsActivity.class);
                bundle.putInt(STROE_ID, stroeId);
                bundle.putInt(GOODS_ID, goodsId);
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                break;
        }
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    public int getType() {
        return type;
    }

    /**
     * 一级分类
     *
     * @param type 1是店铺，2是商品
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 地区分类
     *
     * @return 1本地 2 3全国
     */
    public int getBusinessActivities() {
        return businessActivities;
    }

    /**
     * 地区分类
     *
     * @param businessActivities 1本地 2 3全国
     */
    public void setBusinessActivities(int businessActivities) {
        this.businessActivities = businessActivities;
    }

    /**
     * 模块分类
     *
     * @return 8 其它  7 家装建材  6 商超士多  5 地方名产  4 酒店公寓 3 逛街吧  2 娱乐休闲 1美食分享
     */
    public int getShopType() {
        return shopType;
    }

    /**
     * 模块分类
     *
     * @param shopType 8 其它  7 家装建材  6 商超士多  5 地方名产  4 酒店公寓 3 逛街吧  2 娱乐休闲 1美食分享
     */
    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getStroeId() {
        return stroeId;
    }

    public void setStroeId(int stroeId) {
        this.stroeId = stroeId;
    }
}
