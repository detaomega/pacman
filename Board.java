import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);


    private boolean inGame = false, endgame = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 26;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PACMAN_SPEED = 6;

    private int pacsLeft, score;
    private int [] dx = {-1, 1, 0, 0};
    private int [] dy = {0, 0, -1, 1};
    private int pacmand_x, pacmand_y;


    private int req_x, req_y;
    private boolean dying;

    private DrawPacman drawpac;
    private DrawGhost [] ghost;
    private Path path;
    private Maze maze;

    private Ice ice;
  
    private Timer timer;

    // constructor
    public Board() {
        initVariables();
        loadImages();
        initBoard();
    }

    public void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
    }


    public void initVariables() {


        d = new Dimension(400, 400);
  
        
        drawpac = new DrawPacman();
        ghost = new DrawGhost[4];
        for (int i = 0; i < 4; i++) {
            ghost[i] = new DrawGhost();
        }
        ice = new Ice();
        maze = new Maze(N_BLOCKS);
        timer = new Timer(40, this); // 每0.04秒repaint
        timer.start();
    }

    private void playGame(Graphics2D g2d) {
        drawpac.drawPacman(g2d);
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
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        // for (int i = 0; i < pacsLeft; i++) {
        //     g.drawImage(pacmanleft, i * 28 + 8, SCREEN_SIZE + 1, this);
        // }x
    }


    private void moveGhost(Graphics2D g2d) {
        for (int i = 0; i < 1; i++) {
            if (ghost[i].x % BLOCK_SIZE == 0 && ghost[i].y % BLOCK_SIZE == 0) {
                int pos = ghost[i].x / BLOCK_SIZE + N_BLOCKS * (int) (ghost[i].y / BLOCK_SIZE);
                int count = 0;
                count = 0;
                
                /*
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
                */
                int next = path.next((int) ghost[i].x / BLOCK_SIZE, (int) (ghost[i].y / BLOCK_SIZE));

                ghost[i].nextx = dx[next];
                ghost[i].nexty = dy[next];
            }

            ghost[i].x = ghost[i].x + (ghost[i].nextx * ghost[i].speed);
            ghost[i].y = ghost[i].y + (ghost[i].nexty * ghost[i].speed);
            ghost[i].drawGhost(g2d);
            if (drawpac.pacman_x > (ghost[i].x - 15) 
                    && drawpac.pacman_x < (ghost[i].x + 15)
                    && drawpac.pacman_y > (ghost[i].y - 15) 
                    && drawpac.pacman_y < (ghost[i].y + 15)
                    && inGame) {
                if (ghost[i].state == 0) {
                   dying = true; 
                }  
                else {
                    score += 100;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    
                    }
                    ghost[i].reset();
                }
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
            path.update((int) drawpac.pacman_x / BLOCK_SIZE, (int) (drawpac.pacman_y / BLOCK_SIZE));
            ch = maze.data[pos]; 

            if ((ch & 16) != 0) {
                maze.data[pos] -= 16;
                score++;
            }

            if ((ch & 32) != 0) {
                maze.data[pos] -= 32;
                for (int i = 0; i < 4; i++) {
                    ghost[i].state = 1;
                    ghost[i].change(2);
                }
                 try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    
                }
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
        drawpac.pacman_x = drawpac.pacman_x + 3 * pacmand_x;
        drawpac.pacman_y = drawpac.pacman_y + 3 * pacmand_y;
    }

    private void initGame() {
        pacsLeft = 3;
        score = 0;
        restartgame();
    }

    private void restartgame() {

        Map maps = new Map(N_BLOCKS);
        if (dying == false) {
            maze.data = maps.Get_data("map.txt");
        }
        path = new Path(N_BLOCKS);
        path.loadMap("map.txt");
        drawpac.pacman_x = 0 * BLOCK_SIZE;
        drawpac.pacman_y = 0 * BLOCK_SIZE;
        path.update(0, 0);
        drawpac.view_x = 0;
        drawpac.view_y = 1;
        pacmand_x = 0;
        pacmand_y = 0;
        req_x = 0;
        req_y = 0;
        for (int i = 0; i < 1; i++) {
            ghost[i].x = 7 * BLOCK_SIZE;
            ghost[i].y = i * BLOCK_SIZE;
            ghost[i].ori_x = 7 * BLOCK_SIZE;
            ghost[i].ori_y = i * BLOCK_SIZE;
            ghost[i].speed = 3;
        }
        
        dying = false;
    }

    private void loadImages() {
        drawpac.loadImages("playerOne");
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
            ice.drawIce(g2d);
            drawpac.drawPacman(g2d);
            movePacman();
            moveGhost(g2d);
            death();

        } 
        else if (endgame == true) {
            GameOver game = new GameOver();
            game.showImage(g2d);
        }
        else {
            showIntroScreen(g2d);
        }

    }

    private void death() {
        if (dying == false) return;
        pacsLeft--;

        if (pacsLeft == 0) {
            endgame = true;
            inGame = false;
        }
        restartgame();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
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