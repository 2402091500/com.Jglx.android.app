package com.example.com.jglx.android.app.ui;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.XinpanInfo_2;
import com.example.com.jglx.android.app.view.RetangleImageView;
import com.example.com.jglx.android.app.view.RfListView;
import com.example.com.jglx.android.app.view.RfListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Fuwu_XinPanTuiJianActivity extends BaseActivity implements IXListViewListener{

	private RfListView mRfListView;
	public List<XinpanInfo_2> mList;
	private XinpanAdapter mAdapter;
	private TextView hotsellTv;
	private TextView lowpriceTv;
	private TextView newpanTv;
	private TextView allTv;
	private String city;
	private TextView search_tv;
	private EditText txt_search;
	public String type="0";
	
	private boolean isfresh=true;
	private boolean has_dialog=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_fuwu__xin_pan_tui_jian);
		setTitleTextRightText("", "新盘推荐", "", true);

		initView();
		getData("0");

	}

	private void initData() {
		mList = LXApplication.m_loupan_List;

		mAdapter = new XinpanAdapter();
		mRfListView.setAdapter(mAdapter);
	}

	public void getData(String type) {

		if (LXApplication.addr == null || LXApplication.addr.equals("")) {
			city = "重庆";
		} else {
			city = LXApplication.addr.substring(0,
					LXApplication.addr.length() - 1);
		}
		if(isfresh){
			has_dialog=false;
		}

		RequstClient.Get_loupan_list2( type, new CustomResponseHandler(
				this, has_dialog) {

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				Log.v("新盘列表---", content);
				System.out.println("新盘列表---" + content);

				try {
					JSONObject obj = new JSONObject(content);
					if (!obj.getString("State").equals("0")) {
						String errorMsg = obj.getString("Message");
						return;
					}
					if (isfresh) {
						SimpleDateFormat sDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						String date = sDateFormat
								.format(new java.util.Date());
						mRfListView.stopRefresh(date);
						isfresh=false;
						}
					Log.v("新盘列表---", content);
					mList = new Gson().fromJson(new JSONObject(content)
							.getJSONArray("Data").toString(),
							new TypeToken<List<XinpanInfo_2>>() {
							}.getType());

					LXApplication.m_loupan_List = mList;

					initData();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.xptj_allTv:
			allTv.setTextColor(Color.BLACK);
			hotsellTv.setTextColor(Color.GRAY);
			lowpriceTv.setTextColor(Color.GRAY);
			newpanTv.setTextColor(Color.GRAY);
			
			type="0";
			getData("0");
			break;
		case R.id.xptj_hotsell:
			allTv.setTextColor(Color.GRAY);
			hotsellTv.setTextColor(Color.BLACK);
			lowpriceTv.setTextColor(Color.GRAY);
			newpanTv.setTextColor(Color.GRAY);
			type="1";
			getData("1");
			break;
		case R.id.xptj_lowprice:
			allTv.setTextColor(Color.GRAY);
			hotsellTv.setTextColor(Color.GRAY);
			lowpriceTv.setTextColor(Color.BLACK);
			newpanTv.setTextColor(Color.GRAY);
			type="2";
			getData("2");
			break;
		case R.id.xptj_newpan:
			allTv.setTextColor(Color.GRAY);
			hotsellTv.setTextColor(Color.GRAY);
			lowpriceTv.setTextColor(Color.GRAY);
			newpanTv.setTextColor(Color.BLACK);
			type="3";
			getData("3");
			break;
		}
	}

	private void initView() {

		mRfListView = (RfListView) findViewById(R.id.fuwu_xingpan);
		mRfListView.setOnItemClickListener(mListener);
		// 全部
		allTv = (TextView) findViewById(R.id.xptj_allTv);
		allTv.setOnClickListener(this);

		// 热销
		hotsellTv = (TextView) findViewById(R.id.xptj_hotsell);
		hotsellTv.setOnClickListener(this);
		// 特价
		lowpriceTv = (TextView) findViewById(R.id.xptj_lowprice);
		lowpriceTv.setOnClickListener(this);
		// 新盘
		newpanTv = (TextView) findViewById(R.id.xptj_newpan);
		newpanTv.setOnClickListener(this);

		search_tv = (TextView) findViewById(R.id.search_tv);
		search_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				get_search_Data();
				
			}
		});
		txt_search=(EditText)findViewById(R.id.txt_search);
		txt_search.addTextChangedListener(new Edit_textwach());
		mRfListView.disableLoadMore();
		mRfListView.setOnItemClickListener(mListener);
		mRfListView.setXListViewListener(this);

	}

	protected void get_search_Data() {
		String txt=txt_search.getText().toString().replaceAll("\\s*", "");
		RequstClient.Get_loupan_list3(txt, type, new CustomResponseHandler(this, true){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				try {
					JSONObject obj = new JSONObject(content);
					if (!obj.getString("State").equals("0")) {
						String errorMsg = obj.getString("Message");
						return;
					}
					Log.v("新盘列表---", content);
					mList = new Gson().fromJson(new JSONObject(content)
							.getJSONArray("Data").toString(),
							new TypeToken<List<XinpanInfo_2>>() {
							}.getType());

					LXApplication.m_loupan_List = mList;
					mAdapter.notifyDataSetChanged();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	OnItemClickListener mListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// XinpanInfo_2

			XinpanInfo_2 info_2 = mList.get(arg2 - 1);
			if (info_2 != null) {
				Intent intent = new Intent(Fuwu_XinPanTuiJianActivity.this,
						Fuwu_xinpan_ditail_Activity.class);
				intent.putExtra("PropertyID", info_2.getPropertyID());
				startActivity(intent);
			}

		}

	};


	/*
	 * "PropertyID": 1, //楼盘ID "Name": "财富园", //楼盘名称 "CityName": "重庆", //所在城市
	 * "Logo": "PropertyImage/0.png", //封面图片 "MinPrice": 5000.00, //最低价
	 * "MaxPrice": 9000.00, //最高价 "Browses": 1000, //浏览量 "Label": "写字楼 学区房" //标签
	 */
	class XinpanAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList == null ? 0 : mList.size();
		}

		@Override
		public XinpanInfo_2 getItem(int arg0) {
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
				arg1 = LayoutInflater.from(Fuwu_XinPanTuiJianActivity.this)
						.inflate(R.layout.item_xingpan, null);
				holder = new ViewHolder(arg1);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}

			XinpanInfo_2 info = getItem(arg0);
			if (info != null) {
				holder.titleTv.setText(info.getName());
				holder.tipsTv.setText(info.getLable());
				System.out.println("该是的楼盘标签：" + info.getLable());
				holder.timesTv.setText("浏览" + info.getBrowses() + "次");
				holder.imgIv.setUrl(info.getLogo());
			}
			return arg1;
		}

		class ViewHolder {
			RetangleImageView imgIv;
			TextView titleTv;
			TextView tipsTv;
			TextView timesTv;

			public ViewHolder(View view) {
				imgIv = (RetangleImageView) view.findViewById(R.id.xinpan_imag);
				titleTv = (TextView) view.findViewById(R.id.xinpan_title);
				tipsTv = (TextView) view.findViewById(R.id.xinpan_tips);
				timesTv = (TextView) view.findViewById(R.id.xinpan_times);
			}
		}

	}
	class Edit_textwach implements TextWatcher{

		@Override
		public void afterTextChanged(Editable s) {
			get_search_Data();
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void onRefresh() {
		isfresh=true;
		getData(type);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}
