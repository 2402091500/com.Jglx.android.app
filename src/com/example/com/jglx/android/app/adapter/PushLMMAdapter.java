package com.example.com.jglx.android.app.adapter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.db.PushDao;
import com.example.com.jglx.android.app.util.DateUtils;
import com.example.com.jglx.android.app.view.CircleImageView;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 推送消息的适配器
 * 
 * @author jjj
 * 
 * @date 2015-9-11
 */
public class PushLMMAdapter extends BaseAdapter {
	private Context mContext;
	private List<Map<String, String>> mList;

	public PushLMMAdapter(Context context, List<Map<String, String>> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Map<String, String> getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (arg1 == null) {
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.item_pushlmm,
					null);
			holder = new ViewHolder(arg1);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		Map<String, String> map = getItem(arg0);

		holder.timeTv.setText(DateUtils.getTimestampString(new Date(Long
				.parseLong(map.get(PushDao.time)))));
		String data = map.get(PushDao.detail);

		if (!TextUtils.isEmpty(data)) {
			try {
				JSONObject jsonObject = new JSONObject(data);
				int type = jsonObject.getInt("Type");
				switch (type) {
				case 101:// 认证通过
					holder.iv.setImageResource(R.drawable.default_group);
					holder.titleTv
							.setText(LXApplication.mUserInfo.BuildingName);
					holder.contentTv.setText("您已通过认证");
					holder.showTv.setVisibility(View.GONE);
					break;
				case 102:// 认证不通过
					holder.iv.setImageResource(R.drawable.default_group);
					holder.titleTv
							.setText(LXApplication.mUserInfo.BuildingName);
					holder.contentTv.setText("您未通过认证");
					holder.showTv.setVisibility(View.GONE);
					break;
				case 105:// 收到好友邀请
					JSONObject object = (JSONObject) jsonObject.get("Data");
					if (object != null) {
						holder.showTv.setVisibility(View.VISIBLE);
						holder.titleTv.setText(object.getString("NickName"));
						holder.contentTv.setText("请求加你为好友");
						String iconP = object.getString("Logo");
						if (!TextUtils.isEmpty(iconP)) {
							holder.iv.setUrl(iconP);
						} else {
							holder.iv.setImageResource(R.drawable.default_head);
						}
					}
					break;

				case 109:// 管理员申请通过
					holder.iv.setImageResource(R.drawable.default_group);
					holder.titleTv
							.setText(LXApplication.mUserInfo.BuildingName);
					holder.contentTv.setText("您的管理员申请已经通过");
					holder.showTv.setVisibility(View.GONE);
					break;
				case 110:// 管理员申请不通过
					holder.iv.setImageResource(R.drawable.default_group);
					holder.titleTv
							.setText(LXApplication.mUserInfo.BuildingName);
					holder.contentTv.setText("您未通过管理员的申请");
					holder.showTv.setVisibility(View.GONE);
					break;
				case 205:// 邻友入驻
					holder.iv.setImageResource(R.drawable.default_group);
					holder.titleTv
							.setText(LXApplication.mUserInfo.BuildingName);
					holder.contentTv.setText(jsonObject.getString("Data"));
					holder.showTv.setVisibility(View.GONE);
					break;
				case 201:// 邻妹妹
					JSONObject objectLmm = jsonObject.getJSONObject("Data");
					if (objectLmm != null) {
						String pathString = objectLmm.getString("Logo");
						if (!TextUtils.isEmpty(pathString)) {
							holder.iv.setUrl(pathString);
						} else {
							holder.iv.setImageResource(R.drawable.icon_lmm);
						}
						holder.titleTv.setText(objectLmm.getString("name"));
						holder.contentTv.setVisibility(View.GONE);
					}
					holder.showTv.setVisibility(View.GONE);
					break;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		return arg1;
	}

	class ViewHolder {
		CircleImageView iv;
		TextView timeTv;
		TextView titleTv;
		TextView contentTv;
		TextView showTv;

		public ViewHolder(View view) {
			iv = (CircleImageView) view.findViewById(R.id.item_pushlmm_iconIv);
			timeTv = (TextView) view.findViewById(R.id.item_pushlmm_timeTv);
			titleTv = (TextView) view.findViewById(R.id.item_pushlmm_titleTv);
			contentTv = (TextView) view
					.findViewById(R.id.item_pushlmm_contentTv);
			showTv = (TextView) view.findViewById(R.id.item_pushlmm_showTv);
		}
	}

}
