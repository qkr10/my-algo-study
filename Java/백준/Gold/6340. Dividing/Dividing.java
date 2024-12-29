import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] resultStrs = new String[]{
                "Collection #%d:\nCan't be divided.\n",
                "Collection #%d:\nCan be divided.\n"
        };
        int T = 1;
        while (true) {
            String[] inputStrs = br.readLine().split(" ");
            int[] inputs = Arrays.stream(inputStrs)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int sum = 0;
            for (int i = 0; i < 6; i++)
                sum += inputs[i] * (i+1);
            if (sum == 0)
                break;

            if (T != 1)
                System.out.print("\n");

            if (sum % 2 == 0 && isPossible(inputs, sum / 2))
                System.out.printf(resultStrs[1], T);
            else
                System.out.printf(resultStrs[0], T);

            T++;
        }
    }

    public static boolean isPossible(int[] inputs, int target) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int v = 1; v <= 6; v++) {
            int count = inputs[v-1];
            for (int t = target; t >= 0; t--)
                for (int c = 1; c <= count && t >= v * c; c++)
                    dp[t] |= dp[t - v * c];
        }

        return dp[target];
    }
}