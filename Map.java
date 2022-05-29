import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Map {
    
    private int N_BLOCKS;
    private int DATA_SIZE;
    
    int [][] visit;
    int [][] map;
    
    int [] data;
    int [] dx = {-1, 1, 0, 0};
    int [] dy = {0, 0, -1, 1};
    
    public Map(int N_BLOCKS) {
        this.N_BLOCKS = N_BLOCKS;
        DATA_SIZE = N_BLOCKS * N_BLOCKS;
        visit = new int [N_BLOCKS][N_BLOCKS];
        map = new int [N_BLOCKS][N_BLOCKS]; 
        data = new int [DATA_SIZE];
    }

    private void init() {
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                map[i][j] = 0;
            }
        }
        for (int i = 0; i < DATA_SIZE; i++) {
            data[i] = 0;
        }
    }

    private void dfs (int x, int y) {
        visit[x][y] = 1;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if ((nx >= 0 && nx < N_BLOCKS && ny >= 0 && ny < N_BLOCKS)) {
                if (map[nx][ny] == 0 && visit[nx][ny] == 0) {
                    dfs (nx, ny);
                }
            }
        }
    }

    private int check_compoment(int row, int col) {
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                visit[i][j] = 0;
            }
        }
        int count_compoment = 0;
        map[row][col] = 1;
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                if (visit[i][j] == 0 && map[i][j] == 0) {
                    dfs (i, j);
                    count_compoment = count_compoment + 1;
                }
            }
        }
        if (count_compoment == 1) {
            return 1;
        }
        else {
            map[row][col] = 0;
            return 0;
        }
    }

    public void generate() {
        init();
        Random r1 = new Random();
        int row, col;
        for (int i = 0; i < 40; i++) {
            while (true) {
                row = r1.nextInt(15);
                col = r1.nextInt(15);
                if (map[row][col] == 1 || check_compoment(row, col) != 1) {
                    continue;
                }
                else {
                    break;
                }
            }
        }
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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    public int[] Get_data(String mapName) {
        int temp = 0;
        int now = 0;
        String s = readfile(mapName);
        System.out.print(s);
      
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                map[i][j] =  (int)(s.charAt(now++)- '0');
            }
        }
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                if (i == 0 || (map[i - 1][j] + map[i][j]) == 1) {
                    data[temp] += 2;
                }
                if (j == 0 || (map[i][j - 1] + map[i][j]) == 1) {
                    data[temp] += 1;
                }
                if (i == N_BLOCKS - 1 || (map[i + 1][j] + map[i][j]) == 1) {
                    data[temp] += 8;
                }
                if (j == N_BLOCKS - 1 || (map[i][j + 1] + map[i][j]) == 1) {
                    data[temp] += 4;
                }
                if (map[i][j] == 0) {
                    data[temp] += 16;
                }
                temp = temp + 1;
           
            }

        }
        return data;
    }


}
