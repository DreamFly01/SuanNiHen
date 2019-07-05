package com.fdl.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.fdl.bean.ProductDetailsBean;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/25<p>
 * <p>changeTime：2018/12/25<p>
 * <p>version：1<p>
 */
public class SkuAdapter extends CommonAdapter<ProductDetailsBean.Suk> {
    private DataListener dataListener;

    public SkuAdapter(Context context, int layoutId, List<ProductDetailsBean.Suk> datas, DataListener dataListener) {
        super(context, layoutId, datas);
        this.dataListener = dataListener;
    }

    public SkuAdapter(Context context, int layoutId, List<ProductDetailsBean.Suk> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsBean.Suk suk, int position) {
        TextView name = holder.getView(R.id.tv_sku_name);
        TextView text = holder.getView(R.id.tv_test);
        LabelsView labelsView = holder.getView(R.id.labels);

        List<ProductDetailsBean.ShopNormsValuesListView> listViews = new ArrayList<>();
        listViews = suk.ShopNormsValuesListView;
        labelsView.setLabels(listViews, new LabelsView.LabelTextProvider<ProductDetailsBean.ShopNormsValuesListView>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, ProductDetailsBean.ShopNormsValuesListView data) {
                return data.NormValue;
            }
        });

        for (int i = 0; i < mDatas.size(); i++) {
                int NormsId =  mDatas.get(i).ShopNormsValuesListView.get(0).Id;
                String NormsInfo = mDatas.get(i).ShopNormsValuesListView.get(0).NormValue;
                int GoodsId = mDatas.get(i).ShopNormsValuesListView.get(0).ShopNormNameId;
                dataListener.send(i, NormsId, NormsInfo);
        }

        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position1) {
                int NormsId = mDatas.get(position).ShopNormsValuesListView.get(position1).Id;
                String NormsInfo = mDatas.get(position).ShopNormsValuesListView.get(position1).NormValue;
                int GoodsId = mDatas.get(position).ShopNormsValuesListView.get(position1).ShopNormNameId;
                dataListener.send(position, NormsId, NormsInfo);
            }
        });
        name.setText(mDatas.get(position).ShopNormsNamesView.NormName);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public interface DataListener {
        void send(int postion, int NormsId, String NormsInfo);
    }
}
