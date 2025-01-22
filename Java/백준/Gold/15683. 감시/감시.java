/*
1시간 10분 소요
### 풀이과정
1. 필드에 map[][], spy[][], cctv[][2], result, minResult 변수 선언
2. map[][] 에 입력받은 사무실 정보 저장. 이때 cctv의 갯수 세기.
3. cctv[][2] 에 cctv의 좌표값 저장.
4. dfs(int cctvIndex) 재귀적으로 구현.
4-1. cctv 하나의 방향을 정함.
4-2. spy 배열에 추가로 감시하는 칸마다 +1 하고, result 변수 수정.
4-3. dfs(cctvIndex+1) 호출
4-4. 마지막 cctv까지 방향을 정했으면 minResult 변수 업데이트
*/

import java.util.*;
import java.io.*;

public class Main{
    private static int[][] map, spy, cctv;
    private static int[][] delta = {
            {0, 1},
            {1, 0},
            {0, -1},
            {-1, 0}
    };
    private static int[][] cctvDir = {
            {0},
            {0, 2},
            {0, 1},
            {0, 1, 2},
            {0, 1, 2, 3}
    };
    private static int[] cctvRot = {4, 2, 4, 4, 4};
    private static int x, y, c, result, minResult;

    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));

        var st = new StringTokenizer(br.readLine());
        y = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        map = new int[y][x];
        spy = new int[y][x];
        for (int i = 0; i < y; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < x; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0)
                    result++;
                if (1 <= map[i][j] && map[i][j] <= 5)
                    c++;
            }
        }

        cctv = new int[c][];
        int k = 0;
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                if (1 <= map[i][j] && map[i][j] <= 5)
                    cctv[k++] = new int[]{i, j};

        minResult = Integer.MAX_VALUE;
        dfs(0);
        System.out.println(minResult);
    }

    private static void dfs(int cctvIndex) {
        if (cctvIndex == c) {
            minResult = Math.min(minResult, result);
            return;
        }

        int cctvY = cctv[cctvIndex][0];
        int cctvX = cctv[cctvIndex][1];
        int cctvType = map[cctvY][cctvX] - 1;
        for (int rot = 0; rot < cctvRot[cctvType]; rot++) {
            for (int dir : cctvDir[cctvType]) {
                int curDir = (dir + rot) % 4;
                for (int i = 1; i <= Math.max(x, y); i++) {
                    int curY = cctvY + delta[curDir][0] * i;
                    int curX = cctvX + delta[curDir][1] * i;
                    if (curY < 0 || y <= curY || curX < 0 || x <= curX)
                        break;
                    if (map[curY][curX] >= 6)
                        break;
                    if (map[curY][curX] == 0 && spy[curY][curX] == 0)
                        result--;
                    spy[curY][curX]++;
                }
            }
            dfs(cctvIndex+1);
            for (int dir : cctvDir[cctvType]) {
                int curDir = (dir + rot) % 4;
                for (int i = 1; i <= Math.max(x, y); i++) {
                    int curY = cctvY + delta[curDir][0] * i;
                    int curX = cctvX + delta[curDir][1] * i;
                    if (curY < 0 || y <= curY || curX < 0 || x <= curX)
                        break;
                    if (map[curY][curX] >= 6)
                        break;
                    if (map[curY][curX] == 0 && spy[curY][curX] == 1)
                        result++;
                    spy[curY][curX]--;
                }
            }
        }
    }
}