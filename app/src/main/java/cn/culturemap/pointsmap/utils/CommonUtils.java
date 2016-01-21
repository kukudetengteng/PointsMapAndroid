/**
 * 
 */
package cn.culturemap.pointsmap.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.Window;

/**
 * 公共服务类
 * @author XP
 * @time   2015年11月13日 下午4:45:07
 */
public class CommonUtils {
	
	public final static String PIC_PNG = "png";
	public final static String PIC_JPEG = "jpeg";
	public final static String PIC_JPG = "jpg";
	public final static String PIC_WEBP = "webp";

	/********************* startActivity ******************************/
	public static void startActivity(Context ctx, Class<?> clazz) {
		Intent main = new Intent(ctx, clazz);
		ctx.startActivity(main);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startSubActivity(Context ctx, Intent intent) {
		ctx.startActivity(intent);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startSubActivity(Context ctx, Class<?> clazz) {
		Intent main = new Intent(ctx, clazz);
		ctx.startActivity(main);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startSubActivity(Context ctx, String action) {
		Intent main = new Intent(action);
		ctx.startActivity(main);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	/********************* startActivityForResult ******************************/
	public static void startActivityForResult(Context ctx, Class<?> clazz, int code) {
		Intent main = new Intent(ctx, clazz);
		((Activity) ctx).startActivityForResult(main, code);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startSubActivityForResult(Context ctx, Intent intent, int code) {
		((Activity) ctx).startActivityForResult(intent, code);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startSubActivityForResult(Context ctx, Class<?> clazz, int code) {
		Intent main = new Intent(ctx, clazz);
		((Activity) ctx).startActivityForResult(main, code);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startSubActivityForResult(Context ctx, String action, int code) {
		Intent main = new Intent(action);
		((Activity) ctx).startActivityForResult(main, code);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startActivityForResult(Context ctx, Class<?> clazz, String key, Serializable obj, int code) {
		Intent main = new Intent(ctx, clazz);
		main.putExtra(key, obj);
		((Activity) ctx).startActivityForResult(main, code);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public static void startActivityForResult(Context ctx, Intent in, int code) {
		((Activity) ctx).startActivityForResult(in, code);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

	}

	/** 从相册选择跳转 **/
	public static void startAlbumActivityForResult(Context ctx, int code) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		((Activity) ctx).startActivityForResult(Intent.createChooser(intent, "Select Picture"), code);

		((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	/********************* Other Method ******************************/
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	// 判断GPS是否可用
	public static boolean isGpsAvailable(Context context) {
		LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders = locationManager.getProviders(true);

		if (accessibleProviders != null && accessibleProviders.size() > 0) {
			return true;
		}
		return false;
	}

	// wifi是否打开
	public static boolean isWifiAvailable(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		return ((mgrConn.getActiveNetworkInfo() != null
				&& mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
				|| mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	/*
	 * 打电话
	 */
	public static void callPhone(Context context, String phoneNum) {

		if (StringUtils.isBlank(phoneNum)) {
			return;
		}

		// 打电话
		Intent myIntentDial = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNum));

		context.startActivity(myIntentDial);
	}
	
	/*
	 * 存储Bitmap对象到SD卡 支持文件格式： PNG（默认） JPEG JPG WEBP
	 */
	public static void saveBitmapInSdCard(Bitmap bitmap, String pathAndFileName) {
		File file = new File(pathAndFileName);
		FileOutputStream output = null;

		try {
			output = new FileOutputStream(file);

			String picFormat = pathAndFileName.substring(
					pathAndFileName.lastIndexOf(".") - 1,
					pathAndFileName.length());

			Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;

			// JPEG, JPG格式
			if (picFormat.equalsIgnoreCase(PIC_JPEG)
					|| picFormat.equalsIgnoreCase(PIC_JPG)) {
				compressFormat = Bitmap.CompressFormat.JPEG;
			}

			Log.d("debug", picFormat);

			bitmap.compress(compressFormat, 100, output);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 取得屏幕高度
	 * 
	 *  ctx
	 * @return
	 */
	public static int getDisplayWidth(Window dialogWindow) {
		int width = 0;

		// 获取屏幕宽、高用
		Display d = dialogWindow.getWindowManager().getDefaultDisplay();
		width = d.getWidth();

		return width;
	}
}
