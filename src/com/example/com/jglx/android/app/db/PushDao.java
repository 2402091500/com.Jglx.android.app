package com.example.com.jglx.android.app.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 推送相关-detail为json格式
 * 
 * @author jjj
 * 
 * @date 2015-9-11
 */
public class PushDao {
	private DbOpenHelper dbHelper;
	private static PushDao mPushDao;
	public static String time = "time";
	public static String detail = "detail";

	// 推送操作表
	public static final String TABLE_PUSH = "push_table";
	public static final String PUSH_TIME = "push_time";
	public static final String PUSH_TITLE = "push_title";
	public static final String PUSH_CONTENT = "push_content";
	public static final String PUSH_CODE = "push_code";
	public static final String PUSH_COUNT = "push_count";

	// 充值表
	public static final String TABLE_RECAHRGE = "recharge_table";
	public static final String RECAHRGE_TIME = "recharge_time";
	public static final String RECAHRGE_DETAIL = "recharge_detail";

	// 报名表
	public static final String TABLE_ENROLL = "enroll_table";
	public static final String ENROLL_TIME = "enroll_time";
	public static final String ENROLL_DETAIL = "enroll_detail";

	// 邻妹妹
	public static final String TABLE_LMM = "lmm_table";
	public static final String LMM_TIME = "lmm_time";
	public static final String LMM_DETAIL = "lmm_detail";

	// 商家表
	public static final String TABLE_SHOP = "shop_table";
	public static final String SHOP_TIME = "shop_time";
	public static final String SHOP_DETAIL = "shop_detail";

	public PushDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	public static PushDao getInstance(Context context) {
		if (mPushDao == null) {
			mPushDao = new PushDao(context);
		}
		return mPushDao;
	}

	/**
	 * 关闭数据库
	 */
	synchronized public void closeDB() {
		if (dbHelper != null) {
			dbHelper.closeDB();
		}
	}

	/**
	 * 添加充值消息
	 * 
	 * @param time
	 * @param detail
	 */
	synchronized public void addRecharge(String time, String detail) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put(PushDao.RECAHRGE_TIME, time);
			values.put(PushDao.RECAHRGE_DETAIL, detail);
			db.insert(PushDao.TABLE_RECAHRGE, null, values);
		}
		closeDB();
	}

	/**
	 * 添加报名消息
	 * 
	 * @param time
	 * @param detail
	 */
	synchronized public void addEnroll(String time, String detail) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put(PushDao.ENROLL_TIME, time);
			values.put(PushDao.ENROLL_DETAIL, detail);
			db.insert(PushDao.TABLE_ENROLL, null, values);
		}
		closeDB();
	}

	/**
	 * 添加邻妹妹消息
	 * 
	 * @param time
	 * @param detail
	 */
	synchronized public void addLmm(String time, String detail) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put(PushDao.LMM_TIME, time);
			values.put(PushDao.LMM_DETAIL, detail);
			db.insert(PushDao.TABLE_LMM, null, values);
		}
		closeDB();
	}

	/**
	 * 添加商家消息
	 * 
	 * @param time
	 * @param detail
	 */
	synchronized public void addShop(String time, String detail) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put(PushDao.SHOP_TIME, time);
			values.put(PushDao.SHOP_DETAIL, detail);
			db.insert(PushDao.TABLE_SHOP, null, values);
		}
		closeDB();
	}

	/**
	 * 获取充值消息
	 * 
	 * @return
	 */
	synchronized public List<Map<String, String>> getRechargeList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		if (db.isOpen()) {
			// Cursor cursor = db.query(TABLE_RECAHRGE, null, null, null, null,
			// null, RECAHRGE_TIME, " desc ");
			String sql = "select * from " + PushDao.TABLE_RECAHRGE;
			Cursor cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();

				String time = cursor.getString(cursor
						.getColumnIndex(PushDao.RECAHRGE_TIME));
				String detail = cursor.getString(cursor
						.getColumnIndex(PushDao.RECAHRGE_DETAIL));

				map.put(PushDao.time, time);
				map.put(PushDao.detail, detail);
				list.add(map);
			}
		}
		closeDB();
		return list;
	}

	/**
	 * 获取报名消息
	 * 
	 * @return
	 */
	synchronized public List<Map<String, String>> getEnrollList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		if (db.isOpen()) {
			String sql = "select * from " + PushDao.TABLE_ENROLL;
			Cursor cursor = db.rawQuery(sql, null);
			// Cursor cursor = db.query(TABLE_ENROLL, null, null, null, null,
			// null, ENROLL_TIME, " desc ");

			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();

				String time = cursor.getString(cursor
						.getColumnIndex(PushDao.ENROLL_TIME));
				String detail = cursor.getString(cursor
						.getColumnIndex(PushDao.ENROLL_DETAIL));

				map.put(PushDao.time, time);
				map.put(PushDao.detail, detail);
				list.add(map);
			}
		}
		closeDB();
		return list;
	}

	/**
	 * 获取邻妹妹消息
	 * 
	 * @return
	 */
	synchronized public List<Map<String, String>> getLmmList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		if (db.isOpen()) {
			// Cursor cursor = db.query(TABLE_LMM, null, null, null, null, null,
			// LMM_TIME, " desc ");

			String sql = "select * from " + PushDao.TABLE_LMM;
			Cursor cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();

				String time = cursor.getString(cursor
						.getColumnIndex(PushDao.LMM_TIME));
				String detail = cursor.getString(cursor
						.getColumnIndex(PushDao.LMM_DETAIL));

				map.put(PushDao.time, time);
				map.put(PushDao.detail, detail);
				list.add(map);
			}
		}
		closeDB();
		return list;
	}

	/**
	 * 获取商家消息
	 * 
	 * @return
	 */
	synchronized public List<Map<String, String>> getShopList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		if (db.isOpen()) {
			// Cursor cursor = db.query(TABLE_SHOP, null, null, null, null,
			// null,
			// SHOP_TIME, " desc ");

			String sql = "select * from " + PushDao.TABLE_SHOP;
			Cursor cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();

				String time = cursor.getString(cursor
						.getColumnIndex(PushDao.SHOP_TIME));
				String detail = cursor.getString(cursor
						.getColumnIndex(PushDao.SHOP_DETAIL));

				map.put(PushDao.time, time);
				map.put(PushDao.detail, detail);
				list.add(map);
			}
		}
		closeDB();
		return list;
	}

	/**
	 * 删除消息
	 * 
	 * @param code
	 * @param itemID
	 */
	synchronized public int deletePushItemInfo(int code, String itemID) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String table = null;
		String whereId = null;
		if (db.isOpen()) {
			switch (code) {
			case 201:// 邻妹妹
				table = TABLE_LMM;
				whereId = LMM_TIME;
				break;

			case 202:// 充值
				table = TABLE_RECAHRGE;
				whereId = RECAHRGE_TIME;
				break;

			case 203:// 报名
				table = TABLE_ENROLL;
				whereId = ENROLL_TIME;
				break;

			case 204:// 商家
				table = TABLE_SHOP;
				whereId = SHOP_TIME;
				break;
			}

			return db.delete(table, whereId + "=?", new String[] { itemID });
		}
		closeDB();
		return -1;
	}

}
