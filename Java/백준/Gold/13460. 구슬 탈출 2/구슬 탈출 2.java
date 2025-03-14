import java.util.*;
import java.io.*;

public class Main {
    static char[][] map;
    static int[] pos;
    static int[][][][] visit;
    static int[][] delta = {
        {0, 1},
        {0, -1},
        {1, 0},
        {-1, 0}
    };
    static int dfs(int depth, int ry, int rx, int by, int bx) {
        if (visit[ry][rx][by][bx] != 0 && visit[ry][rx][by][bx] <= depth || depth > 10)
            return Integer.MAX_VALUE;
        visit[ry][rx][by][bx] = depth;
        int ans = Integer.MAX_VALUE;
        map[ry][rx] = map[by][bx] = '.';
        for (int i = 0; i < 4; i++) {
            int dy = delta[i][0], dx = delta[i][1];
            boolean redFirst = dy == 0 ? ((dx < 0) ^ (rx > bx)) : ((dy < 0) ^ (ry > by));
            int nry = ry, nrx = rx, nby = by, nbx = bx;
            
            boolean redInHole = false;
            
            if (redFirst) {
                while (map[nry+dy][nrx+dx] == '.') {
                    nry += dy; nrx += dx;
                }
                if (map[nry+dy][nrx+dx] == 'O')
                    redInHole = true;
                else
                    map[nry][nrx] = 'R';
            }
            
            while (map[nby+dy][nbx+dx] == '.') {
                nby += dy; nbx += dx;
            }
            if (map[nby+dy][nbx+dx] == 'O') {
                map[nry][nrx] = '.';
                continue;
            }
            if (redInHole) {
                ans = Math.min(ans, depth);
                continue;
            }
            map[nby][nbx] = 'B';
            
            if (!redFirst) {
                while (map[nry+dy][nrx+dx] == '.') {
                    nry += dy; nrx += dx;
                }
                if (map[nry+dy][nrx+dx] == 'O') {
                    ans = depth;
                    map[nry][nrx] = map[nby][nbx] = '.';
                    continue;
                }
                map[nry][nrx] = 'R';
            }
            
            ans = Math.min(ans, dfs(depth+1, nry, nrx, nby, nbx));
            
            map[nry][nrx] = map[nby][nbx] = '.';
        }
        map[ry][rx] = 'R';
        map[by][bx] = 'B';
        return ans;
    }
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        map = new char[N][];
        pos = new int[4];
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 'R') {
                    pos[0] = i;
                    pos[1] = j;
                } else if (map[i][j] == 'B') {
                    pos[2] = i;
                    pos[3] = j;
                }
            }
        }
        visit = new int[10][10][10][10];
        int ans = dfs(1, pos[0], pos[1], pos[2], pos[3]);
        if (ans == Integer.MAX_VALUE) ans = -1;
        System.out.println(ans);
    }
}