package com.fdl.updata;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sg.cj.snh.R;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/8<p>
 * <p>changeTime：2018/12/8<p>
 * <p>version：1<p>
 */
public class UpdateDialog {

    private Dialog mDialog;
    private Activity mActivity;
    private MyBtnclickLisener myBtnclickLisener;

    public interface MyBtnclickLisener {

        void onOkClick(View v);
        void onCancel(View v);

    }


    public UpdateDialog(final Activity context) {
        this.mActivity = context;
    }


    public void showDialog(String showMsg, MyBtnclickLisener myTwoBtnclickLisener,
                           boolean isAutoCanlce, boolean isHiddenCross) {

        if (null != this.mActivity && !mActivity.isFinishing()) {
            this.myBtnclickLisener = myTwoBtnclickLisener;
            Create(showMsg, isAutoCanlce, isHiddenCross);
        }

    }


    private Button btn_ok;
    private ProgressBar progressBar;
    private ImageView iv_cross;

    public void setBtnEnable(boolean enable) {
        btn_ok.setEnabled(enable);
    }

    public void showPrograss() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void Create(String showMsg, boolean c, boolean isHiddenCross) {

        if (null != this.mActivity && !this.mActivity.isFinishing()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
            mDialog = builder.create();
            mDialog.setCanceledOnTouchOutside(c);
            mDialog.setCancelable(c);
            mDialog.show();

            View view = ((LayoutInflater) this.mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.new_dialog_update_layout, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_updataT);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_UpdataContent);
            progressBar = (ProgressBar) view.findViewById(R.id.pb_progressbar);
            iv_cross = (ImageView) view.findViewById(R.id.iv_delete);

            if (isHiddenCross) {
                iv_cross.setVisibility(View.GONE);
            } else {
                iv_cross.setVisibility(View.VISIBLE);
            }

            btn_ok = (Button) view.findViewById(R.id.btn_UpdataOk);

            tv_title.setText("新版上线");
            tv_content.setText(Html.fromHtml(showMsg));
            iv_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    myBtnclickLisener.onCancel(v);
                }
            });

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myBtnclickLisener.onOkClick(v);
                }
            });
            mDialog.setContentView(view);

        }
    }

    public void setPrograss(int prograss) {
        progressBar.setProgress(prograss);
    }

    public void DismissMyDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }
}
