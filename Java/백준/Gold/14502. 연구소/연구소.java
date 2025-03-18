import java.util.*;
import java.io.*;

public class Main {
    static int[][] delta = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };
    
    static void bfs(int[][] visit, int[][] map, int N, int M, int i, int j) {
        var q = new ArrayDeque<int[]>();
        q.add(new int[]{i, j});
        visit[i][j] = 1;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int ny = cur[0] + delta[d][0];
                int nx = cur[1] + delta[d][1];
                if (nx < 0 || ny < 0 || nx >= M || ny >= N)
                    continue;
                if (map[ny][nx] == 1 || visit[ny][nx] == 1)
                    continue;
                q.add(new int[]{ny, nx});
                visit[ny][nx] = 1;
            }
        }
    }
    
    static int countSafeArea(int[][] map, int N, int M) {
        int[][] visit = new int[N][M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                if (visit[i][j] != 0 || map[i][j] != 2)
                    continue;
                bfs(visit, map, N, M, i, j);
            }
        int ans = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (map[i][j] == 0 && visit[i][j] == 0)
                    ans++;
        return ans;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        int cells = N*M, maxSafeArea = 0;
        for (int i = 0; i < cells; i++) {
            if (map[i/M][i%M] != 0)
                continue;
            map[i/M][i%M] = 1;
            for (int j = i+1; j < cells; j++) {
                if (map[j/M][j%M] != 0)
                    continue;
                map[j/M][j%M] = 1;
                for (int k = j+1; k < cells; k++) {
                    if (map[k/M][k%M] != 0)
                        continue;
                    map[k/M][k%M] = 1;
                    maxSafeArea = Math.max(maxSafeArea, countSafeArea(map, N, M));
                    map[k/M][k%M] = 0;
                }
                map[j/M][j%M] = 0;
            }
            map[i/M][i%M] = 0;
        }
        System.out.println(maxSafeArea);
    }
}