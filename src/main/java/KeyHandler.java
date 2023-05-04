import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener, MouseListener, ActionListener {

    public boolean northPressed, southPressed, westPressed, eastPressed, buttonPressed;
    Game game;

    KeyHandler(Game g) {
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
        game.jFrame.repaint();
        System.out.println("you pressed " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // int keyboardINT = e.getKeyCode();

        // if (keyboardINT == KeyEvent.VK_W) {
        // northPressed = false;
        // }
        // if (keyboardINT == KeyEvent.VK_S) {
        // southPressed = false;
        // }
        // if (keyboardINT == KeyEvent.VK_A) {
        // westPressed = false;
        // }
        // if (keyboardINT == KeyEvent.VK_D) {
        // eastPressed = false;
        // }
        // System.out.println("you released " + e.getKeyChar());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println("You clicked the mouse");
        // System.out.println("x: " + e.getX() + " y: " + e.getY());

        if (game.isLvlEditorOn()) {
            int col = e.getX() / 32;
            int row = e.getY() / 32;
            System.out.println("col: " + col + " Row: " + row);
            game.getCurrLvl().getBlock(col, row).ToggleBlock();
            game.jFrame.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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

            if (e.getSource() == game.saveBtn) {
                game.getCurrLvl().setName(game.textAreaFile.getText());
                game.getCurrLvl().saveLevel();
                return;
            }
            if (e.getSource() == game.loadBtn) {
                String customPath = "";
                if (game.checkBoxCustomLvl.isSelected()) {
                    customPath = "custom/";
                }

                game.loadLevel(game.textAreaFile.getText(), customPath);
                return;
            }
        }

        if (e.getSource() == game.restartLevel) {
            String customPath = "";
            if (game.isCustomLevel) {
                customPath = "custom/";
            }
            game.sound.stopMusic();
            game.loadLevel(game.getCurrLvl().levelName, customPath);
            System.out.println("Level restarted!");
            return;
        }
        if (e.getSource() == game.startLvlEditor) {
            game.startLevelEditor(!game.isLvlEditorOn());
            return;
        }
    }

}
