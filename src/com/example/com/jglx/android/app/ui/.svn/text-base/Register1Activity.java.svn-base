package com.example.com.jglx.android.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;

/**
 * 注册1--输入手机号
 * 
 * @author jjj
 * 
 * @date 2015-8-7
 */
public class Register1Activity extends BaseActivity {

	private EditText register1_phoneEdt;
	public String phone;
	public String code;
	public String tel;
	private int type = 0;// 1-注册 2-忘记密码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_register1);
		setTitleTextRightText("", "欢迎注册邻信", "", true);

		type = getIntent().getIntExtra("type", 0);

		initView();
	}

	private void initView() {
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
//		String deviceid = tm.getDeviceId();
		tel = tm.getLine1Number();// 手机号码
//		String imei = tm.getSimSerialNumber();
//		String imsi = tm.getSubscriberId();
		register1_phoneEdt = (EditText) findViewById(R.id.register1_phoneEdt);
		register1_phoneEdt.setText(tel);
		findViewById(R.id.register1_nexBtn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						phone = register1_phoneEdt.getText().toString();
						code = String.valueOf(Math.random()).substring(2, 8);
						Intent intent = new Intent(Register1Activity.this,
								RegisterActivity.class);
						intent.putExtra("phone", phone);
						intent.putExtra("type", type);
						intent.putExtra("code", code);
						startActivity(intent);

					}
				});
	}

}
