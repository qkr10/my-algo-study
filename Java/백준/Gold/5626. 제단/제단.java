import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[] arr = new int[N];
        var st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
        
        //dp[i][j] = i번째 열의 높이가 j인 경우의 수
        //dp[0][0] = 1
        //dp[i][j] = dp[i+1][j-1] + dp[i+1][j+1] + dp[i+1][j]
        //dp[N-1][0] 이 정답
        int minh = 0, maxh = 0;
        int[][] dp = new int[2][10001];
        dp[0][0] = 1;
        long mod = 1000000007;
        for (int i = 0; i < N-1; i++) {
            if (arr[i] != -1 && (arr[i] < minh || maxh < arr[i])) {
                System.out.println(0);
                return;
            }
            Arrays.fill(dp[(i+1)%2], 0);
            for (int h = minh; h <= maxh; h++) {
                dp[(i+1)%2][h] = (int)((dp[(i+1)%2][h] + (long)dp[i%2][h]) % mod);
                if (h-1 >= 0)
                    dp[(i+1)%2][h-1] = (int)((dp[(i+1)%2][h-1] + (long)dp[i%2][h]) % mod);
                if (h+1 <= 10000)
                    dp[(i+1)%2][h+1] = (int)((dp[(i+1)%2][h+1] + (long)dp[i%2][h]) % mod);
            }
            minh = Math.max(0, minh-1);
            maxh = Math.min(10000, maxh+1);
            if (arr[i+1] != -1) {
                minh = maxh = arr[i+1];
            }
        }
        if (minh > 0 || (N == 1 && arr[0] > 0)) {
            System.out.println(0);
            return;
        }
        System.out.println(dp[(N-1)%2][0]);
    }
}