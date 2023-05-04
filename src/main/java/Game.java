// package src.main.java;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Game {

    final String GAMENAME;
    JFrame jFrame;
    private Level currentLevel;
    boolean isCustomLevel;
    private final int WIDTH, HEIGHT;
    KeyHandler kH;
    Sound sound;

    private Player player;
    private ArrayList<Box> boxes;

    // Editor
    JFrame editorFrame;
    JButton saveBtn;
    JButton loadBtn;
    TextField textAreaFile;
    JCheckBox checkBoxCustomLvl;

    // Menu
    MenuBar menuBar;
    MenuItem restartLevel;
    MenuItem startLvlEditor;

    // Flags
    private boolean isLvlEditorOn;

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Game() {
        GAMENAME = "Sokoban";
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length

        BlockManager bM = new BlockManager(this);
        this.kH = new KeyHandler(this);
        this.currentLevel = new Level("level1");
        this.player = new Player(this);
        this.sound = new Sound(this);
        this.isLvlEditorOn = false;
        this.isCustomLevel = false;

        // Frame
        jFrame = new JFrame(GAMENAME);
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban
        jFrame.getContentPane().setBackground(Color.BLACK);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit app on close

        // Init config
        menuBar();
        loadLevel("level1", "");

        // Label
        JLabel mouseLabel = new JLabel();
        mouseLabel.setSize(WIDTH, HEIGHT);
        mouseLabel.addMouseListener(kH); // sets mouse coordinates to be relative to canvas area

        // Adds components
        jFrame.addKeyListener(kH);
        jFrame.add(mouseLabel);
        jFrame.add(bM);

        jFrame.setVisible(true);
        System.out.println("Game started!");
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }

    public Level getCurrLvl() {
        return this.currentLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public JCheckBox getCheckBoxCustomLvl() {
        return checkBoxCustomLvl;
    }

    public boolean isLvlEditorOn() {
        return isLvlEditorOn;
    }

    public void setLvlEditorOn(boolean status) {
        this.isLvlEditorOn = status;
    }

    public void spawnBoxes() {
        boxes = new ArrayList<Box>();
        for (int row = 0; row < this.getCurrLvl().getGrid().length; row++) {
            for (int col = 0; col < this.getCurrLvl().getGrid()[0].length; col++) {
                if (this.getCurrLvl().getBlock(col, row).hasBox()) {
                    Box box = new Box(this);
                    box.setRow(row);
                    box.setCol(col);
                    boxes.add(box);
                }
            }
        }
    }

    public void loadLevel(String fileName, String customPath) {

        // FIXME soundstacking on lvl reload/load
        try {
            sound.stop();
        } catch (Exception e) {
            // TODO: handle exception
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("../levels/" + customPath + fileName + ".dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            currentLevel = (Level) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getPlayer().spawnPlayer();
        spawnBoxes();

        jFrame.repaint();
        jFrame.setTitle(GAMENAME + " - " + currentLevel.getName());
        sound.playAudio(sound.bg_music);
        System.out.println("Level loaded!");
    }

    public void menuBar() {
        MenuBar menuBar = new MenuBar();
        Menu levelMenu = new Menu("Level");
        menuBar.add(levelMenu);
        jFrame.setMenuBar(menuBar);

        // Levelmenu

        // RestartItem
        restartLevel = new MenuItem("Restart");
        levelMenu.add(restartLevel);
        restartLevel.addActionListener(kH);

        // LevelEditorItem
        startLvlEditor = new MenuItem("Start Level Editor");
        levelMenu.add(startLvlEditor);
        startLvlEditor.addActionListener(kH);
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
            setLvlEditorOn(true);
            sound.stop();
            loadLevel("blank", "");
            sound.stop();

        } else {
            editorFrame.setVisible(false);
            startLvlEditor.setLabel("Start Level Editor");
            setLvlEditorOn(false);
        }

    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        System.out.println("starting game...");
        new Game();
    }
}
