package com.example.com.jglx.android.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.adapter.GroupDetailAdapter;
import com.example.com.jglx.android.app.adapter.GroupGagAdapter;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.view.RGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 群成员
 * 
 * @author jjj
 * 
 * @date 2015-8-17
 */
public class GroupMemberActivity extends BaseActivity {
	private RGridView mGv;
	private Button mGagBtn;// 禁言
	private TextView mTv;
	private RGridView mGagedGv;// 已被禁言的
	private Button mGagCancelBtn;// 取消禁言
	private GroupGagAdapter mGagAdapter;
	private GroupGagAdapter mGagedAdapter;
	private GroupDetailAdapter mDetailAdapter;

	private boolean isGM = false;// 是否为小区管理员
	private List<Map<String, Object>> mList;// 未被禁言成员
	private List<UserInfo_2> mMemberList;
	private List<Map<String, Object>> mGagedList;// 已被禁言的成员

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_groupmember);
		setTitleTextRightText("", "群成员", "", true);

		initView();

		getGroupDetail();
		mGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (isGM) {// 管理员操作

					Map<String, Object> map = mList.get(arg2);
					UserInfo_2 user = (UserInfo_2) map.get("user");
					if (user.AuditingState != 2) {
						Toast.makeText(GroupMemberActivity.this,
								"该成员还未通过认证,无需禁言", Toast.LENGTH_SHORT).show();
						return;
					}
					if (user.AuditingState == 3) {
						Toast.makeText(GroupMemberActivity.this, "此为管理员,不能禁言",
								Toast.LENGTH_SHORT).show();
						return;
					}

					if ((Boolean) map.get("isSelect")) {

						map.put("isSelect", false);
					} else {
						map.put("isSelect", true);
					}
					mGagAdapter.notifyDataSetChanged();
				} else {
					UserInfo_2 uInfo_2 = mDetailAdapter.getItem(arg2);
					if (uInfo_2 != null) {
						Intent intent = new Intent(GroupMemberActivity.this,
								PersonCenterActivity.class);
						intent.putExtra("userId", uInfo_2.UserID);
						startActivity(intent);
						GroupMemberActivity.this.finish();
					}
				}
			}
		});

		mGagedGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, Object> map = mGagedList.get(arg2);

				if ((Boolean) map.get("isSelect")) {

					map.put("isSelect", false);
				} else {
					map.put("isSelect", true);
				}
				mGagedAdapter.notifyDataSetChanged();
			}
		});
	}

	private void initView() {
		mGv = (RGridView) findViewById(R.id.groupMember_gv);
		mGagBtn = (Button) findViewById(R.id.groupMember_gagBtn);
		mGagBtn.setOnClickListener(onGagListener);
		mTv = (TextView) findViewById(R.id.groupMember_tv);
		mGagedGv = (RGridView) findViewById(R.id.groupMember_gagedGv);
		mGagCancelBtn = (Button) findViewById(R.id.groupMember_gagCancelBtn);
		mGagCancelBtn.setOnClickListener(onGagCancelListener);
		isGM = getIntent().getBooleanExtra("isGM", false);
		if (isGM) {
			mGagBtn.setVisibility(View.VISIBLE);
		}
		mList = new ArrayList<Map<String, Object>>();
		mGagedList = new ArrayList<Map<String, Object>>();
		mMemberList = new ArrayList<UserInfo_2>();

		mGagAdapter = new GroupGagAdapter(GroupMemberActivity.this, mList);
		mGv.setAdapter(mGagAdapter);

		mGagedAdapter = new GroupGagAdapter(GroupMemberActivity.this,
				mGagedList);
		mGagedGv.setAdapter(mGagedAdapter);
	}

	OnClickListener onGagListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			boolean isHave = false;
			int size = mList.size();
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					Map<String, Object> map = mList.get(i);
					if ((Boolean) map.get("isSelect")) {
						isHave = true;

						gagByGM(map, "False", i);
					}
				}
				if (!isHave) {
					Toast.makeText(GroupMemberActivity.this, "请选择需要禁言的成员!",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	/**
	 * 取消禁言
	 */
	OnClickListener onGagCancelListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			boolean isHave = false;
			int size = mGagedList.size();
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = mGagedList.get(i);
				if ((Boolean) map.get("isSelect")) {
					isHave = true;

					gagByGM(map, "True", i);
				}
			}
			if (!isHave) {
				Toast.makeText(GroupMemberActivity.this, "请选择需要取消禁言的成员!",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * 网络获取小区详情
	 */
	private void getGroupDetail() {
		RequstClient.getLXFrendList(LXApplication.mUserInfo.BuildingID,
				new CustomResponseHandler(this, true) {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						Toast.makeText(GroupMemberActivity.this, "获取群组详情失败!",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("群组详情---", content);
						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								String errorMsg = obj.getString("Message");
								Toast.makeText(getBaseContext(), errorMsg,
										Toast.LENGTH_SHORT).show();
								return;
							}

							List<UserInfo_2> list = new Gson().fromJson(
									new JSONObject(content)
											.getJSONArray("Data").toString(),
									new TypeToken<List<UserInfo_2>>() {
									}.getType());
							int size = list.size();
							if (list != null && size > 0) {

								if (isGM) {// 禁言
									for (int lis = 0; lis < size; lis++) {
										Map<String, Object> map = new HashMap<String, Object>();
										UserInfo_2 uInfo_2 = list.get(lis);

										map.put("user", uInfo_2);
										map.put("isSelect", false);
										if (uInfo_2.AuditingState == 4) {
											map.put("isGag", true);
											mGagedList.add(map);

										} else {
											map.put("isGag", false);
											mList.add(map);
										}
									}

									mGagAdapter.notifyDataSetChanged();
									if (mGagedList.size() > 0) {
										mTv.setVisibility(View.VISIBLE);
										mGagedGv.setVisibility(View.VISIBLE);
										mGagCancelBtn
												.setVisibility(View.VISIBLE);
										mGagedAdapter.notifyDataSetChanged();
									}
								} else {
									mMemberList.addAll(list);
									mMemberList.add(0, LXApplication.mUserInfo);
									mDetailAdapter = new GroupDetailAdapter(
											GroupMemberActivity.this, 1,
											mMemberList);
									mGv.setAdapter(mDetailAdapter);
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * 群管理员禁言
	 * 
	 * @param userId
	 * @param state
	 * @param userName
	 */
	private void gagByGM(final Map<String, Object> map, final String state,
			final int pos) {
		final UserInfo_2 uInfo_2 = (UserInfo_2) map.get("user");
		RequstClient.gag_ByGroupManager(uInfo_2.UserID, state,
				new CustomResponseHandler(this, true) {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						if (state.equals("True")) {// 取消禁言
							Toast.makeText(GroupMemberActivity.this,
									"对" + uInfo_2.NickName + "取消禁言失败!",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(GroupMemberActivity.this,
									"对" + uInfo_2.NickName + "禁言失败!",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("禁言/取消禁言---", content);
						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								String errorMsg = obj.getString("Message");
								Toast.makeText(getBaseContext(), errorMsg,
										Toast.LENGTH_SHORT).show();
								return;
							}
							if (map != null) {
								if (state.equals("True")) {// 取消禁言
									map.put("isSelect", false);
									map.put("isGag", false);

									mGagedList.remove(pos);
									mList.add(map);
									if (mGagedList.size() == 0) {
										mTv.setVisibility(View.GONE);
										mGagedGv.setVisibility(View.GONE);
										mGagCancelBtn.setVisibility(View.GONE);
									}

								} else {
									map.put("isGag", true);
									map.put("isSelect", false);
									mList.remove(pos);
									mGagedList.add(map);
									mTv.setVisibility(View.VISIBLE);
									mGagedGv.setVisibility(View.VISIBLE);
									mGagCancelBtn.setVisibility(View.VISIBLE);
								}
								mGagedAdapter.notifyDataSetChanged();
								mGagAdapter.notifyDataSetChanged();
								JPushInterface.setAlias(
										GroupMemberActivity.this,
										uInfo_2.UserID, new TagAliasCallback() {

											@Override
											public void gotResult(int arg0,
													String arg1,
													Set<String> arg2) {

												if (arg0 == 0) {
													Log.e("---------已被禁言----:",
															arg1);
												} else {
													Log.e("---------禁言申请别名错误----:",
															String.valueOf(arg0));
												}
											}
										});
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

}
