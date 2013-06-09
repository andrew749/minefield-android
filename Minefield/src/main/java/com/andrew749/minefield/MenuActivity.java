package com.andrew749.minefield;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by andrew on 07/06/13.
 */
public class MenuActivity extends Activity implements View.OnClickListener {
    Button play, goodies, leaderboard, about, settings;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuscreen);
        play = (Button) findViewById(R.id.button);
        play.setOnClickListener(this);
        leaderboard = (Button) findViewById(R.id.button2);
        leaderboard.setOnClickListener(this);
        goodies = (Button) findViewById(R.id.button3);
        goodies.setOnClickListener(this);
        about = (Button) findViewById(R.id.button4);
        about.setOnClickListener(this);
        settings = (Button) findViewById(R.id.button5);
        settings.setOnClickListener(this);

    }

    @Override
    public void onClick(View i) {
        switch (i.getId()) {
            case R.id.button:
                intent = new Intent().setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                intent = new Intent().setClass(getApplicationContext(), LeaderBoardActivity.class);
                startActivity(intent);

                break;
            case R.id.button3:
                intent = new Intent().setClass(getApplicationContext(), GoodiesActivity.class);
                startActivity(intent);

                break;
            case R.id.button4:
                intent = new Intent().setClass(getApplicationContext(), AboutActivity.class);
                startActivity(intent);

                break;
            case R.id.button5:
                intent = new Intent().setClass(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);

                break;

        }
    }
}
