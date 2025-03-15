import java.util.*;
import java.io.*;

public class Main {
    static int[][] map;
    static int maxValue = 0, N;
    
    static int[][] copyOf(int[][] arr) {
        int[][] res = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++)
        for (int j = 0; j < arr.length; j++)
            res[i][j] = arr[i][j];
        return res;
    }
    
    static int[][] rotate = {
        {0, -1, 1, 0}, //시계 90 회전
        {-1, 0, 0, -1}, //시계 180 회전
        {0, 1, -1, 0}, //시계 270 회전
        {1, 0, 0, 1}
    };
    static int[][] transition = {
        {0, 1}, //오른쪽으로 평행이동
        {1, 1}, //위 오른쪽으로 평행이동
        {1, 0}, //위로 평행이동
        {0, 0}
    };
    
    static int get(int dir, int y, int x) {
        int ny = rotate[dir][2] * x + rotate[dir][3] * y;
        ny += transition[dir][0] * (N-1);
        int nx = rotate[dir][0] * x + rotate[dir][1] * y;
        nx += transition[dir][1] * (N-1);
        return map[ny][nx];
    }
    static void set(int dir, int y, int x, int val) {
        int ny = rotate[dir][2] * x + rotate[dir][3] * y;
        ny += transition[dir][0] * (N-1);
        int nx = rotate[dir][0] * x + rotate[dir][1] * y;
        nx += transition[dir][1] * (N-1);
        map[ny][nx] = val;
    }
    
    static void move(int dir) {
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int y = i, x = j;
                while (y-1 >= 0 && get(dir, y-1, x) == 0) {
                    set(dir, y-1, x, get(dir, y, x));
                    set(dir, y, x, 0);
                    y--;
                }
                if (y-1 >= 0 && get(dir, y-1, x) == get(dir, y, x)) {
                    int val = get(dir, y-1, x) * -2;
                    maxValue = Math.max(maxValue, -val);
                    set(dir, y-1, x, val);
                    set(dir, y, x, 0);
                }
            }
        }
        
        for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            map[i][j] = Math.abs(map[i][j]);
    }
    
    static void dfs(int depth) {
        if (depth == 5)
            return;
        
        int[][] cur = copyOf(map);
        for (int i = 0; i < 4; i++) {
            move(i);
            dfs(depth + 1);
            map = copyOf(cur);
        }
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                maxValue = Math.max(maxValue, map[i][j]);
            }
        }
        dfs(0);
        System.out.println(maxValue);
    }
}