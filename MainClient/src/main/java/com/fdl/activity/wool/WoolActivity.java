package com.fdl.activity.wool;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.order.MyOrderActivity;
import com.fdl.activity.order.OrderFragment;
import com.fdl.utils.IsBang;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class WoolActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.tab_vp)
    ViewPager tabVp;
    private MyAdapter adapter;
    private String[] titles = {"全部", "可薅羊毛", "已薅羊毛"};
    private List<String> listTab = new ArrayList<>();
    //    private int images[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private List<Fragment> list = new ArrayList<>();
    private int tabType;
    private Bundle bundle;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wool_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            tabType = bundle.getInt("tabType");
        }
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTitle.setText("薅羊毛");
        for (int i = 0; i < titles.length; i++) {
            WoolFragment woolFragment = new WoolFragment();
            bundle = new Bundle();
            bundle.putInt("type",i);
            woolFragment.setArguments(bundle);
            list.add(woolFragment);
        }
        adapter = new WoolActivity.MyAdapter(getSupportFragmentManager(), this);
        tabVp.setAdapter(adapter);
        tabOrder.setupWithViewPager(tabVp);
        for (int i = 0; i < tabOrder.getTabCount(); i++) {
            TabLayout.Tab tab = tabOrder.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        for (int i = 0; i < listTab.size(); i++) {
            tabOrder.addTab(tabOrder.newTab().setText(listTab.get(i)));
        }
        tabOrder.getTabAt(tabType).select();
        tabOrder.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabOrder, 20, 20);
            }
        });
        tabVp.setOffscreenPageLimit(titles.length);
    }

    @Override
    public void setUpLisener() {

    }

    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }

    class MyAdapter extends FragmentPagerAdapter {

        private Context context;

        public MyAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.tab_custom, null);
            TextView mTv_Title = (TextView) v.findViewById(R.id.mTv_Title);
            ImageView mImg = (ImageView) v.findViewById(R.id.mImg);
            mTv_Title.setText(titles[position]);
//            mImg.setImageResource(images[position]);
            //添加一行，设置颜色
            mTv_Title.setTextColor(tabOrder.getTabTextColors());//
            return v;
        }
    }
}
