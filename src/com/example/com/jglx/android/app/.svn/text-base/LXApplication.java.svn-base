package com.example.com.jglx.android.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.easemob.chat.EMChat;
import com.example.com.jglx.android.app.helper.LXHXSDKHelper;
import com.example.com.jglx.android.app.info.ActionInfo;
import com.example.com.jglx.android.app.info.InvateInfo_2;
import com.example.com.jglx.android.app.info.QuanJin_Info;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.info.XinpanInfo_2;
import com.example.com.jglx.android.app.util.CrashHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class LXApplication extends Application {
	private HashMap<String, Activity> mActivityMap = new HashMap<String, Activity>();
	private static LXApplication mInstance;
	public static int AppLaunchTimes = 0;
	/**
	 * 是否打印日志信息 modify by lilifeng
	 */
	public static final boolean isDebugEnable = true;
	public static boolean isInitData = true;
	// 图片加载相关
	private static ImageLoader imageLoader;
	private static DisplayImageOptions imageOptions;
	public static boolean isLogin;

	public static UserInfo_2 mUserInfo;
	public static String LocalTouxian;

	public static LXHXSDKHelper hxSDKHelper = new LXHXSDKHelper();

	public static LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	// 百度定位返回信息 经伟度
	public static String Baidu_location;
	// 活动列表信息
	public static List<ActionInfo> mList = new ArrayList<ActionInfo>();
	// 新盘推荐 List<XinpanInfo_2> mList
	public static List<XinpanInfo_2> m_loupan_List;

	// 邀约列表信息
	public static List<InvateInfo_2> m_yaoyue_List;
	// 当前城市
	public static String addr;
	// 当前省份
	public static String shen;
	//
	public static int p;
	private String[] titles = { "水费", "电费", "燃气费", "话费充值", "交通罚款", "新盘推荐" };
	private int[] images = { R.drawable.fuwu_suifei, R.drawable.fuwu_dianfei,
			R.drawable.fuwu_ranqifei, R.drawable.fuwu_huafei,
			R.drawable.fuwu_jiaotong, R.drawable.fuwu_xingpan };
	// 更多里面的
	public static ArrayList<String> list_title_ = new ArrayList<String>();
	public static ArrayList<Integer> list_images_ = new ArrayList<Integer>();
	// 服务与更多的一个标识（用的同一个适配器）
	public static Boolean ismore = true;
	// 服务主页的
	public static ArrayList<String> list_title = new ArrayList<String>();
	public static ArrayList<Integer> list_images = new ArrayList<Integer>();
	// 发布图片集合
	public static List<String> mSelectedImage = new LinkedList<String>();
	// 看房图list

	public static List<String> sului_list = new ArrayList<String>();
	// 全景看房
	public static List<QuanJin_Info> List_360 = new ArrayList<QuanJin_Info>();
	public static int wjCount = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initImageLoader();

		// 设置是否抓取异常日志
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

		EMChat.getInstance().setAutoLogin(false);
		hxSDKHelper.onInit(mInstance);

		for (int i = 0; i < titles.length; i++) {
			list_title.add(titles[i]);
			list_images.add(images[i]);

		}
		// 推送初始化
		JPushInterface.init(getApplicationContext());

		mLocationClient = new LocationClient(this.getApplicationContext());
		initLocation();
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();

	}

	/**
	 * 百度定位实现实时位置回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		private String shen;

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			if (location != null && !TextUtils.isEmpty(location.getCity())) {
				String sb = "";
				sb = sb + location.getTime() + "|" + location.getLocType()
						+ "|" + location.getLatitude() + "|"
						+ location.getLongitude();

				addr = location.getCity().substring(0,
						location.getCity().length() - 1);
				addr = (addr == null ? "重庆" : addr);
				shen = location.getProvince().substring(0,
						location.getProvince().length() - 1);
				shen = (shen == null ? "重庆" : shen);
				Baidu_location = sb;
				mLocationClient.stop();
			} else {
				addr = "重庆";
				shen = "重庆";
			}

		}

	}

	/*
	 * 这里用一句话描述这个方法的作用)
	 * 
	 * @param @param mContext
	 * 
	 * @param @param LocalTouxian 设定文件
	 * 
	 * @return void 返回类型
	 * 
	 * @throws
	 */
	public static void saveLocaltouxian(Context mContext, String LocalTouxian,
			String userid) {
		SharedPreferences mPreferences = mContext.getSharedPreferences(userid,
				Context.MODE_PRIVATE);
		Editor mEditor = mPreferences.edit();
		mEditor.putString("LocalTouxian", LocalTouxian);
		mEditor.commit();
		LXApplication.LocalTouxian = LXApplication.getLocaltouxian(mContext,
				LXApplication.mUserInfo.LoginPhone);
	}

	/*
	 * 这里用一句话描述这个方法的作用)
	 * 
	 * @param @param mContext
	 * 
	 * @param @return 设定文件
	 * 
	 * @return String 返回类型
	 * 
	 * @throws
	 */
	public static String getLocaltouxian(Context mContext, String userid) {
		SharedPreferences mPreferences = mContext.getSharedPreferences(userid,
				Context.MODE_PRIVATE);
		return mPreferences.getString("LocalTouxian", "");
	}

	/*************************************************************************
	 * @Title: saveUserInfo
	 * @Description: TODO(保存用户信息)
	 * @param @param mContext
	 * @param @param mUserInfo 设定文件
	 * @return void 返回类型
	 * @throws
	 ************************************************************************** 
	 */
	public static void saveUserInfo(Context mContext, UserInfo_2 mUserInfo) {
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				"UserInfo_2", Context.MODE_PRIVATE);
		Editor mEditor = mPreferences.edit();
		if (mUserInfo != null) {
			mEditor.putString("UserID", mUserInfo.UserID);
			mEditor.putString("LoginPhone", mUserInfo.LoginPhone);
			mEditor.putString("LoginPw", mUserInfo.LoginPw);
			mEditor.putString("ID", mUserInfo.ChatID);
			mEditor.putString("NickName", mUserInfo.NickName);
			// user.Birthday.substring(0, 10)
			mEditor.putString("Birthday", mUserInfo.Birthday.substring(0, 10));

			mEditor.putString("Signatures", mUserInfo.Signatures);
			mEditor.putString("Balance", mUserInfo.Balance);

			mEditor.putString("BuildingID", mUserInfo.BuildingID);
			mEditor.putString("BuildingChatID", mUserInfo.BuildingChatID);

			mEditor.putString("CityName", mUserInfo.CityName);
			mEditor.putString("BuildingName", mUserInfo.BuildingName);
			mEditor.putString("AuditingImage", mUserInfo.AuditingImage);
			mEditor.putString("Logo", mUserInfo.Logo);

			mEditor.putInt("Age", mUserInfo.Age);
			mEditor.putInt("Integral", mUserInfo.Integral);
			mEditor.putInt("Sex", mUserInfo.Sex);

			mEditor.putInt("AuditingState", mUserInfo.AuditingState);

			mEditor.commit();
			LXApplication.mUserInfo = LXApplication.getLocalUserInfo(mContext);
			saveUserInfo_byPhone(mContext, mUserInfo, mUserInfo.LoginPhone);
		} else {
			mEditor.putString("LoginPhone", "");
			mEditor.putString("LoginPw", "");
			mEditor.commit();
		}

	}

	public static void saveUserInfo_byPhone(Context mContext,
			UserInfo_2 mUserInfo, String phone) {
		SharedPreferences mPreferences = mContext.getSharedPreferences(phone
				+ "2", Context.MODE_PRIVATE);
		Editor mEditor = mPreferences.edit();
		mEditor.putString("UserID", mUserInfo.UserID);
		mEditor.putString("LoginPhone", mUserInfo.LoginPhone);
		mEditor.putString("LoginPw", mUserInfo.LoginPw);
		mEditor.putString("ID", mUserInfo.ChatID);
		mEditor.putString("NickName", mUserInfo.NickName);
		// user.Birthday.substring(0, 10)
		mEditor.putString("Birthday", mUserInfo.Birthday.substring(0, 10));

		mEditor.putString("Signatures", mUserInfo.Signatures);
		mEditor.putString("Balance", mUserInfo.Balance);

		mEditor.putString("BuildingID", mUserInfo.BuildingID);
		mEditor.putString("BuildingChatID", mUserInfo.BuildingID);

		mEditor.putString("CityName", mUserInfo.CityName);
		mEditor.putString("BuildingName", mUserInfo.BuildingName);
		mEditor.putString("AuditingImage", mUserInfo.AuditingImage);
		mEditor.putString("Logo", mUserInfo.Logo);

		mEditor.putInt("Age", mUserInfo.Age);
		mEditor.putInt("Integral", mUserInfo.Integral);
		mEditor.putInt("Sex", mUserInfo.Sex);

		mEditor.putInt("AuditingState", mUserInfo.AuditingState);

		mEditor.commit();
	}

	/*************************************************************************
	 * @Title: getLocalUserInfo
	 * @Description: TODO(获取本地用户信息)
	 * @param @param mContext
	 * @param @return 设定文件
	 * @return UserInfo_2 返回类型
	 * @throws
	 ************************************************************************** 
	 */
	public static UserInfo_2 getLocalUserInfo(Context mContext) {
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				"UserInfo_2", Context.MODE_PRIVATE);

		UserInfo_2 userInfo = new UserInfo_2();
		userInfo.UserID = mPreferences.getString("UserID", "");
		userInfo.LoginPhone = mPreferences.getString("LoginPhone", "");
		userInfo.LoginPw = mPreferences.getString("LoginPw", "");
		userInfo.ChatID = mPreferences.getString("ID", "");
		userInfo.NickName = mPreferences.getString("NickName", "");
		userInfo.Sex = mPreferences.getInt("Sex", 1);
		userInfo.Birthday = mPreferences.getString("Birthday", "");
		userInfo.Age = mPreferences.getInt("Age", 0);
		userInfo.Signatures = mPreferences.getString("Signatures", "2");
		userInfo.Balance = mPreferences.getString("Balance", "");
		userInfo.Logo = mPreferences.getString("Logo", "");

		userInfo.Integral = mPreferences.getInt("Integral", 0);
		userInfo.BuildingID = mPreferences.getString("BuildingID", "");
		userInfo.BuildingChatID = mPreferences.getString("BuildingChatID", "");

		userInfo.CityName = mPreferences.getString("CityName", "");
		userInfo.BuildingName = mPreferences.getString("BuildingName", "0.0");

		userInfo.AuditingState = mPreferences.getInt("AuditingState", 0);
		userInfo.AuditingImage = mPreferences.getString("AuditingImage", "0.0");
		return userInfo;
	}

	public static UserInfo_2 getLocalUserInfo_ByPhone(Context mContext,
			String phone) {
		SharedPreferences mPreferences = mContext.getSharedPreferences(phone
				+ "2", Context.MODE_PRIVATE);

		UserInfo_2 userInfo = new UserInfo_2();
		userInfo.UserID = mPreferences.getString("UserID", "");
		userInfo.LoginPhone = mPreferences.getString("LoginPhone", "");
		userInfo.LoginPw = mPreferences.getString("LoginPw", "");
		userInfo.ChatID = mPreferences.getString("ID", "");
		userInfo.NickName = mPreferences.getString("NickName", "");
		userInfo.Sex = mPreferences.getInt("Sex", 1);
		userInfo.Birthday = mPreferences.getString("Birthday", "");
		userInfo.Age = mPreferences.getInt("Age", 0);
		userInfo.Signatures = mPreferences.getString("Signatures", "2");
		userInfo.Balance = mPreferences.getString("Balance", "");
		userInfo.Logo = mPreferences.getString("Logo", "");

		userInfo.Integral = mPreferences.getInt("Integral", 0);
		userInfo.BuildingID = mPreferences.getString("BuildingID", "");
		userInfo.BuildingChatID = mPreferences.getString("BuildingChatID", "");

		userInfo.CityName = mPreferences.getString("CityName", "");
		userInfo.BuildingName = mPreferences.getString("BuildingName", "0.0");

		userInfo.AuditingState = mPreferences.getInt("AuditingState", 0);
		userInfo.AuditingImage = mPreferences.getString("AuditingImage", "0.0");
		return userInfo;
	}

	/**
	 * 单例模式中获取唯一的ExitApplication实例
	 * 
	 * @return
	 */
	public static LXApplication getInstance() {
		return mInstance;
	}

	/**
	 * 添加Activity到容器中
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		mActivityMap.put(activity.getClass().getSimpleName(), activity);
	}

	/**
	 * 从容器中删除activity
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		mActivityMap.remove(activity.getClass().getSimpleName());
	}

	/**
	 * 遍历所有Activity并finish
	 */
	public void exit() {
		Iterator<Activity> it = mActivityMap.values().iterator();
		while (it.hasNext()) {
			it.next().finish();
		}
		System.exit(0);
	}

	/**
	 * 清空activity容器
	 */
	public void clearActivityMap() {
		mActivityMap.clear();
	}

	/**
	 * 获取activity容器
	 * 
	 * @return
	 */
	public HashMap<String, Activity> getActivityMap() {
		return mActivityMap;
	}

	private void initImageLoader() {
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCacheExtraOptions(800, 400)
				.discCacheExtraOptions(800, 400, CompressFormat.JPEG, 65, null)
				.threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.memoryCacheSizePercentage(10)
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileCount(50)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// default
				.imageDownloader(
						new BaseImageDownloader(getApplicationContext()))
				// default
				.imageDecoder(new BaseImageDecoder(true))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs().build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		initDisplayImageOptions();
	}

	private void initDisplayImageOptions() {
		imageOptions = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.iv_loading)
				.showImageForEmptyUri(R.drawable.default_head)
				// .showImageOnFail(R.drawable.iv_loading)
				.resetViewBeforeLoading(false).delayBeforeLoading(10)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// .displayer(new RoundedBitmapDisplayer(2))
				.displayer(new SimpleBitmapDisplayer()).handler(new Handler())
				.build();
	}

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	public static DisplayImageOptions getOptions() {
		return imageOptions;
	}

	public static DisplayImageOptions getOptions(int resId) {
		imageOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(resId).showImageOnFail(resId)
				.showImageOnLoading(resId).resetViewBeforeLoading(false)
				.delayBeforeLoading(10).cacheInMemory(true).cacheOnDisc(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// .displayer(new RoundedBitmapDisplayer(2))
				.displayer(new SimpleBitmapDisplayer()).handler(new Handler())
				.build();
		return imageOptions;
	}

	public static DisplayImageOptions getOptionsToNotLoading(int resId) {
		imageOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(resId).showImageOnFail(resId)
				.resetViewBeforeLoading(false).delayBeforeLoading(10)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// .displayer(new RoundedBitmapDisplayer(2))
				.displayer(new SimpleBitmapDisplayer()).handler(new Handler())
				.build();
		return imageOptions;
	}

	public static void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("gcj02");// 可选，默认gcj02，设置返回的定位结果坐标系，
		option.setScanSpan(1000000000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		mLocationClient.setLocOption(option);
	}
}
