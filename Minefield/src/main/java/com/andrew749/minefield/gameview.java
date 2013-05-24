package com.andrew749.minefield;

import android.content.Context;
import android.graphics.*;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;

import java.io.File;
import java.util.List;

/**
 * Created by andrew on 17/05/13.
 */
public class gameview extends View implements View.OnKeyListener {
    public static int screenWidth;
    static Player player;
    static Context cxt;
    static GameMap map;
    static int numberOfMaps = 2;
    static int nextMap = 1;
    static ProximityMeter meter;
    static String move = "";
    static int explosionCount=1;
    static boolean[][] mines;
    static Bitmap grasstile, rocktile, explosivetile, leveltile;
    static Bitmap[] explosionAnimation=new Bitmap[26];
    static int[] explosionpics = {R.raw.explosion1, R.raw.explosion2, R.raw.explosion3, R.raw.explosion4, R.raw.explosion5, R.raw.explosion6, R.raw.explosion7, R.raw.explosion8, R.raw.explosion9, R.raw.explosion10, R.raw.explosion11, R.raw.explosion12, R.raw.explosion13, R.raw.explosion14, R.raw.explosion15, R.raw.explosion16, R.raw.explosion17, R.raw.explosion18, R.raw.explosion19, R.raw.explosion20, R.raw.explosion21, R.raw.explosion22, R.raw.explosion23, R.raw.explosion24, R.raw.explosion25};
    static int[] maps = new int[numberOfMaps];
    private static float[] userInput = new float[2];

    public gameview(Context context) {
        super(context);
        this.setOnKeyListener(this);
        for (int i = 1; i <= 25; i++) {
            try{
            explosionAnimation[i] = BitmapFactory.decodeResource(getResources(),explosionpics[i]);
            Log.d("Image Status", "Loaded image "+i);
            }catch (ArrayIndexOutOfBoundsException e){}
        }
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        Log.d("", "ScreenX:" + metrics.widthPixels);
        screenWidth = metrics.widthPixels;
        cxt = context;
        player = new Player(100, 100);
        userInput[0] = 0;
        userInput[1] = 0;
        grasstile = BitmapFactory.decodeResource(getResources(), R.raw.grass);
        rocktile = BitmapFactory.decodeResource(getResources(), R.raw.rock);
        explosivetile = BitmapFactory.decodeResource(getResources(), R.raw.tile);
        leveltile = BitmapFactory.decodeResource(getResources(), R.raw.level);
        /*trying to automatically create array
        for (int i=1;i<=numberOfMaps;i++){
            maps[i]=R.raw.map;
        }*/
        maps[0] = R.raw.map1;
        maps[1] = R.raw.map2;
        map = new GameMap(getResources().openRawResource(maps[0]), screenWidth);
        mines = map.mines();
        meter = new ProximityMeter(mines, player.getX(), player.getY());
    }

    public static String determineSectionOfScreen(int screenx, int screeny) {
        if (userInput[0] < screenx / 2 && userInput[1] > screeny - 100 && userInput[1] < 100) {
            Log.d("left", "left");

            return "Left";
        }
        if (userInput[0] > screenx / 2 && userInput[1] > screeny - 100 && userInput[1] < 100) {
            Log.d("right", "right");

            return "Right";
        }
        if (userInput[1] > screeny / 2 && userInput[0] > 100 && userInput[0] < screenx - 100) {
            Log.d("down", "down");

            return "Down";
        }
        if (userInput[1] < screeny / 2 && userInput[0] > 100 && userInput[0] < screenx - 100) {
            Log.d("up", "up");

            return "Up";
        } else {
            return "No";
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(GameMap.explosive[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {
            if (!(GameMap.blocked[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {
                map.drawMap(canvas, 0, 0, grasstile, explosivetile, rocktile, leveltile);

                meter.drawMeter(canvas, player.getX(), player.getY(), getWidth());
                player.update(userInput, canvas, determineSectionOfScreen(getWidth(), getHeight()));
                invalidate();
            } else {


            }
            if ((GameMap.newLevel[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {

                map = new GameMap(getResources().openRawResource(maps[nextMap]), screenWidth);
                player.setCoordinates(map.tileDimension, map.tileDimension);
                nextMap++;
                postInvalidate();
                Log.d("", "Loaded new map");
            }

        } else {
                try {
                    map.drawMap(canvas, 0, 0, grasstile, explosivetile, rocktile, leveltile);
                    canvas.drawBitmap(explosionAnimation[explosionCount], player.getX(), player.getY(), new Paint());
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
            case KeyEvent.KEYCODE_ENTER:
         /* This is a sample for handling the Enter button */
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                return true;
        }
        return false;
    }
}
