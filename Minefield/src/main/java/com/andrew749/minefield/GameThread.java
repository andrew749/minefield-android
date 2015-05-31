package com.andrew749.minefield;

import android.view.SurfaceHolder;

/**
 * Created by andrew on 29/06/13.
 */
public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;

    private game gamePanel;
    private Backgroundview backgroundview;

    public GameThread(SurfaceHolder holder, game gamePanel, Backgroundview backgroundview) {
        surfaceHolder = holder;
        this.gamePanel = gamePanel;
    }

    private boolean running;
    private boolean background;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setBackgroundRunning(boolean background) {
        this.background = background;
    }

    @Override
    public void run() {
        long tick = 0l;
        while (running) {
            tick++;
            gamePanel.invalidate();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }

            if (background) {
                backgroundview.invalidate();
            }
        }
    }
}
