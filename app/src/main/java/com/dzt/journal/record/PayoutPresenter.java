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

public class PayoutPresenter extends RecordPresenter {

	public PayoutPresenter(Context context, RecordContract.View view) {
		super(context, view);
		view.initPopupWindow();
	}

	@Override
	public void saveJournals(String money,String rootType, String subType,
							 String description, String dates[], String imgPath) {
		TbJournal payOut = new TbJournal();
		payOut.rootType = rootType;
		payOut.journalType = TbJournal.PAYOUT;
		payOut.subType = subType;
		payOut.description = description;
		payOut.imgPath = imgPath;
		money = money.replace(",", "");
		payOut.money = Double.parseDouble(money);
		payOut.date = dates[0];
		payOut.time = dates[1];
		payOut.week = dates[2];
		journalDao.saveJournal(payOut);
	}

	@Override
	protected List<TbClassify> getRootList() {
		List<TbClassify> classifies = classifyDao.findTbClassify(ClassifyType.PAYOUT.value());
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
