package com.dzt.journal.settings.classify;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzt.journal.R;
import com.dzt.journal.base.AbsListAdapter;
import com.dzt.journal.been.MenuBean;

import java.util.List;


/**
 * Created by M02323 on 2017/3/28.
 */

public class ClassifyAdapter extends AbsListAdapter<Classify> {

	public ClassifyAdapter(Context context, List<Classify> datas) {
		super(context, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_menu, parent, false);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Classify classify = datas.get(position);
		holder.ivIcon.setImageBitmap(classify.getIcon());
		holder.tvName.setText(classify.getName());
		return convertView;
	}

	class ViewHolder {
		ImageView ivIcon;
		TextView tvName;
	}
}
