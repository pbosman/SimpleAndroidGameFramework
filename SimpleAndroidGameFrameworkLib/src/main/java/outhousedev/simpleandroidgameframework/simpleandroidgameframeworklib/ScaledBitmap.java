package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Implements a scaled bitmap helper around a bitmap object.
 * This class is used to create a scaled bitmap from a source bitmap that is
 * specified on construction.
 * A reference to the source bitmap is kept so that it can be used repeatedly for creating
 * bitmaps of other scales, when the setBitmapScale method is called.
 */
public class ScaledBitmap {
    private Bitmap _sourceBitmap;
    private float _width;
    private float _height;
    private float _xScale;
    private float _yScale;
    private Bitmap _scaledBitmap;
    private float _aspectRatio;

    /**
     * Construct a scaled bitmap, given the source bitmap.
     * @param bitmap The source bitmap to use in this ScaledBitmap.
     */
    public ScaledBitmap(Bitmap bitmap) {
        _sourceBitmap = bitmap;
        resetScale();
    }

    /**
     * Gets the source bitmap.
     * @return The source bitmap.
     */
    public Bitmap getSourceBitmap() {
        return _sourceBitmap;
    }

    /**
     * Gets the scaled bitmap.
     * The source bitmap is returned if the scale has not been set.
     * @return The scaled bitmap.
     */
    public Bitmap getScaledBitmap() {
        return _scaledBitmap;
    }

    /**
     * Gets the X scale of the bitmap.
     * @return The X scale of the bitmap.
     */
    public float getXScale() { return _xScale; }

    /**
     * Gets the Y scale of the bitmap.
     * @return The Y scale of the bitmap.
     */
    public float getYScale() { return _yScale; }

    /**
     * Set the new bitmap width and height.
     * @param width The new bitmap width.
     * @param height The new bitmap height.
     */
    public void setBitmapScale(float width, float height) {
        if(width == _width && height == _height) {
            return;
        }

        _width = width;
        _height = height;
        _xScale = width / (float)_sourceBitmap.getWidth();
        _yScale = height / (float)_sourceBitmap.getHeight();

        _scaledBitmap = Bitmap.createScaledBitmap(_sourceBitmap, (int)width, (int)height, true);
    }

    /**
     * Reset the scale of the bitmap.
     */
    public void resetScale() {
        _scaledBitmap = _sourceBitmap;
        _width = _sourceBitmap.getWidth();
        _height = _sourceBitmap.getHeight();
        _xScale = 1;
        _yScale = 1;
        _aspectRatio = _height / _width;
    }

    /**
     * Get the width of the scaled bitmap.
     * @return The scaled bitmap width.
     */
    public float getWidth() {
        return _width;
    }

    /**
     * Get the height of the scaled bitmap.
     * @return The scaled bitmap height.
     */
    public float getHeight() {
        return _height;
    }

    /**
     * Get the aspect ratio of the bitmap.
     * @return The aspect ratio of the bitmap.
     */
    public float getAspectRatio() {
        return _aspectRatio;
    }
}
