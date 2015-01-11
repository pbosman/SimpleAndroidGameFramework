package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

import android.view.View;

/**
 * The interface for a Stage, extending IGameObject.
 * The interface defines support for the following functionality;
 *      Backdrop management
 */
public interface IStage extends IGameObject {

    void addBackdrop(View view, int resourceId);
    void nextBackdrop();
}
