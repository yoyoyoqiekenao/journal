package com.dzt.journal.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.dzt.journal.R;
import com.dzt.journal.base.FragmentBase;

import java.util.List;

public class FragmentsManager {

	private List<FragmentBase> mFragments;
	private FragmentManager mFragmentManager; // Fragment所属的Activity
	private int mFragmentContentId; // Activity中所要被替换的区域的id

	private int mCurrentIndex = 0; // 当前Tab页面索引

	public FragmentsManager(FragmentManager frgmanager, List<FragmentBase> fragments,
							int fragmentContentId, int index) {
		this.mFragments = fragments;
		this.mFragmentManager = frgmanager;
		this.mFragmentContentId = fragmentContentId;

		if (mFragments != null && mFragments.size() > 0) {
			final Fragment fragment = fragments.get(index);
			if (!fragment.isAdded()) {
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.add(fragmentContentId, fragment);
				ft.commitAllowingStateLoss();

				mCurrentIndex = index;
			}
		}
	}

	public void showFragmentByIndex(int index) {
		FragmentTransaction ft = obtainFragmentTransaction(index);
		hideCurrentFragment(ft);
		showIndexFragment(ft, index);
		ft.commitAllowingStateLoss();
	}

	public int getCurrentIndex() {
		return mCurrentIndex;
	}

	public FragmentBase getCurrentFragment() {
		return getFragmentByIndex(mCurrentIndex);
	}

	/**
	 * 隐藏当前的fragment
	 */
	private void hideCurrentFragment(FragmentTransaction ft) {
		Fragment fragment = getFragmentByIndex(mCurrentIndex);// 目前当前的tab
		if (fragment != null) {
			ft.hide(fragment); // 隐藏当前tab
			fragment.setUserVisibleHint(false);
		}
	}

	/**
	 * 显示选中的fragment
	 */
	private void showIndexFragment(FragmentTransaction ft, int position) {
		Fragment fragment = getFragmentByIndex(position);// 目标tab
		if (fragment != null) {
			if (fragment.isAdded()) {
				ft.show(fragment); // 显示目标tab
			} else {
				ft.add(mFragmentContentId, fragment);// 添加当前tab
			}

			fragment.setUserVisibleHint(true);
			mCurrentIndex = position; // 更新目标tab为当前tab
		}
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 *
	 * @param index 将要显示的fragment的index
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		// 设置切换动画
		if (index > mCurrentIndex) {
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		} else {
			ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
		}
		return ft;
	}

	private FragmentBase getFragmentByIndex(int index) {
		return mFragments == null ? null : (index < 0 || index >= mFragments.size() ? null
				: mFragments.get(index));
	}
}
