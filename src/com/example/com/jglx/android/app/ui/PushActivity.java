package com.example.com.jglx.android.app.ui;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.adapter.PushActionAdapter;
import com.example.com.jglx.android.app.adapter.PushEnrollAdapter;
import com.example.com.jglx.android.app.adapter.PushLMMAdapter;
import com.example.com.jglx.android.app.adapter.PushReChargeAdapter;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.db.PushDao;
import com.example.com.jglx.android.app.util.DialogUtil;

/**
 * 推送列表
 * 
 * @author jjj
 * 
 * @date 2015-9-11
 */
public class PushActivity extends BaseActivity {
	private int code = 0;
	private ListView mListView;
	private List<Map<String, String>> mList;
	private PushLMMAdapter mLMMAdapter;
	private PushActionAdapter mActionAdapter;
	private PushEnrollAdapter mEnrollAdapter;
	private PushReChargeAdapter mReChargeAdapter;
	private PushDao mPushDao;

	private String deleteItemID = null;
	private int deletePos;
	private Dialog deleteDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_push);

		code = getIntent().getIntExtra("code", 0);
		mListView = (ListView) findViewById(R.id.push_lv);
		mPushDao = PushDao.getInstance(this);

		switch (code) {
		case 201:// 邻妹妹
			setTitleTextRightText("", "邻妹妹", "", true);
			mList = mPushDao.getLmmList();
			mLMMAdapter = new PushLMMAdapter(this, mList);
			mListView.setAdapter(mLMMAdapter);
			break;

		case 202:// 充值
			setTitleTextRightText("", "充值消息", "", true);
			mList = mPushDao.getRechargeList();
			mReChargeAdapter = new PushReChargeAdapter(this, mList);
			mListView.setAdapter(mReChargeAdapter);
			break;

		case 203:// 报名
			setTitleTextRightText("", "报名消息", "", true);
			mList = mPushDao.getEnrollList();
			mEnrollAdapter = new PushEnrollAdapter(this, mList);
			mListView.setAdapter(mEnrollAdapter);
			break;

		case 204:// 商家
			setTitleTextRightText("", "商家消息", "", true);
			mList = mPushDao.getShopList();
			mActionAdapter = new PushActionAdapter(this, mList);
			mListView.setAdapter(mActionAdapter);
			break;
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				switch (code) {
				case 201:// 邻妹妹
					Map<String, String> mapLmm = mLMMAdapter.getItem(arg2);
					String dataLmm = mapLmm.get(PushDao.detail);
					try {
						JSONObject jsonObjectLmm = new JSONObject(dataLmm);
						JSONObject objectLmm = jsonObjectLmm
								.getJSONObject("Data");
						int type = jsonObjectLmm.getInt("Type");

						if (type == 105) {
							Intent intent = new Intent(PushActivity.this,
									AddFriendActivity.class);
							startActivity(intent);
							PushActivity.this.finish();

						} else if (type == 201) {// 邻妹妹
							int id = objectLmm.getInt("id");
							if (id != 0) {
								Intent intent = new Intent(PushActivity.this,
										ActionDetailActivity.class);
								intent.putExtra("AcitvityID",
										String.valueOf(id));
								startActivity(intent);
							}
						}

					} catch (JSONException e1) {
						e1.printStackTrace();
					}

					break;
				case 204:// 商家
					Map<String, String> map = mActionAdapter.getItem(arg2);
					String data = map.get(PushDao.detail);
					try {
						JSONObject jsonObject = new JSONObject(data);
						JSONObject object = jsonObject.getJSONObject("Data");

						int id = object.getInt("id");
						if (id != 0) {
							Intent intent = new Intent(PushActivity.this,
									ActionDetailActivity.class);
							intent.putExtra("AcitvityID", String.valueOf(id));
							startActivity(intent);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

					break;
				}
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				deletePos = arg2;
				Map<String, String> map = null;

				switch (code) {
				case 201:// 邻妹妹
					map = mLMMAdapter.getItem(arg2);
					deleteItemID = map.get(PushDao.time);
					break;

				case 202:// 充值
					map = mReChargeAdapter.getItem(arg2);
					deleteItemID = map.get(PushDao.time);
					break;

				case 203:// 报名
					map = mEnrollAdapter.getItem(arg2);
					deleteItemID = map.get(PushDao.time);
					break;

				case 204:// 商家
					map = mActionAdapter.getItem(arg2);
					deleteItemID = map.get(PushDao.time);

					break;
				}
				if (!TextUtils.isEmpty(deleteItemID)) {
					showDeleteDialog();
				}
				return true;
			}
		});
	}

	/**
	 * 删除消息的对话框
	 */
	private void showDeleteDialog() {

		if (deleteDialog == null) {
			View dView = LayoutInflater.from(this).inflate(
					R.layout.dialog_deletechat, null);
			deleteDialog = DialogUtil.getCenterDialog(this, dView);
			dView.findViewById(R.id.dialog_dc_deleteTv).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							deleteDialog.dismiss();
							if (mPushDao.deletePushItemInfo(code, deleteItemID) > 0) {
								Toast.makeText(PushActivity.this, "删除成功",
										Toast.LENGTH_SHORT).show();
								
								mList.remove(deletePos);

								switch (code) {
								case 201:// 邻妹妹
									mLMMAdapter.notifyDataSetChanged();
									break;

								case 202:// 充值
									mReChargeAdapter.notifyDataSetChanged();
									break;

								case 203:// 报名
									mEnrollAdapter.notifyDataSetChanged();
									break;

								case 204:// 商家
									mActionAdapter.notifyDataSetChanged();
									break;
								}
							} else {

								Toast.makeText(PushActivity.this, "删除失败",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
			dView.findViewById(R.id.dialog_dc_cancelTv).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							deleteDialog.dismiss();
						}
					});
			deleteDialog.show();
		} else if (!deleteDialog.isShowing()) {
			deleteDialog.show();
		}

	}

}
