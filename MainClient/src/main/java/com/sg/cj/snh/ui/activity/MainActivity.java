package com.sg.cj.snh.ui.activity;




import android.view.View;

import com.sg.cj.common.base.view.indicator.FixedIndicatorView;
import com.sg.cj.common.base.view.indicator.Indicator;
import com.sg.cj.common.base.view.indicator.IndicatorViewPager;
import com.sg.cj.common.base.view.viewpager.SgViewPager;
import com.sg.cj.snh.R;
import com.sg.cj.snh.adaper.MainFragmentPageAdapter;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.main.MainActivityContract;
import com.sg.cj.snh.presenter.main.MainActivityPresenter;
import com.sg.cj.snh.ui.activity.shopcar.ShopCarActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * author : ${CHENJIE}
 * created at  2018/10/23 08:25
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityContract.View {

  private IndicatorViewPager indicatorViewPager;

  private FixedIndicatorView indicator;

  @Override
  protected void initInject() {
    getActivityComponent().inject(this);
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_main;
  }

  @Override
  protected void initEventAndData() {
    initView();
//    if(!isLogin()) {
//      startLogin();
//    }


  }


  private void initView(){
    SgViewPager viewPager =  findViewById(R.id.tabmain_viewPager);
    indicator =  findViewById(R.id.tabmain_indicator);
    indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
    indicatorViewPager.setAdapter(new MainFragmentPageAdapter(getSupportFragmentManager(),mContext));
    // 禁止viewpager的滑动事件
    viewPager.setCanScroll(false);
    // 设置viewpager保留界面不重新加载的页面数量
    viewPager.setOffscreenPageLimit(5);
    indicator.setOnIndicatorItemClickListener(new Indicator.OnIndicatorItemClickListener() {
      @Override
      public boolean onItemClick(View clickItemView, int position) {

        if(!isLogin()){
          startLogin();
          return true;
        }
        refreshTopbar(position);


        if(position==1){

          //indicator.setCurrentItem(0);
          startWebviewAct(Constants.MAIN_FJ);
          return true;
        }else if(position==3){
          //indicator.setCurrentItem(0);

          startAct(ShopCarActivity.class);
          return true;
        }
        return false;
      }
    });
    //refreshTopbar(0);
  }


  private void refreshTopbar(int position){
    switch (position){
      case 0:

        setTopbarVisiable(View.GONE);
        break;
      case 1:

        setTopbarVisiable(View.GONE);
        break;
      case 2:
        initTitle("发现");
        break;
      case 3:

        setTopbarVisiable(View.GONE);
        break;
      case 4:
        setTopbarVisiable(View.GONE);

        break;
        default:
          setTopbarVisiable(View.GONE);
          break;
    }
  }

  @Override
  public void onBackPressedSupport() {
    if (JCVideoPlayer.backPress()) {
      return;
    }
    super.onBackPressedSupport();
  }



  @Override
  protected void onPause() {
    super.onPause();
    JCVideoPlayer.releaseAllVideos();
  }

}
