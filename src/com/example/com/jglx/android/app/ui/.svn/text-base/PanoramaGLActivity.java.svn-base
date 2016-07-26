package com.example.com.jglx.android.app.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.adapter.PanoramaGLAdapter;
import com.example.com.jglx.android.app.info.QuanJin_Info;
import com.example.com.jglx.android.app.util.DialogUtil;
import com.example.com.jglx.android.app.util.ImageDownLoader;
import com.example.com.jglx.android.constants.URLs;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.panoramagl.PLCubicPanorama;
import com.panoramagl.PLIPanorama;
import com.panoramagl.PLImage;
import com.panoramagl.PLView;
import com.panoramagl.enumerations.PLCubeFaceOrientation;
import com.panoramagl.transitions.PLTransitionBlend;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PanoramaGLActivity extends PLView {

	private TextView back;
	private ListView lv_gallery;
	private PanoramaGLAdapter adapter;
	private DisplayImageOptions options;
	private int position;
	public QuanJin_Info info=new QuanJin_Info();
	
	 private Dialog dialog;
	
	public ImageDownLoader loader = new ImageDownLoader(this);
	private ImageLoader imageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	@Override
	protected View onContentViewCreated(View contentView) {
		
		imageLoader=ImageLoader.getInstance();
		 position=getIntent().getIntExtra("position", 1);
		 info=LXApplication.List_360.get(0);
		    
		init_image_loader();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		ViewGroup mainView = (ViewGroup) this.getLayoutInflater().inflate(
				R.layout.activity_panorama_gl, null);
		
		// 添加360视图
				mainView.addView(contentView, 0);

				back = (TextView) mainView.findViewById(R.id.back);
				back.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						PanoramaGLActivity.this.finish();
					}
				});
				
				lv_gallery = (ListView) mainView.findViewById(R.id.lv_gallery);
				lv_gallery.setVisibility(View.VISIBLE);
				adapter = new PanoramaGLAdapter(this);
				lv_gallery.setAdapter(adapter);
				lv_gallery.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						 info=LXApplication.List_360.get(arg2);
						 load_quanjingtu();
						
					}
				});
		
		
		     load_quanjingtu();
		
		
		
		return super.onContentViewCreated(mainView);
	}
	public void load_quanjingtu() {
		dialog= DialogUtil.getCenterProgressDialog(PanoramaGLActivity.this, R.string.dialog_loading, true);
		dialog.show();
		new Thread(){
			

			public void run() {
				 loadPanorama(0, info.ImageZP, info.ImageZN, info.ImageXP, info.ImageXN, info.ImageYP, info.ImageYN);
			 };
		 }.start();
	}
	private void loadPanorama(int index, String imageZP, String imageZN,
			String imageXP, String imageXN, String imageYP, String imageYN) {try {
				Context context = this.getApplicationContext();
				PLIPanorama panorama = null;
				// 锁定全景视图
				this.setLocked(true);
				// 全景全景视图
				switch (index) {
				// 立方体全景 (supports up 1024x1024 image per face)
				case 0:
					PLCubicPanorama cubicPanorama = new PLCubicPanorama();
                    System.out.println("立体全景看房"+URLs.SERVICE_HOST_IMAGE+imageZP);
                    cubicPanorama.setImage
                    (new  PLImage(imageLoader.loadImageSync(URLs.SERVICE_HOST_IMAGE+imageZN,options)), PLCubeFaceOrientation.PLCubeFaceOrientationFront);
                    cubicPanorama.setImage
                    (new  PLImage(imageLoader.loadImageSync(URLs.SERVICE_HOST_IMAGE+imageZP,options)), PLCubeFaceOrientation.PLCubeFaceOrientationBack);
					
                    cubicPanorama.setImage
                    (new  PLImage(imageLoader.loadImageSync(URLs.SERVICE_HOST_IMAGE+imageXN,options)), PLCubeFaceOrientation.PLCubeFaceOrientationLeft);
                    cubicPanorama.setImage
                    (new  PLImage(imageLoader.loadImageSync(URLs.SERVICE_HOST_IMAGE+imageXP,options)), PLCubeFaceOrientation.PLCubeFaceOrientationRight);
                    
                    cubicPanorama.setImage
                    (new  PLImage(imageLoader.loadImageSync(URLs.SERVICE_HOST_IMAGE+imageYP,options)), PLCubeFaceOrientation.PLCubeFaceOrientationUp);
                    cubicPanorama.setImage
                    (new  PLImage(imageLoader.loadImageSync(URLs.SERVICE_HOST_IMAGE+imageYN,options)), PLCubeFaceOrientation.PLCubeFaceOrientationDown);
					
					panorama = cubicPanorama;
					 dialog.dismiss();
						lv_gallery.setEnabled(true);
					break;
				default:
					break;
				}
				if (panorama != null) {
					// 设置摄像机的旋转角度
					panorama.getCamera().lookAt(0.0f, 170.0f);
					/*
					 * // 添加一个热点 panorama.addHotspot(new PLHotspot(1, new
					 * PLImage(PLUtils .getBitmap(context, R.raw.hotspot), false),
					 * 0.0f, 170.0f, 0.05f, 0.05f));
					 */
					// 重置视图
					this.reset();
					// 加载全景图
					this.startTransition(new PLTransitionBlend(2.0f), panorama); // 或者
																					// this.setPanorama(panorama);
				}
				// 开启全景视图
				this.setLocked(false);
				
			} catch (Throwable e) {
				Toast.makeText(getApplicationContext(), "请清理下手机内存再来试试！",
						Toast.LENGTH_SHORT).show();
			}}
	public void init_image_loader() {
		options = new DisplayImageOptions.Builder()
         .cacheInMemory(true)
         .showImageOnFail(R.drawable.default_head)
         .delayBeforeLoading(0)
          .cacheOnDisc(true)
           .resetViewBeforeLoading(true)
          .displayer(new FadeInBitmapDisplayer(100))
          .showStubImage(R.drawable.default_head)
//            .showImageOnLoading(R.drawable.loading) //设置图片在下载期间显示的图片  
       .showImageForEmptyUri(R.drawable.ic_launcher)
         .bitmapConfig(Bitmap.Config.RGB_565)
//         .imageScaleType(ImageScaleType.EXACTLY)
         .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
         .build();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imageLoader.clearMemoryCache();  
        imageLoader.clearDiscCache();
	}
    
	
}
