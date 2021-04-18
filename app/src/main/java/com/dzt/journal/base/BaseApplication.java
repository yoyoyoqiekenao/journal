package com.dzt.journal.base;

import android.app.Application;
import android.content.Context;

import com.dzt.journal.exceptions.BaseExceptionHandler;
import com.dzt.journal.exceptions.LocalFileHandler;


public abstract class BaseApplication extends Application {

	public Context applicationContext;

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = getApplicationContext();
		if (getDefaultUncaughtExceptionHandler() == null) {
			Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(
					applicationContext));
		} else {
			Thread.setDefaultUncaughtExceptionHandler(getDefaultUncaughtExceptionHandler());
		}
	}

	public abstract BaseExceptionHandler getDefaultUncaughtExceptionHandler();
}
