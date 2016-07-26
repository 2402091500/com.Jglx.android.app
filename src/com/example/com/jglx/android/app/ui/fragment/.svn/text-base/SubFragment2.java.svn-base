package com.example.com.jglx.android.app.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.adapter.InvateApdater;
import com.example.com.jglx.android.app.adapter.InvateApdater.ItemGridViewClickListener;
import com.example.com.jglx.android.app.common.Constant;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.InvateInfo_2;
import com.example.com.jglx.android.app.interfaces.IconClickListener;
import com.example.com.jglx.android.app.interfaces.WebViewListener;
import com.example.com.jglx.android.app.ui.InvateDetailActivity;
import com.example.com.jglx.android.app.ui.ItemDetailActivity;
import com.example.com.jglx.android.app.ui.PersonCenterActivity;
import com.example.com.jglx.android.app.view.RfListView;
import com.example.com.jglx.android.app.view.RfListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 邀约
 * 
 * @author jjj
 * 
 * @date 2015-8-4
 */
public class SubFragment2 extends Fragment implements
		ItemGridViewClickListener, IconClickListener, IXListViewListener {
	private RfListView mRfListView;
	private List<InvateInfo_2> mList;
	private InvateApdater mApdater;
	private String[] itemNames;
	private String lastInvateId = "";
	private boolean isRefresh = true;

	public static boolean isReume = false;

	private WebViewListener mWebViewListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.subfragment_home, null);
		mRfListView = (RfListView) view.findViewById(R.id.sf_home_Lv);
		mRfListView.setOnItemClickListener(mListener);
		mRfListView.setXListViewListener(this);
		mRfListView.disableLoadMore();
		mRfListView.setPullRefreshEnable(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mList = new ArrayList<InvateInfo_2>();
		itemNames = getResources().getStringArray(R.array.item_namelist);
		mApdater = new InvateApdater(getActivity(), mList, this, itemNames);
		mWebViewListener = (WebViewListener) mApdater.getmInvateActionLayout();

		mRfListView.setAdapter(mApdater);

		if (Constant.invateInfoList != null
				&& Constant.invateInfoList.size() > 0) {
			if (Constant.invateInfoList.size() > 9) {
				mRfListView.setPullLoadEnable(true);
			}
			mList.addAll(Constant.invateInfoList);
			lastInvateId = mList.get(mList.size() - 1).InviteID;
			mApdater.notifyDataSetChanged();
		} else {
			lastInvateId = "";
			isRefresh = true;
			getInvateInfoList();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mWebViewListener != null) {
			mWebViewListener.doPause();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mWebViewListener != null) {
			mWebViewListener.doDestory(getActivity());
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isReume) {
			lastInvateId = "";
			isRefresh = true;
			getInvateInfoList();
			isReume = false;
		}

		if (mWebViewListener != null) {
			mWebViewListener.doResume();
		}
	}

	// 邀约信息点击事件
	OnItemClickListener mListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int i = arg2;
			if (arg2 == 6) {
				return;
			}
			if (arg2 > 1 && arg2 < 6) {
				i = arg2 - 1;
			} else if (arg2 > 6) {
				i = arg2 - 2;
			}
			InvateInfo_2 info_2 = mList.get(i - 1);
			if (info_2 != null) {

				isReume = true;
				Intent intent = new Intent(getActivity(),
						InvateDetailActivity.class);
				intent.putExtra("invateId", info_2.InviteID);
				startActivity(intent);
			}
		}

	};

	@Override
	public void itemGridViewClick(int position) {
		Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
		intent.putExtra("itemName", itemNames[position]);
		intent.putExtra("itemImg", position);
		startActivity(intent);
	}

	@Override
	public void onIconClick(int position) {

		Intent intent = new Intent(getActivity(), PersonCenterActivity.class);
		intent.putExtra("userId", mApdater.getItem(position).UserID);
		startActivity(intent);
	}

	/**
	 * 获取邀约信息
	 */
	public void getInvateInfoList() {
		RequstClient.QUERYTOPICLIST_home(lastInvateId,
				new CustomResponseHandler(getActivity(), true) {
					@Override
					public void onFailure(String error, String errorMessage) {
						super.onFailure(error, errorMessage);
						Toast.makeText(getActivity(), errorMessage,
								Toast.LENGTH_SHORT).show();
						if (isRefresh) {
							SimpleDateFormat sDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss");
							String date = sDateFormat
									.format(new java.util.Date());
							mRfListView.stopRefresh(date);
						}
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("邀约列表---", content);

						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								return;
							}
							List<InvateInfo_2> list = new Gson().fromJson(obj
									.getJSONArray("Data").toString(),
									new TypeToken<List<InvateInfo_2>>() {
									}.getType());

							if (isRefresh) {
								SimpleDateFormat sDateFormat = new SimpleDateFormat(
										"yyyy-MM-dd hh:mm:ss");
								String date = sDateFormat
										.format(new java.util.Date());
								mRfListView.stopRefresh(date);

								if (list != null && list.size() > 0) {
									if (mList.size() > 0) {
										mList.clear();
									}
									mList.addAll(list);
									mApdater.notifyDataSetChanged();
									if (list.size() > 9) {
										mRfListView.setPullLoadEnable(true);
									}
								} else {
									mRfListView.setPullLoadEnable(false);
									Toast.makeText(getActivity(), "目前还没有邀约数据",
											Toast.LENGTH_SHORT).show();
								}

							} else {
								mRfListView.stopLoadMore();

								if (list != null && list.size() > 0) {
									mList.addAll(list);
									mApdater.notifyDataSetChanged();
									mRfListView.setPullLoadEnable(true);
								} else {
									Toast.makeText(getActivity(), "没有更多信息了",
											Toast.LENGTH_SHORT).show();
									mRfListView.setPullLoadEnable(false);
								}

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onRefresh() {
		isRefresh = true;
		lastInvateId = "";
		getInvateInfoList();
	}

	@Override
	public void onLoadMore() {
		isRefresh = false;
		lastInvateId = mList.get(mList.size() - 1).InviteID;
		getInvateInfoList();
	}

}
