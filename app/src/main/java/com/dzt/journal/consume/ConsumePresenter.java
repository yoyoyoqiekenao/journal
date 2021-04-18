package com.dzt.journal.consume;

import android.content.Context;

import com.dzt.journal.dao.TbJournal;
import com.dzt.journal.dao.TbJournalDao;
import com.dzt.journal.utils.JDataKit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by M02323 on 2017/5/3.
 */

public class ConsumePresenter implements ConsumeContract.Presenter {
	private ConsumeContract.View view;
	private Context context;
	private TbJournalDao journalDao;

	public ConsumePresenter(Context context, ConsumeContract.View view) {
		this.context = context;
		this.view = view;
		initDataBase();
	}

	@Override
	public void start() {

	}

	@Override
	public void initDataBase() {
		journalDao = TbJournalDao.getInstance();
	}

	@Override
	public void loadJournals(String start, String end) {
		List<TbJournal> incomes = journalDao.findBetween(start, end, TbJournal.INCOME);
		double incomeSum = 0;
		for (TbJournal income : incomes) {
			incomeSum += income.money;
		}
		view.showIncome(JDataKit.doubleFormat(incomeSum));
		List<TbJournal> payouts = journalDao.findBetween(start, end, TbJournal.PAYOUT);
		double payoutSum = 0;
		for (TbJournal payout : payouts) {
			payoutSum += payout.money;
		}
		view.showPayOut(JDataKit.doubleFormat(payoutSum));
		double surplus = incomeSum - payoutSum;
		view.showSurplus(JDataKit.doubleFormat(surplus));
		List<TbJournal> list = new ArrayList<>();
		list.addAll(incomes);
		list.addAll(payouts);
		Collections.sort(list, new DateComparator());
		view.showJournals(list);
	}
}
