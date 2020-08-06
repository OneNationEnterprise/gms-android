package com.gymapp.helper;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class ScreenDimenHelper {
    public ScreenDimenHelper() {
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static int getToolBarHeight(Context context, int actionBarSize) {
        int[] attrs = new int[]{actionBarSize};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
