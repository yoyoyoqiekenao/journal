package com.dzt.journal.budget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzt.journal.R;
import com.dzt.journal.base.AbsListAdapter;
import com.dzt.journal.been.BudgetInfo;

import java.util.List;

/**
 * Created on 2016/12/17.
 */

public class BudgetAdapter extends AbsListAdapter<BudgetInfo> {

	public BudgetAdapter(Context context, List<BudgetInfo> datas) {
		super(context, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		BudgetInfo info = datas.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_budget, parent, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.balance = (TextView) convertView.findViewById(R.id.tv_balance);
			holder.balanceValue = (TextView) convertView.findViewById(R.id.tv_balance_value);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(info.getTitle());
		holder.icon.setImageBitmap(info.getIcon());
		if (!TextUtils.isEmpty(info.getBalance())) {
			holder.balance.setVisibility(View.VISIBLE);
			holder.balanceValue.setVisibility(View.VISIBLE);
			holder.balanceValue.setText(info.getBalance());
		}else{
			holder.balance.setVisibility(View.GONE);
			holder.balanceValue.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		public ImageView icon;
		public TextView title;
		public TextView balance;
		public TextView balanceValue;
	}
}
