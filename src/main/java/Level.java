public class Level {

    Block[][] blockGrid;
    String levelName;

    Level(Block[][] grid, String lvlName) {
        this.blockGrid = grid;
        this.levelName = lvlName;
    }

    public Block[][] getGrid() {
        return this.blockGrid;
    }

    public String getName() {
        return this.levelName;
    }

    public void setBlock(int col, int row, Block data) {
        this.blockGrid[col][row] = data;
    }

    public void printBlocks() {
        System.out.print("[");
        for (Block[] row : blockGrid) {
            System.out.println();
            for (Block block : row) {
                String string = "";
                if (block.isTile()) {
                    string = "-";
                } else if (block.isWall()) {
                    string = "W";
                }
                System.out.print(string + " ");
            }
        }
        System.out.println("\n]");
    }

    public int getGridLength() {
        return this.blockGrid.length;
    }

    public int getGridHeight() {
        return this.blockGrid[0].length;
    }

    public Block getBlock(int col, int row) {
        return this.blockGrid[col][row];
    }

}
