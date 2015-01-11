package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.graphics.Canvas;
import android.view.View;

/**
 * IGameObject defines the interface used by the GameObjectController
 * for initializing and controlling the game object.
 */
public interface IGameObject {
    /**
     * Gets the name of this game object.
     * @return The name of the game object.
     */
    String getName();

    /**
     * Called to initialize the game object.
     * This will cause the game object thread to start.
     * @param parentContainer The parent container
     * @param defaultToleranceWidth The default touch tolerance width in pixels.
     * @param defaultToleranceHeight The default touch tolerance height in pixels.
     */
    void initialize(IGameObjectContainer parentContainer, float defaultToleranceWidth, float defaultToleranceHeight);

    /**
     * Add a media resource to this game object.
     * @param view The view from which to retrieve the media resource.
     * @param resourceId The resource id.
     * @param soundName A unique name to be used to identify the media resource.
     */
    void addMediaResource(View view, int resourceId, String soundName);

    /**
     * Set the game object state to paused.
     * This will cause the thread to stop executing the game
     * code only when a call is made to waitWhilePaused.
     * @param value True to specify paused.
     */
    void setPaused(boolean value);

    /**
     * Get the paused state.
     * @return True if the game object is paused.
     */
    boolean isPaused();

    /**
     * Get the running state.
     * @return True if the game object is running.
     */
    boolean isRunning();

    /**
     * Called to render the game object.
     * @param canvas The canvas to render the game object to.
     */
    void onDraw(Canvas canvas);

    /**
     * Called when the size of the view changes.
     * @param width The new view width.
     * @param height The new view height.
     */
    void onSizeChanged(int width, int height);

    /**
     * Called by the GameObjectCollection to deliver a message
     * to the game object.
     * @param source The IGameObject where the message originated from.
     * @param message The message.
     */
    void onReceivedMessage(IGameObject source, String message);

    /**
     * Called by the GameObjectCollection when there is a motion down event.
     * @param x The x position of the event.
     * @param y The y position of the event.
     */
    void onMotionActionDown(float x, float y);

    /**
     * Called by the GameObjectCollection when there is a motion move event.
     * @param x The x position of the event.
     * @param y The y position of the event.
     */
    void onMotionActionMove(float x, float y);

    /**
     * Called by the GameObjectCollection when there is a motion up event.
     * @param x The x position of the event.
     * @param y The y position of the event.
     */
    void onMotionActionUp(float x, float y);
}
