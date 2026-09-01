package com.longx.intelligent.android.lib.longdialog.util;

/**
 * Created by LONG on 2026/8/28 at 下午5:57.
 */
public class ThemeHelper {
    public static boolean isM3Theme(int theme){
        return (theme == com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                || (theme == com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog);
    }

    public static boolean isM3CenteredTheme(int theme){
        return theme == com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered;
    }
    public static boolean isM2Theme(int theme) {
        return (theme == com.google.android.material.R.style.Theme_MaterialComponents_DayNight_Dialog_Alert)
                || (theme == com.google.android.material.R.style.Theme_MaterialComponents_Light_Dialog_Alert)
                || (theme == com.google.android.material.R.style.Theme_MaterialComponents_Dialog_Alert);
    }
}
