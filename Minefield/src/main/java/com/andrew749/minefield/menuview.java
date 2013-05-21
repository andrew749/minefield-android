package com.andrew749.minefield;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by andrew on 17/05/13.
 */
public class menuview extends View {
static Player player=new Player(0,0);
    public menuview(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
