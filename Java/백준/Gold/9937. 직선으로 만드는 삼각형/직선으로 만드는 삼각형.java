import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        TreeSet<Long> lines = new TreeSet<>();
        TreeMap<Long, Integer> sameLines = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(" ");
            int a = Integer.parseInt(line[0]);
            int b = Integer.parseInt(line[1]);
            if ((a < 0 && b < 0) || (a < 0 && b > 0)) {
                a *= -1;
                b *= -1;
            }
            int cd = gcd(a, b);
            long key = (long)(a/cd) << 32 | (b/cd);
            if (!lines.add(key))
                sameLines.compute(key, (k, v) -> v == null ? 2 : v+1);
        }
        
        int mod = 1000000007;
        long result = combination(N, 3, mod);
        for (int count : sameLines.values()) {
            // 기울기가 같은 선분 두개를 변으로 하는 삼각형 제거
            long minus = (long)(N-count) * combination(count, 2, mod) % mod;
            // 기울기가 같은 선분으로만 만들어진 삼각형 제거
            minus = (minus + combination(count, 3, mod)) % mod;
            result = (result - minus + mod) % mod;
        }
        System.out.println(result);
    }
    
    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    
    private static int combination(int n, int m, int mod) {
        if (n < m) return 0;
        int inverse1 = inverse(factorial(m, mod), mod);
        int inverse2 = inverse(factorial(n - m, mod), mod);
        long result = (long)factorial(n, mod) * inverse1 % mod * inverse2 % mod;
        return (int)result;
    }
    
    private static int inverse(int n, int mod) {
        long result = 1;
        long base = n;
        int pow = mod - 2;
        while (pow != 0) {
            if ((pow & 1) == 1)
                result = (result * base) % mod;
            pow >>= 1;
            base = base * base % mod;
        }
        return (int)result;
    }
    
    private static int factorial(int n, int mod) {
        long result = 1;
        for (int i = 2; i <= n; i++)
            result = result * i % mod;
        return (int)result;
    }
}