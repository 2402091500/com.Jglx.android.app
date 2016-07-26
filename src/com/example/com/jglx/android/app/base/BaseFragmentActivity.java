package com.example.com.jglx.android.app.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.helper.HXSDKHelper;

public abstract class BaseFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);

		LXApplication.getInstance().addActivity(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LXApplication.getInstance().removeActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// onresume时，取消notification显示
		HXSDKHelper.getInstance().getNotifier().reset();
	}
}
