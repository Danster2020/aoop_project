package controller;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener, MouseListener, ActionListener {

    public boolean northPressed, southPressed, westPressed, eastPressed, buttonPressed;
    Game game;

    public KeyHandler(Game g) {
        this.game = g;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // keyTyped = Invoked when a key is typed. Uses KeyChar, char output.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyboardINT = e.getKeyCode();

        if (keyboardINT == KeyEvent.VK_W || keyboardINT == KeyEvent.VK_UP) {
            northPressed = true;
            game.getPlayer().move(Game.Direction.UP);
        }
        if (keyboardINT == KeyEvent.VK_S || keyboardINT == KeyEvent.VK_DOWN) {
            southPressed = true;
            game.getPlayer().move(Game.Direction.DOWN);
        }
        if (keyboardINT == KeyEvent.VK_A || keyboardINT == KeyEvent.VK_LEFT) {
            westPressed = true;
            game.getPlayer().move(Game.Direction.LEFT);
        }
        if (keyboardINT == KeyEvent.VK_D || keyboardINT == KeyEvent.VK_RIGHT) {
            eastPressed = true;
            game.getPlayer().move(Game.Direction.RIGHT);
        }
        if (keyboardINT == KeyEvent.VK_SPACE) {
            game.restartLevel();
        }
        game.gameView.updateView();
        System.out.println("you pressed " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // System.out.println("You clicked the mouse");
        // System.out.println("x: " + e.getX() + " y: " + e.getY());

        if (game.isLvlEditorOn()) {
            int col = e.getX() / game.gameView.getBlockSize();
            int row = e.getY() / game.gameView.getBlockSize();
            System.out.println("col: " + col + " Row: " + row);
            game.getCurrLvl().getBlock(col, row).ToggleBlock();
            game.gameView.updateView();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Enters Frame Window
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Exist Frame Window
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (game.isLvlEditorOn()) {

            if (e.getSource() == game.gameView.saveBtn) {
                game.getCurrLvl().setName(game.gameView.textAreaFile.getText());
                game.getCurrLvl().saveLevel();
                return;
            }
            if (e.getSource() == game.gameView.loadBtn) {

                if (game.gameView.checkBoxCustomLvl.isSelected()) {
                    game.isCustomLevel = true;
                } else {
                    game.isCustomLevel = false;
                }

                game.loadLevel(game.gameView.textAreaFile.getText(), game.isCustomLevel);
                return;
            }
        }

        // Menubar
        if (e.getSource() == game.gameView.restartLevel) {
            game.gameView.sound.stopMusic();
            game.restartLevel();
            System.out.println("Level restarted!");
            return;
        }
        if (e.getSource() == game.gameView.startLvlEditor) {
            game.gameView.startLevelEditor(!game.isLvlEditorOn());
            return;
        }
        if (e.getSource() == game.gameView.zoomIn) {
            game.gameView.setBlockSize(game.gameView.getBlockSize() + 16);
            System.out.println("Zoomed in");
            game.gameView.jFrame.repaint();
            return;
        }

        if (e.getSource() == game.gameView.zoomOut) {
            if (game.gameView.getBlockSize() >= 32) {
                System.out.println("Zoomed out");
                game.gameView.setBlockSize(game.gameView.getBlockSize() - 8);
                game.gameView.jFrame.repaint();
            }
            return;
        }
    }

}
