//56분 소요
//마지막 10분 가량 디버깅
//침착하게 위에서 부터 다시 훑어본 것이 도움이 되었음.
//1. getDiff() 의 diff += S[i][j] 와 diff -= S[i][j] 를 고칠수 있었음.

import java.util.*;
import java.io.*;

public class Main {
    static int[][] S;
    static int N;
    
    static int getDiff(int cur) {
        int diff = 0;
        //스타트 팀은 diff += Sij 함.
        int temp1 = cur;
        while (temp1 != 0) {
            int i = Integer.numberOfTrailingZeros(temp1 & -temp1);
            temp1 &= temp1 - 1;
            int temp2 = temp1;
            while (temp2 != 0) {
                int j = Integer.numberOfTrailingZeros(temp2 & -temp2);
                temp2 &= temp2 - 1;
                diff += S[i][j] + S[j][i];
            }
        }
        //링크 팀은 diff -= Sij 함.
        temp1 = ~cur & ((1 << N) - 1);
        while (temp1 != 0) {
            int i = Integer.numberOfTrailingZeros(temp1 & -temp1);
            temp1 &= temp1 - 1;
            int temp2 = temp1;
            while (temp2 != 0) {
                int j = Integer.numberOfTrailingZeros(temp2 & -temp2);
                temp2 &= temp2 - 1;
                diff -= S[i][j] + S[j][i];
            }
        }
        return Math.abs(diff);
    }
    
    static int dfs(int cur) {
        int remain = N/2 - Integer.bitCount(cur);
        if (remain == 0) {
            return getDiff(cur);
        }
        int last = 32 - Integer.numberOfLeadingZeros(cur);
        int ans = Integer.MAX_VALUE;
        for (int n = last; n < N - remain + 1; n++) {
            ans = Math.min(ans, dfs(cur | 1 << n));
        }
        return ans;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        S = new int[N][N];
        for (int i = 0; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(dfs(0));
    }
}