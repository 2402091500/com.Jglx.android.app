package com.example.com.jglx.android.app.ui;

import java.io.File;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.util.DataCleanManager;
import com.example.com.jglx.android.app.util.IOSDialogUtil;
import com.example.com.jglx.android.app.util.IOSDialogUtil.OnSheetItemClickListener;
import com.example.com.jglx.android.app.util.IOSDialogUtil.SheetItemColor;
import com.example.com.jglx.android.app.util.ImageUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 设置
 * 
 * @author jjj
 * 
 * @date 2015-9-9
 */
public class MySheZhiActivity extends BaseActivity implements OnClickListener {

	private Intent intent;
	private LogOutDialog logOUt;
	private TextView mTextView;
	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_she_zhi);
		setTitleTextRightText("", "设置", "", true);
		logOUt = new LogOutDialog(this, R.style.tuicun_dilog_style);
		initView();

	}

	private void initView() {
		findViewById(R.id.s_LL_yonhufank).setOnClickListener(this);
		findViewById(R.id.s_LL_anquansezi).setOnClickListener(this);
		findViewById(R.id.s_LL_qincufancun).setOnClickListener(this);
		findViewById(R.id.s_LL_yonhuxiyi).setOnClickListener(this);
		findViewById(R.id.s_LL_guanyuwomen).setOnClickListener(this);
		findViewById(R.id.s_LL_tuicun).setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.s_tv_qincufancun_right);
		try {
			filePath = Environment.getExternalStorageDirectory().getPath()
					+ "/LinXin";
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String string = DataCleanManager.getCacheSize(file);
			if (!TextUtils.isEmpty(string)) {
				mTextView.setText(string);
			} else {
				mTextView.setText("0.0KB");
			}

		} catch (Exception e) {
			mTextView.setText("0.0KB");
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.s_LL_yonhufank:
			intent = new Intent(this, MySheZhi_yonhuActivity.class);
			startActivity(intent);

			break;
		case R.id.s_LL_anquansezi:
			intent = new Intent(this, MyShezhiAnquanActivity.class);
			startActivity(intent);
			break;
		case R.id.s_LL_yonhuxiyi:
			intent = new Intent(this, MyShezhiXieyiActivity.class);
			startActivity(intent);
			break;
		case R.id.s_LL_guanyuwomen:
			intent = new Intent(this, MyShezhiWomenActivity.class);
			startActivity(intent);
			break;
		case R.id.s_LL_tuicun:
			logOUt.show();
			break;
		case R.id.s_LL_qincufancun:// 清除缓存

			new IOSDialogUtil(this)
					.builder()
					.setCancelable(true)
					.setCanceledOnTouchOutside(true)
					.addSheetItem("确定", SheetItemColor.Black,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									DataCleanManager
											.cleanInternalCache(MySheZhiActivity.this);
//									DataCleanManager
//											.cleanDatabases(MySheZhiActivity.this);
									DataCleanManager.cleanCustomCache(filePath);
									DataCleanManager
											.cleanCustomCache(ImageUtil.filePath);

									runOnUiThread(new Runnable() {
										@Override
										public void run() {

											mTextView.setText("0.0KB");
											Toast.makeText(
													MySheZhiActivity.this,
													"清除成功", Toast.LENGTH_SHORT)
													.show();
										}
									});

								}
							}).show();

			break;

		}
	}
}