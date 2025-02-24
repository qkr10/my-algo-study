//1시간 51분 소요

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        q.add(new int[]{N, 0});
        boolean[][] visit = new boolean[500001][2];
        visit[N][0] = true;
        int ans = Integer.MAX_VALUE;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int a1 = K, a2 = 1;
        while (a1 <= 500000) {
            map.put(a1, a2-1);
            a1 += a2;
            a2++;
        }
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            
            long temp = cur[1] * (cur[1] + 1) / 2;
            if (cur[0] == K + temp) {
                ans = Math.min(ans, cur[1]);
                continue;
            } else {
                var res = map.get(cur[0]);
                if (res != null && res > cur[1])
                    if (Math.abs(res - cur[1]) % 2 == 0)
                        ans = Math.min(ans, res);
            }
            
            if (cur[0]-1 >= 0 && !visit[cur[0]-1][cur[1] & 1 ^ 1]) {
                q.add(new int[]{cur[0]-1, cur[1]+1});
                visit[cur[0]-1][cur[1] & 1 ^ 1] = true;
            }
            if (cur[0]+1 <= 500000 && !visit[cur[0]+1][cur[1] & 1 ^ 1]) {
                q.add(new int[]{cur[0]+1, cur[1]+1});
                visit[cur[0]+1][cur[1] & 1 ^ 1] = true;
            }
            if (cur[0]*2 <= 500000 && !visit[cur[0]*2][cur[1] & 1 ^ 1]) {
                q.add(new int[]{cur[0]*2, cur[1]+1});
                visit[cur[0]*2][cur[1] & 1 ^ 1] = true;
            }
        }
        if (ans == Integer.MAX_VALUE)
            ans = -1;
        System.out.println(ans);
    }
}
