package view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import controller.GameDataPublisher;
import model.Observer;

public class WindowTextView implements Observer {
    private GameDataPublisher gameData;
    private JFrame frame;
    private JTextArea text;

    public WindowTextView(GameDataPublisher gameData) {
        this.gameData = gameData;

        frame = new JFrame();
        text = new JTextArea("");
        text.setEditable(false);
        text.setFont(new Font(Font.MONOSPACED, 0, 10));
        frame.add(text);

        frame.setVisible(true);
    }

    /**
     * @param event
     */
    @Override
    public void update(model.Event event) {
        text.setText(gameData.lvlGridToString());
        frame.pack();
    }

}
