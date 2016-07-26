package com.example.com.jglx.android.app.ui;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.R.layout;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.util.ShareUtil;
import com.example.com.jglx.android.constants.URLs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

@SuppressLint("ResourceAsColor")
public class My_tuijian_share_Activity extends BaseActivity {

	private ImageButton xiazai;
	private Dialog mShareDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String imgurl="http://t28-4.yunpan.360.cn/p2/800-600.c42592d1ace187de754266f2b27143a9e4864b2f_tjhc_28_tjhc2_t.de5ee1.jpg?st=h9hpw2W_bCzxI6543aMoGg&e=1443162293";
		String url="http://120.25.160.25/";
		setActiviyContextView(R.layout.activity_my_tuijian_share_);
		setTitleTextRightText("", "邻信", "分享", true);
		setRightTvColor(R.color.green);
		
		
		mShareDialog = new ShareUtil(this, "请下载邻信，谢谢！！@……@", "注意了一定要下载哦，不下载我打你哦！！", imgurl, url).getShareDialog();
		xiazai=(ImageButton)findViewById(R.id.xiazai);
		xiazai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse(URLs.SERVICE_HOST);    
				Intent it = new Intent(Intent.ACTION_VIEW, uri);    
				startActivity(it);  
				
			}
		});
		
	}
	@Override
	public void onRightClick() {
		super.onRightClick();
		if (mShareDialog != null && !mShareDialog.isShowing()) {
			mShareDialog.show();
		}
	}

}
