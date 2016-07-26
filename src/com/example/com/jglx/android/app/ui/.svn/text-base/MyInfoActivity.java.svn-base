package com.example.com.jglx.android.app.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.util.IOSDialogUtil;
import com.example.com.jglx.android.app.util.IOSDialogUtil.OnSheetItemClickListener;
import com.example.com.jglx.android.app.util.IOSDialogUtil.SheetItemColor;
import com.example.com.jglx.android.app.util.ImageUtil;
import com.example.com.jglx.android.app.view.CircleImageView;
import com.example.com.jglx.android.constants.URLs;
import com.example.com.jglx.android.datepicker.wheelview.NumericWheelAdapter;
import com.example.com.jglx.android.datepicker.wheelview.OnWheelScrollListener;
import com.example.com.jglx.android.datepicker.wheelview.WheelView;

/**
 * 个人信息
 * 
 * @author jjj
 * 
 * @date 2015-8-5
 */
public class MyInfoActivity extends BaseActivity implements OnClickListener {
	private final static int TAKE_PHOTO = 1;// 拍照
	private final static int TAKE_PICTURE = 2;// 本地获取
	private final static int TAKE_CROP = 3;// 裁剪

	private CircleImageView mIconIv;
	private TextView mNameTv;
	private TextView mSexTv;
	private TextView mAgeTv;
	private TextView mSignTv;

	private String mFilePath;
	private LayoutInflater inflater;
	public LinearLayout ll;

	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private WheelView min;
	private WheelView sec;
	private String impath;

	private int mYear = 1996;
	private int mMonth = 0;
	private int mDay = 1;
	private View view;
	private String birthday;

