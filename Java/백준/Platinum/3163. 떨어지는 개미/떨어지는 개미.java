//1시간 40분 소요
/*
푸는것도 디버깅도 너무 오래 걸림.
*/

import java.util.*;
import java.io.*;

public class Main {
    private static int countFallenAnts(int[][] ants, int time, int l) {
        int count = 0;
        for (int i = 0; i < ants[0].length; i++) {
            int sign = ants[1][i] < 0 ? -1 : 1;
            int pos = ants[0][i] + sign * time;
            if (pos < 0 || pos > l)
                count++;
        }
        return count;
    }
    private static int getKthFallenAntId(int[][] ants, int time, int l, int k) {
        int fallenAntsCount = 0;
        for (int i = 0; i < ants[0].length; i++) {
            int sign = ants[1][i] < 0 ? -1 : 1;
            ants[0][i] += sign * time;
            if (ants[0][i] < 0 || ants[0][i] > l)
                fallenAntsCount++;
        }
        Arrays.sort(ants[0]);
        
        int[][] fallenAnts = new int[fallenAntsCount][2];
        int idx = 0;
        for (int i = 0; i < ants[0].length; i++)
            if (ants[0][i] < 0 || ants[0][i] > l) {
                int pos = ants[0][i];
                int timeFromFallen = Math.min(Math.abs(pos), Math.abs(pos - l));
                fallenAnts[idx][0] = timeFromFallen;
                fallenAnts[idx++][1] = ants[1][i];
            }
        Arrays.sort(fallenAnts, (a, b) -> {
            if (a[0] == b[0]) return a[1] - b[1];
            return b[0] - a[0];
        });
        
        return fallenAnts[k-1][1];
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var sb = new StringBuffer();
        int T = Integer.parseInt(br.readLine());
        while (T-- != 0) {
            var st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[][] ants = new int[2][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                ants[0][i] = Integer.parseInt(st.nextToken());
                ants[1][i] = Integer.parseInt(st.nextToken());
            }
            
            int mint = 1, maxt = 5005000, rest = 0;
            while (mint + 1 < maxt) {
                int midt = (mint + maxt) / 2;
                int res = countFallenAnts(ants, midt, L);
                if (res == K) {
                    rest = midt;
                    break;
                }
                else if (res < K)
                    mint = midt;
                else
                    maxt = midt;
            }
            if (rest == 0) rest = maxt;
            sb.append(getKthFallenAntId(ants, rest, L, K)).append('\n');
        }
        System.out.println(sb);
    }
}