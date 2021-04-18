package com.dzt.journal.settings.classify;

import android.graphics.Bitmap;

/**
 * Created by M02323 on 2017/3/28.
 */

public class Classify {
	private Bitmap icon;
	private String name;
	private int index;

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
