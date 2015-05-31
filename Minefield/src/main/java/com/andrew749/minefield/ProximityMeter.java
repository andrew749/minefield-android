package com.andrew749.minefield;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by andrew on 17/05/13.
 */
public class ProximityMeter {
    public int dangerState = 3;
    public int x, y, blockx, blocky;
    static Paint textpaint;
    static Bitmap meters;
    private static Paint indicatorpaint;
    static Rect indic;
    static int screenHeight, screenWidth;

    public ProximityMeter(MinefieldMap map, Player player, int screenH, int screenW) {
        textpaint = new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextSize(75);
        indicatorpaint = new Paint();
        indicatorpaint.setColor(Color.BLACK);
        screenHeight = screenH;
        screenWidth = screenW;
        indic = new Rect(0, screenHeight - 300, 20, screenHeight);

    }

    public double determineDistanceToMine(MinefieldMap map, Player player) {
        blockx = (int) player.getX() / 30;
        blocky = (int) player.getY() / 30;

        double distance = 0, tempDistance = 0;
        int closestMineX = 10000, closestMineY = 10000;
        for (int i = 0; i < map.mineBlocks.size(); i++) {
            //not workign
            try {
                tempDistance = Math.sqrt(Math.pow((int) map.mineBlocks.get(i).centerX() - blockx,
                        2) + Math.pow((int) map.mineBlocks.get(i).centerY() - blocky, 2));
            } catch (NullPointerException e) {
                Log.d("Exception", "Null pointer");
            }
            // sets the closest distance to the proximity
            if (tempDistance < Math
                    .sqrt(((closestMineX * closestMineX))
                            + ((closestMineY * closestMineY)))) {
                closestMineX = (int) map.mineBlocks.get(i).centerX();
                closestMineY = (int) map.mineBlocks.get(i).centerY();
                distance = tempDistance;
            }

        }


        return distance;
    }

    public void update(Canvas c, Player player, MinefieldMap map) {
        drawIndicator(c, player, map);
    }

//draw the gauge

    public static void drawMeter(Bitmap meter, Canvas c) {
        meters = meter;
        Bitmap finalmeter = Bitmap.createScaledBitmap(meter, screenWidth, screenHeight, true);
        c.drawBitmap(finalmeter, 0, screenHeight - 300, new Paint());
    }

    public void drawIndicator(Canvas c, Player player, MinefieldMap map) {
        int dangerposition;
        dangerposition = (int) ((1 / (determineDistanceToMine(map, player)) * screenWidth));
        indic = new Rect(dangerposition, screenHeight - 300, dangerposition + 20, screenHeight);
        c.drawRect(indic, indicatorpaint);
    }

    public Rect getmeterRect() {
        return indic;
    }

}
