package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

/**
 * The GameObject class is the base class from which
 * game objects can derive from to inherit common functionality.
 * The GameObjectContainer contains a collection of IGameObject objects.
 * GameObject its self inherits from Thread. The run method
 * is executed by the GameObjectContainer when the game starts.
 */
public abstract class GameObject extends Thread implements IGameObject {
    private boolean _paused = false;
    private boolean _running = false;
    protected Stopwatch _stopwatch = new Stopwatch();
    protected IGameObjectContainer _parentContainer = null;
    private ArrayList<MediaPlayer> _mediaPlayers = new ArrayList<MediaPlayer>();
    protected float _width;
    protected float _height;
    protected float _touchToleranceX = 0;
    protected float _touchToleranceY = 0;

    //region Overrides of Thread

    /**
     * The thread run method.
     * The method will wait initially for the application to become
     * un-paused.
     */
    @Override
    public final void run() {
        waitWhilePaused();
        _stopwatch.reset();
        onReady();
        _running = false;
    }

    //endregion

    //region Implementation of IGameObject

    @Override
    public void initialize(IGameObjectContainer parentContainer, float defaultToleranceWidth, float defaultToleranceHeight) {
        _parentContainer = parentContainer;
        _touchToleranceX = defaultToleranceWidth;
        _touchToleranceY = defaultToleranceHeight;
        _paused = true;
        _running = true;
        start();
    }

    @Override
    public void addMediaResource(View view, int resourceId, String clipName) {
        // ToDo: store the media player object in a dictionary.
        _mediaPlayers.add(MediaPlayer.create(view.getContext(), resourceId));
    }

    @Override
    public final void setPaused(boolean value) {
        if(_paused != value) {
            if(value) {
                Log.d(getName(), "Pausing");
                onPausing();
            }
            else {
                Log.d(getName(), "Resuming");
                onResuming();
            }
        }
        _paused = value;
    }

    @Override
    public final boolean isPaused() {
        return _paused;
    }

    @Override
    public final boolean isRunning() { return _running; }

    @Override
    public void onSizeChanged(int width, int height) {
        _width = width;
        _height = height;
    }

    @Override
    public void onReceivedMessage(IGameObject source, String message) {
    }

    //endregion

    //region Non-overridable Methods.

    /**
     * P|ay the specified media resource.
     * @param name The name of the media resource to play.
     */
    public final void playSound(String name) {
        // ToDo: Modify this to use a dictionary.
        if(_mediaPlayers.size() > 0) {
            _mediaPlayers.get(0).start();
        }
    }

    /**
     * Called to broadcast a message to all game objects.
     * @param message The message to broadcast.
     */
    protected final void broadcastMessage(String message) {
        _parentContainer.broadcastMessage(this, message);
    }

    /**
     * Wait while the game object is paused.
     */
    protected final void waitWhilePaused() {
        // ToDo: Can we use standard synchronization object instead?
        if(!_paused) return;

        Log.d(getName(), "Paused.");
        while(_paused) {
            try {
                sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(getName(), "Resumed.");
    }

    /**
     * Reset the timer for this game object.
     */
    protected final void resetTimer() {
        _stopwatch.reset();
    }

    /**
     * Get the current value of the timer for this game object.
     * @return The time elapsed in seconds.
     */
    protected final double getTimer() { return _stopwatch.elapsedTime(); }

    // State notification events.

    /**
     *  Called when the game object is ready to start doing work.
     *  This happens once the game has started and the display has valid size.
     */
    protected abstract void onReady();

    /**
     * Called when the game object is just about to go into the paused state.
     */
    protected abstract void onPausing();

    /**
     * Called when the game object is just about to go into the resumed state.
     */
    protected abstract void onResuming();

    //endregion
}
