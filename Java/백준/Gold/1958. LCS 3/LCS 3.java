import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        char[] A = br.readLine().toCharArray();
        char[] B = br.readLine().toCharArray();
        char[] C = br.readLine().toCharArray();
        int a = A.length;
        int b = B.length;
        int c = C.length;
        int[][][] dp = new int[a+1][b+1][c+1];
        for (int i = 1; i <= a; i++)
        for (int j = 1; j <= b; j++)
        for (int k = 1; k <= c; k++) {
            if (A[i-1] == B[j-1] && B[j-1] == C[k-1]) {
                dp[i][j][k] = dp[i-1][j-1][k-1] + 1;
            } else {
                dp[i][j][k] = Math.max(dp[i-1][j][k], Math.max(dp[i][j-1][k], dp[i][j][k-1]));
            }
        }
        System.out.println(dp[a][b][c]);
    }
}