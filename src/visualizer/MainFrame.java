
package visualizer;

import javax.swing.JFrame;

/**
 * 
 * @author Nuno Fonseca
 */
public class MainFrame extends JFrame{
    Screen screen;
    public MainFrame() {
        screen = new Screen();
        add(screen);
        pack();
        
        setTitle("3D Graphics");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new MainFrame();
    }
    
}
