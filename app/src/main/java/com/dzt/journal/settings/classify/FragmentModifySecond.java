package com.dzt.journal.settings.classify;

import android.annotation.SuppressLint;

import com.dzt.journal.R;
import com.dzt.journal.base.FragmentBase;

import org.xutils.view.annotation.ContentView;

/**
 * Created by M02323 on 2017/9/22.
 */

@ContentView(R.layout.fragment_modify_second)
public class FragmentModifySecond extends FragmentBase {
	private ClassifyType classify = ClassifyType.INCOME;

	@SuppressLint("ValidFragment")
	private FragmentModifySecond() {
		super();
	}

	@SuppressLint("ValidFragment")
	private FragmentModifySecond(ClassifyType classify) {
		this();
		this.classify = classify;
	}

	public static FragmentModifySecond newInstance(ClassifyType classify) {
		return new FragmentModifySecond(classify);
	}

	@Override
	protected void initWidgets() {

	}
}
