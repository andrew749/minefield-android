package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class game extends SurfaceView implements SurfaceHolder.Callback {
//    public static ProximityMeter meter;
    public static Player player;
    public static Point userInput = new Point();
    public static int currentmap = 0;
    private GameThread thread;
    private BackgroundView backgroundView;
    int mapnumber = 0;

    public game(Context context, AttributeSet set) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        backgroundView = new BackgroundView(context, set, 0);
        thread = new GameThread(getHolder(), this, backgroundView);
        userInput.set(0, 0);
        //creates the player
        player = new Player(100, 100);
//creates the proximity meter
//        meter = new ProximityMeter(Backgroundview.map.get(currentmap), player, Backgroundview.screenHeight, Backgroundview.screenWidth);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (CollisionDetection.isCollidedWithMine(player, BackgroundView.map.get(currentmap))) {
            canvas.drawColor(Color.BLUE);
            //lost a life
        }
        if (CollisionDetection.isOnLevel(player, BackgroundView.map.get(currentmap))) {

            initializeNewLevel(BackgroundView.mapParser);
//            meter = new ProximityMeter(Backgroundview.map.get(currentmap), player,
//                    Backgroundview.screenHeight,
//                    Backgroundview.screenWidth);
        }
        if (CollisionDetection.isBlocked(player, BackgroundView.map.get(currentmap))) {
            player.movePlayer(new Point(0, 0));
        }
        player.update(userInput);
        player.draw(canvas);
//        meter.update(canvas, player, Backgroundview.map.get(currentmap));
        invalidate();
    }

    public void initializeNewLevel(GameMapParser map) {

        player.setCoordinates(map.tileDimension, map.tileDimension);
        currentmap++;
        destroyDrawingCache();

    }

    /*
    handle the touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP) {
            userInput.set((int) event.getX(), (int) event.getY());
        } else {
            userInput.set(0, 0);
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {

            try {

                thread.join();

                retry = false;

            } catch (InterruptedException e) {

                // try again shutting down the thread

            }

        }

    }
}
