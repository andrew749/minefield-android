package com.andrew749.minefield;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by andrew on 17/05/13.
 */
public class Player {
    public static final int playerHeight = 50;
    public static final int playerWidth = 30;
    public static int xcoordinate = 0;
    public static int ycoordinate = 0;
    static Rect playerrect;
    static int xToIncrease = 10;
    static int yToIncrease = 10;
    public static Paint playerPaint = new Paint();

    public Player(int x, int y) {
        playerPaint.setColor(Color.CYAN);
        xcoordinate = x;
        ycoordinate = y;
        playerrect = new Rect(xcoordinate, ycoordinate, xcoordinate + playerWidth, ycoordinate + playerHeight);
    }

    public static void setCoordinates(int x, int y) {
        xcoordinate = x;
        ycoordinate = y;
    }

    public void draw(Canvas c) {
        c.drawRect(playerrect, playerPaint);
    }

    public void update(Point coordinates) {
        movePlayer(coordinates);
        playerrect.set(xcoordinate, ycoordinate, xcoordinate + playerWidth, ycoordinate + playerHeight);

    }

    public Rect player() {
        return playerrect;
    }

    public int getX() {
        return xcoordinate;
    }

    public int getY() {
        return ycoordinate;
    }

    protected void movePlayer(Point userInput) {
        if (userInput.x != 0 && userInput.y != 0) {
            if (userInput.x < getX()) {
                if (Math.abs(userInput.x - getX()) < 10) {
                    --xcoordinate;
                } else {
                    xcoordinate -= xToIncrease;
                }
            } else if (userInput.x > getX()) {
                if (Math.abs(userInput.x - getX()) < 100) {
                    ++xcoordinate;
                } else {

                    xcoordinate += xToIncrease;
                }
            }
            if (userInput.y < getY()) {

                if (Math.abs(userInput.y - getY()) < 10) {
                    --ycoordinate;
                } else {

                    ycoordinate -= yToIncrease;
                }
            } else if (userInput.y > getY()) {
                if (Math.abs(userInput.y - getY()) < 10) {
                    ++ycoordinate;
                } else {

                    ycoordinate += yToIncrease;
                }
            } else {
            }


        }
    }
}
