import java.util.*;
import java.io.*;

public class Main {
    static int[][] delta = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        
        int K = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];
        for (int i = 0; i < K; i++) {
            var st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            map[y][x] = -1;
        }
        
        int L = Integer.parseInt(br.readLine()), dir = 0;
        int[][] rotates = new int[L+1][2];
        for (int i = 0; i < L; i++) {
            var st = new StringTokenizer(br.readLine());
            rotates[i][0] = Integer.parseInt(st.nextToken());
            dir = st.nextToken().charAt(0) == 'D' ? (dir + 1) % 4 : (dir + 3) % 4;
            rotates[i][1] = dir;
        }
        rotates[L][0] = rotates[L-1][0] + N+1;
        rotates[L][1] = dir;
        
        int y = 0, x = 0, t = 0, i;
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});
        dir = 0;
        map[y][x] = t+1;
        for (i = 0; i < L+1; i++) {
            boolean trig = false;
            while (t < rotates[i][0]) {
                t++;
                
                int ny = y + delta[dir][0];
                int nx = x + delta[dir][1];
                if (ny < 0 || ny >= N || nx < 0 || nx >= N || map[ny][nx] > 0) {
                    trig = true;
                    break;
                }
                
                boolean isApple = map[ny][nx] == -1;
                map[ny][nx] = t+1;
                q.add(new int[]{ny, nx});
                y = ny; x = nx;
                
                if (!isApple && !q.isEmpty()) {
                    int[] tail = q.poll();
                    map[tail[0]][tail[1]] = 0;
                }
            }
            if (trig)
                break;
            dir = rotates[i][1];
        }
        System.out.println(t);
    }
}