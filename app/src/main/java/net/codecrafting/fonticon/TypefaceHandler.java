package net.codecrafting.fonticon;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.util.Hashtable;

public class TypefaceHandler {

    private static final String TAG = TypefaceHandler.class.getSimpleName();

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    /**
     * Handle the loading of the Typeface fonts
     * @param c Context caller
     * @param assetPath Path to the font asset
     * @return New Typeface or the cached one
     */
    @Nullable
    public static Typeface get(Context c, String assetPath)
    {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(),
                            assetPath);
                    cache.put(assetPath, t);
                    Log.e(TAG, "Loaded '" + assetPath);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }

    /**
     * Get the pixel size from given unit
     * @param unit Android unit type
     * @param size Desirable unit size
     * @return Pixel size of the unit type
     */
    public static int getPixels(int unit, float size)
    {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(unit, size, metrics);
    }
}
