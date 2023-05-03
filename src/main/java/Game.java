// package src.main.java;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Game {

    JFrame jFrame;
    private Level currentLevel;
    private final int WIDTH, HEIGHT;
    KeyHandler kH;

    private Player player;
    private ArrayList<Box> boxes;

    // Editor
    JFrame editorFrame;
    JButton saveBtn;
    JButton loadBtn;
    TextArea TextAreaFile;

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Game() {
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length

        kH = new KeyHandler(this);

        // Frame
        jFrame = new JFrame("Sokoban");
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban

        this.currentLevel = new Level("level1");

        currentLevel.printBlocks();

        BlockManager bM = new BlockManager(this);
        this.player = new Player(this);
        startLevelEditor();
        loadLevel("level1");

        // Label
        JLabel mouseLabel = new JLabel();
        mouseLabel.setSize(WIDTH, HEIGHT);
        mouseLabel.addMouseListener(kH); // sets mouse coordinates to be relative to canvas area

        // Adds components
        jFrame.addKeyListener(kH);
        jFrame.add(mouseLabel);
        jFrame.add(bM);

        jFrame.getContentPane().setBackground(Color.BLACK);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit app on close
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

    public void loadLevel(String fileName) {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("../levels/" + fileName + ".dat"));
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
        System.out.println("Level loaded!");
    }

    public void startLevelEditor() {
        // Editor window
        editorFrame = new JFrame("Level editor");
        editorFrame.setLayout(new BorderLayout());
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");
        TextAreaFile = new TextArea("");
        editorFrame.add(TextAreaFile, BorderLayout.CENTER);
        editorFrame.add(saveBtn, BorderLayout.PAGE_START);
        editorFrame.add(loadBtn, BorderLayout.PAGE_END);
        saveBtn.addActionListener(kH);
        loadBtn.addActionListener(kH);

        editorFrame.setSize(200, 200);
        editorFrame.setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        System.out.println("starting game...");
        new Game();
    }
}
