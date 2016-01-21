package cn.culturemap.pointsmap.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 消息提示工具类
 * @author XP
 * @time   2015年11月13日 下午5:12:59
 */
public class ShowMessageUtils {

	public static void show(Context ctx, String msg) {
		show(ctx, msg, Toast.LENGTH_SHORT);
	}

	public static void showLongTime(Context ctx, String msg) {
		show(ctx, msg, Toast.LENGTH_LONG);
	}

	public static void show(Context ctx, String msg, int duration) {
		Toast.makeText(ctx, msg, duration).show();
	}

	public static void show(Context ctx, int msgId) {
		show(ctx, ctx.getResources().getString(msgId), Toast.LENGTH_SHORT);
	}

	/**
	 * 显示带图片的Toast
	 * @param ctx
	 * @param imgId
	 */
	public static void showWithImg(Context ctx, int imgId) {

		Toast toast = Toast.makeText(ctx, "", Toast.LENGTH_LONG);

		toast.setGravity(Gravity.CENTER, 0, 0);

		ImageView view = new ImageView(ctx);
		view.setImageResource(imgId);

		toast.setView(view);
		toast.show();
	}

}
