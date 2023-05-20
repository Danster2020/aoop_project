package model;

import controller.Game;

public class Box extends MovableObject {

    public Box(Game g) {
        super(g);
    }

    @Override
    public void place(Block block) {
        block.placeBox();
        game.gameData.notifyObservers(Event.BOX_MOVED);
        if (block.isTarget()) {
            game.gameData.notifyObservers(Event.BOX_MOVED_ON_TARGET);
        }
    }

    @Override
    public void remove(Block block) {
        block.removeBox();
    }
}
