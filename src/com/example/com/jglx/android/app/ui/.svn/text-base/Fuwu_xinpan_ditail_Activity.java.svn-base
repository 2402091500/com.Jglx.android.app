package com.example.com.jglx.android.app.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.adapter.HorizontalListViewAdapter;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.ActionInfo;
import com.example.com.jglx.android.app.info.QuanJin_Info;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.info.Xinpan_ditail;
import com.example.com.jglx.android.app.util.DialogUtil;
import com.example.com.jglx.android.app.util.ImageDownLoader;
import com.example.com.jglx.android.app.view.HorizontalListView;
import com.example.com.jglx.android.app.view.RetangleImageView;
import com.example.com.jglx.android.constants.URLs;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView.ScaleType;

public class Fuwu_xinpan_ditail_Activity extends BaseActivity {

	private FrameLayout videoview;
	private WebView videowebview;
	private xWebChromeClient xwebchromeclient;
	private Boolean islandport = true;// true表示此时是竖屏，false表示此时横屏。
	private View xCustomView;
	private WebChromeClient.CustomViewCallback xCustomViewCallback;
	public String PropertyID;

	public Xinpan_ditail info = new Xinpan_ditail();
	public QuanJin_Info qj_info = new QuanJin_Info();

	public static List<QuanJin_Info> mList = new ArrayList<QuanJin_Info>();
	public List<String> sului_list = new ArrayList<String>();
	public List<String> suolui_list1 = new ArrayList<String>();
	private String url;
	private HorizontalListView jdhx;
	private HorizontalListViewAdapter hListViewAdapter;

	public ImageDownLoader loader = new ImageDownLoader(this);

	private HorizontalListView jgmy;
	private HorizontalListViewAdapter hListViewAdapter1;
	private TextView ditail;
	private RetangleImageView quwei;
	private RetangleImageView jianzu;
	private LinearLayout wuyea;

	private ImageLoader imageLoader;
	public DisplayImageOptions options;
	private RetangleImageView hongbao;

