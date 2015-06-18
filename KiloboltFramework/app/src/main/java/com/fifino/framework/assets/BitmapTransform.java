package com.fifino.framework.assets;

import android.graphics.Bitmap;
import android.graphics.Matrix;


public class BitmapTransform {
    public static Bitmap rotate(Bitmap source, float angle)
    {
          Matrix matrix = new Matrix();
          matrix.postRotate(angle);
          return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public static Bitmap scale(Bitmap source, int w, int h)
    {
    	return Bitmap.createScaledBitmap(source, w, h, false);
    }
}
