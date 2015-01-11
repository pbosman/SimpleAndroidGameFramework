package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.Vector;

/**
 * The GameObjectContainer class hosts instances of IGameObject.
 * The game objects life cycles are managed from this container and
 * events are routed to the contained game objects.
 */
public class GameObjectContainer extends Thread implements IGameObjectContainer {
    private Vector<IGameObject> _gameObjects = new Vector<IGameObject>();
    private final int MAX_MOTION_TRACKERS=20;
    private MotionTracker[] _motionTrackers = new MotionTracker[MAX_MOTION_TRACKERS];
    private static final String TAG = GameObjectContainer.class.getSimpleName();
    private boolean _paused = false;
    private boolean _running = false;
    private boolean _hasArea = false;
    // Surface holder that can access the physical surface
    private SurfaceHolder _surfaceHolder;
    private DisplayMetrics _metrics;
    private double _touchSizeInches = 0.5;
    private int _touchToleranceWidth = 0;
    private int _touchToleranceHeight = 0;
    private Object _gameObjectLock = new Object();

    //region Implementation of Thread.run

    /**
     * The Thread.run method.
     * This is the main game loop - the game objects are drawn here.
     */
    @Override
    public void run() {
        Canvas canvas = null;

        while (_running) {
            if(!_paused && _hasArea) {
                try {
                    // Try locking the canvas.
                    canvas = _surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        try {
                            synchronized (_surfaceHolder) {
                                onDraw(canvas);
                            }
                        }
                        finally {
                            _surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
                catch(IllegalStateException excep) {
                    Log.e(TAG, "Exception while locking canvas.", excep);
                }
            }

            try {
                sleep(30);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region Implementation of IGameObjectContainer.

    /**
     * Add a game object to the container.
     * @param gameObject The game object to add.
     */
    @Override
    public void add(IGameObject gameObject) {
        _gameObjects.add(gameObject);
    }

    /**
     * Initialize the GameObjectContainer.
     * This is called by the GameSurface to initialize and start the
     * main rendering thread and the game threads.
     * @param surfaceHolder The surface holder for the view.
     * @param metrics The metrics for the view.
     */
    @Override
    public void initialize(SurfaceHolder surfaceHolder, DisplayMetrics metrics) {
        _surfaceHolder = surfaceHolder;
        _metrics = metrics;

        _touchToleranceWidth = (int)(metrics.xdpi * _touchSizeInches);
        _touchToleranceHeight = (int)(metrics.ydpi * _touchSizeInches);

        synchronized(_gameObjectLock) {
            for(IGameObject gameObject : _gameObjects) {
                gameObject.initialize(this, _touchToleranceWidth, _touchToleranceHeight);
            }
        }

        _paused = true;
        _running = true;
        this.start();
    }

    /**
     * Get the default touch tolerance width.
     * @return The default touch tolerance width.
     */
    public int getDefaultTouchToleranceWidth() {
        return _touchToleranceWidth;
    }

    /**
     * Get the default touch tolerance height.
     * @return The default touch tolerance height.
     */
    public int getDefaultTouchToleranceHeight() {
        return _touchToleranceHeight;
    }

    /**
     * Called by the GameSurface to pause the game.
     * When pausing, this will also pause all contained game objects,
     * however, when resuming, this only resumes the GameObjectController thread;
     * The threads for the game objects are resumed when the view size becomes valid,
     * in onSizeChanged.
     * @param value
     */
    @Override
    public void setPaused(boolean value) {
        _paused = value;
        // Always reset the hasArea flag - assume there is no area.
        _hasArea = false;

        // If resuming, don't un-pause the game objects.
        // They will be un-paused in onSizeChanged.
        if(!value) {
            return;
        }

        synchronized(_gameObjectLock) {
            for(final IGameObject gameObject : _gameObjects) {
                gameObject.setPaused(true);
            }
        }
    }

    /**
     * Gets the paused state.
     * @return True if the state is paused.
     */
    @Override
    public boolean isPaused() {
        return _paused;
    }

    @Override
    public boolean isTouchingSprite(ISprite sourceSprite, String targetSpriteName) {
        synchronized (_gameObjectLock) {

        }
        for(IGameObject gameObject : _gameObjects) {
            if(gameObject instanceof ISprite) {
                ISprite targetSprite = (ISprite)gameObject;
                if(targetSprite.getName().equals(targetSpriteName)) {
                    return sourceSprite.isTouchingSprite(targetSprite);
                }
            }
        }
        return false;
    }

    @Override
    public void broadcastMessage(final IGameObject source, final String message) {
        synchronized(_gameObjectLock) {
            for(final IGameObject gameObject : _gameObjects) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        gameObject.onReceivedMessage(source, message);
                    }
                }).start();
            }
        }
    }

    @Override
    public void onSizeChanged(int width, int height) {
        _hasArea = (width > 0) && (height > 0);

        if(_hasArea) {
            synchronized (_gameObjectLock) {
                for(final IGameObject gameObject : _gameObjects) {
                    gameObject.onSizeChanged(width, height);
                    if(gameObject.isPaused()) {
                        gameObject.setPaused(false);
                    }
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        synchronized(_gameObjectLock) {
            for(final IGameObject gameObject : _gameObjects) {
                gameObject.onDraw(canvas);
            }
        }
    }

    public ISprite getSprite(float x, float y) {
        synchronized (_gameObjectLock) {
            int size = _gameObjects.size();
            for(int index=size-1;index >= 0; --index) {
                IGameObject gameObject = _gameObjects.get(index);
                if(gameObject instanceof ISprite) {
                    ISprite sprite = (ISprite) gameObject;
                    if(sprite.hitTest(x, y)) {
                        return sprite;
                    }
                }
            }
        }

        return null;
    }

    public void bringToFront(IGameObject gameObject) {
        synchronized (_gameObjectLock) {
            _gameObjects.remove(gameObject);
            _gameObjects.add(gameObject);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        int pointerCount = event.getPointerCount();
        int actionMasked = event.getActionMasked();

        for(int pointerIndex=0;pointerIndex < pointerCount; ++pointerIndex) {
            float xPos = event.getX(pointerIndex);
            float yPos = event.getY(pointerIndex);

            switch(actionMasked) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: {
                    ISprite sprite = getSprite(xPos, yPos);
                    if(sprite != null) {
                        _motionTrackers[pointerIndex] = new MotionTracker(xPos, yPos, sprite);
                        sprite.onMotionActionDown(xPos, yPos);
                    }
                }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL: {
                    MotionTracker tracker = _motionTrackers[pointerIndex];
                    _motionTrackers[pointerIndex] = null;
                    if(tracker != null) {
                        tracker.getSprite().onMotionActionUp(xPos, yPos);
                    }
                }
                    break;

                case MotionEvent.ACTION_MOVE: {
                    MotionTracker tracker = _motionTrackers[pointerIndex];
                    if(tracker != null) {
                        tracker.move(xPos, yPos);
                    }
                }
                    break;
            }
        }

        return true;
    }

    //endregion

    //region Internal implementation

    //endregion
}
