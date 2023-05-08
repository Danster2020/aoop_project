package view;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import controller.Game;
import controller.KeyHandler;

public class GameView {

    Game game;
    final String GAMENAME;
    public JFrame jFrame;
    boolean isCustomLevel;
    private final int WIDTH, HEIGHT;
    public Sound sound;
    private int blockSize;
    KeyHandler kH;


    // Editor
    JFrame editorFrame;
    public JButton saveBtn;
    public JButton loadBtn;
    public TextField textAreaFile;
    public JCheckBox checkBoxCustomLvl;
    
    // Level Menu
    public MenuItem restartLevel;
    public MenuItem startLvlEditor;

    // Zoom Men
    public MenuItem zoomIn;
    public MenuItem zoomOut;

    // Flags
    private boolean isLvlEditorOn;
    
    public GameView(Game g) {
        
        this.game = g;
        GAMENAME = "Sokoban";
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length
        blockSize = 48;

        this.sound = new Sound(this);
        this.kH = new KeyHandler(game);
        this.isLvlEditorOn = false;
        this.isCustomLevel = false;


        // Frame
        jFrame = new JFrame(GAMENAME);
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban
        jFrame.getContentPane().setBackground(Color.BLACK);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit app on close
        jFrame.setLocationRelativeTo(null);

        // Init config
        menuBar();
        BlockManager bM = new BlockManager(game);


        // Label
        JLabel mouseLabel = new JLabel();
        mouseLabel.setSize(WIDTH, HEIGHT);
        mouseLabel.addMouseListener(kH); // sets mouse coordinates to be relative to canvas area

        // Adds components
        jFrame.addKeyListener(kH);
        jFrame.add(mouseLabel);
        jFrame.add(bM);

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

        // #Levelmenu
        Menu levelMenu = new Menu("Level");
        menuBar.add(levelMenu);

        // ##RestartItem
        restartLevel = new MenuItem("Restart (SPACE)");
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
            sound.stopMusic();
            game.loadLevel("blank", false);
            sound.stopMusic();
        } else {
            editorFrame.setVisible(false);
            startLvlEditor.setLabel("Start Level Editor");
            game.setLvlEditorOn(false);
        }

    }
}
