package com.dzt.journal.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dzt.journal.ActivityLogin;
import com.dzt.journal.ChartActivity;
import com.dzt.journal.JournalService;
import com.dzt.journal.LocalApplication;
import com.dzt.journal.R;
import com.dzt.journal.base.ActivityPermissions;
import com.dzt.journal.been.Journal;
import com.dzt.journal.been.MenuBean;
import com.dzt.journal.budget.BudgetActivity;
import com.dzt.journal.consume.ConsumeDetailActivity;
import com.dzt.journal.consume.TodayDetailActivity;
import com.dzt.journal.eventbus.UpdateEvent;
import com.dzt.journal.permission.PermissionListener;
import com.dzt.journal.record.RecordActivity;
import com.dzt.journal.settings.ActivitySetting;
import com.dzt.journal.utils.JDateKit;
import com.dzt.journal.utils.JLogUtils;
import com.dzt.journal.utils.ToastUtil;
import com.dzt.journal.widgets.TitleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;


public class MainActivity extends ActivityPermissions implements MainContract.View {

	@ViewInject(R.id.tv_title)
	private TitleView titleView;
	@ViewInject(R.id.iv_login_logo)
	private ImageView ivLoginLogo;
	@ViewInject(R.id.tv_login_name)
	private TextView tvLoginName;
	@ViewInject(R.id.lv_left)
	private ListView lvLeft;
	@ViewInject(R.id.dl_content)
	private DrawerLayout dlContent;
	@ViewInject(R.id.lv_journal)
	private ListView lvJournal;
	@ViewInject(R.id.tv_income_value)
	private TextView tvIncome;
	@ViewInject(R.id.tv_payout_value)
	private TextView tvPayOut;
	@ViewInject(R.id.tv_surplus_text)
	private TextView tvSurplusText;
	@ViewInject(R.id.tv_surplus_value)
	private TextView tvSurplus;
	@ViewInject(R.id.tv_night_mode)
	private TextView tvNightMode;

	private MainContract.Presenter presenter;
	private JournalAdapter adapter;
	private MenuAdapter menuAdapter;
	private List<MenuBean> datas = new ArrayList<>();

