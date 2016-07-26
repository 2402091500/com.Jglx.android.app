package com.example.com.jglx.android.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.adapter.BillAdapter;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.info.BillInfo;

/**
 * 账单列表
 * 
 * @author jjj
 * 
 * @date 2015-8-31
 */
public class BillActivity extends BaseActivity {
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_bill);
		setTitleTextRightText("", "账单查询", "", true);
		

		List<BillInfo> billInfos = new ArrayList<BillInfo>();
		for (int i = 0; i < 10; i++) {
			BillInfo info=new BillInfo();
			info.setResult("sdddd");
			billInfos.add(info);
		}
		mListView=(ListView) findViewById(R.id.bill_lv);
		BillAdapter billAdapter = new BillAdapter(this, 1, billInfos);
		mListView.setAdapter(billAdapter);
	}
}
