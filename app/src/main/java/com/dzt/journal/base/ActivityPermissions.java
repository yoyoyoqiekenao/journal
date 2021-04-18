package com.dzt.journal.base;

import com.dzt.journal.permission.PermissionListener;
import com.dzt.journal.permission.PermissionManager;

/**
 * Created by M02323 on 2017/4/26.
 */

public abstract class ActivityPermissions extends ActivityBase{
	public static final int REQUEST_PERMISSION_CODE = 1;

	protected PermissionManager helper;

	public void requestRuntimePermission(String[] permissions,
										 PermissionListener listener) {
		helper = PermissionManager.with(this);
		//添加权限请求码
		helper.addRequestCode(REQUEST_PERMISSION_CODE);
		//设置权限，可以添加多个权限
		helper.permissions(permissions);
		//设置权限监听器
		helper.setPermissionsListener(listener);
		//请求权限
		helper.request();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case REQUEST_PERMISSION_CODE:
				helper.onPermissionResult(permissions, grantResults);
				break;
		}
	}
}
