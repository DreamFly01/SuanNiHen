package com.fdl.activity.food;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FoodOrderDetailsActivity extends BaseActivity {


    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_food_order_details);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("订单详情");
        heardTvMenu.setText("提交");

    }

    @Override
    public void setUpLisener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.heard_back, R.id.heard_tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.heard_tv_menu:

                break;
            default:
                break;

        }

    }

}
