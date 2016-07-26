package com.example.com.jglx.android.app.adapter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.db.PushDao;
import com.example.com.jglx.android.app.util.DateUtils;
import com.example.com.jglx.android.app.view.RetangleImageView;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 推送消息商家的适配器
 * 
 * @author jjj
 * 
 * @date 2015-9-11
 */
public class PushActionAdapter extends BaseAdapter {
	private Context mContext;
	private List<Map<String, String>> mList;

	public PushActionAdapter(Context context, List<Map<String, String>> list) {
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
			arg1 = LayoutInflater.from(mContext).inflate(
					R.layout.item_pushaction, null);
			holder = new ViewHolder(arg1);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		Map<String, String> map = getItem(arg0);

		holder.timeTv.setText(DateUtils.getTimestampString(new Date(Long
				.parseLong(map.get(PushDao.time)))));
		String data = map.get(PushDao.detail);
		try {
			JSONObject jsonObject = new JSONObject(data);
			JSONObject object = jsonObject.getJSONObject("Data");
			if (object != null) {
				holder.titleTv.setText(object.getString("name"));
				holder.dateTv.setText(object.getString("Data"));
				String path = object.getString("Logo");
				if (!TextUtils.isEmpty(path)) {
					holder.iv.setUrl(path);
					holder.iv.setVisibility(View.VISIBLE);
				} else {
					holder.iv.setVisibility(View.GONE);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arg1;
	}

	class ViewHolder {
		TextView timeTv;
		TextView dateTv;
		TextView titleTv;
		RetangleImageView iv;

		public ViewHolder(View view) {
			timeTv = (TextView) view.findViewById(R.id.item_pushaction_timeTv);
			dateTv = (TextView) view.findViewById(R.id.item_pushaction_dateTv);
			titleTv = (TextView) view
					.findViewById(R.id.item_pushaction_titleTv);
			iv = (RetangleImageView) view.findViewById(R.id.item_pushaction_iv);
		}
	}

}
