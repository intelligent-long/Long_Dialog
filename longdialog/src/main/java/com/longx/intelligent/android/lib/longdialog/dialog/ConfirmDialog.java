package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import androidx.annotation.StringRes;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.LongDialog;
import com.longx.intelligent.android.lib.longdialog.R;

/**
 * Created by LONG on 2026/8/24 at 上午2:07.
 */
public class ConfirmDialog extends BaseMessageDialog<ConfirmDialog> {
    // Custom Field -----
    private CharSequence cancelButtonText;
    private DialogInterface.OnClickListener okDialogButtonYier;
    private View.OnClickListener okButtonYier;
    private DialogInterface.OnClickListener cancelDialogButtonYier;
    private View.OnClickListener cancelButtonYier;
    // -----

    public ConfirmDialog(Activity activity) {
        super(activity);
    }

    // Custom Method -----
    public ConfirmDialog cancelButtonText(CharSequence cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
        return this;
    }

    public ConfirmDialog cancelButtonText(@StringRes int cancelButtonTextRes) {
        this.cancelButtonText = getContext().getString(cancelButtonTextRes);
        return this;
    }

    public ConfirmDialog okButtonYier(View.OnClickListener okButtonYier) {
        this.okButtonYier = okButtonYier;
        return this;
    }

    public ConfirmDialog okDialogButtonYier(DialogInterface.OnClickListener okDialogButtonYier) {
        this.okDialogButtonYier = okDialogButtonYier;
        return this;
    }

    public ConfirmDialog cancelButtonYier(View.OnClickListener cancelButtonYier) {
        this.cancelButtonYier = cancelButtonYier;
        return this;
    }

    public ConfirmDialog cancelDialogButtonYier(DialogInterface.OnClickListener cancelDialogButtonYier) {
        this.cancelDialogButtonYier = cancelDialogButtonYier;
        return this;
    }
    // -----

    @Override
    protected void onBuild(MaterialAlertDialogBuilder builder) {
        super.onBuild(builder);
        if (this.message == null) {
            int defaultConfirmTextRes = LongDialog.confirmText != 0 ?
                    LongDialog.confirmText :
                    R.string.long_dialog_default_confirm_message;
            builder.setMessage(defaultConfirmTextRes);
        }
        if (this.cancelButtonText != null) {
            builder.setNegativeButton(this.cancelButtonText, cancelDialogButtonYier);
        } else {
            int finalCancelButtonTextRes = LongDialog.cancelButtonText != 0 ?
                    LongDialog.cancelButtonText :
                    android.R.string.cancel;
            builder.setNegativeButton(finalCancelButtonTextRes, cancelDialogButtonYier);
        }
        if (okDialogButtonYier != null) {
            if (this.okButtonText != null) {
                builder.setPositiveButton(this.okButtonText, okDialogButtonYier);
            } else {
                int finalOkButtonTextRes = LongDialog.okButtonText != 0 ?
                        LongDialog.okButtonText :
                        android.R.string.ok;
                builder.setPositiveButton(finalOkButtonTextRes, okDialogButtonYier);
            }
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
        if (this.cancelDialogButtonYier == null && this.cancelButtonYier != null) {
            Button cancelButton = getDialog().getButton(DialogInterface.BUTTON_NEGATIVE);
            if (cancelButton != null) {
                cancelButton.setOnClickListener(cancelButtonYier);
            }
        }
    }
}