package com.dzt.journal.calendar;

import android.content.Intent;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.been.JCalendar;

import java.util.List;

/**
 * Created by M02323 on 2017/2/11.
 */

public interface CalendarContract {
	interface Presenter extends BasePresenter {
		void initTopText();
		void loadCalendar();
		void addGridView();
		void moveToLeft();
		void moveToRight();
		void clickGridViewItem(int position);
	}

	interface View extends BaseView<CalendarContract.Presenter> {
		void showGridView();
		void showCalendar(List<JCalendar> calendar);
		void showTopText(String text);
		void close(Intent intent);
	}
}
