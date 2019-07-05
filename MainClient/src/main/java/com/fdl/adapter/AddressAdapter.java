package com.fdl.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.activity.account.EditAddressActivity;
import com.fdl.bean.AddressBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.JumpUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/29<p>
 * <p>changeTime：2018/12/29<p>
 * <p>version：1<p>
 */
public class AddressAdapter extends CommonAdapter<AddressBean> {

    private DialogUtils dialogUtils;

    private isZero myisZero;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        dialogUtils = new DialogUtils(mContext);
        return super.onCreateViewHolder(parent, viewType);
    }

    public AddressAdapter(Context context, int layoutId, List<AddressBean> datas, AddressAdapter.isZero isZero) {
        super(context, layoutId, datas);
        myisZero = isZero;
    }

    @Override
    protected void convert(ViewHolder holder, AddressBean addressBean, int position) {
        TextView name = holder.getView(R.id.tv_name);
        TextView phone = holder.getView(R.id.tv_phone1);
        TextView address = holder.getView(R.id.tv_address);
        TextView isDefult = holder.getView(R.id.tv_isDefult);
        LinearLayout edit = holder.getView(R.id.ll_edit);
        LinearLayout del = holder.getView(R.id.ll_del);


        name.setText(addressBean.RealName);
        phone.setText(addressBean.TelPhone);
        address.setText(addressBean.AreaAddress);
        if(addressBean.IsDefault == 1){
            isDefult.setVisibility(View.VISIBLE);
        }else {
            isDefult.setVisibility(View.GONE);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("address",addressBean);
                JumpUtils.dataJump((Activity) mContext, EditAddressActivity.class,bundle,false);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUtils.twoBtnDialog("是否删除地址？", new DialogUtils.ChoseClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        delAddress(addressBean.Id,position);
                        dialogUtils.dismissDialog();
                    }

                    @Override
                    public void onCancelClick(View v) {
                        dialogUtils.dismissDialog();
                    }
                },true);
            }
        });
    }

    private void delAddress(int id, int position){
        RequestClient.DelAddress(id+"", mContext, new NetSubscriber<BaseResultBean>(mContext,true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                dialogUtils.noBtnDialog("删除成功");
                mDatas.remove(position);
                notifyDataSetChanged();
                if(mDatas.size()<=0){
                    myisZero.isZero(true);
                }else {
                    myisZero.isZero(false);
                }
            }
        });
    }

    public interface isZero{
        void isZero(boolean flag);
    }
}
