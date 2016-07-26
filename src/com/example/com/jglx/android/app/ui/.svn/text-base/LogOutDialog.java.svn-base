package com.example.com.jglx.android.app.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.helper.LXHXSDKHelper;

public class LogOutDialog extends Dialog {

	public Context mContext;
	public static final int INTENT_CODE = 0x00002;
	private LinearLayout tuicun_top;
	private LinearLayout tuicun_bottom;

	public LogOutDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public LogOutDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
	}

	private void initViews() {
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.tuicudialog_layout);

		tuicun_top = (LinearLayout) findViewById(R.id.tuicun_top);
		tuicun_bottom = (LinearLayout) findViewById(R.id.tuicun_bottom);
		tuicun_bottom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LXApplication.getInstance().exit();
				dismiss();
			}
		});
		tuicun_top.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				// // 此方法为异步方法
				// EMChatManager.getInstance().logout(new EMCallBack() {
				//
				// @Override
				// public void onSuccess() {
				// LXApplication.mUserInfo = null;
				// LXApplication.getInstance().saveUserInfo(mContext, null);
				// Intent intent = new Intent(((Activity) mContext),
				// LoginActivity.class);
				// mContext.startActivity(intent);
				// ((Activity) mContext).finish();
				// }
				//
				// @Override
				// public void onProgress(int progress, String status) {
				//
				// }
				//
				// @Override
				// public void onError(int code, final String message) {
				// ((Activity) mContext).runOnUiThread(new Runnable() {
				//
				// @Override
				// public void run() {
				// Toast.makeText(mContext, message,
				// Toast.LENGTH_SHORT).show();
				// }
				// });
				//
				// }
				// });
				LXHXSDKHelper.getInstance().logout(new EMCallBack() {

					@Override
					public void onSuccess() {
						LXApplication.mUserInfo = null;
						LXApplication.getInstance()
								.saveUserInfo(mContext, null);
						Intent intent = new Intent(((Activity) mContext),
								LoginActivity.class);
						mContext.startActivity(intent);
						((Activity) mContext).finish();
					}

					@Override
					public void onProgress(int arg0, String arg1) {

					}

					@Override
					public void onError(int arg0, final String arg1) {
						((Activity) mContext).runOnUiThread(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(mContext, arg1,
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				});
				dismiss();
			}
		});
	}

}
