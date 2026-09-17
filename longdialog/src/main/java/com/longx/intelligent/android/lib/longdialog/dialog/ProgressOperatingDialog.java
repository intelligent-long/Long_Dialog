package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.databinding.DialogProgressOperatingBinding;
import com.longx.intelligent.android.lib.longdialog.ui.UiThread;
import com.longx.intelligent.android.lib.longdialog.util.ThemeHelper;
import com.longx.intelligent.android.lib.longdialog.util.UiUtil;

/**
 * Created by LONG on 2026/8/24 at 上午2:10.
 */
public class ProgressOperatingDialog extends BaseDialog<ProgressOperatingDialog> {
    private DialogProgressOperatingBinding binding;
    // Custom Field -----
    protected CharSequence title;
    protected int iconResId;
    protected Drawable iconDrawable;
    // -----

    public ProgressOperatingDialog(Activity activity) {
        super(activity);
    }

    // Custom Method -----
    public ProgressOperatingDialog title(CharSequence title) {
        this.title = title;
        return this;
    }

    public ProgressOperatingDialog title(@StringRes int titleRes) {
        this.title = getContext().getString(titleRes);
        return this;
    }

    public ProgressOperatingDialog icon(@DrawableRes int iconResId) {
        this.iconResId = iconResId;
        return this;
    }

    public ProgressOperatingDialog icon(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
        return this;
    }
    // -----

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
    protected void onBuild(MaterialAlertDialogBuilder builder) {
        super.onBuild(builder);
        boolean hasIcon = this.iconDrawable != null || this.iconResId != 0;
        if (hasIcon || title != null){
            binding.getRoot().setPadding(binding.getRoot().getPaddingLeft(),
                    UiUtil.dpToPx(getContext(), 12),
                    binding.getRoot().getPaddingRight(),
                    binding.getRoot().getPaddingBottom());
        }
        if (title != null) {
            builder.setTitle(title);
            if (this.iconDrawable != null) {
                builder.setIcon(this.iconDrawable);
            } else if (this.iconResId != 0) {
                builder.setIcon(this.iconResId);
            }
        } else if (hasIcon) {
            boolean isM3Theme = ThemeHelper.isM3Theme(resolvedTheme);
            LinearLayout container = new LinearLayout(getContext());
            container.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            container.setOrientation(LinearLayout.VERTICAL);
            ImageView iconView = new ImageView(getContext());
            if(isM3Theme) {
                int paddingTop = UiUtil.dpToPx(context, 22.5f);
                int paddingBottom = UiUtil.dpToPx(context, 4.5f);
                if (centered) {
                    container.setGravity(Gravity.CENTER);
                    container.setPadding(0, paddingTop, 0, paddingBottom);
                } else {
                    container.setGravity(Gravity.START);
                    int paddingLeft = UiUtil.dpToPx(context, 24.2f);
                    container.setPadding(paddingLeft, paddingTop, 0, paddingBottom);
                }
                iconView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }else {
                int paddingTop = UiUtil.dpToPx(context, 18.4f);
                int paddingBottom = 0;
                if (centered) {
                    container.setGravity(Gravity.CENTER);
                    container.setPadding(0, paddingTop, 0, paddingBottom);
                } else {
                    container.setGravity(Gravity.START);
                    int paddingLeft = UiUtil.dpToPx(context, 24.2f);
                    container.setPadding(paddingLeft, paddingTop, 0, paddingBottom);
                }
                int iconSize = UiUtil.dpToPx(context, 32f);
                iconView.setLayoutParams(new ViewGroup.LayoutParams(iconSize, iconSize));
                iconView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            if (this.iconDrawable != null) {
                iconView.setImageDrawable(this.iconDrawable);
            } else {
                iconView.setImageResource(this.iconResId);
            }
            Context context = builder.getContext();
            TypedValue typedValue = new TypedValue();
            int iconColorAttr;
            if(isM3Theme){
                iconColorAttr = com.google.android.material.R.attr.colorSecondary;
            }else {
                iconColorAttr = android.R.attr.colorControlNormal;
            }
            if (context.getTheme().resolveAttribute(iconColorAttr, typedValue, true)) {
                if (typedValue.resourceId != 0) {
                    ColorStateList tintList = ContextCompat.getColorStateList(context, typedValue.resourceId);
                    ImageViewCompat.setImageTintList(iconView, tintList);
                } else {
                    ImageViewCompat.setImageTintList(iconView, ColorStateList.valueOf(typedValue.data));
                }
            }
            container.addView(iconView);
            builder.setCustomTitle(container);
        }
    }

    @Override
    protected void onShowed() {
        super.onShowed();
        if (centered && dialog != null) {
            binding.text.setGravity(Gravity.CENTER);
            binding.text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    public void updateTitle(String title){
        UiThread.run(() -> {
            dialog.setTitle(title);
        });
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
