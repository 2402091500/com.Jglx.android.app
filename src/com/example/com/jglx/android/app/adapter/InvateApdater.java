package com.example.com.jglx.android.app.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.com.jglx.android.app.R;
import com.example.com.jglx.android.app.info.InvateInfo_2;
import com.example.com.jglx.android.app.interfaces.IconClickListener;
import com.example.com.jglx.android.app.view.CircleImageView;
import com.example.com.jglx.android.app.view.InvateActionLayout;
import com.example.com.jglx.android.app.view.RGridView;
import com.example.com.jglx.android.app.view.RetangleImageView;

/**
 * 首页邀约适配器
 * 
 * @author jjj
 * 
 * @date 2015-8-6
 */
public class InvateApdater extends BaseAdapter {
	private List<InvateInfo_2> mList;
	private Context mContext;
	private String[] itemNames;
	private int[] itemImgs;
	private final int type_1 = 0;
	private final int type_2 = 1;
	private final int type_3 = 2;
	private ItemGridViewClickListener mItemGridViewClickListener;
	private IconClickListener mIconClickListener;
	private ItemAdapter mItemAdapter;
	private InvateActionLayout mInvateActionLayout;

	public InvateApdater(Context context, List<InvateInfo_2> list,
			Fragment fragment, String[] itemNames) {
		this.mContext = context;
		this.mList = list;
		this.itemNames = itemNames;
		mItemGridViewClickListener = (ItemGridViewClickListener) fragment;
		mIconClickListener = (IconClickListener) fragment;

		mItemAdapter = new ItemAdapter();
		itemImgs = new int[] { R.drawable.item_mother, R.drawable.item_tour,
				R.drawable.item_pet, R.drawable.item_home,
				R.drawable.item_poker, R.drawable.item_father,
				R.drawable.item_run, R.drawable.item_game,
				R.drawable.item_gossip };

		mInvateActionLayout = new InvateActionLayout(mContext);
	}

	public InvateActionLayout getmInvateActionLayout() {
		return mInvateActionLayout;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size() + 2;
	}

