package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.databinding.DialogProgressOperatingBinding;
import com.longx.intelligent.android.lib.longdialog.ui.UiThread;

/**
 * Created by LONG on 2026/8/24 at 上午2:10.
 */
public class ProgressOperatingDialog extends BaseDialog<ProgressOperatingDialog> {
    private DialogProgressOperatingBinding binding;

    public ProgressOperatingDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(LayoutInflater layoutInflater) {
        binding = DialogProgressOperatingBinding.inflate(layoutInflater);
        return binding.getRoot();
    }

    @Override
    protected AlertDialog onCreate(MaterialAlertDialogBuilder builder) {
        AlertDialog dialog = super.onCreate(builder);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    protected void onShowed() {
        super.onShowed();
        if (centered && dialog != null) {
            binding.text.setGravity(Gravity.CENTER);
            binding.text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    public void updateText(String text){
        UiThread.run(() -> {
            binding.text.setText(text);
        });
    }

    public void updateProgress(long current, long total){
        UiThread.run(() -> {
            int progress = (int) ((current / (double) total) * binding.indicator.getMax());
            progress = Math.min(progress, binding.indicator.getMax());
            progress = Math.max(progress, 0);
            binding.indicator.setProgressCompat(progress, true);
        });
    }

    public String getText(){
        return binding.text.getText().toString();
    }

    public int getProgress(){
        return binding.indicator.getProgress();
    }

    public DialogProgressOperatingBinding getBinding() {
        return binding;
    }
}
