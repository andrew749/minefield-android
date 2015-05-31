package com.andrew749.minefield;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by andrew on 15/06/13.
 */
public class MinefieldMap {

    ArrayList<Rect> mineBlocks;
    ArrayList<Rect> newLevelBlocks;
    ArrayList<Rect> blockedBlocks;
    ArrayList<Rect> normalBlocks;

    public MinefieldMap(ArrayList<Rect> mines, ArrayList<Rect> levels, ArrayList<Rect> blocked, ArrayList<Rect> normal) {
        mineBlocks = mines;
        newLevelBlocks = levels;
        blockedBlocks = blocked;
        normalBlocks = normal;
    }

    public ArrayList<Rect> getMines() {
        return mineBlocks;
    }

    public ArrayList<Rect> getLevelBlocks() {
        return newLevelBlocks;
    }

    public ArrayList<Rect> getblockedBlocks() {
        return blockedBlocks;
    }

    public ArrayList<Rect> getNormalBlocks() {
        return normalBlocks;
    }

}
