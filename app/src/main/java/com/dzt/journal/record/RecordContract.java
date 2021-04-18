package com.dzt.journal.record;

import android.content.Context;
import android.content.Intent;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.dao.TbClassify;

import java.util.List;

/**
 * Created by M02323 on 2017/2/11.
 */

public interface RecordContract {
	interface Presenter extends BasePresenter {
		void result(int requestCode, int resultCode, Intent data);
		void initDataBase(Context context);
		void saveJournals(String money,String rootType, String subType,
						  String description, String dates[], String imgPath);
		void openCalendar();
		void popupClassify();
	}

	interface View extends BaseView<RecordContract.Presenter> {
		void initPopupWindow();
		void showPopupClassify(List<String> rootList, List<List<String>> subList);
		void showCalendar();
		void showDateInfo(String date, String time, String week);
		void showClassifyText(String classify);
	}
}
