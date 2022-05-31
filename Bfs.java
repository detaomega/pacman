import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Point;
import java.util.Scanner;
import java.io.*;

public class Bfs {

    public int N_BLOCKS;
    private int [][]map;
    public int [][]distance;
    private int [] dx = {-1, 1, 0, 0};
    private int [] dy = {0, 0, -1, 1};

    public Bfs(int N_BLOCKS, int map[][]) {
        this.N_BLOCKS = N_BLOCKS;
        this.map = map;
        distance = new int[N_BLOCKS][N_BLOCKS];
    }

    private void init() {
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                distance[i][j] = -1;
            }
        }
    }

    public void bfs(int pac_x, int pac_y) {
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(new Point(pac_x, pac_y));
        init();
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
}