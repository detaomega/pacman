import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Point;
import java.util.Scanner;
import java.io.*;


public class Path {
    private int pac_x, pac_y, N_BLOCKS;
    private int [][] map;
    private int [][] distance;
    private int [] dx = {-1, 1, 0, 0};
    private int [] dy = {0, 0, -1, 1};

    void bfs() {
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(new Point(pac_x, pac_y));
        distance[pac_y][pac_x] = 0;
        while (queue.size() > 0) {
           
            Point now = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i], ny = now.y + dy[i];
                if (nx < 0 || nx >= N_BLOCKS || ny < 0 || ny >= N_BLOCKS) continue;
                if (distance[ny][nx] == -1 && map[ny][nx] != 1) {
                    distance[ny][nx] = distance[now.y][now.x] + 1;
                    queue.add(new Point(nx, ny));
                }
            }
        } 
    }

    public Path(int N_BLOCKS) {
        this.N_BLOCKS = N_BLOCKS;
        distance = new int[N_BLOCKS][N_BLOCKS];
        map = new int[N_BLOCKS][N_BLOCKS];
    }
    
    private String readfile(String s) {
        String data = "";
        try {
            File file = new File("map/" + s);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                data += sc.nextLine();
            }           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void loadMap(String mapName) {
        int now = 0;
        String s = readfile(mapName);
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                map[i][j] =  (int)(s.charAt(now++)- '0');
            }
        }
    }

    private int bestAction(int x, int y) {
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                distance[i][j] = -1;
            }
        }
        bfs();
        int best_Action = N_BLOCKS * N_BLOCKS;
        int []choose = new int[4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];
        
            if (nx < 0 || nx >= N_BLOCKS || ny < 0 || ny >= N_BLOCKS || distance[ny][nx] == -1) continue;
            if (distance[ny][nx] < best_Action) {
                best_Action = distance[ny][nx];
                count = 0;
                choose[count++] = i;
            }
            else if (distance[ny][nx] == best_Action) {
                choose[count++] = i;
            }
        }
        return choose[(int)Math.random() * count];
    }

    public int worstAction(int x, int y) {
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                distance[i][j] = -1;
            }
        }
        bfs();
        int best_Action = -1;
        int []choose = new int[4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if (nx < 0 || nx >= N_BLOCKS || ny < 0 || ny >= N_BLOCKS || distance[ny][nx] == -1) continue;
            if (distance[ny][nx] > best_Action) {
                best_Action = distance[ny][nx];
                count = 0;
                choose[count++] = i;
            }
            else if (distance[ny][nx] == best_Action) {
                choose[count++] = i;
            }
        }
        return choose[(int)Math.random() * count];
    }

    public int next(int x, int y, int state) {
        if (state == 0) return bestAction(x, y);
        else return worstAction(x, y);
    }

    public void update(int x, int y) {
        pac_x = x;
        pac_y = y;
    }
}