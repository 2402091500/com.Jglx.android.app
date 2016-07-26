package com.example.com.jglx.android.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;

/**
 * 群管理员申请
 * 
 * @author jjj
 * 
 * @date 2015-8-26
 */
public class ApplyGroupManagerActivity extends BaseActivity {
	private EditText mPhoneEdt;
	private EditText mQQEdt;
	private EditText mDetailEdt;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_applygm);
		setTitleTextRightText("", "群管理员申请", "申请", true);
		setRightTvColor(R.color.green);

		initView();
	}

	private void initView() {
		mPhoneEdt = (EditText) findViewById(R.id.appGM_phoneEdt);
		mQQEdt = (EditText) findViewById(R.id.appGM_QQEdt);
		mDetailEdt = (EditText) findViewById(R.id.appGM_detailEdt);
	}

	@Override
	public void onRightClick() {
		super.onRightClick();
		String phone = mPhoneEdt.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "请填写电话号码", Toast.LENGTH_SHORT).show();
			return;
		}
		String qq = mQQEdt.getText().toString();
		if (TextUtils.isEmpty(qq)) {
			Toast.makeText(this, "请填写QQ号码", Toast.LENGTH_SHORT).show();
			return;
		}
		String detail = mDetailEdt.getText().toString();
		if (TextUtils.isEmpty(detail)) {
			Toast.makeText(this, "请填写申请说明", Toast.LENGTH_SHORT).show();
			return;
		}
		applyGM(qq, phone, detail);
	}

	/**
	 * 申请为群管理员
	 * 
	 * @param qq
	 * @param phone
	 * @param detail
	 */
	private void applyGM(String qq, String phone, String detail) {
		RequstClient.apply_GroupManager(qq, phone, detail,
				new CustomResponseHandler(this, true) {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						Toast.makeText(ApplyGroupManagerActivity.this,
								"申请群管理员失败!", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("申请群管理员---", content);
						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								String errorMsg = obj.getString("Message");
								Toast.makeText(getBaseContext(), errorMsg,
										Toast.LENGTH_SHORT).show();
								return;
							}

							Toast.makeText(ApplyGroupManagerActivity.this,
									obj.getString("Data"), Toast.LENGTH_SHORT)
									.show();
							ApplyGroupManagerActivity.this.finish();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}
}
