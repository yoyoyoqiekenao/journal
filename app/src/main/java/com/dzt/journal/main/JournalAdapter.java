package com.dzt.journal.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzt.journal.R;
import com.dzt.journal.base.AbsListAdapter;
import com.dzt.journal.been.Journal;
import com.dzt.journal.utils.JDataKit;

import java.util.List;

/**
 * 记帐日志类
 * Created on 2016/12/9.
 */

public class JournalAdapter extends AbsListAdapter<Journal> {

	private JournalItemListener listener;

	public JournalAdapter(Context context, List<Journal> datas, JournalItemListener listener) {
		super(context, datas);
		this.listener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final Journal journal = datas.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_journal,
					parent, false);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvDescription = (TextView) convertView.findViewById(R.id.tv_description);
			holder.tvIncome = (TextView) convertView.findViewById(R.id.tv_income);
			holder.tvPayout = (TextView) convertView.findViewById(R.id.tv_pay_out);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ivIcon.setImageBitmap(journal.getBitmap());
		holder.tvTitle.setText(journal.getDate());
		holder.tvDescription.setText(journal.getDescription());
		holder.tvIncome.setText("+" + JDataKit.doubleFormat(journal.getIncome()));
		holder.tvPayout.setText("-" + JDataKit.doubleFormat(journal.getPayout()));
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onJournalClick(journal);
			}
		});
		return convertView;
	}

	public interface JournalItemListener {
		void onJournalClick(Journal clickedJournal);
	}

	class ViewHolder {
		public ImageView ivIcon;
		public TextView tvTitle;
		public TextView tvDescription;
		public TextView tvIncome;
		public TextView tvPayout;
	}
}
