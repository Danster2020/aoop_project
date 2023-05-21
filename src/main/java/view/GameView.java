package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.w3c.dom.events.Event;

import controller.Game;
import controller.KeyHandler;
import model.GameDataPublisher;
import model.Observer;

public class GameView implements Observer {

    public Game game;
    GameDataPublisher gameData;
    final String GAMENAME;
    public JFrame jFrame;
    boolean isCustomLevel;
    private final int WIDTH, HEIGHT;
    public JLabel mouseLabel;
    private int blockSize;
    KeyHandler kH;

    // Editor
    JFrame editorFrame;
    public JButton saveBtn;
    public JButton loadBtn;
    public TextField textAreaFile;
    public JCheckBox checkBoxCustomLvl;

    // Game Menu
    public MenuItem restartGame;
    public MenuItem saveGame;
    public MenuItem loadGame;

    // Level Menu
    public MenuItem restartLevel;
    public MenuItem startLvlEditor;

    // Zoom Menu
    public MenuItem zoomIn;
    public MenuItem zoomOut;

    // Flags
    private boolean isLvlEditorOn;

    public GameView(GameDataPublisher gameData) {

        this.gameData = gameData;
        this.game = gameData.game;
        GAMENAME = "Sokoban";
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length
        blockSize = 48;

        // this.sound = new Sound(this);
        this.isLvlEditorOn = false;
        this.isCustomLevel = false;

        // Frame
        jFrame = new JFrame(GAMENAME);
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban
        jFrame.getContentPane().setBackground(Color.BLACK);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit app on close
        jFrame.setLocationRelativeTo(null);

        // Label
        mouseLabel = new JLabel();
        mouseLabel.setSize(WIDTH, HEIGHT);

        // Init config
        BlockManager bM = new BlockManager(game);
        this.kH = new KeyHandler(this);
        menuBar();



        // Adds components
        jFrame.add(mouseLabel);
        jFrame.add(bM);

        jFrame.setIconImage(bM.boxImg);
        jFrame.setVisible(true);
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }

    public void updateView() {
        jFrame.repaint();
    }

    public JCheckBox getCheckBoxCustomLvl() {
        return checkBoxCustomLvl;
    }

    public void menuBar() {
        MenuBar menuBar = new MenuBar();
        jFrame.setMenuBar(menuBar);

        // #GameMenu
        Menu GameMenu = new Menu("Game");
        menuBar.add(GameMenu);

        // ##RestartGameItem
        restartGame = new MenuItem("Restart game");
        restartGame.addActionListener(kH);
        GameMenu.add(restartGame);

        // ##SaveGameItem
        saveGame = new MenuItem("Save game");
        saveGame.addActionListener(kH);
        GameMenu.add(saveGame);

        // ##LoadGameItem
        loadGame = new MenuItem("Load game");
        loadGame.addActionListener(kH);
        GameMenu.add(loadGame);

        // #Levelmenu
        Menu levelMenu = new Menu("Level");
        menuBar.add(levelMenu);

        // ##RestartItem
        restartLevel = new MenuItem("Restart (Space)");
        restartLevel.addActionListener(kH);
        levelMenu.add(restartLevel);

        // ##LevelEditorItem
        startLvlEditor = new MenuItem("Start Level Editor");
        startLvlEditor.addActionListener(kH);
        levelMenu.add(startLvlEditor);

        // #Zoom menu
        Menu zoomMenu = new Menu("Zoom");
        menuBar.add(zoomMenu);

        // ##ZoomIn
        zoomIn = new MenuItem("Zoom in");
        zoomIn.addActionListener(kH);
        zoomMenu.add(zoomIn);

        // ##ZoomOut
        zoomOut = new MenuItem("Zoom out");
        zoomOut.addActionListener(kH);
        zoomMenu.add(zoomOut);
    }

    public void startLevelEditor(boolean status) {
        // Editor window

        // if start
        if (status) {
            editorFrame = new JFrame("Level editor");
            editorFrame.setLayout(null);
            saveBtn = new JButton("Save");
            loadBtn = new JButton("Load");
            textAreaFile = new TextField("", 20);
            JLabel textLabel = new JLabel("Name of level");
            checkBoxCustomLvl = new JCheckBox("Custom level");

            editorFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            editorFrame.setLayout(new GridBagLayout());
            GridBagConstraints gBC = new GridBagConstraints();

            gBC.gridx = 0;
            gBC.gridy = 0;
            gBC.gridwidth = 1;
            gBC.gridheight = 1;
            editorFrame.add(textLabel, gBC);

            gBC.gridx = 0;
            gBC.gridy = 1;
            gBC.gridwidth = 2;
            gBC.gridheight = 1;
            editorFrame.add(textAreaFile, gBC);

            gBC.gridx = 0;
            gBC.gridy = 2;
            gBC.gridwidth = 1;
            gBC.gridheight = 1;
            editorFrame.add(checkBoxCustomLvl, gBC);

            gBC.gridx = 0;
            gBC.gridy = 3;
            gBC.gridwidth = 1;
            gBC.gridheight = 1;
            editorFrame.add(saveBtn, gBC);

            gBC.gridx = 1;
            gBC.gridy = 3;
            editorFrame.add(loadBtn, gBC);
            saveBtn.addActionListener(kH);
            loadBtn.addActionListener(kH);

            editorFrame.setSize(200, 200);
            editorFrame.setVisible(true);
            startLvlEditor.setLabel("Close Level editor");
            editorFrame.setResizable(false);
            game.setLvlEditorOn(true);
            game.loadLevel("blank", false);
        } else {
            if (editorFrame == null) {
                return;
            }
            editorFrame.setVisible(false);
            startLvlEditor.setLabel("Start Level Editor");
            game.setLvlEditorOn(false);
        }

    }

    public void showGameWonPrompt() {

        if (game.isLevelComplete) {
            return;
        }

        game.isLevelComplete = true;

        JOptionPane.showMessageDialog(jFrame,
                "You completed the level!",
                game.getGameName(),
                JOptionPane.PLAIN_MESSAGE);

        String currLvlName = game.getCurrLvl().getName();
        int currLvlNr = Integer.parseInt(currLvlName.replaceAll("[^0-9]", ""));
        String nextLvl = "level" + (currLvlNr + 1);
        game.loadLevel(nextLvl, false);
    }



    @Override
    public void update(model.Event event) {
        jFrame.repaint();
    }
}
