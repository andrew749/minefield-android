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
    static int numberOfMaps = 2;
    static int nextMap = 1;
    static boolean init = true;
    static ProximityMeter meter;
    static int explosionCount = 1;
    static boolean[][] mines;
    static Bitmap[] explosionAnimation = new Bitmap[26];
    static Bitmap proximityGauge, proximityIndicator;
    static int[] explosionpics = {R.raw.explosion1, R.raw.explosion2, R.raw.explosion3, R.raw.explosion4, R.raw.explosion5, R.raw.explosion6, R.raw.explosion7, R.raw.explosion8, R.raw.explosion9, R.raw.explosion10, R.raw.explosion11, R.raw.explosion12, R.raw.explosion13, R.raw.explosion14, R.raw.explosion15, R.raw.explosion16, R.raw.explosion17, R.raw.explosion18, R.raw.explosion19, R.raw.explosion20, R.raw.explosion21, R.raw.explosion22, R.raw.explosion23, R.raw.explosion24, R.raw.explosion25};
    static int[] maps = new int[numberOfMaps];
    private static float[] userInput = new float[2];

    public gameview(Context context) {
        super(context);
        this.setOnKeyListener(this);
        for (int i = 1; i <= 25; i++) {
            try {
                explosionAnimation[i] = BitmapFactory.decodeResource(getResources(), explosionpics[i]);
                Log.d("Image Status", "Loaded image " + i);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
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

        proximityGauge = BitmapFactory.decodeResource(getResources(), R.raw.gauge);

        maps[0] = R.raw.map1;
        maps[1] = R.raw.map2;
        map = new GameMap(getResources().openRawResource(maps[0]), screenWidth);
        mines = map.mines();
        meter = new ProximityMeter(mines, player.getX(), player.getY());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (init) {
            map.drawMap(canvas);
            player.update(userInput, canvas);
            init = false;
        } else {
            if (!(GameMap.explosive[(int) player.getX() / map.tileDimension][(int) player.getY() / map
                    .tileDimension])) {
                if (!(GameMap.blocked[(int) player.getX() / map.tileDimension][(int) player.getY() / map
                        .tileDimension])) {

                    map.drawMap(canvas);
                    meter.drawMeter(canvas, player.getX(), player.getY(), getWidth());
                    meter.drawMeter(proximityGauge, canvas, screenWidth, screenHeight);
                    player.update(userInput, canvas);

                } else {


                }
                if ((GameMap.newLevel[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {

                    map = new GameMap(getResources().openRawResource(maps[nextMap]), screenWidth);
                    player.setCoordinates(map.tileDimension, map.tileDimension);
                    nextMap++;
                    Log.d("", "Loaded new map");
                    init = true;
                }

            } else {
                try {
                    map.drawMap(canvas);
                    canvas.drawBitmap(explosionAnimation[explosionCount], player.getX(), player.getY(), new Paint());
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