	@Override
	protected int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		requestRuntimePermission();
		presenter = new MainPresenter(context, this);
		presenter.initDataBase();

		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayoutSwitch(true);
			}
		});
		menuAdapter = new MenuAdapter(this, datas);
		loadData();
		lvLeft.setAdapter(menuAdapter);
		lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				listViewItemClick(i);
			}
		});
		setCurDate();
		initListView();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			firstStart();
		}
	}

	private void firstStart() {
		boolean result = appPref.getBoolValue("first", false);
		if (!result) {
			appPref.setBoolValue("first", true);
			Intent intent = new Intent(MainActivity.this, JournalService.class);
			startService(intent);
		}
	}

	private void loadData() {
		String[] menus = getResources().getStringArray(R.array.menus);
		int icons[] = {R.mipmap.icon_chart, R.mipmap.icon_logout};
		datas.clear();
		for (int i = 0; i < menus.length; i++) {
			MenuBean bean = new MenuBean();
			bean.setName(menus[i]);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
			bean.setIcon(bitmap);
			datas.add(bean);
		}
		if (getCurrentTime()) {
			tvNightMode.setText(R.string.night);
			getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		} else {
			tvNightMode.setText(R.string.day);
			getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		}
		menuAdapter.notifyDataSetChanged();
	}

	public boolean getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(new Date());
		int k = Integer.parseInt(hour);
		if ((k >= 0 && k < 6) || (k >= 18 && k < 24)) {
			return true;
		} else {
			return false;
		}
	}

	private void listViewItemClick(int pos) {
		switch (pos) {
			case 0:
				startActivity(ChartActivity.class, null);
				break;
			case 1:
				//清除缓存用户对象
				BmobUser.logOut();
				LocalApplication.userName = null;
				tvLoginName.setText(R.string.text_login);
				ToastUtil.getInstance().showMessage("退出成功");
				break;
			default:
				break;
		}
	}

	private void requestRuntimePermission() {
		requestRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.READ_PHONE_STATE},
				new PermissionListener() {

					@Override
					public void onGranted() {
						//当权限被授予时调用
						ToastUtil.getInstance().showMessage("Permission granted");
						firstStart();
					}

					@Override
					public void onDenied() {
						ToastUtil.getInstance().showMessage("Permission denied");
					}

					@Override
					public void onShowRationale(String[] permissions) {
						if (permissions != null) {
							JLogUtils.getInstance().i("onShowRationale");
							helper.setIsPositive(true);
							helper.request();
						}
					}
				});
	}

	private void setCurDate() {
		String date = JDateKit.dateToStr("yyyy-M", new Date());
		String year = date.split("-")[0];
		String month = date.split("-")[1];
		SpannableString sp = new SpannableString(month + "/" + year);
		sp.setSpan(new ForegroundColorSpan(Color.RED), 0, month.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new StyleSpan(Typeface.BOLD),
				0, month.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(2.0f), 0, month.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(1.0f), month.length(), sp.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		titleView.setLeftDetail(R.drawable.selector_menu, sp);
	}

	private void initListView() {
		adapter = new JournalAdapter(context, new ArrayList<Journal>(0), listener);
		lvJournal.setAdapter(adapter);
		presenter.loadJournals();
	}

	public void drawerLayoutSwitch(boolean isOpen) {
		if (isOpen) {
			dlContent.openDrawer(Gravity.START);
		} else {
			dlContent.closeDrawer(Gravity.START);
		}
	}

	@Override
	public void onBackPressed() {
		if (dlContent.isDrawerOpen(Gravity.START)) {
			drawerLayoutSwitch(false);
		} else {
			super.onBackPressed();
		}
	}

	JournalAdapter.JournalItemListener listener = new JournalAdapter.JournalItemListener() {
		@Override
		public void onJournalClick(Journal clickedJournal) {
			presenter.openJournalDetails(clickedJournal);
		}
	};

	@Event(value = {R.id.btn_remember, R.id.tv_income_value, R.id.tv_payout_value,
			R.id.tv_surplus_value, R.id.iv_login_logo, R.id.tv_login_name,
			R.id.tv_setting, R.id.tv_night_mode})
	private void onClickView(View v) {
		switch (v.getId()) {
			case R.id.btn_remember:
				presenter.addNewJournal();
				break;
			case R.id.tv_income_value:
			case R.id.tv_payout_value:
				Intent intent = new Intent();
				intent.putExtra("index", JournalType.THIS_MONTH.value());
				startActivity(ConsumeDetailActivity.class, intent);
				break;
			case R.id.tv_surplus_value:
				presenter.openSurplusDetails();
				break;
			case R.id.iv_login_logo:
			case R.id.tv_login_name:
				BmobUser bu = BmobUser.getCurrentUser();
				if (bu == null) {
					startActivity(ActivityLogin.class, null);
				} else {
					LocalApplication.userName = bu.getUsername();
					tvLoginName.setText(LocalApplication.userName);
					ToastUtil.getInstance().showMessage("登录成功");
				}
				break;
			case R.id.tv_setting:
				startActivity(ActivitySetting.class, null);
				break;
			case R.id.tv_night_mode:
				break;
			default:
				break;
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(UpdateEvent response) {
		if (response.from.equals("EditActivity")) {
			if (response.event.equals("delete")) {
				presenter.loadJournals();
			} else if (response.event.equals("update")) {
				presenter.loadJournals();
			}
		} else if (response.from.equals("RecordActivity")) {
			if (response.event.equals("add")) {
				presenter.loadJournals();
			}
		} else if (response.from.equals("BudgetActivity")) {
			if (response.event.equals("budget")) {
				presenter.loadJournals();
			} else if (response.event.equals("change_budget")) {
				presenter.loadJournals();
			}
		}
	}

	@Override
	public void setPresenter(MainContract.Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void showJournals(List<Journal> journals) {
		adapter.refreshDatas(journals);
	}

	@Override
	public void showSurplusText(String text) {
		tvSurplusText.setText(text);
	}

	@Override
	public void showSurplus(String surplus) {
		tvSurplus.setText(getString(R.string.rmb) + surplus);
	}

	@Override
	public void showIncome(String income) {
		tvIncome.setText(getString(R.string.rmb) + income);
	}

	@Override
	public void showPayOut(String payout) {
		tvPayOut.setText(getString(R.string.rmb) + payout);
	}

	@Override
	public void showSurplusDetailsUi() {
		startActivity(BudgetActivity.class, null);
	}

	@Override
	public void showAddJournal() {
		startActivity(RecordActivity.class, null);
	}

	@Override
	public void showTodayDetailsUi() {
		startActivity(TodayDetailActivity.class, null);
	}

	@Override
	public void showConsumeDetailUi(Journal journal) {
		Intent intent = new Intent();
		intent.putExtra("index", journal.getType().value());
		startActivity(ConsumeDetailActivity.class, intent);
	}
}
