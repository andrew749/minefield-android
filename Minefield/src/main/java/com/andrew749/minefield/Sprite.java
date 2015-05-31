package com.andrew749.minefield;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by andrewcodispoti on 2015-05-31.
 */
public abstract class Sprite {
    int x,y;
    public abstract void update(Point coordinates);
    public abstract void draw(Canvas c);
}
