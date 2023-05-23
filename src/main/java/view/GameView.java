package view;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controller.Game;
import controller.KeyHandler;
import model.GameDataPublisher;
import model.Observer;

public class GameView implements Observer {

    private Game game;
    private final String GAMENAME;
    private JFrame jFrame;
    private final int WIDTH, HEIGHT;
    private JLabel mouseLabel;
    private int blockSize;
    private KeyHandler kH;

    // Editor
    private JFrame editorFrame;
    private JButton saveBtn;
    private JButton loadBtn;
    private TextField textAreaFile;
    private JCheckBox checkBoxCustomLvl;

    // Game Menu
    private MenuItem restartGame;
    private MenuItem saveGame;
    private MenuItem loadGame;

    // Level Menu
    private MenuItem restartLevel;
    private MenuItem startLvlEditor;

    // Zoom Menu
    private MenuItem zoomIn;
    private MenuItem zoomOut;

    public GameView(GameDataPublisher gameData) {

        this.game = gameData.getGame();
        GAMENAME = "Sokoban";
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length
        blockSize = 48;

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

        jFrame.setIconImage(bM.getBoxImg());
        jFrame.setVisible(true);
    }

    /**
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return JButton
     */
    public JButton getSaveBtn() {
        return saveBtn;
    }

    /**
     * @return JButton
     */
    public JButton getLoadBtn() {
        return loadBtn;
    }

    /**
     * @return JLabel
     */
    public JLabel getMouseLabel() {
        return mouseLabel;
    }

    /**
     * @return MenuItem
     */
    public MenuItem getZoomIn() {
        return zoomIn;
    }

    /**
     * @return MenuItem
     */
    public MenuItem getZoomOut() {
        return zoomOut;
    }

    /**
     * @return TextField
     */
    public TextField getTextAreaFile() {
        return textAreaFile;
    }

    /**
     * @return JFrame
     */
    public JFrame getjFrame() {
        return jFrame;
    }

    /**
     * @return int
     */
    public int getBlockSize() {
        return this.blockSize;
    }

    /**
     * @return MenuItem
     */
    public MenuItem getRestartGame() {
        return restartGame;
    }

    /**
     * @return MenuItem
     */
    public MenuItem getSaveGame() {
        return saveGame;
    }

    /**
     * @return MenuItem
     */
    public MenuItem getLoadGame() {
        return loadGame;
    }

    /**
     * @return MenuItem
     */
    public MenuItem getRestartLevel() {
        return restartLevel;
    }

    /**
     * @param blockSize
     */
    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * @return int
     */
    public int getWidth() {
        return this.WIDTH;
    }

    /**
     * @return int
     */
    public int getHeight() {
        return this.HEIGHT;
    }

    /**
     * @return MenuItem
     */
    public MenuItem getStartLvlEditor() {
        return startLvlEditor;
    }

    /**
     * @return JCheckBox
     */
    public JCheckBox getCheckBoxCustomLvl() {
        return checkBoxCustomLvl;
    }

    /**
     * Activates the menu bar.
     * 
     * @return JCheckBox
     */
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

    /**
     * Starts the level editor.
     * 
     * @param status
     */
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

    /**
     * Shows the prompt for when a level has been completed for the user.
     */
    public void showGameWonPrompt() {

        if (game.isLevelComplete()) {
            return;
        }

        game.setIsLevelComplete(true);

        JOptionPane.showMessageDialog(jFrame,
                "You completed the level!",
                game.getGameName(),
                JOptionPane.PLAIN_MESSAGE);

        String currLvlName = game.getCurrLvl().getName();
        int currLvlNr = Integer.parseInt(currLvlName.replaceAll("[^0-9]", ""));
        String nextLvl = "level" + (currLvlNr + 1);
        game.loadLevel(nextLvl, false);
    }

    /**
     * @param event
     */
    @Override
    public void update(model.Event event) {
        jFrame.repaint();
    }
}
