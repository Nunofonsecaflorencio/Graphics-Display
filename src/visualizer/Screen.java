
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
    private final int ROWS = 48, COLS = 64;
    
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
        drawGridLines(g2d);
        
        plotLine(4, 6, 60, 32, g2d);
        
        
        g2d.dispose();
    }

    private void drawGridLines(Graphics2D g2d) {
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

    private void plotPoint(int i, int j, Graphics2D g2d) {
        g2d.setColor(TILE_ON_COLOR);
        g2d.fillRect(getLoc(i), getLoc(j), TILE_SIZE, TILE_SIZE);
    }
    
    private void plotLineLow(int x0, int y0, int x1, int y1, Graphics2D g2d){
        int dx = x1 - x0;
        int dy = y1 - y0;
        int yi = 1;
        if (dy < 0){
            yi = -1;
            dy = -dy;
        }
        int D = (2 * dy) - dx;
        int y = y0;
        
        for (int x = x0; x <= x1; x++) {
            plotPoint(x, y, g2d);
            if (D > 0){
                y = y + yi;
                D = D + (2 * (dy - dx));
            }else{
                D = D + 2*dy;
            }
        }
    }
    
    private void plotLineHigh(int x0, int y0, int x1, int y1, Graphics2D g2d){
        int dx = x1 - x0;
        int dy = y1 - y0;
        int xi = 1;
        if (dx < 0){
            xi = -1;
            dx = -dx;
        }
        int D = (2 * dx) - dy;
        int x = x0;
        
        for (int y = y0; y <= y1; y++) {
            plotPoint(x, y, g2d);
            if (D > 0){
                x = x + xi;
                D = D + (2 * (dx - dy));
            }else{
                D = D + 2*dx;
            }
        }
    }
    
    private void plotLine(int x0, int y0, int x1, int y1, Graphics2D g2d){
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0)){
            if (x0 > x1)
                plotLineLow(x1, y1, x0, y0, g2d);
            else
                plotLineLow(x0, y0, x1, y1, g2d);
        }else{
            if (y0 > y1)
                plotLineHigh(x1, y1, x0, y0, g2d);
            else
                plotLineHigh(x0, y0, x1, y1, g2d);
        }
    }
    
    private int getLoc(int i){
        return i * (TILE_SIZE + LINE_SIZE);
    }
    
    private int getI(int loc){
        return loc / (TILE_SIZE + LINE_SIZE);
    }
}
