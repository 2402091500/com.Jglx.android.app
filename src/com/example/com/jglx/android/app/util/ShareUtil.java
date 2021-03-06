package com.example.com.jglx.android.app.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.com.jglx.android.app.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 分享工具类
 * 
 * @author jjj
 * 
 * @date 2015-8-24
 */
public class ShareUtil {
	private static Dialog shareDialog;
	// 首先在您的Activity中添加如下成员变量
	private UMSocialService mController;
	private Activity mContext;
	
	
	String title = "邻信红包大放送";
	String content = "您只要轻轻点动手指就能获得10元红包，还在等什么，赶快告诉你的小伙伴吧，和他们一起来捡耙活!";
	UMImage uImage ;
	String targeUrl = "http://120.25.160.25/";

	public ShareUtil(Activity context) {
		this.mContext = context;
		uImage = new UMImage(mContext, R.drawable.linxin_logo);
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// 添加QQ、QZone平台
		addQQQZonePlatform();

		// 添加微信、微信朋友圈平台
		addWXPlatform();
		shareContent();
	}
	public ShareUtil(Activity context,String title,String content,String  uImage,String targeUrl) {
		this.mContext = context;
		this.title=title;
		this.content=content;
		this.uImage=new UMImage(mContext, "uImage");
		this.targeUrl=targeUrl;
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		shareContent();
		addQQQZonePlatform();
		addWXPlatform();
	}

	public Dialog getShareDialog() {

		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_share, null);
		view.findViewById(R.id.dialog_share_QQLayout).setOnClickListener(
				shareListener);
		view.findViewById(R.id.dialog_share_firendLineLayout)
				.setOnClickListener(shareListener);
		view.findViewById(R.id.dialog_share_weiboLayout).setOnClickListener(
				shareListener);
		view.findViewById(R.id.dialog_share_cancelTv).setOnClickListener(
				shareListener);
		shareDialog = DialogUtil.getMenuDialog(mContext, view);
		return shareDialog;
	}

	OnClickListener shareListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.dialog_share_QQLayout:// QQ
				performShare(SHARE_MEDIA.QQ);
				break;
			case R.id.dialog_share_firendLineLayout:// 朋友圈
				performShare(SHARE_MEDIA.WEIXIN_CIRCLE);

				break;
			case R.id.dialog_share_weiboLayout:// 微博
				performShare(SHARE_MEDIA.SINA);
				break;
			case R.id.dialog_share_cancelTv:// 取消
				break;
			}
			shareDialog.dismiss();
		}
	};

	private void performShare(SHARE_MEDIA platform) {
		mController.postShare(mContext, platform, new SnsPostListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				if (eCode == 200) {
					Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
				} else {
					String eMsg = "";
					if (eCode == -101) {
						eMsg = "没有授权";
					}
					Toast.makeText(mContext, "分享失败[" + eCode + "] " + eMsg,
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private void shareContent() {

	

		// 微信分享
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(content);
		weixinContent.setTitle(title);
		weixinContent.setTargetUrl(targeUrl);
		weixinContent.setShareMedia(uImage);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setTitle(title);
		circleMedia.setShareContent(content);
		circleMedia.setShareMedia(uImage);
		circleMedia.setTargetUrl(targeUrl);
		mController.setShareMedia(circleMedia);

		// 设置QQ空间分享内容
		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setTitle(title);
		qzone.setTargetUrl(targeUrl);
		qzone.setShareContent(content);
		qzone.setShareMedia(uImage);
		mController.setShareMedia(qzone);

		// QQ好友分享
		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setTitle(title);
		qqShareContent.setShareContent(content);
		qqShareContent.setShareMedia(uImage);
		qqShareContent.setTargetUrl(targeUrl);
		mController.setShareMedia(qqShareContent);

		// 新浪微博
		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setTitle(title);
		sinaShareContent.setShareContent(content);
		sinaShareContent.setTargetUrl(targeUrl);
		sinaShareContent.setShareImage(uImage);
		mController.setShareMedia(sinaShareContent);
	}

	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
		String appId = "100424468";
		String appKey = "c7394704798a158208a74ab60104f0ba";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) mContext,
				appId, appKey);
		qqSsoHandler.setTargetUrl(targeUrl);
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				(Activity) mContext, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = "wx54ef86d945522d69";
		String appSecret = "bf15a9dfe632bc43fe0a8cc29d6e358c";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(mContext, appId, appSecret);
		wxHandler.addToSocialSDK();

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(mContext, appId,
				appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

}
