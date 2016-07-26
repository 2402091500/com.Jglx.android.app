package com.example.com.jglx.android.app.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.common.Constant;
import com.example.com.jglx.android.app.db.LXDBManager;
import com.example.com.jglx.android.app.helper.HXSDKHelper;
import com.example.com.jglx.android.app.helper.LXHXSDKHelper;
import com.example.com.jglx.android.app.http.AsyncHttpResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.PushInfo;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.util.DateUtils;
import com.example.com.jglx.android.app.util.SmileUtils;
import com.example.com.jglx.android.app.view.CircleImageView;
import com.google.gson.Gson;

/**
 * 显示所有聊天记录adpater
 * 
 */
public class ChatAllHistoryAdapter extends ArrayAdapter<Map<String, Object>> {

	private LayoutInflater inflater;
//	private List<Map<String, Object>> conversationList;
//	private List<Map<String, Object>> copyConversationList;
//	private ConversationFilter conversationFilter;
//	private boolean notiyfyByFilter;
	private Map<String, UserInfo_2> contactMap;

	private String mChatId;
	private TextView mNameTv;
	private CircleImageView mIconIv;

	public ChatAllHistoryAdapter(Context context, int textViewResourceId,
			List<Map<String, Object>> objects) {
		super(context, textViewResourceId, objects);
//		this.conversationList = objects;
//		copyConversationList = new ArrayList<Map<String, Object>>();
//		copyConversationList.addAll(objects);
		inflater = LayoutInflater.from(context);
	}

	public void setContactMap(Map<String, UserInfo_2> contactMap) {
		this.contactMap = contactMap;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.item_fmessag, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Map<String, Object> chatMap = getItem(position);
		int type = (Integer) chatMap.get("type");

		if (type == 0) {// 会话消息类
			// 获取与此用户/群组的会话
			EMConversation conversation = (EMConversation) chatMap
					.get("conversation");
			// 获取用户username或者群组groupid
			String username = conversation.getUserName();

			String name = username;
			// 设置昵称\头像
			if (contactMap != null && contactMap.containsKey(username)) {
				UserInfo_2 uInfo_2 = contactMap.get(username);

				if (!TextUtils.isEmpty(uInfo_2.NickName)) {
					name = uInfo_2.NickName;
				}
				if (!TextUtils.isEmpty(uInfo_2.Logo)) {
					holder.avatar.setUrl(uInfo_2.Logo);
				} else {
					holder.avatar.setImageResource(R.drawable.default_head);
				}
				holder.name.setText(name);
			} else {
				mChatId = username;
				mNameTv = holder.name;
				mIconIv = holder.avatar;
				getUserInfoByChatID();
			}

			// 显示消息未读数
			if (conversation.getUnreadMsgCount() > 0) {
				holder.unreadLabel.setText(String.valueOf(conversation
						.getUnreadMsgCount()));
				holder.unreadLabel.setVisibility(View.VISIBLE);
			} else {
				holder.unreadLabel.setVisibility(View.INVISIBLE);
			}

			// 把最后一条消息的内容作为item的message内容
			if (conversation.getMsgCount() != 0) {
				EMMessage lastMessage = conversation.getLastMessage();
				holder.message.setText(SmileUtils.getSmiledText(getContext(),
						getMessageDigest(lastMessage, (this.getContext()))),
						BufferType.SPANNABLE);

				holder.time.setText(DateUtils.getTimestampString(new Date(
						lastMessage.getMsgTime())));
				if (lastMessage.direct == EMMessage.Direct.SEND
						&& lastMessage.status == EMMessage.Status.FAIL) {
					holder.msgState.setVisibility(View.VISIBLE);
				} else {
					holder.msgState.setVisibility(View.GONE);
				}
			}
		} else if (type == 1) {
			PushInfo info = (PushInfo) chatMap.get("pushInfo");
			if (info != null) {
				switch (info.getCode()) {
				case 201:
					holder.avatar.setUrl2("", R.drawable.icon_lmm);
//					holder.avatar.setImageResource(R.drawable.icon_lmm);
					break;
				case 202:
					holder.avatar.setUrl2("", R.drawable.icon_chongzhi);
//					holder.avatar.setImageResource(R.drawable.icon_chongzhi);
					break;
				case 203:
					
					holder.avatar.setUrl2("", R.drawable.icon_baoming);
//					holder.avatar.setImageResource(R.drawable.icon_baoming);
					break;
				case 204:
					holder.avatar.setUrl2("", R.drawable.icon_shop);
//					holder.avatar.setImageResource(R.drawable.icon_shop);
					break;

				default:
					break;
				}

				holder.name.setText(info.getTitle());
				holder.message.setText(info.getContent());
				if (info.getCount() == 0) {
					holder.unreadLabel.setVisibility(View.INVISIBLE);
				} else {
					holder.unreadLabel.setVisibility(View.VISIBLE);
					holder.unreadLabel.setText(String.valueOf(info.getCount()));
				}
				holder.time.setText(DateUtils.getTimestampString(new Date(Long
						.parseLong(info.getId()))));
			}

		}
		return convertView;
	}

