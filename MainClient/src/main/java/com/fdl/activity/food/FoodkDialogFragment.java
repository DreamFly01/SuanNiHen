package com.fdl.activity.food;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.adapter.FoodDialogAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.FoodPredeterminelistBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.ToastUtils;
import com.fdl.wedgit.RecycleViewDivider;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/1<p>
 * <p>changeTime：2019/4/1<p>
 * <p>version：1<p>
 */
public class FoodkDialogFragment extends DialogFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.tv_predetetermineNum)
    TextView title;
    @BindView(R.id.ll_add_predetermine)
    LinearLayout llAddPredetermine;
    private LinearLayout view;

    private FoodDialogAdapter adapter;
    private List<FoodPredeterminelistBean> data = new ArrayList<>();
    private int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = (LinearLayout) inflater.inflate(R.layout.dialog_predeterminelist_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.height = (int) (display.getHeight() * 0.6);
        getDialog().getWindow().setAttributes(layoutParams);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setContentView(view);
        getDialog().getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        getDialog().setCanceledOnTouchOutside(true);
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = getArguments().getParcelableArrayList("data");
        id = getArguments().getInt("storeId");
        if (data.size() >= 2) {
            llAddPredetermine.setVisibility(View.GONE);
        }
        title.setText("你已预定"+data.size()+"个订单");
        setView();
    }

    private void setView() {
        adapter = new FoodDialogAdapter(R.layout.item_predeterminelist_layout, data);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayout.VERTICAL, R.drawable.line_2_gray));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_item:
                        Bundle bundle = new Bundle();
                        bundle.putInt("storeId", id);
                        bundle.putInt("applyId", data.get(position).Id);
                        bundle.putInt("isMenuShow",1);
                        JumpUtils.dataJump(getActivity(), FoodPredetermineStepTwoActivity.class, bundle, false);
                        break;
                    case R.id.tv_delete:
                        cancleOrder(data.get(position).Id, position);

                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        this.onDestroyView();
    }

    private void cancleOrder(int applyId, int postion) {
        RequestClient.CanclePredeterminerOrder(applyId, getContext(), new NetSubscriber<BaseResultBean>(getContext(), true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                ToastUtils.toast("取消成功");
                data.remove(postion);
                adapter.setNewData(data);
                if (data.size() >= 2) {
                    llAddPredetermine.setVisibility(View.GONE);
                }else {
                    llAddPredetermine.setVisibility(View.VISIBLE);
                }
                if(data.size()<=0){
                    title.setText("暂无预定订单");
                }else {
                    title.setText("你已预定"+data.size()+"个订单");
                }
            }
        });
    }

    @OnClick(R.id.ll_add_predetermine)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putInt("storeId",id);
        JumpUtils.dataJump(getActivity(),FoodPredetermineStepOneActivity.class,bundle,false);
    }
}
