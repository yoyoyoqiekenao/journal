package com.dzt.journal.settings;

import android.os.Bundle;


import com.dzt.journal.utils.FragmentsManager;
import com.dzt.journal.R;
import com.dzt.journal.base.ActivityFrame;
import com.dzt.journal.base.FragmentBase;

import java.util.ArrayList;
import java.util.List;

public class ActivitySetting extends ActivityFrame {
	public static final int INDEX_SETTING_MAIN = 0x00; //设置主界面
	public static final int INDEX_SETTING_PWD = 0x01;//修改wifi密码
	public static final int INDEX_SETTING_UPDATE = 0x02;//版本更新
	public static final int INDEX_SETTING_ABOUT = 0x03;//关于界面
	public static final int INDEX_SETTING_MODIFY_INCOME_FIRST = 0x04;//添加收入一级分类
	public static final int INDEX_SETTING_MODIFY_PAYOUT_FIRST = 0x05;//添加支出一级分类
	public static final int INDEX_SETTING_MODIRY_INCOME_SECOND = 0x06;//添加收入二级分类
	public static final int INDEX_SETTING_MODIRY_PAYOUT_SECOND = 0x07;//添加支出二级分类
	private int currentIndex;

	private FragmentsManager manager;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_setting;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		List<FragmentBase> fragments = new ArrayList<>();
		fragments.add(new FragmentSettingMain());
		fragments.add(new FragmentModifyClassify());
		fragments.add(new FragmentUpdate());
		fragments.add(new FragmentAbout());
		currentIndex = INDEX_SETTING_MAIN;
		manager = new FragmentsManager(getSupportFragmentManager(),
				fragments, R.id.fragments_container, currentIndex);
	}

	public void showFragmentByIndex(int index) {
		if (manager != null) {
			currentIndex = index;
			manager.showFragmentByIndex(index);
		}
	}

	@Override
	public void onBackPressed() {
		if (currentIndex != INDEX_SETTING_MAIN) {
			showFragmentByIndex(INDEX_SETTING_MAIN);
		} else {
			super.onBackPressed();
		}
	}
}
