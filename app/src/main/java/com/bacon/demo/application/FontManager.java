package com.bacon.demo.application;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by serajam on 6/18/2017.
 */

public class FontManager {

    public static final String ROOT = "fonts/";

    public static final String FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}
