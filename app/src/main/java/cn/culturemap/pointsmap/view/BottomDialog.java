/**
 * 
 */
package cn.culturemap.pointsmap.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import cn.culturemap.pointsmap.R;
import cn.culturemap.pointsmap.utils.CommonUtils;

/**
 * 由底部弹出的Dialog
 * @author XP
 * @time   2015年11月13日 下午4:44:01
 */
public class BottomDialog {

	public Dialog dialog;

	public BottomDialog(Context context, View view) {

		// 初始化视图
		initDaliogView(context);

		dialog.setContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	
	public BottomDialog(Context context) {
		// 初始化视图
		initDaliogView(context);
	}

	public BottomDialog() {
	}

	/**
	 * 初始化视图
	 */
	public void initDaliogView(Context context) {

		dialog = new Dialog(context, R.style.ButtomDialogTheme);

		Window w = dialog.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		lp.width = CommonUtils.getDisplayWidth(w);
		dialog.onWindowAttributesChanged(lp);
	}

	public void show() {
		if (!dialog.isShowing()) {
			// 最终决定dialog的大小,实际由contentView确定了
			dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			dialog.show();
		}
	}

	/**
	 * 隐藏Dialog
	 */
	public void dismiss() {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
