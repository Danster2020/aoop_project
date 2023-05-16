package view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import model.Block;
import model.GameDataPublisher;
import model.Observer;
import model.Publisher;

public class WindowTextView implements Observer {
    private GameDataPublisher gameData;
    private Block[][] blockGrid;
    JFrame frame;
    JTextArea text;

    public WindowTextView(GameDataPublisher gameData) {
        this.gameData = gameData;

        frame = new JFrame();
        text = new JTextArea("");
        text.setEditable(false);
        text.setFont(new Font(Font.MONOSPACED, 0, 10));
        frame.add(text);

        frame.setVisible(true);
    }

    @Override
    public void update() {
        this.blockGrid = gameData.getLvlGrid();
        text.setText(gameData.lvlGridToString());
        frame.pack();
    }

}
