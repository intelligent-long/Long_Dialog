package com.longx.intelligent.android.lib.longdialog.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.LongDialog;
import com.longx.intelligent.android.lib.longdialog.ui.UiThread;
import com.longx.intelligent.android.lib.longdialog.util.ThemeHelper;
import com.longx.intelligent.android.lib.longdialog.util.UiUtil;

import java.util.concurrent.CountDownLatch;

/**
 * Created by LONG on 2026/8/24 at 上午2:06.
 */
public abstract class BaseDialog<T extends BaseDialog<T>> {
    protected final Context context;
    protected MaterialAlertDialogBuilder dialogBuilder;
    protected AlertDialog dialog;
    // Custom Field -----
    protected boolean centered;
    protected int theme;
    protected int resolvedTheme;
    protected boolean cancelable = true;
    protected DialogInterface.OnDismissListener onDismissListener;
    protected DialogInterface.OnCancelListener onCancelListener;
    protected DialogInterface.OnKeyListener onKeyListener;
    protected DialogInterface.OnShowListener onShowListener;
    // -----

    public BaseDialog(Context context) {
        this.context = context;
    }

    // Custom Method -----
    public T centered(boolean centered) {
        this.centered = centered;
        return (T) this;
    }

    public T theme(int style) {
        this.theme = style;
        return (T) this;
    }

    public T cancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return (T) this;
    }

    public T onDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return (T) this;
    }

    public T onCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        return (T) this;
    }

    public T onKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
        return (T) this;
    }

    public T onShowListener(DialogInterface.OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return (T) this;
    }
    // -----

    public T create(){
        return create(null);
    }

    public T create(Runnable onCreated){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        UiThread.run(() -> {
            resolvedTheme = theme != 0 ? theme : (centered ? LongDialog.centeredTheme : LongDialog.theme);
            if (resolvedTheme != 0) {
                dialogBuilder = new MaterialAlertDialogBuilder(getContext(), resolvedTheme);
            } else {
                dialogBuilder = new MaterialAlertDialogBuilder(getContext());
            }
            LayoutInflater inflater = LayoutInflater.from(dialogBuilder.getContext());
            View view = onCreateView(inflater);
            if(view != null){
                dialogBuilder.setView(view);
            }
            dialogBuilder.setCancelable(cancelable);
            dialog = onCreate(dialogBuilder);
            if (view != null) {
                setAutoCancelInput(view);
            }
            if(onDismissListener != null){
                dialog.setOnDismissListener(onDismissListener);
            }
            if(onCancelListener != null){
                dialog.setOnCancelListener(onCancelListener);
            }
            if(onKeyListener != null){
                dialog.setOnKeyListener(onKeyListener);
            }
            if(onShowListener != null){
                dialog.setOnShowListener(onShowListener);
            }
            onCreated();
            if(onCreated != null) onCreated.run();
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return (T) this;
    }

    public T show(){
        return show(null);
    }

    public T show(Runnable onShowedYier) {
        UiThread.run(() -> {
            try {
                dialog.show();
            }catch (WindowManager.BadTokenException ignore){}
            adjustDialogSize();
            onShowed();
            if(onShowedYier != null) onShowedYier.run();
        });
        return (T) this;
    }

    protected void onBuild(MaterialAlertDialogBuilder builder){
    };

    protected AlertDialog onCreate(MaterialAlertDialogBuilder builder) {
        onBuild(builder);
        return builder.create();
    }

    protected View onCreateView(LayoutInflater layoutInflater){
        return null;
    }

    protected void onCreated() {
    }

    protected void adjustDialogSize() {
    }

    protected void onShowed(){
        boolean isM3CenteredTheme = ThemeHelper.isM3CenteredTheme(resolvedTheme);
        if (centered && !isM3CenteredTheme && dialog != null) {
            TextView titleView = dialog.findViewById(androidx.appcompat.R.id.alertTitle);
            if (titleView != null) {
                titleView.setGravity(Gravity.CENTER);
                titleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

    public void hide() {
        UiThread.run(() -> {
            if (dialog != null) {
                dialog.hide();
            }
        });
    }

    public void dismiss() {
        UiThread.run(() -> {
            if (dialog != null) {
                try {
                    dialog.dismiss();
                }catch (Exception ignore){}
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setAutoCancelInput(View contentView) {
        contentView.setOnTouchListener((view, motionEvent) -> {
            Window window = dialog.getWindow();
            UiUtil.autoCancelInput(context, window == null ? null : window.getCurrentFocus(), motionEvent);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
                return true;
            }
            return false;
        });
    }

    public Context getContext() {
        return context;
    }

    public AlertDialog getDialog() {
        return dialog;
    }
}