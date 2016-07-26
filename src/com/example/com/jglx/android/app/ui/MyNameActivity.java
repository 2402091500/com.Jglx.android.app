package com.example.com.jglx.android.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.util.LogUtil;

/**
 * 昵称修改
 * 
 * @author jjj
 * 
 * @date 2015-8-5
 */
public class MyNameActivity extends BaseActivity {
	private EditText name_nameTv;
	private UserInfo_2 user;
	private String name;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_name);
		setTitleTextRightText("", "昵称", "保存", true);
		setRightTvColor(R.color.green);

		name_nameTv = (EditText) findViewById(R.id.name_nameEdt);
		name_nameTv.setText(LXApplication.mUserInfo.NickName);
	}

	@Override
	public void onRightClick() {
		super.onRightClick();

		name = name_nameTv.getText().toString();
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		user = LXApplication.mUserInfo;
		RequstClient.XiuGaiyonHu_zhiliao(name, String.valueOf(user.Sex),
				user.Birthday, String.valueOf(user.Age), user.Signatures,
				new CustomResponseHandler(this, true) {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Toast.makeText(MyNameActivity.this, "修改昵称失败",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						LogUtil.i("MyNameActivity", "MyNameActivity:" + content);

						try {
							JSONObject object = new JSONObject(content);
							if (!object.getString("State").equals("0")) {
								Toast.makeText(MyNameActivity.this,
										object.getString("Message"),
										Toast.LENGTH_SHORT).show();
								return;
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

						Toast.makeText(MyNameActivity.this, "昵称修改成功",
								Toast.LENGTH_SHORT).show();

						LXApplication.mUserInfo.NickName = name;
						// 更改当前用户推送在ios的昵称
						EMChatManager.getInstance().updateCurrentUserNick(name);

						startActivity(new Intent(MyNameActivity.this,
								MyInfoActivity.class));
						MyNameActivity.this.finish();

					}
				});

	}
}