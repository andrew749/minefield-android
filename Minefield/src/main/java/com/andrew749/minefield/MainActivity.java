package com.andrew749.minefield;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by andrew on 17/05/13.
 */
public class MainActivity extends Activity implements View.OnTouchListener {
    static gameview view;
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        h = new Handler();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new gameview(getApplicationContext());
        setContentView(view);
        r.run();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        view.onTouchEvent(event);
        return false;
    }

    private Runnable r = new Runnable() {
        public void run() {
            view.invalidate(view.getPlayer().player());
            h.postDelayed(r, 10);
        }
    };


}


