package model;
import controller.Game;

public abstract class MovableObject {

    private int objCol, objRow;// Player column and row
    Game game;

    MovableObject(Game g) {
        this.game = g;
    }

    public abstract void place(Block block);

    public abstract void remove(Block block);

    public int getCol() {
        return objCol;
    }

    public int getRow() {
        return objRow;
    }

    public void setCol(int objCol) {
        this.objCol = objCol;
    }

    public void setRow(int objRow) {
        this.objRow = objRow;
    }

    public Block getBlock(int col, int row) {
        return game.getCurrLvl().getBlock(col, row);
    }

    // returns if move was successful or not
    public boolean move(Game.Direction dir) {
        Block source = getBlock(objCol, objRow);
        Block destination = null;

        int newCol = objCol;
        int newRow = objRow;

        if (dir == Game.Direction.UP) {
            destination = getBlock(objCol, objRow - 1);
            newRow -= 1;
        } else if (dir == Game.Direction.DOWN) {
            destination = getBlock(objCol, objRow + 1);
            newRow += 1;
        } else if (dir == Game.Direction.LEFT) {
            destination = getBlock(objCol - 1, objRow);
            newCol -= 1;
        } else if (dir == Game.Direction.RIGHT) {
            destination = getBlock(objCol + 1, objRow);
            newCol += 1;
        }

        if (this.ifCollision(source, destination, dir)) {
            return false;
        }

        place(destination);
        remove(source);
        objCol = newCol;
        objRow = newRow;
        game.gameData.notifyObservers();
        return true;
    }

    public Boolean ifCollision(Block moveFrom, Block moveTo, Game.Direction dir) {

        if (moveTo.isWall()) {
            System.out.println("This tile is blocked by wall!");
            return true;
        }

        if (moveFrom.hasBox() && moveTo.hasBox()) {
            System.out.println("blocked!");
            return true;
        }

        if (moveTo.hasBox()) {
            for (Box box : game.getBoxes()) {
                if (box.getCol() == game.getCurrLvl().getBlockCol(moveTo)
                        && box.getRow() == game.getCurrLvl().getBlockRow(moveTo)) {
                    System.out.println("hit");
                    
                    if (!box.move(dir)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
