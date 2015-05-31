package com.andrew749.minefield;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by andrew on 17/05/13.
 */
public class Player extends Sprite {
    public static final int playerHeight = 100;
    public static final int playerWidth = 60;
    static Rect playerrect;
    static int xToIncrease = 10;
    static int yToIncrease = 10;
    public static Paint playerPaint = new Paint();

    public Player(int x, int y) {
        playerPaint.setColor(Color.CYAN);
        this.x = x;
        this.y = y;
        playerrect = new Rect(this.x, this.y, this.x + playerWidth, this.y + playerHeight);
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas c) {
        c.drawRect(playerrect, playerPaint);
    }

    public void update(Point coordinates) {
        movePlayer(coordinates);
        playerrect.set(x, y, x + playerWidth, y + playerHeight);

    }

    public Rect player() {
        return playerrect;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void movePlayer(Point userInput) {
        if (userInput.x != 0 && userInput.y != 0) {
            if (userInput.x < getX()) {
                if (Math.abs(userInput.x - getX()) < 10) {
                    --x;
                } else {
                    x -= xToIncrease;
                }
            } else if (userInput.x > getX()) {
                if (Math.abs(userInput.x - getX()) < 100) {
                    ++x;
                } else {

                    x += xToIncrease;
                }
            }
            if (userInput.y < getY()) {

                if (Math.abs(userInput.y - getY()) < 10) {
                    --y;
                } else {

                    y -= yToIncrease;
                }
            } else if (userInput.y > getY()) {
                if (Math.abs(userInput.y - getY()) < 10) {
                    ++y;
                } else {

                    y += yToIncrease;
                }
            } else {
            }


        }
    }
}
