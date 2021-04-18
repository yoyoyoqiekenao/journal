package com.dzt.journal.consume;

import android.graphics.Bitmap;

/**
 * Created by M02323 on 2017/2/14.
 */

public class IncomePayout {
	private Bitmap bitmap;
	private String type;
	private String time;
	private Double money;

	public IncomePayout() {

	}

	public IncomePayout(Bitmap bitmap, String type, String time, Double money) {
		this.bitmap = bitmap;
		this.type = type;
		this.time = time;
		this.money = money;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "IncomePayout{" +
				", type='" + type + '\'' +
				", time='" + time + '\'' +
				", money=" + money +
				'}';
	}
}
