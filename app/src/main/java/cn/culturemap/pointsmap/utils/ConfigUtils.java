package cn.culturemap.pointsmap.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

/**
 * 文件工具类
 * @author XP
 * @time   2015年11月13日 下午5:13:55
 */
public class ConfigUtils {

	public final static int OK = 0;

	public final static int NG = 1;

	public final static String BUICK_ROOT = "download";


	public static String getBuickRootDir(Context ctx) {
		
		if( existSDCard() ) {
			return ctx.getExternalFilesDir(BUICK_ROOT).getAbsolutePath();
		}
		return null;
	}
	/**
	 * 返回指定path的路径
	 * 
	 * path=/handbook
	 * 返回{/mnt/sdcard/Android/data/com.buick/files}/download/handbook
	 * 
	 * @param ctx
	 * @param path
	 * @return
	 */
	public static String getBuickRootDir(Context ctx, String path) {
		
		if( existSDCard() ) {
			return ctx.getExternalFilesDir(BUICK_ROOT + path).getAbsolutePath();
		}
		return null;
	}

	/**
	 * 返回缓存跟目录路径
	 * 
	 * 返回/mnt/sdcard/Android/data/com.buick/cache
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getBuickCacheDir(Context ctx) {
		
		if( existSDCard() ) {
			return ctx.getExternalCacheDir().getAbsolutePath();
//			return ctx.getExternalFilesDir("cache").getAbsolutePath();
		}
		return null;
	}
	/**
	 * 返回指定path的缓存路径
	 * 
	 * path=/handbook
	 * 返回/mnt/sdcard/Android/data/com.buick/cache/handbook
	 * 
	 * @param ctx
	 * @param path
	 * @return
	 */
	public static String getBuickCacheDir(Context ctx, String path) {
		
		if( existSDCard() ) {
			return ctx.getExternalFilesDir("cache" + path).getAbsolutePath();
		}
		return null;
	}

	/**
	 * 取得配置信息
	 * 
	 * ctx
	 * @return
	 */
	public static Properties getConfig(String path) {
		
		if( !existSDCard() ) {
			return null;
		}
		
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(new File(path));
			props.load(in);
			return props;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if( null != in ) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * SD卡路径
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getCategoryDir(Context ctx, String category) {
		if( existSDCard() ) {
			return ctx.getExternalFilesDir(BUICK_ROOT + category).getAbsolutePath();
		}
		return null;
	}
	
	public static int saveConfig(Context ctx, Properties config, String toPath) {
		
		if( !existSDCard() ) {
			return NG;
		}
			
		if(null != config && StringUtils.isNotEmpty(toPath)) {
			try {
				FileOutputStream out = new FileOutputStream(toPath);
				
				config.store(out, null);
	
				out.flush();
				out.close();
				
				return OK;
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
		}
		return NG;
	}
	

	public static boolean existSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} 
		return false;
	}
	
	public static boolean existSDCard(Context ctx) {
		if(existSDCard()) {
			return true;
		}
		return false;
	}
	
	/*
	 * 删除文件 
	 */
	public static void deleteFile(String filePath) {
		
		if(filePath == null) {
			return;
		}
		
		File file = new File(filePath);
		
		if(file.exists()) {
			file.delete();
		}
	}
	
	/*
	 * 文件存在判断 
	 */
	public static boolean existFile(String filePath) {
		File file = new File(filePath);
		
		return file.exists();
	}
	
}
