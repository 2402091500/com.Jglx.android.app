package com.example.com.jglx.android.app.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseFragmentActivity;
import com.example.com.jglx.android.app.erweima.MipcaActivityCapture;
import com.example.com.jglx.android.app.interfaces.RedCircleListener;
import com.example.com.jglx.android.app.ui.fragment.FragmentHome;
import com.example.com.jglx.android.app.ui.fragment.FragmentMessage;
import com.example.com.jglx.android.app.ui.fragment.FragmentMy;
import com.example.com.jglx.android.app.ui.fragment.FragmentServer;

/**
 * 首页-主页
 * 
 * @author jjj
 * 
 * @date 2015-7-31
 */
public class MainActivity extends BaseFragmentActivity implements
		OnClickListener, RedCircleListener {
	private TextView titleTv;
	private TextView leftTv;
	private ImageView rightIv;
	private ImageView homeIv;
	private TextView homeTv;
	private ImageView msgIv;
	private TextView msgTv;
	private ImageView serverIv;
	private TextView serverTv;
	private ImageView myIv;
	private TextView myTv;
	private ImageView mMsgRedCircleIv;

	private FragmentTransaction fragmentTransaction;
	public FragmentHome fragmenthome;

	private long exitTime;
	private boolean isFirst = true;

	public static String HOME = "HOME";
	public static String MSG = "MSG";
	public static String SERVER = "SERVER";
	public static String MY = "MY";

	private String tag = "";

	private PopupWindow pWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		initView();

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land do nothing is ok
		} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port do nothing is ok
		}
	}

	private void initView() {
		findViewById(R.id.main_homeLayout).setOnClickListener(this);
		findViewById(R.id.main_msgLayout).setOnClickListener(this);
		findViewById(R.id.main_serverLayout).setOnClickListener(this);
		findViewById(R.id.main_myLayout).setOnClickListener(this);
		mMsgRedCircleIv = (ImageView) findViewById(R.id.main_msg_redCricleIv);
		titleTv = (TextView) findViewById(R.id.baseTitle_milddleTv);
		homeIv = (ImageView) findViewById(R.id.main_homeIv);
		homeTv = (TextView) findViewById(R.id.main_homeTv);
		msgIv = (ImageView) findViewById(R.id.main_msgIv);
		msgTv = (TextView) findViewById(R.id.main_msgTv);
		serverIv = (ImageView) findViewById(R.id.main_serverIv);
		serverTv = (TextView) findViewById(R.id.main_serverTv);
		myIv = (ImageView) findViewById(R.id.main_myIv);
		myTv = (TextView) findViewById(R.id.main_myTv);

		leftTv = (TextView) findViewById(R.id.baseTitle_leftTv);
		rightIv = (ImageView) findViewById(R.id.baseTitle_rightIv);
		rightIv.setOnClickListener(rightListener);
		initFragment();
	}

	private void initFragment() {
		if (isFirst) {
			fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			currentFrag = new FragmentHome();
			fragmentTransaction.add(R.id.main_fLayout, currentFrag, HOME)
					.commit();
			isFirst = false;
			tag = HOME;
			changeViewBackground(HOME);
		}
	}

	Fragment currentFrag = null;

	/**
	 * fragment跳转
	 * 
	 * @param Tag
	 */
	public void switchToFragment(String Tag) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		Fragment findresult = null;
		findresult = getSupportFragmentManager().findFragmentByTag(Tag);
		if (currentFrag != null && currentFrag.getTag().equals(Tag)) {
			// 判断为相同fragment不切换
		} else {
			if (findresult != null) {
				fragmentTransaction.hide(currentFrag).show(findresult).commit();
			} else {
				if (Tag.equals(HOME)) {
					findresult = new FragmentHome();
				} else if (Tag.equals(MSG)) {
					findresult = new FragmentMessage();
				} else if (Tag.equals(MY)) {
					findresult = new FragmentMy();
				} else if (Tag.equals(SERVER)) {
					findresult = new FragmentServer();
				}
				fragmentTransaction.hide(currentFrag)
						.add(R.id.main_fLayout, findresult, Tag).commit();
			}
		}
		currentFrag = findresult;

	}

	public void changeViewBackground(String Tag) {
		if (Tag.equals(HOME)) {
			homeIv.setImageResource(R.drawable.main_home_d);
			homeTv.setTextColor(getResources().getColor(R.color.bottomText_d));
			msgIv.setImageResource(R.drawable.main_msg_n);
			msgTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			serverIv.setImageResource(R.drawable.main_server_n);
			serverTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			myIv.setImageResource(R.drawable.main_my_n);
			myTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			leftTv.setVisibility(View.GONE);
			rightIv.setVisibility(View.VISIBLE);
			rightIv.setImageResource(R.drawable.my_tv_bianji_imag);
			titleTv.setText(R.string.app_name);
		} else if (Tag.equals(MSG)) {
			msgIv.setImageResource(R.drawable.main_msg_d);
			msgTv.setTextColor(getResources().getColor(R.color.bottomText_d));
			serverIv.setImageResource(R.drawable.main_server_n);
			serverTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			myIv.setImageResource(R.drawable.main_my_n);
			myTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			homeIv.setImageResource(R.drawable.main_home_n);
			homeTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			leftTv.setVisibility(View.GONE);
			rightIv.setVisibility(View.VISIBLE);
			rightIv.setImageResource(R.drawable.my_add);
			titleTv.setText(R.string.xiaoxi);
		} else if (Tag.equals(SERVER)) {
			serverIv.setImageResource(R.drawable.main_server_d);
			serverTv.setTextColor(getResources().getColor(R.color.bottomText_d));
			msgIv.setImageResource(R.drawable.main_msg_n);
			msgTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			myIv.setImageResource(R.drawable.main_my_n);
			myTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			homeIv.setImageResource(R.drawable.main_home_n);
			homeTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			leftTv.setVisibility(View.GONE);
			rightIv.setVisibility(View.VISIBLE);
			rightIv.setImageResource(R.drawable.my_add);

			titleTv.setText(R.string.fuwu);
		} else if (Tag.equals(MY)) {
			myIv.setImageResource(R.drawable.main_my_d);
			myTv.setTextColor(getResources().getColor(R.color.bottomText_d));
			msgIv.setImageResource(R.drawable.main_msg_n);
			msgTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			serverIv.setImageResource(R.drawable.main_server_n);
			serverTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			homeIv.setImageResource(R.drawable.main_home_n);
			homeTv.setTextColor(getResources().getColor(R.color.bottomText_n));
			leftTv.setVisibility(View.GONE);
			rightIv.setVisibility(View.GONE);
			titleTv.setText(R.string.wode);
		}

	}

	OnClickListener rightListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (tag.equals(HOME)) {
				Intent intent = new Intent(MainActivity.this,
						InvatePublishActivity.class);
				startActivity(intent);
			} else if (tag.equals(MSG)) {
				showMsgWindow();
			} else if (tag.equals(SERVER)) {
				Intent intent = new Intent(MainActivity.this,
						Fuwu_More_Activity.class);
				startActivity(intent);
			}

		}
	};

	/**
	 * 消息界面的窗体弹框
	 */
	private void showMsgWindow() {
		if (pWindow == null) {

			View view = LayoutInflater.from(this).inflate(
					R.layout.popwindow_msg, null);
			view.findViewById(R.id.popW_msg_goodFTv).setOnClickListener(
					msgListener);
			view.findViewById(R.id.popW_msg_linFTv).setOnClickListener(
					msgListener);
			view.findViewById(R.id.popW_msg_saoTv).setOnClickListener(
					msgListener);
			view.findViewById(R.id.popW_msg_bgView).setOnClickListener(
					msgListener);

			pWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			pWindow.setOutsideTouchable(true);
			pWindow.showAsDropDown(rightIv);
		} else {
			if (!pWindow.isShowing()) {
				pWindow.showAsDropDown(rightIv);
			}
		}
	}

	OnClickListener msgListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;

			switch (v.getId()) {
			case R.id.popW_msg_goodFTv:// 好友
				intent = new Intent(MainActivity.this, MyFriendActivity.class);
				intent.putExtra("FriendType", 0);
				startActivity(intent);
				break;
			case R.id.popW_msg_linFTv:// 邻友
				intent = new Intent(MainActivity.this, MyFriendActivity.class);
				intent.putExtra("FriendType", 1);
				startActivity(intent);
				break;
			case R.id.popW_msg_saoTv:
				intent = new Intent(MainActivity.this,
						MipcaActivityCapture.class);
				startActivity(intent);
				break;
			case R.id.popW_msg_bgView:
				break;
			}
			disPWindow();
		}
	};

	/**
	 * 关闭消息弹窗
	 */
	private void disPWindow() {
		if (pWindow != null && pWindow.isShowing()) {
			pWindow.dismiss();
		}
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.main_homeLayout:
			tag = HOME;
			disPWindow();
			break;
		case R.id.main_msgLayout:
			tag = MSG;
			break;
		case R.id.main_serverLayout:
			tag = SERVER;
			disPWindow();
			break;
		case R.id.main_myLayout:
			tag = MY;
			disPWindow();
			break;
		}
		switchToFragment(tag);
		changeViewBackground(tag);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - exitTime < 2000) {
				LXApplication.getInstance().exit();
			} else {
				exitTime = System.currentTimeMillis();
				Toast.makeText(this, "再按一次退出", 20).show();
			}
		}
		return true;
	}

	@Override
	public void showRed(boolean isShow) {

		if (isShow) {
			mMsgRedCircleIv.setVisibility(View.VISIBLE);
		} else {
			mMsgRedCircleIv.setVisibility(View.INVISIBLE);
		}

	}

}
