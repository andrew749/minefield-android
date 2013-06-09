package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    static Player player;
    static Context cxt;
    static GameMap map;
    static int numberOfMaps = 3;
    static int nextMap = 1;
    static boolean init = true;
    static ProximityMeter meter;
    static int explosionCount = 1;
    static boolean[][] mines;
    static Bitmap[] explosionAnimation = new Bitmap[26];
    static Bitmap proximityGauge, proximityIndicator;
    static int[] maps = new int[numberOfMaps];
    private static float[] userInput = new float[2];

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
        player = new Player(100, 100);
        userInput[0] = 0;
        userInput[1] = 0;

        proximityGauge = BitmapFactory.decodeResource(getResources(), R.drawable.gauge);

        maps[0] = R.raw.map1;
        maps[1] = R.raw.map2;
        maps[2] = R.raw.map3;
        map = new GameMap(getResources().openRawResource(maps[0]), screenWidth);
        mines = map.mines();
        meter = new ProximityMeter(mines, player.getX(), player.getY());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (init) {
            map.drawMap(canvas);
            meter.drawMeter(proximityGauge, canvas, screenWidth, screenHeight);
            player.update(userInput, canvas);
            init = false;
        } else {
            if (!(GameMap.explosive[(int) player.getX() / map.tileDimension][(int) player.getY() / map
                    .tileDimension])) {
                if (!(GameMap.blocked[(int) player.getX() / map.tileDimension][(int) player.getY() / map
                        .tileDimension])) {
                    map.drawMap(canvas);
                    meter.drawMeter(proximityGauge, canvas, screenWidth, screenHeight);
                    player.update(userInput, canvas);
                    meter.update(canvas, player.getX(), player.getY(), screenWidth, screenHeight);
                    invalidate(meter.getmeterRect());

                } else {


                }
                if ((GameMap.newLevel[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {
                    userInput[0] = 0;
                    userInput[1] = 0;
                    map = new GameMap(getResources().openRawResource(maps[nextMap]), screenWidth);
                    meter = new ProximityMeter(mines, player.getX(), player.getY());
                    player.setCoordinates(map.tileDimension, map.tileDimension);
                    nextMap++;
                    Log.d("", "Loaded new map");
                    init = true;
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP) {
            userInput[0] = event.getX();
            userInput[1] = event.getY();
        } else {
            userInput[0] = 0;
            userInput[1] = 0;
        }
        return super.onTouchEvent(event);
    }

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

    public Player getPlayer() {
        return player;
    }
}
