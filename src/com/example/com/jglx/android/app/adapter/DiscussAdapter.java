package com.example.com.jglx.android.app.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.info.InvateInfo_2;

/**
 * 评论适配器
 * 
 * @author jjj
 * 
 * @date 2015-8-24
 */
public class DiscussAdapter extends BaseAdapter {
	private List<InvateInfo_2> mList;
	private LayoutInflater mInflater;

	public DiscussAdapter(Context context, List<InvateInfo_2> list) {
		mInflater = LayoutInflater.from(context);
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public InvateInfo_2 getItem(int arg0) {
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
			arg1 = mInflater.inflate(R.layout.item_invatedetail_discuss, null);
			holder = new ViewHolder(arg1);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		InvateInfo_2 info = getItem(arg0);
		if (info != null && !TextUtils.isEmpty(info.NickName)
				&& !TextUtils.isEmpty(info.Detail)) {
			holder.contentTv.setText(
					Html.fromHtml("<font color='#35bc9c'>" + info.NickName
							+ "</font>" + "&nbsp &nbsp " + info.Detail),
					TextView.BufferType.SPANNABLE);
		}
		return arg1;
	}

	class ViewHolder {
		TextView contentTv;

		public ViewHolder(View view) {
			contentTv = (TextView) view
					.findViewById(R.id.item_invatedetail_discuss_textTv);
		}
	}
}
