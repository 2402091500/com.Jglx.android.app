package com.example.com.jglx.android.app.ui.fragment;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.common.Constant;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.info.XinpanInfo_2;
import com.example.com.jglx.android.app.ui.MyInfoActivity;
import com.example.com.jglx.android.app.ui.MyPhotoActivity;
import com.example.com.jglx.android.app.ui.MySheZhiActivity;
import com.example.com.jglx.android.app.ui.MyWalletActivity;
import com.example.com.jglx.android.app.ui.My_ErWeiMaActivity;
import com.example.com.jglx.android.app.ui.My_RenZhenActivity;
import com.example.com.jglx.android.app.ui.My_tuijian_share_Activity;
import com.example.com.jglx.android.app.view.CircleImageView;

/**
 * 首页-我的
 * 
 * @author jjj
 * 
 * @date 2015-9-15
 */
public class FragmentMy extends Fragment implements OnClickListener {

	private CircleImageView v;
	private TextView nichen;
	private LinearLayout mWeiRenzhengLayout;
	private RelativeLayout mRenzhengLayout;
	private TextView xiaoquming;
	private TextView my_midle2_xiaoqumin;
	public TextView my_midle2_renzen;

	public List<XinpanInfo_2> mList;
	private ActiveReceiver mActiveReceiver;
	private RZReceiver mRZReceiver;
	private UserInfo_2 user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mActiveReceiver = new ActiveReceiver();
		getActivity().registerReceiver(mActiveReceiver,
				new IntentFilter(Constant.LXAction));
		mRZReceiver = new RZReceiver();
		getActivity().registerReceiver(mRZReceiver,
				new IntentFilter("renzheng"));

		View view = inflater.inflate(R.layout.fragment_my, null);

		view.findViewById(R.id.my_tv_xianceTv).setOnClickListener(this);
		view.findViewById(R.id.my_tv_qianbaoTv).setOnClickListener(this);
		view.findViewById(R.id.my_tv_bianjiTv).setOnClickListener(this);
		view.findViewById(R.id.my_tv_sehzhiTv).setOnClickListener(this);
		view.findViewById(R.id.my_tv_renzhenzliao).setOnClickListener(this);
		view.findViewById(R.id.my_tv_erweima).setOnClickListener(this);
		view.findViewById(R.id.my_tv_fenxiang).setOnClickListener(this);

		v = (CircleImageView) view.findViewById(R.id.my_img_personphoto);
		nichen = (TextView) view.findViewById(R.id.my_tv_nicheng);

		mWeiRenzhengLayout = (LinearLayout) view
				.findViewById(R.id.f_my_weiRenzhengLayout);
		mRenzhengLayout = (RelativeLayout) view
				.findViewById(R.id.f_my_RenzhengLayout);
		xiaoquming = (TextView) view.findViewById(R.id.my_tv_renzhenxq);
		my_midle2_renzen = (TextView) view.findViewById(R.id.my_midle2_renzen);
		my_midle2_xiaoqumin = (TextView) view
				.findViewById(R.id.my_midle2_xiaoqumin);

		initdata();

		return view;
	}

	private void initdata() {
		user = LXApplication.mUserInfo;
		System.out.println(user.Logo);
		if (LXApplication.LocalTouxian != ""
				&& LXApplication.LocalTouxian != null) {
			Bitmap bit = BitmapFactory.decodeFile(LXApplication.LocalTouxian);
			v.setImageBitmap(bit);
		} else if (user.Logo == "" || user.Logo == null) {
			v.setImageBitmap(BitmapFactory.decodeResource(getResources(),
					R.drawable.default_head));
		} else {

			v.setUrl(user.Logo);
		}
		nichen.setText(user.NickName);
		// 0.未认证 1.认证中 2.已认证
		switch (user.AuditingState) {
		case 0:
			xiaoquming.setText(user.BuildingName + "(未认证)");
			mWeiRenzhengLayout.setVisibility(View.VISIBLE);
			mRenzhengLayout.setVisibility(View.GONE);
			break;
		case 1:

			xiaoquming.setText(user.BuildingName + "(未认证)");
			my_midle2_renzen.setText("正在认证中");
			my_midle2_xiaoqumin.setText(user.BuildingName);
			mWeiRenzhengLayout.setVisibility(View.GONE);
			mRenzhengLayout.setVisibility(View.VISIBLE);
			break;
		case 2:
			my_midle2_renzen.setText("已认证");
			my_midle2_xiaoqumin.setText(user.BuildingName);
			mWeiRenzhengLayout.setVisibility(View.GONE);
			mRenzhengLayout.setVisibility(View.VISIBLE);
			xiaoquming.setText(user.BuildingName + "(已认证)");
			break;
		case 3:
			my_midle2_renzen.setText("已认证");
			my_midle2_xiaoqumin.setText(user.BuildingName);
			xiaoquming.setText(user.BuildingName + "(已认证)");
			mWeiRenzhengLayout.setVisibility(View.GONE);
			mRenzhengLayout.setVisibility(View.VISIBLE);
			break;
		case 4:
			my_midle2_renzen.setText("已认证");
			my_midle2_xiaoqumin.setText(user.BuildingName);
			xiaoquming.setText(user.BuildingName + "(已认证)");
			mWeiRenzhengLayout.setVisibility(View.GONE);
			mRenzhengLayout.setVisibility(View.VISIBLE);
			break;

		}
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = null;

		switch (arg0.getId()) {
		case R.id.my_tv_xianceTv:
			intent = new Intent(getActivity(), MyPhotoActivity.class);
			startActivity(intent);

			break;
		case R.id.my_tv_qianbaoTv:
			intent = new Intent(getActivity(), MyWalletActivity.class);
			startActivity(intent);
			break;
		case R.id.my_tv_bianjiTv:
			intent = new Intent(getActivity(), MyInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.my_tv_sehzhiTv:
			intent = new Intent(getActivity(), MySheZhiActivity.class);
			startActivity(intent);
			break;
		case R.id.my_tv_renzhenzliao:
			intent = new Intent(getActivity(), My_RenZhenActivity.class);
			startActivity(intent);
			break;
		case R.id.my_tv_fenxiang:
			intent = new Intent(getActivity(), My_tuijian_share_Activity.class);
			startActivity(intent);
			break;
		case R.id.my_tv_erweima:
			intent = new Intent(getActivity(), My_ErWeiMaActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mActiveReceiver);
		getActivity().unregisterReceiver(mRZReceiver);
	}

	public void refresh_UI() {
		xiaoquming.setText(user.BuildingName + "(已认证)");
		my_midle2_renzen.setText("已认证");
		mWeiRenzhengLayout.setVisibility(View.GONE);
		mRenzhengLayout.setVisibility(View.VISIBLE);
	}

	class ActiveReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int code = intent.getIntExtra("Code", 0);
			Log.v("推送过来的消息----------",
					"推送过来的消息----------" + String.valueOf(code));
			switch (code) {
			case 101:// 认证通过
				refresh_UI();

				break;

			}
		}
	};

	class RZReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			xiaoquming.setText(user.BuildingName + "(未认证)");
			my_midle2_renzen.setText("正在认证中");
			my_midle2_xiaoqumin.setText(user.BuildingName);
			mWeiRenzhengLayout.setVisibility(View.GONE);
			mRenzhengLayout.setVisibility(View.VISIBLE);

		}
	};
}
