package com.dzt.journal.record;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dzt.journal.calendar.CalendarActivity;
import com.dzt.journal.dao.TbClassify;
import com.dzt.journal.dao.TbClassifyDao;
import com.dzt.journal.dao.TbJournalDao;
import com.dzt.journal.dao.TbSubclassDao;
import com.dzt.journal.utils.JLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M02323 on 2017/2/11.
 */

public abstract class RecordPresenter implements RecordContract.Presenter {
	protected Context context;
	protected RecordContract.View view;
	protected TbJournalDao journalDao;
	protected TbClassifyDao classifyDao;
	protected TbSubclassDao subclassDao;

	public RecordPresenter(Context context, RecordContract.View view) {
		this.context = context;
		this.view = view;
	}

	@Override
	public void result(int requestCode, int resultCode, Intent data) {
		if (Activity.RESULT_OK != resultCode || data == null) {
			return;
		}
		if (requestCode == CalendarActivity.REQUEST_CALENDAR) {
			String date = data.getStringExtra("date");
			String time = data.getStringExtra("time");
			String week = data.getStringExtra("week");
			JLogUtils.getInstance().i("date = " + date);
			view.showDateInfo(date, time, week);
		}
	}

	@Override
	public void initDataBase(Context context) {
		journalDao = TbJournalDao.getInstance();
		classifyDao = TbClassifyDao.getInstance();
		subclassDao = TbSubclassDao.getInstance();
	}

	@Override
	public void openCalendar() {
		view.showCalendar();
	}

	@Override
	public void popupClassify() {
		List<TbClassify> classifies = getRootList();
		List<String> rootList = new ArrayList<>();
		List<List<String>> subList = new ArrayList<>();
		for (TbClassify classify : classifies) {
			subList.add(getSubList(classify.idx));
			rootList.add(classify.name);
		}
		view.showPopupClassify(rootList, subList);
	}

	@Override
	public void start() {

	}

	protected abstract List<TbClassify> getRootList();

	protected abstract List<String> getSubList(int index);
}