	private String hongbao_money;
	private TextView huodongxianqing;
	public static Boolean can_hong_bao = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_fuwu_xinpan_ditail);
		setTitleTextRightText("", "", "", true);

		PropertyID = getIntent().getStringExtra("PropertyID");
		imageLoader = ImageLoader.getInstance();

		initview();
		get360Data();
		getData();

	}

	public void set_title(String name) {

		setTitleTextRightText("", name, "", true);
	}

	private void get360Data() {
		RequstClient.Get_loupan_allImage(PropertyID, new CustomResponseHandler(
				this, false) {

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
					Log.v("全景图列表---", content);
					mList = new Gson().fromJson(new JSONObject(content)
							.getJSONArray("Data").toString(),
							new TypeToken<List<QuanJin_Info>>() {
							}.getType());
					LXApplication.List_360 = mList;
					if (mList.size() != 0) {

						for (int i = 0; i < mList.size(); i++) {
							sului_list.add(mList.get(i).IconPath);
						}
					}
					LXApplication.sului_list = sului_list;
					// mRfListView.stopRefresh("0");
					// mRfListView.setPullLoadEnable(false);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

	}

	public void getHongbao() {
		if (can_hong_bao) {
			can_hong_bao = false;

			RequstClient.Get_loupan_money(PropertyID,
					new CustomResponseHandler(this, false) {

						@Override
						public void onSuccess(int statusCode, String content) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, content);
							System.out.println("获得到的红包：" + content);

							JSONObject obj;
							try {
								obj = new JSONObject(content);
								if (obj.getString("State").equals("1")) {
									hongbao_money = "0";
								} else {
									hongbao_money = info.getRedMoney();
								}
								Intent intent = new Intent(
										Fuwu_xinpan_ditail_Activity.this,
										Qian_hongBao_Activity.class);
								intent.putExtra("hongbao_money", hongbao_money);
								startActivity(intent);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		can_hong_bao = true;
		try {
			videowebview.getClass().getMethod("onResume")
					.invoke(videowebview, (Object[]) null);
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			videowebview.getClass().getMethod("onPause")
					.invoke(videowebview, (Object[]) null);
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

	private void initview() {

		jdhx = (HorizontalListView) findViewById(R.id.jdhx);
		jgmy = (HorizontalListView) findViewById(R.id.jgmy);
		ditail = (TextView) findViewById(R.id.ditail);

		quwei = (RetangleImageView) findViewById(R.id.quwei);
		jianzu = (RetangleImageView) findViewById(R.id.jianzu);
		hongbao = (RetangleImageView) findViewById(R.id.hongbao);
		// 活动详情
		huodongxianqing = (TextView) findViewById(R.id.huodongxianqing);
		hongbao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getHongbao();

			}

		});

		wuyea = (LinearLayout) findViewById(R.id.wuyea);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.showImageOnFail(R.drawable.default_head).delayBeforeLoading(0)
				.cacheOnDisc(false).resetViewBeforeLoading(true)
				.displayer(new FadeInBitmapDisplayer(100))
				.showStubImage(R.drawable.default_head)
				// .showImageOnLoading(R.drawable.loading) //设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				// .imageScaleType(ImageScaleType.EXACTLY)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();

		videoview = (FrameLayout) findViewById(R.id.video_view);
		videowebview = (WebView) findViewById(R.id.v_webview);
		WebSettings ws = videowebview.getSettings();

		ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕

		ws.setUseWideViewPort(false);// 可任意比例缩放
		ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
		ws.setSavePassword(true);
		ws.setSaveFormData(true);// 保存表单数据
		ws.setJavaScriptEnabled(true);
		ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
		ws.setDomStorageEnabled(true);
		/**
		 * modify by lilifeng
		 */
		videowebview.getSettings().setRenderPriority(RenderPriority.HIGH);
		
		videowebview.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				videowebview
						.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
			}
		});
		/**
		 * modify by lilifeng
		 */
		xwebchromeclient = new xWebChromeClient();
		videowebview.setWebChromeClient(xwebchromeclient);
		videowebview.setWebViewClient(new xWebViewClientent());
	}

	public void setIMage() {
		set_title(info.getName());
		System.out.println("视频播放地址：" + URLs.SERVICE_HOST_IMAGE
				+ info.getVideo());
		videowebview.loadUrl(URLs.SERVICE_HOST_IMAGE + info.getVideo());
		String js="javascript: var v=document.getElementsByTagName('video')[0]; "
				+"v.play(); ";
		videowebview.loadUrl(js);
		videowebview.onResume();
		// 经典户型
		hListViewAdapter = new HorizontalListViewAdapter(
				getApplicationContext(), sului_list);
		jdhx.setAdapter(hListViewAdapter);

		if (info.getAcitvityID() != "0") {
			huodongxianqing.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(
							Fuwu_xinpan_ditail_Activity.this,
							ActionDetailActivity.class);
					intent.putExtra("AcitvityID", info.getAcitvityID());
					startActivity(intent);

				}
			});
		} else {
			huodongxianqing.setVisibility(View.GONE);
		}
		jdhx.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Fuwu_xinpan_ditail_Activity.this,
						PanoramaGLActivity.class);
				intent.putExtra("position", arg2);
				startActivity(intent);

			}
		});
		// 景观漫游
		hListViewAdapter1 = new HorizontalListViewAdapter(
				getApplicationContext(), info.getLandscapeImages());
		jgmy.setAdapter(hListViewAdapter1);
		jgmy.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String[] array = (String[]) info.getLandscapeImages().toArray(
						new String[0]);
				Intent intent = new Intent(Fuwu_xinpan_ditail_Activity.this,
						ShowImgActivity.class);
				intent.putExtra("imgs", array);
				intent.putExtra("curImg", arg2);
				startActivity(intent);
			}
		});

		System.out.println("info.getDetail()----------------"
				+ info.getDetail());
		ditail.setText(info.getDetail());
		// //1.区位展示
		quwei.setUrl(info.getLocatImage());

		jianzu.setUrl(info.getPlaneImage());

		if (info.getManageImages().size() != 0) {

			for (int d = 0; d < info.getManageImages().size(); d++) {
				ImageView wuye_image = new ImageView(this);

				wuye_image.setAdjustViewBounds(true);
				wuye_image.setScaleType(ScaleType.CENTER_CROP);
				System.out.println(URLs.SERVICE_HOST_IMAGE
						+ info.getManageImages().get(0));
				imageLoader.displayImage(URLs.SERVICE_HOST_IMAGE
						+ info.getManageImages().get(d), wuye_image);
				wuyea.addView(wuye_image);

			}
		}


	}

	/**
	 * 处理各种通知、请求等事件
	 * 
	 * @author
	 */
	public class xWebViewClientent extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("webviewtest", "shouldOverrideUrlLoading: " + url);
			return false;
		}
	}

	/**
	 * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
	 */
	public class xWebChromeClient extends WebChromeClient {
		private Bitmap xdefaltvideo=BitmapFactory.decodeResource(getResources(),
				R.drawable.x1);
		private View xprogressvideo;

		@Override
		// 播放网络视频时全屏会被调用的方法
		public void onShowCustomView(View view,
				WebChromeClient.CustomViewCallback callback) {
			if (islandport) {
			} else {

			}
			quanpin_and_not(false);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			videowebview.setVisibility(View.GONE);
			if (xCustomView != null) {
				callback.onCustomViewHidden();
				return;
			}
			videoview.addView(view);
			xCustomView = view;
			xCustomViewCallback = callback;
			videoview.setVisibility(View.VISIBLE);
		}

		@Override
		// 视频播放退出全屏会被调用的
		public void onHideCustomView() {
			quanpin_and_not(true);

			if (xCustomView == null)// 不是全屏播放状态
				return;
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			xCustomView.setVisibility(View.GONE);

			videoview.removeView(xCustomView);
			xCustomView = null;
			videoview.setVisibility(View.GONE);
			xCustomViewCallback.onCustomViewHidden();
			videowebview.setVisibility(View.VISIBLE);
		}

		public void quanpin_and_not(Boolean b) {
			if (b) {

				base_title_goneor_not(true);
				hongbao.setVisibility(View.VISIBLE);
			} else {
				hongbao.setVisibility(View.GONE);
				base_title_goneor_not(false);
			}
		}

		// 视频加载添加默认图标
		@Override
		public Bitmap getDefaultVideoPoster() {
			// Log.i(LOGTAG, "here in on getDefaultVideoPoster");
			if (xdefaltvideo == null) {
				xdefaltvideo = BitmapFactory.decodeResource(getResources(),
						R.drawable.x1);
			}
			return xdefaltvideo;
		}

		// 视频加载时进程loading
		@Override
		public View getVideoLoadingProgressView() {
			// Log.i(LOGTAG, "here in on getVideoLoadingPregressView");

			if (xprogressvideo == null) {
				LayoutInflater inflater = LayoutInflater
						.from(Fuwu_xinpan_ditail_Activity.this);
				xprogressvideo = inflater.inflate(R.layout.view_loading_video,
						null);
			}
			return xprogressvideo;
		}

		// 网页标题
		@Override
		public void onReceivedTitle(WebView view, String title) {
			(Fuwu_xinpan_ditail_Activity.this).setTitle(title);
		}

	}

	public void getData() {

		RequstClient.Get_loupan_Ditail(PropertyID, new CustomResponseHandler(
				this, true) {
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				System.out.println("Get_loupan_Ditail----" + content);
				try {
					JSONObject obj = new JSONObject(content);
					if (!obj.getString("State").equals("0")) {
						String errorMsg = obj.getString("Message");
						Toast.makeText(getBaseContext(), errorMsg,
								Toast.LENGTH_SHORT).show();
						return;
					}

					info = new Gson().fromJson(new JSONObject(content)
							.getJSONObject("Data").toString(),
							Xinpan_ditail.class);
					System.out.println("aaaaaaaaaaaaaaa"
							+ info.getLandscapeIcons().get(1));
					System.out.println("aaaaaaaaaaaaaaa" + info.getDetail());
					System.out.println("aaaaaaaaaaaaaaa"
							+ info.getManageImages().get(0));
					setIMage();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
			}
		});

	}

}
