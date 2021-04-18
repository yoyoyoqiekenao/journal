package com.dzt.journal.record;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dzt.journal.R;
import com.dzt.journal.base.ActivityFrame;
import com.dzt.journal.widgets.TitleView;
import com.github.chrisbanes.photoview.PhotoView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class PhotoViewActivity extends ActivityFrame {
	public static final int PHOTO_REQUEST = 30;
	@ViewInject(R.id.photo_view)
	private PhotoView photoView;
	@ViewInject(R.id.tv_title)
	private TitleView titleView;
	@ViewInject(R.id.rl_edit)
	private RelativeLayout rlEdit;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_photo_view;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		String name = intent.getStringExtra("name");
		boolean isEdit = intent.getBooleanExtra("edit", false);
		if (isEdit) {
			rlEdit.setVisibility(View.VISIBLE);
		}
		Bitmap bitmap = BitmapFactory.decodeFile(url);
		photoView.setImageBitmap(bitmap);
		titleView.setResource(R.drawable.selector_return_btn,
				R.string.app_name);
		titleView.setCenterDetail(name);
		titleView.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Event(value = {R.id.ib_delete})
	private void OnClickView(View v) {
		setResult(Activity.RESULT_OK);
		finish();
	}
}
