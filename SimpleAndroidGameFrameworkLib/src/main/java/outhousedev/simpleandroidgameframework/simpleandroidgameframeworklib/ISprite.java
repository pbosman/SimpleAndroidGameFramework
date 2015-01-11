package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.graphics.RectF;
import android.view.View;

/**
 * The interface for a Sprite, extending IGameObject.
 * The interface defines support for the following functionality;
 *      Costume management
 *      Sprite collision sensing
 *      Ability to define sprite as draggable in one or both axes
 *      Ability to set the rotation of the sprite
 *      Ability to set/get the position of the sprite in absolute or relative coordinates.
 * ToDo: Rename or combine the methods for setting the absolute or relative position.
 */
public interface ISprite extends IGameObject {
    /**
     * Add a costume to the list of costumes in this sprite.
     * @param view The view containing the bitmap resource.
     * @param resourceId The resource id of the bitmap to add.
     */
    void addCostume(View view, int resourceId);

    /**
     * Go to the next costume if it exists.
     * If there isn't more than one costume, this does nothing.
     */
    void nextCostume();

    /**
     * Get the rectangle defining the bounds of the sprite, in screen coordinates.
     * @return The bounding rectangle of the sprite.
     */
    RectF getTargetRect();

    /**
     * Check if this sprite is touching or overlapping the specified sprite.
     * Return true if it is.
     * @param sprite The Sprite to check if it is touching.
     * @return True if the sprites are touching.
     */
    boolean isTouchingSprite(ISprite sprite);

    /**
     * Check if this sprite is touching or overlapping the sprite with the given name.
     * Return true if it is .
     * @param name
     * @return
     */
    boolean isTouchingSprite(String name);

    /**
     * Check if the specified coordinates are within the bounding rectangle of this sprite.
     * This can be used to detect if a user touch or click event is on this sprite.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the point is within this sprite.
     */
    boolean hitTest(float x, float y);

    /**
     * Check if the specified rectangle is touching or overlapping the
     * bounding rectangle of this sprite.
     * @param rect The rectangle to check against this sprite's bounding rectangle.
     * @return Return true if the specified rectangle touches this sprite.
     */
    boolean isTouchingRect(RectF rect);

    /**
     * Get the rectangle defining the touchable area for this sprite.
     * The touchable area is defined as the maximum of the bounding rectangle of
     * the sprite against the touch tolerance width and height.
     * @return The touchable area for this sprite.
     */
    RectF getTouchRect();

    /**
     * Set whether this sprite is draggable, specifying which directions it is draggable in.
     * By specifying whether the sprite is horizontally or vertically draggable,
     * or draggable in both directions, the framework will take care of handling touch events and
     * will move the sprite accordingly.
     * @param horizontallyDraggable
     * @param verticallyDraggable
     */
    void setDraggable(boolean horizontallyDraggable, boolean verticallyDraggable);

    /**
     * Set the initial position of the sprite in relative coordinates.
     * The sprite is moved to this initial position when the screen size is first set, when
     * the application first launches.
     * @param x The x coordinate of the center of the sprite.
     * @param y The y coordinate of the center of the sprite.
     */
    void setInitialPosition(float x, float y);

    /**
     * Set the size of the sprite in relative sizes.
     * Note: The total screen width and height are both 2 in relative coordinates.
     * The sprite is sized to these relative sizes whenever the view size changes.
     * (This happens initially when the view is first defined, but can also happen when the screen
     * is rotated because the device was rotated.)
     * If the width is specified with zero height, the height is calculated from the aspect ratio.
     * If the height is specified with zero width, the width is calculated from the aspect ratio.
     * If the width and height are both specified, the aspect ratio is ignored.
     * @param width The width in relative coordinates.
     * @param height The height in relative coordinates.
     */
    void setSize(float width, float height);

    /**
     * Sets the X position of the sprite in relative coordinates.
     * Note: The X axis relative coordinates are from -1 to 1, where -1 is the left of the screen.
     * @param value The X position in relative coordinates to set the sprite position to.
     */
    void setX(float value);

    /**
     * Sets the Y position of the sprite in relative coordinates.
     * Note: The Y axis relative coordinates are from -1 to 1, where -1 is the top of the screen.
     * @param value The Y position in relative coordinates to set the sprite position to.
     */
    void setY(float value);

    /**
     * Moves the sprite to the specified relative coordinate position.
     * Note:
     *      The X axis relative coordinates are from -1 to 1, where -1 is the left of the screen.
     *      The Y axis relative coordinates are from -1 to 1, where -1 is the top of the screen.
     * @param x The X position in relative coordinates to set the sprite position to.
     * @param y The Y position in relative coordinates to set the sprite position to.
     */
    void gotoXY(float x, float y);

    /**
     * Get the current X position of the sprite in relative coordinates.
     * Note: The X axis relative coordinates are from -1 to 1, where -1 is the left of the screen.
     * @return The X position of the sprite in relative coordinates.
     */
    float getX();

    /**
     * Get the current Y position of the sprite in relative coordinates.
     * Note: The Y axis relative coordinates are from -1 to 1, where -1 is the top of the screen.
     * @return The Y position of the sprite in relative coordinates.
     */
    float getY();

    /**
     * Get the current X position of the sprite in screen coordinates.
     * @return The X position of the sprite in screen coordinates.
     */
    float getXPosition();

    /**
     * Get the current Y position of the sprite in screen coordinates.
     * @return The Y position of the sprite in screen coordinates.
     */
    float getYPosition();

    /**
     * Set the rotation of the sprite to the specified angle (degrees).
     * @param rotation The rotation of the sprite in degrees.
     */
    void setRotation(float rotation);

    /**
     * Get the current rotation of the sprite in degrees.
     * @return The current rotation of the sprite in degrees.
     */
    float getRotation();
}
