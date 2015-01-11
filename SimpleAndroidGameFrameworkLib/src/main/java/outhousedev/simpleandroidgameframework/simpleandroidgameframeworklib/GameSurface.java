package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * GameSurface class, which extends a SurfaceView view
 * and passes events onto the underlying GameObjectContainer.
 * ToDo: Consider merging this into GameObjectContainer.
 */
public class GameSurface extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = GameSurface.class.getSimpleName();
    private IGameObjectContainer _gameObjectContainer;

    /**
     * Construct the game surface and add add the event handler.
     * @param context The application context.
     */
    public GameSurface(Context context) {
        super(context);

        // Handle events from the holder.
        getHolder().addCallback(this);
        setFocusable(true);
    }

    /**
     * Initialize the GameSurface with the display metrics and the list of game objects.
     * @param metrics The display metrics of the main display.
     * @param gameObjects The list of game objects to add to the view.
     */
    public void initialize(DisplayMetrics metrics, ArrayList<IGameObject> gameObjects) {
        _gameObjectContainer = new GameObjectContainer();
        for(IGameObject gameObject : gameObjects) {
            _gameObjectContainer.add(gameObject);
        }

        _gameObjectContainer.initialize(getHolder(), metrics);
    }

    @Override
    public void onDraw( Canvas canvas) {
        if(_gameObjectContainer == null) {
            return;
        }

        _gameObjectContainer.onDraw(canvas);
    }

    //region SurfaceHolder.Callback implementation

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(_gameObjectContainer == null) {
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        if(_gameObjectContainer == null) {
            return;
        }
        _gameObjectContainer.onSizeChanged(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if(_gameObjectContainer == null) {
            return;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(_gameObjectContainer == null) {
            return false;
        }
        return _gameObjectContainer.onTouchEvent(event);
    }

    //endregion

    //region Activity lifecycle notifications.

    public void onPauseActivity() {
        Log.d(TAG, "onPauseActivity");
        if(_gameObjectContainer == null) {
            return;
        }
        _gameObjectContainer.setPaused(true);
    }

    public void onResumeActivity(){
        Log.d(TAG, "onResumeActivity");
        if(_gameObjectContainer == null) {
            return;
        }
        _gameObjectContainer.setPaused(false);
    }

    //endregion
}
