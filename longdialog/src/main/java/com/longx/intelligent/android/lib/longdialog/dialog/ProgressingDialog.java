package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.databinding.DialogOperatingBinding;

/**
 * Created by LONG on 2026/8/24 at 上午2:06.
 */
public class ProgressingDialog extends BaseDialog<ProgressingDialog>{
    private DialogOperatingBinding binding;

    public ProgressingDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(LayoutInflater layoutInflater) {
        binding = DialogOperatingBinding.inflate(layoutInflater);
        return binding.getRoot();
    }

    @Override
    protected AlertDialog onCreate(MaterialAlertDialogBuilder builder) {
        AlertDialog dialog = super.onCreate(builder);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public DialogOperatingBinding getBinding() {
        return binding;
    }
}
