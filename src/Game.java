
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Fire {

    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);
    private int time = 0;
    private int fire = 0;

    private BufferedImage image;

    private ArrayList<Fire> fire_array = new ArrayList<Fire>();
    private int fireMove = 1;

    private int ballX = 0;
    private int ballMove = 2;

    private int spaceshipX = 0;
    private int spaceshipMove = 30;

    public boolean control() {
        for (Fire f : fire_array) {
            if (new Rectangle(f.getX(), f.getY(), 10, 20).intersects(new Rectangle(ballX, 0, 20, 20))) {
                return true;
            }

        }
        return false;
    }

    public Game() {
        try {
            image = ImageIO.read(new FileInputStream(new File("spaceship.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        time += 5;
        g.setColor(Color.red);
        g.fillOval(ballX, 0, 20, 20);
        g.drawImage(image, spaceshipX, 460, image.getWidth() / 6, image.getHeight() / 6, this);

        for (Fire f : fire_array) {
            if (f.getY() < 0) {
                fire_array.remove(f);
            }
        }
        g.setColor(Color.blue);
        for (Fire f : fire_array) {
            g.fillRect(f.getX(), f.getY(), 10, 20);
        }
        if (control()) {
            timer.stop();
            String message = "You won! \n" + "Fire count: " + fire + "\nTime: " + time / 1000.0 + " s";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) {
            if (spaceshipX <= 0) {
                spaceshipX = 0;
            } else {
                spaceshipX -= spaceshipMove;

            }
        } else if (c == KeyEvent.VK_RIGHT) {
            if (spaceshipX >= 720) {
                spaceshipX = 720;
            } else {
                spaceshipX += spaceshipMove;

            }
        } else if (c == KeyEvent.VK_SPACE) {
            fire_array.add(new Fire(spaceshipX + 36, 460));

            fire++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Fire f : fire_array) {
            f.setY(f.getY() - fireMove);
        }
        ballX += ballMove;

        if (ballX >= 750) {
            ballMove = -ballMove;
        }

        if (ballX <= 0) {
            ballMove = -ballMove;
        }
        repaint();
    }

}
