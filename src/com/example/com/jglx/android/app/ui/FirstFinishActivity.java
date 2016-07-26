package com.example.com.jglx.android.app.ui;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.common.Constant;
import com.example.com.jglx.android.app.http.AsyncHttpResponseHandler;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.UserInfo_2;
import com.example.com.jglx.android.app.task.JoinGroupTask;
import com.example.com.jglx.android.app.util.IOSDialogUtil;
import com.example.com.jglx.android.app.util.IOSDialogUtil.OnSheetItemClickListener;
import com.example.com.jglx.android.app.util.IOSDialogUtil.SheetItemColor;
import com.example.com.jglx.android.app.util.ImageUtil;
import com.example.com.jglx.android.app.view.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 如果信息不完善,则需要完善之后才可进入主页
 * 
 * @author jjj
 * 
 * @date 2015-8-21
 */
public class FirstFinishActivity extends BaseActivity {
	private final static int TAKE_PHOTO = 1;// 拍照
	private final static int TAKE_PICTURE = 2;// 本地获取
	public final static String TAG = "FirstFinishActivity";

	private CircleImageView mIconIv;
	private EditText mNameEdt;
	private TextView mBuildingTv;
	private TextView mErroTv;
	private Button mOkBtn;

	private String mFilePath;// 照片地址
	private String buildingId;
	private String buildingName;
	private String buildingChatId;

