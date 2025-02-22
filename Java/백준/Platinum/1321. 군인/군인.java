//펜윅트리 첫 구현 (448ms)
//26분 소요
//세그트리도 짜서 비교해보기 (564ms)

import java.util.*;
import java.io.*;

class Fenwick {
    int[] tree;
    int N;
    public Fenwick(int n) {
        tree = new int[n+1];
        N = n;
    }
    public void add(int pos, int val) {
        while (pos <= N) {
            tree[pos] += val;
            pos += pos & -pos;
        }
    }
    public int get(int pos) {
        int ret = 0;
        while (0 < pos) {
            ret += tree[pos];
            pos &= pos - 1;
        }
        return ret;
    }
}

class SegTree {
    int[] tree;
    int leafStart;
    public SegTree(int n) {
        tree = new int[n*4];
        leafStart = Integer.highestOneBit(n-1) << 1;
    }
    public void add(int pos, int val) {
        pos += leafStart;
        while (pos != 0) {
            tree[pos] += val;
            pos /= 2;
        }
    }
    public int get(int l, int r) {
        return get(l, r, 0, leafStart-1, 1);
    }
    private int get(int l, int r, int ln, int rn, int n) {
        if (rn < l || r < ln)
            return 0;
        if (l <= ln && rn <= r)
            return tree[n];
        int mid = (ln + rn) / 2;
        int a = get(l, r, ln, mid, n*2);
        int b = get(l, r, mid+1, rn, n*2+1);
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        
        //Fenwick fenwick = new Fenwick(N+1);
        SegTree segTree = new SegTree(N);
        int[] group = new int[N];
        var st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            group[i] = Integer.parseInt(st.nextToken());
            segTree.add(i, group[i]);
        }
        
        var sb = new StringBuilder();
        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            if (Integer.parseInt(st.nextToken()) == 1) {
                int pos = Integer.parseInt(st.nextToken()) - 1;
                int val = Integer.parseInt(st.nextToken());
                segTree.add(pos, val);
            } else {
                int val = Integer.parseInt(st.nextToken());
                int l = 0, r = N-1;
                while (l < r) {
                    int mid = (l + r) / 2;
                    int midVal = segTree.get(0, mid);
                    if (midVal < val) {
                        l = mid+1;
                    } else {
                        r = mid;
                    }
                }
                sb.append(r+1).append('\n');
            }
        }
        System.out.println(sb);
    }
}
