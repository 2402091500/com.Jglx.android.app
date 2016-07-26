package com.example.com.jglx.android.app.view;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;

import org.json.JSONObject;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.http.AsyncHttpResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.interfaces.WebViewListener;
import com.example.com.jglx.android.app.ui.ActionDetailActivity;
import com.example.com.jglx.android.constants.URLs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 邀约广告
 * 
 * @author jjj
 * 
 * @date 2015-8-30
 */
public class InvateActionLayout extends LinearLayout implements
		OnClickListener, WebViewListener {
	private RetangleImageView mView;
	private TextView mNameTv;
	private WebView mWebView;
	private String AcitvityID;

	private boolean isWebView = false;
	private RZReceiver mRZReceiver;

	public InvateActionLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public InvateActionLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public InvateActionLayout(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_invate_action, null);
		mView = (RetangleImageView) view
				.findViewById(R.id.item_invateAction_Iv);
		mNameTv = (TextView) view.findViewById(R.id.item_invateAction_titleTv);
		mWebView = (WebView) view.findViewById(R.id.item_invateAction_webView);
		addView(view);
		getInvateAction(context, LXApplication.mUserInfo.BuildingID);
		setOnClickListener(this);

		mRZReceiver = new RZReceiver();
		context.registerReceiver(mRZReceiver, new IntentFilter("renzheng"));

	}

	/**
	 * 获取邀约广告信息
	 */
	public void getInvateAction(Context context, String builingId) {
		RequstClient.query_invateAction(builingId,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						Toast.makeText(getContext(), "广告获取失败",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("邀约广告---", content);

						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								return;
							}
							JSONObject object = (JSONObject) obj.get("Data");
							if (object != null) {
								String name = object.getString("Name");
								String imgPath = object.getString("ImagePath");
								String videoPath = object
										.getString("VideoPath");
								int link = object.getInt("Link");
								if (link != 0) {
									AcitvityID = String.valueOf(link);
								}
								if (!TextUtils.isEmpty(imgPath)) {
									isWebView = false;
									mView.setUrl(imgPath);
									mView.setVisibility(View.VISIBLE);
									mWebView.setVisibility(View.GONE);

								} else if (!TextUtils.isEmpty(videoPath)) {
									isWebView = true;
									mView.setVisibility(View.GONE);
									mWebView.setVisibility(View.VISIBLE);
									mWebView.loadUrl(URLs.SERVICE_HOST_IMAGE
											+ videoPath);
								}
								if (!TextUtils.isEmpty(name)) {
									mNameTv.setText(name);
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onClick(View arg0) {
		if (!TextUtils.isEmpty(AcitvityID)) {
			Intent intent = new Intent(getContext(), ActionDetailActivity.class);
			intent.putExtra("AcitvityID", AcitvityID);
			getContext().startActivity(intent);
		}
	}

	@Override
	public void doResume() {

		if (isWebView) {
			try {
				mWebView.getClass().getMethod("onResume")
						.invoke(mWebView, (Object[]) null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void doPause() {
		if (isWebView) {
			try {
				mWebView.getClass().getMethod("onPause")
						.invoke(mWebView, (Object[]) null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class RZReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			getInvateAction(context, LXApplication.mUserInfo.BuildingID);

		}
	}

	@Override
	public void doDestory(Context context) {
		context.unregisterReceiver(mRZReceiver);
	};

}
