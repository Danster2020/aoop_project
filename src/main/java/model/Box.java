package model;

import controller.Game;

public class Box extends MovableObject {

    public Box(Game g) {
        super(g);
    }

    @Override
    public void place(Block block) {
        block.placeBox();
        game.gameView.sound.playSE(game.gameView.sound.box_Moved);
        if (block.isTarget()) {
            game.gameView.sound.playSE(game.gameView.sound.marked_box_placed);
        }
    }

    @Override
    public void remove(Block block) {
        block.removeBox();
    }
}
