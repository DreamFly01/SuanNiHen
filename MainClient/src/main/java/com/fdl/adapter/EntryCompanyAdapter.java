package com.fdl.adapter;

import android.content.Context;
import android.view.PointerIcon;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.CompanyBean;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/22<p>
 * <p>changeTime：2019/1/22<p>
 * <p>version：1<p>
 */
public class EntryCompanyAdapter extends CommonAdapter<CompanyBean> {


    private Map<Integer,Boolean> map = new TreeMap<>();
    public EntryCompanyAdapter(Context context, int layoutId, List<CompanyBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CompanyBean companyBean, int position) {
        TextView name = holder.getView(R.id.tv_name);
        TextView introduce = holder.getView(R.id.tv_introduce);
        ImageView chose = holder.getView(R.id.iv_item_chose);

        name.setText(companyBean.name);
        introduce.setText(companyBean.introcude);

            if (companyBean.isChose) {
                chose.setBackgroundResource(R.drawable.pay_selete);
            } else {
                chose.setBackgroundResource(R.drawable.pay_normall);
            }
        }

    public void updata(List<CompanyBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
