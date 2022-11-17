
package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Nuno Fonseca
 */
public class Screen extends JPanel implements ActionListener{
    
    private final int TILE_SIZE = 16;
    private final int LINE_SIZE = TILE_SIZE / 5;
    private final int ROWS = 32, COLS = 32;
    
    private final int WIDTH = (TILE_SIZE + LINE_SIZE) * COLS - LINE_SIZE;
    private final int HEIGHT = (TILE_SIZE + LINE_SIZE) * ROWS - LINE_SIZE;
    
    private final Color TILE_ON_COLOR = new Color(248, 163, 0);
    private final Color TILE_OFF_COLOR = new Color(153, 99, 0);
    private final Color LINE_COLOR = new Color(47, 47, 47);
    
    private final int DELAY = 60 / 1000;
    private Timer timer;
    
    int mx, my;
    
    public Screen() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(TILE_OFF_COLOR);
        
        timer = new Timer(DELAY, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    private void update() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawLines(g2d);
        
        for (int i = 0; i < COLS; i++) {
            drawPoint(getLoc(i), getLoc(i / 2), g2d);
        }
        
        
        g2d.dispose();
    }

    private void drawLines(Graphics2D g2d) {
        g2d.setColor(LINE_COLOR);
        for (int i = 1; i < ROWS; i++) {
            int y = i * (TILE_SIZE + LINE_SIZE) - LINE_SIZE;
            g2d.fillRect(0, y, WIDTH, LINE_SIZE);
        }
        
        for (int i = 1; i < COLS; i++) {
            int x = i * (TILE_SIZE + LINE_SIZE) - LINE_SIZE;
            g2d.fillRect(x, 0, LINE_SIZE, HEIGHT);
        }
    }

    private void drawPoint(int i, int j, Graphics2D g2d) {
        g2d.setColor(TILE_ON_COLOR);
        g2d.fillRect(i, j, TILE_SIZE, TILE_SIZE);
    }

    
    private int getLoc(int i){
        return i * (TILE_SIZE + LINE_SIZE);
    }
    
    private int getI(int loc){
        return loc / (TILE_SIZE + LINE_SIZE);
    }
}
