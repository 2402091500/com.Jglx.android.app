package com.example.com.jglx.android.app.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.db.PushDao;
import com.example.com.jglx.android.app.util.DateUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 推送消息充值的适配器
 * 
 * @author jjj
 * 
 * @date 2015-9-11
 */
public class PushReChargeAdapter extends BaseAdapter {
	private Context mContext;
	private List<Map<String, String>> mList;

	public PushReChargeAdapter(Context context, List<Map<String, String>> list) {
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
					R.layout.item_pushrecharge, null);
			holder = new ViewHolder(arg1);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		Map<String, String> map = getItem(arg0);
		String time = map.get(PushDao.time);
		holder.timeTv.setText(DateUtils.getTimestampString(new Date(Long
				.parseLong(time))));
		String data = map.get(PushDao.detail);
		try {
			JSONObject jsonObject = new JSONObject(data);
			JSONObject object = jsonObject.getJSONObject("Data");
			if (object != null) {
				holder.tv1
						.setText("账户" + object.getString("Card") + "的本次账单情况:");
				holder.dingdanTv.setText("订单:" + object.getString("ID"));
				holder.muchTv.setText("金额:" + object.getString("Money"));
				holder.typeTv.setText("商品:" + object.getString("Type"));
				holder.chargeTimeTv.setText(refFormatNowDate(Long
						.parseLong(time)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arg1;
	}

	public String refFormatNowDate(long cTime) {
		Date nowTime = new Date(cTime);
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;
	}

	class ViewHolder {
		TextView timeTv;
		TextView chargeTimeTv;
		TextView tv1;
		TextView dingdanTv;
		TextView muchTv;
		TextView typeTv;

		public ViewHolder(View view) {
			timeTv = (TextView) view
					.findViewById(R.id.item_pushrecharge_timeTv);
			chargeTimeTv = (TextView) view
					.findViewById(R.id.item_pushrecharge_billTimeTv);
			tv1 = (TextView) view.findViewById(R.id.item_pushrecharge_tv1);
			dingdanTv = (TextView) view
					.findViewById(R.id.item_pushrecharge_dingdanTv);
			muchTv = (TextView) view
					.findViewById(R.id.item_pushrecharge_moneyTv);
			typeTv = (TextView) view
					.findViewById(R.id.item_pushrecharge_typeTv);
		}
	}

}
