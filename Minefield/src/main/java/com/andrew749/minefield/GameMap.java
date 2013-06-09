package com.andrew749.minefield;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by andrew on 17/05/13.
 */
public class GameMap {
    public static boolean[][] explosive;
    public static boolean[][] blocked;
    public static boolean[][] newLevel;
    public static int tileDimension;
    public static int fileWidth = 24;
    public static int fileHeight = 42;
    private static BufferedReader r;
    private static Paint backgroundtile;
    private static Paint blockedtile;
    private static Paint warpTile;
    private static Paint leveltile;
    public static Rect[][] mines;

    public GameMap(InputStream mapfile, int screenWidth) {
        tileDimension = (int) Math.ceil(screenWidth / fileWidth);
        explosive = new boolean[fileWidth][fileHeight];
        blocked = new boolean[fileWidth][fileHeight];
        newLevel = new boolean[fileWidth][fileHeight];
        r = new BufferedReader(new InputStreamReader(mapfile));
        backgroundtile = new Paint();
        leveltile = new Paint();
        blockedtile = new Paint();
        warpTile = new Paint();
        mines = new Rect[100][100];
        backgroundtile.setColor(Color.BLACK);
        blockedtile.setColor(Color.WHITE);
        leveltile.setColor(Color.GRAY);
        parseFile();
    }

    public static void clearMap() {
        purgeVariables();
    }

    public static void parseFile() {
        for (int i = 0; i <= fileHeight; i++) {
            for (int j = 0; j <= fileWidth; j++) {
                try {
                    String currentLetter = null;
                    char letter = 'a';
                    try {
                        letter = (char) r.read();
                        System.out.print(letter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentLetter = letter + "";
                    if (currentLetter.equals("#")) {
                        blocked[j][i] = true;
                    }
                    if (currentLetter.equals("X")) {
                        explosive[j][i] = true;
                        Log.w("Success", "Found an Explosive Block at x:" + j + " y:" + i);
                    }
                    if (currentLetter.equals("L")) {
                        newLevel[j][i] = true;
                        Log.w("Success", "Found a New Level Block at x:" + j + " y:" + i);

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    public static void purgeVariables() {
        for (int y = 0; y < explosive.length || y < blocked.length || y < newLevel.length; y++) {
            for (int x = 0; x < explosive.length || x < blocked.length || x < newLevel.length; x++) {
                explosive[x][y] = false;
                blocked[x][y] = false;
                newLevel[x][y] = false;
                mines[x][y] = null;
            }
        }
    }

    public static boolean[][] mines() {
        return explosive;
    }

    public void drawMap(Canvas c) {
        for (int i = 0; i <= fileHeight; i++) {
            for (int j = 0; j <= fileWidth; j++) {
                try {
                    Rect r = new Rect(j * tileDimension, i * tileDimension, (j + 1) * tileDimension,
                            (i + 1) * tileDimension);
                    mines[j][i] = r;
                    if (blocked[j][i]) {
                        c.drawRect(r, blockedtile);
                    } else if (explosive[j][i]) {
                        c.drawRect(r, blockedtile);
                    } else if (newLevel[j][i]) {
                        c.drawRect(r, leveltile);

                    } else {
                        c.drawRect(r, backgroundtile);

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }
}
