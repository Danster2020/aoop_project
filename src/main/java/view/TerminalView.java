package view;

import controller.GameDataPublisher;
import model.Observer;

public class TerminalView implements Observer {
    private GameDataPublisher gameData;

    public TerminalView(GameDataPublisher gameData) {
        this.gameData = gameData;
    }

    /**
     * @param event
     */
    @Override
    public void update(model.Event event) {
        printBlocks();
    }

    /**
     * Prints the block grid to the terminal.
     */
    public void printBlocks() {
        System.out.println(gameData.lvlGridToString());
    }
}
