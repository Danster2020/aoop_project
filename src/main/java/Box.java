import java.util.PrimitiveIterator.OfDouble;

public class Box extends MovableObject {

    Box(Game g) {
        super(g);
    }

    @Override
    public void place(Block block) {
        block.placeBox();
    }

    @Override
    public void remove(Block block) {
        block.removeBox();
    }
}
