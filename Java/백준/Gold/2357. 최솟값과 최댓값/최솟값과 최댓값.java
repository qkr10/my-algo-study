//30분 소요

import java.io.*;
import java.util.*;
import java.util.function.*;

class SegTree {
    int[] tree;
    int leafStart;
    BiFunction<Integer, Integer, Integer> fn;
    int init;
    public SegTree(int len, BiFunction<Integer, Integer, Integer> fn, int init) {
        leafStart = Integer.highestOneBit(len-1) << 1;
        tree = new int[leafStart << 1];
        Arrays.fill(tree, init);
        this.init = init;
        this.fn = fn;
    }
    public int get(int s, int e) {
        return get(s, e, 0, leafStart-1, 1);
    }
    private int get(int s, int e, int ns, int ne, int n) {
        if (ne < s || e < ns)
            return init;
        if (s <= ns && ne <= e)
            return tree[n];
        int mid = (ns + ne) / 2;
        int left = get(s, e, ns, mid, n*2);
        int right = get(s, e, mid+1, ne, n*2+1);
        return fn.apply(left, right);
    }
    public void set(int pos, int val) {
        int node = leafStart + pos;
        tree[node] = val;
        node /= 2;
        while (node != 0) {
            tree[node] = fn.apply(tree[node * 2], tree[node * 2 + 1]);
            node /= 2;
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        SegTree minSeg = new SegTree(N, Math::min, Integer.MAX_VALUE);
        SegTree maxSeg = new SegTree(N, Math::max, 0);
        for (int i = 0; i < N; i++) {
            int val = Integer.parseInt(br.readLine());
            minSeg.set(i, val);
            maxSeg.set(i, val);
        }
        
        var sb = new StringBuffer();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            sb.append(minSeg.get(a, b)).append(' ');
            sb.append(maxSeg.get(a, b)).append('\n');
        }
        System.out.println(sb);
    }
}