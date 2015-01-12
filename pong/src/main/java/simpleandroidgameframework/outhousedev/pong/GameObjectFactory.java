package simpleandroidgameframework.outhousedev.pong;

import android.view.View;

import java.util.ArrayList;

import outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib.IGameObject;
import outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib.Sprite;
import outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib.Stage;
import simpleandroidgameframework.outhousedev.pong.R;

/**
 * Created by PBosman on 21/11/2014.
 */
public class GameObjectFactory {
    public ArrayList<IGameObject> createGameObjects(View view) {
        return createPongGameObjects(view);
    }

    private ArrayList<IGameObject> createPongGameObjects(View view) {
        ArrayList<IGameObject> gameObjects = new ArrayList<IGameObject>();

        Stage stage = new Stage();
        stage.setName("stage");
        stage.addBackdrop(view, R.drawable.pong_background);
        gameObjects.add(stage);

        Sprite bat1 = new Sprite();
        bat1.setName("bat1");
        bat1.addCostume(view, R.drawable.bat);
        bat1.setInitialPosition(-0.80f, 0);
        bat1.setDraggable(false, true);
        gameObjects.add(bat1);

        Sprite bat2 = new Sprite();
        bat2.setName("bat2");
        bat2.addCostume(view, R.drawable.bat);
        bat2.setInitialPosition(0.80f, 0);
        bat2.setDraggable(false, true);
        gameObjects.add(bat2);

        TestScore score1 = new TestScore();
        score1.setName("score1");
        score1.addCostume(view, R.drawable.pong_num1);
        score1.addCostume(view, R.drawable.pong_num2);
        score1.addCostume(view, R.drawable.pong_num3);
        score1.addCostume(view, R.drawable.pong_num4);
        score1.addCostume(view, R.drawable.pong_num5);
        score1.addCostume(view, R.drawable.pong_num6);
        score1.addCostume(view, R.drawable.pong_num7);
        score1.addCostume(view, R.drawable.pong_num8);
        score1.addCostume(view, R.drawable.pong_num9);
        score1.addCostume(view, R.drawable.pong_num10);
        score1.setInitialPosition(-0.25f, -0.80f);
        gameObjects.add(score1);

        TestScore score2 = new TestScore();
        score2.setName("score2");
        score2.addCostume(view, R.drawable.pong_num1);
        score2.addCostume(view, R.drawable.pong_num2);
        score2.addCostume(view, R.drawable.pong_num3);
        score2.addCostume(view, R.drawable.pong_num4);
        score2.addCostume(view, R.drawable.pong_num5);
        score2.addCostume(view, R.drawable.pong_num6);
        score2.addCostume(view, R.drawable.pong_num7);
        score2.addCostume(view, R.drawable.pong_num8);
        score2.addCostume(view, R.drawable.pong_num9);
        score2.addCostume(view, R.drawable.pong_num10);
        score2.setInitialPosition(0.25f, -0.80f);
        gameObjects.add(score2);

        TestBall ball = new TestBall();
        ball.setName("ball");
        ball.addCostume(view, R.drawable.ball);
        ball.setInitialPosition(0, 0);
        ball.addMediaResource(view, R.raw.ping_noise, "ping");
        gameObjects.add(ball);

        return gameObjects;
    }
}