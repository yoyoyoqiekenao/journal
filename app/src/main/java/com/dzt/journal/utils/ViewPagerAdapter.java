package com.dzt.journal.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import com.dzt.journal.base.FragmentBase;

import java.util.List;

/**
 * 文件名 : ViewPagerAdapter.java V1.0 May 10,2015<br>
 * 描　述 : Fragment 切换器<br>
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

	private List<FragmentBase> fragmentsList;
	private List<String> titles;

	public ViewPagerAdapter(FragmentManager fm,
							List<FragmentBase> fragments, List<String> titles) {
		super(fm);
		this.fragmentsList = fragments;
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		if (fragmentsList != null && position >= 0 && position < fragmentsList.size()) {
			return fragmentsList.get(position);
		}
		return null;
	}

	@Override
	public int getCount() {
		return fragmentsList == null ? 0 : fragmentsList.size();
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}
}
