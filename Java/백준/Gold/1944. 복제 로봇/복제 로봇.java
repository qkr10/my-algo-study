//2시간 이상 소요

import java.util.*;
import java.io.*;

public class Main {
    static int[][] delta = {
            {0, 1},
            {-1, 0},
            {1, 0},
            {0, -1},
    };
    static Comparator<int[]> fn = (a, b) -> a[0] - b[0];

    static void bfs(char[][] map, int[][] imap, int[][] coord, int mex) {
        int N = map.length;
        PriorityQueue<int[]> queue = new PriorityQueue<>(fn);
        queue.add(new int[]{0, coord[mex][1], coord[mex][2]});
        boolean[][] visit = new boolean[N][N];
        visit[coord[mex][1]][coord[mex][2]] = true;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (cur[0] != 0 && map[cur[1]][cur[2]] == 'K') {
                int idx = imap[cur[1]][cur[2]];
                if (idx <= mex || cur[0] >= coord[idx][0])
                    continue;
                coord[idx][0] = cur[0];
                continue;
            }
            for (int[] d : delta) {
                int nextI = cur[1] + d[0];
                int nextJ = cur[2] + d[1];
                if (nextI < 0 || N <= nextI || nextJ < 0 || N <= nextJ)
                    continue;
                if (visit[nextI][nextJ] || map[nextI][nextJ] == '1')
                    continue;
                visit[nextI][nextJ] = true;
                queue.add(new int[]{cur[0] + 1, nextI, nextJ});
            }
        }

        Arrays.sort(coord, mex+1, coord.length, fn);
        for (int i = mex+1; i < coord.length; i++)
            imap[coord[i][1]][coord[i][2]] = i;

        while (mex+2 < coord.length) {
            if (coord[mex+1][0] == 0) {
                mex++;
                continue;
            }
            bfs(map, imap, coord, mex+1);
            break;
        }
    }

    public static void main1(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        char[][] map = new char[N][N];
        System.out.printf("%d %d\n", N, K);
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = st.nextToken().charAt(0);
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        char[][] map = new char[N][];
        for (int i = 0; i < N; i++)
            map[i] = br.readLine().toCharArray();

        int[][] imap = new int[N][N];
        int[][] coord = new int[K+1][3];
        int count = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 'S') {
                    coord[0][0] = 0;
                    coord[0][1] = i;
                    coord[0][2] = j;
                } else if (map[i][j] == 'K') {
                    coord[count][0] = 0x00FFFFFF;
                    coord[count][1] = i;
                    coord[count][2] = j;
                    imap[i][j] = count++;
                }
            }

        bfs(map, imap, coord, 0);
        int ret = 0;
        for (int i = 1; i < coord.length; i++) {
            ret += coord[i][0];
            if (coord[i][0] == 0x00FFFFFF) {
                ret = -1;
                break;
            }
        }
        System.out.println(ret);
    }
}