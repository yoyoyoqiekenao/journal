package com.dzt.journal.consume;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.dao.TbJournal;

import java.util.List;

/**
 * Created by M02323 on 2017/2/14.
 */

public interface TodayContract {
	interface Presenter extends BasePresenter {
		void initDataBase();
		void loadJournals();
		void loadTodayJournals();
		void addNewJournal();
	}

	interface View extends BaseView<TodayContract.Presenter> {
		void showJournals(List<TbJournal> incomes, List<TbJournal> payOuts);
		void showTodayJournals(List<TbJournal> incomes, List<TbJournal> payOuts);
//		void showSurplusText(String text);
		void showSurplus(String surplus);
		void showIncome(String income);
		void showPayOut(String payout);
//		void showSurplusDetailsUi();
		void showAddJournal();
//		void showTodayDetailsUi();
//		void showConsumeDetailUi(Journal journal);
	}
}
