package com.dzt.journal.budget;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.been.BudgetInfo;
import com.dzt.journal.main.JournalType;

import java.util.List;

/**
 * Created by M02323 on 2017/2/13.
 */

public interface BudgetContract {
	interface Presenter extends BasePresenter {
		void initDataBase();
		void initTitle();
		void changeTitle(int position);
		void popupDataType();
		void loadBudgets();
		void saveBudget(String money);
		void setJournalType(JournalType requestType);
		JournalType getJournalType();
	}

	interface View extends BaseView<BudgetContract.Presenter> {
		void initPopupWindow();
		void showPopupDataType(List<String> dataTypes);
		void showBudgets(List<BudgetInfo> budgets);
		void showTitle(String title);
		void showUsedBudget(String used);
		void showUsableBudget(String usable);
		void showMoney(String money);
		void close();
	}
}
