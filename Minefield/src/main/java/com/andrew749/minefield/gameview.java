package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.util.List;

/**
 * Created by andrew on 17/05/13.
 */
public class gameview extends View {
    static Player player;
    static Context cxt;
    static GameMap map;
    static int numberOfMaps=2;
    static int nextMap=1;
    static ProximityMeter meter;
    static boolean[][]mines;
    static Bitmap grasstile, rocktile,explosivetile,leveltile;
    static int[] maps=new int[numberOfMaps];
    private static float[] userInput=new float[2];
    public gameview(Context context) {
        super(context);
        cxt=context;
        player=new Player(100,100);
        userInput[0]=0;
        userInput[1]=0;
        grasstile= BitmapFactory.decodeResource(getResources(), R.raw.grass);
        rocktile= BitmapFactory.decodeResource(getResources(), R.raw.rock);
        explosivetile= BitmapFactory.decodeResource(getResources(), R.raw.tile);
        leveltile= BitmapFactory.decodeResource(getResources(), R.raw.level);
        /*trying to automatically create array
        for (int i=1;i<=numberOfMaps;i++){
            maps[i]=R.raw.map;
        }*/
        maps[0]=R.raw.map1;
        maps[1]=R.raw.map2;
        map=new GameMap(getResources().openRawResource(maps[0]));
        mines=map.mines();
        meter=new ProximityMeter(mines,player.getX(),player.getY());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!(GameMap.explosive[(int)player.getX()/30][(int)player.getY()/30])){
            if(!(GameMap.blocked[(int)player.getX()/30][(int)player.getY()/30])){
                map.drawMap(canvas,0,0,grasstile,explosivetile,rocktile,leveltile);

                meter.drawMeter(canvas, player.getX(),player.getY(),getWidth());
                player.update(userInput, canvas,determineSectionOfScreen(getWidth(),getHeight()));
                invalidate();
            }else{


            }
            if((GameMap.newLevel[(int)player.getX()/30][(int)player.getY()/30])){

                map=new GameMap(getResources().openRawResource(maps[nextMap]));
                player.setCoordinates(30,30);
                nextMap++;
            }

        }else{
            canvas.drawColor(Color.DKGRAY);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_MOVE||event.getAction()==MotionEvent.ACTION_UP) {
            userInput[0] = event.getX();
            userInput[1] = event.getY();
        }else{
            userInput[0]=0;
            userInput[1]=0;
        }
        return super.onTouchEvent(event);

    }
    public static String determineSectionOfScreen(int screenx, int screeny){
        if(userInput[0]<screenx/2&&userInput[1]>screeny-100&&userInput[1]<100){
            Log.d("left","left");

            return "Left";
        }
        if(userInput[0]>screenx/2&&userInput[1]>screeny-100&&userInput[1]<100){
            Log.d("right","right");

            return "Right";
        }
        if(userInput[1]>screeny/2&&userInput[0]>100&&userInput[0]<screenx-100){
            Log.d("down","down");

            return "Down";
        }
        if (userInput[1]<screeny/2&&userInput[0]>100&&userInput[0]<screenx-100){
            Log.d("up","up");

            return "Up";
        }
        else{
            return "No";
        }
    }

}
