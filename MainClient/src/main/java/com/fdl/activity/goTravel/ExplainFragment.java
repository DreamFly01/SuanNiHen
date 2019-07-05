package com.fdl.activity.goTravel;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/7<p>
 * <p>changeTime：2019/5/7<p>
 * <p>version：1<p>
 */
public class ExplainFragment extends DialogFragment {
    @BindView(R.id.tv_back)
    TextView tvBack;
    Unbinder unbinder;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.web_view)
    WebView webView;

    private LinearLayout view;
    private Bundle bundle;
    private String content;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), (int) (dm.heightPixels * 0.5));
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = (LinearLayout) inflater.inflate(R.layout.dialog_explain_layout, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bundle = getArguments();
        if (null != bundle) {
            content = bundle.getString("data");
        }
        unbinder = ButterKnife.bind(this, view);
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
//        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        getDialog().dismiss();
    }
}
