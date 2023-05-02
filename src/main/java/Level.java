import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level implements Serializable {

    private static final long serialVersionUID = 0000000000000000001L;
    Block[][] blockGrid;
    String levelName;

    Level(String lvlName) {

        this.blockGrid = new Block[20][30]; // [row][col]

        // defaults all blocks to be blank tiles
        for (int row = 0; row < this.getGrid().length; row++) {
            for (int col = 0; col < this.getGrid()[0].length; col++) {
                this.setBlock(col, row, new Block());
            }
        }

        this.levelName = lvlName;
    }

    public Block[][] getGrid() {
        return this.blockGrid;
    }

    public String getName() {
        return this.levelName;
    }

    public void setName(String levelName) {
        this.levelName = levelName;
    }

    public void setBlock(int col, int row, Block data) {
        this.blockGrid[row][col] = data;
    }

    public void printBlocks() {
        System.out.print("[");
        for (Block[] row : blockGrid) {
            System.out.println("");
            for (Block block : row) {
                String string = "";
                if (block.hasPlayer()) {
                    string = "P";
                } else if (block.hasTargetBox()) {
                    string = "TB";
                } else if (block.hasBox()) {
                    string = "B";
                } else if (block.isTarget()) {
                    string = "T";
                } else if (block.isWall()) {
                    string = "W";
                } else if (block.isTile()) {
                    string = "-";
                } else {
                    string = "null";
                }
                System.out.print(string + " ");
            }
        }
        System.out.println("\n]");
    }

    public int getGridLength() {
        return this.blockGrid[0].length;
    }

    public int getGridHeight() {
        return this.blockGrid.length;
    }

    public Block getBlock(int col, int row) {
        return this.blockGrid[row][col];
    }

    public void saveLevel() {
        ObjectOutputStream dataOut = null;
        try {
            dataOut = new ObjectOutputStream(new FileOutputStream(this.levelName + ".dat"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataOut.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Level saved!");
    }
}