	private Bitmap bitmap;
	private boolean isIcon = false;
	private boolean isName = false;
	private boolean isBuilding = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_firstfinish);
		setTitleTextRightText("", "个人信息", "", false);

		initView();
	}

	private void initView() {
		mIconIv = (CircleImageView) findViewById(R.id.firstFinish_iconIv);
		mNameEdt = (EditText) findViewById(R.id.firstFinish_nameEdt);
		mBuildingTv = (TextView) findViewById(R.id.firstFinish_buildingTv);
		mErroTv = (TextView) findViewById(R.id.firstFinish_erroTv);
		mOkBtn = (Button) findViewById(R.id.firstFinish_okBtn);
		mIconIv.setOnClickListener(this);
		mOkBtn.setOnClickListener(this);
		mBuildingTv.setOnClickListener(this);
		mNameEdt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s) && !isName) {
					mOkBtn.setVisibility(View.VISIBLE);
				} else {
					mOkBtn.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		if (!TextUtils.isEmpty(LXApplication.mUserInfo.NickName)) {
			isName = true;
			mNameEdt.setText(LXApplication.mUserInfo.NickName);
		}
		if (!TextUtils.isEmpty(LXApplication.mUserInfo.Logo)) {
			isIcon = true;
			mIconIv.setUrl(LXApplication.mUserInfo.Logo);
		}
		if (!LXApplication.mUserInfo.BuildingID.equals("1")
				&& !TextUtils.isEmpty(LXApplication.mUserInfo.BuildingName)) {
			isBuilding = true;
			mBuildingTv.setText(LXApplication.mUserInfo.BuildingName);
		}

	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.firstFinish_iconIv:
			showPhotoDialog();
			break;
		case R.id.firstFinish_okBtn:
			finishName(mNameEdt.getText().toString());
			break;
		case R.id.firstFinish_buildingTv:
			Intent intent = new Intent(FirstFinishActivity.this,
					FirstFinishActivity.class);
			startActivityForResult(intent, 11);
			break;
		}
	}

	// @Override
	// protected void onResume() {
	// super.onResume();
	//
	// buildingId = getIntent().getStringExtra("buildingId");
	// buildingName = getIntent().getStringExtra("buildingName");
	//
	// if (!TextUtils.isEmpty(buildingName) && !TextUtils.isEmpty(buildingId)) {
	// mBuildingTv.setText(buildingName);
	// finishXiaoquName(getIntent().getStringExtra("cityName"));
	// }
	//
	// }

	/**
	 * 选取图片框
	 */
	private void showPhotoDialog() {
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
								intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri
										.fromFile(new File(ImageUtil.filePath,
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
								startActivityForResult(intent2, TAKE_PICTURE);
							}
						}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (requestCode == 11) {
				buildingId = data.getStringExtra("buildingId");
				buildingName = data.getStringExtra("buildingName");

				if (!TextUtils.isEmpty(buildingName)
						&& !TextUtils.isEmpty(buildingId)) {
					mBuildingTv.setText(buildingName);
					finishXiaoquName(getIntent().getStringExtra("cityName"));
				}
			}

			String sdCardState = Environment.getExternalStorageState();
			if (!sdCardState.equals(Environment.MEDIA_MOUNTED)) {
				return;
			} else {

				switch (requestCode) {
				case TAKE_PHOTO:
					if (bitmap != null) {
						bitmap.recycle();
					}
					mFilePath = ImageUtil.filePath + "123.jpg";
					mFilePath = ImageUtil.bitmap2File(mFilePath,
							new Date().getTime() + ".jpg");

					bitmap = ImageUtil.compressImage(mFilePath);
					if (bitmap != null) {

						mIconIv.setImageBitmap(bitmap);
						finishUserIcon();
					}
					break;
				case TAKE_PICTURE:
					if (bitmap != null) {
						bitmap.recycle();
					}
					ContentResolver resolver = getContentResolver();
					Uri originalUri = data.getData(); // 获得图片的uri
					// 这里开始的第二部分，获取图片的路径：
					String[] proj = { MediaStore.Images.Media.DATA };
					Cursor cursor = managedQuery(originalUri, proj, null, null,
							null);
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					// 最后根据索引值获取图片路径
					mFilePath = cursor.getString(column_index);
					bitmap = ImageUtil.compressImage(mFilePath);
					if (bitmap != null) {
						mIconIv.setImageBitmap(bitmap);
						finishUserIcon();
					}
					break;
				}

			}
		}
	}

	@Override
	public void finish() {
		super.finish();
		if (bitmap != null) {
			bitmap.recycle();
		}
	}

	/**
	 * 完成用户头像
	 * 
	 * @param Logo
	 * @param bitmap
	 */
	private void finishUserIcon() {
		File file = new File(mFilePath);

		RequstClient.XiuGaiyonHu_touxian(file, new CustomResponseHandler(this,
				true) {
			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				mErroTv.setText("头像上传失败:" + content);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);

				try {
					JSONObject obj = new JSONObject(content);
					if (!obj.getString("State").equals("0")) {
						mErroTv.setText(content);
						return;
					}
					isIcon = true;
					mErroTv.setText("头像上传成功!");

					LXApplication.mUserInfo.Logo = obj.getString("Data");
					if (isName && isBuilding) {
						new JoinGroupTask(FirstFinishActivity.this)
								.execute(buildingChatId);
						finishSucss();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 上传昵称
	 * 
	 * @param NickName
	 */
	private void finishName(final String NickName) {
		RequstClient.XiuGaiyonHu_zhiliao(NickName, String
				.valueOf(LXApplication.mUserInfo.Sex),
				LXApplication.mUserInfo.Birthday, String
						.valueOf(LXApplication.mUserInfo.Age),
				LXApplication.mUserInfo.Signatures, new CustomResponseHandler(
						this, true) {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						mErroTv.setText("昵称设置失败:" + content);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);

						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								mErroTv.setText(content);
								return;
							}
							isName = true;
							mErroTv.setText("昵称设置成功!");
							LXApplication.mUserInfo.NickName = NickName;

							if (isIcon && isBuilding) {
								new JoinGroupTask(FirstFinishActivity.this)
										.execute(buildingChatId);
								finishSucss();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * 修改入住小区
	 */
	private void finishXiaoquName(final String cityName) {
		RequstClient.XiuGaiyonHuruzhu_xiaoqu(buildingId,
				new CustomResponseHandler(this, true) {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						mErroTv.setText("小区上传失败:" + content);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								mErroTv.setText(content);
								return;
							}

							mErroTv.setText("小区上传成功!");
							isBuilding = true;
							LXApplication.mUserInfo.BuildingID = buildingId;
							LXApplication.mUserInfo.BuildingName = buildingName;
							LXApplication.mUserInfo.CityName = cityName;
							buildingChatId = obj.getString("Data");

							if (isIcon && isName) {
								new JoinGroupTask(FirstFinishActivity.this)
										.execute(buildingChatId);
								finishSucss();
							}
							LXApplication.mUserInfo.BuildingChatID = buildingChatId;
							Set<String> set = new HashSet<String>();
							set.add(LXApplication.mUserInfo.BuildingID);
							set.add(LXApplication.mUserInfo.CityName);
							JPushInterface.setAliasAndTags(
									FirstFinishActivity.this,
									LXApplication.mUserInfo.UserID, set,
									new TagAliasCallback() {

										@Override
										public void gotResult(int arg0,
												String arg1, Set<String> arg2) {
											if (arg0 == 0) {
												Log.e("---------推送标签---:",
														"推送标签成功---"
																+ LXApplication.mUserInfo.BuildingID);
											} else {
												Log.e("---------推送标签失败----:",
														String.valueOf(arg0));
											}
										}
									});
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void finishSucss() {
		Intent intent = new Intent(FirstFinishActivity.this, MainActivity.class);
		startActivity(intent);
		FirstFinishActivity.this.finish();
	}

}
