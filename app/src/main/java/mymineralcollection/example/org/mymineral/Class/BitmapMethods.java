package mymineralcollection.example.org.mymineral.Class;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Santiago on 11/15/2016.
 */
public class BitmapMethods {
    public static int VERTICAL = 1;
    public static int HORIZONTAL = 2;

    public static int ROTATE_LEFT = -90;
    public static int ROTATE_RIGHT = 90;


    public static Bitmap flipBitmap(Bitmap src, int type) {
        Matrix matrix = new Matrix();

        if(type == VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        }
        else if(type == HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        } else {
            return src;
        }

        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