	@Override
	public InvateInfo_2 getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public int getItemViewType(int position) {

		if (getCount() == 2) {// 没有邀约数据的时候
			if (position == 0) {
				return type_2;
			} else {
				return type_3;
			}
		} else if (getCount() < 5) {
			if (position == 1) {
				return type_2;
			} else if (position == getCount()) {
				return type_3;
			} else {

				return type_1;
			}

		} else {
			if (position == 1) {
				return type_2;
			} else if (position == 5) {
				return type_3;
			} else {
				return type_1;
			}
		}
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		ItemHolder itemHolder = null;

		int type = getItemViewType(position);
		if (type == type_1) {// 邀约信息布局
			if (arg1 == null) {
				arg1 = LayoutInflater.from(mContext).inflate(
						R.layout.item_sf_invite, null);
				holder = new ViewHolder(arg1);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}

			final InvateInfo_2 info;
			if (position == 0) {
				info = getItem(position);
			} else if (position < 6) {
				info = getItem(position - 1);
			} else {
				info = getItem(position - 2);
			}

			if (info != null) {
				if ("0".equals(info.Type)) {// 话题
					holder.enrollTv.setVisibility(View.GONE);
				} else {// 邀约
					holder.enrollTv.setVisibility(View.VISIBLE);
					doText(holder.enrollTv, String.valueOf(info.Applys), "");
				}
				doText(holder.nameTv, info.NickName, "");
				doText(holder.ageTv, String.valueOf(info.Age), "");
				doText(holder.addressTv, info.BuildingName, "");
				doText(holder.contentTv, info.Detail, "");
				doText(holder.disucssTv, String.valueOf(info.Replys), "");
				doText(holder.surfTv, String.valueOf(info.Browses), "");
				if (!TextUtils.isEmpty(info.Logo)) {
					holder.iconIv.setUrl(info.Logo);
				} else {
					holder.iconIv.setImageResource(R.drawable.default_head);
				}
				String time = info.PublishDate.replace("T", " ");
				if (time.contains(".")) {
					time = time.substring(0, time.indexOf("."));
				}
				holder.timeTv.setText(time);
				if (info.Sex == 0) {
					holder.sexIv.setVisibility(View.GONE);
					holder.sexLayout
							.setBackgroundResource(R.drawable.retangle_pink);
				} else if (info.Sex == 1) {
					holder.sexIv.setImageResource(R.drawable.sex_man);
					holder.sexIv.setVisibility(View.VISIBLE);
					holder.sexLayout
							.setBackgroundResource(R.drawable.retangle_blue);
				} else if (info.Sex == 2) {
					holder.sexIv.setVisibility(View.VISIBLE);
					holder.sexLayout
							.setBackgroundResource(R.drawable.retangle_pink);
					holder.sexIv.setImageResource(R.drawable.sex_woman);
				}
				String[] imgs = info.Images;
				int len = imgs.length;
				if (imgs != null && len > 0) {
					holder.imgLayout.setVisibility(View.VISIBLE);
					if (len > 2) {
						holder.imgIv1.setUrl(imgs[0]);
						holder.imgIv2.setUrl(imgs[1]);
						holder.imgIv3.setUrl(imgs[2]);
						holder.imgIv1.setVisibility(View.VISIBLE);
						holder.imgIv2.setVisibility(View.VISIBLE);
						holder.imgIv3.setVisibility(View.VISIBLE);
					} else if (len > 1) {
						holder.imgIv1.setUrl(imgs[0]);
						holder.imgIv2.setUrl(imgs[1]);
						holder.imgIv1.setVisibility(View.VISIBLE);
						holder.imgIv2.setVisibility(View.VISIBLE);
						holder.imgIv3.setVisibility(View.INVISIBLE);
					} else if (len > 0) {
						holder.imgIv1.setUrl(imgs[0]);
						holder.imgIv1.setVisibility(View.VISIBLE);
						holder.imgIv2.setVisibility(View.INVISIBLE);
						holder.imgIv3.setVisibility(View.INVISIBLE);
					}
				} else {
					holder.imgLayout.setVisibility(View.GONE);
				}
			}
			holder.iconIv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (mIconClickListener != null) {
						int i = position;
						if (position > 0 && position < 5) {
							i = position - 1;
						} else if (position > 5) {

							i = position - 2;
						}
						mIconClickListener.onIconClick(i);
					}
				}
			});
			// holder.imgIv1.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// mImgClickListener.onImgClick(1, info.InviteID);
			// }
			// });
			// holder.imgIv2.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// mImgClickListener.onImgClick(2, info.InviteID);
			//
			// }
			// });
			// holder.imgIv3.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			//
			// mImgClickListener.onImgClick(3, info.InviteID);
			// }
			// });
		} else if (type == type_2) {
			if (arg1 == null) {// 主题布局
				arg1 = LayoutInflater.from(mContext).inflate(
						R.layout.item_sf_invite_item, null);
				itemHolder = new ItemHolder(arg1);
				arg1.setTag(itemHolder);
			} else {
				itemHolder = (ItemHolder) arg1.getTag();
			}

			itemHolder.itemGv.setAdapter(mItemAdapter);
		} else if (type == type_3) {
			// mInvateActionLayout = new InvateActionLayout(
			// mContext);
			arg1 = mInvateActionLayout;
		}

		return arg1;
	}

	class ItemHolder {
		RGridView itemGv;

		public ItemHolder(View view) {
			itemGv = (RGridView) view.findViewById(R.id.item_sfInviteItem_gv);
			itemGv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					mItemGridViewClickListener.itemGridViewClick(arg2);
				}

			});
		}
	}

	private void doText(TextView tv, String string1, String string2) {
		if (!TextUtils.isEmpty(string1)) {
			tv.setText(string1);
		} else {
			tv.setText(string2);
		}
	}

	class ViewHolder {
		CircleImageView iconIv;
		ImageView sexIv;
		RelativeLayout sexLayout;
		TextView nameTv;
		TextView ageTv;
		TextView addressTv;
		TextView timeTv;
		TextView contentTv;
		TextView enrollTv;
		TextView disucssTv;
		TextView surfTv;
		LinearLayout imgLayout;
		RetangleImageView imgIv1;
		RetangleImageView imgIv2;
		RetangleImageView imgIv3;

		public ViewHolder(View view) {
			iconIv = (CircleImageView) view
					.findViewById(R.id.item_sfInvite_iconIv);
			sexIv = (ImageView) view.findViewById(R.id.item_sfInvite_sexIv);
			sexLayout = (RelativeLayout) view
					.findViewById(R.id.item_sfInvite_sexLayout);
			nameTv = (TextView) view.findViewById(R.id.item_sfInvite_nameTv);
			ageTv = (TextView) view.findViewById(R.id.item_sfInvite_oldTv);
			addressTv = (TextView) view.findViewById(R.id.item_sfInvite_homeTv);
			timeTv = (TextView) view.findViewById(R.id.item_sfInvite_timeTv);
			contentTv = (TextView) view
					.findViewById(R.id.item_sfInvite_contentTv);
			enrollTv = (TextView) view
					.findViewById(R.id.item_sfInvite_baomingTv);
			disucssTv = (TextView) view
					.findViewById(R.id.item_sfInvite_pinglunTv);
			surfTv = (TextView) view.findViewById(R.id.item_sfInvite_surfTv);
			imgLayout = (LinearLayout) view
					.findViewById(R.id.item_sfInvite_ivlayout);
			imgIv1 = (RetangleImageView) view
					.findViewById(R.id.item_sfInvite_iv1);
			imgIv2 = (RetangleImageView) view
					.findViewById(R.id.item_sfInvite_iv2);
			imgIv3 = (RetangleImageView) view
					.findViewById(R.id.item_sfInvite_iv3);
		}

	}

	class ItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return itemNames.length;
		}

		@Override
		public String getItem(int arg0) {
			return itemNames[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if (arg1 == null) {
				arg1 = LayoutInflater.from(mContext).inflate(
						R.layout.item_item_iv, null);
				holder = new ViewHolder(arg1);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}

			holder.imgIv.setImageResource(itemImgs[arg0]);
			holder.nameTv.setText(itemNames[arg0]);
			return arg1;
		}

		class ViewHolder {
			ImageView imgIv;
			TextView nameTv;

			public ViewHolder(View view) {
				imgIv = (ImageView) view.findViewById(R.id.item_itemIv_iv);
				nameTv = (TextView) view.findViewById(R.id.item_itemIv_nameTv);
			}
		}

	}

	public interface ItemGridViewClickListener {
		public void itemGridViewClick(int position);
	}

}
