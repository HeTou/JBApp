package com.programmer.jbapp.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.programmer.jbapp.R;

/**
 * zft
 * 2017/2/22.
 */

public class MenuDialog {
    private Context mContext;
    private Dialog mMenuDialog;


    public MenuDialog(Context context) {
        this.mContext = context;
        initUI();
    }

    private void initUI() {
        mMenuDialog = new Dialog(mContext, R.style.DialogTheme);
        mMenuDialog.setCanceledOnTouchOutside(true);       //点击外部不取消
        Window window = mMenuDialog.getWindow();
        window.setContentView(R.layout.dialog_menu);
        window.setWindowAnimations(R.style.DialogAnimation);
        WindowManager.LayoutParams lp = window.getAttributes();
        if(lp!=null){
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            lp.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels*0.8f);
            lp.gravity = Gravity.RIGHT;
        }
        window.setAttributes(lp);
    }

    public void show(){
        mMenuDialog.show();
    }
}
