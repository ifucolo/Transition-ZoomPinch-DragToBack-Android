package example.com.zoompinch.util;

import android.content.Context;

public class Util {

    public static int pxToDp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(px / density);
    }
}
