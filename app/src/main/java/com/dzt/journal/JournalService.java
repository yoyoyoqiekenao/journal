package com.dzt.journal;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.dzt.journal.dao.TbClassify;
import com.dzt.journal.dao.TbClassifyDao;
import com.dzt.journal.dao.TbSubclass;
import com.dzt.journal.dao.TbSubclassDao;
import com.dzt.journal.settings.classify.ClassifyType;
import com.dzt.journal.utils.JFileKit;
import com.dzt.journal.utils.JLogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第一次启动会启动此服务加载数据保存到数据库，后续不会再启动。
 */
public class JournalService extends Service {

	private TbClassifyDao classifyDao;
	private TbSubclassDao subclassDao;

	public JournalService() {
		classifyDao = TbClassifyDao.getInstance();
		subclassDao = TbSubclassDao.getInstance();
		new Thread(new Runnable() {
			@Override
			public void run() {
				loadPayout();
				loadIncome();
				stopSelf();
			}
		}).start();
	}

	private void loadIncome() {
		String json = JFileKit.getJsonString(this, "income.json");
		try {
			JSONObject dataJson = new JSONObject(json);
			JSONArray provinces = dataJson.getJSONArray("categories");
			int icons[] = {R.mipmap.icon_zysr, R.mipmap.icon_qtsr};
			for (int i = 0; i < provinces.length(); i++) {
				JSONObject province = provinces.getJSONObject(i);
				String category = province.getString("category");
				TbClassify classify = new TbClassify();
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
				classify.idx = TbClassifyDao.INCOME_COUNT + i;
				classify.imgs = baos.toByteArray();
				classify.name = category;
				classify.type = ClassifyType.INCOME.value();
				long result = classifyDao.saveClassify(classify);
				JLogUtils.getInstance().e("result = " + result);
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONArray cities = province.getJSONArray("subclass");
				for (int j = 0; j < cities.length(); j++) {
					TbSubclass sub = new TbSubclass();
					sub.index = classify.idx;
					sub.name = cities.getString(j);
					subclassDao.saveSubclass(sub);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void loadPayout() {
		String json = JFileKit.getJsonString(this, "payout.json");
		try {
			JSONObject dataJson = new JSONObject(json);
			JSONArray provinces = dataJson.getJSONArray("categories");
			int icons[] = {R.mipmap.icon_spjs, R.mipmap.icon_yfsp, R.mipmap.icon_jjwy,
					R.mipmap.icon_xcjt_sjcfy, R.mipmap.icon_jltx, R.mipmap.icon_xxyl,
					R.mipmap.icon_xxjx, R.mipmap.icon_rqwl, R.mipmap.icon_ylbj,
					R.mipmap.icon_jrbx, R.mipmap.icon_zysr_jzsr};
			for (int i = 0; i < provinces.length(); i++) {
				JSONObject province = provinces.getJSONObject(i);
				String category = province.getString("category");
				TbClassify classify = new TbClassify();
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
				classify.idx = i;
				classify.imgs = baos.toByteArray();
				classify.name = category;
				classify.type = ClassifyType.PAYOUT.value();
				long result = classifyDao.saveClassify(classify);
				JLogUtils.getInstance().e("result = " + result);
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONArray cities = province.getJSONArray("subclass");
				for (int j = 0; j < cities.length(); j++) {
					TbSubclass sub = new TbSubclass();
					sub.index = classify.idx;
					sub.name = cities.getString(j);
					subclassDao.saveSubclass(sub);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		JLogUtils.getInstance().e("onDestroy");
	}
}
