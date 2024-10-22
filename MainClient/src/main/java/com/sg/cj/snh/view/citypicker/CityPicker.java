package com.sg.cj.snh.view.citypicker;

import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.sg.cj.snh.view.citypicker.adapter.OnPickListener;
import com.sg.cj.snh.view.citypicker.model.City;
import com.sg.cj.snh.view.citypicker.model.HotCity;
import com.sg.cj.snh.view.citypicker.model.LocateState;
import com.sg.cj.snh.view.citypicker.model.LocatedCity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Bro0cL
 * @Date: 2018/2/6 17:52
 */
public class CityPicker {
    private static final String TAG = "CityPicker";

    private WeakReference<FragmentActivity> mContext;
    private WeakReference<Fragment> mFragment;
    private WeakReference<FragmentManager> mFragmentManager;

    private boolean enableAnim;
    private int mAnimStyle;
    private LocatedCity mLocation;
    private List<HotCity> mHotCities;
    private List<City> mAllCities=new ArrayList<>();
    private OnPickListener mOnPickListener;
//    private List<City> mAllCities1=new ArrayList<>();
    private CityPicker(){}

    private CityPicker(Fragment fragment){
        this(fragment.getActivity(), fragment);
        mFragmentManager = new WeakReference<>(fragment.getChildFragmentManager());
    }

    private CityPicker(FragmentActivity activity){
        this(activity, null);
        mFragmentManager = new WeakReference<>(activity.getSupportFragmentManager());
    }

    private CityPicker(FragmentActivity activity, Fragment fragment){
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public static CityPicker from(Fragment fragment){
        return new CityPicker(fragment);
    }

    public static CityPicker from(FragmentActivity activity){
        return new CityPicker(activity);
    }

    /**
     * 设置动画效果
     * @param animStyle
     * @return
     */
    public CityPicker setAnimationStyle(@StyleRes int animStyle) {
        this.mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置当前已经定位的城市
     * @param location
     * @return
     */
    public CityPicker setLocatedCity(LocatedCity location) {
        this.mLocation = location;
        return this;
    }

    public CityPicker setHotCities(List<HotCity> data){
        this.mHotCities = data;
        return this;
    }

    public CityPicker setAllCities(List<City> data){
        if(null!=data){
            this.mAllCities.clear();
            this.mAllCities.addAll(data);
        }

        return this;
    }

    /**
     * 启用动画效果，默认为false
     * @param enable
     * @return
     */
    public CityPicker enableAnimation(boolean enable){
        this.enableAnim = enable;
        return this;
    }

    /**
     * 设置选择结果的监听器
     * @param listener
     * @return
     */
    public CityPicker setOnPickListener(OnPickListener listener){
        this.mOnPickListener = listener;
        return this;
    }

    public void show(){
        FragmentTransaction ft = mFragmentManager.get().beginTransaction();
        final Fragment prev = mFragmentManager.get().findFragmentByTag(TAG);
        if (prev != null){
            ft.remove(prev).commit();
            ft = mFragmentManager.get().beginTransaction();
        }
        ft.addToBackStack(null);
        final CityPickerDialogFragment cityPickerFragment =
                CityPickerDialogFragment.newInstance(enableAnim);
        cityPickerFragment.setLocatedCity(mLocation);
        cityPickerFragment.setHotCities(mHotCities);
        cityPickerFragment.setAllCities(mAllCities);
        cityPickerFragment.setAnimationStyle(mAnimStyle);
        cityPickerFragment.setOnPickListener(mOnPickListener);
        cityPickerFragment.show(ft, TAG);
    }

    /**
     * 定位完成
     * @param location
     * @param state
     */
    public void locateComplete(LocatedCity location, @LocateState.State int state){
        CityPickerDialogFragment fragment = (CityPickerDialogFragment) mFragmentManager.get().findFragmentByTag(TAG);
        if (fragment != null){
            fragment.locationChanged(location, state);
        }
    }
}
