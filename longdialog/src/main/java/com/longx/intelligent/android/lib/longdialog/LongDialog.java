package com.longx.intelligent.android.lib.longdialog;

import android.app.Activity;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.longx.intelligent.android.lib.longdialog.dialog.ConfirmDialog;
import com.longx.intelligent.android.lib.longdialog.dialog.InputDialog;
import com.longx.intelligent.android.lib.longdialog.dialog.ListDialog;
import com.longx.intelligent.android.lib.longdialog.dialog.MessageDialog;
import com.longx.intelligent.android.lib.longdialog.dialog.OperatingDialog;
import com.longx.intelligent.android.lib.longdialog.dialog.OptionDialog;
import com.longx.intelligent.android.lib.longdialog.dialog.ProgressOperatingDialog;

/**
 * Created by LONG on 2026/8/24 at 下午10:27.
 */
public class LongDialog {
    @StyleRes
    public static int theme = 0;
    @StyleRes
    public static int centeredTheme = 0;
    @StringRes
    public static int okButtonText = android.R.string.ok;
    @StringRes
    public static int cancelButtonText = android.R.string.cancel;
    @StringRes
    public static int neutralButtonText = R.string.long_dialog_default_neutral_text;
    @StringRes
    public static int confirmText = R.string.long_dialog_default_confirm_message;

    public static void material3Theme(){
        theme = com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog;
        centeredTheme = com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered;
    }

    public static void material2ThemeDayNight(){
        theme = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_Dialog_Alert;
        centeredTheme = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_Dialog_Alert;
    }

    public static void material2ThemeLight(){
        theme = com.google.android.material.R.style.Theme_MaterialComponents_Light_Dialog_Alert;
        centeredTheme = com.google.android.material.R.style.Theme_MaterialComponents_Light_Dialog_Alert;
    }

    public static void material2ThemeDark(){
        theme = com.google.android.material.R.style.Theme_MaterialComponents_Dialog_Alert;
        centeredTheme = com.google.android.material.R.style.Theme_MaterialComponents_Dialog_Alert;
    }

    public static MessageDialog newMessageDialog(Activity activity){
        return new MessageDialog(activity);
    }

    public static ConfirmDialog newConfirmDialog(Activity activity){
        return new ConfirmDialog(activity);
    }

    public static OptionDialog newOptionDialog(Activity activity){
        return new OptionDialog(activity);
    }

    public static OperatingDialog newOperatingDialog(Activity activity){
        return new OperatingDialog(activity);
    }

    public static ProgressOperatingDialog newProgressOperatingDialog(Activity activity){
        return new ProgressOperatingDialog(activity);
    }

    public static InputDialog newInputDialog(Activity activity){
        return new InputDialog(activity);
    }

    public static ListDialog newListDialog(Activity activity){
        return new ListDialog(activity);
    }
}