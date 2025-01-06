import java.util.*;
import java.io.*;

class Edge implements Comparable {
    public int a, b, w;
    public Edge(int a, int b, int w) {
        this.a = a; this.b = b; this.w = w;
    }
    @Override
    public int compareTo(Object e) {
        return w - ((Edge)e).w;
    }
}

class UnionFind {
    public int[] list;
    public int[] rank;
    public UnionFind(int size) {
        list = new int[size];
        Arrays.fill(list, -1);
        rank = new int[size];
    }
    public void union(int a, int b) {
        if (rank[a] == rank[b]) {
            list[a] = b;
            rank[a]++;
        }
        else if (rank[a] < rank[b])
            list[a] = b;
        else
            list[b] = a;
    }
    public int find(int a) {
        if (list[a] == -1)
            return a;
        return list[a] = find(list[a]);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int A = Integer.parseInt(line[0]);
        int B = Integer.parseInt(line[1]);
        int N = Integer.parseInt(line[2]);
        int M = Integer.parseInt(line[3]);
        int[] vFence = new int[N+2];
        int[] hFence = new int[M+2];
        for (int i = 0; i < N; i++)
            vFence[i] = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++)
            hFence[i] = Integer.parseInt(br.readLine());
        vFence[N] = hFence[M] = 0;
        vFence[N+1] = B;
        hFence[M+1] = A;

        Arrays.sort(vFence);
        Arrays.sort(hFence);

        Edge[] edges = new Edge[(N+1)*(M+1)*2 - (N+1) - (M+1)];
        int edgeCount = 0;
        for (int m = 0; m < M+1; m++) {
            int leftWeight = hFence[m+1] - hFence[m];
            for (int n = 0; n < N+1; n++) {
                int cur = m*(N+1) + n;
                int upWeight = vFence[n+1] - vFence[n];
                if (n > 0) {
                    int left = cur - 1;
                    edges[edgeCount++] = new Edge(cur, left, leftWeight);
                }
                if (m > 0) {
                    int up = cur - N - 1;
                    edges[edgeCount++] = new Edge(cur, up, upWeight);
                }
            }
        }

        //kruskal
        Arrays.sort(edges);
        UnionFind uf = new UnionFind((N+1)*(M+1));
        long result = 0;
        for (Edge edge : edges) {
            int aRoot = uf.find(edge.a);
            int bRoot = uf.find(edge.b);
            if (aRoot == bRoot)
                continue;
            uf.union(aRoot, bRoot);
            result += edge.w;
        }
        System.out.println(result);
    }
}