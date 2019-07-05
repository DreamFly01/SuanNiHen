package com.sg.cj.snh.ui.activity.category;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.sg.cj.snh.R;
import com.sg.cj.snh.adaper.RvListener;
import com.sg.cj.snh.adaper.SortAdapter;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.bean.SortBean;
import com.sg.cj.snh.contract.category.CategoryContract;
import com.sg.cj.snh.model.response.category.CategoryResponse;
import com.sg.cj.snh.presenter.category.CategoryPresenter;
import com.sg.cj.snh.ui.fragment.category.CategoryFragment;
import com.sg.cj.snh.ui.fragment.category.ItemHeaderDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * author : ${CHENJIE}
 * created at  2018/11/30 08:57
 * e_mail : chenjie_goodboy@163.com
 * describle : 分类
 */
public class CategoryActivity extends BaseActivity<CategoryPresenter> implements CategoryContract.View,CheckListener {

  @BindView(R.id.rv_sort)
  RecyclerView rvSort;
  @BindView(R.id.lin_fragment)
  FrameLayout linFragment;
  private LinearLayoutManager mLinearLayoutManager;

  private SortAdapter mSortAdapter;

  List<String> list = new ArrayList<>();



  private Map<String ,SortBean.CategoryOneArrayBean> oneArrayBeanMap=new HashMap<>();
  private SortBean mSortBean=new SortBean();

  private int targetPosition;//点击左边某一个具体的item的位置
  private boolean isMoved;

  private CategoryFragment categoryFragment;

  @Override
  protected void initInject() {
    getActivityComponent().inject(this);
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_category;
  }

  @Override
  protected void initEventAndData() {
    initView();
    mPresenter.doGetclassify();
  }

  @Override
  public void displayClassifySuccess(CategoryResponse categoryResponse) {

    if(null==categoryResponse.data){
      return;
    }
    Collections.sort(categoryResponse.data);
    for(CategoryResponse.DataBean dataBean:categoryResponse.data){
      if(dataBean.Depth==1){
        SortBean.CategoryOneArrayBean oneArrayBean=new SortBean.CategoryOneArrayBean();
        oneArrayBean.setDepth(dataBean.Depth);
        oneArrayBean.setId(dataBean.Id);
        oneArrayBean.setImageUrl(dataBean.ImageUrl);
        oneArrayBean.setLinkUrl(dataBean.linkUrl);
        oneArrayBean.setOrderBy(dataBean.OrderBy);
        oneArrayBean.setParentId(dataBean.ParentId);
        oneArrayBean.setTitle(dataBean.Title);
        oneArrayBeanMap.put(""+dataBean.Id,oneArrayBean);
        mSortBean.getCategoryOneArray().add(oneArrayBean);
        list.add(oneArrayBean.getTitle());
      }
      if(dataBean.Depth==2){

        if(oneArrayBeanMap.containsKey(""+dataBean.ParentId)){
          SortBean.CategoryOneArrayBean.CategoryTwoArrayBean twoArrayBean=new SortBean.CategoryOneArrayBean.CategoryTwoArrayBean();
          twoArrayBean.setDepth(dataBean.Depth);
          twoArrayBean.setId(dataBean.Id);
          twoArrayBean.setImageUrl(dataBean.ImageUrl);
          twoArrayBean.setLinkUrl(dataBean.linkUrl);
          twoArrayBean.setOrderBy(dataBean.OrderBy);
          twoArrayBean.setParentId(dataBean.ParentId);
          twoArrayBean.setTitle(dataBean.Title);
          oneArrayBeanMap.get(""+dataBean.ParentId).getCategoryTwoArray().add(twoArrayBean);
        }
      }
    }
    mSortAdapter.notifyDataSetChanged();
    createFragment();
  }

  private void initView(){

    mLinearLayoutManager = new LinearLayoutManager(mContext);
    rvSort.setLayoutManager(mLinearLayoutManager);
    DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
    rvSort.addItemDecoration(decoration);

    mSortAdapter = new SortAdapter(mContext, list, new RvListener() {
      @Override
      public void onItemClick(int id, int position) {
        if (categoryFragment != null) {
          isMoved = true;
          targetPosition = position;
          setChecked(position, true);
        }
      }
    });
    rvSort.setAdapter(mSortAdapter);

  }


  public void createFragment() {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    categoryFragment = new CategoryFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList("right", mSortBean.getCategoryOneArray());
    categoryFragment.setArguments(bundle);
    categoryFragment.setListener(this);
    fragmentTransaction.add(R.id.lin_fragment, categoryFragment);
    fragmentTransaction.commit();
  }


  private void setChecked(int position, boolean isLeft) {
    Log.d("p-------->", String.valueOf(position));
    if (isLeft) {
      mSortAdapter.setCheckedPosition(position);
      //此处的位置需要根据每个分类的集合来进行计算
      int count = 0;
      for (int i = 0; i < position; i++) {
        count += mSortBean.getCategoryOneArray().get(i).getCategoryTwoArray().size();
      }
      count += position;
      categoryFragment.setData(count);
      ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag
    } else {
      if (isMoved) {
        isMoved = false;
      } else
        mSortAdapter.setCheckedPosition(position);
      ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

    }
    moveToCenter(position);

  }

  //将当前选中的item居中
  private void moveToCenter(int position) {
    //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
    View childAt = rvSort.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
    if (childAt != null) {
      int y = (childAt.getTop() - rvSort.getHeight() / 2);
      rvSort.smoothScrollBy(0, y);
    }

  }




  @Override
  public void check(int position, boolean isScroll) {
    setChecked(position, isScroll);

  }

}
