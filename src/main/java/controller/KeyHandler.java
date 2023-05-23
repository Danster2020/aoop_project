package controller;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Event;
import view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements ActionListener {

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

        new Keyboard(view, this);
        new Mouse(view, this);
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
    public void actionPerformed(ActionEvent e) {

        if (game.isLvlEditorOn()) {

            if (e.getSource() == game.gameView.saveBtn) {
                game.getCurrLvl().setName(game.gameView.textAreaFile.getText());
                game.saveLevel();
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
            game.restartLevel();
            return;
        }
        if (e.getSource() == game.gameView.startLvlEditor) {
            game.gameView.startLevelEditor(!game.isLvlEditorOn());
            return;
        }
        if (e.getSource() == game.gameView.zoomIn) {
            game.gameView.setBlockSize(game.gameView.getBlockSize() + 16);
            game.gameData.notifyObservers(Event.ZOOM_IN);
            System.out.println("Zoomed in");
            return;
        }

        if (e.getSource() == game.gameView.zoomOut) {
            if (game.gameView.getBlockSize() >= 32) {
                game.gameView.setBlockSize(game.gameView.getBlockSize() - 8);
                game.gameData.notifyObservers(Event.ZOOM_OUT);
                System.out.println("Zoomed out");
            }
            return;
        }

        if (e.getSource() == game.gameView.saveGame) {
            game.saveGame();
            return;
        }

        if (e.getSource() == game.gameView.loadGame) {
            game.loadGame();
            return;
        }

        if (e.getSource() == game.gameView.restartGame) {
            game.restartGame();
            return;
        }
    }

}
