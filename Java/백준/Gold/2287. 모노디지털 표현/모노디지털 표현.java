import java.io.*;
import java.util.*;

public class Main {
    static int[] arr;
    static int K, N, A;
    static List<Set<Integer>> dp;
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine());
        arr = new int[]{1, 11, 111, 1111, 11111, 111111, 1111111, 11111111};
        arr = Arrays.stream(arr).map(a -> a*K).toArray();
        dp = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            dp.add(new TreeSet<>());

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            A = Integer.parseInt(br.readLine());
            int result = solve();
            if (result == Integer.MAX_VALUE)
                System.out.println("NO");
            else
                System.out.println(result);
        }
    }
    static int solve() {
        for (int i = 1; i < 9; i++) {
            dp.get(i).add(arr[i-1]);
            for (int j = 1; j < i; j++) {
                for (int a : dp.get(j)) {
                    for (int b : dp.get(i - j)) {
                        dp.get(i).add(a + b);
                        dp.get(i).add(a - b);
                        dp.get(i).add(a * b);
                        if (b != 0)
                            dp.get(i).add(a / b);
                    }
                }
            }
            for (int a : dp.get(i))
                if (a == A) return i;
        }
        return Integer.MAX_VALUE;
    }
}