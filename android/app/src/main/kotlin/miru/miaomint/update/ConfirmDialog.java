package miru.miaomint.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import miru.miaomint.js.R;

public class ConfirmDialog {
    private Context context;
    private OnClickListener listener;

    private Dialog dialog;
    private TextView tvTitle;
    private TextView tvContent;
    private Button btnCancel;
    private Button btnConfirm;

    public ConfirmDialog(Context context, OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_confirm, null);

        initView(layout);

        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);
    }

    private void initView(View layout) {
        tvTitle = layout.findViewById(R.id.txt_title);
        tvContent = layout.findViewById(R.id.txt_content);

        btnCancel = layout.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(view -> dialog.dismiss());
        btnConfirm = layout.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(view -> {
            if (listener != null) {
                listener.onConfirm();
            }
            dialog.dismiss();
        });
    }

    public void setTitle(String text) {
        tvTitle.setText(text);
        tvTitle.setVisibility(View.VISIBLE);
    }

    public void setTitle(int resid) {
        tvTitle.setText(resid);
        tvTitle.setVisibility(View.VISIBLE);
    }

    public void setContent(String text) {
        tvContent.setText(text);
    }

    public void setContent(int resid) {
        tvContent.setText(resid);
    }

    public void close() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public interface OnClickListener {
        public void onConfirm();
    }

    public void setConfirmText(int resId) {
        btnConfirm.setText(resId);
    }

    public void setCancelText(int resId) {
        btnCancel.setText(resId);
    }
}