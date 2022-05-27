import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Board2 extends JPanel implements ActionListener {
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 26;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PACMAN_SPEED = 6;

    private int pacsLifeP1, pacsLifeP2, p1score, p2score;
    private int dying_state;
    private int[] dx, dy;
    //private Point playerpos; 
 
    private Image pacmanup, pacmandown, pacmanleft, pacmanright, pacman1;
    private Image pacmanupP2, pacmandownP2, pacmanleftP2, pacmanrightP2, pacman2;
    private Image [] ghost_left;
    private Image [] ghost_right;
    private int  p1pacmand_x, p1pacmand_y,p2pacmand_x, p2pacmand_y;
    private int [] ghost_x, ghost_y, ghost_nextx, ghost_nexty; 
    private int speed[] = {4, 6, 8, 8};
    private Point p1, p2;
    private Point p1view, p2view;
    private Point p1dir, p2dir; // 判斷方向
    //private char p1direction;
    private boolean initflag, initflag2;
    private DrawPacman pacman1, pacman2;

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
    public Board2() {
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
        ghost_x = new int[4];
        ghost_y = new int[4];
        ghost_nextx = new int[4];
        ghost_nexty = new int[4];
        ghost_left = new Image[4];
        ghost_right = new Image[4];

        initflag = true;    
        initflag2 = true;
        dying_state = 0;
        p1 = new Point(10 * BLOCK_SIZE, 11 * BLOCK_SIZE);
        p2 = new Point(4 * BLOCK_SIZE, 11 * BLOCK_SIZE);
        p1dir = new Point(0,0);
        p2dir = new Point(0,0);
        p1view = new Point(0, 0);
        p2view = new Point(0, 0);
        timer = new Timer(40, this); // 每0.04秒repaint
        timer.start();
    }

    private void playGame(Graphics2D g2d) {
        drawPacman(g2d);
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
        String s1,s2;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s1 = "Player1: " + p1score;
        s2 = "Player2: " + p2score;
        g.drawString(s1, 60, SCREEN_SIZE + 20);
        g.drawString(s2, 60, SCREEN_SIZE + 40);

        for (int i = 0; i < pacsLifeP1; i++) {
            g.drawImage(pacmanleft, i * 28 + 8, SCREEN_SIZE + 3, this);
        }

        for (int i = 0; i < pacsLifeP2; i++) {
            g.drawImage(pacmanleftP2, i * 28 + 8, SCREEN_SIZE + 25, this);
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

            ghost_x[i] = ghost_x[i] + (ghost_nextx[i] * speed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_nexty[i] * speed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1, i);

            if (p1.x > (ghost_x[i] - 12) && p1.x < (ghost_x[i] + 12)
                    && p1.y > (ghost_y[i] - 12) && p1.y < (ghost_y[i] + 12)
                    && inGame) {

                dying_state |= 1;
            }

            if (p2.x > (ghost_x[i] - 12) && p2.x < (ghost_x[i] + 12)
                    && p2.y > (ghost_y[i] - 12) && p2.y < (ghost_y[i] + 12)
                    && inGame) {

                dying_state |= 2;
            }
        }
    }


    private void drawGhost(Graphics2D g2d, int x, int y,int id) {
        if (ghost_nextx[id] + ghost_nexty[id] == -1)
            g2d.drawImage(ghost_left[id], x, y, this);
        else 
            g2d.drawImage(ghost_right[id], x, y, this);
    }


    private void movePacman() {
        int p1pos, p2pos, p1ch, p2ch;

        if (p1dir.x == -p1pacmand_x && p1dir.y == -p1pacmand_y) {
            p1pacmand_x = p1dir.x;
            p1pacmand_y = p1dir.y;
            p1view.x = p1dir.x;
            p1view.y = p1dir.y;
        }

        if (p1.x % BLOCK_SIZE == 0 && p1.y % BLOCK_SIZE == 0) {
            p1pos = p1.x / BLOCK_SIZE + N_BLOCKS * (int) (p1.y / BLOCK_SIZE);
            p1ch = screenData[p1pos];

            if ((p1ch & 16) != 0) {
                screenData[p1pos] -= 16;
                p1score++;
            }

            
            if (p1dir.x != 0 || p1dir.y != 0) {
                if (!((p1dir.x == -1 && p1dir.y == 0 && (p1ch & 1) != 0)
                        || (p1dir.x == 1 && p1dir.y == 0 && (p1ch & 4) != 0)
                        || (p1dir.x == 0 && p1dir.y == -1 && (p1ch & 2) != 0)
                        || (p1dir.x == 0 && p1dir.y == 1 && (p1ch & 8) != 0))) {
                    p1pacmand_x = p1dir.x;
                    p1pacmand_y = p1dir.y;
                    p1view.x = p1dir.x;
                    p1view.y = p1dir.y;
                }
            }

            if (((p1pacmand_x == -1 && p1pacmand_y == 0) && (p1ch & 1) != 0)
                    || ((p1pacmand_x == 1 && p1pacmand_y == 0) && (p1ch & 4) != 0)
                    || ((p1pacmand_x == 0 && p1pacmand_y == -1)  && (p1ch & 2) != 0)
                    || ((p1pacmand_x == 0 && p1pacmand_y == 1) && (p1ch & 8) != 0)) {
                p1pacmand_x = 0;
                p1pacmand_y = 0;
            }
        }
        p1.x = p1.x + 6 * p1pacmand_x;
        p1.y = p1.y + 6 * p1pacmand_y;


        if (p2dir.x == -p2pacmand_x && p2dir.y == -p2pacmand_y) {
            p2pacmand_x = p2dir.x;
            p2pacmand_y = p2dir.y;
            p2view.x = p2dir.x;
            p2view.y = p2dir.y;
        }

        if (p2.x % BLOCK_SIZE == 0 && p2.y % BLOCK_SIZE == 0) {
            p2pos = p2.x / BLOCK_SIZE + N_BLOCKS * (int) (p2.y / BLOCK_SIZE);
            p2ch = screenData[p2pos];

            if ((p2ch & 16) != 0) {
                screenData[p2pos] -= 16;
                p2score++;
            }

            
            p2pacmand_x = p2dir.x;
            p2pacmand_y = p2dir.y;
            p2view.x = p2dir.x;
            p2view.y = p2dir.y;
             

            if (p2dir.x != 0 || p2dir.y != 0) {
                if (!((p2dir.x == -1 && p2dir.y == 0 && (p2ch & 1) != 0)
                        || (p2dir.x == 1 && p2dir.y == 0 && (p2ch & 4) != 0)
                        || (p2dir.x == 0 && p2dir.y == -1 && (p2ch & 2) != 0)
                        || (p2dir.x == 0 && p2dir.y == 1 && (p2ch & 8) != 0))) {
                    p2pacmand_x = p2dir.x;
                    p2pacmand_y = p2dir.y;
                    p2view.x = p2dir.x;
                    p2view.y = p2dir.y;
                }
            }

            if (((p2pacmand_x == -1 && p2pacmand_y == 0) && (p2ch & 1) != 0)
                    || ((p2pacmand_x == 1 && p2pacmand_y == 0) && (p2ch & 4) != 0)
                    || ((p2pacmand_x == 0 && p2pacmand_y == -1)  && (p2ch & 2) != 0)
                    || ((p2pacmand_x == 0 && p2pacmand_y == 1) && (p2ch & 8) != 0)) {
                p2pacmand_x = 0;
                p2pacmand_y = 0;
            }
        }
        p2.x = p2.x + 6 * p2pacmand_x;
        p2.y = p2.y + 6 * p2pacmand_y;
    }

    private void drawPacman(Graphics2D g2d) {
        if (pacsLifeP1 > 0) {
            if (p1view.x == -1 && p1view.y == 0) {
                drawPacnanLeft(g2d, p1);

            } 
            else if (p1view.x == 1 && p1view.y == 0 || initflag) {
                drawPacmanRight(g2d, p1);
            } 
            else if (p1view.x == 0 && p1view.y == -1) {
                drawPacmanUp(g2d, p1);
            } 
            else if (p1view.x == 0 && p1view.y == 1){
                drawPacmanDown(g2d, p1);
            }
        }
        if (pacsLifeP2 > 0) {
            if (p2view.x == -1 && p2view.y == 0 || initflag2) {
                drawPacnanLeftP2(g2d, p2);

            } 
            else if (p2view.x == 1 && p2view.y == 0) {
                drawPacmanRightP2(g2d, p2);
            } 
            else if (p2view.x == 0 && p2view.y == -1) {
                drawPacmanUpP2(g2d, p2);
            } 
            else if (p2view.x == 0 && p2view.y == 1){
                drawPacmanDownP2(g2d, p2);
            }
        }
    }
    


    // player one
    private void drawPacmanUp(Graphics2D g2d, Point p) {
        g2d.drawImage(pacmanup, p.x, p.y, this);    
    
    }

    private void drawPacmanDown(Graphics2D g2d, Point p) {
        g2d.drawImage(pacmandown, p.x, p.y, this);
        
    }

    private void drawPacnanLeft(Graphics2D g2d, Point p) {
        
        g2d.drawImage(pacmanleft, p.x, p.y, this);
        
    }

    private void drawPacmanRight(Graphics2D g2d, Point p) {
        g2d.drawImage(pacmanright, p.x , p.y , this);
    }

    // player two

    private void drawPacmanUpP2(Graphics2D g2d, Point p) {
        g2d.drawImage(pacmanupP2, p.x, p.y, this);    
    
    }

    private void drawPacmanDownP2(Graphics2D g2d, Point p) {
        g2d.drawImage(pacmandownP2, p.x, p.y, this);
        
    }

    private void drawPacnanLeftP2(Graphics2D g2d, Point p) {
        
        g2d.drawImage(pacmanleftP2, p.x, p.y, this);
        
    }

    private void drawPacmanRightP2(Graphics2D g2d, Point p) {
        g2d.drawImage(pacmanrightP2, p.x , p.y , this);
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
        pacsLifeP1 = 2;
        pacsLifeP2 = 2;
        p1score = -1;
        p2score = -1;
        restartgame();
    }

    private void restartgame() {
        Get_map maps = new Get_map();
        screenData = maps.Get_data();
        initflag = true;
        initflag2 = true;
        p1.x = 0 * BLOCK_SIZE;
        p1.y = 0 * BLOCK_SIZE;
        p2.x = 25 * BLOCK_SIZE;
        p2.y = 25 * BLOCK_SIZE;
        p1pacmand_x = 0;
        p1pacmand_y = 0;
        p2pacmand_x = 0;
        p2pacmand_y = 0;
        
        for (int i = 11; i < 15; i++) {
            ghost_x[i - 11] = i * BLOCK_SIZE;
            ghost_y[i - 11] = 12 * BLOCK_SIZE;
        }
        p1view.x = 0;
        p1view.y = 0;
        p1dir.x = 0;
        p1dir.y = 0;
        p2dir.x = 0;
        p2dir.y = 0;
 
    }

    private void loadImages() {
        pacman1 = new ImageIcon("images/plyerOne/down.png").getImage();

        pacmanup = new ImageIcon("images/playerOne/up.png").getImage();
    
        pacmandown = new ImageIcon("images/playerOne/down.png").getImage();
      
        pacmanleft = new ImageIcon("images/playerOne/left.png").getImage();

        pacmanright = new ImageIcon("images/playerOne/right.png").getImage();

        pacmanupP2 = new ImageIcon("images/playerTwo/up.png").getImage();
    
        pacmandownP2 = new ImageIcon("images/playerTwo/down.png").getImage();
      
        pacmanleftP2 = new ImageIcon("images/playerTwo/left.png").getImage();

        pacmanrightP2 = new ImageIcon("images/playerTwo/right.png").getImage();

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

      
        if (inGame) {
            playGame(g2d);
            drawMaze(g2d);
            drawScore(g2d);
            drawPacman(g2d);
            movePacman();
            moveGhost(g2d);
            if ((dying_state & 1) != 0) {
                p1.x = 0 * BLOCK_SIZE;
                p1.y = 0 * BLOCK_SIZE;
                pacsLifeP1--;
            }
            if ((dying_state & 2) != 0) {
                p2.x = 25 * BLOCK_SIZE;
                p2.y = 25 * BLOCK_SIZE;
                pacsLifeP2--;
            }
            dying_state = 0;
        } 
        else {
            showIntroScreen(g2d);
        }
        if (pacsLifeP1 <= 0 && pacsLifeP2 <= 0) {
            inGame = false;
        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
                
                
                if (key == KeyEvent.VK_LEFT) {
                    p1dir.x = -1;
                    p1dir.y = 0;
                    initflag = false;
                           
                } 
                else if (key == KeyEvent.VK_RIGHT) {
                    p1dir.x = 1;
                    p1dir.y = 0;
                    initflag = false;
                } 
                else if (key == KeyEvent.VK_UP) {
                    p1dir.x = 0;
                    p1dir.y = -1;
                    initflag = false;
                } 
                else if (key == KeyEvent.VK_DOWN) {
                    p1dir.x = 0;
                    p1dir.y = 1;
                    initflag = false;
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

                if (key == KeyEvent.VK_A) {
                    p2dir.x = -1;
                    p2dir.y = 0;
                    initflag2 = false;          
                } 
                else if (key == KeyEvent.VK_D) {
                    p2dir.x = 1;
                    p2dir.y = 0;
                    initflag2 = false;
                } 
                else if (key == KeyEvent.VK_W) {
                    p2dir.x = 0;
                    p2dir.y = -1;
                    initflag2 = false;
                } 
                else if (key == KeyEvent.VK_S) {
                    p2dir.x = 0;
                    p2dir.y = 1;
                    initflag2 = false;
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
