package com.dzt.journal.consume;

import com.dzt.journal.dao.TbJournal;

import java.util.Comparator;

/**
 * Created by M02323 on 2017/5/3.
 */

public class DateComparator implements Comparator<TbJournal> {

	@Override
	public int compare(TbJournal j1, TbJournal j2) {
		return j2.date.compareTo(j1.date);
	}
}
