package com.dzt.journal.record;

import android.content.Context;

import com.dzt.journal.dao.TbClassify;
import com.dzt.journal.dao.TbJournal;
import com.dzt.journal.dao.TbSubclass;
import com.dzt.journal.settings.classify.ClassifyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M02323 on 2017/2/11.
 */

public class IncomePresenter extends RecordPresenter {

	public IncomePresenter(Context context, RecordContract.View view) {
		super(context, view);
		view.initPopupWindow();
	}

	@Override
	public void saveJournals(String money, String rootType, String subType,
							 String description, String dates[], String imgPath) {
		TbJournal income = new TbJournal();
		income.rootType = rootType;
		income.journalType = TbJournal.INCOME;
		income.subType = subType;
		income.description = description;
		income.imgPath = imgPath;
		money = money.replace(",", "");
		income.money = Double.parseDouble(money);
		income.date = dates[0];
		income.time = dates[1];
		income.week = dates[2];
		journalDao.saveJournal(income);
	}

	@Override
	protected List<TbClassify> getRootList() {
		List<TbClassify> classifies = classifyDao.findTbClassify(ClassifyType.INCOME.value());
		return classifies;
	}

	@Override
	protected List<String> getSubList(int index) {
		List<TbSubclass> subclasses = subclassDao.findTbSubclass(index);
		List<String> list = new ArrayList<>();
		for(TbSubclass subclass : subclasses){
			list.add(subclass.name);
		}
		return list;
	}
}
