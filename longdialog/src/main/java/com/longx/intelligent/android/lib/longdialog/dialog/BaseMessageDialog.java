package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.LongDialog;
import com.longx.intelligent.android.lib.longdialog.util.ThemeHelper;
import com.longx.intelligent.android.lib.longdialog.util.UiUtil;

/**
 * Created by LONG on 2026/8/25 at 上午4:57.
 */
public abstract class BaseMessageDialog<T extends BaseMessageDialog<T>> extends BaseDialog<T> {
    // Custom Field -----
    protected CharSequence title;
    protected CharSequence message;
    protected CharSequence okButtonText;
    protected int okButtonTextRes;
    protected int iconResId;
    protected Drawable iconDrawable;
    // -----

    public BaseMessageDialog(Activity activity) {
        super(activity);
    }

    // Custom Method -----
    public T title(CharSequence title) {
        this.title = title;
        return (T) this;
    }

    public T title(@StringRes int titleRes) {
        this.title = getContext().getString(titleRes);
        return (T) this;
    }

    public T message(CharSequence message) {
        this.message = message;
        return (T) this;
    }

    public T message(@StringRes int messageRes) {
        this.message = getContext().getString(messageRes);
        return (T) this;
    }

    public T okButtonText(CharSequence okButtonText) {
        this.okButtonText = okButtonText;
        return (T) this;
    }

    public T okButtonText(@StringRes int okButtonTextRes) {
        this.okButtonTextRes = okButtonTextRes;
        return (T) this;
    }

    public T icon(@DrawableRes int iconResId) {
        this.iconResId = iconResId;
        return (T) this;
    }

    public T icon(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
        return (T) this;
    }
    // -----

    @Override
    protected void onBuild(MaterialAlertDialogBuilder builder) {
        super.onBuild(builder);
        boolean hasIcon = this.iconDrawable != null || this.iconResId != 0;
        if (this.okButtonText != null) {
            builder.setPositiveButton(this.okButtonText, null);
        } else {
            int finalOkButtonTextRes = this.okButtonTextRes != 0 ?
                    this.okButtonTextRes :
                    (LongDialog.okButtonText != 0 ? LongDialog.okButtonText : android.R.string.ok);
            builder.setPositiveButton(finalOkButtonTextRes, null);
        }
        if (message != null) {
            builder.setMessage(message);
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
}