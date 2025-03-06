import java.util.*;
import java.io.*;

public class Main {
    static int[] dp = new int[1<<25];
    static int dfs(int map, int cur) {
        map &= ~cur;
        int ntz = Integer.numberOfTrailingZeros(cur);
        if (ntz > 4 && (map & (cur >> 5)) != 0)
            map = dfs(map, cur >> 5);
        if (ntz < 20 && (map & (cur << 5)) != 0)
            map = dfs(map, cur << 5);
        if (ntz % 5 != 0 && (map & (cur >> 1)) != 0)
            map = dfs(map, cur >> 1);
        if (ntz % 5 != 4 && (map & (cur << 1)) != 0)
            map = dfs(map, cur << 1);
        return map;
    }
    static boolean isConnected(int map) {
        map = dfs(map, Integer.highestOneBit(map));
        if (map == 0)
            return true;
        return false;
    }
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int map = 0, count = 0;
        for (int i = 0; i < 5; i++) {
            String line = br.readLine();
            for (int j = 0; j < 5; j++) {
                if (line.charAt(j) == '*') {
                    map |= 1 << (i*5+j);
                    count++;
                }
            }
        }
        Arrays.fill(dp, -1);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(map);
        dp[map] = 0;
        if (isConnected(map)) {
            System.out.println("0");
            return;
        }
        while (!q.isEmpty()) {
            int cur = q.poll();
            int origin = cur;
            int nextDp = dp[origin]+1;
            while (cur != 0) {
                int msb = Integer.highestOneBit(cur);
                int ntz = Integer.numberOfTrailingZeros(msb);
                
                int down = (origin | (msb >> 5)) & ~msb;
                if (ntz > 4 && Integer.bitCount(down) == count && dp[down] == -1) {
                    q.add(down);
                    dp[down] = nextDp;
                    if (isConnected(down)) {
                        System.out.println(nextDp);
                        return;
                    }
                }
                
                int up = (origin | (msb << 5)) & ~msb;
                if (ntz < 20 && Integer.bitCount(up) == count && dp[up] == -1) {
                    q.add(up);
                    dp[up] = nextDp;
                    if (isConnected(up)) {
                        System.out.println(nextDp);
                        return;
                    }
                }
                
                int right = (origin | (msb >> 1)) & ~msb;
                if (ntz % 5 != 0 && Integer.bitCount(right) == count && dp[right] == -1) {
                    q.add(right);
                    dp[right] = nextDp;
                    if (isConnected(right)) {
                        System.out.println(nextDp);
                        return;
                    }
                }
                
                int left = (origin | (msb << 1)) & ~msb;
                if (ntz % 5 != 4 && Integer.bitCount(left) == count && dp[left] == -1) {
                    q.add(left);
                    dp[left] = nextDp;
                    if (isConnected(left)) {
                        System.out.println(nextDp);
                        return;
                    }
                }
                
                cur &= ~msb;
            }
        }
        return;
    }
}