package com.dzt.journal.settings;

import android.view.View;


import com.dzt.journal.R;
import com.dzt.journal.base.FragmentBase;
import com.dzt.journal.widgets.TitleView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by M02323 on 2017/3/31.
 */

@ContentView(R.layout.fragment_update)
public class FragmentUpdate extends FragmentBase {
	@ViewInject(R.id.tv_title)
	private TitleView titleView;

	@Override
	protected void initWidgets() {
		titleView.setResource(R.drawable.selector_return_btn,
				R.string.text_update);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySetting activity = (ActivitySetting) getActivity();
				activity.showFragmentByIndex(ActivitySetting.INDEX_SETTING_MAIN);
			}
		});
	}
}
