//40분 소요

import java.util.*;
import java.io.*;

class SegTree {
    int leafStart;
    long[] tree;
    public SegTree(int n) {
        tree = new long[n*4];
        leafStart = Integer.highestOneBit(n-1) << 1;
    }
    public void add(int pos, int val) {
        int node = leafStart + pos;
        while (node != 0) {
            tree[node] += val;
            node /= 2;
        }
    }
    public long get(int s, int e) {
        return get(s, e, 0, leafStart-1, 1);
    }
    private long get(int s, int e, int ns, int ne, int n) {
        if (ne < s || e < ns)
            return 0;
        if (s <= ns && ne <= e)
            return tree[n];
        int mid = (ns + ne) / 2;
        long left = get(s, e, ns, mid, n * 2);
        long right = get(s, e, mid+1, ne, n * 2 + 1);
        return left + right;
    }
}

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        
        SegTree segTree = new SegTree(N);
        var sb = new StringBuilder();
        while (Q-- != 0) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (op == 1)
                segTree.add(a-1, b);
            else
                sb.append(segTree.get(a-1, b-1)).append('\n');
        }
        System.out.print(sb.toString());
    }
}