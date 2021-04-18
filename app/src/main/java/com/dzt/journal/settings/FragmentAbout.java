package com.dzt.journal.settings;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.dzt.journal.R;
import com.dzt.journal.base.FragmentBase;
import com.dzt.journal.widgets.TitleView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by M02323 on 2017/3/31.
 */

@ContentView(R.layout.fragment_about)
public class FragmentAbout extends FragmentBase {
	@ViewInject(R.id.tv_title)
	private TitleView titleView;
	@ViewInject(R.id.iv_app_icon)
	private ImageView ivAppIcon;
	@ViewInject(R.id.tv_version)
	private TextView tvVersion;

	@Override
	protected void initWidgets() {
		titleView.setResource(R.drawable.selector_return_btn,
				R.string.text_about);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySetting activity = (ActivitySetting) getActivity();
				activity.showFragmentByIndex(ActivitySetting.INDEX_SETTING_MAIN);
			}
		});
		showAppInfo();
	}

	private void showAppInfo() {
		ivAppIcon.setImageResource(R.mipmap.ic_launcher);
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			if (info == null)
				return;
			tvVersion.setText(getString(R.string.text_current_version, info.versionName));
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
