public class Player extends MovableObject {

    Player(Game g) {
        super(g);
    }

    @Override
    public void remove(Block block) {
        block.removePlayer();
    }

    @Override
    public void place(Block block) {
        block.placePlayer();
        game.sound.playSE(game.sound.soundEffect_Walk);
    }

    public void spawnPlayer() {
        for (int row = 0; row < game.getCurrLvl().getGrid().length; row++) {
            for (int col = 0; col < game.getCurrLvl().getGrid()[0].length; col++) {
                if (game.getCurrLvl().getBlock(col, row).hasPlayer()) {
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
