package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by andrew on 28/05/13.
 */
public class Game {
    public static int screenWidth, screenHeight;
    static Runnable game = new Runnable() {
        @Override
        public void run() {
            if (!(GameMap.explosive[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {
                if (!(GameMap.blocked[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {
                    map.drawMap(canvas);
                    meter.drawMeter(canvas, player.getX(), player.getY(), 100);
                    meter.drawMeter(proximityGauge, canvas, screenWidth, screenHeight);
                    player.update(userInput, canvas);
                } else {


                }
                if ((GameMap.newLevel[(int) player.getX() / map.tileDimension][(int) player.getY() / map.tileDimension])) {

                    map = new GameMap(c.getResources().openRawResource(maps[nextMap]), screenWidth);
                    player.setCoordinates(map.tileDimension, map.tileDimension);
                    nextMap++;
                    Log.d("", "Loaded new map");
                }

            } else {
                try {
                    map.drawMap(canvas);
                    canvas.drawBitmap(explosionAnimation[explosionCount], player.getX(), player.getY(), new Paint());
                    Thread.sleep(100);
                    explosionCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    static Player player;
    static Context cxt;
    static GameMap map;
    static int numberOfMaps = 2;
    static int nextMap = 1;
    static ProximityMeter meter;
    static int explosionCount = 1;
    static boolean[][] mines;
    static Bitmap grasstile, rocktile, explosivetile, leveltile;
    static Bitmap[] explosionAnimation = new Bitmap[26];
    static Bitmap proximityGauge, proximityIndicator;
    static int[] explosionpics = {R.raw.explosion1, R.raw.explosion2, R.raw.explosion3, R.raw.explosion4, R.raw.explosion5, R.raw.explosion6, R.raw.explosion7, R.raw.explosion8, R.raw.explosion9, R.raw.explosion10, R.raw.explosion11, R.raw.explosion12, R.raw.explosion13, R.raw.explosion14, R.raw.explosion15, R.raw.explosion16, R.raw.explosion17, R.raw.explosion18, R.raw.explosion19, R.raw.explosion20, R.raw.explosion21, R.raw.explosion22, R.raw.explosion23, R.raw.explosion24, R.raw.explosion25};
    static int[] maps = new int[numberOfMaps];
    static Context c;
    static Canvas canvas;
    static Handler handler;
    private static float[] userInput = new float[2];

    public Game(Context context, Canvas can) {
        c = context;
        canvas = can;
    }

    public static void runGame() {
        handler.publish(null);
    }

    public static void initialize() {
        handler = new Handler() {

            @Override
            public void close() {

            }

            @Override
            public void flush() {

            }

            @Override
            public void publish(LogRecord logRecord) {
                game.run();
            }
        };
        for (int i = 1; i <= 25; i++) {
            try {
                explosionAnimation[i] = BitmapFactory.decodeResource(c.getResources(), explosionpics[i]);
                Log.d("Image Status", "Loaded image " + i);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        Log.d("", "ScreenX:" + metrics.widthPixels);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        player = new Player(100, 100);
        userInput[0] = 0;
        userInput[1] = 0;
        grasstile = BitmapFactory.decodeResource(c.getResources(), R.raw.grass);
        rocktile = BitmapFactory.decodeResource(c.getResources(), R.raw.rock);
        explosivetile = BitmapFactory.decodeResource(c.getResources(), R.raw.tile);
        leveltile = BitmapFactory.decodeResource(c.getResources(), R.raw.level);
        proximityGauge = BitmapFactory.decodeResource(c.getResources(), R.raw.gauge);
        maps[0] = R.raw.map1;
        maps[1] = R.raw.map2;
        map = new GameMap(c.getResources().openRawResource(maps[0]), screenWidth);
        mines = map.mines();
        meter = new ProximityMeter(mines, player.getX(), player.getY());
    }

}
