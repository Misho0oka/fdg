import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    static final int EL_SIZE = 20;
    static final int NOMER_EL = (WIDTH * HEIGHT) / (EL_SIZE * EL_SIZE);
    int length = 4;
    int appleEaten;
    int appleX;
    int appleY;
    final int x[] = new int[NOMER_EL];
    final int y[] = new int[NOMER_EL];
    char direction = 'D';
    boolean running = false;
    Random random;
    Timer timer;
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new mKeyAdapter());
        play();
    }
    public void play() {
        addapple();
        running = true;
        timer = new Timer(60, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }
    public void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (direction == 'L') {
            x[0] = x[0] - EL_SIZE;
        } else if (direction == 'R') {
            x[0] = x[0] + EL_SIZE;
        } else if (direction == 'U') {
            y[0] = y[0] - EL_SIZE;
        } else {
            y[0] = y[0] + EL_SIZE;
        }
    }
    public class mKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
    public void checkFood() {
        if(x[0] == appleX && y[0] == appleY) {
            length++;
            appleEaten++;
            addapple();
        }
    }
    public void draw(Graphics visual) {
        if (running) {
            visual.setColor(new Color(255, 0, 0));
            visual.fillOval(appleX, appleY, EL_SIZE, EL_SIZE);

            visual.setColor(Color.YELLOW);
            visual.fillRect(x[0], y[0], EL_SIZE, EL_SIZE);

            for (int i = 1; i < length; i++) {
                visual.setColor(new Color(122, 32, 178));
                visual.fillRect(x[i], y[i], EL_SIZE, EL_SIZE);
            }

            visual.setColor(Color.white);
            visual.setFont(new Font("", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(visual.getFont());
            visual.drawString("Score: " + appleEaten, (WIDTH - metrics.stringWidth("Score: " + appleEaten)) / 2, visual.getFont().getSize());

        } else {
            krai(visual);
        }
    }
    public void addapple() {
        appleX = random.nextInt((int)(WIDTH / EL_SIZE))*EL_SIZE;
        appleY = random.nextInt((int)(HEIGHT / EL_SIZE))*EL_SIZE;
    }
    public void Hit() {
        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        if (x[0] < 0 || x[0] > WIDTH-10 || y[0] < 0 || y[0] > HEIGHT-10) {
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }
    public void krai(Graphics visual) {
        visual.setColor(Color.red);
        visual.setFont(new Font("", Font.BOLD, 50));
        FontMetrics stoinost = getFontMetrics(visual.getFont());
        visual.drawString("Гушна Букета", (WIDTH - stoinost.stringWidth("Гушна Букета")) / 2, HEIGHT / 2);

        visual.setColor(Color.white);
        visual.setFont(new Font("", Font.BOLD, 25));
        stoinost = getFontMetrics(visual.getFont());
        visual.drawString("Score: " + appleEaten, (WIDTH - stoinost.stringWidth("Score: " + appleEaten)) / 2, visual.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (running) {
            move();
            checkFood();
            Hit();
        }
        repaint();
    }
}