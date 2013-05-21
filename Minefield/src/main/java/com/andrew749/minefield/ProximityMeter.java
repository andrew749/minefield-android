package com.andrew749.minefield;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by andrew on 17/05/13.
 */
public class ProximityMeter {
    public static boolean[][]landmines;
    public static int dangerState=3;
    public static int x,y, blockx,blocky;
    static Paint textpaint;
    public ProximityMeter(boolean[][]mines, int playerX, int playerY){
        landmines=mines;
        textpaint=new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextSize(75);
    }
    public void drawMeter(Canvas c, int x, int y, int screenWidth){
        c.drawText(dangerLevelString(x,y),screenWidth-300,100, textpaint);
    }
    public static double determineDistanceToMine(int x, int y){
        blockx=(int)x/30;
        blocky=(int)y/30;

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
public void update(Canvas c, int playerx,int playery,int screenWidth){
drawMeter(c, playerx,playery,screenWidth);
}
    /**
     * danger level of 1 is closest
     * 2 is safer
     * 3 is safest
     * @return
     */
    public static int determineDangerLevel(int x, int y){
        if(determineDistanceToMine(x,y)<3&&determineDistanceToMine(x,y)!=0){
            dangerState=1;
        }else if (determineDistanceToMine(x,y)<6&&determineDistanceToMine(x,y)!=0){
            dangerState=2;
        }else{
            dangerState=3;
        }
      return dangerState;
    }
    public static String dangerLevelString(int x, int y){
        if(determineDangerLevel(x,y)==1){
            return "Danger";
        }else if (determineDangerLevel(x,y)==2){
            return "Warning";
        }else{
            return "Safe";
        }
    }
}
