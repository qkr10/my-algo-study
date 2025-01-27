/*
현재 2시간 31분 소요

#### 풀이과정
1. map[i][j] = i행 j열이 0=호수 1=땅 2=배양액 뿌릴수 있는 땅
2. N, M, G, R, B = map[i][j]==2 인 땅의 수, A[i] = i번째 배양액 뿌릴수 있는 땅의 좌표
3. bfs(g, r) : g[i] = A[i]에 초록 배양액 뿌려졌는지 진리값. r[i] = A[i]에 빨간 배양액 뿌려졌는지 진리값.
    1. g, r 은 비트마스크로 표현. 주어진 g, r 에 대해 피울수 있는 꽃의 최대 갯수를 반환함.
4. dfs(g, r) : g, r 의 모든 경우의 수 각각에 대해 bfs(g, r) 을 호출하고 최대값을 반환함.
*/

import java.util.*;
import java.io.*;

public class Main {
    private static int N, M, G, R, B;
    private static int[][] map, A;
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        G = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2)
                    B++;
            }
        }
        A = new int[B][2];
        int Ai = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (map[i][j] == 2) {
                    A[Ai][0] = i;
                    A[Ai++][1] = j;
                }
        
        System.out.println(dfs(0, 0));
    }
    
    private static int dfs(int g, int r) {
        if (Integer.bitCount(g) != G) {
            int ret = 0;
            int gtz = Math.min(Integer.numberOfTrailingZeros(g), B);
            for (int i = gtz-1; i >= 0; i--)
                ret = Math.max(ret, dfs(g | (1 << i), r));
            return ret;
        }
        else if (Integer.bitCount(r) != R) {
            int ret = 0;
            int rtz = Math.min(Integer.numberOfTrailingZeros(r), B);
            for (int i = rtz-1; i >= 0; i--) {
                if (((g | r) & (1 << i)) != 0)
                    continue;
                ret = Math.max(ret, dfs(g, r | (1 << i)));
            }
            return ret;
        }
        return bfs(g, r);
    }
    private static final int[][] delta = {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };
    private static int bfs(int g, int r) {
        //colorMap[a][b][c] = a행 b열 c색 배양액이 뿌져진 시간
        int[][][] colorMap = new int[N][M][2];
        
        //0: 행, 1: 열, 2: 색, 3: time
        Queue<int[]> queue = new ArrayDeque<>();
        while (g != 0) {
            int[] temp = A[Integer.numberOfTrailingZeros(g)];
            queue.add(new int[]{temp[0], temp[1], 0});
            colorMap[temp[0]][temp[1]][0] = 1;
            g &= g - 1;
        }
        while (r != 0) {
            int[] temp = A[Integer.numberOfTrailingZeros(r)];
            queue.add(new int[]{temp[0], temp[1], 1});
            colorMap[temp[0]][temp[1]][1] = 1;
            r &= r - 1;
        }
        
        int ret = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int t = colorMap[cur[0]][cur[1]][cur[2]];
            if (colorMap[cur[0]][cur[1]][cur[2] ^ 1] == t) {
                if (cur[2] == 0)
                    ret++;
                continue;
            }
            for (int dir = 0; dir < 4; dir++) {
                int row = cur[0] + delta[dir][0];
                int col = cur[1] + delta[dir][1];
                if (Math.min(row, col) < 0 || row >= N || col >= M)
                    continue;
                if (map[row][col] != 0 && colorMap[row][col][cur[2]] == 0) {
                    queue.add(new int[]{row, col, cur[2]});
                    colorMap[row][col][cur[2]] = t+1;
                }
            }
        }
        return ret;
    }
}