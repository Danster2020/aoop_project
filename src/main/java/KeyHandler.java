import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener{

    public boolean northPressed, southPressed, westPressed, eastPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //keyTyped = Invoked when a key is typed. Uses KeyChar, char output.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyboardINT = e.getKeyCode();

        if(keyboardINT == KeyEvent.VK_W){
            northPressed = true;
        }
        if(keyboardINT == KeyEvent.VK_S){
            southPressed = true;
        }
        if(keyboardINT == KeyEvent.VK_A){
            westPressed = true;
        }
        if(keyboardINT == KeyEvent.VK_D){
            eastPressed = true;
        }
        System.out.println("you pressed " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyboardINT = e.getKeyCode();

        if(keyboardINT == KeyEvent.VK_W){
            northPressed = false;
        }
        if(keyboardINT == KeyEvent.VK_S){
            southPressed = false;
        }
        if(keyboardINT == KeyEvent.VK_A){
            westPressed = false;
        }
        if(keyboardINT == KeyEvent.VK_D){
            eastPressed = false;
        }
        System.out.println("you released " + e.getKeyChar());
    }
    
}
