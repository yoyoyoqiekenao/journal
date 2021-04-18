package com.dzt.journal.budget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzt.journal.R;
import com.dzt.journal.base.AbsListAdapter;
import com.dzt.journal.utils.AppPref;

import java.util.List;

/**
 * Created on 2016/12/15.
 */

public class DataTypeAdapter extends AbsListAdapter<String> {

	private AppPref appPref;

	public DataTypeAdapter(Context context, List<String> datas) {
		super(context, datas);
		appPref = AppPref.getInstance();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		String type = datas.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_pull_down, parent, false);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTitle.setText(type);
		int index = appPref.getIntValue("select_index", 1);
		int show = (position == index) ? View.VISIBLE : View.GONE;
		holder.ivIcon.setVisibility(show);
		return convertView;
	}

	class ViewHolder {
		public TextView tvTitle;
		public ImageView ivIcon;
	}
}
