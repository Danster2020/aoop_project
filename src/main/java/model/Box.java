package model;

import controller.Game;

public class Box extends MovableObject {

    public Box(Game g) {
        super(g);
    }

    @Override
    public void place(Block block) {
        block.placeBox();
        getGame().getGameData().notifyObservers(Event.BOX_MOVED);
        if (block.isTarget()) {
            getGame().getGameData().notifyObservers(Event.BOX_MOVED_ON_TARGET);
        }
    }

    @Override
    public void remove(Block block) {
        block.removeBox();
    }
}
