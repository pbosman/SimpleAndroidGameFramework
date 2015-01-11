package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;

/**
 * Class implementing a Sprite.
 * The Class has members to position, move and respond to touch events.
 */
public class Sprite extends GameObject implements ISprite {
    private float _initialX = 0.0f;
    private float _initialY = 0.0f;
    private float _x = 0;
    private float _y = 0;
    private float _rotation = 0;
    private ArrayList<ScaledBitmap> _costumes = new ArrayList<ScaledBitmap>();
    private ScaledBitmap _currentCostume;
    private int _costumeIndex = -1;
    private boolean _hDraggable = false;
    private boolean _vDraggable = false;
    private boolean _useTargetSize = false;
    private float _targetWidthRelative = 0f;
    private float _targetHeightRelative = 0f;
    private Paint _paint = new Paint();
    private Matrix _matrix = new Matrix();

    //region Overrides of GameObject.

    @Override
    public void initialize(IGameObjectContainer parentContainer, float defaultToleranceWidth, float defaultToleranceHeight) {
        if(_costumes.size() > 0) {
            _currentCostume = _costumes.get(0);
            _costumeIndex = 0;
        }

        _paint.setColor(Color.WHITE);
        _paint.setStyle(Paint.Style.FILL_AND_STROKE);
        _paint.setTextSize(_paint.getTextSize() * 3);

        super.initialize(parentContainer, defaultToleranceWidth, defaultToleranceHeight);
    }

    /**
     * Called by the GameObjectContainer to onDraw this game object.
     * The following URL explains how to use the Matrix class.
     * // http://www.satyakomatineni.com/akc/display?url=displaynoteimpurl&ownerUserId=satya&reportId=2898
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        if(canvas == null || _currentCostume == null) {
            return;
        }
        Bitmap pBitmap = _currentCostume.getSourceBitmap();
        Matrix matrix = _matrix;
        matrix.reset ();
        matrix.postTranslate(-_currentCostume.getSourceBitmap().getWidth()/2f, -_currentCostume.getSourceBitmap().getHeight()/2f);
        matrix.postScale(_currentCostume.getXScale(), _currentCostume.getYScale());
        if(_rotation > 0f) {
            matrix.postRotate(_rotation);
        }
        matrix.postTranslate(_x, _y);

        // Finally, onDraw the bitmap using the matrix as a guide.
        canvas.drawBitmap (pBitmap, matrix, null);
        //canvas.drawText(Float.toString(_rotation), _x, _y, _paint);
    }

    @Override
    public void onReady() {
        gotoXY(_initialX, _initialY);
    }

    @Override
    public void onPausing() {

    }

    @Override
    public void onResuming() {

    }

    @Override
    public boolean hitTest(float x, float y) {
        return getTouchRect().contains(x, y);
    }

    @Override
    public void onMotionActionDown(float xPos, float yPos) {
    }

    @Override
    public void onMotionActionMove(float xPos, float yPos) {
        if(!_hDraggable && !_vDraggable) {
            return;
        }

        if(_hDraggable) _x = Math.max(Math.min(xPos, _width-1), 0);
        if(_vDraggable) _y = Math.max(Math.min(yPos, _height-1), 0);
    }

    @Override
    public void onMotionActionUp(float xPos, float yPos) {
    }

    @Override
    public final void setInitialPosition(float x, float y) {
        _initialX = x;
        _initialY = y;
    }

    @Override
    public void onSizeChanged(int width, int height) {
        super.onSizeChanged(width, height);

        for(ScaledBitmap bitmap : _costumes) {
            if(_useTargetSize) {
                float targetWidth = 0;
                float targetHeight = 0;
                float aspectRatio = bitmap.getAspectRatio();

                if(_targetHeightRelative == 0.0f) {
                    targetWidth = _width * _targetWidthRelative / 2f;
                    targetHeight = aspectRatio * targetWidth;
                }
                else if(_targetWidthRelative == 0.0f) {
                    targetHeight = _height * _targetHeightRelative / 2f;
                    targetWidth = targetHeight / aspectRatio;
                }
                else {
                    targetWidth = _width * _targetWidthRelative / 2f;
                    targetHeight = _height * _targetHeightRelative / 2f;
                }

                bitmap.setBitmapScale(targetWidth, targetHeight);
            }
            else {
                bitmap.resetScale();
            }
        }
    }

    @Override
    public final void setSize(float width, float height) {
        _useTargetSize = true;
        _targetWidthRelative = width;
        _targetHeightRelative = height;
    }

    @Override
    public final void setX(float value) {
        _x = ((value + 1f) * _width / 2f);
    }

    @Override
    public final void setY(float value) {
        _y = ((value + 1f) * _height / 2f);
    }

    @Override
    public final void gotoXY(float x, float y) {
        _x = (x + 1f) * _width / 2f;
        _y = (y + 1f) * _height / 2f;
    }

    @Override
    public final float getX() {
        return _x * 2f / _width - 1f;
    }

    @Override
    public final float getY() {
        return _y * 2f / _height - 1f;
    }

    @Override
    public void setDraggable(boolean horizontallyDraggable, boolean verticallyDraggable) {
        _hDraggable = horizontallyDraggable;
        _vDraggable = verticallyDraggable;
    }

    @Override
    public boolean isTouchingSprite(String name) {
        return _parentContainer.isTouchingSprite(this, name);
    }

    @Override
    public boolean isTouchingRect(RectF rect) {
        return getTargetRect().intersect(rect);
    }

    @Override
    public RectF getTargetRect() {
        if(_currentCostume == null) {
            return new RectF(_x, _y, _x, _y);
        }

        float targetWidth = _currentCostume.getWidth();
        float targetHeight = _currentCostume.getHeight();

        return new RectF(_x - targetWidth / 2,
                _y - targetHeight / 2,
                _x + targetWidth / 2,
                _y + targetHeight / 2);
    }

    @Override
    public RectF getTouchRect() {
        if(_currentCostume == null) {
            return new RectF(_x, _y, _x, _y);
        }

        RectF touchArea = new RectF(_x - _touchToleranceX / 2,
                _y - _touchToleranceY / 2,
                _x + _touchToleranceX / 2,
                _y + _touchToleranceY / 2);

        RectF targetRect = getTargetRect();
        targetRect.union(touchArea);

        return targetRect;
    }

    @Override
    public boolean isTouchingSprite(ISprite targetSprite) {
        return isTouchingRect(targetSprite.getTargetRect());
    }

    @Override
    public float getXPosition() {
        return _x;
    }

    @Override
    public float getYPosition() {
        return _y;
    }

    public void setRotation(float rotation) {
        _rotation = rotation;
    }

    public float getRotation() {
        return _rotation;
    }

    //endregion

    //region Implementation of ISprite

    @Override
    public void addCostume(View view, int resourceId) {
        _costumes.add(new ScaledBitmap(BitmapFactory.decodeResource(view.getResources(), resourceId)));
    }

    @Override
    public void nextCostume() {
        if(_costumes.size() < 2) {
            return;
        }

        if(_costumeIndex < (_costumes.size()-1)) {
            _costumeIndex++;
        }
        else {
            _costumeIndex = 0;
        }
        _currentCostume = _costumes.get(_costumeIndex);
    }

    //endregion
}
