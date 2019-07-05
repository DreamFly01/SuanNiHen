package com.sg.cj.snh.ui.fragment.category;



import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.sg.cj.snh.R;
import com.sg.cj.snh.adaper.ClassifyDetailAdapter;
import com.sg.cj.snh.adaper.RvListener;
import com.sg.cj.snh.base.BaseFragment;
import com.sg.cj.snh.bean.RightBean;
import com.sg.cj.snh.bean.SortBean;
import com.sg.cj.snh.contract.category.CategoryFragmentContract;
import com.sg.cj.snh.presenter.category.CategoryFragmentPresenter;
import com.sg.cj.snh.ui.activity.category.CheckListener;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;


/**
 * author : ${CHENJIE}
 * created at  2018/11/30 21:34
 * e_mail : chenjie_goodboy@163.com
 * describle : 分类
 */
public class CategoryFragment extends BaseFragment<CategoryFragmentPresenter> implements CategoryFragmentContract.View, CheckListener {

  @BindView(R.id.rv)
  RecyclerView rv;

  private ClassifyDetailAdapter mAdapter;
  private GridLayoutManager mManager;
  private List<RightBean> mDatas = new ArrayList<>();
  private ItemHeaderDecoration mDecoration;
  private boolean move = false;
  private int mIndex = 0;



  private CheckListener checkListener;

  public void setListener(CheckListener listener) {
    this.checkListener = listener;
  }

  @Override
  protected void initInject() {
    getFragmentComponent().inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_category;
  }

  @Override
  protected void initEventAndData() {
initView();
  }

  private void initView(){
    rv.addOnScrollListener(new RecyclerViewListener());
    mManager = new GridLayoutManager(mContext, 3);
    //通过isTitle的标志来判断是否是title
    mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        if(position<0){
          return 0;
        }
        return mDatas.get(position).getDepth()==1 ? 3 : 1;
      }
    });
    rv.setLayoutManager(mManager);
    mAdapter = new ClassifyDetailAdapter(mContext, mDatas, new RvListener() {
      @Override
      public void onItemClick(int id, int position) {
        String content = "";
        switch (id) {
          case R.id.root:
            content = "title";
            break;
          case R.id.content:
            content = "content";
            break;

        }

      }
    });

    rv.setAdapter(mAdapter);
    mDecoration = new ItemHeaderDecoration(mContext, mDatas);
    rv.addItemDecoration(mDecoration);
    mDecoration.setCheckListener(checkListener);
    initData();


  }
  private void initData() {
    ArrayList<SortBean.CategoryOneArrayBean> rightList = getArguments().getParcelableArrayList("right");
    for (int i = 0; i < rightList.size(); i++) {
      SortBean.CategoryOneArrayBean bean=rightList.get(i);
      RightBean head = new RightBean();
      //头部设置为true
      head.setDepth(bean.getDepth());
      head.setId(bean.getId());
      head.setImageUrl(bean.getImageUrl());
      head.setLinkUrl(bean.getLinkUrl());
      head.setOrderBy(bean.getOrderBy());
      head.setParentId(bean.getParentId());
      head.setTitle(bean.getTitle());
      head.setTag(String.valueOf(i));
      mDatas.add(head);
      List<SortBean.CategoryOneArrayBean.CategoryTwoArrayBean> categoryTwoArray = rightList.get(i).getCategoryTwoArray();
      for (int j = 0; j < categoryTwoArray.size(); j++) {
        SortBean.CategoryOneArrayBean.CategoryTwoArrayBean b=categoryTwoArray.get(j);
        RightBean body = new RightBean();
        body.setDepth(b.getDepth());
        body.setId(b.getId());
        body.setImageUrl(b.getImageUrl());
        body.setLinkUrl(b.getLinkUrl());
        body.setOrderBy(b.getOrderBy());
        body.setParentId(b.getParentId());
        body.setTitle(b.getTitle());


        body.setTag(String.valueOf(i));

        mDatas.add(body);
      }

    }

    mAdapter.notifyDataSetChanged();
    mDecoration.setData(mDatas);
  }

  public void setData(int n) {
    mIndex = n;
    rv.stopScroll();
    smoothMoveToPosition(n);
  }

  private void smoothMoveToPosition(int n) {
    int firstItem = mManager.findFirstVisibleItemPosition();
    int lastItem = mManager.findLastVisibleItemPosition();
    Log.d("first--->", String.valueOf(firstItem));
    Log.d("last--->", String.valueOf(lastItem));
    if (n <= firstItem) {
      rv.scrollToPosition(n);
    } else if (n <= lastItem) {
      Log.d("pos---->", String.valueOf(n) + "VS" + firstItem);
      int top = rv.getChildAt(n - firstItem).getTop();
      Log.d("top---->", String.valueOf(top));
      rv.scrollBy(0, top);
    } else {
      rv.scrollToPosition(n);
      move = true;
    }
  }
  @Override
  public void check(int position, boolean isScroll) {
    checkListener.check(position, isScroll);

  }




  private class RecyclerViewListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
      if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
        move = false;
        int n = mIndex - mManager.findFirstVisibleItemPosition();
        Log.d("n---->", String.valueOf(n));
        if (0 <= n && n < rv.getChildCount()) {
          int top = rv.getChildAt(n).getTop();
          Log.d("top--->", String.valueOf(top));
          rv.smoothScrollBy(0, top);
        }
      }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      if (move) {
        move = false;
        int n = mIndex - mManager.findFirstVisibleItemPosition();
        if (0 <= n && n < rv.getChildCount()) {
          int top = rv.getChildAt(n).getTop();
          rv.scrollBy(0, top);
        }
      }
    }
  }


}
