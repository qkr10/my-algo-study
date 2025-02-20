//35분 소요

import java.util.*;
import java.io.*;

public class Main {
    static final int mod = 1000000007;
    
    static long pow(long n, long pow) {
        long ret = 1;
        while (pow != 0) {
            if ((pow & 1) == 1)
                ret = ret * n % mod;
            n = n * n % mod;
            pow >>= 1;
        }
        return ret;
    }
    static long inv(long n) {
        return pow(n, mod-2);
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int M = Integer.parseInt(br.readLine());
        
        int[] factorial = new int[4000001];
        factorial[0] = 1;
        for (int i = 1; i < 4000001; i++)
            factorial[i] = (int)((long)factorial[i-1] * i % mod);
        
        for (int i = 1; i <= M; i++) {
            var st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            long div = inv(factorial[K]) * inv(factorial[N-K]) % mod;
            long ans = factorial[N] * div % mod;
            System.out.println(ans);
        }
    }
}