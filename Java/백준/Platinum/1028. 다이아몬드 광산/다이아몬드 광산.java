/*
1시간 10분 소요

input[i][j] = i행 j열 숫자
rightdown[i][j] = (rightdown[i+1][j+1] + 1) * input[i][j]
leftdown[i][j] = (leftdown[i+1][j-1] + 1) * input[i][j]
rightup[i][j] = (rightup[i-1][j+1] + 1) * input[i][j]
leftup[i][j] = (leftup[i-1][j-1] + 1) * input[i][j]
*/

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] input = new char[R][];
        for (int i = 0; i < R; i++) {
            input[i] = br.readLine().toCharArray();
            for (int j = 0; j < C; j++)
                input[i][j] -= '0';
        }
        
        short[][] rightdown = new short[R+2][C+2];
        short[][] leftdown = new short[R+2][C+2];
        short[][] rightup = new short[R+2][C+2];
        short[][] leftup = new short[R+2][C+2];
        for (int i = R; i > 0; i--)
            for (int j = 1; j <= C; j++) {
                rightdown[i][j] = (short)((rightdown[i+1][j+1] + 1) * input[i-1][j-1]);
                leftdown[i][j] = (short)((leftdown[i+1][j-1] + 1) * input[i-1][j-1]);
            }
        for (int i = 1; i <= R; i++)
            for (int j = 1; j <= C; j++) {
                rightup[i][j] = (short)((rightup[i-1][j+1] + 1) * input[i-1][j-1]);
                leftup[i][j] = (short)((leftup[i-1][j-1] + 1) * input[i-1][j-1]);
            }
        
        int result = 0;
        for (int i = 1; i <= R; i++)
            for (int j = 1; j <= C; j++) {
                int sz = Math.min(rightdown[i][j], rightup[i][j]);
                int rightj = j + (sz - 1) * 2;
                while (rightj > C) {
                    sz--;
                    rightj = j + (sz - 1) * 2;
                }
                if (sz <= 0)
                    continue;
                int rightsz = Math.min(sz, Math.min(leftdown[i][rightj], leftup[i][rightj]));
                while (sz != rightsz) {
                    sz--;
                    if (sz == 0)
                        break;
                    rightj = j + (sz - 1) * 2;
                    rightsz = Math.min(sz, Math.min(leftdown[i][rightj], leftup[i][rightj]));
                }
                if (sz != rightsz || sz <= 0)
                    continue;
                result = Math.max(result, sz);
            }
        System.out.println(result);
    }
}