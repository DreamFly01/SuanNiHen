package com.sg.cj.snh.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.sg.cj.common.base.App;
import com.sg.cj.common.base.fragment.SimpleFragment;
import com.sg.cj.common.base.mvp.BasePresenter;
import com.sg.cj.common.base.mvp.BaseView;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.common.base.view.SgLoadingDialog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.di.component.DaggerFragmentComponent;
import com.sg.cj.snh.di.component.FragmentComponent;
import com.sg.cj.snh.di.module.FragmentModule;
import com.sg.cj.snh.ui.activity.WebViewActivity;
import com.sg.cj.snh.ui.activity.login.LoginActivity;

import javax.inject.Inject;

public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {

	@Inject
	protected T mPresenter;

	protected FragmentComponent getFragmentComponent(){
		return DaggerFragmentComponent.builder()
				.appComponent(PartyApp.getAppComponent())
				.fragmentModule(getFragmentModule())
				.build();
	}

	protected FragmentModule getFragmentModule(){
		return new FragmentModule(this);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		initInject();
		mPresenter.attachView(this);
		super.onViewCreated(view, savedInstanceState);
	}

	protected boolean isLogin(){
		return PartyApp.getAppComponent().getDataManager().getId()!=0;
	}

	@Override
	public void onDestroyView() {
		if (mPresenter != null) {mPresenter.detachView();}
		super.onDestroyView();
	}

	@Override
	public void showErrorMsg(String msg) {
		ToastUtil.shortShow(msg);
	}


	@Override
	public void showLoading() {
		//SgLoadingDialog.getInstance().showLoading(mContext);
	}

	@Override
	public void closeLoading() {
		//SgLoadingDialog.getInstance().dismissLoading();
	}

	@Override
	public void stateError() {

	}

	@Override
	public void stateEmpty() {

	}

	@Override
	public void stateLoading() {

	}

	@Override
	public void startLogin() {

	startActivity(new Intent(mContext, LoginActivity.class));
	}

	@Override
	public void stateMain() {

	}

	protected abstract void initInject();


	private String webViewUrl;

	@Override
	public void startWebview() {
		jumpWebview();
	}

	private void jumpWebview(){
		SgLog.d("webViewUrl == "+webViewUrl);
		Intent intent = new Intent(mContext, WebViewActivity.class);
		intent.putExtra("webViewUrl", webViewUrl );

		if(TextUtils.isEmpty(webViewUrl)){
			showErrorMsg("跳转路径有误");
			return;
		}


		mActivity.startActivity(intent);
	}
	protected void startWebviewAct(String webViewUrl ){

		if(!isLogin()){
			startLogin();
			return;
		}

		this.webViewUrl=webViewUrl;

		mPresenter.doApiLogin(""+PartyApp.getAppComponent().getDataManager().getId());
	}
}