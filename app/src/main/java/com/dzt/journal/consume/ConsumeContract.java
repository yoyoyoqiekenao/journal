package com.dzt.journal.consume;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.dao.TbJournal;

import java.util.List;

/**
 * Created by M02323 on 2017/2/14.
 */

public interface ConsumeContract {
	interface Presenter extends BasePresenter {
		void initDataBase();
		void loadJournals(String start, String end);
//		void loadBudget();
//		void addNewJournal();
//		void openJournalDetails(Journal journal);
//		void openSurplusDetails();
//		void setJournalType(JournalType requestType);
//		JournalType getJournalType();
	}

	interface View extends BaseView<ConsumeContract.Presenter> {
		void showJournals(List<TbJournal> journals);
//		void showSurplusText(String text);
		void showSurplus(String surplus);
		void showIncome(String income);
		void showPayOut(String payout);
	}
}
