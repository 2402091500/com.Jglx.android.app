package com.example.com.jglx.android.app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.helper.HXSDKHelper;

public class BaseActivity extends Activity implements OnClickListener {
	private TextView mLeftTv;
	private TextView milddleTv;
	private ImageView rightIv;
	private TextView rightTv;
	protected InputMethodManager manager;
	private RelativeLayout base_titleLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		LXApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_base);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		base_titleLayout = (RelativeLayout) findViewById(R.id.base_titleLayout);
		mLeftTv = (TextView) findViewById(R.id.baseTitle_leftTv);
		milddleTv = (TextView) findViewById(R.id.baseTitle_milddleTv);
		rightIv = (ImageView) findViewById(R.id.baseTitle_rightIv);
		rightTv = (TextView) findViewById(R.id.baseTitle_rightTv);
	}

	public void base_title_goneor_not(Boolean b) {
		if (b) {
			base_titleLayout.setVisibility(View.VISIBLE);
		} else {
			base_titleLayout.setVisibility(View.GONE);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// onresume时，取消notification显示
		HXSDKHelper.getInstance().getNotifier().reset();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	/**
	 * 初始化布局实现
	 * 
	 * @param paramInt
	 */
	protected void setActiviyContextView(int paramInt) {
		FrameLayout localFrameLayout = (FrameLayout) findViewById(R.id.base_contentLayout);
		localFrameLayout.removeAllViews();
		LayoutInflater.from(this).inflate(paramInt, localFrameLayout);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 设置标题栏 右边为纯图片
	 * 
	 * @param left
	 * @param middle
	 * @param right
	 */
	public void setTitleText(String left, String middle, int right,
			boolean isShowLeft) {
		rightTv.setVisibility(View.GONE);
		if (isShowLeft) {
			mLeftTv.setVisibility(View.VISIBLE);
			mLeftTv.setOnClickListener(this);
		} else {
			mLeftTv.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(left)) {
			mLeftTv.setText(left);
		}

		if (!TextUtils.isEmpty(middle)) {
			milddleTv.setText(middle);
		}

		if (right != 0) {
			rightIv.setImageResource(right);
			rightIv.setVisibility(View.VISIBLE);
			rightIv.setOnClickListener(this);
		} else {
			rightIv.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置标题栏 右边带有文字的一种
	 * 
	 * @param left
	 * @param middle
	 * @param right
	 */
	public void setTitleTextRightText(String left, String middle, String right,
			boolean isShowLeft) {
		rightIv.setVisibility(View.GONE);
		if (isShowLeft) {
			mLeftTv.setVisibility(View.VISIBLE);
			mLeftTv.setOnClickListener(this);
		} else {
			mLeftTv.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(left)) {
			mLeftTv.setText(left);
		}

		if (!TextUtils.isEmpty(middle)) {
			milddleTv.setText(middle);
		}

		if (!TextUtils.isEmpty(right)) {
			rightTv.setVisibility(View.VISIBLE);
			rightTv.setText(right);
			rightTv.setOnClickListener(this);
		} else {
			rightTv.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置标题栏右键的颜色,默认为黑色
	 * 
	 * @param color
	 */
	public void setRightTvColor(int color) {
		rightTv.setTextColor(getResources().getColor(color));
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.baseTitle_leftTv:
			this.finish();
			break;
		case R.id.baseTitle_rightIv:
			onRightClick();
			break;
		case R.id.baseTitle_rightTv:
			onRightClick();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LXApplication.getInstance().removeActivity(this);
	}

	/**
	 * 标题栏右键点击事件
	 */
	public void onRightClick() {

	}

}
