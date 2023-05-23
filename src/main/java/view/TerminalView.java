package view;

import model.GameDataPublisher;
import model.Observer;

public class TerminalView implements Observer {
    private GameDataPublisher gameData;

    public TerminalView(GameDataPublisher gameData) {
        this.gameData = gameData;
    }

    @Override
    public void update(model.Event event) {
        printBlocks();
    }

    public void printBlocks() {

        System.out.println(gameData.lvlGridToString());
    }
}
