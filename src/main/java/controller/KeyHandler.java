package controller;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements MouseListener, ActionListener {

    public enum inputSignal {
        INPUT_UP,
        INPUT_DOWN,
        INPUT_LEFT,
        INPUT_RIGHT,
        INPUT_RESTART,
    }

    public boolean northPressed, southPressed, westPressed, eastPressed, buttonPressed;
    public Game game;

    public KeyHandler(GameView view) {

        this.game = view.game;

        // NEW
        // InputController inputController = new Mouse(view.jFrame, this);
        new Keyboard(view, this);
    }

    // received signal from controller
    public void controllerAction(inputSignal signal) {

        switch (signal) {
            case INPUT_UP:
                game.getPlayer().move(Game.Direction.UP);
                break;
            case INPUT_DOWN:
                game.getPlayer().move(Game.Direction.DOWN);
                break;
            case INPUT_LEFT:
                game.getPlayer().move(Game.Direction.LEFT);
                break;
            case INPUT_RIGHT:
                game.getPlayer().move(Game.Direction.RIGHT);
                break;
            case INPUT_RESTART:
                game.restartLevel();
                break;
            default:
                break;
        }
        game.checkGameState();
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
            game.gameData.notifyObservers();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
            return;
        }
        if (e.getSource() == game.gameView.startLvlEditor) {
            game.gameView.startLevelEditor(!game.isLvlEditorOn());
            return;
        }
        if (e.getSource() == game.gameView.zoomIn) {
            game.gameView.setBlockSize(game.gameView.getBlockSize() + 16);
            System.out.println("Zoomed in");
            game.gameData.notifyObservers();
            return;
        }

        if (e.getSource() == game.gameView.zoomOut) {
            if (game.gameView.getBlockSize() >= 32) {
                System.out.println("Zoomed out");
                game.gameView.setBlockSize(game.gameView.getBlockSize() - 8);
                game.gameData.notifyObservers();
            }
            return;
        }
    }

}
