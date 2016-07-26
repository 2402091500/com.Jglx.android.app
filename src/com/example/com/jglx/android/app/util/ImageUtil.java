package com.example.com.jglx.android.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Environment;

/**
 * 图形类
 * 
 * @author jjj
 * 
 * @date 2015-8-5
 */
public class ImageUtil {
	public static String filePath = Environment.getExternalStorageDirectory()
			.getPath() + "/LinXin/images/";// 头像存储路径;

	/**
	 * 获取相对路径
	 * 
	 * @param path
	 * @param name
	 * @return
	 */
	public static String bitmap2File(String path, String name) {

		Bitmap bitmap = compressImage(path);
		// Bitmap bitmap = null;
		//
		// BitmapFactory.Options Boptions = new BitmapFactory.Options();
		// Boptions.inJustDecodeBounds = true;
		// BitmapFactory.decodeFile(path, Boptions);
		// Boptions.inSampleSize = calculateInSampleSize(Boptions, 320, 480);
		// Boptions.inPurgeable = true;
		// Boptions.inInputShareable = true;
		// Boptions.inJustDecodeBounds = false;
		//
		// bitmap = BitmapFactory.decodeFile(path, Boptions);
		// int degree = readPictureDegree(path);
		// if (degree != 0) {
		// bitmap = rotaingImageView(degree, bitmap);// 传入压缩后的图片进行旋转
		// }
		if (null == bitmap) {
			return null;
		}
		File dFile = new File(path);
		if (dFile.exists()) {
			dFile.delete();
		}
		File file = null;
		try {
			File pathFile = new File(filePath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			file = new File(filePath, name);
			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();

			FileOutputStream fileOut = new FileOutputStream(file);
			int size = 100;
			if (bitmap.getHeight() > 1000 || bitmap.getWidth() > 1000) {
				size = 80;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, size, fileOut);

			fileOut.flush();
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
	
	public static String bitmap2File2(String path) {
		
		Bitmap bitmap = compressImage(path);
		
		if (null == bitmap) {
			return null;
		}
		File dFile = new File(path);
		if (dFile.exists()) {
			dFile.delete();
		}
		File file = null;
		try {
			File pathFile = new File(filePath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			
			file.createNewFile();
			
			FileOutputStream fileOut = new FileOutputStream(file);
			int size = 100;
			if (bitmap.getHeight() > 1000 || bitmap.getWidth() > 1000) {
				size = 80;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, size, fileOut);
			
			fileOut.flush();
			fileOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片为正方向
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 获得压缩过后的图片
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	public static Bitmap compressImage(String path) {

		if (null == path || path.contains("null")) {
			return null;
		}
		Bitmap bitmap = null;

		BitmapFactory.Options Boptions = new BitmapFactory.Options();
		Boptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, Boptions);
		Boptions.inSampleSize = calculateInSampleSize(Boptions, 320, 480);
		Boptions.inPurgeable = true;
		Boptions.inInputShareable = true;
		Boptions.inJustDecodeBounds = false;

		bitmap = BitmapFactory.decodeFile(path, Boptions);
		int degree = readPictureDegree(path);
		if (degree != 0) {
			bitmap = rotaingImageView(degree, bitmap);// 传入压缩后的图片进行旋转
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int size1 = 100;// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩

			baos.reset();// 重置baos即清空baos
			size1 -= 10;// 每次都减少10
			if (size1 == 0) {
				size1 = 1;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, size1, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		bitmap = BitmapFactory.decodeStream(isBm);
		return bitmap;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
		paint.setColor(color);

		// 以下有两种方法画圆,drawRounRect和drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
		// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

		return output;
	}
	public static void compressBmpToFile(Bitmap bmp,File file){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;//个人喜欢从80开始,
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 100) { 
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
