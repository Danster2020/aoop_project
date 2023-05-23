package controller;

import model.Event;
import view.GameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyHandler implements ActionListener {

    public enum inputSignal {
        INPUT_UP,
        INPUT_DOWN,
        INPUT_LEFT,
        INPUT_RIGHT,
        INPUT_RESTART,
    }

    private Game game;

    public KeyHandler(GameView view) {

        this.game = view.getGame();

        new Keyboard(view, this);
        new Mouse(view, this);
    }

    /**
     * Receives signal from controller and manipulates the game.
     * 
     * @param signal
     */
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

    /**
     * Handels user interaction with buttons etc. on the jFrame.
     * 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (game.isLvlEditorOn()) {

            if (e.getSource() == game.getGameView().getSaveBtn()) {
                game.getCurrLvl().setName(game.getGameView().getTextAreaFile().getText());
                game.saveLevel();
                return;
            }
            if (e.getSource() == game.getGameView().getLoadBtn()) {

                if (game.getGameView().getCheckBoxCustomLvl().isSelected()) {
                    game.setIsCustomLevel(true);
                } else {
                    game.setIsCustomLevel(false);
                }

                game.loadLevel(game.getGameView().getTextAreaFile().getText(), game.isCustomLevel());
                return;
            }
        }

        // Menubar
        if (e.getSource() == game.getGameView().getRestartLevel()) {
            game.restartLevel();
            return;
        }
        if (e.getSource() == game.getGameView().getStartLvlEditor()) {
            game.getGameView().startLevelEditor(!game.isLvlEditorOn());
            return;
        }
        if (e.getSource() == game.getGameView().getZoomIn()) {
            game.getGameView().setBlockSize(game.getGameView().getBlockSize() + 16);
            game.getGameData().notifyObservers(Event.ZOOM_IN);
            System.out.println("Zoomed in");
            return;
        }

        if (e.getSource() == game.getGameView().getZoomOut()) {
            if (game.getGameView().getBlockSize() >= 32) {
                game.getGameView().setBlockSize(game.getGameView().getBlockSize() - 8);
                game.getGameData().notifyObservers(Event.ZOOM_OUT);
                System.out.println("Zoomed out");
            }
            return;
        }

        if (e.getSource() == game.getGameView().getSaveGame()) {
            game.saveGame();
            return;
        }

        if (e.getSource() == game.getGameView().getLoadGame()) {
            game.loadGame();
            return;
        }

        if (e.getSource() == game.getGameView().getRestartGame()) {
            game.restartGame();
            return;
        }
    }

}
