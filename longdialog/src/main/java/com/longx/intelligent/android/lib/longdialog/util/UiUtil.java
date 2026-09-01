package com.longx.intelligent.android.lib.longdialog.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by LONG on 2026/8/24 at 上午4:54.
 */
public class UiUtil {
    public static void autoCancelInput(Context context, View focusView, MotionEvent motionEvent){
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (focusView instanceof EditText) {
                Rect outRect = new Rect();
                focusView.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)motionEvent.getRawX(), (int)motionEvent.getRawY())) {
                    focusView.clearFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
            }
        }
    }

    public static int dpToPx(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int pxToDp(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
}
