package com.fdl.activity.coupons;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.jpush.Logger;
import com.fdl.utils.IsBang;
import com.sg.cj.snh.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/27<p>
 * <p>changeTime：2019/4/27<p>
 * <p>version：1<p>
 */
public class MyCouponsActivity extends BaseActivity {
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
    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.tab_vp)
    ViewPager tabVp;
    private MyAdapter adapter;
    private String[] titles = {"未使用", "已使用", "已过期"};
    private List<String> listTab = new ArrayList<>();
    //    private int images[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private List<Fragment> list = new ArrayList<>();
    private int tabType;
    private Bundle bundle;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mycoupons_layout);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        heardTitle.setText("优惠券");
        for (int i = 0; i < titles.length; i++) {
            MyCouponsFragment couponsFragment = new MyCouponsFragment();
            bundle = new Bundle();
            bundle.putInt("type", i);
            couponsFragment.setArguments(bundle);
            list.add(couponsFragment);
        }
        adapter = new MyAdapter(getSupportFragmentManager(), this);
        tabVp.setAdapter(adapter);
        tabVp.setOffscreenPageLimit(2);

        tabVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Logger.i("test", "ViewPager onPageScrolled position = " + position + " , positionOffset"
//                        + positionOffset + " , positionOffsetPixels" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Logger.i("test", "ViewPager onPageSelected = " + position);
                MyCouponsEvent event = new MyCouponsEvent();
                event.position = position;
                EventBus.getDefault().post(event);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.i("test", "ViewPager onPageScrollStateChanged = " + state);
            }
        });


        tabOrder.setupWithViewPager(tabVp);
        for (int i = 0; i < tabOrder.getTabCount(); i++) {
            TabLayout.Tab tab = tabOrder.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        for (int i = 0; i < listTab.size(); i++) {
            tabOrder.addTab(tabOrder.newTab().setText(listTab.get(i)));
        }
        tabOrder.getTabAt(tabType).select();
//        tabOrder.post(new Runnable() {
//            @Override
//            public void run() {
//                setIndicator(tabOrder, 40, 40);
//            }
//        });

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
