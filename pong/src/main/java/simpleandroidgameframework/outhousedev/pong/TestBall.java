package simpleandroidgameframework.outhousedev.pong;

import android.util.Log;

import outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib.Sprite;

/**
 * Created by Administrator on 01/11/2014.
 */
public class TestBall extends Sprite {
    float Ox = 0;
    float Oy = 0;
    float Vx = -50;
    float Vy = -50;
    float X;
    float Y;
    float t0;

    @Override
    public void onPausing() {
        Oy = Y;
        Ox = X;
        t0 = (float)getTimer();
    }

    @Override
    public void onResuming() {
        t0 = (float)getTimer();
    }

    @Override
    public void onReady()
    {
        super.onReady();

        boolean _touchingBat1 = false;
        boolean _touchingBat2 = false;
        t0 = (float)getTimer();

        int top = -100;
        int bottom = 100;
        int left = -100;
        int right = 100;

        gotoXY(0, 0);

        while(isRunning()) {
            waitWhilePaused();

            // ToDo: Fix bug: should be getTimer - t0. This will mess things up since t will no longer be +ve.
            float t = t0 - (float)getTimer();

            X = Ox + Vx * t;
            Y = Oy + Vy * t;

            gotoXY(X, Y);

            if(getY() <= top) {
                Vy = Vy * -1;
                Ox = X;
                Oy = top;
                t0 = (float)getTimer();
                continue;
            }

            if(getX() < left) {
                Vx = Vx * -1;
                Oy = Y;
                Ox = left;
                t0 = (float)getTimer();
                continue;
            }

            if(getY() > bottom) {
                Vy = Vy * -1;
                Ox = X;
                Oy = bottom;
                t0 = (float)getTimer();
                continue;
            }

            if(getX() > right) {
                Vx = Vx * -1;
                Oy = Y;
                Ox = right;
                t0 = (float)getTimer();
                continue;
            }

            if(isTouchingSprite("bat1")) {
                if(!_touchingBat1) {
                    // Only count hit if ball approaching from right.
                    if(Vx > 0f) {
                        playSound("ping");
                        broadcastMessage("bat1hit");
                    }
                    Vx = Vx * -1;
                    Oy = Y;
                    Ox = X;
                    t0 = (float)getTimer();
                    _touchingBat1 = true;
                }
                continue;
            } else {
                _touchingBat1 = false;
            }

            if(isTouchingSprite("bat2")) {
                if(!_touchingBat2) {
                    // Only count hit if ball approaching from left.
                    if(Vx < 0f) {
                        playSound("pong");
                        broadcastMessage("bat2hit");
                    }
                    Vx = Vx * -1;
                    Oy = Y;
                    Ox = X;
                    t0 = (float)getTimer();
                    _touchingBat2 = true;
                }
                continue;
            } else {
                _touchingBat2 = false;
            }
        }

        Log.d("TestBall", "Ball thread is exiting!");
    }
}
