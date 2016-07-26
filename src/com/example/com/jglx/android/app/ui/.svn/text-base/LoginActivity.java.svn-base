package com.example.com.jglx.android.app.ui;

import java.util.HashSet;
import java.util.Set;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.AsyncHttpResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.util.DialogUtil;
import com.example.com.jglx.android.app.util.LogUtil;
import com.google.gson.Gson;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.example.com.jglx.android.app.util.CommonUtils;
import com.example.com.jglx.android.app.view.CircleImageView;

/**
 * 登录
 * 
 * @author jjj
 * 
 * @date 2015-8-7
 */
public class LoginActivity extends BaseActivity {

	private EditText mEmailET;
	private EditText mPwdET;
	private Intent intent = null;
	private UserInfo_2 mUserInfo;

	private Dialog dialog;
	private CircleImageView login_iconIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initData();
		initView();

	}

	private void initData() {
		mUserInfo = LXApplication.mUserInfo;
	}

	private void initView() {
		findViewById(R.id.login_loginBtn).setOnClickListener(this);
		findViewById(R.id.login_rigsterTv).setOnClickListener(this);
		findViewById(R.id.login_missPWTv).setOnClickListener(this);
		mEmailET = (EditText) findViewById(R.id.login_nameTv);
		mPwdET = (EditText) findViewById(R.id.login_PWTv);
		login_iconIv = (CircleImageView) findViewById(R.id.login_iconIv);

		if (mUserInfo != null) {
			mEmailET.setText(mUserInfo.LoginPhone);
			mPwdET.setText(mUserInfo.LoginPw);
			mEmailET.addTextChangedListener(watcher);

			if (mEmailET.getText().toString() != ""
					&& mEmailET.getText().toString() != null) {
				LXApplication.LocalTouxian = LXApplication.getLocaltouxian(
						this, mEmailET.getText().toString());

				if (LXApplication.LocalTouxian != ""
						&& LXApplication.LocalTouxian != null) {
					Bitmap bit = BitmapFactory
							.decodeFile(LXApplication.LocalTouxian);
					login_iconIv.setImageBitmap(bit);
				} else if (mUserInfo.Logo == "" || mUserInfo.Logo == null) {
					login_iconIv.setImageBitmap(BitmapFactory.decodeResource(
							getResources(), R.drawable.default_head));

				} else {

					login_iconIv.setUrl(mUserInfo.Logo);
				}
			} else {

				login_iconIv.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.default_head));
			}

		}

		dialog = DialogUtil.getCenterProgressDialog(this,
				R.string.dialog_login, true);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case R.id.login_loginBtn:// 登录
			String name = mEmailET.getText().toString();
			String password = mPwdET.getText().toString();
			if (TextUtils.isEmpty(name)) {
				Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			doLogin(name, password);
			break;
		case R.id.login_rigsterTv:// 注册

			intent = new Intent(this, Register1Activity.class);
			intent.putExtra("type", 1);
			startActivity(intent);
			break;
		case R.id.login_missPWTv:// 忘记密码
			intent = new Intent(this, Register1Activity.class);
			intent.putExtra("type", 2);
			startActivity(intent);
			break;
		}
	}

	/*************************************************************************
	 * @Title: doLogin
	 * @Description: TODO( 用户登陆)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 ************************************************************************** 
	 */
	private void doLogin(String userName, String password) {
		DialogUtil.showDialog(dialog, -1);

		RequstClient.doLogin(userName, password,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						DialogUtil.dissDialog(dialog);

						Toast.makeText(LoginActivity.this, "登录失败",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);
						LogUtil.i("MainActivity", "login:" + content);
						Log.v("MainActivity", "login--:" + content);

						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								DialogUtil.dissDialog(dialog);

								String errorMsg = obj.getString("Message");
								Toast.makeText(LoginActivity.this, errorMsg,
										Toast.LENGTH_SHORT).show();
								return;
							}

							mUserInfo = new Gson().fromJson(new JSONObject(
									content).getJSONObject("Data").toString(),
									UserInfo_2.class);
							mUserInfo.LoginPw = mPwdET.getText().toString();

							if (!TextUtils.isEmpty(mUserInfo.BuildingID)) {
								String bId = mUserInfo.BuildingID;
								Set<String> set = new HashSet<String>();
								set.add(bId);
								set.add(mUserInfo.CityName);
								JPushInterface.setAliasAndTags(
										LoginActivity.this, mUserInfo.UserID,
										set, new TagAliasCallback() {

											@Override
											public void gotResult(int arg0,
													String arg1,
													Set<String> arg2) {
												if (arg0 == 0) {
													Log.e("---------推送别名-biaoqian----:",
															"推送别名注册成功---"
																	+ mUserInfo.UserID);
												} else {
													Log.e("---------推送别名注册失败----:",
															String.valueOf(arg0));
												}
											}
										});

							}

							login(mUserInfo.ChatID, "linxing");

						} catch (JSONException e) {
							DialogUtil.dissDialog(dialog);
							e.printStackTrace();
						}

					}
				});
	}

	/**
	 * 登录环信
	 * 
	 * @param view
	 */
	public void login(String userName, String password) {
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
			return;
		}

		final long start = System.currentTimeMillis();
		// 调用sdk登陆方法登陆聊天服务器
		EMChatManager.getInstance().login(userName, password, new EMCallBack() {

			@Override
			public void onSuccess() {

				DialogUtil.dissDialog(dialog);
				try {
					// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
					// ** manually load all local groups and
					EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();

				} catch (Exception e) {
					e.printStackTrace();
					// 取好友或者群聊失败，不让进入主页面
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"取好友或者群聊失败", 1).show();
						}
					});
					// return;
				}
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), "登录成功",
								Toast.LENGTH_SHORT).show();
						LXApplication.mUserInfo = mUserInfo;
						// 更改当前用户推送在ios的昵称
						EMChatManager.getInstance().updateCurrentUserNick(
								mUserInfo.NickName);
						LXApplication.saveUserInfo(getBaseContext(), mUserInfo);
						LXApplication.isLogin = true;
					}
				});
				if (TextUtils.isEmpty(mUserInfo.Logo)
						|| TextUtils.isEmpty(mUserInfo.NickName)) {
					intent = new Intent(LoginActivity.this,
							FirstFinishActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				} else {
					intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
			}

			@Override
			public void onProgress(int progress, String status) {
			}

			@Override
			public void onError(final int code, final String message) {
				runOnUiThread(new Runnable() {
					public void run() {
						DialogUtil.dissDialog(dialog);
						Toast.makeText(getApplicationContext(),
								"登录失败" + message, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mPwdET.setText("");
			if (mEmailET.getText().toString() != ""
					&& mEmailET.getText().toString() != null) {
				LXApplication.mUserInfo = LXApplication
						.getLocalUserInfo_ByPhone(LoginActivity.this, mEmailET
								.getText().toString());
				LXApplication.LocalTouxian = LXApplication.getLocaltouxian(
						LoginActivity.this, mEmailET.getText().toString());

				if (LXApplication.LocalTouxian != ""
						&& LXApplication.LocalTouxian != null) {
					Bitmap bit = BitmapFactory
							.decodeFile(LXApplication.LocalTouxian);
					login_iconIv.setImageBitmap(bit);
				} else if (mUserInfo.Logo == "" || mUserInfo.Logo == null) {
					login_iconIv.setImageBitmap(BitmapFactory.decodeResource(
							getResources(), R.drawable.default_head));

				}

				else {

					login_iconIv.setUrl(LXApplication.mUserInfo.Logo);
				}
			} else {
				login_iconIv.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.default_head));
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (mEmailET.getText().toString() == ""
					&& mEmailET.getText().toString() == null) {
				login_iconIv.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.default_head));
			}

			// TODO Auto-generated method stub

		}
	};

}
