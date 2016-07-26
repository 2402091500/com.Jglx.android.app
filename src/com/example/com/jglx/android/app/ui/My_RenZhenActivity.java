package com.example.com.jglx.android.app.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.http.AsyncHttpResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.task.JoinGroupTask;
import com.example.com.jglx.android.app.task.LogoutGroupTask;
import com.example.com.jglx.android.app.util.DialogUtil;
import com.example.com.jglx.android.app.util.IOSDialogUtil;
import com.example.com.jglx.android.app.util.ImageUtil;
import com.example.com.jglx.android.app.util.IOSDialogUtil.OnSheetItemClickListener;
import com.example.com.jglx.android.app.util.IOSDialogUtil.SheetItemColor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 认证
 * 
 * @author jjj
 * 
 * @date 2015-8-19
 */
public class My_RenZhenActivity extends BaseActivity {
	private final static int TAKE_PHOTO = 1;// 拍照
	private final static int TAKE_PICTURE = 2;// 本地获取
	public final static String TAG = "My_RenZhenActivity";

	private TextView mHomeTv;
	private ImageView mXiaoquIv;
	private ImageView mfeiIv;
	private String mFilePath;// 照片地址
	private boolean isXiaoQu = false;
	private String buildingId;
	private String buildingName;
	private Bitmap bitmapPhoto;
	private Bitmap bitmapPiture;