	/**
	 * 通过环信Id获取用户资料
	 * 
	 * @param chatId
	 * @param nameTv
	 * @param iconIv
	 */
	private void getUserInfoByChatID() {

		RequstClient.Get_UserInfo_byChatID(mChatId,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						mNameTv.setText(mChatId);
						mIconIv.setImageResource(R.drawable.default_head);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("chatId获取用户资料---", content);

						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								mNameTv.setText(mChatId);
								mIconIv.setImageResource(R.drawable.default_head);
								return;
							}
							UserInfo_2 user = new Gson().fromJson(
									new JSONObject(content).getString("Data"),
									UserInfo_2.class);
							String name = mChatId;

							if (user != null) {
								if (!TextUtils.isEmpty(user.NickName)) {
									name = user.NickName;
								}
								if (!TextUtils.isEmpty(user.Logo)) {
									mIconIv.setUrl(user.Logo);
								} else {
									mIconIv.setImageResource(R.drawable.default_head);
								}

								LXDBManager manager = LXDBManager
										.getInstance(getContext());
								manager.addChatUser(user);
							} else {
								mIconIv.setImageResource(R.drawable.default_head);
							}

							mNameTv.setText(name);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

	/**
	 * 根据消息内容和消息类型获取消息内容提示
	 * 
	 * @param message
	 * @param context
	 * @return
	 */
	private String getMessageDigest(EMMessage message, Context context) {
		String digest = "";
		switch (message.getType()) {
		case LOCATION: // 位置消息
			if (message.direct == EMMessage.Direct.RECEIVE) {
				digest = "位置消息";
				return digest;
			} else {
				digest = "我的位置";
			}
			break;
		case IMAGE: // 图片消息
			digest = "图片";
			break;
		case VOICE:// 语音消息
			digest = " 语音";
			break;
		case VIDEO: // 视频消息
			digest = " 视频";
			break;
		case TXT: // 文本消息

			if (((LXHXSDKHelper) HXSDKHelper.getInstance())
					.isRobotMenuMessage(message)) {
				digest = ((LXHXSDKHelper) HXSDKHelper.getInstance())
						.getRobotMenuMessageDigest(message);
			} else if (message.getBooleanAttribute(
					Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
				TextMessageBody txtBody = (TextMessageBody) message.getBody();
				digest = "文本消息" + txtBody.getMessage();
			} else {
				TextMessageBody txtBody = (TextMessageBody) message.getBody();
				digest = txtBody.getMessage();
			}
			break;
		case FILE: // 普通文件消息
			digest = " 普通文件";
			break;
		default:
			return "";
		}

		return digest;
	}

	private static class ViewHolder {
		/** 和谁的聊天记录 */
		TextView name;
		/** 消息未读数 */
		TextView unreadLabel;
		/** 最后一条消息的内容 */
		TextView message;
		/** 最后一条消息的时间 */
		TextView time;
		/** 用户头像 */
		CircleImageView avatar;
		// /** 最后一条消息的发送状态 */
		View msgState;

		public ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.item_fMsg_nameTv);
			unreadLabel = (TextView) view
					.findViewById(R.id.item_fMsg_unreadNumberTv);
			message = (TextView) view.findViewById(R.id.item_fMsg_msgTv);
			time = (TextView) view.findViewById(R.id.item_fMsg_timeTv);
			avatar = (CircleImageView) view
					.findViewById(R.id.item_fMsg_avaterIv);
			msgState = view.findViewById(R.id.item_fMsg_stateTv);
		}

	}

	// String getStrng(Context context, int resId) {
	// return context.getResources().getString(resId);
	// }
	//
//	@Override
//	public Filter getFilter() {
//		if (conversationFilter == null) {
//			conversationFilter = new ConversationFilter(conversationList);
//		}
//		return conversationFilter;
//	}
//
//	//
//	private class ConversationFilter extends Filter {
//		List<Map<String, Object>> mOriginalValues = null;
//
//		public ConversationFilter(List<Map<String, Object>> mList) {
//			mOriginalValues = mList;
//		}
//
//		@Override
//		protected FilterResults performFiltering(CharSequence prefix) {
//			FilterResults results = new FilterResults();
//
//			if (mOriginalValues == null) {
//				mOriginalValues = new ArrayList<Map<String, Object>>();
//			}
//			if (prefix == null || prefix.length() == 0) {
//				results.values = copyConversationList;
//				results.count = copyConversationList.size();
//			} else {
//				String prefixString = prefix.toString();
//				final int count = mOriginalValues.size();
//				final ArrayList<EMConversation> newValues = new ArrayList<EMConversation>();
//
//				for (int i = 0; i < count; i++) {
//					Map<String, Object> map = mOriginalValues.get(i);
//					EMConversation value = null;
//					if (map != null && map.containsKey("conversation")) {
//						value = (EMConversation) map.get("conversation");
//					}
//					if (value != null) {
//						String username = value.getUserName();
//						EMGroup group = EMGroupManager.getInstance().getGroup(
//								username);
//						if (group != null) {
//							username = group.getGroupName();
//						}
//						// First match against the whole ,non-splitted value
//						if (username.startsWith(prefixString)) {
//							newValues.add(value);
//						} else {
//							final String[] words = username.split(" ");
//							final int wordCount = words.length;
//
//							// Start at index 0, in case valueText starts with
//							// space(s)
//							for (int k = 0; k < wordCount; k++) {
//								if (words[k].startsWith(prefixString)) {
//									newValues.add(value);
//									break;
//								}
//							}
//						}
//					}
//
//				}
//
//				results.values = newValues;
//				results.count = newValues.size();
//			}
//			return results;
//		}
//
//		@Override
//		protected void publishResults(CharSequence constraint,
//				FilterResults results) {
//			conversationList.clear();
//			conversationList.addAll((List<Map<String, Object>>) results.values);
//			if (results.count > 0) {
//				notiyfyByFilter = true;
//				notifyDataSetChanged();
//			} else {
//				notifyDataSetInvalidated();
//			}
//
//		}
//
//	}
//
//	@Override
//	public void notifyDataSetChanged() {
//		super.notifyDataSetChanged();
//		if (!notiyfyByFilter) {
//			copyConversationList.clear();
//			copyConversationList.addAll(conversationList);
//			notiyfyByFilter = false;
//		}
//	}
}
