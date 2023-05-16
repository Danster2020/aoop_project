package view;

import model.Block;
import model.GameDataPublisher;
import model.Observer;
import model.Publisher;

public class TerminalView implements Observer {
    private GameDataPublisher gameData;
    private Block[][] blockGrid;

    public TerminalView(GameDataPublisher gameData) {
        this.gameData = gameData;
    }

    @Override
    public void update() {
        this.blockGrid = gameData.getLvlGrid();
        printBlocks();
        // gameData.game.checkGameState();
    }

    public void printBlocks() {

        System.out.println(gameData.lvlGridToString());
    }

}
