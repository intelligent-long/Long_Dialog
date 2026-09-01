package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.StringRes;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.LongDialog;
import com.longx.intelligent.android.lib.longdialog.databinding.DialogInputBinding;
import com.longx.intelligent.android.lib.longdialog.util.UiUtil;

/**
 * Created by LONG on 2026/8/24 at 上午2:10.
 */
public class InputDialog extends BaseMessageDialog<InputDialog>{
    private DialogInputBinding binding;
    // Custom Field -----
    protected CharSequence okButtonText;
    private CharSequence cancelButtonText;
    private DialogInterface.OnClickListener okDialogButtonYier;
    private View.OnClickListener okButtonYier;
    private DialogInterface.OnClickListener cancelDialogButtonYier;
    private View.OnClickListener cancelButtonYier;
    private CharSequence text;
    private CharSequence hint;
    // -----

    public InputDialog(Activity activity) {
        super(activity);
    }

    // Custom Method -----
    public InputDialog okButtonText(CharSequence okButtonText) {
        this.okButtonText = okButtonText;
        return this;
    }

    public InputDialog okButtonText(@StringRes int okButtonTextRes) {
        this.okButtonText = getContext().getString(okButtonTextRes);
        return this;
    }

    public InputDialog cancelButtonText(CharSequence cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
        return this;
    }

    public InputDialog cancelButtonText(@StringRes int cancelButtonTextRes) {
        this.cancelButtonText = getContext().getString(cancelButtonTextRes);
        return this;
    }

    public InputDialog okButtonYier(View.OnClickListener okButtonYier) {
        this.okButtonYier = okButtonYier;
        return this;
    }

    public InputDialog okDialogButtonYier(DialogInterface.OnClickListener okDialogButtonYier) {
        this.okDialogButtonYier = okDialogButtonYier;
        return this;
    }

    public InputDialog cancelButtonYier(View.OnClickListener cancelButtonYier) {
        this.cancelButtonYier = cancelButtonYier;
        return this;
    }

    public InputDialog cancelDialogButtonYier(DialogInterface.OnClickListener cancelDialogButtonYier) {
        this.cancelDialogButtonYier = cancelDialogButtonYier;
        return this;
    }

    public InputDialog text(CharSequence text) {
        this.text = text;
        return this;
    }

    public InputDialog text(@StringRes int textRes) {
        this.text = getContext().getString(textRes);
        return this;
    }

    public InputDialog hint(CharSequence hint) {
        this.hint = hint;
        return this;
    }

    public InputDialog hint(@StringRes int hintRes) {
        this.hint = getContext().getString(hintRes);
        return this;
    }

    public String getText() {
        if (binding != null && binding.textInputEditText.getText() != null) {
            return binding.textInputEditText.getText().toString();
        }
        return "";
    }

    public void setError(CharSequence errorMsg) {
        if (binding != null) {
            binding.textInputLayout.setError(errorMsg);
        }
    }
    // -----

    @Override
    protected View onCreateView(LayoutInflater layoutInflater) {
        binding = DialogInputBinding.inflate(layoutInflater);
        return binding.getRoot();
    }

    @Override
    protected void onCreated() {
        super.onCreated();
        if (text != null) {
            binding.textInputEditText.setText(text);
            binding.textInputEditText.setSelection(text.length());
        }
        if (hint != null) {
            binding.textInputLayout.setHint(hint);
        }
    }

    @Override
    protected void onBuild(MaterialAlertDialogBuilder builder) {
        super.onBuild(builder);
        if (this.cancelButtonText != null) {
            builder.setNegativeButton(this.cancelButtonText, cancelDialogButtonYier);
        } else {
            int finalCancelButtonTextRes = LongDialog.cancelButtonText != 0 ?
                    LongDialog.cancelButtonText :
                    android.R.string.cancel;
            builder.setNegativeButton(finalCancelButtonTextRes, cancelDialogButtonYier);
        }
        if (this.okButtonText != null) {
            builder.setPositiveButton(this.okButtonText, okDialogButtonYier);
        }
    }

    @Override
    protected void onShowed() {
        super.onShowed();
        if (message != null && binding != null) {
            binding.getRoot();
            View root = binding.getRoot();
            android.view.ViewParent parent = root.getParent();
            while (parent instanceof ViewGroup) {
                ((ViewGroup) parent).setClipChildren(false);
                parent = parent.getParent();
            }
            ViewGroup.LayoutParams params = root.getLayoutParams();
            if (params instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
                marginParams.topMargin = - UiUtil.dpToPx(context, 30);
                root.setLayoutParams(marginParams);
            }
        }
        if (this.okDialogButtonYier == null && this.okButtonYier != null) {
            Button okButton = getDialog().getButton(DialogInterface.BUTTON_POSITIVE);
            if (okButton != null) {
                okButton.setOnClickListener(v -> {
                    setError(null);
                    okButtonYier.onClick(v);
                });
            }
        }
        if (this.cancelDialogButtonYier == null && this.cancelButtonYier != null) {
            Button cancelButton = getDialog().getButton(DialogInterface.BUTTON_NEGATIVE);
            if (cancelButton != null) {
                cancelButton.setOnClickListener(v -> {
                    cancelButtonYier.onClick(v);
                });
            }
        }
        if (binding != null) {
            binding.textInputEditText.requestFocus();
            binding.textInputEditText.postDelayed(() -> {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(binding.textInputEditText, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 240);
        }
    }

    public DialogInputBinding getBinding() {
        return binding;
    }
}