import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Board2 extends JPanel implements ActionListener {
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);


    private boolean inGame = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 26;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PACMAN_SPEED = 6;

    private int pacsLifeP1, pacsLifeP2, p1score, p2score;
    private int dying_state;
    private int[] dx, dy;
    //private Point playerpos; 
 

    private int  p1pacmand_x, p1pacmand_y,p2pacmand_x, p2pacmand_y;
    private int speed[] = {4, 6, 8, 8};
    private Point p1dir, p2dir; // 判斷方向

    private DrawPacman pacman1, pacman2;
    private DrawGhost [] ghost;  
    private Maze maze;
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

        maze = new Maze(N_BLOCKS);
        d = new Dimension(400, 400);
        dx = new int[4];
        dy = new int[4];
        
        ghost = new DrawGhost[4];
        for (int i = 0; i < 4; i++) {
            ghost[i] = new DrawGhost();
        }
        
        p1dir = new Point(0, 0);
        p2dir = new Point(0, 0);
        pacman1 = new DrawPacman();
        pacman2 = new DrawPacman();
        dying_state = 0;
        timer = new Timer(40, this); // 每0.04秒repaint
        timer.start();
    }

    private void playGame(Graphics2D g2d) {
        drawPacman(g2d);

        if (maze.checkMaze()) {
            restartgame();
        }
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

        // for (int i = 0; i < pacsLifeP1; i++) {
        //     g.drawImage(pacmanleft, i * 28 + 8, SCREEN_SIZE + 3, this);
        // }

        // for (int i = 0; i < pacsLifeP2; i++) {
        //     g.drawImage(pacmanleftP2, i * 28 + 8, SCREEN_SIZE + 25, this);
        // }
    }



    private void moveGhost(Graphics2D g2d) {

        for (int i = 0; i < 4; i++) {
            if (ghost[i].x % BLOCK_SIZE == 0 && ghost[i].y % BLOCK_SIZE == 0) {
                int pos = ghost[i].x / BLOCK_SIZE + N_BLOCKS * (int) (ghost[i].y / BLOCK_SIZE);
                int count = 0;
                count = 0;

                if ((maze.data[pos] & 1) == 0 && ghost[i].nextx != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((maze.data[pos] & 2) == 0 && ghost[i].nexty != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((maze.data[pos] & 4) == 0 && ghost[i].nextx != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((maze.data[pos] & 8) == 0 && ghost[i].nexty != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((maze.data[pos] & 15) == 15) {
                        ghost[i].nextx = 0;
                        ghost[i].nexty = 0;
                    } else {
                        ghost[i].nextx = -ghost[i].nextx;
                        ghost[i].nexty = -ghost[i].nexty;
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost[i].nextx = dx[count];
                    ghost[i].nexty = dy[count];
                }

            }

            ghost[i].x = ghost[i].x + (ghost[i].nextx * speed[i]);
            ghost[i].y = ghost[i].y + (ghost[i].nexty * speed[i]);
            ghost[i].drawGhost(g2d);
            if (pacman1.pacman_x > (ghost[i].x - 15) && pacman1.pacman_x < (ghost[i].x + 15)
                    && pacman1.pacman_y > (ghost[i].y - 15) && pacman1.pacman_y < (ghost[i].y + 15)
                    && inGame) {

                dying_state |= 1;
            }

            if (pacman2.pacman_x > (ghost[i].x - 15) && pacman2.pacman_x < (ghost[i].x + 15)
                    && pacman2.pacman_y > (ghost[i].y - 15) && pacman2.pacman_y < (ghost[i].y + 15)
                    && inGame) {

                dying_state |= 2;
            }
        }
    }


  
    private void movePacman() {
        int p1pos, p2pos, p1ch, p2ch;

        if (p1dir.x == -p1pacmand_x && p1dir.y == -p1pacmand_y) {
            p1pacmand_x = p1dir.x;
            p1pacmand_y = p1dir.y;
            pacman1.view_x = p1dir.x;
            pacman1.view_y = p1dir.y;
        }

        if (pacman1.pacman_x % BLOCK_SIZE == 0 && pacman1.pacman_y % BLOCK_SIZE == 0) {
            p1pos = pacman1.pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman1.pacman_y / BLOCK_SIZE);
            p1ch = maze.data[p1pos];

            if ((p1ch & 16) != 0) {
                maze.data[p1pos] -= 16;
                p1score++;
            }

            if (p1dir.x != 0 || p1dir.y != 0) {
                if (!((p1dir.x == -1 && p1dir.y == 0 && (p1ch & 1) != 0)
                        || (p1dir.x == 1 && p1dir.y == 0 && (p1ch & 4) != 0)
                        || (p1dir.x == 0 && p1dir.y == -1 && (p1ch & 2) != 0)
                        || (p1dir.x == 0 && p1dir.y == 1 && (p1ch & 8) != 0))) {
                    p1pacmand_x = p1dir.x;
                    p1pacmand_y = p1dir.y;
                    pacman1.view_x = p1dir.x;
                    pacman1.view_y = p1dir.y;
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
        pacman1.pacman_x = pacman1.pacman_x + 6 * p1pacmand_x;
        pacman1.pacman_y = pacman1.pacman_y + 6 * p1pacmand_y;


        if (p2dir.x == -p2pacmand_x && p2dir.y == -p2pacmand_y) {
            p2pacmand_x = p2dir.x;
            p2pacmand_y = p2dir.y;
            pacman2.view_x = p2dir.x;
            pacman2.view_y = p2dir.y;
        }

        if (pacman2.pacman_x % BLOCK_SIZE == 0 && pacman2.pacman_y % BLOCK_SIZE == 0) {
            p2pos = pacman2.pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman2.pacman_y / BLOCK_SIZE);
            p2ch = maze.data[p2pos];

            if ((p2ch & 16) != 0) {
                maze.data[p2pos] -= 16;
                p2score++;
            }

             

            if (p2dir.x != 0 || p2dir.y != 0) {
                if (!((p2dir.x == -1 && p2dir.y == 0 && (p2ch & 1) != 0)
                        || (p2dir.x == 1 && p2dir.y == 0 && (p2ch & 4) != 0)
                        || (p2dir.x == 0 && p2dir.y == -1 && (p2ch & 2) != 0)
                        || (p2dir.x == 0 && p2dir.y == 1 && (p2ch & 8) != 0))) {
                    p2pacmand_x = p2dir.x;
                    p2pacmand_y = p2dir.y;
                    pacman2.view_x = p2dir.x;
                    pacman2.view_y = p2dir.y;
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
        pacman2.pacman_x = pacman2.pacman_x + 6 * p2pacmand_x;
        pacman2.pacman_y = pacman2.pacman_y + 6 * p2pacmand_y;
    }

    private void drawPacman(Graphics2D g2d) {
        if (pacsLifeP1 > 0) {
            pacman1.drawPacman(g2d);
        }
        if (pacsLifeP2 > 0) {
            pacman2.drawPacman(g2d);
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
        maze.data = maps.Get_data();

        pacman1.pacman_x = 0 * BLOCK_SIZE;
        pacman1.pacman_y = 0 * BLOCK_SIZE;
        pacman2.pacman_x = 25 * BLOCK_SIZE;
        pacman2.pacman_y = 25 * BLOCK_SIZE;
        pacman1.view_x = 0;
        pacman1.view_y = 0;
        pacman2.view_x = 0;
        pacman2.view_y = 0;
 
        p1pacmand_x = 0;
        p1pacmand_y = 0;
        p2pacmand_x = 0;
        p2pacmand_y = 0;
        
        for (int i = 11; i < 15; i++) {
            ghost[i - 11].x = i * BLOCK_SIZE;
            ghost[i - 11].y = 12 * BLOCK_SIZE;
        }
      
    }

    private void loadImages() {
       
        pacman1.loadImages("playerOne");
        pacman2.loadImages("playerTwo");
        ghost[0].loadImages("Orange");
        ghost[1].loadImages("Blue");
        ghost[2].loadImages("Red");
        ghost[3].loadImages("Pink");

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
            maze.drawMaze(g2d);
            drawScore(g2d);
            drawPacman(g2d);
            movePacman();
            moveGhost(g2d);
            if ((dying_state & 1) != 0) {
                pacman1.pacman_x = 0 * BLOCK_SIZE;
                pacman1.pacman_y = 0 * BLOCK_SIZE;
                pacsLifeP1--;
            }
            if ((dying_state & 2) != 0) {
                pacman2.pacman_x = 25 * BLOCK_SIZE;
                pacman2.pacman_y = 25 * BLOCK_SIZE;
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
                } 
                else if (key == KeyEvent.VK_RIGHT) {
                    p1dir.x = 1;
                    p1dir.y = 0;
                } 
                else if (key == KeyEvent.VK_UP) {
                    p1dir.x = 0;
                    p1dir.y = -1;
                } 
                else if (key == KeyEvent.VK_DOWN) {
                    p1dir.x = 0;
                    p1dir.y = 1;
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
                } 
                else if (key == KeyEvent.VK_D) {
                    p2dir.x = 1;
                    p2dir.y = 0;
                } 
                else if (key == KeyEvent.VK_W) {
                    p2dir.x = 0;
                    p2dir.y = -1;
                } 
                else if (key == KeyEvent.VK_S) {
                    p2dir.x = 0;
                    p2dir.y = 1;
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
