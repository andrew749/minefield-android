package com.andrew749.minefield;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by andrew on 17/05/13.
 */
public class ProximityMeter {
    public static boolean[][] landmines;
    public static int dangerState = 3;
    public static int x, y, blockx, blocky;
    static Paint textpaint;
    static Bitmap meters;
    private static Paint indicatorpaint;
    static Rect indic;

    public ProximityMeter(boolean[][] mines, int playerX, int playerY) {
        landmines = mines;
        textpaint = new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextSize(75);
        indicatorpaint = new Paint();
        indicatorpaint.setColor(Color.BLACK);
    }

    public static double determineDistanceToMine(int x, int y) {
        blockx = (int) x / 30;
        blocky = (int) y / 30;

        double distance = 0, tempDistance;
        int closestMineX = 10000, closestMineY = 10000;
        // iterate over all of the mines and determine the distances
        for (int j = 0; j < GameMap.fileHeight; j++) {
            for (int i = 0; i < GameMap.fileWidth; i++) {
                if (landmines[i][j]) {
                    tempDistance = Math.sqrt(Math.pow(i - blockx, 2)
                            + Math.pow(j - blocky, 2));

                    // sets the closest distance to the proximity
                    if (tempDistance < Math
                            .sqrt(((closestMineX * closestMineX))
                                    + ((closestMineY * closestMineY)))) {
                        closestMineX = i;
                        closestMineY = j;
                        distance = tempDistance;
                    }

                }
            }
        }
        return distance;
    }

    public void update(Canvas c, int playerx, int playery, int screenWidth, int screenHeight) {
        drawIndicator(c, playerx, playery, screenWidth, screenHeight);
    }

    /**
     * danger level of 1 is safest
     * 2 is less
     * 3 is worst
     *
     * @return
     */
    public static int determineDangerLevel(int x, int y) {
        if (determineDistanceToMine(x, y) < 3 && determineDistanceToMine(x, y) != 0) {
            dangerState = 3;
        } else if (determineDistanceToMine(x, y) < 6 && determineDistanceToMine(x, y) != 0) {
            dangerState = 2;
        } else {
            dangerState = 1;
        }
        return dangerState;
    }
//draw the gauge

    public void drawMeter(Bitmap meter, Canvas c, int screenX, int screenY) {
        meters = meter;
        Bitmap finalmeter = Bitmap.createScaledBitmap(meter, screenX, screenY, true);
        c.drawBitmap(finalmeter, 0, screenY - 300, new Paint());
    }

    public void drawIndicator(Canvas c, int playerx, int playery, int screenWidth, int screenHeight) {
        int dangerposition;
        dangerposition = (int) ((1 / (determineDistanceToMine(playerx, playery)) * screenWidth));
        indic = new Rect(dangerposition, screenHeight - 300, dangerposition + 20, screenHeight);
        c.drawRect(indic, indicatorpaint);
    }

    public Rect getmeterRect() {
        return indic;
    }

}
