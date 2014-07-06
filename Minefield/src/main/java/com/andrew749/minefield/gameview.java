package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by andrew on 17/05/13.
 */
public class gameview extends View implements View.OnKeyListener {
    public static int screenWidth, screenHeight;
    Player player;
    Context cxt;
    GameMap map;
    int numberOfMaps = 2;
    int nextMap = 1;
    ProximityMeter meter;
    int explosionCount = 1;
    boolean[][] mines;
    boolean init = true;
    boolean running = true;
    Bitmap[] explosionAnimation = new Bitmap[26];
    //    static Bitmap proximityGauge, proximityIndicator;
    int[] maps = new int[numberOfMaps];
    private float[] userInput = new float[2];


    public gameview(Context context) {
        super(context);
        this.setOnKeyListener(this);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        Log.d("", "ScreenX:" + metrics.widthPixels);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        cxt = context;
        player = new Player(getContext(), 100, 100);
        userInput[0] = 0;
        userInput[1] = 0;

//        proximityGauge = BitmapFactory.decodeResource(getResources(), R.drawable.gauge);

        maps[0] = R.raw.map1;
        maps[1] = R.raw.map2;
        map = new GameMap(getResources().openRawResource(maps[0]), screenWidth);
        mines = map.mines();
        meter = new ProximityMeter(mines, player.getX(), player.getY());
    }


    //runs the player updating thread
    Thread mainGame = new Thread() {
        @Override
        public void run() {
            while (running) {


                player.update(userInput);
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (init) {
            mainGame.start();
            init = false;
        }
        map.drawMap(canvas);
//            meter.drawMeter(proximityGauge, canvas, screenWidth, screenHeight);
        player.draw(canvas);


        if (!(GameMap.explosive[(int) player.getX() / map.tileDimension][(int) player.getY() / map
                .tileDimension])) {
            if (!(GameMap.blocked[(int) player.getX() / map.tileDimension][(int) player.getY() / map
                    .tileDimension])) {
                map.drawMap(canvas);
                player.draw(canvas);
//                    meter.drawMeter(proximityGauge, canvas, screenWidth, screenHeight);
//                    meter.update(canvas, player.getX(), player.getY(), screenWidth, screenHeight);
//                    invalidate(meter.getmeterRect());
                invalidate(player.playerrect);

            }
            if ((GameMap.newLevel[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {
                userInput[0] = 0;
                userInput[1] = 0;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                map = new GameMap(getResources().openRawResource(maps[nextMap]), screenWidth);
                meter = new ProximityMeter(mines, player.getX(), player.getY());
                player.setCoordinates(map.tileDimension, map.tileDimension);
                nextMap++;
                Log.d("", "Loaded new map");


                invalidate();
            }

        } else {
            try {
                map.drawMap(canvas);
                canvas.drawText("you lose", 0, 0, new Paint());
                Thread.sleep(100);
                invalidate();
                explosionCount++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Minefield", "User Input at x=" + event.getX() + " and y=" + event.getY());
        Log.d("Minefield", "differences are x=" + Math.abs(player.getX() - event.getX()) + " and y=" + Math.abs(player.getY() - event.getY()));
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(player.getX() - event.getX()) < 50 && Math.abs(player
                    .getY() - event.getY()) < 50) {
                //TODO check position of user to ensure they are generally within the previous touch area
                userInput[0] = event.getX();
                userInput[1] = event.getY();
                Log.d("Minefield", "CHANGING POSITION");
            }
        } /*else {
            userInput[0] = 0;
            userInput[1] = 0;
        }*/
        return true;
    }

    /*Handle key clicks if being played from a gamepad*/
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                userInput[1] = player.getY() + 20;
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                userInput[1] = player.getY() - 20;

                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                userInput[0] = player.getX() - 20;
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                userInput[0] = player.getX() + 20;
                return true;
        }
        return false;
    }

}
