package com.dzt.journal.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dzt.journal.calendar.CalendarActivity;
import com.dzt.journal.dao.TbClassify;
import com.dzt.journal.dao.TbClassifyDao;
import com.dzt.journal.dao.TbJournal;
import com.dzt.journal.dao.TbJournalDao;
import com.dzt.journal.dao.TbSubclass;
import com.dzt.journal.dao.TbSubclassDao;
import com.dzt.journal.settings.classify.ClassifyType;
import com.dzt.journal.utils.JLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M02323 on 2017/9/27.
 */

public class EditPresenter implements EditContract.Presenter {
	protected Context context;
	protected EditContract.View view;
	protected TbJournalDao journalDao;
	protected TbClassifyDao classifyDao;
	protected TbSubclassDao subclassDao;

	public EditPresenter(Context context, EditContract.View view) {
		this.context = context;
		this.view = view;
		view.initPopupWindow();
	}

	@Override
	public void start() {

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
	public void updateJournals(TbJournal journal) {
		journalDao.updateJournal(journal);
	}

	@Override
	public void deleteJournals(long id) {
		journalDao.deleteJournal(id);
	}

	@Override
	public void openCalendar() {
		view.showCalendar();
	}

	@Override
	public void popupClassify(int type) {
		List<TbClassify> classifies = getRootList(type);
		List<String> rootList = new ArrayList<>();
		List<List<String>> subList = new ArrayList<>();
		for (TbClassify classify : classifies) {
			subList.add(getSubList(classify.idx));
			rootList.add(classify.name);
		}
		view.showPopupClassify(rootList, subList);
	}

	protected List<TbClassify> getRootList(int type) {
		List<TbClassify> classifies = classifyDao.findTbClassify(type);
		return classifies;
	}

	protected List<String> getSubList(int index) {
		List<TbSubclass> subclasses = subclassDao.findTbSubclass(index);
		List<String> list = new ArrayList<>();
		for (TbSubclass subclass : subclasses) {
			list.add(subclass.name);
		}
		return list;
	}
}
