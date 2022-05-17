package com.rabbit.mylibrary;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by DongZF on 2022/5/17
 */
public class UnitUtil {
    public static float dpToPx(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getApplicationContext().getResources().getDisplayMetrics());
    }

    public static float spToPx(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getApplicationContext().getResources().getDisplayMetrics());
    }
}
