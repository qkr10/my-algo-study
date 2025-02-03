//6시간 가량 소요

import java.util.*;
import java.io.*;

public class Main {
    static long sumOfDigit(long n) {
        long ret = 0;
        while (n != 0) {
            ret += n % 10;
            n /= 10;
        }
        return ret;
    }
    static long sum(long n) {
        long ret = 0;
        for (int i = 1; i <= n; i++)
            ret += sumOfDigit(i);
        return ret;
    }
    
    static long calc(int n) {
        if (n <= 0) return 0;
        long result = 0;
        long pow = 10, pow_ = 1;
        int i = 0;
        while (n != 0) {
            result += sumOfDigit(n / pow * pow) * (n % pow);
            result += (n / pow_ % 10) + sum(n / pow_ % 10 - 1) * pow_;
            result += (n / pow_ % 10) * (45 * pow_ / 10 * i);
            
            n -= n % pow;
            pow *= 10;
            pow_ *= 10;
            i++;
        }
        return result;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        long result = calc(b) - calc(a - 1);
        System.out.println(result);
    }
}