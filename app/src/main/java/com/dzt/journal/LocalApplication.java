package com.dzt.journal;

import com.dzt.journal.base.BaseApplication;
import com.dzt.journal.dao.TbBudgetDao;
import com.dzt.journal.dao.TbClassifyDao;
import com.dzt.journal.dao.TbJournalDao;
import com.dzt.journal.dao.TbSubclassDao;
import com.dzt.journal.exceptions.BaseExceptionHandler;
import com.dzt.journal.exceptions.LocalFileHandler;
import com.dzt.journal.utils.AppPref;
import com.dzt.journal.utils.DensityUtil;
import com.dzt.journal.utils.JFileKit;
import com.dzt.journal.utils.JLogUtils;
import com.dzt.journal.utils.ToastUtil;

import java.io.File;

import cn.bmob.v3.Bmob;

public class LocalApplication extends BaseApplication {

	private static LocalApplication instance;
	public static String userName = null;
	public int screenW;
	public int screenH;

	public static LocalApplication getInstance() {
		if (instance == null) {
			instance = new LocalApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		JLogUtils.getInstance().setTag("journal-->");
		AppPref.getInstance().init(this);
		ToastUtil.getInstance().init(this);
		TbBudgetDao.getInstance().init(this);
		TbJournalDao.getInstance().init(this);
		TbClassifyDao.getInstance().init(this);
		TbSubclassDao.getInstance().init(this);
		// Create the APP crash log directory
		File appFolder = new File(JFileKit.getDiskCacheDir(this) + "/log/");
		JFileKit.createFolder(appFolder);

		instance = this;
		// Get the screen width
		screenW = DensityUtil.getInstance(instance).getWidthInPx();
		screenH = DensityUtil.getInstance(instance).getHeightInPx();
		Bmob.initialize(this, getString(R.string.application_id));
	}

	@Override
	public BaseExceptionHandler getDefaultUncaughtExceptionHandler() {
		return new LocalFileHandler(applicationContext);
	}
}
