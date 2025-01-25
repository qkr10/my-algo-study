/*
2시간 40분 소요
#### 풀이과정
1. N, S, M[2][4][4][8], Sx, Sy 입력 받기. 물고기의 위치와 방향이 a, b, c 이면 M[0][a-1][b-1][c-1]++ 한다.
2. 현재 i번째 연습 중이라면 M[1] 배열에 물고기의 다음 위치/방향을 저장.
3. 상어를 64가지 방법으로 테스트하여 가장 많은 물고기를 제외시키면서 사전순으로 앞서는 방법을 찾음.
4. M[0] 배열에 있는 물고기를 M[1] 로 복제
5. i++ 하여 2번부터 i <= S 인 동안 반복.
6. 현재 물고기 수 출력.
*/

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int[][][][] M = new int[3][4][4][9];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            M[0][a-1][b-1][c-1]++;
            M[2][a-1][b-1][c-1]++;
        }
        st = new StringTokenizer(br.readLine());
        int Sx = Integer.parseInt(st.nextToken()) - 1;
        int Sy = Integer.parseInt(st.nextToken()) - 1;

        int[][] Sdelta = {
                {-1, 0},
                {0, -1},
                {1, 0},
                {0, 1}
        };
        int[][] Fdelta = {
                {0, -1},
                {-1, -1},
                {-1, 0},
                {-1, 1},
                {0, 1},
                {1, 1},
                {1, 0},
                {1, -1}
        };
        for (int i = 0; i < S; i++) {
            int[][][] curM = M[i & 1];
            int[][][] nextM = M[(i+1) & 1];

            //물고기 이동
            for (int x = 0; x < 4; x++)
                for (int y = 0; y < 4; y++)
                    for (int d = 7; d >= 0; d--) {
                        int nextX = x + Fdelta[d][0];
                        int nextY = y + Fdelta[d][1];
                        boolean a = nextX < 0 || 4 <= nextX || nextY < 0 || 4 <= nextY;
                        boolean b = !a && curM[nextX][nextY][8] != 0;
                        boolean c = nextX == Sx && nextY == Sy;
                        int newD = d;
                        while (a || b || c) {
                            newD--;
                            if (newD < 0) newD = 7;
                            if (newD == d) {
                                nextX = x;
                                nextY = y;
                                break;
                            }
                            nextX = x + Fdelta[newD][0];
                            nextY = y + Fdelta[newD][1];
                            a = nextX < 0 || 4 <= nextX || nextY < 0 || 4 <= nextY;
                            b = !a && curM[nextX][nextY][8] != 0;
                            c = nextX == Sx && nextY == Sy;
                        }
                        nextM[nextX][nextY][newD] += curM[x][y][d];
                    }

            //상어 이동경로 결정
            int[] method = new int[3];
            long count = -1;
            for (int d1 = 0; d1 < 4; d1++) {
                int x1 = Sx + Sdelta[d1][0];
                int y1 = Sy + Sdelta[d1][1];
                if (x1 < 0 || 4 <= x1 || y1 < 0 || 4 <= y1)
                    continue;
                int temp1 = 0;
                for (int d = 0; d < 8; d++)
                    temp1 += nextM[x1][y1][d];
                for (int d2 = 0; d2 < 4; d2++) {
                    int x2 = x1 + Sdelta[d2][0];
                    int y2 = y1 + Sdelta[d2][1];
                    if (x2 < 0 || 4 <= x2 || y2 < 0 || 4 <= y2)
                        continue;
                    int temp2 = 0;
                    if (x1 != x2 || y1 != y2)
                        for (int d = 0; d < 8; d++)
                            temp2 += nextM[x2][y2][d];
                    for (int d3 = 0; d3 < 4; d3++) {
                        int x3 = x2 + Sdelta[d3][0];
                        int y3 = y2 + Sdelta[d3][1];
                        if (x3 < 0 || 4 <= x3 || y3 < 0 || 4 <= y3)
                            continue;
                        int temp3 = 0;
                        if (x1 != x3 || y1 != y3)
                            if (x2 != x3 || y2 != y3)
                                for (int d = 0; d < 8; d++)
                                    temp3 += nextM[x3][y3][d];
                        int curCount = temp1 + temp2 + temp3;
                        if (curCount > count) {
                            count = curCount;
                            method[0] = d1;
                            method[1] = d2;
                            method[2] = d3;
                        }
                    }
                }
            }

            //물고기 냄새 업데이트, 물고기 제외, 상어의 위치 업데이트
            for (int m = 0; m < 3; m++) {
                Sx += Sdelta[method[m]][0];
                Sy += Sdelta[method[m]][1];
                for (int d = 0; d < 8; d++) {
                    if (nextM[Sx][Sy][d] > 0)
                        nextM[Sx][Sy][8] = 2;
                    nextM[Sx][Sy][d] = 0;
                }
            }

            //복제 마법 마무리
            for (int x = 0; x < 4; x++)
                for (int y = 0; y < 4; y++) {
                    nextM[x][y][8] = Math.max(curM[x][y][8] - 1, nextM[x][y][8]);
                    curM[x][y][8] = 0;
                    for (int d = 0; d < 8; d++) {
                        nextM[x][y][d] += curM[x][y][d];
                        curM[x][y][d] = 0;
                    }
                }
        }
        int[][][] curM = M[S & 1];
        long result = 0;
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++)
                for (int d = 0; d < 8; d++)
                    result += curM[x][y][d];
        System.out.println(result);
    }
}