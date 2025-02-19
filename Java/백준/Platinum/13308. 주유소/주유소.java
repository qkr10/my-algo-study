import java.util.*;
import java.io.*;

public class Main {
    static List<int[]>[] graph;
    static int[] prices;
    static int N, M;
    static long minCost() {
        long[][] visit = new long[N][5001];
        for (int i = 0; i < N; i++) Arrays.fill(visit[i], 0x7fffffffffffL);
        //visit[i].get(j) = i까지 오는 동안 마주한 주유소중 최소 리터당 가격이 j일때 최소비용.
        PriorityQueue<long[]> q = new PriorityQueue<>((a, b)->(int)(a[0]-b[0]));
        q.add(new long[]{0L, 0L, 5000L});
        while (!q.isEmpty()) {
            long[] cur = q.poll();
            long curCost = cur[0];
            int city = (int)cur[1];
            int minPrice = (int)cur[2];
            if (city == N - 1)
                return curCost;
            if (visit[city][minPrice] < curCost)
                continue;
            visit[city][minPrice] = curCost;
            minPrice = Math.min(minPrice, prices[city]);
            for (int[] neighbor : graph[city]) {
                int n = neighbor[0];
                int d = neighbor[1];
                long nextCost = curCost + (long)minPrice * d;
                q.add(new long[]{nextCost, n, minPrice});
            }
        }
        return -1;
    }
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        prices = new int[N];
        graph = new ArrayList[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            prices[i] = Integer.parseInt(st.nextToken());
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            graph[a].add(new int[]{b, c});
            graph[b].add(new int[]{a, c});
        }
        System.out.println(minCost());
    }
}