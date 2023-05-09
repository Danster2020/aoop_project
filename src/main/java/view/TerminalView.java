package view;

import model.Block;
import model.GameData;
import model.Observer;
import model.Subject;

public class TerminalView implements Observer {
    private GameData gameData;
    private Block[][] blockGrid;

    public TerminalView(GameData gameData) {
        this.gameData = gameData;
        this.gameData.registerObserver(this);
    }

    @Override
    public void update() {
        this.blockGrid = gameData.getLvlGrid();
        printBlocks();
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

}
