/**
 * 
 */
package cn.culturemap.pointsmap.activity.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.ResponseInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import cn.culturemap.pointsmap.R.id;
import cn.culturemap.pointsmap.R.layout;
import cn.culturemap.pointsmap.R.string;
import cn.culturemap.pointsmap.pojo.Point;
import cn.culturemap.pointsmap.utils.BaseHttpClient;
import cn.culturemap.pointsmap.utils.CommonUtils;
import cn.culturemap.pointsmap.utils.ConfigUtils;
import cn.culturemap.pointsmap.utils.PhotoSelectUtil;
import cn.culturemap.pointsmap.utils.ShowMessageUtils;
import cn.culturemap.pointsmap.utils.StringUtils;
import cn.culturemap.pointsmap.view.BottonPicSelectDialog;

/**
 * 设置图片
 * @author XP
 * @time   2015年11月13日 下午6:11:22
 */
public class SetPicActivity extends ActionBarActivity {
	
	public static final int UPLOAD_OK = 998;
	
	public static final String POINT_KEY = "POINT_KEY";
	
	ImageView picShow;
	
	// 名称
	EditText name;
	// 电话
	EditText tel;
	// 描述
	EditText desc;
	// 地址
	EditText add;
	
	// 图片选择Dialog
	BottonPicSelectDialog picWayDialog;
	
	// 切割图片的大小
	private int cut_path = 250;
	String cutImgPath = "";
	private Uri photoUri;
	private File mCurrentPhotoFile;
	private String photoSaveDir;
	
	// 兴趣点
	Point point;
	
	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_set_pic);
		
		// 初始化数据
		initData();
		
		// 初始化View
		initView();
		
		ActionBar mActionBar = getSupportActionBar();
        // 将 Home 按钮显示为向上， 提示用户点击这个按钮可以返回应用程序的上一级。  
        mActionBar.setDisplayHomeAsUpEnabled(true);  
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	protected static final int MENU_COMPLET = Menu.FIRST;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    //添加菜单项
	    MenuItem nextMenu = menu.add(0,MENU_COMPLET,0,"完成");
