import java.util.*;
import java.io.*;

class Edges {
    public List<Integer> edges = new ArrayList<>();
}

class UnionFind {
    int[] parent, size, rank;
    public UnionFind(int N) {
        parent = new int[N];
        size = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }
    public void reset(int a) {
        parent[a] = a;
        rank[a] = 0;
        size[a] = 1;
    }
    public int getSize(int n) {
        return size[n];
    }
    public void union(int a, int b) {
        if (a == b) return;
        if (rank[a] < rank[b]) {
            parent[a] = b;
            size[b] += size[a];
        } else if (rank[b] < rank[a]) {
            parent[b] = a;
            size[a] += size[b];
        } else {
            rank[a]++;
            parent[b] = a;
            size[a] += size[b];
        }
    }
    public int find(int a) {
        if (a == parent[a]) return a;
        return parent[a] = find(parent[a]);
    }
}

class Tree {
    public int[] parent;
    public Tree(Edges[] graph) {
        parent = new int[graph.length];
        boolean[] visit = new boolean[graph.length];
        visit[0] = true;
        dfs(graph, visit, 0);
    }
    private void dfs(Edges[] graph, boolean[] visit, int n) {
        for (int child : graph[n].edges) {
            if (visit[child])
                continue;
            visit[child] = true;
            parent[child] = n;
            dfs(graph, visit, child);
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Edges[] graph = new Edges[N];
        for (int i = 0; i < N; i++)
            graph[i] = new Edges();
        for (int i = 0; i < N-1; i++) {
            var st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            graph[a].edges.add(b);
            graph[b].edges.add(a);
        }
        
        var sb = new StringBuilder();
        Tree tree = new Tree(graph);
        UnionFind uf = new UnionFind(N);
        boolean[] S = new boolean[N];
        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            var st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            int[] list = new int[K];
            for (int j = 0; j < K; j++) {
                int a = Integer.parseInt(st.nextToken()) - 1;
                uf.reset(a);
                S[a] = true;
                list[j] = a;
            }
            
            for (int a : list) {
                if (S[tree.parent[a]])
                    uf.union(uf.find(a), uf.find(tree.parent[a]));
            }
            
            long ans = 0;
            for (int a : list) {
                if (uf.find(a) == a) {
                    int size = uf.getSize(a);
                    ans += (long)size*(size-1)/2;
                }
            }
            sb.append(ans).append('\n');
            
            for (int a : list) {
                S[a] = false;
            }
        }
        System.out.print(sb);
    }
}