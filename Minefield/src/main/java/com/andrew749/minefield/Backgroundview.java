package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.andrew749.minefield.R;

import java.util.ArrayList;

/**
 * game view will exclusively deal with drawing the background of the gauge and the tiled map
 */
public class BackgroundView extends View {
    public static int screenWidth, screenHeight;
    static Context cxt;
    public static GameMapParser mapParser;
    public static int numberOfMaps = 4;
    public static ArrayList<MinefieldMap> map = new ArrayList<MinefieldMap>();
    public static Bitmap proximityGauge;
    public static int[] maps = new int[numberOfMaps];


    public BackgroundView(Context context, AttributeSet set, int mapnumber) {
        super(context);
        //get display properties
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        Log.d("", "ScreenX:" + metrics.widthPixels);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        //create the bitmap for the proximity gauge
        proximityGauge = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        cxt = context;


        //create all the map objects
        for (int i = 0; i < maps.length; i++) {
            map.add(mapParser.getMap());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mapParser.drawMap(canvas);
        ProximityMeter.drawMeter(proximityGauge, canvas);

    }
}