	private List<String> pathList = new ArrayList<String>();// 照片地址
	private Dialog dialog;
	private String cityName;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_my__ren_zhen);
		setTitleTextRightText("", "认证", "提交", true);
		setRightTvColor(R.color.green);

		buildingId = LXApplication.mUserInfo.BuildingID;
		cityName = LXApplication.mUserInfo.CityName;

		initView();

	}

	private void initView() {
		dialog = DialogUtil.getCenterProgressDialog(this,
				R.string.dialog_puting, true);
		mHomeTv = (TextView) findViewById(R.id.my_tv_Renzhen_right);
		mXiaoquIv = (ImageView) findViewById(R.id.my_tv_xiaoquzhaopianIv);
		mfeiIv = (ImageView) findViewById(R.id.my_tv_Renzhen_suidaifeiIv);
		findViewById(R.id.my_tv_xiaoquzhaopianLayout).setOnClickListener(this);
		findViewById(R.id.my_tv_Renzhen_suidaifeiLayout).setOnClickListener(
				this);
		mHomeTv.setOnClickListener(this);
		mHomeTv.setText(LXApplication.mUserInfo.BuildingName);
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		if (arg0.getId() == R.id.my_tv_xiaoquzhaopianLayout) {// 小区
			isXiaoQu = true;
			showPhotoDialog();
		} else if (arg0.getId() == R.id.my_tv_Renzhen_suidaifeiLayout) {// 水电气费
			isXiaoQu = false;
			showPhotoDialog();
		} else if (arg0.getId() == R.id.my_tv_Renzhen_right) {// 小区名称选择
			Intent intent = new Intent(My_RenZhenActivity.this,
					BuidingActivity.class);
			startActivityForResult(intent, 11);
		}
	}

	@Override
	public void onRightClick() {
		super.onRightClick();
		if (!TextUtils.isEmpty(buildingId) && pathList.size() > 0) {
			UpdateXiaoquName();
		} else {
			Toast.makeText(My_RenZhenActivity.this, "请填写完认证信息!",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void finish() {
		super.finish();

		if (bitmapPhoto != null) {
			bitmapPhoto.recycle();
			bitmapPhoto = null;
		}
		if (bitmapPiture != null) {
			bitmapPiture.recycle();
			bitmapPiture = null;
		}
	}

	/**
	 * 修改入住小区
	 */
	private void UpdateXiaoquName() {
		DialogUtil.showDialog(dialog, -1);
		// 如果小区没有改变就只上传图片
		if (buildingId.equals(LXApplication.mUserInfo.BuildingID)) {
			for (int i = 0; i < pathList.size(); i++) {
				File file = new File(pathList.get(i));
				updateXQImg(file, i + 1);
			}
			return;
		}
		RequstClient.XiuGaiyonHuruzhu_xiaoqu(buildingId,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						DialogUtil.dissDialog(dialog);
						Toast.makeText(My_RenZhenActivity.this, "小区修改失败!",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("修改用户入住小区---r--", content);
						try {
							final JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								Toast.makeText(My_RenZhenActivity.this,
										obj.getString("Message").toString(),
										Toast.LENGTH_SHORT).show();
								return;
							}
							String buildingChatId = obj.get("Data").toString();

							new LogoutGroupTask(My_RenZhenActivity.this)
									.execute(LXApplication.mUserInfo.BuildingChatID);
							new JoinGroupTask(My_RenZhenActivity.this)
									.execute(buildingChatId);

							LXApplication.mUserInfo.BuildingID = buildingId;
							LXApplication.mUserInfo.BuildingChatID = buildingChatId;
							LXApplication.mUserInfo.BuildingName = buildingName;
							LXApplication.mUserInfo.AuditingState = 1;
							LXApplication.mUserInfo.CityName = cityName;

							sendBroadcast(new Intent().setAction("renzheng"));
							
							Set<String> set = new HashSet<String>();
							set.add(LXApplication.mUserInfo.BuildingID);
							set.add(cityName);

							JPushInterface.setAliasAndTags(
									My_RenZhenActivity.this,
									LXApplication.mUserInfo.UserID, set,
									new TagAliasCallback() {

										@Override
										public void gotResult(int arg0,
												String arg1, Set<String> arg2) {
											if (arg0 == 0) {
												Log.e("---------推送别名-biaoqian----:",
														"推送别名注册成功---"
																+ LXApplication.mUserInfo.BuildingID
																+ cityName);
											} else {
												Log.e("---------推送别名注册失败----:",
														String.valueOf(arg0));
											}
										}
									});

							for (int i = 0; i < pathList.size(); i++) {
								File file = new File(pathList.get(i));
								updateXQImg(file, i + 1);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * 修改小区-上传认证图片
	 * 
	 * @param Logo
	 * @param stream
	 * @param contentType
	 */
	private void updateXQImg(File Logo, final int i) {

		RequstClient.Sanchan_Renzhenzhiliao(Logo,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);

						if (i == pathList.size()) {
							DialogUtil.dissDialog(dialog);

							Toast.makeText(My_RenZhenActivity.this,
									"当前的认证图片上传失败", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(My_RenZhenActivity.this,
									"上一张图片没有上传成功", Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.v("修改用户入住图片----r-img-", content);
						try {
							JSONObject obj = new JSONObject(content);
							if (!obj.getString("State").equals("0")) {
								Toast.makeText(My_RenZhenActivity.this,
										obj.getString("Message").toString(),
										Toast.LENGTH_SHORT).show();
								return;
							}

							if (i == pathList.size()) {
								DialogUtil.dissDialog(dialog);
								Toast.makeText(My_RenZhenActivity.this,
										"认证信息上传成功!", Toast.LENGTH_SHORT).show();

								LXApplication.mUserInfo.AuditingState = 1;
								if (bitmapPhoto != null) {
									bitmapPhoto.recycle();
									bitmapPhoto = null;
								}
								if (bitmapPiture != null) {
									bitmapPiture.recycle();
									bitmapPiture = null;
								}

								My_RenZhenActivity.this.finish();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

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

			if (requestCode == 11) {// 选择小区
				buildingId = data.getStringExtra("buildingId");
				buildingName = data.getStringExtra("buildingName");
				cityName = data.getStringExtra("cityName");

				if (!TextUtils.isEmpty(buildingName)) {
					mHomeTv.setText(buildingName);
				}
			} else if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				return;
			} else {

				switch (requestCode) {
				case TAKE_PHOTO:
					if (bitmapPhoto != null) {
						bitmapPhoto.recycle();
						bitmapPhoto = null;
					}

					mFilePath = ImageUtil.filePath + "123.jpg";
					mFilePath = ImageUtil.bitmap2File(mFilePath,
							new Date().getTime() + ".jpg");
					bitmapPhoto = ImageUtil.compressImage(mFilePath);
					if (bitmapPhoto != null) {
						pathList.add(mFilePath);
						if (isXiaoQu) {
							mXiaoquIv.setImageBitmap(bitmapPhoto);
						} else {
							mfeiIv.setImageBitmap(bitmapPhoto);
						}
					}
					break;
				case TAKE_PICTURE:
					if (bitmapPiture != null) {
						bitmapPiture.recycle();
						bitmapPiture = null;
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

					bitmapPiture = ImageUtil.compressImage(mFilePath);

					if (bitmapPiture != null) {
						pathList.add(mFilePath);
						if (isXiaoQu) {
							mXiaoquIv.setImageBitmap(bitmapPiture);
						} else {
							mfeiIv.setImageBitmap(bitmapPiture);
						}
					}
					break;
				}

			}
		}
	}

}
