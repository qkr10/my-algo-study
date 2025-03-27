import java.util.*;
import java.io.*;

public class Main {
    static int[][] map, chickPos, visit;
    static int N, M, chick, calcCount, answer;
    
    static int[][] delta = {
        {0, 1},
        {1, 0},
        {-1, 0},
        {0, -1}
    };
    
    static void calc(int select) {
        calcCount++;
        
        Queue<int[]> q = new ArrayDeque<>();
        int i = 0;
        while (select != 0) {
            if ((select & 1) == 1) {
                q.add(chickPos[i]);
                visit[chickPos[i][0]][chickPos[i][1]] = calcCount;
            }
            select >>= 1;
            i++;
        }
        
        int ans = 0, dist = 0;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int j = 0; j < sz; j++) {
                int[] cur = q.poll();
                if (map[cur[0]][cur[1]] == 1)
                    ans += dist;
                for (int k = 0; k < 4; k++) {
                    int ny = cur[0] + delta[k][0];
                    int nx = cur[1] + delta[k][1];
                    if (ny < 0 || nx < 0 || ny >= N || nx >= N)
                        continue;
                    if (visit[ny][nx] == calcCount)
                        continue;
                    visit[ny][nx] = calcCount;
                    q.add(new int[]{ny, nx});
                }
            }
            dist++;
        }
        answer = Math.min(answer, ans);
    }
    
    static void dfs(int depth, int cur) {
        if (Integer.bitCount(cur) == M) {
            calc(cur);
            return;
        }
        if (depth > chick || chick - depth < M - Integer.bitCount(cur))
            return;
        dfs(depth+1, cur);
        cur |= 1 << depth;
        dfs(depth+1, cur);
        cur &= 0 << depth;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2)
                    chick++;
            }
        }
        chickPos = new int[chick][2];
        chick = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 2) {
                    chickPos[chick][0] = i;
                    chickPos[chick][1] = j;
                    chick++;
                }
            }
        
        visit = new int[N][N];
        answer = Integer.MAX_VALUE;
        dfs(0, 0);
        System.out.println(answer);
    }
}