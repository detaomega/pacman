import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Board2 extends JPanel implements ActionListener {
    public int mode = 1;
    private String finalResult = "";
    private Dimension d;

    private boolean inGame = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 26;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

    private int dying_state;
    private int [] dx = {-1, 1, 0, 0};
    private int [] dy = {0, 0, -1, 1};
    //private Point playerpos; 
 

    private int  p1pacmand_x, p1pacmand_y,p2pacmand_x, p2pacmand_y;
    private Point p1dir, p2dir; 
    private int state, dying_count, ghostNumber, eatPointP1, eatPointP2, initGhostNumber;
    private Player player1, player2;
    private Ghost [] ghost;  
    private Maze maze;
    private Path path1, path2;
    private Item item;
    private Score p1score, p2score;
    // pacman map


    private Timer timer;

    // constructor
    public Board2(int n, int m) {
        initGhostNumber = n;
        mode = m;
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

        maze = new Maze(N_BLOCKS);
        d = new Dimension(400, 400);

        ghostNumber = initGhostNumber;
        ghost = new Ghost[20];
        for (int i = 0; i < 4; i++) {
            ghost[i] = new Ghost();
        }
        p1dir = new Point(0, 0);
        p2dir = new Point(0, 0);
        player1 = new Player("playerOne");
        player2 = new Player("playerTwo");
        p1score = new Score("playerOne");
        p2score = new Score("playerTwo");
        dying_state = 0;
        timer = new Timer(40, this); 
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
        for (int i = 0; i < ghostNumber; i++) {
            if (ghost[i].x % BLOCK_SIZE == 0 && ghost[i].y % BLOCK_SIZE == 0) {
                int pos = ghost[i].x / BLOCK_SIZE + N_BLOCKS * (int) (ghost[i].y / BLOCK_SIZE);
                int count = 0;
                count = 0;
                if (ghost[i].state == 3) {
                    int next = 0;
                    Path recover = new Path(N_BLOCKS);
                    recover.loadMap("map.txt");
                    recover.update((int) ghost[i].ori_x / BLOCK_SIZE, (int) (ghost[i].ori_y / BLOCK_SIZE), 0, 0);
                    next = recover.next((int) ghost[i].x / BLOCK_SIZE, (int) (ghost[i].y / BLOCK_SIZE), ghost[i].state);
                    ghost[i].nextx = dx[next]; 
                    ghost[i].nexty = dy[next];
                }
                else if (i >= 4 && ghost[i].freeze == 0) {
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
                else if (ghost[i].chasePlayer == 1) {
                    int next = path1.next((int) ghost[i].x / BLOCK_SIZE, (int) (ghost[i].y / BLOCK_SIZE), ghost[i].state);
                    ghost[i].nextx = dx[next]; 
                    ghost[i].nexty = dy[next];
                }
                else {
                    int next = path2.next((int) ghost[i].x / BLOCK_SIZE, (int) (ghost[i].y / BLOCK_SIZE), ghost[i].state);
                    ghost[i].nextx = dx[next]; 
                    ghost[i].nexty = dy[next];
                }
            }
            
            if (ghost[i].freeze == 0 || ghost[i].state == 3) {
                ghost[i].x = ghost[i].x + (ghost[i].nextx * ghost[i].speed);
                ghost[i].y = ghost[i].y + (ghost[i].nexty * ghost[i].speed);
            }
            ghost[i].drawGhost(g2d);
            if (player1.pacman_x > (ghost[i].x - 15) 
                    && player1.pacman_x < (ghost[i].x + 15)
                    && player1.pacman_y > (ghost[i].y - 15) 
                    && player1.pacman_y < (ghost[i].y + 15)
                    && inGame && ghost[i].state != 3
                    && p1score.life > 0) {
                if (ghost[i].state == 0) {
                    dying_state |= 1;
                    state = 1;
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                    
                    }
                }  
                else {
                    p1score.score += 100;
                    try {
                        Thread.sleep(400);
                    } 
                    catch (InterruptedException e) {
                    
                    }
                }
                ghost[i].state = 3;
                ghost[i].change(8);
            }
            if (player2.pacman_x > (ghost[i].x - 15) 
                    && player2.pacman_x < (ghost[i].x + 15)
                    && player2.pacman_y > (ghost[i].y - 15) 
                    && player2.pacman_y < (ghost[i].y + 15)
                    && inGame && ghost[i].state != 3 
                    && p2score.life > 0) {
                if (ghost[i].state == 0) {
                   dying_state |= 2;
                   state = 2;
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                    
                    }
                }  
                else {
                    p2score.score += 100;
                    try {
                        Thread.sleep(400);
                    } 
                    catch (InterruptedException e) {
                    
                    }
                }
                ghost[i].state = 3;
                ghost[i].change(8);
            }
        }
    }
    

    private void movePacman1() {
        int pos, ch;

        if (p1dir.x == 0 && p1dir.y == 0) return;

         if (p1dir.x == -p1pacmand_x && p1dir.y == -p1pacmand_y) {
            p1pacmand_x = p1dir.x;
            p1pacmand_y = p1dir.y;
            player1.view_x = p1dir.x;
            player1.view_y = p1dir.y;
        }

 
        if (player1.pacman_x % BLOCK_SIZE == 0 && player1.pacman_y % BLOCK_SIZE == 0) {
            pos = player1.pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (player1.pacman_y / BLOCK_SIZE);
            ch = maze.data[pos]; 

            if ((ch & 16) != 0) {
                maze.data[pos] -= 16;
                if (maze.player == 1) {
                    p1score.score += 20;
                }
                else {
                    p1score.score += 10;
                }
                eatPointP1++;
                if (eatPointP1 % 40 == 0 && eatPointP1 != 0) {
                    ghost[ghostNumber] = new Ghost();
                    ghost[ghostNumber].addGhost(12 * BLOCK_SIZE, 12 * BLOCK_SIZE, "Red");
                    ghostNumber++;
                    System.out.print(ghostNumber);
                }
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

            if (p1dir.x != 0 || p1dir.y != 0) {
                if (!((p1dir.x == -1 && p1dir.y == 0 && (ch & 1) != 0)
                        || (p1dir.x == 1 && p1dir.y == 0 && (ch & 4) != 0)
                        || (p1dir.x == 0 && p1dir.y == -1 && (ch & 2) != 0)
                        || (p1dir.x == 0 && p1dir.y == 1 && (ch & (8 + 64)) != 0))) {
                    p1pacmand_x = p1dir.x;
                    p1pacmand_y = p1dir.y;
                    player1.view_x = p1dir.x;
                    player1.view_y = p1dir.y;
                }
            }
            // Check for standstill
            if (((p1pacmand_x == -1 && p1pacmand_y == 0) && (ch & 1) != 0)
                    || ((p1pacmand_x == 1 && p1pacmand_y == 0) && (ch & 4) != 0)
                    || ((p1pacmand_x == 0 && p1pacmand_y == -1)  && (ch & 2) != 0)
                    || ((p1pacmand_x == 0 && p1pacmand_y == 1) && (ch & (8 + 64)) != 0)) {
                p1pacmand_x = 0;
                p1pacmand_y = 0;
            }

        }
        player1.pacman_x = player1.pacman_x + player1.speed * p1pacmand_x;
        player1.pacman_y = player1.pacman_y + player1.speed * p1pacmand_y;
        int blockX, blockY;
        if (p1pacmand_x == 1 && ((player1.pacman_x - 9) % BLOCK_SIZE != 0)) {
            blockX = (int) ((player1.pacman_x - 9) / BLOCK_SIZE) + 1;
            blockY = (int) (player1.pacman_y / BLOCK_SIZE);
        }  
        else if (p1pacmand_y == 1 && ((player1.pacman_y - 9) % BLOCK_SIZE != 0)) {
            blockX = (int) (player1.pacman_x / BLOCK_SIZE);
            blockY = (int) ((player1.pacman_y - 9) / BLOCK_SIZE) + 1;
        }
        else {
            blockX = (int) (player1.pacman_x / BLOCK_SIZE);
            blockY = (int) (player1.pacman_y / BLOCK_SIZE);
        } 
        path1.update(blockX, blockY, p1pacmand_x, p1pacmand_y);
        int eatItem = item.getItem(blockX, blockY);
        if (eatItem == 1) {
            try {
                Thread.sleep(200);
                } catch (InterruptedException e) {
                    
                }
            for (int i = 0; i < 4; i++) {
                ghost[i].freeze = 1;
            }
        }
        else if (eatItem == 2) {
            player1.speedUp(6, 120);
        }
        else if (eatItem == 3) {
            p1score.life = Math.min(5, p1score.life + 1);
        }
        else if (eatItem == 4) {
            maze.doubleScore(1);
        }
    }

    private void movePacman2() {
        int pos, ch;

        if (p2dir.x == 0 && p2dir.y == 0) return;

         if (p2dir.x == -p2pacmand_x && p2dir.y == -p2pacmand_y) {
            p2pacmand_x = p2dir.x;
            p2pacmand_y = p2dir.y;
            player2.view_x = p2dir.x;
            player2.view_y = p2dir.y;
        }

 
        if (player2.pacman_x % BLOCK_SIZE == 0 && player2.pacman_y % BLOCK_SIZE == 0) {
            pos = player2.pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (player2.pacman_y / BLOCK_SIZE);
            ch = maze.data[pos]; 

            if ((ch & 16) != 0) {
                maze.data[pos] -= 16;
                if (maze.player == 2) {
                    p2score.score += 20;
                }
                else {
                    p2score.score += 10;
                }
                if (eatPointP2 % 40 == 0 && eatPointP2 != 0) {
                    ghost[ghostNumber] = new Ghost();
                    ghost[ghostNumber].addGhost(13 * BLOCK_SIZE, 12 * BLOCK_SIZE, "Pink");
                    ghostNumber++;
                }
            }

            if ((ch & 32) != 0) {
                maze.data[pos] -= 32;
                for (int i = 0; i < 4; i++) {
                    ghost[i].weak();
                }
                player2.change(4);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    
                }
            }

            if (p2dir.x != 0 || p2dir.y != 0) {
                if (!((p2dir.x == -1 && p2dir.y == 0 && (ch & 1) != 0)
                        || (p2dir.x == 1 && p2dir.y == 0 && (ch & 4) != 0)
                        || (p2dir.x == 0 && p2dir.y == -1 && (ch & 2) != 0)
                        || (p2dir.x == 0 && p2dir.y == 1 && (ch & (8 + 64)) != 0))) {
                    p2pacmand_x = p2dir.x;
                    p2pacmand_y = p2dir.y;
                    player2.view_x = p2dir.x;
                    player2.view_y = p2dir.y;
                }
            }
            // Check for standstill
            if (((p2pacmand_x == -1 && p2pacmand_y == 0) && (ch & 1) != 0)
                    || ((p2pacmand_x == 1 && p2pacmand_y == 0) && (ch & 4) != 0)
                    || ((p2pacmand_x == 0 && p2pacmand_y == -1)  && (ch & 2) != 0)
                    || ((p2pacmand_x == 0 && p2pacmand_y == 1) && (ch & (8 + 64)) != 0)) {
                p2pacmand_x = 0;
                p2pacmand_y = 0;
            }

        }
        player2.pacman_x = player2.pacman_x + player2.speed * p2pacmand_x;
        player2.pacman_y = player2.pacman_y + player2.speed * p2pacmand_y;
        int blockX, blockY;
        if (p2pacmand_x == 1 && ((player2.pacman_x - 9) % BLOCK_SIZE != 0)) {
            blockX = (int) ((player2.pacman_x - 9) / BLOCK_SIZE) + 1;
            blockY = (int) (player2.pacman_y / BLOCK_SIZE);
        }  
        else if (p2pacmand_y == 1 && ((player2.pacman_y - 9) % BLOCK_SIZE != 0)) {
            blockX = (int) (player2.pacman_x / BLOCK_SIZE);
            blockY = (int) ((player2.pacman_y - 9) / BLOCK_SIZE) + 1;
        }
        else {
            blockX = (int) (player2.pacman_x / BLOCK_SIZE);
            blockY = (int) (player2.pacman_y / BLOCK_SIZE);
        } 
        path2.update(blockX, blockY, p2pacmand_x, p2pacmand_y);
        int eatItem = item.getItem(blockX, blockY);
        if (eatItem == 1) {
            try {
                Thread.sleep(200);
                } catch (InterruptedException e) {
                    
                }
            for (int i = 0; i < 4; i++) {
                ghost[i].freeze = 1;
            }
        }
        else if (eatItem == 2) {
            player2.speedUp(6, 120);
        }
        else if (eatItem == 3) {
            p2score.life = Math.min(5, p2score.life + 1);
        }
        else if (eatItem == 4) {
            maze.doubleScore(2);
        }
    }


    private void drawPacman(Graphics2D g2d) {
        if (p1score.life > 0) {
            movePacman1();
            player1.drawPacman(g2d);
        }
        if (p2score.life > 0) {
            movePacman2();
            player2.drawPacman(g2d);
        }
    }
    
    
    private void initGame() {
        p1score.life = 2;
        p2score.life = 2;
        p1score.score = 0;
        p2score.score = 0;
        restartgame();
    }

    private void restartgame() {
        Map maps = new Map(N_BLOCKS);
        maze.data = maps.Get_data("map.txt");
        path1 = new Path(N_BLOCKS);
        path2 = new Path(N_BLOCKS);
        path1.loadMap("map.txt");
        path2.loadMap("map.txt");
        item = new Item(path1.map, mode);
        path1.update(0, 0, 0, 0);
        path2.update(25, 25, 0, 0);
        ghostNumber = initGhostNumber;
        player1.pacman_x = 0 * BLOCK_SIZE;
        player1.pacman_y = 0 * BLOCK_SIZE;
        player2.pacman_x = 25 * BLOCK_SIZE;
        player2.pacman_y = 25 * BLOCK_SIZE;
        player1.speed = 3;
        player2.speed = 3;
        player1.view_x = 0;
        player1.view_y = 0;
        player2.view_x = 0;
        player2.view_y = 0;
 
        p1pacmand_x = 0;
        p1pacmand_y = 0;
        p2pacmand_x = 0;
        p2pacmand_y = 0;
        
        eatPointP1 = 0;
        eatPointP2 = 0;
        for (int i = 11; i < 15; i++) {
            ghost[i - 11].x = i * BLOCK_SIZE;
            ghost[i - 11].y = 12 * BLOCK_SIZE;
            ghost[i - 11].ori_x = i * BLOCK_SIZE;
            ghost[i - 11].ori_y = 12 * BLOCK_SIZE;
            ghost[i - 11].state = 0;
            ghost[i - 11].speed = 3;
            if (i % 2 == 0) {
                ghost[i - 11].chasePlayer = 1;
            }
            else {
                ghost[i - 11].chasePlayer = 0;

            }
        }
      
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

    private void drawDeadAnimation(Graphics2D g) {
        if (state == 1) {
            g.setColor(new Color(255, 254, 56));
            try {
                Thread.sleep(100);
                if  (300 - 30 * dying_count >= 0)
                g.fillArc(player1.pacman_x, player1.pacman_y, 22, 22, 120 + dying_count * 15, 300 - 30 * dying_count);
            } catch (InterruptedException e) {
                    
            }
            if (dying_count == 12) {
                dying_count = 0;
                state = 0;
            }
            
        }
        else {
            g.setColor(new Color(0, 255, 0));
            try {
                Thread.sleep(100);
                if  (300 - 30 * dying_count >= 0)
                g.fillArc(player2.pacman_x, player2.pacman_y, 22, 22, 120 + dying_count * 15, 300 - 30 * dying_count);
            } catch (InterruptedException e) {
                    
            }
        }
        dying_count++;
        if (dying_count == 12) {
            dying_count = 0;
            state = 0;
            death();

        }
    }
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        if (p1score.life <= 0 && p2score.life <= 0) {
            GameOver game = new GameOver(finalResult);
            game.showImage(g2d);
        }
        else if (state != 0) {
            maze.drawMaze(g2d);
            p1score.drawScore(g2d, SCREEN_SIZE);
            p2score.drawScore(g2d, SCREEN_SIZE + 22);
            drawDeadAnimation(g2d);
        }
        else if (inGame) {
            playGame(g2d);
            maze.drawMaze(g2d);
            p1score.drawScore(g2d, SCREEN_SIZE);
            p2score.drawScore(g2d, SCREEN_SIZE + 22);
            item.drawItem(g2d);
            drawPacman(g2d);
            moveGhost(g2d);
            
        } 
        else {
            showIntroScreen(g2d);
        }
    }

    private void death() {
        if ((dying_state & 1) != 0) {
            p1score.life--;
        }
        if ((dying_state & 2) != 0) {
            p2score.life--;
        }
        ghostNumber = initGhostNumber;
        for (int i = 0; i < ghostNumber; i++) {
            ghost[i].x = ghost[i].ori_x;
            ghost[i].y = ghost[i].ori_y;
        }
        dying_state = 0;
        player1.pacman_x = 0 * BLOCK_SIZE;
        player1.pacman_y = 0 * BLOCK_SIZE;
        player2.pacman_x = 25 * BLOCK_SIZE;
        player2.pacman_y = 25 * BLOCK_SIZE;
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
