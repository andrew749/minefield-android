package com.andrew749.minefield;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by andrew on 17/05/13.
 */
public class MainActivity extends Activity  {
    static BackgroundView view;
    static game game;
    GameThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new game(getApplicationContext(),null));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}


