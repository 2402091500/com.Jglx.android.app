package com.example.com.jglx.android.app.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.AbsListView.LayoutParams;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.adapter.ItemDetailAdapter;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.InvateInfo_2;
import com.example.com.jglx.android.app.interfaces.IconClickListener;
import com.example.com.jglx.android.app.interfaces.ImgClickListener;
import com.example.com.jglx.android.app.interfaces.WebViewListener;
import com.example.com.jglx.android.app.util.AppUtil;
import com.example.com.jglx.android.app.view.RfListView;
import com.example.com.jglx.android.app.view.RfListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 主题详情
 * 
 * @author jjj
 * 
 * @date 2015-8-6
 */
public class ItemDetailActivity extends BaseActivity implements
		IXListViewListener, IconClickListener, ImgClickListener {
	private RfListView mRfListView;
	private List<InvateInfo_2> mList;
	private ItemDetailAdapter mAdapter;
	private FrameLayout mLayout;
	private boolean isRefresh = true;
	private String lastInvateId = "";

	private int itemImg = 0;
	private int[] itemImgs = new int[] { R.drawable.item_mother_bg,
			R.drawable.item_lvyou_bg, R.drawable.item_animal_bg,
			R.drawable.item_yezhu_bg, R.drawable.item_paiyou_bg,
			R.drawable.item_father_bg, R.drawable.item_active_bg,
			R.drawable.item_game_bg, R.drawable.item_bagua_bg };

	private WebViewListener mViewListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_itemdetail);

		String itemName = getIntent().getStringExtra("itemName");
		itemImg = getIntent().getIntExtra("itemImg", 0);
		if (!TextUtils.isEmpty(itemName)) {
			setTitleTextRightText("", itemName, "", true);
		}

		initView();
		initData();
	}

	private void initView() {
		mRfListView = (RfListView) findViewById(R.id.itemDetail_lv);
		mRfListView.setOnItemClickListener(mListener);
		mLayout = (FrameLayout) findViewById(R.id.itemDetail_Layout);
		mLayout.setBackgroundResource(itemImgs[itemImg]);
		mRfListView.setXListViewListener(this);
		mRfListView.setPullRefreshEnable(true);
		mRfListView.disableLoadMore();

		View arg1 = LayoutInflater.from(this).inflate(
				R.layout.itemdetail_headerview, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				AppUtil.screenHeight * 2 / 3);
		arg1.setLayoutParams(params);
		mRfListView.addHeaderView(arg1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mViewListener != null) {
			mViewListener.doResume();
		}
		getInvateInfoList();

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mViewListener != null) {
			mViewListener.doPause();
		}
	}

	@Override
	public void finish() {
		super.finish();
		if (mViewListener != null) {
			mViewListener.doDestory(this);
		}
	}

	private void initData() {

		mList = new ArrayList<InvateInfo_2>();

		mAdapter = new ItemDetailAdapter(this, mList);
		mRfListView.setAdapter(mAdapter);

		mViewListener = mAdapter.getmActionLayout();
	}

	// 邀约信息点击事件
	OnItemClickListener mListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			InvateInfo_2 info_2 = null;
			int size = mList.size();

			if (size < 4) {
				if (arg2 - 2 == size) {// 广告
					return;
				}
				info_2 = mList.get(arg2 - 2);
			} else {
				if (arg2 - 2 == 4) {// 广告

					return;
				}
				if (arg2 - 2 < 4) {
					info_2 = mList.get(arg2 - 2);
				} else {
					info_2 = mList.get(arg2 - 3);
				}
			}

			if (info_2 != null) {
				Intent intent = new Intent(ItemDetailActivity.this,
						InvateDetailActivity.class);
				intent.putExtra("invateId", info_2.InviteID);
				startActivity(intent);
			}
		}

	};

	/**
	 * 获取邀约信息
	 */
	public void getInvateInfoList() {
		RequstClient.QUERYTOPICLIST_Item(String.valueOf(itemImg + 1),
				lastInvateId, new CustomResponseHandler(
						ItemDetailActivity.this, false) {
					@Override
					public void onFailure(String error, String errorMessage) {
						super.onFailure(error, errorMessage);
						Toast.makeText(ItemDetailActivity.this, errorMessage,
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
									mAdapter.notifyDataSetChanged();
									if (list.size() > 9) {
										mRfListView.setPullLoadEnable(true);
									}
								} else {
									mRfListView.setPullLoadEnable(false);
									Toast.makeText(ItemDetailActivity.this,
											"目前还没有邀约数据", Toast.LENGTH_SHORT)
											.show();
								}

							} else {
								mRfListView.stopLoadMore();

								if (list != null && list.size() > 0) {
									mList.addAll(list);
									mAdapter.notifyDataSetChanged();
									mRfListView.setPullLoadEnable(true);
								} else {
									Toast.makeText(ItemDetailActivity.this,
											"没有更多信息了", Toast.LENGTH_SHORT)
											.show();
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

	@Override
	public void onImgClick(int curImg, String invateID) {
		Intent intent = new Intent(this, ShowImgActivity.class);
		intent.putExtra("curImg", curImg);
		intent.putExtra("invateID", invateID);
		startActivity(intent);
	}

	@Override
	public void onIconClick(int position) {
		Intent intent = new Intent(this, PersonCenterActivity.class);
		intent.putExtra("userId",
				((InvateInfo_2) mAdapter.getItem(position)).UserID);
		startActivity(intent);
	}

}
