//5분만 고민하고 답 찾아봄.
//총 47분 소요

import java.util.*;
import java.io.*;

class UnionFind {
    private int[] parents;
    private int[] ranks;
    private int[] sizes;
    public UnionFind(int n) {
        parents = new int[n];
        Arrays.fill(parents, -1);
        sizes = new int[n];
        Arrays.fill(sizes, 1);
        ranks = new int[n];
    }
    public void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b)
            return;
        
        int asz = sizes[a];
        int bsz = sizes[b];
        int arank = ranks[a];
        int brank = ranks[b];
        
        if (arank == brank) {
            parents[b] = a;
            ranks[a]++;
            sizes[a] = asz + bsz;
        } else if (arank > brank) {
            parents[b] = a;
            sizes[a] = asz + bsz;
        } else {
            parents[a] = b;
            sizes[b] = asz + bsz;
        }
    }
    
    public int find(int a) {
        if (parents[a] == -1)
            return a;
        return parents[a] = find(parents[a]);
    }
    
    public int size(int a) {
        return sizes[find(a)];
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        int[][] links = new int[M][2];
        for (int[] link : links) {
            st = new StringTokenizer(br.readLine());
            link[0] = Integer.parseInt(st.nextToken()) - 1;
            link[1] = Integer.parseInt(st.nextToken()) - 1;
        }
        int[] queries = new int[Q];
        for (int q = 0; q < Q; q++)
            queries[q] = Integer.parseInt(br.readLine()) - 1;
        
        int[] sortedQueries = new int[Q+1];
        sortedQueries[Q] = 0x7fffffff;
        System.arraycopy(queries, 0, sortedQueries, 0, Q);
        Arrays.sort(sortedQueries);
        
        UnionFind uf = new UnionFind(N);
        int q = 0;
        for (int l = 0; l < M; l++) {
            if (sortedQueries[q] == l) {
                q++;
                continue;
            }
            uf.union(links[l][0], links[l][1]);
        }
        
        long sum = 0;
        for (q = Q-1; q >= 0; q--) {
            int linkIdx = queries[q];
            int[] link = links[linkIdx];
            int ans = 0;
            if (uf.find(link[0]) != uf.find(link[1]))
                ans = uf.size(link[0]) * uf.size(link[1]);
            uf.union(link[0], link[1]);
            sum += ans;
        }
        System.out.println(sum);
    }
}