package com.dzt.journal;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dzt.journal.base.ActivityFrame;
import com.dzt.journal.utils.ToastUtil;
import com.dzt.journal.widgets.TitleView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ActivityLogin extends ActivityFrame {

	@ViewInject(R.id.tv_title)
	private TitleView titleView;
	@ViewInject(R.id.et_account)
	private EditText etAccount;
	@ViewInject(R.id.et_pwd)
	private EditText etPwd;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_login;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		titleView.setResource(R.drawable.selector_return_btn,
				R.string.title_login);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
	}

	@Event(value = {R.id.btn_login, R.id.btn_register})
	private void onViewClick(View v) {
		switch (v.getId()) {
			case R.id.btn_login:
				login();
				break;
			case R.id.btn_register:
				startActivity(ActivityRegister.class, null);
				break;
			default:
				break;
		}
	}

	private void login(){
		final String account = etAccount.getText().toString().trim();
		String pwd = etPwd.getText().toString().trim();
		if(TextUtils.isEmpty(account)
				|| TextUtils.isEmpty(pwd)){
			ToastUtil.getInstance().showMessage("用户名或密码不能为空");
			return;
		}
		BmobUser ub = new BmobUser();
		ub.setUsername(account);
		ub.setPassword(pwd);
		ub.login(new SaveListener<BmobUser>() {
			@Override
			public void done(BmobUser userBean, BmobException e) {
				if(e == null){
					LocalApplication.userName = account;
					ToastUtil.getInstance().showMessage("登录成功");
					finish();
				}else{
					LocalApplication.userName = null;
					ToastUtil.getInstance().showMessage("登录失败");
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_OK);
		super.onBackPressed();
	}
}
