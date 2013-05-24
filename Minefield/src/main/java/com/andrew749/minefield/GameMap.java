package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.io.*;
import java.util.Scanner;

/**
 * Created by andrew on 17/05/13.
 */
public class GameMap {
private static BufferedReader r;
    public GameMap(InputStream mapfile, int screenWidth){
        tileDimension=(int)Math.ceil(screenWidth/fileWidth);
        explosive=new boolean[fileWidth][fileHeight];
        blocked=new boolean[fileWidth][fileHeight];
        newLevel=new boolean[fileWidth][fileHeight];
        r = new BufferedReader(new InputStreamReader(mapfile));

        parseFile();
    }
    public static void clearMap(){
        purgeVariables();
    }
    public static boolean[][] explosive;
    public static boolean[][] blocked;
    public static boolean[][] newLevel;
    public static  int tileDimension;
    public static  int fileWidth=24;
    public static  int fileHeight=42;
    public static void parseFile(){
        for (int i =0;i<fileHeight;i++){
            for (int j=0;j<fileWidth;j++){
                String currentLetter=null;
                char letter='a';
                try {
                    letter = (char)r.read();
                    System.out.print(letter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentLetter=letter+"";
                if (currentLetter.equals("#")){
                    blocked[j][i]=true;
                }if (currentLetter.equals("X")){
                    explosive[j][i]=true;
                    Log.w("Success","Found an Explosive Block at x:"+j+" y:"+i);
                }if (currentLetter.equals("L")){
                    newLevel[j][i]=true;
                    Log.w("Success","Found a New Level Block at x:" + j+" y:"+i);

                }
            }
        }
    }
    public static void purgeVariables(){
        for (int y=0;y<explosive.length||y<blocked.length||y<newLevel.length;y++){
            for (int x=0;x<explosive.length||x<blocked.length||x<newLevel.length;x++){
                explosive[x][y]=false;
                blocked[x][y]=false;
                newLevel[x][y]=false;
            }
        }
    }
    public void drawMap(Canvas c, int screenWidth, int screenHeight, Bitmap gtile, Bitmap etile,Bitmap btile,Bitmap ntile){
        for (int i=0;i<fileHeight;i++){
            for (int j=0;j<fileWidth;j++){
                if(blocked[j][i]){
                    c.drawBitmap(btile,j*tileDimension,i*tileDimension,new Paint());

                }else if (explosive[j][i]){
                    c.drawBitmap(etile,j*tileDimension,i*tileDimension,new Paint());

                }else if (newLevel[j][i]){
                    c.drawBitmap(ntile,j*tileDimension,i*tileDimension,new Paint());

                }
                else{
                c.drawBitmap(gtile,j*tileDimension,i*tileDimension,new Paint());
                }
            }
        }
    }
    public static boolean[][] mines(){
        return explosive;
    }
}
