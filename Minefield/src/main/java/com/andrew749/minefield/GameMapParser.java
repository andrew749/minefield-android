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
import java.util.ArrayList;

/**
 * Created by andrew on 17/05/13.
 */
public class GameMapParser {
    boolean[][] explosive;
    boolean[][] blocked;
    boolean[][] newLevel;
    boolean[][] normalblock;
    public int tileDimension;
    public int fileWidth = 24;
    public int fileHeight = 42;
    private BufferedReader r;
    private Paint backgroundtile = new Paint();
    private Paint blockedtile = new Paint();
    private Paint warpTile = new Paint();
    private Paint leveltile = new Paint();
    MinefieldMap map;
    public ArrayList<Rect> mines = new ArrayList<Rect>();
    public ArrayList<Rect> levels = new ArrayList<Rect>();
    public ArrayList<Rect> block = new ArrayList<Rect>();
    public ArrayList<Rect> normal = new ArrayList<Rect>();
    public ArrayList<MinefieldMap> maps = new ArrayList<MinefieldMap>();

    public GameMapParser(InputStream mapfile, int screenWidth) {
        tileDimension = (int) Math.ceil(screenWidth / fileWidth);
        r = new BufferedReader(new InputStreamReader(mapfile));
        backgroundtile.setColor(Color.BLACK);
        blockedtile.setColor(Color.WHITE);
        leveltile.setColor(Color.GRAY);
        parseFile();
    }

    public void parseFile() {
        explosive = new boolean[fileWidth][fileHeight];
        blocked = new boolean[fileWidth][fileHeight];
        newLevel = new boolean[fileWidth][fileHeight];
        normalblock = new boolean[fileWidth][fileHeight];
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
                    if (currentLetter.equals("O")) {
                        normalblock[j][i] = true;

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }

    }

    public void purgeVariables() {
        for (int y = 0; y < explosive.length || y < blocked.length || y < newLevel.length; y++) {
            for (int x = 0; x < explosive.length || x < blocked.length || x < newLevel.length; x++) {
                explosive[x][y] = false;
                blocked[x][y] = false;
                newLevel[x][y] = false;

            }
        }
    }


    public void drawMap(Canvas c) {
        for (int i = 0; i <= fileHeight; i++) {
            for (int j = 0; j <= fileWidth; j++) {
                try {
                    Rect r = new Rect(j * tileDimension, i * tileDimension, (j + 1) * tileDimension,
                            (i + 1) * tileDimension);
                    if (blocked[j][i]) {
                        c.drawRect(r, blockedtile);
                        block.add(r);
                    } else if (explosive[j][i]) {
                        c.drawRect(r, blockedtile);
                        mines.add(r);
                    } else if (newLevel[j][i]) {
                        c.drawRect(r, leveltile);
                        levels.add(r);

                    } else {
                        c.drawRect(r, backgroundtile);

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
        maps.add(map = new MinefieldMap(mines, levels, block, normal));
    }

    public MinefieldMap getMap() {
        return map;
    }
}
