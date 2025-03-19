//1시간 30분 소요
//delta 배열의 북쪽과 남쪽을 반대로 설정해서 문제를 찾아내기 어려웠음.
//코드를 한줄한줄 다시 꼼꼼히 살펴봐야 한다.

import java.util.*;
import java.io.*;

public class Main {
    static int[][] map;
    static int N, M, R, C, D;
    static int[][] delta = {
        {-1, 0},
        {0, 1},
        {1, 0},
        {0, -1}
    };
    
    static int operate() {
        int ans = 0;
        while (true) {
            if (map[R][C] == 0) {
                map[R][C] = 2 + ans;
                ans++;
            }
            int i;
            for (i = 0; i < 4; i++) {
                int nr = R + delta[i][0];
                int nc = C + delta[i][1];
                if (nr < 0 || nc < 0 || nr >= N || nc >= M)
                    continue;
                if (map[nr][nc] == 0)
                    break;
            }
            if (i == 4) {
                //청소되지 않은 빈칸이 없다면
                int rear = (D + 2) % 4;
                int nr = R + delta[rear][0];
                int nc = C + delta[rear][1];
                if (nr < 0 || nc < 0 || nr >= N || nc >= M || map[nr][nc] == 1)
                    break;
                R = nr;
                C = nc;
            } else {
                //청소되지 않은 빈칸이 있다면
                D = (D + 3) % 4;
                int nr = R + delta[D][0];
                int nc = C + delta[D][1];
                if (nr < 0 || nc < 0 || nr >= N || nc >= M || map[nr][nc] != 0)
                    continue;
                R = nr;
                C = nc;
            }
        }
        return ans;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        System.out.println(operate());
    }
}