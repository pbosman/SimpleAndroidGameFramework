package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;

/**
 * Interface defining the GameObjectContainer.
 */
public interface IGameObjectContainer {

    /**
     * Add a new game object to the container.
     * @param gameObject The game object to add.
     */
    void add(IGameObject gameObject);

    /**
     * Initialize the GameObjectContainer.
     * This initializes and starts the game loop and starts the threads
     * for the individual game objects.
     * @param surfaceHolder The view surface holder.
     * @param metrics The view metrics.
     */
    void initialize(SurfaceHolder surfaceHolder, DisplayMetrics metrics);

    /**
     * Set the paused state of the GameObjectContainer.
     * This is called by the GameSurface when the application pauses.
     * @param value True to pause, False to resume.
     */
    void setPaused(boolean value);

    /**
     * Gets the paused state of the GameObjectContainer.
     * @return True if paused.
     */
    boolean isPaused();

    /**
     * Called by the GameSurface when the view size changes.
     * @param width The height in pixels.
     * @param height The width in pixels.
     */
    void onSizeChanged(int width, int height);

    /**
     * Called by the GameSurface to draw the game scene.
     * @param canvas
     */
    void onDraw(Canvas canvas);

    /**
     * Called by the GameSurface when a touch event occurs.
     * @param event The touch event.
     * @return True if the touch event is processed.
     */
    boolean onTouchEvent(MotionEvent event);

    /**
     * Called by the game objects to check if specified sprite is touching source sprite.
     * @param sourceSprite The source sprite.
     * @param targetSpriteName The second sprite to check if touching against the first.
     * @return
     */
    boolean isTouchingSprite(ISprite sourceSprite, String targetSpriteName);

    /**
     * Called by the game objects to broadcast messages to the other game objects.
     * @param source The game object broadcasting the message.
     * @param message The message to be broadcast.
     */
    void broadcastMessage(IGameObject source, String message);
}