	public UserInfo_2 user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_myinfo);
		setTitleTextRightText("", "个人信息", "", true);

		initData();
		initView();
	}

	private void initData() {

	}

	public void initView() {
		user = LXApplication.mUserInfo;
		inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		ll = (LinearLayout) findViewById(R.id.ll);
		ll.addView(getDataPick());
		mIconIv = (CircleImageView) findViewById(R.id.myinfo_IconIv);
		if (LXApplication.LocalTouxian != ""
				&& LXApplication.LocalTouxian != null) {
			Bitmap bit = BitmapFactory.decodeFile(LXApplication.LocalTouxian);
			mIconIv.setImageBitmap(bit);
		} else if (user.Logo == "" || user.Logo == null) {
			mIconIv.setImageBitmap(BitmapFactory.decodeResource(getResources(),
					R.drawable.default_head));

		} else {

			mIconIv.setUrl(user.Logo);
		}
		mNameTv = (TextView) findViewById(R.id.myinfo_nameTv);
		mNameTv.setText(user.NickName);
		mSexTv = (TextView) findViewById(R.id.myinfo_sexTv);
		switch (user.Sex) {
		case 0:
			mSexTv.setText("保密");
			break;
		case 1:
			mSexTv.setText("男");

			break;
		case 2:
			mSexTv.setText("女");

			break;

		}

		mAgeTv = (TextView) findViewById(R.id.myinfo_ageTv);
		mAgeTv.setText(user.Birthday);
		mSignTv = (TextView) findViewById(R.id.myinfo_signTv);
		mSignTv.setText(user.Signatures);
		mIconIv.setOnClickListener(this);
		findViewById(R.id.myinfo_nameLayout).setOnClickListener(this);
		findViewById(R.id.myinfo_sexLayout).setOnClickListener(this);
		findViewById(R.id.myinfo_ageLayout).setOnClickListener(this);
		findViewById(R.id.myinfo_signLayout).setOnClickListener(this);
		findViewById(R.id.left_quxiao).setOnClickListener(this);
		findViewById(R.id.right_queding).setOnClickListener(this);

	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @return int
	 * @author lilifeng
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 * 
	 */
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,
				1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
	}

	/*
	 * dataPick滑动 scrollListener
	 */
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_year = year.getCurrentItem() + 1950;// 年
			int n_month = month.getCurrentItem() + 1;// 月

			initDay(n_year, n_month);

			birthday = new StringBuilder()
					.append((year.getCurrentItem() + 1950))
					.append("-")
					.append((month.getCurrentItem() + 1) < 10 ? "0"
							+ (month.getCurrentItem() + 1) : (month
							.getCurrentItem() + 1))
					.append("-")
					.append(((day.getCurrentItem() + 1) < 10) ? "0"
							+ (day.getCurrentItem() + 1) : (day
							.getCurrentItem() + 1)).toString();
		}
	};

	/**
	 * 根据日期计算年龄
	 * 
	 * @param birthday
	 * @return
	 * 
	 */
	public static final String calculateDatePoor(String birthday) {
		try {
			if (TextUtils.isEmpty(birthday))
				return "0";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdayDate = sdf.parse(birthday);
			String currTimeStr = sdf.format(new Date());
			Date currDate = sdf.parse(currTimeStr);
			if (birthdayDate.getTime() > currDate.getTime()) {
				return "0";
			}
			long age = (currDate.getTime() - birthdayDate.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			String year = new DecimalFormat("0.00").format(age / 365f);
			if (TextUtils.isEmpty(year))
				return "0";
			return String.valueOf(new Double(year).intValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}

	/*
	 * datapick modify by lilifeng
	 */
	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);
		// int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		// int curDate = c.get(Calendar.DATE);

		int curYear = mYear;
		int curMonth = mMonth + 1;
		int curDate = mDay;

		view = inflater.inflate(R.layout.wheel_date_picker, null);

		year = (WheelView) view.findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				this, 1950, norYear);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// 是否可循环滑动
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				this, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear, curMonth);
		day.addScrollingListener(scrollListener);
		day.setCyclic(true);

		year.setVisibleItems(9);// 设置显示行数
		month.setVisibleItems(9);
		day.setVisibleItems(9);

		year.setCurrentItem(curYear - 1950);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		return view;
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		Intent intent = null;

		switch (arg0.getId()) {
		case R.id.myinfo_IconIv:// // 头像

			new IOSDialogUtil(this)
					.builder()
					.setCancelable(true)
					.setCanceledOnTouchOutside(true)
					.addSheetItem("拍照", SheetItemColor.Black,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									File file = new File(ImageUtil.filePath);
									if (!file.exists()) {
										file.mkdirs();
									}
									Intent intent1 = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent1.putExtra(MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(
													ImageUtil.filePath,
													"123.jpg")));
									startActivityForResult(intent1, TAKE_PHOTO);
								}
							})
					.addSheetItem("本地获取", SheetItemColor.Black,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Intent intent2 = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
									intent2.setType("image/*");
									startActivityForResult(intent2,
											TAKE_PICTURE);
								}
							}).show();

			break;
		case R.id.myinfo_nameLayout:// 姓名
			intent = new Intent(this, MyNameActivity.class);
			startActivity(intent);
			break;
		case R.id.myinfo_sexLayout:// 性别
			intent = new Intent(this, MySexActivity.class);
			startActivity(intent);

			break;
		case R.id.myinfo_ageLayout:// 生日
			ll.setVisibility(1);

			break;
		case R.id.left_quxiao:// 生日 取消
			ll.setVisibility(-1);

			break;
		case R.id.right_queding:// 生日 确定
			mAgeTv.setText(birthday);
			ll.setVisibility(-1);

			// 修改服务器上的生日
			Xiugai_shenri();

			break;

		case R.id.myinfo_signLayout:// 个性签名
			intent = new Intent(this, SignActivity.class);
			startActivity(intent);
			break;
		}
	}

	/*
	 * 修改服务器上的生日)
	 * 
	 * @param 设定文件
	 * 
	 * @return void 返回类型
	 * 
	 * @throws
	 */
	public void Xiugai_shenri() {
		user = LXApplication.mUserInfo;
		RequstClient.XiuGaiyonHu_zhiliao(user.NickName,
				String.valueOf(user.Sex), birthday,
				calculateDatePoor(birthday), user.Signatures,
				new CustomResponseHandler(this, true) {
					@Override
					public void onSuccess(int statusCode, String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, content);
						LXApplication.mUserInfo.Birthday = birthday;
						LXApplication.mUserInfo.Age = Integer
								.parseInt(calculateDatePoor(birthday));

					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			String sdCardState = Environment.getExternalStorageState();
			if (!sdCardState.equals(Environment.MEDIA_MOUNTED)) {
				return;
			} else {

				switch (requestCode) {
				case TAKE_PHOTO:
					mFilePath = ImageUtil.filePath + "123.jpg";
					mFilePath = ImageUtil.bitmap2File(mFilePath,
							new Date().getTime() + ".jpg");
					File file = new File(mFilePath);
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (Exception e) {
						}
					}
					startPhotoZoom(Uri.fromFile(file), 100);

					break;
				case TAKE_CROP:// // 裁剪成功后显示图片
					Bundle bundle = data.getExtras();
					if (bundle != null) {
						Bitmap bitmap = bundle.getParcelable("data");
						if (bitmap != null) {
							bitmap = ImageUtil.toRoundBitmap(bitmap);

							saveBitmapFile(bitmap);
							RequstClient.XiuGaiyonHu_touxian(new File(impath),
									new CustomResponseHandler(this, true) {
										@Override
										public void onSuccess(int statusCode,
												String content) {
											// TODO Auto-generated method stub
											super.onSuccess(statusCode, content);

											JSONObject obj;
											try {
												obj = new JSONObject(content);
												if (obj.getString("State")
														.equals("0")) {
													String touxianming = obj
															.getString("Data");
													LXApplication.mUserInfo.Logo = touxianming;
													LXApplication
															.saveLocaltouxian(
																	MyInfoActivity.this,
																	impath,
																	user.LoginPhone);
													Toast.makeText(
															getBaseContext(),
															"头像修改成功"
																	+ touxianming,
															Toast.LENGTH_SHORT)
															.show();
												}
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

										}
									});
							mIconIv.setImageBitmap(bitmap);
						}
					}
					break;
				case TAKE_PICTURE:

					Uri imgUri_2 = data.getData();
					startPhotoZoom(imgUri_2, 100);
					break;
				}

			}
		}
	}

	// 使用系统当前日期加以调整作为照片的名称
	@SuppressLint("SimpleDateFormat")
	public String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	public void saveBitmapFile(Bitmap bitmap) {
		// sdcard/pic/
		impath = "/mnt/sdcard/" + getPhotoFileName();

		File file = new File(impath);// 将要保存图片的路径
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String saveImg(Bitmap b, String name) throws Exception {

		String path = Environment.getExternalStorageDirectory() + "/"
				+ "BJLiuJian/YaSuoTuPian";
		File dirFile = new File(path);
		File mediaFile = new File(path + File.separator + name + ".jpg");
		if (mediaFile.exists()) {
			mediaFile.delete();
		}
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		mediaFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(mediaFile);
		b.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
		b.recycle();
		b = null;
		System.gc();
		return mediaFile.getPath();
	}

	/**
	 * 跳转至系统截图界面进行截图
	 * 
	 * @param data
	 * @param size
	 */
	private void startPhotoZoom(Uri data, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		// crop为true时表示显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, TAKE_CROP);
	}

	// 根据uri获得图片真实地址
	public static String getPath(Uri uri, Context context) {
		String[] proj = { MediaStore.Images.Media.DATA };
		ContentResolver cr = context.getContentResolver();

		Cursor cursor = cr.query(uri, proj, null, null, null);

		cursor.moveToFirst();

		int actual_image_column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		return cursor.getString(actual_image_column_index);

	}

}
