package com.dzt.journal.settings.classify;

import com.dzt.journal.base.BasePresenter;
import com.dzt.journal.base.BaseView;
import com.dzt.journal.been.MenuBean;

import java.util.List;

/**
 * Created by M02323 on 2017/2/13.
 */

public interface ClassifyContract {
	interface Presenter extends BasePresenter {
		void initDataBase();
		void loadClassify(ClassifyType type);
	}

	interface View extends BaseView<ClassifyContract.Presenter> {
		void showClassify(List<Classify> classify);
	}
}
