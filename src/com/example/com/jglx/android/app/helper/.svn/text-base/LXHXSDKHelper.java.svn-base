/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.com.jglx.android.app.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.util.EMLog;
import com.easemob.util.EasyUtils;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.common.Constant;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.model.CallReceiver;
import com.example.com.jglx.android.app.model.DemoHXSDKModel;
import com.example.com.jglx.android.app.model.HXNotifier;
import com.example.com.jglx.android.app.model.HXNotifier.HXNotificationInfoProvider;
import com.example.com.jglx.android.app.model.HXSDKModel;
import com.example.com.jglx.android.app.ui.ChatActivity;
import com.example.com.jglx.android.app.ui.MainActivity;
import com.example.com.jglx.android.app.ui.VideoCallActivity;
import com.example.com.jglx.android.app.ui.VoiceCallActivity;

/**
 * Demo UI HX SDK helper class which subclass HXSDKHelper
 * 
 * @author easemob
 * 
 */
public class LXHXSDKHelper extends HXSDKHelper {

	private static final String TAG = "DemoHXSDKHelper";

	/**
	 * EMEventListener
	 */
	protected EMEventListener eventListener = null;

	private CallReceiver callReceiver;

	/**
	 * 用来记录foreground Activity
	 */
	private List<Activity> activityList = new ArrayList<Activity>();

	public void pushActivity(Activity activity) {
		if (!activityList.contains(activity)) {
			activityList.add(0, activity);
		}
	}

	public void popActivity(Activity activity) {
		activityList.remove(activity);
	}

	@Override
	protected void initHXOptions() {
		super.initHXOptions();

		// you can also get EMChatOptions to set related SDK options
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		options.allowChatroomOwnerLeave(getModel()
				.isChatroomOwnerLeaveAllowed());
	}

	@Override
	protected void initListener() {
		super.initListener();
		IntentFilter callFilter = new IntentFilter(EMChatManager.getInstance()
				.getIncomingCallBroadcastAction());
		if (callReceiver == null) {
			callReceiver = new CallReceiver();
		}

		// 注册通话广播接收者
		appContext.registerReceiver(callReceiver, callFilter);
		// 注册消息事件监听
		initEventListener();
	}

	/**
	 * 全局事件监听 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理 activityList.size()
	 * <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
	 */
	protected void initEventListener() {
		eventListener = new EMEventListener() {
			private BroadcastReceiver broadCastReceiver = null;

			@Override
			public void onEvent(EMNotifierEvent event) {
				EMMessage message = null;
				if (event.getData() instanceof EMMessage) {
					message = (EMMessage) event.getData();
					EMLog.d(TAG, "receive the event : " + event.getEvent()
							+ ",id : " + message.getMsgId());
				}

				switch (event.getEvent()) {
				case EventNewMessage:
					// 应用在后台，不需要刷新UI,通知栏提示新消息
					if (activityList.size() <= 0) {
						HXSDKHelper.getInstance().getNotifier()
								.onNewMsg(message);
					}
					break;
				case EventOfflineMessage:
					if (activityList.size() <= 0) {
						EMLog.d(TAG, "received offline messages");
						List<EMMessage> messages = (List<EMMessage>) event
								.getData();
						HXSDKHelper.getInstance().getNotifier()
								.onNewMesg(messages);
					}
					break;
				// below is just giving a example to show a cmd toast, the app
				// should not follow this
				// so be careful of this
				case EventNewCMDMessage: {

					EMLog.d(TAG, "收到透传消息");
					// 获取消息body
					CmdMessageBody cmdMsgBody = (CmdMessageBody) message
							.getBody();
					final String action = cmdMsgBody.action;// 获取自定义action

					// 获取扩展属性 此处省略
					// message.getStringAttribute("");
					EMLog.d(TAG, String.format("透传消息：action:%s,message:%s",
							action, message.toString()));
					final String str = "Receive cmd message : action：";

					final String CMD_TOAST_BROADCAST = "easemob.demo.cmd.toast";
					IntentFilter cmdFilter = new IntentFilter(
							CMD_TOAST_BROADCAST);

					if (broadCastReceiver == null) {
						broadCastReceiver = new BroadcastReceiver() {

							@Override
							public void onReceive(Context context, Intent intent) {
								Toast.makeText(
										appContext,
										intent.getStringExtra("cmd_value")
												+ "邻信", Toast.LENGTH_SHORT)
										.show();
							}
						};

						// 注册广播接收者
						appContext.registerReceiver(broadCastReceiver,
								cmdFilter);
					}

					Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
					broadcastIntent.putExtra("cmd_value", str + action);
					appContext.sendBroadcast(broadcastIntent, null);

					break;
				}
				case EventDeliveryAck:
					message.setDelivered(true);
					break;
				case EventReadAck:
					message.setAcked(true);
					break;
				default:
					break;
				}

			}
		};

		EMChatManager.getInstance().registerEventListener(eventListener);
	}

