import java.util.PrimitiveIterator.OfDouble;

public class Box extends MovableObject {

    Box(Game g) {
        super(g);
    }

    @Override
    public void place(Block block) {
        block.placeBox();
        game.sound.playSE(game.sound.box_Moved);
        if (block.isTarget()) {
            game.sound.playSE(game.sound.marked_box_placed);
        }
    }

    @Override
    public void remove(Block block) {
        block.removeBox();
    }
}
