package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

/**
 * Class to track motion of a sprite when it's being dragged.
 */
public class MotionTracker {
    private float _x;
    private float _y;
    private float _objX;
    private float _objY;
    private ISprite _sprite;

    /**
     * Construct the motion tracker, given the initial coordinates and the sprite.
     * @param x The initial x position of the sprite.
     * @param y The initial y position of the sprite.
     * @param sprite The sprite.
     */
    public MotionTracker(float x, float y, ISprite sprite) {
        _x = x;
        _y = y;
        _sprite = sprite;
        _objX = sprite.getXPosition();
        _objY = sprite.getYPosition();
    }

    /**
     * Get the sprite associated with this motion tracker.
     * @return
     */
    public ISprite getSprite() { return _sprite; }

    /**
     * Called when there is a touch move event.
     * The sprite is moved by the corresponding amount.
     * @param x The new x position of the touch point.
     * @param y The new y position of the touch point.
     */
    public void move(float x, float y) {
        _objX += x - _x;
        _objY += y - _y;
        _sprite.onMotionActionMove(_objX, _objY);
        _x = x;
        _y = y;
    }
}
