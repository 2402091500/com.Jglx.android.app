package com.example.com.jglx.android.app.adapter;


import java.util.ArrayList;
import java.util.List;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.view.RetangleImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HorizontalListViewAdapter extends BaseAdapter{
	public List<String> paths=new ArrayList<String>();
	private Context mContext;
	private LayoutInflater mInflater;
//	private int selectIndex = -1;
	private Bitmap bit;

	public HorizontalListViewAdapter(Context context,List<String> paths){
		this.mContext = context;
		this.paths = paths;
		mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return paths.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.h_horizontal_list_item, null);
			holder.mImage=(RetangleImageView)convertView.findViewById(R.id.img_list_item);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
//		if(position == selectIndex){
//			convertView.setSelected(true);
//		}else{
//			convertView.setSelected(false);
//		}
		if(paths.size()==0){
			 bit = BitmapFactory.decodeResource(mContext.getResources(),
						R.drawable.default_head);
			holder.mImage.setImageBitmap(bit);
		}else{
		holder.mImage.setUrl(paths.get(position));
		}
		return convertView;
	}

	private static class ViewHolder {
		private RetangleImageView mImage;
	}
//	public void setSelectIndex(int i){
//		selectIndex = i;
//	}
}