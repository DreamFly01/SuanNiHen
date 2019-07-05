package com.sg.cj.snh.base;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sg.cj.common.base.adapter.SgQuickAdapter;
import com.sg.cj.common.base.adapter.ViewHolder;
import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.utils.SgUtils;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author : chenjie
 * creat at 2018/11/26 08:39
 * @Description:
 */
public abstract class BaseListFragment<K, T extends BasePresenter> extends BaseFragment<T> {


  protected List<K> mData = new ArrayList<>();
  @BindView(R.id.listView)
  protected ListView listView;
  @BindView(R.id.pull_refresh)
  RefreshLayout refreshLayout;

  private SgQuickAdapter<K> listAdapter;

  protected boolean isRefresh = false;//是否可刷新

  protected int currentPage = 1;

  protected int defauleSize = 20;


  @Override
  protected int getLayoutId() {
    return R.layout.base_listview_activity;
  }

  @Override
  protected void initEventAndData() {
    initView();
    requestData();
  }


  private void initView() {

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SgUtils.isListEmpty(mData)) {
          return;
        }
        onListItemClick(mData.get(position), position);
      }
    });


    setEnableLoadMore(false);
    setEnableRefresh(false);

    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        loadRefresh();
      }
    });
    refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(RefreshLayout refreshlayout) {
        currentPage++;
        loadMore();
      }
    });

  }


  protected void loadRefresh() {

  }

  protected void loadMore() {

  }


  protected void finishLoadMore() {
    refreshLayout.finishLoadMore();
  }

  /**
   * 设置允许加载更多
   */
  protected void setEnableLoadMore(boolean loadMore) {
    refreshLayout.setEnableLoadMore(loadMore);
  }

  /**
   * 设置允许下拉刷新
   */
  protected void setEnableRefresh(boolean refresh) {
    refreshLayout.setEnableRefresh(refresh);
  }

  /**
   * 刷新数据
   */
  protected void notifyDataSetChanged() {
    if (listAdapter == null) {
      listAdapter = new SgQuickAdapter<K>(getActivity(), mData, getListItemLayout()) {
        @Override
        public void convert(ViewHolder helper, K item, int position) {
          convertView(helper, item, position);
        }
      };

      listView.setAdapter(listAdapter);
    } else {
      listAdapter.notifyDataSetChanged();
    }
  }

  protected abstract int getListItemLayout();

  protected abstract void requestData();

  protected abstract void convertView(ViewHolder helper, K item, int position);


  protected abstract void onListItemClick(K item, int position);


}
