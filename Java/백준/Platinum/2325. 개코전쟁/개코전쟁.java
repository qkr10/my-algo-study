//41분 소요

import java.util.*;
import java.io.*;

public class Main{
    static List<Integer> bfs(List<List<Integer>> graph, int[][] weight) {
        int[] visit = new int[graph.size()];
        Arrays.fill(visit, -1);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->a[0]-b[0]);
        pq.add(new int[]{0, 0, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int dist = cur[0];
            int node = cur[1];
            if (visit[node] != -1)
                continue;
            visit[node] = cur[2];
            if (node == graph.size()-1) {
                List<Integer> ret = new ArrayList<>();
                ret.add(node);
                while (node != 0) {
                    node = visit[node];
                    ret.add(node);
                }
                return ret;
            }
            for (int next : graph.get(node)) {
                int w = weight[node][next];
                if (w == 0)
                    continue;
                if (visit[next] != -1)
                    continue;
                pq.add(new int[]{dist+w, next, node});
            }
        }
        return null;
    }
    static int bfs2(List<List<Integer>> graph, int[][] weight) {
        var path = bfs(graph, weight);
        int ret = 0;
        for (int i = 1; i < path.size(); i++) {
            ret += weight[path.get(i-1)][path.get(i)];
        }
        return ret;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        List<List<Integer>> graph = new ArrayList<>();
        int[][] weight = new int[N][N];
        for (int i = 0; i < N; i++)
            graph.add(new ArrayList<>());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            graph.get(b).add(a);
            weight[a][b] = weight[b][a] = c;
        }
        
        List<Integer> path = bfs(graph, weight);
        int maxDist = Integer.MIN_VALUE;
        for (int i = 1; i < path.size(); i++) {
            int a = path.get(i-1);
            int b = path.get(i);
            int c = weight[a][b];
            weight[a][b] = weight[b][a] = 0;
            
            int dist = bfs2(graph, weight);
            maxDist = Math.max(dist, maxDist);
            
            weight[a][b] = weight[b][a] = c;
        }
        System.out.println(maxDist);
    }
}