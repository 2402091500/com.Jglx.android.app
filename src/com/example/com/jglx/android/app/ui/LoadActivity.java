package com.example.com.jglx.android.app.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.common.Constant;
import com.example.com.jglx.android.app.http.AsyncHttpResponseHandler;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.ActionInfo;
import com.example.com.jglx.android.app.info.InvateInfo_2;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.util.AppUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 启动页
 * 
 * @author jjj
 * 
 * @date 2015-8-7
 */
public class LoadActivity extends BaseActivity {

	private SharedPreferences mPreferences;
	private boolean isFirst;
	public List<ActionInfo> mList = new ArrayList<ActionInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);

		getInvateInfoList();
		LXApplication.mUserInfo = LXApplication.getLocalUserInfo(this);
		AppUtil.screenWidth = AppUtil.getScreenWH(this)[0];
		AppUtil.screenHeight = AppUtil.getScreenWH(this)[1];

		initData();

		new Thread() {
			public void run() {
				try {
					sleep(2000);
					if (isFirst) {// 第一次进入app
						Editor dEditor = mPreferences.edit();
						dEditor.putBoolean("isFirst", false);
						dEditor.commit();
						Intent intent = new Intent(LoadActivity.this,
								FirstActivity.class);
						startActivity(intent);
						LoadActivity.this.finish();
					} else {
						if (LXApplication.mUserInfo != null
								&& !TextUtils
										.isEmpty(LXApplication.mUserInfo.LoginPhone)) {
							// 自动登录,登录成功之后在跳转到主页
							doLogin();

						} else {
							Intent intent = new Intent(LoadActivity.this,
									LoginActivity.class);
							startActivity(intent);
							LoadActivity.this.finish();
						}
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			};
		}.start();
	}

	/**
	 * 自动登录
	 */
	private void doLogin() {
		final String pw = LXApplication.mUserInfo.LoginPw;

		RequstClient.doLogin(LXApplication.mUserInfo.LoginPhone,
				LXApplication.mUserInfo.LoginPw,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						Intent intent = new Intent(LoadActivity.this,
								LoginActivity.class);
						startActivity(intent);
						LoadActivity.this.finish();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);
						System.out.println("登录" + content);
						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								Intent intent = new Intent(LoadActivity.this,
										LoginActivity.class);
								startActivity(intent);
								LoadActivity.this.finish();

								return;
							}

							LXApplication.mUserInfo = new Gson().fromJson(
									new JSONObject(content).getJSONObject(
											"Data").toString(),
									UserInfo_2.class);
							LXApplication.mUserInfo.LoginPw = pw;

							String bId = LXApplication.mUserInfo.BuildingID;
							Set<String> set = new HashSet<String>();
							set.add(bId);
							set.add(LXApplication.mUserInfo.CityName);

							JPushInterface.setAliasAndTags(LoadActivity.this,
									LXApplication.mUserInfo.UserID, set,
									new TagAliasCallback() {

										@Override
										public void gotResult(int arg0,
												String arg1, Set<String> arg2) {
											if (arg0 == 0) {
												Log.e("---------推送别名-biaoqian----:",
														"推送别名注册成功---"
																+ LXApplication.mUserInfo.UserID);
											} else {
												Log.e("---------推送别名注册失败----:",
														String.valueOf(arg0));
											}
										}
									});

							login();

						} catch (JSONException e) {
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
	public void login() {

		// 调用sdk登陆方法登陆聊天服务器
		EMChatManager.getInstance().login(LXApplication.mUserInfo.ChatID,
				"linxing", new EMCallBack() {

					@Override
					public void onSuccess() {

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
							Intent intent = new Intent(LoadActivity.this,
									MainActivity.class);
							startActivity(intent);
							LoadActivity.this.finish();

							return;
						}
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(LoadActivity.this, "登录成功",
										Toast.LENGTH_SHORT).show();

								// 更改当前用户推送在ios的昵称
								EMChatManager
										.getInstance()
										.updateCurrentUserNick(
												LXApplication.mUserInfo.NickName);
								LXApplication.saveUserInfo(getBaseContext(),
										LXApplication.mUserInfo);
								LXApplication.isLogin = true;

								Intent intent = new Intent(LoadActivity.this,
										MainActivity.class);
								startActivity(intent);
								LoadActivity.this.finish();
							}
						});
					}

					@Override
					public void onProgress(int progress, String status) {
					}

					@Override
					public void onError(final int code, final String message) {
						runOnUiThread(new Runnable() {
							public void run() {

								Intent intent = new Intent(LoadActivity.this,
										LoginActivity.class);
								startActivity(intent);
								LoadActivity.this.finish();
							}
						});
					}
				});
	}

	/**
	 * 获取邀约信息-供首页使用
	 */
	public void getInvateInfoList() {
		RequstClient.QUERYTOPICLIST_home("", new CustomResponseHandler(this,
				false) {

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				Log.v("邀约列表---", content);

				try {
					JSONObject obj = new JSONObject(content);
					if (!obj.getString("State").equals("0")) {
						return;
					}
					Constant.invateInfoList = new Gson().fromJson(
							new JSONObject(content).getJSONArray("Data")
									.toString(),
							new TypeToken<List<InvateInfo_2>>() {
							}.getType());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initData() {

		mPreferences = getSharedPreferences("LinXin", Context.MODE_PRIVATE);
		isFirst = mPreferences.getBoolean("isFirst", true);
	}

}
