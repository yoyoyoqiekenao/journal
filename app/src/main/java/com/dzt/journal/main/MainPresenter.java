package com.dzt.journal.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dzt.journal.R;
import com.dzt.journal.been.Journal;
import com.dzt.journal.budget.BudgetActivity;
import com.dzt.journal.dao.TbBudget;
import com.dzt.journal.dao.TbBudgetDao;
import com.dzt.journal.dao.TbJournal;
import com.dzt.journal.dao.TbJournalDao;
import com.dzt.journal.record.RecordActivity;
import com.dzt.journal.utils.AppPref;
import com.dzt.journal.utils.BitmapUtil;
import com.dzt.journal.utils.JDataKit;
import com.dzt.journal.utils.JDateKit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by M02323 on 2017/2/10.
 */

public class MainPresenter implements MainContract.Presenter {

	private final MainContract.View view;
	private Context context;
	private JournalType currentType = JournalType.TODAY;
	private TbJournalDao journalDao;
	private TbBudgetDao budgetDao;
	private AppPref appPref;
	private String budgetType;
	private String startDate;
	private String endDate;

	public MainPresenter(Context context, MainContract.View view) {
		this.context = context;
		this.view = view;
		appPref = AppPref.getInstance();
	}

	@Override
	public void start() {

	}

	@Override
	public void initDataBase() {
		journalDao = TbJournalDao.getInstance();
		budgetDao = TbBudgetDao.getInstance();
	}

	@Override
	public void loadJournals() {
		String dateStr = JDateKit.dateToStr("yyyy-M-dd", new Date());
		String year = dateStr.split("-")[0];
		String day = dateStr.split("-")[2];
		Bitmap bitmap = null;
		String date = null;
		List<Journal> journals = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Journal journal = new Journal();
			calculateDate(JournalType.valueOf(i));
			switch (i) {
				case 0:
					bitmap = BitmapUtil.drawTextToBitmap(context, R.mipmap.main_today,
							day);
					date = context.getString(R.string.today);
					break;
				case 1:
					bitmap = BitmapFactory.decodeResource(context.getResources(),
							R.mipmap.icon_trans_item_week);
					date = context.getString(R.string.this_week);
					break;
				case 2:
					bitmap = BitmapFactory.decodeResource(context.getResources(),
							R.mipmap.icon_trans_item_month);
					date = context.getString(R.string.this_month);
					break;
				case 3:
					int y = JDateKit.yearDays(Integer.parseInt(year));
					bitmap = BitmapUtil.drawTextToBitmap(context, R.mipmap.main_today,
							String.valueOf(y));
					date = context.getString(R.string.this_year);
					break;
				case 4:
					bitmap = BitmapFactory.decodeResource(context.getResources(),
							R.mipmap.icon_all_bill);
					date = context.getString(R.string.this_all_bill);
					break;
				default:
					break;
			}
			double incomeSum = 0;
			List<TbJournal> incomes = journalDao.findBetween(startDate,
					endDate, TbJournal.INCOME);
			for (TbJournal income : incomes) {
				incomeSum += income.money;
			}
			double payOutSum = 0;
			List<TbJournal> payOuts = journalDao.findBetween(startDate,
					endDate, TbJournal.PAYOUT);
			for (TbJournal payout : payOuts) {
				payOutSum += payout.money;
			}
			journal.setType(JournalType.valueOf(i));
			journal.setIncome(incomeSum);
			journal.setPayout(payOutSum);
			journal.setBitmap(bitmap);
			journal.setDate(date);
			String description = startDate + "-" + endDate;
			if (i == 0) {
				if (incomeSum == 0 && payOutSum == 0) {
					journal.setDescription(context.getString(R.string.journal_description));
				} else {
					journal.setDescription(context.getString(R.string.recently));
				}
			} else {
				journal.setDescription(description);
			}
			if (i == 2) {
				List<TbBudget> budgets = getBudget();
				view.showSurplusText(budgetType + context.getString(R.string.budget_surplus));
				if (budgets == null || budgets.size() == 0) {
					view.showSurplus(JDataKit.doubleFormat(0));
				} else {
					for (TbBudget budget : budgets) {
						double money = budget.money - payOutSum;
						view.showSurplus(JDataKit.doubleFormat(money));
					}
				}
				view.showIncome(JDataKit.doubleFormat(incomeSum) + "");
				view.showPayOut(JDataKit.doubleFormat(payOutSum) + "");
			}
			journals.add(journal);
		}
		view.showJournals(journals);
	}

	private List<TbBudget> getBudget() {
		int index = appPref.getIntValue("select_index", JournalType.THIS_WEEK.value());
		calculateDate(JournalType.valueOf(index));
		return budgetDao.findBudget(startDate, endDate);
	}

	private void calculateDate(JournalType type) {
		switch (type) {
			case THIS_WEEK:
				budgetType = context.getString(R.string.this_week);
				startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfWeek());
				endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfWeek());
				break;
			case THIS_MONTH:
				budgetType = context.getString(R.string.this_month);
				startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfMonth());
				endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfMonth());
				break;
			case THIS_YEAR:
				budgetType = context.getString(R.string.this_year);
				startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfYear());
				endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfYear());
				break;
			case THIS_ALL_BILL:
				budgetType = context.getString(R.string.this_all_bill);
				startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.setDate(2000, 2, 2));
				endDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
				break;
			default:
				budgetType = context.getString(R.string.today);
				startDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
				endDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
				break;
		}
	}

	@Override
	public void loadBudget() {

	}

	@Override
	public void addNewJournal() {
		view.showAddJournal();
	}

	@Override
	public void openJournalDetails(Journal journal) {
		if (journal.getType() == JournalType.TODAY) {
			view.showTodayDetailsUi();
		} else {
			view.showConsumeDetailUi(journal);
		}
	}

	@Override
	public void openSurplusDetails() {
		view.showSurplusDetailsUi();
	}

	@Override
	public void setJournalType(JournalType requestType) {
		currentType = requestType;
	}

	@Override
	public JournalType getJournalType() {
		return currentType;
	}
}