	/**
	 * 自定义通知栏提示内容
	 * 
	 * @return
	 */
	@Override
	protected HXNotificationInfoProvider getNotificationListener() {
		// 可以覆盖默认的设置
		return new HXNotificationInfoProvider() {

			@Override
			public String getTitle(EMMessage message) {
				// 修改标题,这里使用默认
				return null;
			}

			@Override
			public int getSmallIcon(EMMessage message) {
				// 设置小图标，这里为默认
				return R.drawable.linxin_logo;
			}

			@Override
			public String getDisplayedText(EMMessage message) {
				// 设置状态栏的消息提示，可以根据message的类型做相应提示
				// String ticker = CommonUtils.getMessageDigest(message,
				// appContext);
				// if (message.getType() == Type.TXT) {
				// ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
				// }
				// Map<String, UserInfo_2> robotMap = ((LXHXSDKHelper)
				// HXSDKHelper
				// .getInstance()).get;
				// if (robotMap != null &&
				// robotMap.containsKey(message.getFrom())) {
				// String nick = robotMap.get(message.getFrom()).getNick();
				// if (!TextUtils.isEmpty(nick)) {
				// return nick + ": " + ticker;
				// } else {
				// return message.getFrom() + ": " + ticker;
				// }
				// } else {
				// return message.getFrom() + ": " + ticker;
				// }
				return "";
			}

			@Override
			public String getLatestText(EMMessage message, int fromUsersNum,
					int messageNum) {
				return fromUsersNum + "好友发来了" + messageNum + "条消息";
			}

			@Override
			public Intent getLaunchIntent(EMMessage message) {
				// 设置点击通知栏跳转事件
				Intent intent = null;
				// 有电话时优先跳转到通话页面
				if (isVideoCalling) {
					intent = new Intent(appContext, VideoCallActivity.class);
				} else if (isVoiceCalling) {
					intent = new Intent(appContext, VoiceCallActivity.class);
				} else {
					intent = new Intent(appContext, ChatActivity.class);
					ChatType chatType = message.getChatType();
					if (chatType == ChatType.Chat) { // 单聊信息
						intent.putExtra(ChatActivity.chat_id, message.getFrom());
						intent.putExtra(ChatActivity.chat_type,
								ChatActivity.CHATTYPE_SINGLE);
					} else { // 群聊信息
						// message.getTo()为群聊id
						intent.putExtra(ChatActivity.chat_id, message.getTo());
						if (chatType == ChatType.GroupChat) {
							intent.putExtra(ChatActivity.chat_type,
									ChatActivity.CHATTYPE_GROUP);
						}

					}
				}
				return intent;
			}
		};
	}

	@Override
	protected void onConnectionConflict() {
		Intent intent = new Intent(appContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("conflict", true);
		appContext.startActivity(intent);
	}

	@Override
	protected void onCurrentAccountRemoved() {
		Intent intent = new Intent(appContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(Constant.ACCOUNT_REMOVED, true);
		appContext.startActivity(intent);
	}

	@Override
	protected HXSDKModel createModel() {
		return new DemoHXSDKModel(appContext);
	}

	@Override
	public HXNotifier createNotifier() {
		return new HXNotifier() {
			public synchronized void onNewMsg(final EMMessage message) {
				if (EMChatManager.getInstance().isSlientMessage(message)) {
					return;
				}

				String chatUsename = null;
				List<String> notNotifyIds = null;
				// 获取设置的不提示新消息的用户或者群组ids
				if (message.getChatType() == ChatType.Chat) {
					chatUsename = message.getFrom();
					// notNotifyIds = ((DemoHXSDKModel) hxModel)
					// .getDisabledGroups();
				} else {
					chatUsename = message.getTo();
					// notNotifyIds = ((DemoHXSDKModel)
					// hxModel).getDisabledIds();
				}

				if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
					// 判断app是否在后台
					if (!EasyUtils.isAppRunningForeground(appContext)) {
						EMLog.d(TAG, "app is running in backgroud");
						sendNotification(message, false);
					} else {
						sendNotification(message, true);

					}

					viberateAndPlayTone(message);
				}
			}
		};
	}

	/**
	 * get demo HX SDK Model
	 */
	public DemoHXSDKModel getModel() {
		return (DemoHXSDKModel) hxModel;
	}

	public boolean isRobotMenuMessage(EMMessage message) {

		try {
			JSONObject jsonObj = message
					.getJSONObjectAttribute(Constant.MESSAGE_ATTR_ROBOT_MSGTYPE);
			if (jsonObj.has("choice")) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public String getRobotMenuMessageDigest(EMMessage message) {
		String title = "";
		try {
			JSONObject jsonObj = message
					.getJSONObjectAttribute(Constant.MESSAGE_ATTR_ROBOT_MSGTYPE);
			if (jsonObj.has("choice")) {
				JSONObject jsonChoice = jsonObj.getJSONObject("choice");
				title = jsonChoice.getString("title");
			}
		} catch (Exception e) {
		}
		return title;
	}

	@Override
	public void logout(final EMCallBack callback) {
		endCall();
		super.logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				//清除联系人列表\好友列表等
				
				if (callback != null) {
					callback.onSuccess();
				}
			}

			@Override
			public void onError(int code, String message) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgress(int progress, String status) {
				// TODO Auto-generated method stub
				if (callback != null) {
					callback.onProgress(progress, status);
				}
			}

		});
	}

	void endCall() {
		try {
			EMChatManager.getInstance().endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