//	    nextMenu.setIcon(R.drawable.next_icon);
	    //绑定到ActionBar 
	    nextMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == MENU_COMPLET) {
			// 完成事件
			completeAction();
		}
		
		if (id == android.R.id.home) {
			onBackPressed();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		point = (Point) getIntent().getSerializableExtra(POINT_KEY);
		
		photoSaveDir = getExternalFilesDir("/DCIM/Camera").getAbsolutePath();
		mCurrentPhotoFile = new File(photoSaveDir, getPhotoFileName());
	}
	
	/**
	 * 初始化View
	 */
	public void initView() {
		
		progressDialog = new ProgressDialog(this);
		
		picShow = (ImageView) findViewById(id.picShow);
		picShow.setOnClickListener(picShowClick);
		
		name = (EditText) findViewById(id.name);
		name.setText(point.getName());
		tel = (EditText) findViewById(id.tel);
		tel.setText(point.getTel());
		desc = (EditText) findViewById(id.desc);
		desc.setText(point.getDesc());
		add = (EditText) findViewById(id.add);
		add.setText(point.getAdd());
	}
	
	/**
	 * 完成
	 */
	public void completeAction() {
		
			if (check()) {
				
				progressDialog.show();
				
				try{
					String url = getString(string.url_add_map_point);
					
					Map<String, String> params = new HashMap<String, String>();
					
					params.put("name", point.getName());
					params.put("tel", point.getTel());
					params.put("des", point.getDesc());
					params.put("add", point.getAdd());
					params.put("add_lat", point.getLat());
					params.put("add_lng", point.getLng());
					params.put("app_id", getString(string.app_id));
					params.put("province_id", point.getProvice());
					params.put("city_id", point.getCity());

					List<String> fileKeys = new ArrayList<String>();
					fileKeys.add(mCurrentPhotoFile.getName());
					
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(cutImgPath);
					
					// 上传
					BaseHttpClient.doPostRequestWithFile(url, params, fileKeys, filePaths, callBack);
					
				}catch(Exception e){
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						public void run() {
							ShowMessageUtils.show(SetPicActivity.this, "上传失败！");
						}
					});
				}finally{
					progressDialog.dismiss();
				}
			}
	}
	
	/**
	 * 上传回调
	 */
	RequestCallBack<String> callBack = new RequestCallBack<String>() {
		
		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			progressDialog.dismiss();
			ShowMessageUtils.show(SetPicActivity.this, "上传成功！");
			setResult(UPLOAD_OK);
			finish();
		}
		
		@Override
		public void onFailure(HttpException arg0, String arg1) {
			progressDialog.dismiss();
			ShowMessageUtils.show(SetPicActivity.this, "上传失败！");
		}
	};
	
	/**
	 * 校验
	 * @return
	 */
	public boolean check() {
		if (StringUtils.isBlank(cutImgPath)) {
			ShowMessageUtils.show(this, "请选择图片！");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 图片选择
	 */
	OnClickListener picShowClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (picWayDialog == null) {
				picWayDialog = new BottonPicSelectDialog(SetPicActivity.this, photoSaveDir, mCurrentPhotoFile);
			}
			
			picWayDialog.show();
		}
	};
	
	/**
	 * 切换照片
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 正常返回
		if (resultCode == Activity.RESULT_OK) {

			// 图片选择
			if (requestCode == BottonPicSelectDialog.REQUEST_CAMARA_CODE
					|| requestCode == BottonPicSelectDialog.REQUEST_ALBAME_CODE) {
				
				// 选择图片后，获取图片的路径
				Uri uri = doPhoto(requestCode, data);
				
				if(uri != null) {
					PhotoSelectUtil.startPhotoZoom(this, uri, cut_path);
				}
			}

			// 切割图片
			if (requestCode == PhotoSelectUtil.REQUEST_CUT_CODE) {
				Bitmap pic = PhotoSelectUtil.getPic(data);
				// 设置图片
				picShow.setImageBitmap(pic);
				
				cutImgPath = ConfigUtils.getBuickCacheDir(this) + "/" + PhotoSelectUtil.getPhotoFileName();
				// 存储到SD卡
				CommonUtils.saveBitmapInSdCard(pic, cutImgPath);
			}
		}
	}
	
	/**
	 * 选择图片后，获取图片的路径
	 * 
	 * @param requestCode
	 * @param data
	 */
	private Uri doPhoto(int requestCode, Intent data) {
		// 从相册取图片，有些手机有异常情况，请注意
		if (requestCode == BottonPicSelectDialog.REQUEST_ALBAME_CODE) {
			if (data == null) {
				ShowMessageUtils.show(this, "选择图片文件出错");
				return null;
			}
			photoUri = data.getData();
			if (photoUri == null) {
				ShowMessageUtils.show(this, "选择图片文件出错");
				return null;
			}
		}
		
		// 拍照处理
		else if(requestCode == BottonPicSelectDialog.REQUEST_CAMARA_CODE) {
			Uri imgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			ContentResolver cr = getContentResolver();

			Uri fileUri = Uri.fromFile(mCurrentPhotoFile);
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
					fileUri));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Cursor cursor = cr.query(imgUri, null,
					MediaStore.Images.Media.DISPLAY_NAME + "='"
							+ mCurrentPhotoFile.getName() + "'", null, null);
			Uri uri = null;
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToLast();
				long id = cursor.getLong(0);
				uri = ContentUris.withAppendedId(imgUri, id);
			}
			
			String picPath = photoSaveDir + "/" + mCurrentPhotoFile.getName();
			
			photoUri = Uri.fromFile(new File(picPath));
		}
		
		return photoUri;
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

		((Activity) context).startActivityForResult(intent, BottonPicSelectDialog.REQUEST_CUT_CODE);
	}
	
	/*
	 * 取得拍照的文件名称
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
}
