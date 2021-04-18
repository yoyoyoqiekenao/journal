package com.dzt.journal.record;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dzt.journal.R;
import com.dzt.journal.base.ActivityFrame;
import com.dzt.journal.base.FragmentAdapter;
import com.dzt.journal.widgets.TitleView;

import org.xutils.view.annotation.ViewInject;

public class RecordActivity extends ActivityFrame implements TabLayout.OnTabSelectedListener  {

	private PayoutFragment payoutFragment;
	private IncomeFragment incomeFragment;
	/**
	 *保存viewpage当前位置，供子fragment判断当前是否是本fragment然后onEvent接收事件处理item动画
	 */
	protected int mCurrentTabIndex = 0;

	@ViewInject(R.id.titleview)
	private TitleView titleView;
	@ViewInject(R.id.tl_tab)
	private TabLayout indicator;
	@ViewInject(R.id.viewpager)
	protected ViewPager mFileViewPager;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_record;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		incomeFragment = new IncomeFragment();
		payoutFragment = new PayoutFragment();
		indicator.addOnTabSelectedListener(this);
		FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
		adapter.addFragment(incomeFragment, "收入");
		adapter.addFragment(payoutFragment, "支出");
		mFileViewPager.setAdapter(adapter);
		indicator.setupWithViewPager(mFileViewPager);

		titleView.setResource(R.drawable.selector_return_btn, R.string.remember);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		titleView.setRightDetail(R.drawable.selector_ok_btn);
		titleView.setRightClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentTabIndex == 0) {
					incomeFragment.save();
				} else {
					payoutFragment.save();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
