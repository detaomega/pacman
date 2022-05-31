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

    private int [] dx = {-1, 1, 0, 0};
    private int [] dy = {0, 0, -1, 1};
    private int pacmand_x, pacmand_y;


    private int req_x, req_y;
    private boolean dying;

    private Player player1;
    private Ghost [] ghost;
    private Score P1score;
    private Path path;
    private Maze maze;

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
  
        
        player1 = new Player("playerOne");
        P1score = new Score("playerOne");
        ghost = new Ghost[4];
        for (int i = 0; i < 4; i++) {
            ghost[i] = new Ghost();
        }
        maze = new Maze(N_BLOCKS);
        timer = new Timer(40, this); // 每0.04秒repaint
        timer.start();
    }

    private void playGame(Graphics2D g2d) {
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



    private void moveGhost(Graphics2D g2d) {
        for (int i = 0; i < 4; i++) {
            if (ghost[i].x % BLOCK_SIZE == 0 && ghost[i].y % BLOCK_SIZE == 0) {
                int pos = ghost[i].x / BLOCK_SIZE + N_BLOCKS * (int) (ghost[i].y / BLOCK_SIZE);
                int count = 0;
                count = 0;
                if (ghost[i].state == 3) {
                    int next = 0;
                    Path recover = new Path(N_BLOCKS);
                    recover.loadMap("map.txt");
                    recover.update((int) ghost[i].ori_x / BLOCK_SIZE, (int) (ghost[i].ori_y / BLOCK_SIZE));
                    next = recover.next((int) ghost[i].x / BLOCK_SIZE, (int) (ghost[i].y / BLOCK_SIZE), ghost[i].state);
                    ghost[i].nextx = dx[next]; 
                    ghost[i].nexty = dy[next];
                }
                else if (i >= 1) {
                    int []randomx = new int[4];
                    int []randomy = new int[4];
                    if ((maze.data[pos] & 1) == 0 && ghost[i].nextx != 1) {
                        randomx[count] = -1;
                        randomy[count] = 0;
                        count++;
                    }

                    if ((maze.data[pos] & 2) == 0 && ghost[i].nexty != 1) {
                        randomx[count] = 0;
                        randomy[count] = -1;
                        count++;
                    }

                    if ((maze.data[pos] & 4) == 0 && ghost[i].nextx != -1) {
                        randomx[count] = 1;
                        randomy[count] = 0;
                        count++;
                    }

                    if ((maze.data[pos] & 8) == 0 && ghost[i].nexty != -1) {
                        randomx[count] = 0;
                        randomy[count] = 1;
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

                        ghost[i].nextx = randomx[count];
                        ghost[i].nexty = randomy[count];
                    }
                }
                else {
                    int next = path.next((int) ghost[i].x / BLOCK_SIZE, (int) (ghost[i].y / BLOCK_SIZE), ghost[i].state);
                    ghost[i].nextx = dx[next]; 
                    ghost[i].nexty = dy[next];
                }
            }

            ghost[i].x = ghost[i].x + (ghost[i].nextx * ghost[i].speed);
            ghost[i].y = ghost[i].y + (ghost[i].nexty * ghost[i].speed);
            ghost[i].drawGhost(g2d);
            if (player1.pacman_x > (ghost[i].x - 15) 
                    && player1.pacman_x < (ghost[i].x + 15)
                    && player1.pacman_y > (ghost[i].y - 15) 
                    && player1.pacman_y < (ghost[i].y + 15)
                    && inGame && ghost[i].state != 3) {
                if (ghost[i].state == 0) {
                   dying = true; 
                   try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                    
                    }
                }  
                else {
                    P1score.score += 100;
                    g2d.drawString("100000", ghost[i].x,  ghost[i].y);
                    try {
                        g2d.drawString("100", ghost[i].x,  ghost[i].y);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    
                    }
                    ghost[i].state = 3;
                    ghost[i].change(8);
                }
            }
        }
    }


    private void movePacman(Graphics2D g2d) {
        int pos, ch;

        if (req_x == 0 && req_y == 0) return;

        if (req_x == -pacmand_x && req_y == -pacmand_y) {
            pacmand_x = req_x;
            pacmand_y = req_y;
            player1.view_x = pacmand_x;
            player1.view_y = pacmand_y;
        }
 
        if (player1.pacman_x % BLOCK_SIZE == 0 && player1.pacman_y % BLOCK_SIZE == 0) {
            pos = player1.pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (player1.pacman_y / BLOCK_SIZE);
            ch = maze.data[pos]; 

            if ((ch & 16) != 0) {
                maze.data[pos] -= 16;
                P1score.score++;
            }

            if ((ch & 32) != 0) {
                maze.data[pos] -= 32;
                for (int i = 0; i < 4; i++) {
                    ghost[i].weak();
                }
                player1.change(4);
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
                    player1.view_x = req_x;
                    player1.view_y = req_y;
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
        player1.pacman_x = player1.pacman_x + player1.speed * pacmand_x;
        player1.pacman_y = player1.pacman_y + player1.speed * pacmand_y;
        if (pacmand_x == 1 && (player1.pacman_x % BLOCK_SIZE != 0))
            path.update((int) player1.pacman_x / BLOCK_SIZE + 1, (int) (player1.pacman_y / BLOCK_SIZE));
        else if (pacmand_y == 1 && (player1.pacman_y / BLOCK_SIZE != 0))
            path.update((int) player1.pacman_x / BLOCK_SIZE, (int) (player1.pacman_y / BLOCK_SIZE) + 1);
        else 
            path.update((int) player1.pacman_x / BLOCK_SIZE, (int) (player1.pacman_y / BLOCK_SIZE));
    }

    private void initGame() {
        restartgame();
    }

    private void restartgame() {

        Map maps = new Map(N_BLOCKS);
        if (dying == false) {
            maze.data = maps.Get_data("map.txt");
        }
        path = new Path(N_BLOCKS);
        path.loadMap("map.txt");
        player1.pacman_x = 0 * BLOCK_SIZE;
        player1.pacman_y = 0 * BLOCK_SIZE;
        player1.speed = 3;
        path.update(0, 0);
        player1.view_x = 0;
        player1.view_y = 1;
        pacmand_x = 0;
        pacmand_y = 0;
        req_x = 0;
        req_y = 0;
        for (int i = 11; i < 15; i++) {
            ghost[i - 11].x = i * BLOCK_SIZE;
            ghost[i - 11].y = 12 * BLOCK_SIZE;
            ghost[i - 11].ori_x = i * BLOCK_SIZE;
            ghost[i - 11].ori_y = 12 * BLOCK_SIZE;
            ghost[i - 11].state = 0;
            ghost[i - 11].speed = 3;
        }
        
        dying = false;
    }

    private void loadImages() {
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
            P1score.drawScore(g2d, SCREEN_SIZE);
            movePacman(g2d);
            player1.drawPacman(g2d);
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
        P1score.life--;

        if (P1score.life == 0) {
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