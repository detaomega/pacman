import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PACMAN_SPEED = 6;

    private int pacsLeft, score;
    private int[] dx, dy;
    //private Point playerpos; 
    private int pacmand_x, pacmand_y;
    private Image pacmanup, pacmandown, pacmanleft, pacmanright, pacman1;
    private Image [] ghost_left;
    private Image [] ghost_right;
    private int [] ghost_x, ghost_y, ghost_nextx, ghost_nexty; 
    private int viewghost_x, viewghost_y;
    private int req_x, req_y;
    private boolean initflag;
    private boolean dying;

    private DrawPacman drawpac;

    private int levelData[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };  
    // pacman map



    private int[] screenData;
    private Timer timer;

    // constructor
    public Board() {
        initVariables();
        loadImages();
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
    }


    private void initVariables() {
        screenData = new int[N_BLOCKS * N_BLOCKS];
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);
        dx = new int[4];
        dy = new int[4];
        drawpac = new DrawPacman();

        ghost_x = new int[4];
        ghost_y = new int[4];
        ghost_nextx = new int[4];
        ghost_nexty = new int[4];
        ghost_left = new Image[4];
        ghost_right = new Image[4];
        initflag = true;

        timer = new Timer(40, this); // 每0.04秒repaint
        timer.start();
    }

    private void playGame(Graphics2D g2d) {
        drawpac.drawPacman(g2d);
        checkMaze();
    }
    
    //the fuction of showing starting screen
    private void showIntroScreen(Graphics2D g2d) { 
        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }



    private void drawScore(Graphics2D g) {
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < pacsLeft; i++) {
            g.drawImage(pacmanleft, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {
        boolean finished = true;

        for (int i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            if ((screenData[i] & 48) != 0) {
                finished = false;
                break;
            }
        }

        if (finished) {
            restartgame();
        }
    }

    private void moveGhost(Graphics2D g2d) {

        for (int i = 0; i < 4; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                int pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);
                int count = 0;
                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_nextx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_nexty[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_nextx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_nexty[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_nextx[i] = 0;
                        ghost_nexty[i] = 0;
                    } else {
                        ghost_nextx[i] = -ghost_nextx[i];
                        ghost_nexty[i] = -ghost_nexty[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_nextx[i] = dx[count];
                    ghost_nexty[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_nextx[i] * 6);
            ghost_y[i] = ghost_y[i] + (ghost_nexty[i] * 6);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1, i);

            if (drawpac.pacman_x > (ghost_x[i] - 15) && drawpac.pacman_x < (ghost_x[i] + 15)
                    && drawpac.pacman_y > (ghost_y[i] - 15) && drawpac.pacman_y < (ghost_y[i] + 15)
                    && inGame) {

                dying = true;
            }
        }
    }


    private void movePacman() {
        int pos, ch;

        if (req_x == 0 && req_y == 0) return;

        if (req_x == -pacmand_x && req_y == -pacmand_y) {
            pacmand_x = req_x;
            pacmand_y = req_y;
            drawpac.view_x = pacmand_x;
            drawpac.view_y = pacmand_y;
        }

        if (drawpac.pacman_x % BLOCK_SIZE == 0 && drawpac.pacman_y % BLOCK_SIZE == 0) {
            pos = drawpac.pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (drawpac.pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] -= 16;
                score++;
            }

            if (req_x != 0 || req_y != 0) {
                if (!((req_x == -1 && req_y == 0 && (ch & 1) != 0)
                        || (req_x == 1 && req_y == 0 && (ch & 4) != 0)
                        || (req_x == 0 && req_y == -1 && (ch & 2) != 0)
                        || (req_x == 0 && req_y == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_x;
                    pacmand_y = req_y;
                    drawpac.view_x = req_x;
                    drawpac.view_y = req_y;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }

        }
        drawpac.pacman_x = drawpac.pacman_x + 6 * pacmand_x;
        drawpac.pacman_y = drawpac.pacman_y + 6 * pacmand_y;
    }

    private void drawGhost(Graphics2D g2d, int x, int y,int id) {
        if (ghost_nextx[id] + ghost_nexty[id] == -1)
            g2d.drawImage(ghost_left[id], x, y, this);
        else 
            g2d.drawImage(ghost_right[id], x, y, this);
    }


    private void drawMaze(Graphics2D g2d) {

        int i = 0;

        for (int y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (int x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) { // 左邊界
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) { // 上邊界
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) { // 右邊界
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) { // 下邊界
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) { // 果實
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
                i++;
            }
        }
    }

    private void initGame() {
        
        pacsLeft = 3;
        score = 0;
        restartgame();
    }

    private void restartgame() {
        if (dying == false) {
            for (int i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
                screenData[i] = levelData[i];
            }
        }
        
        initflag = true;
        drawpac.pacman_x = 7 * BLOCK_SIZE;
        drawpac.pacman_y = 11 * BLOCK_SIZE;
        drawpac.view_x = 0;
        drawpac.view_y = 1;
        pacmand_x = 0;
        pacmand_y = 0;
        req_x = 0;
        req_y = 0;
        for (int i = 0; i < 4; i++) {
            ghost_x[i] = 7 * BLOCK_SIZE;
            ghost_y[i] = i * BLOCK_SIZE;
        }
        
        dying = false;
    }

    private void loadImages() {
        drawpac.loadImages("playerOne");

        ghost_left[0] = new ImageIcon("images/ghostOrange/ghostOrangeLeft.jpeg").getImage();
        ghost_right[0] = new ImageIcon("images/ghostOrange/ghostOrangeRight.jpeg").getImage();

        ghost_left[1] = new ImageIcon("images/ghostBlue/ghostBlueLeft.jpeg").getImage();
        ghost_right[1] = new ImageIcon("images/ghostBlue/ghostBlueRight.jpeg").getImage();

        ghost_left[2] = new ImageIcon("images/ghostRED/ghostREDLeft.jpeg").getImage();
        ghost_right[2] = new ImageIcon("images/ghostRED/ghostREDRight.jpeg").getImage();

        ghost_left[3] = new ImageIcon("images/ghostPink/ghostPinkLeft.jpeg").getImage();
        ghost_right[3] = new ImageIcon("images/ghostPink/ghostPinkRight.jpeg").getImage();
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        playGame(g2d);
        drawMaze(g2d);
        drawScore(g2d);
        if (inGame) {
            if (dying) {

                death();
                
            }
            else {
               
                drawpac.drawPacman(g2d);
                movePacman();
                moveGhost(g2d);
            }

        } 
        else {
            showIntroScreen(g2d);
        }

    }

    private void death() {

        pacsLeft--;

        if (pacsLeft == 0) {
            inGame = false;
        }
        restartgame();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
                initflag = false;
                if (key == KeyEvent.VK_LEFT) {
                    req_x = -1;
                    req_y = 0;       
                } 
                else if (key == KeyEvent.VK_RIGHT) {
                    req_x = 1;
                    req_y = 0;
             
                } 
                else if (key == KeyEvent.VK_UP) {
                    req_x = 0;
                    req_y = -1;
                } 
                else if (key == KeyEvent.VK_DOWN) {
                    req_x = 0;
                    req_y = 1;
                } 
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } 
                else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } 
            else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}