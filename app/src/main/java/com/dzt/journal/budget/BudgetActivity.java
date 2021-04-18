package com.dzt.journal.budget;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dzt.journal.R;
import com.dzt.journal.base.ActivityFrame;
import com.dzt.journal.been.BudgetInfo;
import com.dzt.journal.eventbus.EventBusUtil;
import com.dzt.journal.eventbus.UpdateEvent;
import com.dzt.journal.utils.JLogUtils;
import com.dzt.journal.utils.JSystemKit;
import com.dzt.journal.utils.ToastUtil;
import com.dzt.journal.widgets.BasePopupWindow;
import com.dzt.journal.widgets.ClearEditText;
import com.dzt.journal.widgets.TitleView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends ActivityFrame implements BudgetContract.View {

	@ViewInject(R.id.titleview)
	private TitleView titleView;
	@ViewInject(R.id.cet_money)
	private ClearEditText cetMoney;
	@ViewInject(R.id.tv_used_budget_value)
	private TextView tvUsedBudget;
	@ViewInject(R.id.tv_usable_budget_value)
	private TextView tvUsableBudget;
	@ViewInject(R.id.lv_budget)
	private ListView lvBudget;
	private ListView lvDateType;
	private boolean isRotate = false;
	private BasePopupWindow popupWindow;
	private DataTypeAdapter adapter;
	private BudgetAdapter budgetAdapter;
	private BudgetContract.Presenter presenter;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_budget;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		presenter = new BudgetPresenter(context, this);
		presenter.initDataBase();
		titleView.setResource(R.drawable.selector_return_btn, R.string.today);
		titleView.setTitleRightDrawable(R.mipmap.super_trans_action_bar_menu_arrow,
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						titleView.setTitleAnimation(isRotate);
						isRotate = !isRotate;
						if (isRotate) {
							popupWindow.showAsDropDown(titleView);
							JSystemKit.hideInputWindow(context, titleView);
						} else {
							popupWindow.dismiss();
						}
					}
				});
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		presenter.initTitle();
		titleView.setRightDetail(R.drawable.selector_ok_btn);
		titleView.setRightClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String money = cetMoney.getText().toString();
				if (TextUtils.isEmpty(money)) {
					ToastUtil.getInstance().showMessage(R.string.hint);
					return;
				}
				presenter.saveBudget(money);
			}
		});

		budgetAdapter = new BudgetAdapter(context, new ArrayList<BudgetInfo>(0));
		lvBudget.setAdapter(budgetAdapter);
		presenter.loadBudgets();
	}

	@Override
	public void setPresenter(BudgetContract.Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initPopupWindow() {
		adapter = new DataTypeAdapter(context, new ArrayList<String>(0));
		popupWindow = new BasePopupWindow(this);
		View view = LayoutInflater.from(context).
				inflate(R.layout.layout_pull_down, new LinearLayout(this), false);
		lvDateType = (ListView) view.findViewById(R.id.lv_date_type);
		lvDateType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if (presenter.getJournalType().value() != i) {
					presenter.changeTitle(i);
					presenter.loadBudgets();
					EventBusUtil.postSync(new UpdateEvent("change_budget", "BudgetActivity", this));
				}
				popupWindow.dismiss();
			}
		});
		lvDateType.setAdapter(adapter);
		popupWindow.setContentView(view);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				titleView.setTitleAnimation(isRotate);
				isRotate = !isRotate;
			}
		});
	}

	@Override
	public void showPopupDataType(List<String> dataTypes) {
		adapter.refreshDatas(dataTypes);
	}

	@Override
	public void showBudgets(List<BudgetInfo> budgets) {
		budgetAdapter.refreshDatas(budgets);
	}

	@Override
	public void showTitle(String title) {
		setResult(Activity.RESULT_OK);
		titleView.setCenterDetail(title);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void showUsedBudget(String used) {
		tvUsedBudget.setText(used);
	}

	@Override
	public void showUsableBudget(String usable) {
		tvUsableBudget.setText(usable);
	}

	@Override
	public void showMoney(String money) {
		JLogUtils.getInstance().i("show money = " + money);
		if (!money.equals("0.00")) {
			cetMoney.setText(money);
		}else{
			cetMoney.setText("");
		}
	}

	@Override
	public void close() {
		EventBusUtil.postSync(new UpdateEvent("budget", "BudgetActivity", this));
		finish();
	}
}
