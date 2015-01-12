package simpleandroidgameframework.outhousedev.pong;

/**
 * Created by PBosman on 21/11/2014.
 */


import outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib.IGameObject;
import outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib.Sprite;

/**
 * Created by Administrator on 04/11/2014.
 */
public class TestScore extends Sprite {

    @Override
    public void onReceivedMessage(IGameObject source, String message) {
        if(getName().equals("score1") && message.equals("bat1hit")) {
            nextCostume();
            return;
        }

        if(getName().equals("score2") && message.equals("bat2hit")) {
            nextCostume();
            return;
        }
    }
}
