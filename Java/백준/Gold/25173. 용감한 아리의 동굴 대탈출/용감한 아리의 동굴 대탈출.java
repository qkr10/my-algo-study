//2시간 소요

import java.util.*;
import java.io.*;

public class Main{
    static String[] answer = {"VICTORY!\n", "CAVELIFE...\n"};
    static int[][] delta = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };
    
    static int[][] map;
    static int[] ahri, boss;
    static int N, M;
    static boolean hasStalagmite;
    
    static boolean ahriAttack() {
        boss[2] -= ahri[3];
        return boss[2] <= 0;
    }
    
    static boolean ahriMove() {
        ahri[5] = ahri[0];
        ahri[6] = ahri[1];
        for (int i = 0; i < 4; i++) {
            int dir = (ahri[4] + i) % 4;
            int y = ahri[0] + delta[dir][0];
            int x = ahri[1] + delta[dir][1];
            if (isInMap(y, x) && map[y][x] == 0) {
                map[ahri[0]][ahri[1]] = 0;
                ahri[0] = y;
                ahri[1] = x;
                ahri[4] = dir;
                map[y][x] = 2;
                break;
            }
            ahri[2]--;
        }
        return ahri[2] <= 0;
    }
    
    static boolean isInMap(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < M;
    }
    
    static boolean bossAttack() {
        if (!hasStalagmite)
            return false;
        
        int curInCaveCount = 0;
        int dir = boss[4], dirCount = 1, dirCountMax = 1, cornerCount = 0;
        int cury = boss[0], curx = boss[1];
        while (true) {
            int nexty = cury + delta[dir][0];
            int nextx = curx + delta[dir][1];
            
            dirCount--;
            if (dirCount == 0) {
                cornerCount++;
                if (cornerCount == 2) {
                    cornerCount = 0;
                    dirCountMax++;
                }
                dirCount = dirCountMax;
                dir = (dir + 1) % 4;
            }
            
            cury = nexty;
            curx = nextx;
            
            if (isInMap(cury, curx) && map[cury][curx] == 1)
                break;
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->a[0]-b[0]);
        pq.add(new int[]{0, cury, curx});
        boolean[][] visit = new boolean[N][M];
        visit[cury][curx] = true;
        
        int resultDist = -1, desty = ahri[0], destx = ahri[1];
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            for (int i = 0; i < 4; i++) {
                int y = cur[1] + delta[i][0];
                int x = cur[2] + delta[i][1];
                if (desty == y && destx == x) {
                    resultDist = cur[0]+1;
                    break;
                }
                if (!isInMap(y, x) || visit[y][x] || map[y][x] != 0)
                    continue;
                visit[y][x] = true;
                pq.add(new int[]{cur[0]+1, y, x});
            }
            if (resultDist != -1)
                break;
        }
        if (resultDist == -1)
            return false;
        
        int damage = boss[3] - resultDist;
        ahri[2] = Math.min(ahri[2], ahri[2] - damage);
        return ahri[2] <= 0;
    }
    
    static void bossMove() {
        if (ahri[0] == ahri[5] && ahri[1] == ahri[6])
            return;
        map[boss[0]][boss[1]] = 0;
        boss[0] = ahri[5];
        boss[1] = ahri[6];
        boss[4] = ahri[4];
        map[boss[0]][boss[1]] = 3;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        map = new int[N][M];
        ahri = new int[7];
        boss = new int[5];
        hasStalagmite = false;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1)
                    hasStalagmite = true;
                else if (map[i][j] == 2)
                    ahri = new int[]{i, j, 0, 0, 0, 0, 0};
                else if (map[i][j] == 3)
                    boss = new int[]{i, j, 0, 0, 0};
            }
        }
        st = new StringTokenizer(br.readLine());
        ahri[2] = Integer.parseInt(st.nextToken()); //아리 체력
        ahri[3] = Integer.parseInt(st.nextToken()); //아리 공격
        boss[2] = Integer.parseInt(st.nextToken()); //보스 체력
        boss[3] = Integer.parseInt(st.nextToken()); //보스 공격
        for (int i = 0; i < 4; i++) {
            boolean y = ahri[0] == boss[0] + delta[i][0];
            boolean x = ahri[1] == boss[1] + delta[i][1];
            if (y && x) {
                boss[4] = ahri[4] = i;
                break;
            }
        }
        
        while (true) {
            if (ahriAttack()) //보스 체력에서 아리 공격력 빼기
                break;
            if (ahriMove())
                break;
            if (bossAttack())
                break;
            bossMove();
        }
        
        if (ahri[2] <= 0)
            System.out.println(answer[1]);
        else
            System.out.println(answer[0]);
    }
}