//1시간 30분 소요
//롤링 해시, 라빈 카프 등을 검색해서 구현

import java.util.*;
import java.io.*;

public class Main {
    private static int p = 1000000007;
    private static int x = 1;
    private static int getRollingHash(int prev, char cur, int xx) {
        //str[0] * x^0 + str[1] * x^1 ... mod p
        long temp = ((long)cur * (long)xx) % (long)p;
        long ans = ((long)prev + temp) % p;
        return (int)ans;
    }
    private static int randomRange(int a, int b) {
        return new Random().nextInt(b - a) + a;
    }
    private static int getNum(char[] str) {
        /*
        n = str의 길이
        num = 0 <= k < n 인 정수 k에 대해서 str[0:k] == str[n-k-1:n-1] 이 되게하는 최대 k 값
            k 가 존재하지 않으면 -1
        */
        int n = str.length, num = -1, res = -1;
        
        x = randomRange((int)Math.sqrt(p) - 1000, (int)Math.sqrt(p));
        int hash1 = 0, hash2 = 0;
        long xx = x;
        
        while (num + 2 < n) {
            hash1 = getRollingHash(hash1, str[num+1], (int)xx);
            hash2 = getRollingHash(hash2, str[n-num-2], x);
            if (hash1 == hash2)
                res = num+1;
            num++;
            xx = (xx * x) % p;
            hash2 = (int)(((long)hash2 * x) % p);
        }
        return res;
    }
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        char[] str = st.nextToken().toCharArray();
        int k = Integer.parseInt(st.nextToken());
        int num = getNum(str) + 1;
        long ans = ((long)str.length - num) * k + num;
        System.out.println(ans);
    }
}