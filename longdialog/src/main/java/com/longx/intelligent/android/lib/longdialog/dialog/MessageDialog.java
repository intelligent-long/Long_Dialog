package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.LongDialog;

/**
 * Created by LONG on 2026/8/24 at 上午2:07.
 */
public class MessageDialog extends BaseMessageDialog<MessageDialog> {
    // Custom Field -----
    private DialogInterface.OnClickListener okDialogButtonYier;
    private View.OnClickListener okButtonYier;
    // -----

    public MessageDialog(Activity activity) {
        super(activity);
    }

    // Custom Method -----
    public MessageDialog okButtonYier(View.OnClickListener okButtonYier) {
        this.okButtonYier = okButtonYier;
        return this;
    }
    public MessageDialog okDialogButtonYier(DialogInterface.OnClickListener okDialogButtonYier) {
        this.okDialogButtonYier = okDialogButtonYier;
        return this;
    }
    // -----

    @Override
    protected void onBuild(MaterialAlertDialogBuilder builder) {
        super.onBuild(builder);
        if (this.okButtonText != null) {
            builder.setPositiveButton(this.okButtonText, okDialogButtonYier);
        } else {
            int finalOkButtonTextRes = this.okButtonTextRes != 0 ?
                    this.okButtonTextRes :
                    (LongDialog.okButtonText != 0 ? LongDialog.okButtonText : android.R.string.ok);
            builder.setPositiveButton(finalOkButtonTextRes, okDialogButtonYier);
        }
    }

    @Override
    protected void onShowed() {
        super.onShowed();
        if (this.okDialogButtonYier == null && this.okButtonYier != null) {
            Button okButton = getDialog().getButton(DialogInterface.BUTTON_POSITIVE);
            if (okButton != null) {
                okButton.setOnClickListener(okButtonYier);
            }
        }
    }
}