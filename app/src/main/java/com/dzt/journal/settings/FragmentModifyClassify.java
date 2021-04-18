package com.dzt.journal.settings;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dzt.journal.R;
import com.dzt.journal.base.FragmentAdapter;
import com.dzt.journal.base.FragmentBase;
import com.dzt.journal.settings.classify.ClassifyType;
import com.dzt.journal.settings.classify.FragmentModifyFirst;
import com.dzt.journal.widgets.TitleView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by M02323 on 2017/3/31.
 */

@ContentView(R.layout.fragment_modify_classify)
public class FragmentModifyClassify extends FragmentBase implements TabLayout.OnTabSelectedListener {
	@ViewInject(R.id.tv_title)
	private TitleView titleView;
	@ViewInject(R.id.tl_tab)
	private TabLayout indicator;
	@ViewInject(R.id.viewpager)
	protected ViewPager mFileViewPager;

	protected int mCurrentTabIndex = 0;
	private FragmentModifyFirst fragmentIncome;
	private FragmentModifyFirst fragmentPayout;

	@Override
	protected void initWidgets() {
		titleView.setResource(R.drawable.selector_return_btn,
				R.string.add_first_classify);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySetting activity = (ActivitySetting) getActivity();
				activity.showFragmentByIndex(ActivitySetting.INDEX_SETTING_MAIN);
			}
		});
		titleView.setRightDetail(R.drawable.selector_ok_btn);
		titleView.setRightClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentTabIndex == 0) {
					fragmentIncome.save();
				} else {
					fragmentPayout.save();
				}
				ActivitySetting activity = (ActivitySetting) getActivity();
				activity.showFragmentByIndex(ActivitySetting.INDEX_SETTING_MAIN);
			}
		});

		fragmentIncome = FragmentModifyFirst.newInstance(ClassifyType.INCOME);
		fragmentPayout = FragmentModifyFirst.newInstance(ClassifyType.PAYOUT);
		indicator.addOnTabSelectedListener(this);
		FragmentAdapter adapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
		adapter.addFragment(fragmentIncome, "收入");
		adapter.addFragment(fragmentPayout, "支出");
		mFileViewPager.setAdapter(adapter);
		indicator.setupWithViewPager(mFileViewPager);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		indicator.removeOnTabSelectedListener(this);
	}

	@Override
	public void onTabSelected(TabLayout.Tab tab) {
		if(tab.getText().equals("收入")){
			mCurrentTabIndex = 0;
		}else{
			mCurrentTabIndex = 1;
		}
	}

	@Override
	public void onTabUnselected(TabLayout.Tab tab) {

	}

	@Override
	public void onTabReselected(TabLayout.Tab tab) {

	}
}
