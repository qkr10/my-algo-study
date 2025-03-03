//3시간 23분에 답 확인

import java.util.*;
import java.io.*;

public class Main {
    static char[] str;
    static int N;
    static int[][] dp = new int[51][51];
    
    static void swap(char[] arr, int a, int b) {
        char temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    
    static void reset() {
        for (int i = 0; i < 51; i++)
            for (int j = 0; j < 51; j++)
                dp[i][j] = -1;
    }
    
    static int dfs(int s, int e) {
        if (dp[s][e] != -1)
            return dp[s][e];
        if (e - s < 2)
            return dp[s][e] = (str[s] == str[e] ? 0 : 1);
        
        int ans = Integer.MAX_VALUE;
        ans = Math.min(ans, dfs(s+1, e-1) + (str[s] == str[e] ? 0 : 1));
        ans = Math.min(ans, dfs(s+1, e) + 1);
        ans = Math.min(ans, dfs(s, e-1) + 1);
        return dp[s][e] = ans;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        str = br.readLine().toCharArray();
        N = str.length;
        
        reset();
        int ans = dfs(0, N-1);
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                swap(str, i, j);
                reset();
                ans = Math.min(ans, 1 + dfs(0, N-1));
                swap(str, i, j);
            }
        }
        System.out.println(ans);
    }
}