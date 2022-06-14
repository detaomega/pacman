import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Point;
import java.util.Scanner;
import java.io.*;


public class Path {
    private int pac_x, pac_y, N_BLOCKS, pacPre_x, pacPre_y, view_x, view_y;
    public int [][] map;
    private int [] dx = {-1, 1, 0, 0};
    private int [] dy = {0, 0, -1, 1};
    private Bfs bfs;

    public Path(int N_BLOCKS) {
        this.N_BLOCKS = N_BLOCKS;
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
        bfs = new Bfs(N_BLOCKS, map);
    }

    private int bestAction(int x, int y) {
        bfs.bfs(pac_x, pac_y);
        int best_Action = N_BLOCKS * N_BLOCKS;
        int []choose = new int[4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];

            if (nx < 0 || nx >= N_BLOCKS || ny < 0 || ny >= N_BLOCKS || bfs.distance[ny][nx] == -1) continue;
            if (bfs.distance[ny][nx] < best_Action) {
                best_Action = bfs.distance[ny][nx];
                count = 0;
                choose[count++] = i;
            }
            else if (bfs.distance[ny][nx] == best_Action) {
                choose[count++] = i;
            }
        }
        return choose[(int)Math.random() * count];
    }

    public int worstAction(int x, int y) {
        bfs.bfs(pac_x, pac_y);
        int best_Action = -1;
        int []choose = new int[4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if (nx < 0 || nx >= N_BLOCKS || ny < 0 || ny >= N_BLOCKS || bfs.distance[ny][nx] == -1) continue;
            if (bfs.distance[ny][nx] > best_Action) {
                best_Action = bfs.distance[ny][nx];
                count = 0;
                choose[count++] = i;
            }
            else if (bfs.distance[ny][nx] == best_Action) {
                choose[count++] = i;
            }
        }
        return choose[(int)Math.random() * count];
    }

    public int next(int x, int y, int state) {
        if (state == 0 || state == 3) return bestAction(x, y);
        else return worstAction(x, y);
    }

    public int predictAction(int x, int y) {
        bfs.bfs(pacPre_x, pacPre_y);
        int best_Action = N_BLOCKS * N_BLOCKS;
        int []choose = new int[4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];

            if (nx < 0 || nx >= N_BLOCKS || ny < 0 || ny >= N_BLOCKS || bfs.distance[ny][nx] == -1) continue;
            if (bfs.distance[ny][nx] < best_Action) {
                best_Action = bfs.distance[ny][nx];
                count = 0;
                choose[count++] = i;
            }
            else if (bfs.distance[ny][nx] == best_Action) {
                choose[count++] = i;
            }
        }
        return choose[(int)Math.random() * count];
    } 

    private void solve() {
        for (int i = 3; i >= 0; i--) {
            int nx = pac_x + view_x * i;
            int ny = pac_y + view_y * i;
            if (nx >= 0 && nx < N_BLOCKS && ny >= 0 && ny < N_BLOCKS && map[ny][nx] != 1) {
                pacPre_x = nx;
                pacPre_y = ny;
                break;
            }
        }
    }
  

    public void update(int x, int y, int viewX, int viewY) {
        pac_x = x;
        pac_y = y;
        view_x = viewX;
        view_y = viewY;
        solve();
    }
}