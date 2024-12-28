import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] nmStr = br.readLine().split(" ");
        int N = Integer.parseInt(nmStr[0]);
        int M = Integer.parseInt(nmStr[1]);
        int[] D = new int[N];
        for (int i = 0; i < N; i++) {
            D[i] = Integer.parseInt(br.readLine());
        }
        //dp[i][j] = 시간이 i 이고, 지침 지수가 j 이고, 달리기 가능한 상태일때, 최대 이동한 거리
        int[][] dp = new int[N+1][M+2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M+2; j++) {
                //현재 시간이 i 이고, 현재 지침 지수가 j 일때
                
                //달린다.
                if (j <= M && i >= j)
                    dp[i+1][j+1] = dp[i][j] + D[i];
                
                //쉰다.
                int nextI = i + Math.max(1, j);
                if (nextI <= N)
                    dp[nextI][0] = Math.max(dp[nextI][0], dp[i][j]);
            }
        }
        System.out.printf("%d\n", dp[N][0]);
    }
}