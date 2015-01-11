package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by PBosman on 14/11/2014.
 */
public class Stage extends GameObject implements IStage {
    private ArrayList<ScaledBitmap> _backdrops = new ArrayList<ScaledBitmap>();
    private ScaledBitmap _currentBackdrop;
    private int _backdropIndex = -1;
    private Paint _paint = new Paint();

    @Override
    public void initialize(IGameObjectContainer parentContainer, float defaultToleranceWidth, float defaultToleranceHeight) {
        if(_backdrops.size() > 0) {
            _currentBackdrop = _backdrops.get(0);
            _backdropIndex = 0;
        }
        _paint.setColor(Color.WHITE);
        _paint.setStyle(Paint.Style.FILL_AND_STROKE);

        super.initialize(parentContainer, defaultToleranceWidth, defaultToleranceHeight);
    }

    @Override
    public final void onDraw(Canvas canvas) {
        if(canvas == null) {
            return;
        }

        if(_currentBackdrop == null) {
            canvas.drawRect(new RectF(0,0, _width-1f, _height-1f), _paint);
        }
        else {
            canvas.drawBitmap(_currentBackdrop.getScaledBitmap(), 0.0f, 0.0f, null);
        }
    }

    @Override
    public void onReady() {

    }

    @Override
    public void onPausing() {

    }

    @Override
    public void onResuming() {

    }

    @Override
    public void onSizeChanged(int width, int height) {
        super.onSizeChanged(width, height);

        for(ScaledBitmap bitmap : _backdrops) {
            bitmap.setBitmapScale(width, height);
        }
    }

    @Override
    public void onMotionActionDown(float x, float y) {
    }

    @Override
    public void onMotionActionMove(float x, float y) {
    }

    @Override
    public void onMotionActionUp(float x, float y) {
    }

    public void addBackdrop(View view, int resourceId) {
        _backdrops.add(new ScaledBitmap(BitmapFactory.decodeResource(view.getResources(), resourceId)));
    }

    public void nextBackdrop() {
        if(_backdrops.size() < 2) {
            return;
        }

        if(_backdropIndex < (_backdrops.size()-1)) {
            _backdropIndex++;
        }
        else {
            _backdropIndex = 0;
        }
        _currentBackdrop = _backdrops.get(_backdropIndex);
    }
}
