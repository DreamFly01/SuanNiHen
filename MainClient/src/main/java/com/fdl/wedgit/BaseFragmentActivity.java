package com.fdl.wedgit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.fdl.jpush.Logger;

/**
 * @author 陈自强
 * @date 2019/7/30
 */
public class BaseFragmentActivity  extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("FragmentActivityName",getActivityName());
    }


    private String getActivityName(){
        Context context = this;
        String contextString = context.toString();
        return contextString.substring(0, contextString.indexOf("@"));
    }
}
