package com.andrew749.minefield;

/**
 * Created by andrew on 09/06/13.
 */
public class CollisionDetection {

    protected static boolean isCollidedWithMine(Player player, MinefieldMap map) {
        boolean state = false;
        for (int x = 0; x < map.mineBlocks.size(); x++) {
            if (!(map.mineBlocks.get(x) == null))
                if (player.player().intersect(map.mineBlocks.get(x)))
                    state = true;
        }

        return state;
    }

    protected static boolean isOnLevel(Player player, MinefieldMap map) {
        boolean state = false;
        for (int x = 0; x < map.getLevelBlocks().size(); x++) {
            if (!(map.getLevelBlocks().get(x) == null))
                if (player.player().intersect(map.getLevelBlocks().get(x)))
                    state = true;
        }

        return state;
    }

    protected static boolean isBlocked(Player player, MinefieldMap map) {
        boolean state = false;
        for (int x = 0; x < map.getblockedBlocks().size(); x++) {
            if (!(map.getblockedBlocks().get(x) == null))
                if (player.player().intersect(map.getblockedBlocks().get(x)))
                    state = true;
        }

        return state;
    }
}
