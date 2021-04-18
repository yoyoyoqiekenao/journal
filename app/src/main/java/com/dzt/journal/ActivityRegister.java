package com.dzt.journal;

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


public class ActivityRegister extends ActivityFrame {

	@ViewInject(R.id.tv_title)
	private TitleView titleView;
	@ViewInject(R.id.et_account)
	private EditText etAccount;
	@ViewInject(R.id.et_pwd)
	private EditText etPwd;
	@ViewInject(R.id.et_re_pwd)
	private EditText etRePwd;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_register;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		titleView.setResource(R.drawable.selector_return_btn,
				R.string.title_register);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Event(value = {R.id.btn_register})
	private void onViewClick(View v) {
		switch (v.getId()) {
			case R.id.btn_register:
				register();
				break;
			default:
				break;
		}
	}

	private void register(){
		String account = etAccount.getText().toString().trim();
		String pwd = etPwd.getText().toString().trim();
		String rePwd = etRePwd.getText().toString().trim();
		if(TextUtils.isEmpty(account)
				|| TextUtils.isEmpty(pwd)
				|| TextUtils.isEmpty(rePwd)){
			ToastUtil.getInstance().showMessage("用户名或密码不能为空");
			return;
		}

		if(!pwd.equals(rePwd)){
			ToastUtil.getInstance().showMessage("两次输入的密码不一致");
			return;
		}

		BmobUser ub = new BmobUser();
		ub.setUsername(account);
		ub.setPassword(pwd);
		ub.signUp(new SaveListener<BmobUser>() {
			@Override
			public void done(BmobUser userBean, BmobException e) {
				if(e == null){
					ToastUtil.getInstance().showMessage("注册成功");
				}else{
					ToastUtil.getInstance().showMessage("注册失败");
				}
			}
		});
	}
}
