package com.dzt.journal.main;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.been.Journal;

import java.util.List;

/**
 * Created by M02323 on 2017/2/10.
 */

public interface MainContract {
	interface Presenter extends BasePresenter {
		void initDataBase();
		void loadJournals();
		void loadBudget();
		void addNewJournal();
		void openJournalDetails(Journal journal);
		void openSurplusDetails();
		void setJournalType(JournalType requestType);
		JournalType getJournalType();
	}

	interface View extends BaseView<Presenter> {
		void showJournals(List<Journal> journals);
		void showSurplusText(String text);
		void showSurplus(String surplus);
		void showIncome(String income);
		void showPayOut(String payout);
		void showSurplusDetailsUi();
		void showAddJournal();
		void showTodayDetailsUi();
		void showConsumeDetailUi(Journal journal);
	}
}
