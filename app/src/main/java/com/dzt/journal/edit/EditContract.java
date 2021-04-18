package com.dzt.journal.edit;

import android.content.Context;
import android.content.Intent;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.dao.TbJournal;
import com.dzt.journal.settings.classify.ClassifyType;

import java.util.List;

/**
 * Created by M02323 on 2017/9/27.
 *
 */

public class EditContract {
	interface Presenter extends BasePresenter {
		void result(int requestCode, int resultCode, Intent data);
		void initDataBase(Context context);
		void updateJournals(TbJournal journal);
		void deleteJournals(long id);
		void openCalendar();
		void popupClassify(int type);
	}

	interface View extends BaseView<Presenter> {
		void initPopupWindow();
		void showPopupClassify(List<String> rootList, List<List<String>> subList);
		void showCalendar();
		void showDateInfo(String date, String time, String week);
		void showClassifyText(String classify);
	}
}
