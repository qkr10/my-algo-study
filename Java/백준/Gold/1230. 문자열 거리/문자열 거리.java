import java.util.*;
import java.io.*;

public class Main {
    static String A, B;
    static int[][] dp;
    static int dfs(int Aidx, int Bidx) {
        if (dp[Aidx][Bidx] != -1)
            return dp[Aidx][Bidx];
        
        if (Aidx == A.length()-1)
            return dp[Aidx][Bidx] = (Bidx == B.length()-1 ? 0 : 1);
        
        char a = A.charAt(Aidx+1);
        int ans = 10000;
        for (int i = Bidx+1; i < B.length(); i++)
            if (a == B.charAt(i))
                ans = Math.min(ans, dfs(Aidx+1, i) + (i == Bidx+1 ? 0 : 1));
        
        return dp[Aidx][Bidx] = ans;
    } 
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        A = br.readLine();
        B = br.readLine();
        dp = new int[1001][1001];
        for (int i = 0; i < 1001; i++)
            for (int j = 0; j < 1001; j++)
                dp[i][j] = -1;
        int ans = 10000;
        for (int i = 0; i < B.length(); i++) {
            if (A.charAt(0) == B.charAt(i))
                ans = Math.min(ans, dfs(0, i) + (i == 0 ? 0 : 1));
        }
        if (ans == 10000)
            ans = -1;
        System.out.println(ans);
    }
}