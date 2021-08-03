package com.zhongya666.library.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongya666.library.R;

import java.util.ArrayList;
import java.util.List;



/**
 * description:弹窗浮动加载进度条
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingDialog {
    /** 加载数据对话框 */
    private static Dialog mLoadingDialog;
    private  static List<Dialog> dialogs;


    /**
     * 显示加载对话框
     * @param context 上下文
     * @param msg 对话框显示内容
     * @param cancelable 对话框是否可以取消
     */

    public static Dialog showDialogForLoading(Activity context, String msg, boolean cancelable) {
        synchronized (LoadingDialog.class){
        if (dialogs == null) {
            dialogs = new ArrayList<>();
        }
    }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView)view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(msg);

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        dialogs.add(mLoadingDialog);
        return  mLoadingDialog;
    }

    public static Dialog showDialogForLoading(Activity context) {
        synchronized (LoadingDialog.class){
            if (dialogs == null) {
                dialogs = new ArrayList<>();
            }
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView)view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText("加载中...");

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        dialogs.add(mLoadingDialog);
        return  mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public static void cancelDialogForLoading() {
        if (dialogs==null)
            return;
        for (Dialog dialog : dialogs) {
            if (dialog.isShowing()){
                dialog.cancel();

            }
        }

//        if(mLoadingDialog != null) {
//            mLoadingDialog.cancel();
//        }
    }
}
