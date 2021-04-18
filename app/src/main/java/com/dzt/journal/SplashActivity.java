package com.dzt.journal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dzt.journal.base.ActivityBase;
import com.dzt.journal.main.MainActivity;

public class SplashActivity extends ActivityBase {

	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					startActivity(MainActivity.class, null);
					finish();
					break;
				default:
					break;
			}
			return false;
		}
	});

	@Override
	protected int getLayoutId() {
		return R.layout.activity_splash;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		handler.sendEmptyMessageDelayed(0, 1000);
	}
}
