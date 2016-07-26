package com.example.com.jglx.android.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.com.jglx.android.app.LXApplication;
import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.aliyPay.pay_Utils;
import com.example.com.jglx.android.app.base.BaseActivity;
import com.example.com.jglx.android.app.common.T;
import com.example.com.jglx.android.app.http.CustomResponseHandler;
import com.example.com.jglx.android.app.http.RequstClient;
import com.example.com.jglx.android.app.info.Sdq_info;
import com.example.com.jglx.android.app.info.order_info;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Fuwu_pay_ditail extends BaseActivity {

	private TextView order_ditail_tv;
	private TextView order_money_tv;
	private ImageView pay_bt;
	private pay_Utils payutils;
	private Drawable mWay_Drawable;
	private TextView tv1;
	private TextView tv2;
	private order_info order_;
	private Sdq_info order_sdq;
	private String flag;
	
	private int pay_type=1;
/*
 * 1.用户手机话费充值
 * 2.用户邻信账号充值
*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActiviyContextView(R.layout.activity_fuwu_pay_ditail);
		setTitleTextRightText("", "付款详情", "", true);
		flag=getIntent().getStringExtra("flag");
		if(flag.equals("sdq")){
			order_sdq=(Sdq_info)getIntent().getSerializableExtra("order_sdq");
		}else if(flag.equals("hf")){
			
			order_=(order_info)getIntent().getSerializableExtra("order");
		}else if(flag.equals("ye")){
			
			order_=(order_info)getIntent().getSerializableExtra("order");
		}

		payutils = new pay_Utils(Fuwu_pay_ditail.this);
		initData();
		initview();
	}

	private void initData() {

		mWay_Drawable = getResources().getDrawable(R.drawable.pay_vv);
		mWay_Drawable.setBounds(0, 0, mWay_Drawable.getMinimumWidth(),
				mWay_Drawable.getMinimumHeight());

	}

	public void setonfouce(TextView v) {
         v.setOnClickListener(new OnClickListener() {
			
		

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv1:
					pay_type=1;
					tv1.setCompoundDrawables(null, null, mWay_Drawable, null);
					tv2.setCompoundDrawables(null, null, null, null);
					break;
				case R.id.tv2:
					pay_type=2;
					tv2.setCompoundDrawables(null, null, mWay_Drawable, null);
					tv1.setCompoundDrawables(null, null, null, null);
					break;

				
				}
				
			}
		});
	}

	private void initview() {
		order_ditail_tv = (TextView) findViewById(R.id.order_ditail);
		if(flag.equals("sdq")){
			
			order_ditail_tv.setText(order_sdq.getAccount()+" "+order_sdq.getUnitName() + "充值");
		}else{
			
			order_ditail_tv.setText(order_.getOrder_ditail() + "充值");
		}
		order_money_tv = (TextView) findViewById(R.id.order_money);
		
		if(flag.equals("sdq")){
			
			order_money_tv.setText(order_sdq.getMoney() + "元");
		}else{
			
			order_money_tv.setText(order_.getPay_money() + "元");
		}

		tv1 = (TextView) findViewById(R.id.tv1);

		tv2 = (TextView) findViewById(R.id.tv2);
		
		tv2.setText("余额：  ￥"+LXApplication.mUserInfo.Balance);
		setonfouce(tv1);
		setonfouce(tv2);
		pay_bt = (ImageView) findViewById(R.id.pay_bt);
		pay_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(flag.equals("sdq")){
					if(pay_type==1){
						
						payutils.pay(order_sdq.getUnitName()+"充值", order_sdq.getAccount()+order_sdq.getUnitName(),order_sdq.getMoney());
					}else{
						float a=tofloat(LXApplication.mUserInfo.Balance);
						float b=tofloat(order_sdq.getMoney());
						if(a-b>=0){
							sdq_charege();
						}else{
							
							payutils.pay(order_sdq.getUnitName()+"充值", order_sdq.getAccount()+order_sdq.getUnitName(),String.valueOf(b-a));
						}
						
					}
				}else {
					
					if(order_.getFlag().equals("1")){
						if(pay_type==2){
							float a=tofloat(LXApplication.mUserInfo.Balance);
							float b=tofloat(order_.getPay_money());
							if(a-b>=0){
								mobile_charege();
							}else{
								
								payutils.pay("手机话费充值", order_.getOrder_ditail(),String.valueOf(b-a));
							}
						}else{
							payutils.pay("手机话费充值", order_.getOrder_ditail(), order_.getPay_money());
							
						}
						
					}else if(order_.getFlag().equals("2")){
						payutils.pay("账户余额充值", order_.getOrder_ditail(), order_.getPay_money());
						
					}
				}
				


			}
		});

	}

	public void paymentSuccess() {

		T.showShort(this, "支付成功");

		pay_bt.setEnabled(false);
		if(flag.equals("sdq")){
			money_to_linxin_sdq();
		}else{
			// 话费与余额  调这里
			money_to_linxin_yueand_huafei();
		}

	}

	public void money_to_linxin_yueand_huafei() {
		RequstClient.User_charge(order_.getMoney(), "钱包充值  "+order_.getMoney()+"元",
				new CustomResponseHandler(this, true) {
					@Override
					public void onSuccess(int statusCode, String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, content);

						JSONObject obj;
						try {
							obj = new JSONObject(content);
							if (obj.getString("State").equals("0")) {
								System.out.println(content + "支付宝充值成功！");
								T.showShort(Fuwu_pay_ditail.this, "支付宝充值成功！");
								if(order_.getFlag().equals("1")){
									
									mobile_charege();
								}
							} else {
								System.out.println(content + "支付宝充值失败！");
								T.showShort(Fuwu_pay_ditail.this, "支付宝充值失败！");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
	}
	public void money_to_linxin_sdq() {
		RequstClient.User_charge(order_sdq.getMoney(), "钱包充值  "+order_sdq.getMoney()+"元",
				new CustomResponseHandler(this, true) {
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				
				JSONObject obj;
				try {
					obj = new JSONObject(content);
					if (obj.getString("State").equals("0")) {
						System.out.println(content + "支付宝充值成功！");
						T.showShort(Fuwu_pay_ditail.this, "支付宝充值成功！");
						
							
							sdq_charege();
						
					} else {
						System.out.println(content + "支付宝充值失败！钱已打到您的邻信钱包");
						T.showShort(Fuwu_pay_ditail.this, "支付宝充值失败！钱已打到您的邻信钱包");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}

			
		});
	}
	private void sdq_charege() {
		RequstClient.Fuwu_pay_money(order_sdq.getProvinceID(), order_sdq.getCityID(), order_sdq.getProjectID(),
				order_sdq.getUnitID(), order_sdq.getProductID(), order_sdq.getAccount(), order_sdq.getContract(),
				order_sdq.getMentDay(), order_sdq.getMoney(), order_sdq.getMode(),new CustomResponseHandler(Fuwu_pay_ditail.this, true){
			
			
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				JSONObject obj;
				try {
					obj = new JSONObject(content);
					if (obj.getString("State").equals("0")) {
						System.out.println(content + order_sdq.getUnitName()+"充值成功！");
						T.showShort(Fuwu_pay_ditail.this, order_sdq.getUnitName()+"充值成功！");
					}else{
						System.out.println(content + order_sdq.getUnitName()+"充值失败！");
						T.showShort(Fuwu_pay_ditail.this, order_sdq.getUnitName()+"充值失败！");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	public void mobile_charege() {

		RequstClient.Mobile_charge
		(order_.getCard_number(), order_.getMoney(), order_.getPay_money(),
				new CustomResponseHandler(this, true) {
					@Override
					public void onSuccess(int statusCode, String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, content);
                         System.out.println("手机充值返回"+content);
						JSONObject obj;
						try {
							obj = new JSONObject(content);
							if (obj.getString("State").equals("0")) {
								System.out.println(content + "话费充值成功！");
								T.showShort(Fuwu_pay_ditail.this, "话费充值成功！");
							}else{
								System.out.println(content + "话费充值失败！");
								T.showShort(Fuwu_pay_ditail.this, "话费充值失败！");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}
	
	
	public Float tofloat(String str){
		return Float.parseFloat(str);
	}

}
