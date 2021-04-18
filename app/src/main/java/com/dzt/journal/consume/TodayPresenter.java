package com.dzt.journal.consume;

import android.content.Context;

import com.dzt.journal.dao.TbJournal;
import com.dzt.journal.dao.TbJournalDao;
import com.dzt.journal.utils.JDateKit;

import java.util.Date;
import java.util.List;

/**
 * Created by M02323 on 2017/2/14.
 */

public class TodayPresenter implements TodayContract.Presenter {
	private TodayContract.View view;
	private Context context;
	private TbJournalDao journalDao;

	public TodayPresenter(Context context, TodayContract.View view) {
		this.context = context;
		this.view = view;
		initDataBase();
	}

	@Override
	public void initDataBase() {
		journalDao = TbJournalDao.getInstance();
	}

	@Override
	public void loadJournals() {
		List<TbJournal> incomes = journalDao.findByType(TbJournal.INCOME);
		List<TbJournal> payOuts = journalDao.findByType(TbJournal.PAYOUT);
		view.showJournals(incomes, payOuts);
	}

	@Override
	public void loadTodayJournals() {
		String date = JDateKit.dateToStr("yyyy-MM-dd", new Date());
		//今天收入
		List<TbJournal> incomes = journalDao.findBetween(date, date, TbJournal.INCOME);

		//今天支出
		List<TbJournal> payOuts = journalDao.findBetween(date, date, TbJournal.PAYOUT);
		view.showTodayJournals(incomes, payOuts);
	}

	@Override
	public void addNewJournal() {
		view.showAddJournal();
	}

	@Override
	public void start() {

	}
}
