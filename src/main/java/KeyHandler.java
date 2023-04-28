import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener, MouseListener{

    public boolean northPressed, southPressed, westPressed, eastPressed, buttonPressed;

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

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("You clicked the mouse");
        System.out.println("x: " + e.getXOnScreen() + " y: " + e.getYOnScreen());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Enters Frame Window
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Exist Frame Window
    }
    
}
