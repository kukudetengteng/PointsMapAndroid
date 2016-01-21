/**
 * 
 */
package cn.culturemap.pointsmap.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

/**
 * 图片选择---工具类
 * @author XP
 * @time   2015年11月13日 下午5:11:03
 */
public class PhotoSelectUtil {
	
	// 请求切割图片
		public static final int REQUEST_CUT_CODE = 1006;

	/**
	 * 将进行剪裁后的图片显示到UI界面上
	 * 
	 * @param picdata
	 * @return
	 */
	public static Bitmap getPic(Intent picdata) {
		Bitmap photo = null;
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			photo = bundle.getParcelable("data");
		}
		return photo;
	}

	/**
	 * 使用系统当前日期加以调整作为照片的名称
	 * 
	 * @return
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 转换图片的大小
	 * 
	 * @return
	 */
	public static Bitmap changePostItemImgSize(Context context, Bitmap source) {

		// 屏幕的1/8
		int displayWidth = (int) (CommonUtils
				.getDisplayWidth(((Activity) context).getWindow()) * 0.9);

		Bitmap trBitmap = null;

		int source_w = source.getWidth();
		int source_h = source.getHeight();

		// 图片需要压缩
		if (source_w > displayWidth) {
			trBitmap = ThumbnailUtils.extractThumbnail(source, displayWidth,
					source_h * displayWidth / source_w);
		} else {
			trBitmap = ThumbnailUtils.extractThumbnail(source, source_w,
					source_h);
		}

		return trBitmap;
	}
	
	/**
	 * 通过降低图片的质量来压缩图片
	 * 
	 *            要压缩的图片
	 * @param maxSize
	 *            压缩后图片大小的最大值,单位KB
	 * @return 压缩后的图片
	 */
	public static Bitmap compressByQuality(Bitmap bitmap, int maxSize) {
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        int quality = 100;  
	        bitmap.compress(CompressFormat.JPEG, quality, baos);  
	        System.out.println("图片压缩前大小：" + baos.toByteArray().length + "byte");  
	        while (baos.toByteArray().length / 1024 > maxSize) {  
	            quality -= 10;  
	            baos.reset();  
	            bitmap.compress(CompressFormat.JPEG, quality, baos);  
	            System.out.println("质量压缩到原来的" + quality + "%时大小为："  
	                    + baos.toByteArray().length + "byte");  
	        }  
	        System.out.println("图片压缩后大小：" + baos.toByteArray().length + "byte");  
	        bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,  
	                baos.toByteArray().length);  
	        return bitmap;
	}

	/**
	 * 通过压缩图片的尺寸来压缩图片大小
	 * 
	 * @param pathName
	 *            图片的完整路径
	 * @param targetWidth
	 *            缩放的目标宽度
	 * @param targetHeight
	 *            缩放的目标高度
	 * @return 缩放后的图片
	 */
	public static Bitmap compressBySize(String pathName, int targetWidth,
			int targetHeight) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
		Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
		// 得到图片的宽度、高度；
		int imgWidth = opts.outWidth;
		int imgHeight = opts.outHeight;
		
		double scale;
		
		if(imgWidth > imgHeight) {
			
			scale = (double)targetWidth / (double)imgWidth;
			int itemHeight = (int)(scale * imgHeight);
			
			bitmap = ThumbnailUtils.
					extractThumbnail(bitmap, targetWidth, itemHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
		} else {
			
			scale = (double)targetWidth / (double)imgHeight;
			int itemWidth = (int)(scale * imgWidth);
			
			bitmap = ThumbnailUtils.
					extractThumbnail(bitmap, itemWidth, targetHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
		}
		
//		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
//		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
//		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
//		if (widthRatio > 1 || widthRatio > 1) {
//			if (widthRatio > heightRatio) {
//				opts.inSampleSize = get2Index(widthRatio);
//			} else {
//				opts.inSampleSize = get2Index(heightRatio);
//			}
//		}
//		// 设置好缩放比例后，加载图片进内容；
//		opts.inJustDecodeBounds = false;
//		bitmap = BitmapFactory.decodeFile(pathName, opts);
		return bitmap;
	}
	
//	/**
//	 * 通过压缩图片的尺寸来压缩图片大小
//	 * 
//	 * @param pathName
//	 *            图片的完整路径
//	 * @param targetWidth
//	 *            缩放的目标宽度
//	 * @param targetHeight
//	 *            缩放的目标高度
//	 * @return 缩放后的图片
//	 */
//	public static Bitmap compressBySize2(String pathName, int targetWidth,
//			int targetHeight) {
//		
//	}
	
	/**
	 * 取得最接近参数的2的指数
	 * @return
	 */
	private static int get2Index(int value) {
		
		int valueR = value;
		
		switch (value) {
		case 2:
			valueR = 2;
			break;
			
		case 3:
			valueR = 4;
			break;
			
		case 4:
			valueR = 4;
			break;
			
		case 5:
			valueR = 4;
			break;
			
		case 6:
			valueR = 8;
			break;
			
		case 7:
			valueR = 8;
			break;
			
		case 8:
			valueR = 8;
			break;
			
		case 9:
			valueR = 8;
			break;
			
		case 10:
			valueR = 8;
			break;

		default:
			valueR = value;
			break;
		}
		return valueR;
	}

	/**
	 * 通过压缩图片的尺寸来压缩图片大小
	 * 
	 * @param bitmap
	 *            要压缩图片
	 * @param targetWidth
	 *            缩放的目标宽度
	 * @param targetHeight
	 *            缩放的目标高度
	 * @return 缩放后的图片
	 */
	public static Bitmap compressBySize(Bitmap bitmap, int targetWidth,
			int targetHeight) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
				baos.toByteArray().length, opts);
		// 得到图片的宽度、高度；
		int imgWidth = opts.outWidth;
		int imgHeight = opts.outHeight;
		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		if (widthRatio > 1 && widthRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内存；
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
				baos.toByteArray().length, opts);
		return bitmap;
	}
	
	/**
	 * 通过压缩图片的尺寸来压缩图片大小，通过读入流的方式，可以有效防止网络图片数据流形成位图对象时内存过大的问题；
	 * 
	 * InputStream
	 *      要压缩图片，以流的形式传入
	 * targetWidth
	 *      缩放的目标宽度
	 * targetHeight
	 *      缩放的目标高度
	 * 缩放后的图片
	 * @throws IOException
	 *      读输入流的时候发生异常
	 */
	public static Bitmap compressBySize(InputStream is, int targetWidth,
			int targetHeight) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int len = 0;
		while ((len = is.read(buff)) != -1) {
			baos.write(buff, 0, len);
		}

		byte[] data = baos.toByteArray();
 		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		// 得到图片的宽度、高度；
		int imgWidth = opts.outWidth;
		int imgHeight = opts.outHeight;
		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		if (widthRatio > 1 && widthRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内存；
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		return bitmap;
	}
	
	public static void startPhotoZoom(Context context, Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		((Activity) context).startActivityForResult(intent, REQUEST_CUT_CODE);
	}
}
