package com.example.com.jglx.android.app.adapter;

import java.util.List;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.info.BillInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 账单适配器
 * 
 * @author jjj
 * 
 * @date 2015-8-31
 */
public class BillAdapter extends ArrayAdapter<BillInfo> {
	private List<BillInfo> mList;
	private Context mContext;

	public BillAdapter(Context context, int resource, List<BillInfo> objects) {
		super(context, resource, objects);
		this.mList = objects;
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_bill, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	class ViewHolder {
		TextView timeTv;
		TextView typeTv;
		TextView dateTv;
		TextView yinMoneyTv;
		TextView resultTv;
		TextView muchTv;

		public ViewHolder(View view) {
			timeTv = (TextView) view.findViewById(R.id.item_bill_timeTv);
			typeTv = (TextView) view.findViewById(R.id.item_bill_titleTv);
			dateTv = (TextView) view.findViewById(R.id.item_bill_billTimeTv);
			yinMoneyTv = (TextView) view
					.findViewById(R.id.item_bill_yinMoneyTv);
			resultTv = (TextView) view.findViewById(R.id.item_bill_shiMoneyTv);
			muchTv = (TextView) view.findViewById(R.id.item_bill_muchTv);
		}
	}

}
