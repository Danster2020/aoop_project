package model;

import java.io.Serializable;

public class Level implements Serializable {

    private static final long serialVersionUID = 2L;
    private Block[][] blockGrid;
    private String levelName;

    public Level(String lvlName) {

        this.blockGrid = new Block[20][30]; // [row][col]

        // defaults all blocks to be blank tiles
        for (int row = 0; row < this.getGrid().length; row++) {
            for (int col = 0; col < this.getGrid()[0].length; col++) {
                this.setBlock(col, row, new Block());
            }
        }

        this.levelName = lvlName;
    }

    /**
     * @return Block[][]
     */
    public Block[][] getGrid() {
        return this.blockGrid;
    }

    /**
     * @return String
     */
    public String getName() {
        return this.levelName;
    }

    /**
     * Sets the name of the level.
     * 
     * @param levelName
     */
    public void setName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * @param col
     * @param row
     * @param data
     */
    public void setBlock(int col, int row, Block data) {
        this.blockGrid[row][col] = data;
    }

    /**
     * Returns the number of columns in the grid.
     * 
     * @return int
     */
    public int getGridLength() {
        return this.blockGrid[0].length;
    }

    /**
     * Returns the number of rows in the grid.
     * 
     * @return int
     */
    public int getGridHeight() {
        return this.blockGrid.length;
    }

    /**
     * @param col
     * @param row
     * @return Block
     */
    public Block getBlock(int col, int row) {
        return this.blockGrid[row][col];
    }

    /**
     * @param targetBlock
     * @return int
     */
    public int getBlockCol(Block targetBlock) {
        int colIndex = 0;
        for (Block[] row : blockGrid) {
            for (Block block : row) {
                if (block.equals(targetBlock)) {
                    return colIndex;
                }
                colIndex++;
            }
            colIndex = 0;
        }
        return -1;
    }

    /**
     * @param targetBlock
     * @return int
     */
    public int getBlockRow(Block targetBlock) {
        int rowIndex = 0;
        for (Block[] row : blockGrid) {
            for (Block block : row) {
                if (block.equals(targetBlock)) {
                    return rowIndex;
                }
            }
            rowIndex++;
        }
        return -1;
    }
}
