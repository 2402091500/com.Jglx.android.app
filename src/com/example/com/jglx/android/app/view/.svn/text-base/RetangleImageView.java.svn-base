package com.example.com.jglx.android.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.constants.URLs;

/**
 * 矩形异步ImageView
 * 
 * @author jjj
 * 
 * @date 2015-8-4
 */
public class RetangleImageView extends ImageView {
	private String shape = "";
	private Bitmap bitmap;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public RetangleImageView(Context context) {
		super(context);
	}

	public RetangleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RetangleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void setDefaultImageResource(int imgPreholderEvent) {
		setImageResource(imgPreholderEvent);
	}

	/**
	 * 设置图片URL
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		LXApplication.getImageLoader().displayImage(URLs.SERVICE_HOST_IMAGE+url, this,
				LXApplication.getOptions(R.drawable.pictures_no));// 默认图片设置
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Bitmap bitmapNew = null;
		Drawable drawable = getDrawable();
		if (drawable != null) {
			bitmapNew = ((BitmapDrawable) drawable).getBitmap();
		}
		setBitmap(bitmapNew);

	}

}
