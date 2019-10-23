package com.geniusplaza;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by admin on 08-Sep-16.
 */
public class FontManager {
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "Pe-icon-7-stroke.ttf";

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }

}
