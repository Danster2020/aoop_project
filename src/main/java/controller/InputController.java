package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Event;
import model.Player;
import view.GameView;

public abstract class InputController {

    private KeyHandler keyHandler;
    private GameView view;
    private JFrame frame;

    InputController(GameView view, KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        this.view = view;
        this.frame = view.getjFrame();
    }

    // movement
    public void inputAction(KeyHandler.inputSignal action) {
        keyHandler.controllerAction(action);
    }

    public GameView getView() {
        return view;
    }

    public JFrame getFrame() {
        return frame;
    }

}

class Keyboard extends InputController implements KeyListener {

    Keyboard(GameView view, KeyHandler keyHandler) {
        super(view, keyHandler);
        getFrame().addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyboardINT = e.getKeyCode();

        if (keyboardINT == KeyEvent.VK_W || keyboardINT == KeyEvent.VK_UP) {
            inputAction(KeyHandler.inputSignal.INPUT_UP);
        }
        if (keyboardINT == KeyEvent.VK_S || keyboardINT == KeyEvent.VK_DOWN) {
            inputAction(KeyHandler.inputSignal.INPUT_DOWN);
        }
        if (keyboardINT == KeyEvent.VK_A || keyboardINT == KeyEvent.VK_LEFT) {
            inputAction(KeyHandler.inputSignal.INPUT_LEFT);
        }
        if (keyboardINT == KeyEvent.VK_D || keyboardINT == KeyEvent.VK_RIGHT) {
            inputAction(KeyHandler.inputSignal.INPUT_RIGHT);
        }
        if (keyboardINT == KeyEvent.VK_SPACE) {
            inputAction(KeyHandler.inputSignal.INPUT_RESTART);
        }
        System.out.println("you pressed " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}

class Mouse extends InputController implements MouseListener {

    Mouse(GameView view, KeyHandler keyHandler) {
        super(view, keyHandler);
        view.getMouseLabel().addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int col = e.getX() / getView().getBlockSize();
        int row = e.getY() / getView().getBlockSize();
        System.out.println("col: " + col + " Row: " + row);

        // LEVEL EDITOR
        if (getView().getGame().isLvlEditorOn()) {
            getView().getGame().getCurrLvl().getBlock(col, row).ToggleBlock();
            getView().getGame().getGameData().notifyObservers(Event.NOTHING);
            return;
        }

        Player player = getView().getGame().getPlayer();
        System.out.println("player col: " + player.getCol() + " Row: " + player.getRow());

        if (player.getCol() > col && player.getRow() == row) {
            inputAction(KeyHandler.inputSignal.INPUT_LEFT);
            return;
        }

        if (player.getCol() < col && player.getRow() == row) {
            inputAction(KeyHandler.inputSignal.INPUT_RIGHT);
            return;
        }

        if (player.getRow() > row && player.getCol() == col) {
            inputAction(KeyHandler.inputSignal.INPUT_UP);
            return;
        }

        if (player.getRow() < row && player.getCol() == col) {
            inputAction(KeyHandler.inputSignal.INPUT_DOWN);
            return;
        }

        if (SwingUtilities.isMiddleMouseButton(e)) {
            inputAction(KeyHandler.inputSignal.INPUT_RESTART);
            return;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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

}