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
 * Created by LONG on 2026/8/24 at 上午3:13.
 */
public class OptionDialog extends BaseMessageDialog<OptionDialog> {
    // Custom Field -----
    private CharSequence negativeButtonText;
    private DialogInterface.OnClickListener negativeDialogButtonYier;
    private View.OnClickListener negativeButtonYier;
    private CharSequence positiveButtonText;
    private DialogInterface.OnClickListener positiveDialogButtonYier;
    private View.OnClickListener positiveButtonYier;
    private CharSequence neutralButtonText;
    private DialogInterface.OnClickListener neutralDialogButtonYier;
    private View.OnClickListener neutralButtonYier;
    // -----

    public OptionDialog(Activity activity) {
        super(activity);
    }

    // Custom Method -----
    public OptionDialog positiveButtonText(CharSequence positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
        return this;
    }

    public OptionDialog positiveButtonText(@StringRes int positiveButtonTextRes) {
        this.positiveButtonText = getContext().getString(positiveButtonTextRes);
        return this;
    }

    public OptionDialog negativeButtonText(CharSequence negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
        return this;
    }

    public OptionDialog negativeButtonText(@StringRes int negativeButtonTextRes) {
        this.negativeButtonText = getContext().getString(negativeButtonTextRes);
        return this;
    }

    public OptionDialog neutralButtonText(CharSequence neutralButtonText) {
        this.neutralButtonText = neutralButtonText;
        return this;
    }

    public OptionDialog neutralButtonText(@StringRes int neutralButtonTextRes) {
        this.neutralButtonText = getContext().getString(neutralButtonTextRes);
        return this;
    }

    public OptionDialog positiveButtonYier(View.OnClickListener positiveButtonYier) {
        this.positiveButtonYier = positiveButtonYier;
        return this;
    }

    public OptionDialog positiveDialogButtonYier(DialogInterface.OnClickListener positiveDialogButtonYier) {
        this.positiveDialogButtonYier = positiveDialogButtonYier;
        return this;
    }

    public OptionDialog negativeButtonYier(View.OnClickListener negativeButtonYier) {
        this.negativeButtonYier = negativeButtonYier;
        return this;
    }

    public OptionDialog negativeDialogButtonYier(DialogInterface.OnClickListener negativeDialogButtonYier) {
        this.negativeDialogButtonYier = negativeDialogButtonYier;
        return this;
    }

    public OptionDialog neutralButtonYier(View.OnClickListener neutralButtonYier) {
        this.neutralButtonYier = neutralButtonYier;
        return this;
    }

    public OptionDialog neutralDialogButtonYier(DialogInterface.OnClickListener neutralDialogButtonYier) {
        this.neutralDialogButtonYier = neutralDialogButtonYier;
        return this;
    }
    // -----

    @Override
    protected void onBuild(MaterialAlertDialogBuilder builder) {
        super.onBuild(builder);
        if (this.positiveButtonText != null || this.positiveDialogButtonYier != null || this.positiveButtonYier != null) {
            CharSequence finalPositiveText = this.positiveButtonText != null ? this.positiveButtonText :
                    (this.okButtonText != null ? this.okButtonText :
                            getContext().getString(LongDialog.okButtonText != 0 ? LongDialog.okButtonText : android.R.string.ok));
            DialogInterface.OnClickListener listener = this.positiveDialogButtonYier != null ? this.positiveDialogButtonYier : null;
            builder.setPositiveButton(finalPositiveText, listener);
        }
        if (this.negativeButtonText != null || this.negativeDialogButtonYier != null || this.negativeButtonYier != null) {
            CharSequence finalNegativeText = this.negativeButtonText != null ? this.negativeButtonText :
                    getContext().getString(LongDialog.cancelButtonText != 0 ? LongDialog.cancelButtonText : android.R.string.cancel);
            DialogInterface.OnClickListener listener = this.negativeDialogButtonYier != null ? this.negativeDialogButtonYier : null;
            builder.setNegativeButton(finalNegativeText, listener);
        }
        if (this.neutralButtonText != null || this.neutralDialogButtonYier != null || this.neutralButtonYier != null) {
            CharSequence finalNeutralText = this.neutralButtonText != null ? this.neutralButtonText :
                    getContext().getString(LongDialog.neutralButtonText != 0 ? LongDialog.neutralButtonText : R.string.long_dialog_default_neutral_text);
            DialogInterface.OnClickListener listener = this.neutralDialogButtonYier != null ? this.neutralDialogButtonYier : null;
            builder.setNeutralButton(finalNeutralText, listener);
        }
    }

    @Override
    protected void onShowed() {
        super.onShowed();
        if (this.positiveDialogButtonYier == null && this.positiveButtonYier != null) {
            Button positiveButton = getDialog().getButton(DialogInterface.BUTTON_POSITIVE);
            if (positiveButton != null) {
                positiveButton.setOnClickListener(positiveButtonYier);
            }
        }
        if (this.negativeDialogButtonYier == null && this.negativeButtonYier != null) {
            Button negativeButton = getDialog().getButton(DialogInterface.BUTTON_NEGATIVE);
            if (negativeButton != null) {
                negativeButton.setOnClickListener(negativeButtonYier);
            }
        }
        if (this.neutralDialogButtonYier == null && this.neutralButtonYier != null) {
            Button neutralButton = getDialog().getButton(DialogInterface.BUTTON_NEUTRAL);
            if (neutralButton != null) {
                neutralButton.setOnClickListener(neutralButtonYier);
            }
        }
    }
}