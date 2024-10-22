package com.sg.cj.snh.adaper;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.sg.cj.snh.R;
import java.util.List;

public class SortAdapter extends RvAdapter<String> {

    private int checkedPosition;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public SortAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_sort_list;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view, viewType, listener);
    }

    private class SortHolder extends RvHolder<String> {

        private TextView tvName;
        private View mView;
        private View leftView;

        SortHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            this.mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_sort);
            leftView =  itemView.findViewById(R.id.leftView);
        }

        @Override
        public void bindHolder(String string, int position) {
            tvName.setText(string);
            if (position == checkedPosition) {
                leftView.setVisibility(View.VISIBLE);
                mView.setBackgroundColor(Color.parseColor("#ffffff"));
                //tvName.setTextColor(Color.parseColor("#0068cf"));
            } else {
                mView.setBackgroundColor(Color.parseColor("#f6f6f6"));
               //tvName.setTextColor(Color.parseColor("#1e1d1d"));
                leftView.setVisibility(View.GONE);
            }
        }

    }
}
