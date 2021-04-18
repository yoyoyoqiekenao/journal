package com.dzt.journal.settings;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;


import com.dzt.journal.R;
import com.dzt.journal.base.FragmentBase;
import com.dzt.journal.utils.JLogUtils;
import com.dzt.journal.widgets.SettingItemView;
import com.dzt.journal.widgets.TitleView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_setting_main)
public class FragmentSettingMain extends FragmentBase {

	@ViewInject(R.id.tv_title)
	private TitleView titleView;
	@ViewInject(R.id.item_update_pwd)
	private SettingItemView itemUpdatePwd;
	@ViewInject(R.id.item_auto_record)
	private SettingItemView itemAutoRecord;
	@ViewInject(R.id.item_update)
	private SettingItemView itemUpdate;
	@ViewInject(R.id.item_about)
	private SettingItemView itemAbout;

	@Override
	protected void initWidgets() {
		titleView.setResource(R.drawable.selector_return_btn,
				R.string.title_setting);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Activity activity = getActivity();
				activity.finish();
			}
		});
		boolean isRecord = appPref.getBoolValue("auto_record", false);
		itemAutoRecord.setToggleChecked(isRecord);
		itemAutoRecord.setToggleBtnVisible(autoRecordChangedListener);
	}

	@Event(value = {R.id.item_update_pwd, R.id.item_auto_record,
			R.id.item_update, R.id.item_about})
	private void onViewClick(View view) {
		ActivitySetting activity = (ActivitySetting) getActivity();
		switch (view.getId()) {
			case R.id.item_update_pwd:
				activity.showFragmentByIndex(ActivitySetting.INDEX_SETTING_PWD);
				break;
			case R.id.item_auto_record:
				itemAutoRecord.changeToggleBtnCheck();
				break;
			case R.id.item_update:
				activity.showFragmentByIndex(ActivitySetting.INDEX_SETTING_UPDATE);
				break;
			case R.id.item_about:
				activity.showFragmentByIndex(ActivitySetting.INDEX_SETTING_ABOUT);
				break;
			default:
				break;
		}
	}

	private CompoundButton.OnCheckedChangeListener autoRecordChangedListener = new CompoundButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			JLogUtils.getInstance().i("onCheckedChanged isChecked = " + isChecked);
			appPref.setBoolValue("auto_record", isChecked);
		}
	};
}
