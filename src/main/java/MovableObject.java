public abstract class MovableObject {

    int objCol, objRow;// Player column and row
    Game game;

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

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

    public void moveUp() {
        Block source = getBlock(objCol, objRow);
        Block destination = getBlock(objCol, objRow - 1);

        if (this.ifCollision(destination, Direction.UP)) {
            return;
        }

        place(destination);
        remove(source);
        objRow -= 1;
    }

    public void moveDown() {
        Block source = getBlock(objCol, objRow);
        Block destination = getBlock(objCol, objRow + 1);

        if (this.ifCollision(destination, Direction.DOWN)) {
            return;
        }

        place(destination);
        remove(source);
        objRow += 1;
    }

    public void moveLeft() {
        Block source = getBlock(objCol, objRow);
        Block destination = getBlock(objCol - 1, objRow);

        if (this.ifCollision(destination, Direction.LEFT)) {
            return;
        }

        place(destination);
        remove(source);
        objCol -= 1;
    }

    public void moveRight() {
        Block source = getBlock(objCol, objRow);
        Block destination = getBlock(objCol + 1, objRow);

        if (this.ifCollision(destination, Direction.RIGHT)) {
            return;
        }

        place(destination);
        remove(source);
        objCol += 1;
    }

    public Boolean ifCollision(Block moveTo, Direction dir) {
        if (moveTo.isWall()) {
            System.out.println("This tile is blocked by wall!");
            return true;
        }
        if (moveTo.hasBox()) {
            // TODO
            for (Box box : game.getBoxes()) {
                if (box.getCol() == game.getCurrLvl().getBlockCol(moveTo)
                        && box.getRow() == game.getCurrLvl().getBlockRow(moveTo)) {
                    System.out.println("hit");
                    if (dir == Direction.UP) {
                        box.moveUp();
                    } else if (dir == Direction.DOWN) {
                        box.moveDown();
                    } else if (dir == Direction.LEFT) {
                        box.moveLeft();
                    } else if (dir == Direction.RIGHT) {
                        box.moveRight();
                    }
                }
            }
        }
        return false;
    }

}
