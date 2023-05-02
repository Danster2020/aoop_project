public class Player extends MovableObject {

    int pCol, pRow;// Player column and row
    Boolean playerExist;
    Game game;

    Player(Game g) {
        this.game = g;
    }

    public void spawnPlayer() {
        System.out.println("hej jag är här");
        for (int row = 0; row < game.getCurrLvl().getGrid().length; row++) {
            for (int col = 0; col < game.getCurrLvl().getGrid()[0].length; col++) {
                System.out.println(pRow + "+" + pCol);
                if (game.getCurrLvl().getBlock(col, row).hasPlayer()) {
                    this.pRow = row;
                    this.pCol = col;
                    break;
                }
            }
        }
    }

    public int getCol() {
        return pCol;
    }

    public int getRow() {
        return pRow;
    }

    public void moveUp() {
        Block destination = null;
        destination = game.getCurrLvl().getBlock(pCol, pRow - 1);

        if (this.ifCollision(destination)) {
            System.out.println("This tile is blocked");
            return;
        }

        destination.placePlayer();
        game.getCurrLvl().getBlock(pCol, pRow).removePlayer();
        pRow -= 1;
    }

    public void moveDown() {
        Block destination = null;
        destination = game.getCurrLvl().getBlock(pCol, pRow + 1);

        if (this.ifCollision(destination)) {
            System.out.println("This tile is blocked");
            return;
        }

        destination.placePlayer();
        game.getCurrLvl().getBlock(pCol, pRow).removePlayer();
        pRow += 1;
    }

    public void moveLeft() {
        Block destination = null;
        destination = game.getCurrLvl().getBlock(pCol - 1, pRow);

        if (this.ifCollision(destination)) {
            System.out.println("This tile is blocked");
            return;
        }

        destination.placePlayer();
        game.getCurrLvl().getBlock(pCol, pRow).removePlayer();
        pCol -= 1;
    }

    public void moveRight() {
        Block destination = null;
        destination = game.getCurrLvl().getBlock(pCol + 1, pRow);

        if (this.ifCollision(destination)) {
            System.out.println("This tile is blocked");
            return;
        }

        destination.placePlayer();
        game.getCurrLvl().getBlock(pCol, pRow).removePlayer();
        pCol += 1;
    }
}
