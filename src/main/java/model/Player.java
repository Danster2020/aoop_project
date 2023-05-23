package model;

import controller.Game;

public class Player extends MovableObject {

    public Player(Game g) {
        super(g);
    }

    @Override
    public void remove(Block block) {
        block.removePlayer();
    }

    @Override
    public void place(Block block) {
        block.placePlayer();
        getGame().getGameData().notifyObservers(Event.PLAYER_MOVED);
    }

    public void spawnPlayer() {
        for (int row = 0; row < getGame().getCurrLvl().getGrid().length; row++) {
            for (int col = 0; col < getGame().getCurrLvl().getGrid()[0].length; col++) {
                if (getGame().getCurrLvl().getBlock(col, row).hasPlayer()) {
                    this.setRow(row);
                    this.setCol(col);
                    System.out.println("Player spawned!");
                    return;
                }
            }
        }
        System.out.println("No player found!");
    }
}
