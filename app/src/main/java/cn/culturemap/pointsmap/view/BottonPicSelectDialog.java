package cn.culturemap.pointsmap.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import cn.culturemap.pointsmap.R;
import cn.culturemap.pointsmap.utils.CommonUtils;

/**
 * 图片选择Dialog
 * @author XP
 * @time   2015年11月13日 下午4:47:16
 */
public class BottonPicSelectDialog extends BottomDialog {

	// 请求相机
	public static final int REQUEST_CAMARA_CODE = 1001;
	// 请求相册
	public static final int REQUEST_ALBAME_CODE = 1002;
	// 请求切割图片
	public static final int REQUEST_CUT_CODE = 1006;

	Context context;
	View view;
	
	String photoSaveDir;
	private File mCurrentPhotoFile;

	public BottonPicSelectDialog(Context context, String photoSaveDir, File mCurrentPhotoFile) {
		super(context);
		this.context = context;
		this.photoSaveDir = photoSaveDir;
		this.mCurrentPhotoFile = mCurrentPhotoFile;

		// 初始化视图
		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {

		view = LayoutInflater.from(context).inflate(
				R.layout.pic_select_dialog_layout, null);

		// 相册选择
		Button albameSelect = (Button) view.findViewById(R.id.picSelectAlbam);
		albameSelect.setOnClickListener(albameSelectClick);
		// 相机选择
		Button camaraSelect = (Button) view.findViewById(R.id.picSelectCamara);
		camaraSelect.setOnClickListener(camaraSelectClick);
		// 取消按钮
		Button cancleSelect = (Button) view.findViewById(R.id.picSelectCancle);
		cancleSelect.setOnClickListener(cancleSelectClick);

		// 添加View到视图
		dialog.setContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 相册选择
	 */
	OnClickListener albameSelectClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
			openAlbumIntent.setType("image/*");
			CommonUtils.startActivityForResult(context, openAlbumIntent, REQUEST_ALBAME_CODE);

			// 取消Dialog显示
			dismiss();
		}
	};

	/**
	 * 相机选择
	 */
	OnClickListener camaraSelectClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// 开始照相 
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
			CommonUtils.startSubActivityForResult(context, intent, REQUEST_CAMARA_CODE);

			// 取消Dialog显示
			dismiss();
		}
	};
	
	/*
	 * 取得拍照的文件名称
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 取消按钮
	 */
	OnClickListener cancleSelectClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 取消Dialog显示
			dismiss();
		}
	};

}
