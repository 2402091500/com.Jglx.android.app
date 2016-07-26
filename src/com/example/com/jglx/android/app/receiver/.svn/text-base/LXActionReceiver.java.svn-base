package com.example.com.jglx.android.app.receiver;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.common.Constant;
import com.example.com.jglx.android.app.db.LXDBManager;
import com.example.com.jglx.android.app.db.PushDao;
import com.example.com.jglx.android.app.info.AddfriendInfo;
import com.example.com.jglx.android.app.info.PushInfo;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.jpush.android.api.JPushInterface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * 自定义推送广播
 * 
 * @author jjj
 * 
 * @date 2015-8-27
 */
public class LXActionReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();
		Log.e("收到了yitiaop消息。消息内容是：------",
				"----------------------" + intent.getAction());
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.e("收到了自定义消息。消息内容是：------",
					"----------------------"
							+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);

			if (!TextUtils.isEmpty(msg)) {
				try {
					JSONObject jsonObject = new JSONObject(msg);
					int code = jsonObject.getInt("Type");
					linxinReceived(context, jsonObject, code, msg);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			System.out.println("收到了通知");
			// 在这里可以做些统计，或者做些其他工作
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			System.out.println("用户点击打开了通知");
			// 在这里可以自己写代码去定义用户点击后的行为
			// actionListener=(LXActionListener) context;
			// Intent i = new Intent(context, MainActivity.class); // 自定义打开的界面
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(i);
		} else {
			// Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

	/**
	 * 自定义消息处理
	 * 
	 * @param context
	 * @param object
	 * @param code
	 */
	private void linxinReceived(Context context, JSONObject object, int code,
			String detail) {
		String time = String.valueOf(System.currentTimeMillis());

		if (object != null) {
			switch (code) {
			case 101:// 认证通过
				LXApplication.mUserInfo.AuditingState = 2;
				addPushInfoToDB(context, "邻妹妹", "您已通过小区认证", 201, time);
				addLmm(context, time, detail);
				sendLXBrocast(context, code);
				break;
			case 102:// 认证不通过
				addPushInfoToDB(context, "邻妹妹", "您未通过小区认证", 201, time);
				addLmm(context, time, detail);
				sendLXBrocast(context, code);
				break;
			case 103:// 禁言
				LXApplication.mUserInfo.AuditingState = 4;
				break;
			case 104:// 解除禁言
				LXApplication.mUserInfo.AuditingState = 2;
				break;
			case 105:// 收到好友申请
				UserInfo_2 usInfo_2;
				try {
					usInfo_2 = new Gson().fromJson(object.get("Data")
							.toString(), UserInfo_2.class);

					if (usInfo_2 != null) {
						LXDBManager manager = LXDBManager.getInstance(context);
						AddfriendInfo u = manager
								.queryAddFriendInfo(usInfo_2.UserID);

						if (u == null) {
							AddfriendInfo info = new AddfriendInfo();
							info.setFriendChatID(usInfo_2.ChatID);
							info.setFriendId(usInfo_2.UserID);
							info.setFriendName(usInfo_2.NickName);
							info.setFriendAvatar(usInfo_2.Logo);
							info.setIsAdd(0);
							manager.addAddFriendInfo(info);
							addPushInfoToDB(context, "邻妹妹", "您收到一条"
									+ usInfo_2.NickName + "发来的好友请求消息", 201,
									time);
							addLmm(context, time, detail);

							sendLXBrocast(context, code);
						}
					}
				} catch (JsonSyntaxException e1) {
					e1.printStackTrace();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;

			case 107:// 好友申请被拒绝

				break;
			case 108:// 解除好友关系

				break;
			case 109:// 申请管理员通过
				LXApplication.mUserInfo.AuditingState = 3;
				addPushInfoToDB(context, "邻妹妹", "您的管理员申请已审核通过", 201, time);
				addLmm(context, time, detail);
				sendLXBrocast(context, code);
				break;
			case 110:// 申请管理员不通过
				addPushInfoToDB(context, "邻妹妹", "您没有通过管理员的申请", 201, time);
				addLmm(context, time, detail);
				sendLXBrocast(context, code);
				break;
			case 201:// 邻妹妹消息
				try {
					JSONObject jsonObject = (JSONObject) object.get("Data");
					if (jsonObject != null) {
						// id为0时表示自定义广告,name表示广告内容
						// id大于0时表示活动广告,name表示活动名称
						String name = jsonObject.getString("name");
						if (!TextUtils.isEmpty(name)) {
							addPushInfoToDB(context, "邻妹妹", name, 201, time);
							addLmm(context, time, detail);
							sendLXBrocast(context, code);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case 202:// 充值

				try {
					JSONObject objEnroll = object.getJSONObject("Data");
					if (objEnroll != null) {
						String msg = objEnroll.getString("Type");
						if (!TextUtils.isEmpty(msg)) {
							addPushInfoToDB(context, "充值消息", msg, code, time);
							addRecharge(context, time, detail);
							sendLXBrocast(context, code);
						}
					}

				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;
			case 203:// 报名
				try {
					JSONObject objEnroll = object.getJSONObject("Data");
					if (objEnroll != null) {
						String msg1 = objEnroll.getString("Name");
						if (!TextUtils.isEmpty(msg1)) {
							addPushInfoToDB(context, "报名消息", msg1, code, time);
							addEnroll(context, time, detail);
							sendLXBrocast(context, code);
						}
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;
			case 204:// 商家
				try {
					JSONObject jsonObject = (JSONObject) object.get("Data");
					if (jsonObject != null) {
						// id为0时表示自定义广告,name表示广告内容
						// id大于0时表示活动广告,name表示活动名称
						String name = jsonObject.getString("name");
						if (!TextUtils.isEmpty(name)) {
							addPushInfoToDB(context, " 商家消息", name, code, time);
							addShop(context, time, detail);
							sendLXBrocast(context, code);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case 205:// 邻友入驻
				String msg2;
				try {
					msg2 = object.getString("Data");
					if (!TextUtils.isEmpty(msg2)) {
						addPushInfoToDB(context, "邻妹妹", msg2, 201, time);
						addLmm(context, time, detail);
						sendLXBrocast(context, code);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;
			}
		}
	}

	/**
	 * 发送广播操作
	 * 
	 * @param context
	 * @param code
	 */
	private void sendLXBrocast(Context context, int code) {

		Intent intent = new Intent();
		intent.setAction(Constant.LXAction);
		intent.putExtra("Code", code);
		context.sendBroadcast(intent);
	}

	/**
	 * 往数据库里面添加推送消息
	 * 
	 * @param context
	 * @param title
	 * @param content
	 * @param code
	 */
	public void addPushInfoToDB(Context context, String title, String content,
			int code, String time) {
		PushInfo pInfo = new PushInfo();
		pInfo.setId(time);
		pInfo.setTitle(title);
		pInfo.setContent(content);
		pInfo.setCode(code);
		pInfo.setCount(1);
		LXDBManager manager = LXDBManager.getInstance(context);
		manager.addPushInfo(pInfo, code);
	}

	/**
	 * 添加充值
	 * 
	 * @param context
	 * @param time
	 * @param detail
	 */
	private void addRecharge(Context context, String time, String detail) {
		PushDao dao = PushDao.getInstance(context);
		dao.addRecharge(time, detail);
	}

	/**
	 * 添加报名
	 * 
	 * @param context
	 * @param time
	 * @param detail
	 */
	private void addEnroll(Context context, String time, String detail) {
		PushDao dao = PushDao.getInstance(context);
		dao.addEnroll(time, detail);
	}

	/**
	 * 添加邻妹妹
	 * 
	 * @param context
	 * @param time
	 * @param detail
	 */
	private void addLmm(Context context, String time, String detail) {
		PushDao dao = PushDao.getInstance(context);
		dao.addLmm(time, detail);
	}

	/**
	 * 添加商家
	 * 
	 * @param context
	 * @param time
	 * @param detail
	 */
	private void addShop(Context context, String time, String detail) {
		PushDao dao = PushDao.getInstance(context);
		dao.addShop(time, detail);
	}

}
