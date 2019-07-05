package com.fdl.activity.food;

import android.app.Activity;
import android.os.Bundle;

import com.fdl.BaseActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;

public class OrderDetailsActivity extends BaseActivity {


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_details);
    }

    @Override
    public void setUpViews() {
        ImmersionBar.with(this).init();

    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
