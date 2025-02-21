//1시간 10분 소요

import java.util.*;
import java.io.*;

class UnionFind {
    int[] parent, rank;
    public UnionFind(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++)
            parent[i] = i;
        rank = new int[N];
    }
    public void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (rank[a] < rank[b]) {
            parent[a] = b;
        } else if (rank[a] > rank[b]) {
            parent[b] = a;
        } else {
            rank[a]++;
            parent[b] = a;
        }
    }
    public int find(int n) {
        if (parent[n] == n)
            return n;
        return parent[n] = find(parent[n]);
    }
}

public class Main {
    static int[][] delta = new int[][]{
            {1, 0},
            {0, 1},
            {-1, 0},
            {0, -1}
    };

    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][];
        for (int i = 0; i < R; i++)
            map[i] = br.readLine().toCharArray();

        UnionFind uf = new UnionFind(R*C);
        Queue<int[]> q = new ArrayDeque<>();
        int[] swan = new int[2];
        int swanCount = 0;
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 'X') {
                    for (int d = 0; d < 4; d++) {
                        int y = i + delta[d][0];
                        int x = j + delta[d][1];
                        if (y < 0 || y >= R || x < 0 || x >= C)
                            continue;
                        if (map[y][x] == 'X')
                            continue;
                        q.add(new int[]{i, j});
                        break;
                    }
                    continue;
                }
                for (int d = 0; d < 2; d++) {
                    int y = i + delta[d][0];
                    int x = j + delta[d][1];
                    if (y >= R || x >= C)
                        continue;
                    if (map[y][x] == 'X')
                        continue;
                    uf.union(i*C+j, y*C+x);
                }
                if (map[i][j] == 'L') {
                    swan[swanCount++] = i*C+j;
                }
            }

        if (uf.find(swan[0]) == uf.find(swan[1])) {
            System.out.println(0);
            return;
        }

        int day = 0;
        int iceCount = q.size();
        boolean[][] visit = new boolean[R][C];
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int curPos = cur[0] * C + cur[1];
            map[cur[0]][cur[1]] = '.';

            for (int i = 0; i < 4; i++) {
                int y = cur[0] + delta[i][0];
                int x = cur[1] + delta[i][1];
                int nextPos = y * C + x;
                if (y < 0 || y >= R || x < 0 || x >= C)
                    continue;

                if (map[y][x] != 'X')
                    uf.union(curPos, nextPos);
                if (map[y][x] != 'X' || visit[y][x])
                    continue;
                q.add(new int[]{y, x});
                visit[y][x] = true;
            }

            iceCount--;
            if (iceCount == 0) {
                day++;
                if (uf.find(swan[0]) == uf.find(swan[1]))
                    break;
                iceCount = q.size();
            }
        }
        System.out.println(day);
    }
}